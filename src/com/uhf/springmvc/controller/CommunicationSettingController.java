package com.uhf.springmvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uhf.linkage.Linkage;
import com.uhf.springmvc.entity.PageResult;
import com.uhf.sqlserver.entity.SqlServer;
import com.uhf.sqlserver.service.SqlServerService;
import com.uhf.sqlserver.utils.OperatParam;
import com.uhf.structures.DynamicQParams;
import com.uhf.structures.FixedQParameters;
import com.uhf.structures.ProtschTxtime;
import com.uhf.structures.QueryTagGroup;
import com.uhf.structures.RfidValue;

//通讯设置
@Controller
public class CommunicationSettingController {

	@Resource
	private OperatParam operatParam;
	
	@Resource(name="sqlServerService")
	private SqlServerService sqlServerServices;
	
	@RequestMapping("/setCurrentProfile.action")
	@ResponseBody
	public PageResult setCurrentProfile(String link) {
		System.out.println("接收参数--->> " + link);
		PageResult pageResult = new PageResult();

		int status = Linkage.getInstance().setCurrentProfile(
				Integer.parseInt(link));
		// return (status == 0) ? "success" : "Failed, status:" + status;
		if (status == 0) {
			pageResult.setStatus("setSuccess");
			pageResult.setResultDate("链接设置成功");
			return pageResult;
		}
		pageResult.setStatus("field");
		pageResult.setResultDate("链接设置失败");

		return pageResult;
	}

	@RequestMapping("/getCurrentProfile.action")
	@ResponseBody
	public PageResult getCurrentProfile(String link) {
		System.out.println("接收参数--->> " + link);
		/*List<SqlServer> list = sqlServerServices.list();
		for (SqlServer sqlServer : list) {
			System.out.println(sqlServer.getMenusname());
			System.out.println("连接了");
		}*/
		/*String key = operatParam.get("KEY");
		System.out.println(key);
		String CONNECT_TIME_OUT = operatParam.get("CONNECT_TIME_OUT");//连接超时时间
		System.out.println(CONNECT_TIME_OUT);*/
		PageResult pageResult = new PageResult();

		RfidValue rfidValue = new RfidValue();
		rfidValue.setValue(Integer.parseInt(link));
		int status = Linkage.getInstance().getCurrentProfile(rfidValue);
		if (status == 0) {
			int value = rfidValue.value;
			pageResult.setStatus("getSuccess");
			pageResult.setValue(String.valueOf(value));
			pageResult.setResultDate("链接获取成功");
			return pageResult;
		}
		pageResult.setStatus("field");
		pageResult.setResultDate("链接获取失败");

		return pageResult;
	}

	// 区域设置
	@RequestMapping("/setRegion.action")
	@ResponseBody
	public PageResult setRegion(String region) {
		System.out.println("接收参数--->> " + region);

		PageResult pageResult = new PageResult();
		int status = Linkage.getInstance().setRegion(Integer.parseInt(region));
		if (status == 0) {
			pageResult.setStatus("setSuccess");
			pageResult.setResultDate("区域设置成功");
			return pageResult;
		}
		pageResult.setStatus("field");
		pageResult.setResultDate("区域设置失败");

		return pageResult;
	}

	@RequestMapping("/getRegion.action")
	@ResponseBody
	public PageResult getRegion(String region) {
		PageResult pageResult = new PageResult();
		RfidValue rfidValue = new RfidValue();
		rfidValue.setValue(Integer.parseInt(region));
		int status = Linkage.getInstance().getRegion(rfidValue);
		if (status == 0) {
			int value = rfidValue.value;
			pageResult.setStatus("getSuccess");
			pageResult.setValue(String.valueOf(value));
			pageResult.setResultDate("区域获取成功");
			return pageResult;
		}
		pageResult.setStatus("field");
		pageResult.setResultDate("区域获取失败");

		return pageResult;
	}

	// 跳频时间 获取
	@RequestMapping("/getProtschTxtime.action")
	@ResponseBody
	public Map<String, Object> getProtschTxtime(String txOn, String txOff) {
		Map<String, Object> map = new HashMap<String, Object>();

		ProtschTxtime protschTxtime = new ProtschTxtime();
		protschTxtime.setValue(Integer.parseInt(txOn), Integer.parseInt(txOff));
		int status = Linkage.getInstance().getProtschTxtime(protschTxtime);
		if (status == 0) {
			map.put("txOn", protschTxtime.txOn);
			map.put("txOff", protschTxtime.txOff);
			map.put("status", "getSuccess");
			map.put("data", "跳频时间获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "跳频时间获取失败");
		return map;
	}

	// 跳频时间 设置
	@RequestMapping("/setProtschTxtime.action")
	@ResponseBody
	public Map<String, Object> setProtschTxtime(String txOn, String txOff) {
		Map<String, Object> map = new HashMap<String, Object>();
		ProtschTxtime protschTxtime = new ProtschTxtime();
		protschTxtime.setValue(Integer.parseInt(txOn), Integer.parseInt(txOff));
		int status = Linkage.getInstance().setProtschTxtime(protschTxtime);

		if (status == 0) {
			map.put("status", "success");
			map.put("data", "跳频时间设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "跳频时间设置失败");
		return map;
	}

	@RequestMapping("/setFixFreq.action")
	@ResponseBody
	public PageResult setFixFreq(String frequency) {
		System.out.println("接收参数--->> " + frequency);
		PageResult pageResult = new PageResult();
		/*
		 * double fdouble = Double.parseDouble(frequency); int i = (int)
		 * fdouble; System.out.println(i);
		 */
		int freq = (int) (Double.parseDouble(frequency) * 1000.0);
		int status = Linkage.getInstance().setFixFreq(freq);

		if (status == 0) {
			pageResult.setStatus("setSuccess");
			pageResult.setResultDate("频点设置成功");
			return pageResult;
		}
		pageResult.setStatus("field");
		pageResult.setResultDate("频点设置失败");

		return pageResult;
	}

	// 频点获取
	@RequestMapping("/getFixFreq.action")
	@ResponseBody
	public PageResult getFixFreq(String frequency) {
		System.out.println("接收参数--->> " + frequency);
		PageResult pageResult = new PageResult();

		RfidValue rfidValue = new RfidValue();
		/*
		 * int freq = (int) (Double.parseDouble(frequency) * 1000);
		 * 
		 * rfidValue.setValue(freq);
		 */
		int status = Linkage.getInstance().getFixFreq(rfidValue);
		if (status == 0) {
			pageResult.setStatus("getSuccess");
			pageResult.setValue(Double.toString(rfidValue.value / 1000.0));
			pageResult.setResultDate("频点获取成功");
			return pageResult;
		}
		pageResult.setStatus("field");
		pageResult.setResultDate("频点获取失败");

		return pageResult;
	}

	@RequestMapping("/setFixedAlgorithmic.action")
	@ResponseBody
	public Map<String, Object> setFixedAlgorithmic(String algorithm,
			String flip, String retryCount, String qValue, String isRepeat) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 固定算法
		FixedQParameters fixedQParameters = new FixedQParameters();
		fixedQParameters.setValue(Integer.parseInt(qValue),
				Integer.parseInt(retryCount), Integer.parseInt(flip),
				Integer.parseInt(isRepeat));
		int status = Linkage.getInstance().setSingulationFixedQParameters(
				fixedQParameters);

		if (status == 0) {
			map.put("status", "success");
			map.put("data", "固定算法设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "固定算法设置失败");
		return map;
	}

	@RequestMapping("/setDynamicAlgorithm.action")
	@ResponseBody
	public Map<String, Object> setDynamicAlgorithm(String algorithm,
			String flip, String retryCount, String startingQValue, String minQ,
			String maxQ, String threshold) {
		System.out.println("接收参数--->> " + algorithm + flip + retryCount
				+ startingQValue + minQ + maxQ + threshold);
		Map<String, Object> map = new HashMap<String, Object>();

		// 动态算法
		DynamicQParams dynamicQParams = new DynamicQParams();
		dynamicQParams.setValue(Integer.parseInt(startingQValue),
				Integer.parseInt(minQ), Integer.parseInt(maxQ),
				Integer.parseInt(retryCount), Integer.parseInt(flip),
				Integer.parseInt(threshold));
		int status = Linkage.getInstance().setSingulationDynamicQParameters(
				dynamicQParams);

		if (status == 0) {
			map.put("status", "success");
			map.put("data", "动态算法设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "动态算法设置失败");
		return map;
	}

	// 算法获取
	@RequestMapping("/getAlgorithm.action")
	@ResponseBody
	public Map<String, Object> getAlgorithm() {
		Map<String, Object> map = new HashMap<String, Object>();
		RfidValue rfidValue = new RfidValue();
		if (Linkage.getInstance().getCurrentSingulationAlgorithm(rfidValue) == 0) {
			if (rfidValue.value == 0) {
				// 固定算法
				FixedQParameters fixedQParameters = new FixedQParameters();

				int status = Linkage.getInstance()
						.getSingulationFixedQParameters(fixedQParameters);
				if (status == 0) {
					map.put("value", 0);
					map.put("toggleTarget", fixedQParameters.toggleTarget);
					map.put("retryCount", fixedQParameters.retryCount);
					map.put("qValue", fixedQParameters.qValue);
					map.put("repeatUntiNoTags",
							fixedQParameters.repeatUntiNoTags);
					map.put("status", "getAlgorithmSuccess");
					map.put("data", "固定算法获取成功");
					return map;
				}
				map.put("status", "failed");
				map.put("data", "固定算法获取失败");
				return map;
			} else {
				// 动态算法
				DynamicQParams dynamicQParams = new DynamicQParams();
				int status = Linkage.getInstance()
						.getSingulationDynamicQParameters(dynamicQParams);
				if (status == 0) {
					map.put("value", 1);
					map.put("startQValue", dynamicQParams.startQValue);
					map.put("minQValue", dynamicQParams.minQValue);
					map.put("maxQValue", dynamicQParams.maxQValue);
					map.put("retryCount", dynamicQParams.retryCount);
					map.put("toggleTarget", dynamicQParams.toggleTarget);
					map.put("thresholdMultiplier",
							dynamicQParams.thresholdMultiplier);
					map.put("status", "getAlgorithmSuccess");
					map.put("data", "动态算法获取成功");
					return map;
				}
				map.put("status", "failed");
				map.put("data", "动态算法获取失败");
				return map;
			}
		}
		map.put("status", "failed");
		map.put("data", "算法获取失败");
		return map;
	}

	@RequestMapping("/getQuery.action")
	@ResponseBody
	public Map<String, Object> getQuery(String queryParameter, String session,
			String selectAll) {
		System.out.println("接收参数--->> " + queryParameter + session + selectAll);
		Map<String, Object> map = new HashMap<String, Object>();

		QueryTagGroup queryTagGroup = new QueryTagGroup();
		int status = Linkage.getInstance().get18K6CQueryTagGroup(queryTagGroup);
		if (status == 0) {
			map.put("target", queryTagGroup.target);
			map.put("session", queryTagGroup.session);
			map.put("selected", queryTagGroup.selected);

			map.put("status", "getSuccess");
			map.put("data", "Query参数获取成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "Query参数获取失败");
		return map;
	}

	@RequestMapping("/setQuery.action")
	@ResponseBody
	public Map<String, Object> setQuery(String queryParameter, String session,
			String selectAll) {
		System.out.println("接收参数--->> " + queryParameter + session + selectAll);
		QueryTagGroup queryTagGroup = new QueryTagGroup();
		Map<String, Object> map = new HashMap<String, Object>();
		queryTagGroup.setValue(Integer.parseInt(selectAll),
				Integer.parseInt(session), Integer.parseInt(queryParameter));
		int status = Linkage.getInstance().set18K6CQueryTagGroup(queryTagGroup);
		if (status == 0) {
			map.put("status", "success");
			map.put("data", "Query参数设置成功");
			return map;
		}
		map.put("status", "failed");
		map.put("data", "Query参数设置失败");
		return map;
	}
	
}
