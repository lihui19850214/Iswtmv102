package com.serialport;

import java.util.ArrayList;
import java.util.List;
import com.serialport.Utilities;
import android.util.Log;

/****
 * 类名称：R1000UHF
 * 类描述：下位机返回帧数据的解析算法
 * 编写作者： jiangtao
 * 创建时间：2013-01-31
 ***/
public class UHF {
	
	private static final String TAG = "UHF";
	private static ArrayList<RFPowerData> mRFDataCacheList = new ArrayList<RFPowerData>();
	
	/**
	 * 返回帧数据内容的检查结果
	 * @param SerBfr 下位机返回的16进制数据帧内容
	 * @param len 数据帧的size
	 * @return 0 表示返回数据不正确  1表示正确
	 */
	public static int TestData(byte[] SerBfr, int len) {
		int serBfr_AA = Integer.parseInt(Utilities.byte2HexStr(SerBfr[0]), 16);    //返回帧起始位的int值
		if(serBfr_AA != 170) return 0;  //0表示帧数据不正确
		
		int serBfr_Length = Integer.parseInt(Utilities.byte2HexStr(SerBfr[1]), 16);   //返回帧长度的int值
		if((serBfr_Length + 1) != len) return 0;
		
		int serBfr_Status = Integer.parseInt(Utilities.byte2HexStr(SerBfr[5]), 16);   //返回状态码的int值
		if (serBfr_Status != 0)  return 0;     
		
		return 1;
	}
	
	/**
	 * 返回帧数据体内容的byte数组
	 * @param SerBfr 传入帧数据response部分的Length+Data结构数据体
	 * @return
	 */
	public static byte[] GetRFData(byte[] SerBfr, int tagLen){
		byte[] mBufferData = new byte[512];
		//获取帧数据体每一个Tag标签的长度，根据第0位的值进行计算
		System.arraycopy(SerBfr, 1, mBufferData, 0, tagLen);
		return mBufferData;
	}
	
	/**
	 * 返回RFPowerData返回数据帧标签对象的实例
	 * @param SerBfr 帧数据Length+Data部分数据
	 * @param tagCount 帧标签个数
	 * @param len 帧数据Length+Data+...+Length+Data长度
	 * @return List数组 保存从第一次读取开始的标签数据及个数
	 */
	public static ArrayList<RFPowerData> GetRFDataHexStr(byte[] oldSerBfr, int tagCount, int len){
//		ArrayList<RFPowerData> mRFDataArray = new ArrayList<RFPowerData>();
		//获取帧数据体每一个Tag标签的长度，从第0位开始
		for(int i=0; i<tagCount; i++) {
			byte[] mBufferData = new byte[512];
			byte[] newSerBfr = new byte[512];
			
			int serBuffer_TagLength = Integer.parseInt(Utilities.byte2HexStr(oldSerBfr[0]), 16);
			System.arraycopy(oldSerBfr, 1, mBufferData, 0, serBuffer_TagLength);
			System.arraycopy(oldSerBfr, serBuffer_TagLength + 1, newSerBfr, 0, len - serBuffer_TagLength - 1);
			
			oldSerBfr = newSerBfr;
			
			String hstmp = Utilities.byte2HexStr(mBufferData, serBuffer_TagLength);
			if(!GetRFDataDuplicateCount(hstmp)) {
				AddRFReadDataToArray(hstmp, 1); 
			}
		}
		return mRFDataCacheList;
	}
	
	/**
	 * 判断返回帧数据中的标签是否重复
	 * @param HexRFTag
	 * @param RFTagCacheList
	 * @return
	 */
	public static boolean GetRFDataDuplicateCount(String HexRFTag){
		for(RFPowerData epcData : mRFDataCacheList) {
			if (HexRFTag.equalsIgnoreCase(epcData.getTargeId())) {
				int n = epcData.getCount() + 1;
				epcData.setCount(n);
				Log.i(TAG, "多次出现：tagId：" + epcData.getTargeId() + "  count：" + epcData.getCount());
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 解析返回的帧数据时，对于单次出现的数据，需要添加到全局ArrayList缓存中
	 * @param tagId
	 * @param ncount
	 */
	public static ArrayList<RFPowerData> AddRFReadDataToArray(String tagId, int ncount){
		RFPowerData epcData = new RFPowerData();
		epcData.setTargeId(tagId);
		epcData.setCount(ncount);
		mRFDataCacheList.add(epcData);
		Log.i(TAG, "单次出现：tagId：" + epcData.getTargeId() + "  count：" + epcData.getCount());
		return mRFDataCacheList;
	}
	
	public static void cleanmRFDataCache() {
		mRFDataCacheList.clear();
	}
	
	/**
	 * 解析返回的帧数据时，对于单次出现的数据，需要添加到全局ArrayList缓存中
	 * @param tagId
	 * @param ncount
	 */
	public static ArrayList<RFPowerData> addScannerDataToArray(String tagId, List<RFPowerData> getRFPowerList){
		int nindex = getRFPowerList.size() + 1;
		boolean flag = false;
		if(tagId.length() > 0){
			for(RFPowerData pf : getRFPowerList){
				if(pf.getTargeId().equals(tagId)){
					pf.setCount(pf.getCount() + 1);
					flag = true;
				} 
			}
			if(!flag) {
				RFPowerData epcData = new RFPowerData();
				epcData.setTargeId(tagId);
				epcData.setIndex(nindex);
				epcData.setCount(1);
				getRFPowerList.add(epcData);
			}
		}
		return (ArrayList<RFPowerData>) getRFPowerList;
	}
	
	/**
	 * CRC16数据校验函数
	 * @param buff
	 * @param bufflen
	 * @param lsb
	 * @param msb
	 */
	public static void Get_CRC16(byte[] buff, int offset, int bufflen, byte[] lsb, byte[] msb) {
		short crc = (short) 0xFFFF;          // Preload to FFFF
		short tempresults;           // Just a temporary results holder
		short bitindex;           // 0 - 7
		short byteindex;           // The byte pointer into the byte array that holds
		// the command to be checked.
		byte placeholder;           // A place to put the byte while we work on it.

		for (byteindex = 0; byteindex < bufflen ; byteindex++) { // begin checking after SOH and before CRC bytes
			placeholder =  buff[offset + byteindex];
			for(bitindex = 0; bitindex <= 7; bitindex++) {
				tempresults = (short) ((crc >> 15) ^ (placeholder >> 7));    // Shift CRC right 15 bits then do a bitwise XOR
				crc <<= 1;             // Shift CRC left one bit and store it in CRC
				if(tempresults != 0) {
					crc ^= 0x1021;           // Standard CCITT Polynomial X16+X12+X5+1
				}
				placeholder <<= 1;
			}
		}
		lsb[0] = (byte) (crc & 0x00ff);	
		msb[0] = (byte) ((crc>>8) & 0x00ff);	
	}
	
	public static boolean isCode(byte[] readBuf, int len) {
		byte ch;
		if(null != readBuf && len > 0){
			for (int i = 0; i < len; i++) {
				ch = readBuf[i];
				if (ch == 0x0a) {
					len = i;
					break;
				}
				if ((ch >= '0' && ch <= '9')
					||(ch >= 'a' && ch <= 'z')
					||(ch >= 'A' && ch <= 'Z')
					)
				{
				}
				else {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
}