<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请店铺等待审核</title>
<%@ include file="../shop/include/head.jsp"%>
<script>

$(function() {
	$("ul.FavouriteTab").tabs("div.TabParent_lj > div",{initialIndex:0},{history: true});
});
//checkbox全选、取消全选操作
function selectAllcheckbox(id,name){
   var b = document.getElementsByName(name);
      var all = document.getElementById(id);
         if(all.checked){
             for(i=0;i<b.length;i++){
                 b[i].checked=true;
             }
         }else{
             for(i=0;i<b.length;i++){
                 b[i].checked=false;
             }
         } 
	} 
//分页函数
function changePage(pageIndex,flag,flag2){
	if(flag2==1){
	    window.location="${pageContext.request.contextPath}/front/store/platformPromotion/gotoStoreApplyPromotionListPage.action?shopInfoId="+${shopInfoId}+"&promotionId="+${promotionId}+"&pageIndex="+pageIndex+"&pageIndex2="+flag;
	}else{
	    window.location="${pageContext.request.contextPath}/front/store/platformPromotion/gotoStoreApplyPromotionListPage.action?shopInfoId="+${shopInfoId}+"&promotionId="+${promotionId}+"&pageIndex2="+pageIndex+"&pageIndex="+flag;
	}
}
//校验 checkBox是否为空
function doButtion(){
	if(this.submitFlag==1){
		return false;
	}else{
		this.submitFlag=1;
		return true;
	}
}
</script>
</head>

<body>
<%@ include file="../shop/include/header.jsp"%>

<div class="margin-center PublicWidth1004">
<div class="ClearBoth">
<%@ include file="../shop/include/left_shop.jsp"%>
<!--right-->
<div id="rightBox" class="float-right publicHidden">
<section>
<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">参与活动</h3>
<div class="MarketpalceTree publicMarginTop15">
<ul class="FavouriteTab">
<!-- <li class="first-child"><a href="javascript:void(0);" class="FontSize14 radius gradient">参与活动</a></li> -->
</ul>
<div class="TabParent_lj publicBorder ClearBoth">
</br>
<div>
<div id="test" class="publicPadding10">
<form  id="form1" name="form1" action="${pageContext.request.contextPath}/front/store/storeApplyPromotion/batchApplication.action" method="get" >
<s:token></s:token>
<input id="shopInfoId" name="shopInfoId"  type="hidden" value="<s:property value='shopInfoId'/>"/>
<input id="pageIndex" name="pageIndex"  type="hidden" value="<s:property value='pageHelper.pageIndex'/>"/>
<input id="pageIndex2" name="pageIndex2"  type="hidden" value="<s:property value='pageHelper2.pageIndex'/>"/>
<input id="promotionId" name="promotionId"  type="hidden" value="<s:property value='promotionId'/>"/>
<input id="oldParams" name="oldParams"  type="hidden" value="<s:property value='#request.oldParams'/>"/>
<input id="paramsCheckbox" name="paramsCheckbox"  type="hidden" value=""/>
<table width="100%" border="0" class="OrderinforTableL">
  <tr class=" publicBorder gradient">
    <td width="10%">缩略图</td>
    <td width="20%">商品名称</td>
    <td width="12%">分类</td>
    <!-- <td width="12%">品牌</td> -->
    <td width="10%">状态</td>
  </tr>
  <!-- 遍历该店铺参加该活动的商品List -->
  <s:iterator value="storeApplyPromotionList">
	  	<tr class="checkbox_tr">
		    <td><img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></td>
		    <td><s:property value="productName"/></td>
		    <td><s:property value="sortName"/></td>
		    <%-- <td><s:property value="brandName"/></td> --%>
	    	<s:if test="promotionState==0">
		    	<td><font color="gray">待审核</font> </td>
	    	</s:if>
	    	<s:if test="promotionState==1">
		    	<td><font color="blue">通过</font> </td>
	    	</s:if>
	    	<s:if test="promotionState==3">
		    	<td><font color="gray"> 再次申请</font> </td>
	    	</s:if>
	  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="storeApplyPromotionList.size>0">
	<div class="pageList strong ClearBoth">
	    <table id="dataArea" cellpadding="0" cellspacing="0" width="99.8%">
			<tr>
				<td class="mainpage">
		<!-- 			<span><a href="javascript:changePage(<s:property value='pageHelper.firstPageIndex'/>)"><font size="2px;">Home Page</font></a></span>&nbsp;&nbsp; -->
					<span><a href="javascript:changePage(<s:property value='pageHelper.prePageIndex'/>,<s:property value='pageHelper2.pageIndex'/>,1)"><font size="2px;">◀  上一页</font></a></span>&nbsp;
					<s:iterator value="%{pageHelper.optionalPageIndexList}" status="index" var="pageNumber" >
					<s:set var="ind" value="pageNumber"></s:set>
			        	<span><a href="javascript:changePage(<s:property />,<s:property value='pageHelper2.pageIndex'/>,1)" <s:if test="#ind == pageHelper.pageIndex"> style="color: red;" </s:if>  ><font size="2px;"><s:property value="#ind"/></font></a></span>&nbsp;	
					</s:iterator>
				    <span><a href="javascript:changePage(<s:property value='pageHelper.nextPageIndex'/>,<s:property value='pageHelper2.pageIndex'/>,1)"><font size="2px;">下一页 ▶</font></a></span>&nbsp;&nbsp;
		<!-- 	        <span><a href="javascript:changePage(<s:property value='pageHelper.lastPageIndex'/>)"><font size="2px;">Trailer Page</font></a></span>&nbsp;&nbsp;&nbsp;</br> -->
		<!-- 			<span><font size="2px;">第</font><b><s:property value='pageHelper.pageIndex'/>/<s:property value='pageHelper.pageCount'/></b><font size="2px;">页，共</font><b><s:property value='pageHelper.pageRecordCount'/></b>条记录</span> -->
		<!-- 		    <span><font size="2px;">跳转页：</font><input type="text" class="" name="anyPageNum2" id="goto" size='3' value="<s:property value='pageIndex'/>" style="color:red;height: 11px;"/></span> -->
		<!-- 		    <span><a href="#" onclick="gotoPage('onclick')"><font size="2px;">跳转</font></a></span> -->
				</td>
			</tr>
	  	 </table>
	</div>
</s:if>
<!--分页 end-->
</br>
</br>
</br>
</br>
<aside class=" publicBorder BackgroundF2Hui publicPadding10">
<div id="test" class="publicPadding10">
	<p  class=" widthpx1200 text-left FontSize14 FontSizeB">选择商品:&nbsp;&nbsp;&nbsp;<input name="submit" class="FontSize14"  type="submit"  value="选择完毕，提交商品" style="background-color:#ff7400; border:0; color: #fff; padding:3px; border:1px #be5802 solid;"/></p>
</div>
</aside>
<table width="100%" border="0" class="OrderinforTableL">
  <tr class=" publicBorder gradient">
  	<td width="3%"><input id="onCheckboxAll" name="checkboxAll" type="checkbox"  onclick="selectAllcheckbox('onCheckboxAll','checkbox');" value="all"></td>
    <td width="10%">缩略图</td>
    <td width="20%">商品名称</td>
  </tr>
  <!-- 遍历店铺的商品List -->
  <s:iterator value="noHasProductList">
	  	<tr class="checkbox_tr">
			<td><input id="checkbox" name="checkbox" type="checkbox" value="<s:property value='productId'/>"></td>
		    <td><img style="max-height:47px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></td>
		    <td><s:property value="productName"/></td>
	  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="noHasProductList.size>0">
	<div class="pageList strong ClearBoth">
	    <table id="dataArea" cellpadding="0" cellspacing="0" width="99.8%">
			<tr>
				<td class="mainpage">
		<!-- 			<span><a href="javascript:changePage(<s:property value='pageHelper.firstPageIndex'/>)"><font size="2px;">Home Page</font></a></span>&nbsp;&nbsp; -->
					<span><a href="javascript:changePage(<s:property value='pageHelper2.prePageIndex'/>,<s:property value='pageHelper.pageIndex'/>,2)"><font size="2px;">◀  上一页</font></a></span>&nbsp;
					<s:iterator value="%{pageHelper2.optionalPageIndexList}" status="index" var="pageNumber" >
					<s:set var="ind" value="pageNumber"></s:set>
			        	<span><a href="javascript:changePage(<s:property />,<s:property value='pageHelper.pageIndex'/>,2)" <s:if test="#ind == pageHelper2.pageIndex"> style="color: red;" </s:if>  ><font size="2px;"><s:property value="#ind"/></font></a></span>&nbsp;	
					</s:iterator>
				    <span><a href="javascript:changePage(<s:property value='pageHelper2.nextPageIndex'/>,<s:property value='pageHelper.pageIndex'/>,2)"><font size="2px;">下一页 ▶</font></a></span>&nbsp;&nbsp;
		<!-- 	        <span><a href="javascript:changePage(<s:property value='pageHelper.lastPageIndex'/>)"><font size="2px;">Trailer Page</font></a></span>&nbsp;&nbsp;&nbsp;</br> -->
		<!-- 			<span><font size="2px;">第</font><b><s:property value='pageHelper.pageIndex'/>/<s:property value='pageHelper.pageCount'/></b><font size="2px;">页，共</font><b><s:property value='pageHelper.pageRecordCount'/></b>条记录</span> -->
		<!-- 		    <span><font size="2px;">跳转页：</font><input type="text" class="" name="anyPageNum2" id="goto" size='3' value="<s:property value='pageIndex'/>" style="color:red;height: 11px;"/></span> -->
		<!-- 		    <span><a href="#" onclick="gotoPage('onclick')"><font size="2px;">跳转</font></a></span> -->
				</td>
			</tr>
	  	 </table>
	</div>
</s:if>
<!--分页 end-->
</div>
</div>
</div>
</form>
</div>
</section>
</div>
<!--//right-->
</div>
</div>
<%@ include file="../shop/include/footer.jsp"%>
</body>
</html>
