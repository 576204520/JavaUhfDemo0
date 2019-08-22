package com.uhf.sqlserver.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import com.uhf.detailwith.InventoryDetailWith;
import com.uhf.sqlserver.entity.TblRfidData;

/**
 * 定时任务
 * 
 * @author zyl
 *
 */
public class Task extends TimerTask {

	InventoryDetailWith inWith = new InventoryDetailWith();
	
	TestPing tp = new TestPing();
	
	Map<String, Integer> Names2 = new HashMap<String, Integer>();

	/**
	 * 定时任务主要执行方法--->做无码逻辑判断
	 */
	@Override
	public void run() {
		boolean task = tp.isPing(GreyStartServlet.configurationMap.get("IP"));
		if (task == false && GreyStartServlet.times == false) {
			tp.timeOs();
		}else if(task == true && GreyStartServlet.times == true){
			tp.shutDownTime();
		}
		
		List<Map<String, Object>> 全局 = InventoryDetailWith.list;
		
		System.out.println("==================" + 全局.size() + "===================");
		for (int i = 0; i < 全局.size(); i++) {
			Map<String, Object> map = 全局.get(i);
			TblRfidData obj = retObj(map);
			
			System.out.printf(
				"设备: %d===> 天线: %d===> fid: %s===> 次数: %d ===> 天线: %s", 
				obj.getRFEId(),
				obj.getRFTXId(),
				obj.getRFIdCode(),
				obj.getInputType(),
				map.get("antennaPort")
			);
			
			if(Names2.containsKey(obj.getId())) {
				if(Names2.get(obj.getId()) >= obj.getInputType()) {
					input(obj, 0);
					System.out.println(">>>>>>>>>>>> ============ <<<<<<<<<<");
				} else {
					input(obj, 1);
					System.out.println(">>>>>>>>>>>> OOOOOOOOOOOO <<<<<<<<<<");
				}
			}else {
				input(obj, 1);
			}
			Names2.put(obj.getId(), obj.getInputType());
		}
		System.out.println();
		// Names2.addAll(GreyStartServlet.Names);
		
		/*if (GreyStartServlet.countName != 0 && GreyStartServlet.hardCount != 0) {
			if (GreyStartServlet.countName <= GreyStartServlet.hardCount) {
				List<TblRfidData> inputList = inWith.findByInputType(1);
				boolean ip = tp.isPing(GreyStartServlet.configurationMap.get("IP"));
				if (inputList != null && ip == true) {
					for (TblRfidData tblRfidData : inputList) {
						inWith.updateTblRfidData(0, inWith.getCurrentDateAString(), tblRfidData.getRFEId(),
								tblRfidData.getRFTXId(), tblRfidData.getRFIdCode());
					}
				}
				GreyStartServlet.countName = 0;
				GreyStartServlet.hardCount = 0;
			} else {
				GreyStartServlet.hardCount = GreyStartServlet.countName;
			}
		}
		if (GreyStartServlet.countName != 0 && GreyStartServlet.hardCount == 0) {
			GreyStartServlet.hardCount = GreyStartServlet.countName;
		}*/
	}
	void input(TblRfidData obj, int input) {
		List<TblRfidData> tblRfidDataList = inWith.findByTblRfidData(obj.getRFIdCode(), obj.getRFTXId(), obj.getRFEId());
		if (tblRfidDataList == null || tblRfidDataList.size() == 0) {
			add(obj);
		} else {
			update(obj, input);
		}
	}
	TblRfidData retObj(Map map){
		String rfidcode = map.get("epc").toString(); // fid
		int countName = Integer.parseInt(map.get("count").toString()); // 次数
		Integer antennaPort = Integer.parseInt(map.get("antennaPort").toString()); // 天线端口
		String information = rfidcode + antennaPort; // fid + 天线端口
		String 设备id = GreyStartServlet.configurationMap.get("EQUIPMENT_NAME");
		
		// new TblRfidData(id, parentId, rFEId, rFTXId, rFIdCode, inputType, iSTime, oSTime)
		
		TblRfidData tblRfidData = new TblRfidData(
			information, // id // 暂代fid + 天线端口
			"0", // pid
			Integer.parseInt(设备id), // 设备
			antennaPort, // 天线
			rfidcode, // 编码
			countName, // inputType类型 // 暂时当作 次数
			"0", // 时间
			"0" // 时间
		);
		return tblRfidData;
	}
	void add(TblRfidData tblRfidData) {
		inWith.addTblRfidData( 
			tblRfidData.getRFEId(),
			tblRfidData.getRFTXId(), 
			tblRfidData.getRFIdCode(),
			1, 
			inWith.getCurrentDateAString()
		);
	}
	void update(TblRfidData tblRfidData, int input) {
		if(input == 0) {
			
			inWith.updateTblRfidData(
					input, 
					inWith.getCurrentDateAString(), 
					tblRfidData.getRFEId(),
					tblRfidData.getRFTXId(), 
					tblRfidData.getRFIdCode()
				);			
		}else {
			
			inWith.updateTblRfidData2(
					input, 
					inWith.getCurrentDateAString(), 
					tblRfidData.getRFEId(),
					tblRfidData.getRFTXId(), 
					tblRfidData.getRFIdCode()
				);

		}
	}
}
