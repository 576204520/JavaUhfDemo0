package com.uhf.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.detailwith.InventoryDetailWith;
import com.uhf.detailwith.RwDataDetailWith;
import com.uhf.linkage.Linkage;
import com.uhf.linkage.MulLinkage;
import com.uhf.springmvc.entity.StringUtils;
import com.uhf.structures.RfidValue;
import com.uhf.structures.SerialNumber;
import com.uhf.structures.SoftVersion;

// 设备连接 
@Controller
public class InitModelController {
	// 串口模式连接
	@RequestMapping("/connectRFModel.action")
	@ResponseBody
	public Map<String, Object> connectRFModel(String modeType,
			String modeTypeValue, String serial, String port, String band,
			String BaudRate, Model model) {
		System.out.println("接收参数band--->> " + port + band + serial + BaudRate);

		Map<String, Object> map = new HashMap<String, Object>();
		Linkage.getInstance().setRFModuleType(
				Integer.parseInt(modeType));
		// Linkage.getInstance().setRFModuleType(0);
		Linkage.getInstance().setRFConnectMode(0);
		if (Linkage.getInstance().initRFID() == 0) {
			System.out.println("Init success!");
		} else {
			System.out.println("Init Failed!");
			map.put("status", "Failed");
			map.put("data", "初始化失败");
			return map;

		}
		if (Linkage.getInstance().openCom(serial, Long.parseLong(BaudRate)) == 0) {

			SerialNumber serialNumber = new SerialNumber();

			System.out.println("Open com success!");

			if (Linkage.getInstance().getModuleSerialNumber(serialNumber) == 0) {
				String serialNo = StringUtils.byteToHexString(
						serialNumber.snData, serialNumber.snLen);
				serialNo = StringUtils.convertHexToString(serialNo);
				map.put("serialNo", serialNo);
			}
			if (modeTypeValue.equals("RM70XX")) {// RM70XX多一个方法获取序列号
				if (Linkage.getInstance().getBoardSerialNumber(serialNumber) == 0) {
					String serialNum = StringUtils.byteToHexString(
							serialNumber.snData, serialNumber.snLen);
					serialNum = StringUtils.convertHexToString(serialNum);
					map.put("serialNum", serialNum);
				}
			}

			SoftVersion softVersion = new SoftVersion();
			if (Linkage.getInstance().getModuleSoftVersion(softVersion) == 0) {
				String versionNo = StringUtils.byteToHexString(
						softVersion.version, softVersion.versionLen);
				versionNo = StringUtils.convertHexToString(versionNo);
				map.put("versionNo", versionNo);
			}
			/*
			 * else { map.put("status", "failed"); map.put("data", "连接版本号失败");
			 * return map; }
			 */
			// InventoryDetailWith inventoryDetailWith = new
			// InventoryDetailWith();
			InventoryDetailWith.getInstance()
					.setListener(Linkage.getInstance());

			// RwDataDetailWith rwDataDetailWith = new RwDataDetailWith();
			RwDataDetailWith.getInstance().setListener(Linkage.getInstance());
			if ("RM801X".equals(modeTypeValue)) { // RM8011判断天线是单口还是多口，单口没有天线设置页面，多口才有
				RfidValue rfidValue = new RfidValue();
				if (Linkage.getInstance().getAntennaPortNum(rfidValue) != 0) {
					map.put("failed", "连接失败");
					return map;
				}
				int antNum = rfidValue.value;// /1是单口,大于1是多口
				if (antNum == 1) { // RM8011判断天线是单口还是多口，单口没有天线设置页面，多口才有
					map.put("antNum", "1");
				}
			}
			map.put("status", "connectRFSuccess");
			map.put("data", "连接成功");
			return map;
		}else{
			System.out.println("打开串口失败");
		}
		map.put("status", "Failed");
		map.put("data", "连接失败");
		return map;
	}

	// 网络模式连接
	@RequestMapping("/connectNWModel.action")
	@ResponseBody
	public Map<String, Object> connectNWModel(String modeType,
			String modeTypeValue, String serial, String port, String IPAddress,
			String serverPort) {
		System.out.println("接收参数--->> " + IPAddress);
		Map<String, Object> map = new HashMap<String, Object>();

		Linkage.getInstance().setRFModuleType(
				Integer.parseInt(modeType));
		Linkage.getInstance().setRFConnectMode(1);// 连接模式网络模式
		if (Linkage.getInstance().initRFID() == 0) {
			System.out.println("Init success!");
		} else {
			System.out.println("Init Failed!");
			map.put("status", "Failed");
			map.put("data", "初始化失败");
			return map;

		}
		if (Linkage.getInstance().connectRemoteNetwork(IPAddress,
				Integer.parseInt(serverPort)) == 0) {

			SerialNumber serialNumber = new SerialNumber();

			System.out.println("Open com success!");

			if (Linkage.getInstance().getModuleSerialNumber(serialNumber) == 0) {
				String serialNo = StringUtils.byteToHexString(
						serialNumber.snData, serialNumber.snLen);
				serialNo = StringUtils.convertHexToString(serialNo);
				map.put("serialNo", serialNo);
			}
			if (modeTypeValue.equals("RM70XX")) {// RM70XX多一个方法获取序列号
				if (Linkage.getInstance().getBoardSerialNumber(serialNumber) == 0) {
					String serialNum = StringUtils.byteToHexString(
							serialNumber.snData, serialNumber.snLen);
					serialNum = StringUtils.convertHexToString(serialNum);
					map.put("serialNum", serialNum);
				}
			}

			SoftVersion softVersion = new SoftVersion();
			if (Linkage.getInstance().getModuleSoftVersion(softVersion) == 0) {
				String versionNo = StringUtils.byteToHexString(
						softVersion.version, softVersion.versionLen);
				versionNo = StringUtils.convertHexToString(versionNo);
				map.put("versionNo", versionNo);
			}

			InventoryDetailWith.getInstance()
					.setListener(Linkage.getInstance());

			RwDataDetailWith.getInstance().setListener(Linkage.getInstance());
			if ("RM801X".equals(modeTypeValue)) { // RM8011判断天线是单口还是多口，单口没有天线设置页面，多口才有
				RfidValue rfidValue = new RfidValue();
				if (Linkage.getInstance().getAntennaPortNum(rfidValue) != 0) {
					map.put("failed", "连接失败");
					return map;
				}
				int antNum = rfidValue.value;// /1是单口,大于1是多口
				if (antNum == 1) { // RM8011判断天线是单口还是多口，单口没有天线设置页面，多口才有
					map.put("antNum", "1");
				}
			}
			map.put("status", "connectNWSuccess");
			map.put("data", "网络模式连接成功");
			return map;
		}
		map.put("status", "Failed");
		map.put("data", "网络模式连接失败");
		return map;

	}

	// 网络模式监听
	@RequestMapping("/monitorPort.action")
	@ResponseBody
	public Map<String, Object> monitorPort(String monitorPort) {
		System.out.println("接收参数--->> " + monitorPort);
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("status", "success");
		map.put("data", "监听成功");
		return map;
	}

	// 断开
	@RequestMapping("/closeRFModel.action")
	@ResponseBody
	public Map<String, Object> closeRFModel() {
		Map<String, Object> map = new HashMap<String, Object>();
		int status;
		status = Linkage.getInstance().closeCom();
		Linkage.getInstance().deinitRFID();
		if (status == 0) {
			map.put("status", "closeRFSuccess");
			map.put("data", "断开成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "断开失败");
		return map;
	}

	// 断开
	@RequestMapping("/closeNetwork.action")
	@ResponseBody
	public Map<String, Object> closeNetwork() {
		Map<String, Object> map = new HashMap<String, Object>();
		int status;
		status = Linkage.getInstance().closeNetwork();
		Linkage.getInstance().deinitRFID();
		if (status == 0) {
			map.put("status", "closeNWSuccess");
			map.put("data", "断开成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "断开失败");
		return map;
	}
	
	@RequestMapping("/connectMulRFModule.action")
	@ResponseBody
	public Map<String, Object> connectMulRFModule(String modeType,
			String modeTypeValue, String serial, String port, String band,
			String BaudRate, Model model,HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		int handle = MulLinkage.getInstance().mul_loadRFIDLib();
		System.out.println(handle);
		HttpSession session = request.getSession();
		session.setAttribute("handle", handle);
		
		if (handle >= 0) {
			MulLinkage.getInstance().setRFModuleType(handle,
					Integer.parseInt(modeType));
			MulLinkage.getInstance().setRFConnectMode(handle,0);
			
			if (MulLinkage.getInstance().initRFID(handle) == 0) {
				System.out.println("Init success!");
			} else {
				System.out.println("Init Failed!");
				map.put("status", "Failed");
				map.put("data", "初始化失败");
				return map;

			}
			
			if (MulLinkage.getInstance().openCom(handle,serial, Long.parseLong(BaudRate)) == 0) {

				SerialNumber serialNumber = new SerialNumber();

				System.out.println("Open com success!");

				if (MulLinkage.getInstance().getModuleSerialNumber(handle,serialNumber) == 0) {
					String serialNo = StringUtils.byteToHexString(
							serialNumber.snData, serialNumber.snLen);
					serialNo = StringUtils.convertHexToString(serialNo);
					map.put("serialNo", serialNo);
				}
				
				if (modeTypeValue.equals("RM70XX")) {
				    // RM70XX多一个方法获取序列号
					if (MulLinkage.getInstance().getBoardSerialNumber(handle,serialNumber) == 0) {
						String serialNum = StringUtils.byteToHexString(
								serialNumber.snData, serialNumber.snLen);
						serialNum = StringUtils.convertHexToString(serialNum);
						map.put("serialNum", serialNum);
					}
				}

				SoftVersion softVersion = new SoftVersion();
				if (MulLinkage.getInstance().getModuleSoftVersion(handle,softVersion) == 0) {
					String versionNo = StringUtils.byteToHexString(
							softVersion.version, softVersion.versionLen);
					versionNo = StringUtils.convertHexToString(versionNo);
					map.put("versionNo", versionNo);
				}
				/*
				 * else { map.put("status", "failed"); map.put("data", "连接版本号失败");
				 * return map; }
				 */
				// InventoryDetailWith inventoryDetailWith = new
				// InventoryDetailWith();
				InventoryDetailWith.getInstance()
						.setListener(MulLinkage.getInstance());

				// RwDataDetailWith rwDataDetailWith = new RwDataDetailWith();
				RwDataDetailWith.getInstance().setListener(MulLinkage.getInstance());
				if ("RM801X".equals(modeTypeValue)) { 
				    // RM8011判断天线是单口还是多口，单口没有天线设置页面，多口才有
					RfidValue rfidValue = new RfidValue();
					if (MulLinkage.getInstance().getAntennaPortNum(handle,rfidValue) != 0) {
						map.put("failed", "连接失败");
						return map;
					}
					int antNum = rfidValue.value; // 1是单口,大于1是多口
					if (antNum == 1) { 
					    // RM8011判断天线是单口还是多口，单口没有天线设置页面，多口才有
						map.put("antNum", "1");
					}
				}
				map.put("status", "connectRFSuccess");
				map.put("data", "连接成功");
				return map;
			}else{
				System.out.println("打开串口失败");
			}
		}
	
		map.put("status", "Failed");
		map.put("data", "连接失败");
		return map;
	}
	
	
    // 断开
	@RequestMapping("/closeMulRFModule.action")
	@ResponseBody
	public Map<String, Object> closeUhf(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		int handle = (int)session.getAttribute("handle");
		int status;
		
		status = MulLinkage.getInstance().closeCom(handle);
		MulLinkage.getInstance().deinitRFID(handle);
		MulLinkage.getInstance().mul_unloadRFIDLib();
		session.removeAttribute("handle");
		
		if (status == 0) {
			map.put("status", "closeRFSuccess");
			map.put("data", "断开成功");
			return map;
		}
		
		map.put("status", "failed");
		map.put("data", "断开失败");
		return map;
	}
}
