package com.serialport;

public class SerialportJNI {
	
    //HF(rc663)
    public native int Rc663_HFConnect(String portName, int baudrate);
    public native int Rc663_HFDisconnect();
    public native int Rc663_HFInit(int protocol);
    public native int Rc663_HFGetUid(byte[] uReadData, byte[] uDataLen);
    
    static {
		System.loadLibrary("rc663");
    }
	
  //RFID
  	public native int WIrUHFConnect(String portName, int baudrate);        								//创建连接
  	
  	public native int WIrUHFDisconnect();						   		   								//断开连接
  	
  	public native int WIrUHFReadWriteTestDemo(String portName, int baudrate, byte[] uReadData);  		//接口测试函数
      
  	public native int WIrUHFInventoryOnce(byte[] nTagCount, byte[] uReadData);
      
  	public native int WIrUHFSetInventoryMode(byte uMode);
      
  	public native int WIrUHFGetInventoryMode(byte[] uMode);
      
  	public native int WIrUHFGetPower(byte[] uPower);
      
  	public native int WIrUHFSetPower(byte uPower);
      
  	public native int WIrUHFGetQ(byte[] nQ);
      
  	public native int WIrUHFSetQ(byte nQ);
      
  	public native int WIrGetFrequency(byte[] dwFreqIndex);
      
  	public native int WIrSetFrequency(byte dwFreqIndex);
      
  	public native int WIrUHFReadData(byte uBank, byte uOff, byte uLen, byte[] nTagCount, byte[] uReadData);
      
  	public native int WIrUHFReadDataByUID(byte uBank, byte uOff, byte uLen, byte[] uUii, byte uUiiLen, byte[] uReadData);
      
  	public native int WIrUHFWriteData(byte uBank, byte uOff, byte uLen, byte[] uWriteData);
      
  	public native int WIrUHFWriteDataByUID(byte uBank, byte uOff, byte uLen, byte[] uUii, byte uUiiLen, byte[] uWriteData);
  	
  	public native int WIrUHFKillTag(byte[] password, int len, byte[] uUii);
  	
      public native int WIrUHFLockTag(int uBank, int uAction, byte[] password, int len, byte[] uUii);
      
      static {
  		System.loadLibrary("r1000");
      }
}
