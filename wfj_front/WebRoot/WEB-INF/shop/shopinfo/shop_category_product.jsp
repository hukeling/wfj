<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商品分类列表</title>
		<%@ include file="../include/head.jsp"%>
		<script>
			var submit_form=function(){
				document.getElementById("currentPage").value=1;
				$("#formModule").submit();
			}
		</script>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
					<form id="formModule" name="formModule" method="post" action="${pageContext.request.contextPath}/front/store/frontShopCategory/findCategoryProduct.action">
					<aside
						class="shop_orderSearch publicPaddingB10 ClearBoth">
						<p>
							店铺分类 :
							<select name="shopProCategory.shopProCategoryId" >
								<option value="">所有</option>
								<s:iterator value="listShopPc">
									<option value="<s:property value="shopProCategoryId"/>" <s:if test="shopProCategoryId==shopProCategory.shopProCategoryId">selected="selected"</s:if>><s:property value="shopProCategoryName"/></option>
								</s:iterator>
							</select>
						</p>
						<p>
							<input type="hidden" name="shopProCategory.shopInfoId" value="${shopProCategory.shopInfoId}"/>
							<input style=" border: 0;" onclick="submit_form()" type="button" value="搜索" class="publicPadding2_5 gradientRed publicBorderRed radius FontSize14 ColorWhite1">
						</p>
					

					</aside>
					<div class="clear"></div>

					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td  style="border-left: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
										分类名称
									</td>
									<td  style=" border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid; width:25%;">
										商品名称
									</td>
									<td style=" border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
										图片
									</td>
									<td style=" border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
										上架状态
									</td>
									<td style=" border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
										销售价格
									</td>
									<td style=" border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
										销量
									</td>
									<td style=" border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid; border-right: 1px #d2d2d2 solid;">
										库存
									</td>
								</tr>
							<s:iterator value="listCategoryProduct">
								<tr>
									<td style=" border-bottom: 1px #d2d2d2 solid;"><s:property value="shopProCategoryName"/></td>
									<td style=" border-bottom: 1px #d2d2d2 solid; line-height:22px; text-align: left;"><s:property value="productName"/></td>
									<td style=" border-bottom: 1px #d2d2d2 solid;"><img title="<s:property value="productName"/>" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot+logoImg}'/>" style="height: 100px;width: 100px" /></td>
									<td style=" border-bottom: 1px #d2d2d2 solid;"><s:if test="isPutSale==2">是</s:if><s:else>否</s:else></td>
									<td style=" border-bottom: 1px #d2d2d2 solid;"><s:property value="salesPrice"/></td>
									<td style=" border-bottom: 1px #d2d2d2 solid;"><s:property value="totalSales"/></td>
									<td style=" border-bottom: 1px #d2d2d2 solid;" class="lastR"><s:property value="storeNumber"/></td>
								</tr>
							</s:iterator>
							</table>
							
							<!--分页 start-->
							<div class="pageList strong ClearBoth">
								<jsp:include page="../../util/splitPage.jsp" />
							</div>
							<!--分页 end-->
							</form>
						</div>
					</div>
					
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
