<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请货详情</title>
<%@ include file="../include/head.jsp"%>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script> 
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
	.ClearBoth2 ul li{wdith:800px;height:30px;}
	.ClearBoth2 ul li.ClearTitile{height:25px; text-align:left;padding-left:25px;font-weight:900;font-size:12px;}
	.ClearBoth2 ul li span{width:225px;display:block; float:left;height:30px;margin-right:15px;line-height:30px; text-align:left;padding-left:25px;}
	*+html .ClearBoth2 ul li{wdith:800px;height:26px;}
	*+html .ClearBoth2 ul li.ClearTitile{width:800px;height:21px; text-align:left;}
	*+html .ClearBoth2 ul li span{width:225px;display:block; float:left;height:26px;margin-right:15px;line-height:26px; text-align:left;padding-left:25px;}
</style>

</head>

<body>
<%@ include file="../include/header.jsp"%>

<div class="margin-center PublicWidth1004">
<article class="publicrelative publicMarginBot15 publicPaddingB30 ClearBoth">
<h1 class="FontSize16 publicPadding10 publicBorderB FontSizeB">申请退货明细</h1>
<br>
<!-- 规则说明 -->
<div style="border: 1px solid #EDD28B;background: none repeat scroll 0 0 #FFFDEE;">
	<div style="margin-top: 8px;margin-left: 10px;">
			<h5 style="font-size: 13px;">售后服务规则</h5>
	</div>
	<div style="display: block;margin-left: 25px;margin-right: 25px;margin-top: 15px;margin-bottom: 15px;">
		1.由您实际收到货物日期起7日内出现质量问题可退货。 <br>
		2.由您实际收到货物日期起15日以上，为了不耽误您使用，缩短故障商品的维修时间，建议您在商品出现故障时直接联系当地厂商维修网点处理。请访问厂商官方网站或拨打厂商服务热线，咨询维修网点信息，凭借发票可到网点维修。国家三包规定保修期内，无品牌厂商售后服务产品，SHOPJSP提供免费维修服务。           <br>
		3.在商品无任何问题情况下，SHOPJSP承诺：由您实际收到货物日期起7日内，只要未使用不影响二次销售，都可以全额退货。SHOPJSP所售均为全新品，为保护消费者利益，以下情况视为影响二次销售：<br>
		1）大家电类如冰箱、电视、洗衣机、空调、热水器等非产品本身问题概不接受退换，请谨慎下单； <br>
		2）密封产品原包装打开； <br>
		3）产品通电、过水、插入卡槽等已使用； <br>
		4）钻石、黄金、手表、奢侈品、珠宝首饰及个人配饰类产品不接受退货。 <br>
		注：<span style="color: #FF6600;">博世电动工具不在国家三包范围之列 出现质量问题可直接联系厂家维修 卡西欧电子琴需凭借厂家检测报告进行鉴定</span>
	</div>
</div>
<br>
<!-- 商品信息 -->
<section class="publicMarginTop30">
	<table width="100%" border="0" class="Sales_after">
	  <tr class=" publicBorder gradient">
	    <td width="20%">订单号</td>
	    <td width="20%">商品名称</td>
	    <td width="20%">店铺名称</td>
	    <td width="10%">商品价格</td>
	    <td width="10%">成交价格</td>
	    <td width="10%">购买数量</td>
	  </tr>
	  <tr class="checkbox_tr">
	    <td><s:property value="orders.ordersNo"/></td>
	    <td class="line-height150 text-left">
	    	<a target="_blank"  href="${pageContext.request.contextPath}/productInfo/<s:property value='ordersList.productId'/>"><img title="<s:property value="ordersList.productFullName"/>" style="height: 50px;width: 50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='ordersList.logoImage'/>"/></a><br>
	    </td>
	    <td><s:property value="ordersList.shopName"/></td>
	    <td>￥<s:property value="ordersList.salesPrice"/></td>
	    <td>￥<s:property value="ordersList.memberPrice"/></td>
	    <td><s:property value="ordersList.count"/></td>
	  </tr>
	</table>
</section>

<!-- 服务单明细 -->
<aside class="parent_for_p publicMarginTop15">
		<fieldset class="publicPadding10 pBorderY_e1db66">
		<legend class="publicMargin10" ><h2>服务单明细：</h2></legend>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13">服务类型：</p>
			<p>退货</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13">提交数量：</p>
			<p>
			<s:property value="returnsApply.count"/>
			</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13">问题描述：</p>
				<p>
					<textarea disabled="disabled" name="returnsApply.problemDescription" cols="70" rows="5" wrap="virtual" ><s:property value="returnsApply.problemDescription"/></textarea>
				</p>
		</div>
		<s:if test="showUploadImgList!=null&&showUploadImgList.size>0">
			<div class="ClearBoth" >
				<p class="widthpx150 text-right FontSize13">申请退货图片：</p>
				<p>
					<s:iterator value="showUploadImgList">
						<img style="height: 50px; width: 50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property />"/>
					</s:iterator>
				</p>
			</div>
		</s:if>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13">商品返回方式：</p>
			<p>快递至商家</p>
		</div>
		<%--<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13">收货地址:</p>
			<p><input name="returnsApply.shippingAddress" type="text" value="" class="{validate:{required:true,maxlength:[150]}}"></p>
		</div> --%>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13">联系人姓名：</p>
			<p><s:property value='orders.consignee'/></p>
		</div> 
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13">手机号码：</p>
			<p><s:property value='orders.mobilePhone'/></p>
		</div>
		</fieldset>
</aside>
<aside class="parent_for_p publicMarginTop15">
		<fieldset class="publicPadding10 pBorderY_e1db66">
		<legend class="publicMargin10" ><h2>操作明细：</h2></legend>
		<div class="ClearBoth ClearBoth2">
		    <ul>
		        <li class="ClearTitile">退货申请单号:&nbsp;<s:property value="returnsApply.returnsApplyNo"/></li>
		        <li>
		            <span style="width: 150px;">操作人</span>
		            <span>操作信息</span>
		            <span>操作时间</span>
		        </li>
		        <s:iterator value="returnsApplyOPLogList">
			        <li>
			        	<span style="width: 150px;"><s:property value="operatorLoginName" /></span>
			            <span><s:property value="comments" /></span>
			            <span><s:date name="operatorTime" /> </span>
			        </li>
		        </s:iterator>
		    </ul>
		</div>
		
		</fieldset>
</aside>
</article>
		<div style="padding-left: 480px;">
			<p class="widthpx150 "></p>
			<p>
				<input onclick="window.history.back(-1); " type="button" value="返回" class="publicPadding5_10 BackgroundOrange FontSize13 FontSizeB ColorWhite1 pBorderY_e1db66 publicMarginR10">
			</p>
		</div>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
