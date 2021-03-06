<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>用户中心</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css"/>
		<SCRIPT type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/js/json2.js"></SCRIPT>
		<script type="text/javascript">
		
			$(function(){
				var latest = '${latest}';
				var popular ='${popular}';
				var price ='${price}';
				
				if(latest=='1') $('#fileter-bar-latest').addClass('ColorRed');
				if(popular=='1') $('#fileter-bar-popular').addClass('ColorRed');
				if(price=='1') $('#fileter-bar-pricedown').addClass('ColorRed');
				if(price=='0') $('#fileter-bar-priceup').addClass('ColorRed');
				
				//筛选
				$('.filter-bar').click(function(){
					var url = 'wishGallery.action';
					var entry = $(this).attr('rel');
					var value = entry.split('-');
					url += "?" + value[0] +"="+ value[1];
					location.href=url;
				});
				//全选
				$('#ckb-product-all').click(function(){
					if($(this).attr('checked')=='checked'){
						$('input[name="ckb-product-sel"]').attr('checked','checked');
					}else{
						$('input[name="ckb-product-sel"]').removeAttr('checked');
					}
				});
				//删除
				$('#delete-sel-product').click(function(){
					var ckbs =$('input[name="ckb-product-sel"]:checked');
					if(ckbs.size() == 0){
						lalert('提醒','请选择商品!');
					}else{
						var ids = '';
						for(var i =0;i<ckbs.size();i++){
							var ckb = ckbs.eq(i);
							var vals= ckb.val();
							ids += vals.split('-')[0]+',';
						}
						ids=ids.substring(0,ids.lastIndexOf(","));
						$.post('deleteWishProduct.action',{productIds:ids},function(data){
							if(data.success){
								location.href=location.href;
							}
						},"JSON");
					}
				});
				//添加到购物车
				$('#addcart-sel-product').click(function(){
					var ckbs =$('input[name="ckb-product-sel"]:checked');
					if(ckbs.size() == 0){
						lalert('提醒','请选择商品!');
					}else{
						var ids = [];
						for(var i =0;i<ckbs.size();i++){
							var ckb = ckbs.eq(i);
							var vals= ckb.val();
							var all = vals.split('-');
							ids.push({"sid":all[2],"pid":all[1],"stockUpDate":all[3]});
						}
						
						$.post('addToCart.action',{productIdsJson:JSON.stringify(ids)},function(data){
							if(data.success){
								lalert("提醒","添加成功!");
							}
						},"JSON");
						
					}
				});
			});
		</script>
	</head>
	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<s:if test="#session.customer.type==1">
					<%@ include file="../include/left_account.jsp"%>
				</s:if>
				<s:elseif test="#session.customer.type==3">
					<%@ include file="../include/left_account_gr.jsp"%>
				</s:elseif>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">商品收藏</h3>	
					<aside>
						<div style="background-color: #fafafa;" id="filter-param" class="FontSize13 publicBorder publicPadding10 publicMarginBot15 produstsList_screen publicrelative ClearBoth">
							<a href="javascript:;" class="filter-bar" id="fileter-bar-latest" rel="latest-1">新品</a> 
							<a href="javascript:;" class="filter-bar" id="fileter-bar-popular" rel="popular-1">流行</a> 
							<a href="javascript:;" class="price_up filter-bar" id="fileter-bar-priceup" rel="price-0">价格</a> 
							<a href="javascript:;" class="price_down filter-bar" id="fileter-bar-pricedown" rel="price-1">价格</a> 
							<a class="change_list" href="${pageContext.request.contextPath}/front/customer/wishList.action">列表</a> 
							<a class="change_Gallery_select ColorRed">图片</a>
						</div>
					</aside>
					<div class="FontSize13 PublicWidth1004 margin-center ">
						<input type="checkbox" name="ckb-product-all" id="ckb-product-all" /><label for="ckb-product-all"></label>
						&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:;" id="delete-sel-product" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14">删除</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:;" id="addcart-sel-product" class="BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14">加入购物车</a>
					</div>
					
					<div class="ProitemBox publicMarginTop15">
						<ul class="WishListUl ClearBoth">
						<s:iterator value="products" var="p">
							<li style="height:225px; padding-bottom:20px;  border-bottom: 1px #ccc dashed; margin:0px 0px; text-align: center; margin-left:11px; display: inline;">
								<a target="_blank" href="${pageContext.request.contextPath}/productInfo/${p.productId}.html">
									<img style="height:130px;width:130px;" src="${application.fileUrlConfig.visitFileUploadRoot}${p.logoImg}"/>
								</a>
							
								<p class="ColorRed" style="text-align:center; padding-top:15px;">
								   <input style="margin-right:3px;" type="checkbox"  name="ckb-product-sel" value="${p.customerCollectProductId}-${p.productId}-${p.shopInfoId}-${p.stockUpDate}" /><a target="_blank" href="${pageContext.request.contextPath}/productInfo/${p.productId}.html">${p.productFullName}</a>
								</p>
								<p style="color:#f40f00;">
									<span class="FontSize18">￥${p.marketPrice}</span><font size="2px">起</font></p>
								<a class="ColorBlue publicinline" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=${p.shopInfoId}"><i class="icon iconShop01" style="height:20px;"></i>${p.shopName}</a>
							</li>
						</s:iterator>
						</ul>
							
						<!--分页 start-->
						<form id="formModule" action="${pageContext.request.contextPath}/front/customer/wishGallery.action" method="get">
							<s:if test="products.size>0">
								<div class="pageList strong ClearBoth">
								     <jsp:include page="../../util/splitPage.jsp" />
								</div>
							</s:if>
						</form>
						<!--分页 end-->
						
				</div>
				<!--//right-->
				
				
			</div>
			
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
