<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>收藏店铺</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css">
		<SCRIPT type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/js/json2.js"></SCRIPT>
		<script type="text/javascript">
	
		$(function(){
				//筛选
				$('.filter-bar').click(function(){
					/*var url = 'wishList.action';
					var entry = $(this).attr('rel');
					var value = entry.split('-');
					url += "?" + value[0] +"="+ value[1];
					location.href=url;*/
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
						lalert('删除','请选择店铺!');
					}else{
						var ids = '';
						for(var i =0;i<ckbs.size();i++){
							var ckb = ckbs.eq(i);
							ids += ckb.val()+ ",";
						}
						if(ids!=""){
							ids=ids.substring(0,ids.lastIndexOf(","));
						}
						$.post('deleteFollowingShop.action',{followingShopIds:ids},function(data){
							if(data.success){
								location.href=location.href;
							}
						},"JSON");
					}
				});
				//分页
				$('.pageList a').click(function(){
					var url = 'followingShop.action';
					var page =$(this).attr('rel');
					if(url.indexOf('?')>0){
						url += "&page=" + page;
					}else{
						url += "?page=" + page;
					}
					location.href = url;
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
					<aside>
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">店铺收藏</h3>
					</aside>
					<div class="FontSize13 margin-center publicBorderB" style="padding:5px 0;">
						<input style=" position:relative; top:3px;" type="checkbox" name="ckb-product-all" id="ckb-product-all" /><label for="ckb-product-all">&nbsp;&nbsp;选择所有</label>
						&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:;" id="delete-sel-product" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14">删除</a>
					</div>
					
					
					<ul class="account_MyCart">
					<s:iterator value="shopList" var="s">
						<li class="ClearBoth">
						   <div style="width:auto; float:left;">
							<input type="checkbox"  name="ckb-product-sel" value="${s.customerCollectShopId}" style="float: left" />
							<a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=${s.shopInfoId}" target="_blank"><img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${s.logoUrl}"> </a>
							</div>
							<div>
								<a class="FontSize13 FontSizeB" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=${s.shopInfoId}"  target="_blank">${s.shopName}</a>
								<span class="ColorQHui publicMarginT5 publicblock">信誉 :
									<s:if test="photoNum != null"><s:iterator begin="0" end="photoNum"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/shop_home_page/shop_home_page_10.png" width="16" height="16" /></s:iterator></s:if>
									<s:else>
										<s:iterator begin="0" end="4"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/shop_home_page/shop_home_page_10.png" width="16" height="16" /></s:iterator>
									</s:else>
								</span>
								<span class="ColorQHui publicMarginT5 publicblock" >卖家 : ${s.loginName}</span>
							</div>
							<div class="ColorRed FontSize18">
							</div>
						</li>
					</s:iterator>
					</ul>
					<form id="formModule" method="post" action="${pageContext.request.contextPath}/front/customer/followingShop.action">
					<!--分页 start-->
						<div class="pageList strong ClearBoth">
							<jsp:include page="../../util/splitPage.jsp" />
						</div>
						<!--分页 end-->
					</form>
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
