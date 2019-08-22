package com.uhf.structures;

public class Rm8011Query {
	public int DR = 0;
	public int M;
	public int TRext;
	public int Sel;
	public int Session;
	public int Target;
    public int Q;
    
    public void setValue(int DR, int M, int TRext, int Sel, int Session, int Target,
		            int Q) 
    {
    	this.DR = DR;
    	this.M = M;
    	this.TRext = TRext;
    	this.Sel = Sel;
    	this.Session = Session;
    	this.Target = Target;
        this.Q = Q;
    }
}
