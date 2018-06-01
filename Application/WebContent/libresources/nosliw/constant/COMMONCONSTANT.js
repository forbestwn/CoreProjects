//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

/**
 * 
 */
var COMMONCONSTANT=

{
  "SERVICECODE_SUCCESS": 200,
  "SERVICECODE_FAILURE": 400,
  "SERVICECODE_EXCEPTION": 5000,
  "ERRORCODE_NEWDATAOPERATION_NOTDEFINED": 1000,
  "ERRORCODE_DATAOPERATION_NOTDEFINED": 1005,
  "ERRORCODE_DATAOPERATION_NOTEXIST": 1010,
  "ERRORCODE_DATATYPE_WRONGTYPE": 1100,
  "ERRORCODE_DATATYPE_WRONGVERSION": 1101,
  "ERRORCODE_APPLICATION_INVALIDCLIENTID": 5201,
  "ERRORCODE_DATA_INVALID": 1102,
  "SERVICECODE_ENTITYOPERATION_FORWARD": 100,
  "ERRORCODE_ENTITYOPERATION_AUTOCOMMIT": 2001,
  "ERRORCODE_ENTITYOPERATION_INVALIDTRANSACTION": 2002,
  "ERRORCODE_ENTITYOPERATION_INVALIDSCOPE": 2003,
  "ERRORCODE_REMOTESERVICE_SUSPEND": 9000,
  "ERRORCODE_REMOTESERVICE_EXCEPTION": 5000,
  "OPERATIONDEF_SCRIPT_JAVASCRIPT": "javascript",
  "OPERATIONDEF_PATH_VERSION": "version",
  "OPERATIONDEF_PATH_PARENT": "parent",
  "ENTITYOPERATION_SCOPE_UNDEFINED": -1,
  "ENTITYOPERATION_SCOPE_GLOBAL": 1,
  "ENTITYOPERATION_SCOPE_ENTITY": 2,
  "ENTITYOPERATION_SCOPE_OPERATION": 3,
  "SYMBOL_KEYWORD": "#",
  "SYMBOL_GROUP": "@",
  "SYMBOL_ENTITYNAME_COMMON": "..",
  "SYMBOL_ENTITYNAME_CURRENT": ".",
  "SEPERATOR_NAMEVALUE": "\u003d",
  "SEPERATOR_ELEMENT": ",",
  "SEPERATOR_SEGMENT": ";",
  "SEPERATOR_PART": ":",
  "SEPERATOR_PATH": ".",
  "SEPERATOR_FULLNAME": ".",
  "SEPERATOR_DETAIL": "|",
  "SEPERATOR_SURFIX": "_",
  "SEPERATOR_PREFIX": "_",
  "SEPERATOR_LEVEL1": ";",
  "SEPERATOR_LEVEL2": "|",
  "SEPERATOR_LEVEL3": ":",
  "SEPERATOR_LEVEL4": ",",
  "SEPERATOR_ARRAYSTART": "[",
  "SEPERATOR_ARRAYEND": "]",
  "SEPERATOR_VARSTART": "{{",
  "SEPERATOR_VAREND": "}}",
  "SEPERATOR_EXPRESSIONSTART": "${",
  "SEPERATOR_EXPRESSIONEND": "}",
  "DATAOPERATION_NEWDATA": "new",
  "DATAOPERATION_TOPARENTTYPE": "toParentType",
  "DATAOPERATION_FROMPARENTTYPE": "fromParentType",
  "DATAOPERATION_TOVERSION": "toVersion",
  "DATAOPERATION_FROMVERSION": "fromVersion",
  "DATAOPERATION_GETCHILD": "getChild",
  "DATAOPERATION_GETCHILDDATATYPE": "getChildDatatype",
  "DATAOPERATION_COMPARE": "compare",
  "DATAOPERATION_PARSELITERAL": "parseLiteral",
  "DATAOPERATION_COMPLEX_GETCHILDRENNAMES": "getChildrenNames",
  "DATAOPERATION_COMPLEX_GETCHILDDATA": "getChildData",
  "DATAOPERATION_COMPLEX_GETCHILDDATABYINDEX": "getChildDataByIndex",
  "DATAOPERATION_COMPLEX_SETCHILDDATA": "setChildData",
  "DATAOPERATION_COMPLEX_ISACCESSCHILDBYID": "isAccessChildById",
  "DATAOPERATION_COMPLEX_LENGTH": "length",
  "DATAOPERATION_COMPLEX_ADDCHILD": "addChild",
  "DATAOPERATION_COMPLEX_REMOVECHILD": "removeChild",
  "DATAOPERATION_PARM_BASENAME": "ABCDEFGHIJKLMN",
  "DATAOPERATION_TYPE_NORMAL": "normal",
  "DATAOPERATION_TYPE_NEW": "new",
  "DATAOPERATION_TYPE_CONVERT": "convert",
  "DATAOPERATION_TYPE_CONVERTFROM": "convertFrom",
  "DATAOPERATION_TYPE_TOFORMAT": "toFormat",
  "DATATYPECRITERIA_TYPE_ANY": "any",
  "DATATYPECRITERIA_TYPE_DATATYPEID": "dataTypeId",
  "DATATYPECRITERIA_TYPE_DATATYPEIDS": "dataTypeIds",
  "DATATYPECRITERIA_TYPE_DATATYPERANGE": "dataTypeRange",
  "DATATYPECRITERIA_TYPE_AND": "and",
  "DATATYPECRITERIA_TYPE_OR": "or",
  "DATATYPECRITERIA_TYPE_EXPRESSION": "expression",
  "DATATYPECRITERIA_TYPE_REFERENCE": "reference",
  "DATATYPECRITERIA_TYPE_LITERATE": "literate",
  "EXPRESSION_OPERAND_CONSTANT": "constant",
  "EXPRESSION_OPERAND_VARIABLE": "variable",
  "EXPRESSION_OPERAND_REFERENCE": "reference",
  "EXPRESSION_OPERAND_OPERATION": "operation",
  "EXPRESSION_OPERAND_DATAOPERATION": "dataoperation",
  "EXPRESSION_OPERAND_DATATYPEOPERATION": "datatypeoperation",
  "EXPRESSION_OPERAND_ATTRIBUTEOPERATION": "attributeoperation",
  "EXPRESSION_OPERAND_DATASOURCE": "datasource",
  "EXPRESSION_OPERAND_PATHOPERATION": "pathoperation",
  "EXPRESSION_OPERAND_NEWOPERATION": "newoperation",
  "EXPRESSION_VARIABLE_STATUS_OPEN": "open",
  "EXPRESSION_VARIABLE_STATUS_CLOSE": "close",
  "EXPRESSION_OPERAND_STATUS_NEW": "new",
  "EXPRESSION_OPERAND_STATUS_PROCESSED": "processed",
  "EXPRESSION_OPERAND_STATUS_INVALID": "invalid",
  "EXPRESSION_VARIABLE_THIS": "this",
  "EXPRESSION_VARIABLE_PARENT": "parent",
  "EXPRESSION_VARIABLE_ENTITY": "entity",
  "EXPRESSION_VARIABLE_DATA1": "data1",
  "EXPRESSION_VARIABLE_DATA2": "data2",
  "DATATYPE_RELATIONSHIPTYPE_SELF": "self",
  "DATATYPE_RELATIONSHIPTYPE_ROOT": "root",
  "DATATYPE_RELATIONSHIPTYPE_INTERMEDIA": "intermedia",
  "DATATYPE_CATEGARY_SIMPLE": "simple",
  "DATATYPE_CATEGARY_BLOCK": "block",
  "DATATYPE_CATEGARY_CONTAINER": "container",
  "DATATYPE_CATEGARY_ENTITY": "entity",
  "DATATYPE_CATEGARY_QUERYENTITY": "queryentity",
  "DATATYPE_CATEGARY_REFERENCE": "reference",
  "DATATYPE_CATEGARY_OBJECT": "object",
  "DATATYPE_CATEGARY_DATA": "data",
  "DATATYPE_CATEGARY_SERVICE": "service",
  "DATATYPE_TYPE_INTEGER": "integer",
  "DATATYPE_TYPE_FLOAT": "float",
  "DATATYPE_TYPE_BOOLEAN": "boolean",
  "DATATYPE_TYPE_STRING": "string",
  "DATATYPE_TYPE_CONTAINER_ENTITYATTRIBUTE": "normal",
  "DATATYPE_TYPE_CONTAINER_OPTIONS": "options",
  "DATATYPE_TYPE_CONTAINER_QUERY": "query",
  "DATATYPE_TYPE_CONTAINER_WRAPPER": "wrapper",
  "DATATYPE_TYPE_REFERENCE_NORMAL": "normal",
  "DATATYPE_TYPE_QUERYENTITY_NORMAL": "normal",
  "DATATYPE_PATHSEGMENT_PARENT": "parent",
  "DATATYPE_PATHSEGMENT_LINKED": "linked",
  "UIRESOURCE_TYPE_RESOURCE": "resource",
  "UIRESOURCE_TYPE_TAG": "tag",
  "UIRESOURCE_ROOTTYPE_RELATIVE": "relative",
  "UIRESOURCE_ROOTTYPE_ABSOLUTE": "absolute",
  "ENTITY_CRITICALVALUE_OTHER": "other",
  "ATTRIBUTE_PATH_CRITICAL": "critical",
  "ATTRIBUTE_PATH_ELEMENT": "element",
  "ATTRIBUTE_PATH_ENTITY": "entity",
  "ATTRIBUTE_PATH_EACH": "each",
  "OPTIONS_TYPE_STATIC": "static",
  "OPTIONS_TYPE_DYNAMIC": "dynamic",
  "OPTIONS_TYPE_DYNAMIC_EXPRESSION": "expression",
  "OPTIONS_TYPE_DYNAMIC_EXPRESSION_ATTRIBUTE": "attribute",
  "OPTIONS_TYPE_QUERY": "query",
  "OPTIONS_TYPE_COMPLEX": "complex",
  "CONFIGUREITEM_ENTITY_ISEMPTYONINIT": "emptyOnInit",
  "EVENTTYPE_ENTITY_OPERATION": 1,
  "EVENTTYPE_ENTITY_MODIFY": 2,
  "EVENTTYPE_ENTITY_NEW ": 3,
  "EVENTTYPE_ENTITY_CLEARUP ": 4,
  "EVENT_ENTITY_CHANGE ": "entityChange",
  "WRAPECLEARUP_PARM_SCOPE": "scope",
  "SORTING_ORDER_ASCEND": 0,
  "SORTING_ORDER_DESCEND": 1,
  "SORTING_TYPE_EXPRESSION": "expression",
  "SORTING_TYPE_ATTRIBUTE": "attribute",
  "SORTING_TYPE_PROGRAMMING": "programming",
  "COMPARE_LESS": -1,
  "COMPARE_LARGER": 1,
  "COMPARE_EQUAL": 0,
  "SERVICEDATA_METADATA_TRANSACTIONID": "transactionId",
  "ENTITYOPERATION_ATTR_ATOM_SET": "ENTITYOPERATION_ATTR_ATOM_SET",
  "ENTITYOPERATIONCODE_ATTR_ATOM_SET": 101,
  "ENTITYOPERATION_ATTR_CRITICAL_SET": "ENTITYOPERATION_ATTR_CRITICAL_SET",
  "ENTITYOPERATIONCODE_ATTR_CRITICAL_SET": 112,
  "ENTITYOPERATION_ATTR_ELEMENT_NEW": "ENTITYOPERATION_ATTR_ELEMENT_NEW",
  "ENTITYOPERATIONCODE_ATTR_ELEMENT_NEW": 113,
  "ENTITYOPERATION_ATTR_ELEMENT_DELETE": "ENTITYOPERATION_ATTR_ELEMENT_DELETE",
  "ENTITYOPERATIONCODE_ATTR_ELEMENT_DELETE": 153,
  "ENTITYOPERATION_ENTITY_OPERATIONS": "ENTITYOPERATION_ENTITY_OPERATIONS",
  "ENTITYOPERATIONCODE_ENTITY_OPERATIONS": 114,
  "ENTITYOPERATION_ENTITY_NEW": "ENTITYOPERATION_ENTITY_NEW",
  "ENTITYOPERATIONCODE_ENTITY_NEW": 102,
  "ENTITYOPERATION_ENTITY_DELETE": "ENTITYOPERATION_ENTITY_DELETE",
  "ENTITYOPERATIONCODE_ENTITY_DELETE": 103,
  "ENTITYOPERATION_TRANSACTION_START": "ENTITYOPERATION_TRANSACTION_START",
  "ENTITYOPERATIONCODE_TRANSACTION_START": 104,
  "ENTITYOPERATION_TRANSACTION_COMMIT": "ENTITYOPERATION_TRANSACTION_COMMIT",
  "ENTITYOPERATIONCODE_TRANSACTION_COMMIT": 105,
  "ENTITYOPERATION_TRANSACTION_ROLLBACK": "ENTITYOPERATION_TRANSACTION_ROLLBACK",
  "ENTITYOPERATIONCODE_TRANSACTION_ROLLBACK": 106,
  "ENTITYOPERATION_QUERY_ENTITY_ADD": "ENTITYOPERATION_QUERY_ENTITY_ADD",
  "ENTITYOPERATIONCODE_QUERY_ENTITY_ADD": 107,
  "ENTITYOPERATION_QUERY_ENTITY_REMOVE": "ENTITYOPERATION_QUERY_ENTITY_REMOVE",
  "ENTITYOPERATIONCODE_QUERY_ENTITY_REMOVE": 108,
  "ENTITYOPERATION_QUERY_ENTITY_MODIFY": "ENTITYOPERATION_QUERY_ENTITY_MODIFY",
  "ENTITYOPERATIONCODE_QUERY_ENTITY_MODIFY": 109,
  "ENTITYOPERATION_ATTR_REFERENCE_SET": "ENTITYOPERATION_ATTR_REFERENCE_SET",
  "ENTITYOPERATIONCODE_ATTR_REFERENCE_SET": 110,
  "ENTITYOPERATION_ENTITYATTR_ADD": "ENTITYOPERATION_ENTITYATTR_ADD",
  "ENTITYOPERATIONCODE_ENTITYATTR_ADD": 111,
  "ENTITYOPERATION_ENTITYATTR_REMOVE": "ENTITYOPERATION_ENTITYATTR_REMOVE",
  "ENTITYOPERATIONCODE_ENTITYATTR_REMOVE": 113,
  "UIRESOURCE_ATTRIBUTE_CONTEXT": "context",
  "UIRESOURCE_ATTRIBUTE_UIID": "nosliwid",
  "UIRESOURCE_ATTRIBUTE_EVENT": "event",
  "UIRESOURCE_ATTRIBUTE_DATABINDING": "data",
  "UIRESOURCE_TAG_PLACEHOLDER": "nosliw",
  "UIRESOURCE_CUSTOMTAG_KEYATTRIBUTE_PREFIX": "nosliw-",
  "UIRESOURCE_CUSTOMTAG_WRAPER_START_POSTFIX": "-tag-start",
  "UIRESOURCE_CUSTOMTAG_WRAPER_END_POSTFIX": "-tag-end",
  "UIRESOURCE_FUNCTION_EXCECUTEEXPRESSION": "excecuteExpression",
  "UIRESOURCE_UIEXPRESSIONFUNCTION_PREFIX": "expression",
  "UIRESOURCE_CONTEXTTYPE_PUBLIC": "public",
  "UIRESOURCE_CONTEXTTYPE_INTERNAL": "internal",
  "UIRESOURCE_CONTEXTTYPE_PRIVATE": "private",
  "UIRESOURCE_CONTEXTTYPE_EXCLUDED": "excluded",
  "UIRESOURCEMAN_SETTINGNAME_SCRIPTLOCATION": "temp.scriptLocation",
  "DATATYPEMAN_SETTINGNAME_SCRIPTLOCATION": "temp.scriptLocation",
  "REMOTESERVICE_LOGIN": "login",
  "REMOTESERVICE_GETUIRESOURCE": "getUIResource",
  "REMOTESERVICE_GETDATATYPES": "getDataTypes",
  "REMOTESERVICE_EXECUTEEXPRESSION": "executeExpression",
  "REMOTESERVICE_GETALLENTITYDEFINITIONS": "getAllEntityDefinitions",
  "REMOTESERVICE_GETENTITYDEFINITIONBYNAMES": "getEntityDefinitionByNames",
  "DATAACCESS_ENTITYSTATUS_NORMAL": 0,
  "DATAACCESS_ENTITYSTATUS_CHANGED": 1,
  "DATAACCESS_ENTITYSTATUS_NEW": 2,
  "DATAACCESS_ENTITYSTATUS_DEAD": 3,
  "APPLICATION_CONFIGURE_DATATYPE": "dataType",
  "APPLICATION_CONFIGURE_ENTITYDEFINITION": "entityDefinition",
  "APPLICATION_CONFIGURE_UIRESOURCE": "uiResource",
  "APPLICATION_CONFIGURE_UITAG": "uiTag",
  "APPLICATION_CONFIGURE_QUERYDEFINITION": "queryDefinition",
  "APPLICATION_CONFIGURE_USERENV": "userEnv",
  "APPLICATION_CONFIGURE_LOGGER": "logger",
  "REMOTESERVICE_TASKTYPE_NORMAL": "normal",
  "REMOTESERVICE_TASKTYPE_GROUP": "group",
  "REMOTESERVICE_TASKTYPE_GROUPCHILD": "groupchild",
  "REMOTESERVICE_GROUPTASK_MODE_ALL": "all",
  "REMOTESERVICE_GROUPTASK_MODE_ALWAYS": "always",
  "SERVICENAME_LOGIN": "login",
  "SERVICENAME_SERVICE": "service",
  "SERVICECOMMAND_GROUPREQUEST": "groupRequest",
  "SCRIPTTYPE_UIRESOURCE": "uiResource",
  "SCRIPTTYPE_DATAOPERATIONS": "dataOperations",
  "SCRIPTTYPE_UITAGS": "uiTags",
  "REFERENCE_TYPE_ABSOLUTE": 0,
  "REFERENCE_TYPE_RELATIVE": 1,
  "TESTRESULT_TYPE_SUITE": "SUITE",
  "TESTRESULT_TYPE_CASE": "CASE",
  "TESTRESULT_TYPE_ITEM": "ITEM",
  "TEST_TYPE_SUITE": "SUITE",
  "TEST_TYPE_CASE": "CASE",
  "TEST_TYPE_ITEM": "ITEM",
  "CONFIGURATION_DEFAULTBASE": "default",
  "STRINGABLE_ATOMICVALUETYPE_STRING": "string",
  "STRINGABLE_ATOMICVALUETYPE_BOOLEAN": "boolean",
  "STRINGABLE_ATOMICVALUETYPE_INTEGER": "integer",
  "STRINGABLE_ATOMICVALUETYPE_FLOAT": "float",
  "STRINGABLE_ATOMICVALUETYPE_ARRAY": "array",
  "STRINGABLE_ATOMICVALUETYPE_MAP": "map",
  "STRINGABLE_ATOMICVALUETYPE_OBJECT": "object",
  "STRINGABLE_VALUESTRUCTURE_ATOMIC": "atomic",
  "STRINGABLE_VALUESTRUCTURE_MAP": "map",
  "STRINGABLE_VALUESTRUCTURE_LIST": "list",
  "STRINGABLE_VALUESTRUCTURE_ENTITY": "entity",
  "STRINGABLE_VALUESTRUCTURE_OBJECT": "object",
  "STRINGALBE_VALUEINFO_ATOMIC": "atomic",
  "STRINGALBE_VALUEINFO_REFERENCE": "reference",
  "STRINGALBE_VALUEINFO_ENTITY": "entity",
  "STRINGALBE_VALUEINFO_ENTITYOPTIONS": "entityOptions",
  "STRINGALBE_VALUEINFO_LIST": "list",
  "STRINGALBE_VALUEINFO_MAP": "map",
  "STRINGALBE_VALUEINFO_OBJECT": "object",
  "STRINGALBE_VALUEINFO_MODE": "mode",
  "STRINGALBE_VALUEINFO_COLUMN_ATTRPATH_ABSOLUTE": "absolute",
  "STRINGALBE_VALUEINFO_COLUMN_ATTRPATH_PROPERTY": "property",
  "STRINGALBE_VALUEINFO_COLUMN_ATTRPATH_PROPERTYASPATH": "propertyAsPath",
  "UITAG_NAME_INCLUDE": "include",
  "UITAG_NAME_INCLUDE_PARM_SOURCE": "source",
  "UITAG_NAME_INCLUDE_PARM_CONTEXT": "context",
  "DATAOPERATION_VAR_TYPE_IN": "parm",
  "DATAOPERATION_VAR_TYPE_OUT": "out",
  "RUNTIME_RESOURCE_TYPE_OPERATION": "operation",
  "RUNTIME_RESOURCE_TYPE_DATATYPE": "datatype",
  "RUNTIME_RESOURCE_TYPE_CONVERTER": "converter",
  "RUNTIME_RESOURCE_TYPE_JSLIBRARY": "jslibrary",
  "RUNTIME_RESOURCE_TYPE_JSHELPER": "jshelper",
  "RUNTIME_RESOURCE_TYPE_JSGATEWAY": "jsGateway",
  "RUNTIME_RESOURCE_TYPE_UIRESOURCE": "uiResource",
  "RUNTIME_RESOURCE_TYPE_UITAG": "uiTag",
  "RUNTIME_RESOURCE_TYPE_MATCHER": "matcher",
  "RUNTIME_LANGUAGE_JS": "javascript",
  "RUNTIME_ENVIRONMENT_RHINO": "rhino",
  "RUNTIME_ENVIRONMENT_BROWSER": "browser",
  "RUNTIME_LANGUAGE_JS_GATEWAY": "runtime.gateway",
  "CATEGARY_NAME": "CATEGARY_NAME",
  "DATATASK_TYPE_EXPRESSION": "expression",
  "DATATASK_TYPE_DATASOURCE": "dataSource",
  "EXPRESSIONTASK_STEPTYPE_EXPRESSION": "expression",
  "EXPRESSIONTASK_STEPTYPE_BRANCH": "branch",
  "EXPRESSIONTASK_STEPTYPE_LOOP": "loop",
  "MINIAPPSERVICE_TYPE_DATASOURCE": "dataSource",
  "MINIAPPDATA_TYPE_SETTING": "setting",
  "MINIAPPUIENTRY_NAME_MAINMOBILE": "main_mobile",
  "MINIAPPUIENTRY_MAINMOBILE_MODULE_SETTING": "setting"
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createChildNode("COMMONCONSTANT", COMMONCONSTANT); 

})(packageObj);
