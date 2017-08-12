<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单列表</title>
<%@ include file="../include/head.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
<script type="text/javascript" >
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

//提交表单
function submitForm(){
	var url ="${pageContext.request.contextPath}/front/store/shopCoupon/saveFrontShopCoupon.action";
	$.post(url,$('#form1').serialize(),function(data){
			window.location.reload();
	},"JSON");
}
//删除
function deleteConpon(x){
	var url ="${pageContext.request.contextPath}/front/store/shopCoupon/deleteFrontShopCoupon.action?discountCouponID="+x;
	$.post(url,{},function(data){
		if(data.state=="on"){
			window.location.reload();
       }
	},"JSON");
}
//上传图片 
function uploadPhoto() {
	$(document).ready(  
         function() {  
              var options = {  
                  url : "${pageContext.request.contextPath}/front/store/shopCoupon/uploadImageFront.action?is_ajax=2",
                  type : "post",  
                  dataType:"json",
                  success : function(data) {
                   if(data.photoUrl=="false1"){
                     $("#mymessage").html(" <font style='color:red'>Please select your photo</font>");
                   }else if(data.photoUrl=="false2"){
                     $("#mymessage").html(" <font style='color:red'>Please select photo by jpg、png、gif pattern !!</font>");
                   }else{
                    $("#discountImage").val(data.photoUrl);
                    $("#photo").html("");
                  	$("#photo").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' style='width: 100px;height: 70px' />");
                   } 
                  }
              };
             $("#photoForm").ajaxSubmit(options);
      });  
}

//编辑
function edit(value){
	var url ="${pageContext.request.contextPath}/front/store/shopCoupon/changeCoupon.action";
	$.post(url,{discountCouponID:value},function(data){
			$("#discountCouponID").val(data.discountCouponID);
			$("#shopInfoId").val(data.shopInfoId);
			$("#discountCouponCode").val(data.discountCouponCode);
			$("#discountCouponName").val(data.discountCouponName);
			$("#discountCouponAmount").val(data.discountCouponAmount);
			$("#discountCouponLowAmount").val(data.discountCouponLowAmount);
			$("#beginTime").val(data.beginTime);
			$("#expirationTime").val(data.expirationTime);
			$("#discountImage").val(data.discountImage);
			$("#createTime").val(data.createTime);
			$("#addressSwitchDiv").show();
			$("#photo").html("<img src='"+"<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>"+data.discountImage+"' style='width: 100px;height: 70px' />");
	},"JSON");
}
</script>
</head>
<body>
	<%@ include file="../include/header.jsp"%>
	
	<div class="margin-center PublicWidth1004">
	<div class="ClearBoth">
	<%@ include file="../include/left_shop.jsp"%>
	<!--right-->
	<div id="rightBox" class="float-right publicHidden publicrelative">
	<h3 class="BackgroundRed ColorWhite1 FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">优惠券列表</h3>
	<a href="javascript:" id="addressSwitch" class="publicNoBorder publicPadding5_10 BackgroundPurple ColorWhite1 FontSize14 publicabsolute" style="top:5px;right:5px;">添加优惠券</a>
    <div class="radius publicPadding20 aMarginL60 publicBorder publicMarginBot15" id="addressSwitchDiv" style="height: auto; background-color:#f5f5f5;">
    <form id="photoForm" method="post" enctype="multipart/form-data">
     <table class="strong">
          <tr align="left">
		      <td class="strong">优惠券图片:</td>
		  </tr>
		  <tr align="left">
		      <td class="height25 publicMarginTB10">
      		    <input id="fileId" name="imagePath" type="file" class="widthpx200 height25" />
	  	        <input id="buttonId" type="button" value="上传浏览" onclick="uploadPhoto()" class="submitButtonL"/>
	            <span id="mymessage"></span>
		      </td>
		  </tr> 
	 </table>
   	</form>
  	<form id="form1" method="post">
  		<input type="hidden" id="discountCouponID" name="discountCoupon.discountCouponID" value=""/>
  		<input type="hidden" id="discountImage" name="discountCoupon.discountImage" value=""/>
          <p class="strong">优惠券名称:</p>
          <p class="height25 publicMarginTB10">
         		<input id="discountCouponName" name="discountCoupon.discountCouponName" type="text" class="widthpx200 height25">
          </p>
          <p class="strong">优惠金额:</p>
          <p class="height25 publicMarginTB10">
         		<input id="discountCouponAmount" name="discountCoupon.discountCouponAmount" type="text" class="widthpx200 height25">
          </p>
          <p class="strong">最低使用消费:</p>
          <p class="height25 publicMarginTB10">
         		<input id="discountCouponLowAmount" name="discountCoupon.discountCouponLowAmount" type="text" class="widthpx200 height25">
          </p>
          <p class="strong">起始日期:</p>
          <p class="height25 publicMarginTB10">
          	<input id="beginTime" class="widthpx200 height25" name="discountCoupon.beginTime" readonly="readonly"  type="text" onfocus="WdatePicker({lang:'en',maxDate:'#F{$dp.$D(\'expirationTime\')||\'2020-10-01\'}'})"/>
          </p>
          <p class="strong">截止日期:</p>
          <p class="height25 publicMarginTB10">
          	<input id="expirationTime" readonly="readonly" class="widthpx200 height25" type="text" name="discountCoupon.expirationTime" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'2020-10-01',lang:'en'})" />
          </p>
          <p class="strong">图片预览:</p>
          <p class="height25 publicMarginTB10" style="height: 80px">
          	<span id="photo"></span>
          </p>
          <p class="publicMarginTB20">
          <input name="" type="button" onclick="submitForm()" value="提交" class=" publicNoBorder publicPadding5_10 BackgroundPurple ColorWhite1 FontSize14 FontSizeB"> 
          <input type="button" value="取消" class="publicNoBorder publicPadding5_10 BackgroundOrange ColorWhite1 FontSize14 FontSizeB" id="addressSwitchCancel"></p>
   </form>
  </div>
        <!-- 以上为隐藏{添加优惠券} -->
			
			<div class="MarketpalceTree publicMarginTop15">
				<div class="TabParent_lj publicBorder ClearBoth"></br>
					<div>
						<div id="test" class="publicPadding10">
							<form id="formModule" action="${pageContext.request.contextPath}/front/store/shopCoupon/gotoShopCoupon.action" method="post">
								
								<table width="100%" border="0" class="OrderinforTableL">
									<tr class="publicBorder gradient">
										<td width="9%">名称</td>
										<td width="9%">起始日期</td>
										<td width="9%">截止日期</td>
										<td width="9%">优惠金额</td>
										<td width="9%">最低使用消费</td>
										<td width="10%">优惠券图片</td>
										<td width="9%">操作</td>
									</tr>
									<!-- List -->
									<s:iterator value="discountCouponList">
										<tr class="checkb	ox_tr">
											<td class="line-height150 publicNoBorderL">
												<s:property value="discountCouponName" /></a>
											</td>
											<td>
												<s:date name="beginTime" format="yyyy-MM-dd"/>
											</td>
											<td>
												<s:date name="expirationTime" format="yyyy-MM-dd"/>
											</td>
											<td>
												<fmt:formatNumber value="${discountCouponAmount}" type="number"/>
											</td>
											<td>
												<fmt:formatNumber value="${discountCouponLowAmount}" type="number"/>
											</td>
											<td>
												<img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='discountImage' />" style="width: 50px;height: 50px">
											</td>
											<td class="publicNoBorderR">
												<a onclick="edit('${discountCouponID}');" style="color: blue">编辑</a>丨
												<a onclick="deleteConpon('${discountCouponID}')" style="color: blue">删除</a>
											</td>
										</tr>
									</s:iterator>
								</table>
								<!--分页 start-->
								<jsp:include page="../../util/splitPage.jsp" />
								</form>
								<!--分页 end-->
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--//right-->
	</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
