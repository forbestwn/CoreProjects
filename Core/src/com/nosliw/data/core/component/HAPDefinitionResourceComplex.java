package com.nosliw.data.core.component;

import com.nosliw.data.core.complex.HAPDefinitionEntityComplex;
import com.nosliw.data.core.resource.HAPResourceDefinition1;

//a complex resource definition that contains attachment, context data, child resource 
public interface HAPDefinitionResourceComplex extends HAPDefinitionEntityComplex, HAPResourceDefinition1{

	//all external resource reffered by this complex resource, for debug purpose
	HAPContainerChildReferenceResource getChildrenReferencedResource();

	void cloneToComplexResourceDefinition(HAPDefinitionResourceComplex complexEntity, boolean cloneValueStructure);
}
