package com.uhf.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.detailwith.InventoryDetailWith;
import com.uhf.linkage.Linkage;
import com.uhf.springmvc.entity.StringUtils;
import com.uhf.structures.InventoryArea;
import com.uhf.structures.InventoryFilterThreshold;

//盘点测试
@Controller
public class InventoryController {
	@RequestMapping("/startInventory.action")
	@ResponseBody
	public String startInventory(String ModuleCheck, String area,
			String startAddr, String wordLen) {
		System.out.println(ModuleCheck + area + startAddr + wordLen);
		InventoryArea inventory = new InventoryArea();
		inventory.setValue(Integer.parseInt(area), Integer.parseInt(startAddr),
				Integer.parseInt(wordLen));
		Linkage.getInstance().setInventoryArea(inventory);
		InventoryDetailWith.tagCount = 0;
		Linkage.getInstance().startInventory(2, 0);
		InventoryDetailWith.startTime = System.currentTimeMillis();
		return "success";
	}

	@RequestMapping("/stopInventory.action")
	@ResponseBody
	public String stopInventory() {
		System.out.println("接收参数--->> " + "stopInventory");
		Linkage.getInstance().stopInventory();
		return "stop";
	}

	@RequestMapping("/getInventory.action")
	@ResponseBody
	public List<Map<String, Object>> getInventory() {

		/*
		 * long m_lEndTime = System.currentTimeMillis(); double Rate =
		 * Math.ceil((InventoryDetailWith.tagCount * 1.0) * 1000 / (m_lEndTime -
		 * InventoryDetailWith.startTime)); long total_time = m_lEndTime -
		 * InventoryDetailWith.startTime; int tag =
		 * InventoryDetailWith.list.size(); System.out.println(tag);
		 */
		return InventoryDetailWith.list;

	}

	@RequestMapping("/getTagData.action")
	@ResponseBody
	public List<Map<String, Object>> getTagData() {
		List<Map<String, Object>> list = new ArrayList<>();
		/*long m_lEndTime = System.currentTimeMillis();
		double Rate = Math.ceil((InventoryDetailWith.tagCount * 1.0) * 1000
				/ (m_lEndTime - InventoryDetailWith.startTime));

		long total_time = m_lEndTime - InventoryDetailWith.startTime;
		String dateStr = StringUtils.getTimeFromMillisecond(total_time);
		int tag = InventoryDetailWith.list.size();

		Map<String, Object> map = new HashMap<>();
		map.put("tagCount", InventoryDetailWith.tagCount);// 获取个数
		map.put("Rate", Rate);// 盘点速度
		if (tag != 0) {
			map.put("dateStr", dateStr);// 盘点时间
		} else {
			map.put("dateStr", "0时0分0秒0毫秒");// 盘点时间
		}
		map.put("tag", tag);// 标签个数
		list.add(map);*/
		return list;
	}

	// 清空
	@RequestMapping("/clearInventory.action")
	@ResponseBody
	public void clearInventory() {
		if (InventoryDetailWith.list != null) {
			InventoryDetailWith.list.clear();
		}
		if (InventoryDetailWith.inventoryMap != null) {
			InventoryDetailWith.inventoryMap.clear();
		}
		InventoryDetailWith.tagCount = 0;
		InventoryDetailWith.startTime = System.currentTimeMillis();
	}

	// 盘点区域获取
	@RequestMapping("/getInventoryArea.action")
	@ResponseBody
	public Map<String, Object> getInventoryArea(String area, String startAddr,
			String wordLen) {
		System.out.println(area + startAddr + wordLen);
		Map<String, Object> map = new HashMap<String, Object>();
		InventoryArea inventoryArea = new InventoryArea();
		int status = Linkage.getInstance().getInventoryArea(inventoryArea);
		if (status == 0) {
			map.put("area", inventoryArea.area);
			map.put("startAddr", inventoryArea.startAddr);
			map.put("wordLen", inventoryArea.wordLen);
			map.put("status", "getInventoryAreaSuccess");
			map.put("data", "盘点区域获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "盘点区域获取失败");
		return map;

	}

	// 盘点区域设置
	@RequestMapping("/setInventoryArea.action")
	@ResponseBody
	public Map<String, Object> setInventoryArea(String area, String startAddr,
			String wordLen) {
		System.out.println(area + startAddr + wordLen);
		Map<String, Object> map = new HashMap<String, Object>();

		InventoryArea inventoryArea = new InventoryArea();
		inventoryArea.setValue(Integer.parseInt(area),
				Integer.parseInt(startAddr), Integer.parseInt(wordLen));
		int status = Linkage.getInstance().setInventoryArea(inventoryArea);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "盘点区域设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "盘点区域设置失败");
		return map;
	}

	// 过滤 获取
	@RequestMapping("/filterGet.action")
	@ResponseBody
	public Map<String, Object> filterGet(String threshold, String maxRepeatTimes) {
		Map<String, Object> map = new HashMap<String, Object>();
		InventoryFilterThreshold inventoryFilterThreshold = new InventoryFilterThreshold();
		int status = Linkage.getInstance().getInventoryFilterThreshold(
				inventoryFilterThreshold);
		if (status == 0) {
			map.put("threshold", inventoryFilterThreshold.threshold);
			map.put("maxRepeatTimes", inventoryFilterThreshold.maxRepeatTimes);
			map.put("status", "filterGetSuccess");
			map.put("data", "过滤获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "过滤获取失败");
		return map;
	}

	// 过滤设置
	@RequestMapping("/filterSet.action")
	@ResponseBody
	public Map<String, Object> filterSet(String threshold, String maxRepeatTimes) {
		Map<String, Object> map = new HashMap<String, Object>();
		InventoryFilterThreshold inventoryFilterThreshold = new InventoryFilterThreshold();
		inventoryFilterThreshold.setValue(Integer.parseInt(threshold),
				Integer.parseInt(maxRepeatTimes), 0);
		int status = Linkage.getInstance().setInventoryFilterThreshold(
				inventoryFilterThreshold);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "过滤设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "过滤设置失败");
		return map;
	}
}
