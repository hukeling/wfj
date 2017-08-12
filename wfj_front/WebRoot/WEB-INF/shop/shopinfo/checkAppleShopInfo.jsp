<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>认证企业状态显示页</title>
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css">
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
	<%@ include file="../include/head.jsp" %>

	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		

		<div class="margin-center PublicWidth1004">
			<div>
			<div class="ClearBoth">
				
				<div class="registerSuccessBody Backgroundpink radius publicPadding20">
					<s:if test="sellerflag==2">
						<div class="registerSuccessTitle  ColorRed" style="text-align:center;">您的认证，审核没有通过!</div>
					    <p  style="text-align:center;"><a href="${pageContext.request.contextPath}/"  class="ColorBlue FontSize14">返回首页>></a></p>
					    <p  style="text-align:center;"><a href="${pageContext.request.contextPath}/front/store/frontshopinfo/gotoFrontShopInfoPage.action"  class="ColorBlue FontSize14">认证企业>></a></p>
					</s:if>
					<s:if test="sellerflag==3">
						<div class="registerSuccessTitle  ColorRed" style="text-align:center;">您需要先认证企业！</div>
						
						<p  style="text-align:center;"><a href="${pageContext.request.contextPath}/"  class="ColorBlue FontSize14">返回首页>></a></p>
					    <p  style="text-align:center;"><a href="${pageContext.request.contextPath}/front/store/frontshopinfo/gotoFrontShopInfoPage.action"  class="ColorBlue FontSize14">现在去认证企业>></a></p>
					</s:if>
					<s:if test="sellerflag==4">
						<div class="registerSuccessTitle  ColorRed" style="text-align:center;">您的认证正在审核,请耐心等待!</div>
						<p  style="text-align:center;"><a href="${pageContext.request.contextPath}/"  class="ColorBlue FontSize14">返回首页>></a></p>
					</s:if>
					<s:if test="sellerflag==5">
						<div class="registerSuccessTitle  ColorRed" style="text-align:center;">您的认证已被平台关闭!</div>
						<p style="text-align:center;"><a href="${pageContext.request.contextPath}/shopHome/gotoHomePage.action"  class="ColorBlue FontSize14">返回首页>></a></p>
					</s:if>
					<s:if test="sellerflag==6">
						<div class="registerSuccessTitle  ColorRed" style="text-align:center;">您的企业信息未通过认证!</div>
						<p style="text-align:center;"><a href="${pageContext.request.contextPath}/shopHome/gotoHomePage.action"  class="ColorBlue FontSize14">返回首页>></a></p>
					</s:if>
				</div>
			</div>
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
