<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../include/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>优惠券</title>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/youhuijuan_channel.css" type="text/css" rel="stylesheet">
<script type="text/javascript" >

//领取优惠券
function getCoupon(x){
	
	var url ="${pageContext.request.contextPath}/front/customer/coupon/gotoCustomerHaveCoupon.action";
	$.post(url,{discountCouponID:x},function(data){
		 if(data.state=="on"){
				lalert("Prompt","get succeed!");
	     }else if(data.state=="off"){
//				lalert("提示","请登录！");
	     }
	},"JSON");
}
//跳转我的优惠券
function gotoMyCoupon(x){
	if(x != null){
		location.href="${pageContext.request.contextPath}/front/customer/frontMyCoupon/gotoMyCouponPage.action";
	}else{
		location.href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action";
	}
}
</script>
</head>
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
  <div class="main_content">
    <div class="home">
      <div class="home_img"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/u51_normal.png" style="padding-bottom:2px;"/></div>
      <div class="lujing">
        <a href="${pageContext.request.contextPath}/">首页</a> > 优惠券
      </div>
      
      <s:if test="#request.session.customer.customerId != null">
      <div class="home_img" style="width:19px; margin-left:676px; display:inline; padding-right:5px;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/youhuiquan_03.jpg"/></div>
      <div class="lujing" style="font-family:应用字体;font-size:13px;">
        <a href="javascript:gotoMyCoupon(<s:property value="#request.session.customer.customerId"/>)">我的优惠券</a>
      </div>
      </s:if>
      
      
      
    </div>
    <div class="make_hold">
      <div class="make_hold_left">
        <div class="makleft_wenzi">
           <h4>优惠券说明</h4>
           <p>
           <s:iterator value="cmsArticleList">
           <a href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value="articleId"/>" style="color: blue;"><s:property value="title" /></a><br/>
           </s:iterator>
           </p>
        </div>
      </div>
	  <div class="biaoge">
       	<div class="biaoge_head">
       		<s:iterator value="map" id="m">
       			<div class="biaogeh_left gexian biaoge_head"><a href="${pageContext.request.contextPath}/coupon/gotoCouponList.action?shopCategoryId=<s:property value="#m.key.shopCategoryId" />"><s:property value="#m.key.shopCategoryName" /></a></div>
   			</s:iterator>
         </div>
	  </div>
	</div>
    <s:iterator value="map" id="p">
    <div class="gate_hold">
       <div class="gate_head">
         <div class="gatewenzi"><a href="${pageContext.request.contextPath}/coupon/gotoCouponList.action?shopCategoryId=<s:property value="#p.key.shopCategoryId" />"><s:property value="#p.key.shopCategoryName" /></a></div>
         <div class="moree"><a href="${pageContext.request.contextPath}/coupon/gotoCouponList.action?shopCategoryId=<s:property value="#p.key.shopCategoryId" />">更多></a></div>
       </div>
       	<div class="shopimg">
       <s:iterator value="#p.value">
         <div class="shopim_left">
           <div class="shopimgl_head"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/youhuiqua_03.jpg" style="padding-right:5px;"/><a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="shopInfoId" />"><s:property value="shopName" /></a></div>
           <div class="cdoff">
             <div class="cd"><img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='discountImage' />" ></div>
             <div class="cd_right">
               <div class="offvilid">￥<fmt:formatNumber value="${discountCouponAmount}" type="number"/> 折扣 <span style="color:#999999;">￥<fmt:formatNumber value="${discountCouponLowAmount}" type="number"/></span>
               <br><span style="font-size:13px;color:#999999;">有效期至 <s:date name="expirationTime" format="yyyy-MM-dd" /></span></div>
               <div class="getnow"><a onclick="getCoupon('${discountCouponID}')"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/youhuiqua_09.jpg"/></a></div>
             </div>
           </div>
         </div>
       </s:iterator>
         </div>
       </div>
      </s:iterator>
</div>
<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
