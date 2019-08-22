package com.uhf.springmvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.detailwith.RwDataDetailWith;
import com.uhf.linkage.Linkage;
import com.uhf.springmvc.entity.StringUtils;
import com.uhf.structures.RwData;

//权限设置
@Controller
public class PermissionsController {

	@RequestMapping("/killTag.action")
	@ResponseBody
	public Map<String, Object> killTag(String accessPassword,
			String destructionPassword) {
		System.out.println("接收参数--->> " + accessPassword + destructionPassword);
		Map<String, Object> map = new HashMap<String, Object>();
		if (accessPassword.length() != 8 || destructionPassword.length() != 8) {// 需要判断密码是否为8位
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		byte[] password = StringUtils.stringToByte(accessPassword);
		byte[] killPassword = StringUtils.stringToByte(destructionPassword);
		int status = Linkage.getInstance().killTag(password, killPassword);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "销毁成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "销毁失败");
		return map;
	}

	@RequestMapping("/readTag.action")
	@ResponseBody
	public Map<String, Object> readTag(String passwordType, String rePassword,
			String accessPasswords) {

		System.out.println("接收参数--->> " + passwordType + rePassword
				+ accessPasswords);
		RwDataDetailWith.flag = 0;// 重新赋值
		Map<String, Object> map = new HashMap<String, Object>();
		if (rePassword.length() != 8 || accessPasswords.length() != 8) {// 需要判断密码是否为8位
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		byte[] password = StringUtils.stringToByte(accessPasswords);

		int startAddr = Integer.parseInt(passwordType) * 2;
		int status = Linkage.getInstance().readTag(password, 0, startAddr, 2);

		if (status == 0) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(5);// 设置等待回调执行
					System.out.println("flag====" + RwDataDetailWith.flag);
					if (RwDataDetailWith.flag == 1) {// 回调执行了返回数据
						RwData rwData = RwDataDetailWith.rw;
						String result = "";
						if (rwData.status == 0) {
							if (rwData.rwDataLen > 0) {
								result = StringUtils.byteToHexString(
										rwData.rwData, rwData.rwDataLen);
							}
							System.out.println("result====" + result);
							map.put("result", result);
							map.put("status", "readTagSuccess");
							map.put("data", "读取成功");
							return map;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		map.put("status", "failed");
		map.put("data", "读取失败");
		return map;
	}

	// 修改
	@RequestMapping("/writeTag.action")
	@ResponseBody
	public Map<String, Object> writeTag(String rpasswordType,
			String newPassword, String accessPass) {
		System.out.println("接收参数--->> " + rpasswordType + newPassword
				+ accessPass);
		RwDataDetailWith.flag = 0;// 重新赋值
		Map<String, Object> map = new HashMap<String, Object>();
		if (newPassword.length() != 8 || accessPass.length() != 8) {// 需要判断密码是否为8位
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		byte[] accessPassword = StringUtils.stringToByte(accessPass);
		int startAddr = Integer.parseInt(rpasswordType) * 2;
		byte[] password = StringUtils.stringToByte(newPassword);
		int status = Linkage.getInstance().writeTag(accessPassword, 0,
				startAddr, 2, password);
		if (status == 0) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(5);
					System.out.println("flag====" + RwDataDetailWith.flag);
					if (RwDataDetailWith.flag == 1) {
						RwData rwData = RwDataDetailWith.rw;
						if (rwData.status == 0) {// 根据回调中返回的状态判断是否成功修改
							map.put("status", "success");
							map.put("data", "修改成功");
							return map;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		map.put("status", "failed");
		map.put("data", "修改失败");
		return map;
	}

	// 锁定
	@RequestMapping("/lockTag.action")
	@ResponseBody
	public Map<String, Object> lockTag(String killPasswordPermissions,
			String accessPasswordPermissions, String epcMemoryBankPermissions,
			String tidMemoryBankPermissions, String userMemoryBankPermissions,
			String accessPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (accessPassword.length() != 8) {// 需要判断密码是否为8位
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		byte[] password = StringUtils.stringToByte(accessPassword);
		int status = Linkage.getInstance().lockTag(
				Integer.parseInt(killPasswordPermissions),
				Integer.parseInt(accessPasswordPermissions),
				Integer.parseInt(epcMemoryBankPermissions),
				Integer.parseInt(tidMemoryBankPermissions),
				Integer.parseInt(userMemoryBankPermissions), password);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "锁定成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "锁定失败");
		return map;
	}
}
