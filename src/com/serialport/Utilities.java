package com.serialport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
public final class Utilities
{

    public static final String EXT_APP = ".apk";

    public static final Rect S_BOUNDS = new Rect();

    public static final Rect S_OLD_BOUNDS = new Rect();

    public static final Paint S_PAINT = new Paint();

    public static Canvas sCanvas = new Canvas();

    public static int sIconHeight = -1;

    public static int sIconWidth = -1;

    public static final int TYPE_APP = 1;

    public static final int TYPE_UNKOWN = 0;

    public static final int TYPE_WIDGET = 2;
    static
    {
        sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));
    }

    /**
     * Returns a Bitmap representing the thumbnail of the specified Bitmap. The size of the thumbnail is defined by the
     * dimension android.R.dimen.launcher_application_icon_size. This method is not thread-safe and should be invoked on
     * the UI thread only.
     * 
     * @param bitmap
     *        The bitmap to get a thumbnail of.
     * @param current
     *        The application's current.
     * @return A thumbnail for the specified bitmap or the bitmap itself if the thumbnail could not be created.
     */
    public static Bitmap createBitmapThumbnail(Bitmap bitmap, Context context)
    {
        if (sIconWidth == -1)
        {
            final Resources resources = context.getResources();
            sIconWidth =
                sIconHeight =
                    (int) resources.getDimension(android.R.dimen.app_icon_size);
        }
        int width = sIconWidth;
        int height = sIconHeight;
        return Utilities.createBitmapThumbnail(bitmap, width, height);
    }

    public static Bitmap createBitmapThumbnail(Bitmap bitmap, int width,
        int height)
    {
        final int bitmapWidth = bitmap.getWidth();
        final int bitmapHeight = bitmap.getHeight();
        if (width > 0 && height > 0)
        {
            if (width < bitmapWidth || height < bitmapHeight)
            {
                final float ratio = (float) bitmapWidth / bitmapHeight;
                if (bitmapWidth > bitmapHeight)
                {
                    height = (int) (width / ratio);
                }
                else if (bitmapHeight > bitmapWidth)
                {
                    width = (int) (height * ratio);
                }
                final Bitmap.Config c =
                    (width == sIconWidth && height == sIconHeight) ? bitmap
                        .getConfig() : Bitmap.Config.ARGB_8888;
                Bitmap.Config cc = (null == c) ? Bitmap.Config.ARGB_8888 : c;
                final Bitmap thumb =
                    Bitmap.createBitmap(sIconWidth, sIconHeight, cc);
                final Canvas canvas = sCanvas;
                final Paint paint = S_PAINT;
                canvas.setBitmap(thumb);
                paint.setDither(false);
                paint.setFilterBitmap(true);
                S_BOUNDS.set((sIconWidth - width) / 2,
                    (sIconHeight - height) / 2, width, height);
                S_OLD_BOUNDS.set(0, 0, bitmapWidth, bitmapHeight);
                canvas.drawBitmap(bitmap, S_OLD_BOUNDS, S_BOUNDS, paint);
                return thumb;
            }
            else if (bitmapWidth < width || bitmapHeight < height)
            {
                final Bitmap.Config c = Bitmap.Config.ARGB_8888;
                final Bitmap thumb =
                    Bitmap.createBitmap(sIconWidth, sIconHeight, c);
                final Canvas canvas = sCanvas;
                final Paint paint = S_PAINT;
                canvas.setBitmap(thumb);
                paint.setDither(false);
                paint.setFilterBitmap(true);
                canvas.drawBitmap(bitmap, (sIconWidth - bitmapWidth) / 2.0F,
                    (sIconHeight - bitmapHeight) / 2.0F, paint);
                return thumb;
            }
        }
        return bitmap;
    }

    public static Bitmap drawableToBitmap(Drawable drawable)
    {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config =
            drawable.getOpacity() != PixelFormat.OPAQUE
                ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
    
    public static boolean checkDevice(File device) {
		try {
			/* Missing read/write permission, trying to chmod the file */
			Process su = Runtime.getRuntime().exec("/system/xbin/su");
			String cmd = "chmod 777 " + device.getAbsolutePath() + "\n" + "exit\n";
			su.getOutputStream().write(cmd.getBytes());
			if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
    }
    
    //16进制串转化为字节数组
    public static byte[] hexStringToBytes(String hexString) {
	    if ((hexString == null) || (hexString.equals(""))) {
	    	return null;
	    }
	    hexString = hexString.toUpperCase();
	    int length = hexString.length() / 2;
	    char[] hexChars = hexString.toCharArray();
	    byte[] d = new byte[length];
	    for (int i = 0; i < length; ++i) {
	    	int pos = i * 2;
	    	d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)]));
	    }
	    return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	  
	//字节数组转换成16进制串
	public static String byte2HexStr(byte[] b, int length) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < length; ++n) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
		    else 
		    	hs = hs + stmp;
		}
		return hs.toUpperCase();
	}
	
	//字节转换为16进制
	public static String byte2HexStr(byte b){
		String hs = "";
		String stmp = "";
		stmp = Integer.toHexString(b & 0xFF);
		if (stmp.length() == 1)
			hs = hs + "0" + stmp;
	    else 
	    	hs = hs + stmp;
		return hs.toUpperCase();
	}
	
	//一维扫描解析
	public static String Hex2DenaryStr(byte[] data, int len){
		byte[] temp = new byte[len + 10];
		System.arraycopy(data, 0, temp, 0, len);
		String tempBuff = new String(temp);
		return tempBuff.trim().toString(); 
	}
	
	//标签数据每1位之间用“-”隔开, tagId：E2-00-75-21-21-11-01-27-17-10-66-A0
	public static String HexString2Tag(String HexStr){
		String hs = "";
		String temp = "";
		int start = 0;
		for (int n = start; n < HexStr.length() / 2; n++) {
			temp = (String) HexStr.subSequence(start, start + 2);
			if (temp.length() == 2) {
				if(start < HexStr.length() - 2){
					hs += temp + "-";
				} else {
					hs += temp;
				}
			}	
			start += 2;
		}
		return hs.toString();
	}
	
	public static int getIndexFromList(String[] array, String str){
		String temp = null;
		for(int i=0; i<array.length; i++){
			temp = array[i];
			if(null != temp && temp.equals(str)){
				return i;
			}
		}
		return -1;
	}
	
	//判断字符串中是否包含非法字符
	public static int isConSpeCharacters(String str) {
		 if (str == null || str.length() <= 0) {
			 return 0;
		 }
		 char c;
		 for (int i = str.length() - 1; i >= 0; i--) {
			 c = str.charAt(i);
			 if (!Character.isLetter(c) && !Character.isDigit(c)) {
				 //非字母或数字
				 return 0;
			 }
		 }
		 return 1;
	}
	
	//判断输入数据是否为16进制数据
	public static int isHexString(String str){
		String ss = "0123456789ABCDEF";   
		for (int i = str.length() - 1; i >= 0; i--) {
			char temp = Character.toUpperCase(str.charAt(i));
			if (!ss.contains(String.valueOf(temp))) {
				return 0;
			}
		}
		return 1;
	}
	
	/**
	 * 转换UTC时间
	 */
	public static String getStandardDateFromUTC(long time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return simpleDateFormat.format(new Date(time));
	}
}