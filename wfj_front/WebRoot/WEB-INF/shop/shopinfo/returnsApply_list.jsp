<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<%--会员：退货列表--%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" ></meta>
		<title>我的退货-退货列表</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		$(function (){
			$('#deliver-goods-overlay').css("display","none");
		});
		function checkRsState(obj,remenber){
			if(obj.value<remenber){
				lalert("提醒","不可逆向操作！");
				$("#"+obj.id).val(remenber);
			}
		}
		function tijiao2(id,ordersId,remenber){
			$("#submitLink").attr("disabled","disabled");
			var as=$("#asState_"+id).val();
			var rs=$("#rsState_"+id).val();
			if(parseInt(remenber)==parseInt(rs)){
				lalert("提醒","当前状态已操作，不可重复操作！");
				return false;
			}
			//获取当前页
			var index=$("#currentPage").val();
			
			//取消rs下拉框的disable属性
			$("#rsState_"+id).removeAttr("disabled");
			if(type==3){
				window.location = "${pageContext.request.contextPath}/front/store/frontReturnsApplyOPLog/updataSate.action?asState="+as+"&rsState="+rs+"&returnsApplyId="+id+"&currentPage="+index;
			}else{
				if(rs==4){
					$("#returnsApplyId").val(id);
					$('#deliver-goods-overlay').css("display","");
				}else{
					window.location = "${pageContext.request.contextPath}/front/store/frontReturnsApplyOPLog/updataSate.action?asState="+as+"&rsState="+rs+"&returnsApplyId="+id+"&currentPage="+index;
				}
			}
			return true;
		}
		function close_div(){
			$("#deliver-goods-overlay").css("display","none");
			$("#overly_div").removeAttr("style");
		}
		$(function(){
			//初始化物流公司信息
			$('#code').combogrid({
				idField:'code',
				textField:'deliveryCorpName',
				rownumbers:true,//序号
				editable:false,
				width:300,
				url:'${pageContext.request.contextPath}/back/logistics/initLogistics.action',
				columns:[[
					{field:'deliveryCorpName',title:'物流公司名称',width:100},
					{field:'deliveryUrl',title:'物流公司网址',width:150}
				]]
			});
		});
		
		//如果审核状态选择拒绝 ，则退货状态更改为 ‘订单已完成’
		function set1(id,value){
			if(value==3){
				$("#"+id).val(5);//订单处理完成
				$("#"+id).attr("disabled","true");
			}else{
				$("#"+id).removeAttr("disabled");
				$("#"+id).val(1);//退货中
			}
		}
		</script>

	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
					<section id="Marketpalce">					
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">退货</h3>
						<table width="100%" border="0"
							class="RecentlyOrders_table publicMarginT10">
							<tr>
								<td width="15%">
									订单编号
								</td>
								<td width="30%">
									退货图片
								</td>
								<td width="10%">申请时间</td>
								<td width="15%">退货编号</td>
								<td width="10%">审核</td>
								<td width="10%">退货状态</td>
								<td width="16%">操作</td>
							</tr>
<!-- 							订单对象 -->
							<s:iterator value="returnsApplyListMap">
								<tr>
									<td>
										<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderInfo.action?orders.ordersId=${ordersId}"><s:property value="ordersNo" /></a>
									</td>
									<td>
										<div class="ClearBoth" style="text-align: center;">
										<s:iterator value="imgList" var="img">
											<a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
												<img style="height: 50px; width: 50px;" title="<s:property value="productName"/>"
													alt="<s:property value="productName"/>"
													src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#img'/>" />
											</a>
										</s:iterator>
										</div>
									</td>
									<td>
										<s:date name="applyTime"/>
									</td>
									<td>
										<s:property value="returnsApplyNo"/>
									</td>
									<td>
										<s:if test="applyState!=1">
											<select id="asState_<s:property value='returnsApplyId'/>" disabled="disabled">
												<s:iterator value="#application.keybook['applyState']" var="kb">
													<s:if test="applyState==#kb.value">
														<option checked="checked" value=""><s:property value="#kb.name"/></option>
												</s:if>
												 </s:iterator>
											 </select>
										</s:if>
										<s:else>
											<select id="asState_<s:property value='returnsApplyId'/>" name="asState" onchange="set1('rsState_<s:property value='returnsApplyId'/>',this.value)">
												<s:iterator value="#application.keybook['applyState']" var="kb">
													<s:if test="1!=#kb.value">
														<option value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
													</s:if>
												 </s:iterator>
											 </select>
										</s:else>
									</td>
<!-- 									 退款状态 -->
									<td>
										<s:if test="returnsState==5">
											<s:if test="disposeMode==3">
												<select id="rsState_<s:property value='returnsApplyId'/>" disabled="disabled">
													<s:iterator value="#application.keybook['returnsState']" var="kb">
														<s:if test="returnsState==#kb.value">
															<option selected="selected" value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
														</s:if>
													 </s:iterator>
												</select>
											</s:if>
											<s:elseif test="disposeMode==2">
												<select id="rsState_<s:property value='returnsApplyId'/>" disabled="disabled">
													<s:iterator value="#application.keybook['trade']" var="kb">
														<s:if test="returnsState==#kb.value">
															<option selected="selected" value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
														</s:if>
													 </s:iterator>
												</select>
											</s:elseif>
										</s:if>
										<s:else>
											<s:if test="disposeMode==3">
												<select id="rsState_<s:property value='returnsApplyId'/>" name="rsState" onchange="checkRsState(this,<s:property value='returnsState'/>)">
													<s:iterator value="#application.keybook['returnsState']" var="kb">
														<s:if test="returnsState==#kb.value">
															<option selected="selected" value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
														</s:if>
														<s:else>
															<option value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
														</s:else>
													</s:iterator>
												</select>
											</s:if>
											<s:elseif test="disposeMode==2">
												<select id="rsState_<s:property value='returnsApplyId'/>" name="rsState" onchange="checkRsState(this,<s:property value='returnsState'/>)">
													<s:iterator value="#application.keybook['trade']" var="kb">
														<s:if test="returnsState==#kb.value">
															<option selected="selected" value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
														</s:if>
														<s:else>
															<option value="<s:property value='#kb.value'/>"><s:property value="#kb.name"/></option>
														</s:else>
													</s:iterator>
												</select>
											</s:elseif>
										</s:else>
									</td> 
									<td>
										<s:if test="returnsState!=5">
											<a id="submitLink" style="color: #005EA7;" href="javascript:void(0);" onclick="javascript:tijiao2('<s:property value="returnsApplyId"/>','<s:property value="ordersId"/>','<s:property value="returnsState"/>');">提交</a> |
										</s:if>
										<a href="${pageContext.request.contextPath }/front/store/frontShopComplaints/gotoComplaintsLogInfoPage.action?returnsApplyId=<s:property value='returnsApplyId'/>" style="color: #005EA7;">详情</a>
									</td>
								</tr>
							</s:iterator>
						</table>
						<!--分页 start-->
						<form id="formModule" action="${pageContext.request.contextPath }/front/store/frontShopComplaints/gotoReturnsApplyListPageShop.action" method="post">
							<div class="pageList strong ClearBoth">
								<jsp:include page="../../util/splitPage.jsp" />
							</div>
						</form>
						<!--分页 end-->
					
					</section>

				</div>
				<div id="deliver-goods-overlay" style="width: 400px;height: 300px; background-color: #fff;border: 2px solid #d5af6b; border-radius: 5px; padding: 15px; position: absolute;top:376px; left:40%; z-index:9; display:none;">
					<form  method="post" id="deliver-goods-form" action="${pageContext.request.contextPath}/front/store/frontShopComplaints/saveTradeOrder.action">
						<input id="returnsApplyId" type="hidden" name="returnsApplyId" value=""/>
						<table>
							<tr>
								<td style="text-align: right">物流单号:</td>
								<td><input style="border: 1px #ccc solid;" type="text" name="shipping.deliverySn" value="" class="{validate:{required:true,onlyCharAndNumber:true}}"/></td>
							</tr>
							
							<tr>
								<td style="text-align: right">物流公司名称:</td>
								<td colspan="3">
								<select id="code" name="shipping.code" class="{validate:{required:true}}"></select><span id="codeError"></span>
								</td>
							</tr>
						</table>
						
						 <div style="width:250px; height:auto; margin-top:25px; padding-left:80px;">
							<p class="modal-buttons">
								<button style="border: 0; cursor: pointer; border: 1px #77235b solid;" type="submit">提交</button>
								<button style="border: 0; cursor: pointer; border: 1px #77235b solid; border-radius: 3px; color: #fff;
 									font-size: 16px; font-weight: bold; height: 30px; line-height: 20px;margin-left: 50px;text-align: center;width: 60px; background-color:  #d5af6b;" onclick="close_div()" type="button">关闭</button>
							</p>
							
						</div>
					</form>
					
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>