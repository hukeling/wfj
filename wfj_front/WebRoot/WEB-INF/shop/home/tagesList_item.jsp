<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产标签列表</title>
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
    	var whichOrderBy = $("#flag").val();
    	if(whichOrderBy!=null && whichOrderBy!=""){
    		$("#"+whichOrderBy).css("color","red");
    	}
	    //表单验证
		$("#btn1").bind("click",function(){  
	    	if($("#minPrice").val()==""||$("#maxPrice").val()==""){
	    		lalert("提示","请输入价格！");
	    		<%-- 请输入价格:Please input the price  --%>
	    	}else if(eval($("#minPrice").val())>=eval($("#maxPrice").val())){
	    		lalert("提示","请输入正确的价格区间！");
	    		<%-- 请输入正确的价格范围:Please input the price  --%>
	    		return false;
	    	}else if($("#minPrice").val()!=null&&$("#maxPrice").val()!=null&&eval($("#minPrice").val())<eval($("#maxPrice").val())){
	    		$("#form1").submit();   
	    	}
		});
	  });
	    //初始化
	    $(function (){
// 	    	选中品牌
	    	if($("#brandHidden").val()!=null){
	    		var bValue=$("#brandHidden").val();//品牌的id数组
	    		if(bValue!=null){
			    	var brandParams=bValue.split(","); //将品牌字符串以','分割
			    	for(var i=0;i<brandParams.length;i++){//循环选中
			    		var brand=brandParams[i];
			    		$("#brand_"+brand).attr("checked",true);
			    	}
	    		}
	    	}
	    });
	    //delect所有查询条件(不包含排序)
	    function clear_all_query_conditions(){
	    	$("#select_screen").css("display","none");
	    	$("[type='checkbox']").removeAttr("checked");//取消选中
	    	window.location="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${productTypeId}&orderBy=normal";
	    }
	    //左侧复选框操作
	    function checkBoxChange(){
	    	//获得排序参数
	    	var f=$("#flag").val();
	    	var min=$("#min").val();
	    	var max=$("#max").val();
	    	//查询品牌参数
	    	var brandValue=[];
			$("input[name='brandCheckbox']:checked").each(function(){    
				brandValue.push($(this).val());    
			}); 
			window.location.href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?brandParams="+brandValue+"&productTypeId=${productTypeId}&orderBy="+f+"&minPrice="+min+"&maxPrice="+max;
	    }
	    //单个条件的删除
	     function clearOne(params){//params为删除的参数 (color,brand,price)等
	    	//获得排序参数
	    	var f=$("#flag").val();
	    	var min=$("#min").val();
	    	var max=$("#max").val();
    		$("#"+params).css("display","none");
    		$("input[name='"+params+"Checkbox']:checked").removeAttr("checked");
    		if(params=="价钱"){
    			$("#minPrice").val("");
    			$("#maxPrice").val("");
    			min="";
    			max="";
    		}
	    	if($("input[type='checkbox']:checked").length == 0){//如果没有选中的checkbox 则清除所有左侧查询条件
	    		$("#select_screen").css("display","none");
	    		window.location="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId=${productTypeId}&orderBy="+f+"&minPrice="+min+"&maxPrice="+max;
	    	}else{
		    	//查询品牌参数
		    	var brandValue=[];
				$("input[name='brandCheckbox']:checked").each(function(){    
					brandValue.push($(this).val());    
				}); 
				window.location.href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?brandParams="+brandValue+"&productTypeId=${productTypeId}&orderBy="+f+"&minPrice="+min+"&maxPrice="+max;
	    	}
	    }
		//初始化
		$(function (){
			var id='${brandId}';
			if(id==''){
				$("#isTrue_"+id).attr("src","${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
			}
		});
		//点击品牌后更换选择图标
		function pinpai(brandId,productTypeId){
			var brandIds = $("#brandIds").val();
			var brandList = brandIds.split(",");
			var flag = false;
			for(var i=0;i<brandList.length;i++){
				if(brandList[i]==brandId){
					flag = true;
					brandList.splice(i, 1);
					break;
				}
			}
			brandIds = brandList.join(",");
			if(!flag){
				if(brandIds==null||brandIds==""){
					brandIds = brandId;
				}else{
					brandIds += ","+brandId;
				}
			}
			var sv = $("#specificationValueIds").val();
			window.location.href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?brandId="+brandIds+"&productTypeId="+productTypeId+"&specificationValueId="+sv+"&orderBy=normal";
		}
		//点击规格
		function guige(brandId,productTypeId,specificationValueId){
			var sv = $("#specificationValueIds").val();
			var svList = sv.split(",");
			var flag = false;
			for(var i=0;i<svList.length;i++){
				if(svList[i]==specificationValueId){
					flag = true;
					svList.splice(i, 1);
					break;
				}
			}
			sv = svList.join(",");
			if(!flag){
				if(sv==null||sv==""){
					sv = specificationValueId;
				}else{
					sv += ","+specificationValueId;
				}
			}
			var brands = $("#brandIds").val();
			window.location.href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?brandId="+brands+"&productTypeId="+productTypeId+"&specificationValueId="+sv+"&orderBy=normal";
		}
		//规格js效果
		(function($) {
			$(function(){
				$('#sort_filter').change(function(){
					var url = $('option:selected', $(this)).attr('href');
					location.href = url;
				});
				$('span[cpath]').hover(function() {
					$(this).replaceWith('<a href="/s/c-' + $(this).attr('cpath') + '/">' + $(this).text() + '</a>')
				});
				var dv = this;
				dv._CartTimer = null;
				$(".filter-attr").mouseenter(function () {
					var h = $(this);
					clearTimeout(h._CartTimer);
					h._CartTimer = null;
					$(this).removeClass("filter-attr-off").addClass("filter-attr-on");
					$(this).children("i").removeClass("arrow-off").addClass("arrow-on");
					$(this).parent().find(".filter-attr-expand").show();
				}).mouseleave(function () {
					var t = $(this);
					dv._CartTimer = setTimeout(function () {
						t.parent().find(".filter-attr-expand").hide();
						t.removeClass("filter-attr-on").addClass("filter-attr-off");
						t.children("i").removeClass("arrow-on").addClass("arrow-off");
					}, 100)
				});
				$(".filter-attr-expand").hover(function () {
					clearTimeout(dv._CartTimer);
					dv._CartTimer = null;
				}, function () {
					dv._CartTimer = setTimeout(function () {
						$(".filter-attr-expand").hide();
						$(".filter-attr-expand").next().removeClass("filter-attr-on").addClass("filter-attr-off");
						$(".filter-attr-expand").next().children("i").removeClass("arrow-on").addClass("arrow-off");
					}, 100)
				});
			});
		})(jQuery);
		
		$(function(){
			//回显
			var specificationValueId = '${specificationValueId}';
			var s = specificationValueId.split(",");
			for(var i=0;i<s.length;i++){
				$("#specificationValueId"+s[i]).attr("src","${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
			}
			var brandId = '${brandId}';
			var b = brandId.split(",");
			for(var i=0;i<b.length;i++){
				$("#isTrue_"+b[i]).attr("src","${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/checkbox_on.gif");
			}
		});
</script>
</head>

<body>
	<input id="brandIds" type="hidden" value="${brandId}"/>
	<input type="hidden" id="specificationValueIds" value="${specificationValueId}"/>
	<%@ include file="../include/header.jsp"%>
	<div id="mbxBox" class="FontSize13 PublicWidth1004 margin-center ">
	<span class="ColorBlack">当前位置:</span>    <!-- 导航条 -->
       <s:property escape="false" value="tageName"/>
     </div>
	<div class="margin-center PublicWidth1004">
	<div class="ClearBoth" style="float:left;">
		<div class="category_main-w search_page_layout_type1" style="float: left; width:100%;margin-bottom:30px;">
			<table class="search_filter_table_w search_filter_table" style="margin-top: 0px;">
					<s:if test="#request.shopTradeTagList.size!=0">
						<tr id="ct" style="height:1px;">
							<th style="width:100px;">适用专业</th>
								<td>
									<div id="" class="summary_list split_line5" style="height: 40px; line-height:40px;" flag="c">
										<div class="split_list">
											<ul class="node_list">
												<!-- <a href="javascript:showMoreCategoryInfo();" style="text-decoration: none;">更多>></a> -->
												<s:iterator  value="#request.shopTradeTagList">
													<li class="" style="overflow: visible;width:115px;">
														<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/categoryListmark.gif" width="5" height="5" />
														<a title="<s:property value='tageName'/>" href="${pageContext.request.contextPath}/homeModule/homeModule/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&ttId=<s:property value='ttId'/>&flagImgOrList=2" style="text-decoration: none;">
															<s:if test="tageName.length()>9">
																<s:property value="tageName.substring(0,6)"/>...
															</s:if>
															<s:else>
																<s:property value="tageName"/>
															</s:else>
														</a>
														<s:if test="count!=null">
															<span class="cnt">(<s:property value="count"/>)</span>
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
					<s:if test="#request.flag=='true'||#request.shopSituationTagList.size!=0">
						<tr id="ct" style="height:1px;">
							<th style="width:100px;">使用场合</th>
								<td>
									<div id="" class="summary_list split_line5" style="height: 40px; line-height:40px;" flag="c">
										<div class="split_list">
											<ul class="node_list">
												<!-- <a href="javascript:showMoreCategoryInfo();" style="text-decoration: none;">更多>></a> -->
												<s:iterator  value="#request.shopSituationTagList">
													<li class="" style="overflow: visible;width:115px;">
														<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/productList/categoryListmark.gif" width="5" height="5" />
														<a title="<s:property value='tageName'/>" href="${pageContext.request.contextPath}/homeModule/homeModule/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>&flagImgOrList=2" style="text-decoration: none;">
															<s:if test="tageName.length()>9">
																<s:property value="tageName.substring(0,6)"/>...
															</s:if>
															<s:else>
																<s:property value="tageName"/>
															</s:else>
														</a>
														<s:if test="count!=null">
															<span class="cnt">(<s:property value="count"/>)</span>
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
			<input id="flag" type="hidden" value="<s:property value='orderBy'/>"/>
			<!--right-->
			<div id="rightBox" class="float-right publicHidden"  style="cursor:default;width:1204px;">
			<aside><!-- 排序 -->
					<div class="FontSize13 publicBorder publicMarginBot15 produstsList_screen ClearBoth" style="background-color: #f2f2f2;width:1182px; padding:12px 10px 11px 10px;">
						<a id="putSaleDate" href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=2&orderBy=putSaleDateDesc&minPrice=<s:property value='minPrice'/>&maxPrice=<s:property value='maxPrice'/>&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>">最新上架</a> 
						<a id="totalSales" href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=2&orderBy=totalSales&minPrice=<s:property value='minPrice'/>&maxPrice=<s:property value='maxPrice'/>&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>">销量</a> 
						<a id="salesPriceAsc" href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=2&orderBy=salesPriceAsc&minPrice=<s:property value='minPrice'/>&maxPrice=<s:property value='maxPrice'/>&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>" class="price_up">价格</a> 
						<a id="salesPriceDesc" href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=2&orderBy=salesPriceDesc&minPrice=<s:property value='minPrice'/>&maxPrice=<s:property value='maxPrice'/>&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>" class="price_down">价格</a> 
						<a href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=2&orderBy=<s:property value='orderBy'/>&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>" class="change_list_select ColorRed" >列表</a>
						<a href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=1&orderBy=<s:property value='orderBy'/>&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>" class="change_Gallery" >图片</a> 
					</div>
				</aside>
              <!-- 查询的条件显示 -->
              <s:if test="mapParams.size>0">
					<div id="select_screen" class="select_screen" >
					<s:iterator value="mapParams" id="map">
						<span style="position:absolute" id="${map.key }"> ${map.key }: <s:property value="#map.value"/> <a  onclick="clearOne('${map.key }');" title="Remove Auction" class="remove"></a></span>
					</s:iterator>
						 <a  style="position:absolute; left:415px; top:277px;" href="javascript:clear_all_query_conditions();" class="Clear_All" >全部清除</a>
					</div> 
				</s:if> 
			<!--Proitems-->    <!-- 商品 列表 -->
				<div class="ProitemBox publicMarginTop15" style="width:1202px;">
     		<form id="formModule" action="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=2&orderBy=<s:property value='orderBy'/>&ttId=<s:property value='ttId'/>&stId=<s:property value='stId'/>" method="post"> 
				 <input id="minPrice" name="minPrice" type="hidden" value="${minPrice }" />
				 <input id="maxPrice" name="maxPrice" type="hidden" value="${maxPrice }" />
				<ul class="account_MyCart">
					<s:iterator value="#request.productList" var="productInfo">
						<li class="ClearBoth">
						<a target="_blank" style="display:block;width:125px;height:100px;float:left;" href="${pageContext.request.contextPath}/productInfo/<s:property value="#productInfo.productId" />.html">
						<img title="<s:property value="#productInfo.productFullName"/>" width="100px;" height="100px;"
							class="lazy" src="${fileUrlConfig.visitFileUploadRoot}<s:property value='logoImg'/>" data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#productInfo.logoImg"/>" />
						</a>
						<div >
							<p style="height: 20px; overflow: hidden;">
								<a target="_blank" title="<s:property value="#productInfo.productFullName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='#productInfo.productId'/>.html" class="FontSize13 FontSizeB"><s:property value="#productInfo.productFullName" /></a>
							</p>
							<span title="<s:property value="#productInfo.describle"/>" class="ColorQHui publicMarginT5 publicblock" style="height: 40px; overflow: hidden;"><s:property value="#productInfo.describle"/></span>
							<s:if test="#productInfo.goodsCount>1"><span style="color:#d01743 ;  font-size:12px; line-height:24px;">共<s:property value="#productInfo.goodsCount" />种规格</span></s:if>
							<a target="_blank"  title="<s:property value="#productInfo.shopName"/>" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="#productInfo.shopInfoId"/>" class="ColorBlue publicinline" style="display:block;"><i class="icon iconShop01" style="background-position: left top; width:25px !important;"></i><s:property value="#productInfo.shopName" /></a>
						</div>
						<div class="ColorRed FontSize18">￥<s:property  value="#productInfo.salesPrice" /><font size="2px">起</font></div>
						</li>
					</s:iterator>
				</ul>			
					<!--分页 start-->
					<s:if test="#request.productList.size>0">
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
