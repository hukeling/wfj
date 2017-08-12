<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上架产品列表</title>
<%@ include file="../include/head.jsp"%>
<script>
	//开启团购商品操作
	function operateOpen(id){
	  	$.ajax({
		   type: "POST", dataType: "JSON",
		   url: "${pageContext.request.contextPath}/front/customer/tuan/updateStateOpen.action",
		   data: {tuanId:id},
		   success: function(data){
			  window.location.reload();
		   }
		});
	}

	//关闭团购商品操作
	function operateClose(id){
	  	$.ajax({
		   type: "POST", dataType: "JSON",
		   url: "${pageContext.request.contextPath}/front/customer/tuan/updateStateClose.action",
		   data: {tuanId:id},
		   success: function(data){
			  window.location.reload();
		   }
		});
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
<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">团购商品列表</h3>
<%-- 	<aside class=" publicBorder BackgroundF2Hui publicPadding10 ">
	<div class="ClearBoth  parent_for_p">
	   <div style="float:left;">
		<p  style="margin-top:3px;">排序：</p>
		<p style="margin-top:3px;">
			 <select id="goodsSorting">
					<option value="Latest">最新</option>
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
								class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" style="width: 50px;height: 22px;cursor:pointer;"/>
				</p>
		</div>
	</aside> --%>
	
<div class="MarketpalceTree publicMarginTop15 " style="position: relative;">
<div class="TabParent_lj publicBorder ClearBoth">
<br/><br/>
<div>
<div id="test" class="publicPadding10">
<form  id="formModule" action="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoPutawayProductListInfoPage.action" method="post">
<input id="paramsCheckbox" name="paramsCheckbox"  type="hidden" value=""/>
<!-- 查询条件 -->
<input id="orderByParams" name="orderByParams"  type="hidden" value=""/>
<input id="hidProductName" name="productName"  type="hidden" value=""/>
<input id="hidProductTypeId" name="productTypeId"  type="hidden" value=""/>
<!-- 更改商品显示状态标识 -->
<input id="changeShowSate" name="changeShowSate"  type="hidden" value=""/>
<!-- 商品下架操作标识 -->
<input id="changeisPutSate" name="changeisPutSate"  type="hidden" value=""/>
<!-- 商品批量下架操作标识 -->
<input id="bathChangeisPutSate" name="bathChangeisPutSate"  type="hidden" value=""/>


<table width="100%" border="0" class="OrderinforTableL firstChild_Tab">
  <tr class=" publicBorder gradient">
    <td width="8%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">主图图片</td>
    <td width="10%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">标题</td>
    <td width="11%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">商品名称</td>
    <td width="9%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">分类名称</td>
    <td width="9%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">团购价</td>
    <td width="8%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">申请时间</td>
    <td width="7%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">团购周期</td>
    <td width="8%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">开始时间</td>
    <td width="8%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">结束时间</td>
    <td width="7%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">已购人数</td>
    <td width="5%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">状态</td>
    <td width="5%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-right: 1px #d2d2d2 solid;">操作</td>   
  </tr>
  <s:iterator value="tuanProductList">
		  	<tr class="checkbox_tr">
			    <td style="border-bottom: 1px #d2d2d2 solid;"><img style="height:50px; width:50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='tuanImageUrl'/>"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="title"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;">
			    	<div style="height: 3.5em;word-wrap:break-word;overflow:hidden;">
			    		<s:property value="productFullName"/>
			    	</div>
			    </td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="sortName"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;">￥<s:property value="price"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="tuanPeriod"/>(天)</td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:date name="beginTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="bought"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;">
				    <s:if test="state==0">未开启</s:if>
				    <s:elseif test="state==1">开启</s:elseif>
				    <s:elseif test="state==2">关闭</s:elseif>
				    <s:elseif test="state==3">结束</s:elseif>
			    </td>
			    <td style="border-bottom: 1px #d2d2d2 solid;">
			    	<s:if test="state==0">
			    		<a href="javascript:void(0);" onclick="operateOpen(<s:property value="tuanId"/>);" class="ColorBlue">开启</a>
			    	</s:if>
			    	<s:elseif test="state==1">
			    		<a href="javascript:void(0);" onclick="operateClose(<s:property value="tuanId"/>);" class="ColorBlue">关闭</a>
			    	</s:elseif>
			    </td>
		  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="tuanProductList.size>0||tuanProductList.size>0">
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
