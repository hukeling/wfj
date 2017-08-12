<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>基础信息</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css"/>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<style type="text/css">
			.tip-tx{height:25px;width:122px;text-align:center;background-color: #666;}
			.tip-tx a {color:#fff;}
		</style>
		
<!-- tab效果js -->
		<script type="text/javascript">
			$(function() {
			    $('#sex').val("${customerInfo.sex}");
			    //上传头像
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
			                url: "editImage.action?is_ajax=2",
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
			                        $('#upload-tx-overlay').overlay().close();
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
			
		$(function() {//表单验证
		    $("#base-info-form").validate({meta: "validate",
		                 submitHandler:function(form){//当表单通过验证时回调函数
		                	 $.post($(form).attr("action"),$(form).serialize(),function(data){
		                		 if(data.success==true){
		                			 lconfirm_yes('消息提醒', '修改成功',submitOk);
		                		 }
		                	 },'json');
		                 }
		    });
		});  
		//修改基本信息成功后执行方法	    
		function submitOk(){
  	 		window.location.href='${pageContext.request.contextPath}/front/customer/Setting/baseInfo.action';
	    }
		
	      //邮箱唯一性校验
	      jQuery.validator.addMethod("checkEmail",function(value, element) {
		    	var flag=true;
		    	var emailHidd=$("#emailHidd").val();
		    	if(emailHidd!=$("#cemail").val()){
	 				$.ajax({
	 				   type: "POST",dataType: "JSON",
	 				   url:"${pageContext.request.contextPath}/shop/check/checkEmail.action",
	 				   data: {email:value},
	 				   async : false,
	 				   success: function(data){
			 				   if(data.isSuccess!="true"){
			 					   flag=false;
			 				   }
	 				   }
	 				});
		    	}
	 			return flag;
			},"该邮箱已被使用！" );
		//手机唯一性校验
		 jQuery.validator.addMethod("checkMobile",function(value, element) {
		    	var flag=true;
		    	var phoneHidd=$("#phoneHidd").val();
		    	if(phoneHidd!=$("#cphone").val()){
	 				$.ajax({
	 				   type: "POST",dataType: "JSON",
	 				   url:"${pageContext.request.contextPath}/shop/check/checkPhone.action",
	 				   data: {phone:value},
	 				   async : false,
	 				   success: function(data){
			 				   if(data.isSuccess!="true"){
			 					   flag=false;
			 				   }
	 				   }
	 				});
		    	}
	 			return flag;
			},"该号码已被使用！" );
		
		function uploadPhoto(){
			$("#controlButton").removeAttr("disabled");
	        $('#upload-tx-overlay').overlay().load();
		}
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
				<s:elseif test="#session.customer.type==2">
					<%@ include file="../include/left_shop.jsp"%>
				</s:elseif>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				 <section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">基础信息</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<section class="publicPadding10">
								<h3 class="publicblock FontSize16 FontSizeB publicPadding10">
									欢迎您,${customer.loginName}!
								</h3>
								<form id="base-info-form" name="base-info-form" method="post" action="modifyBaseInfo.action">
									<fieldset class="publicNoBorder">
										<ul class="ConInformation margin-center publicMarginTop15">
											<li>
												<div>
													<i class="ColorRed">*</i> 用户登录名称
												</div>
												<s:if test="#session.customer.loginName!=null&&#session.customer.loginName!=''">
													<div>
														${customer.loginName}
													</div>
												</s:if>
												<s:if test="#session.customer.loginName==null||#session.customer.loginName==''">
													<div>
														<input id="loginName" name="customer.loginName" type="text" /><span id="userMsg"></span>
													</div>
												</s:if>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 邮箱
												</div>
												<div>
													<input id="cemail" name="customer.email" class="{validate:{required:true,emailNew:true,checkEmail:true}}" type="text" value="${customer.email}"/><span id="emailMsg"></span>
													<input id="emailHidd" type="hidden" value="${customer.email}"/>
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 用户头像
												</div>
												<div>
													<img style="width:120px;" id="member-img" class="accountFigure" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='customer.photoUrl'/>"/>
													<div class="tip-tx"><a href="javascript:uploadPhoto();">上传</a></div>
													<input id="custPhoto" name="customer.photoUrl" type="hidden"  value="${customer.photoUrl}" />
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 手机号码
												</div>
												<div>
													<input id="cphone" name="customer.phone" class="{validate:{required:true,mobile:true,checkMobile:true}}" type="text" value="${customer.phone}" /><span id="phoneMsg"></span>
													<input id="phoneHidd" type="hidden" value="${customer.phone}"/>
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 性别
												</div>
												<div>
													<select name="customerInfo.sex">
														<s:iterator value="#application.keybook['sex']" status="s" var="kb">
															<option id="sex_<s:property value='#s.index'/>" value="<s:property value='#kb.value'/>" <s:if test="customerInfo.sex==#kb.value">selected='selected'</s:if>><s:property value='#kb.name'/></option>
														</s:iterator>
													</select>
													<script>
														$(function(){
															//若用户无性别记录 则默认选中保密
															var sex='${customerInfo.sex}';
															if(sex==""){
																$('select[name="customerInfo.sex"]').val(3);
															}
														});
													</script>
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 出生日期
												</div>
												<div>
													<input type="text" name="birthday" class="{validate:{required:true}}" value="<s:date name="customerInfo.birthday" format="yyyy/MM/dd"/>" id="birthday"  onclick="WdatePicker({dateFmt:'yyyy/MM/dd'})"/>
												</div>
											</li>
											<li>
												<div>简介</div>
												<div>
													<textarea cols="60" rows="5" wrap="virtual" name="customerInfo.notes" >${customerInfo.notes}</textarea>
												</div>
											</li>
											<li>
												<div></div>
												<div>
													<input type="submit" class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" value="保存" id="Submit_button" style="border: 0;">
												</div>
											</li>
										</ul>
									</fieldset>
								</form>
								</section>
							</div>
							</div>
					    </div>
					</div>
				 </section>

				</div>
				<!--//right-->
			</div>
		</div>
		<div class="modal" id="upload-tx-overlay">
			<form enctype="multipart/form-data" method="post" id="upload-tx-form">
				<h2 class="modal-title" style="height:25px; line-height:25px; width:447px; border-bottom: 1px #d2d2d2 solid;">
					用户头像
				</h2>
<!-- 				<p class="modal-content" style="height: 24px;"> -->
<!-- 					
<!-- 				</p> -->
<!--                <p id="tx-error" class="ColorRed"></p> -->
			        	图像文件:
 					<input type="file" name="txImage" id="tx-file">
 					<p>注意:为了使您的头像尽可能的清楚的显示我们建议您使用150X150像素的图片.</p>

				<p class="modal-buttons">
					<button type="submit" id="controlButton" style="border: 0;">提交</button>
					<button class="close" type="button" style="border: 0; margin-left: 5px;">
						关闭
					</button>
				</p>
			</form>
		</div>
		
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
