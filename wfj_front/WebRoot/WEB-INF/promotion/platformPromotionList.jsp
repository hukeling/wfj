<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>促销活动</title>
	<%@ include file="../shop/include/head.jsp"%>
	<script type="text/javascript">
// 		$(function() {
// 			$("ul.FavouriteTab").tabs("div.TabParent_lj > div", {
// 				initialIndex : 0
// 			}, {
// 				history : true
// 			});
// 		});
	</script>
</head>

<body>
	<%@ include file="../shop/include/header.jsp"%>
	
	<div class="margin-center PublicWidth1004">
		<div class="ClearBoth" style="margin-top:10px;">
			<%@ include file="../shop/include/left_shop.jsp"%>
			<!--right-->
			<div id="rightBox" class="float-right publicHidden">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">促销</h3>
					<div class="MarketpalceTree publicMarginTop15">
						<ul class="FavouriteTab">
<!-- 							<li class="first-child"><a href="javascript:void(0);" class="FontSize14 radius gradient">促销活动列表</a> -->
							</li>
						</ul>
						<div class="TabParent_lj ClearBoth">
							<div>
								<div id="test" class="publicPadding10">
									<form id="formModule" action="${pageContext.request.contextPath}/front/store/platformPromotion/gotoPlatformPromotionListPage.action" method="post">
										<input id="paramsCheckbox" name="paramsCheckbox" type="hidden" value="" />
										<table width="100%" border="0"  class="hydsOrders_table publicMarginT10">
											<tr >
												<td width="13%">活动名称</td>
												<td width="16%">开始时间</td>
												<td width="16%">结束时间</td>
												<td width="18%">操作</td>
											</tr>
											<!-- 遍历List -->
											<s:iterator value="platformPromotionList">
												<s:if test="isPass==2">
													<tr>
														<td>
														<a href="${pageContext.request.contextPath}/front/store/salesPromotionCenter/gotoSalesPromotionCenter.action?promotionId=<s:property value='promotionId'/>"><s:property value="promotionName"  /></a>
														</td>
														<td>
															<s:date name="beginTime" format="yyyy-MM-dd HH:mm:ss"/>
														</td>
														<td>
															<s:date name="endTime" format="yyyy-MM-dd HH:mm:ss"/>
														</td>
														<td >
															<s:if test="useStatus==0">
																<a href="${pageContext.request.contextPath}/front/store/platformPromotion/gotoStoreApplyPromotionListPage.action?shopInfoId=<s:property value='shopInfoId'/>&promotionId=<s:property value='promotionId'/>&pageIndex=1&pageIndex2=1">参与活动</a>
															</s:if><s:elseif test="useStatus==1">
																活动已开启
															</s:elseif><s:elseif test="useStatus==2">
																活动已结束
															</s:elseif>
														</td>
													</tr>
												</s:if>
											</s:iterator>
										</table>
										<!--分页 start-->
										<s:if test="platformPromotionList.size>0">
											<jsp:include page="../util/splitPage.jsp" />
										</s:if>
										<!--分页 end-->
								</div>
							</div>
						</div>
						</form>
					</div>
				</section>
			</div>
			<!--//right-->
		</div>
	</div>
	<%@ include file="../shop/include/footer.jsp"%>
</body>
</html>
