<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"><html xmlns="http://www.w3.org/1999/xhtml">
	<!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加评价</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css"/>
		<SCRIPT type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/js/json2.js"></SCRIPT>
		<script type="text/javascript">
			$(function(){
				
				$('#submit-comment').click(function(){
					
					var all_json = [];
					var valid = "";
					$('.AddCommentUl li').each(function(){
						var p_level = $(this).find('.comment-sel').val();
						var p_content = $(this).find('.comment-text').val();
						var p_isa = $(this).find('input:radio:checked').val();
						var p_productId = $(this).find('[name="productId"]').val();
						var p_orderDetailId = $(this).find('[name="orderDetailId"]').val();
						var p_ordersId = $(this).find('[name="ordersId"]').val();
						var p_ordersNo = $(this).find('[name="ordersNo"]').val();
						//判断用户是否对该商品进行评价
						if($.trim(p_content)=='') {
							return;
						}
						valid+="1";
						var p_json = {level:p_level,content:p_content,isa:p_isa,productId:p_productId,orderDetailId:p_orderDetailId,ordersId:p_ordersId,ordersNo:p_ordersNo};
						all_json.push(p_json);
					});
					
					if(valid==""){
						lalert('提醒','请填写评价信息！');
					}else{
						var cj =JSON.stringify(all_json);
						$.post('addComment.action',{commentJson:cj,"order.ordersId":'${order.ordersId}' },function(data){
							if(data.success){
								//lalert('Add Comment','Add Comment Success!');
								location.href="${pageContext.request.contextPath}/front/customer/commentList.action?type=2";
							}else{
								if(data.err){
									lalert('提醒',data.err);
								}else{
									lalert('提醒','添加评价失败！');
								}
							}
						},'json');
					}
					
				});
			});
			
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
					<section>
					<h3
						class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">
						添加评价
					</h3>
					<ul class="AddCommentUl publicMarginBot15">
					<s:iterator value="orderDetails" var="o">
						<li class="ClearBoth publicdashedline publicMarginTop15">
							<div class="addCommentImgBox float-left publicHidden">
							  		<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot"/><s:property value="productImg[#o.orderDetailId].logoImg"/>" />
								<p class="publicMarginT10">
									<a target="_blank"  href="${pageContext.request.contextPath}/productInfo/${o.productId}.html" class="FontSize14">${o.productFullName}</a>
									<br>
								</p>
								<p class="ColorRed">
									<span class="FontSize18">￥ ${o.salesPrice}</span>
								</p>
							</div>
							<div
								class="addCommentTextBox float-left publicHidden publicMarginL10">
								<div>
									<select class="publicMarginBot15 comment-sel">
										<option value="1" selected="">
											好评
										</option>
										<option value="0">
											中评
										</option>
										<option value="-1">
											差评
										</option>
									</select>
								</div>
								<div>
									<textarea cols="63" rows="5" class="width98 comment-text" style="width: 400px;"></textarea>
								</div>
								<div class="publicBorder publicPadding5_10 gradient theRadio" style="width:390px;">
									<input name="comment-rdo-${o.orderDetailId}" id="comment-rdo-public-${o.orderDetailId}" type="radio" value="0" >
									<label for="comment-rdo-public-${o.orderDetailId}">
										公开评价
									</label>
									&nbsp;&nbsp;
									<input name="comment-rdo-${o.orderDetailId}" id="comment-rdo-anonymous-${o.orderDetailId}" type="radio" value="1" checked>
									<label for="comment-rdo-anonymous-${o.orderDetailId}">
										匿名评价
									</label>
								</div>
								<br>
							</div>
							<input type="hidden" value="${o.orderDetailId}" name="orderDetailId"/>
							<input type="hidden" value="${o.productId}" name="productId"/>
							<input type="hidden" value="${order.ordersId}" name="ordersId"/>
							<input type="hidden" value="${order.ordersNo}" name="ordersNo"/>
						</li>
					</s:iterator>
					</ul>
					<input id="submit-comment" type="button"
						class="publicPadding5_10 BackgroundPurple publicBorder FontSize14 FontSizeB ColorWhite1 float-right"
						value="提交">
					</section>
				</div>
				<!--//right-->
				
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
