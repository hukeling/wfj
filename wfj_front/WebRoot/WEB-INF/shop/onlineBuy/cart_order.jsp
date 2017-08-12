<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>订单信息</title>
	<%@ include file="../include/head.jsp"%>
	<link rel="stylesheet" type="text/css"	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.CardIdNo.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollto.js"></script>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
	<style type="text/css">
		#yOrder a:hover{text-decoration:none;}
	</style>
	
	<script type="text/javascript">
	//初始化或者刷新页面时，在使用了SHOPJSP币抵扣显示抵扣金额和使用的SHOPJSP币数量
	$(function(){
		//初始化选中开发票操作
		$("#isInvoice_1").attr("checked","checked");
		$("#userCoin").val(0);//使用金币数量
		$("#p_total_coin").css("display","none");
		//获取订单中金币抵扣上限比例
		var limitCoinRules = '${application.coinRules["limit_Proportion"][0].value}';//当前兑换比例 20%
		var totalAmount = $("#totalAmount").val();//含运费的商品价格
		var canchangeCoin = totalAmount*limitCoinRules/100;//加入金币比例的计算
		var coinRules = '${application.coinRules["changeCoin"][0].value}';//当前兑换比例
		var canUserCoin = canchangeCoin*coinRules;
		$("#exchangeIegulation").html("最多可抵扣商品总金额(含运费)的"+limitCoinRules+"%");//政策和规则提示信息
		$("#canUserCoin").html(canUserCoin.toFixed(0));//当前订单可以使用金币数量上限
		$("#canchangeCoin").html(canchangeCoin.toFixed(0));//当前订单可以使用金额数量上限
// 		if(userCoin>0){
// 			changeImg('plus');//使用的SHOPJSP币数量
// 			
// 		}
		if('<s:property value='isAllTransactionFlag' />'=="all"){
			$("#yOrder").css("display","none");
			$("#nOrder").css("display","none");
		}else if('<s:property value='isAllTransactionFlag' />'=="oneMore"){
			$("#yOrder").css("display","none");
			$("#nOrder").css("display","");
		}
	});
	//更改收货地址
	function changeAddress() {
	 	window.open(
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
				$("#customerAcceptAddressId").val(custAddress.customerAcceptAddressId);
				$("#Addressee").html(custAddress.consignee );
				$("#Address").html(custAddress.address );
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
		$.ajax({
   			url:"${pageContext.request.contextPath}/front/customer/shoppingOnline/checkProduct.action",
   			dataType:"json",
   			type:"post",
   			data:{productId:$("#productId").val(),shopInfoId:$("#shopInfoId").val(),shopCartIds:$("#shopCartIds").val()},
   			success:function(data){
   				if(data.isFlag==true){
   					if(data.edFlag==true){
   						$(".submitImg").attr("disabled","disabled");
   						$(".submitImgLoad").attr("style","display:block;");
   						$("#form1").submit();
   					}else{
   						lalert('消息提醒', '您的授信额度小于订单交易总金额,无法生成订单!');
   					}
   				}else{
   					lalert('消息提醒', '商品不满足购买条件,无法生成订单!');
   				}
   			}
   		});
	}
	
	//继续提交
	function goOnToOrder() {
		var shopInfoCheckSatus = '${shopInfo.shopInfoCheckSatus}';//企业认证状态
		var son='${sonaccount}';//子账号信息
		var sonType='${sonaccount.type}';//子账号类型
		var custAddId = $("#customerAcceptAddressId").val();
		if(type!=3){ 
			if(shopInfoCheckSatus==2){//2为认证通过
				if(son!=""){//当前登陆人为企业子账号
					if(sonType==2){//当前登录人为采购人员
						lalert('消息提醒', '您的权限不足,无法生成订单!');
					}
				}else{
					lalert('消息提醒', '您的权限不足,无法生成订单!');
				}
			}else{
				lalert('消息提醒', '您的企业资质认证尚未通过,无法生成订单!');
			}
		}
		if ("" == custAddId) {
			$("#scrollto_dz").ScrollTo(200);
			lalert('消息提醒', '<font style="font-size:16px;">请添写收货地址！</font>');
			return;
		}
		if(false){
			$.ajax({
	   			url:"${pageContext.request.contextPath}/front/customer/shoppingOnline/checkProduct.action",
	   			dataType:"json",
	   			type:"post",
	   			data:{productId:$("#productId").val(),shopInfoId:$("#shopInfoId").val(),shopCartIds:$("#shopCartIds").val()},
	   			success:function(data){
	   				if(data.isFlag==true){
	   					if(data.edFlag==true){
	   						$(".submitImg").attr("disabled","disabled");
	   						$(".submitImgLoad").attr("style","display:block;");
	   						$("#form1").submit();
	   					}else{
	   						lalert('消息提醒', '您的授信额度小于订单交易总金额,无法生成订单!');
	   					}
	   				}else{
	   					window.location.href=window.location.href;
	   				}
	   			}
	   		}); 
		}
	}
	//使用SHOPJSP币兑换
	function changeImg(obj){
		if(obj=="plus"){//图片为‘+’号时不显示输入框
			$("#minus").css("display","");
			$("#plus").css("display","none");
		}else{//图片为'-'时显示下方输入框
			$("#plus").css("display","");
			$("#minus").css("display","none");
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
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    cents = num%100;
	    num = Math.floor(num/100).toString();
	    if(cents<10)
	    cents = "0" + cents;
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num + '.' + cents);
	}
	//统计金额和数量
	function amountMoneyCart(){
		var amountItme = document.getElementsByName("amountItme_cart");//购买商品数
		var costPriceTotal =0;//市场价总额
		var Subtotal_cart=0;//销售总价格
		var total="";//小计
		for(var i=0;i<amountItme.length;i++){
			var priceId=amountItme[i].id;
			var array = new Array();
			array=priceId.split("_");
			var productId=array[1];//购物车id
			if(document.getElementById("checkbox_"+productId).checked){
				var price=$("#salesPrice"+productId).val();//单价
				var costPrice=$("#costPrice"+productId).val();//市场价
				var discount=$("#discount"+productId).val();//会员折扣
				var OneAmount=amountItme[i].value;//当前商品数量
				if(null!=discount&&discount!=""){
					total =Number(price*OneAmount*discount/10);
					//Subtotal_cart=Number(price*OneAmount*discount/10)+Number(Subtotal_cart);//销售总价格
					//costPriceTotal=Number(costPrice*OneAmount*discount/10)+Number(costPriceTotal);//市场价总额
				}else{
					total =Number(price*OneAmount);
					//Subtotal_cart=Number(price*OneAmount)+Number(Subtotal_cart);//销售总价格
					//costPriceTotal=Number(costPrice*OneAmount)+Number(costPriceTotal);//市场价总额
				}
				Subtotal_cart=Number(total)+Number(Subtotal_cart);//销售总价格
				costPriceTotal=Number(costPrice*OneAmount)+Number(costPriceTotal);//市场价总额
				total = formatCurrency(total);
				$("#total"+productId).html("￥"+total);//当前商品价格小计
				Subtotal_cart=Subtotal_cart.toFixed(2); 
				costPriceTotal=costPriceTotal.toFixed(2);
			}
		}
		$("#SubtotalItems").html("折扣前总价:￥"+formatCurrency(costPriceTotal));//市场价总额
		$("#Subtotal_cart").html("￥"+formatCurrency(Subtotal_cart));//销售价总额
		var Discount = Number(costPriceTotal)-Number(Subtotal_cart);//差额
		Discount=formatCurrency(Discount);//小数点后两位 
		$("#Discount").html("-&nbsp;折扣: ￥"+Discount);
		$("#hiddenDiscount").val(Discount);
		allPrice();//计算价格
		//$("#StatusSubNum").html(SubNum+"/50");
	}
	//更改使用的金币数量
	function changeCoin(coin){
		var a=/^[1-9]*[1-9][0-9]*$/;
		var finalAmount = $("#finalAmount-des").val();//用固定不变的数据来操作  否侧会产生bug
		var canchangeCoin = $("#canchangeCoin").text();//抵扣最高金额
	    	if(!a.test(coin)){
	    		$("#errorMsg").html("只能输入大于零的正整数！");
	    		$("#userCoin").val("0");
	    		$("#p_total_coin").css("display","none");
	    		$("#total_coin").html("0");
	    		$("#show_changeAmount").html("￥0.00");//显示兑换的金额
	    		$("#finalAmount").val(finalAmount);
	    		$("#subPrice").html(finalAmount);
	    		return ;
	    	}else if(Number(coin)>Number('${coinTotal}')){//当前输入数量大于实际用户拥有数量
	    		$("#errorMsg").html("输入的数量大于当前拥有的金币数量！");
	    		$("#userCoin").val("0");
	    		$("#p_total_coin").css("display","none");
	    		$("#total_coin").html("0");
	    		$("#finalAmount").val(finalAmount);
	    		$("#subPrice").html(finalAmount);
	    		return ;
	    	}else if(Number(coin)>Number($("#canUserCoin").text())){//当前输入数量大于实际用户拥有数量
	    		$("#errorMsg").html("输入的数量大于当前使用金币数量上限！");
	    		$("#userCoin").val("0");
	    		$("#p_total_coin").css("display","none");
	    		$("#total_coin").html("0");
	    		$("#finalAmount").val(finalAmount);
	    		$("#subPrice").html(finalAmount);
	    		return ;
	    	}else{
	    		var coinRules = '${application.coinRules["changeCoin"][0].value}';//当前兑换比例
	    		var coinTotal = coin/Number(coinRules);
	    		var ct=Number(coinTotal).toFixed(0);
	    		var cc=Number(canchangeCoin).toFixed(0);
	    		if(Number(ct)>Number(cc)){
	    			$("#errorMsg").html("抵扣的金额大于当前订单订单抵扣上限金额！");
		    		$("#userCoin").val("0");
		    		$("#p_total_coin").css("display","none");
		    		$("#total_coin").html("0");
		    		$("#finalAmount").val(finalAmount);
		    		$("#subPrice").html(finalAmount);
		    		return ;
	    		}else{
		    		$("#show_changeAmount").html("￥"+coinTotal.toFixed(2));//显示兑换的金额
		    		$("#changeAmount").val(coinTotal.toFixed(2));//隐藏域存放保存的兑换金额
		    		$("#userCoin").val(coin);//使用的金币数量
		    		$("#p_total_coin").css("display","");//显示抵扣金额
		    		$("#total_coin").html("￥"+coinTotal.toFixed(2));
		    		
		    		finalAmount = Number(finalAmount)-Number(coinTotal.toFixed(2));//总金额去掉抵扣金额
		    		$("#finalAmount").val(finalAmount.toFixed(2));
		    		$("#subPrice").html(finalAmount.toFixed(2));
			    	$("#coinTotalsss").val(coinTotal);
	    		}
	    	}
	    	allPrice();//计算价格
	    	$("#errorMsg").html("");
	}
	
	//计算最终价格
	function allPrice(){
		var okcoupon=$("#okcoupon").val();//优惠券
// 		var coinTotalsss=$("#coinTotalsss").val();//商城币
		var coinTotalsss=$("#changeAmount").val();//使用的金币数量
		var tempMoney=$("#tempMoney").val();//税费
		var isnoFreight=$("#isnoFreight").val();//运费
		var finalAmount=${orders.finalAmount}//总价格
		var freight =${orders.freight};
		var allfinalAmount;
		if(isnoFreight!=freight){
			allfinalAmount=Number(finalAmount)-Number(okcoupon)-Number(coinTotalsss)+Number(tempMoney)-Number(freight);
		}else{
			allfinalAmount=Number(finalAmount)-Number(okcoupon)-Number(coinTotalsss)+Number(tempMoney);
		}
		$("#finalAmount").val(allfinalAmount.toFixed(2));
	}
</script>
<style>
	.yjfhr{color:red;background-color: #FF0000;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
	.yjfhr2{color:red;background-color: #3C89FF;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
	.sku { border: 1px solid #d2d2d2;padding: 3px 5px;}
	input.Registration {
    border: 1px solid #999;
    border-radius: 3px;
    padding: 3px 5px;
}
</style>
</head>
	<body>
		<%@ include file="/WEB-INF/discountcoupon/car_order_discountcoupon.jsp"%>
		<%@ include file="../include/header.jsp"%>
	<div>
		<div class="margin-center PublicWidth1004">
			<!--mainClass-->

			<div id="cart" class=" ClearBoth">
				<div class="cart_title">
					<ul>
						<li class="title4 strong ColorWhite1 " style="line-height:41px;">
							1.我的购物车
						</li>
						<li class="title4 strong ColorWhite1 " style="line-height:41px;">
							2.填写核对订单信息
						</li>
						<li class="strong" style="line-height:41px;">3.成功提交订单</li>
					</ul>
				</div>
				<div class="BackgroundRed height5 publicMarginTB20"></div>
				<form id="form1" class="formLoad" action="${pageContext.request.contextPath}/front/customer/shoppingOnline/toSuccessOrder.action" method="post">
					<input type="hidden" name="shopInfoId" value="<s:property value="shopInfoId"/>"/>
					 <s:token></s:token>
<!-- 					收获地址 -->
					<div class="radius publicHidden publicMarginBot15">
						<div id="scrollto_dz" class="height2 publicHidden">
							<span
								class="float-left ColorRed strong publicPadding5 FontSize14">收货地址
								<a href="javascript:;" onclick="changeAddress();" class="ColorBlue strong publicPaddingLR5 publicunderline publicPaddingL7">编辑</a>
							</span>

						</div>
						<div class=" publicPadding20">
							<input type="hidden" id="customerAcceptAddressId" name="custAddress.customerAcceptAddressId" value="${custAddress.customerAcceptAddressId}" />
							<!-- 收货方式 -->
							<p style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">收货方式:</span>
								<span>
									<input id="sendType_1" type="radio" name="orders.sendType" value="1" onclick="clickSendType('1')" />&nbsp;&nbsp;快递公司
									<input id="sendType_2" type="radio" name="orders.sendType" value="2" onclick="clickSendType('2')" />&nbsp;&nbsp;同城快递
									<input id="sendType_3" type="radio" name="orders.sendType" value="3" onclick="clickSendType('3')" />&nbsp;&nbsp;线下自取
									<script type="text/javascript">
										$(function(){
											$("#sendType_1").attr("checked",true);
											$("#xiangxidizhi").css("display","");
											$("#shouhuoren").text("收货人");
										});
										function clickSendType(type){
											if(type=="3"){
												$("#shouhuoren").text("提货人");
												$("#xiangxidizhi").css("display","none");
											}else{
												$("#shouhuoren").text("收货人");
												$("#xiangxidizhi").css("display","");
											}
										}
									</script>
								</span>
							</p>
							<p style="display:block;height:20px;">
								<span id="shouhuoren" class="float-left text-right publicPaddingR7 strong widthpx100">收货人:</span>
								<span id="Addressee">${custAddress.consignee}
								 </span>
							</p>
							<p id="xiangxidizhi" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">详细地址:</span>
								<span id="Address">
									${custAddress.address}&nbsp;&nbsp;
								</span>
							</p>
							<p style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">联系电话:</span>
								<span id="Email">${custAddress.mobilePhone}</span>
							</p>
							
						</div>
						
<!-- 						发票信息 -->
						<div class="height2 publicMarginTB20 publicHidden">
							<span class="float-left ColorRed strong publicPadding5 FontSize14">发票信息</span>
						</div>
						<script type="text/javascript">
							$(function(){
								var st = '${shopInfo.invoiceType }';
								if(st==null||st==""){
									st = 1;
								}
								changeInvoice(st);
								$("input[name='orders.invoiceType'][value='"+st+"']").attr("checked","checked");
							});
							function changeInvoice(value){
								var tempMoney = "";
								var totalAmount = $("#amount").val();
								var shipping = $("#ordersFreight").val();
								totalAmount = Number(totalAmount)-Number(shipping);
								//var totalAmount=document.getElementById('subPrice').innerHTML;//获取当前应付金额
								if(value==1){
									$(".putong").hide();
									$(".zengzhishui").hide();
								}else if(value==2){
									$(".putong").show();
									$(".zengzhishui").hide();
									var generalRates = "<s:property value='#application.fileUrlConfig.generalRates'/>";//普通税率
									tempMoney = totalAmount*generalRates/100;
									totalAmount = Number(totalAmount) + Number(tempMoney);
								}else if(value==3){
									$(".putong").hide();
									$(".zengzhishui").show();
									var vatRates = "<s:property value='#application.fileUrlConfig.vatRates'/>";//增值税率
									tempMoney = totalAmount*vatRates/100;
									totalAmount = Number(totalAmount) + Number(tempMoney);
								}
								totalAmount = Number(totalAmount) + Number(shipping);
								totalAmount = Math.round(totalAmount*100)/100;
								//$("#changeAmount").val(0);//使用金币为0
								//$("#userCoin").val(0);
								//$("#total_coin").html("0");
								//$("#totalAmount").val(totalAmount);
								$("#finalAmount").val(totalAmount.toFixed(2));
								$("#finalAmount-des").val(totalAmount.toFixed(2));
								$("#subPrice").html(totalAmount.toFixed(2));
								$("#tempMoney").val(tempMoney);//税费
								var userCoin = $("#userCoin").val();//使用金币数量
								changeCoin(userCoin);//显示抵扣金额
								allPrice();//计算价格
							}
							
							$(function() {
								 //表单验证
									$("#form1").validate({meta: "validate", 
								   	      submitHandler:function(form){
							 			        form.submit();
								          }
							 	    });
								}); 
							//企业姓名校验
						    jQuery.validator.addMethod("checkCompanyName",function(value, element) {
						    	var companyName = '${shopInfo.companyName}';
						    	var flag=true;
						    	//正则校验
						    	var reg=/^[\u4e00-\u9fa5]+$/;
						    	if(value!=companyName){
					 				$.ajax({
					 					type: "POST",dataType: "JSON",
					 					url:"${pageContext.request.contextPath}/front/customer/Setting/checkCompanyName.action",
					 					data: {companyName:value},
					 					async : false,
					 					success: function(data){
											if(data.isSuccess==true){
												flag=false;
											}
										}
									});
								}
								return flag;
							},"企业名称已存在！" );
							//纳税人编号校验
						    jQuery.validator.addMethod("checkTaxpayerNumber",function(value, element) {
						    	var taxpayerNumber = '${shopInfo.taxpayerNumber}';
						    	var flag=true;
						    	if(value!=taxpayerNumber){
					 				$.ajax({
					 					type: "POST",dataType: "JSON",
					 					url:"${pageContext.request.contextPath}/front/customer/Setting/checkTaxpayerNumber.action",
					 					data: {taxpayerNumber:value},
					 					async : false,
					 					success: function(data){
											if(data.isSuccess==true){
												flag=false;
											}
										}
									});
								}
								return flag;
							},"您的纳税人编号已存在！" );
							//电话唯一校验
						      jQuery.validator.addMethod("checkPhoneForInvoice",function(value, element) {
						      	var phoneForInvoice = '${shopInfo.phoneForInvoice}';
						    	var flag=true;
						    	if(value!=phoneForInvoice){
					 				$.ajax({
					 				   type: "POST",dataType: "JSON",
					 				   url:"${pageContext.request.contextPath}/front/customer/Setting/checkPhoneForInvoice.action",
					 				   data: {phoneForInvoice:value},
					 				   async : false,
					 				   success: function(data){
					 				   		if(data.isSuccess==true){
						 					   flag=false;
						 				   }
					 				   }
					 				});
					 			}
					 			return flag;
							},"该电话已被注册！" );
							//账号校验
						    jQuery.validator.addMethod("checkBankAccountNumber",function(value, element) {
						    	var bankAccountNumber = '${shopInfo.bankAccountNumber}';
						    	var flag=true;
						    	if(value!=bankAccountNumber){
					 				$.ajax({
					 					type: "POST",dataType: "JSON",
					 					url:"${pageContext.request.contextPath}/front/customer/Setting/checkBankAccountNumber.action",
					 					data: {bankAccountNumber:value},
					 					async : false,
					 					success: function(data){
											if(data.isSuccess==true){
												flag=false;
											}
										}
									});
								}
								return flag;
							},"您的账号已存在！" );
						</script>
						<div class="publicPadding20">
							<p id="companyName" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">发票类型:</span>
								<span>
								<input type="radio" name="orders.invoiceType" value="1" onclick="changeInvoice(this.value);"/>&nbsp;不开发票&nbsp;
								<input type="radio" name="orders.invoiceType" value="2" onclick="changeInvoice(this.value);"/>&nbsp;普通发票&nbsp;
								<input type="radio" name="orders.invoiceType" value="3" onclick="changeInvoice(this.value);"/>&nbsp;增值税发票
								</span>
							</p>
							<p class="putong" id="companyName" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">发票抬头:</span>
								<span><input  type="text" name="orders.companyNameForInvoice" value="${shopInfo.companyName}"  class="{validate:{required:true,chinese:true,checkCompanyName:true}} Registration sku"  size="50"/></span>
							</p>
							<p class="putong" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">发票内容:</span>
								<span><input class="sku" type="text" name="orders.invoiceInfo" value="${shopInfo.invoiceInfo}" size="50"/></span>
							</p>
							<p class="putong" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">普通税率:</span>
								<span><s:property value='#application.fileUrlConfig.generalRates'/>%</span>
							</p>
							<p class="zengzhishui" id="companyName" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">发票抬头:</span>
								<%-- <span>${shopInfo.companyName}</span> --%>
								<span><input type="text" name="shopInfoCompanyName" value="${shopInfo.companyName}" class="{validate:{required:true,chinese:true,checkCompanyName:true}} Registration sku"  size="50"/></span>
							</p>
							<p class="zengzhishui" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">纳税人识别号:</span>
								<span><input type="text" name="orders.taxpayerNumber" class="{validate:{required:true,number:true,checkTaxpayerNumber:true}} Registration sku" value="${shopInfo.taxpayerNumber}" size="50"/></span>
							</p>
							<p class="zengzhishui" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">地址:</span>
								<span><input type="text" name="orders.addressForInvoice" class="{validate:{required:true}} Registration sku" value="${shopInfo.addressForInvoice}" size="50"/></span>
							</p>
							<p class="zengzhishui" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">电话:</span>
								<span><input  type="text" name="orders.phoneForInvoice" class="{validate:{required:true,phone:true,checkPhoneForInvoice:true}} Registration sku" value="${shopInfo.phoneForInvoice}" size="50"/>&nbsp;<font color="red">格式：0796-8128162</font></span>
							</p>
							<p class="zengzhishui" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">开户行:</span>
								<span><input type="text" name="orders.openingBank" class="{validate:{required:true}} Registration sku" value="${shopInfo.openingBank}" size="50"/></span>
							</p>
							<p class="zengzhishui" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">账号:</span>
								<span><input type="text" name="orders.bankAccountNumber" class="{validate:{required:true,number:true,checkBankAccountNumber:true,byteRangeLength:[16,19]}} Registration sku" value="${shopInfo.bankAccountNumber}" size="50"/></span>
							</p>
							<p class="zengzhishui" style="display:block;height:20px;">
								<span class="float-left text-right publicPaddingR7 strong widthpx100">增值税率:</span>
								<span><s:property value='#application.fileUrlConfig.vatRates'/>%</span>
							</p>
						</div>
						
 						<!--支付方式 -->
						<div class="height2 publicMarginTB20 publicHidden">
							<span class="float-left ColorRed strong publicPadding5 FontSize14">支付方式</span>
						</div>
						<%-- <div class="radius publicPadding20" style="padding: 0;">
							<div>
								<input id="payWay" name="orders.payMode" type="radio" value="1"
									checked="checked"/>
								<img style="height: 80px; width: 160px;"
									src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/cart/kqlogo.png"/>
							</div>
						</div> --%>
						<div class="radius publicPadding20" style="padding: 0;">
							<div style="float: left;">
								<input id="payWay" name="orders.payMode" type="radio" value="2" checked="checked" onclick="payMode('2')"/>
								支付宝
							</div>
							<div style=""> 
								<input id="payWay2" name="orders.payMode" type="radio" value="4" onclick="payMode('4')"/>
								网银支付
							</div>
							<script>
								$(function(){
									$("#payWay").attr("checked",true);
								});
								function payMode(v){
									$o = $("#wypay");
									if(v == '2'){
										$o.css("display","none");
									}else if (v == '4') {
										$o.css("display","block");
										$("#bankCodeC").attr("checked",true);
									}
								}
							</script>
							<div style="float: none;"></div>
							<div id="wypay" style="display: none;">
							  <span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
							    <input id="bankCodeC" name="orders.bankCode" type="radio" value="BOCB2C" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/zg.gif" alt="中国银行" />
							  </span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="ICBCB2C" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/gs.gif" alt="中国工商银行" />
							</span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="CMB" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/zs.gif" alt="招商银行" />
							</span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="CCB" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/js.gif" alt="中国建设银行" />
							</span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="ABC" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/ny.gif" alt="中国农业银行" />
							</span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="SPDB" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/pd.gif" alt="上海浦东发展银行" />
							</span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="GDB" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/gf.gif" alt="广发银行" />
							</span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="SHBANK" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/sh.gif" alt="上海银行" />
							</span>
							<span style="width:190px; margin:8px 0 0 10px; display:block; float:left;">
								<input name="orders.bankCode" type="radio" value="POSTGC" />
								<img src="${fileUrlConfig.fileServiceUploadRoot}bankImg/yz.gif" alt="中国邮政储蓄银行" />
							</span>
							</div>
						</div>
						<div style="clear:both;"></div>
						<div class="publicMarginTB20_h publicHidden" >
							<span
								class="float-left ColorRed strong publicPadding5 FontSize14">商品列表
								 <a href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action" class="ColorBlue strong publicPaddingLR5 publicunderline publicMarginL10">跳转购物车&nbsp;&gt;&gt;</a>
							</span>
						</div>
						<div class="order">
							<table width="1209" border="0"
								class="BackgroundF2Hui text-center line-height3">
								<tr class="height2  height2_h Backgroundpink">
									<td align="center" class="publicBorderR strong">订货号</td>
									<td align="center" class="publicBorderR strong">商品</td>
									<td width="100" align="center" valign="middle"class="publicBorderR strong">店铺</td>
									<td width="100" align="center" valign="middle"class="publicBorderR strong">运费</td>
									<td width="123" align="center" valign="middle"class="publicBorderR strong">价格</td>
									<td width="85" align="center" valign="middle" class="publicBorderR strong publicBorderB">会员折扣 </td>
									<!-- <td width="123" align="center" valign="middle"class="publicBorderR strong">成交价格</td> -->
									<td width="123" align="center" valign="middle"class="publicBorderR strong">预计出货日</td>
									<td width="85" align="center" valign="middle"class="publicBorderR strong">数量</td>
									<td width="85" align="center" valign="middle"class="publicBorderR strong">库存</td>
									<td width="110" align="center" valign="middle" class="publicBorderR strong">小计(未含运费)</td>
									<td width="130" align="center" valign="middle" class="strong">折后小计(未含运费)</td>
								</tr>
								<s:iterator value="#request.shopInfoMap" var="map">
								<s:iterator value="#map.value" var="list" status="a">
									<tr>
										<td width="86" align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
											${list.sku}
										</td>
										<td width="86" align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
											<a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value="#list.productId" />.html" class="strong">
												<img title="<s:property value="#list.productFullName" />" src="<s:property value='#application.fileUrlConfig.visitFileUploadRoot+#list.smallImgUrl'/>"
													style="height: 80px; width:100px;" class="publicBorder"/> 
											</a>
										</td>
										<s:if test="#a.index==0">
											<td class="strong" align="center" rowspan="<s:property value="#map.value.size"/>" style=" width:60px;border-bottom: 2px #ccc dotted;">
												<a target="_blank" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=${list.shopInfoId}" class="strong ColorBlue publicunderline">${list.shopName}</a>
											</td>
										</s:if>
										<s:if test="#a.index==0">
											<td align="center" rowspan="<s:property value="#map.value.size"/>" style=" width:60px;border-bottom: 2px #ccc dotted;">
												<s:if test="#list.postage>0">
													<span  class="ColorRed">￥<fmt:formatNumber value="${list.postage}" pattern="#,##0.00#" /></span>
												</s:if>
												<s:else>
													<font>店铺包邮</font>
												</s:else>
											</td>
										</s:if>
										<s:if test="#list.flag==0">
										<td align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
											<span class="del">
												￥<fmt:formatNumber value="${list.marketPrice}" pattern="#,##0.00#" /> 
											</span>
											<span class="ColorRed">
												￥<fmt:formatNumber value="${list.salesPrice}" pattern="#,##0.00#" /> 
											</span>
										</td>
										<td id="xxxx" align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
											<s:if test="#list.discount!=null&&#list.discount>0">
												<input type="hidden" id="discount${list.productId}" value="${list.discount}"/>
												<span class="ColorRed"><fmt:formatNumber value="${list.discount}" pattern="#,##0.0#" />&nbsp;折</span>
											</s:if>
											<s:else>
											<span>无折扣</span>
											</s:else>
										</td>
										<%-- <td align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
											<span class="ColorRed">
												￥<fmt:formatNumber value="${list.buyPrice}" pattern="#,##0.00#" /> 
												 <s:if test="#list.buyPriceCustomer==1">
										    		<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productInfo/sp_price_icon_1.gif" class="special_price_icon" alt="会员价" title="会员价"/>
										    	</s:if>
											</span>
										</td> --%>
										<s:if test="#list.storeNumber>=#list.productNum">
											<td style="border-bottom: 2px #ccc dotted;">
												<span class="yjfhr">当日出货</span>
											</td>
										</s:if>
										<s:elseif test="#list.storeNumber<#list.productNum">
											<td style="border-bottom: 2px #ccc dotted;">
												<span id="stockUpDate" class="yjfhr2"><s:property value='#list.stockUpDate'/> 天</span>
											</td>
										</s:elseif>
										<td style="border-bottom: 2px #ccc dotted;">
											<s:property value="#list.productNum" />
										</td>
										<td style="border-bottom: 2px #ccc dotted;">
											<s:property value="#list.storeNumber" />
										</td>
										<%-- <td style="border-bottom: 2px #ccc dotted;">
											<input type="hidden" name="price"
												value="<s:property value='#list.subTotal' />" />
											￥
											<fmt:formatNumber value="${list.subTotal}"
												pattern="#,##0.00#" />
										</td> --%>
										<td style="border-bottom: 2px #ccc dotted;">
											<input type="hidden" name="subTotal"value="<s:property value='#list.salesPrice*#list.productNum' />" />
											￥<fmt:formatNumber value="${list.salesPrice*list.productNum}"	pattern="###,##0.00#" />
										</td>
										<s:if test="#list.discount>0">
											<td style="border-bottom: 2px #ccc dotted;">
											<input type="hidden" name="subTotal"value="<s:property value='#list.discount/10*#list.salesPrice*#list.productNum'/>" />
											<span  class="ColorRed">￥<fmt:formatNumber value="${list.discount/10*list.salesPrice*list.productNum}"	pattern="###,##0.00#" /></span>
											</td>
										</s:if>
										<s:else>
										<td style="border-bottom: 2px #ccc dotted;">
											<input type="hidden" name="subTotal"value="<s:property value='#list.salesPrice*#list.productNum' />" />
											<span  class="ColorRed">￥<fmt:formatNumber value="${list.salesPrice*list.productNum}"	pattern="###,##0.00#" /></span>
										</td>
										</s:else>
										</s:if>
										<s:else>
											<td colspan="4">
											此商品已下架或不存在，不做交易
											</td>
										</s:else>
									</tr>
								</s:iterator>
								<!-- <tr class="publicdashedline" style="border-bottom: 0;">
							  		<td colspan="9"></td>
							    </tr> -->
								</s:iterator>
							</table>
							<input type="hidden" name="orders.shopInfoId"
								value="${orders.shopInfoId}" />
							<input type="hidden" name="productId" value="${productId}" />
							<input type="hidden" name="productNum" value="${productNum}" />
							<%-- <div class="FontSize14 BackgroundE4Hui publicPadding5_10 publicHidden publicMarginB10">
								<p class="text-right">
									运费:
									<input type="hidden" name="orders.freight"
										value="${orders.freight}" />
									￥
									<span id="shipping"><fmt:formatNumber value="${orders.freight}" pattern="#,##0.00#" /> </span>
								</p>
								<p class="text-right">
									折扣:
									<input type="hidden" name="discount" value="${discount}" />
									￥
									<span id="discount"><fmt:formatNumber value="${discount}" pattern="#,##0.00#" /> </span>
								</p>
								<p class="text-right">
									合计(未含运费):￥
									<span class="strong ColorRed publicPaddingL7" id="subPrice">
										<fmt:formatNumber value="${orders.finalAmount}" pattern="#,##0.00#" />
									</span>
								</p>
								<p class="text-right">
									<input type="hidden" id="amount" name="orders.totalAmount" value="${orders.totalAmount}" />
									折扣前总价(未含运费):￥
									<span class="strong ColorRed publicPaddingL7" id="subPrice">
										<fmt:formatNumber value="${orders.totalAmount}" pattern="#,##0.00#" />
									</span>
								</p>
							</div> --%>
							<div class="FontSize14 BackgroundE4Hui publicPadding5_10 publicHidden publicMarginB10">
								<p class="text-right">
									运费:
									<input type="hidden" id="ordersFreight" name="orders.freight"
										value="${orders.freight}" />
									￥
									<span id="shipping"><fmt:formatNumber value="${orders.freight}" pattern="#,##0.00#" /> </span>
								</p>
								<p class="text-right">
									-&nbsp;折扣:
									<input type="hidden" name="discount" value="${discount}" />
									￥
									<span id="discount"><fmt:formatNumber value="${discount}" pattern="#,##0.00#" /> </span>
								</p>
								<p class="text-right">
									合计(含运费):￥
									<!-- 店铺折扣完以后的商品价格 -->
									<input type="hidden" id="amount" name="orders.totalAmount" value="${orders.finalAmount}" />
									<span class="strong ColorRed publicPaddingL7" id="subtotal"><fmt:formatNumber
											value="${orders.finalAmount}" pattern="###,##0.00#" /> </span>
								</p>
							</div>
							<!-- 优惠券抵扣begin -->
							<div id="plusCoupon" style="margin: 10px;" onclick="changeCoupon(this.id);">
								<div class="text-left" style="overflow:hidden;">
									<img style="display:block;float:left;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/plus.jpg" />
									<span style="color:#0199E4;font-size:15px;display:block;float:left; ">&nbsp;使用商城优惠券抵扣</span>
								</div>
							</div>
							<div id="minusCoupon" style="margin: 10px;display: none">
								<div class="text-left" style="overflow:hidden;"  onclick="changeCoupon(this.id);">
									<img style="display:block;float:left;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/minus.jpg" />
									<span style="color:#0199E4;font-size:15px;display:block;float:left; ">&nbsp;使用商城优惠券抵扣</span>
								</div>
								<div id="exchangeCoupon" class="text-left" style="margin-top:10px;" >
								<p>可以使用的优惠券</p>
									<select id="qlockedState" name="orders.discountCouponId">
						               <option id="qnull" value="0" onclick="okcoupon(0,0);" selected="selected">&nbsp;&nbsp;请选择&nbsp;&nbsp;</option>
						            <s:iterator value="ordersListMap"> 
										  <option value="<s:property value='customerDiscountCouponID'/>" onclick="okcoupon(<s:property value='discountCouponAmount'/>,<s:property value='discountCouponLowAmount'/>);"><s:property value="discountCouponName"/>&nbsp;&nbsp;抵扣金额:￥<s:property value="discountCouponAmount"/>&nbsp;&nbsp;开始时间:<s:property value="beginTime"/>&nbsp;&nbsp;结束时间:<s:property value="expirationTime"/></option>
									 </s:iterator>
									</select>
									<input type="hidden" id="mydiscountCouponAmount" name="orders.orderCouponAmount" value="0"/>
									<input type="hidden" id="isUseCoupon" name="orders.isUseCoupon" value=""/>
									<p><span id="errorMsgCoupon" style="color:#FE4F00;"></span></p>
								</div>
							</div>
							<!-- 优惠券抵扣end -->
							
							<div id="plus" style="margin: 10px;" onclick="changeImg(this.id);">
								<div class="text-left" style="overflow:hidden;">
									<img style="display:block;float:left;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/plus.jpg" />
									<span style="color:#0199E4;font-size:15px;display:block;float:left; ">&nbsp;使用金币抵扣</span>
								</div>
							</div>
							<div id="minus" style="margin: 10px;display: none">
								<div class="text-left" style="overflow:hidden;"  onclick="changeImg(this.id);">
									<img style="display:block;float:left;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/minus.jpg" />
									<span style="color:#0199E4;font-size:15px;display:block;float:left; ">&nbsp;使用金币抵扣</span>
								</div>
								<div id="exchange" class="text-left" style="margin-top:10px;" >
								<p >您有金币
									<span style="color:#d01743;">
										<s:if test="coinTotal>0">
											<fmt:formatNumber value="${coinTotal}" pattern="#" />
										</s:if>
										<s:else>
										0</s:else>
									</span>个
										（使用上限<span id="canUserCoin" style="color:#d01743;"></span>个）</p>
										<input id="canchangeCoin" type="hidden"/>
									<p>
										 本次使用 <input id="userCoin" type="text" style="width: 100px;" name="orders.userCoin" onblur="changeCoin(this.value);" value="0"/>
										<input id="changeAmount" type="hidden" name="orders.changeAmount" value="0"/>个金币支付，
										 折合人民币：<span id="show_changeAmount" style="color:#d01743;">¥0.00</span>元
									</p>
									<p><span id="errorMsg" style="color:#d01743;"></span></p>
								</div>
							</div>
								
								
							<div class="height2 publicHidden" style="padding-left:4px;">
								<p class="float-left ColorRed strong FontSize14">
									政策和规则：<span id="exchangeIegulation"></span>
								</p>
							</div>

							<p class="policy_text publicMarginTB10 publicPadding5">
								需要三到五个工作日,快件才会发到您所在的地址.
							</p>
							<div class="publicHidden">
								<div
									class="Backgroundpink Paypal1 float-right radius publicPadding10" style="width:1190px;">
									<!-- 优惠券抵扣begin -->
									<p id="p_total_Coupon" style="display: none" class="text-right strong">
										-&nbsp;优惠券抵扣金额:
										<span class="ColorRed publicPaddingL7 FontSize16" id="total_Coupon">
										</span>
										<input type="hidden" id="okcoupon" value=""/><!-- 优惠券 -->
										<input type="hidden" id="coinTotalsss" value=""/><!-- 积分 -->
										<input type="hidden" id="tempMoney" value=""/><!-- 税费 -->
										<input type="hidden" id="isnoFreight" value=""/><!-- 运费 -->
									</p>
									<!-- 优惠券抵扣end -->
									<p id="p_total_coin" style="display: none" class="text-right strong">
										-&nbsp;金币抵扣金额:
										<span class="ColorRed publicPaddingL7 FontSize16" id="total_coin">
										</span>
									</p>
									<p id="p_total_coin" class="text-right strong">
										应付总额(含运费):
										<input type="hidden" id="totalAmount"  value="${orders.finalAmount}" />
										<input type="hidden" name="orders.finalAmount" id="finalAmount" value="${orders.finalAmount}" />
										<input type="hidden" id="finalAmount-des" value="${orders.finalAmount}" />
										<span class="ColorRed publicPaddingL7 FontSize16">￥<span class="ColorRed publicPaddingL7 FontSize16"  id="subPrice"><fmt:formatNumber value="${orders.finalAmount}" pattern="###,##0.00#" /></span> 
										</span>
									</p>
									<p class="text-right">
										<span class="strong">收货地址:</span><span id="ShippingTo">${custAddress.address}&nbsp;&nbsp;
										</span>
									</p>
									<p class="text-right">
										<span class="strong">收货人:</span>
										<span id="name">
										${custAddress.consignee}&nbsp;${custAddress.lastName}
										</span>
									</p>
									<p class="text-right">
										<span class="strong">联系方式:</span>
										<span id="accpeEmail">
										${custAddress.mobilePhone}
										</span>
									</p>
									
								</div>
							</div>
								<input id="shopCartIds" type="hidden" name="shopCartIds" value="${shopCartIds}" />
								<a id="yOrder" style="display:;width:80px;text-align:center;height:30px;line-height:30px;background-color:#d01743 !important " onclick="form1.submit()" href="javascript:;" 
									class="submitImg ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong ">提交订单</a>
								<!-- <a id="yOrder" style="display:;width:80px;text-align:center;height:30px;line-height:30px;background-color:#d01743 !important " href="javascript:toSuccess();" 
									class="submitImg ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong ">提交订单</a> -->
								<div id="nOrder" style="display:none;">
									<a href="javascript:;" onclick="goOnToOrder();"
										class="submitImg ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong ">继续提交</a>
									<a 
										class="submitImg ColorWhite1  publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong "></a>
									<a href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action" 
										class="submitImg ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong ">返回购物车</a>
									<p class="submitImgLoad float-right publicMarginTB20 strong " style="display:none;margin:25px 10px 0 0;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>	
									<div>
									</div>
								</div>
							</div>
						<!--//mainClass-->
					</div>
				</form>
			</div>
		</div>
	  </div>	
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
