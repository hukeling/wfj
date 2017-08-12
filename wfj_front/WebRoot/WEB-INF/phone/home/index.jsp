<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html class="index-page">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<meta name="wap-font-scale" content="no" />
<title>PhoneHomeJsp</title>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<link rel="stylesheet" href="../../../css/ratchet.min.css" />
<link rel="stylesheet" href="../../../css/docs.min.css" />
<script type="text/javascript" src="../../../js/jquery.min.js"></script>
<link rel="stylesheet" href="../../../css/framework7.css" />
<link rel="stylesheet" href="../../../style.css" />
<link type="text/css" rel="stylesheet" href="../../../css/swipebox.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index_header.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/base.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/public.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/cart.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/scrollup.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
<!-- 商城底部文章样式 -->
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/css/pubfootcss.css" />
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.tools.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js"></script>
<!-- 新版轮播图js开始 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tt.index_V1.4.min-t.js"></script>
<!-- 新版轮播图js结束 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tab.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollUp.js"></script>
<!-- 轮播图下方的滚动图片 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/scroll.1.3.js"></script>
<style type="text/css">
body {
	font-family: 微软雅黑;
}

.gn li {
	display: inline;
}

.banner_bg {
	width: 790px;
	height: 251px; /*position: relative;*/
	overflow: hidden;
}

.banner_marquee {
	height: 251px;
	overflow: hidden;
	z-index: 0;
	min-width: 990px;
}

.banner_marquee_list {
	margin: 0px auto;
	width: 790px;
	height: 251px;
	overflow: hidden;
}

.banner_marquee_list li {
	width: 790px;
	height: 251px;
	overflow: hidden;
}

.banner_main {
	margin: 0px auto;
	width: 790px;
	height: 251px;
	overflow: hidden;
}

.banner_marquee_list li a.banner_link {
	width: 790px;
	height: 251px;
	margin-left: 190px;
	display: block;
}

.banner_btn { /*left: 50%;*/
	width: 60%;
	height: 10%;
	text-align: right; /*bottom: 12px; */
	overflow: hidden;
	position: absolute;
	z-index: 5;
	top: 300px;
}

.banner_btn span {
	width: 5%;
	height: 5%;
	text-align: center;
	color: rgb(136, 136, 136);
	line-height: 25px;
	font-family: Verdana, Geneva, sans-serif;
	font-size: 35px;
	display: inline-block;
	cursor: pointer;
	_display: inline;
	_zoom: 1;
}

.banner_btn span.on {
	color: rgb(236, 26, 91);
}

/* 滚动图片 */
.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden
}

.clearfix {
	display: inline-table
}

* html .clearfix {
	height: 1%
}

.clearfix {
	display: block
}

*+html .clearfix {
	min-height: 1%
}

.wc960 {
	width: 790px;
	margin-top: 10px;
	border: 1px solid #CCC;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.icon {
	background:
		url(${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/icon.png)
		no-repeat 0 0;
}

.warp-pic-list li {
	float: left;
	display: inline;
}

.warp-pic-list .img_wrap {
	display: block;
	font-size: 0;
	overflow: hidden;
}

.warp-pic-list .text-area {
	background-color: #f2f2f2;
	line-height: 24px;
}
/*全局板块*/
.row .hd {
	background: url(../images/hd-line_01.jpg) no-repeat 0 50px;
	height: 55px;
}

.row .hd .title {
	font: 26px/40px "微软雅黑", "Microsoft YaHei", "黑体", "SimHei";
}
/*全局页签*/
.tab-T-3 {
	width: 66px;
}

.tab-T-3 li {
	width: 12px;
	height: 12px;
	font-size: 0;
	background-color: #dfdfdf;
	float: left;
	margin-left: 10px;
	cursor: pointer;
	display: inline;
}

.tab-T-3 li.cur {
	background-color: #d81c1b;
}
/*热门车型*/
.rowE .warp-pic-list {
	width: 739px;
	height: 110px;
	overflow: hidden; /* margin-left:10px; */
}

.rowE .count li {
	width: 147px;
	height: 110px;
	display: inline-block;
	border-right: 1px solid #CCC;
}

.rowE .count li:last-child {
	border-right: none;
}

.rowE .count li img {
	width: 147px;
	height: 110px;
	display: inline-block;
}

.rowE .count .img_wrap {
	width: 147px;
	height: 110px;
}

.rowE .count li .text-area {
	padding: 10px 0 10px 15px;
}

.rowE .count li .text-area  p {
	line-height: 24px;
	height: 24px;
}

.rowE .count li:hover .text-area,.rowE .count li.hover .text-area {
	background-color: #d81c1b;
	color: #fff;
}

.rowE .count .p-num {
	font-family: "Tahoma";
	font-weight: bold;
}

.rowE .btn {
	display: block;
	height: 110px;
	position: absolute;
	top: 0px;
	width: 26px;
	z-index: 200;
	cursor: pointer;
}

.rowE .prev {
	background-position: 0 -1px;
	left: 0;
}

.rowE .prev:hover {
	background-position: 0 -112px;
}

.rowE .next {
	background-position: 0 -222px;
	right: -8px;
}

.rowE .next:hover {
	background-position: 0 -333px;
}
</style>

<!--延迟加载js-->
<script type="text/javascript">
	var _gaq = _gaq || [];
	_gaq.push([ '_setAccount', 'UA-36050008-1' ]);
	_gaq.push([ '_trackPageview' ]);

	(function() {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://ssl'
				: 'http://www')
				+ '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();
	$(function() {
		$.scrollUp();
		//首页图片延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn"
		});

		$("#count1").dayuwscroll({
			parent_ele : '#wrapBox1',
			list_btn : '#tabT04',
			pre_btn : '#left1',
			next_btn : '#right1',
			path : 'left',
			auto : false,
			time : 3000,
			num : 5,
			gd_num : 5,
			waite_time : 1000
		});
	});
	$(function() {
		var cookie = $.cookie('customerCar');
		//初始化判断cookie中用户名是否存在
		var thshop_customerNameValue = $.cookie('thshop_customerName');
		if (chkcookies("thshop_customerName")) {
			if (thshop_customerNameValue != "") {
				$("#loginShow").css("display", "block");
				$("#noLoginShow").css("display", "none");
				$("#cookie_loginName").html(
						"<font color='red'>" + thshop_customerNameValue
								+ "</font>");
			} else {
				$("#loginShow").css("display", "none");
				$("#noLoginShow").css("display", "block");
			}
		} else {
			$("#loginShow").css("display", "none");
			$("#noLoginShow").css("display", "block");
		}
	});
	//检测cookie
	function chkcookies(NameOfCookie) {
		var c = document.cookie.indexOf(NameOfCookie + "=");
		if (c != -1) {
			return true;
		}
		return false;
	}
	function verdictgwc() {
		location.href = "${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
	}
</script>

<!--顶部js-->
<script type="text/javascript">
	//点击搜索查询
	searchImg = function() {
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
</script>

<!--轮换图 js-->
<script type="text/javascript">
	$(function() {
		var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
		var len = $("#focus ul li").length; //获取焦点图个数
		var index = 0;
		var picTimer;

		//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
		var btn = "<div class='btnBg'></div><div class='btn'>";
		for ( var i = 0; i < len; i++) {
			btn += "<span></span>";
		}
		btn += "</div><div class='preNext pre' style='display:none;'></div><div class='preNext next' style='display:none;'></div>";
		$("#focus").append(btn);
		$("#focus .btnBg").css("opacity", 0.5);

		//为小按钮添加鼠标滑入事件，以显示相应的内容
		$("#focus .btn span").css("opacity", 0.4).mouseenter(function() {
			index = $("#focus .btn span").index(this);
			showPics(index);
		}).eq(0).trigger("mouseenter");

		//上一页、下一页按钮透明度处理
		$("#focus .preNext").css("opacity", 0.2).hover(function() {
			$(this).stop(true, false).animate({
				"opacity" : "0.5"
			}, 300);
		}, function() {
			$(this).stop(true, false).animate({
				"opacity" : "0.2"
			}, 300);
		});

		//上一页按钮
		$("#focus .pre").click(function() {
			index -= 1;
			if (index == -1) {
				index = len - 1;
			}
			showPics(index);
		});

		//下一页按钮
		$("#focus .next").click(function() {
			index += 1;
			if (index == len) {
				index = 0;
			}
			showPics(index);
		});

		//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
		$("#focus ul").css("width", sWidth * (len));

		//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
		$("#focus").hover(function() {
			clearInterval(picTimer);
		}, function() {
			picTimer = setInterval(function() {
				showPics(index);
				index++;
				if (index == len) {
					index = 0;
				}
			}, 4000); //此4000代表自动播放的间隔，单位：毫秒
		}).trigger("mouseleave");

		//显示图片函数，根据接收的index值显示相应的内容
		function showPics(index) { //普通切换
			var nowLeft = -index * sWidth; //根据index值计算ul元素的left值
			$("#focus ul").stop(true, false).animate({
				"left" : nowLeft
			}, 300); //通过animate()调整ul元素滚动到计算出的position
			// 		$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
			$("#focus .btn span").stop(true, false).animate({
				"opacity" : "0.4"
			}, 300).eq(index).stop(true, false).animate({
				"opacity" : "1"
			}, 300); //为当前的按钮切换到选中的效果
		}

		//鼠标在图片上
		$("#focus").mouseover(function() {
			$("#focus .pre").css("display", "block");
			$("#focus .next").css("display", "block");
		});
		//鼠标移走
		$("#focus").mouseout(function() {
			$("#focus .pre").css("display", "none");
			$("#focus .next").css("display", "none");
		});

	});
</script>

<!--热销榜单 js-->
<script type="text/javascript">
	//切换展示
	function openThis(obj) {
		var liSize = obj.parentElement.children.length;
		var elementList = obj.parentElement.children;
		for ( var i = 0; i < liSize; i++) {
			if (elementList[i] == obj) {
				$(elementList[i]).removeClass("onee");
				$(elementList[i]).addClass("on");
				//切换的同时加载图片
				$(elementList[i]).find('img').each(
						function() {
							var original = $(this).attr("data-original");
							if (original) {
								$(this).attr('src', original).removeAttr(
										'data-original');
							}
						});
			} else {
				$(elementList[i]).removeClass("on");
				$(elementList[i]).addClass("onee");
			}
		}
	}
	//转向一级分类
	function goFirstType(firstTypeId, level) {
		var winopen = window
				.open("${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="
						+ firstTypeId + "&level=" + level + "&orderBy=normal");
		/*
		if( winopen==null){//自动提交的链接
		    document.write('<a href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&orderBy=normal" target="_blank" id="autopop" style="display:none"></a>');
		    document.getElementById('autopop').click(); 
		}
		 */
	}
	//品牌链接function 
	function brandLink(brandName) {//参数为品牌名称
		$("#searchContent").val(brandName);
		searchImg();
	}

	//头部购物车-组品html代码
	function headShoppingCartHtml() {
		//请求购物车数据
		$
				.post(
						"${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCartAjax.action",
						function(data) {
							var listMap = data;
							var html = "";
							if (listMap != null && listMap.length > 0) {
								html += "<div id=\"divMini\" class=\"cart-bar-expand\">";
								for ( var i = 0; i < listMap.length; i++) {
									var productId = listMap[i].productId;
									var smallImgUrl = listMap[i].smallImgUrl;
									var productName = listMap[i].productName;
									var buyPrice = listMap[i].salesPrice
											.toFixed(2);
									;
									var quantity = listMap[i].quantity;
									var webroot = '${pageContext.request.contextPath}';
									var webroot = '${pageContext.request.contextPath}';
									html += "<dl> <dd class=\"image\"><a href=\"${pageContext.request.contextPath}/productInfo/"+productId+".html\" target=\"_blank\">";
									html += "<img onerror=\"this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'\"  src=\"${application.fileUrlConfig.visitFileUploadRoot}"+smallImgUrl+"\" style=\"height:35px; width:35px;\" />";
									html += " </a></dd><dt><a muse_scanned=\"true\" target=\"_blank\" href=\"${pageContext.request.contextPath}/productInfo/"+productId+".html\">"
											+ productName + "</a>";
									html += " </dt><dd class=\"operation\"><a onclick=\"deleteCart('"
											+ productId
											+ "');\" href=\"javascript:;\" class=\"secondary-link\" style=\"color: #1478da;\">删除</a></dd>";
									html += "<dd>";
									html += "<input type=\"hidden\" id=\"salesPrice2"+productId+"\" value=\""+buyPrice+"\"/>";
									html += "<input id=\"amount_"+productId+"\" name=\"amountItme2\" type=\"hidden\" value=\""+quantity+"\" />";
									html += "<span style=\"font-family:微软雅黑;color:#ff5500;\">￥</span><em>"
											+ buyPrice
											+ "</em>x<i>"
											+ quantity
											+ "</i>";
									html += " </dd> </dl>";
								}
								html += "<div class=\"cart-bar-expand-total\">";
								html += " <p>共<i id=\"minicartcnt2\">"
										+ listMap.length
										+ "</i>件商品</p><p>合计(未含运费):<em id=\"minicarttotalprice\"><span style=\"font-family:微软雅黑;color:#ff5500;\">￥</span><span style=\"font-family:微软雅黑;color:#ff5500;\" id=\"Subtotal\"></span></em></p>";
								html += " <input value=\"去结算\" id=\"btnGoPay\" href=\"javascript:;\" onclick=\"verdictgwc();\" class=\"btn btn-s\" type=\"button\" />";
								html += "</div></div>";
							} else {
								html += "<div id=\"divMiniNo\" class=\"cart-bar-expand\">";
								html += " <div class=\"cart-bar-empty\">您还未添加任何商品，欢迎选购！</div>";
								html += " </div>";
							}
							$("#divShopCart").html(html);
							$("#count").html(listMap.length);
							amountMoney();
						}, 'json');
	}

	//删除
	function deleteCart(id) {
		var ids = "";
		if (id == "all") {//删除选中的商品
			var carts = document.getElementsByName("cartInfo");
			for ( var i = 0; i < carts.length; i++) {
				if (carts[i].value != "") {
					if (carts[i].checked) {//如果选中
						if (ids != "") {
							ids = ids + "," + carts[i].value;
						} else {
							ids = carts[i].value;
						}
					}
				}
			}
		} else {
			ids = id;
		}
		var url = "${pageContext.request.contextPath}/loginCustomer/checkLogin.action";
		$
				.post(
						url,
						{},
						function(data) {//验证是否登录
							if (data.bool == true) {//已登录操作
								if (ids != "") {//有选中的商品时
									lconfirm(
											"提醒",
											"确定删除!",
											function(o) {
												var url = "${pageContext.request.contextPath}/shopFront/shoppingCar/deleteShoppingCart.action";
												$.post(url, {
													ids : ids
												}, function(data) {
													if (data) {
														window.location
																.reload(); //删除后重新加载列表
													}
												}, "JSON");
											});
								} else {
									lalert("提醒", "请选择商品！");
								}
							} else {
								//没有登录时，删除cookie中的数据
								if (chkcookies('customerCar')) {
									var carCookie = eval($
											.cookie('customerCar'));
									if (id == "all") {//点击清除选中商品时操作
										lconfirm(
												"提醒",
												"确定删除!",
												function(o) {
													var selectNum = 0;//当前选中商品个数
													$(".checkboxClass")
															.each(
																	function() {
																		if (this.checked) {
																			selectNum++;
																		}
																	});
													if (selectNum == carCookie.length) {//如果当前选中商品个数等于cookie中的个数
														$
																.cookie(
																		"customerCar",
																		null,
																		{
																			expires : -1,
																			path : "/"
																		});//全部清除
													} else {//如果当前选中商品个数不等于cookie个数
														var value = "";
														$(".checkboxClass")
																.each(
																		function() {
																			for ( var i = 0; i < carCookie.length; i++) {
																				if (!this.checked) {//如果当前商品没有选中，说明不删除此商品，cookie中保存
																					if (this.value == carCookie[i].productId) {
																						value += ",{'productId':'"
																								+ carCookie[i].productId
																								+ "','num':'"
																								+ carCookie[i].num
																								+ "','shopInfoId':'"
																								+ carCookie[i].shopInfoId
																								+ "','sku':'"
																								+ carCookie[i].sku
																								+ "'}";//以json格式存放，方便维护和取
																					}
																				}
																			}
																		});
														value = value
																.substring(
																		1,
																		value.length);
														value = "[" + value
																+ "]";
														$
																.cookie(
																		'customerCar',
																		value,
																		{
																			expires : 7,
																			path : "/"
																		}); //设置带时间的cookie 7天，加path属性时，只能其他页面使用，且其他页面取值条件为当前页面的上级路径包含path一致
													}
													window.location.reload(); //删除后重新加载列表
												});
									} else {//单个商品删除操作按钮
										lconfirm(
												"提醒",
												"确定删除!",
												function(o) {
													if (carCookie.length == 1) {//当前cookie中只有一个商品，直接删除cookie中的商品
														$
																.cookie(
																		"customerCar",
																		"",
																		{
																			expires : -1,
																			path : "/"
																		});
													} else {
														var value = "";
														for ( var i = 0; i < carCookie.length; i++) {
															if (id != carCookie[i].productId) {//判断当前操作商品是否是指定删除的商品
																value += ",{'productId':'"
																		+ carCookie[i].productId
																		+ "','num':'"
																		+ carCookie[i].num
																		+ "','shopInfoId':'"
																		+ carCookie[i].shopInfoId
																		+ "','sku':'"
																		+ carCookie[i].sku
																		+ "'}";//以json格式存放，方便维护和取
															}
														}
														value = value
																.substring(
																		1,
																		value.length);
														value = "[" + value
																+ "]";
														$
																.cookie(
																		'customerCar',
																		value,
																		{
																			expires : 7,
																			path : "/"
																		}); //设置带时间的cookie 7天，加path属性时，只能其他页面使用，且其他页面取值条件为当前页面的上级路径包含path一致
													}
													window.location.reload(); //删除后重新加载列表
												});
									}
								}
							}
						}, "JSON");
	}

	//快速下单 文字颜色控制js
	$(function() {
		$("input.useFunction").live("focus", function() {
			$(this).attr("style", "color:#555;");
		});
		$(".useFunction").live("blur", function() {
			if ($(this).val() == "") {
				$(this).attr("style", "color: rgb(169, 169, 169);");
			}
		});
	});
</script>
</head>

<body ontouchstart="">
	<!--topBottom-->
	<header class="bar bar-nav">
	<div class="fl searchYin">
		<form
			action="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action"
			method="get" id="searchForm">
			<input type="hidden" name="pager.keyword" id="searchContentValue" />
			<div class="searchYinT">
				<input type="text" onfocus="focusAndBlur('focus')"
					onblur="focusAndBlur('blur')"
					style="color: #727171; margin: 1px 0 0 60px; float: left; display: inline-block;"
					value="" placeholder="请输入品牌，订货号，商品名称"
					value="<s:property value='keyword'/>" autocomplete="off"
					id="searchContent" class="searchText" />
			</div>
		</form>
	</div>
	</header>
	<div>
		<div class="fl" style="margin-left: 10px; display: inline;">
			<!-- 改版轮播图开始 -->
			<div id="slidepicturediv">
				<ul class="banner_marquee_list" id="slidepicture">
					<s:if test="#request.homeLBTList.size>0">
						<s:iterator value="#request.homeLBTList" var="h">
							<li
								style='background: url("<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#h.broadcastingIamgeUrl'/>") no-repeat 50% 0px;'>
								<div class="banner_main">
									<a title="" class="banner_link" id="1350_62128"
										href="<s:property value='#h.interlinkage'/>" target="_blank"></a>
								</div>
							</li>
						</s:iterator>
					</s:if>
				</ul>
				<div class="banner_btn" id="slidebutton" align="center"></div>
			</div>
		</div>
	</div>
	<div>
		<ul class="fix" style="margin-top: 23%;">
			<li style="margin-left: 20%; display: inline;"><a
				href="javascript:;"> <img src="images/143262193526726791.jpg" />
					会员中心
			</a></li>
			<li style="display: inline;"><a href="javascript:;"> <img
					src="images/143262190460206422.jpg" /> 推广链接
			</a></li>
			<li style="display: inline;"><a href="javascript:;"><img
					src="images/143262692801385199.jpg" /> 我的订单</a></li>
			<li style="display: inline;"><a href="javascript:;"> <img
					src="images/143385213411317884.jpg" /> 财务明细
			</a></li>
		</ul>
		<ul class="fix" style="margin-top: 3%;">
			<li style="margin-left: 20%; display: inline;"><a
				href="javascript:;"> <img src="images/143262193526726791.jpg" />
					会员中心
			</a></li>
			<li style="display: inline;"><a href="javascript:;"> <img
					src="images/143262190460206422.jpg" /> 推广链接
			</a></li>
			<li style="display: inline;"><a href="javascript:;"><img
					src="images/143262692801385199.jpg" /> 我的订单</a></li>
			<li style="display: inline;"><a href="javascript:;"> <img
					src="images/143385213411317884.jpg" /> 财务明细
			</a></li>
		</ul>
	</div>
	<div>
		<hr style="border-color: gray" />
		<div>
			<font color="red" size="5">今日头条:</font>
		</div>
		<hr style="border-color: gray" />
	</div>
	<%-- <!-- 将底部通用部分引入首页 -->
	<%@ include file="../../shop/include/footer.jsp"%> --%>
</body>
</html>