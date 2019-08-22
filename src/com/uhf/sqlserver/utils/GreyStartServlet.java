package com.uhf.sqlserver.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.uhf.detailwith.InventoryDetailWith;
import com.uhf.linkage.Linkage;
import com.uhf.springmvc.controller.AntennaSettingController;
import com.uhf.springmvc.controller.InitModelController;
import com.uhf.springmvc.controller.InventoryController;

/**
 * 设置tomcat启动执行
 * 
 * @author zyl
 *
 */
public class GreyStartServlet implements ServletContextListener {
	// 定义全局List用于保存返回数据
	public static List<String> epcs = new ArrayList<>();
	// 定义全局Map
	public static Map<String, String> configurationMap = new HashMap<>();
	// 定义全局计数器-->主要是看它有没有码在范围内
	public static long countName = 0;
	// 定义临时全局计数器-->主要跟上面数进行比对
	public static long hardCount = 0;
	
	public static boolean times = false;
	
	//定义全局集合
	public static List<Map<String, Object>> Names = new ArrayList<>();
	public static List<Map<String, Object>> Name2;
	
	// 调用启动RFID
	InitModelController arr = new InitModelController();
	
	InventoryController inven = new InventoryController();
	
	TestPing tp = new TestPing();
	/**
	 * tomcat销毁执行
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("this is last destroyeed");
	}

	/**
	 * tomcat启动执行
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties prop = new Properties();
		try {
			// 读取属性文件config.properties
			String filePath = sce.getServletContext().getRealPath("/WEB-INF/classes/config.properties");
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			prop.load(in); /// 加载属性列表
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				this.configurationMap.put(key, prop.getProperty(key));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// 启动RFID程序
		arr.connectNWModel(configurationMap.get("KEY"), configurationMap.get("MODULE"), configurationMap.get("GORGE"),
				configurationMap.get("KEYS"), configurationMap.get("IP"), configurationMap.get("SERVER_PORT"));
		for(int idx = 0; idx < 4; idx++){
			
			int status = Linkage.getInstance().setAntennaPortState(idx, 1);
			if(status != 0){
				System.out.println("Open aerial");
			}
		}
		inven.startInventory("1","0","0","6");
		// 开启定时任务
		Timer timer = new Timer();
		timer.schedule(new Task(), 3000, 5000);
		
		//机器重启
	}
}
