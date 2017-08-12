<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>快钱支付跳转页面</title>
		<%@ include file="../include/head.jsp"%>
		<script type="text/javascript">
			$(function(){
				$("#kqPayForm").submit();
			});
		</script>
	</head>
	<body>
		<div style="width: 0px;height:0px;">
			<form id="kqPayForm" name="kqPay" action="https://www.99bill.com/gateway/recvMerchantInfoAction.htm" method="post">
				<input type="hidden" name="inputCharset" value="${map.inputCharset}" />
				<input type="hidden" name="pageUrl" value="${map.pageUrl }" />
				<input type="hidden" name="bgUrl" value="${map.bgUrl }" />
				<input type="hidden" name="version" value="${map.version }" />
				<input type="hidden" name="language" value="${map.language }" />
				<input type="hidden" name="signType" value="${map.signType }" />
				<input type="hidden" name="signMsg" value="${map.signMsg }" />
				<input type="hidden" name="merchantAcctId" value="${map.merchantAcctId }" />
				<input type="hidden" name="payerName" value="${map.payerName }" />
				<input type="hidden" name="payerContactType" value="${map.payerContactType }" />
				<input type="hidden" name="payerContact" value="${map.payerContact }" />
				<input type="hidden" name="orderId" value="${map.orderId }" />
				<input type="hidden" name="orderAmount" value="${map.orderAmount }" />
				<input type="hidden" name="orderTime" value="${map.orderTime }" />
				<input type="hidden" name="productName" value="${map.productName }" />
				<input type="hidden" name="productNum" value="${map.productNum }" />
				<input type="hidden" name="productId" value="${map.productId }" />
				<input type="hidden" name="productDesc" value="${map.productDesc }" />
				<input type="hidden" name="ext1" value="${map.ext1 }" />
				<input type="hidden" name="ext2" value="${map.ext2 }" />
				<input type="hidden" name="payType" value="${map.payType }" />
				<input type="hidden" name="bankId" value="${map.bankId }" />
				<input type="hidden" name="redoFlag" value="${map.redoFlag }" />
				<input type="hidden" name="pid" value="${map.pid }" />
			</form>
		</div>
	</body>
</html>
