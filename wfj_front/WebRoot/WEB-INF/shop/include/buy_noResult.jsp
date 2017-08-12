<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>页面正在建设中</title>
<%@ include file="../include/head.jsp"%>
</head>
<body>
	<%@ include file="../include/header.jsp"%>
	
	<div class="text_Georgia FontSize13 font_family_Georgia " >
		<div style="font-size: 24px;margin-top: 10px;margin-bottom: 10px; width:1210px; margin:0 auto; text-align: center;">
			页面正在建设中...<br/>
		</div>
		<br/>
		<!-- <div style="font-size: 16px;margin-top: 10px;margin-bottom: 10px;width:1210px; margin:0 auto;">
			<p style="width:341px; height:26px; line-height26px; margin:0 auto;  text-align: left;">建议：</p>
			<p style="width:341px; height:26px; line-height26px; margin:0 auto;  text-align: left;">1 &nbsp;检查您的查询条件是否有语法上的错误；</p>		  								
		  	<p style="width:341px; height:26px; line-height26px; margin:0 auto;  text-align: left;">2 &nbsp;您也可以输入其他相近的词语来进行查询；</p>
		</div> -->
	</div>
	
	<%--<div class="margin-center PublicWidth1004">		
		<section class="publicMarginTop15 publicPaddingT15">
			<h3 class=" publicblock publicPadding10 publicBorderB_3 FontSize16 FontSizeB">查询结果:</h3>
			<ul class="WishListUl ClearBoth">
			<s:iterator value="productInfoList" var="list" >
				<li>
					<a target="_blank" style="display: block;width:220px;height:220px;" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId' />.html"><img style="width:220px;height:220px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImage' />"></a>
					<p><a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId' />.html"><s:property value="describle" /></a></p>
					<p class="ColorRed">USD&nbsp;&nbsp;<span class="FontSize18"><s:property value="salesPrice" /></span></p> 
				</li>
			</s:iterator>
			</ul>
		</section>
	</div> --%>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
