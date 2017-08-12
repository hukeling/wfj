<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单商品清单</title>
<style>
body{
background-color:#EEEEEE;
font-size:16px;
font-family:Arial, Helvetica, sans-serif,"黑体";
padding:0px;
margin:0px;}
.box{
background-color:#FFFFFF;
border-bottom:1px solid #CDCDCD;
float:left;
width:100%;
margin-left:2%;
margin-right:2%;
padding:0;
display:block
} 
.money{
font-size:18px;
color:#FF0000}
.item{
float:left; 
padding-top:4%; 
padding-bottom:4%; 
text-align:left; 
}	
</style>
</head>

<body>
	<div id="content">
<s:iterator value="#session.proMap" var="map">
   <s:iterator value="#map.value" var="list" status="a">
	<div class="box">
		<div class="item"  style="float:left; width:40%; background-color:#FFFFF0">
			<a href="${pageContext.request.contextPath}/phoneProductInfo/<s:property value="#list.productId" />.html?customerId=${customerId}"><img class="proImg" width="80%" height="25%" src="<s:property value='#application.fileUrlConfig.visitFileUploadRoot+#list.smallImgUrl'/>" />
			</a>
		</div>
		<div class="item"  style="float:right; width:60%;">
			<div id="proName"><a href="${pageContext.request.contextPath}/phoneProductInfo/<s:property value="#list.productId" />.html?customerId=${customerId}"><s:property value="#list.productFullName"/></a></div>
			<div class="money"  id="proPrice">￥<s:property value="#list.salesPrice" /></div>
			<div id="proNum"><s:property value="#list.productNum" />件</div>
			<div id="proBack" style="color:#0000FF">支持7天无理由退货</div>
		</div>	
   </div>
  </s:iterator>
</s:iterator>
	</div>
</body>
</html>