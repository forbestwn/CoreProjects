package com.nosliw.uiresource.page.definition;

public class HAPDefinitionStyle {

	private String m_id;
	private String m_definition;

	public HAPDefinitionStyle(String id) {
		this.m_id = id;
	}
	
	public String getId() {   return this.m_id;    }
	
	public String getDefinition() {   return this.m_definition;   }
	public void setDefinition(String def) {    this.m_definition = def;     }
	
}
