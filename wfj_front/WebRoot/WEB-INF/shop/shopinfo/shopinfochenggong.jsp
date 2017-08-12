<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>店铺信息</title>
	<%@ include file="../include/head.jsp" %>
<script type="text/javascript">
	 	
</script>
<style type="text/css">
		#imgcss{
			margin-top: -50px;
		}
		</style>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
			<%@ include file="../include/left_shop.jsp"%>
			<form  action="" method="post">
			<input type="hidden" id="nationalIsoId" name="nationalIso" value=""/>
			<table width="690" border="0" align="center">
				<tr>
<!-- 					<td colspan="5"><img id="imgcss" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/back/shopinfo/ss.jpg"></td> -->
				</tr>
				<tr>
					<td width="350"><center><font style="font-weight: bold;">申请成功，我们将尽快核实你的信息，大概需要一到三个工作日，请你耐心等待！</font></center>
					</td>
					
				</tr>
				<tr>
				   <td width="350" style="text-align: center; padding-top:20px;"><center><font style="font-weight: bold;"><a href="${pageContext.request.contextPath}/">返回首页</a></font></center>
					</td>
				</tr>
			</table>
			</form>
			</div>
		</div>
</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
