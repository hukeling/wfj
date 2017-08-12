<!doctype html>
<!--[if lt IE 7 ]><html class="no-js ie6" lang="en"><![endif]-->
<!--[if IE 7 ]><html class="no-js ie7" lang="en"><![endif]-->
<!--[if IE 8 ]><html class="no-js ie8" lang="en"><![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!--<![endif]-->
<head>
<title>商城首页</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	 
	<link rel="stylesheet" type="text/css" href="${contextPath}/common/shop/front/css/index.css">
	<link rel="stylesheet" type="text/css" href="${contextPath}/common/shop/css/modal.css" />
	<script src="${contextPath}/common/shop/front/js/jquery-1.7.2.min.js"></script>
	<script src="${contextPath}/common/shop/front/js/jquery.tools.min.js"></script>
	<script src="${contextPath}/common/shop/front/js/jquery.scrollUp.js"></script>
	<script src="${contextPath}/common/shop/front/js/public.js"></script>
	
	<!--二维码(jquery qrcode插件制作二维码生成可随意生成多张二维码图片的二维码生成器。拿手机扫一扫就能获取二维码内容数据。)-->
	<script src="${contextPath}/common/shop/front/js/qrcode.js"></script>
	<!--[if (gte IE 6)&(lte IE 8)]>
	<script src="${contextPath}/common/shop/front/js/html5.js"></script>
	<script src="${contextPath}/common/shop/front/js/selectivizr-min.js"></script>
	<script src="${contextPath}/common/shop/front/js/css3-mediaqueries.js"></script>  
	<![endif]-->
	<style>
		.foot{width:auto; height:165px; background:#f7f7f7; overflow:hidden;}
		.foot_daohang{width:1192px; height:165px; overflow:hidden; margin:0 auto;}
		.foot_daohang p{text-align:center; line-height:24px; color:#282828; height:auto;}
		.dibu_img{width:400px; height:40px; margin:0 auto; overflow:hidden;  margin-top:20px;}
		.dibu_img img{ padding-left:5px; padding-right:5px;}
	</style>
	<script type="text/javascript">
		$(function () {
			//
			$.scrollUp();
		});
	</script>

<script type="text/javascript">
	
	var t = n = 0, count;
	$(document).ready(
			function() {
				count = $("#banner_list a").length;
				$("#banner_list a:not(:first-child)").hide();
				$("#banner_info").html( $("#banner_list a:first-child").find("img").attr('alt'));
				$("#banner_info").click(
						function() {
							window.open($("#banner_list a:first-child").attr('href'), "_blank")
						});
				$("#banner li").click(
						function() {
							var i = $(this).text() - 1;//获取Li元素内的值，即1，2，3，4 
							n = i;
							if (i >= count)
								return;
							$("#banner_info").html(
									$("#banner_list a").eq(i).find("img").attr('alt'));
							$("#banner_info").unbind().click(
									function() {
										window.open($("#banner_list a").eq(i).attr('href'), "_blank")
									})
							$("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000);
							$(this).css({
								"background" : "#be2424",
								'color' : '#000'
							}).siblings().css({
								"background" : "#6f4f67",
								'color' : '#fff'
							});
						});
				t = setInterval("showAuto()", 4000);
				$("#banner").hover(function() {
					clearInterval(t)
				}, function() {
					t = setInterval("showAuto()", 4000);
				});
			})

	function showAuto() {
		n = n >= (count - 1) ? 0 : ++n;
		$("#banner li").eq(n).trigger('click');
	}
	//
//领取优惠券
function getCoupon(x){
		var url ="${contextPath}/front/customer/coupon/gotoCustomerHaveCoupon.action";
		$.post(url,{discountCouponID:x},function(data){
			 if(data.state=="on"){
				lalert("Prompt","get succeed!");
	       }else if(data.state=="off"){
//				lalert("Warn","please log in!");
	       }
		},"JSON");
}
	
 $().ready(function() {
		$productSearchForm = $("#productSearchForm");//搜索表单
		$productSearchFormButton = $("#productSearchFormButton");//搜索表单提交按钮
		$productSearchKeyword = $("#productSearchKeyword");//搜索关键词标签
		$keyword = $.trim($productSearchKeyword.val());//获取搜索关键词，并且去首尾空格		
		$productSearchFormButton.bind("click",function(){
  	  		var v =$.trim($productSearchKeyword.val());
  	  		if(v=="" || v =="请输入关键字..."){
	  	  		lalert("提示","请输入关键字!");
				return false;
  	    	}else{
  	    	//转码
				var k = escape(v);
				console.log("productSearchKeyword : "+v+", 转码后 : "+k);
				$("#productSearchKeyword").val(k);
  	    		$productSearchForm.submit();
  	    	}
		});
		$productSearchKeyword.blur(function() {
			var v = $.trim($(this).val());
			if (v == "") {
				$(this).val("请输入关键字...");
			}
		})
		$productSearchKeyword.focus(function() {
			var v = $.trim($(this).val());
			if (v == "请输入关键字...") {
				$(this).val("");
			}
		})
		if ($keyword == "") {
			$productSearchKeyword.val("请输入关键字...");
		}
	});
	
</script>

<style type="text/css">
#banner {
	position: relative;
	width: 478px;
	height: 286px;
	border: 1px solid #fdae69;
	overflow: hidden;
	font-size: 16px
}

#banner_list img {
	border: 0px;
}

#banner_info {
	position: absolute;
	bottom: 4px;
	left: 5px;
	height: 22px;
	color: #fff;
	z-index: 1001;
	cursor: pointer
}

#banner_text {
	position: absolute;
	width: 120px;
	z-index: 1002;
	right: 3px;
	bottom: 3px;
}

#banner ul {
	list-style-type: none;
	filter: Alpha(Opacity = 80);
	opacity: 0.8;
	position: absolute;
	z-index:10;
	margin: 0;
	padding: 0;
	bottom: 3px;
	right: 5px;
	height: 20px;
	padding: 0;
	right: 5px;
	height: 20px;
	opacity: 0.8;
	position: absolute;
	z-index: 10;
}

#banner ul li {
	padding: 0 8px;
	line-height: 18px;
	float: left;
	color: #FFF;
	border: #e5eaff 1px solid;
	background-color: #965c2e;
	cursor: pointer;
	margin: 0;
	font-size: 16px;
	display: block;
}

#banner_list a {
	position: relative;
} /* 让四张图片都可以重叠在一起 */
</style>
</head>

<body>

<header id="mainHeader">
	<!--Header-->
	<script type="text/javascript">
	
	/*$(document).ready(function(){
		var selectHeader = $("#selectHeader a");
		var aSelect = $("a.Select");
		//选定头部导航样式
		selectHeader.each(function(){
			$(this).bind("click",function(){
				aSelect.removeClass("Select");
				$(this).addClass("Select");
			});
		});*/
		
			//购物车，我的收藏，买家中心
			function doRedirect(state) {
				if (state == "1") {//My account
					window.location = "${contextPath}/front/customer/index.action";
				} else if (state == "2") {//cart
					window.location = "${contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
				}
			}
	//卖家中心
			function doSellerCheck() {
				window.location = "${contextPath}/front/store/frontshopinfo/goToShopLoginHome.action";
			}
		  //提交表单拦截（按回车时）
		  	function submitForm(event){
		  		 var e= event ? event : (window.event ? window.event : null);
		  		 if (e.keyCode == 13){
			        e.returnValue=false;
			        e.cancel = true;
			  		$productSearchForm = $("#productSearchForm");//搜索表单
					$productSearchFormButton = $("#productSearchFormButton");//搜索表单提交按钮
					$productSearchKeyword = $("#productSearchKeyword");//搜索关键词标签
					$keyword = $.trim($productSearchKeyword.val());//获取搜索关键词，并且去首尾空格
					var v =$.trim($productSearchKeyword.val());
			  		if(v==""||v =="请输入关键字..."){
						lalert("提示","请输入关键字!");
				        e.cancel = false;
						return false;
			    	}else{
				    	//转码
						var k = escape(v);
				    	console.log("productSearchKeyword : "+v+", 转码后 : "+k);
						$("#productSearchKeyword").val(k);
			    		$productSearchForm.submit();
			    	}
		  		 }
		  	  };
</script>

	<div class="">
		<div class=" margin-center headerFont ColorWhite" style="height:32px;line-height:32px;background-color: #f2f2f2;padding-right:130px;">
			<div class="publicinline float-left rightCartBox_L" style="padding-left:130px;">
				 <div style="float:left; width:15px; height:15px;"><img style="position:relative;top:0px;width:14px;height:14px;" src="${contextPath}/common/shop/front/images/brand_channel/pinpaipindao_09.png"></div>
         		 <div style="width: 100px;">&nbsp;<a href="javascript:window.external.addFavorite('http://www.baidu.com','天海商城')" title="天海商城">收藏商城</a>&nbsp;&nbsp;</div>
			</div>
			<div class="publicinline float-right rightCartBox_L">
				<span style="color:#666;">欢迎您!</span>&nbsp; 
				<a href="${contextPath}/loginCustomer/gotoLoginPage.action" style="color:#666;">登录</a>&nbsp;
				<a href="${contextPath}/register/gotoRegister.action" style="color:#666;">注册</a>
				<a href="javascript:;" onclick="doRedirect('1');" style="color:#666;">买家中心</a>| 
				<a href="javascript:;" onclick="doSellerCheck();" style="color:#666;">卖家中心</a>| 
				<a href="javascript:;" onclick="doRedirect('2');" style="color:#666;"><i class="icon iconCart" style="position:relative;top:3px;"></i>购物车</a>|
				<a href="${contextPath}/helpFront/gotoHelpFront.action" style="color:#666;"><img style="position:relative;top:0px;" src="${contextPath}/common/shop/front/images/help.png" />帮助中心</a>
			</div>
		</div>
	</div>
	
	<div class="PublicWidth1004 margin-center publicMarginTop15 ClearBoth publicrelative">
		<form id="productSearchForm" action="${contextPath}/frontSearchProduct/productInfoSearch.action" method="get" >
			<a href="${contextPath}" class=" publicblock LogoBox_L float-left">请输入关键字...</a>
			<div class="searchBox_L publicabsolute publicHidden  ClearBoth">
				<input type="text" id="productSearchKeyword" name="pager.keyword" onkeydown="return submitForm(event)" class="SearchInput publicBorderRed ColorQHui float-left line-height2" placeholder="请输入关键字..." /> 
				<button id="productSearchFormButton" type="button" class="SearchButton float-left publicNoBorder FontSize15 ColorWhite" ></button>

			</div>
		</form>	
	</div>
	<!--nav-->
	<div class="PublicWidth1004 margin-center publicMarginTop15 publicrelative navBox_L ClearBoth">
		<nav class="selectHeader" >
			<a href="${contextPath}/frontCategories/gotoShopCategoriesPage.action"
				class="Classification_s text-indent FontSize16">全部分类</a>
			<a id="home_home" href="${contextPath}/shopHome/gotoHomePage.action?headerType=home" class="selectHeader Select"  >商城首页</a> 
<#-- 			<a id="home_coupons" href="${contextPath}/coupon/gotoCoupon.action?headerType=coupons" class="selectHeader" >优惠券</a>   -->
			<a id="home_shops" href="${contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops" class="selectHeader">店铺</a>
			<a id="home_brands" href="${contextPath}/brandChannel/gotoBrandChannelPage.action?headerType=brands" class="selectHeader" >品牌</a> 
			<a id="home_bestselling" href="${contextPath}/hotsale/gotoHotSale.action?headerType=bestselling" class="selectHeader" >热卖频道</a>
		</nav>
	</div>
	<!--//nav-->
</header>
<!--//Header-->
	<div class="margin-center PublicWidth1004">
		<div class="publicrelative" id="mainCenOne">
			<div class="ClearBoth">
				<!--left-->
				<aside class="float-left" id="mainLClassification">
					<ul class="mainLClassificationUl publicBorder">
					<#if (categoryList?size > 0)> 
						<#list categoryList as m>
							<li>
								<div class="ClearBoth">
									<h3>
										<a href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${m.productTypeId}&orderBy=normal"
											class="oneClassA">${m.sortName}
										</a>
									</h3>
									<span class="i_CalistoMT FontSize30 float-right" style="line-height:25px;">›</span>
								</div> <!--Small Class-->
								<div class="ClassSmallBox publicBorder">
									<div class="publicrelative ClearBoth">
										<dl class="ClassFireDlL_01">
											<#list m.childProductType as secondm>
												<dt>
													<a href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${secondm.productTypeId}&orderBy=normal" class="ClassSmall02 ColorRed FontSizeB">
														${secondm.sortName}
													</a>
												</dt>
												<dd>
													<#list secondm.childProductType as third>
														<a href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${third.productTypeId}&orderBy=normal">
															${third.sortName}
														</a>
													</#list>
												</dd>
											</#list>
										</dl>
										<dl class="ClassFireDlR_01">
											<dt>
												<a href="${contextPath}/brandDirectory/gotoBrandDirectoryPage.action" class="ClassSmall02 ColorRed FontSizeB">品牌信息</a>
											</dt>
											<#if (m.brandList?size > 0)>
												<#list m.brandList as brand >
													<dd>
														<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${brand.brandId}">
															<img class="ClassFireDlR_img" onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${brand.brandImageUrl}">
														</a>
													</dd>
												</#list>
											</#if>
										</dl>
										<a href="javascript:void(0);" class="publicblock iconJD ClassSmallBox_close" onClick="$(function(){ClassSmallBox_close('.ClassSmallBox_close');});"></a>
									</div>
								</div>
							</li>
						</#list>		
					</#if>
					<!--	<li>
							<div>
								<h3>
									<a href="${contextPath}/frontCategories/gotoShopCategoriesPage.action">All Categroy</a>
								</h3>
							</div>
						</li>--!>
					</ul>
				</aside>
				<!--//left-->
				<!--center-->
				<div class="mainCenOneCenter publicHidden publicMarginLR10 float-left">
					<!-- 大广告位 -->
					
					<div id="banner" style="width: 563px; height: 237px;">
						<!--标题背景-->
						<div id="banner_info"></div>
						<!--标题-->
						<ul>
							<li style="background: #965c2e;">1</li>
							<li style="background: #965c2e;">2</li>
							<li style="background: #965c2e;">3</li>
							<li style="background: #965c2e;">4</li>
							<li style="background: #965c2e;">5</li>
						</ul>
						<div id="banner_list">
							<#if (bigIamgeList?size > 0) >
								<#list bigIamgeList as bi>
									<a href="${bi.interlinkage}" target="_blank">
										<img width="560px;" height="235px;" onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${bi.broadcastingIamgeUrl}" />
									</a>
								</#list>
							</#if>
						</div>
					</div>
					
					<div id="ad-poster-three" class="publicrelative publicBorder">
						<a class="ad-poster-browse prev"></a> <a class="ad-poster-browse next"></a>
						<div class="three_scrollable publicrelative publicHidden" id="ad-poster-three_scrollable">
							<div class="items">
								<!--首页顶部小滚屏图 -->
								<#if (smallTopIamgeList?size > 0) >
									<#if (smallTopIamgeList?size > 3)>
										<div>
											<#list smallTopIamgeList as sti>
												<#if (sti_index >=0) && (sti_index<=2)>
												<a target="_blank" href="${sti.interlinkage}">
													<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${sti.broadcastingIamgeUrl}">
												</a>
												</#if>
											</#list>
										</div>
										<div>
											<#list smallTopIamgeList as sti>
												<#if (sti_index >=3) && (sti_index<=5)>
												<a target="_blank" href="${sti.interlinkage}">
													<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${sti.broadcastingIamgeUrl}">
												</a>
												</#if>
											</#list>
										</div>
									
									<#else>
										<div>
											<#list smallTopIamgeList as sti>
												<#if (sti_index >=0) && (sti_index<=2)>
												<a target="_blank" href="${sti.interlinkage}">
													<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${sti.broadcastingIamgeUrl}">
												</a>
												</#if>
											</#list>
										</div>
									</#if>
								</#if>
							</div>
						</div>
					</div>
				</div>
				<!--//center-->
				<!--right-->
				<div class="mainCenOneRight publicHidden float-left">
				<div class="publicBorder" id="ad-poster-hot">
				<div id="ad-poster-hot_scrollable" class="hot_scrollable publicrelative publicHidden">
				<a class="ad-poster-hot-browse prev disabled"></a>
				<a class="ad-poster-hot-browse next"></a>
				<div class="items">
				<#if (hotList?size > 0) >
					<#list hotList as h>
						<div class="publicrelative">
							<span class="publicblock publicabsolute publicTop publicLeft publicPadding5_10 BackgroundRed ColorWhite1 FontSize14">热销</span>
							<a target="_blank" href="${h.interlinkage}">
								<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${h.broadcastingIamgeUrl}">
							</a>
							<a href="${h.interlinkage}" class="ColorRed FontSize14 publicblock hot-poster-title">
								${h.title}
							</a> 
						</div>
					</#list>
				</#if>
				</div>
				</div>
				<script>
				$(function() {
				  $(".hot_scrollable").scrollable();
				});
				</script>
				</div>
				</div>
				<!--//right-->
			</div>
			<div class="mainoneGreen ClearBoth publicBorder">
				<#if onePoster?? >
					<a href="${onePoster.interlinkage}" class="FontSize18">
						${onePoster.synopsis}
					</a>									
				</#if>
			</div>
			<!--第一广告位-->
			<figure class="nickShoeBox publicabsolute publicRight">
				<#if broadcasting2?? >
					<a target="_blank" href="${broadcasting2.interlinkage}">
						<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${broadcasting2.broadcastingIamgeUrl}">
					</a>
				</#if>
			</figure>
		</div>
		<div id="mainCenTwo" class="publicMarginTop15 ClearBoth">
			<!--left-->
			<section id="BrandShowcase" class="float-left">
				<!--推荐品牌-->
				<h3 class="publicBorderB_3 BorderColor_Black FontSize16 publicPaddingTB7 ">商品展示</h3>
				<div class="publicrelative publicMarginTB10">
						<#if broadcasting??>
							<a target="_blank" href="${broadcasting.interlinkage}">
								<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${broadcasting.broadcastingIamgeUrl}">
							</a> 
						</#if>
						<#-- 
							<#if brand??>
								<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${brand.brandId}" class=" publicblock publicabsolute BrandShowcase_title publicPaddingTB10 text-center publicTop ColorWhite FontSize16">
									${brand.brandName}
								</a>
							</#if>
						 -->
				</div>
				<#-- 
				<p>
					<div style="height: 3em;word-wrap:break-word;overflow:hidden;">
					<a href="${contextPath}/productInfo/${(productInfo.productId)!''}.html">
						${(productInfo.describle)!''}
					</a>
					</div>
				</p>
				<p class="ColorRed">
					￥&nbsp;&nbsp;
					<span class="FontSize18">${(productInfo.salesPrice)!''}</span>
				</p>
				 -->
			</section>
			<!--//left-->
			<!--center-->
			<section id="Bestselling" 
				class="float-left publicMarginLR10 publicHidden" style="margin:0 0 0 10px;">
				<!-- 热销商品-->
				<h3 class="FontSize16 publicPaddingTB7 publicBorderB_3 BorderColor_Black">热销商品</h3>
				<ul class="Bestselling_ConBox ClearBoth publicPaddingTB10">
					<#if (bestsellingList?size>0)>
						<#list bestsellingList as b>
							<li>
								<a target="_blank" href="${contextPath}/productInfo/${b.productId}.html">
									<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${b.logoImg}">
								</a>
								<p>
									<div style="width:160px;height: 3em;word-wrap:break-word;overflow:hidden;" class="text-left" >
										<a title="${b.productName}" href="${contextPath}/productInfo/${b.productId}.html">
											${b.productName}
										</a>
									</div>								
								</p>
								<p class="ColorRed text-left"> ￥&nbsp;&nbsp;<span class="FontSize18">
									${b.salesPrice}
									</span>
								</p>
							</li>
						</#list>
					</#if>
				</ul>
			</section>
			<!--//center-->
			<!--right-->
			<!--优惠券-->
			<!--//right-->
		</div>
		
		<#if (proCateList?size>0 )>
		<#list proCateList as cm>
			<section class="mainCategoryName_L publicMarginTop15 publicBorderB publicPaddingB7">
				<div class="mainCategoryName_h3div">
					<h3 class="FontSize20 publicPaddingTB10">
						${cm.productTypeName}
					</h3>
				</div>
				<div class="ClearBoth">
					<!--left-->
					<aside class="CategoryName_Logo float-left">
						<div class="CategoryLogo_scrollable publicrelative publicHidden">
							<div class="items">
								<!-- 中间部分推荐品牌 -->
								<#if (cm.brandList?size>0)>
									<#if (cm.brandList?size>10)>
										<div>
										<#list cm.brandList as b >
											<#if (b_index >=0) && (b_index<=4)>
												
													<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${b.brandId}">
															<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${b.brandImageUrl}">
													</a>	
											</#if>
											
										</#list>
										</div>
										<div>
										<#list cm.brandList as b >
											<#if (b_index >=5) && (b_index<=9)>
													<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${b.brandId}">
															<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${b.brandImageUrl}">
													</a>	
											</#if>
										</#list>
										</div>
										<div>
										<#list cm.brandList as b >
											<#if (b_index >=10) && (b_index<=15)>
													<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${b.brandId}">
															<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${b.brandImageUrl}">
													</a>	
											</#if>
										</#list>
										</div>
										
									<#elseif (cm.brandList?size>5)>
										<div>
										<#list cm.brandList as b >
											<#if (b_index >=0) && (b_index<=4)>
												
													<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${b.brandId}">
															<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${b.brandImageUrl}">
													</a>	
											</#if>
											
										</#list>
										</div>
										<div>
										<#list cm.brandList as b >
											<#if (b_index >=5) && (b_index<=9)>
													<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${b.brandId}">
															<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${b.brandImageUrl}">
													</a>	
											</#if>
										</#list>
										</div>
									<#else>
										<div>
										<#list cm.brandList as b >
													<a target="_blank" href="${contextPath}/shopHomePage/gotoShopHomePage.action?brandId=${b.brandId}">
															<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${b.brandImageUrl}">
													</a>	
										</#list>
										</div>
									</#if>
								</#if>
							</div>
							<div class="navi margin-center">
								<a class=""></a> <a class=""></a> <a class=""></a>
							</div>
						</div>
					</aside>
					<!--//left-->
					<!--right-->
					<#if (cm.secondprosceniumCategoryList?size>=4)>
						
						<ul class="mainCategoryNameUl">
						<#list cm.secondprosceniumCategoryList as secondcm>
							<#if (secondcm_index>=0) && (secondcm_index<=3)>
							<li>
								<div class="ic_container">
									<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${secondcm.prosceniumCategoryUrl}">
									<div class="overlay" style="display:none;"></div>
									<div class="ic_caption">
										<p class="ic_category">
											${secondcm.secondProductTypeName}
										</p>
										<h3>
											<div style="height: 1.2em;word-wrap:break-word;overflow:hidden;">
											<a href="${secondcm.interlinkage}">${secondcm.title}</a>
											</div>
										</h3>
										<p class="ic_text">
											${secondcm.synopsis}
										</p>
									</div>
								</div>
							</li>
							</#if>
						</#list>
						</ul>
						
						<ul class="mainCategoryNameUl mainCategoryNameUl02 ">
						<#list cm.secondprosceniumCategoryList as secondcm>
							<#if (secondcm_index>=4) && (secondcm_index<=6)>
							<li>
								<div class="ic_container">
									<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${secondcm.prosceniumCategoryUrl}">
									<div class="overlay" style="display:none;"></div>
									<div class="ic_caption">
										<p class="ic_category">
											${secondcm.secondProductTypeName}
										</p>
										<h3>
											<div style="height: 1.2em;word-wrap:break-word;overflow:hidden;">
														<a target="_blank" href="<s:property value='#secondcm.interlinkage'/>"><s:property
																value="#secondcm.title" /></a>
											</div>
										</h3>
										<p class="ic_text">
											${secondcm.synopsis}
										</p>
									</div>
								</div>
							</li>
							</#if>
						</#list>
						<#else>
						
							<ul class="mainCategoryNameUl">
								<#list cm.secondprosceniumCategoryList as secondcm>
									<li>
										<div class="ic_container">
											<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${secondcm.prosceniumCategoryUrl}">
											<div class="overlay" style="display:none;"></div>
											<div class="ic_caption">
												<p class="ic_category">
													${secondcm.secondProductTypeName}
												</p>
												<h3>
													<div style="height: 1.2em;word-wrap:break-word;overflow:hidden;">
														<a target="_blank"  href="${secondcm.interlinkage}">${secondcm.title}</a>
													</div>
												</h3>
												<p class="ic_text">
													${secondcm.synopsis}
												</p>
											</div>
										</div>
									</li>
								</#list>
								<#list ["1","2","3","4"] as secondcm>
									<#if (cm.secondprosceniumCategoryList?size+secondcm_index<=3)>
									<li>
										<div class="ic_container">
											<img style="width:185px; height: 185px;" src="${contextPath}/common/shop/front/images/mrbj.png" >
											<div class="overlay" style="display:none;"></div>
											<div class="ic_caption">
												<p class="ic_category">
												无
												</p>
												<h3>
													<div style="height: 1.2em;word-wrap:break-word;overflow:hidden;">
														<a target="_blank"  >无</a>
													</div>
												</h3>
												<p class="ic_text">
													无
												</p>
											</div>
										</div>
									</li>
									</#if>
								</#list>
							</ul>
							<ul class="mainCategoryNameUl mainCategoryNameUl02 ">
								<#list ["1","2","3"] as secondcm>
									<#if secondcm_index<=2>
									<li>
										<div class="ic_container">
											<img style="width:185px; height: 185px;"
												 src="${contextPath}/common/shop/front/images/mrbj.png"  >
											<div class="overlay" style="display:none;"></div>
											<div class="ic_caption">
												<p class="ic_category">
													无
												</p>
												<h3>
													<div style="height: 1.2em;word-wrap:break-word;overflow:hidden;">
														<a target="_blank" >无</a>
													</div>
												</h3>
												<p class="ic_text">
													无
												</p>
											</div>
										</div>
									</li>
									</#if>
								</#list>
						
					</#if>
						<li class="publicrelative">
							<ul class="mainCategoryName_SmallUl">
								<#if (cm.productTypeList?size<7)>
									<#list cm.productTypeList as secondpt>
										<li>
											<a target="_blank" href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${secondpt.productTypeId}&orderBy=normal">
												${secondpt.sortName}
											</a>
										</li>
									</#list>
								
								<#else>
									<#list cm.productTypeList as secondpt>
										<#if (secondpt_index>=0) && (secondpt_index<=6)>
										<li>
											<a href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${secondpt.productTypeId}&orderBy=normal">
												${secondpt.sortName}
											</a>
										</li>
										</#if>
									</#list>
								</#if>
							</ul> <a target="_blank" href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${cm.productTypeId}&orderBy=normal" 
							class="publicinline publicabsolute mainCategoryName_Small_J publicRight publicBottom"></a>
						</li>
					</ul>
					<!--//right-->
				</div>
			</section>
		</#list>
		</#if>
		
		<!--第二广告位-->
		<figure class="mainPosterImg publicMarginTB10">
			<#if twoPoster??>
				<a target="_blank" href="${twoPoster.interlinkage}">
					<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${twoPoster.broadcastingIamgeUrl}">
				</a>
			</#if>
		</figure>
		<section id="Recommendations" class="publicMarginBot15">
			<h3 class=" publicblock FontSize20 publicPaddingTB10"></h3>
			<div class=" publicBorder RecommendationsCon publicHidden  publicrelative">
				<a class="Recommendations-browse prev"></a> 
				<a class="Recommendations-browse next"></a>
				<div class="RecommendationsCon_scrollable publicHidden publicrelative" id="RecommendationsCon_scrollable">
					<div class="items">
						<div>
							<ul class="RecommendationsUl ClearBoth">
								<#if (smallFootIamgeList?size>5)>
									<#list smallFootIamgeList as s >
										<#if (s_index>=0) && (s_index <=4)>
										<li><a href="${s.interlinkage}" title="">
											<img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${s.broadcastingIamgeUrl}" alt="">
											</a> <a href="${s.interlinkage}" style="text-align: center; ">
											<div style="height: 1.5em;word-wrap:break-word;overflow:hidden;">
												${s.title}
											</div>
										</a></li>
										</#if>
									</#list>
								
								<#else>
									<#list smallFootIamgeList as s>
										<li><a target="_blank" href="${s.interlinkage}" title=""><img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${s.broadcastingIamgeUrl}" alt="">
											</a> <a target="_blank" href="${s.interlinkage}" style="text-align: center; ">
												<div style="height: 1.5em;word-wrap:break-word;overflow:hidden;">
													${s.title}
												</div>
											</a>
										</li>
									</#list>
								</#if>
							</ul>
						</div>
						<#if (smallFootIamgeList?size > 6)>
							<div>
								<ul class="RecommendationsUl ClearBoth">
									<#list smallFootIamgeList as s >
										<#if (s_index >=5) && (s_index<=9)>
										<li><a target="_blank" href="${s.interlinkage}" title=""><img onerror="this.src='${contextPath}/common/shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${s.broadcastingIamgeUrl}" alt="">
											</a> <a target="_blank" href="${s.interlinkage}" style="text-align: center; ">
												<div style="height: 1.5em;word-wrap:break-word;overflow:hidden;">
													${s.title}
												</div>
											</a>
										</li>
										</#if>
									</#list>
								</ul>
							</div>
						</#if>
					</div>
					<script>
						$(function() {
							$(".RecommendationsCon_scrollable").scrollable();
							$(".three_scrollable").scrollable();
					        $(".CategoryLogo_scrollable").scrollable().navigator();
					        $(".hot_scrollable").scrollable();
						});
					</script>
				</div>
			</div>
		</section>
	</div>
	<script src="${contextPath}/common/shop/front/js/index/jquery.capSlide.js"></script>
	<script>
		$(function(){
        $(".ic_container").capslide();
		});
	</script>
	
	<!-- foot --> 
	<!--Footer-->

	<footer id="mainFooter" class="BackgroundF2Hui publicMarginTop15">
	<div
		class="PublicWidth1004 margin-center publicrelative publicPaddingB7">
		<div class="ClearBoth publicPaddingTB7" style="width:600px;margin-left:180px;">
			<p class="publicinline float-left footerShare_L">
				<a href="http://weibo.com/?c=spr_web_sq_baidub_weibo_t001"
					class="ColorBlue"> <i class=" icon iconShare01 "></i> 关注我们<br>
					新浪微博
				</a> <a href="http://t.sohu.com/new_index" class="ColorBlue"> <i
					class=" icon iconShare02 "></i> 关注我们<br> 搜狐微博
				</a> <a href="http://www.kaixin001.com/" class="ColorBlue"> <i
					class=" icon iconShare03 "></i> 点击我们<br> 开心网
				</a> <a href="http://www.renren.com/" class="ColorBlue"> <i
					class=" icon iconShare04 "></i> 关注我们<br> 人人网
				</a>
			</p>
		</div>
		<div class="ClearBoth publicMarginB10" style="width:900px;margin-left:100px;">
			<section class="footerSection">
			<#list footrtArticleMap?keys as footAtricle>
				
				<section class="footerSection" style="width:120px;margin-left:5px;padding: 10px;">
				<h4 class="FontSize14 publicMarginB10 FontSizeB"
				style="margin-bottom:15px;font-size:16px;font-weight:bold;">
					${footAtricle}
				</h4>
				
					<ul class="footerSectionUl">
					<#list footrtArticleMap[footAtricle] as f>
						<li style="margin-bottom:10px;line-height:15px;">
							&middot;
							<a
								href="${contextPath}/helpFront/gotoFooterArticleList.action?articleId=${f.articleId}">${f.title}
							</a>
						</li>
						</#list>
					</ul>
				
				</section>
			</#list>
			</section>
			<ul class="footerSectionUl">
				<li><span id="getCode">
		 
		 </span></li>
		</div>
		<span  class="erweima publicabsolute">
		</span>
	</div>
	
	</footer>
	<div class="foot">
	    <div class="foot_daohang">
	      <p style="padding-top:20px;">关于我们 | 隐私及安全 | 商务合作 | 联系我们 | 人才招聘 | 友情链接 | 百度统计 </p>
	      <p>版权所有SHOPJSP商城copyright***.com  2013  京IPC 备  100004422号</p>
	      <div class="dibu_img"><img src="${contextPath}/common/shop/front/img/dibu_115.gif" /><img src="${contextPath}/common/shop/front/img/dibu_117.gif" /><img src="${contextPath}/common/shop/front/img/dibu_119.jpg" /></div>
	    </div>
    </div>

	<#--//Footer
	<script>
	var url = window.location.href;
	$("#getCode").html("<img src='https://chart.googleapis.com/chart?chs=100x100&cht=qr&chl="+url+"&chld=L|1&choe=UTF-8' />");
	</script>-->
</body>
</html>