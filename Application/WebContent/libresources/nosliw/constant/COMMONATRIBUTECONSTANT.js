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
  "ENTITYINFO_ID": "id",
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
  "USERINFO_USER": "user",
  "USERINFO_ENTITIES": "entities",
  "ENTITYINFO_VALUE": "value",
  "_CONTEXT": "context",
  "_ELEMENT": "element",
  "ATTACHMENTCONTAINER_ELEMENT": "element",
  "ENTITYINFO_ENTITY": "entity",
  "ENTITYINFO_RESOURCETYPE": "resourceType",
  "ATTACHMENTREFERENCE_REFERENCEID": "referenceId",
  "_CONTAINER": "container",
  "_COMPONENTENTITY": "componentEntity",
  "_ELEMENTID": "elementId",
  "ENTITYINFO_REFERENCE": "reference",
  "ENTITYINFO_NAMEMAPPING": "nameMapping",
  "ENTITYINFO_INPUTMAPPING": "inputMapping",
  "ENTITYINFO_OUTPUTMAPPING": "outputMapping",
  "ENTITYINFO_EVENTHANDLER": "eventHandler",
  "_TYPE": "type",
  "_NAME": "name",
  "ENTITYINFO_ELEMENT": "element",
  "_EVENTHANDLER": "eventHandler",
  "_LIFECYCLE": "lifecycle",
  "_NAMEMAPPING": "nameMapping",
  "DATATYPECRITERIA_TYPE": "type",
  "DATATYPECRITERIA_CHILDREN": "children",
  "DATATYPECRITERIA_DATATYPEID": "dataTypeId",
  "DATATYPECRITERIA_ELEMENTDATATYPECRITERIA": "elementDataTypeCriteria",
  "DATATYPECRITERIA_IDSCRITERIA": "idsCriteria",
  "DATATYPECRITERIA_DATATYPEFROM": "dataTypeFrom",
  "DATATYPECRITERIA_DATATYPETO": "dataTypeTo",
  "VARIABLEINFO_CRITERIA": "criteria",
  "VARIABLEINFO_STATUS": "status",
  "VARIABLEINFO_DEFAULTVALUE": "defaultValue",
  "ENTITYINFO_SCHEDULE": "schedule",
  "ENTITYINFO_TASK": "task",
  "ENTITYINFO_END": "end",
  "_SCHEDULETYPE": "scheduleType",
  "_START": "start",
  "_INTERVAL": "interval",
  "GATEWAYERRORLOGGER_COMMAND_LOGERRRO": "logError",
  "GATEWAYERRORLOGGER_PARMS_ERROR": "error",
  "_POLLTASK": "pollTask",
  "_POLLSCHEDULE": "pollSchedule",
  "ENTITYINFO_SOURCE": "source",
  "ENTITYINFO_EVENTINFO": "eventInfo",
  "ENTITYINFO_HANDLER": "handler",
  "_END": "end",
  "_INPUT": "input",
  "_EXPRESSION": "expression",
  "_OPERAND": "operand",
  "_VARIABLENAMES": "variableNames",
  "_REFERENCENAMES": "referenceNames",
  "EXPRESSION_OPERAND": "operand",
  "EXPRESSION_VARIABLESMATCHERS": "variablesMatchers",
  "EXPRESSION_VARIABLEINFOS": "variableInfos",
  "ENTITYINFO_EXPRESSION": "expression",
  "ENTITYINFO_REFERENCEMAPPING": "referenceMapping",
  "ENTITYINFO_RESOURCEID": "resourceId",
  "ENTITYINFO_ELEMENTNAME": "elementName",
  "EXECUTABLEEXPRESSION_OPERAND": "operand",
  "EXECUTABLEEXPRESSION_OUTPUTMATCHERS": "outputMatchers",
  "EXECUTABLEEXPRESSION_VARIABLEINFOS": "variableInfos",
  "EXPRESSIONGROUP_VARIABLEINFOS": "variableInfos",
  "EXPRESSIONGROUP_EXPRESSIONS": "expressions",
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
  "OPERAND_EXPRESSION": "expression",
  "OPERAND_ELEMENTNAME": "elementName",
  "OPERAND_VARMATCHERS": "varMatchers",
  "OPERAND_VARMAPPING": "varMapping",
  "OPERAND_VARIABLENAME": "variableName",
  "DEFINITIONACTIVITY_OUTPUT": "output",
  "EXECUTABLEACTIVITY_RESULTNAME": "resultName",
  "DEFINITIONACTIVITY_SCRIPT": "script",
  "EXECUTABLEACTIVITY_SCRIPTEXPRESSION": "scriptExpression",
  "EXECUTABLEACTIVITY_SCRIPTEXPRESSIONSCRIPT": "scriptExpressionScript",
  "DEFINITIONACTIVITY_CONTAINERNAME": "containerName",
  "DEFINITIONACTIVITY_ELEMENTNAME": "elementName",
  "DEFINITIONACTIVITY_INDEXNAME": "indexName",
  "DEFINITIONACTIVITY_STEP": "step",
  "DEFINITIONACTIVITY_PROCESS": "process",
  "EXECUTABLEACTIVITY_STEP": "step",
  "EXECUTABLEACTIVITY_CONTAINERDATAPATH": "containerDataPath",
  "EXECUTABLEACTIVITY_ELEMENTNAME": "elementName",
  "EXECUTABLEACTIVITY_INDEXNAME": "indexName",
  "EXECUTABLEACTIVITY_PROCESS": "process",
  "DEFINITIONACTIVITY_PROVIDER": "provider",
  "EXECUTABLEACTIVITY_SERVICE": "service",
  "EXECUTABLEACTIVITY_PROVIDER": "provider",
  "DEFINITIONACTIVITY_FLOW": "flow",
  "EXECUTABLEACTIVITY_FLOW": "flow",
  "DEFINITIONACTIVITY_EXPRESSION": "expression",
  "DEFINITIONACTIVITY_TYPE": "type",
  "DEFINITIONACTIVITY_INPUTMAPPING": "inputMapping",
  "DEFINITIONACTIVITY_BRANCH": "branch",
  "DEFINITIONACTIVITY_RESULT": "result",
  "DEFINITIONPROCESSSUITEELEMENTENTITY_ACTIVITY": "activity",
  "DEFINITIONRESULTACTIVITYBRANCH_FLOW": "flow",
  "DEFINITIONRESULTACTIVITYBRANCH_DATA": "data",
  "DEFINITIONRESULTACTIVITYNORMAL_FLOW": "flow",
  "DEFINITIONRESULTACTIVITYNORMAL_OUTPUT": "output",
  "DEFINITIONSEQUENCEFLOW_TARGET": "target",
  "_PROCESS": "process",
  "EXECUTABLEACTIVITY_CATEGARY": "categary",
  "EXECUTABLEACTIVITY_TYPE": "type",
  "EXECUTABLEACTIVITY_ID": "id",
  "EXECUTABLEACTIVITY_DEFINITION": "definition",
  "EXECUTABLEACTIVITY_INPUTMAPPING": "inputMapping",
  "EXECUTABLEACTIVITY_BRANCH": "branch",
  "EXECUTABLEACTIVITY_RESULT": "result",
  "EXECUTABLEPROCESS_DEFINITION": "definition",
  "EXECUTABLEPROCESS_ID": "id",
  "EXECUTABLEPROCESS_ACTIVITY": "activity",
  "EXECUTABLEPROCESS_STARTACTIVITYID": "startActivityId",
  "EXECUTABLEPROCESS_CONTEXT": "context",
  "EXECUTABLEPROCESS_RESULT": "result",
  "EXECUTABLEPROCESS_INITSCRIPT": "initScript",
  "EXECUTABLERESULTACTIVITYBRANCH_FLOW": "flow",
  "EXECUTABLERESULTACTIVITYBRANCH_DATA": "data",
  "EXECUTABLERESULTACTIVITYNORMAL_FLOW": "flow",
  "EXECUTABLERESULTACTIVITYNORMAL_DATAASSOCIATION": "dataAssociation",
  "RESULTPROCESS_RESULTNAME": "resultName",
  "RESULTPROCESS_OUTPUT": "output",
  "PLUGINACTIVITY_TYPE": "type",
  "PLUGINACTIVITY_SCRIPT": "script",
  "PLUGINACTIVITY_DEFINITION": "definition",
  "PLUGINACTIVITY_PROCESSOR": "processor",
  "ENTITYINFO_DATA": "data",
  "RESOURCE_ID": "id",
  "RESOURCE_RESOURCEDATA": "resourceData",
  "RESOURCE_INFO": "info",
  "RESOURCEDEPENDENCY_ALIAS": "alias",
  "RESOURCEDEPENDENCY_ID": "id",
  "RESOURCEID_TYPE": "type",
  "RESOURCEID_ID": "id",
  "RESOURCEID_STRUCUTRE": "strucutre",
  "RESOURCEID_SUP": "supliment",
  "RESOURCEID_BUILDER": "builder",
  "RESOURCEID_PARMS": "parms",
  "RESOURCEID_PARENT": "parent",
  "RESOURCEID_PATH": "path",
  "RESOURCEINFO_ID": "id",
  "RESOURCEINFO_INFO": "info",
  "RESOURCEINFO_DEPENDENCY": "dependency",
  "RESOURCEINFO_CHILDREN": "children",
  "ENTITYINFO_CONTEXT": "context",
  "RUNTIMETASKEXECUTECONVERTER_DATAT": "data",
  "RUNTIMETASKEXECUTECONVERTER_MATCHERS": "matchers",
  "RUNTIMETASKEXECUTEDATAOPERATION_DATATYPEID": "dataTypeId",
  "RUNTIMETASKEXECUTEDATAOPERATION_OPERATION": "operation",
  "RUNTIMETASKEXECUTEDATAOPERATION_PARMS": "parms",
  "RUNTIMETASKEXECUTEEXPRESSION_EXPRESSION": "expression",
  "RUNTIMETASKEXECUTEEXPRESSION_ITEMNAME": "itemName",
  "RUNTIMETASKEXECUTEEXPRESSION_VARIABLESVALUE": "variablesValue",
  "RUNTIMETASKEXECUTEPROCESS_PROCESS": "process",
  "RUNTIMETASKEXECUTEPROCESS_INPUT": "input",
  "RUNTIMETASKEXECUTEPROCESSEMBEDED_PROCESS": "process",
  "RUNTIMETASKEXECUTEPROCESSEMBEDED_PARENTCONTEXT": "parentContext",
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
  "GATEWAYRESOURCE_COMMAND_DISCOVERRESOURCES": "requestDiscoverResources",
  "GATEWAYRESOURCE_COMMAND_DISCOVERRESOURCES_RESOURCEIDS": "resourceIds",
  "GATEWAYRESOURCE_COMMAND_DISCOVERANDLOADRESOURCES": "requestDiscoverAndLoadResources",
  "GATEWAYRESOURCE_COMMAND_DISCOVERANDLOADRESOURCES_RESOURCEIDS": "resourceIds",
  "GATEWAYRESOURCE_COMMAND_LOADRESOURCES": "requestLoadResources",
  "GATEWAYRESOURCE_COMMAND_LOADRESOURCES_RESOURCEINFOS": "resourceInfos",
  "GATEWAYRESOURCEDEFINITION_COMMAND_LOADRESOURCEDEFINITION": "requestLoadResourceDefinition",
  "GATEWAYRESOURCEDEFINITION_COMMAND_LOADRESOURCEDEFINITION_ID": "resourceId",
  "GATEWAYOUTPUT_SCRIPTS": "scripts",
  "GATEWAYOUTPUT_DATA": "data",
  "JSSCRIPTINFO_NAME": "name",
  "JSSCRIPTINFO_FILE": "file",
  "JSSCRIPTINFO_SCRIPT": "script",
  "_VALUE": "value",
  "RUNTIME_NODENAME_GATEWAY": "gatewayObj",
  "RUNTIME_GATEWAY_RESOURCE": "resources",
  "RUNTIME_GATEWAY_RESOURCEDEFINITION": "resourceDefinition",
  "RUNTIME_GATEWAY_CRITERIA": "criteria",
  "RUNTIME_GATEWAY_ERRORLOG": "errorLog",
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
  "_VERSION": "version",
  "_DATATYPENAME": "dataTypeName",
  "_GATEWAY": "gateway",
  "RESOURCEDATAJSLIBRARY_URIS": "uris",
  "_OPERATIONID": "operationId",
  "_OPERATIONNAME": "operationName",
  "_SCRIPTEXPRESSION": "scriptExpression",
  "_VARIABLESVALUE": "variablesValue",
  "_OUTPUTMAPPING": "outputMapping",
  "_INPUTMAPPING": "inputMapping",
  "_TASK": "task",
  "EXECUTABLEDATAASSOCIATION_TYPE": "type",
  "EXECUTABLEDATAASSOCIATION_INPUT": "input",
  "EXECUTABLEDATAASSOCIATION_OUTPUT": "output",
  "EXECUTABLEGROUPDATAASSOCIATION_ELEMENT": "element",
  "EXECUTABLEWRAPPERTASK_OUTPUTMAPPING": "outputMapping",
  "EXECUTABLEWRAPPERTASK_INPUTMAPPING": "inputMapping",
  "EXECUTABLEWRAPPERTASK_TASK": "task",
  "_CONTEXTSTRUCTURE": "contextStructure",
  "ENTITYINFO_ASSOCIATION": "association",
  "EXECUTABLEASSOCIATION_CONTEXT": "context",
  "EXECUTABLEASSOCIATION_PATHMAPPING": "pathMapping",
  "EXECUTABLEASSOCIATION_INPUT": "input",
  "EXECUTABLEASSOCIATION_FLATINPUT": "flatInput",
  "EXECUTABLEASSOCIATION_OUTPUT": "output",
  "EXECUTABLEASSOCIATION_FLATOUTPUT": "flatOutput",
  "EXECUTABLEASSOCIATION_OUTPUTMATCHERS": "outputMatchers",
  "EXECUTABLEASSOCIATION_CONVERTFUNCTION": "convertFunction",
  "EXECUTABLEDATAASSOCIATION_ASSOCIATION": "association",
  "EXECUTABLEDATAASSOCIATION_INPUTDEPENDENCY": "inputDependency",
  "EXECUTABLEDATAASSOCIATION_OUTPUT1": "output",
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
  "CONTEXTPATH_ROOTPATH": "rootPath",
  "CONTEXTPATH_PATH": "path",
  "_CONSTANTNAME": "constantName",
  "_DEFINITION": "definition",
  "_SEGMENTS": "segments",
  "_SCRIPT": "script",
  "_ELEMENTS": "elements",
  "SCRIPTEXPRESSION111_DEFINITION": "definition",
  "SCRIPTEXPRESSION111_SCRIPTFUNCTION": "scriptFunction",
  "SCRIPTEXPRESSION111_EXPRESSIONS": "expressions",
  "SCRIPTEXPRESSION111_VARIABLENAMES": "variableNames",
  "_VARIABLENAME": "variableName",
  "ENTITYINFO_SCRIPT": "script",
  "ENTITYINFO_TYPE": "type",
  "_SCRIPTTYPE": "scriptType",
  "_ID": "id",
  "EXECUTABLESCRIPTENTITY_SCRIPTFUNCTION": "scriptFunction",
  "EXECUTABLESCRIPTENTITY_SUPPORTFUNCTION": "supportFunction",
  "EXECUTABLESCRIPTENTITY_VARIABLESINFO": "variablesInfo",
  "EXECUTABLESCRIPTENTITY_EXPRESSIONREF": "expressionRef",
  "EXECUTABLESCRIPTGROUP_EXPRESSIONGROUP": "expressionGroup",
  "EXECUTABLESCRIPTGROUP_ELEMENT": "element",
  "EMBEDEDSCRIPTEXPRESSION111_SCRIPTEXPRESSIONS": "scriptExpressions",
  "EMBEDEDSCRIPTEXPRESSION111_SCRIPTFUNCTION": "scriptFunction",
  "EMBEDEDSCRIPTEXPRESSION111_SCRIPTEXPRESSIONSCRIPTFUNCTION": "scriptExpressionScriptFunction",
  "_TEXT": "text",
  "SERVICEINTERFACE_PARM": "parm",
  "SERVICEINTERFACE_RESULT": "result",
  "SERVICEOUTPUT_CRITERIA": "criteria",
  "SERVICEPARM_CRITERIA": "criteria",
  "SERVICEPARM_DEFAULT": "default",
  "SERVICERESULT_OUTPUT": "output",
  "DEFINITIONSERVICE_STATIC": "static",
  "DEFINITIONSERVICE_RUNTIME": "runtime",
  "_SUITE": "suite",
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
  "RESULTSERVICE_RESULTVALUE": "resultValue",
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
  "EXECUTABLESERVICEUSE_SERVICEMAPPING": "serviceMapping",
  "_SERVICE": "services",
  "CHANGEBATCH_INITIALCHANGE": "initialChange",
  "CHANGEBATCH_CHANGES": "changes",
  "CHANGEBATCH_QUESTION": "question",
  "CHANGEITEM_CHANGETYPE": "changeType",
  "CHANGEITEM_TARGETCATEGARY": "targetCategary",
  "CHANGEITEM_TARGETID": "targetId",
  "CHANGEITEM_ELEMENT": "element",
  "DESIGNSTORY_DIRECTORID": "directorId",
  "DESIGNSTORY_STORY": "story",
  "DESIGNSTORY_CHANGEHISTORY": "changeHistory",
  "QUESTION_QUESTION": "question",
  "QUESTION_TYPE": "type",
  "QUESTION_CHILDREN": "children",
  "QUESTION_TARGETCATEGARY": "targetCategary",
  "QUESTION_TARGETID": "targetId",
  "CONNECTIONENTITYCONTAIN_CHILD": "child",
  "CONNECTION_END1": "end1",
  "CONNECTION_END2": "end2",
  "CONNECTIONEND_CONNECTIONID": "connectionId",
  "CONNECTIONEND_NODEID": "nodeId",
  "CONNECTIONEND_PROFILE": "profile",
  "CONNECTIONGROUP_CONNECTIONS": "connections",
  "STORY_DIRECTOR": "director",
  "STORY_SHOWTYPE": "showType",
  "STORY_NODE": "node",
  "STORY_CONNECTION": "connection",
  "STORY_CONNECTIONGROUP": "connectionGroup",
  "STORYELEMENT_TYPE": "type",
  "STORYELEMENT_ENTITY": "entity",
  "STORYELEMENT_STATUS": "status",
  "STORYNODE_CONNECTIONS": "connection",
  "_CRONJOB": "cronJob",
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
  "DEFINITIONAPP_ID": "id",
  "DEFINITIONAPP_APPLICATIONDATA": "applicationData",
  "DEFINITIONAPPELEMENTUI_MODULE": "module",
  "DEFINITIONAPPMODULE_MODULE": "module",
  "DEFINITIONAPPMODULE_ROLE": "role",
  "DEFINITIONAPPMODULE_STATUS": "status",
  "EXECUTABLEAPPENTRY_ID": "id",
  "EXECUTABLEAPPENTRY_MODULE": "module",
  "EXECUTABLEAPPENTRY_CONTEXT": "context",
  "EXECUTABLEAPPENTRY_PROCESS": "process",
  "EXECUTABLEAPPENTRY_APPLICATIONDATA": "applicationData",
  "EXECUTABLEAPPENTRY_INITSCRIPT": "initScript",
  "EXECUTABLEAPPMODULE_MODULEDEFID": "moduleDefId",
  "EXECUTABLEAPPMODULE_ROLE": "role",
  "EXECUTABLEAPPMODULE_MODULE": "module",
  "EXECUTABLEAPPMODULE_INPUTMAPPING": "inputMapping",
  "EXECUTABLEAPPMODULE_OUTPUTMAPPING": "outputMapping",
  "EXECUTABLEAPPMODULE_DATADEPENDENCY": "dataDependency",
  "EXECUTABLEAPPMODULE_EVENTHANDLER": "eventHandler",
  "COMPONENTWITHCONFIGURATION_PAGEINFO": "pageInfo",
  "COMPONENTWITHCONFIGURATION_SERVICE": "service",
  "DEFINITIONDECORATION_SHARE": "share",
  "DEFINITIONDECORATION_PARTS": "parts",
  "EXECUTABLEEVENTHANDLER_PROCESS": "process",
  "INFODECORATION_ID": "id",
  "INFODECORATION_CONFIGURE": "configure",
  "INFOPAGE_ID": "id",
  "INFOPAGE_DECORATION": "decoration",
  "DEFINITIONACTIVITY_PARTID": "partId",
  "DEFINITIONACTIVITY_COMMAND": "command",
  "EXECUTABLEACTIVITY_PARTID": "partId",
  "EXECUTABLEACTIVITY_COMMAND": "command",
  "DEFINITIONACTIVITY_UI": "ui",
  "DEFINITIONACTIVITY_SETTING": "setting",
  "EXECUTABLEACTIVITY_UI": "ui",
  "EXECUTABLEACTIVITY_SETTING": "setting",
  "DEFINITIONMODULE_UI": "ui",
  "DEFINITIONMODULE_UIDECORATION": "uiDecoration",
  "DEFINITIONMODULEUI_PAGE": "page",
  "DEFINITIONMODULEUI_UIDECORATION": "uiDecoration",
  "DEFINITIONMODULEUI_TYPE": "type",
  "DEFINITIONMODULEUI_STATUS": "status",
  "EXECUTABLEMODULE_ID": "id",
  "EXECUTABLEMODULE_CONTEXT": "context",
  "EXECUTABLEMODULE_UI": "ui",
  "EXECUTABLEMODULE_PROCESS": "process",
  "EXECUTABLEMODULE_LIFECYCLE": "lifecycle",
  "EXECUTABLEMODULE_INITSCRIPT": "initScript",
  "EXECUTABLEMODULEUI_ID": "id",
  "EXECUTABLEMODULEUI_PAGE": "page",
  "EXECUTABLEMODULEUI_UIDECORATION": "uiDecoration",
  "EXECUTABLEMODULEUI_INPUTMAPPING": "inputMapping",
  "EXECUTABLEMODULEUI_OUTPUTMAPPING": "outputMapping",
  "EXECUTABLEMODULEUI_EVENTHANDLER": "eventHandler",
  "EXECUTABLEMODULEUI_PAGENAME": "pageName",
  "DEFINITIONUICOMMAND_PARM": "parm",
  "DEFINITIONUICOMMAND_RESULT": "result",
  "DEFINITIONUIEVENT_DATA": "data",
  "ELEMENTEVENT_UIID": "uiId",
  "ELEMENTEVENT_EVENT": "event",
  "ELEMENTEVENT_FUNCTION": "function",
  "ELEMENTEVENT_SELECTION": "selection",
  "EXECUTABLEUIBODY_CONTEXT": "context",
  "EXECUTABLEUIBODY_SCRIPTEXPRESSIONSINCONTENT": "scriptExpressionsInContent",
  "EXECUTABLEUIBODY_SCRIPTEXPRESSIONINATTRIBUTES": "scriptExpressionInAttributes",
  "EXECUTABLEUIBODY_SCRIPTEXPRESSIONINTAGATTRIBUTES": "scriptExpressionTagAttributes",
  "EXECUTABLEUIBODY_SCRIPTGROUP": "scriptGroup",
  "EXECUTABLEUIBODY_SCRIPT": "script",
  "EXECUTABLEUIBODY_HTML": "html",
  "EXECUTABLEUIBODY_ELEMENTEVENTS": "elementEvents",
  "EXECUTABLEUIBODY_TAGEVENTS": "tagEvents",
  "EXECUTABLEUIBODY_UITAGS": "uiTags",
  "EXECUTABLEUIBODY_UITAGLIBS": "uiTagLibs",
  "EXECUTABLEUIBODY_CONSTANTS": "constants",
  "EXECUTABLEUIBODY_EXPRESSIONS": "expressions",
  "EXECUTABLEUIBODY_EVENTS": "events",
  "EXECUTABLEUIBODY_SERVICES": "services",
  "EXECUTABLEUIBODY_SERVICEPROVIDERS": "serviceProviders",
  "EXECUTABLEUIBODY_COMMANDS": "commands",
  "EXECUTABLEUIUNIT_ID": "id",
  "EXECUTABLEUIUNIT_TYPE": "type",
  "EXECUTABLEUIUNIT_ATTRIBUTES": "attributes",
  "EXECUTABLEUIUNIT_BODYUNIT": "bodyUnit",
  "EXECUTABLEUIUNIT_TAGNAME": "tagName",
  "EXECUTABLEUIUNIT_TAGCONTEXT": "tagContext",
  "EXECUTABLEUIUNIT_EVENT": "event",
  "EXECUTABLEUIUNIT_EVENTMAPPING": "eventMapping",
  "EXECUTABLEUIUNIT_CONTEXTMAPPING": "contextMapping",
  "EXECUTABLEUIUNIT_COMMANDMAPPING": "commandMapping",
  "EXECUTABLEUIUNIT_SERVICEMAPPING": "serviceMapping",
  "UIEMBEDEDSCRIPTEXPRESSION_UIID": "uiId",
  "UIEMBEDEDSCRIPTEXPRESSION_SCRIPT": "script",
  "UIEMBEDEDSCRIPTEXPRESSION_ATTRIBUTE": "attribute",
  "UITAGDEFINITION_NAME": "name",
  "UITAGDEFINITION_SCRIPT": "script",
  "UITAGDEFINITION_ATTRIBUTES": "attributes",
  "UITAGDEFINITION_CONTEXT": "context",
  "UITAGDEFINITION_REQUIRES": "requires",
  "UITAGDEFINITION_EVENT": "event",
  "UITAGDEFINITIONATTRIBUTE_DEFAULTVALUE": "defaultValue",
  "_RESOURCEID": "resourceId",
  "_URL": "url",
  "_ISVALID": "isValid",
  "_CHILDREN": "children",
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
  "NEWDESIGN_DESIGNID": "designId",
  "NEWDESIGN_DIRECTOR": "director",
  "NEWDESIGN_STORYID": "storyId",
  "NEWDESIGN_STORY": "story",
  "STORYBUILDSERVLET_COMMAND_GETDESIGN": "getDesign",
  "STORYBUILDSERVLET_COMMAND_GETDESIGN_ID": "id",
  "STORYBUILDSERVLET_COMMAND_NEWDESIGN": "newDesign",
  "STORYBUILDSERVLET_COMMAND_NEWDESIGN_DIRECTORID": "directorId",
  "STORYBUILDSERVLET_COMMAND_DESIGN": "design",
  "STORYBUILDSERVLET_COMMAND_DESIGN_DESIGNID": "designId",
  "STORYBUILDSERVLET_COMMAND_DESIGN_CHANGE": "change",
  "GATEWAYSERVLET_COMMAND_PARM_RUNTIMEINFO": "runtimeInfo",
  "SERVICESERVLET_COMMAND_LOGIN": "login",
  "REQUESTINFO_CLIENTID": "clientId",
  "REQUESTINFO_COMMAND": "command",
  "REQUESTINFO_PARMS": "parms",
  "SERVICEINFO_SERVICE_COMMAND": "command",
  "SERVICEINFO_SERVICE_PARMS": "parms",
  "SERVICESERVLET_REQUEST_TYPE": "type",
  "SERVICESERVLET_REQUEST_SERVICE": "service",
  "SERVICESERVLET_REQUEST_MODE": "mode",
  "SERVICESERVLET_REQUEST_CHILDREN": "children",
  "PLAYERINFO_EMAIL": "email",
  "_USERID": "userId",
  "PLAYERLINEUP_WAITINGLIST": "waitingList",
  "PLAYERLINEUP_LINEUP": "lineUp",
  "PLAYERLINEUP_VACANT": "vacant",
  "RESPONSEPLAYERLINEUP_WAITINGLIST": "waitingList",
  "RESPONSEPLAYERLINEUP_LINEUP": "lineUp",
  "RESPONSESPOT_PLAYERS": "players",
  "RESPONSESPOT_VACANT": "vacant",
  "SPOT_PLAYERS": "players",
  "APPDATAINFO_ID": "id",
  "APPDATAINFO_NAME": "name",
  "APPDATAINFO_OWNERINFO": "ownerInfo",
  "APPDATAINFO_DATA": "data",
  "APPDATAINFOCONTAINER_APPDATAINFOS": "appDataInfos",
  "MINIAPP_CATEGARY": "dataOwnerType",
  "OWNERINFO_USERID": "userId",
  "OWNERINFO_COMPONENTID": "componentId",
  "OWNERINFO_COMPONENTTYPE": "componentType",
  "USERGROUPMINIAPP_GROUP": "group",
  "USERGROUPMINIAPP_MINIAPP": "miniApp",
  "_GROUP": "group",
  "_MINIAPP": "miniApp",
  "_GROUPMINIAPP": "groupMiniApp",
  "GATEWAYAPPDATA_GATEWAY_APPDATA": "appData",
  "GATEWAYAPPDATA_COMMAND_GETOWNERAPPDATA": "getOwnerAppData",
  "GATEWAYAPPDATA_COMMAND_GETOWNERAPPDATA_OWNER": "owner",
  "GATEWAYAPPDATA_COMMAND_GETAPPDATA": "getAppData",
  "GATEWAYAPPDATA_COMMAND_GETAPPDATA_INFOS": "infos",
  "GATEWAYAPPDATA_COMMAND_UPDATEAPPDATA": "updateAppData",
  "GATEWAYAPPDATA_COMMAND_UPDATEAPPDATA_INFOS": "infos"
};

//*******************************************   End Node Definition  ************************************** 	
//Register Node by Name
packageObj.createChildNode("COMMONATRIBUTECONSTANT", COMMONATRIBUTECONSTANT); 

})(packageObj);
