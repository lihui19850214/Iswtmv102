package com.t_epc.reader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android_serialport_api.SerialPort;

public class ReadAndWrite implements IReadAndWrite {

	private SerialPort serialPort;
	private InputStream is;
	private OutputStream os;
	
	public static ReadAndWrite raw;
	/**
	 * 单例模式
	 *
	 * @return
	 * @throws SecurityException
	 * @throws IOException
	 */
	public static ReadAndWrite getInstance() throws SecurityException,
			IOException {
		if (raw == null) {
			raw = new ReadAndWrite();
		}
		return raw;
	}

	/**
	 * 私有化构造方法
	 * 
	 * @throws SecurityException
	 * @throws IOException
	 */
	private ReadAndWrite() throws SecurityException, IOException {
		this.serialPort = new SerialPort(new File("/dev/ttySAC2"), 115200, 0);
		this.is = this.serialPort.getInputStream();
		this.os = this.serialPort.getOutputStream();
	}

}
