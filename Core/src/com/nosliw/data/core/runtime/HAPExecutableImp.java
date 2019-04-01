package com.nosliw.data.core.runtime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.serialization.HAPJsonUtility;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.data.core.resource.HAPResourceData;
import com.nosliw.data.core.resource.HAPResourceDependent;
import com.nosliw.data.core.runtime.js.HAPResourceDataFactory;

public abstract class HAPExecutableImp  extends HAPSerializableImp implements HAPExecutable{

	@Override
	public List<HAPResourceDependent> getResourceDependency(HAPRuntimeInfo runtimeInfo) {
		List<HAPResourceDependent> out = new ArrayList<HAPResourceDependent>();
		this.buildResourceDependency(out, runtimeInfo);
		return out;
	}

	@Override
	public HAPResourceData toResourceData(HAPRuntimeInfo runtimeInfo) {
		Map<String, String> jsonMap = new LinkedHashMap<String, String>();
		Map<String, Class<?>> typeJsonMap = new LinkedHashMap<String, Class<?>>();
		this.buildJsonMap(jsonMap, typeJsonMap);
		this.buildResourceJsonMap(jsonMap, typeJsonMap, runtimeInfo);
		return HAPResourceDataFactory.createJSValueResourceData(HAPJsonUtility.buildMapJson(jsonMap, typeJsonMap));
	}

	protected void buildResourceJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap, HAPRuntimeInfo runtimeInfo) {	}

	protected void buildResourceDependency(List<HAPResourceDependent> dependency, HAPRuntimeInfo runtimeInfo) {}

}
