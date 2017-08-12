<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>订单打印</title>
   <%@ include file="../include/head.jsp"%>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/DatePicker/WdatePicker.js"></script>
	<style type="text/css">
	
.addOrEditTable {
    border: 2px none gray;
    border-collapse: collapse;
    height: 50px;
    margin: 40px;
    width:80%;
    margin-left: 10%;
    margin-right: 10%;
}
* {
    font-size: 12px;
}

.addOrEditTable tr {
    line-height: 30px;
}
	.addOrEditTable td {
    border: 1px solid #d0d0d0;
    padding: 1px 6px;
}
    </style>
<style type="text/css" media=print>  
.noprint{display : none }  
</style> 
    <script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/js/json2.js"></script>
	<script type="text/javascript">
		function outPrint(){
			window.print(); 
		}
	</script>
  </head>
  
  <body >
  	<div class="noprint" align="right" style=""><input type="button" value="打印" onclick="outPrint()" style="background:gray;filter : progidximagetransform.microsoft.dropshadow(color=#fff,offx=2,offy=2,positives=true);
         width:60px;height:22px;"/></div>
    <table align="center" class="addOrEditTable" >
    	<tr style="line-height: 30px;">
  			<td colspan="4" style="background-color: #F0F0F0;">
  				<span><font style="font-size: 15px;">店铺信息：</font></span>
  			</td>
  		</tr>
    	<tr>
	  		<td style="text-align: right;width: 150px;">店铺名称:&nbsp;&nbsp;</td><td >&nbsp;&nbsp;<s:property value="shopInfo.shopName"/></td>
	   		<td style="text-align: right;width: 150px;">管理者:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="shopInfo.customerName"/></td>
   		</tr>
    	<tr>
	  		<td style="text-align: right;width: 150px;">公司名称:&nbsp;&nbsp;</td><td >&nbsp;&nbsp;<s:property value="shopInfo.companyName"/></td>
	   		<td style="text-align: right;width: 150px;">邮政编码:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="shopInfo.postCode"/></td>
   		</tr>
    	<tr>
	  		<td style="text-align: right;width: 150px;">店铺地址:&nbsp;&nbsp;</td><td >&nbsp;&nbsp;<s:property value="shopInfo.addressForInvoice"/></td>
	  		<td style="text-align: right;width: 150px;">店铺电话:&nbsp;&nbsp;</td><td >&nbsp;&nbsp;<s:property value="shopInfo.phone"/></td>
   		</tr>
    	<tr style="line-height: 30px;">
  			<td colspan="4" style="background-color: #F0F0F0;">
  				<span><font style="font-size: 15px;">购买人信息：</font></span>
  			</td>
  		</tr>
   		<tr>
	   		<td style="text-align: right;width: 150px;">收货人姓名:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.consignee"/></td>
   			<td style="text-align: right;width: 150px;">电子邮件:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.email"/></td>
   		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">手机:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.mobilePhone"/></td>
   			<td style="text-align: right;width: 150px;">邮政编码:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.postcode"/></td>
   		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">收货地址:&nbsp;&nbsp;</td><td  colspan="3">&nbsp;&nbsp;<s:property value="orders.address"/></td>
   		</tr>
   		<tr>
  			<td colspan="4" style="background-color: #F0F0F0;">
  				<span><font style="font-size: 15px;">订单信息：</font></span>
  			</td>
  		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">总订单号:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.totalOrdersNo"/></td>
   			<td style="text-align: right;width: 150px;">子订单号:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.ordersNo"/></td>
   		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">下单会员:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="customer.loginName"/></td>
   			<td style="text-align: right;width: 150px;">订单生成时间:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:date name="orders.createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
   		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">商品总金额:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.totalAmount"/></td>
   			<td style="text-align: right;width: 150px;">应付金额:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.finalAmount"/></td>
   		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">商城币抵扣金额:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.changeAmount"/></td>
   			<td style="text-align: right;width: 150px;">使用商城币数量:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.userCoin"/></td>
   		</tr>
   		<tr>
   		<td style="text-align: right;width: 150px;">运费:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="orders.freight"/></td>
   			<td style="text-align: right;width: 150px;">送货时间:&nbsp;&nbsp;</td>
   			<td align="left" style="width: 300px;">
			    <s:if test="orders.bestSendDate == 1">只工作日送货(双休日、假日不用送)</s:if>
			    <s:elseif test="orders.bestSendDate == 2">工作日、双休日与假日均可送货</s:elseif>
			    <s:elseif test="orders.bestSendDate == 3">只双休日、假日送货(工作日不用送)</s:elseif>
			    <s:else>工作日、双休日与假日均可送货</s:else>
	    	</td>
   		</tr>
   		<tr>
	   		<td style="text-align: right;width: 150px;">发票类型:&nbsp;&nbsp;</td>
	   		<td>
	   			<s:if test="orders.invoiceType == 1" >不开发票</s:if>
	   			<s:elseif test="orders.invoiceType == 2" >普通发票</s:elseif>
	   			<s:elseif test="orders.invoiceType == 3">增值税发票</s:elseif>
	   		</td>
	   		<td style="text-align: right;width: 150px;">配送方式:&nbsp;&nbsp;</td>
   			<td align="left" style="width: 300px;">
			    <s:if test="orders.sendType == 1">快递公司</s:if>
			    <s:elseif test="orders.sendType == 2">同城快递</s:elseif>
			    <s:elseif test="orders.sendType == 3">上门取货</s:elseif>
	    	</td>

   		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">支付方式:&nbsp;&nbsp;</td>
   			<td align="left" style="width: 300px;">
			<%--     <s:if test="orders.payWay == 1">货到付款</s:if>
			    <s:elseif test="orders.payWay == 2">支付宝</s:elseif>
			    <s:elseif test="orders.payWay == 3">银行汇款</s:elseif>
	    	--%>
	    	在线支付
	    	</td> 
			<td style="text-align: right;width: 150px;">订单状态:&nbsp;&nbsp;</td>
   			<td align="left" style="width: 300px;">
		    	<s:if test="orders.ordersState == 0">
			    	<s:if test="orders.settlementStatus==0">
			    		未付款
			    	</s:if>
			    	<s:else>
			    		已付款
			    	</s:else>
		    	</s:if>
			    <s:elseif test="orders.ordersState == 3">已配货</s:elseif>
			    <s:elseif test="orders.ordersState == 4">已发货</s:elseif>
			    <s:elseif test="orders.ordersState == 5">已收货</s:elseif>
			    <s:elseif test="orders.ordersState == 6">订单取消</s:elseif>
			    <s:elseif test="orders.ordersState == 7">异常订单</s:elseif>
			    <s:elseif test="orders.ordersState == 9">已评价</s:elseif>
	    </td>
   		</tr>
   		<tr>
  			<td colspan="4" style="background-color: #F0F0F0;">
  				<span><font style="font-size: 15px;">商品信息：</font></span>
  			</td>
  		</tr>
  		<s:iterator value="orderList" var="orderDetail">
  			<tr>
	   			<td style="text-align: right;width: 150px;">商品名称:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="#orderDetail.productFullName"/></td>
	   			<td style="text-align: right;width: 150px;">商品条形码:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="#orderDetail.barCode"/></td>
   			</tr>
   			<tr>
	   			<td style="text-align: right;width: 150px;">商品销售价:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="#orderDetail.salesPrice"/></td>
	   			<td style="text-align: right;width: 150px;">商品数量:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="#orderDetail.count"/></td>
   			</tr>
  		</s:iterator>
  		<s:if test="shipping!=null&&shipping.code!='tongcheng'">
   		 <tr>
  			<td colspan="4" style="background-color: #F0F0F0;">
  				<span><font style="font-size: 15px;">物流公司信息：</font></span>
  			</td>
  		</tr>
	   		<tr>
	   			<td style="text-align: right;width: 150px;">物流公司名称:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="shipping.deliveryCorpName"/></td>
	   			<td style="text-align: right;width: 150px;">物流公司代码:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="shipping.code"/></td>
	   		</tr>
	   		<tr>
	   			<td style="text-align: right;width: 150px;">物流公司网址:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="shipping.deliveryUrl"/></td>
	   			<td style="text-align: right;width: 150px;">物流编号:&nbsp;&nbsp;</td><td>&nbsp;&nbsp;<s:property value="shipping.deliverySn"/></td>
	   		</tr>
  	 </s:if>
  		<%-- <tr>
  			<td colspan="4" style="background-color: #F0F0F0;">
  				<span><font style="font-size: 15px;">其它信息：</font></span>
  			</td>
  		</tr>
   		<tr>
   			<td style="text-align: right;width: 150px;">订单附言:&nbsp;&nbsp;</td>
   			<td  colspan="3">&nbsp;&nbsp;
   				<s:if test="#request.ordersLog.operatorName!=null">
   					操作人:<s:property value="#request.ordersOPLog.operatorName"/>在:<s:property value="#request.ordersOPLog.operatorTime"/>进行了留言，内容是<s:property value="#request.ordersOPLog.ordersMsg"/>
   				</s:if>
   			</td>
   		</tr>
   		 <tr>
   		 	<td align="right" style="width: 200px;">订单备注(客服留言):</td><td align="left" colspan="3" style="width: 300px;"><s:property value="orders.ordersRemark"/></td>
   		 </tr> --%>
    </table>
    <br/><br/>
  </body>
</html>
