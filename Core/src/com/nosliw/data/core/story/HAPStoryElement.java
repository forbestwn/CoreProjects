package com.nosliw.data.core.story;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;
import com.nosliw.data.core.story.design.HAPChangeResult;

@HAPEntityWithAttribute
public interface HAPStoryElement extends HAPEntityInfo{

	@HAPAttribute
	public static final String CATEGARY = "categary";
	
	@HAPAttribute
	public static final String TYPE = "type";

	@HAPAttribute
	public static final String ENABLE = "enable";

	@HAPAttribute
	public static final String STATUS = "status";

	HAPIdElement getElementId();
	
	String getCategary();
	
	String getType();

	HAPChangeResult patch(String path, Object value);
	
	//configuration for element, for ui purpose
	HAPStatus getStatus();

	boolean isEnable();
	
}
