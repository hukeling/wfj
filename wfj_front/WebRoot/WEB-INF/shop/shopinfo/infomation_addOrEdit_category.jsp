<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="../include/head.jsp"%>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
<title>添加资讯分类</title>
<script>
	$(function() {//表单验证
		$("#form1")
				.validate(
						{
							meta : "validate",
							submitHandler : function(form) {
								var options = {
									url : "${pageContext.request.contextPath}/front/store/infoMationCategory/saveOrUpdateShopCategory.action?is_ajax=2",
									type : "post",
									dataType : "json",
									success : function(data) {
										window.close();
										window.opener.afterAddOrEdit();
										$(".submitImg").attr("disabled",false);
									}
								};
								//控制保存按钮变灰，避免重复提交
								$(".submitImg").attr("disabled","disabled");
								$(".submitImgLoad").attr("style","display:block;");
								$("#form1").ajaxSubmit(options);
							}
						})
	});
	//异步上传图片
	function uploadPhoto() {
		var fileValue=$("#tfile").val();
		if(fileValue!=null&&fileValue!=""){
			var htm="<img src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif'/>";
	      	htm+="&nbsp;&nbsp;正在提交，请稍等...";
	      	  //控制保存按钮变灰，避免重复提交
	      	$("#buttonId").attr("disabled","disabled");
	      	$("#sfile").html(htm);
			$(document).ready(function() {
				var options = {
					url : "${pageContext.request.contextPath}/front/store/infoMationCategory/uploadImageFront.action",
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.photoUrl == "false") {
							$("#sfile").html(" <font style='color:red'>请检查上传图片的格式，只能是： jpg,gif,jpeg,png,bmp!</font>");
						} else {
							$("#categoryImage").val(data.photoUrl);
							$("#showCategoryImage").html("");
							$("#showCategoryImage").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' style='width: 100px;height: 100px' />");
							$("#sfile").html("");
							$("#buttonId").attr("disabled",false);
						}
					}
				};
			$("#imageForm").ajaxSubmit(options);
			});
			$("#sfile").html("");
		}
	}
</script>
</head>

<body>
	<div class="margin-center PublicWidth1004">
		<!--mainClass-->

		<div class="radius publicPadding20 publicHidden ">
			<h3 class="strong">添加资讯分类</h3>
			<div style="padding:0px 0px;  height:5px; background:#ff7400; margin-top:15px; margin-bottom:30px;"></div>

			<form id="imageForm" method="post" enctype="multipart/form-data">
				<ul  style="padding:10px 0  0px;">
					<li >
						<div style="float:left; padding-left:115px; font-size:14px; font-weight:bold;">图片:</div>
						<div>
							<input id="tfile" type="file" name="imagePath"  style=" margin-left:23px;"/> <span id="sfile"></span>
							<input id="buttonId" type="button" value="上传"
								onclick="uploadPhoto();" style="width: 50px;height: 20px;"
								class="submitImg BackgroundRed ColorWhite1 FontSizeB publicNoBorder FontSize16" />
						</div>
					</li>
				</ul>
			</form>
				<form id="form1" method="post">
					<input type="hidden" name="informationCategory.categoryId" value="${informationCategory.categoryId}" />
					<input type="hidden" name="informationCategory.level" value="1" /> 
					<input type="hidden" name="informationCategory.parentId" value="1" /> 
				    <input type="hidden" name="informationCategory.shopInfoId" value="${shopInfo.shopInfoId}" />
					<fieldset class="publicNoBorder">
						<ul class="ConInformation">
							<li style="padding:5px 0;">
								<div>
									<i class="ColorRed">*</i>&nbsp;&nbsp;分类名称:
								</div>
								<div>
									<input name="informationCategory.shopProCategoryName" type="text"
										class="widthpx200 height25 {validate:{required:true,maxlength:[40]}}"
										value="${informationCategory.shopProCategoryName}" />
								</div>
							</li>
							<li style="padding:5px 0;">
								<div>
									<i class="ColorRed">*</i>&nbsp;&nbsp;图片:
								</div>
								<div>
									<input id="categoryImage" name="informationCategory.categoryImage"
										type="hidden"
										value="<s:property value='informationCategory.categoryImage'/>" />
									<span id="showCategoryImage"> <s:if
											test="informationCategory.categoryImage!=null">
											<img
												src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='informationCategory.categoryImage'/>"
												style="height: 100px; width: 100px;" />
										</s:if> </span>
								</div>
							</li>
							<li style="padding:5px 0;">
								<div>
									<i class="ColorRed">*</i>&nbsp;&nbsp;排序:
								</div>
								<div>
									<input name="informationCategory.sortCode" type="text"
										class="widthpx200 height25 {validate:{required:true,number:true,maxlength:[4]}}"
										value="${informationCategory.sortCode}" />
								</div>
							</li>
							<li style="padding:5px 0;">
								<div>
									<i class="ColorRed">*</i>&nbsp;&nbsp;是否显示:
								</div>
								<div>
									<input name="informationCategory.isShow" type="radio"
										<s:if test="informationCategory.isShow==0">checked="checked"</s:if>
										class="{validate:{required:true}}" value="0">
									<p>不显示</p>
									<input name="informationCategory.isShow" type="radio"
										<s:if test="informationCategory.isShow==1">checked="checked"</s:if>
										class="{validate:{required:true}}" value="1" checked="checked">显示
								</div>
							</li>
							<li style="padding:5px 0;">
								<div>分类描述:</div>
								<div>
									<textarea cols="45" rows="5" wrap="virtual"
										name="informationCategory.categoryDescription"
										class="{validate:{maxlength:[150]}}">${informationCategory.categoryDescription}</textarea>
								</div>
							</li>
							<li style="padding:5px 0;">
								<div></div>
								<div>
									<input type="submit"
										class="submitImg BackgroundRed ColorWhite1 FontSizeB publicNoBorder radius FontSize16" value="提交" style="border:0;width: 80px;">
									<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
								</div>
							</li>
						</ul>
					</fieldset>
				</form>
		</div>
		<!--//mainClass-->
	</div>
</body>
</html>
