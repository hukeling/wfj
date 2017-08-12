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
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/js/jq.js"></script>

<!-- 新版轮播图js开始 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tt.index_V1.4.min-t.js"></script>
<!-- 新版轮播图js结束 -->

<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndexNew/css/wfj.css" />

<style>
* {
	margin: 0;
	padding: 0px;
}

a {
	text-decoration: none;
}

img {
	border: none;
}

li {
	list-style: none;
}

body {
	background-color: #f9f9f9;
	font-family: "微软雅黑";
}
</style>

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
		var winopen = window.open ("${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&level="+level+"&orderBy=normal");
		/*
		if( winopen==null){//自动提交的链接
		    document.write('<a href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&orderBy=normal" target="_blank" id="autopop" style="display:none"></a>');
		    document.getElementById('autopop').click(); 
		}
		*/
	}
	
	$(function(){						//左侧导航隐藏、显示
		$("#navBar").hover(function() {
			$(".layout").show();
		}, function() {
			$(".layout").hide();
		});
	})
	
</script>


</head>

<body>
	<!-- <div style="margin: 0 auto; height: 120px; width: 1200px;"><img src="images/20151127-4.jpg"/></div> -->
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
							href="${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action">
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
			<a href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action" class="shopping_cart_a">购物车</a>
		</div>
	</div>
	
	<!--------导航----------->
	<div class="nav_box">
		<div class="in_nav">
			<div id="navBar">
				<div class="all_nav">
					<a href="#">所有分类</a>
				</div>
				<!------导航,通栏--------->
				<div class="layout"
					style="display: none; position: absolute; z-index: 9999; top: 176px">
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
										</div>
									</div>
								</s:if>
							</s:iterator>

							<div class="header_bgyinB"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="main_nav">
				<ul>
					<li><a class="maT" href="${pageContext.request.contextPath}/">首页</a></li>
					<li><a class="ma" href="${pageContext.request.contextPath}/home/bainian.html">百年王府井</a></li>
	                <li><a class="ma" href="${pageContext.request.contextPath}/home/shishang.html">时尚王府井</a></li>
	                <li><a class="ma" href="#">畅游</a></li>
	                <li><a class="ma" href="${pageContext.request.contextPath}/tuan/gotoTuanHome.action?headerType=group">团购</a></li>
	                <li><a class="ma" href="${pageContext.request.contextPath}/front/discountcoupon/coupon/gotofrontcoupon.action?headerType=coupon">优惠券</a></li>
	                <li style="width: 40px;height: 20px;padding-left: 40px; color: #999;">|</li>
	                <li><a class="ma" href="cooperation.html">合作商家</a></li>
	                <li><a class="ma" href="#">金融服务</a></li>
	                <li><a class="ma" href="${pageContext.request.contextPath}/companyIntroduce.action">加盟王府井</a></li>
				</ul>
			</div>

		</div>
	</div>
	<div style="clear: both"></div>


</body>
</html>