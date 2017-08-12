<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单中可退货商品列表</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/html5.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<style>
			.m7 .mt h3 {
			    background: none repeat scroll 0 0 #FFFFFF;
			    color: #666666;
			    font-size: 12px;
			    line-height: 25px;
			    padding: 0 8px;
			}
			.m, .sm {
			    margin-bottom: 10px;
			}
			.m, .mt, .mc, .mb, .sm, .smt, .smc, .smb {
			    overflow: hidden;
			}
			.m7 .mc {
			    border: 1px solid #E6E6E6;
			    padding: 20px;
			}
			
		</style>
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
					<section id="Marketpalce">					
						<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">
							退换货
						</h3>										
						<table width="100%" border="0"
							class="RecentlyOrders_table publicMarginT10">
							<tr>
								<td width="15%">
									订单编号
								</td>
								<td width="32%">
									商品
								</td>
								<td width="10%">
									下单时间
								</td>
							</tr>
<!-- 							订单对象 -->
							<tr>
								<td>
									<a	href="${pageContext.request.contextPath }/front/customer/myOrders/accountOrderDetail.action?orders.ordersId=<s:property value="ordersId" />"><s:property value="orders.ordersNo" /></a>
								</td>
								<td>
									<div class="ClearBoth"  style=" text-align: center;">
										<s:iterator value="mapImg">
											<div style="width:100px; float:left; height:100px;">
											<a style="display:inline-block;width:100px;" target="_blank"  href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
												<img style="height: 50px; width: 50px; margin-bottom:10px;" title="<s:property value="productName"/>"
													alt="<s:property value="productName"/>"
													src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="thumbnail"/>" />
											</a>
											<br>
											<div  style=" text-align: center;  width:100px;">
												<s:if test="list.size>0">
													<s:if test="sq==0">
														<a style="color:#fff;padding:3px 10px;text-align: center;margin-top:5px; background-color:#ff7000;  border: 1px #db6305 solid; cursor: pointer;" href="${pageContext.request.contextPath }/front/customer/frontCustomerComplaints/gotoComplaintsSalesInfoPage.action?productId=<s:property value='productId'/>&ordersId=<s:property value='ordersId'/>&countTatol=<s:property value='countTatol'/>&count=<s:property value='count'/>">
														申请
																		
														</a>
													</s:if>
													<s:else>
														<a style="color:#fff; padding:3px 10px;text-align: center;margin-top:5px; background-color:#acacac;  border: 1px #828282 solid; cursor: pointer;">
									
														申请
														
													
														</a>
													</s:else>
												</s:if> 
												<s:else>
													<a style="color:#fff; padding:3px 10px;text-align: center;margin-top:5px; background-color:#ff7000;  border: 1px #db6305 solid; cursor: pointer; " href="${pageContext.request.contextPath }/front/customer/frontCustomerComplaints/gotoComplaintsSalesInfoPage.action?productId=<s:property value='productId'/>&ordersId=<s:property value='ordersId'/>">
													申请
											
													</a>
												</s:else>
											</div>
											</div>
										</s:iterator>
									</div>
								</td>
								<td>
									<s:date name="orders.createTime" format="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
						</table>
						<br>
						<fieldset class="publicPadding10 pBorderY_e1db66">
							<legend class="publicMargin10"><h3>退货申请常见问题：</h3></legend>
							<div class="mc">
								1. “申请”按钮若为灰色，可能是因为该商品正在退货中;<br>
		                        2. 查看 <a style="color: #005EA7;">售后政策;</a>
							</div>
						</fieldset>
					</section>
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
