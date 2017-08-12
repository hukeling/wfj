<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
<title>购物车</title>
<%@ include file="../include/head.jsp"%>
<script>
$(document).ready(function(){
	//默认隐藏div
	$("#addressSwitchDiv").hide();
	//点击显示添加地址
	$("#addressSwitch").click(function(){
		$("#addressSwitchDiv").show();
		$("#addressSwitch").hide();
	});
	//点击cancel隐藏添加地址
	$("#addressSwitchCancel").click(function(){
		$("#addressSwitch").show();
		$("#addressSwitchDiv").hide();
	});
});
//展示MS
function showMS(name){
	var spanId="DefAddress_"+name;
	var flag=$("#flag").val();
	if(flag!=null){
		$("#"+flag).css("display","none");
	}
	$("#"+spanId).css("display","");
	$("#flag").val(spanId);
}
//点击默认地址单选钮时  隐藏MS
function noneMS(){
	var flag=$("#flag").val();
	if(flag!=null){
		$("#"+flag).css("display","none");
	}
}

//提交表单
function submitForm(){
		 var url = '${pageContext.request.contextPath}/register/checkEmail.action';
	     $.post(url,$('#form1').serialize(),function(data){
		 if(data){
		   window.location.reload();
		       }
	},"json");
}
//设为默认地址
function setDefAddress(value){
	
	 var url = '${pageContext.request.contextPath}/front/customer/fontCustomerAddress/setDefAddress.action';
		     $.post(url, {"radioName":value},function(data){
			 if(data){
			   window.location.reload();
			       }
		},"json");
}


//编辑
function edit(value){
	var parames=value;
	if(parames=="-1"){
		 $("[name='checkbox']").attr("checked",'true');
		 $("#addressSwitchDiv").show();
	}else{
		
		var url = '{pageContext.request.contextPath}/front/customer/fontCustomerAddress/findObjById.action';
		     $.post(url, {"id":value},function(data){
			 if(data){
			    $("#dcustomerAcceptAddressId").val(data.customerAcceptAddressId);
				   $("#dconsignee").val(data.consignee);
				   $("#dlastName").val(data.lastName);
				   $("#dpostcod").val(data.postcode);
				   $("#dmobilePhone").val(data.mobilePhone);
				   $("#dphone").val(data.phone);
				   $("#dflagContractor").val(data.flagContractor);
				   $("#dbestSendDate").val(data.bestSendDate);
				   $("#dcountry").val(data.country);
				   $("#dcity").val(data.city);
				   $("#dregionLocation").val(data.regionLocation);
				   $("#daddress").val(data.address);
					   if(data.isSendAddress=="1"){
						  $("[name='checkbox']").attr("checked",'true');
					   }else{
						  $("[name='checkbox']").removeAttr("checked");
					   }
				   $("#addressSwitchDiv").show();
			       }
		},"json");
	}
} 


</script>
</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->

<div id="cart" class=" ClearBoth">
	<div class="cart_title">
    	<ul>
        	<li class="line-height4 strong"><a href="">购物车</a></li>
            <li class="line-height4 strong"><a href="" class="on">编辑地址</a></li>
            <li class="line-height4 strong"><a href="">结账</a></li>
            <li class="title4 line-height4 strong"><a href="">成功</a></li>
        </ul>
    </div>
    
    <div class="BackgroundRed height5 publicMarginTB20"></div>
    <div class="radius publicPadding20 publicHidden publicMarginBot15">
    	<div class="height2 publicHidden"><span class="float-left ColorRed strong publicPadding5 FontSize14">Address</span><span class="float-right publicPadding5 FontSize14"><a href="${pageContext.request.contextPath}/front/customer/Setting/index.action?tableIndex=2" class="ColorBlue ">地址管理</a></span></div>
        <div class=" publicPadding20">
        	<div class="FontSize16 strong float-left">To</div>
        	<input id="flag" name="flag" type="hidden" value=""/>
        	<!-- 遍历地址集合customerAcceptAddressList -->
	        	<s:iterator value="customerAcceptAddressList" var="caa">
	        		<s:if test="#caa.isSendAddress==1" >
	            		<div class="taddress"><input name="radioName" type="radio" onclick="noneMS()" checked="checked" value="<s:property value='#caa.customerAcceptAddressId'/>"><s:property value="#caa.consignee"/>&nbsp;<s:property value="#caa.lastName"/>&nbsp;&nbsp;<s:property value="#caa.province"/><s:property value="#caa.city"/><s:property value="#caa.country"/><s:property value="#caa.address"/>&nbsp;&nbsp;&nbsp;<s:property value="#caa.mobilePhone"/><s:if test="#caa.isSendAddress==1" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<front class="ColorRed strong">默认地址</front></s:if><a href="javascript:;" onclick="edit(<s:property value='#caa.customerAcceptAddressId'/>);" class="ColorBlue float-right publicPaddingLR10 strong">编辑</a>   </div>
	            	</s:if>	
	            </s:iterator>
           		<s:iterator value="customerAcceptAddressList" var="caa">
	        		<s:if test="#caa.isSendAddress!=1" >
	            		<div class="taddress" >
	            			<input name="radioName" type="radio"  value="<s:property value='#caa.customerAcceptAddressId'/>" onclick="showMS(<s:property value='#caa.customerAcceptAddressId'/>)" >
	            				<s:property value="#caa.consignee"/>&nbsp;
	            				<s:property value="#caa.lastName"/>&nbsp;&nbsp;
	            				<s:property value="#caa.province"/><s:property value="#caa.city"/>
	            				<s:property value="#caa.country"/><s:property value="#caa.address"/>&nbsp;&nbsp;&nbsp;
	            				<s:property value="#caa.mobilePhone"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            				<a id="DefAddress_<s:property value='#caa.customerAcceptAddressId'/>" href="javascript:setDefAddress(<s:property value='#caa.customerAcceptAddressId'/>);" style="display:none"><front class="ColorBlue strong">设为默认地址</front></a>
	            				<a href="javascript:;" onclick="edit(<s:property value='#caa.customerAcceptAddressId'/>);" class="ColorBlue float-right publicPaddingLR10 strong">编辑</a>
          				</div>
	        		</s:if>	
        		</s:iterator>
            
            
        </div>
        <a href="javascript:;" id="addressSwitch" class="taddress ColorBlue strong aMarginL60">添加一个新地址</a>
        <div class="radius publicPadding20 aMarginL60" id="addressSwitchDiv">
        	<form id="form1" method="post">
        		<input type="hidden" id="dcustomerAcceptAddressId" name="customerAcceptAddress.customerAcceptAddressId"/>
        		<input type="hidden" id="dcustomerId" name="customerAcceptAddress.customerId"  value="1"/>
        		<input type="hidden" id="dphone" name="customerAcceptAddress.phone" />
        		<input type="hidden" id="dflagContractor" name="customerAcceptAddress.flagContractor" />
        		<input type="hidden" id="dbestSendDate" name="customerAcceptAddress.bestSendDate" />
            	<p class="strong">地址:</p>
				<select id="dcountry" name="customerAcceptAddress.country" class="widthpx180 height25 publicMarginR10 ColorHui03">
                	<option>Country</option>
                	<option value="北京">北京</option>
                	<option value="上海">上海</option>
                	
                </select>
                <select id="dregionLocation" name="customerAcceptAddress.regionLocation" class="widthpx180 height25 publicMarginR10 ColorHui03">
                	<option>Province</option>
                	<option value="海淀">海淀</option>
                	<option value="上海">上海</option>
                </select>
                <select id="dcity" name="customerAcceptAddress.city" class="widthpx180 height25 publicMarginR10 ColorHui03">
                	<option>City</option>
                	<option value="北京市">北京市</option>
                	<option value="上海市">上海市</option>
                </select>
               <p class="height25 publicMarginTB10"><input id="daddress" name="customerAcceptAddress.address" type="text" class="atext {validate:{required:true}" placeholder="street address, P.O.box, company name, c/o"></p>
               <p class="strong">邮政编码:</p>
               <p class="height25 publicMarginTB10"><input id="dpostcod" name="customerAcceptAddress.postcode" type="text" class="widthpx200 height25"></p>
               <p class="strong">名称:</p>
               <p class="height25 publicMarginTB10"><input id="dconsignee" name="customerAcceptAddress.consignee" type="text" class="widthpx200 height25" placeholder="first name"> <input id="dlastName" name="customerAcceptAddress.lastName" type="text" class="widthpx200 height25 publicMarginL10" placeholder="last name"></p>
               <p class="strong">电话号码:</p>
               <p class="height25 publicMarginTB10"><input id="dmobilePhone" name="customerAcceptAddress.mobilePhone" type="text" class="widthpx200 height25"></p>
               <p class="publicMarginTB20"><input id="checkbox" name="checkbox" type="checkbox"  value="1"> 设置为默认地址</p>
               <p class="publicMarginTB20"><input name="" type="button" onclick="submitForm()" value="submit" class="publicPaddingLR10 publicMarginR10"> 
               <input name="" type="button" value="Cancel" class="publicPaddingLR10 publicMarginR10" id="addressSwitchCancel"></p>
            </form>
        </div>
        
        <div class="height2 publicMarginTB20 publicHidden"><span class="float-left ColorRed strong publicPadding5 FontSize14">付款方式</span></div>
        <div class="radius publicPadding20">
        	<div class="publicdashedline">
                <p class="strong">贝宝</p>
                <input name="" type="radio" value=""> <img src="images/cart/card (1).jpg">
                <div class="radius Backgroundyellow publicPadding20 Paypal ">
                    <p><span class="widthpx100 float-left text-right publicPaddingR7">贝宝账户</span> <input name="" type="text" class="widthpx200 height25 publicMarginL10"></p>
                    <p><span class="widthpx100 float-left text-right publicPaddingR7">密码</span> <input name="" type="text" class="widthpx200 height25 publicMarginL10"></p>
                    <p class="publicMarginTB20 Paypal_btn"><input name="" type="button" value="Ok" class="publicPaddingLR10 publicMarginR10"> <input name="" type="button" value="Cancel" class="publicPaddingLR10 publicMarginR10"></p>
                </div>
            </div>
            <p class="strong publicPaddingT15">信用卡</p>
            	<span class="publicPaddingR7"><input name="" type="radio" value=""> <img src="images/cart/card (2).jpg"></span>
                <span class="publicPaddingR7"><input name="" type="radio" value=""> <img src="images/cart/card (3).jpg"></span> 
                <span class="publicPaddingR7"><input name="" type="radio" value=""> <img src="images/cart/card (2).jpg"></span> 
                <span class="publicPaddingR7"><input name="" type="radio" value=""> <img src="images/cart/card (3).jpg"></span>
        </div>
        
        <a href="" class="ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong">查看订单</a>
    </div>
</div>

<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
