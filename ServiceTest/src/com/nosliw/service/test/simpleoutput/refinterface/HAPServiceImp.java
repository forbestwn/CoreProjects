package com.nosliw.service.test.simpleoutput.refinterface;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nosliw.data.core.data.HAPData;
import com.nosliw.data.core.data.HAPDataTypeId;
import com.nosliw.data.core.data.HAPDataWrapper;
import com.nosliw.data.core.service.definition.HAPExecutableServiceImp;
import com.nosliw.data.core.service.definition.HAPProviderService;
import com.nosliw.data.core.service.definition.HAPResultService;
import com.nosliw.data.core.service.definition.HAPUtilityService;

public class HAPServiceImp extends HAPExecutableServiceImp implements HAPProviderService{

	@Override
	public HAPResultService execute(Map<String, HAPData> parms) {
		
		this.printParms(parms);
		
		Map<String, HAPData> output = new LinkedHashMap<String, HAPData>();
		output.put("simpleOutput1", new HAPDataWrapper(new HAPDataTypeId("test.string;1.0.0"), "output1_"+System.currentTimeMillis()));
		output.put("simpleOutput2", new HAPDataWrapper(new HAPDataTypeId("test.string;1.0.0"), "output2_"+System.currentTimeMillis()));
		
		this.printOutput(output);
		return HAPUtilityService.generateSuccessResult(output);
	}

}
