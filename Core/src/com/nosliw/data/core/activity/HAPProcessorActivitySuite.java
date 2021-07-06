package com.nosliw.data.core.activity;

import java.util.Map;

import com.nosliw.common.utils.HAPProcessTracker;
import com.nosliw.data.core.runtime.HAPRuntimeEnvironment;

public class HAPProcessorActivitySuite {

	public static HAPExecutableActivitySuite process(
			String id,
			HAPDefinitionActivitySuite activitySuiteDef,
			HAPContextProcessAttachmentReferenceActivity attachmentReferenceContext,
			Map<String, String> configure,
			HAPRuntimeEnvironment runtimeEnv,
			HAPProcessTracker processTracker) {
		
		HAPExecutableActivitySuite out = new HAPExecutableActivitySuite(activitySuiteDef);
		
		for(HAPDefinitionActivity activityDef : activitySuiteDef.getEntityElements()) {
			HAPExecutableActivity activityExe = runtimeEnv.getActivityManager().getPluginManager().getPlugin(activityDef.getType()).process(activityDef, activityDef.getId(), attachmentReferenceContext, activitySuiteDef.getValueStructureWrapper(), runtimeEnv, HAPUtilityConfigure.getContextProcessConfigurationForActivity(), processTracker);
			
		}
		
		return out;
	}
	
	
}