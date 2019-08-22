package com.uhf.sqlserver.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
/**
 * 公共类
 * @author zyl
 *
 */
public class TestPing {

	public static int timeOut = 3000; //超时应该在3秒以上
	/**
	 * 检查ip是否能ping通
	 * @param ip
	 * @return
	 */
	public static boolean isPing(String ip) {
		boolean status = false;
		try {
			if(ip != null) {
				status = InetAddress.getByName(ip).isReachable(timeOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return status;
	}
	
	Timer timerIp;
	
	/**
	 * 开启没连接RFID机器定时器
	 */
	public void timeOs() {
		timerIp = new Timer();
		timerIp.schedule(new TaskIp(), 3000, 9000);
		GreyStartServlet.times = true;
	}
	
	/**
	 * 关闭定时器
	 */
	public void shutDownTime() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timerIp.cancel();
		GreyStartServlet.times = false;
	}
}
