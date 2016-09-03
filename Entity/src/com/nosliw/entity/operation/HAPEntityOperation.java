package com.nosliw.entity.operation;

import com.nosliw.common.utils.HAPConstant;

public enum HAPEntityOperation {
	ENTITYOPERATION_ATTR_ATOM_SET(HAPConstant.ENTITYOPERATION_ATTR_ATOM_SET, HAPConstant.ENTITYOPERATIONCODE_ATTR_ATOM_SET),
	ENTITYOPERATION_ATTR_CRITICAL_SET(HAPConstant.ENTITYOPERATION_ATTR_CRITICAL_SET, HAPConstant.ENTITYOPERATIONCODE_ATTR_CRITICAL_SET),
	ENTITYOPERATION_ATTR_ELEMENT_NEW(HAPConstant.ENTITYOPERATION_ATTR_ELEMENT_NEW, HAPConstant.ENTITYOPERATIONCODE_ATTR_ELEMENT_NEW),
	ENTITYOPERATION_ATTR_ELEMENT_DELETE(HAPConstant.ENTITYOPERATION_ATTR_ELEMENT_DELETE, HAPConstant.ENTITYOPERATIONCODE_ATTR_ELEMENT_DELETE),

	ENTITYOPERATION_ENTITY_NEW(HAPConstant.ENTITYOPERATION_ENTITY_NEW, HAPConstant.ENTITYOPERATIONCODE_ENTITY_NEW),
	ENTITYOPERATION_ENTITY_DELETE(HAPConstant.ENTITYOPERATION_ENTITY_DELETE, HAPConstant.ENTITYOPERATIONCODE_ENTITY_DELETE),
	ENTITYOPERATION_ENTITY_OPERATIONS(HAPConstant.ENTITYOPERATION_ENTITY_OPERATIONS, HAPConstant.ENTITYOPERATIONCODE_ENTITY_OPERATIONS),
	
	ENTITYOPERATION_TRANSACTION_START(HAPConstant.ENTITYOPERATION_TRANSACTION_START, HAPConstant.ENTITYOPERATIONCODE_TRANSACTION_START),
	ENTITYOPERATION_TRANSACTION_COMMIT(HAPConstant.ENTITYOPERATION_TRANSACTION_COMMIT, HAPConstant.ENTITYOPERATIONCODE_TRANSACTION_COMMIT),
	ENTITYOPERATION_TRANSACTION_ROLLBACK(HAPConstant.ENTITYOPERATION_TRANSACTION_ROLLBACK, HAPConstant.ENTITYOPERATIONCODE_TRANSACTION_ROLLBACK),
	
	ENTITYOPERATION_QUERY_ENTITY_ADD(HAPConstant.ENTITYOPERATION_QUERY_ENTITY_ADD, HAPConstant.ENTITYOPERATIONCODE_QUERY_ENTITY_ADD),
	ENTITYOPERATION_QUERY_ENTITY_REMOVE(HAPConstant.ENTITYOPERATION_QUERY_ENTITY_REMOVE, HAPConstant.ENTITYOPERATIONCODE_QUERY_ENTITY_REMOVE),
	ENTITYOPERATION_QUERY_ENTITY_MODIFY(HAPConstant.ENTITYOPERATION_QUERY_ENTITY_MODIFY, HAPConstant.ENTITYOPERATIONCODE_QUERY_ENTITY_MODIFY),

	ENTITYOPERATION_ATTR_REFERENCE_SET(HAPConstant.ENTITYOPERATION_ATTR_REFERENCE_SET, HAPConstant.ENTITYOPERATIONCODE_ATTR_REFERENCE_SET),

	ENTITYOPERATION_ENTITYATTR_ADD(HAPConstant.ENTITYOPERATION_ENTITYATTR_ADD, HAPConstant.ENTITYOPERATIONCODE_ENTITYATTR_ADD),

	ENTITYOPERATION_ENTITYATTR_REMOVE(HAPConstant.ENTITYOPERATION_ENTITYATTR_REMOVE, HAPConstant.ENTITYOPERATIONCODE_ENTITYATTR_REMOVE),
	ENTITYOPERATION_ATTR_REFERENCE_CLEAR("", 103),
	ENTITYOPERATION_REFERENCE_ADD("", 117),
	ENTITYOPERATION_REFERENCE_REMOVE("", 118),

	
//	ENTITYOPERATION_ATTR_ELEMENT_DELETE("", 106),
//	
//	ENTITYOPERATION_ATTR_ELEMENT_SET("", 105),
//	ENTITYOPERATION_ENTITY_SYN("", 107),
//	ENTITYOPERATION_ENTITYTRANSACTION_START("", 130),
//	ENTITYOPERATION_CONTAINER_INDEX_INCREASE("", 115),
//	ENTITYOPERATION_ENTITY_SETDEFINITION("", 116),
//	ENTITYOPERATION_QUERY_REMOVE("", 119),
//	
//	ENTITYOPERATION_ATTR_CONTAINER_ADD("", 122),
	;
	
	private final String m_name;
	private final int m_code;

	private HAPEntityOperation(String name, int code){
		this.m_code = code;
		this.m_name = name;
	}

	public String getName(){return this.m_name;}
	public int getCode(){return this.m_code;}

	public static HAPEntityOperation getOperationByName(String name){
		for (HAPEntityOperation op : HAPEntityOperation.values()) {
			if(op.getName().equals(name))  return op;
		}
		return null;
	}

	public static HAPEntityOperation getOperationByCode(int code){
		for (HAPEntityOperation op : HAPEntityOperation.values()) {
			if(op.getCode()==code)  return op;
		}
		return null;
	}
}
