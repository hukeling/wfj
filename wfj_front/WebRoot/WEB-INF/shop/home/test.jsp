<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>智慧王府井</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="css/mui.min.css">
<link rel="stylesheet" href="css/echo.css">
<style type="text/css">

.iconFont {
	font-size: 12px;
	color: #333;
	font-family: "华文黑体";
}

</style>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.tools.min.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script src="js/installapp.js@v=20140319100001"
	type="text/javascript"></script>
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
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollUp.js"></script>
<!-- 轮播图下方的滚动图片 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/scroll.1.3.js"></script>
<script type="text/javascript">
	var _winLocation = window.location.href;//获得当前页面的路径，根据路径规则进行逐页替换
	var _isWebKit = '__proto__' in {};//是否是webkit内核
	if (_isWebKit) {//如果是webkit内核，则分模块使用zepto
		//要使用zeptojs的路径列表，可以做为分模块替换的开关
		var _locationList = new Array();
		//活动模块
		_locationList.push('activity/proActList');
		_locationList.push('activity/proActWareList');
		_locationList.push('activity/list');
		//商品分类模块
		_locationList.push('category/');
		//京东快讯模块
		_locationList.push('newslist.html');
		_locationList.push('newslist/');
		_locationList.push('news/');
		//机票模块
		_locationList.push('airline/');
		//酒店模块
		_locationList.push('hotel/');
		//团购模块
		_locationList.push('tuan/');
		//首页相关
		_locationList.push('hotbrand.html');//品牌馆
		//商品筛选相关
		_locationList.push('ware/expandSort.action');
		_locationList.push('ware/categoryFilter.action');
		_locationList.push('ware/search.action');
		_locationList.push('products/');

		_locationList.push('notice/');//通告模块
		_locationList.push('coupons/');//随地惠模块
		_locationList.push('chongzhi/');//手机充值模块
		_locationList.push('comment/');//手机推荐模块
		_locationList.push('pay/');//支付
		_locationList.push('order/');//订单
		var _needReplace = false;
		//如果当前路径符合要使用的路径规则，则进行替换
		for ( var i = 0; i < _locationList.length; i++) {
			if (_winLocation.indexOf(_locationList[i]) != -1) {
				_needReplace = true;
				break;
			}
		}
		//如果是首页的话，则使用zepto
		var _tmp = _winLocation.replace(/(^http:\/\/)|(\/*$)/g, '');
		if (_tmp.indexOf('/') < 0
				|| (_tmp.split('/').length <= 2 && _tmp.indexOf('/index') >= 0)) {
			_needReplace = true;
		}
		//如果是商品详情页的话，则使用zepto
		var _dlocationList = new Array();
		_dlocationList.push(/\/product\/([0-9]+)\.html/);
		_dlocationList.push(/\/orderComment\/([0-9]+)\.html/);
		_dlocationList.push(/\/consultations\/([0-9]+)\.html/);
		_dlocationList.push(/\/consultations\/([0-9]+)-([0-9]+)\.html/);
		_dlocationList.push(/\/comments\/([0-9]+)\.html/);
		for ( var i = 0, len = _dlocationList.length; i < len; i++) {
			if (_dlocationList[i].test(_winLocation)) {
				_needReplace = true;
				break;
			}
		}
		if (_needReplace) {
			document.write('<script src="js/zepto.min.js"><\/script>');
			document
					.write('<script type="text/javascript">window.jQuery=window.Zepto;<\/script>');
		} else {
			document.write('<script src="js/jquery-1.8.3.min.js"><\/script>');
		}
	} else {//如果是非webkit内核直接使用jquery
		document.write('<script src="js/jquery-1.8.3.min.js"><\/script>');
	}
</script>

<!--延迟加载js-->
<script type="text/javascript">
	$(function() {
		$.scrollUp();
		//首页图片延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn",
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
<!--左侧分类 js-->

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
	function goFirstType(firstTypeId) {
		var winopen = window
				.open("${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="
						+ firstTypeId + "&orderBy=normal");
		if (winopen == null) {//自动提交的链接
			document
					.write('<a href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&orderBy=normal" target="_blank" id="autopop" style="display:none"></a>');
			document.getElementById('autopop').click();
		}
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
				if(index == len) {
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

</head>
<body id="body" style="background: rgb(255, 255, 255);" >
	<header></header>
	<!-- 轮播图开始 -->
	<div class="banner scroll-wrapper" id="idContainer2"
		ontouchstart="touchStart(event)" ontouchmove="touchMove(event);"
		ontouchend="touchEnd(event);"
		style="overflow: hidden; height: 100%; margin-bottom: -12px">
		<ul class="scroller"
			style="position: relative; left: -1000px; height: 100%; width: 500%"
			id="idSlider2">
			<s:if test="#request.homeLBTList.size>0">
				<s:iterator value="#request.homeLBTList" var="li">
					<li style="width: 100%;"><a
						href="<s:property value="#li.interlinkage"/>"><img
							style="height: 110%;"
							src="${application.fileUrlConfig.visitFileUploadRoot}<s:property value='#li.broadcastingIamgeUrl'/>"></img></a>
					</li>
				</s:iterator>
			</s:if>
		</ul>
		<ul class="new-banner-num " style="display: none;" id="idNum">
		</ul>
	</div>
	<input type="hidden" value="5" id="activity" />
	<input type="hidden" value="15" id="crazy" />
	<!-- 轮播图结束,以下是轮播图js -->
	<script>
		//活动轮播图
		var picCount = $("#activity").val();
		$(".pic-num1").css("width", (picCount * 30) + "px");
		var forEach = function(array, callback) {
			for ( var i = 0, len = array.length; i < len; i++) {
				callback.call(this, array[i], i);
			}
		}
		var st = createPicMove("idContainer2", "idSlider2", picCount); //图片数量更改后需更改此数值
		var nums = [];
		//插入数字
		for ( var i = 0, n = st._count - 1; i <= n; i++) {
			var li = document.createElement("li");
			nums[i] = document.getElementById("idNum").appendChild(li);
		}
		//设置按钮样式
		st.onStart = function() {
			//forEach(nums, function(o, i){ o.className = ""})
			forEach(nums, function(o, i) {
				o.className = st.Index == i ? "new-tbl-cell on"
						: "new-tbl-cell";
			})
		}
		// 重新设置浮动
		$("#idSlider2").css("position", "relative");

		var _initX = 0;
		var _finishX = 0;
		var _startX = 0;
		var _startY = 0;
		function touchStart(event) {
			_startX = event.touches[0].clientX;
			_startY = event.touches[0].clientY;
			_initX = _startX;
		}
		function touchMove(event) {
			var touches = event.touches;
			var _endX = event.touches[0].clientX;
			var _endY = event.touches[0].clientY;
			if (Math.abs(_endY - _startY) > Math.abs(_endX - _startX)) {
				return;
			}
			event.preventDefault();
			_finishX = _endX;
			var _absX = Math.abs(_endX - _startX);
			var lastX = $('#idSlider2').css('left').replace('px', '');
			if (_startX > _endX) {
				st.Stop();
				$('#idSlider2').css('left', (parseInt(lastX) - _absX) + 'px');
			} else {
				st.Stop();
				$('#idSlider2').css('left', (parseInt(lastX) + _absX) + 'px');
			}
			_startX = _endX;
		}
		//触屏  离开屏幕事件
		function touchEnd(event) {
			if (_finishX == 0) {
				return;
			}
			if (_initX > _finishX) {
				bindEvent(_initX, _finishX);
			} else if (_initX < _finishX) {
				bindEvent(_initX, _finishX);
			}
			_initX = 0;
			_finishX = 0;
		}

		/**
		 *  绑定触屏触发事件
		 * @param start
		 * @param end
		 */
		function bindEvent(start, end) {
			if (start >= end) {
				st.Next();
			} else {
				st.Previous();
			}
		}
		st.Run();
		var resetScrollEle = function() {
			$("#shelper").css("width", $("#newkeyword").width() + "px");
			var slider2Li = $("#idSlider2 li");
			slider2Li.css("width", $(".scroll-wrapper").width() + "px");
			$("#shelper").css("width", $("#newkeyword").width() + "px");
		}

		window.addEventListener("resize", function() {
			st.Change = st._slider.offsetWidth / st._count;
			st.next();
			resetScrollEle();
		});
		window.addEventListener("orientationchange", function() {
			st.Change = st._slider.offsetWidth / st._count;
			st.Next();
			resetScrollEle();
		})
		resetScrollEle();

		$(function() {
			//根据cookie判断用户是否已经主动取消，主动取消了则不再出现提醒
			var cookieName = "ucTip";
			var cancel = false;
			var start = document.cookie.indexOf(cookieName + "=");
			if (start != -1) {
				start = start + cookieName.length + 1;
				var end = document.cookie.indexOf(";", start);
				if (end == -1) {
					end = document.cookie.length;
				}
				var ucTip = document.cookie.substring(start, end);
				if (ucTip == '1') {
					cancel = true;
				}
			}
			if (!cancel) {
				//外层div元素
				var ucElement = $('<div>').attr('id', 'ucTip').css({
					"position" : "fixed",
					"bottom" : "30%",
					"z-index" : "10001",
					"border" : "0",
					"display" : "none"
				});
				//frame元素，目前UC只支持该接入类型
				var ucFrame = $('<iframe>')
						.css({
							"height" : "160px",
							"width" : "320px",
							"border" : "0px"
						})
						.attr(
								'src',
								'../app.uc.cn/appstore/AppCenter/frame@uc_param_str=nieidnutssvebipfcp&api_ver=2.0&id=445&bg=#ffffff');
				ucElement.append(ucFrame);
				//将元素添加到页面
				$('body').append(ucElement);
				//回调方法
				window.addEventListener("message", function(event) {
					var dt = event.data.type;
					var dm = event.data.message;
					//判断出现的情况
					if (dm != 'not android uc' && dm != 'not exist'
							&& dm != 'browser version error'
							&& dm != 'already exist' && dm != undefined) {
						$('#ucTip').show();
					}
					//判断隐藏的情况
					if (dm == 'success' || dm == 'cancle' || dm == 'close') {
						$('#ucTip').hide();
						//如果用户主动取消，则记录到cookie，30天内不再提醒
						if (dm == 'cancle') {
							var expires = new Date();
							expires.setTime(expires.getTime() + 30 * 24 * 60
									* 60 * 1000);
							document.cookie = cookieName + '=1;expires='
									+ expires.toGMTString()
									+ ';path=/;domain=.jd.com';
							document.cookie = cookieName + '=1;expires='
									+ expires.toGMTString()
									+ ';path=/;domain=.360buy.com';
						}
					}
				}, false);
			}
		});
		function clickResponse(obj) {
			$('[res]').removeClass('on');
			$(obj).addClass('on');
			setTimeout(function() {
				$(obj).removeClass('on');
			}, 700);
		}
		$("#newkeyword").focus(function() {
			setTimeout(function() {
				window.scrollTo(0, $("#newkeyword").offset().top - 60);
			}, 10);
		});
		$(document).ready(function() {
			$("#categoryMenu li").addClass("route");
		})
		$(document).ready(function() {
			var script = document.createElement("script");
			script.src = "js/2013/installapp.js@v=20140319100001";
			script.type = "text/javascript";
			document.getElementsByTagName("head")[0].appendChild(script);
		})
	</script>
	<!-- 轮播图js结束 -->

	<!--图标导航-->
	<div class="mui-table-view mui-grid-view">

		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px; margin-left: 12px;">
			<div class="iconFont" style="border: 0;">
				<a
					href="${pageContext.request.contextPath}/phone/categorylist.action?productTypeId=26&level=1&orderBy=normal">
					<img class="mui-media-object" style=" border-radius:50%" src="img/icon_meishi.png" /> 美食
				</a>
			</div>
		</div>
		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px;">
			<div class="iconFont">
				<a
					href="${pageContext.request.contextPath}/phone/categorylist.action?productTypeId=5&level=1&orderBy=normal">
					<img class="mui-media-object" style=" border-radius:50%"  src="img/icon_xiuxian.png" /> 休闲
				</a>
			</div>
		</div>
		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px;">
			<div class="iconFont">
				<a
					href="${pageContext.request.contextPath}/phone/categorylist.action?productTypeId=8&level=1&orderBy=normal">
					<img class="mui-media-object" style=" border-radius:50%"  src="img/icon_lvsu.png" /> 旅宿
				</a>
			</div>
		</div>
		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px;">
			<div class="iconFont">
				<a
					href="${pageContext.request.contextPath}/phone/categorylist.action?productTypeId=25&level=1&orderBy=normal">
					<img class="mui-media-object" style=" border-radius:50%"  src="img/icon_liren.png" /> 丽人
				</a>
			</div>
		</div>
		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px; margin-left: 12px">
			<div class="iconFont">
				<a
					href="${pageContext.request.contextPath}/phone/categorylist.action?productTypeId=2&level=1&orderBy=normal">
					<img class="mui-media-object" style=" border-radius:50%"  src="img/icon_jingpin.png" /> 精品
				</a>
			</div>
		</div>
		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px;">
			<img class="mui-media-object" style=" border-radius:50%"  src="img/icon_tuangou.png" />
			<div class="iconFont">团购</div>
		</div>
		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px;">
			<img class="mui-media-object"  style=" border-radius:50%" src="img/icon_dianpu.png" />
			<div class="iconFont">店铺</div>
		</div>
		<div
			class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center"
			style="padding: 4px 18px 0 18px;">
			<img class="mui-media-object" style=" border-radius:50%"  src="img/icon_fenlei.png" />
			<div class="iconFont">分类</div>
		</div>
	</div>


	<!--图片推广-->
	<div class="mui-table-view mui-grid-view"
		style="margin-top: 12px; background-color: #fff;">
		<div class="mui-table-view-cell mui-media mui-col-xs-6"
			style="float: left; text-align: center">
			<img class="mui-media-object" src="img/ad_03.jpg" />
		</div>
		<div class="mui-table-view-cell mui-media mui-col-xs-6"
			style="float: left;">
			<div>
				<img class="mui-media-object" src="img/ad_05.jpg" />
			</div>
			<div style="margin-top: -2px;">
				<img class="mui-media-object" src="img/ad_09.jpg" />
			</div>
		</div>
		<div class="mui-table-view-cell mui-media mui-col-xs-12">
			<img class="mui-media-object" src="img/ad_13.jpg" />
		</div>
	</div>

	<!--优秀品牌-->
	<div
		style="font-size: 16px; color: #333; margin-left: 20px; margin-top: 16px; margin-bottom: 8px;">优秀品牌</div>
	<div
		style="width: 100%; height: 2px; background-color: #cc0000; margin-top: 4px;"></div>
	<ul class="mui-table-view mui-grid-view"
		style="list-style: none; background-color: #f7f7f7;">
		<s:iterator value="#session.brandList" var="brand">
			<li
				class="mui-table-view-cell mui-media mui-col-xs-2 mui-text-center">
				<a href="${pageContext.request.contextPath}/phone/getProInfoByBrandId.action?brandId=<s:property value='#brand.brandId'/>">
					<img class="mui-media-object"       
					src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#brand.brandBigImageUrl'/>" />
			</a>
			</li>
		</s:iterator>

	</ul>


	<!--热销活动-->

	<div class="mui-content" style="background-color: #fff;">
		<div
			style="font-size: 16px; color: #333; margin-left: 20px; margin-top: 16px; margin-bottom: 12px;">热销活动</div>
		<ul class="mui-table-view mui-grid-view">
			<li
				class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center">
				<a href="#"> <img class="mui-media-object"
					src="img/huodong_32.jpg" />
					<div style="height: 10px; background-color: #b00800;"></div>
			</a>
			</li>
			<li
				class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center">
				<a href="#"> <img class="mui-media-object"
					src="img/huodong_33.jpg" />
					<div style="height: 10px; background-color: #dc24a2;"></div>
			</a>
			</li>
			<li
				class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center">
				<a href="#"> <img class="mui-media-object"
					src="img/huodong_34.jpg" />
					<div style="height: 10px; background-color: #1d7d00;"></div>
			</a>
			</li>
			<li
				class="mui-table-view-cell mui-media mui-col-xs-3 mui-text-center">
				<a href="#"> <img class="mui-media-object"
					src="img/huodong_35.jpg" />
					<div style="height: 10px; background-color: #0a5fd6;"></div>
			</a>
			</li>
		</ul>
	</div>

	<!--精品推荐-->
	<div class="mui-content" style="background-color: #fff">
		<div
			style="font-size: 16px; color: #333; margin-left: 20px; margin-top: 16px; margin-bottom: 12px;">精品推荐</div>
		<ul class="mui-table-view mui-grid-view">
			<s:iterator value="#request.listMap" var="m" status="eLM" step="1">
				<s:iterator value="#m.listMapTab" var="tabMap">
					<s:if test="#eLM.odd">
						<s:if test="#tabMap.tabProductList.size>0">
							<s:iterator value="#tabMap.tabProductList" var="p" status="uPro">
								<s:iterator value="#uPro.index">
									<li class="mui-table-view-cell mui-media mui-col-xs-6"><a
										title="<s:property value='#p.title' />" target="_blank"
										href="<s:property value='#p.link' />?customerId=${sessionScope.customerId}"> <img
											class="mui-media-object lazy"
											style="height: 8em; overflow: hidden"
											src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#p.imageUrl'/>"
											data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#p.imageUrl'/>" />
											<div class="mui-media-body">
												<s:property value="#p.title" />
											</div>
											<div style="height: 20px; margin-top: 8px;">
												<span style="color: #cc0000; font-size: 22px;"><s:property
														value="#p.synopsis" /></span>
											</div>
									</a></li>
								</s:iterator>
							</s:iterator>
						</s:if>
					</s:if>
				</s:iterator>
			</s:iterator>
		</ul>
	</div>

	<script>
		var menu = null, main = null;
		var showMenu = false;
		mui.init({
			swipeBack : false,
			statusBarBackground : '#f7f7f7',
			gestureConfig : {
				doubletap : true
			},
			subpages : [ {
				id : 'list',
				url : 'list.html',
				styles : {
					top : '45px',
					bottom : 0,
					bounce : 'vertical'
				}
			} ]
		});
		mui.plusReady(function() {
			//仅支持竖屏显示
			plus.screen.lockOrientation("portrait-primary");
			main = plus.webview.currentWebview();
			main.addEventListener('maskClick', closeMenu);
			//处理侧滑导航，为了避免和子页面初始化等竞争资源，延迟加载侧滑页面；
			setTimeout(function() {
				menu = mui.preload({
					id : 'index-menu',
					url : 'index-menu.html',
					styles : {
						left : 0,
						width : '70%',
						zindex : -1
					},
					show : {
						aniShow : 'none'
					}
				});
			}, 200);
		});
		var isInTransition = false;
		//敲击顶部导航，内容区回到顶部
		document.querySelector('header').addEventListener('doubletap',
				function() {
					main.children()[0].evalJS('mui.scrollTo(0, 100)');
				});
		//处理右上角关于图标的点击事件；
		var subWebview = null, template = null;
		document.getElementById('info').addEventListener('tap', function() {
			if (subWebview == null) {
				//获取共用父窗体
				template = plus.webview.getWebviewById("default-main");
			}
			if (template) {
				subWebview = template.children()[0];
				subWebview.loadURL('examples/info.html');
				//修改共用父模板的标题
				mui.fire(template, 'updateHeader', {
					title : '关于',
					showMenu : false
				});
				template.show('slide-in-right', 150);
			}
		});
	</script>
</body>

</html>