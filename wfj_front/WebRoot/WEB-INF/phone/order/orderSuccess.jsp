<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Expires" CONTENT="0">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="Pragma" CONTENT="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单提交页面</title>
<style type="text/css">
body {
	background-color: #EEEEEE;
	font-size: 16px;
	font-family: Arial, Helvetica, sans-serif, "黑体";
	padding: 0px;
	margin: 0px;
}

#content {
	margin: 2px;
	padding: 2px;
}

.box {
	background-color: #FFFFFF;
	border: 0px dashed gray;
	float: left;
	width: 100%;
	margin-top: 2%;
	margin-left: 1%;
	margin-right: 1%;
	padding: 0;
	display: block
}

.money {
	font-size: 18px;
	color: #FF0000
}

.item {
	float: left;
	padding-top: 4%;
	padding-bottom: 4%;
	text-align: center;
}

div .box {
	border: 0px dashed gray;
	margin-bottom: 0px
}

a {
	text-decoration: none;
	color: #000000
}

.mbox {
	background-color: #FFFFFF;
	border-bottom: 1px solid #CDCDCD;
	float: left;
	width: 100%;
	margin-bottom: 3%;
	margin-left: 2%;
	margin-right: 2%;
	padding: 0;
	display: block
}
</style>
<script type="text/javascript">
	window.onlocad = function() {
		location.href = window.opener.location.href;
	}

	//初始化或者刷新页面时，在使用了SHOPJSP币抵扣显示抵扣金额和使用的SHOPJSP币数量
	$(function() {
		//初始化选中开发票操作
		$("#isInvoice_1").attr("checked", "checked");
		$("#userCoin").val(0);//使用金币数量
		$("#p_total_coin").css("display", "none");
		//获取订单中金币抵扣上限比例
		var limitCoinRules = '${application.coinRules["limit_Proportion"][0].value}';//当前兑换比例 20%
		var totalAmount = $("#totalAmount").val();//含运费的商品价格
		var canchangeCoin = totalAmount * limitCoinRules / 100;//加入金币比例的计算
		var coinRules = '${application.coinRules["changeCoin"][0].value}';//当前兑换比例
		var canUserCoin = canchangeCoin * coinRules;
		$("#exchangeIegulation")
				.html("最多可抵扣商品总金额(含运费)的" + limitCoinRules + "%");//政策和规则提示信息
		$("#canUserCoin").html(canUserCoin.toFixed(0));//当前订单可以使用金币数量上限
		$("#canchangeCoin").html(canchangeCoin.toFixed(0));//当前订单可以使用金额数量上限
		// 		if(userCoin>0){
		// 			changeImg('plus');//使用的SHOPJSP币数量
		// 			
		// 		}
		if ('<s:property value='isAllTransactionFlag' />' == "all") {
			$("#yOrder").css("display", "none");
			$("#nOrder").css("display", "none");
		} else if ('<s:property value='isAllTransactionFlag' />' == "oneMore") {
			$("#yOrder").css("display", "none");
			$("#nOrder").css("display", "");
		}
	});
	//更改收货地址
	function changeAddress() {
		window
				.open(
						'${pageContext.request.contextPath}/front/customer/shoppingOnline/gotoCustomerAddressPage.action',
						'newwindow',
						'height=500, width=800, top=100, left=300, toolbar=no, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=no');
	}
	//修改完地址返回调用的方法
	function doAddress(addressId) {
		var url = '${pageContext.request.contextPath}/front/customer/addressAction/getAddress.action';
		$.post(url, {
			"CAAId" : addressId
		}, function(data) {
			if (data) {
				var custAddress = data.customerAcceptAddress;
				$("#accpePostCode1").html(custAddress.postcode);
				$("#customerAcceptAddressId").val(
						custAddress.customerAcceptAddressId);
				$("#Addressee").html(custAddress.consignee);
				$("#Address").html(custAddress.address);
				$("#Email").html(custAddress.mobilePhone);
				$("#Postcode").html(custAddress.postcode);
				$("#name").html(custAddress.consignee);
				$("#ShippingTo").html(custAddress.address);
				$("#accpeEmail").html(custAddress.mobilePhone);
			}
		}, "json");
	}
	//确认订单提交
	function toSuccess() {
		/* 		var shopInfoCheckSatus = '${shopInfo.shopInfoCheckSatus}';//企业认证状态
		 var son='${sonaccount}';//子账号信息
		 var sonType='${sonaccount.type}';//子账号类型
		 var type = '${customer.type}';//账户类型 */
		var custAddId = $("#customerAcceptAddressId").val();
		/* 		if(type!=3){
		 if(shopInfoCheckSatus==2){//2为认证通过
		 if(son!=""){//当前登陆人为企业子账号
		 if(sonType==2){//当前登录人不是采购人员
		 lalert('消息提醒', '您的权限不足,无法生成订单!');
		 return false;
		 }
		 }else{
		 lalert('消息提醒', '您的权限不足,无法生成订单!');
		 return false;
		 }
		 }else{
		 lalert('消息提醒', '您的企业资质认证尚未通过,无法生成订单!');
		 return false;
		 }
		 } */
		if ("" == custAddId) {
			$("#scrollto_dz").ScrollTo(200);
			lalert('消息提醒', '<font style="font-size:16px;">请添写收货地址！</font>');
			return;
		}
		$
				.ajax({
					url : "${pageContext.request.contextPath}/front/customer/shoppingOnline/checkProduct.action",
					dataType : "json",
					type : "post",
					data : {
						productId : $("#productId").val(),
						shopInfoId : $("#shopInfoId").val(),
						shopCartIds : $("#shopCartIds").val()
					},
					success : function(data) {
						if (data.isFlag == true) {
							if (data.edFlag == true) {
								$(".submitImg").attr("disabled", "disabled");
								$(".submitImgLoad").attr("style",
										"display:block;");
								$("#form1").submit();
							} else {
								lalert('消息提醒', '您的授信额度小于订单交易总金额,无法生成订单!');
							}
						} else {
							lalert('消息提醒', '商品不满足购买条件,无法生成订单!');
						}
					}
				});
	}

	//继续提交
	function goOnToOrder() {
		var shopInfoCheckSatus = '${shopInfo.shopInfoCheckSatus}';//企业认证状态
		var son = '${sonaccount}';//子账号信息
		var sonType = '${sonaccount.type}';//子账号类型
		var custAddId = $("#customerAcceptAddressId").val();
		if (type != 3) {
			if (shopInfoCheckSatus == 2) {//2为认证通过
				if (son != "") {//当前登陆人为企业子账号
					if (sonType == 2) {//当前登录人为采购人员
						lalert('消息提醒', '您的权限不足,无法生成订单!');
					}
				} else {
					lalert('消息提醒', '您的权限不足,无法生成订单!');
				}
			} else {
				lalert('消息提醒', '您的企业资质认证尚未通过,无法生成订单!');
			}
		}
		if ("" == custAddId) {
			$("#scrollto_dz").ScrollTo(200);
			lalert('消息提醒', '<font style="font-size:16px;">请添写收货地址！</font>');
			return;
		}
		if (false) {
			$
					.ajax({
						url : "${pageContext.request.contextPath}/front/customer/shoppingOnline/checkProduct.action",
						dataType : "json",
						type : "post",
						data : {
							productId : $("#productId").val(),
							shopInfoId : $("#shopInfoId").val(),
							shopCartIds : $("#shopCartIds").val()
						},
						success : function(data) {
							if (data.isFlag == true) {
								if (data.edFlag == true) {
									$(".submitImg")
											.attr("disabled", "disabled");
									$(".submitImgLoad").attr("style",
											"display:block;");
									$("#form1").submit();
								} else {
									lalert('消息提醒', '您的授信额度小于订单交易总金额,无法生成订单!');
								}
							} else {
								window.location.href = window.location.href;
							}
						}
					});
		}
	}
	//使用SHOPJSP币兑换
	function changeImg(obj) {
		if (obj == "plus") {//图片为‘+’号时不显示输入框
			$("#minus").css("display", "");
			$("#plus").css("display", "none");
		} else {//图片为'-'时显示下方输入框
			$("#plus").css("display", "");
			$("#minus").css("display", "none");
			$("#errorMsg").html("");
		}
	}
	/**
	 * 将数值四舍五入(保留2位小数)后格式化成金额形式
	 *
	 * @param num 数值(Number或者String)
	 * @return 金额格式的字符串,如'1,234,567.45'
	 * @type String
	 */
	function formatCurrency(num) {
		num = num.toString().replace(/\$|\,/g, '');
		if (isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num * 100 + 0.50000000001);
		cents = num % 100;
		num = Math.floor(num / 100).toString();
		if (cents < 10)
			cents = "0" + cents;
		for ( var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
			num = num.substring(0, num.length - (4 * i + 3)) + ','
					+ num.substring(num.length - (4 * i + 3));
		return (((sign) ? '' : '-') + num + '.' + cents);
	}
	//统计金额和数量
	function amountMoneyCart() {
		var amountItme = document.getElementsByName("amountItme_cart");//购买商品数
		var costPriceTotal = 0;//市场价总额
		var Subtotal_cart = 0;//销售总价格
		var total = "";//小计
		for ( var i = 0; i < amountItme.length; i++) {
			var priceId = amountItme[i].id;
			var array = new Array();
			array = priceId.split("_");
			var productId = array[1];//购物车id
			if (document.getElementById("checkbox_" + productId).checked) {
				var price = $("#salesPrice" + productId).val();//单价
				var costPrice = $("#costPrice" + productId).val();//市场价
				var discount = $("#discount" + productId).val();//会员折扣
				var OneAmount = amountItme[i].value;//当前商品数量
				if (null != discount && discount != "") {
					total = Number(price * OneAmount * discount / 10);
					//Subtotal_cart=Number(price*OneAmount*discount/10)+Number(Subtotal_cart);//销售总价格
					//costPriceTotal=Number(costPrice*OneAmount*discount/10)+Number(costPriceTotal);//市场价总额
				} else {
					total = Number(price * OneAmount);
					//Subtotal_cart=Number(price*OneAmount)+Number(Subtotal_cart);//销售总价格
					//costPriceTotal=Number(costPrice*OneAmount)+Number(costPriceTotal);//市场价总额
				}
				Subtotal_cart = Number(total) + Number(Subtotal_cart);//销售总价格
				costPriceTotal = Number(costPrice * OneAmount)
						+ Number(costPriceTotal);//市场价总额
				total = formatCurrency(total);
				$("#total" + productId).html("￥" + total);//当前商品价格小计
				Subtotal_cart = Subtotal_cart.toFixed(2);
				costPriceTotal = costPriceTotal.toFixed(2);
			}
		}
		$("#SubtotalItems").html("折扣前总价:￥" + formatCurrency(costPriceTotal));//市场价总额
		$("#Subtotal_cart").html("￥" + formatCurrency(Subtotal_cart));//销售价总额
		var Discount = Number(costPriceTotal) - Number(Subtotal_cart);//差额
		Discount = formatCurrency(Discount);//小数点后两位 
		$("#Discount").html("-&nbsp;折扣: ￥" + Discount);
		$("#hiddenDiscount").val(Discount);
		allPrice();//计算价格
		//$("#StatusSubNum").html(SubNum+"/50");
	}
	//更改使用的金币数量
	function changeCoin(coin) {
		var a = /^[1-9]*[1-9][0-9]*$/;
		var finalAmount = $("#finalAmount-des").val();//用固定不变的数据来操作  否侧会产生bug
		var canchangeCoin = $("#canchangeCoin").text();//抵扣最高金额
		if (!a.test(coin)) {
			$("#errorMsg").html("只能输入大于零的正整数！");
			$("#userCoin").val("0");
			$("#p_total_coin").css("display", "none");
			$("#total_coin").html("0");
			$("#show_changeAmount").html("￥0.00");//显示兑换的金额
			$("#finalAmount").val(finalAmount);
			$("#subPrice").html(finalAmount);
			return;
		} else if (Number(coin) > Number('${coinTotal}')) {//当前输入数量大于实际用户拥有数量
			$("#errorMsg").html("输入的数量大于当前拥有的金币数量！");
			$("#userCoin").val("0");
			$("#p_total_coin").css("display", "none");
			$("#total_coin").html("0");
			$("#finalAmount").val(finalAmount);
			$("#subPrice").html(finalAmount);
			return;
		} else if (Number(coin) > Number($("#canUserCoin").text())) {//当前输入数量大于实际用户拥有数量
			$("#errorMsg").html("输入的数量大于当前使用金币数量上限！");
			$("#userCoin").val("0");
			$("#p_total_coin").css("display", "none");
			$("#total_coin").html("0");
			$("#finalAmount").val(finalAmount);
			$("#subPrice").html(finalAmount);
			return;
		} else {
			var coinRules = '${application.coinRules["changeCoin"][0].value}';//当前兑换比例
			var coinTotal = coin / Number(coinRules);
			var ct = Number(coinTotal).toFixed(0);
			var cc = Number(canchangeCoin).toFixed(0);
			if (Number(ct) > Number(cc)) {
				$("#errorMsg").html("抵扣的金额大于当前订单订单抵扣上限金额！");
				$("#userCoin").val("0");
				$("#p_total_coin").css("display", "none");
				$("#total_coin").html("0");
				$("#finalAmount").val(finalAmount);
				$("#subPrice").html(finalAmount);
				return;
			} else {
				$("#show_changeAmount").html("￥" + coinTotal.toFixed(2));//显示兑换的金额
				$("#changeAmount").val(coinTotal.toFixed(2));//隐藏域存放保存的兑换金额
				$("#userCoin").val(coin);//使用的金币数量
				$("#p_total_coin").css("display", "");//显示抵扣金额
				$("#total_coin").html("￥" + coinTotal.toFixed(2));

				finalAmount = Number(finalAmount)
						- Number(coinTotal.toFixed(2));//总金额去掉抵扣金额
				$("#finalAmount").val(finalAmount.toFixed(2));
				$("#subPrice").html(finalAmount.toFixed(2));
				$("#coinTotalsss").val(coinTotal);
			}
		}
		allPrice();//计算价格
		$("#errorMsg").html("");
	}

	//计算最终价格
	function allPrice() {
		var okcoupon = $("#okcoupon").val();//优惠券
		// 		var coinTotalsss=$("#coinTotalsss").val();//商城币
		var coinTotalsss = $("#changeAmount").val();//使用的金币数量
		var tempMoney = $("#tempMoney").val();//税费
		var isnoFreight = $("#isnoFreight").val();//运费
		var finalAmount = $
		{
			orders.finalAmount
		}//总价格
		var freight = $
		{
			orders.freight
		}
		;
		var allfinalAmount;
		if (isnoFreight != freight) {
			allfinalAmount = Number(finalAmount) - Number(okcoupon)
					- Number(coinTotalsss) + Number(tempMoney)
					- Number(freight);
		} else {
			allfinalAmount = Number(finalAmount) - Number(okcoupon)
					- Number(coinTotalsss) + Number(tempMoney);
		}
		$("#finalAmount").val(allfinalAmount.toFixed(2));
	}
</script>

</head>

<body>
	<div class="mui-content">
		<div>
			<div align="center">
				<img name="image1" style="margin-top: 20%;"
					src="http://192.168.1.145:8080/wfj_front/orderSuccess.jpg" />
			</div>
			<br />
			<h1 style="color: gray;" align="center">订单生成成功</h1>
			<div align="center">
				<a href="#"><font color="green">查看订单</font></a>
			</div>

		</div>
	</div>


</body>
</html>
