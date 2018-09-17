package com.nosliw.data.core.script.context;

import com.nosliw.common.pattern.HAPNamingConversionUtility;

public class HAPContextRootNodeId {

	public static final String SEPERATOR = "___";
	
	private String m_categary;
	
	private String m_name;
	
	public HAPContextRootNodeId(String categary, String name) {
		this.m_categary = categary;
		this.m_name = name;
	}
	
	public HAPContextRootNodeId(String name) {
		String[] segs = HAPNamingConversionUtility.splitTextByElements(name, SEPERATOR);
		this.m_name = segs[0];
		if(segs.length>=2)   this.m_categary = segs[1];
	}
	
	public String getCategary() {		return this.m_categary;	}
	public void setCategary(String categary) {  this.m_categary = categary;    }
	
	public String getName() {   return this.m_name;   }

	public String getFullName() {  return HAPNamingConversionUtility.cascadeElements(new String[] {this.m_name, this.m_categary}, SEPERATOR);   }

	public HAPContextRootNodeId clone() {
		HAPContextRootNodeId out = new HAPContextRootNodeId(this.m_categary, this.m_name);
		return out;
	}
	
	@Override
	public String toString() {   return this.getFullName();	}
	
}