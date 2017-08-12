<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function showDetailInfo(id){
		createWindow(600,'auto',"&nbsp;&nbsp;详细信息","icon-tip",false,"detailWin");
		$("#addOrEditWin").css("display","none");
		$("#detailWin").css("display","");
		$.ajax({
		   type: "POST", dataType: "JSON",
		   url: "${pageContext.request.contextPath}/back/customerService/getCustomerServiceInfo.action",
		   data: {ids:id},
		   success: function(data){
			   $("#dphotoUrl").html("<img src='"+"<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>"+data.photoUrl+"' width='153px' height='50px' />");
			   $("#dtrueName").html(data.trueName);
			   $("#dnikeName").html(data.nikeName);
			   $("#dqq").html(data.qq);
			   $("#dmobile").html(data.mobile);
			   $("#dphone").html(data.phone);
			   $("#dworkNumber").html(data.workNumber);
			   if(data.useState=="0"){
 				    $("#duseState").html("废弃 ");
 			   }else{
 				    $("#duseState").html("正常使用");
 			   }
		   }
		});
	}
</script>
<div id="detailWin">
    <table style="margin:10px auto;width:400px" class="addOrEditTable">
			<tr>
				<td>员工照片:</td>
				<td>
					<span id="dphotoUrl"></span>
				</td>
			</tr>
			<tr>
				<td>真实姓名:</td>
				<td>
					<span id="dtrueName"></span>
				</td>
			</tr>
			<tr>
				<td>昵称:</td>
				<td><span id="dnikeName"></span></td>
			</tr>
			<tr>
				<td>QQ:</td>
				<td><span id="dqq"></span></td>
			</tr>
			<tr>
				<td>手机号:</td>
				<td><span id="dmobile"></span></td>
			</tr>
			<tr>
				<td>电话号:</td>
				<td><span id="dphone"></span></td>
			</tr>
			<tr>
				<td>工号:</td>
				<td><span id="dworkNumber"></span></td>
			</tr>
			<tr>
	      		<td>使用状态:</td>
	      		<td><span id="duseState"></span></td>
	    	</tr>
	    </table>
    <div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;">
		<input type="button" id="close" class="button_close" onclick="closeWin()" value="" style="cursor:pointer;"/>&nbsp;
	</div>
</div>