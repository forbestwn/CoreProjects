package com.nosliw.data.core.story;

import com.nosliw.common.info.HAPEntityInfo;

public interface HAPStoryElement extends HAPEntityInfo{

	public static final String TYPE = "type";

	public static final String ENTITY = "entity";

	public static final String STATUS = "status";

	String getType();

	//core data
	Object getEntity();

	//configuration for element, for ui purpose
	HAPStatus getStatus();
	
}
