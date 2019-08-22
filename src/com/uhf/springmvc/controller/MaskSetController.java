package com.uhf.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.linkage.Linkage;
import com.uhf.springmvc.entity.StringUtils;
import com.uhf.structures.PostSingulationMatchCriteria;
import com.uhf.structures.SelectCriteria;

@Controller
public class MaskSetController {
	// 获取掩码
	@RequestMapping("/maskGet.action")
	@ResponseBody
	public Map<String, Object> maskGet(String maskStart) {
		Map<String, Object> map = new HashMap<String, Object>();

		PostSingulationMatchCriteria postSingulationMatchCriteria = new PostSingulationMatchCriteria();
		int status = Linkage.getInstance().getPostSingulationMatchCriteria(
				postSingulationMatchCriteria);
		if (status == 0) {
			map.put("maskStart", postSingulationMatchCriteria.status);
			map.put("maskOffset", postSingulationMatchCriteria.maskOffset);
			// 16进制转换为字符串
			String mask = StringUtils.byteToHexString(
					postSingulationMatchCriteria.mask,
					postSingulationMatchCriteria.maskCount / 8);
			map.put("mask", mask);
			map.put("status", "getSuccess");
			map.put("data", "获取掩码成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "获取掩码失败");
		return map;
	}

	// 设置掩码
	@RequestMapping("/maskSet.action")
	@ResponseBody
	public Map<String, Object> maskSet(String maskStart, String startOffset,
			String maskValue) {
		System.out.println("接收参数--->> " + maskStart + startOffset + maskValue);
		// 需要对长度进行校验，能被2整除 。。。。 status选中时为1，否则为0
		// 设置时可以选启用也可以选不启用。。获取时根据这个值进行勾选和不勾选
		Map<String, Object> map = new HashMap<String, Object>();
		if (Integer.parseInt(maskStart) == 1) {// 掩码设置中点了启用 mask值需要输入
			if (maskValue.length() == 0) {
				map.put("status", "failed");
				map.put("data", "掩码不能为空");
				return map;
			}
			if (maskValue.length() % 2 != 0) {
				map.put("status", "failed");
				map.put("data", "掩码长度错误");
				return map;
			}
		}

		byte[] mask = StringUtils.stringToByte(maskValue);// 转为16进制 字节
		int maskCount = maskValue.length() * 4;
		PostSingulationMatchCriteria postSingulationMatchCriteria = new PostSingulationMatchCriteria();
		postSingulationMatchCriteria.setValue(Integer.parseInt(maskStart),
				Integer.parseInt(startOffset), maskCount, mask);
		int status = Linkage.getInstance().setPostSingulationMatchCriteria(
				postSingulationMatchCriteria);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "设置掩码成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "设置掩码失败");
		return map;
	}

	@RequestMapping("/criteriaGet.action")
	@ResponseBody
	public Map<String, Object> criteriaGet(String criteriaStart) {
		System.out.println("接收参数--->> " + criteriaStart);
		Map<String, Object> map = new HashMap<String, Object>();

		SelectCriteria selectCriteria = new SelectCriteria(
				(Integer.parseInt(criteriaStart)));
		int status = Linkage.getInstance().get18K6CSelectCriteria(0,
				selectCriteria);
		if (status == 0) {
			map.put("criteriaStart", selectCriteria.status);
			map.put("memBank", selectCriteria.memBank);
			map.put("target", selectCriteria.target);
			map.put("action", selectCriteria.action);
			map.put("enableTruncate", selectCriteria.enableTruncate);
			map.put("maskOffset", selectCriteria.maskOffset);
			map.put("maskCount", selectCriteria.maskCount / 8);
			// 16进制转换为字符串
			String mask = StringUtils.byteToHexString(selectCriteria.mask,
					selectCriteria.maskCount / 8);
			map.put("mask", mask);
			map.put("status", "getCriteriaSuccess");
			map.put("data", "获取掩码成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "获取掩码失败");
		return map;
	}

	@RequestMapping("/criteriaSet.action")
	@ResponseBody
	public Map<String, Object> criteriaSet(String criteriaStart,
			String criteriaArea, String criteriaSession,
			String criteriaInterception, String criteriaAction,
			String criteriaStartOffset, String criteriaLength,
			String criteriaMaskValue) {
		System.out.println("接收参数--->> " + criteriaStart + criteriaArea
				+ criteriaSession + criteriaInterception + criteriaAction);
		Map<String, Object> map = new HashMap<String, Object>();

		if (Integer.parseInt(criteriaStart) == 1) {// 掩码设置中点了启用 mask值需要输入
			if (criteriaMaskValue.length() == 0) {
				map.put("status", "failed");
				map.put("data", "掩码不能为空");
				return map;
			}
			if (criteriaMaskValue.length() % 2 != 0) {
				map.put("status", "failed");
				map.put("data", "掩码长度错误");
				return map;
			}
		}

		SelectCriteria selectCriteria = new SelectCriteria(
				Integer.parseInt(criteriaStart));
		byte[] mask = StringUtils.stringToByte(criteriaMaskValue);// 转为16进制 字节
		int maskCount = criteriaMaskValue.length() * 4;

		selectCriteria.setValue(Integer.parseInt(criteriaStart),
				Integer.parseInt(criteriaArea),
				Integer.parseInt(criteriaSession),
				Integer.parseInt(criteriaAction),
				Integer.parseInt(criteriaInterception),
				Integer.parseInt(criteriaStartOffset), maskCount, mask);

		int status = Linkage.getInstance().set18K6CSelectCriteria(0,
				selectCriteria);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "设置掩码成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "设置掩码失败");
		return map;
	}
}
