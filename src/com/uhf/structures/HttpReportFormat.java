package com.uhf.structures;

public class HttpReportFormat {
	public int status;
	public int actionLen;
	public byte[] actionName;
	public int customMsgLen;
	public byte[] customMsg;
	public int method;
	public int contentMask;
	
	public HttpReportFormat(int status, int actionLen, byte[] actionName, int customMsgLen,
                            byte[] customMsg, int method, int contentMask) {
		setValue(status, actionLen, actionName, customMsgLen,
	             customMsg, method, contentMask);
	}

	public HttpReportFormat() {
	}

	public void setValue(int status, int actionLen, byte[] actionName, int customMsgLen,
			             byte[] customMsg, int method, int contentMask) {
		this.status = status;
		this.actionLen = actionLen;
		this.actionName = actionName;
		this.customMsgLen = customMsgLen;
		this.customMsg = customMsg;
		this.method = method;
		this.contentMask = contentMask;
	}
}
