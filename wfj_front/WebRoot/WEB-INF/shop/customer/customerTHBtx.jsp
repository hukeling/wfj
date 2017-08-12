<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>佣金币提现</title>
		<%@ include file="../include/head.jsp"%>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<STYLE type="text/css">
			.RecentlyOrders_table tr{
				font-size: 13px;
				line-height: 3em;
			}
			.RecentlyOrders_table{
				table-layout:fixed;
			}
			.RecentlyOrders_table td{
				text-overflow: ellipsis;word-break:keep-all;white-space: nowrap;overflow:hidden;
			}
		</STYLE>
		<script type="text/javascript">
			jQuery.validator.addMethod("checkYHKNO", function(value, element) {
				var regex = /^[0-9]{16,19}$/;
			    return this.optional(element) || (regex.test(value));
			}, "银行卡号输入错误");
			//表单验证提交
			$(function() { //表单验证
			    $("#form1").validate({
			        meta: "validate",
			        submitHandler: function(form) {
						form.submit();
			        }
			    });
			});
			
			//对比SHOPJSP币总数
			jQuery.validator.addMethod("checkYHKMax", function(value, element) {
				var t=$("#transactionAmount").val();
				var max='<s:property value="#request.thbMAX"/>';
			    return this.optional(element) || (parseInt(t)<=parseInt(max));
			}, "提现佣金币数量不可大于可提现总数");
		</script>
	</head>
	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<s:if test="#session.customer.type==1">
					<%@ include file="../include/left_account.jsp"%>
				</s:if>
				<s:elseif test="#session.customer.type==3">
					<%@ include file="../include/left_account_gr.jsp"%>
				</s:elseif>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">佣金币提现</h3>
				<fieldset class="publicNoBorder">
							<form id="form1" name="form1" action="${pageContext.request.contextPath}/front/customer/thbtx/saveInfo.action" method="post">
								<div class="ClearBoth ClearBoth2">
								<ul class="strong">
									<li class="ClearTitile" style="height:24; line-height:24px;">提现说明：每日12点前提现，次日上午到账，每日12点后提现，次日下午到账。</li>
									<li class="ClearTitile" style="height:24; line-height:24px;">提醒：当前佣金币提现比例为<font color="red"><s:property value='#request.thbtxBL'/>:1</font></li>
									<li class="ClearTitile" style="height:24; line-height:24px;">当前可用佣金币数量：<font color="red">
											<s:property value='#request.thbMAX'/>
									  </font>
									</li>
									<li class="ClearTitile" style="height:24; line-height:24px;">当前可兑换金额：<font color="red">
											<s:property value='#request.divide'/>元
									</font></li>
									
									<li style="height:22; line-height:22px;">
									<table>
										<tr style="width: 600px;">
											<td align="right"><span style="text-align:right" ><i class="ColorRed">*</i>&nbsp;&nbsp;持卡人:</span></td>
											<td align="left"><input id="cardHolder" name="incomePayDetail.cardHolder" value="" type="text" class="widthpx190 height25_h {validate:{required:true,maxlength:[25]}}"></td>
										</tr>
										<tr style="width: 600px;">
											<td align="right"><span style="text-align:right" ><i class="ColorRed">*</i>&nbsp;&nbsp;卡号:</span></td>
											<td align="left"><input id="cardNumber" name="incomePayDetail.cardNumber" value="" type="text" class="widthpx190 height25_h {validate:{required:true,checkYHKNO:true}}"></td>
										</tr>
										<tr style="width: 600px;">
											<td align="right"><span style="text-align:right" ><i class="ColorRed">*</i>&nbsp;&nbsp;预留手机号:</span></td>
											<td align="left"><input id="phone" name="incomePayDetail.phone" value="" type="text" class="widthpx190 height25_h {validate:{required:true,mobile:true}}"></td>
										</tr>
										<tr style="width: 600px;">
											<td align="right"><span style="text-align:right" ><i class="ColorRed">*</i>&nbsp;&nbsp;提现佣金币数量:</span></td>
											<td align="left"><input id="transactionAmount" name="incomePayDetail.transactionAmount" value="" type="text" class="widthpx190 height25_h {validate:{required:true,digits:true,checkYHKMax:true,min:1}}"></td>
										</tr>
										<tr style="width: 600px;">
											<td align="right"><span style="text-align:right" >&nbsp;&nbsp;留言:</span></td>
											<td align="left"><textarea id="customerMessage" name="incomePayDetail.customerMessage" cols="50" rows="5" value="" class="{validate:{maxlength:[250]}}" style="border:1px #d3d3d3 solid;"></textarea></td>
										</tr>
									</table>
									<div>
										<button type="submit" id="tijiao" class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" style="text-align: center;margin-left: 150px;margin-top: 20px;">提交</button>
									</div>
								</li>
								</ul>
								</div>
							</form>
						</fieldset>
				</div>
				<!--//right-->
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
