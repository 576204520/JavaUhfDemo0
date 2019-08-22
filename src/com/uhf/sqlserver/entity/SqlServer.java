package com.uhf.sqlserver.entity;

public class SqlServer {

	//主键id
	private Integer menusid;
	//epc编码
	private String menusname;
	//地址
	private String menusurl;
	//天线号
	private Integer fatherid;
	
	public SqlServer(int menusid, String menusname, String menusurl, int fatherid) {
		this.menusid = menusid;
		this.menusname = menusname;
		this.menusurl = menusurl;
		this.fatherid = fatherid;
	}
	public Integer getMenusid() {
		return menusid;
	}
	public void setMenusid(Integer menusid) {
		this.menusid = menusid;
	}
	public String getMenusname() {
		return menusname;
	}
	public void setMenusname(String menusname) {
		this.menusname = menusname;
	}
	public String getMenusurl() {
		return menusurl;
	}
	public void setMenusurl(String menusurl) {
		this.menusurl = menusurl;
	}
	public Integer getFatherid() {
		return fatherid;
	}
	public void setFatherid(Integer fatherid) {
		this.fatherid = fatherid;
	}
	@Override
	public String toString() {
		return "SqlServer [menusid=" + menusid + ", menusname=" + menusname + ", menusurl=" + menusurl + ", fatherid="
				+ fatherid + "]";
	}
	
}
