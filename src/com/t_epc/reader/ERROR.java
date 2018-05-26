package com.t_epc.reader;

public class ERROR {
	public final static byte SUCCESS = 0x10;
	public final static byte FAIL = 0x11;
	public final static byte MCU_RESET_ERROR = 0x20;
	public final static byte CW_ON_ERROR = 0x21;
	public final static byte ANTENNA_MISSING_ERROR = 0x22;
	public final static byte WRITE_FLASH_ERROR = 0x23;
	public final static byte READ_FLASH_ERROR = 0x24;
	public final static byte SET_OUTPUT_POWER_ERROR = 0x25;
	public final static byte TAG_INVENTORY_ERROR = 0x31;
	public final static byte TAG_READ_ERROR = 0x32;
	public final static byte TAG_WRITE_ERROR = 0x33;
	public final static byte TAG_LOCK_ERROR = 0x34;
	public final static byte TAG_KILL_ERROR = 0x35;
	public final static byte NO_TAG_ERROR = 0x36;
	public final static byte INVENTORY_OK_BUT_ACCESS_FAIL = 0x37;
	public final static byte BUFFER_IS_EMPTY_ERROR = 0x38;
	public final static byte ACCESS_OR_PASSWORD_ERROR = 0x40;
	public final static byte PARAMETER_INVALID = 0x41;
	public final static byte PARAMETER_INVALID_WORDCNT_TOO_LONG = 0x42;
	public final static byte PARAMETER_INVALID_MEMBANK_OUT_OF_RANGE = 0x43;
	public final static byte PARAMETER_INVALID_LOCK_REGION_OUT_OF_RANGE = 0x44;
	public final static byte PARAMETER_INVALID_LOCK_ACTION_OUT_OF_RANGE = 0x45;
	public final static byte PARAMETER_READER_ADDRESS_INVALID = 0x46;
	public final static byte PARAMETER_INVALID_ANTENNA_ID_OUT_OF_RANGE = 0x47;
	public final static byte PARAMETER_INVALID_OUTPUT_POWER_OUT_OF_RANGE = 0x48;
	public final static byte PARAMETER_INVALID_FREQUENCY_REGION_OUT_OF_RANGE = 0x49;
	public final static byte PARAMETER_INVALID_BAUDRATE_OUT_OF_RANGE = 0x4A;
	public final static byte PARAMETER_BEEPER_MODE_OUT_OF_RANGE = 0x4B;
	public final static byte PARAMETER_EPC_MATCH_LEN_TOO_LONG = 0x4C;
	public final static byte PARAMETER_EPC_MATCH_LEN_ERROR = 0x4D;
	public final static byte PARAMETER_INVALID_EPC_MATCH_MODE = 0x4E;
	public final static byte PARAMETER_INVALID_FREQUENCY_RANGE = 0x4F;
	public final static byte FAIL_TO_GET_RN16_FROM_TAG = 0x50;
	public final static byte PARAMETER_INVALID_DRM_MODE = 0x51;
	public final static byte PLL_LOCK_FAIL = 0x52;
	public final static byte RF_CHIP_FAIL_TO_RESPONSE = 0x53;
	public final static byte FAIL_TO_ACHIEVE_DESIRED_OUTPUT_POWER = 0x54;
	public final static byte COPYRIGHT_AUTHENTICATION_FAIL = 0x55;
	public final static byte SPECTRUM_REGULATION_ERROR = 0x56;
	public final static byte OUTPUT_POWER_TOO_LOW = 0x57;

	public static String format(byte btErrorCode)
    {
		String strErrorCode = "";
        switch (btErrorCode)
        {
            case SUCCESS:
                strErrorCode = "The command completed successfully";//命令成功完成
                break;
            case FAIL:
                strErrorCode = "Failed to perform the command";//命令执行失败
                break;
            case MCU_RESET_ERROR:
                strErrorCode = "CPU reset error";//CPU 复位错误
                break;
            case CW_ON_ERROR:
                strErrorCode = "Open the CW error";//打开CW 错误
                break;
            case ANTENNA_MISSING_ERROR:
                strErrorCode = "Antenna is not connected";//天线未连接
                break;
            case WRITE_FLASH_ERROR:
                strErrorCode = "Write the Flash error";//写Flash 错误
                break;
            case READ_FLASH_ERROR:
                strErrorCode = "Read the Flash error";//读Flash 错误
                break;
            case SET_OUTPUT_POWER_ERROR:
                strErrorCode = "Set the transmission power error";//设置发射功率错误
                break;
            case TAG_INVENTORY_ERROR:
                strErrorCode = "Inventory tag errors";//盘存标签错误
                break;
            case TAG_READ_ERROR:
                strErrorCode = "Error reading labels";//读标签错误
                break;
            case TAG_WRITE_ERROR:
                strErrorCode = "Write tag errors";//写标签错误
                break;
            case TAG_LOCK_ERROR:
                strErrorCode = "Lock label error";//锁定标签错误
                break;
            case TAG_KILL_ERROR:
                strErrorCode = "Inactivated label mistake";//灭活标签错误
                break;
            case NO_TAG_ERROR:
                strErrorCode = "An operating error label";//无可操作标签错误
                break;
            case INVENTORY_OK_BUT_ACCESS_FAIL:
                strErrorCode = "Successful inventory but the visit to fail";//成功盘存但访问失败
                break;
            case BUFFER_IS_EMPTY_ERROR:
                strErrorCode = "The cache is empty";//缓存为空
                break;
            case ACCESS_OR_PASSWORD_ERROR:
                strErrorCode = "Access to the labels or access password errors";//访问标签错误或访问密码错误
                break;
            case PARAMETER_INVALID:
                strErrorCode = "Invalid parameter";//无效的参数
                break;
            case PARAMETER_INVALID_WORDCNT_TOO_LONG:
                strErrorCode = "WordCnt parameters exceed the specified length";//wordCnt 参数超过规定长度
                break;
            case PARAMETER_INVALID_MEMBANK_OUT_OF_RANGE:
                strErrorCode = "Beyond the scope of MemBank parameters";//MemBank 参数超出范围
                break;
            case PARAMETER_INVALID_LOCK_REGION_OUT_OF_RANGE:
                strErrorCode = "Beyond the scope of the Lock data area parameters";//Lock 数据区参数超出范围
                break;
            case PARAMETER_INVALID_LOCK_ACTION_OUT_OF_RANGE:
                strErrorCode = "Beyond the scope of LockType parameters";//LockType 参数超出范围
                break;
            case PARAMETER_READER_ADDRESS_INVALID:
                strErrorCode = "Read/write device address is invalid";//读写器地址无效
                break;
            case PARAMETER_INVALID_ANTENNA_ID_OUT_OF_RANGE:
                strErrorCode = "Antenna_id beyond range";//Antenna_id 超出范围
                break;
            case PARAMETER_INVALID_OUTPUT_POWER_OUT_OF_RANGE:
                strErrorCode = "Beyond the range of output parameters";//输出功率参数超出范围
                break;
            case PARAMETER_INVALID_FREQUENCY_REGION_OUT_OF_RANGE:
                strErrorCode = "Beyond the scope of rf specifications area parameters";//射频规范区域参数超出范围
                break;
            case PARAMETER_INVALID_BAUDRATE_OUT_OF_RANGE:
                strErrorCode = "Beyond the scope of baud rate parameters";//波特率参数超出范围
                break;
            case PARAMETER_BEEPER_MODE_OUT_OF_RANGE:
                strErrorCode = "Beyond the scope of buzzer to set parameters";//蜂鸣器设置参数超出范围
                break;
            case PARAMETER_EPC_MATCH_LEN_TOO_LONG:
                strErrorCode = "EPC matching length of crossing the line";//EPC 匹配长度越界
                break;
            case PARAMETER_EPC_MATCH_LEN_ERROR:
                strErrorCode = "EPC matching length error";//EPC 匹配长度错误
                break;
            case PARAMETER_INVALID_EPC_MATCH_MODE:
                strErrorCode = "Beyond the scope of EPC matching parameters";//EPC 匹配参数超出范围
                break;
            case PARAMETER_INVALID_FREQUENCY_RANGE:
                strErrorCode = "Frequency range set parameters errors";//频率范围设置参数错误
                break;
            case FAIL_TO_GET_RN16_FROM_TAG:
                strErrorCode = "Unable to receive the label RN16";//无法接收标签的RN16
                break;
            case PARAMETER_INVALID_DRM_MODE:
                strErrorCode = "DRM setting parameter error";//DRM 设置参数错误
                break;
            case PLL_LOCK_FAIL:
                strErrorCode = "PLL could not be locked";//PLL 不能锁定
                break;
            case RF_CHIP_FAIL_TO_RESPONSE:
                strErrorCode = "The rfid chip no response";//射频芯片无响应
                break;
            case FAIL_TO_ACHIEVE_DESIRED_OUTPUT_POWER:
                strErrorCode = "The output can not meet the specified output power";//输出达不到指定的输出功率
                break;
            case COPYRIGHT_AUTHENTICATION_FAIL:
                strErrorCode = "Copyright authentication failed";//版权认证未通过
                break;
            case SPECTRUM_REGULATION_ERROR:
                strErrorCode = "Spectrum incorrect specification";//频谱规范设置错误
                break;
            case OUTPUT_POWER_TOO_LOW:
                strErrorCode = "The output power is too low";//输出功率过低
                break;
            default:
            	strErrorCode = "An unknown error";//未知错误
                break;
        }
        return strErrorCode;
    }
}
