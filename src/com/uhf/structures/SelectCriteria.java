package com.uhf.structures;

public class SelectCriteria
{
    public int status = 0;
    public int memBank = 0;
    public int target = 0;
    public int action = 0;
    public int enableTruncate = 0;
    public int maskOffset = 0;
    public int maskCount = 0;
    public byte[] mask = new byte[32];

    public SelectCriteria(int status)
    {
        this.status = status;
    }

    public void setValue(int status, int memBank, int target,
                         int action, int enableTruncate,
                         int maskOffset, int maskCount, byte[] mask)
    {
        this.status = status;
        this.memBank = memBank;
        this.target = target;
        this.action = action;
        this.enableTruncate = enableTruncate;
        this.maskOffset = maskOffset;
        this.maskCount = maskCount;
        this.mask = mask;
    }
}
