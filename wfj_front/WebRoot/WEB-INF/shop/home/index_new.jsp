<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <title>${application.homekeybook['homeSeoTitle1'][0].value}</title> -->
<title>智慧王府井</title>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/js/jquery-1.9.1.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/js/jq.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/js/sHover.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/js/sHover.min.js"></script>

<!-- 新版轮播图js开始 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tt.index_V1.4.min-t.js"></script>
<!-- 新版轮播图js结束 -->

<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/wfj.css" />

<!--顶部js-->
<script type="text/javascript">
	//点击搜索查询
	searchImg=function(){
		var keyWord = $.trim($("#searchContent").val());
		if(keyWord != null && keyWord != ""){
			var newKey = escape(keyWord);
			$("#searchContentValue").val(newKey);
			$("#searchForm").submit();
		}
	};
	//失去焦点与获得焦点事件
	focusAndBlur=function(params){//params用于区别失去焦点 还是  获得焦点
		var kw=$("#searchContent").val();//搜索框中的关键字
		if(params=="focus"){
			if(kw=="请输入品牌，订货号，商品名称"){
				$("#searchContent").val("");
			}
		}else{
			if(kw==""){
				$("#searchContent").val("请输入品牌，订货号，商品名称");
			}
		}
	};
	(function(a){
		a.fn.hoverClass=function(b){
			var a=this;
			a.each(function(c){
				a.eq(c).hover(function(){
					$(this).addClass(b);
				},function(){
					$(this).removeClass(b);
				});
			});
			return a;
		};
	})(jQuery);
	
	$(function(){
		$("#navbox").hoverClass("current");
		$("#navbox2").hoverClass("current");
		$("#navbox3").hoverClass("current");
		// 回车处理
		$("#searchContent").keydown(function(){ 
			var evt = event ? event : (window.event ? window.event : null);
			if(evt.keyCode == 13){
				var keyWord = $.trim($("#searchContent").val());
				if(keyWord != null && keyWord != "" && keyWord != "请输入品牌，订货号，商品名称"){
					var newKey = escape(keyWord);
					$("#searchContentValue").val(newKey);
					$("#searchForm").submit();
				}else{
					$("#searchContent").val("请输入品牌，订货号，商品名称");
					//$(this).focus();
				};
			};
		});	
	});
	//将搜索框中的内容置空
	function setSearchNull(){
		var keyWord = $.trim($("#searchContent").val());
		if(keyWord == "请输入品牌，订货号，商品名称"){
			$("#searchContent").val("");
		};
	}
	//将搜索框中的内容置空
	function reBack(){
		var keyWord = $.trim($("#searchContent").val());
		if(keyWord == null || keyWord == ""){
			$("#searchContent").val("请输入品牌，订货号，商品名称");
		};
	}
	//搜索商品
	function search(){
		var keyWord = $.trim($("#searchContent").val());
		if(keyWord != null && keyWord != "" && keyWord != "请输入品牌，订货号，商品名称"){
			var newKey = escape(keyWord);
			$("#searchContentValue").val(newKey);
			$("#searchForm").submit();
		}else{
			$("#searchContent").val("请输入品牌，订货号，商品名称");
			//$("#searchContent").focus();
		};
	}
	//热门关键字搜索
	function hotWordSearch(hotWord){
		$("#searchContent").val(hotWord);
		searchImg();
	}
	//购物车，买家中心
	function doRedirect(state) {
		if (state == "1") {//My account
			window.location = "${pageContext.request.contextPath}/front/customer/index.action";
		} else if (state == "2") {//cart
			window.location = "${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
		}
	}
	//用户中心
	function doSellerCheck(type) {
			if (type == "1"||type=="3") {//My account
				window.location = "${pageContext.request.contextPath}/front/customer/index.action";
			} else if (type == "2") {//cart
				window.location = "${pageContext.request.contextPath}/front/store/frontshopinfo/goToShopLoginHome.action";
			}
	}
	
	//转向一级分类
	function goFirstType(firstTypeId,level){
		var winopen = window.open ("${pageContext.request.contextPath}/frontBrands/getShopsAndProductInfos.action?productTypeId="+firstTypeId+"&level="+level+"&orderBy=normal");
		/*
		if( winopen==null){//自动提交的链接
		    document.write('<a href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&orderBy=normal" target="_blank" id="autopop" style="display:none"></a>');
		    document.getElementById('autopop').click(); 
		}
		*/
	}
</script>


</head>

<body>
	<!--顶部-->
	<div class="top_man">
		<div class="top_box">
			<s:if test="#session.customer==null">
				<div class="load_btn">
					<p>Hi,请</p>
					<a
						href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">登录</a>
					<p>/</p>
					<a
						href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=3">注册</a>
				</div>
				<div class="qu">
					<p>送货至</p>
					<b>北京</b>
					<div class="qu_two">
						<ul class="qu_title">
							<li>
								<h3>热门省市</h3>
							</li>
							<li><a href="">上海</a></li>
							<li><a href="">北京</a></li>
							<li><a href="">广东</a></li>
							<li><a href="">广西</a></li>
							<li><a href="">湖北</a></li>
							<li><a href="">四川</a></li>
							<li><a href="">福建</a></li>
						</ul>
						<div class="qu_box">
							<ul class="qu_one border_right">
								<li><span>A</span> <a href="">安徽</a></li>
								<li><span>B</span> <a href="">北京</a></li>
								<li><span>C</span> <a href="">重庆</a></li>
								<li><span>G</span> <a href="">广东</a> <a href="">广西</a> <a
									href="">贵州</a> <a href="">甘肃</a></li>
								<li><span>F</span> <a href="">福建</a></li>
								<li><span>H</span> <a href="">河北</a> <a href="">黑龙江</a> <a
									href="">海南</a> <a href="">湖北</a> <a href="">湖南</a> <a href="">河南</a>
								</li>
								<li><span>J</span> <a href="">江苏</a> <a href="">吉林</a> <a
									href="">江西</a></li>
								<li><span>L</span> <a href="">辽宁</a></li>
							</ul>
							<ul class="qu_one">
								<li><span>N</span> <a href="">内蒙古</a> <a href="">宁夏</a></li>
								<li><span>Q</span> <a href="">青海</a></li>
								<li><span>S</span> <a href="">上海</a> <a href="">山东</a> <a
									href="">山西</a> <a href="">四川</a> <a href="">陕西</a></li>
								<li><span>T</span> <a href="">天津</a></li>
								<li><span>X</span> <a href="">西藏</a> <a href="">新疆</a></li>
								<li><span>Y</span> <a href="">云南</a></li>
								<li><span>Z</span> <a href="">浙江</a></li>
							</ul>
						</div>
					</div>
				</div>
			</s:if>
			<s:else>
				<div class="load_btn">
					<p>您好，</p>
					<s:if
						test="#session.shopInfo.companyName!=''&&#session.shopInfo.companyName!=null">
						<a style=""
							href="${pageContext.request.contextPath}/front/store/frontshopinfo/goToShopLoginHome.action">
							<s:property value="#session.shopInfo.companyName" />
						</a>
					</s:if>
					<s:else>
						<a
							href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action"<%-- href="${pageContext.request.contextPath}/front/store/frontshopinfo/goToShopLoginHome.action --%>">
							<s:property value="#session.customer.loginName" />
						</a>
					</s:else>

				</div>
			</s:else>

			<ul class="top_nav">
				<li class="top_index">
					<p>我的王府井</p>
					<ul class="top_two">
						<li><a id="red" href="">我的王府井</a></li>
						<li><a href="">已买到的宝贝</a></li>
						<li><a href="">我的足迹</a></li>
						<li><a href="">我的积分</a></li>
						<li><a href="">我的抵用券</a></li>
					</ul>
				</li>
				<li class="top_index">
					<p>会员服务</p>
					<ul class="top_two">
						<li><a id="red" href="">会员服务</a></li>
						<li><a href="">会员专享</a></li>
						<li><a href="">我的积分</a></li>
					</ul>
				</li>
				<li class="top_index">
					<p>招商加盟</p>
					<ul class="top_two">
						<li><a id="red" href="">招商加盟</a></li>
						<li><a href="">联系方式</a></li>
						<li><a href="">加盟规则</a></li>
					</ul>
				</li>
				<li class="top_index">
					<p>客户服务</p>
					<ul class="top_two">
						<li><a id="red" href="">客户服务</a></li>
						<li><a href="">消费者客服</a></li>
						<li><a href="">卖家客服</a></li>
					</ul>
				</li>
				<li class="top_index">
					<p>网站导航</p>
					<ul class="top_two">
						<li><a id="red" href="">网站导航</a></li>
						<li><a href="">主题市场</a></li>
						<li><a href="">特色购物</a></li>
						<li><a href="">当前热点</a></li>
						<li><a href="">更多精彩</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>

	<!--LOL 搜索框-->
	<div class="logo_box">
		<div class="logo"></div>
		<div class="sousuo_big">
			<form
				action="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action"
				method="get" id="searchForm">
				<input type="hidden" name="pager.keyword" id="searchContentValue" />
				<div class="sousuo_box">
					<input type="text" id="searchContent" autocomplete="off"
						placeholder="请输入品牌，订货号，商品名称" onfocus="focusAndBlur('focus')"
						onblur="focusAndBlur('blur')"
						value="<s:property value='keyword'/>" class="sousuo" /> <input
						type="submit" class="sousuo_btn" value="搜索" onclick="searchImg()" />
				</div>
				<ul class="sousuo_list">
					<li><a href=# onclick="javascript:hotWordSearch('卷纸');">卷纸</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('脱毛');">脱毛</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('趣多多');">趣多多</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('护肤');">护肤</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('进口牛奶');">进口牛奶</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('软抽');">软抽</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('大金');">大金</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('三星');">三星</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('联合利华');">联合利华</a></li>
					<li><a href=# onclick="javascript:hotWordSearch('欧莱雅');">欧莱雅</a></li>
				</ul>
			</form>
		</div>
		<div class="shopping_cart">
			<a
				href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action"
				class="shopping_cart_a">购物车</a>
		</div>
	</div>

	<!--------导航----------->
	<div class="nav_box">
		<div class="in_nav">
			<div class="all_nav">
				<a href="#">所有分类</a>
			</div>
			<div class="main_nav">
				<ul>
					<li><a class="maT" href="index_new.jsp">首页</a></li>
					<li><a class="ma" href="home/bainian.html">百年王府井</a></li>
					<li><a class="ma" href="home/shishang.html">时尚王府井</a></li>
					<li><a class="ma" href="home/changyou.jsp">畅游</a></li>
					<li><a class="ma"
						href="${pageContext.request.contextPath}/tuan/gotoTuanHome.action?headerType=group">团购</a></li>
					<li><a class="ma"
						href="${pageContext.request.contextPath}/front/discountcoupon/coupon/gotofrontcoupon.action?headerType=coupon">优惠券</a></li>
					<li
						style="width: 40px; height: 20px; padding-left: 40px; color: #999;">|</li>
					<li><a class="ma" href="cooperation.html">合作商家</a></li>
					<li><a class="ma" href="#">金融服务</a></li>
					<li><a class="ma" href="jiameng.html"
						<%-- href="${pageContext.request.contextPath}/companyIntroduce.action" --%>>加盟王府井</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div style="clear: both"></div>

	<!------导航,通栏--------->
	<div class="layout">
		<div class="in_layout">
			<!----左---->
			<div class="classify_nav">
				<s:iterator value="#application.categoryMap" var="ptbOne"
					status="stOne" end="5">
					<s:if test="#stOne.index<5">
						<div class="every">
							<h4>
								<a href=#
									onclick="goFirstType(<s:property value='#ptbOne.key.productTypeId'/>,<s:property value='#ptbOne.key.level'/>)">
									<i class="icon<s:property value="#stOne.index+1" />"></i> <s:property
										value='#ptbOne.key.sortName' />
								</a>
							</h4>
							<p>
								<s:iterator value="#ptbOne.value" var="ptbTwo" status="st">
									<s:if test="#st.index<2">
										<a href="#"
											onclick="goFirstType(<s:property value='#ptbTwo.key.productTypeId'/>,<s:property value='#ptbTwo.key.level'/>)"><s:property
												value="#ptbTwo.key.sortName" /></a>、
						        		</s:if>
									<s:if test="#st.index==2">
										<a href="#"
											onclick="goFirstType(<s:property value='#ptbTwo.key.productTypeId'/>,<s:property value='#ptbTwo.key.level'/>)"><s:property
												value="#ptbTwo.key.sortName" /></a>
									</s:if>
								</s:iterator>
							</p>
							<div class="second_Box">
								<ul class="se_1">
									<s:iterator value="#ptbOne.value" var="ptbTwo" status="st">
										<s:if test="#st.odd">
											<li class="title"><a href="#"
												onclick="goFirstType(<s:property value='#ptbTwo.key.productTypeId'/>,<s:property value='#ptbTwo.key.level'/>)"><s:property
														value="#ptbTwo.key.sortName" /></a></li>
											<li class="se_link"><s:iterator value="#ptbTwo.value"
													var="ptbThree" status="ts">
													<s:if test='#ts.index<8'>
														<s:if test='#ts.index>0'>|</s:if>
														<a href="javascript:void(0);"
															onclick="goFirstType(<s:property value='#ptbThree.key.productTypeId'/>,<s:property value='#ptbThree.key.level'/>)"><s:property
																value="#ptbThree.key.sortName" /></a>
													</s:if>
												</s:iterator></li>
										</s:if>
									</s:iterator>
								</ul>
								<ul class="se_2">
									<s:iterator value="#ptbOne.value" var="ptbTwo" status="st">
										<s:if test="#st.even">
											<li class="title"><a href="#"
												onclick="goFirstType(<s:property value='#ptbTwo.key.productTypeId'/>,<s:property value='#ptbTwo.key.level'/>)"><s:property
														value="#ptbTwo.key.sortName" /></a></li>
											<li class="se_link"><s:iterator value="#ptbTwo.value"
													var="ptbThree" status="ts">
													<s:if test='#ts.index<18'>
														<s:if test='#ts.index>0'>|</s:if>
														<a href="javascript:void(0);"
															onclick="goFirstType(<s:property value='#ptbThree.key.productTypeId'/>,<s:property value='#ptbThree.key.level'/>)"><s:property
																value="#ptbThree.key.sortName" /></a>
													</s:if>
												</s:iterator></li>
										</s:if>
									</s:iterator>
								</ul>
								<div class="ad_01">
									<a href="#"><img
										src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/ad_<s:property value="#stOne.index" />1.jpg" />
									</a>
								</div>
								<div class="ad_01">
									<a href="#"><img
										src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/ad_<s:property value="#stOne.index" />2.jpg" />
									</a>
								</div>
								<div class="ad_01">
									<a href="#"><img
										src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/ad_<s:property value="#stOne.index" />3.jpg" />
									</a>
								</div>
							</div>
						</div>
					</s:if>
				</s:iterator>

				<div class="header_bgyinB"></div>
			</div>

			<!----中，轮播---->
			<div class="banner">
				<s:if test="#request.homeLBTList.size>0">
					<s:iterator value="#request.homeLBTList" var="h">
						<a href="#"><img
							src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#h.broadcastingIamgeUrl'/>" /></a>
					</s:iterator>
				</s:if>
				<ul class="ba_1">
					<s:if test="#request.homeLBTList.size>0">
						<s:iterator value="#request.homeLBTList" var="h">
							<li class="ba_2"></li>
						</s:iterator>
					</s:if>
				</ul>
			</div>

			<!----右---->
			<div class="facility">
				<div class="fa_1">
					<span> <a href="#"><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/index_10.jpg"></a>
						<a href="#"><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/index_12.jpg"></a>
						<a href="#"><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/index_14.jpg"></a>
					</span>
				</div>
				<div class="fa_2">
					<p>王府井快报</p>
				</div>
				<div class="fa_3">
					<p>
						<a href="#">教你涨积分兑换心动好礼享受专属优惠</a>
					</p>
					<p>
						<a href="#">刷卡消费享受专属优惠冰点折扣</a>
					</p>
					<p>
						<a href="#">清凉一夏冰点折扣来袭教你涨积分</a>
					</p>
					<p>
						<a href="#">一键加盟 让利多多兑换心动好礼</a>
					</p>
					<p>
						<a href="#">注册会员即有好礼相送享受专属优惠</a>
					</p>
					<p>
						<a href="#"> “中国质造”试水速卖通</a>
					</p>
					<p>
						<a href="#">一位P2P平台总监的吐槽：花两百万元能当行业会长</a>
					</p>
					<p>
						<a href="#">A股尾盘强势逆转 沪指涨逾3%逼近3800点</a>
					</p>
				</div>
			</div>
		</div>
	</div>
	<div
		style="margin: 0 auto; height: 125px; width: 1200px; margin-top: 5px;">
		<img src="images/201511301406.jpg" />
	</div>
	<!--      历史、文化、时尚、魅力 -->
	<div class="row">
		<div class="ta">
			<ul class="menu">
				<li class="active">魅力王府井</li>
				<li>百年传承</li>
				<li>名品荟萃</li>
				<li>文艺活动</li>
			</ul>
			<div class="con1">
				<div class="sHoverItem">
					<img
						src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_03.jpg">
						<span class="sIntro">
							<h2>魅力王府井</h2>
							<p>魅力 文化 活力 多彩</p> <a href="home/wfj-charm.jsp">
								<button type="button">点击进入</button>
						</a>
					</span>
				</div>

				<div class="sHoverItem">
					<img
						src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_05.jpg">
						<span class="sIntro">
							<h2>王府井历史</h2>
							<p>历史 传统 风俗 艺术 思想</p> <a href="home/wfj-history.html">
								<button type="button">点击进入</button>
						</a>
					</span>
				</div>


				<div class="sHoverItem">
					<img
						src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_07.jpg">
						<span class="sIntro">
							<h2>文化王府井</h2>
							<p>文化 深度 特色</p> <a href="home/wfj-culture.html">
								<button type="button">点击进入</button>
						</a>
					</span>
				</div>

				<div class="sHoverItem">
					<img
						src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_09.jpg">
						<span class="sIntro">
							<h2>时尚王府井</h2>
							<p>领先 趋势 品牌 个性</p> <a href="home/wfj-fashion.html">
								<button type="button">点击进入</button>
						</a>
					</span>
				</div>
			</div>
			<div class="con2">
				<div class="con">
					<ul>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/lishi_03.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/lishi_05.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/lishi_07.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/lishi_10.jpg" /></li>
					</ul>
				</div>
			</div>
			<div class="con3">
				<div class="con">
					<ul>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_03.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_05.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_07.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_09.jpg" /></li>
					</ul>
				</div>
			</div>
			<div class="con4">
				<div class="con">
					<ul>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_03.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/huodong2.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/huodong1.jpg" /></li>
						<li><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/image_07.jpg" /></li>
					</ul>
				</div>
			</div>
		</div>
		<!--遮罩层显示-->
		<script>
	        window.onload = function () {
	            var example1 = new sHover("sHoverItem", "sIntro");
	            example1.set({
	                slideSpeed: 7,
	                opacity: 80,
	                opacityChange: true
	            });
	        }
	    </script>
		<!--标签切换-->
		<script type="text/javascript">
	        $(function () {
	            $('.ta ul.menu li').hover(function () {
	                //获得当前被点击的元素索引值
	                var Index = $(this).index();
	                //给菜单添加选择样式
	                $(this).addClass('active').siblings().removeClass('active');
	                //显示对应的div
	                $('.ta').children('div').eq(Index).show().siblings('div').hide();
	            });
	        });
	    </script>
	</div>

	<div style="clear: both;"></div>
	<!--购物中心-->
	<%-- <div class="mall_wrap">
		<div class="mall">
			<div class="mall_left">
				<div class="mall_title1">
					<p>澳门中心</p>
				</div>
				<div class="mall_con">
					<div class="mall_img">
						<a href="home/aomenzhongxin.html"><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/index_39.jpg"></a>
					</div>
					<div class="mall_text">
						<div class="mall_text_tuijian">
							<p>商家推荐</p>
							<b><a href="#">更多</a></b>
						</div>
						<div class="mall_text_con">
							<ul>
								<li>
									<h4>
										<a href="#">外婆家</a>
									</h4>
									<h5>
										<a href="#">在线订购可享9.0折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">汉丽轩</a>
									</h4>
									<h5>
										<a href="#">在线订购可享8.8折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">蜀国演义</a>
									</h4>
									<h5>
										<a href="#">在线订购可享7.9折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">豆捞坊（东直门店） </a>
									</h4>
									<h5>
										<a href="#">在线订购可享9.0折优惠</a>
									</h5>
								</li>
							</ul>
						</div>
						<div class="mall_text_zhe">
							<p>9.0折</p>

							<p>8.8折</p>

							<p>7.9折</p>

							<p>9.0折</p>
						</div>
					</div>
				</div>
			</div>
			<div class="mall_right">
				<div class="mall_title2">
					<p>北京百货大楼</p>
				</div>
				<div class="mall_con">
					<div class="mall_img">
						<a href="#"><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/index_42.jpg"></a>
					</div>
					<div class="mall_text">
						<div class="mall_text_tuijian">
							<p>商家推荐</p>
							<b><a href="#">更多</a></b>
						</div>
						<div class="mall_text_con">
							<ul>
								<li>
									<h4>
										<a href="#">成都芙蓉面馆</a>
									</h4>
									<h5>
										<a href="#">在线订购可享7.5折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#"> 冰城串吧</a>
									</h4>
									<h5>
										<a href="#">在线订购可享9.0折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">渝乡人家</a>
									</h4>
									<h5>
										<a href="#">在线订购可享9.5折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">深海800米海鲜</a>
									</h4>
									<h5>
										<a href="#">在线订购可享7.2折优惠</a>
									</h5>
								</li>
							</ul>
						</div>
						<div class="mall_text_zhe">
							<p>7.5折</p>

							<p>9.0折</p>

							<p>9.5折</p>

							<p>7.2折</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="mall">
			<div class="mall_left">
				<div class="mall_title3">
					<p>IN88</p>
				</div>
				<div class="mall_con">
					<div class="mall_img">
						<a href="#"><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/index_46.jpg"></a>
					</div>
					<div class="mall_text">
						<div class="mall_text_tuijian">
							<p>商家推荐</p>
							<b><a>更多</a></b>
						</div>
						<div class="mall_text_con">
							<ul>
								<li>
									<h4>
										<a href="#">兜有中影票务</a>
									</h4>
									<h5>
										<a href="#">在线订购可享5.0折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">外悠季瑜伽</a>
									</h4>
									<h5>
										<a href="#">在线订购可享6.0折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">奥康游泳培训班（东单店） </a>
									</h4>
									<h5>
										<a href="#">在线订购可享7.2折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">大益茶 </a>
									</h4>
									<h5>
										<a href="#">在线订购可享9.0折优惠</a>
									</h5>
								</li>
							</ul>
						</div>
						<div class="mall_text_zhe">
							<p>5.0折</p>

							<p>6.0折</p>

							<p>7.2折</p>

							<p>9.0折</p>
						</div>
					</div>
				</div>
			</div>
			<div class="mall_right">
				<div class="mall_title4">
					<p>东方新天地</p>
				</div>
				<div class="mall_con">
					<div class="mall_img">
						<a href="#"><img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/index_47.jpg"></a>
					</div>
					<div class="mall_text">
						<div class="mall_text_tuijian">
							<p>商家推荐</p>
							<b><a>更多</a></b>
						</div>
						<div class="mall_text_con">
							<ul>
								<li>
									<h4>
										<a href="#">嫣润堂 </a>
									</h4>
									<h5>
										<a href="#">在线订购可享6.0折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">云南汇</a>
									</h4>
									<h5>
										<a href="#">在线订购可享6.5折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">in café（in88店）</a>
									</h4>
									<h5>
										<a href="#">在线订购可享7.8折优惠</a>
									</h5>
								</li>
								<li>
									<h4>
										<a href="#">骊玛阁云南特产专卖店 </a>
									</h4>
									<h5>
										<a href="#">在线订购可享9.0折优惠</a>
									</h5>
								</li>
							</ul>
						</div>
						<div class="mall_text_zhe">
							<p>6.0折</p>

							<p>6.5折</p>

							<p>7.8折</p>

							<p>9.0折</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> --%>
	<div style="clear: both;"></div>
	<!-- 楼层循环开始 -->
	<s:if test="#request.listMap.size>0">
		<s:set name="fir_index" value="1" />
		<s:iterator value="#request.listMap" var="m" status="eLM">
			<div class="storey">
				<div style="float: left; width: 200px;">
					<div class="st_title">
						<s:property value="#m.yiJCategory.categoryName" />
					</div>
					<div class="storey_left">
						<ul>
							<s:if test="#m.secCategory.size > 0">
								<s:iterator value="#m.secCategory" var="erc" status="ercst">
									<s:if test="#ercst.index<8">
										<li><a href="<s:property value='#erc.link'/>"><s:property
													value='#erc.title' /></a></li>
									</s:if>
								</s:iterator>
							</s:if>
						</ul>
						<!-- 促销台图片(待补充) -->
						<div class="storey_left_img">
							<a
								href="${pageContext.request.contextPath}<s:property value='#eLM.imageUrl'/>"><img
								src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/img/left-<s:property value="#eLM.index" />.jpg"></a>
						</div>
					</div>
				</div>
				<!--  商品tab -->
				<div class="zzsc<s:property value="#eLM.index + 1" />">
					<!-- 二级目录 -->
					<div class="tab">
						<s:if test="#m.listMapTab!=null">
							<s:if test="#m.listMapTab.size>0">
								<s:iterator value="#m.listMapTab" var="mlm" status="u">
									<s:if test="#u.index==0">
										<a href="javascript:;" class="on"><s:property
												value='#mlm.tabName' /></a>
									</s:if>
									<s:else>
										<a href="javascript:;"><s:property value='#mlm.tabName' /></a>
									</s:else>
								</s:iterator>
							</s:if>
						</s:if>
					</div>
					<!-- 二级目录下商品 -->
					<div class="content">
						<ul>
							<s:iterator value="#m.listMapTab" var="tabMap">
								<li style="display: block;"><s:if test="#eLM.odd">
										<div class="storey_max">
											<s:if test="#tabMap.tabProductList.size>0">
												<s:iterator value="#tabMap.tabProductList" var="p"
													status="uPro">
													<s:if test="#uPro.index<6">
														<div class="storey_rightmax">
															<div class="rightmax_img">
																<a title="<s:property value='#p.title' />"
																	target="_blank" href="<s:property value='#p.link' />">
																	<img width=270 height=188
																	src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#p.imageUrl'/>" />
																</a>
															</div>
															<div class="rightmax_tx1">
																<a href=""><s:property value="#p.title" /></a>
															</div>
															<div class="rightmax_tx2">
																<a href=""><s:property value="#p.synopsis" /></a>
															</div>
														</div>
													</s:if>
												</s:iterator>
											</s:if>
										</div>
									</s:if> <s:else>
										<div class="storey_middle">
											<div class="storey_middle">
												<a
													title="<s:property value='#tabMap.tabProductList[0].title' />"
													target="_blank"
													href="<s:property value='#tabMap.tabProductList[0].link' />">
													<img width=450 height=340
													src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#tabMap.tabProductList[0].imageUrl'/>" />
												</a>
												<div class="rightmin_tx1">
													<a href="">悠季瑜伽(东方广场)</a>
												</div>
												<div class="rightmin_tx2">
													<a href="">¥142</a>起
												</div>
											</div>
										</div>
										<div class="storey_right">
											<s:if test="#tabMap.tabProductList.size>0">
												<s:iterator value="#tabMap.tabProductList" var="p"
													status="uPro">
													<s:if test="#uPro.index<5 && #uPro.index>0">
														<div class="storey_rightmin">
															<div class="rightmin_img">
																<a title="<s:property value='#p.title' />"
																	target="_blank" href="<s:property value='#p.link' />">
																	<img
																	src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#p.imageUrl'/>" />
																</a>
															</div>
															<div class="rightmin_tx1">
																<a href="">大益茶</a>
															</div>
															<div class="rightmin_tx2">
																<a href=""> ¥78</a>起
															</div>
														</div>
													</s:if>
												</s:iterator>
											</s:if>
										</div>
									</s:else></li>
							</s:iterator>
						</ul>
					</div>
				</div>
			</div>
		</s:iterator>
	</s:if>
	<script>
		function floorTabChange(classID) {
			$("."+classID+" .tab a").mouseover(function(){
				$(this).addClass('on').siblings().removeClass('on');
				var index = $(this).index();
				number = index;
				$("."+classID+" .content li").hide();
				$("."+classID+" .content li:eq("+index+")").show();
	        });
			
		}
    $(function(){
    	floorTabChange("zzsc1");
    	floorTabChange("zzsc2");
    	floorTabChange("zzsc3");
    	floorTabChange("zzsc4");
    	floorTabChange("zzsc5");
    });
</script>
	<!-- 将底部通用部分引入首页 -->
	<%@ include file="../include/footer.jsp"%>
</body>
</html>