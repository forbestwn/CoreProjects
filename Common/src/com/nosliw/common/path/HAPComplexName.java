package com.nosliw.common.path;

import com.nosliw.common.pattern.HAPNamingConversionUtility;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;

public class HAPComplexName {

	private String m_fullName;

	private HAPPath m_path;
	
	private String m_simpleName;
	
	public HAPComplexName(String simpleName, String path){
		this.m_simpleName = simpleName;
		this.m_path = new HAPPath(path);
		this.m_fullName = HAPNamingConversionUtility.cascadePath(this.m_path.getPath(), this.m_simpleName);
	}
	
	public HAPComplexName(String fullName){
		if(HAPBasicUtility.isStringNotEmpty(fullName)){
			this.m_fullName = fullName;
			
			int index = this.m_fullName.lastIndexOf(HAPConstant.SEPERATOR_PATH);
			if(index==-1){
				//name only
				this.m_simpleName = this.m_fullName;
			}
			else{
				this.m_simpleName = this.m_fullName.substring(index+1);
				String p = this.m_fullName.substring(0, index);
				this.m_path = new HAPPath(p);
			}
		}
	}
	
	public String getSimpleName(){
		return this.m_simpleName;
	}
	
	public String getPath(){
		if(this.m_path==null)   return null;
		return this.m_path.getPath();
	}

	public String[] getPathSegs(){
		if(this.m_path==null)  return new String[0];
		return this.m_path.getPathSegs();
	}
	
	public String getFullName(){
		return this.m_fullName;
	}
}
