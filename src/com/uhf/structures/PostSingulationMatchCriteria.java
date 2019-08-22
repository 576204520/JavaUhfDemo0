package com.uhf.structures;

public class PostSingulationMatchCriteria {
	public int status = 0;
	public int maskOffset = 0;
	public int maskCount = 0;
	public byte[] mask = new byte[62];
	
	public void setValue(int status, int maskOffset, int maskCount, byte[] mask)
	{
		this.status = status;
		this.maskOffset = maskOffset;
		this.maskCount = maskCount;
		this.mask = mask;
	}
}
