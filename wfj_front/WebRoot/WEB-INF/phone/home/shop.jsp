<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单商品清单</title>
<style>
body {
	background-color: #EEEEEE;
	font-size: 16px;
	font-family: Arial, Helvetica, sans-serif, "黑体";
	padding: 0px;
	margin: 0px;
}

.box {
	background-color: #FFFFFF;
	border-bottom: 1px solid #CDCDCD;
	float: left;
	width: 100%;
	margin-left: 2%;
	margin-right: 2%;
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
	text-align: left;
}
</style>
<script type="text/javascript">
	$().ready(function() {
		//Lazy Load 延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn",
			threshold : 500
		});
	});
	$(function() {
		var whichOrderBy = $("#orderBy").val();
		if (whichOrderBy != null && whichOrderBy != "") {
			$("#" + whichOrderBy).css("color", "red");
		}
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
	//delect所有查询条件(不包含排序)
	function clear_all_query_conditions() {
		$("#select_screen").css("display", "none");
		$("[type='checkbox']").removeAttr("checked");//取消选中
		window.location = "${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId="
				+ $
		{
			shopInfoId
		}
		+"&orderBy=normal";
	}
	//左侧复选框操作
	function checkBoxChange() {
		//获得排序参数
		var f = $("#flag").val();
		//查询品牌参数
		var brandValue = [];
		$("input[name='品牌Checkbox']:checked").each(function() {
			brandValue.push($(this).val());
		});
		window.location.href = "${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?brandParams="
				+ brandValue + "&shopInfoId=" + $
		{
			shopInfoId
		}
		+"&orderBy=" + f;
	}
	//单个条件的删除
	function clearOne(params) {//params为删除的参数 (color,brand,price)等
		//获得排序参数
		var f = $("#flag").val();
		$("#" + params).css("display", "none");
		$("input[name='" + params + "Checkbox']:checked").removeAttr("checked");
		if ($("input[type='checkbox']:checked").length == 0) {//如果没有选中的checkbox 则清除所有左侧查询条件
			$("#select_screen").css("display", "none");
			window.location = "${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId="
					+ $
			{
				shopInfoId
			}
			+"&orderBy=" + f;
		} else {
			//查询品牌参数
			var brandValue = [];
			$("input[name='品牌Checkbox']:checked").each(function() {
				brandValue.push($(this).val());
			});
			window.location.href = "${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?brandParams="
					+ brandValue + "&shopInfoId=" + $
			{
				shopInfoId
			}
			+"&orderBy=" + f;
		}

	}

	//品牌链接function 
	function brandLink(brandName) {//参数为品牌名称
		$("#searchContent").val(brandName);
		//searchImg();
		$("#searchForm").submit();
	}

	//点击搜索查询
	function searchImg() {
		var keyWord = $.trim($("#searchContent").val());
		if (keyWord != null && keyWord != "") {
			var newKey = escape(keyWord);
			$("#searchContentValue").val(newKey);
			$("#searchForm").submit();
		}
	};
	//失去焦点与获得焦点事件
	focusAndBlur = function(params) {//params用于区别失去焦点 还是  获得焦点
		var kw = $("#searchContent").val();//搜索框中的关键字
		if (params == "focus") {
			if (kw == "请输入品牌，订货号，商品名称") {
				$("#searchContent").val("");
			}
		} else {
			if (kw == "") {
				$("#searchContent").val("请输入品牌，订货号，商品名称");
			}
		}
	};
	(function(a) {
		a.fn.hoverClass = function(b) {
			var a = this;
			a.each(function(c) {
				a.eq(c).hover(function() {
					$(this).addClass(b);
				}, function() {
					$(this).removeClass(b);
				});
			});
			return a;
		};
	})(jQuery);

	$(function() {
		$("#navbox").hoverClass("current");
		$("#navbox2").hoverClass("current");
		$("#navbox3").hoverClass("current");
		// 回车处理
		$("#searchContent").keydown(
				function() {
					var evt = event ? event : (window.event ? window.event
							: null);
					if (evt.keyCode == 13) {
						var keyWord = $.trim($("#searchContent").val());
						if (keyWord != null && keyWord != ""
								&& keyWord != "请输入品牌，订货号，商品名称") {
							var newKey = escape(keyWord);
							$("#searchContentValue").val(newKey);
							$("#searchForm").submit();
						} else {
							$("#searchContent").val("请输入品牌，订货号，商品名称");
							//$(this).focus();
						}
						;
					}
					;
				});
	});
	//将搜索框中的内容置空
	function setSearchNull() {
		var keyWord = $.trim($("#searchContent").val());
		if (keyWord == "请输入品牌，订货号，商品名称") {
			$("#searchContent").val("");
		}
		;
	}
	//将搜索框中的内容置空
	function reBack() {
		var keyWord = $.trim($("#searchContent").val());
		if (keyWord == null || keyWord == "") {
			$("#searchContent").val("请输入品牌，订货号，商品名称");
		}
		;
	}
	//搜索商品
	function search() {
		var keyWord = $.trim($("#searchContent").val());
		if (keyWord != null && keyWord != "" && keyWord != "请输入品牌，订货号，商品名称") {
			var newKey = escape(keyWord);
			$("#searchContentValue").val(newKey);
			$("#searchForm").submit();
		} else {
			$("#searchContent").val("请输入品牌，订货号，商品名称");
			//$("#searchContent").focus();
		}
		;
	}

	//收藏店铺
	function favoriteShops() {
		var type = "<s:property value='#session.customer.type' />";
		if (type == 2) {
			lalert("提醒", "供应商不能收藏店铺！");
			return;
		}
		window.location.href = "${pageContext.request.contextPath}/front/customer/favoriteShops/favoriteShops.action?shopInfoId="
				+ '${shopInfo.shopInfoId}';
	}
	//VIP金卡会员申请
	function memberApply() {
		var loginName = "<s:property value='#session.customer.loginName' />";
		if (loginName != null && loginName != "") {
			var type = "<s:property value='#session.customer.type' />";
			if (type == 2) {
				lalert("提醒", "供应商不能申请！");
			} else {
				$
						.ajax({
							url : "${pageContext.request.contextPath}/front/memberShip/checkMember.action",
							dataType : "json",
							type : "post",
							data : {
								shopInfoId : '${shopInfo.shopInfoId}'
							},
							success : function(data) {
								if (data.isExsit == true) {
									lalert("提醒", "您已申请该店铺会员，不可重复申请！");
								} else {
									$('#edit-payway-overlay').overlay({
										mask : {
											color : '#ebecff',
											loadSpeed : 200,
											opacity : 0.4
										},
										closeOnClick : false
									});
									$("#edit-payway-overlay").overlay().load();//加载浮层
									$("#apply")
											.click(
													function() {
														$("#apply").attr(
																"disabled",
																true);
														location.href = "${pageContext.request.contextPath}/front/memberShip/memberApply.action?shopInfoId="
																+ $
														{
															shopInfo.shopInfoId
														}
														;
													});
								}
							}
						});
			}
		} else {
			lalert("提醒", "请登录，才可申请！");
		}
	}
</script>
</head>

<body>
	<div id="content"
		style="height: 150px;width: 100%;background-image:${#application.fileUrlConfig.visitFileUploadRoot+#shopInfo.bannerUrl};">
		
	</div>
	<div id="content">
		<s:iterator value="#request.productInfoList" var="productInfo" >
			<div class="box">
				<div class="item"
					style="float: left; width: 40%; background-color: #FFFFF0">
					<img class="proImg" width="80%" height="25%"
						src="<s:property value='#application.fileUrlConfig.visitFileUploadRoot+#productInfo.logoImg'/>" />
				</div>
				<div class="item" style="float: right; width: 60%;">
					<div id="proName">
						<s:property value="#productInfo.productFullName" />
					</div>
					<div class="money" id="proPrice">
						￥
						<s:property value="#productInfo.salesPrice" />
						元&nbsp;起
					</div>
				</div>
			</div>
		</s:iterator>
	</div>
</body>
</html>