<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>重置密码</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/login.css">
<script type="text/javascript">

</script>
</head>

<body>
	<%@ include file="../include/header.jsp"%>
	<div class="margin-center PublicWidth1004">
		<div class="w600BoxBody">
			<div class="boxTitle">重置密码</div>
			<div class="w560_dotted"></div>
			<div class="font16 w560">
				验证邮件已经发送至您的邮箱，请您尽快验证!
				<div class="h20"></div>
			</div>
			<font style="font-weight: bold;"><a href="${pageContext.request.contextPath}/shopHome/gotoHomePage.action">返回首页</a></font>
			<div class="h20"></div>
			<div class="ClearBoth"></div>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
