package com.t_epc.utils;

import java.io.FileOutputStream;

public class M10_GPIO {
	
	public static void _14443A_PowerOn()
	{
		writeFile("/sys/bus/platform/devices/odroid-sysfs/ic14443a_enable", "1");
		writeFile("/sys/bus/platform/devices/odroid-sysfs/ic14443a_serial_switch", "0");
	}
	
	public static void _14443A_PowerOFF()
	{
		writeFile("/sys/bus/platform/devices/odroid-sysfs/ic14443a_enable", "0");
		writeFile("/sys/bus/platform/devices/odroid-sysfs/ic14443a_serial_switch", "0");
	}
	
	public static void Infrared_PowerOn()
	{
		writeFile("/syss/platform/devices/odroid-sysfs/infrared_enable_switch", "0");
	}
	
	public static void Infrared_PowerOFF()
	{
		writeFile("/syss/platform/devices/odroid-sysfs/rfid_serial_switch", "0");
	}
	
	public static void R1000_PowerOn()
	{
		writeFile("/sys/bus/platform/devices/odroid-sysfs/r1000_enable", "1");
		writeFile("/sys/bus/platform/devices/odroid-sysfs/r1000_serial_switch", "1");
	}
	
	public static void R1000_PowerOFF()
	{
		writeFile("/sys/bus/platform/devices/odroid-sysfs/r1000_enable", "0");
		writeFile("/sys/bus/platform/devices/odroid-sysfs/r1000_serial_switch", "0");
	}
	
	public static void W433_PowerOn()
	{
		writeFile("/sys/bus/platform/devices/odroid-sysfs/rfid_enable", "1");
		writeFile("/sys/ bus/platform/devices/odroid-sysfs/rfid_serial_switch", "0");
	}
	
	public static void W433_PowerOff()
	{
		writeFile("/sys/bus/platform/devices/odroid-sysfs/rfid_enable", "0");
		writeFile("/sys/ bus/platform/devices/odroid-sysfs/rfid_serial_switch", "0");
	}
	
	//写数据
	private static void writeFile(String fileName,String writestr)
	{
	  try{
	        FileOutputStream fout = new FileOutputStream(fileName);
	        byte [] bytes = writestr.getBytes();
	        fout.write(bytes);
	        fout.close();
	    }
	  	catch(Throwable e){
			System.out.println("M10_GPIO.java--"+e.toString());
	  	}
	}
}
