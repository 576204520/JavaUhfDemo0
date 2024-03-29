package com.uhf.structures;

public class DynamicQParams
{
    public int startQValue;
    public int minQValue;
    public int maxQValue;
    public int retryCount;
    public int toggleTarget;
    public int thresholdMultiplier;

    public DynamicQParams()
    {
    }

    public DynamicQParams(int startQValue, int minQValue, int maxQValue, int retryCount, int toggleTarget, int thresholdMultiplier)
    {
        this.startQValue = startQValue;
        this.minQValue = minQValue;
        this.maxQValue = maxQValue;
        this.retryCount = retryCount;
        this.toggleTarget = toggleTarget;
        this.thresholdMultiplier = thresholdMultiplier;
    }

    public void setValue(int startQValue, int minQValue, int maxQValue, int retryCount, int toggleTarget, int thresholdMultiplier)
    {
        this.startQValue = startQValue;
        this.minQValue = minQValue;
        this.maxQValue = maxQValue;
        this.retryCount = retryCount;
        this.toggleTarget = toggleTarget;
        this.thresholdMultiplier = thresholdMultiplier;
    }


    @Override
    public String toString()
    {
        return "DynamicQParams{" +
                "startQValue=" + startQValue +
                ", minQValue=" + minQValue +
                ", maxQValue=" + maxQValue +
                ", retryCount=" + retryCount +
                ", toggleTarget=" + toggleTarget +
                ", thresholdMultiplier=" + thresholdMultiplier +
                '}';
    }
}
