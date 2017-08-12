<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	
	</head>
	<body>
	<form action="${pageContext.request.contextPath}/phone/saveOrUpdateExcelProductInfo.action" method="post" enctype="multipart/form-data">
	导入excel数据：<input type="file" name="imagePath" /><br/>
	<input type="submit" value="上传"/>
	</form>
	</body>
</html>
