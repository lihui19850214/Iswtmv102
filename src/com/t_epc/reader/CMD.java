package com.t_epc.reader;

public class CMD {
	public final static byte RESET = 0x70;
	public final static byte SET_UART_BAUDRATE = 0x71;
	public final static byte GET_FIRMWARE_VERSION = 0x72;
	public final static byte SET_READER_ADDRESS = 0x73;
	public final static byte SET_WORK_ANTENNA = 0x74;
	public final static byte GET_WORK_ANTENNA = 0x75;
	public final static byte SET_OUTPUT_POWER = 0x76;
	public final static byte GET_OUTPUT_POWER = 0x77;
	public final static byte SET_FREQUENCY_REGION = 0x78;
	public final static byte GET_FREQUENCY_REGION = 0x79;
	public final static byte SET_BEEPER_MODE = 0x7A;
	public final static byte GET_READER_TEMPERATURE = 0x7B;
	public final static byte READ_GPIO_VALUE = 0x60;
	public final static byte WRITE_GPIO_VALUE = 0x61;
	public final static byte SET_ANT_CONNECTION_DETECTOR = 0x62;
	public final static byte GET_ANT_CONNECTION_DETECTOR = 0x63;
	public final static byte SET_TEMPORARY_OUTPUT_POWER = 0x66;
	public final static byte SET_READER_IDENTIFIER = 0x67;
	public final static byte GET_READER_IDENTIFIER = 0x68;
	public final static byte SET_RF_LINK_PROFILE = 0x69;
	public final static byte GET_RF_LINK_PROFILE = 0x6A;
	public final static byte GET_RF_PORT_RETURN_LOSS = 0x7E;
	public final static byte INVENTORY = (byte) 0x80;
	public final static byte READ_TAG = (byte) 0x81;
	public final static byte WRITE_TAG = (byte) 0x82;
	public final static byte LOCK_TAG = (byte) 0x83;
	public final static byte KILL_TAG = (byte) 0x84;
	public final static byte SET_ACCESS_EPC_MATCH = (byte) 0x85;
	public final static byte GET_ACCESS_EPC_MATCH = (byte) 0x86;
	public final static byte REAL_TIME_INVENTORY = (byte) 0x89;
	public final static byte FAST_SWITCH_ANT_INVENTORY = (byte) 0x8A;
	public final static byte CUSTOMIZED_SESSION_TARGET_INVENTORY = (byte) 0x8B;
	public final static byte SET_IMPINJ_FAST_TID = (byte) 0x8C;
	public final static byte SET_AND_SAVE_IMPINJ_FAST_TID = (byte) 0x8D;
	public final static byte GET_IMPINJ_FAST_TID = (byte) 0x8E;
	public final static byte ISO18000_6B_INVENTORY = (byte) 0xB0;
	public final static byte ISO18000_6B_READ_TAG = (byte) 0xB1;
	public final static byte ISO18000_6B_WRITE_TAG = (byte) 0xB2;
	public final static byte ISO18000_6B_LOCK_TAG = (byte) 0xB3;
	public final static byte ISO18000_6B_QUERY_LOCK_TAG = (byte) 0xB4;
	public final static byte GET_INVENTORY_BUFFER = (byte) 0x90;
	public final static byte GET_AND_RESET_INVENTORY_BUFFER = (byte) 0x91;
	public final static byte GET_INVENTORY_BUFFER_TAG_COUNT = (byte) 0x92;
	public final static byte RESET_INVENTORY_BUFFER = (byte) 0x93;
	
	public static String format(byte btCmd)
    {
		String strCmd = "";
        switch (btCmd)
        {
            case RESET:
            	strCmd = "Device reset, speaking, reading and writing";//复位读写器
                break;
            case SET_UART_BAUDRATE:
            	strCmd = "Set the serial communication baud rate";//设置串口通讯波特率
                break;
            case GET_FIRMWARE_VERSION:
                strCmd = "Reading, speaking, reading and writing device firmware version";//读取读写器固件版本
                break;
            case SET_READER_ADDRESS:
                strCmd = "Set to read and write device address";//设置读写器地址
                break;
            case SET_WORK_ANTENNA:
                strCmd = "Set to read and write device working antenna";//设置读写器工作天线
                break;
            case GET_WORK_ANTENNA:
                strCmd = "Query the current antenna work";//查询当前天线工作天线
                break;
            case SET_OUTPUT_POWER:
                strCmd = "Set to read and write, radio frequency (rf) power output";//设置读写器射频输出功率
                break;
            case GET_OUTPUT_POWER:
                strCmd = "Query, speaking, reading and writing, the current output power";//查询读写器当前输出功率
                break;
            case SET_FREQUENCY_REGION:
                strCmd = "Set the working frequency range of the read and write";//设置读写器工作频率范围
                break;
            case GET_FREQUENCY_REGION:
                strCmd = "Working frequency range of the query, speaking, reading and writing";//查询读写器工作频率范围
                break;
            case SET_BEEPER_MODE:
                strCmd = "Set the buzzer";//设置蜂鸣器状态
                break;
            case GET_READER_TEMPERATURE:
                strCmd = "Query the current working temperature of the equipment";//查询当前设备的工作温度
                break;
            case READ_GPIO_VALUE:
                strCmd = "Read the GPIO level";//读取GPIO电平
                break;
            case WRITE_GPIO_VALUE:
                strCmd = "Set the GPIO level";//设置GPIO电平
                break;
            case SET_ANT_CONNECTION_DETECTOR:
                strCmd = "Set the antenna connection state detector";//设置天线连接检测器状态
                break;
            case GET_ANT_CONNECTION_DETECTOR:
                strCmd = "Read the antenna connection state detector";//读取天线连接检测器状态
                break;
            case SET_TEMPORARY_OUTPUT_POWER:
                strCmd = "Set to read and write, temporary rf power output";//设置读写器临时射频输出功率
                break;
            case SET_READER_IDENTIFIER:
                strCmd = "Set to read and write device identification number";//设置读写器识别码
                break;
            case GET_READER_IDENTIFIER:
                strCmd = "Reading, speaking, reading and writing device identification number";//读取读写器识别码
                break;
            case SET_RF_LINK_PROFILE:
                strCmd = "Set the communication rate of radio frequency link";//设置射频链路的通讯速率
                break;
            case GET_RF_LINK_PROFILE:
                strCmd = "Read the communication rate of radio frequency link";//读取射频链路的通讯速率
                break;
            case GET_RF_PORT_RETURN_LOSS:
                strCmd = "Measuring return loss of the antenna port";//测量天线端口的回波损耗
                break;
            case INVENTORY:
                strCmd = "Inventory Tag";//盘存标签
                break;
            case READ_TAG:
                strCmd = "Read Tag";//读标签
                break;
            case WRITE_TAG:
                strCmd = "Write Tag";//写标签
                break;
            case LOCK_TAG:
                strCmd = "Lock Tag";//锁定标签
                break;
            case KILL_TAG:
                strCmd = "Inactivated label";//灭活标签
                break;
            case SET_ACCESS_EPC_MATCH:
                strCmd = "Match the ACCESS operation of EPC number";//匹配ACCESS操作的EPC号
                break;
            case GET_ACCESS_EPC_MATCH:
                strCmd = "Query matching state of EPC";//查询匹配的EPC状态
                break;
            case REAL_TIME_INVENTORY:
                strCmd = "Inventory tags (real-time upload data)";//盘存标签(实时上传标签数据)
                break;
            case FAST_SWITCH_ANT_INVENTORY:
                strCmd = "Rapid polling multiple antenna inventory tags";//快速轮询多个天线盘存标签
                break;
            case CUSTOMIZED_SESSION_TARGET_INVENTORY:
                strCmd = "A custom session and target inventory";//自定义session和target盘存
                break;
            case SET_IMPINJ_FAST_TID:
                strCmd = "Set the Monza label quickly read dar (save)";//设置Monza标签快速读TID(不保存)
                break;
            case SET_AND_SAVE_IMPINJ_FAST_TID:
                strCmd = "Set the Monza tags fast reading TID (save)";//设置Monza标签快速读TID(保存)
                break;
            case GET_IMPINJ_FAST_TID:
                strCmd = "Query the current rapid TID set";//查询当前的快速TID设置
                break;
            case ISO18000_6B_INVENTORY:
                strCmd = "Inventory 18000-6B Tag";
                break;
            case ISO18000_6B_READ_TAG:
                strCmd = "Read 18000-6B Tag";
                break;
            case ISO18000_6B_WRITE_TAG:
                strCmd = "Write 18000-6B Tag";
                break;
            case ISO18000_6B_LOCK_TAG:
                strCmd = "Lock 18000-6B Tag";
                break;
            case ISO18000_6B_QUERY_LOCK_TAG:
                strCmd = "Search 18000-6B Tag";
                break;
            case GET_INVENTORY_BUFFER:
                strCmd = "Extract tag data retention cache backup";//提取标签数据保留缓存备份
                break;
            case GET_AND_RESET_INVENTORY_BUFFER:
                strCmd = "Extract the data and delete cache tags";//提取标签数据并删除缓存
                break;
            case GET_INVENTORY_BUFFER_TAG_COUNT:
                strCmd = "In the query cache read tag number";//查询缓存中已读标签个数
                break;
            case RESET_INVENTORY_BUFFER:
                strCmd = "Empty tag data cache";//清空标签数据缓存
                break;
            default:
            	strCmd = "Position operation";//未知操作
                break;
        }
        return strCmd;
    }
}
