<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>参团申请</title>
		<%@ include file="../include/head.jsp"%>
		<!-- 添加商品规格list -->
		<!-- kindedite -->
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}kindeditor/themes/default/default.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}kindeditor/plugins/code/prettify.css"/>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}kindeditor/kindeditor.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}kindeditor/lang/zh_CN.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}kindeditor/plugins/code/prettify.js"></script>
		<!-- kindedite end-->
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/dateinput.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css"/>
		<!-- 选择适用专业标内容样式 -->
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/choseTagsAlert.css"/>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript">
		//表单验证
		$(function() { 
			$("#inputForm").validate({meta: "validate", 
				submitHandler:function(form){
		        	$("#submitButton").attr("disabled",true);
					form.submit();
		        }
		    });
		});
		
		//上传图片
		$(function() {
		    $('#upload-tx-form').submit(function() {
		    	$("#controlButton").attr("disabled","disabled");
				$('#tx-error').html('正在提交中....');
		        var file = $('#tx-file').val();
		        var index = file.lastIndexOf('.');
		        var file_ext = file.substring(index, file.length).toUpperCase();
		        $('#tx-error').html('');
		        if (file_ext == '.PNG' || file_ext == '.JPG') {
		            //上传
		            var ropts = {
		                url: "uploadImage.action?is_ajax=2",
		                type: "post",
		                dataType: "json",
		                success: function(data) {
		                    if (data.photoUrl =="false") {
		                        //member-img
		                        $("#controlButton").removeAttr("disabled");
		                        $('#tx-error').html('图片格式不是.PNG或 .JPG');
		                    } else {
		                        $('#member-img').attr('src', data.visitFileUploadRoot+data.photoUrl);
		                        $('#member-img').attr('alt', data.visitFileUploadRoot+data.photoUrl);
		                        $('#custPhoto').val(data.photoUrl);
		                        $("#showPlace").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' style='width: 100px;height: 100px' />");
		                    }
		                }
		            };
		            $(this).ajaxSubmit(ropts);
		        } else {
		        	$("#controlButton").removeAttr("disabled");
		            $('#tx-error').html('上传的图片不是 .PNG 或者 .JPG格式');
		        }
		        return false;
		    });
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
				<div class="MarketpalceTree">
					<ul class="FavouriteTab">
						<li class="first-child">
							<a href="javascript:void(0);" class="FontSize14 radius gradient">团购基本信息</a>
						</li>
					</ul>
				<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02"><div>
				<form enctype="multipart/form-data" method="post" id="upload-tx-form">
			     <table width="961px;">
			          <tr>
			          	  <th style="margin-left: 5px;"><i class="ColorRed">*</i>&nbsp;团购主图图片：</th>
					      <td style="float: left;">
					     	<input type="file" name="txImage" id="tx-file"/>
					     	<button type="submit" id="controlButton" style="background-color: #e62d59 !important;border: 1px solid #d01743;color: white;font-size: 14px;font-weight: bold;text-align: center;">上传</button>
					      </td>
					  </tr> 
				 </table> 
				</form>
				<form id="inputForm" method="post" action="${pageContext.request.contextPath}/front/customer/tuan/saveOrUpdateGroupProduct.action">
					<input id="productId" type="hidden" name="tuanProduct.productId" value="<s:property value='productId'/>"/>
					<input id="custPhoto" name="tuanProduct.tuanImageUrl" type="hidden" />
					<table width="961px;">
						<tr>
							<th style="text-align: right">&nbsp;图片预览：</th>
						    <td><span id="showPlace"></span></td>
						</tr>
						<tr>
						    <th style="text-align: right"><i class="ColorRed">*</i>&nbsp;团购商品分类：</th>
							<td>
							   <select name="tuanProduct.tuanProductTypeId" class="{validate:{required:true}}">
					             <option value="">--请选择--</option>
								  <s:iterator value="tuanProductTypeList">
									  	<option value="<s:property value="tuanProductTypeId"/>"  >
									  		<s:property value="sortName"/>
									  	</option>
								  </s:iterator>
					           </select>
							</td>
						</tr>
						<tr>
							<th style="text-align: right"><i class="ColorRed">*</i>&nbsp;团购标题：</th>
							<td><input type="text" name="tuanProduct.title" class="{validate:{required:true,maxlength:[50]}}"  style="border:1px #cecece solid;"/></td>
						</tr>
						<tr>
							<th style="text-align: right"><i class="ColorRed">*</i>&nbsp;团购价格：</th>
							<td><input style="border:1px #cecece solid;" type="text" name="tuanProduct.price" class="{validate:{required:true,number:true,max:99999999,min:0.1}}"/></td>
						</tr>
						<tr>
							<th style="text-align: right"><i class="ColorRed">*</i>&nbsp;团购周期：</th>
							<td><input style="border:1px #cecece solid;" type="text" name="tuanProduct.tuanPeriod" class="{validate:{required:true,number:true,max:999,min:1}}" /></td>
						</tr>
						<tr>
							<th style="text-align: right"><i class="ColorRed">*</i>&nbsp;开团人数：</th>
							<td><input style="border:1px #cecece solid;" type="text" name="tuanProduct.openGroupCount" class="{validate:{required:true,number:true,max:99999,min:1}}"/></td>
						</tr>
						<tr>
							<th style="text-align: right"><i class="ColorRed">*</i>&nbsp;团购简介：</th>
							<td><textarea style="resize:none;" rows="5" cols="50" class="{validate:{required:true,maxlength:[500]}}" name="tuanProduct.introduction"></textarea></td>
						</tr>
					</table>
					<div style="padding:20px 0;">
						<table class="margin-center">
							<tr>
								<td >
									<input id="submitButton" class="submitImg BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px; border:0; text-align: center;  cursor: pointer; line-height:16px;"  type="submit" value="提交"></input>
									<input id="backButton" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" type="button" value="返回" onclick="window.history.back(-1); " style="border:0; text-align: center;  cursor: pointer; line-height:16px;"></input>
								<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
								</td>
							</tr>
						</table>
					</div>	
					</form>
				</div>
				</div>
			</div>
			</section>
		</div>
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
