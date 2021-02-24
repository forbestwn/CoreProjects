package com.nosliw.test.service;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.data.core.data.HAPData;
import com.nosliw.data.core.data.HAPUtilityData;
import com.nosliw.data.core.imp.runtime.js.rhino.HAPRuntimeEnvironmentImpRhino;
import com.nosliw.data.core.service.definition.HAPQueryService;
import com.nosliw.data.core.service.definition.HAPResultService;

public class HAPServiceMain {

	public static void main(String[] args) {
		
		HAPRuntimeEnvironmentImpRhino runtimeEnvironment = new HAPRuntimeEnvironmentImpRhino();
		
		HAPQueryService query = new HAPQueryService();
		query.setServiceId("TestProcessService");
		
		Map<String, HAPData> input = new LinkedHashMap<String, HAPData>();
		input.put("fromVar", HAPUtilityData.buildDataWrapper("#test.integer;1.0.0___7"));
		input.put("toVar", HAPUtilityData.buildDataWrapper("#test.integer;1.0.0___8"));
//		input.put("baseVar", HAPDataUtility.buildDataWrapper("#test.string;1.0.0___helloworld"));
		
		HAPResultService result = runtimeEnvironment.getServiceManager().execute(query, input);
		System.out.println(result.toString());
	}

}
