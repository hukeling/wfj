<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单列表</title>
		<%@ include file="../include/head.jsp"%>
        <link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}css/themes/default/easyui.css"/>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/common.js"></script>
	<script>
	$(function() {
 		$("#ordersSort").val($("#selectParams").val());
		$("#ordersStatus").val($("#selectStatus").val());
		var status = $("#selectStatus").val();
		$("#showSatus").html($("#status"+status).text());
		$('#deliver-goods-overlay').css("display","none");
		$("#deliver-goods-form").validate({meta: "validate",submitHandler:function(form){
	  	    	var code = $("#code").combogrid('getValue');
	 	    		$("#codeError").val("");
	  	    	if(code[0]==null||code[0]==""){
	  	    	$("#codeError").html("<font color='red'>不可为空</font>");
	  	    	}else{
			form.submit();
	  	    	}
	  	      }
			});
		});
		
	function changeSend(ordersId){
		$("#overly_div").attr("style","opacity: 0.4;");
		$("#ordersId").val(ordersId);
		$('#deliver-goods-overlay').css("display","");
	}
		//更改成正在配货
		function changeOrderState(ordersId){
			window.location = "${pageContext.request.contextPath}/front/store/shopOrder/changgeOrdersState.action?orders.ordersId="+ ordersId+"&currentPage=${currentPage}";
		}
		//选择快递公司的同时，回填网址信息
		var urlInfo=function(){
			//获取select控件的指
			var svalue=$("#sdeliveryCorpName").val();
			//与数据字典中快递公司list做比较
			<s:iterator value="#application.keybook['expressCompany']" var="kb">
		           if(svalue=="<s:property value='#kb.name'/>"){
		                   $("#deliveryUrl").val("<s:property value='#kb.value'/>");
		           }
			</s:iterator>
		};
		//全选和反选
		function selectAllCheckBox(id) {
			var b = document.getElementsByName("ordersInfo");
			var all = document.getElementById(id);
			if (all != null) {
				if (all.checked) {
					for (var i = 0; i < b.length; i++) {
						b[i].checked = true;
					}
				} else {
					for (i = 0; i < b.length; i++) {
						b[i].checked = false;
					}
				}
			}
		}
		//点击一个checkbox的操作
		function doCheckBox() {
			//获取选中checkbox的长度
			var checkLength = $('input[name=ordersInfo]:checked').length;
			//获取所有checkbox的长度（除全选checkbox外）
			var totalLength = $('input[name=ordersInfo]').length;
			if (checkLength == totalLength) {
				//选中全选checkbox
				$("#selectAll").attr("checked", true);
			} else {
				$("#selectAll").removeAttr("checked");
			}
		}
		function close_div(){
			$("#deliver-goods-overlay").css("display","none");
			$("#overly_div").removeAttr("style");
		}
</script>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		<div id="overly_div" class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">QQ客服列表</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/shopOrder/shopOrderList.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:5px;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div class="FontSize14" style="line-height:22px;">
							QQ号:
						</div>
						<div>
							<input name="orders.ordersNo" style="height:22px; line-height: 22px;" type="text" value="${orders.ordersNo}"/>
						</div>
						<div class="FontSize14" style="line-height:22px;">
							昵称:
						</div>
						<div>
							<input id="qnikeName" name="nikeName" style="height:22px; line-height: 22px;" type="text" value="${orders.ordersNo}"/>
						</div>
						<div class="FontSize14" style="line-height:22px;">
							使用状态:
						</div>
						<div>
							<select id="ordersStatus" name="orders.ordersState" onchange="changePage('1');">
								<option id="status" value="">全部</option>
								<option id="status1" value="1">正常使用</option>   
								<option id="status1" value="0">作废</option>   
							</select>
						</div>
						<div>
							<input type="submit" value="查询" onclick="changePage('1');"
								class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" style=" cursor: pointer;"/>
						</div>
					</div>
					</aside>
					<div class="MarketpalceTree publicMarginTop15" style="width:963px;">
						<div id="showSatus" class="publicNoBorder publicPadding2_5 BackgroundOrange ColorWhite1 FontSize14 text-center" style="width:120px; float: left;"></div>
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td width="5%">
										<input id="selectAll" style="margin-top:14px;" class="checkboxClass2" type="checkbox" name="ordersInfo" onclick="selectAllCheckBox(this.id)" value=""/><span class="selectAll2"></span>
									</td>
									<td width="18%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;">
										真实姓名
									</td>
									<td width="14%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										昵称
									</td>
									<td width="14%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										QQ
									</td>
									<td width="10%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										使用状态
									</td>
									<td width="12%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										操作
									</td>
								</tr>

								<s:iterator value="productInfoList">
									<tr>
										<td>
											<input class="checkboxClass"  style="margin-top:14px;" name="ordersInfo" onclick="doCheckBox()" id="checkbox_<s:property value="ordersId" />" type="checkbox" value="<s:property value="ordersId" />"/>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="ordersNo" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="customerName" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:if test="ordersState == 1">正常使用</s:if>
											<s:elseif test="ordersState == 0">作废</s:elseif>
										</td>
										<td class="line-height2 lastR">
											<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderInfo.action?orders.ordersId=${ordersId}&orders.customerId=${customerId}"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center;">订单明细</a>
											<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderPrint.action?orders.ordersId=${ordersId}&orders.customerId=${customerId}"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center;" target="_blank" >打印</a>
											<s:if test="sonaccountId!=null">
												<s:if test="ordersState == 0||ordersState==1">
													<a href="javascript:;" onclick="changeOrderState(<s:property value="ordersId" />);" class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius">更改为已配货</a>
												</s:if>
											</s:if>
											<s:else>
												<s:if test="ordersState==1">
													<a href="javascript:;" onclick="changeOrderState(<s:property value="ordersId" />);" class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius">更改为已配货</a>
												</s:if>
											</s:else>
											<s:if test="ordersState == 3">
												<a href="javascript:changeSend(${ordersId});" id="${ordersId}"  class="edit-deliver-goods ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius">更改为已发货</a>
											</s:if>
										</td>
									</tr>
								</s:iterator>
							</table>
							
							<!--分页 start-->
							<div class="pageList strong ClearBoth">
								<jsp:include page="../../util/splitPage.jsp" />
							</div>
							<!--分页 end-->
						</div>
					</div>
				</form>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
