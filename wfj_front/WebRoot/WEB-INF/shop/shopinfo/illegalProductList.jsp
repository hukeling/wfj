<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>违规下架产品列表</title>
<%@ include file="../include/head.jsp"%>
<script>
	//查询条件初始化中商品分类
	$(function(){
		//初始化商品分类
		$("#qproductTypeId").val('${productTypeId}');
		//初始化排序
		$("#goodsSorting").val('${orderByParams}');
	});
	
	//删除商品
	function deleteProduct(id){
		$("#deleteProductObj").val(id);
	    changePage(document.getElementById("currentPage").value);
	}
 	
	function changePage(pageIndex){
		if(document.getElementById("currentPage")!=null){
			document.getElementById("currentPage").value=pageIndex;//分页
		}
	 	document.getElementById("orderByParams").value=$("#goodsSorting").val();//排序
	 	document.getElementById("hidProductName").value=$.trim($("#qproductName").val());//商品名称
	 	document.getElementById("hidProductTypeId").value=$("#qproductTypeId").val();//商品分类
	 	$("#formModule").submit();
	 }
</script>
</head>

<body>
<%@ include file="../include/header.jsp"%>

<div class="margin-center PublicWidth1004">
<div class="ClearBoth" style="margin-top:10px;">
<%@ include file="../include/left_shop.jsp"%>
<!--right-->
<div id="rightBox" class="float-right publicHidden">
<section>
<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">违规下架产品列表</h3>
	<aside class=" publicBorder BackgroundF2Hui publicPadding10 ">
	<div class="ClearBoth  parent_for_p">
	   <div style="float:left;">
		<p  style="margin-top:3px;">排序：
		</p>
		<p style="margin-top:3px;">
			 <select id="goodsSorting">
					<option value="Latest" >最新</option>
					<option id="Pricehighestfirst" value="Pricehighestfirst">价格由高到低</option>
					<option id="Pricelowestfirst" value="Pricelowestfirst">价格由低到高</option>
					<option id="Soldhighestfirst" value="Soldhighestfirst">销售量由高到低</option>
					<option id="Soldlowestfirst" value="Soldlowestfirst">销售量由低到高</option>
				</select>
			</p>
			</div>
				<p>商品名称：
				<input id="qproductName" type="text" style="height:20px;" value="${productName}"/>&nbsp;&nbsp;&nbsp;&nbsp;
				商品分类：
				<select  id="qproductTypeId">
	             <option value="-1">-----请选择分类-----</option>
				  <s:iterator value="#application.categoryMap" var="type1">
				  	<option value="<s:property value="#type1.key.productTypeId"/>"  >
				  	<s:property value="#type1.key.sortName"/>
				  	<s:iterator value="#type1.value" var="type2">
					  	<option value="<s:property value="#type2.key.productTypeId"/>"  >
					  		&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type2.key.sortName"/>
					  		<s:iterator value="#type2.value" var="type3">
					  		<option value="<s:property value="#type3.key.productTypeId"/>" >
					  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type3.key.sortName"/>
					  				<s:iterator value="#type3.value" var="type4">
					  					<option value="<s:property value="#type4.productTypeId"/>" >
					  						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type4.sortName"/>
							  			</option>
						  			</s:iterator>
					  		</option>
					  		</s:iterator>	
					  	</option>
				  	</s:iterator>
				  	</option>
				  </s:iterator>
	           </select>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="查询" onclick="changePage('1');"
								class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" style="width: 50px;height: 22px;cursor:pointer;">
				</p>
		</div>
	</aside>
<div class="MarketpalceTree publicMarginTop15 ">

<div class="TabParent_lj publicBorder ClearBoth">
</br></br>
<!-- 违规下架的商品 -->
<div>
<div id="test" class="publicPadding10">
<form id="formModule" action="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoIllegalProductListPage.action" method="post">
	<!-- 查询条件 -->
	<input id="orderByParams" name="orderByParams"  type="hidden" value=""/>
	<input id="hidProductName" name="productName"  type="hidden" value=""/>
	<input id="hidProductTypeId" name="productTypeId"  type="hidden" value=""/>
	<!-- 删除商品标识 -->
	<input id="deleteProductObj" name="deleteProductObj"  type="hidden" value=""/>
<table width="100%" border="0" class="OrderinforTableL">
  <tr class=" publicBorder gradient">
<!--     <td width="3%"><input id="onCheckboxAll" name="onCheckboxAll" type="checkbox"  onclick="selectAllcheckbox('onCheckboxAll','onShelf','offShelf','offCheckboxAll');" value="all"></td> -->
    <td width="15%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;">图片</td>
    <td width="15%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">商品名称</td>
    <td width="16%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">商品分类</td>
    <td width="9%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">价格</td>
<!--     <td width="9%">ProductID</td> -->
    <td width="13%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">销售量</td>
    <td width="11%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">时间</td>
    <td width="9%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-right: 1px #d2d2d2 solid;">操作</td>   
  </tr>
  <!-- 遍历店铺上架的商品List -->
  <s:iterator value="violationOffProductList">
		  	<tr class="checkbox_tr">
<!-- 			    <td class="publicNoBorderR"><input id="checkboxId_<s:property value='productId'/>" name="onShelf" type="checkbox" value="<s:property value='productId'/>"> </td>-->
			    
			    <td  style="border-bottom: 1px #d2d2d2 solid;" title="<s:property value="productFullName"/>"><img style="height:50px; width:50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"  title="<s:property value="productFullName"/>">
			    <div style="height: 3.5em;word-wrap:break-word;overflow:hidden;">
			    	<s:property value="productFullName"/>
			    </div>
			    </td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="sortName"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="salesPrice"/></td>
<!-- 			    <td><s:property value='productId'/></td> -->
			    <td style="border-bottom: 1px #d2d2d2 solid;">
			    	<s:if test="totalSales != null">
				    	<s:property value="totalSales"/>&nbsp;件
			    	</s:if>
			    	<s:if test="totalSales == null">
				    	0&nbsp;件
			    	</s:if>
				</td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;" class="publicNoBorderR"><a href="#" onclick="deleteProduct(<s:property value='productId'/>,4);" class="ColorBlue">删除</a></td>
		  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="violationOffProductList.size>0">
	<div class="pageList strong ClearBoth">
			<input type="hidden" id="currentPage" name="currentPage" value="<s:property value='currentPage'/>"/>
				<!--翻页效果-->
				<div class="flipEffect">
				   <div style="float:right;">
				   		<a <s:if test="currentPage==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pageHelper.prePageIndex'/>)"</s:else>>上一页</a>
				   		<s:iterator value="pageHelper.optionalPageIndexList" var="pageNumber" >
				   			 <a href="javascript:;" <s:if test="#pageNumber==currentPage">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumber'/>)"</s:else> ><s:property value="#pageNumber" /></a>
						</s:iterator>
				   		<a <s:if test="currentPage>=pageHelper.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pageHelper.nextPageIndex'/>)"</s:else>>下一页</a>
				   </div>
				</div>
	</div>
</s:if>
<!--分页 end-->
</form>
</div>
</div>
<!-- ================ -->
</div>
</div>

</section>
</div>
<!--//right-->
</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
