<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<%--<!--产品列表_图片-->--%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品列表图片页</title>
<%@ include file="../include/head.jsp"%>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<style>
div.node_path {font-size:13px; margin:5px 0;}
	.node_path a {color:#777777; font-weight:normal; text-decoration:none;}
	.node_path span {color:#999999; padding:0 0.5em;}
	.node_path a.active {color:#222222;}    
	table.search_filter_table {border:medium none; border-collapse:collapse; border-spacing:0; display: inline-table; width:1204px;}
	table, div {word-break: break-all;}
	table.search_filter_table th { background-color: #EEEEEE; border: 1px solid #DDDDDD; padding: 10px; vertical-align:middle; width: 100px; font-weight: bold;}
	table.search_filter_table td { border: 1px solid #DDDDDD; padding: 10px;}
	.summary_list { clear: both;}
	.summary_list .split_list {height:auto; width: 100%;}
	.summary_list .split_list ul.node_list { padding-bottom: 0;position: relative;}
	.summary_list .node_list li { background-image: none; font-size: 1em; margin-bottom: 4px;margin-right:10px;}
	.split_line5 ul.node_list li {float: left; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;width: 90px;}
	.node_list img { border: medium none; color: #FFFFFF;vertical-align: middle;}
	.summary_list li span.cnt {color: #777777; font-size: 0.8em; padding-left: 0;}
	.summary_list  a {text-decoration: none;color:#404040;}
	.summary_list  a:hover{color:red;}
	.summary_list li .filter-attr-off { border: 1px solid #D9D9D9; color: #404040;}
	.summary_list li .filter-attr-on{ -moz-border-bottom-colors: none;-moz-border-left-colors: none;-moz-border-right-colors: none;-moz-border-top-colors: none;border: 1px solid #D9D9D9;border-bottom:none; color: #404040;}
	.summary_list LI .filter-attr-on {	BORDER-TOP: #d9d9d9 1px solid; BORDER-RIGHT: #d9d9d9 1px solid; BORDER-BOTTOM: #d9d9d9 1px; COLOR: #404040; BORDER-LEFT: #d9d9d9 1px solid; Z-INDEX: 2}
	.summary_list li .filter-attr { background-color: #FFFFFF; color: #404040; display: block; float: left; height: 23px; line-height: 22px; padding: 0 18px 0 10px;position: relative; text-decoration: none !important;white-space: nowrap;}
	.summary_list li .filter-attr .arrow-off {background-position: -720px 0;background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/arrow-down.png") no-repeat; display: block;height: 10px;position: absolute; right: 3px;top: 10px; width: 10px;}
	.summary_list li .filter-attr .arrow-on{background-position: -720px 0;background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/arrow-right.png") no-repeat; display: block;height: 10px;position: absolute; right: 3px;top: 10px; width: 10px;}
	.summary_list li { float: left; margin: 0 10px 10px 0;  position: relative;}
	.summary_list .split_more_list { overflow: hidden; width: 100%;}
	.summary_list li .filter-attr-expand {background-color: #FFFFFF; border: 1px solid #D9D9D9; display: none; padding: 10px; position: absolute;top: 23px;width: 500px; z-index: 1;}
	.summary_list li .filter-attr-expand li {float: left;margin: 3px 15px 5px 0;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;width: 142px;}
	.checkbox{vertical-align: middle;}
	.goods-mark-hot{background-position: -513px -100px;}
	.goods-mark-imglist{position: absolute;right: 4px;top: 0;}
	.goods-mark{background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/product_module.png") no-repeat scroll -514px -90px rgba(0, 0, 0, 0);
				color: #fff;
				display: block;
				font-size: 12px;
				font-weight: normal;
				height: 40px;
				line-height:40px;
				padding-top: 12px;
				text-align: center;
				width: 42px;}
	.paxuselect{color: red;};
</style>
	<script type="text/javascript">
	$().ready(function(){
		//Lazy Load 延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn",
			threshold : 400
		});
	});
	//初始化
	$(function(){
		//点击分类选中
		var id = "<s:property value="tuanProductTypeId"/>";
		if(id!=null&&id!=""){
			$("#type_"+id).css("color","red");
		}else{
			$("#type").css("color","red");
		}
		//点击排序选中
		var orderBy='${orderBy}';
		if(orderBy != ""){
			$("#"+orderBy+"_px").addClass("paxuselect");
		}else{
			$("#normal_px").addClass("paxuselect");
		}
	 });    
	//对商品进行排序操作
	function orderBy(parma){
		window.location.href="${pageContext.request.contextPath}/tuan/gotoTuanHome.action?tuanProductTypeId=${tuanProductTypeId}&orderBy="+parma+"";
	}
	</script>
</head>

<body>
	<%@ include file="../include/header.jsp"%>
	<div class="margin-center PublicWidth1004">
	<div class="ClearBoth" style="float:left;">
		<div class="category_main-w search_page_layout_type1" style="float: left; width:100%;margin-bottom:30px;">
			<table class="search_filter_table_w search_filter_table" style="margin-top: 0px;">
				<tr id="ct" style="height:1px;">
					<th style="width:100px;">分类</th>
						<td>
							<div id="" class="summary_list split_line5" style="height: 40px; line-height:40px;">
								<div class="split_list">
									<ul class="node_list">
									    <li class="" style="overflow: visible;width:115px;">
									       <a id="type" href="${pageContext.request.contextPath}/tuan/gotoTuanHome.action">全部</a>
									    </li>
										<s:iterator  value="tuanProductTypeList">
											<li class="" style="overflow: visible;width:115px;">
												<a id="type_<s:property value="tuanProductTypeId"/>" href="${pageContext.request.contextPath}/tuan/gotoTuanHome.action?tuanProductTypeId=<s:property value="tuanProductTypeId"/>" style="text-decoration: none;">
													<s:if test="sortName.length()>9">
														<s:property value="sortName.substring(0,6)"/>...
													</s:if>
													<s:else>
														<s:property value="sortName"/>
													</s:else>
												</a>
											</li>
										</s:iterator>
									</ul>
								</div>
							</div>
						</td>
				</tr>
        	</table>
        </div>    
			<!--right-->
			<div id="rightBox" class="publicHidden"  style="cursor:default;width:1204px;">
				<!-- 排序 -->
				<aside>
					<div class="FontSize13 publicBorder publicMarginBot15 produstsList_screen ClearBoth" style="background-color: #f2f2f2;width:1182px; padding:12px 10px 11px 10px;">
						<a id="normal_px" href="javascript:orderBy('normal');">综合</a> 
						<a id="salesPriceAsc_px" href="javascript:orderBy('salesPriceAsc');" class="price_up">价格</a> 
						<a id="totalSales_px" href="javascript:orderBy('totalSales');" class="price_down">销量</a> 
					</div>
				</aside>
			<!--Proitems-->    <!-- 商品 列表 -->
			<div class="ProitemBox publicMarginTop15" >
	     		<form id="formModule" action="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=<s:property value="productTypeId"/>&orderBy=<s:property value='orderBy'/>&brandParams=<s:property value='brandParams'/>&specificationValueId=<s:property value='specificationValueId'/>&brandId=<s:property value='brandId'/>&level=<s:property value="level"/>" method="post"> 
					 <ul class="WishListUl ClearBoth">
						<s:if test="tuanProductList.size>0">
						<s:iterator value="tuanProductList" var="productInfo" status="s">
							<li style="height: 335px;margin:0px 8px 0 7px; position: relative;"><a target="_blank"  style="display:block;width:220px;height:220px;" href="${pageContext.request.contextPath}/tuan/<s:property value="#productInfo.productId" />.html">
								<img style="width:220px;height:220px;"  title="<s:property value="#productInfo.productFullName"/>" class="lazy"
									src="${fileUrlConfig.visitFileUploadRoot}<s:property value='logoImg'/>" data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#productInfo.logoImg"/>"  style="height:220px;width:220px;"/>
								</a>
								<p style="height: 38px;overflow: hidden; margin-top:7px;">
									<a target="_blank" title="<s:property value="#productInfo.productFullName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='#productInfo.productId'/>"><s:property value="#productInfo.productFullName" /></a>
								</p>
								<div class="ColorRed" style=" margin-left:-4px;  padding-bottom:8px;">
								    <span style="float:right;">已抢<s:property value="#productInfo.bought" />件</span>
									<span class="FontSize18">￥<s:property value="#productInfo.price" /></span>
								</div>
								 <a title="<s:property value="#productInfo.shopName" />" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="#productInfo.shopInfoId"/>" class="ColorBlue publicinline"><i style="background-position:left top;  width:25px !important;" class="icon iconShop01"></i>
								<s:property value="#productInfo.shopName" /></a>
							</li>
						</s:iterator>
						</s:if>
				   	 </ul>
				
					<!--分页 start-->
					<s:if test="tuanProductList.size>0">
						<div class="pageList strong ClearBoth">
						     <jsp:include page="../../util/splitPage.jsp" />
						</div>
					</s:if>
					<!--分页 end-->
						
				</form>	
			</div>
			</div>
			<!--//Proitem-->
			<!--//right-->
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
