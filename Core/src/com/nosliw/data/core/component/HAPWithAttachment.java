package com.nosliw.data.core.component;

import java.util.Map;

import com.nosliw.data.core.component.attachment.HAPAttachment;
import com.nosliw.data.core.component.attachment.HAPContainerAttachment;
import com.nosliw.data.core.component.attachment.HAPReferenceAttachment;

public interface HAPWithAttachment {

	public static final String ATTACHMENT = "attachment";

	//all attachments
	HAPContainerAttachment getAttachmentContainer();
	void setAttachmentContainer(HAPContainerAttachment attachmentContainer);
	
	Map<String, HAPAttachment> getAttachmentsByType(String type);
	HAPAttachment getAttachment(String type, String name);
	HAPAttachment getAttachment(HAPReferenceAttachment idAttachment);

	//
	void mergeBy(HAPWithAttachment parent, String mode);

	void cloneToAttachment(HAPWithAttachment withAttachment);
}
