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
  "ENTITYINFO_NAME": "name",
  "ENTITYINFO_DESCRIPTION": "description",
  "ENTITYINFO_INFO": "info",
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
  "VARIABLEINFO_CRITERIA": "criteria",
  "VARIABLEINFO_STATUS": "status",
  "VARIABLEINFO_INFO": "info",
  "_EXPRESSION": "expression",
  "_OPERAND": "operand",
  "_VARIABLENAMES": "variableNames",
  "_REFERENCENAMES": "referenceNames",
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
  "MATCHER_REVERSE": "reverse",
  "MATCHER_DATATYPEID": "dataTypeId",
  "MATCHER_RELATIONSHIP": "relationship",
  "MATCHER_SUBMATCHERS": "subMatchers",
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
  "DEFINITIONACTIVITY_OUTPUT": "output",
  "EXECUTABLEACTIVITY_RESULTNAME": "resultName",
  "DEFINITIONACTIVITY_EXPRESSION": "expression",
  "EXECUTABLEACTIVITY_SCRIPTEXPRESSION": "scriptExpression",
  "EXECUTABLEACTIVITY_SCRIPTEXPRESSIONSCRIPT": "scriptExpressionScript",
  "DEFINITIONACTIVITY_PROVIDER": "provider",
  "DEFINITIONACTIVITY_PARMMAPPING": "parmMapping",
  "EXECUTABLEACTIVITY_SERVICE": "service",
  "EXECUTABLEACTIVITY_PROVIDER": "provider",
  "DEFINITIONACTIVITY_FLOW": "flow",
  "EXECUTABLEACTIVITY_FLOW": "flow",
  "DEFINITIONACTIVITY_TYPE": "type",
  "DEFINITIONACTIVITY_INPUT": "input",
  "DEFINITIONACTIVITY_RESULT": "result",
  "DEFINITIONPROCESS_CONTEXT": "context",
  "DEFINITIONPROCESS_ACTIVITY": "activity",
  "DEFINITIONPROCESS_SERVICEPROVIDER": "activity",
  "DEFINITIONPROCESSSUITE_CONTEXT": "context",
  "DEFINITIONPROCESSSUITE_PROCESS": "process",
  "DEFINITIONRESULTACTIVITYNORMAL_FLOW": "flow",
  "DEFINITIONRESULTACTIVITYNORMAL_OUTPUT": "output",
  "DEFINITIONSEQUENCEFLOW_TARGET": "target",
  "EXECUTABLEACTIVITY_TYPE": "type",
  "EXECUTABLEACTIVITY_ID": "id",
  "EXECUTABLEACTIVITY_DEFINITION": "definition",
  "EXECUTABLEACTIVITY_INPUT": "input",
  "EXECUTABLEACTIVITY_RESULT": "result",
  "EXECUTABLEPROCESS_DEFINITION": "definition",
  "EXECUTABLEPROCESS_ID": "id",
  "EXECUTABLEPROCESS_ACTIVITY": "activity",
  "EXECUTABLEPROCESS_STARTACTIVITYID": "startActivityId",
  "EXECUTABLEPROCESS_CONTEXT": "context",
  "EXECUTABLEPROCESS_RESULT": "result",
  "EXECUTABLEPROCESS_INITSCRIPT": "initScript",
  "EXECUTABLERESULTACTIVITYNORMAL_FLOW": "flow",
  "EXECUTABLERESULTACTIVITYNORMAL_DATAASSOCIATION": "dataAssociation",
  "PLUGINACTIVITY_TYPE": "type",
  "PLUGINACTIVITY_SCRIPT": "script",
  "PLUGINACTIVITY_DEFINITION": "definition",
  "PLUGINACTIVITY_PROCESSOR": "processor",
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
  "_EMBEDEDEXPRESSION": "embededExpression",
  "_VARIABLESVALUE": "variablesValue",
  "_SCRIPTEXPRESSION": "scriptExpression",
  "_TYPE": "type",
  "_OUTPUTMAPPING": "outputMapping",
  "_INPUTMAPPING": "inputMapping",
  "_TASK": "task",
  "EXECUTABLEDATAASSOCIATION_TYPE": "type",
  "EXECUTABLEGROUPDATAASSOCIATION_ELEMENT": "element",
  "EXECUTABLEWRAPPERTASK_OUTPUTMAPPING": "outputMapping",
  "EXECUTABLEWRAPPERTASK_INPUTMAPPING": "inputMapping",
  "EXECUTABLEWRAPPERTASK_TASK": "task",
  "ENTITYINFO_ASSOCIATION": "association",
  "EXECUTABLEASSOCIATION_CONTEXT": "context",
  "EXECUTABLEASSOCIATION_PATHMAPPING": "pathMapping",
  "EXECUTABLEASSOCIATION_FLATINPUT": "flatInput",
  "EXECUTABLEASSOCIATION_FLATOUTPUT": "flatOutput",
  "EXECUTABLEASSOCIATION_OUTPUTMATCHERS": "outputMatchers",
  "EXECUTABLEASSOCIATION_CONVERTFUNCTION": "convertFunction",
  "EXECUTABLEDATAASSOCIATION_NAME": "name",
  "EXECUTABLEDATAASSOCIATION_DEFINITION": "definition",
  "EXECUTABLEDATAASSOCIATION_ASSOCIATION": "association",
  "CONTEXT_ELEMENT": "element",
  "CONTEXTDEFINITIONELEMENT_TYPE": "type",
  "CONTEXTDEFINITIONELEMENT_PROCESSED": "processed",
  "CONTEXTDEFINITIONLEAFCONSTANT_VALUE": "value",
  "CONTEXTDEFINITIONELEMENT_CRITERIA": "criteria",
  "CONTEXTDEFINITIONELEMENT_PATH": "path",
  "CONTEXTDEFINITIONELEMENT_PARENT": "parent",
  "CONTEXTDEFINITIONELEMENT_PARENTCATEGARY": "parentCategary",
  "CONTEXTDEFINITIONELEMENT_DEFINITION": "definition",
  "CONTEXTDEFINITIONELEMENT_MATCHERS": "matchers",
  "CONTEXTDEFINITIONELEMENT_REVERSEMATCHERS": "reverseMatchers",
  "CONTEXTDEFINITIONELEMENT_CHILD": "child",
  "CONTEXTDEFINITIONROOT_DEFINITION": "definition",
  "CONTEXTDEFINITIONROOT_DEFAULT": "defaultValue",
  "CONTEXTFLAT_CONTEXT": "context",
  "CONTEXTFLAT_LOCAL2GLOBAL": "local2Global",
  "CONTEXTGROUP_GROUP": "group",
  "CONTEXTGROUP_INFO": "info",
  "CONTEXTGROUP_INFO_INHERIT": "inherit",
  "CONTEXTGROUP_INFO_POPUP": "popup",
  "CONTEXTGROUP_INFO_ESCALATE": "escalate",
  "CONTEXTPATH_ROOTNAME": "rootEleName",
  "CONTEXTPATH_PATH": "path",
  "_CONSTANTNAME": "constantName",
  "_DEFINITION": "definition",
  "_ELEMENTS": "elements",
  "EMBEDEDSCRIPTEXPRESSION_SCRIPTEXPRESSIONS": "scriptExpressions",
  "EMBEDEDSCRIPTEXPRESSION_SCRIPTFUNCTION": "scriptFunction",
  "EMBEDEDSCRIPTEXPRESSION_SCRIPTEXPRESSIONSCRIPTFUNCTION": "scriptExpressionScriptFunction",
  "SCRIPTEXPRESSION_DEFINITION": "definition",
  "SCRIPTEXPRESSION_SCRIPTFUNCTION": "scriptFunction",
  "SCRIPTEXPRESSION_EXPRESSIONS": "expressions",
  "SCRIPTEXPRESSION_VARIABLENAMES": "variableNames",
  "_SCRIPT": "script",
  "_VARIABLENAME": "variableName",
  "SERVICEINTERFACE_PARM": "parm",
  "SERVICEINTERFACE_RESULT": "result",
  "SERVICEOUTPUT_CRITERIA": "criteria",
  "SERVICEPARM_CRITERIA": "criteria",
  "SERVICEPARM_DEFAULT": "default",
  "SERVICERESULT_OUTPUT": "output",
  "DEFINITIONSERVICE_STATIC": "static",
  "DEFINITIONSERVICE_RUNTIME": "runtime",
  "GATEWAYSERVICE_COMMAND_REQUEST": "request",
  "GATEWAYSERVICE_COMMAND_REQUEST_QUERY": "query",
  "GATEWAYSERVICE_COMMAND_REQUEST_PARMS": "parms",
  "_IMPLEMENTATION": "implementation",
  "_CONFIGURE": "configure",
  "INFOSERVICESTATIC_ID": "id",
  "INFOSERVICESTATIC_TAG": "tag",
  "INFOSERVICESTATIC_INTERFACE": "interface",
  "QUERYSERVICE_SERVICEID": "serviceId",
  "QUERYSERVICE_INTERFACE": "interface",
  "RESULTSERVICE_RESULTNAME": "resultName",
  "RESULTSERVICE_OUTPUT": "output",
  "DEFINITIONMAPPINGSERVICE_PARMMAPPING": "parmMapping",
  "DEFINITIONMAPPINGSERVICE_RESULTMAPPING": "resultMapping",
  "DEFINITIONSERVICEINENTITY_USE": "use",
  "DEFINITIONSERVICEINENTITY_PROVIDER": "provider",
  "DEFINITIONSERVICEPROVIDER_SERVICEID": "serviceId",
  "DEFINITIONSERVICEPROVIDER_SERVICEINTERFACE": "serviceInterface",
  "DEFINITIONSERVICEUSE_PROVIDER": "provider",
  "DEFINITIONSERVICEUSE_SERVICEMAPPING": "serviceMapping",
  "EXECUTABLESERVICEUSE_PROVIDER": "provider",
  "EXECUTABLESERVICEUSE_NAME": "name",
  "EXECUTABLESERVICEUSE_INFO": "info",
  "EXECUTABLESERVICEUSE_PARMMAPPING": "parmMapping",
  "EXECUTABLESERVICEUSE_RESULTMAPPING": "resultMapping",
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
  "RUNTIME_GATEWAY_SERVICE": "service",
  "STRINGABLEVALUE_RESOURCEID": "resourceId",
  "STRINGABLEVALUE_DEPENDENCY": "dependency",
  "RESOURCEMANAGERJSOPERATION_INFO_OPERATIONINFO": "operationInfo",
  "MINIAPPSERVLET_COMMAND_LOGIN": "login",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP": "loadMiniApp",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP_APPID": "appId",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP_USERID": "userId",
  "MINIAPPSERVLET_COMMAND_LOADMINIAPP_ENTRY": "entry",
  "MINIAPPSERVLET_COMMAND_CREATEDATA": "createData",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_USERID": "userId",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_APPID": "appId",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_DATANAME": "dataName",
  "MINIAPPSERVLET_COMMAND_CREATEDATA_DATAINFO": "dataInfo",
  "MINIAPPSERVLET_COMMAND_UPDATEDATA": "updateData",
  "MINIAPPSERVLET_COMMAND_UPDATEDATA_ID": "id",
  "MINIAPPSERVLET_COMMAND_UPDATEDATA_DATAINFO": "dataInfo",
  "MINIAPPSERVLET_COMMAND_DELETEDATA": "deleteData",
  "MINIAPPSERVLET_COMMAND_DELETEDATA_DATATYPE": "dataType",
  "MINIAPPSERVLET_COMMAND_DELETEDATA_ID": "id",
  "GATEWAYSERVLET_COMMAND_PARM_RUNTIMEINFO": "runtimeInfo",
  "REQUESTINFO_CLIENTID": "clientId",
  "REQUESTINFO_COMMAND": "command",
  "REQUESTINFO_PARMS": "parms",
  "SERVICEINFO_SERVICE_COMMAND": "command",
  "SERVICEINFO_SERVICE_PARMS": "parms",
  "SERVICESERVLET_REQUEST_TYPE": "type",
  "SERVICESERVLET_REQUEST_SERVICE": "service",
  "SERVICESERVLET_REQUEST_MODE": "mode",
  "SERVICESERVLET_REQUEST_CHILDREN": "children",
  "DEFINITIONAPP_ID": "id",
  "DEFINITIONAPP_CONTEXT": "context",
  "DEFINITIONAPP_ENTRY": "entry",
  "DEFINITIONAPP_APPLICATIONDATA": "applicationData",
  "DEFINITIONAPPENTRYUI_MODULE": "module",
  "DEFINITIONAPPENTRYUI_PROCESS": "process",
  "DEFINITIONAPPENTRYUI_CONTEXT": "context",
  "DEFINITIONAPPMODULE_ROLE": "role",
  "DEFINITIONAPPMODULE_MODULE": "module",
  "DEFINITIONAPPMODULE_STATUS": "status",
  "DEFINITIONAPPMODULE_INPUTMAPPING": "inputMapping",
  "DEFINITIONAPPMODULE_OUTPUTMAPPING": "outputMapping",
  "EXECUTABLEAPPENTRY_ID": "id",
  "EXECUTABLEAPPENTRY_UIMODULE": "uiModule",
  "EXECUTABLEAPPENTRY_CONTEXT": "context",
  "EXECUTABLEAPPENTRY_PROCESS": "process",
  "EXECUTABLEAPPENTRY_DATADEFINITION": "dataDefinition",
  "EXECUTABLEAPPMODULE_MODULEDEFID": "moduleDefId",
  "EXECUTABLEAPPMODULE_ROLE": "role",
  "EXECUTABLEAPPMODULE_MODULE": "module",
  "EXECUTABLEAPPMODULE_INPUTMAPPING": "inputMapping",
  "EXECUTABLEAPPMODULE_OUTPUTMAPPING": "outputMapping",
  "COMPONENTWITHCONFIGURATION_PAGEINFO": "pageInfo",
  "COMPONENTWITHCONFIGURATION_SERVICE": "service",
  "DEFINITIONACTIVITY_UI": "ui",
  "DEFINITIONACTIVITY_COMMAND": "command",
  "EXECUTABLEACTIVITY_UI": "ui",
  "EXECUTABLEACTIVITY_COMMAND": "command",
  "EXECUTABLEACTIVITY_PARM": "parm",
  "DEFINITIONDECORATION_GLOBAL": "global",
  "DEFINITIONDECORATION_UI": "ui",
  "DEFINITIONMODULE_CONTEXT": "context",
  "DEFINITIONMODULE_UI": "ui",
  "DEFINITIONMODULE_PROCESS": "process",
  "DEFINITIONMODULE_DECORATION": "decoration",
  "DEFINITIONMODULEUI_PAGE": "page",
  "DEFINITIONMODULEUI_INPUTMAPPING": "inputMapping",
  "DEFINITIONMODULEUI_OUTPUTMAPPING": "outputMapping",
  "DEFINITIONMODULEUI_EVENTHANDLER": "eventHandler",
  "DEFINITIONMODULEUI_TYPE": "type",
  "DEFINITIONMODULEUI_STATUS": "status",
  "DEFINITIONMODULEUIEVENTHANDER_PROCESS": "process",
  "EXECUTABLEMODULE_ID": "id",
  "EXECUTABLEMODULE_CONTEXT": "context",
  "EXECUTABLEMODULE_UI": "ui",
  "EXECUTABLEMODULE_DECORATION": "decoration",
  "EXECUTABLEMODULE_PROCESS": "process",
  "EXECUTABLEMODULE_INITSCRIPT": "initScript",
  "EXECUTABLEMODULEUI_ID": "id",
  "EXECUTABLEMODULEUI_PAGE": "page",
  "EXECUTABLEMODULEUI_INPUTMAPPING": "inputMapping",
  "EXECUTABLEMODULEUI_OUTPUTMAPPING": "outputMapping",
  "EXECUTABLEMODULEUI_EVENTHANDLER": "eventHandler",
  "EXECUTABLEMODULEUIEVENTHANDLER_PROCESS": "process",
  "INFOPAGE_ID": "id",
  "DEFINITIONUICOMMAND_PARM": "parm",
  "DEFINITIONUICOMMAND_RESULT": "result",
  "DEFINITIONUIEVENT_DATA": "data",
  "ELEMENTEVENT_UIID": "uiId",
  "ELEMENTEVENT_EVENT": "event",
  "ELEMENTEVENT_FUNCTION": "function",
  "ELEMENTEVENT_SELECTION": "selection",
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
  "UIRESOURCEDEFINITION_UITAGLIBS": "uiTagLibs",
  "UIRESOURCEDEFINITION_CONSTANTS": "constants",
  "UIRESOURCEDEFINITION_EXPRESSIONS": "expressions",
  "UIRESOURCEDEFINITION_EVENTS": "events",
  "UIRESOURCEDEFINITION_SERVICES": "services",
  "UIRESOURCEDEFINITION_SERVICEPROVIDERS": "serviceProviders",
  "UIRESOURCEDEFINITION_COMMANDS": "commands",
  "UIRESOURCEDEFINITION_TAGNAME": "tagName",
  "UIRESOURCEDEFINITION_TAGCONTEXT": "tagContext",
  "UIRESOURCEDEFINITION_EVENT": "event",
  "UIRESOURCEDEFINITION_EVENTMAPPING": "eventMapping",
  "UIRESOURCEDEFINITION_CONTEXTMAPPING": "contextMapping",
  "UIRESOURCEDEFINITION_COMMANDMAPPING": "commandMapping",
  "UIRESOURCEDEFINITION_SERVICEMAPPING": "serviceMapping",
  "EMBEDEDSCRIPTEXPRESSION_UIID": "uiId",
  "EMBEDEDSCRIPTEXPRESSION_ATTRIBUTE": "attribute",
  "UITAGDEFINITION_NAME": "name",
  "UITAGDEFINITION_SCRIPT": "script",
  "UITAGDEFINITION_ATTRIBUTES": "attributes",
  "UITAGDEFINITION_CONTEXT": "context",
  "UITAGDEFINITION_REQUIRES": "requires",
  "UITAGDEFINITION_EVENT": "event",
  "_DESCRIPTION": "description",
  "GROUP_ID": "id",
  "MINIAPP_ID": "id",
  "_ID": "id",
  "_STATUS": "status",
  "_DATA": "data",
  "USER_ID": "id",
  "USERGROUPMINIAPP_GROUP": "group",
  "USERGROUPMINIAPP_MINIAPPS": "miniApps",
  "USERINFO_USER": "user",
  "USERINFO_GROUPS": "groups",
  "USERINFO_MINIAPPS": "miniApps",
  "USERINFO_GROUPMINIAPP": "groupMiniApp",
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
  "_COMMAND_DELETEDATA_ID": "id"
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createChildNode("COMMONATRIBUTECONSTANT", COMMONATRIBUTECONSTANT); 

})(packageObj);
