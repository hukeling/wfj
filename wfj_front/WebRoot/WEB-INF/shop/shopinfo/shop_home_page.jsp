<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
<title>品牌首页</title>
<%@ include file="../include/head.jsp"%>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/youhuijuan_channel.css" rel="stylesheet" type="text/css" />
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css" rel="stylesheet" type="text/css" />
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/brand_channel.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
//领取优惠券
function getCoupon(x){
	
	
     var url = '${pageContext.request.contextPath}/front/customer/coupon/gotoCustomerHaveCoupon.action';
		$.post(url,{"discountCouponID":x},function(data){
			if(data){
		       if(data.state=="on"){
				lalert("提醒","领取成功!");
	       }else if(data.state=="off"){
//				lalert("提示","请登录！");
	       }
			}
		},"json");
	
	
	 
}
//收藏店铺
function followingShop(){
	var obj = $("#customerId").val();
		if(obj != ""){
			
		var url = '${pageContext.request.contextPath}/front/customer/customerFollowingShop.action';
		$.post(url,{"shopInfoId":$("#shopInfoId").val()},function(data){
			if(data){
		      if(data.state=="on"){
					lalert("提示","收藏成功!");
		       }else if(data.state=="off"){
					lalert("提示","您不能重复收藏!");
		       }
			}
		},"json");
		}else{
//			lalert("提示","请登录！");
		} 
}
//进入用户中心
function gotoAccountCenter(){
	var obj = $("#customerId").val();
		if(obj != ""){
			location.href="${pageContext.request.contextPath}/front/customer/index.action";
		}else{
//			lalert("提示","请登录！");
		} 
}
</script>

</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<input type="hidden" id="customerId" value="<s:property value="#request.session.customer.customerId"/>"/>
<input type="hidden" id="shopInfoId" value="${shopInfo.shopInfoId}"/>
<!--mainClass-->
  <div class="main_content">
    	<div class="lujing2" style="margin-bottom:9px;">
      <div class="home_img" style="padding-top:0px; margin-top:-3px;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/u51_normal.png" width="15" height="15" /></div>
      <div class="lujing">
         <a href="${pageContext.request.contextPath}/">首页</a> >&nbsp;品牌<span style="color:#999"></span>
      </div>   
        <div class="url">
           <div class="url_top" style="float:right; margin-left:30px; width:100px;">
     </div>
    </div>
   <div class="shop_top">
   		<div class="serio"><img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brand.brandBigImageUrl'/>" style="height:401px; width:401px;"
 /></div>
     <div class="brand_name">
        	<p class="bra">${brand.brandName}</p>
       <div class="xiaobiao">
       
<!-- 店铺等级 -->
                <div class="clear"></div>
          </div>
          <div class="someone">
          		<div class="so">${brand.brandName }</div>
                <p class="so_con">${brand.synopsis }</p>
          </div>
        </div>
        <div class="clear"></div>
   </div>
    
    <div class="product">
    	<ul>
    		<s:iterator value="productList">
	        	<li style="margin-right:0;">
	        		<a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId'/>"><img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>" style="height:242px; width:276px;" /></a>
	            	<p class="natu"><a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId'/>"><s:property value="describle"/></a></p>
	            	<p class="natu" style="color:#CC3300"><span class="usd">￥${salesPrice}</span></p>
	            </li>
    		</s:iterator>
            <div class="clear"></div>
        </ul>
        <!--分页 start-->
        <input id="shopProCategoryId" name="shopProCategoryId" value="<s:property value='shopProCategoryId'/>" type="hidden"/>
       <s:if test="productList.size>0">
      <form id="formModule" action="${pageContext.request.contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${brand.brandId}&shopProCategoryId=<s:property value='shopProCategoryId'/>" method="post">
      	<jsp:include page="../../util/splitPage.jsp" />
      </form>
       </s:if>
		<!--分页 end-->
    </div>
  </div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
