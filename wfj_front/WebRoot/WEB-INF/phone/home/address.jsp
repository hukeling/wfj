<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta charset="utf-8">
<title>收货地址管理</title>
<meta name="viewport"
	content="initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="../css/mui.min.css">
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery-1.8.0.min.js"></script>
<style type="text/css">
.title {
	font-size: 28px;
	color: #333;
	text-align: center;
	background-color: #ffffff;
	height: 40px;
	line-height: 40px;
}

.ss {
	height: 40px;
	line-height: 40px;
	margin-left: 12px;
}

.cc {
	display: -webkit-box;
	border-bottom: 1px solid #ccc;
	-webkit-box-align: center;
}

.dd {
	margin-left: 12px;
	margin-right: 12px;
	text-align: center;
	float: left;
}

.ee {
	margin-top: 12px;
}

.image-cache {
	max-height: 120px;
	max-width: 120px;
	border-radius: 8px;
	border: 1px solid #EEEEEE;
}
</style>
<script type="text/javascript">
	function setDefault(customerId,customerAcceptAddressId) {
		var url ="${pageContext.request.contextPath}/phone/setDefault.action";
		$.post(url,{"customerId":customerId,"customerAcceptAddressId":customerAcceptAddressId},function(data){
			window.location.reload();
		},"JSON");
	}
</script>
</head>

<body>
	<div class="mui-content">
		<s:if test="%{#session.defaultAddress}">
			<div>
				<ul class="mui-table-view  mui-media mui-col-xs-12">
					<li class="mui-table-view-cell">
						${defaultAddress.consignee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${defaultAddress.mobilePhone}<br />
						<br /> ${defaultAddress.address}
					</li>
					<li>&nbsp;&nbsp;&nbsp;&nbsp;默认地址
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="${pageContext.request.contextPath}/phone/editAddress.action?customerId=${customerId}&customerAcceptAddressId=${defaultAddress.customerAcceptAddressId}">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
						href="${pageContext.request.contextPath}/phone/delAddress.action?customerAcceptAddressId=${defaultAddress.customerAcceptAddressId}">删除</a>
					</li>
					<hr />
				</ul>

				<ul class="mui-table-view">
					<s:iterator value="%{#session.otherAddress}" var="a">
						<li class="mui-table-view-cell">
							${a.consignee}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${a.mobilePhone}<br />
							<br /> ${a.address}
						</li>
						<li>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)"
							onclick="setDefault(${customerId},${a.customerAcceptAddressId})">设为默认</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="${pageContext.request.contextPath}/phone/editAddress.action?customerId=${customerId}&customerAcceptAddressId=${a.customerAcceptAddressId}">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
							href="${pageContext.request.contextPath}/phone/delAddress.action?customerAcceptAddressId=${a.customerAcceptAddressId}">删除</a>
						</li>
					</s:iterator>
				</ul>
			</div>
		</s:if>
		<div></div>
		<a
			href="${pageContext.request.contextPath}/phone/newAddress.action?customerId=${customer.customerId}">新建地址</a>
	</div>
</body>

<script src="../js/mui.min.js"></script>
<script>
	mui.init({
		swipeBack : true
	//启用右滑关闭功能
	});
	mui('.mui-input-group').on('change', 'input', function() {
		var value = this.checked ? "true" : "false";
		this.previousElementSibling.innerText = "checked：" + value;
	});
</script>

</html>