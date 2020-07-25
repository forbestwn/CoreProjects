package com.nosliw.data.core.story;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.info.HAPEntityInfo;

@HAPEntityWithAttribute
public interface HAPStoryElement extends HAPEntityInfo{

	@HAPAttribute
	public static final String CATEGARY = "categary";
	
	@HAPAttribute
	public static final String TYPE = "type";

	@HAPAttribute
	public static final String ENTITY = "entity";

	@HAPAttribute
	public static final String ENABLE = "enable";

	@HAPAttribute
	public static final String STATUS = "status";

	HAPIdElement getElementId();
	
	String getCategary();
	
	String getType();

	boolean patch(String path, Object value);
	
	//core data
	Object getEntity();

	//configuration for element, for ui purpose
	HAPStatus getStatus();

	boolean isEnable();
	
}
