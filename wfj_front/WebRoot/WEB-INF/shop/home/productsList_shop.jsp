<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%--<!--产品列表_图片-->--%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/css.css" />
<title>产品列表图片页</title>
<%@ include file="../include/head.jsp"%>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<style>
div.node_path {
	font-size: 13px;
	margin: 5px 0;
}

.node_path a {
	color: #777777;
	font-weight: normal;
	text-decoration: none;
}

.node_path span {
	color: #999999;
	padding: 0 0.5em;
}

.node_path a.active {
	color: #222222;
}

table.search_filter_table {
	border: medium none;
	border-collapse: collapse;
	border-spacing: 0;
	display: inline-table;
	width: 1204px;
}

table,div {
	word-break: break-all;
}

table.search_filter_table th {
	background-color: #EEEEEE;
	border: 1px solid #DDDDDD;
	padding: 10px;
	vertical-align: middle;
	width: 100px;
	font-weight: bold;
}

table.search_filter_table td {
	border: 1px solid #DDDDDD;
	padding: 10px;
}

.summary_list {
	clear: both;
}

.summary_list .split_list {
	height: auto;
	width: 100%;
}

.summary_list .split_list ul.node_list {
	padding-bottom: 0;
	position: relative;
}

.summary_list .node_list li {
	background-image: none;
	font-size: 1em;
	margin-bottom: 4px;
	margin-right: 10px;
}

.split_line5 ul.node_list li {
	float: left;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 90px;
}

.node_list img {
	border: medium none;
	color: #FFFFFF;
	vertical-align: middle;
}

.summary_list li span.cnt {
	color: #777777;
	font-size: 0.8em;
	padding-left: 0;
}

.summary_list  a {
	text-decoration: none;
	color: #404040;
}

.summary_list  a:hover {
	color: red;
}

.summary_list li .filter-attr-off {
	border: 1px solid #D9D9D9;
	color: #404040;
}

.summary_list li .filter-attr-on {
	-moz-border-bottom-colors: none;
	-moz-border-left-colors: none;
	-moz-border-right-colors: none;
	-moz-border-top-colors: none;
	border: 1px solid #D9D9D9;
	border-bottom: none;
	color: #404040;
}

.summary_list LI .filter-attr-on {
	BORDER-TOP: #d9d9d9 1px solid;
	BORDER-RIGHT: #d9d9d9 1px solid;
	BORDER-BOTTOM: #d9d9d9 1px;
	COLOR: #404040;
	BORDER-LEFT: #d9d9d9 1px solid;
	Z-INDEX: 2
}

.summary_list li .filter-attr {
	background-color: #FFFFFF;
	color: #404040;
	display: block;
	float: left;
	height: 23px;
	line-height: 22px;
	padding: 0 18px 0 10px;
	position: relative;
	text-decoration: none !important;
	white-space: nowrap;
}

.summary_list li .filter-attr .arrow-off {
	background-position: -720px 0;
	background:
		url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/arrow-down.png")
		no-repeat;
	display: block;
	height: 10px;
	position: absolute;
	right: 3px;
	top: 10px;
	width: 10px;
}

.summary_list li .filter-attr .arrow-on {
	background-position: -720px 0;
	background:
		url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/arrow-right.png")
		no-repeat;
	display: block;
	height: 10px;
	position: absolute;
	right: 3px;
	top: 10px;
	width: 10px;
}

.summary_list li {
	float: left;
	margin: 0 10px 10px 0;
	position: relative;
}

.summary_list .split_more_list {
	overflow: hidden;
	width: 100%;
}

.summary_list li .filter-attr-expand {
	background-color: #FFFFFF;
	border: 1px solid #D9D9D9;
	display: none;
	padding: 10px;
	position: absolute;
	top: 23px;
	width: 500px;
	z-index: 1;
}

.summary_list li .filter-attr-expand li {
	float: left;
	margin: 3px 15px 5px 0;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	width: 142px;
}

.checkbox {
	vertical-align: middle;
}

.goods-mark-hot {
	background-position: -513px -100px;
}

.goods-mark-imglist {
	position: absolute;
	right: 4px;
	top: 0;
}

.goods-mark {
	background:
		url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/product_module.png")
		no-repeat scroll -514px -90px rgba(0, 0, 0, 0);
	color: #fff;
	display: block;
	font-size: 12px;
	font-weight: normal;
	height: 40px;
	line-height: 40px;
	padding-top: 12px;
	text-align: center;
	width: 42px;
}
</style>
<script type="text/javascript">
	$().ready(function() {
		//Lazy Load 延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn",
			threshold : 400
		});
	});
	//初始化
	$(function() {
		var whichOrderBy = $("#flag").val();
		if (whichOrderBy != null && whichOrderBy != "") {
			$("#" + whichOrderBy).css("color", "red");
		}
		//表单验证
		$("#btn1")
				.bind(
						"click",
						function() {
							if ($("#minPrice").val() == ""
									|| $("#maxPrice").val() == "") {
								lalert("提示", "请输入价格！");
<%-- 请输入价格:Please input the price  --%>
	} else if (eval($("#minPrice").val()) >= eval($(
									"#maxPrice").val())) {
								lalert("提示", "请输入正确的价格区间！");
<%-- 请输入正确的价格范围:Please input the price  --%>
	return false;
							} else if ($("#minPrice").val() != null
									&& $("#maxPrice").val() != null
									&& eval($("#minPrice").val()) < eval($(
											"#maxPrice").val())) {
								$("#form1").submit();
							}
						});
	});
	//初始化
	$(function() {
		// 	    	选中品牌
		if ($("#brandHidden").val() != null) {
			var bValue = $("#brandHidden").val();//品牌的id数组
			if (bValue != null) {
				var brandParams = bValue.split(","); //将品牌字符串以','分割
				for ( var i = 0; i < brandParams.length; i++) {//循环选中
					var brand = brandParams[i];
					$("#brand_" + brand).attr("checked", true);
				}
			}
		}
	});

	//初始化
	$(function() {
		var id = '${brandId}';
		if (id == '') {
			$("#isTrue_" + id)
					.attr(
							"src",
							"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
		}
	});
	//点击品牌后更换选择图标
	function pinpai(brandId, productTypeId, level) {
		var brandIds = $("#brandIds").val();
		var brandList = brandIds.split(",");
		var flag = false;
		for ( var i = 0; i < brandList.length; i++) {
			if (brandList[i] == brandId) {
				flag = true;
				brandList.splice(i, 1);
				break;
			}
		}
		brandIds = brandList.join(",");
		if (!flag) {
			if (brandIds == null || brandIds == "") {
				brandIds = brandId;
			} else {
				brandIds += "," + brandId;
			}
		}
		$("#brandIds").val(brandIds);
		publicFunction(productTypeId, level);
	}

	//公共的查询方法（点击规格，品牌，属性，均需调用该方法）
	function publicFunction(productTypeId, level) {
		var orderBy = '${orderBy}';
		var url = "${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?orderBy="
				+ orderBy;
		//获取规格信息
		var sv = $("#specificationValueIds").val();
		if (sv != "" && sv != null) {
			url += "&specificationValueId=" + sv;
		}
		//获取属性信息
		var attrValueIds = $("#attrValueIds").val();
		if (attrValueIds != "" && attrValueIds != null) {
			url += "&attrValueId=" + attrValueIds;
		}
		//获取品牌信息
		var brands = $("#brandIds").val();
		if (brands != "" && brands != null) {
			url += "&brandId=" + brands;
		}
		url += "&productTypeId=" + productTypeId;
		url += "&level=" + level;
		window.location.href = url;
	}
	//点击规格
	function guige(brandId, productTypeId, specificationValueId, level) {
		var sv = $("#specificationValueIds").val();
		var svList = sv.split(",");
		var flag = false;
		for ( var i = 0; i < svList.length; i++) {
			if (svList[i] == specificationValueId) {
				flag = true;
				svList.splice(i, 1);
				break;
			}
		}
		sv = svList.join(",");
		if (!flag) {
			if (sv == null || sv == "") {
				sv = specificationValueId;
			} else {
				sv += "," + specificationValueId;
			}
		}
		$("#specificationValueIds").val(sv);
		publicFunction(productTypeId, level);
	}
	//点击属性
	function productAttrValue(brandId, productTypeId, attrValueId, attrId,
			level) {
		//当前操作的一组checkbox控件的名称
		var checkboxName = $("#attrValueId" + attrValueId).attr("name");
		//选中当前操作属性值单选钮
		if ($("#attrValueId" + attrValueId).attr("src") == "${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif") {
			$("#attrValueId" + attrValueId)
					.attr(
							"src",
							"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_off.gif");
		} else {
			$("#attrValueId" + attrValueId)
					.attr(
							"src",
							"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
		}
		//清空当前操作的一组checkbox控件的选择状态 除了当前操作的控件
		var id = "attrValueId" + attrValueId;
		$('img[name="' + checkboxName + '"][id!="' + id + '"]')
				.attr(
						"src",
						"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_off.gif");

		var url = "${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="
				+ productTypeId + "&level=" + level + "&orderBy=normal";
		//获取已经操作的扩展属性ids
		var attrIds = $("#attrIds").val();
		var tmpAttrIds = "," + attrIds + ",";
		var index = tmpAttrIds.indexOf("," + attrId + ",");
		if (index < 0) {
			attrIds += attrId + ",";
			if (attrIds != "") {
				attrIds = attrIds.substring(0, attrIds.lastIndexOf(","));
			}
		}
		$("#attrIds").val(attrIds);
		//获取所有选中单选扭的值
		var srcPath = "${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif";
		var attrValueIds = "";
		$('img[src="' + srcPath + '"][flag2="attr"]').each(function() {
			attrValueIds += $(this).attr("flag") + ",";
		});
		if (attrValueIds != "" && attrValueIds != null) {
			attrValueIds = attrValueIds.substring(0, attrValueIds
					.lastIndexOf(","));
		}
		$("#attrValueIds").val(attrValueIds);
		publicFunction(productTypeId, level);
	}

	//规格js效果
	(function($) {
		$(function() {
			$('#sort_filter').change(function() {
				var url = $('option:selected', $(this)).attr('href');
				location.href = url;
			});
			$('span[cpath]').hover(
					function() {
						$(this).replaceWith(
								'<a href="/s/c-' + $(this).attr('cpath')
										+ '/">' + $(this).text() + '</a>')
					});
			var dv = this;
			dv._CartTimer = null;
			$(".filter-attr").mouseenter(
					function() {
						var h = $(this);
						clearTimeout(h._CartTimer);
						h._CartTimer = null;
						$(this).removeClass("filter-attr-off").addClass(
								"filter-attr-on");
						$(this).children("i").removeClass("arrow-off")
								.addClass("arrow-on");
						$(this).parent().find(".filter-attr-expand").show();
					}).mouseleave(
					function() {
						var t = $(this);
						dv._CartTimer = setTimeout(function() {
							t.parent().find(".filter-attr-expand").hide();
							t.removeClass("filter-attr-on").addClass(
									"filter-attr-off");
							t.children("i").removeClass("arrow-on").addClass(
									"arrow-off");
						}, 100)
					});
			$(".filter-attr-expand").hover(
					function() {
						clearTimeout(dv._CartTimer);
						dv._CartTimer = null;
					},
					function() {
						dv._CartTimer = setTimeout(function() {
							$(".filter-attr-expand").hide();
							$(".filter-attr-expand").next().removeClass(
									"filter-attr-on").addClass(
									"filter-attr-off");
							$(".filter-attr-expand").next().children("i")
									.removeClass("arrow-on").addClass(
											"arrow-off");
						}, 100)
					});
		});
	})(jQuery);

	$(function() {
		//规格回显
		var specificationValueId = '${specificationValueId}';
		var s = specificationValueId.split(",");
		for ( var i = 0; i < s.length; i++) {
			$("#specificationValueId" + s[i])
					.attr(
							"src",
							"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
		}
		//品牌回显
		var brandId = '${brandId}';
		var b = brandId.split(",");
		for ( var i = 0; i < b.length; i++) {
			$("#isTrue_" + b[i])
					.attr(
							"src",
							"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
		}
		//属性回显
		var avi = $("#attrValueIds").val();
		if (avi != "") {
			var list = avi.split(",");
			if (list.length > 0) {
				for ( var i = 0; i < list.length; i++) {
					$("#attrValueId" + list[i])
							.attr(
									"src",
									"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
				}
			}
		}
	});

	//对商品进行排序操作
	function orderBy(parma) {
		//原始的排序参数
		var orderBy = '${orderBy}';
		if (parma == orderBy) {
			parma = "normal";
		}
		window.location.href = "${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${productTypeId}&orderBy="
				+ parma
				+ "&specificationValueId=${specificationValueId}&brandId=${brandId}&level=${level}&minPrice=${minPrice}&maxPrice=${maxPrice}&attrValueId=${attrValueId}";
	}
</script>
</head>

<body>
	<input id="brandIds" type="hidden" value="${brandId}" />
	<input type="hidden" id="specificationValueIds"
		value="${specificationValueId}" />
	<input type="hidden" id="attrValueIds" value="${attrValueId}" />
	<input type="hidden" id="attrIds" value="${attrIds }" />
	<%@ include file="../include/header.jsp"%>
	<div id="mbxBox" class="FontSize13 PublicWidth1004 margin-center ">
		<span class="ColorBlack">当前位置:</span>
		<!-- 导航条 -->
		<s:property escape="false" value="prodTypeNames" />
	</div>
	<div class="margin-center PublicWidth1004">
		<div class="ClearBoth" style="float: left;">
			<div class="category_main-w search_page_layout_type1"
				style="float: left; width: 100%; margin-bottom: 30px;">
				<table class="search_filter_table_w search_filter_table"
					style="margin-top: 0px;">
					<s:if test="isTrue!=true&&productTypeList1.size!=0">
						<tr id="ct" style="height: 1px;">
							<th style="width: 100px;">分类</th>
							<td>
								<div id="" class="summary_list split_line5"
									style="height: 40px; line-height: 40px;" flag="c">
									<div class="split_list">
										<ul class="node_list">
											<!-- <a href="javascript:showMoreCategoryInfo();" style="text-decoration: none;">更多>></a> -->
											<s:iterator value="productTypeList1">
												<li class="" style="overflow: visible; width: 115px;">
													<img
													src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/categoryListmark.gif"
													width="5" height="5" /> <a
													title="<s:property value='sortName'/>"
													href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=<s:property value="productTypeId"/>&level=<s:property value="level"/>&orderBy=normal"
													style="text-decoration: none;"> <s:if
															test="sortName.length()>9">
															<s:property value="sortName.substring(0,6)" />...
															</s:if> <s:else>
															<s:property value="sortName" />
														</s:else>
												</a> <span class="cnt">(<s:property value="total" />)
												</span>
												</li>
											</s:iterator>
										</ul>
									</div>
								</div>
							</td>
						</tr>
					</s:if>
					<s:if test="brandList.size!=0">
						<tr>
							<th>品牌</th>
							<td>
								<div id="" class="summary_list split_line5"
									style="height: 40px; line-height: 40px;" flag="c">
									<div class="split_list">
										<ul class="node_list">
											<!-- 										<a href="javascript:showMoreCategoryInfo();" style="text-decoration: none;">更多>></a> -->
											<s:iterator value="brandList" var="brand">
												<li style="overflow: visible; width: 115px;"><a
													title="<s:property value='#brand.brandName'/>"
													href="javascript:void()"
													onclick=" pinpai('<s:property value="brandId"/>','<s:property value="productTypeId"/>','<s:property value="level"/>')"
													style="text-decoration: none;"> <img
														id="isTrue_<s:property value="brandId"/>"
														src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_off.gif"
														width="12" height="12" /> <span> <s:if
																test="#brand.brandName.length()>9">
																<s:property value="#brand.brandName.substring(0,6)" />...
														</s:if> <s:else>
																<s:property value="#brand.brandName" />
															</s:else>
													</span></a><span class="cnt">(<s:property value="total" />)
												</span></li>
											</s:iterator>
										</ul>
									</div>
								</div>
							</td>
						</tr>
					</s:if>
					<s:if test="isTrue==true&&#request.specificationMap.size!=0">
						<tr>
							<th>规格</th>
							<td>
								<div id="" class="summary_list split_line5"
									style="height: auto;" flag="c">
									<div class="split_list">
										<ul class="summary_list split_line5" id="jq_topmenu">
											<s:iterator value="#request.specificationMap" var="s">
												<li style="margin: 10px 10px 10px 0;">
													<ul
														class="filter-attr-expand filter-attr-expand-left jq_hidebox">
														<s:iterator value="#s.value" var="v">
															<li style="overflow: visible; width: 115px;"><a
																title="<s:property value='#v.name'/>"
																href="javascript:;"
																onclick="guige('<s:property value="brandId"/>','<s:property value="productTypeId"/>','<s:property value="#v.specificationValueId"/>','<s:property value="level"/>');">
																	<img
																	id="specificationValueId<s:property value="#v.specificationValueId"/>"
																	class="checkbox" title="1350" alt="1350"
																	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_off.gif"
																	width="12" height="12" /> <s:if
																		test="#v.name.length()>9">
																		<s:property value="#v.name.substring(0,6)" />...
																</s:if> <s:else>
																		<s:property value="#v.name" />
																	</s:else> <span class="cnt">(<s:property value="#v.total" />)
																</span>
															</a></li>
														</s:iterator>
													</ul> <a class="filter-attr filter-attr-off"
													href="javascript:void(0);"> <s:property value="#s.key" />
														<i class="arrow arrow-off"></i>
												</a>
												</li>
											</s:iterator>
										</ul>
									</div>
								</div>
							</td>
						</tr>
					</s:if>
					<s:if test="isTrue==true&&#request.specificationMap.size!=0">
						<tr>
							<th>属性</th>
							<td>
								<div id="" class="summary_list split_line5"
									style="height: auto;" flag="c">
									<div class="split_list">
										<ul class="summary_list split_line5" id="jq_topmenu">
											<s:iterator value="#request.attrListMap" var="s">
												<li style="margin: 10px 10px 10px 0;">
													<ul
														class="filter-attr-expand filter-attr-expand-left jq_hidebox">
														<s:iterator value="#s.attrValueListMap" var="v">
															<li style="overflow: visible; width: 115px;"><a
																title="<s:property value='#v.attrValueName'/>"
																href="javascript:;"
																onclick="productAttrValue('<s:property value="brandId"/>','<s:property value="productTypeId"/>','<s:property value="#v.attrValueId"/>','<s:property value="#s.productAttrId"/>','<s:property value="level"/>');">
																	<img
																	id="attrValueId<s:property value="#v.attrValueId"/>"
																	flag="<s:property value="#v.attrValueId"/>"
																	flag2="attr"
																	name="attrValueRadio<s:property value='#s.productAttrId'/>"
																	class="checkbox"
																	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_off.gif"
																	width="12" height="12" /> <s:if
																		test="#v.attrValueName.length()>9">
																		<s:property value="#v.attrValueName.substring(0,6)" />...
																</s:if> <s:else>
																		<s:property value="#v.attrValueName" />
																	</s:else> <span class="cnt">(<s:property value="#v.count" />)
																</span>
															</a></li>
														</s:iterator>
													</ul> <a class="filter-attr filter-attr-off"
													href="javascript:void(0);"> <s:property value="#s.name" />
														<i class="arrow arrow-off"></i>
												</a>
												</li>
											</s:iterator>
										</ul>
									</div>
								</div>
							</td>
						</tr>
					</s:if>
				</table>
			</div>
			<input id="flag" type="hidden" value="<s:property value='orderBy'/>" />
			<!--right-->
			<div class="list_content">
				<div class="con_left">
					<div class="list_tab">
						<ul class="list_rank">
							<li class="current">默认</li>
							<li><a href="">人气&nbsp;<img
									src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/images/3_29.png" />
							</a></li>
							<li><a href="">评分&nbsp;<img
									src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/images/3_29.png" /></a>
							</li>
						</ul>
					</div>

					<s:iterator value="shops" id="shop" status="stShops" >
						<div class="list_con">
							<div class="list_img">
								<a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value='#shop.shopInfoId'/>"><img
									src="${fileUrlConfig.visitFileUploadRoot}<s:property value="#shop.logoUrl" />"/>
								</a>
							</div>
							<div class="list_text">
								<div class="list_text_name">
									<a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value='#shop.shopInfoId'/>"><s:property value="#shop.shopName"/> </a>
								</div>
								<div class="list_tel">电话：<s:property value="#shop.phone" /></div>
								<div class="list_add">地址：<s:property value="#shop.address" /></div>
								<div class="list_brif">简介：<s:property value="#shop.description" /><a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value='#shop.shopInfoId'/>">[更多]</a>
								</div>
							</div>
							<div class="list_product">
								<div class="product">热销</div>
								<s:iterator value="#shop.productInfoMap" id="product" status="stProducts">
									<s:if test="#stProducts.index<3">
										<div class="pro_li">
											<div class="pro_name">
												<a href="${pageContext.request.contextPath}/productInfo/<s:property value='#product.productId'/>.html"><s:property value="#product.productName" /></a>
											</div>
											<div class="pro_price">￥<s:property value="#product.salesPrice" /></div>
											<div class="pro_bt">
												<a href="">预定</a>
											</div>
										</div>
									</s:if>
								</s:iterator>
							</div>
						</div>
					</s:iterator>

					<div class="list_con" style="display: none">
						<div class="list_img">
							<a href=""><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/meishi1.0_07.jpg" />
							</a>
						</div>
						<div class="list_text">
							<div class="list_text_name">
								<a href="food_details.html">和平宾馆卡本妮法餐厅</a>
							</div>
							<div class="list_sale">
								折扣：<b>7.5&nbsp;折</b>
							</div>
							<div class="list_tel">电话：010-65266957</div>
							<div class="list_add">地址：东城区金鱼胡同3号诺富特和平宾馆内</div>
							<div class="list_brif">
								简介： 法餐馆。内部装修和氛围都与巴黎当地的餐馆“很类似”。菜肴也相当“正宗”，
								“印象最好”的是冬天出品的“奶酪火锅”，有“瑞士火锅、萨瓦火锅和奥瓦涅火锅”三种，所有奶酪都是从法国或瑞士直接“空运”过来的，
								“醇香无比”。老板是法国人...<a href="food_details.html">[更多]</a>
							</div>
						</div>
						<div class="list_product">
							<div class="product">特色菜</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">焦糖布丁</a>
								</div>
								<div class="pro_price">¥38.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">提拉米苏</a>
								</div>
								<div class="pro_price">¥26.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">芝士蛋糕</a>
								</div>
								<div class="pro_price">¥68.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_more">
								<a href="food_details.html">查看更多产品</a>
							</div>
						</div>
					</div>
					<div class="list_con" style="display: none">
						<div class="list_img">
							<a href=""><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/meishi2.0_06.jpg" />
							</a>
						</div>
						<div class="list_text">
							<div class="list_text_name">
								<a href="">深海800米海鲜</a>
							</div>
							<div class="list_sale">
								折扣：<b>6.3&nbsp;折</b>
							</div>
							<div class="list_tel">电话：010-85608177/85608277</div>
							<div class="list_add">地址：东城区王府井大街301号5楼</div>
							<div class="list_brif">
								简介：在咱首都,有一家蓝色的餐厅,你可以吃到着新西兰进口的蓝色鲍鱼, 喝着蓝色的鸡尾酒,听着蓝色多瑙河,享受着您成功的人生!
								蓝色餐厅,汇集了日式生吃料理,法式铁板烧烤, 中华传统小吃,全球精品海鲜!
								蓝色餐厅，倡导健康自由的用餐方式,提供一对一管家式服务...<a href="food_details.html">[更多]</a>
							</div>
						</div>
						<div class="list_product">
							<div class="product">特色菜</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">三文鱼</a>
								</div>
								<div class="pro_price">¥58.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">蒜蓉生蚝</a>
								</div>
								<div class="pro_price">¥198.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">菲力牛排</a>
								</div>
								<div class="pro_price">¥208.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_more">
								<a href="food_details.html">查看更多产品</a>
							</div>
						</div>
					</div>
					<div class="list_con" style="display: none">
						<div class="list_img">
							<a href=""><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/cooper_meishi4.jpg" />
							</a>
						</div>
						<div class="list_text">
							<div class="list_text_name">
								<a href="">西树泡芙（凤凰汇店）</a>
							</div>
							<div class="list_sale">
								折扣：<b>6.3&nbsp;折</b>
							</div>
							<div class="list_tel">电话：010-56223005</div>
							<div class="list_add">地址：朝阳区三元桥凤凰汇购物中心B101-102室</div>
							<div class="list_brif">
								简介： 西树，源于法语Chez Choux，意为“泡芙之家”。专注于追求泡芙的终
								极美味，将经典法式甜品文化与正统日式手作工艺相结合，独家配方密制曲奇泡芙，自主开发“曲奇派式泡芙”，
								精工细作，现烤现卖。将酥松香甜发挥到极致，令口感更富层次...<a href="food_details.html">[更多]</a>
							</div>
						</div>
						<div class="list_product">
							<div class="product">特色菜</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">可可香草泡芙</a>
								</div>
								<div class="pro_price">¥12.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">清新抹茶泡芙</a>
								</div>
								<div class="pro_price">¥12.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_li">
								<div class="pro_name">
									<a href="food_detail.html">原味香草泡芙</a>
								</div>
								<div class="pro_price">¥12.00</div>
								<div class="pro_bt">
									<a href="">预定</a>
								</div>
							</div>
							<div class="pro_more">
								<a href="food_details.html">查看更多产品</a>
							</div>
						</div>
					</div>
					<div class="page">
						<span class="disabled">&lt; Prev</span> <span class="current">1</span>
						<a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">5</a>
						<a href="#">6</a> <a href="#">7</a>... <a href="#">199</a> <a
							href="#">200</a> <a href="#">Next &gt; </a>
					</div>
				</div>
				<div class="con_right">
					<div class="con_ad">最新商家推荐</div>
					<div class="right_ad">
						<div class="right_ad_img">
							<a href=""><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/xiuxian5.jpg" />
							</a>
						</div>
						<div class="right_ad_tx">
							<b><a href="">保龄球馆保龄球馆保龄球馆保龄球馆</a> </b>
							<p>朝阳门</p>
						</div>
					</div>
					<div class="right_ad">
						<div class="right_ad_img">
							<a href=""><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/cooper_rec04.jpg" />
							</a>
						</div>
						<div class="right_ad_tx">
							<b><a href="">冰城串吧</a> </b>
							<p>东单</p>
						</div>
					</div>
					<div class="right_ad">
						<div class="right_ad_img">
							<a href=""><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/cooper_rec03.jpg" />
							</a>
						</div>
						<div class="right_ad_tx">
							<b><a href="">成都芙蓉面馆</a> </b>
							<p>东城</p>
						</div>
					</div>
					<div class="right_ad">
						<div class="right_ad_img">
							<a href=""><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/cooper_rec01.jpg" />
							</a>
						</div>
						<div class="right_ad_tx">
							<b><a href="">龙达园</a> </b>
							<p>北京西站/p>
						</div>
					</div>
				</div>
			</div>

			<!--//Proitem-->
			<!--//right-->
		</div>
	</div>

	<s:iterator value="productToBrandList" var="brand" status="stBrand">
		<div style="width: 100px; height: 10px; border: solid red 1px;">
			<s:property value="#brand" />
		</div>
	</s:iterator>

	<%@ include file="../include/footer.jsp"%>
</body>
</html>
