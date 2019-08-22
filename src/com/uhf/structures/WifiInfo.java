package com.uhf.structures;

public class WifiInfo {
	public byte[] ssid = new byte[64];
	public int ssidLen;
	public byte[] passwd = new byte[64];
	public int passwdLen;
    public byte[] ip = new byte[5];
    public byte[] gateWay = new byte[5];
    public byte[] netmask = new byte[7];
    public byte[] remoteIp = new byte[5];
    public int remotePort;
    
    public void setValue(byte[] ssid, int ssidLen, byte[] passwd, int passwdLen,
                         byte[] ip, byte[] gateWay, byte[] netmask, byte[] remoteIp,
                         int remotePort)
   {
    	this.ssid = ssid;
    	this.ssidLen = ssidLen;
    	this.passwd = passwd;
    	this.passwdLen = passwdLen;
        this.ip = ip;
        this.gateWay = gateWay;
        this.netmask = netmask;
        this.remoteIp = remoteIp;
        this.remotePort = remotePort;
   }
    
}
