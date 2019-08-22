package com.uhf.sqlserver.utils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.uhf.detailwith.InventoryDetailWith;
import com.uhf.linkage.Linkage;
import com.uhf.springmvc.controller.InitModelController;
import com.uhf.springmvc.controller.InventoryController;
import com.uhf.sqlserver.entity.TblRfidData;

/**
 * 定时任务
 * 
 * @author zyl
 *
 */
public class TaskIp extends TimerTask {

	TestPing tp = new TestPing();
	
	InitModelController arr = new InitModelController();
	
	InventoryController inven = new InventoryController();

	/**
	 * 定时判断是否连接RFID机器
	 */
	@Override
	public void run() {
		boolean task = tp.isPing(GreyStartServlet.configurationMap.get("IP"));
		if (task == true) {
			arr.connectNWModel(GreyStartServlet.configurationMap.get("KEY"), GreyStartServlet.configurationMap.get("MODULE"), GreyStartServlet.configurationMap.get("GORGE"),
					GreyStartServlet.configurationMap.get("KEYS"), GreyStartServlet.configurationMap.get("IP"), GreyStartServlet.configurationMap.get("SERVER_PORT"));
			inven.startInventory("1","0","0","6");
		}
	}
}
