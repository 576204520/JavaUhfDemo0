package com.uhf.structures;

public class InventoryFilterThreshold {
	public int threshold = 0;
	public int maxRepeatTimes = 0;
	public int maxCacheTimeMs = 0;
	
	public void setValue(int threshold, int maxRepeatTimes, int maxCacheTimeMs)
    {
        this.threshold = threshold;
        this.maxRepeatTimes = maxRepeatTimes;
        this.maxCacheTimeMs = maxCacheTimeMs;
    }
}
