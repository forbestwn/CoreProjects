package com.nosliw.common.configure;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.common.utils.HAPConstantShared;

public class HAPConfigureManager {

	private static HAPConfigureManager m_instance;
	
	//pre configured base configuration
	private Map<String, HAPConfigureImp> m_baseConfigures;
	
	private HAPConfigureManager(){
		m_baseConfigures = new LinkedHashMap<String, HAPConfigureImp>();
		this.initDefaultBase();
	}
	
	public static HAPConfigureManager getInstance(){
		if(m_instance==null){
			m_instance = new HAPConfigureManager();
		}
		return m_instance;
	}
	
	public void registerConfigureBase(String name, HAPConfigureImp configureBase){
		this.m_baseConfigures.put(name, configureBase);
	}

	/*
	 * create brand new configure (empty configure)
	 */
	public HAPConfigureImp newConfigure(){	return new HAPConfigureImp();	}
	
	/*
	 * factory method to create configuration object from base
	 */
	public HAPConfigureImp createConfigure(){
		HAPConfigureImp out = (HAPConfigureImp)this.getDefaultBaseConfigure().clone();
		return out;
	}

	private HAPConfigureImp getDefaultBaseConfigure(){
		return this.getBaseConfigure(HAPConstantShared.CONFIGURATION_DEFAULTBASE);
	}
	
	private HAPConfigureImp getBaseConfigure(String name){
		return this.m_baseConfigures.get(name);
	}
	
	private void initDefaultBase(){
		//import global variables
		HAPConfigureImp baseConfigure = this.newConfigure();
		HAPConfigureUtility.importFromProperty(baseConfigure, "global.properties", this.getClass());
		
		//import from constant
		Field[] declaredFields = HAPConstantShared.class.getDeclaredFields();
		for (Field field : declaredFields) {
		    if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
		    	try {
		    		baseConfigure.addVariableValue(field.getName(), field.get(HAPConstantShared.class).toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}

		this.registerConfigureBase(HAPConstantShared.CONFIGURATION_DEFAULTBASE, baseConfigure);
	}
}
