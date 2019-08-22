package com.uhf.structures;

public class InventoryParams {
	public int mode;
	public int triggerDInPort;
	public int triggerLevel;
    public int ignoreTimeMs;
    public int inventoryTimeMs;
    public int maxCountReportFlag;
    
    public void setValue(int mode, int triggerDInPort, int triggerLevel,
		                 int ignoreTimeMs, int inventoryTimeMs, int maxCountReportFlag)
	{
    	this.mode = mode;
    	this.triggerDInPort = triggerDInPort;
    	this.triggerLevel = triggerLevel;
    	this.ignoreTimeMs = ignoreTimeMs;
    	this.inventoryTimeMs = inventoryTimeMs;
    	this.maxCountReportFlag = maxCountReportFlag;
	}
}
