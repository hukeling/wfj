<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="../include/head.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- 店铺目录 -->
	<title>${application.homekeybook['homeSeoTitle2'][0].value}</title>
	<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css" type="text/css" rel="stylesheet"/>
	<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet"/>
	<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/youhuijuan_channel.css" type="text/css" rel="stylesheet"/>
	<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/brand_channel.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/sales_style.css"/>
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/WookMark.css"/>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
	
	<script type="text/javascript" >
		$().ready(function(){
			//Lazy Load 延迟加载
			$("img.lazy").lazyload({
				effect : "fadeIn"
			});
		});
	// 初始化分类css
		$(function(){
			$("div").removeClass("category");
			$("front").removeAttr("style");
			var v='${shopCategoryId}';
			if(v==""){
				$("#category_All").addClass("category");
				$("#front_All").attr("style","color:#fff;");
			}else{
				$("#category_"+v).addClass("category");
				$("#front_"+v).attr("style","color:#fff;");
			}
		});
		// 初始化省市商城三个参数
		$(function(){
			
		});
	</script>
<!--Shop directory contents-->
	<style type="text/css" >
		.codeCatalog{width:1210px;height:auto;overflow:hidden; margin-bottom:15px;}
		.singleDirectory{padding:10px 0 0 8px;width:394px;height:175px;margin-right:-1px;float:left;}
		.connectEnlarge{ display:block;float:left;width:160px;height:160px;}
		.connectEnlarge img{border:1px #f0f0f0 solid;}
		.dtails{width:221px;float:left; margin-left:13px;color:#999;}
		.dtails a.titledirectory{ display:block;color:#333;font-size:18px;}
		.titleBottom {overflow:hidden;height:28px;padding-top:13px;}
		.titleBottom span,.titleBottom a{ display:block; float:left;}
		.titleBottom a.enterYin{color:#0099FF; text-decoration:underline;}
		.titleBottom span.levelStar{ margin:0 7px;}
		.titleBottom span.levelStar a{width:13px;height:25px;padding-top:0px;}
		.figureShows{}
		.figureShows a{ margin-right:3px;width:47px;height:47px;}
		.figureShows a:visited{color:#ffffff;}
		.figureShows a img{width:47px;height:47px;}
		.figureShows a img{width:47px;height:47px;}
		.category{background: #990000;}
		.frontClass{color:#ffffff;}
		.biaoge_head{color:#ffffff;}
	</style>
</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004" style="height: auto;">
	<!--mainClass-->
	<div class="main_content" style="margin:0 auto;">
		<!-- 标题 begin -->
		<div class="home">
			<div class="home_img"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/u51_normal.png" style="padding-top:4px;" /></div>
			<div class="lujing">
				<a href="${pageContext.request.contextPath}/">首页</a> &gt;线下商场
			</div>
		</div>
		<!-- 标题 end -->
		<!-- 省-市-商城查询块 begin -->
		<div class="category_main-w search_page_layout_type1" style="float: left; width:100%;margin-bottom:10px;margin-top:10px;">
				<table class="search_filter_table_w search_filter_table" style="margin-top: 0px;">
						<s:if test="#request.regionLocationList.size!=0"> 
							<tr id="ct" style="height:1px;">
								<th style="width:100px; background-color: #eeeeee;border: 1px solid #dddddd;font-weight: bold; padding: 10px; vertical-align: middle">州省地区</th>
									<td style="border: 1px solid #dddddd;padding: 10px">
										<div id="" class="summary_list split_line5" style="height: 40px; line-height:40px;" flag="c">
											<div class="split_list">
												<ul class="node_list">
													<!-- <a href="javascript:showMoreCategoryInfo();" style="text-decoration: none;">更多>></a> -->
													<s:iterator value="#request.regionLocationList" var="regionLocations">
														<!-- <li class="" style="overflow: visible;width:115px;"> -->
														<li class="" style="text-overflow:ellipsis;overflow: hidden;white-space: nowrap;width:150px;height:30px;float:left;">
															<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/categoryListmark.gif" width="5" height="5" />
															<s:if test="regionLocation!=null&&regionLocation==#regionLocations.areaId">
																<a style="background-color: #dddddd;" title="<s:property value='#regionLocations.name'/>" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops&regionLocation=<s:property value='#regionLocations.areaId'/>&strShopCategoryId=<s:property value='strShopCategoryId' />" style="text-decoration: none;">
																	<s:property value="#regionLocations.name"/>
																</a>
															</s:if>
															<s:else>
																<a title="<s:property value='#regionLocations.name'/>" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops&regionLocation=<s:property value='#regionLocations.areaId'/>&strShopCategoryId=<s:property value='strShopCategoryId' />" style="text-decoration: none;">
																	<s:property value="#regionLocations.name"/>
																</a>
															</s:else>
															</li>
													</s:iterator>
												</ul>
											</div>
										</div>
									</td>
							</tr>
						</s:if>
						<s:if test="#request.cityList.size!=0">
							<tr id="ct" style="height:1px;">
								<th style="width:100px; background-color: #eeeeee;border: 1px solid #dddddd;font-weight: bold; padding: 10px; vertical-align: middle">市区</th>
									<td style="border: 1px solid #dddddd;padding: 10px">
										<div id="" class="summary_list split_line5" style="height: 40px; line-height:40px;" flag="c">
											<div class="split_list">
												<ul class="node_list">
													<!-- <a href="javascript:showMoreCategoryInfo();" style="text-decoration: none;">更多>></a> -->
													<s:iterator  value="#request.cityList" var="citys">
														<li class="" style="text-overflow:ellipsis;overflow: hidden;white-space: nowrap;width:150px;height:30px;float:left;">
															<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/categoryListmark.gif" width="5" height="5" />
															<s:if test="city!=null&&city==#citys.areaId">
																<a style=" background-color: #dddddd;" title="<s:property value='#citys.name'/>" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops&city=<s:property value='#citys.areaId'/>&regionLocation=<s:property value='#citys.parentId'/>&strShopCategoryId=<s:property value='strShopCategoryId' />" style="text-decoration: none;">
																	<s:property value="#citys.name"/>
																</a>
															</s:if>
															<s:else>
																<a title="<s:property value='#citys.name'/>" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops&city=<s:property value='#citys.areaId'/>&regionLocation=<s:property value='#citys.parentId'/>&strShopCategoryId=<s:property value='strShopCategoryId' />" style="text-decoration: none;">
																	<s:property value="#citys.name"/>
																</a>
															</s:else>
														</li>
													</s:iterator>
												</ul>
											</div>
										</div>
									</td>
							</tr>
						</s:if>
						<s:if test="#request.shopsListMap!=null&&#request.shopsListMap.size>0">
							<tr id="ct" style="height:1px;">
								<th style="width:100px;  background-color: #eeeeee;border: 1px solid #dddddd;font-weight: bold; padding: 10px; vertical-align: middle">线下商场</th>
									<td style="border: 1px solid #dddddd;padding: 10px">
										<div id="" class="summary_list split_line5" style="height: 40px; line-height:40px;" flag="c">
											<div class="split_list">
												<ul class="node_list">
													<!-- <a href="javascript:showMoreCategoryInfo();" style="text-decoration: none;">更多>></a> -->
													<s:iterator value="#request.shopsListMap" var="shops">
														<li class="" style="overflow: visible;width:115px;height:30px;float: left;">
															<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/categoryListmark.gif" width="5" height="5" />
															<s:if test="#shops.shopsId==strShopsId">
																<a style=" background-color: #dddddd;" title="<s:property value='#shops.shopsName'/>" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops&city=<s:property value='#shops.city'/>&regionLocation=<s:property value='#shops.regionLocation'/>&strShopsId=<s:property value='#shops.shopsId'/>&strShopCategoryId=<s:property value='strShopCategoryId' />" style="text-decoration: none;">
																	<s:property value="#shops.shopsName"/>
																</a>
															</s:if>
															<s:else>
																<a title="<s:property value='#shops.shopsName'/>" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops&city=<s:property value='#shops.city'/>&regionLocation=<s:property value='#shops.regionLocation'/>&strShopsId=<s:property value='#shops.shopsId'/>&strShopCategoryId=<s:property value='strShopCategoryId' />" style="text-decoration: none;">
																	<s:property value="#shops.shopsName"/>
																</a>
															</s:else>
															<s:if test="#shops.shopInfoIdCount!=null">
																<span class="cnt">(<s:property value="#shops.shopInfoIdCount"/>)</span>
															</s:if>
															<s:else>
																<span class="cnt">(0)</span>
														</s:else>
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
		<!-- 省-市-商城查询块 end -->
		<!-- 右则所有店铺 begin -->
		<!-- <div class="make_hold"> -->
			<%-- <img src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#request.homeAdvertisement.imageUrl'/>" style="float:left;" width="795" height="193"/> --%>
			<!-- <div class="biaoge" style="height:auto; width:399px; border-top:1px #ccc solid; float: left;"> -->
				<div class="biaoge_head" style="height:auto; border-bottom:0; float: left; left;width:140px;">
					<div id="category_All" class="biaogeh_left biaoge_head" style=" width: 130px;background: #d01743; border-left: 1px #ccc solid;">
						<a style="text-decoration:none; color: #fff;" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops&city=<s:property value='city'/>&regionLocation=<s:property value='regionLocation'/>&strShopsId=<s:property value='strShopsId'/>">
							<font id="front_All">店铺全部分类</font>
						</a>
					</div>
					<ul style="height:auto; width: 140px; padding-left:3px;">
						<s:iterator value="shopTypeList" var="shopTypes">
							<li class="" style="overflow: visible;width:70px;height:30px;float: left; text-align: center;">
								<s:if test="#shopTypes.shopCategoryId==strShopCategoryId">
									<a style=" background-color: #d01743;text-decoration:none;" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?strShopCategoryId=<s:property value='#shopTypes.shopCategoryId' />&headerType=shops&city=<s:property value='city'/>&regionLocation=<s:property value='regionLocation'/>">
										<font id="front_<s:property value='#shopTypes.shopCategoryId'/>"><s:property value="#shopTypes.shopCategoryName" /></font>
									</a>
								</s:if>
								<s:else>
									<a style="text-decoration:none;" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?strShopCategoryId=<s:property value='#shopTypes.shopCategoryId' />&headerType=shops&city=<s:property value='city'/>&regionLocation=<s:property value='regionLocation'/>">
										<font id="front_<s:property value='#shopTypes.shopCategoryId'/>"><s:property value="#shopTypes.shopCategoryName" /></font>
									</a>
								</s:else>
							</li>
						</s:iterator>
					</ul>
							<%-- <div style="border-left: 1px #ccc solid;" class="biaogeh_left biaoge_head" id="category_<s:property value='shopCategoryId'/>">
								<a style="text-decoration:none;" href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?shopCategoryId=<s:property value="shopCategoryId" />&headerType=shops">
									<font id="front_<s:property value='shopCategoryId'/>"><s:property value="shopCategoryName" /></font>
								</a>
							</div> --%>
				</div>
			<!-- </div>  -->
		<!-- </div> -->
		<!-- 右则所有店铺 end -->
		<!-- <div class="toulan"></div> -->
		<%-- 
		<!-- 店铺排序 begin-->
		<aside>
			<div class="FontSize13 gradient publicBorder publicPadding10 publicMarginBot15 produstsList_screen publicrelative ClearBoth">
				<a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId=<s:property value="shopInfoId"/>&orderBy=putSaleDate&brandParams=<s:property value='brandParams'/>&minPrice=<s:property value='minPrice'/>&maxPrice=<s:property value='maxPrice'/>">最新上架</a> 
				<a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId=<s:property value="shopInfoId"/>&orderBy=totalSales&brandParams=<s:property value='brandParams'/>&minPrice=<s:property value='minPrice'/>&maxPrice=<s:property value='maxPrice'/>">销量</a>
				<a href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?shopCategoryId=<s:property value="shopCategoryId"/>&orderByType=" class="price_up">信誉</a> 
				<a href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?shopCategoryId=<s:property value="shopCategoryId"/>&orderByType=" class="price_down">信誉</a> 
			</div>
		</aside>
		 --%>
		<!-- 店铺排序 end-->
		<!-- 展示店铺 begin -->
		<div class="margin-center PublicWidth1004" style="border: 1px solid #dddddd;padding: 5px;width: 1035px; float: right;">
			<div class="codeCatalog" >
				<s:if test="mapList.size>0">
					<s:iterator value="mapList">
						<div class="singleDirectory" style="padding: 2px; width: 340px;">
							<!-- 大图片 -->
							<a class="connectEnlarge"style="height:140px; width:140px;" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value='shopInfoId'/>" target="_blank">
								<img style="height:140px; width:140px;" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" class="lazy" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoUrl'/>"/>
							</a>
							<!-- 右侧信息 -->
							<div class="dtails" style="width: 160px;">
								<a class="titledirectory" style="text-decoration: none;" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value='shopInfoId'/>" target="_blank"><s:property value="shopName"/></a>
								<div class="titleBottom" >
									<span>信誉</span>
									<span class="levelStar">
										<s:if test="photoNum==0">
											<s:iterator begin="1" end="5">
												<a href="#"><img style="width:15px;height:15px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/jinpai.png"/></a>
											</s:iterator>
										</s:if>
										<s:else>
											<s:iterator begin="1" end="photoNum" >
												<a href="#"><img style="width:15px;height:15px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/jinpai.png"/></a>
											</s:iterator>
											<s:iterator  begin="1" end="photoNum2">
												<a href="#"><img style="width:15px;height:15px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/huipai.png"/></a>
											</s:iterator>
										</s:else>
									</span>
									<a class="enterYin" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value='shopInfoId'/>" target="_blank">进入>></a>
								</div>
								<span>店主 : <s:property value="customerName"/></span>
								<div class="figureShows">
									<s:iterator value="list" status="status">
									<s:if test="#status.index<3">
										<a target="_blank" title="<s:property value='productFullName'/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId'/>.html">
											<img class="lazy" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>" style="height:47px; width:47px;"/>
										</a>
									</s:if>
									</s:iterator>
								</div>
							</div>
						</div>
					</s:iterator>
				</s:if>
			</div>
			<!--分页 begin-->
			<form id="formModule" action="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?shopCategoryId=<s:property value="shopCategoryId" />&headerType=shops" method="post">
			<input type="hidden" id="strShopsId" name="strShopsId" value="${strShopsId}"/>
			<input type="hidden" id="city" name="city" value="${city}"/>
			<input type="hidden" id="regionLocation" name="regionLocation" value="${regionLocation}"/>
			<input type="hidden" id="strShopCategoryId" name="strShopCategoryId" value="${strShopCategoryId}"/>
			<s:if test="mapList.size>0">
				<div class="pageList strong ClearBoth">
					<jsp:include page="../../util/splitPage.jsp" />
				</div>
			</s:if>
			</form>
			<!--分页 end-->
		</div>
		<!-- 展示店铺 end -->
		
	</div>
	<!--//mainClass-->
</div>

<%@ include file="../include/footer.jsp"%>
</body>
</html>
