package com.uhf.structures;

public class QueryTagGroup {
	public int selected;
	public int session;
	public int target;
	
	public QueryTagGroup()
	{
	}
	
	public QueryTagGroup(int selected,int session,int target){
		setValue(selected, session, target);
	}
	
	public void setValue(int selected,int session,int target){
		this.selected = selected;
		this.session = session;
		this.target = target;
	}
}
