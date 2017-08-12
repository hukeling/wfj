<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<title>收货地址</title>
		<script>
	$(document).ready(function() {
	//默认隐藏div 
		$("#addressSwitchDiv").hide();
		//点击显示添加地址
		$("#addressSwitch").click(function() {
			$("#addressSwitchDiv").show();
			//设置所有的input text滞空
				var a = document.getElementsByTagName("input");
				for ( var i = 0; i < a.length; i++) {
					if (a[i].type == "text" || a[i].type == "hidden") {
						a[i].value = "";
					}
				}
				
				$("#addressSwitch").hide();
			});
		//点击cancel隐藏添加地址
		$("#addressSwitchCancel").click(function() {
			$("#addressSwitch").show();
			$("#addressSwitchDiv").hide();
		});
	
	});

	//表单验证提交
	$(function() {//表单验证
		$("#form1").validate( {
			meta : "validate",
			submitHandler : function(form) {
				var firstArea = $("#firstArea").val();
				var secondArea = $("#secondArea").val();
				var threeArea = $("#threeArea").val();
				if(firstArea!=""){
					firstArea=$("#"+firstArea).text();
				}
				if(secondArea!=""){
					secondArea = $("#"+secondArea).text();
				}
				if(threeArea!=""){
					threeArea = $("#"+threeArea).text();
				}
				var newAddress = $("#address").val();
//					if(old_address!=""){
				var addressIndex = newAddress.indexOf(",");
				newAddress = newAddress.substring(addressIndex+1,newAddress.lenght);
				$("#saveAddress").val("");
				$("#saveAddress").val(firstArea+secondArea+threeArea+","+newAddress);
				form.submit();
			}
		});
	});
	

	
	
	//展示MS
	function showMS(name) {
		var spanId = "DefAddress_" + name;
		var flag = $("#flag").val();
		if (flag != null) {
			$("#" + flag).css("display", "none");
		}
		$("#" + spanId).css("display", "");
		$("#flag").val(spanId);
	}
	//点击默认地址单选钮时  隐藏MS
	function noneMS() {
		var flag = $("#flag").val();
		if (flag != null) {
			$("#" + flag).css("display", "none");
		}
	}

	//设为默认地址
	function setDefAddress(value) {
		var url = '${pageContext.request.contextPath}/front/customer/addressAction/setDefAddress.action';
		$.post(url,{"CAAId":value},function(data){
				if(data){
					window.location.reload();
				}
			},"json");
		
	}
	  
	//删除收货地址
 	function deleteAddress(id){
		lconfirm("提醒","确认删除",function(o){
			var url='${pageContext.request.contextPath}/front/customer/addressAction/deleteAddress.action';
			  $.post(url,{"CAAId":id},function(data){
					if(data){
						window.location.reload();
				    }
			},"json");
		});
 	}
	
	//返回到确认订单
	function toOrder(){
		var radioNames = document.getElementsByName("radioName");
		var addressId="";
		for(var i=0;i<radioNames.length;i++){
			if(radioNames[i].checked){
				addressId=radioNames[i].value;
			}
		}
		if(""==addressId){
			lalert("提醒","请选择收货地址！");
			return false;
		}else{
			window.close();
			window.opener.doAddress(addressId);
		}
		
	}
	//编辑
	function edit(value){
		     $("#addressSwitch").css("display","none");
		     var url = '${pageContext.request.contextPath}/front/customer/addressAction/getAddress.action';
		     $.post(url,{"CAAId":value},function(data){
			   if(data){
				   var custAddress  = data.customerAcceptAddress
				       $("#customerAcceptAddressId").val(custAddress.customerAcceptAddressId);
					   $("#consignee").val(custAddress.consignee);
					   $("#postcode").val(custAddress.postcode);
					   $("#mobilePhone").val(custAddress.mobilePhone);
					   $("#email").val(custAddress.email);
					   $("#old_address").val(custAddress.address);
			           //地址
					   $("#address").val(custAddress.address);
 						$("#firstArea").val(custAddress.regionLocation);
						chengeArea(custAddress.regionLocation,1,custAddress.city,2);
						chengeArea(custAddress.city,2,custAddress.areaCounty);
					   if(custAddress.isSendAddress=="1"){
						  $("[name='checkbox']").attr("checked",'true');
					   }else{
						  $("[name='checkbox']").removeAttr("checked");
					   }
					   $("#addressSwitchDiv").show();
					   }
		},"json");
	} 
		//更改地址level等级,selectId如果是编辑直接选中，level等级
		function chengeArea(id,level,selectId){
			$.ajax({
		  			url:"${pageContext.request.contextPath}/front/customer/addressAction/findAreaList.action",
		  			type:"post",
		  			dataType:"json",
		  			data:{areaId:id},
		  			success:function(data){
		  				if(data!=""){
		  					var areaList=data;
		  					var htmlOption="<option value=''>--请选择--</option>";
	  						for(var i=0;i<areaList.length;i++){
	  							htmlOption+="<option  value='" + areaList[i].aid+"'id='" + areaList[i].aid+"'>" + areaList[i].name+ "</option>";
	  						}
		  					if(level==1){
		  						$("#secondArea").html(htmlOption);
		  						$("#secondArea").val(selectId);
								$("#threeArea").html("<option value=''>--请选择--</option>");
								
		  					}else if(level==2){
		  						$("#threeArea").html("");
		  						$("#threeArea").append(htmlOption);
		  						$("#threeArea").val(selectId);
		  					}
		  				}
		  			}
	  			});
		}
	
</script>
	</head>

	<body>
		<div class="margin-center PublicWidth1004">
			<!--mainClass-->
			<div id="cart" class=" ClearBoth">
					<div class=" publicHidden">
						<span class="float-left ColorRed strong publicPadding5" style=" font-size:20px; font-weight:bold; margin-top:30px;">收货地址管理</span>
					</div>
					<div class="BackgroundRed height5 "></div>
					<div class="radius publicPadding20 publicHidden publicMarginBot15">
					<div class=" publicPadding20">
						<div class="FontSize16 strong float-left">
						</div>
						<s:if test="custAddress!=null">
							<div class="taddress">
								<input name="radioName" type="radio" onclick="noneMS()" checked="checked" value="<s:property value='custAddress.customerAcceptAddressId'/>">
								<s:property value="custAddress.consignee" />
								&nbsp;&nbsp;
								<s:property value="custAddress.address" />
								<s:if test="custAddress.isSendAddress==1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            				<front class="ColorRed strong">默认地址</front>
								</s:if>
								<a href="javascript:;" onclick="edit(<s:property value='custAddress.customerAcceptAddressId'/>);" class="ColorBlue  publicPaddingLR10 strong">编辑</a>
								<a href="javascript:;" onclick="deleteAddress(<s:property value='custAddress.customerAcceptAddressId'/>);" class="ColorBlue  publicPaddingLR10 strong">删除</a>
							</div>
						</s:if>
						<!-- 遍历地址集合customerAcceptAddressList -->
						<s:iterator value="customerAcceptAddressList" var="caa">
							<div class="taddress">
								<input name="radioName" type="radio" value="<s:property value='#caa.customerAcceptAddressId'/>"onclick="showMS(<s:property value='#caa.customerAcceptAddressId'/>)">
								<s:property value="#caa.consignee" />&nbsp;&nbsp;<s:property value="#caa.address" />
								<div id="DefAddress_<s:property value='#caa.customerAcceptAddressId'/>" style="display: none">
									<a href="javascript:setDefAddress(<s:property value='#caa.customerAcceptAddressId'/>);"><front class="ColorBlue strong">设为默认地址</front> </a>
									<a onclick="edit(<s:property value='#caa.customerAcceptAddressId'/>);" class="ColorBlue  publicPaddingLR10 strong">编辑</a>
									<a onclick="deleteAddress(<s:property value='#caa.customerAcceptAddressId'/>);" class="ColorBlue  publicPaddingLR10 strong">删除</a>
								</div>
							</div>
						</s:iterator>
					</div>
					<a href="javascript:" id="addressSwitch" class="taddress ColorBlue strong aMarginL60">添加新地址</a>
					<div class="radius publicPadding20 "
						id="addressSwitchDiv">
						<form id="form1" class="formLoad"
							action="${pageContext.request.contextPath}/front/customer/addressAction/saveOrUpdateAddress.action" method="post">
							<input type="hidden" id="customerAcceptAddressId" name="customerAcceptAddress.customerAcceptAddressId" />
							<!--         		<input type="hidden" id="customerId" name="customerAcceptAddress.customerId" value="1"/> -->
							<input type="hidden" id="old_address"  class="{validate:{required:true}}" value="">
							<input type="hidden" id="saveAddress"  name="customerAcceptAddress.address" >
							<div style="margin-left:60px;">
								<p class="height25 publicMarginTB10">
									<span class="strong" style="padding-left:28px;">区域：</span>
									<select name="customerAcceptAddress.regionLocation" id="firstArea" onchange="chengeArea(this.value,'1','')">
										<option value="">--请选择--</option>
										<s:iterator value="firstAreaList" var="first">
											<option value="<s:property value="#first.aid"/>" id="<s:property value="#first.aid"/>"><s:property value="#first.name"/></option>
										</s:iterator>
									</select>
									<select name="customerAcceptAddress.city" id="secondArea" onchange="chengeArea(this.value,'2','')">
										<option value="" >--请选择--</option>
									</select>
									<select name="customerAcceptAddress.areaCounty" id="threeArea" onchange="chengeArea(this.value,'3','')">
										<option value="">--请选择--</option>
									</select>
								</p>
									
									<p><span style="font-weight:bold;">详细地址：</span><input id="address"  type="text" class="atext {validate:{required:true,maxlength:[150]}}" style="margin-left:5px; line-height:25px;" /></p>
								<p class="height25 publicMarginTB10">
									<span class="strong" style="padding-left:25px;">邮编：</span><input id="postcode" name="customerAcceptAddress.postcode" type="text" class="widthpx200 height25 {validate:{required:true,zipCode:true}}" value="" style="margin-left:6px; line-height:25px;">
								</p>
								<p class="height25 publicMarginTB10">
									<span class="strong" style="padding-left:10px;">收货人：</span><input id="consignee" name="customerAcceptAddress.consignee" type="text" class="widthpx200 height25 {validate:{required:true,maxlength:[20]}}" style="margin-left:8px; line-height:25px;">
								</p>
								<p class="height25 publicMarginTB10">
									<span class="strong">手机号码：</span>
									<input style="line-height:25px;" id="mobilePhone" name="customerAcceptAddress.mobilePhone" type="text" class="widthpx200 height25 {validate:{required:true,mobile:true}}">
								</p>
								<p class="height25 publicMarginTB10">
									<span class="strong" style="padding-left:25px;">邮箱：</span>
									<input style="line-height:25px;" id="email" name="customerAcceptAddress.email" type="text" class="{validate:{required:true,email:true,maxlength:[100]}} widthpx200 height25">
								</p>
							</div>
							<p class="publicMarginTB20" style="margin-left:52px;">
								<input name="customerAcceptAddress.isSendAddress" type="checkbox" checked="checked" value="1">
								设为默认地址
							</p>
							<p class="publicMarginTB20" style="margin-left:52px;">
								<input   name="" type="submit" value="提交" class="submitButtonL BackgroundRed publicinline publicPadding5_10 FontSize14 radius strong"/>
								<input id="addressSwitchCancel" name="" type="button" value="取消" class="submitButtonL BackgroundRed publicinline publicPadding5_10 FontSize14 radius strong" />
								<span class="submitImgLoad"></span>
							</p>
						</form>
					</div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="javascript:;" onclick="toOrder();" class="ColorBlue strong publicPadding5_10 radius">返回确认订单&nbsp;>></a>
				</div>
			</div>
			<!--//mainClass-->
		</div>
	</body>
</html>
