<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!--产品详情-->
<title><s:if
		test="productInfo.seoTitle!=null&&productInfo.seoTitle!=''">${productInfo.seoTitle}</s:if>
	<s:else>${productInfo.productFullName}</s:else></title>
<!-- 添加商品规格list -->
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/public_002.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/ms3All-min.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/productInfo_list.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/product.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/oproduct.css" />
<%@ include file="../include/head.jsp"%>
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/jquery.jcarousel.css"
	rel="stylesheet" type="text/css" />
<link
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/jqzoom.css"
	rel="stylesheet" type="text/css" />
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
table {
	border-collapse: collapse;
	border: none;
}

.paraTitle {
	wdith: 973px;
	height: 25px;
	text-align: center;
	background: #F5FAFE;
	display: block;
	line-height: 25px;
	font-size: 14px;
	font-weight: bold;
	border-top: 1px #ccc solid;
}

.parameterInfoTr {
	height: 25px;
}

.paraTh {
	border-right: 1px solid #ccc;
	background: #F5FAFE;
	padding-right: 5px;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
}

.paraTd {
	padding-left: 5px;
	background: #fff;
	border-top: 1px solid #ccc;
	border-bottom: 1px solid #ccc;
}

#yinProduct_1 {
	background: #fff;
	padding-top: 15px;
}

.discProd {
	width: 220px;
	white-space: nowrap;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	overflow: hidden;
}

.shpinxiq_left_detail {
	width: 350px;
	height: auto;
	float: left;
	padding-left: 10px;
}

.yinDefault {
	border: 1px solid #ccc;
	padding: 2px 10px 2px 9px;
	margin: 0 5px 0px 0;
	cursor: pointer;
}

.yinBor {
	border: 1px solid #f00;
}

.hide {
	display: none;
}

.tuweizi {
	width: 1002px;
	height: auto;
	overflow: hidden;
}

.tuweizi p {
	width: 619px;
	height: auto;
	line-height: 24px;
	margin: 0 auto;
}

.tuweizi ul li {
	width: 292px;
	float: left;
	overflow: hidden;
	height: 27px;
	margin-left: 10px;
}

.tuweizi ul li span.nameYin {
	text-align: left;
}

.tuweizi ul li span {
	float: left;
	display: block;
}

.curr_base {
	border: 2px solid #ccc;
	padding: 1px;
}

.cur_on {
	border: 2px solid #D01743;
}

#pd_lists {
	width: 100%;
	position: relative;
	overflow-x: auto;
	overflow-y: hidden;
}

body {
	font-family: 微软雅黑;
}

.yinBor_i {
	background:
		url(${fileUrlConfig.fileServiceUploadRoot}shop/images/workspace/gouY.png)
		no-repeat;
	position: absolute;
	width: 15px;
	height: 15px;
	z-index: 9;
	top: 35px;
	right: 0;
}

.yinBor_i2 {
	background:
		url(${fileUrlConfig.fileServiceUploadRoot}shop/images/workspace/gouY.png)
		no-repeat;
	position: absolute;
	width: 15px;
	height: 15px;
	z-index: 9;
	top: 10px;
	right: 0;
}

.yjfhr {
	color: red;
	background-color: #FF0000;
	width: 60px;
	border-radius: 2px;
	color: #FFFFFF;
	display: inline-block;
	line-height: 18px;
	padding: 4px 7px;
	text-align: center;
}

.yjfhr2 {
	color: red;
	background-color: #3C89FF;
	width: 60px;
	border-radius: 2px;
	color: #FFFFFF;
	display: inline-block;
	line-height: 18px;
	padding: 4px 7px;
	text-align: center;
}

.ggText {
	text-decoration: none;
	width: auto;
	white-space: nowrap;
	display: block;
	float: left;
	height: 25px;
	line-height: 25px;
	padding: 0 10px;
	text-align: center;
	position: relative;
	cursor: pointer;
}

.ggImage {
	text-decoration: none;
	width: 50px;
	white-space: nowrap;
	display: block;
	float: left;
	height: 50px;
	line-height: 28px;
	padding: 0;
	position: relative;
	cursor: pointer;
}

.hyds_jr {
	display: block;
	width: 226px;
	height: 36px;
	border: 3px #D01743 solid;
	background: #D01743;
	line-height: 36px;
	text-align: center;
	color: #fff;
	font-size: 17px;
	float: left;
	margin-right: 20px;
	text-decoration: none;
}
</style>
<script type="text/javascript">
	//商品图片放大镜效果
	$(document).ready(function() {
		$("#mycarousel").jcarousel({
			initCallback : mycarousel_initCallback
		});
		$(".jqueryzoom").jqueryzoom({
			xzoom : 500, //设置放大 DIV 长度（默认为200）
			yzoom : 500, //设置放大 DIV 高度（默认为200）
			position : "right", //设置放大 DIV 的位置（默认为右边）
			preload : 1, //默认情况下预加载的图片是1
			lens : 1
		//图像缩放镜头,默认是1;
		});
	});

	function mycarousel_initCallback(carousel) {
		$("#mycarousel li")
				.mouseover(
						function() {
							var img_path = '<s:property value="%{#application.fileUrlConfig.visitFileUploadRoot}"/>';
							var JQ_img = $("img", this);
							var imagemedium = JQ_img.attr("imagemedium");//获取中等图片
							var imagelarge = JQ_img.attr("imagelarge");//获取大图片
							$("#_middleImage").attr("src",
									img_path + imagemedium).attr("longdesc",
									img_path + imagelarge);//组拼路径
							$(this).siblings().each(
									function() {
										$("img", this).removeClass().addClass(
												"curr_base");
									});
							JQ_img.addClass("cur_on");
						});
	};

	function lalert(title, content) {
		$('#alert-overlay .modal-title').html(title);
		$('#alert-overlay .modal-content').html(content);
		$("#alert-overlay").overlay().load();
	};
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
<style>
.yjfhr {
	color: red;
	background-color: #FF0000;
	width: 60px;
	border-radius: 2px;
	color: #FFFFFF;
	display: inline-block;
	line-height: 18px;
	padding: 4px 7px;
	text-align: center;
}

.yjfhr2 {
	color: red;
	background-color: #3C89FF;
	width: 60px;
	border-radius: 2px;
	color: #FFFFFF;
	display: inline-block;
	line-height: 18px;
	padding: 4px 7px;
	text-align: center;
}
</style>
</head>
<body>
	<%@ include file="../include/header.jsp"%>
	<input type="hidden" id="goods"
		value="<s:property value="productInfo.goods"/>" />
	<input type="hidden" id="productPara"
		value="<s:property value="joProductPara.productParametersValue"/>" />
	<input type="hidden" id="productAttr"
		value="<s:property value="jaattrList"/>" />
	<input type="hidden" id="brandId_new"
		value="<s:property value="#request.brandId"/>" />
	<input type="hidden" id="productTypeId_new"
		value="<s:property value="#request.productTypeId"/>" />
	<input type="hidden" id="categoryParams_new"
		value="<s:property value="#request.categoryParams"/>" />
	<input type="hidden" id="productId_new"
		value="<s:property value="#request.productId"/>" />
	<!-- 全部 begin -->
	<div class="margin-center PublicWidth1004">
		<!-- 导航条 -->
		<div id="mbxBox" class="FontSize13 PublicWidth1004 margin-center ">
			<span class="ColorBlack">当前位置:<s:property escape="false"
					value='#request.pathInfo' /></span>
		</div>
		<!-- 商品轮播图、价格、店铺信息 begin -->
		<div class="ClearBoth publicMarginBot15 PublicWidth1004"
			style="height: auto;">
			<!-- 左侧滚动图片begin -->
			<div class="shpinxiq_left_detail">
				<table>
					<!-- 如果滚动图的数量大于0则显示滚动图 -->
					<s:if test="productImgList.size>0">
						<tr>
							<td style="width: 335; height: 335;">
								<div id="BigImage" class="jqueryzoom"
									style="border: 1px solid #ccc;">
									<img id="_middleImage" width="345" height="345"
										src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='%{productImgList[0].medium}'/>"
										longdesc="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='%{productImgList[0].large}'/>" />
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<ul id="mycarousel" class="jcarousel-skin-tango">
									<!--商品的附属图片-->
									<s:iterator value="productImgList">
										<li><img class="curr_base"
											style="width: 48px; height: 48px;"
											imagemedium="<s:property value='medium'/>"
											imagelarge="<s:property value='large'/>"
											src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='thumbnail'/>"
											onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" />
										</li>
									</s:iterator>
								</ul>
							</td>
						</tr>
					</s:if>
					<!-- 如果不上传滚动图片 则显示logo图 -->
					<s:else>
						<tr>
							<td style="width: 337px; height: 337px;">
								<div id="BigImage" class="jqueryzoom"
									style="border: 1px solid #ccc;">
									<img id="_middleImage" width="335" height="335"
										src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='productInfo.logoImg'/>"
										longdesc="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='productInfo.logoImg'/>" />
								</div>
							</td>
						</tr>
					</s:else>
				</table>
			</div>
			<!-- 左侧滚动图片end -->
			<!-- 店铺详情 begin -->
			<div
				style="width: 840px; padding-left: 10px; float: left; overflow: hidden; height: auto;">
				<input id="productFullName" type="hidden"
					value="<s:property value="#request.productInfo.productFullName"/>" />
				<input id="productName" type="hidden" name="productName"
					value="<s:property value="#request.productInfo.productName"/>" />
				<input id="buyPrice" type="hidden"
					value="<s:property value="buyPrice"/>" />
				<s:if test="#request.commodityPreview!=null">
					<!-- 					商品预览 -->
					<s:if test="productInfo!=null">
						<section class="DetailTextBox float-left publicMarginLR10">
						<h1 title="<s:property value='productInfo.productFullName'/>"
							class="FontSize20"
							style="font-size: 18px !important; font-weight: bold;">
							<div
								style="height: 3.6em; word-wrap: break-word; overflow: hidden;">
								<s:property value="productInfo.productFullName" />
							</div>
						</h1>

						<div class=" publicBorderB ColorBlue publicMarginTop15 FontSize13"
							style="padding-bottom: 5px;">
							<!--<s:property value="productInfo.describle" /> -->
						</div>

						<div class="textP ClearBoth publicPaddingTB10"
							style="height: 24px;">
							<p class="widthpx100" style="width: 67px; font-size: small;">价&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</p>
							<p style="font-size: 18px; font-weight: bold; color: #d01743;">￥${nowProductBuyPrice}</p>
							<input type="hidden" id="object_2"
								value="<s:property value='object2'/>" /> <span id='zk_1'
								style="background-color: #d01743; padding: 3px 5px; color: #fff; position: relative; top: 5px; margin-left: 5px;">
								<s:property value="#request.priceDiscount" />%&nbsp;原价折扣
							</span>
						</div>
						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 67px; font-size: small;">原&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</p>
							<p class="ColorHui03" style="margin-left: 2px;">

								<del>
									￥
									<s:property value="productInfo.marketPrice" />
								</del>
							</p>
						</div>
						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 67px; font-size: small;">运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</p>
							<p style="margin-left: 2px;">
								￥
								<s:if test="productInfo.isChargeFreight == 2">
									<s:property value="productInfo.freightPrice" />
								</s:if>
								<s:else>0.00</s:else>
							</p>
						</div>


						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 67px; font-size: small;">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</p>
							<p
								style="margin-left: 4px; color: #d01743; font-size: 14px !important;">
								<s:property value="brand.brandName" />
							</p>
						</div>
						<s:if test="productInfo.giveAwayCoinNumber != ''">
							<div class="textP ClearBoth">
								<p class="widthpx100 " style="width: 78px; font-size: small;">赠送商城币：</p>
								<p style="margin-left: 4px; font-size: 14px !important;">
									<fmt:formatNumber type="number"
										value="${productInfo.giveAwayCoinNumber}" />
								</p>
							</div>
						</s:if>
						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 67px; font-size: small;">发货地点：</p>
							<p style="margin-left: 4px; font-size: 14px !important;">
								${deliveryAddress}</p>
						</div>
						<s:if
							test="#request.shopTradeTagList!=null&&#request.shopTradeTagList.size>0">
							<div class="textP ClearBoth">
								<p class="widthpx100 " style="width: 67px; font-size: small;">适用专业：</p>
								<p
									style="margin-left: 4px; width: 320px; font-size: 14px !important;">
									<s:iterator value="#request.shopTradeTagList" status="s">
										<s:if test="#s.index!=#request.shopTradeTagList.size-1">
											<s:property value="tageName" />&nbsp;,&nbsp;
									</s:if>
										<s:else>
											<s:property value="tageName" />
										</s:else>
									</s:iterator>
								</p>
							</div>
						</s:if> <s:if
							test="#request.shopSituationTagList!=null&&#request.shopSituationTagList.size>0">
							<div class="textP ClearBoth">
								<p class="widthpx100 " style="width: 67px; font-size: small;">使用场合：</p>
								<p
									style="margin-left: 4px; width: 320px; font-size: 14px !important;">
									<s:iterator value="#request.shopSituationTagList" status="s">
										<s:if test="#s.index!=#request.shopSituationTagList.size-1">
											<s:property value="tageName" />&nbsp;,&nbsp;
									</s:if>
										<s:else>
											<s:property value="tageName" />
										</s:else>
									</s:iterator>
								</p>
							</div>
						</s:if> <input id="productId" type="hidden"
							value="<s:property value='productInfo.productId'/>" /> <input
							id="shopInfoId" type="hidden"
							value="<s:property value='productInfo.shopInfoId'/>" /> <input
							id="goodsSpecifications" type="hidden"
							value="<s:property value='goodsSpecifications'/>" /> </section>
					</s:if>
					<s:else>
						<section class="DetailTextBox float-left publicMarginLR10">
						<h1 class="FontSize20">很抱歉，当前商品不存在。</h1>
						</section>
					</s:else>
				</s:if>
				<s:elseif
					test="productInfo!=null&&productInfo.isPutSale==2&&productInfo.isPass==1&&shopInfo.isPass==3&&shopInfo.isClose==0">
					<!-- 					正常的商品展示 -->
					<section class="DetailTextBox float-left publicMarginLR10">
					<h1 title="<s:property value='productInfo.productFullName'/>"
						class="FontSize20"
						style="font-size: 18px !important; font-weight: bold;">
						<div
							style="height: 3.6em; word-wrap: break-word; overflow: hidden;">
							<s:property value="productInfo.productFullName" />
						</div>
					</h1>

					<div class=" publicBorderB ColorBlue publicMarginTop15 FontSize13"
						style="padding-bottom: 5px;">
						<!--<s:property value="productInfo.describle" />-->
					</div>
					<div class="textP ClearBoth publicPaddingTB10"
						style="height: 24px;">
						<p class="widthpx100" style="width: 67px; font-size: small;">销&nbsp;&nbsp;售&nbsp;价：</p>
						<p style="font-size: 18px; font-weight: bold; color: #d01743;">
							￥
							<s:property value="productInfo.salesPrice" />
						</p>
						<input type="hidden" id="object_2"
							value="<s:property value='object2'/>" /> <span id='zk_1'
							style="background-color: #d01743; padding: 3px 5px; color: #fff; position: relative; top: 5px; margin-left: 5px;">
							<s:property value="#request.priceDiscount" />%&nbsp;市场价折扣
						</span>
					</div>
					<%-- </s:else> --%> <s:if
						test="#session.customer!=null&&#request.memDiscount!=null">
						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 67px; font-size: small;">会员折扣：</p>
							<p style="font-size: 15px; font-weight: bold; color: #d01743;">
								<fmt:formatNumber value="${memDiscount}" pattern="#,##0.0" />
								折
							</p>
						</div>
					</s:if>
					<div class="textP ClearBoth">
						<p class="widthpx100 " style="width: 67px; font-size: small;">市&nbsp;&nbsp;场&nbsp;价：</p>
						<p class="ColorHui03" style="margin-left: 2px;">
							<del>
								￥
								<s:property value="productInfo.marketPrice" />
							</del>
						</p>
					</div>
					<div class="textP ClearBoth">
						<p class="widthpx100 " style="width: 67px; font-size: small;">运&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;费：</p>
						<p style="margin-left: 2px;">
							￥
							<s:property value="shopInfo.postage" />
						</p>
					</div>
					<div class="textP ClearBoth">
						<p class="widthpx100 " style="width: 67px; font-size: small;">品&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;牌：</p>
						<p
							style="margin-left: 4px; color: #d01743; font-size: 14px !important;">
							<s:property value="brand.brandName" />
						</p>
					</div>
					<s:if test="productInfo.giveAwayCoinNumber != ''">
						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 78px; font-size: small;">赠送商城币：</p>
							<p style="margin-left: 4px; font-size: 14px !important;">
								<fmt:formatNumber type="number"
									value="${productInfo.giveAwayCoinNumber}" />
							</p>
						</div>
					</s:if>
					<div class="textP ClearBoth">
						<p class="widthpx100 " style="width: 67px; font-size: small;">发货地点：</p>
						<p style="margin-left: 4px; font-size: 14px !important;">
							${deliveryAddress}</p>
					</div>
					<s:if
						test="#request.shopTradeTagList!=null&&#request.shopTradeTagList.size>0">
						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 67px; font-size: small;">适用专业：</p>
							<p
								style="margin-left: 4px; width: 320px; font-size: 14px !important;">
								<s:iterator value="#request.shopTradeTagList" status="s">
									<s:if test="#s.index!=#request.shopTradeTagList.size-1">
										<s:property value="tageName" />&nbsp;,&nbsp;
									</s:if>
									<s:else>
										<s:property value="tageName" />
									</s:else>
								</s:iterator>
							</p>
						</div>
					</s:if> <s:if
						test="#request.shopSituationTagList!=null&&#request.shopSituationTagList.size>0">
						<div class="textP ClearBoth">
							<p class="widthpx100 " style="width: 67px; font-size: small;">使用场合：</p>
							<p
								style="margin-left: 4px; width: 320px; font-size: 14px !important;">
								<s:iterator value="#request.shopSituationTagList" status="s">
									<s:if test="#s.index!=#request.shopSituationTagList.size-1">
										<s:property value="tageName" />&nbsp;,&nbsp;
									</s:if>
									<s:else>
										<s:property value="tageName" />
									</s:else>
								</s:iterator>
							</p>
						</div>
					</s:if> <!-- 京东类型的商品规格展示 begin --> <input type="hidden" id="map"
						value="<s:property value='map.size'/>" /> <s:iterator value="map"
						id="m" status="index">
						<s:if test="#m.key.type==2">
							<div class="textP ClearBoth hyds">
								<p
									style="padding-bottom: 5px; float: left; display: block; font-weight: lighter; font-size: 12px;">
									<span
										id="index_specification<s:property value='#m.key.specificationId'/>"
										flag="<s:property value='#index.index+1'/>"
										style="float: left; height: 25px; line-height: 25px; font-size: 16px; color: #666; font-weight: bold;"><s:property
											value="#m.key.name" />：</span>
									<s:iterator value="#m.value.specificationValueListImage"
										id="m2">
										<s:iterator value="#m2" var="list">
											<span class="yinDefault ggImage"
												ggvname="<s:property value="#list.specificationValue.name"/>"
												id="<s:property value="#list.specificationValue.specificationValueId"/>"
												name="specification<s:property value='#list.specificationValue.specificationId'/>">
												<img
												onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"
												width="50" height="50"
												src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${list.image}"
												title="${list.specificationValue.name}" /> <i flag="2"></i>
											</span>
										</s:iterator>
									</s:iterator>
								</p>
							</div>
						</s:if>
						<s:else>
							<div class="textP ClearBoth hyds">
								<p
									style="padding-bottom: 5px; float: left; display: block; font-weight: lighter; font-size: 12px;">
									<span
										id="index_specification<s:property value='#m.key.specificationId'/>"
										flag="<s:property value='#index.index+1'/>"
										style="float: left; height: 25px; line-height: 25px; color: #000; font-size: small;"><s:property
											value="#m.key.name" />：</span>
									<s:iterator value="#m.value.specificationValueList" id="m2">
										<s:iterator value="#m2" var="list">
											<span class="yinDefault ggText"
												ggvname="<s:property value="#list.specificationValue.name"/>"
												id="<s:property value="#list.specificationValue.specificationValueId"/>"
												name="specification<s:property value='#list.specificationValue.specificationId'/>">
												<s:property value="#list.specificationValue.name" /> <i
												flag="1"></i>
											</span>
										</s:iterator>
									</s:iterator>
								</p>
							</div>
						</s:else>
					</s:iterator>
					<div style="width: 589px; height: auto;">
						<div>
							<div class="shulzuof"
								style="margin-left: 0; height: 25px; line-height: 25px;">
								<span style="font-size: small; color: #555;">数量：</span>
							</div>
							<div class="shulzuof"
								style="height: 25px; line-height: 25px; margin-top: 5px;">
								<img
									src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/spxq_03.jpg"
									onclick="addNum('-1')" style="padding-right: 3px;" />
							</div>
							<div style="float: left;">
								<input
									style="width: 50px; font-size: 16px; color: red; text-align: center; height: 22px; margin: 2px 8px 2px 10px; display: block; line-height: 22px;"
									maxlength="3" id="amount" name="amountItme" type="text"
									onblur="addNum('0')" value="1" />
							</div>
							<div class="shulzuof"
								style="width: 26px; height: 25px; line-height: 25px; margin-top: 5px;">
								<img
									src="${fileUrlConfig.fileServiceUploadRoot}shop/front/img/spxq_05.jpg"
									onclick="addNum('-2')" style="padding-left: 0px;" />
							</div>
							<span
								style="float: left; color: #D2D2D2; margin-left: 30px; font-size: 14px; dislay: block; height: 25px; line-height: 25px;">件</span>
							&nbsp;<span
								style="display: block; margin-left: 20px; float: left; font-size: 14px; color: #d2d2d2; height: 25px; line-height: 25px;">库存</span>
							<span id="msg" class="color1"
								style="display: block; float: left; margin: 0px 0 0 5px; color: #d2d2d2; height: 25px; line-height: 25px; font-size: 14px;">
								<s:if test="#request.productInfo.storeNumber<0">
									0
								</s:if> <s:else>
									<s:property value="#request.productInfo.storeNumber" />
								</s:else>
							</span> <span style="float: left; color: #D2D2D2; font-size: 16px;">${productInfo.weightUnit}</span>
							<span id="showMsg" class="color1"></span>
							<div style="clear: both;"></div>
						</div>
						<div style="clear: both;"></div>
						<!-- 京东类型的商品规格展示 end -->
						<input id="productId" type="hidden"
							value="<s:property value='productInfo.productId'/>" /> <input
							id="shopInfoId" type="hidden"
							value="<s:property value='productInfo.shopInfoId'/>" /> <input
							id="goodsSpecifications" type="hidden"
							value="<s:property value='goodsSpecifications'/>" />
						<!-- 加入购物车 与 收藏商品 -->
						<div class=" publicMarginT10" style="width: 510px;">
							<a href="javascript:saveCustomerCollectProduct();"
								class="hyds_jr"
								style="background: #fff; color: #D01743; text-decoration: none;">收藏商品</a>
							<a style="color: #fff; text-decoration: none;"
								href="javascript:putProToShopCart(${productInfo.productId});"
								class="hyds_jr"> <img
								src="${fileUrlConfig.fileServiceUploadRoot}images/shoping.png"
								style="padding-right: 5px;" />加入购物车
							</a>
							<div style="clear: both;"></div>
						</div>
					</section>
				</s:elseif>
				<s:else>
					<section class="DetailTextBox float-left publicMarginLR10">
					<h1 class="FontSize20">很抱歉，当前商品已下架或不存在。</h1>
					</section>
				</s:else>
				<!-- 店铺详情 begin -->
				<div
					style="height: auto; float: right; width: 196px; margin-right: 10px;">
					<div class="Shop_Information float-right publicHidden"
						style="border: 1px solid #D01743; width: 192px;">
						<h4 class="ColorWhite1 FontSize14 publicPadding2_5"
							style="background: #D01743;">店铺详情</h4>
						<article class="publicPadding2_5 publicMarginT5">
						<h3 class="FontSize13 FontSizeB">
							<s:property value="shopInfo.shopName" />
						</h3>
						<p class="publicMarginT5">
							<s:iterator begin="0" end="photoNum-1">
								<i class="publicinline medal-j-y-t medal-j"></i>
							</s:iterator>
						</p>
						<p>
							<span class="ColorGreen"><s:if test="grade > 0">${grade}</s:if>
								<s:else>0</s:else>%</span> &nbsp;信誉
						</p>
						<s:if test="qqList!=null">
							<s:iterator value="qqList" var="map">
								<p class="publicMarginT5" style="width: 192px;">
									<a style="text-decoration: none;" target="_blank"
										href="http://wpa.qq.com/msgrd?v=3&uin=<s:property value='#map.qq'/>&site=qq&menu=yes"><img
										border="0" src="http://wpa.qq.com/pa?p=2:12345678:51" />&nbsp;&nbsp;&nbsp;&nbsp;<span
										style="background-color: #2e9bf2; width: 191px; height: 16px; font-size: 12px; padding: 2px 0; vertical-align: middle; color: #fff; border-radius: 3px;">&nbsp;&nbsp;<s:property
												value="#map.nikeName" />&nbsp;&nbsp;
									</span></a>
								</p>
							</s:iterator>
						</s:if> <s:if test="shopInfo.phoneShowStatus==1">
							<h3 class="FontSize13 FontSizeB">
								联系电话：
								<s:property value="shopInfo.phone" />
							</h3>
						</s:if>
						<p class="publicdashedline" style="padding-bottom: 5px;">
							<a target="_blank"
								href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="shopInfo.shopInfoId" />"
								class="ColorBlue">进入店铺 &gt;&gt;</a>
						</p>
						<!-- 免邮费设置 --> <s:if
							test="shopInfo.minAmount!=null&&shopInfo.minAmount!=''">
							<div class="textP ClearBoth">
								<p style="color: red; font-weight: 800">
									该店满
									<s:property value="shopInfo.minAmount" />
									元免邮费
									<p />
							</div>
						</s:if>
						<h4 class="FontSizeB">付款方式</h4>
						<b class=" publicinline Payments_icon " style="margin-top: 10px;">
							<img style="height: 30px; width: 50px;"
							src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/cart/Alipay.jpg" />
						</b> </article>
					</div>
					<!-- 微信二维码begin -->
					<img
						onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"
						src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='productInfo.qrCode'/>"
						style="margin: 15px auto 0 58px;" />
					<!-- 微信二维码end -->
				</div>
				<!-- 店铺详情 end -->
			</div>
		</div>
		<!-- 商品轮播图、价格、店铺信息 begin -->
		<!-- 相同分类品牌下的商品 begin -->
		<input id="productInfoListByBrandType-hid" type="hidden"
			value="<s:property value='#request.productInfoListByBrandType.size'/>" />
		<s:if test="#request.productInfoListByBrandType.size>0">
			<div class="b2b2c_main">
				<DIV class="SeriesTitle">${ptName}全部产品:</DIV>
				<DIV class="proSeries" id="itemSeriesCont">
					<UL class="productList">
						<s:iterator value="#request.productInfoListByBrandType" var="o">
							<LI>
								<DIV class="productPic">
									<DIV class="current-item-pic">
										<img id="goods_${o.goods}" class="xiaoshou"
											flag="${o.productId }"
											onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"
											src="${fileUrlConfig.visitFileUploadRoot}${o.logoImg}" />
									</DIV>
									<P class="productTitle">
										<a class="xiaoshou" flag="${o.productId }" href="javascrpt:;">${o.productName
											}</a>
									</P>
									<P class="productPrice">
										￥
										<s:property value="productInfo.salesPrice" />
										<S>￥<s:property value="productInfo.marketPrice" /></S>
									</P>
								</DIV>
							</LI>
						</s:iterator>
						<script>
							//初始化选中对应的商品边框
							$(function() {
								//给图片和商品名称加上小手样式
								$(".xiaoshou").css("cursor", "pointer");
								//图片和文字的单击事件
								$(".xiaoshou")
										.click(
												function() {
													var productId = $(this)
															.attr("flag");
													location.href = "${pageContext.request.contextPath}/productInfo/"
															+ productId
															+ ".html";
													//location.href="${pageContext.request.contextPath}/productInfoPage/"+$("#brandId_new").val()+"-"+$("#productTypeId_new").val()+"-"+$("#categoryParams_new").val()+"-"+productId+".html";
												});
								var goods = '${productInfo.goods}';
								$("#goods_" + goods).css("cursor", "default");
								$("#goods_" + goods).addClass("cur-img");
							});
						</script>
					</UL>
				</DIV>
			</div>
		</s:if>
		<!-- 相同分类品牌下的商品 end -->
		<!-- 商品详细描述 begin -->
		<div class="Description" style="overflow: hidden;">
			<div class="splunb">
				<div class="splunb_head">
					<ul id="yinProduct_a_current_">
						<li onclick="changeContent('spjs')"><a id="spjs"
							style="background: #d01743; color: #fff;" class="current"
							href="javascript:;">商品介绍</a></li>
						<li onclick="changeContent('spcs')"><a id="spcs"
							href="javascript:;">规格参数</a></li>
						<li onclick="changeContent('sppj')"><a id="sppj"
							target="evaluateFrame" href="${pageContext.request.contextPath}/productsdetail/evaluateProductList.action?productId=<s:property value='productInfo.productId'/>">商品评价</a></li>
						<li onclick="changeContent('shbz')"><a id="shbz"
							href="javascript:;">售后保障</a></li>
					</ul>
				</div>
				<div class="smsp">
					<!-- 商品属性 begin -->
					<div class="tuweizi" id="yinProduct_1">
						<ul id="productAttrShow">
							<li style="width: 292px; height: 27px;"><span>商品名称：</span><span
								style="float: none;" class="discProd"
								title="${productInfo.productFullName}">${productInfo.productFullName}</span>
							</li>
							<li style="width: 292px; height: 27px;"><span>上架时间：</span><span
								style="float: none; display: inline;" class="discProd"><s:date
										name="productInfo.putSaleDate" format="yyyy-MM-dd HH:mm:ss" /></span>
							</li>
							<li style="width: 292px; height: 27px;"><span>商品重量：</span><span
								style="float: none; display: inline;" class="discProd">${productInfo.weight}&nbsp;${productInfo.weightUnit}</span>
							</li>
							<li style="width: 292px; height: 27px;"><span>品牌：</span> <span
								style="float: none; display: inline;" class="discProd"> <s:if
										test="brand!=null">
									${brand.brandName}
								</s:if> <s:else>其他</s:else>
							</span></li>
							<li style="width: 292px; height: 27px;"><span>销售价：</span><span
								style="float: none; display: inline;" class="discProd">${buyPrice}</span>
							</li>
						</ul>

						<div class="splunb"
							style="border-top: 1px solid #ccc; width: 1208px;"></div>
						<!-- 商品描述 begin -->
						<div class="splunb" style="padding: 10px; width: 1100px;">
							<s:property value="productInfo.functionDesc" escape="false" />
						</div>
						<!-- 商品描述 end -->
					</div>
					<!-- 商品属性 end -->
	
					<!-- 规格参数 begin -->
					<div class="tuweizi hide" id="yinProduct_2">
						<s:if test="#request.productGG.size>0">
							<div style="margin-top: 10px;">
								<strong style="font-size: 120%; padding-left: 10px;">商品规格：</strong>
								<ul id="productAttrShow"
									style="width: 100%; height: auto; overflow: hidden; margin-top: 5px;">
									<s:iterator value="#request.productGG" var="ggMap">
										<s:if test="productInfo.productId==#ggMap.key">
											<s:iterator value="#ggMap.value.gg" var="gg">
												<li style="width: 292px; height: 27px;"><span><s:property
															value="#gg.key" />：</span><span class="discProd"
													style="width: auto;"><s:property value="#gg.value" /></span></li>
											</s:iterator>
										</s:if>
									</s:iterator>
								</ul>
							</div>
						</s:if>
						<div style="margin-top: 10px;">
							<strong style="font-size: 120%; padding-left: 10px;">商品参数：</strong>
							<div style="background: #F5FAFE; margin-top: 10px;">
								<span id="totalProdPara"></span>
							</div>
						</div>
					</div>
					<div class="tuweizi hide" id="yinProduct_3">
						<iframe id="iframe_body" onload="autoHeight()"
							name="evaluateFrame" width="1280px" src="" frameborder="0"
							scrolling="no"></iframe>
					</div>
					<!-- 规格参数 end -->
					<div class="tuweizi hide" id="yinProduct_4">
						<div
							style="width: 1208px; padding: 10px; border-top: 1px dotted #DEDEDE; line-height: 130%; line-height: 22px;">
							<strong style="font-size: 120%;">售后保障：</strong><br />
							<p style="width: 1208px;">
								本店所售商品一律为原装正品并销售全国联保；按国家规定实行1年保修7退换。(7天内出现任何质量问题来回邮费由我方负责，人为除外，8-30天换货来回运费我方与贵方各一半，30天以后均有贵方负责)
								<br />特此声明！ 签收注意事项 我司所发的商品都是经过质量检查，确保全新，完好无损，有问题之商品是出不了仓库大门。
								特别提醒 为充分保障您的权益，收货时请不要急于在物流单上签字，请先做以下操作： <br />
								第一，检查外包装是否完好，并当着快递面拆箱验货（我们会在快递单上注明必须当面验货，部分快递是要求先签字后验货）
								，发现破损,配件缺失等，请立即拒收。 <br />
								第二，签收后产品无破损无配件缺失，请将产品通上电，简单检查产品工作状态，如不能正常运转请立即联系我们。 <br />
								第三，需要他人代签的（如门卫，保安，亲友等）请务尽管交
								待好签收注意项。本制度未尽事宜，按淘宝官方制度或国家相关法律法规解决，解释权归本店所有。 <br /> 关于退货
								如果您在收到我们的产品15天之内，发现产品存在非人为的质量问题，我们将免费为您提供更换或者维修服务由此产生的费用同我店负责。
								更换条件：产品在两周之内发生非人为问题，没有明显使用痕迹（可以退厂） <br />外观无损，所有附件均完整的情况下我们
								可以提供产品换货服务。 退货条件：7天内没有使用过，没有人为损坏，而是由于顾客主观原因我店可以提供退货服务，但是需要由顾客承担往
								返双向邮费。维修条件：产品的代维修服务，费用由又方协商。
								关于费用：非人为质量问题的商品，有顾客描述产品故障，我店在收到退回的商品并检测确实存在顾客所描述的故障，
								运费由我们承担，检测没有顾客所描述的故障我们将原物原路寄回，运费费用由您承担；我点所承担运费仅限与第一次
								相同的运费，多余的部分由买家承担。
								拒绝到付：到付是正常运费的2倍以上，顾客如果到付运费我店将按情况处理，需要我们承担的运费的快件请在发货前 与我们沟通
							</p>
						</div>
					</div>

					<script type="text/javascript">
						new tab('yinProduct_a_current_', '_', null, 'onclick');
					</script>
					<br />

					<div
						style="display: block; padding: 10px; overflow: hidden; border-top: 1px dotted #DEDEDE; line-height: 130%; line-height: 22px;">
						<strong style="font-size: 120%;">服务承诺：</strong><br />
						SHOPJSP向您保证所售商品均为正品行货。凭质保证书及SHOPJSP发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、钟表由SHOPJSP联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。SHOPJSP还为您提供具有竞争力的商品价格和运费政策，请您放心购买！
						<br /> <br />
						注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！
					</div>
				</div>
				<!-- 同类商品推荐 begin -->
				<s:if test="mapList.size>0">
					<section class="publicMarginTop15 publicPaddingT15">
					<h3 class=" publicblock publicPadding10 FontSize16 FontSizeB"
						style="background: #ccc; font-weight: 900; width: 1210px; margin: 0 auto; color: #333;">同类商品推荐</h3>
					<ul class="WishListUl ClearBoth"
						style="width: 1210px; margin: 0 auto;">
						<s:iterator value="mapList">
							<li style="margin-right: 10px;"><a target="_blank"
								href="${pageContext.request.contextPath}/productInfo/<s:property value='productId' />.html">
									<img
									onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"
									src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"
									style="height: 220px; width: 220px" />
							</a>
								<p
									style="padding-top: 10px; height: 38px; overflow: hidden; margin-top: 7px;">
									<a target="_blank"
										href="${pageContext.request.contextPath}/productInfo/<s:property value='productId' />.html"><s:property
											value="productFullName" /> </a>
								</p>
								<p class="ColorRed" style="margin: 10px 0 5px -4px;">
									<span class="FontSize18">￥<s:property value="salesPrice" /></span>
								</p> <a
								style="display: inline-block; line-height: normal !important;"
								href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="shopInfoId" />"
								class="ColorBlue publicinline line-height3"><i
									class="icon iconShop01"
									style="background-position: left top; width: 25px !important;"></i>
									<s:property value="shopName" /></a></li>
						</s:iterator>
					</ul>
					</section>
				</s:if>
				<!-- 同类商品推荐 end -->
			</div>
		</div>
		<!-- 商品详细描述 begin -->
	</div>
	</div>
	<!-- 全部 end -->
	<%@ include file="../include/footer.jsp"%>
	<div class="modal" id="isOk-alert" style="width: 250px; height: 100px;">
		<h2 class="modal-title">提醒</h2>
		<div class="modal-content">
			<a class="close" style="display: none;"></a> <font size="5">商品成功加入购物车!</font>
		</div>
	</div>
</body>

</html>
