<!DOCTYPE html>
<html>
<body nosliwattribute_placeholder="id:pleaseEmbed">
		<div class="page-content">
			<div class="navbar">
			    <div class="navbar-inner">
			        <div class="left">
			            <a href="" class="link" style="display:<%=?(nosliw_module_application_ui_status.index)?==0?'none':'inline'%>" nosliw-event="click:transferBack:">Back</a>
			        </div>
			        <div class="title"><%=?(nosliw_ui_info.title)?%></div>
			        <div class="right">
			            <a href="" class="link" nosliw-event="click:refresh:">Refresh</a> 
			        </div>
			    </div>
			</div>

			<div id="pleaseEmbed"/>
		</div>
</body>

	<scripts>
	{
		transferBack : function(info, env){
			event.preventDefault();
			env.trigueNosliwEvent("module_application_transferBack", info.eventData);
		},
		refresh : function(info, env){
			event.preventDefault();
			env.trigueNosliwEvent("module_application_refresh", info.eventData);
		},
	}
	</scripts>


	<contexts>
	{
		"group" : {
			"public" : {
				"element" : {
					"nosliw_ui_info" : {
						"definition": {
							"child" : {
								"id" : {},
								"title" : {}
							}
						}
					},
					"nosliw_module_application_ui_status" : {
						"definition": {
							"child" : {
								"index" : {},
							}
						}
					},
				}
			}
		}
	}
	</contexts>

	<events>
	[
		{
			name : "transferBack",
			data : {
				element : {
				}
			}
		},
		{
			name : "refresh",
			data : {
				element : {
				}
			}
		}
	]
	</events>

</html>

