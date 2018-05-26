package com.icomp.common.utils;

import android.app.Activity;
import android.media.SoundPool;

public class CardRead {

	private static byte[] uDataLen;
	private static byte[] GetHFData;
	private static SoundPool mSoundPool;
	private static int musickey;
	private static float audioMaxVolumn, audioCurrentVolumn, volumnRatio;

	public static void initRead(Activity activity) {

//		// 打开读头模块
//		AppContext.getJni().Rc663_HFConnect(AppContext.SERIALPORTPATH_TTYSAC2,
//				AppContext.SERIALPORTSPEED_B115200);
//		// 初始化
//		// 0 ISO14443A、1 ISO14443B、2 ISO15693
//		AppContext.getJni().Rc663_HFInit(0);
//
//		uDataLen = new byte[2];
//		GetHFData = new byte[100];
//		AudioManager am = (AudioManager) activity
//				.getSystemService(Context.AUDIO_SERVICE);
//		// 为音效做准备，创建声音池的对象
//		mSoundPool = new SoundPool(3,
//				am.getStreamVolume(AudioManager.STREAM_SYSTEM), 100);
//		// 创建声音资源的ID
//		musickey = mSoundPool.load(activity, R.raw.sound1, 1);
//
//		audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
//		audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
//		volumnRatio = 1;
	}

	public static void initRead(Activity activity, boolean isOpen) {

		
/*
		uDataLen = new byte[2];
		GetHFData = new byte[100];
		AudioManager am = (AudioManager) activity
				.getSystemService(Context.AUDIO_SERVICE);
		// 为音效做准备，创建声音池的对象
		mSoundPool = new SoundPool(3,
				am.getStreamVolume(AudioManager.STREAM_SYSTEM), 100);
		// 创建声音资源的ID
		musickey = mSoundPool.load(activity, R.raw.sound1, 1);

		audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
		audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_SYSTEM);
		volumnRatio = 1;
		while(true){
			if(musickey>0){
				// 打开串口
				// 打开读头模块
				AppContext.getJni().Rc663_HFConnect(AppContext.SERIALPORTPATH_TTYSAC2,
						AppContext.SERIALPORTSPEED_B115200);
				// 初始化
				// 0 ISO14443A、1 ISO14443B、2 ISO15693
				AppContext.getJni().Rc663_HFInit(0);
				return;
			}
		}*/
	}

	public static String readCard() {
/*		uDataLen = new byte[2];
		GetHFData = new byte[100];
		//while (true) {
			AppContext.getJni().Rc663_HFGetUid(GetHFData, uDataLen);
			String m_uid = Utilities.byte2HexStr(GetHFData, uDataLen[0]);
			if (m_uid != null && !"".equals(m_uid)) {

				// 播放背景音乐
				mSoundPool.play(musickey, volumnRatio, volumnRatio, 1, 0, 1);
				uDataLen = new byte[2];
				GetHFData = new byte[100];
					//AppContext.getJni().WIrUHFDisconnect();
				return m_uid;
			}
			return null;
		//}*/
	return  "1";
	}
	
	public static String readCard(boolean isread) {
	/*	while (true) {
			AppContext.getJni().Rc663_HFGetUid(GetHFData, uDataLen);
			String m_uid = Utilities.byte2HexStr(GetHFData, uDataLen[0]);
			if (m_uid != null && !"".equals(m_uid)) {

				// 播放背景音乐
				mSoundPool.play(musickey, volumnRatio, volumnRatio, 1, 0, 1);
				if(isread){
					uDataLen = new byte[2];
					GetHFData = new byte[100];
					//AppContext.getJni().WIrUHFDisconnect();
				}
				return m_uid;
			}
		}
*/
		return  "22";
	}
	
	/**
	 * 关闭卡片读头
	 */
	public static void close(){
		//AppContext.getJni().Rc663_HFDisconnect();
	}
}
