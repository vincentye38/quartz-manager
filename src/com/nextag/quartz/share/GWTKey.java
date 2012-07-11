package com.nextag.quartz.share;

import java.io.Serializable;


public class GWTKey implements Serializable , Comparable<GWTKey>{
	 /**
     * The default group for scheduling entities, with the value "DEFAULT".
     */
    public static final String DEFAULT_GROUP = "DEFAULT";
	
	String name;
	String group;
	
	GWTKey(){}
	
	public GWTKey(String name, String group){
		this.name = name;
		this.group = group;
	}
	public String getName() {
		return name;
	}

	public String getGroup() {
		return group;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public int compareTo(GWTKey o) {
		if(group.equals(DEFAULT_GROUP) && !o.group.equals(DEFAULT_GROUP))
            return -1;
        if(!group.equals(DEFAULT_GROUP) && o.group.equals(DEFAULT_GROUP))
            return 1;
            
        int r = group.compareTo(o.getGroup());
        if(r != 0)
            return r;
        
        return name.compareTo(o.getName());
	}

	
	
}
