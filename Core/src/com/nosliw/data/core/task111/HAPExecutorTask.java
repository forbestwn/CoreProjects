package com.nosliw.data.core.task111;

import java.util.Map;

import com.nosliw.data.core.HAPData;

public interface HAPExecutorTask {

	HAPData execute(HAPExecutableTask task, Map<String, HAPData> parms, HAPTaskReferenceCache cache, HAPLogTask logger);
	
}