<!DOCTYPE html>
<html>
	<body>
	
	<div>
	Include Object
	<nosliw-include source="Example_App_Query"/> 
	</div>

	<br>
	<nosliw-submit title="Submit" datasource="realtor" parms="schoolType:criteria.schoolType"  output="result"/>  
	<br>
	
	<div>
	Include Data
	<nosliw-include source="Example_App_Result"/> 
	</div>

	<nosliw-debug/>
	
	<br>
	</body>

	<script>
	{
	}
	</script>
	
	<constants>
	{
	}
	</constants>
	
		<!-- This part can be used to define context (variable)
				it describle data type criteria for each context element and its default value
		-->
	<context>
	{
	}
	</context>
	
		<!-- This part can be used to define expressions
		-->
	<expressions>
	{
		
	
	}
	</expressions>
	
</html>

