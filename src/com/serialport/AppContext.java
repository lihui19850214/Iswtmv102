package com.serialport;


import com.icomp.common.activity.CommonActivity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 类名称：AppContext 
 * 类描述：系统上下文内容 
 * 作者： jiangtao 
 * 创建时间：2013-3-22 
 */
public class AppContext implements Parcelable {
	
	private static final long serialVersionUID = -6282989041333609912L;
	
	public static final String SERIALPORTPATH_TTYSAC1 = "/dev/ttySAC1";
	public static final String SERIALPORTPATH_TTYSAC2 = "/dev/ttySAC2";
	//public static final String SERIALPORTPATH_TTYSAC3 = "/dev/ttySAC3";
	public static final String SERIALPORTPATH_GPIO = "/dev/GPM1";
	//public static final int SERIALPORTSPEED_B9600 = 9600;
	public static final int SERIALPORTSPEED_B115200 = 115200;
	//public static final int SERIALPORTSPEED_B19200 = 19200;
	
	public static final Parcelable.Creator<AppContext> CREATOR = new Parcelable.Creator<AppContext>() {
		public AppContext createFromParcel(Parcel in) {
			if (null == ins) {
				ins = new AppContext();
			}
			ins.phoneNo = in.readString();
			ins.account = in.readString();
			ins.pwd = in.readString();
			ins.imei = in.readString();
			ins.agent_id = in.readString();
			ins.helpTxt = in.readString();
			ins.width = in.readInt();
			ins.height = in.readInt();
			return ins;
		}

		public AppContext[] newArray(int size) {
			return new AppContext[size];
		}
	};

	public static transient CommonActivity current;

	private static AppContext ins;
	
	public static final String PREFER = "prefer";

	public static synchronized AppContext getIns() {
		if (null == ins) {
			ins = new AppContext();
		}
		return ins;
	}

	public static synchronized void setIns(AppContext appIns) {
		ins = appIns;
	}

	public String account;

	public String agent_id = "S1000144001";

	public int density;

	public int height;

	public String helpTxt;

	public String imei;

	public String model;

	public String phoneNo;

	public String pwd;

	public int width;
	
	private AppContext() {
		ins = this;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(phoneNo);
		dest.writeString(account);
		dest.writeString(pwd);
		dest.writeString(imei);
		dest.writeString(agent_id);
		dest.writeString(helpTxt);
		dest.writeInt(width);
		dest.writeInt(height);
	}
	
	private static SerialportJNI jni;
	
	public static synchronized SerialportJNI getJni() {
		if (null == jni) {
			jni = new SerialportJNI();
		}
		return jni;
	}

}