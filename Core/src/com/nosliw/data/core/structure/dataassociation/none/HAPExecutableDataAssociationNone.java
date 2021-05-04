package com.nosliw.data.core.structure.dataassociation.none;

import java.util.Map;

import com.nosliw.data.core.runtime.HAPRuntimeInfo;
import com.nosliw.data.core.structure.HAPContainerStructure;
import com.nosliw.data.core.structure.dataassociation.HAPExecutableDataAssociationImp;
import com.nosliw.data.core.structure.dataassociation.HAPOutputStructure;

public class HAPExecutableDataAssociationNone extends HAPExecutableDataAssociationImp{

	public HAPExecutableDataAssociationNone() {}

	public HAPExecutableDataAssociationNone(HAPDefinitionDataAssociationNone definition, HAPContainerStructure input) {
		super(definition, input);
	}
	
	@Override
	public HAPContainerStructure getInput() {	return new HAPContainerStructure();	}
	
	@Override
	public HAPOutputStructure getOutput() {
		HAPOutputStructure out = new HAPOutputStructure();
		return out;
	}

	@Override
	protected void buildJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap) {
		super.buildJsonMap(jsonMap, typeJsonMap);
	}

	@Override
	protected void buildResourceJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPRuntimeInfo runtimeInfo) {
		super.buildResourceJsonMap(jsonMap, typeJsonMap, runtimeInfo);
	}
}
