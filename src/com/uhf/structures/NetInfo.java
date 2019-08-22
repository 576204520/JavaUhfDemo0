package com.uhf.structures;

import java.util.Arrays;

public class NetInfo {
	public byte[] ip = new byte[5];
	public byte[] gateWay = new byte[5];
	public byte[] netmask = new byte[5];
	public byte[] macAddr = new byte[7];
	public byte[] remoteIp = new byte[5];
	public int remotePort;
	public int pingGateWay;

	public void setValue(byte[] ip, byte[] gateWay, byte[] netmask,
			byte[] macAddr, byte[] remoteIp, int remotePort, int pingGateWay) {
		this.ip = ip;
		this.gateWay = gateWay;
		this.netmask = netmask;
		this.macAddr = macAddr;
		this.remoteIp = remoteIp;
		this.remotePort = remotePort;
		this.pingGateWay = pingGateWay;
	}

	@Override
	public String toString() {
		return "NetInfo [ip=" + Arrays.toString(ip) + ", gateWay="
				+ Arrays.toString(gateWay) + ", netmask="
				+ Arrays.toString(netmask) + ", macAddr="
				+ Arrays.toString(macAddr) + ", remoteIp="
				+ Arrays.toString(remoteIp) + ", remotePort=" + remotePort
				+ ", pingGateWay=" + pingGateWay + "]";
	}

}
