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

public class MulLinkage {
	private OnInventoryListener onInventoryListener;
	private OnRwListener onRwListener;
	private static String path;

	public MulLinkage(String strLibPath) {
		System.load(strLibPath + "\\mul_uhf.dll");
		System.load(strLibPath + "\\mul_uhfJni.dll");
	}

	public MulLinkage() {
		System.loadLibrary("C://Windows//System32//mul_uhf.dll");
		System.loadLibrary("C://Windows//System32//mul_uhfJni.dll");
	}

	// 实现单例模式,外部直接可以用instance调用
	private static MulLinkage instance = null;

	public static MulLinkage getInstance(String strLibPath) {
		if (instance == null) {
			instance = new MulLinkage(strLibPath);
		}
		return instance;
	}
	
	public static MulLinkage getInstance() {
		if (instance == null) {
			instance = new MulLinkage();
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
	// 初始化系统参数,uhf回调通过Listener传回
	// public native int initRFID(RFID_CALLBACK_FUNC pFunc, RFID_RW_CFG_CALLBACK
	// pRWCfgFunc);
	
	public native int mul_loadRFIDLib();
	public native int mul_unloadRFIDLib();
	
	public native int initRFID(int handle);

	public native int deinitRFID(int handle);

	public native int setRFModuleType(int handle, int moduleType);

	public native int setRFConnectMode(int handle, int flag);

	/*
	 * public native int powerOnRFModule(void); public native int
	 * powerOffRFModule(void); public native int pushRFData(char *rdBuf, int
	 * rdLen); public native int parseRFData(void);
	 */

	public native int openCom(int handle, String port, long baud);

	public native int closeCom(int handle);

	public native int connectRemoteNetwork(int handle, String remoteAddr, int remotePort);

	public native int listenRemoteNetwork(int handle, int listenPort);

	public native int closeNetwork(int handle);

	/*
	 * RF common and don't depend on actual RF moudule(R2000 AND RM8011) RM70xx
	 * is virtual RF module
	 */
	public native int setInventoryArea(int handle, InventoryArea inventoryArea);

	public native int getInventoryArea(int handle, InventoryArea inventoryArea);

	public native int setInventoryFilterThreshold(int handle, 
			InventoryFilterThreshold inventoryFilerThreshold);

	public native int getInventoryFilterThreshold(int handle, 
			InventoryFilterThreshold inventoryFilerThreshold);

	public native int resetInventoryFilter(int handle);

	public native int setAlarmParams(int handle, AlarmParams alarmParams);

	public native int getAlarmParams(int handle, AlarmParams alarmParams);

	public native int setAlarmStatus(int handle, int status);

	public native int getAlarmStatus(int handle, RfidValue rfidValue);

	/* RF common functions */
	public native int getModuleSerialNumber(int handle, SerialNumber serialNumber);

	public native int getModuleSoftVersion(int handle, SoftVersion softVersion);

	public native int startInventory(int handle, int mode, int maskFlag);

	public native int stopInventory(int handle);

	public native int getInventoryStatus(int handle);

	public native int setPowerLevel(int handle, int antennaPort,
			AntennaPortParams antennaPortParams);

	public native int getPowerLevel(int handle, int antennaPort,
			AntennaPortParams antennaPortParams);

	public native int readTag(int handle, byte[] accessPassword, int memBank,
			int startAddr, int wordLen);
	
	public native int readTagSync(int handle, byte[] accessPassword, int memBank,
			int startAddr, int wordLen, int timeOutMs, RwData rwData);

	public native int writeTag(int handle, byte[] accessPassword, int memBank,
			int startAddr, int wordLen, byte[] pWriteData);
	
	public native int writeTagSync(int handle, byte[] accessPassword, int memBank,
			int startAddr, int wordLen, byte[] pWriteData, int timeOutMs, RwData rwData);

	public native int lockTag(int handle, int killPasswordPermissions,
			int accessPasswordPermissions, int epcMemoryBankPermissions,
			int tidMemoryBankPermissions, int userMemoryBankPermissions,
			byte[] accessPassword);
	
	public native int lockTagSync(int handle, int killPasswordPermissions,
			int accessPasswordPermissions, int epcMemoryBankPermissions,
			int tidMemoryBankPermissions, int userMemoryBankPermissions,
			byte[] accessPassword, int timeOutMs, 
			RwData rwData);
	
	public native int killTag(int handle, byte[] accessPassword, byte[] killPassword);
	
	public native int killTagSync(int handle, byte[] accessPassword, byte[] killPassword,
            int timeOutMs, RwData rwData);
	
	public native int setRegion(int handle, int range);

	public native int getRegion(int handle, RfidValue rfidValue);

	public native int setFixFreq(int handle, int freq);

	public native int getFixFreq(int handle, RfidValue rfidValue);

	public native int set18K6CSelectCriteria(int handle, int selectorIdx, SelectCriteria selectCriteria);

	public native int get18K6CSelectCriteria(int handle, int selectorIdx, SelectCriteria selectCriteria);

	public native int getAntennaPortNum(int handle, RfidValue rfidValue);

	public native int setPostSingulationMatchCriteria(int handle, 
			PostSingulationMatchCriteria params);

	public native int getPostSingulationMatchCriteria(int handle, 
			PostSingulationMatchCriteria params);

	/* For R2000 module and RM70xx */
	public native int getAntennaSWR(int handle, int antennaPort, RfidValue rfidValue);

	public native int setCurrentProfile(int handle, int profile);

	public native int getCurrentProfile(int handle, RfidValue rfidValue);

	public native int setAntennaPortState(int handle, int antennaPort, int status);

	public native int getAntennaPortState(int handle, int antennaPort, RfidValue rfidValue);

	public native int setCurrentSingulationAlgorithm(int handle, int algorithm);

	public native int getCurrentSingulationAlgorithm(int handle, RfidValue rfidValue);

	public native int setSingulationFixedQParameters(int handle, 
			FixedQParameters fixedQParameters);

	public native int getSingulationFixedQParameters(int handle, 
			FixedQParameters fixedQParameters);

	public native int setSingulationDynamicQParameters(int handle, 
			DynamicQParams dynamicQParams);

	public native int getSingulationDynamicQParameters(int handle, 
			DynamicQParams dynamicQParams);

	public native int set18K6CQueryTagGroup(int handle, QueryTagGroup queryTagGroup);

	public native int get18K6CQueryTagGroup(int handle, QueryTagGroup queryTagGroup);

	public native int setProtschTxtime(int handle, ProtschTxtime protschTxtime);

	public native int getProtschTxtime(int handle, ProtschTxtime protschTxtime);

	/*
	 * public native int prepareModuleUpdate(int *version, u16 *versionLen, u16
	 * *maxPacketLen, u16 *chunkSize, u32 firmwareLen); public native int
	 * updateModuleFirmware(int *data, u16 dataLen);
	 */

	/* For multiple Channel module and RM70xx */
	public native int setAntennaPort(int handle, int antennaPort,
			AntennaPortParams antennaPortParams);

	public native int getAntennaPort(int handle, int antennaPort,
			AntennaPortParams antennaPortParams);

	/* For RM8011 module and RM70xx */
	public native int setWorkMode(int handle, int mode);

	public native int setQuery(int handle, Rm8011Query rm8011Query);

	public native int getQuery(int handle, Rm8011Query rm8011Query);

	/* Only For RM70xx */
	// public native int boardFirmwareUpdate(int curIdx, int maxIdx, int
	// dataLen, byte[] data);

	public native int boardReboot(int handle);

	public native int getBoardSerialNumber(int handle, SerialNumber serialNumber);

	public native int getBoardSoftVersion(int handle, SoftVersion softVersion);

	public native int setBoardModuleType(int handle, int moduleType);

	public native int getBoardModuleType(int handle, RfidValue rfidValue);

	public native int setInventoryParams(int handle, InventoryParams inventoryParams);

	public native int getInventoryParams(int handle, InventoryParams inventoryParams);

	public native int setHeartBeat(int handle, HeartBeat heartBeat);

	public native int getHeartBeat(int handle, HeartBeat heartBeat);

	public native int setNetInfo(int handle, NetInfo netInfo);

	public native int getNetInfo(int handle, NetInfo netInfo);

	public native int setWifiInfo(int handle, WifiInfo wifiInfo);

	public native int getWifiInfo(int handle, WifiInfo wifiInfo);

	public native int setDOutStatus(int handle, int port, int status);

	public native int getDOutStatus(int handle, int port, RfidValue rfidValue);

	public native int getDInStatus(int handle, int port, RfidValue rfidValue);

	public native int setAlarmDout(int handle, int port, int status);

	public native int getAlarmDout(int handle, AlarmDout alarmDout);

	public native int setInputTriggerAlarm(int handle, InputTriggerAlarm inputTriggerAlarm);

	public native int getInputTriggerAlarm(int handle, InputTriggerAlarm inputTriggerAlarm);

	public native int setDoutInspectMask(int handle, int mask);

	public native int getDoutInspectMask(int handle, RfidValue rfidValue);
	
	public native int setHttpReportFormat(int handle, HttpReportFormat httpReportFormat);
	
	public native int getHttpReportFormat(int handle, HttpReportFormat httpReportFormat);
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
