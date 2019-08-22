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

//读写测试
@Controller
public class ReadWriteController {
	@RequestMapping("/epcRead.action")
	@ResponseBody
	public Map<String, Object> epcRead(String epcStartAddress,
			String epcReadLength, String epcPassword) {
		RwDataDetailWith.flag = 0;// 重新赋值
		Map<String, Object> map = new HashMap<String, Object>();

		if (epcPassword.length() == 0) {
			epcPassword = "00000000";
		} else if (epcPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}

		byte[] password = StringUtils.stringToByte(epcPassword);
		int status = Linkage.getInstance().readTag(password, 1,
				Integer.parseInt(epcStartAddress),
				Integer.parseInt(epcReadLength));
		if (status == 0) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(50);// 设置等待回调执行
					System.out.println("flag====" + RwDataDetailWith.flag);
					if (RwDataDetailWith.flag == 1) {// 回调执行了返回数据
						RwData rwData = RwDataDetailWith.rw;
						String result = "";
						String epc = "";
						if (rwData.status == 0) {// 根据回调中返回的数据状态判断是否成功
							if (rwData.rwDataLen > 0) {
								result = StringUtils.byteToHexString(
										rwData.rwData, rwData.rwDataLen);
							}
							if (rwData.epcLen > 0) {
								epc = StringUtils.byteToHexString(rwData.epc,
										rwData.epcLen);
							}
							System.out.println("result====" + result);
							map.put("result", result);
							map.put("epc", epc);
							map.put("status", "epcReadSuccess");
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

	@RequestMapping("/epcWrite.action")
	@ResponseBody
	public Map<String, Object> epcWrite(String epcStartAddress,
			String epcReadLength, String epcPassword, String epcWriteData) {
		System.out.println("接收参数--->> " + epcStartAddress + epcReadLength
				+ epcPassword + epcWriteData);

		Map<String, Object> map = new HashMap<String, Object>();
		RwDataDetailWith.flag = 0;// 重新赋值
		if (epcPassword.length() == 0) {
			epcPassword = "00000000";
		} else if (epcPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}

		byte[] password = StringUtils.stringToByte(epcPassword);
		byte[] writeData = StringUtils.stringToByte(epcWriteData);
		int status = Linkage.getInstance().writeTag(password, 1,
				Integer.parseInt(epcStartAddress),
				Integer.parseInt(epcReadLength), writeData);
		if (status == 0) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(5);
					System.out.println("flag====" + RwDataDetailWith.flag);
					if (RwDataDetailWith.flag == 1) {
						RwData rwData = RwDataDetailWith.rw;
						if (rwData.status == 0) {// 根据回调中返回的状态判断是否成功修改
							String epc = "";
							if (rwData.epcLen > 0) {
								epc = StringUtils.byteToHexString(rwData.epc,
										rwData.epcLen);
							}
							map.put("epc", epc);
							map.put("status", "epcWriteSuccess");
							map.put("data", "写入成功");
							return map;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		map.put("status", "failed");
		map.put("data", "写入失败");
		return map;
	}

	@RequestMapping("/epcReadSync.action")
	@ResponseBody
	public Map<String, Object> epcReadSync(String epcStartAddress,
			String epcReadLength, String epcPassword) {
		RwDataDetailWith.flag = 0;// 重新赋值
		Map<String, Object> map = new HashMap<String, Object>();

		if (epcPassword.length() == 0) {
			epcPassword = "00000000";
		} else if (epcPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}

		byte[] password = StringUtils.stringToByte(epcPassword);
		RwData rwData = new RwData();
		int status = Linkage.getInstance().readTagSync(password, 1,
				Integer.parseInt(epcStartAddress),
				Integer.parseInt(epcReadLength), 500, rwData);

		if (status == 0) {
			if (rwData.status == 0) {
				String result = "";
				String epc = "";
				if (rwData.rwDataLen > 0) {
					result = StringUtils.byteToHexString(rwData.rwData,
							rwData.rwDataLen);
				}
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				System.out.println("result====" + result);
				map.put("result", result);
				map.put("epc", epc);
				map.put("status", "epcReadSuccess");
				map.put("data", "读取成功");
				return map;
			}
		}
		map.put("status", "failed");
		map.put("data", "读取失败");
		return map;
	}

	@RequestMapping("/epcWriteSync.action")
	@ResponseBody
	public Map<String, Object> epcWriteSync(String epcStartAddress,
			String epcReadLength, String epcPassword, String epcWriteData) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (epcPassword.length() == 0) {
			epcPassword = "00000000";
		} else if (epcPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		byte[] password = StringUtils.stringToByte(epcPassword);
		byte[] writeData = StringUtils.stringToByte(epcWriteData);
		RwData rwData = new RwData();
		int status = Linkage.getInstance().writeTagSync(password, 1,
				Integer.parseInt(epcStartAddress),
				Integer.parseInt(epcReadLength), writeData, 500, rwData);
		if (status == 0) {
			if (rwData.status == 0) {
				String epc = "";
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				map.put("epc", epc);
				map.put("status", "epcWriteSuccess");
				map.put("data", "写入成功");
				return map;
			}
		}
		map.put("status", "failed");
		map.put("data", "写入失败");
		return map;
	}

	@RequestMapping("/tidRead.action")
	@ResponseBody
	public Map<String, Object> tidRead(String tidStartAddress,
			String tidReadLength, String tidPassword) {
		RwDataDetailWith.flag = 0;// 重新赋值
		Map<String, Object> map = new HashMap<String, Object>();
		if (tidPassword.length() == 0) {
			tidPassword = "00000000";
		} else if (tidPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}

		byte[] password = StringUtils.stringToByte(tidPassword);
		int status = Linkage.getInstance().readTag(password, 2,
				Integer.parseInt(tidStartAddress),
				Integer.parseInt(tidReadLength));
		if (status == 0) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(50);// 设置等待回调执行
					System.out.println("flag====" + RwDataDetailWith.flag);
					if (RwDataDetailWith.flag == 1) {// 回调执行了返回数据
						RwData rwData = RwDataDetailWith.rw;
						String result = "";
						String epc = "";
						if (rwData.status == 0) {
							if (rwData.rwDataLen > 0) {
								result = StringUtils.byteToHexString(
										rwData.rwData, rwData.rwDataLen);
							}
							if (rwData.epcLen > 0) {
								epc = StringUtils.byteToHexString(rwData.epc,
										rwData.epcLen);
							}
							map.put("result", result);
							map.put("epc", epc);
							map.put("status", "tidReadSuccess");
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

	@RequestMapping("/tidReadSync.action")
	@ResponseBody
	public Map<String, Object> tidReadSync(String tidStartAddress,
			String tidReadLength, String tidPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (tidPassword.length() == 0) {
			tidPassword = "00000000";
		} else if (tidPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		RwData rwData = new RwData();
		byte[] password = StringUtils.stringToByte(tidPassword);
		int status = Linkage.getInstance().readTagSync(password, 2,
				Integer.parseInt(tidStartAddress),
				Integer.parseInt(tidReadLength), 500, rwData);
		if (status == 0) {
			String result = "";
			String epc = "";
			if (rwData.status == 0) {
				if (rwData.rwDataLen > 0) {
					result = StringUtils.byteToHexString(rwData.rwData,
							rwData.rwDataLen);
				}
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				map.put("result", result);
				map.put("epc", epc);
				map.put("status", "tidReadSuccess");
				map.put("data", "读取成功");
				return map;
			}
		}
		map.put("status", "failed");
		map.put("data", "读取失败");
		return map;
	}

	@RequestMapping("/userRead.action")
	@ResponseBody
	public Map<String, Object> userRead(String userStartAddress,
			String userReadLength, String userPassword) {
		System.out.println("接收参数--->> " + userStartAddress + userReadLength
				+ userPassword);

		RwDataDetailWith.flag = 0;// 重新赋值
		Map<String, Object> map = new HashMap<String, Object>();

		if (userPassword.length() == 0) {
			userPassword = "00000000";
		} else if (userPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}

		byte[] password = StringUtils.stringToByte(userPassword);
		int status = Linkage.getInstance().readTag(password, 3,
				Integer.parseInt(userStartAddress),
				Integer.parseInt(userReadLength));
		if (status == 0) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(50);// 设置等待回调执行
					System.out.println("flag====" + RwDataDetailWith.flag);
					if (RwDataDetailWith.flag == 1) {// 回调执行了返回数据
						RwData rwData = RwDataDetailWith.rw;
						String result = "";
						String epc = "";
						if (rwData.status == 0) {
							if (rwData.rwDataLen > 0) {
								result = StringUtils.byteToHexString(
										rwData.rwData, rwData.rwDataLen);
							}
							if (rwData.epcLen > 0) {
								epc = StringUtils.byteToHexString(rwData.epc,
										rwData.epcLen);
							}
							System.out.println("result====" + result);
							map.put("result", result);
							map.put("epc", epc);
							map.put("status", "userReadSuccess");
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

	@RequestMapping("/userWrite.action")
	@ResponseBody
	public Map<String, Object> userWrite(String userStartAddress,
			String userReadLength, String userPassword, String userWriteData) {
		System.out.println("接收参数--->> " + userStartAddress + userReadLength
				+ userPassword + userWriteData);
		Map<String, Object> map = new HashMap<String, Object>();
		if (userPassword.length() == 0) {
			userPassword = "00000000";
		} else if (userPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		RwDataDetailWith.flag = 0;// 重新赋值
		byte[] password = StringUtils.stringToByte(userPassword);
		byte[] writeData = StringUtils.stringToByte(userWriteData);

		int status = Linkage.getInstance().writeTag(password, 3,
				Integer.parseInt(userStartAddress),
				Integer.parseInt(userReadLength), writeData);
		if (status == 0) {
			for (int i = 0; i < 100; i++) {
				try {
					Thread.sleep(5);
					System.out.println("flag====" + RwDataDetailWith.flag);
					if (RwDataDetailWith.flag == 1) {
						RwData rwData = RwDataDetailWith.rw;
						if (rwData.status == 0) {// 根据回调中返回的状态判断是否成功修改
							String epc = "";
							if (rwData.epcLen > 0) {
								epc = StringUtils.byteToHexString(rwData.epc,
										rwData.epcLen);
							}
							map.put("epc", epc);
							map.put("status", "userWriteSuccess");
							map.put("data", "写入成功");
							return map;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		map.put("status", "failed");
		map.put("data", "写入失败");
		return map;
	}

	@RequestMapping("/userReadSync.action")
	@ResponseBody
	public Map<String, Object> userReadSync(String userStartAddress,
			String userReadLength, String userPassword) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userPassword.length() == 0) {
			userPassword = "00000000";
		} else if (userPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		RwData rwData = new RwData();
		byte[] password = StringUtils.stringToByte(userPassword);
		int status = Linkage.getInstance().readTagSync(password, 3,
				Integer.parseInt(userStartAddress),
				Integer.parseInt(userReadLength), 500, rwData);
		if (status == 0) {
			String result = "";
			String epc = "";
			if (rwData.status == 0) {
				if (rwData.rwDataLen > 0) {
					result = StringUtils.byteToHexString(rwData.rwData,
							rwData.rwDataLen);
				}
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				map.put("result", result);
				map.put("epc", epc);
				map.put("status", "userReadSuccess");
				map.put("data", "读取成功");
				return map;
			}
		}
		map.put("status", "failed");
		map.put("data", "读取失败");
		return map;
	}

	@RequestMapping("/userWriteSync.action")
	@ResponseBody
	public Map<String, Object> userWriteSync(String userStartAddress,
			String userReadLength, String userPassword, String userWriteData) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userPassword.length() == 0) {
			userPassword = "00000000";
		} else if (userPassword.length() != 8) {
			map.put("status", "failed");
			map.put("data", "密码格式不对");
			return map;
		}
		byte[] password = StringUtils.stringToByte(userPassword);
		byte[] writeData = StringUtils.stringToByte(userWriteData);
		RwData rwData = new RwData();
		int status = Linkage.getInstance().writeTagSync(password, 3,
				Integer.parseInt(userStartAddress),
				Integer.parseInt(userReadLength), writeData, 500, rwData);
		if (status == 0) {
			if (rwData.status == 0) {
				String epc = "";
				if (rwData.epcLen > 0) {
					epc = StringUtils
							.byteToHexString(rwData.epc, rwData.epcLen);
				}
				map.put("epc", epc);
				map.put("status", "userWriteSuccess");
				map.put("data", "写入成功");
				return map;
			}
		}
		map.put("status", "failed");
		map.put("data", "写入失败");
		return map;
	}
}
