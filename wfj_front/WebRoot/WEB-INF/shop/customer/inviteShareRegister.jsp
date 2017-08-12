<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>分享注册</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css">
		<style type="text/css">
			.tip-tx{
				text-align:center;line-height:25px;
				z-index:999;
				background-color: #333;
				filter:alpha(opacity=50);
				-moz-opacity:0.5;
				-khtml-opacity: 0.5;
				opacity: 0.5;
				display: none;
				margin-top: -26px;
				width:122px;
				position: absolute;
			}
			.tip-tx a {
				filter:alpha(opacity=100);
				-moz-opacity:1;
				-khtml-opacity: 1;
				opacity: 1;
				color:#ccc;
			}
		</style>
<script type="text/javascript">
$(function(){
	$(".makeUrl").bind('click',function(){
		var id=$(this).attr("flag");
		$("#"+id).val("");
		$.ajax({
			type: "POST",
			dataType: "JSON",
			url: "${pageContext.request.contextPath}/front/customer/Setting/makeUrl.action?p="+id,
			success: function(data){
			   $("#"+id).val(data.sru);
			}
		});
	});
	$(".fz-button").bind('click',function(){
		var id=$(this).attr("flag");
		var url = $("#"+id).val();
		window.clipboardData.setData("Text",url);
		lalert("信息","已复制到剪贴板！");
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
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">分享注册</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<form id="newMessageForm" name="base-info-form" method="post" action="${pageContext.request.contextPath}/front/customer/messageCenter/saveOrUpdateMessageCenter.action">
									<input type="hidden" id="toMemberId" name="messageCenter.toMemberId"/>
									<fieldset class="publicNoBorder">
										<ul class="ConInformation margin-center publicMarginTop15">
											<li>
												<div style="width:110px;">
													 PC端分享
												</div>
												<div style="word-break:break-all;width: 470px;">
													<input id="pcUrl" type="text" value="" style="width: 470px;"/>
												</div>
												<div style="width: 200px;margin-left:20px;">
													<input id="pcUrlBut" flag="pcUrl" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14   makeUrl" type="button" value="生成连接"  style="border:0;"/><input style="border:0;" id="fuzhiPC" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14 fz-button" flag="pcUrl" type="button" value="复制"/>
												</div>
											</li>
											<li>
												<div style="width:110px;">
													手机端分享
												</div>
												<div style="word-break:break-all;width: 470px;">
													<input id="phoneUrl"  type="text" value="" style="width: 470px;"/>
												</div>
												<div style="width: 200px;margin-left:20px;">
													<input id="phoneUrlBut" flag="phoneUrl" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14  makeUrl" type="button" value="生成连接"  style="border:0;"/><input style="border:0;" id="fuzhiPhone" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14 fz-button" flag="phoneUrl" type="button" value="复制"/>
												</div>
											</li>
											<li>
												<div></div>
												<div>
													非IE内核浏览器用户请手动复制
												</div>
											</li>
											
											
											<li>
												<div></div>
												<div>
													<a href="javascript:history.back(-1);" id="delete-drafts" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14">返回</a>
												</div>
											</li>
										</ul>
									</fieldset>
								</form>
							</div>
							
					</div>
				</div>
			<!--//right-->
			</div>
		</div>
</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
