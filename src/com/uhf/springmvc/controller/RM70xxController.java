package com.uhf.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.linkage.Linkage;
import com.uhf.springmvc.entity.StringUtils;
import com.uhf.structures.AlarmDout;
import com.uhf.structures.AlarmParams;
import com.uhf.structures.HeartBeat;
import com.uhf.structures.HttpReportFormat;
import com.uhf.structures.InputTriggerAlarm;
import com.uhf.structures.InventoryParams;
import com.uhf.structures.NetInfo;
import com.uhf.structures.RfidValue;
import com.uhf.structures.SoftVersion;
import com.uhf.structures.WifiInfo;

@Controller
public class RM70xxController {
	// RF模块
	@RequestMapping("/rfGet.action")
	@ResponseBody
	public Map<String, Object> rfGet(String rfModule) {
		System.out.println("接收参数--->> " + rfModule);
		Map<String, Object> map = new HashMap<String, Object>();
		RfidValue rfidValue = new RfidValue();
		int status = Linkage.getInstance().getBoardModuleType(rfidValue);
		if (status == 0) {
			map.put("value", rfidValue.value);
			map.put("status", "rfGetSuccess");
			map.put("data", "获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "获取失败");
		return map;
	}

	// RF模块
	@RequestMapping("/rfSet.action")
	@ResponseBody
	public Map<String, Object> rfSet(String rfModule) {
		System.out.println("接收参数--->> " + rfModule);
		Map<String, Object> map = new HashMap<String, Object>();
		int status = Linkage.getInstance().setBoardModuleType(
				Integer.parseInt(rfModule));
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "设置失败");
		return map;
	}

	// 心跳
	@RequestMapping("/heartbeatGet.action")
	@ResponseBody
	public Map<String, Object> heartbeatGet() {
		Map<String, Object> map = new HashMap<String, Object>();
		HeartBeat heartBeat = new HeartBeat();
		int result = Linkage.getInstance().getHeartBeat(heartBeat);
		if (result == 0) {
			map.put("result", heartBeat.status);
			map.put("interval", heartBeat.interval);
			map.put("status", "heartbeatGetSuccess");
			map.put("data", "心跳获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "心跳获取失败");
		return map;
	}

	// 心跳
	@RequestMapping("/heartbeatSet.action")
	@ResponseBody
	public Map<String, Object> heartbeatSet(String status, String interval) {
		Map<String, Object> map = new HashMap<String, Object>();
		HeartBeat heartBeat = new HeartBeat();
		heartBeat
				.setValue(Integer.parseInt(status), Integer.parseInt(interval));
		int result = Linkage.getInstance().setHeartBeat(heartBeat);
		if (result == 0) {
			map.put("status", "success");
			map.put("data", "心跳设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "心跳设置失败");
		return map;
	}

	// 盘点
	@RequestMapping("/inventoryModelGet.action")
	@ResponseBody
	public Map<String, Object> inventoryModelGet() {
		Map<String, Object> map = new HashMap<String, Object>();
		InventoryParams inventoryParams = new InventoryParams();
		int status = Linkage.getInstance().getInventoryParams(inventoryParams);
		if (status == 0) {
			map.put("mode", inventoryParams.mode);
			map.put("triggerDInPort", inventoryParams.triggerDInPort);
			map.put("triggerLevel", inventoryParams.triggerLevel);
			map.put("ignoreTimeMs", inventoryParams.ignoreTimeMs);
			map.put("inventoryTimeMs", inventoryParams.inventoryTimeMs);
			map.put("maxCountReportFlag", inventoryParams.maxCountReportFlag);
			map.put("status", "getSuccess");
			map.put("data", "盘点获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "盘点获取失败");
		return map;
	}

	// 盘点
	@RequestMapping("/inventoryModelSet.action")
	@ResponseBody
	public Map<String, Object> inventoryModelSet(String mode,
			String triggerDInPort, String triggerLevel, String ignoreTimeMs,
			String inventoryTimeMs, String maxCountReportFlag) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 勾选了 最多次数 必须设置盘点时间
		if (maxCountReportFlag.equals("1")) {
			if (Integer.parseInt(inventoryTimeMs) == 0) {
				map.put("status", "failed");
				map.put("data", " 最多次数 上报必须设置盘点时间");
				return map;
			}

		}
		InventoryParams inventoryParams = new InventoryParams();

		inventoryParams.setValue(Integer.parseInt(mode),
				Integer.parseInt(triggerDInPort),
				Integer.parseInt(triggerLevel), Integer.parseInt(ignoreTimeMs),
				Integer.parseInt(inventoryTimeMs),
				Integer.parseInt(maxCountReportFlag));
		int status = Linkage.getInstance().setInventoryParams(inventoryParams);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "盘点设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "盘点设置失败");
		return map;
	}

	// 网络配置
	@RequestMapping("/networkConfigurationGet.action")
	@ResponseBody
	public Map<String, Object> networkConfigurationGet(String networkType) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (Integer.parseInt(networkType) == 0) {
			// 有线网络
			NetInfo netInfo = new NetInfo();
			int status = Linkage.getInstance().getNetInfo(netInfo);
			if (status == 0) {
				map.put("ip", StringUtils.bytesToIp(netInfo.ip));
				map.put("gateWay", StringUtils.bytesToIp(netInfo.gateWay));
				map.put("netmask", StringUtils.bytesToIp(netInfo.netmask));
				map.put("remoteIp", StringUtils.bytesToIp(netInfo.remoteIp));
				map.put("macAddr",
						StringUtils.byteToHexString(netInfo.macAddr, 6));
				map.put("remotePort", netInfo.remotePort);
				map.put("pingGateWay", netInfo.pingGateWay);

				map.put("status", "wiredNetworkSuccess");
				map.put("data", "获取成功");
				return map;
			}
		} else {// 无线网络
			WifiInfo wifiInfo = new WifiInfo();
			int status = Linkage.getInstance().getWifiInfo(wifiInfo);
			if (status == 0) {
				map.put("ip", StringUtils.bytesToIp(wifiInfo.ip));
				map.put("gateWay", StringUtils.bytesToIp(wifiInfo.gateWay));
				map.put("netmask", StringUtils.bytesToIp(wifiInfo.netmask));
				map.put("remoteIp", StringUtils.bytesToIp(wifiInfo.remoteIp));
				map.put("remotePort", wifiInfo.remotePort);
				String passwd = StringUtils.byteToHexString(wifiInfo.passwd,
						wifiInfo.passwdLen);
				map.put("passwd", StringUtils.convertHexToString(passwd));
				String ssid = StringUtils.byteToHexString(wifiInfo.ssid,
						wifiInfo.ssidLen);
				map.put("ssid", StringUtils.convertHexToString(ssid));

				map.put("status", "wirelessNetworkSuccess");
				map.put("data", "获取成功");
				return map;
			}
		}
		map.put("status", "success");
		map.put("data", "获取失败");
		return map;
	}

	// 网络配置 设置
	@RequestMapping("/networkConfigurationSet.action")
	@ResponseBody
	public Map<String, Object> networkConfigurationSet(String networkType,
			String iPAddress, String gateway, String pingGateway,
			String subnetMask, String MACAddressA, String MACAddressB,
			String MACAddressC, String MACAddressD, String MACAddressE,
			String MACAddressF, String machineIP, String machinePort,
			String wifiSSID, String wifiPassword) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (Integer.parseInt(networkType) == 0) {// 有线网络
			NetInfo netInfo = new NetInfo();
			// 192.168.1.16，四个字节就是192 168 1 16
			byte[] ip = StringUtils.ipToBytesByReg(iPAddress);
			byte[] gateWay = StringUtils.ipToBytesByReg(gateway);
			byte[] netmask = StringUtils.ipToBytesByReg(subnetMask);
			byte[] remoteIp = StringUtils.ipToBytesByReg(machineIP);

			String macAddr = MACAddressA + MACAddressB + MACAddressC
					+ MACAddressD + MACAddressE + MACAddressF;
			System.out.println(macAddr);
			// // mac是十六进制 其他都是十进制
			byte[] mac = StringUtils.stringToByte(macAddr);
			int remotePort = Integer.parseInt(machinePort);
			int pingGateWay = Integer.parseInt(pingGateway);
			netInfo.setValue(ip, gateWay, netmask, mac, remoteIp, remotePort,
					pingGateWay);
			int status = Linkage.getInstance().setNetInfo(netInfo);
			if (status == 0) {
				map.put("status", "success");
				map.put("data", "设置成功");
				return map;
			} else if (status == -2) {
				map.put("status", "failed");
				map.put("data", "有限网络与无线网络的ip地址不能相同");
				return map;
			}
		} else { // 无线网络
			WifiInfo wifiInfo = new WifiInfo();
			// 转换为十进制
			byte[] ip = StringUtils.ipToBytesByReg(iPAddress);
			byte[] gateWay = StringUtils.ipToBytesByReg(gateway);
			byte[] netmask = StringUtils.ipToBytesByReg(subnetMask);
			byte[] remoteIp = StringUtils.ipToBytesByReg(machineIP);
			int remotePort = Integer.parseInt(machinePort);
			// 转换为字节
			byte[] ssid = wifiSSID.getBytes();
			byte[] passwd = wifiPassword.getBytes();
			int ssidLen = wifiSSID.length();
			int passwdLen = wifiPassword.length();

			wifiInfo.setValue(ssid, ssidLen, passwd, passwdLen, ip, gateWay,
					netmask, remoteIp, remotePort);
			int status = Linkage.getInstance().setWifiInfo(wifiInfo);
			if (status == 0) {
				map.put("status", "success");
				map.put("data", "设置成功");
				return map;
			} else if (status == -2) {
				map.put("status", "failed");
				map.put("data", "有限网络与无线网络的ip地址不能相同");
				return map;
			}
		}
		map.put("status", "failed");
		map.put("data", "设置失败");
		return map;
	}

	// // ///////////////////////GPIO配置/////////////////////////////////////
	// // 输出端口
	@RequestMapping("/outPutPortGet.action")
	@ResponseBody
	public Map<String, Object> outPutPortGet(String port, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		RfidValue rfidValue = new RfidValue();
		int result = Linkage.getInstance().getDOutStatus(
				Integer.parseInt(port), rfidValue);
		if (result == 0) {
			map.put("value", rfidValue.value);
			map.put("status", "outPutPortGetSuccess");
			map.put("data", "输出端口获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "输出端口获取失败");
		return map;

	}

	// 输出端口
	@RequestMapping("/outPutPortSet.action")
	@ResponseBody
	public Map<String, Object> outPutPortSet(String port, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = Linkage.getInstance().setDOutStatus(
				Integer.parseInt(port), Integer.parseInt(status));
		if (result == 0) {
			map.put("status", "success");
			map.put("data", "输出端口设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "输出端口设置失败");
		return map;
	}

	// 启动测试
	@RequestMapping("/startTestGet.action")
	@ResponseBody
	public Map<String, Object> startTestGet() {
		Map<String, Object> map = new HashMap<String, Object>();
		RfidValue rfidValue = new RfidValue();
		int status = Linkage.getInstance().getDoutInspectMask(rfidValue);
		if (status == 0) {
			int mask = rfidValue.value;
			String D7 = ((mask & 0x80) == 0x80) ? "1" : "0";
			String D6 = ((mask & 0x40) == 0x40) ? "1" : "0";
			String D5 = ((mask & 0x20) == 0x20) ? "1" : "0";
			String D4 = ((mask & 0x10) == 0x10) ? "1" : "0";
			String D3 = ((mask & 0x08) == 0x08) ? "1" : "0";
			String D2 = ((mask & 0x04) == 0x04) ? "1" : "0";
			String D1 = ((mask & 0x02) == 0x02) ? "1" : "0";
			String D0 = ((mask & 0x01) == 0x01) ? "1" : "0";
			map.put("D7", D7);
			map.put("D6", D6);
			map.put("D5", D5);
			map.put("D4", D4);
			map.put("D3", D3);
			map.put("D2", D2);
			map.put("D1", D1);
			map.put("D0", D0);
			map.put("status", "startTestGetSuccess");
			map.put("data", "启动测试获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "启动测试获取失败");
		return map;
	}

	// 启动测试
	@RequestMapping("/startTestSet.action")
	@ResponseBody
	public Map<String, Object> startTestSet(String D7, String D6, String D5,
			String D4, String D3, String D2, String D1, String D0) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 需要转为hex值
		int mask = (Integer.parseInt(D7) << 7) | (Integer.parseInt(D6) << 6)
				| (Integer.parseInt(D5) << 5) | (Integer.parseInt(D4) << 4)
				| (Integer.parseInt(D3) << 3) | (Integer.parseInt(D2) << 2)
				| (Integer.parseInt(D1) << 1) | (Integer.parseInt(D0) << 0);
		int status = Linkage.getInstance().setDoutInspectMask(mask);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "启动测试设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "启动测试设置失败");
		return map;
	}

	// // 输入端口
	@RequestMapping("/inPutPortGet.action")
	@ResponseBody
	public Map<String, Object> inPutPortGet(String port) {
		Map<String, Object> map = new HashMap<String, Object>();
		RfidValue rfidValue = new RfidValue();
		int status = Linkage.getInstance().getDInStatus(Integer.parseInt(port),
				rfidValue);
		if (status == 0) {
			map.put("value", rfidValue.value);
			map.put("status", "inPutPortGetSuccess");
			map.put("data", "输入端口获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "输入端口获取失败");
		return map;
	}

	// // ///////////////////////标签触发报警/////////////////////////////////////

	// 标签触发报警
	@RequestMapping("/alarmGet.action")
	@ResponseBody
	public Map<String, Object> alarmGet(String callPolice, String police,
			String accidentTime, String continuousTime, String reportNum,
			String model, String EPCValue, String state) {
		Map<String, Object> map = new HashMap<String, Object>();

		AlarmParams alarmParams = new AlarmParams();
		AlarmDout alarmDout = new AlarmDout();
		int status = Linkage.getInstance().getAlarmDout(alarmDout);
		int result = Linkage.getInstance().getAlarmParams(alarmParams);
		if (status == 0 && result == 0) {

			map.put("port", alarmDout.port);
			map.put("resultStatus", alarmDout.status);
			map.put("mode", alarmParams.mode);
			map.put("alarmTimes", alarmParams.alarmTimes);
			map.put("ignoreSec", alarmParams.ignoreSec);
			map.put("alarmSec", alarmParams.alarmSec);
			map.put("matchStart", alarmParams.matchStart);
			map.put("epc", StringUtils.byteToHexString(alarmParams.match,
					alarmParams.matchLen));

			map.put("status", "alarmGetSuccess");
			map.put("data", "标签触发报警获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "标签触发报警获取失败");
		return map;
	}

	// 标签触发报警
	@RequestMapping("/alarmSet.action")
	@ResponseBody
	public Map<String, Object> alarmSet(String callPolice, String police,
			String accidentTime, String continuousTime, String reportNum,
			String model, String offsetBytes, String EPCValue, String state) {
		Map<String, Object> map = new HashMap<String, Object>();

		AlarmParams alarmParams = new AlarmParams();

		int mode = Integer.parseInt(model);// 模式
		int alarmTimes = Integer.parseInt(reportNum);// 上报次数
		int ignoreSec = Integer.parseInt(accidentTime);// 误触
		int alarmSec = Integer.parseInt(continuousTime);// 报警
		int matchStart = Integer.parseInt(offsetBytes);// 上报次数
		// 匹配长度，epc输入框中输入了几个字节。输入的长度必须是2的整数倍
		int matchLen = EPCValue.length();
		if (matchLen != 0) {
			if (matchLen % 2 != 0) {
				map.put("status", "failed");
				map.put("data", "epc输入有误");
				return map;
			}
		}

		byte[] match = StringUtils.stringToByte(EPCValue);

		alarmParams.setValue(mode, alarmTimes, ignoreSec, alarmSec, matchStart,
				matchLen, match);
		int status = Linkage.getInstance().setAlarmDout(
				Integer.parseInt(callPolice), Integer.parseInt(police));
		int result = Linkage.getInstance().setAlarmParams(alarmParams);

		if (status == 0 && result == 0) {
			map.put("status", "success");
			map.put("data", "标签触发报警设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "标签触发报警设置失败");
		return map;
	}

	// 标签触发报警 启用报警
	@RequestMapping("/enableAlarm.action")
	@ResponseBody
	public Map<String, Object> enableAlarm() {
		Map<String, Object> map = new HashMap<String, Object>();
		int status = Linkage.getInstance().setAlarmStatus(1);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", " 启用报警成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", " 启用报警失败");
		return map;
	}

	// 标签触发报警 禁用报警
	@RequestMapping("/disableAlarm.action")
	@ResponseBody
	public Map<String, Object> disableAlarm() {
		Map<String, Object> map = new HashMap<String, Object>();
		int status = Linkage.getInstance().setAlarmStatus(0);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", " 禁用报警成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", " 禁用报警失败");
		return map;
	}

	// 输入触发报警
	@RequestMapping("/inputTriggerAlarmGet.action")
	@ResponseBody
	public Map<String, Object> inputTriggerAlarmGet(String triggerState,
			String inputAccidentTime, String inputContinuousTime,
			String inputTriggerPin, String inputTriggerPinState,
			String inputCallPolice, String inputCallPoliceState) {
		Map<String, Object> map = new HashMap<String, Object>();
		InputTriggerAlarm inputTriggerAlarm = new InputTriggerAlarm();
		int status = Linkage.getInstance().getInputTriggerAlarm(
				inputTriggerAlarm);
		if (status == 0) {
			map.put("resultStatus", inputTriggerAlarm.status);
			map.put("triggerDInPort", inputTriggerAlarm.triggerDInPort);
			map.put("triggerLevel", inputTriggerAlarm.triggerLevel);
			map.put("DPort", inputTriggerAlarm.DPort);
			map.put("DOutLevel", inputTriggerAlarm.DOutLevel);
			map.put("ignoreMs", inputTriggerAlarm.ignoreMs);
			map.put("alarmMs", inputTriggerAlarm.alarmMs);
			map.put("status", "getSuccess");
			map.put("data", "输入触发报警获取成功");
			return map;
		}

		map.put("status", "failed");
		map.put("data", "输入触发报警获取失败");
		return map;
	}

	// 输入触发报警
	@RequestMapping("/inputTriggerAlarmSet.action")
	@ResponseBody
	public Map<String, Object> inputTriggerAlarmSet(String triggerState,
			String inputAccidentTime, String inputContinuousTime,
			String inputTriggerPin, String inputTriggerPinState,
			String inputCallPolice, String inputCallPoliceState) {
		Map<String, Object> map = new HashMap<String, Object>();
		InputTriggerAlarm inputTriggerAlarm = new InputTriggerAlarm();
		inputTriggerAlarm.setValue(Integer.parseInt(triggerState),
				Integer.parseInt(inputTriggerPin),
				Integer.parseInt(inputTriggerPinState),
				Integer.parseInt(inputCallPolice),
				Integer.parseInt(inputCallPoliceState),
				Integer.parseInt(inputAccidentTime),
				Integer.parseInt(inputContinuousTime));
		int status = Linkage.getInstance().setInputTriggerAlarm(
				inputTriggerAlarm);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "输入触发报警设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "输入触发报警设置失败");
		return map;
	}
	
	// // ///////////////////////定制HTTP上传信息/////////////////////////////////////
	// 获取
	@RequestMapping("/httpReportGet.action")
	@ResponseBody
	public Map<String, Object> httpReportGet() {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpReportFormat httpReportFormat = new HttpReportFormat();
		int status = Linkage.getInstance().getHttpReportFormat(httpReportFormat);
		if (status == 0) {
			//方法类型
			map.put("method", httpReportFormat.method);
			//Action
			map.put("actionName", StringUtils.convertHexToString(StringUtils.byteToHexString(httpReportFormat.actionName,
					httpReportFormat.actionLen)));
			//用户定制信息
			map.put("customMsg", StringUtils.convertHexToString(StringUtils.byteToHexString(httpReportFormat.customMsg,
					httpReportFormat.customMsgLen)));
			//是否启用
			map.put("statusType", httpReportFormat.status);
			//内容
			int mask = httpReportFormat.contentMask;
			String D7 = ((mask & 0x80) == 0x80) ? "1" : "0";
			String D6 = ((mask & 0x40) == 0x40) ? "1" : "0";
			String D5 = ((mask & 0x20) == 0x20) ? "1" : "0";
			String D4 = ((mask & 0x10) == 0x10) ? "1" : "0";
			String D3 = ((mask & 0x08) == 0x08) ? "1" : "0";
			String D2 = ((mask & 0x04) == 0x04) ? "1" : "0";
			String D1 = ((mask & 0x02) == 0x02) ? "1" : "0";
			String D0 = ((mask & 0x01) == 0x01) ? "1" : "0";
			map.put("D7", D7);
			map.put("D6", D6);
			map.put("D5", D5);
			map.put("D4", D4);
			map.put("D3", D3);
			map.put("D2", D2);
			map.put("D1", D1);
			map.put("D0", D0);
			map.put("status", "httpReportGetSuccess");
			map.put("data", "定制HTTP上传信息获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "定制HTTP上传信息获取失败");
		return map;
	}
	
	//设置
	@RequestMapping("/httpReportSet.action")
	@ResponseBody
	public Map<String, Object> httpReportSet(Integer statusType,String actionName,Integer method,
			String customMsg,String D7, String D6, String D5,
			String D4, String D3, String D2, String D1, String D0) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 需要转为hex值
		int contentMask = (Integer.parseInt(D7) << 7) | (Integer.parseInt(D6) << 6)
				| (Integer.parseInt(D5) << 5) | (Integer.parseInt(D4) << 4)
				| (Integer.parseInt(D3) << 3) | (Integer.parseInt(D2) << 2)
				| (Integer.parseInt(D1) << 1) | (Integer.parseInt(D0) << 0);		
		if(actionName == null){
			actionName = "";
		}
		if(customMsg == null){
			customMsg = "";
		}
		HttpReportFormat httpReportFormat = new HttpReportFormat(statusType,actionName.length(),
				actionName.getBytes(),customMsg.length(),customMsg.getBytes(),method,contentMask);
		int status = Linkage.getInstance().setHttpReportFormat(httpReportFormat);
		if (status == 0) {
			map.put("status", "httpReportSetSuccess");
			map.put("data", "定制HTTP上传信息设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "定制HTTP上传信息设置失败");
		return map;
	}
	
	// 版本号获取
	@RequestMapping("/getBoardSoftVersion.action")
	@ResponseBody
	public Map<String, Object> getBoardSoftVersion(String versionNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		SoftVersion softVersion = new SoftVersion();
		int status = Linkage.getInstance().getBoardSoftVersion(softVersion);
		if (status == 0) {
			String version = StringUtils.convertHexToString(StringUtils
					.byteToHexString(softVersion.version,
							softVersion.versionLen));
			map.put("version", version);
			map.put("status", "getVersionSuccess");
			map.put("data", "获取版本号成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "获取版本号失败");
		return map;
	}

	// 设备重启
	@RequestMapping("/boardReboot.action")
	@ResponseBody
	public Map<String, Object> boardReboot() {
		Map<String, Object> map = new HashMap<String, Object>();
		int status = Linkage.getInstance().boardReboot();
		//int status = 0;
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "设备重启成功");
			System.out.println("设备重启成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "设备重启失败");
		System.out.println("设备重启失败");
		return map;
	}

}
