package com.nosliw.data.core.domain.entity.dataassociation;

import org.json.JSONObject;

import com.nosliw.data.core.domain.HAPContextParser;
import com.nosliw.data.core.domain.HAPIdEntityInDomain;
import com.nosliw.data.core.domain.HAPPluginEntityDefinitionInDomainImp;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPPluginEntityDefinitionInDomainDataAssociation  extends HAPPluginEntityDefinitionInDomainImp{

	public HAPPluginEntityDefinitionInDomainDataAssociation(HAPRuntimeEnvironment runtimeEnv) {
		super(HAPDefinitionEntityDataAssciation.class, runtimeEnv);
	}
	
	@Override
	protected void parseDefinitionContent(HAPIdEntityInDomain entityId, Object obj, HAPContextParser parserContext) {
		HAPDefinitionEntityDataAssciation entity = (HAPDefinitionEntityDataAssciation)this.getEntity(entityId, parserContext);
		HAPDefinitionDataAssociation da = HAPParserDataAssociation.buildDefinitionByJson((JSONObject)obj);
		entity.setDataAssciation(da);
	}

	@Override
	public boolean isComplexEntity() {   return false;  }
	
}
