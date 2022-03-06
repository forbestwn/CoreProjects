package com.nosliw.data.core.domain;

import java.util.List;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.serialization.HAPSerializable;

//container for entity
public interface HAPContainerEntity<T extends HAPInfoContainerElement> extends HAPSerializable{

	@HAPAttribute
	public static String ELEMENT = "element";

	public static final String ELEMENT_INFO = "eleInfo";

	String getContainerType();

	void addEntityElement(T eleInfo);

	T getElementInfoById(HAPIdEntityInDomain id);
	
	T getElementInfoByName(String name);
	
	List<T> getAllElementsInfo();
	
	
//	HAPInfoContainerElement newElementInfoInstance();
	
	
	HAPContainerEntity<T> cloneContainerEntity();

	String toExpandedJsonString(HAPDomainDefinitionEntity entityDefDomain);

}
