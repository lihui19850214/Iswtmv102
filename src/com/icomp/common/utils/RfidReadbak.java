package com.icomp.common.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.icomp.Iswtmv10.R;
import com.serialport.AppContext;
import com.serialport.RFPowerData;
import com.serialport.UHF;

import java.util.ArrayList;
import java.util.List;

public class RfidReadbak {

    private static byte[] nTagCount;
    private static byte[] uReadData; // 帧标签数据
    private static int m_serial = -1;
    private static byte[] GetRFTagData = new byte[512];
    private static List<RFPowerData> GetRFPowerList;
    private static SoundPool mSoundPool;
    private static int musickey;
    private static float audioMaxVolumn, audioCurrentVolumn, volumnRatio;

    /**
     * 启动RFID读头模块
     *
     * @return
     */
    public static void initRfid(Activity activity) {
        //如果没有启动,则启动RFID读头模块
        if (musickey < 1) {
            AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
            // 为音效做准备，创建声音池的对象
            mSoundPool = new SoundPool(3, am.getStreamVolume(AudioManager.STREAM_SYSTEM), 100);
            // 创建声音资源的ID
            musickey = mSoundPool.load(activity, R.raw.sound1, 1);
            audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
            audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
            volumnRatio = 1;
            while (true) {
                if (musickey > 0) {
                    // 打开串口
                    AppContext.getJni().WIrUHFConnect(AppContext.SERIALPORTPATH_TTYSAC1, AppContext.SERIALPORTSPEED_B115200);
                    return;
                }
            }
        }
    }

    /**
     * 启动RFID读头模块
     *
     * @return
     */
    public static void initRfid(Activity activity, boolean isopen) {
        // 设置频率
        AudioManager am = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        // 为音效做准备，创建声音池的对象
        mSoundPool = new SoundPool(3, am.getStreamVolume(AudioManager.STREAM_SYSTEM), 100);
        // 创建声音资源的ID
        musickey = mSoundPool.load(activity, R.raw.sound1, 1);
        audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
        audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
        volumnRatio = 1;

        while (true) {
            if (musickey > 0) {
                // 打开串口
                AppContext.getJni().WIrUHFConnect(AppContext.SERIALPORTPATH_TTYSAC1, AppContext.SERIALPORTSPEED_B115200);
                return;
            }
        }
    }

    /**
     * 读取RFID标签
     *
     * @return
     */
    public static String readRfid(boolean scanf) {
        m_serial = 0;
        GetRFPowerList = new ArrayList<RFPowerData>();
        nTagCount = new byte[2];
        uReadData = new byte[512];
        //while (scanf) {
        m_serial = AppContext.getJni().WIrUHFInventoryOnce(nTagCount, uReadData);
        if (m_serial > 0) {
            int GetRFTagDataLen = uReadData.length - 2; // 指针从第9位开始复制，总长度要-2位CRC校验码
            System.arraycopy(uReadData, 0, GetRFTagData, 0, GetRFTagDataLen);
            GetRFPowerList = UHF.GetRFDataHexStr(GetRFTagData, nTagCount[0], GetRFTagDataLen);
        }
        if (GetRFPowerList.size() > 0 && GetRFPowerList.get(0).getTargeId() != null && !"".equals(GetRFPowerList.get(0).getTargeId())) {

            // 播放背景音乐
            mSoundPool.play(musickey, volumnRatio, volumnRatio, 1, 0, 1);
            String rfid = GetRFPowerList.get(0).getTargeId();
            GetRFPowerList.clear();
            if (scanf) {
                nTagCount = new byte[2];
                uReadData = new byte[512];
                //AppContext.getJni().WIrUHFDisconnect();
            }
            return rfid;
        }
        //}g
        return null;

    }

    /**
     * 关闭rfid读头
     *
     * @return
     */
    public static void close() {
        //        if (GetRFPowerList != null) {
        //            GetRFPowerList.clear();
        //        }
        //        nTagCount = new byte[2];
        //        uReadData = new byte[512];
        //        AppContext.getJni().WIrUHFDisconnect();
    }

    /**
     * 关闭rfid读头
     *
     * @return
     */
    public static void close(boolean isClose) {
        if (GetRFPowerList != null) {
            GetRFPowerList.clear();
        }
        nTagCount = new byte[2];
        uReadData = new byte[512];
        AppContext.getJni().WIrUHFDisconnect();
    }
}
