package com.uhf.springmvc.entity;

public class InitModel {
	// 模块类型
	public int moduleType;
	// 连接模式
	public int connectType;
	// 端口号
	public String port;
	// 波特率
	public int baud;
	// 远端IP
	public String remoteIp;
	// 远端端口
	public int remotePort;
	// 监听端口
	public int listenPort;
	
	public int getModuleType() {
		return moduleType;
	}
	public void setModuleType(int moduleType) {
		this.moduleType = moduleType;
	}
	public int getConnectType() {
		return connectType;
	}
	public void setConnectType(int connectType) {
		this.connectType = connectType;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public int getBaud() {
		return baud;
	}
	public void setBand(int band) {
		this.baud = band;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public int getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
	public int getListenPort() {
		return listenPort;
	}
	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}
	
}
