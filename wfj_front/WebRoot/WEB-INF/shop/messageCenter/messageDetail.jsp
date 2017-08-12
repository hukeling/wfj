<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商城信息(SendMessage)详情</title>
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

});
</script>		
	</head>
	<body>
		<%@ include file="../include/header.jsp"%>
		
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<%@ include file="../include/left_account.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">商城信息(SendMessage)详情</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<h3 class="publicblock FontSize16 FontSizeB publicPadding10">
									
								</h3>
								<article
									class="width95 margin-center publicPaddingTB7 publicMarginTop15">
								<!-- 
								<h5 class="FontSize15">
									Please complete your infomation
								</h5>
								<div id="progressbar" class="publicMarginTop15 publicrelative">
								</div>
								<figure
									class="progressbar_Tooltips publicrelative publicPaddingT20">
								<i
									class=" publicinline icon iconDark-nip float-none publicabsolute"></i>
								<p
									class=" radius publicNoBorder BackgroundF8Hui width90 publicPaddingTB10 text-center">
									Your information will be
									<span class="ColorOrange FontSizeB">15%</span> complete
								</p>
								
								 -->
								</figure>
								</article>
								<p
									class="FontSize18 publicdashedline width95 margin-center ColorRed publicPaddingTB10 ">
								</p>
								<form id="newMessageForm" name="base-info-form" method="post" action="${pageContext.request.contextPath}/front/customer/messageCenter/saveOrUpdateMessageCenter.action">
									<input type="hidden" id="toMemberId" name="messageCenter.toMemberId"/>
									<fieldset class="publicNoBorder">
										<ul class="ConInformation margin-center publicMarginTop15">
											<li>
												<div>
													 发件人
												</div>
												<div style="word-break:break-all;width: 470px;">
													${messageCenter.fromMemberName}
												</div>
											</li>
											<li>
												<div>
													 时间
												</div>
												<div>
													<s:date name="messageCenter.createDate" format="yyyy-MM-dd HH:mm:ss"/>
												</div>
											</li>
											<li>
												<div>
													 主题
												</div>
												<div style="word-break:break-all;width: 470px;">
													${messageCenter.messageTitle}
												</div>
											</li>
											<li>
												<div>内容</div>
												<div style="word-break:break-all;width: 470px;">
													${messageCenter.content}
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
