package com.uhf.structures;

public class FixedQParameters
{
    public int qValue;
    public int retryCount;
    public int toggleTarget;
    public int repeatUntiNoTags;
    
    public void setValue(int qValue, int retryCount, int toggleTarget, int repeatUntiNoTags)
    {
        this.qValue = qValue;
        this.retryCount = retryCount;
        this.toggleTarget = toggleTarget;
        this.repeatUntiNoTags = repeatUntiNoTags;
    }

    @Override
    public String toString()
    {
        return "FixedQParams{" +
                "qValue=" + qValue +
                ", retryCount=" + retryCount +
                ", toggleTarget=" + toggleTarget +
                ", repeatUntiNoTags=" + repeatUntiNoTags +
                '}';
    }
}
