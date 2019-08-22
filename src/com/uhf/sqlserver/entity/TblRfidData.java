package com.uhf.sqlserver.entity;

/**
 * RFID数据表entity
 * 
 * @author zyl
 *
 */
public class TblRfidData {

	//主键
	private String Id;
	//父节点
	private String ParentId;
	//设备ID
	private Integer RFEId;
	//天线Id
	private Integer RFTXId;
	//RFID编码
	private String RFIdCode;
	//出入类型(1:进 0:出)
	private Integer InputType;
	//进库时间
	private String ISTime;
	//出库时间
	private String OSTime;
	public TblRfidData(String id, String parentId, int rFEId, int rFTXId, String rFIdCode, int inputType,
			String iSTime, String oSTime) {
		this.Id = id;
		this.ParentId = parentId;
		this.RFEId = rFEId;
		this.RFTXId = rFTXId;
		this.RFIdCode = rFIdCode;
		this.InputType = inputType;
		this.ISTime = iSTime;
		this.OSTime = oSTime;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		this.Id = id;
	}
	public String getParentId() {
		return ParentId;
	}
	public void setParentId(String parentId) {
		this.ParentId = parentId;
	}
	public Integer getRFEId() {
		return RFEId;
	}
	public void setRFEId(Integer rFEId) {
		this.RFEId = rFEId;
	}
	public Integer getRFTXId() {
		return RFTXId;
	}
	public void setRFTXId(Integer rFTXId) {
		this.RFTXId = rFTXId;
	}
	public String getRFIdCode() {
		return RFIdCode;
	}
	public void setRFIdCode(String rFIdCode) {
		this.RFIdCode = rFIdCode;
	}
	public Integer getInputType() {
		return InputType;
	}
	public void setInputType(Integer inputType) {
		this.InputType = inputType;
	}
	public String getISTime() {
		return ISTime;
	}
	public void setISTime(String iSTime) {
		this.ISTime = iSTime;
	}
	public String getOSTime() {
		return OSTime;
	}
	public void setOSTime(String oSTime) {
		this.OSTime = oSTime;
	}
	@Override
	public String toString() {
		return "TblRfidData [Id=" + Id + ", ParentId=" + ParentId + ", RFEId=" + RFEId + ", RFTXId=" + RFTXId
				+ ", RFIdCode=" + RFIdCode + ", InputType=" + InputType + ", ISTime=" + ISTime + ", OSTime=" + OSTime
				+ "]";
	}
}
