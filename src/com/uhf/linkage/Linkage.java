package com.uhf.linkage;

import com.uhf.structures.AlarmDout;
import com.uhf.structures.AlarmParams;
import com.uhf.structures.AntennaPortParams;
import com.uhf.structures.DynamicQParams;
import com.uhf.structures.FixedQParameters;
import com.uhf.structures.HeartBeat;
import com.uhf.structures.HttpReportFormat;
import com.uhf.structures.InputTriggerAlarm;
import com.uhf.structures.InventoryArea;
import com.uhf.structures.InventoryData;
import com.uhf.structures.InventoryFilterThreshold;
import com.uhf.structures.InventoryParams;
import com.uhf.structures.NetInfo;
import com.uhf.structures.OnInventoryListener;
import com.uhf.structures.OnRwListener;
import com.uhf.structures.PostSingulationMatchCriteria;
import com.uhf.structures.ProtschTxtime;
import com.uhf.structures.QueryTagGroup;
import com.uhf.structures.RfidValue;
import com.uhf.structures.Rm8011Query;
import com.uhf.structures.RwData;
import com.uhf.structures.SelectCriteria;
import com.uhf.structures.SerialNumber;
import com.uhf.structures.SoftVersion;
import com.uhf.structures.WifiInfo;

public class Linkage {
	private OnInventoryListener onInventoryListener;
	private OnRwListener onRwListener;
	private static String path;

	public Linkage(String strLibPath) {
		System.load(strLibPath + "\\uhf.dll");
		System.load(strLibPath + "\\uhfJni.dll");
	}

	public Linkage() {
//		System.load("D:\\RFID\\dll\\dcrf32.dll");
		System.load("C:\\Users\\86184\\Desktop\\zhang\\RFID-war包\\RFID\\JavaUhfDemo0\\config\\dll\\x64\\mfc120u.dll");
		System.load("C:\\Users\\86184\\Desktop\\zhang\\RFID-war包\\RFID\\JavaUhfDemo0\\config\\dll\\x64\\msvcp120.dll");
		System.load("C:\\Users\\86184\\Desktop\\zhang\\RFID-war包\\RFID\\JavaUhfDemo0\\config\\dll\\x64\\msvcr120.dll");
		System.load("C:\\Users\\86184\\Desktop\\zhang\\RFID-war包\\RFID\\JavaUhfDemo0\\config\\dll\\x64\\mul_uhf.dll");
		System.load("C:\\Users\\86184\\Desktop\\zhang\\RFID-war包\\RFID\\JavaUhfDemo0\\config\\dll\\x64\\mul_uhfJni.dll");
//		System.load("D:\\RFID\\dll\\BHDll.dll");
		System.load("C:\\Users\\86184\\Desktop\\zhang\\RFID-war包\\RFID\\JavaUhfDemo0\\config\\dll\\x64\\uhf.dll");
		System.load("C:\\Users\\86184\\Desktop\\zhang\\RFID-war包\\RFID\\JavaUhfDemo0\\config\\dll\\x64\\uhfJni.dll");
//		System.loadLibrary("uhf");
//		System.loadLibrary("uhfJni");
	}

	// 实现单例模式,外部直接可以用instance调用
	private static Linkage instance = null;

	public static Linkage getInstance(String strLibPath) {
		if (instance == null) {
			instance = new Linkage(strLibPath);
		}
		return instance;
	}
	
	public static Linkage getInstance() {
		if (instance == null) {
			instance = new Linkage();
		}
		return instance;
	}

	public void setOnInventoryListener(OnInventoryListener onInventoryListener) {
		this.onInventoryListener = onInventoryListener;
	}

	public void setOnRwListener(OnRwListener onRwListener) {
		this.onRwListener = onRwListener;
	}

	/* libaray functions */
	// public native int initRFID(RFID_CALLBACK_FUNC pFunc, RFID_RW_CFG_CALLBACK
	// pRWCfgFunc);
	// 初始化系统参数,uhf回调通过Listener传回
	public native int initRFID();

	public native int deinitRFID();

	public native int setRFModuleType(int moduleType);

	public native int setRFConnectMode(int flag);

	/*
	 * public native int powerOnRFModule(void); public native int
	 * powerOffRFModule(void); public native int pushRFData(char *rdBuf, int
	 * rdLen); public native int parseRFData(void);
	 */

	public native int openCom(String port, long baud);

	public native int closeCom();

	public native int connectRemoteNetwork(String remoteAddr, int remotePort);

	public native int listenRemoteNetwork(int listenPort);

	public native int closeNetwork();

	/*
	 * RF common and don't depend on actual RF moudule(R2000 AND RM8011) RM70xx
	 * is virtual RF module
	 */
	public native int setInventoryArea(InventoryArea inventoryArea);

	public native int getInventoryArea(InventoryArea inventoryArea);

	public native int setInventoryFilterThreshold(
			InventoryFilterThreshold inventoryFilerThreshold);

	public native int getInventoryFilterThreshold(
			InventoryFilterThreshold inventoryFilerThreshold);

	public native int resetInventoryFilter();

	public native int setAlarmParams(AlarmParams alarmParams);

	public native int getAlarmParams(AlarmParams alarmParams);

	public native int setAlarmStatus(int status);

	public native int getAlarmStatus(RfidValue rfidValue);

	/* RF common functions */
	public native int getModuleSerialNumber(SerialNumber serialNumber);

	public native int getModuleSoftVersion(SoftVersion softVersion);

	public native int startInventory(int mode, int maskFlag);

	public native int stopInventory();

	public native int getInventoryStatus();

	public native int setPowerLevel(int antennaPort,
			AntennaPortParams antennaPortParams);

	public native int getPowerLevel(int antennaPort,
			AntennaPortParams antennaPortParams);

	public native int readTag(byte[] accessPassword, int memBank,
			int startAddr, int wordLen);
	
	public native int readTagSync(byte[] accessPassword, int memBank,
			int startAddr, int wordLen, int timeOutMs, RwData rwData);

	public native int writeTag(byte[] accessPassword, int memBank,
			int startAddr, int wordLen, byte[] pWriteData);
	
	public native int writeTagSync(byte[] accessPassword, int memBank,
			int startAddr, int wordLen, byte[] pWriteData, int timeOutMs, 
			RwData rwData);
	
	public native int lockTag(int killPasswordPermissions,
			int accessPasswordPermissions, int epcMemoryBankPermissions,
			int tidMemoryBankPermissions, int userMemoryBankPermissions,
			byte[] accessPassword);
	
	public native int lockTagSync(int killPasswordPermissions,
			int accessPasswordPermissions, int epcMemoryBankPermissions,
			int tidMemoryBankPermissions, int userMemoryBankPermissions,
			byte[] accessPassword, int timeOutMs, 
			RwData rwData);
	
	public native int killTag(byte[] accessPassword, byte[] killPassword);

	public native int killTagSync(byte[] accessPassword, byte[] killPassword,
			                      int timeOutMs, RwData rwData);

	public native int setRegion(int range);

	public native int getRegion(RfidValue rfidValue);

	public native int setFixFreq(int freq);

	public native int getFixFreq(RfidValue rfidValue);

	public native int set18K6CSelectCriteria(int selectorIdx, SelectCriteria selectCriteria);

	public native int get18K6CSelectCriteria(int selectorIdx, SelectCriteria selectCriteria);

	public native int getAntennaPortNum(RfidValue rfidValue);

	public native int setPostSingulationMatchCriteria(
			PostSingulationMatchCriteria params);

	public native int getPostSingulationMatchCriteria(
			PostSingulationMatchCriteria params);

	/* For R2000 module and RM70xx */
	public native int getAntennaSWR(int antennaPort, RfidValue rfidValue);

	public native int setCurrentProfile(int profile);

	public native int getCurrentProfile(RfidValue rfidValue);

	public native int setAntennaPortState(int antennaPort, int status);

	public native int getAntennaPortState(int antennaPort, RfidValue rfidValue);

	public native int setCurrentSingulationAlgorithm(int algorithm);

	public native int getCurrentSingulationAlgorithm(RfidValue rfidValue);

	public native int setSingulationFixedQParameters(
			FixedQParameters fixedQParameters);

	public native int getSingulationFixedQParameters(
			FixedQParameters fixedQParameters);

	public native int setSingulationDynamicQParameters(
			DynamicQParams dynamicQParams);

	public native int getSingulationDynamicQParameters(
			DynamicQParams dynamicQParams);

	public native int set18K6CQueryTagGroup(QueryTagGroup queryTagGroup);

	public native int get18K6CQueryTagGroup(QueryTagGroup queryTagGroup);

	public native int setProtschTxtime(ProtschTxtime protschTxtime);

	public native int getProtschTxtime(ProtschTxtime protschTxtime);

	/*
	 * public native int prepareModuleUpdate(int *version, u16 *versionLen, u16
	 * *maxPacketLen, u16 *chunkSize, u32 firmwareLen); public native int
	 * updateModuleFirmware(int *data, u16 dataLen);
	 */

	/* For multiple Channel module and RM70xx */
	public native int setAntennaPort(int antennaPort,
			AntennaPortParams antennaPortParams);

	public native int getAntennaPort(int antennaPort,
			AntennaPortParams antennaPortParams);

	/* For RM8011 module and RM70xx */
	public native int setWorkMode(int mode);

	public native int setQuery(Rm8011Query rm8011Query);

	public native int getQuery(Rm8011Query rm8011Query);

	/* Only For RM70xx */
	// public native int boardFirmwareUpdate(int curIdx, int maxIdx, int
	// dataLen, byte[] data);

	public native int boardReboot();

	public native int getBoardSerialNumber(SerialNumber serialNumber);

	public native int getBoardSoftVersion(SoftVersion softVersion);

	public native int setBoardModuleType(int moduleType);

	public native int getBoardModuleType(RfidValue rfidValue);

	public native int setInventoryParams(InventoryParams inventoryParams);

	public native int getInventoryParams(InventoryParams inventoryParams);

	public native int setHeartBeat(HeartBeat heartBeat);

	public native int getHeartBeat(HeartBeat heartBeat);

	public native int setNetInfo(NetInfo netInfo);

	public native int getNetInfo(NetInfo netInfo);

	public native int setWifiInfo(WifiInfo wifiInfo);

	public native int getWifiInfo(WifiInfo wifiInfo);

	public native int setDOutStatus(int port, int status);

	public native int getDOutStatus(int port, RfidValue rfidValue);

	public native int getDInStatus(int port, RfidValue rfidValue);

	public native int setAlarmDout(int port, int status);

	public native int getAlarmDout(AlarmDout alarmDout);

	public native int setInputTriggerAlarm(InputTriggerAlarm inputTriggerAlarm);

	public native int getInputTriggerAlarm(InputTriggerAlarm inputTriggerAlarm);

	public native int setDoutInspectMask(int mask);

	public native int getDoutInspectMask(RfidValue rfidValue);
	
	public native int setHttpReportFormat(HttpReportFormat httpReportFormat);
	
	public native int getHttpReportFormat(HttpReportFormat httpReportFormat);
	/**
	 * RFID回调函数接口,目前处理盘点返回信息
	 */
	public void inventoryCallBack(InventoryData inventoryData) {
		if (inventoryData != null && onInventoryListener != null)
			onInventoryListener.getInventoryData(inventoryData);
	}

	/**
	 * RFID回调函数接口,处理读写返回信息
	 */
	public void rwCallBack(RwData rwData) {
		System.out.println("rwCallBack");
		if (rwData != null && onRwListener != null)
			onRwListener.getRwData(rwData);
	}

	public enum RFID_18K6C_COUNTRY_REGION {
		China840_845(0), China920_925(1), Open_Area902_928(2), user_Area(3);
		private int result;

		private RFID_18K6C_COUNTRY_REGION(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum RFID_18K6C_COUNTRY_REGION_QILIAN {
		China840_845(4), China920_925(1), US902_928(2), Europe865_868(3), Korea917_923(
				6);

		private int result;

		private RFID_18K6C_COUNTRY_REGION_QILIAN(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum RFID_18K6C_TAG_PWD_PERM {
		ACCESSIBLE(0), ALWAYS_ACCESSIBLE(1), SECURED_ACCESSIBLE(2), ALWAYS_NOT_ACCESSIBLE(
				3), NO_CHANGE(4);
		private int result;

		private RFID_18K6C_TAG_PWD_PERM(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

	public enum RFID_18K6C_TAG_MEM_PERM {
		WRITEABLE(0), ALWAYS_WRITEABLE(1), SECURED_WRITEABLE(2), ALWAYS_NOT_WRITEABLE(
				3), NO_CHANGE(4);
		private int result;

		private RFID_18K6C_TAG_MEM_PERM(int result) {
			this.result = result;
		}

		public int getValue() {
			return result;
		}
	}

}
