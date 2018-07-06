//get/create package
var packageObj = library;    

(function(packageObj){
	//get used node
//*******************************************   Start Node Definition  ************************************** 	

/**
 * 
 */
var COMMONATRIBUTECONSTANT=

{
  "REQUEST_GETUIRESOURCE_NAME": "name",
  "REQUEST_GETENTITYDEFINITIONBYNAMES_NAMES": "names",
  "REQUEST_GETDATATYPES_EXISTINGARRAY": "existingArray",
  "REQUEST_GETDATATYPES_REQUESTARRAY": "requestArray",
  "ENTITYNAME_ATTRIBUTENAME": "ENTITYNAME_ATTRIBUTENAME",
  "SERVICEDATA_CODE": "code",
  "SERVICEDATA_MESSAGE": "message",
  "SERVICEDATA_DATA": "data",
  "SERVICEDATA_METADATA": "metaData",
  "DATATYPEINFO_CATEGARY": "categary",
  "DATATYPEINFO_TYPE": "type",
  "DATATYPEINFO_KEY": "key",
  "DATATYPEINFO_ENTITYGROUPS": "entityGroups",
  "DATATYPEINFO_VERSION": "version",
  "DATATYPEINFO_CHILD_CATEGARY": "childCategary",
  "DATATYPEINFO_CHILD_TYPE": "childTYPE",
  "DATATYPEINFO_CHILD_ENTITYGROUPS": "childEntityGroups",
  "DATATYPE_DATATYPEINFO": "dataTypeInfo",
  "DATATYPE_PARENT": "parent",
  "DATATYPE_OPERATIONINFOS": "operationInfos",
  "DATATYPE_NEWOPERATIONINFOS": "newOperationInfos",
  "DATATYPE_OPERATIONSCRIPTS": "operationScripts",
  "DATAOPERATIONINFO_NAME": "name",
  "DATAOPERATIONINFO_DESCRIPTION": "description",
  "DATAOPERATIONINFO_CONVERTPATH": "convertPath",
  "DATAOPERATIONINFO_OUT": "out",
  "DATAOPERATIONINFO_INS": "ins",
  "DATAOPERATIONINFO_DEPENDENTDATATYPES": "dependentDataTypes",
  "DATA_DATATYPEINFO": "dataTypeInfo",
  "DATA_VALUE": "value",
  "WRAPER_DATA": "data",
  "WRAPER_DATATYPE": "dataType",
  "WRAPER_INFO": "info",
  "OPERATIONINFO_OPERATIONID": "operationId",
  "OPERATIONINFO_OPERATION": "operation",
  "OPERATIONINFO_AUTOCOMMIT": "isAutoCommit",
  "OPERATIONINFO_VALIDATION": "isValidation",
  "OPERATIONINFO_SCOPE": "scope",
  "OPERATIONINFO_ROOTOPERATION": "rootOperation",
  "OPERATIONINFO_SUBMITABLE": "submitable",
  "OPERATIONINFO_EXTRA": "extra",
  "OPERATIONINFO_ENTITYID": "entityId",
  "OPERATIONINFO_ATTRIBUTEPATH": "attributePath",
  "OPERATIONINFO_DATA": "data",
  "OPERATIONINFO_TRANSACTIONID": "transactionId",
  "OPERATIONINFO_REFERENCEPATH": "referencePath",
  "OPERATIONINFO_WRAPER": "wraper",
  "OPERATIONINFO_ENTITYDEFINITION": "entityDefinition",
  "OPERATIONINFO_ATTRIBUTEDEFINITION": "attributeDefinition",
  "OPERATIONINFO_ENTITYTYPE": "entityType",
  "OPERATIONINFO_ELEMENTID": "elementId",
  "OPERATIONINFO_QUERYNAME": "queryName",
  "OPERATIONINFO_QUERYENTITYWRAPER": "queryEntityWraper",
  "OPERATIONINFO_VALUE": "value",
  "OPERATIONINFO_REFENTITYID": "refEntityID",
  "OPERATIONINFO_PARMS": "parms",
  "OPERATIONINFO_ENTITYOPERATIONS": "entityOperations",
  "ENTITYATTRDEF_NAME": "name",
  "ENTITYATTRDEF_FULLNAME": "fullName",
  "ENTITYATTRDEF_CRITICALVALUE": "criticalValue",
  "ENTITYATTRDEF_DESCRIPTION": "description",
  "ENTITYATTRDEF_ISEMPTYONINIT": "isEmptyOnInit",
  "ENTITYATTRDEF_DATATYPEDEFINFO": "dataTypeDefInfo",
  "ENTITYATTRDEF_VALIDATION": "validation",
  "ENTITYATTRDEF_RULES": "rules",
  "ENTITYATTRDEF_OPTIONS": "options",
  "ENTITYATTRDEF_EVENTS": "events",
  "ENTITYATTRDEF_ISCRITICAL": "isCritical",
  "ENTITYATTRDEF_DEFAULTVALUE": "defaultValue",
  "ENTITYATTRDEF_ELEMENT": "element",
  "ENTITYID_ID": "id",
  "ENTITYID_ENTITYTYPE": "entityType",
  "ENTITYID_KEY": "key",
  "DATAWRAPER_CHILDDATATYPE": "childDataType",
  "DATAWRAPER_ID": "id",
  "DATAWRAPER_ENTITYID": "entityID",
  "DATAWRAPER_ATTRPATH": "attrPath",
  "DATAWRAPER_PARENTENTITYID": "parentEntityID",
  "DATAWRAPER_ATTRCONFIGURE": "attrConfigure",
  "ENTITYPATH_PATH": "path",
  "ENTITYPATH_ENTITYID": "entityID",
  "ENTITYPATH_EXPECTPATH": "expectPath",
  "ENTITYPATHINFO_ENTITYPATH": "entityPath",
  "ENTITYPATHINFO_PATHSEGMENTS": "pathSegments",
  "ENTITYPATHINFO_DATA": "data",
  "QUERYENTITYATTRIBUTEWRAPER_ENTITYPATH": "entityPath",
  "QUERYDEFINITION_NAME": "name",
  "QUERYDEFINITION_EXPRESSIONINFO": "expressionInfo",
  "QUERYDEFINITION_PROJECTATTRIBUTES": "projectAttributes",
  "QUERYPROJECTATTRIBUTE_ENTITYNAME": "entityName",
  "QUERYPROJECTATTRIBUTE_ATTRIBUTE": "attribute",
  "ENTITYMANAGER_PERSISTANT": "persistent",
  "ENTITYMANAGER_TRANSACTIONS": "transactions",
  "CONSTANTGROUP_TYPE": "type",
  "CONSTANTGROUP_FILEPATH": "filepath",
  "CONSTANTGROUP_CLASSNAME": "classname",
  "CONSTANTGROUP_PACKAGENAME": "packagename",
  "CONSTANTGROUP_DEFINITIONS": "definitions",
  "SERVICEDATA_SERVICEDATA_CODE": "code",
  "SERVICEDATA_SERVICEDATA_MESSAGE": "message",
  "SERVICEDATA_SERVICEDATA_DATA": "data",
  "SERVICEDATA_SERVICEDATA_METADATA": "metaData",
  "STRINGABLEVALUE_STRUCTURE": "structure",
  "STRINGABLEVALUE_TYPE": "type",
  "STRINGABLEVALUE_SUBTYPE": "subType",
  "STRINGABLEVALUE_STRINGVALUE": "stringValue",
  "STRINGABLEVALUE_RESOLVED": "resolved",
  "STRINGABLEVALUE_VALUE": "atomic_value",
  "STRINGABLEVALUE_ELEMENTS": "elements",
  "STRINGABLEVALUE_PROPERTIES": "properties",
  "STRINGABLEVALUE_ID": "id",
  "DATATYPECRITERIA_TYPE": "type",
  "DATATYPECRITERIA_CHILDREN": "children",
  "DATATYPECRITERIA_DATATYPEID": "dataTypeId",
  "DATATYPECRITERIA_ELEMENTDATATYPECRITERIA": "elementDataTypeCriteria",
  "DATATYPECRITERIA_IDSCRITERIA": "idsCriteria",
  "DATATYPECRITERIA_DATATYPEFROM": "dataTypeFrom",
  "DATATYPECRITERIA_DATATYPETO": "dataTypeTo",
  "DATASOURCEMANAGER_GATEWAY_DATASOURCE": "dataSource",
  "DEFINITION_NAME": "name",
  "DEFINITION_IMPLEMENTATION": "implementation",
  "DEFINITION_DESCRIPTION": "description",
  "DEFINITION_PARMS": "parms",
  "DEFINITION_OUTPUT": "output",
  "DEFINITION_CONFIGURE": "configure",
  "DEFINITIONPARM_NAME": "name",
  "DEFINITIONPARM_DESCRIPTION": "description",
  "DEFINITIONPARM_CRITERIA": "criteria",
  "DEFINITIONPARM_DEFAULT": "default",
  "GATEWAYDATASOURCE_COMMAND_GETDATA": "getData",
  "GATEWAYDATASOURCE_COMMAND_GETDATA_NAME": "name",
  "GATEWAYDATASOURCE_COMMAND_GETDATA_PARMS": "parms",
  "DEFINITIONTASK_DATASOURCE": "dataSource",
  "DEFINITIONTASK_PARMS": "parms",
  "_EXPRESSION": "expression",
  "_OPERAND": "operand",
  "_VARIABLENAMES": "variableNames",
  "_REFERENCENAMES": "referenceNames",
  "MATCHER_REVERSE": "reverse",
  "MATCHER_DATATYPEID": "dataTypeId",
  "MATCHER_RELATIONSHIP": "relationship",
  "MATCHER_SUBMATCHERS": "subMatchers",
  "VARIABLEINFO_CRITERIA": "criteria",
  "VARIABLEINFO_STATUS": "status",
  "VARIABLEINFO_INFO": "info",
  "EXPRESSION_VARIABLEINFOS": "variableInfos",
  "DATA_DATATYPEID": "dataTypeId",
  "DATATYPE_INFO": "info",
  "DATATYPE_NAME": "name",
  "DATATYPE_PARENTSINFO": "parentsInfo",
  "DATATYPE_LINKEDVERSION": "linkedVersion",
  "DATATYPE_ISCOMPLEX": "isComplex",
  "DATATYPEID_NAME": "name",
  "DATATYPEID_VERSION": "version",
  "DATATYPEID_PARMS": "parms",
  "DATATYPEID_FULLNAME": "fullName",
  "DATATYPEOPERATION_OPERATIONINFO": "operationInfo",
  "DATATYPEOPERATION_TARGETDATATYPE": "targetDataType",
  "DATATYPEVERSION_MAJOR": "major",
  "DATATYPEVERSION_MINOR": "minor",
  "DATATYPEVERSION_REVISION": "revision",
  "DATATYPEVERSION_NAME": "name",
  "DATA_VALUEFORMAT": "valueFormat",
  "DATAOPERATIONINFO_TYPE": "type",
  "DATAOPERATIONINFO_PAMRS": "parms",
  "DATAOPERATIONINFO_OUTPUT": "output",
  "DATAOPERATIONINFO_INFO": "info",
  "DATATYPEID_OPERATION": "operation",
  "DATAOPERATIONOUTPUTINFO_DESCRIPTION": "description",
  "DATAOPERATIONOUTPUTINFO_CRITERIA": "criteria",
  "OPERATIONPARM_NAME": "name",
  "OPERATIONPARM_DATA": "data",
  "DATAOPERATIONPARMINFO_NAME": "name",
  "DATAOPERATIONPARMINFO_ISBASE": "isBase",
  "DATATYPERELATIONSHIP_PATH": "path",
  "DATATYPERELATIONSHIP_TARGET": "target",
  "DATATYPERELATIONSHIP_SOURCE": "source",
  "OPERAND_TYPE": "type",
  "OPERAND_STATUS": "status",
  "OPERAND_CHILDREN": "children",
  "OPERAND_DATATYPEINFO": "dataTypeInfo",
  "OPERAND_CONVERTERS": "converters",
  "OPERAND_OUTPUTCRITERIA": "outputCriteria",
  "OPERAND_NAME": "name",
  "OPERAND_DATA": "data",
  "OPERAND_DATATYPEID": "dataTypeId",
  "OPERAND_OPERATION": "operation",
  "OPERAND_PARMS": "parms",
  "OPERAND_BASE": "base",
  "OPERAND_MATCHERSPARMS": "matchersParms",
  "OPERAND_REFERENCENAME": "referenceName",
  "OPERAND_VARIABLENAME": "variableName",
  "EXPRESSION_ID": "id",
  "EXPRESSION_OPERAND": "operand",
  "EXPRESSION_VARIABLESMATCHERS": "variablesMatchers",
  "RESOURCE_ID": "id",
  "RESOURCE_RESOURCEDATA": "resourceData",
  "RESOURCE_INFO": "info",
  "RESOURCEDEPENDENT_ALIAS": "alias",
  "RESOURCEDEPENDENT_ID": "id",
  "RESOURCEID_ID": "id",
  "RESOURCEID_TYPE": "type",
  "RESOURCEINFO_ID": "id",
  "RESOURCEINFO_INFO": "info",
  "RESOURCEINFO_DEPENDENCY": "dependency",
  "RESOURCEINFO_CHILDREN": "children",
  "RUNTIMETASKEXECUTECONVERTER_DATAT": "data",
  "RUNTIMETASKEXECUTECONVERTER_MATCHERS": "matchers",
  "RUNTIMETASKEXECUTEDATAOPERATION_DATATYPEID": "dataTypeId",
  "RUNTIMETASKEXECUTEDATAOPERATION_OPERATION": "operation",
  "RUNTIMETASKEXECUTEDATAOPERATION_PARMS": "parms",
  "RUNTIMETASKEXECUTEEXPRESSION_EXPRESSION": "expression",
  "RUNTIMETASKEXECUTEEXPRESSION_VARIABLESVALUE": "variablesValue",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION": "loadTestExpression",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION_ELEMENT_SUITE": "suite",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION_ELEMENT_EXPRESSIONNAME": "expressionName",
  "GATEWAYLOADTESTEXPRESSION_COMMAND_LOADTESTEXPRESSION_ELEMENT_VARIABLES": "variables",
  "RESPONSEGATEWAYLOADTESTEXPRESSION_EXPRESSION": "expression",
  "RESPONSEGATEWAYLOADTESTEXPRESSION_VARIABLESVALUE": "variablesValue",
  "_COMMAND_GETCHILDCRITERIA": "getChildCriteria",
  "_COMMAND_GETCHILDCRITERIA_CRITERIA": "criteria",
  "_COMMAND_GETCHILDCRITERIA_CHILDNAME": "childName",
  "_COMMAND_GETCHILDRENCRITERIA": "getChildrenCriteria",
  "_COMMAND_GETCHILDRENCRITERIA_CRITERIA": "criteria",
  "_COMMAND_ADDCHILDCRITERIA": "addChildCriteria",
  "_COMMAND_ADDCHILDCRITERIA_CRITERIA": "criteria",
  "_COMMAND_ADDCHILDCRITERIA_CHILDNAME": "childName",
  "_COMMAND_ADDCHILDCRITERIA_CHILD": "child",
  "_COMMAND_GETOUTPUTCRITERIA": "getOutputCriteria",
  "_COMMAND_GETOUTPUTCRITERIA_EXPRESSION": "expression",
  "_COMMAND_GETOUTPUTCRITERIA_CONSTANTS": "constants",
  "_COMMAND_GETOUTPUTCRITERIA_VARIABLESCRITERIA": "variablesCriteria",
  "_COMMAND_EXECUTEEXPRESSION": "executeExpression",
  "_COMMAND_EXECUTEEXPRESSION_EXPRESSION": "expression",
  "_COMMAND_EXECUTEEXPRESSION_CONSTANTS": "constants",
  "_COMMAND_EXECUTEEXPRESSION_VARIABLESVALUE": "variablesValue",
  "GATEWAYRESOURCE_COMMAND_DISCOVERRESOURCES": "requestDiscoverResources",
  "GATEWAYRESOURCE_COMMAND_DISCOVERRESOURCES_RESOURCEIDS": "resourceIds",
  "GATEWAYRESOURCE_COMMAND_DISCOVERANDLOADRESOURCES": "requestDiscoverAndLoadResources",
  "GATEWAYRESOURCE_COMMAND_DISCOVERANDLOADRESOURCES_RESOURCEIDS": "resourceIds",
  "GATEWAYRESOURCE_COMMAND_LOADRESOURCES": "requestLoadResources",
  "GATEWAYRESOURCE_COMMAND_LOADRESOURCES_RESOURCEINFOS": "resourceInfos",
  "GATEWAYOUTPUT_SCRIPTS": "scripts",
  "GATEWAYOUTPUT_DATA": "data",
  "JSSCRIPTINFO_NAME": "name",
  "JSSCRIPTINFO_FILE": "file",
  "JSSCRIPTINFO_SCRIPT": "script",
  "_VALUE": "value",
  "RUNTIME_NODENAME_GATEWAY": "gatewayObj",
  "RUNTIME_GATEWAY_RESOURCE": "resources",
  "RUNTIME_GATEWAY_CRITERIA": "criteria",
  "RUNTIME_GATEWAY_DISCOVERY": "discovery",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERRESOURCES": "requestDiscoverResources",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERRESOURCES_RESOURCEIDS": "resourceIds",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERANDLOADRESOURCES": "requestDiscoverAndLoadResources",
  "RUNTIMEGATEWAYJS_REQUEST_DISCOVERANDLOADRESOURCES_RESOURCEIDS": "resourceIds",
  "RUNTIMEGATEWAYJS_REQUEST_LOADRESOURCES": "requestLoadResources",
  "RUNTIMEGATEWAYJS_REQUEST_LOADRESOURCES_RESOURCEINFOS": "resourceInfos",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS": "getExpressions",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_EXPRESSIONS": "expressions",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_ELEMENT_SUITE": "suite",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_ELEMENT_EXPRESSIONNAME": "expressionName",
  "RUNTIMEGATEWAYJS_REQUEST_GETEXPRESSIONS_ELEMENT_VARIABLES": "variables",
  "_NAME": "name",
  "_VERSION": "version",
  "_DATATYPENAME": "dataTypeName",
  "_GATEWAY": "gateway",
  "RESOURCEDATAJSLIBRARY_URIS": "uris",
  "_OPERATIONID": "operationId",
  "_OPERATIONNAME": "operationName",
  "_TYPE": "type",
  "_TRUERESULT": "true",
  "_FALSERESULT": "false",
  "_EXIT": "exit",
  "_OUTPUTVARIABLE": "outputVariable",
  "_CONTAINER": "container",
  "_ELEMENTVARIABLE": "elementVariable",
  "_EXECUTETASK": "executeTask",
  "_BREAK": "break",
  "DEFINITIONTASK_STEPS": "steps",
  "DEFINITIONTASK_REFERENCES": "references",
  "_REFERENCE": "reference",
  "_VARIABLESMAP": "variablesMap",
  "_DATA": "data",
  "_VARIABLENAME": "variableName",
  "_NEXT": "next",
  "_DESCRIPTION": "description",
  "_VARIABLES": "variables",
  "_CONSTANTS": "constants",
  "_CHILDREN": "children",
  "DEFINITIONTASK_TYPE": "type",
  "DEFINITIONTASK_INFO": "info",
  "DEFINITIONTASKSUITE_TASKS": "tasks",
  "DEFINITIONTASKSUITE_VARIABLESDATA": "variablesData",
  "DEFINITIONTASKSUITE_RESULT": "result",
  "DATATYPE_OPERATIONS": "operations",
  "STRINGABLEVALUE_DESCRIPTION": "description",
  "STRINGABLEVALUE_COMPLEX": "complex",
  "DATAOPERATIONINFO_OPERATIONID": "operationId",
  "_SOURCE": "source",
  "_RELATIONSHIPS": "relationship",
  "DATAOPERATIONINFO_BASEPARM": "baseParm",
  "DATAOPERATIONINFO_DATATYPNAME": "dataTypeName",
  "STRINGABLEVALUE_DATATYPEID": "dataTypeId",
  "STRINGABLEVALUE_OPERATIONID": "operationId",
  "STRINGABLEVALUE_SOURCEDATATYPE": "sourceDataType",
  "STRINGABLEVALUE_TARGETDATATYPE": "targetDataType",
  "STRINGABLEVALUE_PATH": "path",
  "STRINGABLEVALUE_TARGETTYPE": "targetType",
  "RUNTIME_GATEWAY_LOADLIBRARIES": "loadLibraries",
  "RUNTIME_GATEWAY_TESTEXPRESSION": "testExpression",
  "RUNTIME_GATEWAY_DATASOURCE": "dataSource",
  "STRINGABLEVALUE_RESOURCEID": "resourceId",
  "STRINGABLEVALUE_DEPENDENCY": "dependency",
  "RESOURCEMANAGERJSOPERATION_INFO_OPERATIONINFO": "operationInfo",
  "_PARMS": "parms",
  "APPSERVLET_COMMAND_LOGIN": "login",
  "APPSERVLET_COMMAND_LOADMINIAPP": "loadMiniApp",
  "APPSERVLET_COMMAND_LOADMINIAPP_APPID": "appId",
  "APPSERVLET_COMMAND_LOADMINIAPP_USERID": "userId",
  "APPSERVLET_COMMAND_LOADMINIAPP_ENTRY": "entry",
  "APPSERVLET_COMMAND_CREATEDATA": "createData",
  "APPSERVLET_COMMAND_CREATEDATA_USERID": "userId",
  "APPSERVLET_COMMAND_CREATEDATA_APPID": "appId",
  "APPSERVLET_COMMAND_CREATEDATA_DATANAME": "dataName",
  "APPSERVLET_COMMAND_CREATEDATA_DATAINFO": "dataInfo",
  "APPSERVLET_COMMAND_UPDATEDATA": "updateData",
  "APPSERVLET_COMMAND_UPDATEDATA_ID": "id",
  "APPSERVLET_COMMAND_UPDATEDATA_DATAINFO": "dataInfo",
  "APPSERVLET_COMMAND_DELETEDATA": "deleteData",
  "APPSERVLET_COMMAND_DELETEDATA_DATATYPE": "dataType",
  "APPSERVLET_COMMAND_DELETEDATA_ID": "id",
  "REQUESTINFO_CLIENTID": "clientId",
  "REQUESTINFO_COMMAND": "command",
  "REQUESTINFO_PARMS": "parms",
  "SERVICEINFO_SERVICE_COMMAND": "command",
  "SERVICEINFO_SERVICE_PARMS": "parms",
  "SERVICESERVLET_REQUEST_TYPE": "type",
  "SERVICESERVLET_REQUEST_SERVICE": "service",
  "SERVICESERVLET_REQUEST_MODE": "mode",
  "SERVICESERVLET_REQUEST_CHILDREN": "children",
  "CONTEXT_ELEMENTS": "elements",
  "CONTEXTNODE_DEFINITION": "definition",
  "CONTEXTNODE_CHILDREN": "children",
  "CONTEXTNODEROOT_TYPE": "type",
  "CONTEXTNODEROOT_DEFAULT": "default",
  "CONTEXTNODEROOT_PATH": "path",
  "CONTEXTNODEROOT_MATCHERS": "matchers",
  "CONTEXTNODEROOT_REVERSEMATCHERS": "reverseMatchers",
  "CONTEXTPATH_ROOTNAME": "rootEleName",
  "CONTEXTPATH_PATH": "path",
  "CONTEXTVARIABLE_NAME": "name",
  "CONTEXTVARIABLE_PATH": "path",
  "EMBEDEDSCRIPTEXPRESSION_UIID": "uiId",
  "EMBEDEDSCRIPTEXPRESSION_SCRIPTEXPRESSIONS": "scriptExpressions",
  "EMBEDEDSCRIPTEXPRESSION_SCRIPTFUNCTION": "scriptFunction",
  "_EMBEDEDEXPRESSION": "embededExpression",
  "_VARIABLESVALUE": "variablesValue",
  "_SCRIPTEXPRESSION": "scriptExpression",
  "SCRIPTEXPRESSION_DEFINITION": "definition",
  "SCRIPTEXPRESSION_ID": "id",
  "SCRIPTEXPRESSION_SCRIPTFUNCTION": "scriptFunction",
  "SCRIPTEXPRESSION_EXPRESSIONS": "expressions",
  "SCRIPTEXPRESSION_VARIABLENAMES": "variableNames",
  "DEFINITIONUIMODULE_ID": "id",
  "DEFINITIONUIMODULE_PAGES": "pages",
  "DEFINITIONUIMODULE_MODULEENTRIES": "moduleEntries",
  "DEFINITIONUIMODULEENTRY_PAGE": "page",
  "DEFINITIONUIMODULEENTRY_CONTEXT": "context",
  "UIMODULEENTRY_ENTRYPAGE": "entryPage",
  "UIMODULEENTRY_PAGES": "pages",
  "UIMODULEENTRY_UIRESOURCEPAGES": "uiResourcePages",
  "UIMODULEENTRY_DATA": "data",
  "CONSTANTDEF_LITERATE": "literate",
  "CONSTANTDEF_PROCESSED": "processed",
  "CONSTANTDEF_VALUE": "value",
  "ELEMENTEVENT_UIID": "uiId",
  "ELEMENTEVENT_EVENT": "event",
  "ELEMENTEVENT_FUNCTION": "function",
  "ELEMENTEVENT_SELECTION": "selection",
  "EMBEDEDSCRIPTEXPRESSION_ATTRIBUTE": "attribute",
  "UIRESOURCEDEFINITION_ID": "id",
  "UIRESOURCEDEFINITION_CONTEXT": "context",
  "UIRESOURCEDEFINITION_TYPE": "type",
  "UIRESOURCEDEFINITION_SCRIPTEXPRESSIONSINCONTENT": "scriptExpressionsInContent",
  "UIRESOURCEDEFINITION_SCRIPTEXPRESSIONINATTRIBUTES": "scriptExpressionInAttributes",
  "UIRESOURCEDEFINITION_SCRIPTEXPRESSIONINTAGATTRIBUTES": "scriptExpressionTagAttributes",
  "UIRESOURCEDEFINITION_SCRIPT": "script",
  "UIRESOURCEDEFINITION_HTML": "html",
  "UIRESOURCEDEFINITION_ELEMENTEVENTS": "elementEvents",
  "UIRESOURCEDEFINITION_TAGEVENTS": "tagEvents",
  "UIRESOURCEDEFINITION_UITAGS": "uiTags",
  "UIRESOURCEDEFINITION_ATTRIBUTES": "attributes",
  "UIRESOURCEDEFINITION_SCRIPTFACTORYNAME": "scriptFactoryName",
  "UIRESOURCEDEFINITION_UITAGLIBS": "uiTagLibs",
  "UIRESOURCEDEFINITION_CONSTANTS": "constants",
  "UIRESOURCEDEFINITION_EXPRESSIONS": "expressions",
  "UIRESOURCEDEFINITION_EXPRESSIONUNIT": "expressionUnit",
  "UIRESOURCEDEFINITION_TAGNAME": "tagName",
  "UITAGDEFINITION_NAME": "name",
  "UITAGDEFINITION_SCRIPT": "script",
  "UITAGDEFINITION_ATTRIBUTES": "attributes",
  "UITAGDEFINITION_CONTEXT": "context",
  "UITAGDEFINITION_REQUIRES": "requires",
  "UITAGDEFINITIONCONTEXT_INHERIT": "inherit",
  "CONTEXTNODE_PATH": "path",
  "INSTANCEMINIAPPDATA_TYPE": "type",
  "INSTANCEMINIAPPDATA_DATA": "data",
  "INSTANCEMINIAPPDATA_ID": "id",
  "INSTANCEMINIAPPDATA_STATUS": "status",
  "INSTANCEMINIAPPDATA_VERSION": "version",
  "DEFINITIONMINIAPP_ID": "id",
  "DEFINITIONMINIAPP_UIMODULES": "uiModules",
  "DEFINITIONMINIAPP_UIENTRIES": "uiEntries",
  "DEFINITIONMINIAPP_DATA": "data",
  "DEFINITIONMINIAPP_SERVICES": "services",
  "DEFINITIONMINIAPPMODULEENTRY_MODULE": "module",
  "DEFINITIONMINIAPPMODULEENTRY_ENTRY": "entry",
  "DEFINITIONMINIAPPMODULEENTRY_DATA": "data",
  "DEFINITIONMINIAPPMODULEENTRY_SERVICE": "service",
  "DEFINITIONMINIAPPUIENTRY_UIMODULEENTRIES": "uiModuleEntries",
  "_COMMAND_LOGIN": "login",
  "_COMMAND_LOADMINIAPP": "loadMiniApp",
  "_COMMAND_LOADMINIAPP_APPID": "appId",
  "_COMMAND_LOADMINIAPP_USERID": "userId",
  "_COMMAND_LOADMINIAPP_ENTRY": "entry",
  "_COMMAND_CREATEDATA": "saveData",
  "_COMMAND_CREATEDATA_USERID": "userId",
  "_COMMAND_CREATEDATA_APPID": "appId",
  "_COMMAND_CREATEDATA_DATANAME": "dataName",
  "_COMMAND_CREATEDATA_DATAINFO": "dataInfo",
  "_COMMAND_UPDATEDATA": "updateData",
  "_COMMAND_UPDATEDATA_ID": "id",
  "_COMMAND_UPDATEDATA_DATAINFO": "dataInfo",
  "_COMMAND_DELETEDATA": "deleteData",
  "_COMMAND_DELETEDATA_DATATYPE": "dataType",
  "_COMMAND_DELETEDATA_ID": "id",
  "INSTANCEMINIAPPUIENTRY_ID": "id",
  "INSTANCEMINIAPPUIENTRY_UIMODULES": "uiModules",
  "INSTANCEMINIAPPUIENTRY_RESOURCES": "resources",
  "INSTANCEMINIAPPUIENTRY_DATA": "data",
  "INSTANCEUIMODULE_UIMODULEENTRY": "uiModuleEntry",
  "INSTANCEUIMODULE_DATA": "data",
  "INSTANCEUIMODULE_SERVICE": "service",
  "DEFINITIONMINIAPPSERVICE_TYPE": "type",
  "DEFINITIONMINIAPPSERVICEDATASOURCE_DATASOURCEID": "dataSourceId",
  "GROUP_ID": "id",
  "GROUP_NAME": "name",
  "USER_ID": "id",
  "USER_NAME": "name",
  "USER_GROUPS": "groups",
  "USERGROUPMINIAPP_GROUP": "group",
  "USERGROUPMINIAPP_MINIAPPS": "miniApps",
  "USERINFO_USER": "user",
  "USERINFO_MINIAPPS": "miniApps",
  "USERINFO_GROUPMINIAPP": "groupMiniApp",
  "USERMINIAPPINFO_ID": "id",
  "USERMINIAPPINFO_APPID": "appId",
  "USERMINIAPPINFO_APPNAME": "appName",
  "USERMINIAPPINFO_GROUPID": "groupId"
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createChildNode("COMMONATRIBUTECONSTANT", COMMONATRIBUTECONSTANT); 

})(packageObj);
