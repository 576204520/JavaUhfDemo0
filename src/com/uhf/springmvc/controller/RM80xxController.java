package com.uhf.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.linkage.Linkage;
import com.uhf.structures.AntennaPortParams;
import com.uhf.structures.RfidValue;
import com.uhf.structures.Rm8011Query;

//参数设置
@Controller
public class RM80xxController {
	// 工作模式
	@RequestMapping("/workModeSet.action")
	@ResponseBody
	public Map<String, Object> workModeSet(String workMode) {
		System.out.println("接收参数--->> " + workMode);
		Map<String, Object> map = new HashMap<String, Object>();
		int status = Linkage.getInstance().setWorkMode(
				Integer.parseInt(workMode));
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "工作模式设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "工作模式设置失败");
		return map;
	}

	// 功率
	@RequestMapping("/powerGet.action")
	@ResponseBody
	public Map<String, Object> powerGet() {
		Map<String, Object> map = new HashMap<String, Object>();
		AntennaPortParams antennaPortParams = new AntennaPortParams();
		int status = Linkage.getInstance().getPowerLevel(0, antennaPortParams);
		if (status == 0) {
			map.put("powerLevel", antennaPortParams.powerLevel / 10);
			map.put("status", "powerGetSuccess");
			map.put("data", "功率获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "功率获取失败");
		return map;
	}

	// 功率
	@RequestMapping("/powerSet.action")
	@ResponseBody
	public Map<String, Object> powerSet(String power, String antennaPort) {
		System.out.println("接收参数--->> " + power + antennaPort);
		Map<String, Object> map = new HashMap<String, Object>();
		AntennaPortParams antennaPortParams = new AntennaPortParams();
		// 传选中的文本？
		antennaPortParams.powerLevel = Integer.parseInt(power) * 10;
		int status = Linkage.getInstance().setPowerLevel(0, antennaPortParams);

		if (status == 0) {
			map.put("status", "success");
			map.put("data", "功率设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "功率设置失败");
		return map;
	}

	// 区域
	@RequestMapping("/areaGet.action")
	@ResponseBody
	public Map<String, Object> areaGet() {
		Map<String, Object> map = new HashMap<String, Object>();
		RfidValue rfidValue = new RfidValue();
		int status = Linkage.getInstance().getRegion(rfidValue);
		if (status == 0) {
			/*
			 * China840_845(4), China920_925(1), US902_928(2), Europe865_868(3),
			 * Korea917_923( 6);
			 */
			int value = 0;
			switch (rfidValue.value) {
			case 1:
				value = 0;
				break;
			case 4:
				value = 1;
				break;
			case 2:
				value = 2;
				break;
			case 3:
				value = 3;
				break;
			case 6:
				value = 4;
				break;
			}
			map.put("value", value);
			map.put("status", "areaGetSuccess");
			map.put("data", "区域获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "区域获取失败");
		return map;
	}

	// 区域
	@RequestMapping("/areaSet.action")
	@ResponseBody
	public Map<String, Object> areaSet(String range) {
		System.out.println("接收参数--->> " + range);
		Map<String, Object> map = new HashMap<String, Object>();
		/*
		 * China840_845(4), China920_925(1), US902_928(2), Europe865_868(3),
		 * Korea917_923( 6);
		 */
		int rangion = 0;
		switch (Integer.parseInt(range)) {
		case 0:
			rangion = 1;
			break;
		case 1:
			rangion = 4;
			break;
		case 2:
			rangion = 2;
			break;
		case 3:
			rangion = 3;
			break;
		case 4:
			rangion = 6;
			break;
		}

		int status = Linkage.getInstance().setRegion(rangion);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "区域设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "区域设置失败");
		return map;
	}

	// 频点
	@RequestMapping("/frequencyGet.action")
	@ResponseBody
	public Map<String, Object> frequencyGet(String paramFrequency) {
		System.out.println("接收参数--->> " + paramFrequency);
		Map<String, Object> map = new HashMap<String, Object>();
		RfidValue rfidValue = new RfidValue();

		int status = Linkage.getInstance().getFixFreq(rfidValue);
		if (status == 0) {
			map.put("value", rfidValue.value / 1000.000);
			map.put("status", "frequencyGetSuccess");
			map.put("data", "频点获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "频点获取失败");
		return map;
	}

	// 频点
	@RequestMapping("/frequencySet.action")
	@ResponseBody
	public Map<String, Object> frequencySet(String paramFrequency) {
		System.out.println("接收参数--->> " + paramFrequency);

		Map<String, Object> map = new HashMap<String, Object>();
		// Linkage.getInstance().setFixFreq(920.125 * 1000);
		int freq = (int) (Double.parseDouble(paramFrequency) * 1000);
		int status = Linkage.getInstance().setFixFreq(freq);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "频点设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "频点设置失败");
		return map;
	}

	// Query参数
	@RequestMapping("/queryParamGet.action")
	@ResponseBody
	public Map<String, Object> queryParamGet(String DRSelect, String MSelect,
			String TRextSelect, String SelSelect, String SessionSelect,
			String TargetSelect, String QInput) {
		// System.out.println("接收参数--->> " + workMode);
		Map<String, Object> map = new HashMap<String, Object>();
		Rm8011Query rm8011Query = new Rm8011Query();
		int status = Linkage.getInstance().getQuery(rm8011Query);
		if (status == 0) {
			map.put("DR", rm8011Query.DR);
			map.put("M", rm8011Query.M);
			map.put("TRext", rm8011Query.TRext);
			System.out.println("rm8011Query.TRext" + rm8011Query.TRext);
			map.put("Sel", rm8011Query.Sel);
			map.put("Session", rm8011Query.Session);
			map.put("Target", rm8011Query.Target);
			map.put("Q", rm8011Query.Q);
			map.put("status", "getSuccess");
			map.put("data", "获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "获取失败");
		return map;
	}

	// Query参数
	@RequestMapping("/queryParamSet.action")
	@ResponseBody
	public Map<String, Object> queryParamSet(String DR, String M, String TRext,
			String Sel, String Session, String Target, String Q) {
		System.out.println(DR + M + TRext + Sel + Session + Target + Q);
		Map<String, Object> map = new HashMap<String, Object>();
		Rm8011Query rm8011Query = new Rm8011Query();
		rm8011Query.setValue(Integer.parseInt(DR), Integer.parseInt(M),
				Integer.parseInt(TRext), Integer.parseInt(Sel),
				Integer.parseInt(Session), Integer.parseInt(Target),
				Integer.parseInt(Q));
		int status = Linkage.getInstance().setQuery(rm8011Query);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "设置失败");
		return map;
	}
}
