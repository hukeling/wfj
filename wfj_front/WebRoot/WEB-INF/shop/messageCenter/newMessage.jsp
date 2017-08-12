<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新建商城信息(SendMessage)</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css">
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/validate_add.js"></script>
		<script src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
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
$("#newMessageForm").bind('submit',function(){
	var toMemberName = $("#toMemberName").val();
	if($("#messageTitle").val()==""||$("#content").val()==""||toMemberName==$("#toMemberName")[0].defaultValue){
		$(".errorMessage").html("<font color='red'>请输入收件人、主题和内容！</font>");
		return false;
	}
	return true;
});
$("#toMemberName").bind({ 
	focus:function(){ 
		if (this.value == this.defaultValue){ 
			$(this).css("color","black");
			this.value=""; 
		}
	}, 
	blur:function(){ 
		if (this.value == ""){ 
			$(this).css("color","gray");
			this.value = this.defaultValue; 
		}else{
			$.ajax({
				type: "POST",
				dataType: "JSON",
				url: "${pageContext.request.contextPath}/front/customer/messageCenter/checkCustomerName.action",
				data: {ids:$("#toMemberName").val()},
				success: function(data){
					if(data.isSuccess=="false"){
						$(".errorMessage").html("<font color='red'>收件人"+data.sb+"不存在</font>");
						$(".submitImg").attr("disabled","disabled");
					}else{
						$("#toMemberId").val(data.sb1);
						$(".errorMessage").html("");
						$(".submitImg").removeAttr("disabled");
					}
				}
			});
		} 
	} 
}); 
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
					<section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">新建商城信息(SendMessage)</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<section class="publicPadding10">
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
									新建商城信息(SendMessage)
								</p>
								<form id="newMessageForm" name="base-info-form" method="post" action="${pageContext.request.contextPath}/front/customer/messageCenter/saveOrUpdateMessageCenter.action">
									<input type="hidden" id="toMemberId" name="messageCenter.toMemberId"/>
									<fieldset class="publicNoBorder">
										<ul class="ConInformation margin-center publicMarginTop15">
											<li>
												<div>
													<i class="ColorRed">*</i>&nbsp;收件人
												</div>
												<div>
													<input id="toMemberName" name="messageCenter.toMemberName" type="text" value="可同时给多人发送商城信息(SendMessage)，用户名之间请用英文逗号隔开" style="width: 370px;color:gray;"/>
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i>&nbsp;主题
												</div>
												<div>
													<input id="messageTitle" name="messageCenter.messageTitle" type="text" value="" style="width: 370px;" class="{validate:{required:true}}" MaxLength="30"></input>
												</div>
											</li>
											<li>
												<div><i class="ColorRed">*</i>&nbsp;内容</div>
												<div>
													<textarea id="content" cols="60" rows="5" style="width:370px;" name="messageCenter.content" class="{validate:{required:true}}"></textarea>
												</div>
											</li>
											<li>
												<div><i class="ColorRed">*</i>&nbsp;是否立即发送</div>
												<div>
													<input id="messageSendState1" type="radio" name="messageCenter.messageSendState" value="1" checked="checked"/><label for="messageSendState1">立即发送</label>
												</div>
												<div>	
					      							<input id="messageSendState2" type="radio" name="messageCenter.messageSendState" value="2" /><label for="messageSendState2">保存至草稿箱</label>
												</div>
											</li>
											<li>
												<div></div>
												<div class="errorMessage">&nbsp;</div>
											</li>
											<li>
												<div></div>
												<div>
													<input type="submit" class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" value="提交" id="Submit_button" style="border:none;">
												</div>
											</li>
										</ul>
									</fieldset>
								</form>
								</section>
							</div>
					</div>
					</section>

				</div>
				<!--//right-->
			</div>
		</div>
</div>
		
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
