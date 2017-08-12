<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>店铺客服设置</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css" />
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
		
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
				
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-ui-1.10.3.custom.min.js"></script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		
		<style type="text/css">
			.tip-tx{
				height:25px;
				width:122px;
				text-align:center;
				background-color: #666;
			}
			.tip-tx a {
				color:#fff;
			}
		</style>
<script type="text/javascript">
$(function() {//表单验证
	$("#base-info-form").validate({meta:"validate",submitHandler:function(form){
		form.submit();
	}});
	//使用状态
	var useState="${customerService.useState}";
	if(useState!=""){
		$("#useState_"+useState).attr("checked",true);
	}else{
		$("#useState_0").attr("checked",true);
	}
});
function saveQQ(){
	var isExceedQQ = $("#isExceedQQ").val();
	if(isExceedQQ!=null&&isExceedQQ=="false"){
		$("#base-info-form").submit();
	}else{
		var useStates = document.getElementsByName("customerService.useState");
		for(var i=0;useStates.length;i++){
			var c = useStates[i].checked;
			if(c){
				var values = useStates[i].value;
				 if(values==1){
					var qqUseCountMax = $("#qqUseCountMax").val();
					alert("对不起，最多可设置"+qqUseCountMax+"个QQ为正常使用状态！");
				}else{
					$("#base-info-form").submit();
				}
				break;
			}
		}
	}
}
$(function(){
	//tools参数设置
	$('.modal').overlay({
		mask: {
			color: '#ebecff',
			loadSpeed: 200,
			opacity: 0.9//模糊度0.1-0.9
		}
	});
	//店铺首页大图上传
	$('#edit-image-btn2').click(function(){
		$("#controlButton2").removeAttr("disabled");
		$('#upload-tx-overlay2').overlay().load();
	});
	//客服照片上传
	//上传头像——店铺首页大图
	$('#upload-tx-form2').submit(function(){
		$("#controlButton2").attr("disabled","disabled");
		$('#tx-error2').html('正在提交中....');
		var file = $('#tx-file2').val();
		var index = file.lastIndexOf('.');
		var file_ext = file.substring(index,file.length).toUpperCase();
		$('#tx-error2').html('');
		if(file_ext == '.PNG' || file_ext=='.JPG'){
			//上传
			var ropts ={
    				url : "uploadImageFront.action?is_ajax=2",
		            type : "post",  
		            dataType:"json",
		            success : function(data) {
		            	if(data.photoUrl=="false2"){
		            		//member-img
		            		$("#controlButton2").attr("disabled","disabled");
		            		$('#tx-error2').html('文件扩展名不是PNG或JPG!');
		            	}else{
		            		var type=$('#templateSet').val();
		            		if(type==2){
		            			$('#bigpp').attr('style',"width: 401px;height: 25px;");
		            			$('#member-img2').attr('style',"width:200px;height: 120px;");
		            		}else{
		            			$('#bigpp').attr('style',"width: 122px;height: 25px;");
		            			$('#member-img2').attr('style',"width: 120px;height: 120px;");
		            		}
		            		$('#member-img2').attr('src',data.visitFileUploadRoot+data.photoUrl);
		            		$('#member-img2').attr('alt',data.visitFileUploadRoot+data.photoUrl);
		            		$('#upload-tx-overlay2').overlay().close();
		            		$('#bannerUrl').val(data.photoUrl);
		            	}
		            }
			};
			$(this).ajaxSubmit(ropts);
		}else{
			$("#controlButton2").attr("disabled","disabled");
			$('#tx-error').html('文件扩展名不是PNG或JPG!');
		}
		return false;
	});
//提示框
var lalert = function(title,content){
	$('#alert-overlay .modal-title').html(title);
	$('#alert-overlay .modal-content').html(content);
	$("#alert-overlay").overlay().load();
};
});
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
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">客服管理</h3>
				<div class="MarketpalceTree">
					<div class="TabParent_lj publicBorder ClearBoth">
					<section class="publicPadding10">
						<p class="FontSize15"  style="height:20px; line-height:20px;">添加QQ客服信息:<font color="red">最多可设置<s:property value="qqUseCountMax" />个QQ为正常使用状态,已设置<s:property value="qqUseCount"/>个。</font></p>
						<div class="publicMarginTop15 publicrelative" style="border-bottom: 1px #ccc dotted;"></div>
						<form  id="base-info-form" name="base-info-form" method="post" action="${pageContext.request.contextPath}/front/store/customerService/saveOrUpdateCustomerService.action">
						<input type="hidden" id="customerService.customerServiceId" name="customerService.customerServiceId" value="${customerService.customerServiceId}"/>
						<input type="hidden" id="isExceedQQ" name="isExceedQQ" value="${isExceedQQ }"/>
						<input type="hidden" id="qqUseCount" name="qqUseCount" value="${qqUseCount }"/>
						<input type="hidden" id="qqUseCountMax" name="qqUseCountMax" value="${qqUseCountMax }"/>
						<div class="publicNoBorder">
							<ul class="ConInformation margin-center publicMarginTop15">
<%-- 								<li>
								<div>
									客服照片:
								</div>
								<div style="height:130px;">
									<img id="member-img" style="width: 120px;height: 120px;" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${customerService.photoUrl}" class="accountFigure"/>
									<div class="tip-tx"><a href="javascript:;" id="edit-image-btn2" class="editImageBtn">图片上传</a></div>
									<input id="photoUrl" type="hidden" name="customerService.photoUrl" value="${customerService.photoUrl}"/>
								</div>
								</li> --%>
								<li>
									<div style="padding-right:0;"><i class="ColorRed">*</i>&nbsp;真实姓名:</div>
									<div>
										<input id="trueName" type="text" name="customerService.trueName" value="${customerService.trueName}" class="{validate:{required:true,maxlength:[10]}}"/>
									</div>
								</li>
								<li>
									<div style="padding-right:0;"><i class="ColorRed">*</i>&nbsp;昵称:</div>
									<div>
										<input id="nikeName" type="text" name="customerService.nikeName" value="${customerService.nikeName}" class="{validate:{required:true,maxlength:[6]}}"/>
									</div>
								</li>
								<li>
									<div style="padding-right:0;"><i class="ColorRed">*</i>&nbsp;QQ号码:</div>
									<div>
										<input id="qq" type="text" name="customerService.qq" value="${customerService.qq}" class="{validate:{required:true,digits:true,maxlength:[15]}}"/>
									</div>
								</li>
								<li>
									<div style="padding-right:0;"><i class="ColorRed">*</i>&nbsp;手机号:</div>
									<div>
										<input id="mobile" type="text" name="customerService.mobile" value="${customerService.mobile}" class="{validate:{required:true,mobile:true}}"/>
									</div>
								</li>
								<li>
									<div style="padding-right:0;">电话号:</div>
									<div>
										<input id="phone" type="text" name="customerService.phone" value="${customerService.phone}" class="{validate:{phone:true}}"/>
									</div>
								</li>
								<li>
									<div style="padding-right:0;">使用状态 :</div>
									<div style="width:150px; ">
										<div style="width:90px; float: left;">
											<input id="useState_1" type="radio" name="customerService.useState" value="1" />正常使用
										</div>
										<div style="width:60px;float: right; ">
											<input id="useState_0" type="radio" name="customerService.useState" value="0" />作废
										</div>
									</div>
								</li>
								<li>
									<div>&nbsp;</div>
									<div style="width:140px; ">
										<input id="Submit_button" class="submitImg submitButtonL publicMarginR10 radius" type="button" style="border: 0; padding:5px 10px;" onclick="saveQQ()" value="提交"/>
										<p class="submitImgLoad strong" style="display:none; width:200px;height:20px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
									</div>
								</li>
							</ul>
						</div>
						</form>
					</section>
					</div>
				</div>
				</section>
			</div>
			<!--//right-->
			<!-- 异步上传图片 -->
			<div class="modal" id="upload-tx-overlay2">
			<form enctype="multipart/form-data" method="post" id="upload-tx-form2">
				<h2 class="modal-title">
					店铺首页大图
				</h2>
				<p class="modal-content">
					图片文件:
					<input type="file" name="imagePath" id="tx-file2" />
					<p>注意：为达到最佳效果，使用一个不超过1M大小的图像。</p>
				</p>
				<p class="modal-buttons">
					<button type="submit" id="controlButton2" style="margin-left:90px; border: 0;">提交</button>
					<button class="close" type="button" style="margin-left: 5px;">
						关闭
					</button>
				</p>
			</form>
		</div>
		<!-- 		模版一 -->
		<div class="modal" id="templateSet-Type">
			<h2 id="toolsTitle" class="modal-title">
			</h2>
			<p style="width: 674px;height: 379px;display: block;" class="modal-content">
				<img id="toolsImg"  style="width: 674px;height: 379px;" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src=""/>
			</p>
			<p class="modal-buttons">
				<button class="close" type="button" style="margin-left:310px;">
					关闭
				</button>
			</p>
		</div>
	</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
