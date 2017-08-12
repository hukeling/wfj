<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../shop/include/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 店铺目录 -->
<title>优惠券</title>
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"
	type="text/css" rel="stylesheet" />
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css"
	type="text/css" rel="stylesheet" />
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/youhuijuan_channel.css"
	type="text/css" rel="stylesheet" />
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/brand_channel.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/sales_style.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/WookMark.css" />
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>

<script type="text/javascript">
		$().ready(function(){
			//Lazy Load 延迟加载
			$("img.lazy").lazyload({
				effect : "fadeIn"
			});
		});
	// 初始化分类css
		$(function(){
			$("div").removeClass("category");
			$("front").removeAttr("style");
			var v='${shopCategoryId}';
			if(v==""){
				$("#category_All").addClass("category");
				$("#front_All").attr("style","color:#fff;");
			}else{
				$("#category_"+v).addClass("category");
				$("#front_"+v).attr("style","color:#fff;");
			}
		});
		// 初始化省市商城三个参数
		$(function(){
			
		});
		function addcustomcoupon(discountCouponID,surplus){
			var type = '${session.customer.customerId}';
			var types = '${session.customer.type}';
			if(type==null||type==""){
				lalert('提醒','需先登录！');
			}else{
				if(surplus<=0){
					lalert('提醒','优惠券不足，无法领取优惠券！');
				}else{
					if(types!=3){
						lalert('提醒','你不是普通会员，无法领取优惠券！');
					}else{
						var x = discountCouponID;
						var url ="${pageContext.request.contextPath}/front/customer/customercoupon/addCustomerdiscountcoupon.action";
						$.post(url,{"discountCouponID":x},function(data){
							if(data.isSuccess=="true"){
								lconfirm_yes("消息提醒", "领取成功!",submitOk);
							}else if(data.isSuccess=="false"){
								lconfirm_yes("消息提醒", "领取失败，请稍后再试!",submitOk);
							}else if(data.isSuccess=="ylq"){
								lconfirm_yes("消息提醒", "您已领取，不可重复领取!",submitOk);
							}else if(data.isSuccess=="nulll"){
								lconfirm_yes("消息提醒", "优惠券剩余不足。",submitOk);
							}  
						},"json");
					}
				}
			}
			
		};
		//修改基本信息成功后执行方法	    
		function submitOk(){
  	 		window.location.href='${pageContext.request.contextPath}/front/discountcoupon/coupon/gotofrontcoupon.action?headerType=coupon';
	    }
	</script>
<!--Shop directory contents-->
<style type="text/css">
.codeCatalog {
	width: 1210px;
	height: auto;
	overflow: hidden;
	margin-bottom: 15px;
}

.singleDirectory {
	padding: 10px 0 0 8px;
	width: 394px;
	height: 175px;
	border-bottom: 1px solid #dcdcdc;
	border-right: 1px solid #dcdcdc;
	margin-right: -1px;
	float: left;
}

.connectEnlarge {
	display: block;
	float: left;
	width: 160px;
	height: 160px;
}

.connectEnlarge img {
	border: 1px #f0f0f0 solid;
}

.dtails {
	width: 221px;
	float: left;
	margin-left: 13px;
	color: #999;
}

.dtails a.titledirectory {
	display: block;
	color: #333;
	font-size: 18px;
}

.titleBottom {
	overflow: hidden;
	height: 28px;
	padding-top: 13px;
}

.titleBottom span, .titleBottom a {
	display: block;
	float: left;
}

.titleBottom a.enterYin {
	color: #0099FF;
	text-decoration: underline;
}

.titleBottom span.levelStar {
	margin: 0 7px;
}

.titleBottom span.levelStar a {
	width: 13px;
	height: 25px;
	padding-top: 0px;
}

.figureShows {
	
}

.figureShows a {
	margin-right: 3px;
	width: 47px;
	height: 47px;
}

.figureShows a:visited {
	color: #ffffff;
}

.figureShows a img {
	width: 47px;
	height: 47px;
}

.figureShows a img {
	width: 47px;
	height: 47px;
}

.category {
	background: #990000;
}

.frontClass {
	color: #ffffff;
}

.biaoge_head {
	color: #ffffff;
}

.hyds_yhj {
	width: 1210px;
	height: auto;
	margin-top: 10px;
	overflow: hidden;
}

.hyds_fir {
	cursor: pointer;
	width: 290px;
	height: 93px;
	padding-top: 20px;
	float: left;
	margin: 10px 0 10px 16px;
	background:
		url(${application.fileUrlConfig.fileServiceUploadRoot}images/coupon-icon-new.png)
		no-repeat;
	position: relative;
}

.hyds_sec {
	cursor: pointer;
	width: 290px;
	height: 93px;
	padding-top: 20px;
	float: left;
	margin: 10px 0 10px 16px;
	background:
		url(${application.fileUrlConfig.fileServiceUploadRoot}images/coupon-icon-new.png)
		no-repeat 0 -123px;
	position: relative;
}

.hyds_thir {
	cursor: pointer;
	width: 290px;
	height: 93px;
	padding-top: 20px;
	float: left;
	margin: 10px 0 10px 16px;
	background:
		url(${application.fileUrlConfig.fileServiceUploadRoot}images/coupon-icon-new.png)
		no-repeat 0 -246px;
	position: relative;
}

.hyds_fou {
	cursor: pointer;
	width: 290px;
	height: 93px;
	padding-top: 20px;
	float: left;
	margin: 10px 0 10px 16px;
	background:
		url(${application.fileUrlConfig.fileServiceUploadRoot}images/coupon-icon-new.png)
		no-repeat 0 -369px;
	position: relative;
}

.manjian {
	height: 21px;
	line-height: 19px;
	color: #fff;
	font-size: 12px;
	padding-left: 20px;
	margin: 0;
	width: 215px;
	overflow: hidden;
}

.manjian em {
	font-weight: bold;
	font-size: 18px;
}

.hyds_pf {
	position: absolute;
	z-index: 10;
	background:
		url(${application.fileUrlConfig.fileServiceUploadRoot}images/coupon-icon-new.png)
		no-repeat -131px -749px;
	height: 48px;
	width: 52px;
	right: 0;
	top: -1px;
}
</style>
</head>

<body>
	<%@ include file="../shop/include/header.jsp"%>
	<div class="margin-center PublicWidth1004" style="height: auto;">
		<!--mainClass-->
		<div class="main_content" style="margin: 0 auto;">
			<!-- 标题 begin -->
			<div class="home">
				<div class="home_img">
					<img
						src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/u51_normal.png"
						style="padding-top: 4px;" />
				</div>
				<div class="lujing">
					<a
						href="${pageContext.request.contextPath}/xd/application/view/index/indexfloor.html">首页</a>
					&gt;优惠券
				</div>
			</div>

			<!-- 标题 end -->


			<div class="hyds_yhj">
				<s:if test="discountCouponList.size>0">
					<s:iterator value="discountCouponList" var="o" status="s">
						<div onclick="addcustomcoupon(${discountCouponID},${surplus})"
							<s:if test="#s.index%4==0"> style="margin-left:-16px;"</s:if>>
							<s:if test="#o.templatetype==1">
								<div class="hyds_fir">
							</s:if>
							<s:elseif test="#o.templatetype==2">
								<div class="hyds_sec">
							</s:elseif>
							<s:elseif test="#o.templatetype==3">
								<div class="hyds_thir">
							</s:elseif>
							<s:elseif test="#o.templatetype==4">
								<div class="hyds_fou">
							</s:elseif>
							<s:else>
								<div class="hyds_fir">
							</s:else>
							<!-- 					  <div class="hyds_fir"> -->
							<p class="manjian">
								减<em>￥${discountCouponAmount}</em>
								<!-- 满100.00可用 -->
							</p>
							<p class="manjian">优惠劵名称：${discountCouponName}</p>
							<p class="manjian">
								有效时间：
								<s:date name="#o.beginTime" format="yyyy-MM-dd" />
								--
								<s:date name="#o.expirationTime" format="yyyy-MM-dd" />
							</p>
							<p class="manjian">剩余个数：${surplus}</p>
							<div class="hyds_pf"></div>
						</div>
			</div>
			</s:iterator>
			</s:if>
			<!-- 		  </div> -->
		</div>
		<!--分页 begin-->
		<form id="formModule"
			action="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?shopCategoryId=<s:property value="shopCategoryId" />&headerType=shops"
			method="post">
			<input type="hidden" id="strShopsId" name="strShopsId"
				value="${strShopsId}" /> <input type="hidden" id="city" name="city"
				value="${city}" /> <input type="hidden" id="regionLocation"
				name="regionLocation" value="${regionLocation}" /> <input
				type="hidden" id="strShopCategoryId" name="strShopCategoryId"
				value="${strShopCategoryId}" />
			<s:if test="mapList.size>0">
				<div class="pageList strong ClearBoth">
					<jsp:include page="../util/splitPage.jsp" />
				</div>
			</s:if>
		</form>
		<!--分页 end-->
		<div class="biaoge_head"
			style="height: auto; border-bottom: 0; float: left; left; width: 140px;">
		</div>
		<!-- 店铺排序 end-->

	</div>
	<!--//mainClass-->
	</div>

	<%@ include file="../shop/include/footer.jsp"%>
</body>
</html>
