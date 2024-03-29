package com.nosliw.data.core.domain.entity.dataassociation;

import java.util.Map;

import com.nosliw.data.core.valuestructure.HAPContainerStructure;

//input and result of task
public interface HAPIOTask {

	HAPContainerStructure getInStructure();

	Map<String, HAPContainerStructure> getOutResultStructure();

}
