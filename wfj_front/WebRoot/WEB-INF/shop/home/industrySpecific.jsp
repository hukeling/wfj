<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
<title>专业推荐</title>
<%@ include file="../include/head.jsp"%>
<style>
	.grep {color:#666;}
</style>
<script type="text/javascript">
	//转向一级分类
	function goType(labelsId){
		var winopen = window.open ("${pageContext.request.contextPath}/frontBrandsLabels/getBrandsAndLabels.action?labelsId="+labelsId+"&orderBy=normal");
	}
</script>
</head>
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004" style=" margin-bottom:50px;">
	<!--mainClass-->
	<div class=" ClearBoth">
		<div id="Categories">
			<h2 class="FontSize20 BackgroundF2Hui publicPadding20 publicMarginB10 strong">专业推荐</h2>
			<s:iterator value="listLabels" var="list" status="s">
				<div class="publicBorderR1" style="width:auto;padding-left:0;  margin-bottom:0; height:30px; line-height:30px;">
					<dl class="publicMarginTB20">
					<a class="grep" style="font-weight:normal !important;line-height: 22px;font-size: 12px;" href="javascript:void(0)" onclick="goType(<s:property value="labelsId"/>)"><s:property value="labelsName"/></a>
					<span class="grep">&nbsp;&nbsp;|</span>
					</dl>
				</div>
				<div class="publicBorderR1" style="width:auto;padding-left:0;  margin-bottom:0; height:30px; line-height:30px;">
					<dl class="publicMarginTB20">
					<a class="grep" style="font-weight:normal !important;line-height: 22px;font-size: 12px;" href="javascript:void(0)" onclick="goType(<s:property value="labelsId"/>)"><s:property value="labelsName"/></a>
					<span class="grep">&nbsp;&nbsp;|</span>
					</dl>
				</div>
			</s:iterator>
		</div>
	</div>
	<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
