package com.nosliw.data.core.activity;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPJsonTypeScript;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.complex.HAPDefinitionEntityInDomainComplex;
import com.nosliw.data.core.component.HAPContextProcessor;
import com.nosliw.data.core.domain.entity.valuestructure.HAPConfigureProcessorValueStructure;
import com.nosliw.data.core.domain.entity.valuestructure.HAPWrapperValueStructureDefinition;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

//each type of activity should provide a plugin which contains information:
//   how to parse activity definition
//	 how to process definition to executable
//   script for the runtime
@HAPEntityWithAttribute
public interface HAPPluginActivity {

	@HAPAttribute
	public static String TYPE = "type";
	
	@HAPAttribute
	public static String SCRIPT = "script";
	
	String getType();
	
	//process activity definition to executable
	HAPExecutableActivity process(
			HAPDefinitionActivity activityDefinition, 
			String id,
			HAPContextProcessor processContext, 
			HAPWrapperValueStructureDefinition valueStructureWrapper,
			HAPRuntimeEnvironment runtimeEnv,
			HAPConfigureProcessorValueStructure configure, 
			HAPProcessTracker processTracker);
	
	HAPDefinitionActivity buildActivityDefinition(Object obj, HAPDefinitionEntityInDomainComplex complexEntity);
	
	HAPExecutableActivity buildActivityExecutable(Object obj);
	
	HAPJsonTypeScript getScript(String env);
}
