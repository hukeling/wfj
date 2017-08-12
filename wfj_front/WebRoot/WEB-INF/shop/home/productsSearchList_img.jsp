<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<%--<!--产品搜索列表_图片-->--%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品搜索列表_图片</title>
<%@ include file="../include/head.jsp"%>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
	<script type="text/javascript">
		$().ready(function(){
			//Lazy Load 延迟加载
			$("img.lazy").lazyload({
				effect : "fadeIn",
				threshold : 400
			});
		});
	    $(function(){
	    	var whichOrderBy = $("#orderType").val();
	    	if(whichOrderBy!=null && whichOrderBy!=""){
	    		$("#"+whichOrderBy).css("color","red");
	    	}
	    	//价格检索提交按钮
			$("#buttonPrice").bind("click",function(){  
		    	if($("#minPriceValue").val()==""||$("#maxPriceValue").val()==""){
		    		lalert("提示","请输入价格！");
		    		<%-- 请输入价格:Please input the price  --%>
		    	}else if(eval($("#minPriceValue").val())>=eval($("#maxPriceValue").val())){
		    		lalert("提示","请输入正确的价格区间！");
		    		<%-- 请输入正确的价格范围:Please input the price  --%>
		    		//return false;
		    	}else if($("#minPriceValue").val()!=null&&$("#maxPriceValue").val()!=null&&eval($("#minPriceValue").val())<eval($("#maxPriceValue").val())){
		    		$("#minPrice").val($("#minPriceValue").val());
		    		$("#maxPrice").val($("#maxPriceValue").val());
		    		$("#keyword").val($("#keywordxxx").val());
		    		//将form1表单中的数据进行编码
		    		submitSearch();
		    		
		    	}
			});
	  });
		var $productTypeId = "${productTypeId}";
	    //delete所有查询条件(不包含排序)
	    function clear_all_query_conditions(){
	    	$("#select_screen").css("display","none");
	    	$("[type='checkbox']").removeAttr("checked");//取消选中
	    	window.location="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action?orderType=<s:property value='orderType'/>&viewType=<s:property value='viewType'/>&pager.keyword=<s:property value='pager.keyword'/>&productTypeId=<s:property value='productTypeId'/>";
	      }
	    //左侧复选框操作
	    function checkBoxChange(){
	    	//获得排序参数
	    	var f=$("#flag").val();
	    	var min=$("#min").val();
	    	var max=$("#max").val();
	    	//查询品牌参数
	    	var brandValue=[];
			$("input[name='BrandCheckbox']:checked").each(function(){
				brandValue.push($(this).val());
			});
			window.location.href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?brandParams="+brandValue+"&productTypeId="+($productTypeId = null ? $productTypeId : "")+"&flag="+f+"&minPrice="+min+"&maxPrice="+max;
	    }
	    //单个条件的删除
	     function clearOne(params){//params为删除的参数 (color,brand,price)等
	    	//获得排序参数
	    	var f=$("#flag").val();
	    	var min=$("#min").val();
	    	var max=$("#max").val();
    		$("#"+params).css("display","none");
    		$("input[name='"+params+"Checkbox']:checked").removeAttr("checked");
    		if(params=="Price"){
    			$("#minPrice").val("");
    			$("#maxPrice").val("");
    			min="";
    			max="";
    		}
	    	if($("input[type='checkbox']:checked").length == 0){//如果没有选中的checkbox 则清除所有左侧查询条件
	    		$("#select_screen").css("display","none");
	    		window.location="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action?orderType=<s:property value='orderType'/>&viewType=<s:property value='viewType'/>&pager.keyword=<s:property value='pager.keyword'/>&productTypeId=<s:property value='productTypeId'/>&maxPrice="+max+"&minPrice="+min;
	    		//window.location="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+($productTypeId = null ? $productTypeId : "")+"&flag="+f+"&minPrice="+min+"&maxPrice="+max;
	    	}else{
		    	//查询品牌参数
		    	var brandValue=[];
				$("input[name='BrandCheckbox']:checked").each(function(){
					brandValue.push($(this).val());
				});
				//window.location.href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?specificationsParams="+specificationsValue+"&brandParams="+brandValue+"&productTypeId="+($productTypeId = null ? $productTypeId : "")+"&flag="+f+"&minPrice="+min+"&maxPrice="+max;
				window.location="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action?orderType=<s:property value='orderType'/>&viewType=<s:property value='viewType'/>&pager.keyword=<s:property value='pager.keyword'/>&productTypeId=<s:property value='productTypeId'/>&maxPrice="+max+"&minPrice="+min+"&brandParams="+brandValue;
	    	}
	    }
	    //全部结果检索
	    function searchAll(){
	    	$("#pageNumber").val(1);
    		$("#productTypeId").val("");
    		$("#brandParams").val("");
    		$("#minPrice").val("");
    		$("#maxPrice").val("");
	    	submitSearch();
	    }
	    //图片或列表展示检索
	    function viewTypeSearch(viewType){
	    	$("#viewType").val(viewType);
	    	submitSearch();
	    }
	    //通过排序检索
	    function orderBySearch(orderBy){
	    	$("#orderType").val(orderBy);
	    	submitSearch();
	    }
	    //通过分类检索
	    function typeSearch(typeId){
	    	$("#productTypeId").val(typeId);
	    	$("#keyword").val($("#keywordx").val());
	    	submitSearch();
	    }
	   	//搜索查询表单编码并提交
	    function submitSearch(){
	    	$("#pageNumber").val(escape($("#pageNumber").val()));
    		$("#productTypeId").val(escape($("#productTypeId").val()));
    		$("#keyword").val(escape($("#keyword").val()));
    		$("#brandParams").val(escape($("#brandParams").val()));
    		$("#viewType").val(escape($("#viewType").val()));
    		$("#orderType").val(escape($("#orderType").val()));
    		$("#minPrice").val(escape($("#minPrice").val()));
    		$("#maxPrice").val(escape($("#maxPrice").val()));
    		$("#formModule").submit();
	    }
	    function changePage(pageIndex){
	     	document.getElementById("pageNumber").value=pageIndex;
	     	submitSearch();
	     }
</script>

</head>

<body>
	<input type="hidden" id="keywordx" value="<s:property value="pager.keyword"/>"/>
	<%@ include file="../include/header.jsp"%>
	<div id="mbxBox" class="FontSize13 PublicWidth1004 margin-center ">
	<span class="ColorBlack">当前位置:</span>    <!-- 导航条 -->
		<a href="javascript:;" onclick="searchAll()" >
			全部结果
		</a>
		&gt;
		"<s:property value="pager.keyword" />"
    </div>
	<div class="margin-center PublicWidth1004">
		<div class="ClearBoth">
			<!--left-->
			<aside id="leftBox" class="publicBorder float-left BackgroundF8Hui">
	  <h3 class="publicPadding10 ColorRed publicBorderB_3red FontSize15">分类</h3><!-- 左侧列表 -->
	 	<span class="FontSize13 FontSizeB publicblock " style="color:#d01743; padding:10px 0 0 10px;" >所有分类</span>
	       <div class="FontSize13 FontSizeB publicblock">
	            <ul>
	             <s:iterator value="productToTypeList" id="typeMap" >
					<li style="height:28px; line-height: 28px; border-bottom: 1px #dcdcdc dashed;"> &nbsp;<a onclick="typeSearch(<s:property value="#typeMap.productTypeId"/>)" href="javascript:;" style="text-decoration: none;">
						<s:property value="#typeMap.sortName"/></a>
						 &nbsp; (<s:property value="#typeMap.productInfoTypeTotal"/>) <span style="color:#999;"></span> </li>
	              </s:iterator>
	            </ul>
           </div>
	        
				<h3 class="publicPadding10 ColorRed publicBorderB_3red FontSize15">条件查询</h3>
				<div class="sortBy publicMarginBot15 publicMarginTop15">
					<span class="FontSize13 FontSizeB publicblock">价格:</span>
					<div class="PriceInput publicMarginBot15 ClearBoth">
					<input id="min" type="hidden" value="<s:property value="minPrice"/>"/>
					<input id="max" type="hidden" value="<s:property value="maxPrice"/>"/>
					<form id="form1xxx" name="form1" method="get" action="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action" >
						<input name="viewType" type="hidden" id="viewTypexxx" value="<s:property value="viewType"/>"/>
						<input name="orderType" type="hidden" id="orderTypexxx" value="<s:property value="orderType"/>"/>
						<input name="pager.keyword" type="hidden" id="keywordxxx" value="<s:property value="pager.keyword"/>"/>
						
						<input name="minPrice" type="text" style="width: 50px;height: 18px;" id="minPriceValue" value="<s:property value="minPrice"/>"/>
						<span class="publicblock FontSize13 float-left publicPaddingTB7" style="padding:5px 3px 0 3px; margin:0 5px;">至</span>
						<input name="maxPrice" type="text" style="width: 50px;height: 18px;" id="maxPriceValue" value="<s:property value="maxPrice"/>"/> 
						<input style=" margin-right:15px; height:20px;" name="buttonPrice" type="button" id="buttonPrice"  class="BackgroundPurple SearchButton02 publicNoBorder ColorWhite1 float-right publicMarginL10" value="提交"/>	
					</form>
					</div>
				</div>
				
				<!-- 推荐品牌 -->
				<h3  class="publicPadding10 ColorRed publicBorderB_3red FontSize15 publicMarginTop15">推荐品牌</h3>
				<div class="FeaturedBrands_img publicMarginBot15 publicPaddingLR10" style="padding-top:10px;" >
				<s:if  test="#request.brandList.size>0&&#request.brandList.size<5">
					 <ul>
						 <s:iterator value="#request.brandList" var="brand">
						 <li>
							<a href="javascript:brandLink('<s:property value='#brand.brandName'/>');">
							   <img style="height:100px; width:200px;" class="lazy" src="${fileUrlConfig.visitFileUploadRoot}<s:property value='brandBigImageUrl'/>"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#brand.brandBigImageUrl"/>" /></a>
						 </li>
						 </s:iterator>
					 </ul>
				</s:if>
				<s:elseif  test="#request.brandList.size>=5">
					<ul>
						 <s:iterator value="#request.brandList" var="brand" begin="0" end="4">
						 <li>
							<a href="javascript:brandLink('<s:property value='#brand.brandName'/>');">
							   <img style="height:100px; width:200px;" class="lazy" src="${fileUrlConfig.visitFileUploadRoot}<s:property value='brandBigImageUrl'/>"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#brand.brandBigImageUrl"/>" /></a>
						 </li>
						 </s:iterator>
				   </ul>
				</s:elseif>
				<s:else>
				 <div>
				   <span>
				      <font style="font-size:12px;">当前分类下没有品牌！</font>
				   </span>
				 </div>
				</s:else>
					<%-- <p class="publicMarginTop15">
						<a href="${pageContext.request.contextPath}/brandDirectory/gotoBrandDirectoryPage.action" class="ColorBlue">所有品牌 >></a>
					</p> --%>
				</div>
			</aside>
			<!--//left-->
			<input id="flag" type="hidden" value="<s:property value='flag'/>"/>
			<!--right-->
			<div id="rightBox" class="float-right publicHidden">
			<div style="font-size: 24px; height:38px; line-height:32px;">
				查询 "<span style="color: red;"><s:property value="pager.keyword"/></span>",  查询 <span style="color: red;"><s:property value="pager.totalCount"/></span> 结果
			</div>
			<!-- 排序 -->
			<aside>
				<div class="FontSize13 gradient publicBorder publicPadding10 publicMarginBot15 produstsList_screen ClearBoth">
					<a id="putSaleDateDesc" onclick="orderBySearch('putSaleDateDesc')" href="javascript:;">最近上架</a> 
					<a id="totalSalesDesc" onclick="orderBySearch('totalSalesDesc')" href="javascript:;">热销</a> 
					<a id="salesPriceAsc" onclick="orderBySearch('salesPriceAsc')" href="javascript:;" class="price_up">价格</a> 
					<a id="salesPriceDesc" onclick="orderBySearch('salesPriceDesc')" href="javascript:;" class="price_down">价格</a> 
					<a onclick="viewTypeSearch('tableType')" href="javascript:;" class="change_list">列表</a> 
					<a onclick="viewTypeSearch('pictureSearch')" href="javascript:;" class="change_Gallery_select ColorRed">图片</a>
				</div>
			</aside>
              <!-- 查询的条件显示 -->
              <s:if test="mapParams.size>0">
					<div id="select_screen" class="select_screen" >
					<s:iterator value="mapParams" id="map">
						<span> <s:property value="#map.key"/> : <s:property value="#map.value"/> <a onclick="clearOne('<s:property value="#map.key"/>');" title="Remove Auction" class="remove"></a></span>
					</s:iterator>
						 <a href="javascript:clear_all_query_conditions();" class="Clear_All" >全部清空</a>
					</div> 
				</s:if> 
		<div class="ProitemBox publicMarginTop15" >
     		<form id="formModule" action="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action" method="get"> 
				<input id="pageNumber" name="pager.pageNumber" type="hidden" value="<s:property value="pager.pageNumber"/>" />
				<input name="productTypeId" type="hidden" id="productTypeId" value="<s:property value="productTypeId"/>"/>
				<input name="pager.keyword" type="hidden" id="keyword" value="<s:property value="pager.keyword"/>"/>
				<input name="brandParams" type="hidden" id="brandParams" value="<s:property value="brandParams"/>"/>
				<input name="viewType" type="hidden" id="viewType" value="<s:property value="viewType"/>"/>
				<input name="orderType" type="hidden" id="orderType" value="<s:property value="orderType"/>"/>
				<input id="minPrice" name="minPrice" type="hidden" value="<s:property value="minPrice"/>" />
				<input id="maxPrice" name="maxPrice" type="hidden" value="<s:property value="maxPrice"/>" />
				
					<ul class="WishListUl ClearBoth">
						<s:if test="productInfoList.size>0">
							<s:iterator value="productInfoList" var="productInfo" status="s">
								<li style="height:332px; margin:0px 7px; "><a target="_blank"  style="display:block;width:220px;height:220px;" href="${pageContext.request.contextPath}/productInfo/<s:property value="#productInfo.productId" />.html">
									<img style="width:220px;height:220px;"  title="<s:property value="#productInfo.productFullName"/>" class="lazy"
										src="${fileUrlConfig.visitFileUploadRoot}<s:property value='logoImg'/>" data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#productInfo.logoImg"/>"  style="height:220px;width:220px;"/>
									</a>
									<p style="height: 38px;overflow: hidden; margin-top:7px;">
										<a target="_blank" title="<s:property value="#productInfo.productFullName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='#productInfo.productId'/>"><s:property escape="false" value="#productInfo.productFullName" /></a>
									</p>
									<p class="ColorRed">
									 <span style="float:right;">共<s:property value="#productInfo.goodsCount" />种规格</span>
										<span class="FontSize18" style="margin-left:-5px;">￥<s:property  value="#productInfo.salesPrice" /><font size="2px">起</font></span>
									</p>
									<a target="_blank"  title="<s:property value="#productInfo.shopInfo.shopName"/>" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="#productInfo.shopInfoId"/>" class="ColorBlue publicinline" style="display:block;"><i class="icon iconShop01" style="background-position: left top; width:25px !important;"></i><s:property value="#productInfo.shopInfo.shopName" /></a>
								</li>
							</s:iterator>
						</s:if>
				   </ul>
					<!--分页 start-->
					<s:if test="productInfoList.size>0">
						<!--翻页效果-->
						<div class="flipEffect">
						   <div style="float:right;">
						   		<a <s:if test="pager.pageNumber==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pager.prePageIndex'/>)"</s:else>>上一页</a>
						   		<s:iterator value="pager.optionalPageIndexList" var="pageNumberIndex" >
						   			 <a href="javascript:;" <s:if test="#pageNumberIndex==pager.pageNumber">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumberIndex'/>)"</s:else> ><s:property value="#pageNumberIndex" /></a>
								</s:iterator>
						   		<a <s:if test="pager.pageNumber>=pager.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pager.nextPageIndex'/>)"</s:else>>下一页</a>
						   </div>
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
