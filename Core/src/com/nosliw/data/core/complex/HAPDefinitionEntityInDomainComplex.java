package com.nosliw.data.core.complex;

import com.nosliw.data.core.common.HAPWithValueStructure;
import com.nosliw.data.core.component.HAPWithAttachment;
import com.nosliw.data.core.domain.HAPDefinitionEntityInDomain;
import com.nosliw.data.core.domain.HAPEmbededEntity;

//entity that have data value structure and attachment
public abstract class HAPDefinitionEntityInDomainComplex extends HAPDefinitionEntityInDomain implements HAPWithValueStructure, HAPWithAttachment{

	public static final String COMPLEX = "complex";
	
	protected HAPDefinitionEntityInDomainComplex() {}
	
	public HAPDefinitionEntityInDomainComplex (String entityType) {
		super(entityType);
	}
	
	@Override
	public HAPEmbededEntity getValueStructureComplexEntity() {  return this.getSimpleAttribute(HAPWithValueStructure.VALUESTRUCTURE);  }

	@Override
	public void setValueStructureComplexEntity(HAPEmbededEntity valueStructureComplexEntity) {    this.setSimpleAttribute(HAPWithValueStructure.VALUESTRUCTURE, valueStructureComplexEntity);      }

	@Override
	public String getValueStructureTypeIfNotDefined() {  return null;  }

	@Override
	public HAPEmbededEntity getAttachmentContainerEntity() {   return this.getSimpleAttribute(HAPWithAttachment.ATTACHMENT);  }

	@Override
	public void setAttachmentContainerEntity(HAPEmbededEntity attachmentEntity) {    this.setSimpleAttribute(HAPWithAttachment.ATTACHMENT, attachmentEntity);   }

}