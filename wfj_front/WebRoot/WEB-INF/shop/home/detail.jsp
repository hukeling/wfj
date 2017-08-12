<%@page import="phone.pojo.ProductInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:if
		test="productInfo.seoTitle!=null&&productInfo.seoTitle!=''">${productInfo.seoTitle}</s:if>
	<s:else>${productInfo.productFullName}</s:else></title>
<meta name="viewport"
	content="initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css" href="../css/mui.min.css" />
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery-1.8.0.min.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.jcarousel.js"
	type="text/javascript"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.jqueryzoom.js"
	type="text/javascript"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}front/js/tab.js"
	type="text/javascript"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/jskoala.min.1.5.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-ui-1.10.3.custom.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>

<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<style type="text/css">
body {
	background-color: #EEEEEE;
	font-size: 16px;
	font-family: Arial, Helvetica, sans-serif, "黑体";
	padding: 0px;
	margin: 0px;
}

.money {
	color: #FF0000;
}

.item {
	float: left;
	margin: 0px;
	padding-top: 4%;
	padding-bottom: 4%;
	text-align: center;
}

.leftItem {
	font-size: 16px;
	color: #666666
}

.item a {
	color: #ffffff
}
</style>

</head>
<script type="text/javascript">
	/* $(function(){
		window.onload="${pageContext.request.contextPath}/front/customer/productsdetail/evaluateProductList.action?productId=<s:property value='productInfo.productId'/>";
	});
 */
	//加入收藏
	function saveCustomerCollectProduct() {
		var customerType = '${customer.type}';
		var customerId = '${customer.customerId}';
		if (customerId != "") {
			if (customerType != "2") {
				window.location.href = "${pageContext.request.contextPath}/front/customer/productsdetail/saveCustomerCollectProduct.action?productId="
						+ $("#productId").val();
			} else {
				lalert("提醒", "供应商无法收藏商品!");
			}
		} else {
			lalert("提醒", "请登录后在操作!");
		}
	}
	//初始化加载商品的属性和参数
	$(function() {
		$("#innow").removeAttr("disabled");
		$("#tocart").removeAttr("disabled");
		var html = '';
		//分类的属性start
		var productAttributeValue = $("#productAttr").val();
		if (productAttributeValue != "" && productAttributeValue.length > 0) {
			productAttributeValue = eval(productAttributeValue);
			var attrHtml = "";
			for ( var k = 0; k < productAttributeValue.length; k++) {
				attrHtml += "<li><span class='nameYin'>"
						+ productAttributeValue[k].name + " ： </span><span>"
						+ productAttributeValue[k].attrValueName
						+ "</span></li>";
			}
			$("#productAttrShow").append(attrHtml);
		}
		//分类的属性end 
		//分类的参数start
		var productPara = $("#productPara").val();
		if (productPara != "" && productPara != "[]") {
			var htmlParaStr = '';
			var infoObj = eval(productPara);
			var trHtml = '';
			var h = 0;
			var trHtml = '<table  align="center"  class="addOrEditTable" width="900px;">';
			for ( var i = 0; i < infoObj.length; i++) {
				trHtml += '<table id="parameterTable'
						+ i
						+ '" class="parameterTable"  frame="below" width="100%"> ';
				trHtml += '<tr  class="parameterTr" >'
						+ '<div class="paraTitle">' + infoObj[i].name
						+ '</div> ';
				trHtml += ' <\/td><\/tr>';
				trHtml += '<tr > <table id="parameterInfoTable'
						+ i
						+ '" class="parameterInfoTable" frame="below" width="100%">';
				var a = eval(infoObj[i].paramGroupInfo);
				for ( var u = 0; u < a.length; u++) {
					trHtml += '<tr class="parameterInfoTr" >';
					trHtml += ' <td class="paraTh" style="text-align: right;width: 150px; background:#fff;  vertical-align:middle;"> '
							+ a[u].name + '<\/td> ';
					trHtml += ' <td class="paraTd" style="vertical-align:middle;">'
							+ a[u].value + ' <\/td>';
					trHtml += '<\/tr>';
					h++;
				}
				trHtml += '<\/table><\/tr>';
				trHtml += '<\/table>';
			}
			trHtml += '<\/table>';
			$("#totalProdPara").html(trHtml);

		}
		//分类的参数end
	});

	//iframe的高度自适应
	function autoHeight() {
		var ifm = document.getElementById("iframe_body");
		var subWeb = document.frames ? document.frames["iframe_body"].document
				: ifm.contentDocument;
		if (ifm != null && subWeb != null) {
			var h = parseInt(subWeb.body.scrollHeight);
			ifm.height = subWeb.body.scrollHeight + 10;
			if (ifm.contentDocument && ifm.contentDocument.body.offsetHeight) { // ff,chrome等
				h = parseInt(ifm.contentDocument.body.offsetHeight);
				ifm.height = h + 10;
			}
		}
	}
	var frontTableTitleId = "spjs";
	//商品详细信息的table切换
	function changeContent(paramsId) {
		if (frontTableTitleId != paramsId) {
			$("#" + frontTableTitleId).removeAttr("style");//去除上一个的头部背景色,去除样式
			$("#" + paramsId).css({
				"background" : "#d01743",
				"color" : "#fff",
				"text-align" : "center"
			});//添加当前的头部背景色
			if (paramsId != frontTableTitleId) {
				$("#yinProduct_1").removeAttr("class");
				$("#yinProduct_2").removeAttr("class");
				$("#yinProduct_3").removeAttr("class");
				$("#yinProduct_4").removeAttr("class");
				if (paramsId == "spjs") {
					$("#yinProduct_1").attr("class", "tuweizi");
					$("#yinProduct_2").attr("class", "tuweizi hide");
					$("#yinProduct_3").attr("class", "tuweizi hide");
					$("#yinProduct_4").attr("class", "tuweizi hide");
				} else if (paramsId == "spcs") {
					$("#yinProduct_1").attr("class", "tuweizi hide");
					$("#yinProduct_2").attr("class", "tuweizi");
					$("#yinProduct_3").attr("class", "tuweizi hide");
					$("#yinProduct_4").attr("class", "tuweizi hide");
				} else if (paramsId == "sppj") {
					$("#yinProduct_1").attr("class", "tuweizi hide");
					$("#yinProduct_2").attr("class", "tuweizi hide");
					$("#yinProduct_3").attr("class", "tuweizi");
					$("#yinProduct_4").attr("class", "tuweizi hide");
				} else {
					$("#yinProduct_1").attr("class", "tuweizi hide");
					$("#yinProduct_2").attr("class", "tuweizi hide");
					$("#yinProduct_3").attr("class", "tuweizi hide");
					$("#yinProduct_4").attr("class", "tuweizi");
				}
			}
			frontTableTitleId = paramsId;//记录当前的ID
		}

	}

	$(function() {
		$('input[name="qty"]').val("");
	});

	//清空购买数量值
	function clearnBuyNum(obj) {
		var $td = $(obj).parent();//获取父节点td
		var $buyName = $td.find('input[name="qty"]');//获取当前商品购买数量元素
		//清空购买数量
		$buyName.val("");
		checkStockUpDate($buyName);
	}

	//当购买数量获得焦点时触发该函数
	function getDefValue(obj) {
		if ($(obj).val() == "") {//如果该对象的value为空时 执行下面操作
			$(obj).val("1");
			$(obj).select();//选中文本
		}
	};

	$(document).ready(function() {
		$('#isOk-alert').overlay({
			mask : {
				color : '#ebecff',
				loadSpeed : 3000,
				opacity : 0.4
			},
			closeOnEsc : false,
			closeOnClick : false
		});
	});

	
	/* <a href="#" onclick="addToCollect(<s:property
		value="productInfo.productId" />)">添加收藏</a><a href="#" onclick="addToCart(<s:property
				value="productInfo.productId" />)">加入购物车</a> */
				
	function addToCollect(productId,flag){
		var customerId = "${sessionScope.customerId}";
		if(customerId!=null&&customerId!=""){
			/* window.location.href="${pageContext.request.contextPath}/phone/addProToCollect.action?productId="+productId;
			var collect = "${sessionScope.collect}";
			alert(collect); */
			var url="${pageContext.request.contextPath}/phone/addProToCollect.action";
			$
			.post(
					url,
					{
						"productId" : productId
					},
					function(data) {
						if(flag){
							alert("取消关注成功");
						}else{
							alert("添加关注成功");
						}
						location.reload(true);
						
					},'json'); 
			
		}else{
			alert("用户未登录");
		}
	}
				
	function addToCart(productId){
		var customerId = "${sessionScope.customerId}";
		if(customerId==null||customerId==""){
			alert("用户未登录");
		}else{
			//window.location.href="${pageContext.request.contextPath}/phone/addProToCart.action?productId="+productId;
			var url="${pageContext.request.contextPath}/phone/addProToCart.action";
			$
			.post(
					url,
					{
						"productId" : productId
					},
					function(data) {
						alert("添加成功");
					},'json');
			
		}
	}
	
	function gotoCart(){
		var customerId = "${sessionScope.customerId}";
		if(customerId==null||customerId==""){
			alert("用户未登录");
		}else{
			window.location.href="${pageContext.request.contextPath}/phone/gotoPhoneCart.action?customerId="+customerId;
		}
	}
	
	//加入购物车
	function putProToShopCart(productId) {
		var num = $("#amount").val();//数量
		var storeNumber = $("#storeNumber").val();//库存
		var productName = $("#productName").val();//商品名称
		var salesPrice = $("#salesPrice").val();//售价
		var logoImage = $("#logoImage").val();//商品图片
		if (productId != null) {
			var customer = "<s:property value='#session.customer.loginName' />";
			if (customer != null && customer != "") {//用户已登录
				$("#submitCart").attr("onclick", "false");
				location.href = "${pageContext.request.contextPath}/shopFront/shoppingCar/addProductToShoppCart.action?productId="
						+ productId
						+ "&num="
						+ num
						+ "&shopInfoId=${productInfo.shopInfoId}&stockUpDate=${productInfo.stockUpDate}&sku=${productInfo.sku}"
						+ "&flag=" + productId;
			} else {//用户未登录
				$("#submitCart").attr("onclick", "false");
				var cookieValue = $.cookie("customerCar");
				var value = "";
				var shopInfoId = "${productInfo.shopInfoId}";
				var sku = "${productInfo.sku}";
				var stockUpDate = "${productInfo.stockUpDate}";
				if (cookieValue == null) {
					value = "[{'productId':'" + productId + "','num':'" + num
							+ "','shopInfoId':'" + shopInfoId
							+ "','stockUpDate':'" + stockUpDate + "','sku':'"
							+ sku + "'}]";//以json格式存放，方便维护和取
				} else {
					var oldCookieValue = eval(cookieValue);//格式化之前cookie中的数据，以便与当前加入的数据做对比
					var flag = true;//之前cookie中的数据与现在加入的数据是否一样标记。默认true表示当前商品不在cookie中
					for ( var i = 0; i < oldCookieValue.length; i++) {//迭代之前cookie
						var oldNum = oldCookieValue[i].num;//当前cookie拿到的商品数量
						if (productId == oldCookieValue[i].productId) {//判断当前操作的商品是否在之前的cookie中
							oldNum = Number(oldNum) + Number(num);//如果存在数量累加
							flag = false;//设置标记
						}
						//重新组装之前cookie数据
						value += ",{'productId':'"
								+ oldCookieValue[i].productId + "','num':'"
								+ oldNum + "','shopInfoId':'"
								+ oldCookieValue[i].shopInfoId
								+ "','stockUpDate':'"
								+ oldCookieValue[i].stockUpDate + "','sku':'"
								+ oldCookieValue[i].sku + "'}";//以json格式存放，方便维护和取
					}
					//去掉value前面的“,”
					value = value.substring(1, value.length);
					if (flag == true) {//true表示当前商品不在之前的cookie中。将当前商品放入cookie中
						value = "[" + value + ",{'productId':'" + productId
								+ "','num':'" + num + "','shopInfoId':'"
								+ shopInfoId + "','stockUpDate':'"
								+ stockUpDate + "','sku':'" + sku + "'}]";//以json格式存放，方便维护和取
					} else {//else为false，表示当前商品在之前的cookie中，上面已经将当前的数量累加进去，不需要再次添加
						value = "[" + value + "]";
					}
				}
				$.cookie('customerCar', value, {
					expires : 7,
					path : "/"
				}); //设置带时间的cookie 7天，加path属性时，只能其他页面使用，且其他页面取值条件为当前页面的上级路径包含path一致
				//console.log("商品详情3："+$.cookie('customerCar'));
				location.href = "${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action?flag="
						+ productId;
			}
		}
	}
	//初始化商品规格-选中样式
	$(function() {
		var productName = $("#productName").val();//商品名称
		var productFullName = $("#productFullName").val();//商品全名称
		var fullName = productFullName.substring(productName.length + 2,
				productFullName.length - 1);//规格
		fullNameArray = fullName.split(",");
		for ( var i = 0; i < fullNameArray.length; i++) {
			$(".yinDefault").each(function() {
				if ($(this).attr("ggvname") == fullNameArray[i]) {
					$(this).addClass("yinBor");//给当前商品加上样式
					var flagggv = $(this).find("i").attr("flag");//i元素为右下角的对勾  
					if (flagggv == "1") {//如果是1 说明是文本规格  所以添加yinBor_i2样式   【文本与图片规格的高度不一致 所以要单独添加】
						$(this).find("i").addClass("yinBor_i2");
					} else {//图片规格
						$(this).find("i").addClass("yinBor_i");
					}
					//给当前选中的样式添加一个标识符号，用于 鼠标滑过js 判断当前所选的规格
					$(this).attr("yinbor", "true");
				}
			});
		}
		$("#innow").removeAttr("disabled");
		$("#tocart").removeAttr("disabled");
	});
	//选择规格时操作
	$(function() {
		//鼠标滑过检测商品是否存在
		$(".yinDefault")
				.bind(
						"mouseover",
						function() {
							//从后台传输的规格组拼数据 例如@红,S@黑,M@
							var hasProductGGG = "${hasProductGGG}";
							var params = "";//获取选择的规格值(多个)
							var name = $("#" + this.id).attr("name");//获取当前的规格name
							//删除鼠标滑过的规格值yinbor属性（是否被选中） 以及style样式（是否存在该组合商品）
							$("span[yinbor='true']").each(function() {
								if (name == $("#" + this.id).attr("name")) {//定位到当前鼠标滑过的规格值组
									$("#" + this.id).removeAttr("yinbor");
									$("#" + this.id).removeAttr("style");
								}
							});
							//给当前滑过的规格值加上选中的属性标记yinbor
							$(this).attr("yinbor", "true");
							//计算鼠标滑过后的  规格值组拼数据  例如：红,S
							$("span[yinbor='true']")
									.each(
											function(i) {//循环选中的规格值
												//获取选中的规格值  名称
												var var1 = $(this).attr(
														"ggvname");
												//在这判断一下  如果当前循环的规格值对象  不在鼠标滑过的规格值组内  则要根据yinBor样式来判断选中状态（而非yinbor属性标记）
												if (name != $("#" + this.id)
														.attr("name")) {
													var thisName = $(this)
															.attr("name");
													var1 = $(
															".yinBor[name='"
																	+ thisName
																	+ "']")
															.attr("ggvname");
												}
												//组拼规格值数据
												if (params == "") {
													params = var1;
												} else {
													params = params + ","
															+ var1;
												}
											});
							//如果当前的规格值数据   在  后台传输的hasProductGGG中可以找到  说明商品是存在的
							if (hasProductGGG.indexOf("@" + params + "@") >= 0) {
								$("#" + this.id).removeAttr("style");//商品存在
							} else {
								$("#" + this.id).attr("style",
										"cursor: not-allowed;");//商品不存在 把鼠标状态改为禁止
							}
						});

		$(".yinDefault")
				.bind(
						"click",
						function() {
							//触发单击的条件参数
							var canClick = $("#" + this.id).attr("style");
							if (typeof (canClick) == "undefined") {
								var goods = $("#goods").val();//当前商品所属商品组
								var params = "";//获取选择的规格值(多个)
								var name = $("#" + this.id).attr("name");//获取当前的规格name
								//修改相关样式
								//声明一个临时变量 存储 无相应规格商品的id 用以恢复样式
								var tmpOldGGid = "";
								//记录当前操作规格值的id
								var tmpObjid = "";
								$(".yinBor")
										.each(
												function() {//获取第二个class为yinBor的规格值名称
													if (name == $("#" + this.id)
															.attr("name")) {
														$("#" + this.id)
																.removeClass(
																		"yinBor");//清空当前已选择的规格中所有的规格值
														var flagggv = $(
																"#" + this.id)
																.find("i")
																.attr("flag");
														if (flagggv == "1") {
															$("#" + this.id)
																	.find("i")
																	.removeClass(
																			"yinBor_i2");
														} else {
															$("#" + this.id)
																	.find("i")
																	.removeClass(
																			"yinBor_i");
														}
														tmpOldGGid = this.id;//  存储上一个ID
													}
												});
								$(this).addClass("yinBor");//选择当前选中的规格值
								var flagggvThis = $(this).find("i")
										.attr("flag");
								if (flagggvThis == "1") {
									$(this).find("i").addClass("yinBor_i2");
								} else {
									$(this).find("i").addClass("yinBor_i");
								}
								tmpObjid = $(this).attr("id");
								$(".yinBor").each(function() {//获取第二个class为yinBor的规格值名称
									var var1 = $(this).attr("ggvname");//找到当前的class id
									if (params == "") {
										params = var1;
									} else {
										params = params + "," + var1;
									}
								});
								//异步查询选择的规格的商品
								if (params != "") {
									$
											.post(
													"${pageContext.request.contextPath}/productsdetail/selectSpecification.action",
													{
														"params" : escape(params),
														"goods" : goods
													},
													function(data) {
														if (data != null) {//商品存在
															var pType = $(
																	"#pType")
																	.val();
															window.location = "${pageContext.request.contextPath}/productInfo/"
																	+ data.productId
																	+ ".html";
														} else {//没有选择的规格商品时，提醒没有此规格，选择之前的规格
															//无此商品 恢复之前所选样式
															$("#" + tmpObjid)
																	.removeClass(
																			"yinBor");
															$("#" + tmpOldGGid)
																	.addClass(
																			"yinBor");
															var flagggv = $(
																	"#"
																			+ this.id)
																	.find("i")
																	.attr(
																			"flag");
															if (flagggv == "1") {
																$(
																		"#"
																				+ tmpObjid)
																		.find(
																				"i")
																		.removeClass(
																				"yinBor_i2");
																$(
																		"#"
																				+ tmpOldGGid)
																		.find(
																				"i")
																		.addClass(
																				"yinBor_i2");
															} else {
																$(
																		"#"
																				+ tmpObjid)
																		.find(
																				"i")
																		.removeClass(
																				"yinBor_i");
																$(
																		"#"
																				+ tmpOldGGid)
																		.find(
																				"i")
																		.addClass(
																				"yinBor_i");
															}
															lalert("提醒",
																	"无此规格商品！");
														}
													}, 'json');
								}
							}

						});
	});
	//商品数量的加减，num为1是减2为加
	function addNum(num) {
		var amount = $("#amount").val();//当前数量
		var storeNumber = $("#storeNumber").val();//库存
		var a = /^[1-9]*[1-9][0-9]*$/;//非负数
		if (!a.test(amount)) {
			$("#showMsg").html("只能输入正整数！");
			$("#amount").val(1);
			amount = 1;
		} else if (Number(amount) > Number(storeNumber)) {
			$("#amount").val(storeNumber);
			amount = Number(storeNumber);
			$("#showMsg").html("");
		}
		if (num == "-1") {//减少商品数量
			if (amount > 1) {
				amount = Number(amount) - Number(1);
				$("#amount").val(amount);
			} else {
				amount = 1;
			}
		} else if (num == "-2") {//增加商品数量
			if (Number(amount) >= Number(storeNumber)) {
				$("#showMsg").html("库存不足！");
				amount = Number(amount);
			} else {
				amount = Number(amount) + Number(1);
				$("#showMsg").html("");
			}
		}
		$("#amount").val(amount);
	}
	//初始化品牌商品横向滚动条
	$(function() {
		var o = $("#itemSeriesCont");//得到要操作的对象o
		var pxInfo = $(".xiaoshou").outerWidth();//得到o下的每一个img元素的外宽pxInfo
		var productInfoListByBrandTypeHid = $("#productInfoListByBrandType-hid")
				.val();
		if (productInfoListByBrandTypeHid != "0") {
			var num = Number(pxInfo) * Number(productInfoListByBrandTypeHid);
			if (eval(num) > eval(1210)) {
				o.css("overflow-x", "scroll");
			}
		}
	});
</script>

<body>

	<div>产品详情</div>
	<!-- 商品详情 -->
	<div>
		<ul class="mui-table-view  mui-media mui-col-xs-12">
			<li class="mui-table-view-cell mui-text-center"><img width="200"
				height="200"
				src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>
			<s:property value='%{productImgList[0].medium}'/>" />
			</li>
			<li class="mui-table-view-cell">
				<h4>
					<s:property value="#request.productInfo.productName" />
				</h4>
			</li>
			<li class="mui-table-view-cell"><span class="leftItem">价格:</span>
				<span class="money">￥<s:property
						value="productInfo.salesPrice" /></span></li>
			<li class="mui-table-view-cell"><span class="leftItem">运费:</span>
				<span class="money">￥<s:property
						value="productInfo.freightPrice" /></span></li>
			<li class="mui-table-view-cell"><span class="leftItem">品牌:&nbsp;</span>
				<s:property value="brand.brandName" /></li>
			<li class="mui-table-view-cell"><span class="leftItem">已选:&nbsp;</span>
				1.8米床1件</li>
			<li class="mui-table-view-cell"><span class="leftItem">发货地点：</span>
				<s:property value="deliveryAddress" /></li>

			<s:if test="%{productInfo.describle!=null}">
				<li class="mui-table-view-cell">
					<!-- 京东类型的商品规格展示 begin --> <input type="hidden" id="map"
					value="<s:property value='map.size'/>" /> <s:iterator value="map"
						id="m" status="index">
						<s:if test="#m.key.type==2">
							<div class="textP ClearBoth hyds">
								<p
									style="padding-bottom: 5px; float: left; display: block; font-size: 16px;">
									<span
										id="index_specification<s:property value='#m.key.specificationId'/>"
										flag="<s:property value='#index.index+1'/>"> <s:property
											value="#m.key.name" />：
									</span> ${productInfo.describle}
								</p>
							</div>
						</s:if>
						<s:else>
							<div class="textP ClearBoth hyds">
								<p
									style="padding-bottom: 5px; float: left; display: block; font-size: 16px;">
									<span
										id="index_specification<s:property value='#m.key.specificationId'/>"
										flag="<s:property value='#index.index+1'/>"> <s:property
											value="#m.key.name" />：
									</span> ${productInfo.describle}
								</p>
							</div>
						</s:else>
					</s:iterator>
				</li>
			</s:if>
			<li class="mui-table-view-cell"><span class="leftItem">提示:&nbsp;</span>
				支持7天无理由退换货</li>
		</ul>
	</div>

	<!-- 评价 -->
	<div class="mui-content">
		<ul class="mui-table-view  mui-media mui-col-xs-12">
			<li class="mui-table-view-cell">评价晒单&nbsp;好评率</li>
		</ul>
		<ul class="mui-table-view  mui-media mui-col-xs-12">
			<s:iterator value="%{#request.cusRevList}" var="list">
				<li class="mui-table-view-cell"><s:if test="%{#list.level==1}">
						好评
					</s:if> <s:elseif test="%{#list.level==0}">
						中评
					</s:elseif> <s:else>
						差评
					</s:else>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${list.loginName}<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${list.createTime}<br />
					${list.content}</li>
			</s:iterator>
		</ul>
	</div>

	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<br />
	<!-- 底部 -->
	<div class="mui-content"
		style="width: 100%; height: 8%; background-color: #000000; color: #FFFFFF; position: fixed; bottom: 0; left: 0;">
		<a href="#"
			onclick="addToCollect(<s:property value="productInfo.productId" />,${session.collect})">
			<div class="item"
				style="width: 35%; background-color: #000000; color: #FFFFFF">

				<s:if test="%{#session.collect}"> 取消关注 </s:if>
				<s:else>添加关注</s:else>

			</div>
		</a> <a href="#" onclick="gotoCart()">
			<div class="item"
				style="width: 30%; background-color: #000000; color: #FFFFFF">
				购物车</div>
		</a> <a href="#"
			onclick="addToCart(<s:property value="productInfo.productId" />)">
			<div class="item"
				style="width: 35%; background-color: #E30000; color: #FFFFFF">
				加入购物车</div>
		</a>

	</div>

</body>

</html>