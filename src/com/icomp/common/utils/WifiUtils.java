package com.icomp.common.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class WifiUtils {

	/**
	 * 获取当前手持机是否连接WIFI
	 * @param inContext
	 * @return
	 */
	public static boolean isWiFiActive(Context inContext) {
		WifiManager mWifiManager = (WifiManager) inContext
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
		int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();

		if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
			// System.out.println("**** WIFI is on");
			return true;
		} else {
			// System.out.println("**** WIFI is off");
			return false;
		}
	}
	
	/**
	 * 获取当前手机Mac地址
	 * @param inContext
	 * @return
	 */
	public static String getMacAddress(Context inContext){
		WifiManager wifi = (WifiManager) inContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}
}
