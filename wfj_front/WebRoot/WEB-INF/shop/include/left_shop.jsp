<!--left店铺中心左侧导航-->
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<aside id="Account_left" class="publicBorder float-left BackgroundF8Hui">
<h3 class="publicPadding10 ColorRed publicBorderB_3red FontSize15"><a href="${pageContext.request.contextPath}/front/store/frontshopinfo/goToShopLoginHome.action">用户中心</a></h3>
<nav>
<ul class="Account_left_nav">
<li>
<div class="">
<a class="publicblock icon iconjian" id="folding01"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">店铺信息 </h4>
</div>
<div class="line-height1 nav_Small">
<a class="accountSettings" target="_blank" href="${pageContext.request.contextPath}/shop/${shopInfo.shopInfoId}.html"><i class="icon icontree"></i>我的店铺</a><br>
<a class="accountSettings" href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action"><i class="icon icontree"></i>基础信息</a><br>
<a href="${pageContext.request.contextPath}/front/store/frontshopinfo/gotoFrontShopInfoPage.action"><i class="icon icontree"></i>企业信息</a><br>
<a href="${pageContext.request.contextPath}/front/store/frontshopinfo/sellerBasicInfo.action"><i class="icon icontree"></i>店铺信息</a><br>
<a class="accountSettings" href="${pageContext.request.contextPath}/front/customer/Setting/changePass.action"><i class="icon icontree"></i>修改密码</a><br>
</div>
</li>
<li>
<div class="">
<a class="publicblock icon iconjian" id="folding02"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">商品管理 </h4>
</div>
<div class="line-height1 nav_Small">
<%-- <a href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action"><i class="icon icontree"></i>商品列表</a><br> --%>
<a href="${pageContext.request.contextPath}/front/store/frontShopCategory/toShopCategory.action"><i class="icon icontree"></i>店铺商品分类</a><br>
<a href="${pageContext.request.contextPath}/front/store/frontProduct/frontAddProduct.action"><i class="icon icontree"></i>添加商品</a><br>
<a href="${pageContext.request.contextPath}/phone/goToExcelProductInfo.action"><i class="icon icontree"></i>导入excel数据</a><br>
<a href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoCheckPendingProductListPage.action"><i class="icon icontree"></i>待审核商品</a><br>
<a href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoPutawayProductListInfoPage.action"><i class="icon icontree"></i>上架商品</a><br>
<a href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoUndercarriageProductListPage.action"><i class="icon icontree"></i>下架商品</a><br>
<a href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoIllegalProductListPage.action"><i class="icon icontree"></i>违规下架商品</a><br>
<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopEvaluationList.action"><i class="icon icontree"></i>客户评价管理</a><br>
</div>
</li>

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding03"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">订单管理 </h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopOrderList.action"><i class="icon icontree"></i>订单列表</a><br>
</div>
<%-- <div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopNoPayOrderList.action"><i class="icon icontree"></i>未结算订单列表</a><br>
</div> --%>
<%-- <div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopApplyOrderList.action"><i class="icon icontree"></i>申请结算记录</a><br>
</div> --%>
</li>
<li>
<div class="">
<a class="publicblock icon iconjian" id="folding04"></a> 
<h4 class="publicPadding10 FontSize14 FontSizeB">退货管理</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/frontShopComplaints/gotoReturnsApplyListPageShop.action"><i class="icon icontree"></i>退货列表</a><br>
</div>
</li>
<li>
<div class="">
<a class="publicblock icon iconjian" id="folding05"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">申请结算</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopNoPayOrderList.action"><i class="icon icontree"></i>店铺申请结算</a><br>
<a href="${pageContext.request.contextPath}/front/store/shopOrder/shopApplyRecord.action"><i class="icon icontree"></i>申请结算记录</a><br>
<a href="${pageContext.request.contextPath}/front/store/orderStatistics/gotoCollectiveOrderPage.action"><i class="icon icontree"></i>订单统计</a><br>
</div>
</li>

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding06"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">会员管理 </h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/memberShip/listMemberShip.action"><i class="icon icontree"></i>VIP金卡会员列表</a><br>
</div>
</li>

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding07"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">客服管理</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/customerService/gotoCustomerServicePage.action"><i class="icon icontree"></i>添加QQ客服</a><br>
<a href="${pageContext.request.contextPath}/front/store/shopCustomerService/listShopCustomerService.action"><i class="icon icontree"></i>维护QQ客服</a><br>
</div>
</li>

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding07"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">商城币管理</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/supplierMallCoin/gotoSCBTXPage.action"><i class="icon icontree"></i>商城币提现</a><br>
<a href="${pageContext.request.contextPath}/front/store/supplierMallCoin/gotoShopSupplierMallCoinDetailListPage.action"><i class="icon icontree"></i>商城币收支明细</a><br>
<a href="${pageContext.request.contextPath}/front/store/supplierMallCoin/gotoSCBTXListPage.action"><i class="icon icontree"></i>商城币提现列表</a><br>
</div>
</li>

<%-- <li>
<div class="">
<a class="publicblock icon iconjian" id="folding06"></a>
<h4 class="publicPadding10 FontSize14 FontSizeB">资讯信息</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/store/infoMationCategory/gotoInformationCategoryPage.action"><i class="icon icontree"></i>资讯信息</a><br>
</div>
</li> --%>
</ul>
</nav>
</aside>
<script>
	$("#folding01").Folding();
	$("#folding02").Folding();
	$("#folding03").Folding();
	$("#folding04").Folding();
	$("#folding05").Folding();
	$("#folding06").Folding();
	$("#folding07").Folding();
	
	//初始化判断当前操作用户是否为供应商用户 
	$(function(){
		//如果不是供应商用户则跳转至首页
		var type="${customer.type}";
		if(type!=2){
			window.location = "${pageContext.request.contextPath}/";
		}
	});
</script>
<!--//left-->