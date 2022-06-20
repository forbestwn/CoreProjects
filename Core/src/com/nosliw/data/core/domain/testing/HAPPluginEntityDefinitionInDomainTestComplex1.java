package com.nosliw.data.core.domain.testing;

import org.json.JSONObject;

import com.nosliw.data.core.domain.HAPContextParser;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPPluginEntityDefinitionInDomainTestComplex1 extends HAPPluginEntityDefinitionInDomainDynamic{

	public HAPPluginEntityDefinitionInDomainTestComplex1(HAPRuntimeEnvironment runtimeEnv) {
		super(HAPDefinitionEntityTestComplex1.class, true, runtimeEnv);
	}
	
	@Override
	protected void parseDefinitionContent(HAPIdEntityInDomain entityId, Object obj, HAPContextParser parserContext) {
		super.parseDefinitionContent(entityId, obj, parserContext);
		HAPDefinitionEntityTestComplex1 entity = (HAPDefinitionEntityTestComplex1)this.getEntity(entityId, parserContext);
		JSONObject jsonObj = (JSONObject)obj;
		Object varObj = jsonObj.opt(HAPPluginEntityDefinitionInDomainDynamic.PREFIX_IGNORE+"_"+HAPDefinitionEntityTestComplex1.ATTR_VARIABLE);
		if(varObj!=null)  entity.setVariable((String)varObj);
	}
}
