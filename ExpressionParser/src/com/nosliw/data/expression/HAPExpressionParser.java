package com.nosliw.data.expression;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nosliw.common.utils.HAPConstant;
import com.nosliw.data.expression.parser.NosliwExpressionParser;
import com.nosliw.data.expression.parser.ParseException;
import com.nosliw.data.expression.parser.SimpleNode;
import com.nosliw.data.expression.parser.TokenMgrError;

public class HAPExpressionParser {

	  public static void main(String args[]) throws ParseException, TokenMgrError {
		  
//		  String str = "!(this:simple)!.fun1(?(bb)?.fun2(:(dd):),:(cc):.fun3())";
		  
//		  String str = "?(key)?.largerThan(&(dddd)&,&(dddd)&)";
		  
		  String str = "?(schoolsData)?.each(parm1:?(validHomeExpression)?,parm2:&(constantData2)&,parm3:&(constantData)&,parm4:<(referenceData)>)";
//		  String str = "?(schoolsData)?.each(parm1:?(validHomeExpression)?,parm2:&(schoolData)&)";
//		  String str = "?(schoolsData)?.each(parm1:?(validHomeExpression)?)";
		  
		  InputStream is = new ByteArrayInputStream(str.getBytes());
		  
          NosliwExpressionParser parser = new NosliwExpressionParser( is ) ;
          SimpleNode root = parser.Expression("");
          root.dump("");
	  }

	  public static HAPOperand parseExpression(String expression){
		  SimpleNode root = null;
		  try{
			  InputStream is = new ByteArrayInputStream(expression.getBytes());
	          NosliwExpressionParser parser = new NosliwExpressionParser( is ) ;
	          root = parser.Expression("");
		  }
		  catch(Exception e){
			  e.printStackTrace();
			  return null;
		  }
          return processExpressionNode(root);
	  }
	  
	  private static HAPOperand processExpressionNode(SimpleNode parentNode){
		  HAPOperand out = null;
		  
		  ExpressionElements expressionEles = getExpressionElements(parentNode);

		  HAPOperand operand = null;
		  if(expressionEles.constantNode!=null){
			//it is a constant operand  
			 operand = new HAPOperandConstant((String)expressionEles.constantNode.jjtGetValue());
		  }
		  else if(expressionEles.variableNode!=null){
			  //it is a variable operand
			 operand = new HAPOperandVariable(((String)expressionEles.variableNode.jjtGetValue()));
		  }
		  else if(expressionEles.dataTypeNode!=null){
			  String dataTypeInfo = (String)expressionEles.dataTypeNode.jjtGetValue();
			  String operation = (String)expressionEles.nameNode.jjtGetValue();
			  if(HAPConstant.DATAOPERATION_NEWDATA.equals(operation)){
				  //new operation
				  operand = new HAPOperandNewOperation(dataTypeInfo, getOperationParms(expressionEles.expressionNodes));
			  }
			  else{
				  //normal data type operation
				  operand = new HAPOperandDataTypeOperation(dataTypeInfo, operation, getOperationParms(expressionEles.expressionNodes));
			  }
		  }
		  
		  out = processExpression1Node(expressionEles.expression1Node, operand);
		  return out;
	  }

	  private static HAPOperand processExpression1Node(SimpleNode parentNode, HAPOperand aheadOperand){
		  if(isNodeEmpty(parentNode))  return aheadOperand;

		  HAPOperand out = null;
		  ExpressionElements expressionEles = getExpressionElements(parentNode);
		  String name = (String)expressionEles.nameNode.jjtGetValue();
		  HAPOperand operand = null;
		  if("function".equals(parentNode.jjtGetValue())){
			  //function call
			  operand = new HAPOperandDataOperation(aheadOperand, name, getOperationParms(expressionEles.expressionNodes));
		  }
		  else{
			  //path
			  operand = new HAPOperandAttribute(aheadOperand, name);
		  }
		  out = processExpression1Node(expressionEles.expression1Node, operand);
		  return out;
	  }

	  private static Map<String, HAPOperand> getOperationParms(List<Parm> expressionParms){
		  Map<String, HAPOperand> out = new LinkedHashMap<String, HAPOperand>();
		  for(Parm parm : expressionParms){
			  HAPOperand op = processExpressionNode(parm.valuNode);
			  out.put(parm.name, op);
		  }
		  return out;
	  }
	  
	  private static ExpressionElements getExpressionElements(SimpleNode parentNode){
		  ExpressionElements out = new ExpressionElements();
		  
		  for(int i=0; i<parentNode.jjtGetNumChildren(); i++){
			  SimpleNode childNode = (SimpleNode)parentNode.jjtGetChild(i);
			  switch(childNode.getId()){
			  case NosliwExpressionParser.JJTCONSTANT:
			  {
				  out.constantNode = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTVARIABLE:
			  {
				  out.variableNode = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTREFERENCE:
			  {
				  out.referenceNode = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTNAME:
			  {
				  out.nameNode =childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTDATATYPE:
			  {
				  out.dataTypeNode =childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTEXPRESSION1:
			  {
				  out.expression1Node = childNode;
				  break;
			  }
			  case NosliwExpressionParser.JJTPARAMETER:
			  {
				  out.expressionNodes.add(processParm(childNode));
				  break;
			  }
			  
			  }
		  }
		  return out;
	  }
	  
	  private static Parm processParm(SimpleNode node){
		  Parm out = new Parm();
		  for(int i=0; i<node.jjtGetNumChildren(); i++){
			  SimpleNode childNode = (SimpleNode)node.jjtGetChild(i);
			  switch(childNode.getId()){
			  case NosliwExpressionParser.JJTPARMNAME:
			  {
				  out.name = (String)childNode.jjtGetValue();
				  break;
			  }
			  case NosliwExpressionParser.JJTEXPRESSION:
			  {
				  out.valuNode = childNode;
				  break;
			  }
			  }
		  }
		  return out;
	  }
	  
	  
	  private static boolean isNodeEmpty(SimpleNode node){
		  if(node.jjtGetNumChildren()==0)   return true;
		  return false;
	  }
}

class ExpressionElements{
	  SimpleNode constantNode;
	  SimpleNode variableNode;
	  SimpleNode referenceNode;
	  SimpleNode nameNode;
	  SimpleNode dataTypeNode;
	  List<Parm> expressionNodes = new ArrayList<Parm>();
	  SimpleNode expression1Node;
}


class Parm{
	String name;
	SimpleNode valuNode;
}