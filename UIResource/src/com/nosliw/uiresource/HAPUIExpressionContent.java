package com.nosliw.uiresource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nosliw.common.constant.HAPAttribute;
import com.nosliw.common.constant.HAPEntityWithAttribute;
import com.nosliw.common.serialization.HAPSerializableImp;
import com.nosliw.common.serialization.HAPSerializationFormat;
import com.nosliw.common.utils.HAPBasicUtility;
import com.nosliw.common.utils.HAPConstant;
import com.nosliw.common.utils.HAPJsonUtility;
import com.nosliw.data.core.HAPData;
import com.nosliw.data.core.HAPDataTypeManager;

/*
 * class that 
 */
@HAPEntityWithAttribute
public class HAPUIExpressionContent extends HAPSerializableImp{

	@HAPAttribute
	public static final String UIID = "uiId";
	@HAPAttribute
	public static final String UIEXPRESSIONELEMENTS = "uiExpressionElements";
	@HAPAttribute
	public static final String ATTRIBUTE = "attribute";

	private String m_uiId;
	
	//a list element for content, 
	//two types of elements in this list: string and uiExpression
	private List<Object> m_contentElements;
	
	private HAPDataTypeManager m_dataTypeMan;

	public  HAPUIExpressionContent(String uiId, List<Object> contentElements, HAPDataTypeManager dataTypeMan){
		this.m_dataTypeMan = dataTypeMan;
		this.m_uiId = uiId;
		this.m_contentElements = contentElements;
	}

	public  HAPUIExpressionContent(String uiId, String content, HAPDataTypeManager dataTypeMan){
		this.m_dataTypeMan = dataTypeMan;
		this.m_uiId = uiId;
		this.m_contentElements = contentElements;
	}
	
	public String getUiId(){return this.m_uiId;}
	public void setUiId(String id){this.m_uiId=id;}

	public Set<HAPUIResourceExpression> getUIExpressions(){
		Set<HAPUIResourceExpression> out = new HashSet<HAPUIResourceExpression>();
		for(Object obj : this.m_contentElements){
			if(obj instanceof HAPUIResourceExpression){
				out.add((HAPUIResourceExpression)obj);
			}
		}
		return out;
	}

	/*
	 * parse string containing expression to create elements that may be string or uiResourceExpression
	 */
	private List<Object> getExpressionContentElements(String content, Map<String, HAPData> constants1, HAPDataTypeManager dataTypeMan){
		List<Object> out = new ArrayList<Object>();
		while(HAPBasicUtility.isStringNotEmpty(content)){
			int index = content.indexOf(HAPConstant.UIRESOURCE_UIEXPRESSION_TOKEN_OPEN);
			if(index==-1){
				//no expression
				out.add(content);
				content = null;
			}
			else if(index!=0){
				//start with text
				out.add(content.substring(0, index));
				content = content.substring(index);
			}
			else{
				//start with expression
				int expEnd = content.indexOf(HAPConstant.UIRESOURCE_UIEXPRESSION_TOKEN_CLOSE);
				int expStart = index + HAPConstant.UIRESOURCE_UIEXPRESSION_TOKEN_OPEN.length();
				out.add(new HAPUIResourceExpression(content.substring(expStart, expEnd), HAPUIResourceUtility.buildExpressionFunctionName(this.createId()), constants, dataTypeMan));
				content = content.substring(expEnd + HAPConstant.UIRESOURCE_UIEXPRESSION_TOKEN_CLOSE.length());
			}
		}
				
		return out;
	}
	

	
	protected HAPDataTypeManager getDataTypeManager(){ return this.m_dataTypeMan; }

	@Override
	protected void buildFullJsonMap(Map<String, String> jsonMap, Map<String, Class<?>> typeJsonMap){
		jsonMap.put(UIID, this.m_uiId);
		jsonMap.put(UIEXPRESSIONELEMENTS, HAPJsonUtility.buildJson(this.m_contentElements, HAPSerializationFormat.JSON_FULL));
	}
}
