<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 由商城首页点击“更多快报”链接 进入此页面 -->
<title>SHOPJSP快报</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/help/base.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/help/newslist20120221.css"/>
</head>
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
<div class="w">
 <div class="yicai" > 
  <div class="yicai_pp">SHOPJSP快报</div>
 
  <form class="form" method="post" action="${pageContext.request.contextPath}/shopHome/moreNews.action">
     
     <input id="txtnews" class="text" type="text" value="${txt}" name="txt">
     <input class="btn-search" type="submit" value="搜索">

   </form>
 </div>
<div id="left" class="content1 fl">
<div id="news_div" class="mc">
<ul>
	<s:if test="#request.moreNews.size>0">
		  <s:iterator value="#request.moreNews" var="o" status="s">
			<s:if test="#s.index<=#request.moreNews.size/2">
				  <li>
				  <div>
				    <a target="_blank" href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value='#o.articleId'/>"><font class="skcolor_ljg"></font><s:property value='#o.title'/></a>
				  </div>
				  <span>${createTime}</span> 
				  <div class="line"></div>
				 </li>
			</s:if>
		</s:iterator>
	</s:if>

  </ul>
  </div>
</div>

<div id="left" class="content1 fl">
<div id="news_div" class="mc" style=" border:0;">
<ul>
	<s:if test="#request.moreNews.size>0">
		<s:iterator value="#request.moreNews" var="o" status="s">
			<s:if test="#s.index>#request.moreNews.size/2">
				 <li>
				  <div>
				    <a target="_blank" href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value='#o.articleId'/>"><font class="skcolor_ljg"></font><s:property value='#o.title'/></a>
				  </div>
				  <span>${createTime}</span> 
				  <div class="line"></div>
				 </li>
			</s:if>
		</s:iterator>
	 </s:if>
 
  </ul>
  </div>
</div>
<div class="clear"></div>
</div>

	<!--分页 start-->
	<form id="formModule" action="${pageContext.request.contextPath}/shopHome/moreNews.action" method="post">
	<div class="pageList strong ClearBoth">
		<jsp:include page="../../util/splitPage.jsp" />
	</div>
	</form>
	<!--分页 end-->
<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
