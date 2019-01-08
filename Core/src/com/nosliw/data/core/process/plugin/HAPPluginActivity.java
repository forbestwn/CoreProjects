package com.nosliw.data.core.process.plugin;

import java.util.Map;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPScript;
import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.process.HAPDefinitionActivity;
import com.nosliw.data.core.process.HAPExecutableDataAssociationGroup;
import com.nosliw.data.core.process.HAPDefinitionProcess;
import com.nosliw.data.core.process.HAPExecutableActivity;
import com.nosliw.data.core.process.HAPExecutableProcess;
import com.nosliw.data.core.process.HAPManagerProcess;
import com.nosliw.data.core.script.context.HAPContextGroup;
import com.nosliw.data.core.script.context.HAPRequirementContextProcessor;

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
			HAPExecutableProcess processExe,
			HAPContextGroup context,
			Map<String, HAPExecutableDataAssociationGroup> results,
			Map<String, HAPDefinitionProcess> contextProcessDefinitions,
			HAPManagerProcess processManager,
			HAPRequirementContextProcessor contextProcessRequirement,
			HAPProcessTracker processTracker
	);
	
	HAPDefinitionActivity buildActivityDefinition(Object obj);
	
	HAPScript getScript(String env);
}
