<!--left用户中心左侧导航-->
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function doRedirect(state) {
	var url ="${pageContext.request.contextPath}/loginCustomer/checkLogin.action";
	$.post(url,{},function(data){
			if (data.bool == false) {
				window.location = "${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action";
			} else {
				if (state == "1") {//My account
					window.location = "${pageContext.request.contextPath}/front/customer/index.action";
				} else if (state == "2") {//cart
					window.location = "${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
				}
			}
	},"JSON");
} 
//初始化判断当前操作用户是否为个人用户 
$(function(){
	//如果不是个人用户则跳转至首页
	var type="${customer.type}";
	if(type!=3){
		window.location = "${pageContext.request.contextPath}/";
	}
});
</script>
<div id="Account_left" class="publicBorder float-left BackgroundF8Hui ">
<h3 class="publicPadding10 ColorRed publicBorderB_3red FontSize15">
	<a class="accountSettings" href="javascript:;" onclick="doRedirect('1');">用户中心 </a>
<!--     <i class="icon iconAccount01"></i> -->
</h3>
<nav>
<ul class="Account_left_nav">

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding01"></a>
<h4 class="publicPadding10 FontSize14_h">账户信息</h4>
</div>
<div class="line-height1 nav_Small">
	<s:if test="#session.sonaccount==null">
		<a class="accountSettings" href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action"><i class="icon icontree"></i>基础信息</a><br>
		<a href="${pageContext.request.contextPath}/front/customer/Setting/gotoForInvoicePage.action"><i class="icon icontree"></i>发票信息</a><br>
		<%-- 
		<a href="${pageContext.request.contextPath}/front/store/frontshopinfo/gotoFrontShopInfoPage.action"><i class="icon icontree"></i>企业信息</a><br>
		<a href="${pageContext.request.contextPath}/front/customer/Setting/listSonaccount.action"><i class="icon icontree"></i>子帐号管理</a><br> --%>
		<a class="accountSettings" href="${pageContext.request.contextPath}/front/customer/Setting/changePass.action"><i class="icon icontree"></i>修改密码</a><br>
	</s:if>
	<a class="accountSettings" href="${pageContext.request.contextPath}/front/customer/Setting/address.action"><i class="icon icontree"></i>收货地址</a><br>
	<s:if test="#session.customer.type!=2">
		<a href="${pageContext.request.contextPath}/front/customer/customerMallCoin/getCustomerMallCoinDetail.action"><i class="icon icontree"></i>我的商城币</a><br>
	</s:if>
	<a href="${pageContext.request.contextPath}/front/customer/Setting/gotoShareRegister.action"><i class="icon icontree"></i>分享注册</a><br>
	<a href="${pageContext.request.contextPath}/front/customer/Setting/gotoShareRegisterDetail.action"><i class="icon icontree"></i>分享记录</a><br>
</div>
</li>
<li>
<div class="">
<a class="publicblock icon iconjian" id="folding02"></a>
<h4 class="publicPadding10 FontSize14_h">申请的VIP店铺</h4>
</div>
<div class="line-height1 nav_Small">
<a  href="${pageContext.request.contextPath}/front/memberShip/memberApplyList.action"><i class="icon icontree"></i>申请的VIP店铺</a><br>
</div>
</li>

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding03"></a>
<h4 class="publicPadding10 FontSize14_h">我的订单</h4>
</div>
<!-- <div class="line-height1 nav_Small"> -->
<!-- <a href="${pageContext.request.contextPath}/front/customer/myOrders/toPayOrdersList.action"><i class="icon icontree"></i>未结算订单列表</a><br> -->
<!-- </div> -->
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/customer/myOrders/OrderList.action"><i class="icon icontree"></i>订单列表</a><br>
</div>
</li>
<s:if test="#session.sonaccount==null||#session.sonaccount.type==1">
<li>
<div class="">
<a class="publicblock icon iconjian" id="folding05"></a>
<h4 class="publicPadding10 FontSize14_h">我的评价</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/customer/commentList.action"><i class="icon icontree"></i>评价列表 </a><br>
</div>
</li>

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding06"></a>
<h4 class="publicPadding10 FontSize14_h">我的收藏</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/customer/wishList.action"><i class="icon icontree"></i>商品收藏</a><br>
<a href="${pageContext.request.contextPath}/front/customer/followingShop.action"><i class="icon icontree"></i>店铺收藏</a><br>
</div>
</li>

<li>
<div class="">
<a class="publicblock icon iconjian" id="folding07"></a>
<h4 class="publicPadding10 FontSize14_h">退货管理</h4>
</div>
<div class="line-height1 nav_Small">
<a href="${pageContext.request.contextPath}/front/customer/frontCustomerComplaints/gotoReturnsApplyListPage.action"><i class="icon icontree"></i>退货列表</a><br>
</div>
</li>
</s:if>

</ul>
</nav>
</div>
<script>
$("#folding01").Folding();
$("#folding02").Folding();
$("#folding03").Folding();
$("#folding04").Folding();
$("#folding05").Folding();
$("#folding06").Folding();
$("#folding07").Folding();
$("#folding08").Folding();
</script>
<!--//left-->