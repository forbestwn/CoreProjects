package com.nosliw.data.core.process;

import org.json.JSONObject;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.info.HAPEntityInfoWritableImp;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.core.resource.HAPResourceId;
import com.nosliw.data.core.resource.HAPResourceIdFactory;

public class HAPDefinitionProcessSuiteElementReference extends HAPEntityInfoWritableImp implements HAPDefinitionProcessSuiteElement{

	@HAPAttribute
	public static String REFERENCE = "reference";

	private HAPResourceId m_resourceId;

	public HAPDefinitionProcessSuiteElementReference() {}

	public HAPDefinitionProcessSuiteElementReference(HAPResourceId resourceId) {
		this.m_resourceId = resourceId;
	}

	@Override
	public String getType() {	return HAPConstant.PROCESSSUITE_ELEMENTTYPE_REFERENCE;	}

	public HAPResourceId getResourceId() {	return this.m_resourceId;	}
	
	@Override
	protected boolean buildObjectByJson(Object obj){
		JSONObject jsonObj = (JSONObject)obj;
		this.buildEntityInfoByJson(jsonObj);
		this.m_resourceId = HAPResourceIdFactory.newInstance(HAPConstant.RUNTIME_RESOURCE_TYPE_PROCESS, jsonObj.get(REFERENCE));
		return true;  
	}

}