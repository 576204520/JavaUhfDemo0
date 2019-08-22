package com.uhf.structures;

public class InputTriggerAlarm {
	public int status;
	public int triggerDInPort;
	public int triggerLevel;
    public int DPort;
    public int DOutLevel;
    public int ignoreMs;
    public int alarmMs;
    
    public void setValue(int status, int triggerDInPort, int triggerLevel,
                         int DPort, int DOutLevel, int ignoreMs, int alarmMs)
    {
    	this.status = status;
    	this.triggerDInPort = triggerDInPort;
    	this.triggerLevel = triggerLevel;
        this.DPort = DPort;
        this.DOutLevel = DOutLevel;
        this.ignoreMs = ignoreMs;
        this.alarmMs = alarmMs;
    }
}
