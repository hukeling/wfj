<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<title>购物车</title>
<meta name="viewport"
	content="initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%-- <script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script> --%>
<script type="text/javascript"
	src="http://192.168.1.145:8080/wfj_front/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.tools.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollUp.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/installapp.js@v=20140319100001" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/base2013.css"
	charset="utf-8" />
<link rel="stylesheet" type="text/css" href="css/index.css"
	charset="utf-8" />
<link rel="apple-touch-icon-precomposed"
	href="http://www.17sucai.com/preview/1/2014-12-15/m.jd.com/images/apple-touch-icon.png" />
<!-- 新版轮播图js开始 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tt.index_V1.4.min-t.js"></script>
<!-- 新版轮播图js结束 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tab.js"></script>
<!-- 轮播图下方的滚动图片 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/scroll.1.3.js"></script>
<link rel="stylesheet" href="css/mui.min.css">
<style type="text/css">
.title {
	font-size: 28px;
	color: #333;
	text-align: center;
	background-color: #ffffff;
	height: 40px;
	line-height: 40px;
}

.item {
	float: left;
	padding-top: 4%;
	padding-bottom: 4%;
	text-align: center;
}

.ss {
	height: 40px;
	line-height: 40px;
	margin-left: 12px;
}

.cc {
	display: -webkit-box;
	border-bottom: 1px solid #ccc;
	-webkit-box-align: center;
}

.dd {
	margin-left: 12px;
	margin-right: 12px;
	text-align: center;
	float: left;
}

div .box {
	border: 0px dashed gray;
	margin-bottom: 0px
}

.ee {
	k margin-top: 12px;
}

.image-cache {
	max-height: 120px;
	max-width: 120px;
	border-radius: 8px;
	border: 1px solid #EEEEEE;
}
</style>
<script type="text/javascript">
	window.onload = function() {
		amountMoneyCart();
		$("#subBtn").removeAttr("disabled");
	};
	//统计金额和数量
	function amountMoneyCart() {
		var amountItme = document.getElementsByName("amountItme_cart");
		var Subtotal_cart = 0;//销售总价格
		var total = "";//小计
		for ( var i = 0; i < amountItme.length; i++) {
			var priceId = amountItme[i].id;
			var array = new Array();
			array = priceId.split("_");
			var productId = array[1];//商品id
			if (document.getElementById("checkbox_" + productId).checked) {
				var price = document.getElementById("singlePrices" + productId).value;
				Subtotal_cart = Subtotal_cart + Number(price);
			}
		}
		document.getElementById("Subtotal_cart").innerHTML = "￥: "
				+ Subtotal_cart;
	}
	
	 //商品数量的加减
	function  changeNum(customerId,productId,num){
		//window.location.href="${pageContext.request.contextPath}/phone/changeNum.action?productId="+productId+"&customerId="+customerId+"&num="+num;
		var url="${pageContext.request.contextPath}/phone/changeNum.action";
		$.post(url,
				{
					"productId":productId,
					"customerId":customerId,
					"num":num
				},function(data){
					//$("#amount_"+productId+"_cart").value
					location.reload(false);
		  },'json');
	}
	 
	function changeSelect(){
		amountMoneyCart();
	}
	
	 function addToOrder(customerId){
	    	 var jsonCart="[";//json格式记录购物车id，数量，此商品的总价
	    	 var productIds="";//记录商品ids
	    	 $(".checkboxClass").each(function(){
				if(this.checked){//如果选择商品
					var productId = this.value;
					var count= document.getElementById("amount_"+productId+"_cart").value;//当前商品数量
					var salesPrice= $("#costPrice"+productId).val();//单价
					var totalPrice=$("#singlePrices"+productId).val();//s个商品总价
					var yjchr = $("#stockUpDate_hidden_"+productId).val();//预计出货日
		    		jsonCart+="{'productId':'"+productId+"','count':'"+count+"','totalPrice':'"+totalPrice+"','stockUpDate':'"+yjchr+"'},";
		    		if(""==productIds){
		    			productIds=productId;
		    		}else{
		    			productIds=productIds+","+productId;
		    		}
				}
			 });
	    	 if(jsonCart!="["){
	    		 jsonCart=jsonCart.substring(0,jsonCart.lastIndexOf(","));
	    		 jsonCart+="]";
	    	 }else{
	    		 jsonCart="";
	    	 }
	    	 if(productIds==""){
	    		 $("#submitImgLoad").html("请选择商品！");
	    	 }else{
		    	 $(".submitImgLoad").attr("style","display:block;float: right;margin-right: 20px;");
	    	 	 //window.location="${pageContext.request.contextPath}/front/customer/shoppingOnline/toCheckOut.action?jsonCart="+jsonCart+"&productIds="+productIds+"&discount="+discount;
		    	 window.location.href="${pageContext.request.contextPath}/phone/toCheckOut.action?customerId="+customerId+"&jsonCart="+jsonCart+"&productIds="+productIds;
	    	 }
     }
	
	 function del(customerId,productId){
		 window.location.href="${pageContext.request.contextPath}/phone/delCart.action?customerId="+customerId+"&productId="+productId;
	 }
</script>
</head>

<body>
	<div class="mui-content">
		<div>
			<s:if test="%{#request.list.size==0}">
				<div align="center"> <img name="image1"
					style="margin-top: 20%;"
					src="https://192.168.1.145:443/wfj_front/cart_no_data_new_icon.png" /></div>
				<br />
				<h1 style="color: gray;" align="center">购物车内没有商品</h1>
			</s:if>
			<s:else>
				<s:iterator value="%{#request.list}" var="cart">
					<ul class="mui-table-view">
						<li id="cc" class="mui-table-view-cell mui-media cc"><input
							id="freightPrice_${productInfo.productId}" type="hidden"
							value="${productInfo.freightPrice}" /> <input
							class="checkboxClass" name="checkbox"
							value="${productInfo.productId}" type="checkbox"
							checked="checked" id="checkbox_${productInfo.productId}"
							value="${productInfo.productId}" onclick="changeSelect()">
							<input type="hidden" id="customerId${customerId}"
							value="${customerId}"> &nbsp;&nbsp;&nbsp;&nbsp; <a
							style="margin-left: 70%;"
							onclick="del(${customerId},${productInfo.productId})"><input
								type="button" style="border: thin;" value="删除"></a>
							<div>
								<a
									href="${pageContext.request.contextPath}/phoneProductInfo/<s:property value="productInfo.productId" />.html?customerId=${customerId}"><img
									class="image-cache dd"
									src="${fileUrlConfig.visitFileUploadRoot}<s:property value="productInfo.logoImg"/>" /></a>
								<h4>
									<a
										href="${pageContext.request.contextPath}/phoneProductInfo/<s:property value="productInfo.productId" />.html?customerId=${customerId}"><s:property
											value="productInfo.productName" /></a> <input type="hidden"
										id="stockUpDate_hidden_${productInfo.productId}"
										value="${productInfo.stockUpDate}">
								</h4>
								<p>
									￥ <input type="hidden" id="costPrice${productInfo.productId}"
										value="${productInfo.salesPrice}" />
									<s:property value="productInfo.salesPrice" />
									<input type="hidden" id="singlePrices${productInfo.productId}"
										value="<s:property value="productInfo.salesPrice*shoppingCart.quantity" />">
								</p>
								<div class="mui-numbox ee">
									<!-- "-"按钮，点击可减小当前数值 -->
									<s:if test="%{shoppingCart.quantity!=1}">
										<button type="button"
											onclick="changeNum(${customerId},${productInfo.productId},-1)">-</button>
									</s:if>
									<s:else>
										<button type="button" disabled="disabled">-</button>
									</s:else>
									<input style="width: 100px" name="amountItme_cart"
										id="amount_${productInfo.productId}_cart"
										value="${shoppingCart.quantity}" disabled="disabled" />
									<!-- "+"按钮，点击可增大当前数值 -->
									<button type="button"
										onclick="javascript:changeNum(${customerId },${productInfo.productId},1)">+</button>

								</div>
							</div></li>
					</ul>
				</s:iterator>
			</s:else>

		</div>
		<br /> <br /> <br /> <br />
		<%-- <div class="mui-content"
			style="width: 100%; height: 8%; background-color: #000000; color: #FFFFFF; position: fixed; bottom: 0; left: 0;">
			合计: <span class="strong ColorRed publicPaddingL7" id="Subtotal_cart"></span>
			<input type="button" id="subBtn" value="去结算"
				onclick="javascript:addToOrder(${customerId});" />
			<p id="submitImgLoad" class="submitImgLoad  strong"
				style="display: none; float: right; margin-right: 20px; width: 200px; font-size: 12px; font-weight: normal;">
				<img
					src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif" />&nbsp;&nbsp;正在提交请稍等...
			</p>
		</div>  --%>
		<s:if test="%{#request.list.size>0}">
			<div class="box"
				style="width: 100%; height: 8%; background-color: #000000; color: #FFFFFF; position: fixed; bottom: 0; left: 0;">
				<div class="item"
					style="width: 65%; background-color: #000000; color: #FFFFFF">
					合计:<a class="strong ColorRed publicPaddingL7" id="Subtotal_cart" />
				</div>
				<a onclick="javascript:addToOrder(${customerId});" href="#">
					<div class="item"
						style="width: 35%; background-color: #E30000; color: #FFFFFF">
						提交订单
						<p id="submitImgLoad" class="submitImgLoad  strong"
							style="display: none; float: right; margin-right: 20px; width: 200px; font-size: 12px; font-weight: normal;">
							<img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif" />&nbsp;&nbsp;正在提交请稍等...
						</p>
					</div>
				</a>
			</div>
		</s:if>
	</div>
</body>


<script src="../js/mui.min.js"></script>
<script>
	mui.init({
		swipeBack : true
	//启用右滑关闭功能
	});
	mui('.mui-input-group').on('change', 'input', function() {
		var value = this.checked ? "true" : "false";
		this.previousElementSibling.innerText = "checked：" + value;
	});
</script>

</html>