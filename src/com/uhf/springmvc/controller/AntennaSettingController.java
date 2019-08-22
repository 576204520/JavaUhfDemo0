package com.uhf.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.linkage.Linkage;
import com.uhf.structures.AntennaPortParams;
import com.uhf.structures.RfidValue;

//天线设置

@Controller
public class AntennaSettingController {
	private static int sAntNum = 0;

	// 获取天线配置
	@RequestMapping("/getAntennaPort.action")
	@ResponseBody
	public List<Map<String, Object>> getAntennaPort(String antennaEnabling,
			String starNum, String endNum, String power, String residingTime,
			String diskCycle, String bobbi) {
		List<Map<String, Object>> list = new ArrayList<>();

		AntennaPortParams antennaPortParams = new AntennaPortParams();
		for (int idx = Integer.parseInt(starNum); idx <= Integer
				.parseInt(endNum); idx++) {
			Linkage.getInstance().getAntennaPort(idx, antennaPortParams);
			RfidValue RfidValue = new RfidValue();
			// 获取驻波比
			int status = Linkage.getInstance().getAntennaSWR(idx, RfidValue);
			if (status == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("antennaPort", antennaPortParams.getAntennaPort());
				map.put("antennaStatus", antennaPortParams.getAntennaStatus());
				map.put("powerLevel", antennaPortParams.getPowerLevel() / 10.0);
				map.put("dwellTime", antennaPortParams.getDwellTime());
				map.put("numberInventoryCycles",
						antennaPortParams.getNumberInventoryCycles());
				// 获取驻波比(获取的值/1000,在页面显示)
				map.put("swr", RfidValue.value / 1000.0);// 驻波比
				map.put("data", "获取天线配置成功");
				map.put("status", "success");
				list.add(map);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("data", "获取天线配置失败");
				map.put("status", "failed");
				list.add(map);
				return list;
			}
		}
		return list;
	}

	// 修改天线配置 修改
	@RequestMapping("/setAntennaPort.action")
	@ResponseBody
	public List<Map<String, Object>> setAntennaPort(String antennaStatus,
			String starNum, String endNum, String power, String residingTime,
			String diskCycle, String bobbi) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (int idx = Integer.parseInt(starNum); idx <= Integer
				.parseInt(endNum); idx++) {
			// 根据选择的天线号的起始值和页面的属性值，重新设置值 。不修改驻波比
			AntennaPortParams antennaPortParams = new AntennaPortParams();
			antennaPortParams.setAntennaPort(idx);
			antennaPortParams.setAntennaStatus(Integer.parseInt(antennaStatus));
			antennaPortParams.setDwellTime(Integer.parseInt(residingTime));
			antennaPortParams.setNumberInventoryCycles(Integer
					.parseInt(diskCycle));
			antennaPortParams.setPowerLevel(Integer.parseInt(power) * 10);
			int status = Linkage.getInstance().setAntennaPort(idx,
					antennaPortParams);
			if (status == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("antennaPort", antennaPortParams.getAntennaPort());
				map.put("antennaStatus", antennaPortParams.getAntennaStatus());
				map.put("powerLevel", antennaPortParams.getPowerLevel() / 10.0);
				map.put("dwellTime", antennaPortParams.getDwellTime());
				map.put("numberInventoryCycles",
						antennaPortParams.getNumberInventoryCycles());
				// map.put("swr", Integer.parseInt(bobbi) / 100.0);
				map.put("status", "success");
				map.put("data", "修改天线配置成功");
				list.add(map);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("status", "failed");
				map.put("data", "修改天线配置失败");
				list.add(map);
				return list;
			}
		}
		return list;
	}

	// 全部获取
	@RequestMapping("/getAll.action")
	@ResponseBody
	public static List<Map<String, Object>> getAll(String modelType) {
		List<Map<String, Object>> list = new ArrayList<>();

		if (sAntNum == 0) {
			RfidValue rfidValue = new RfidValue();
			if (Linkage.getInstance().getAntennaPortNum(rfidValue) != 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("failed", "获取失败");
				list.add(map);
				return list;
			}
			sAntNum = rfidValue.value;
		}
		if ("0".equals(modelType)) { // RM8011是单口还是多口，单口没有天线设置页面，多口才有
			System.out.println(sAntNum);
		}

		for (int idx = 0; idx < sAntNum; idx++) {
			AntennaPortParams antennaPortParams = new AntennaPortParams();
			// 天线信息
			int status = Linkage.getInstance().getAntennaPort(idx,
					antennaPortParams);
			RfidValue RfidValue = new RfidValue();
			// RM8011页面没有驻波比，不要获取
			if ("1".equals(modelType)) {
				status = Linkage.getInstance().getAntennaSWR(idx, RfidValue);
			}
			if (status == 0) {
				// 放入不同的map中
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("antennaPort", antennaPortParams.getAntennaPort());
				map.put("antennaStatus", antennaPortParams.getAntennaStatus());
				map.put("powerLevel", antennaPortParams.getPowerLevel() / 10.0);
				map.put("dwellTime", antennaPortParams.getDwellTime());
				map.put("numberInventoryCycles",
						antennaPortParams.getNumberInventoryCycles());
				// 获取驻波比(获取的值/1000,在页面显示)
				map.put("swr", RfidValue.value / 1000.0);// 驻波比
				map.put("success", "获取成功");
				list.add(map);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("failed", "获取失败");
				list.add(map);
				return list;
			}
		}
		return list;
	}

	@RequestMapping("/enableAll.action")
	@ResponseBody
	public static Map<String, Object> enableAll() {
		Map<String, Object> map = new HashMap<>();

		for (int idx = 0; idx < sAntNum; idx++) {
			int status = Linkage.getInstance().setAntennaPortState(idx, 1);
			if (status != 0) {
				map.put("status", "failed");
				map.put("data", "全部打开失败");
				return map;
			}
		}
		map.put("status", "success");
		map.put("data", "全部打开成功");
		return map;
	}

	@RequestMapping("/disableAll.action")
	@ResponseBody
	public Map<String, Object> disableAll() {
		Map<String, Object> map = new HashMap<>();

		for (int idx = 0; idx < sAntNum; idx++) {
			int status = Linkage.getInstance().setAntennaPortState(idx, 0);
			if (status != 0) {
				map.put("status", "failed");
				map.put("data", "全部关闭失败");
				return map;
			}
		}
		map.put("status", "success");
		map.put("data", "全部关闭成功");
		return map;
	}

	// RM8011修改天线配置 修改
	@RequestMapping("/setAntennaPortRM8011.action")
	@ResponseBody
	public List<Map<String, Object>> setAntennaPortRM8011(String antennaStatus,
			String starNum, String endNum, String residingTime) {
		List<Map<String, Object>> list = new ArrayList<>();

		for (int idx = Integer.parseInt(starNum); idx <= Integer
				.parseInt(endNum); idx++) {
			// 根据选择的天线号的起始值和页面的属性值，重新设置值 。不修改驻波比
			AntennaPortParams antennaPortParams = new AntennaPortParams();
			antennaPortParams.setAntennaPort(idx);
			antennaPortParams.setAntennaStatus(Integer.parseInt(antennaStatus));
			antennaPortParams.setDwellTime(Integer.parseInt(residingTime));

			int status = Linkage.getInstance().setAntennaPort(idx,
					antennaPortParams);
			if (status == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("antennaPort", antennaPortParams.getAntennaPort());
				map.put("antennaStatus", antennaPortParams.getAntennaStatus());
				// map.put("powerLevel", antennaPortParams.getPowerLevel() /
				// 10.0);
				map.put("dwellTime", antennaPortParams.getDwellTime());
				/*
				 * map.put("numberInventoryCycles",
				 * antennaPortParams.getNumberInventoryCycles());
				 */
				// map.put("swr", Integer.parseInt(bobbi) / 100.0);
				map.put("status", "success");
				map.put("data", "修改天线配置成功");
				list.add(map);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("status", "failed");
				map.put("data", "修改天线配置失败");
				list.add(map);
				return list;
			}
		}
		return list;
	}

	// 获取天线配置
	@RequestMapping("/getAntennaPortRM8011.action")
	@ResponseBody
	public List<Map<String, Object>> getAntennaPortRM8011(
			String antennaEnabling, String starNum, String endNum,
			String residingTime) {
		List<Map<String, Object>> list = new ArrayList<>();

		AntennaPortParams antennaPortParams = new AntennaPortParams();
		for (int idx = Integer.parseInt(starNum); idx <= Integer
				.parseInt(endNum); idx++) {
			int status = Linkage.getInstance().getAntennaPort(idx,
					antennaPortParams);
			// RfidValue RfidValue = new RfidValue();
			// 获取驻波比
			// int status = Linkage.getInstance().getAntennaSWR(idx, RfidValue);
			if (status == 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("antennaPort", antennaPortParams.getAntennaPort());
				map.put("antennaStatus", antennaPortParams.getAntennaStatus());
				map.put("powerLevel", antennaPortParams.getPowerLevel() / 10.0);
				map.put("dwellTime", antennaPortParams.getDwellTime());
				// 获取驻波比(获取的值/1000,在页面显示)
				map.put("data", "获取天线配置成功");
				map.put("status", "success");
				list.add(map);
			} else {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("data", "获取天线配置失败");
				map.put("status", "failed");
				list.add(map);
				return list;
			}
		}
		return list;
	}

}
