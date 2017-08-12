<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>客服QQ列表</title>
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
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/shopCustomerService/listShopCustomerService.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:5px;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div class="FontSize14" style="line-height:24px;">
							QQ号:
						</div>
						<div>
							<input id="qQQ" name="QQ" style="height:22px; line-height: 22px; border:1px #ccc solid;" type="text" value="${orders.ordersNo}"/>
						</div>
						<div class="FontSize14" style="line-height:24px;">
							昵称:
						</div>
						<div>
							<input id="qnikeName" name="nikeName" style="height:22px; line-height: 22px; border:1px #ccc solid;" type="text" value="${orders.ordersNo}"/>
						</div>
						<div class="FontSize14" style="line-height:24px;">
							使用状态:
						</div>
						<div>
							<select id="quseState" name="useState" style="height:22px; line-height: 22px; border:1px #ccc solid;">
								<option value="">全部</option>
								<option value="1">正常使用</option>   
								<option value="0">作废</option>   
							</select>
						</div>
						<div>
							<input type="button" value="" onclick="changePage('1');"class="publicNoBorder ColorWhite1 FontSize14" style=" cursor: pointer; height:22px; width:42px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/chaxun.jpg)"/>
						</div>
					</div>
					</aside>
					<div class="MarketpalceTree publicMarginTop15" style="width:963px;">
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<%-- <td width="5%">
										<input id="selectAll" style="margin-top:14px;" class="checkboxClass2" type="checkbox" name="ordersInfo" onclick="selectAllCheckBox(this.id)" value=""/><span class="selectAll2"></span>
									</td> --%>
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

								<s:iterator value="listMap" var="map">
									<tr>
										<%-- <td>
											<input class="checkboxClass"  style="margin-top:14px;" name="ordersInfo" onclick="doCheckBox()" id="checkbox_<s:property value="#map.customerServiceId" />" type="checkbox" value="<s:property value="ordersId" />"/>
											
										</td> --%>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="#map.trueName" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="#map.nikeName" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="#map.qq" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:if test="#map.useState == 1">正常使用</s:if>
											<s:elseif test="#map.useState == 0">作废</s:elseif>
										</td>
										<td class="line-height2 lastR" >
											<a href="${pageContext.request.contextPath }/front/store/customerService/getCustomerServiceInfo.action?ids=<s:property value="#map.customerServiceId" />"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center; width: 40%; margin-left: 10px;float: left;">修改</a>
											<a href="${pageContext.request.contextPath }/front/store/shopCustomerService/deleteShopCustomerService.action?ids=<s:property value="#map.ccsId" />"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center;  width: 40%;margin-right: 10px;float: right;" >删除</a>
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
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
