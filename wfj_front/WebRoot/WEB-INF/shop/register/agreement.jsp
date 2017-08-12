<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css">
<script type="text/javascript">
	var iAgree=function(){
		window.opener.returnInformation('agree');
		window.close();
	}
</script>
</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<div class="agreementBody">
	<div class="agreementCenter"><s:property value="cmsArticle.title"/></div>
	<div class="w800_dotted"></div>
	<div class="agreementText">
       <div><s:property escape="false" value="cmsArticle.content"/></div>
	</div>
	<div class="w800_dotted"></div>
	
	<div class="agreementSubmit">
		<div class="float-left" style="margin-right:20px;">
		<a href="javascript:void(0);" onclick="iAgree()"  class="hoverNo publicPadding7_20 publicinline FontSize14 FontSizeB BackgroundPurple ColorWhite1">同意</a>
		</div>
		<div class="agreementSubmitReturn float-left">
		<a href="javascript:close();" class="hoverNo publicPadding7_20 publicinline FontSize14 FontSizeB BackgroundOrange ColorWhite1">关闭</a></div>
		<div class="ClearBoth"></div>
	</div>
	<div class="h20"></div>
	<div class="ClearBoth"></div>
</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
