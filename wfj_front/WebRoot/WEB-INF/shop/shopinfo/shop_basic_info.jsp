<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>店铺设置</title>
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
		 	    $("#base-info-form").validate({meta: "validate", 
		    	      submitHandler:function(form){
		    	    	//获取session中的企业信息对象
		    	    	var shopInfoIsPass='${shopInfo.isPass}';
		    	    	var shopInfoCheckSatus='${shopInfo.shopInfoCheckSatus}';
		    	    	if(shopInfoCheckSatus!=""){
		    	    		if(shopInfoCheckSatus==1){//待审核
		    	    			lalert("提醒","企业信息尚未审核无法操作!");
		    	    		}else if(shopInfoCheckSatus==3){
		    	    			lalert("提醒","企业信息未通过审核无法操作!");
		    	    		}else{
		    	    			if(shopInfoIsPass==1){
			    	    			lalert("提醒","待审核状态无法操作!");
			    	    		}else{
			    	    			var logoUrl = $("#logoUrl").val();
			    	    			var bannerUrl = $("#bannerUrl").val();
					    	    	if(logoUrl==""||bannerUrl==""){
					    	    		lalert("提醒","请上传店铺图片!");
					    	    	}else{
					    	    		$(".submitImg").attr("disabled","disabled");
						    	    	$(".submitImgLoad").attr("style","display:block;");
					 	    			form.submit();
					    	    	}
			    	    		}
		    	    		}
		    	    	}else{
		    	    		lalert("提醒","请先完善企业信息后在操作!");
		    	    	}
		    	      }
		 	    });
	 	    });
			$(function(){
				//tools参数设置
				$('.modal').overlay({
					mask: {
				        color: '#ebecff',
				        loadSpeed: 200,
				        opacity: 0.9//模糊度0.1-0.9
			        }
				});
				
				
				//店铺logo图像上传
				$('#edit-image-btn').click(function(){
					$("#controlButton1").removeAttr("disabled");
					$('#upload-tx-overlay').overlay().load();
				});
				//店铺首页大图上传
				$('#edit-image-btn2').click(function(){
					$("#controlButton2").removeAttr("disabled");
					$('#upload-tx-overlay2').overlay().load();
				});
				
				//上传头像——店铺logo
				$('#upload-tx-form').submit(function(){
					$("#controlButton1").attr("disabled","disabled");
					$('#tx-error').html('正在提交中....');
					var file = $('#tx-file').val();
					var index = file.lastIndexOf('.');
					var file_ext = file.substring(index,file.length).toUpperCase();
					$('#tx-error').html('');
					if(file_ext == '.PNG' || file_ext=='.JPG'){
						//上传
						var ropts ={
	            				url : "uploadImageFront.action?is_ajax=2",
					            type : "post",  
					            dataType:"json",
					            success : function(data) {
					            	if(data.photoUrl=="false2"){
					            		//member-img
					            		$("#controlButton1").attr("disabled","disabled");
					            		$('#tx-error').html('文件扩展名不是PNG或JPG!');
					            	}else{
					            		$('#member-img').attr('src',data.visitFileUploadRoot+data.photoUrl);
					            		$('#member-img').attr('alt',data.visitFileUploadRoot+data.photoUrl);
					            		$('#upload-tx-overlay').overlay().close();
					            		$('#logoUrl').val(data.photoUrl);
					            	}
					            }
        				};
						$(this).ajaxSubmit(ropts);
					}else{
						$("#controlButton1").attr("disabled","disabled");
						$('#tx-error').html('文件扩展名不是PNG或JPG!');
					}
					return false;
				});
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
			});
			//提示框
			var lalert = function(title,content){
				$('#alert-overlay .modal-title').html(title);
				$('#alert-overlay .modal-content').html(content);
				$("#alert-overlay").overlay().load();
			};
			//初始化模版
			$(function(){
				var ts='${shopInfo.templateSet}';//店铺模版
				$("#templateSet").val(ts);
				//提示上传图片最佳大小
				if(ts!=""){
						$("#photoMgs").html("<input type='button' onclick='showTemplateSetImg("+ts+")' value='预览'/>");
					if(ts==1){
						$("#templateSetMgs").html("图片大小(1366*350)");
						$('#bigpp').attr('style',"width:122px;height: 25px;");
            			$('#member-img2').attr('style',"width:120px;height: 120px;");
					}else if(ts==2){
						$("#templateSetMgs").html("图片大小(350*350)");
						$('#bigpp').attr('style',"width: 122px;height: 25px;");
            			$('#member-img2').attr('style',"width: 120px;height: 120px;");
					}else if(ts==3){
						$("#templateSetMgs").html("图片大小(1366*350)");
						$('#bigpp').attr('style',"width: 122px;height: 25px;");
            			$('#member-img2').attr('style',"width: 120px;height: 120px;");
					}else if(ts==4){
						$("#templateSetMgs").html("图片大小(1366*350)");
						$('#bigpp').attr('style',"width: 122px;height: 25px;");
            			$('#member-img2').attr('style',"width: 120px;height: 120px;");
					}else if(ts==5){
						$("#templateSetMgs").html("图片大小(1366*350)");
						$('#bigpp').attr('style',"width: 122px;height: 25px;");
            			$('#member-img2').attr('style',"width: 120px;height: 120px;");
					}
				}
			});
			//更改店铺模版 动态提示图片大小  value为模版的值
			function showTemplateSetMgs(value){
				var v=value;
				$("#photoMgs").html("");
				$("#templateSetMgs").html("");
				if(v!=""){
					$("#photoMgs").html("<input type='button' onclick='showTemplateSetImg("+v+")' value='预览'/>");
					if(v==1){
						$("#templateSetMgs").html("图片大小(1366*350)");
					}else if(v==2){
						$("#templateSetMgs").html("图片大小(350*350)");
					}else if(v==3){
						$("#templateSetMgs").html("图片大小(1366*350)");
					}else if(v==4){
						$("#templateSetMgs").html("图片大小(1366*350)");
					}else if(v==5){
						$("#templateSetMgs").html("图片大小(1366*350)");
					}
				}
			}
			
			//预览店铺模版图片
			function showTemplateSetImg(templateSetType){
				/*templateSetType:模版类型(1,2,3..)*/
				$("#toolsTitle").html("店铺模版展示：模版（"+templateSetType+"）");
				//更改tools中的图片src路径
				$("#toolsImg").attr("src","${fileUrlConfig.fileServiceUploadRoot}shop/front/images/shopinfo/mb"+templateSetType+".PNG");
				//展示tools内容
				$("#templateSet-Type").overlay().load();
			}
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
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">店铺设置</h3>
					<div class="MarketpalceTree">
						
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<section class="publicPadding10">
								<p class="FontSize15"  style="height:20px; line-height:20px;">
									店铺状态：
									<s:if test="#session.shopInfo.isPass==1">
										待审核
									</s:if>
									<s:elseif test="#session.shopInfo.isPass==2">
										审核未通过
									</s:elseif>
									<s:elseif test="#session.shopInfo.isPass==3">
										审核通过
									</s:elseif>
								  </p>
					  
								  <div class="publicMarginTop15 publicrelative" style="border-bottom: 1px #ccc dotted;">
								  </div>
								
								<form id="base-info-form" name="base-info-form" method="post" action="${pageContext.request.contextPath}/front/store/frontshopinfo/saveOrUpdateSellerInfo.action">
								<input type="hidden" name="shopInfo.phoneShowStatus" value="0"/>	
									<div class="publicNoBorder">
										<ul class="ConInformation margin-center publicMarginTop15">
											<li>
												<div>
													<i class="ColorRed">*</i> 店铺Logo:
												</div>
												<div style="height:130px;">
													<img id="member-img" style="width: 120px;height: 120px;" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${shopInfo.logoUrl}" class="accountFigure"/>
													<div class="tip-tx"><a href="javascript:;" id="edit-image-btn" class="editImageBtn">图片上传</a></div>
													<input type="hidden" id="logoUrl" name="shopInfo.logoUrl"  value="${shopInfo.logoUrl}"/>
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 店铺首页大图:
												</div>
												<div style="height:130px;">
													<img id="member-img2" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${shopInfo.bannerUrl}" class="accountFigure"/>
													<div id="bigpp" class="tip-tx"><a href="javascript:;" id="edit-image-btn2" class="editImageBtn">图片上传</a></div>
													<input type="hidden" id="bannerUrl" name="shopInfo.bannerUrl"  value="${shopInfo.bannerUrl}"/>
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 店铺名称:
												</div>
												<div>
													<input value="<s:property value="shopInfo.shopName"/>" type="text" id="shopName" name="shopInfo.shopName" class="{validate:{required:true,maxlength:[100]}} Registration" />
												</div>
											</li>
											<li>
												<div>
													<i class="ColorRed">*</i> 店铺分类:
												</div>
												<div>
													<select id="shopCategoryId" name="shopInfo.shopCategoryId"  class="{validate:{required:true}}" >
														<option value=""> ---请选择---</option>
														<s:iterator value="listShopCate">
													  	<option value="<s:property value="shopCategoryId"/>" <s:if test="shopInfo.shopCategoryId==shopCategoryId">selected="selected"</s:if> ><s:property value="shopCategoryName"/></option>
													    </s:iterator>
													</select>
												</div>
											</li>
<!-- 											店铺模版 -->
											<li>
												<div>
													<i class="ColorRed">*</i> 店铺模版:
												</div>
												<div>
													<select style="float:left;" id="templateSet" name="shopInfo.templateSet"  class="{validate:{required:true}}" onchange="showTemplateSetMgs(this.value)">
														<option value="">---请选择---</option>
														<s:iterator value="#application.keybook['templateSet']" var="kb">
															<option value="<s:property value="#kb.value"/>"><s:property value="#kb.name"/></option>
														 </s:iterator>
													</select>
													<span id="photoMgs"></span>
													<span id="templateSetMgs" style="color:red; height:20px; vertical-align:top;"></span>
												</div>
											</li>
											<li >
												<div>
													<i class="ColorRed">*</i> 主要商品:
												</div>
												<div >
													<input type="text" size="13" name="shopInfo.mainProduct"  value="<s:property value="shopInfo.mainProduct"/>"  class="{validate:{required:true,maxlength:[50]}}" style="width:100px;"/>
												</div>
											</li>
											<li>
												<div >店铺简介:</div>
												<div>
													<textarea style="line-height:20px; height:80px; font-size:12px;resize: none;overflow-y:scroll;" cols="60" rows="5" wrap="scroll" name="shopInfo.synopsis" class="{validate:{maxlength:[1000]}}">${shopInfo.synopsis}</textarea>
												</div>
											</li>
											<li>
												<div>营业时间:</div>
												<div>
												 	<input type="text" name="shopInfo.businessHoursStart" value="${shopInfo.businessHoursStart}" class="{validate:{maxlength:[45]}}" /> <input type="button"  value="至" style="border: 0;"/><input type="text" name="shopInfo.businessHoursEnd" class="{validate:{maxlength:[45]}}" value="${shopInfo.businessHoursEnd}"/> 
												</div>
											</li>
											<li>
												<div><i class="ColorRed">*</i>店铺电话:</div>
												<div>
												 	<input type="text" name="shopInfo.phone" value="${shopInfo.phone}" maxlength="15" class="{validate:{required:true,phone:true}}" /><i class="ColorRed">格式 010-1234567(8)</i>
												</div>
											</li>
											<li>
												<div><i class="ColorRed">*</i> 店铺地址:</div>
												<div>
												 	<input type="text" name="shopInfo.addressForInvoice" value="${shopInfo.addressForInvoice}" class="{validate:{required:true}}" />
												</div>
											</li>
											<li>
												<div><i class="ColorRed">*</i>运费:</div>
												<div>
												 	<input type="text" style="width: 50px;" name="shopInfo.postage" value="${shopInfo.postage}" class="{validate:{required:true,money:true}}"/>元
												</div>
											</li>
											<li>
												 	<div><i class="ColorRed">*</i>&nbsp;&nbsp;订单满</div>
												 	<div>
												 		<input type="text" style="width: 70px;" class="{validate:{required:true,money:true}}" name="shopInfo.minAmount" value="${shopInfo.minAmount}" />
												 	</div>
												 	<div>元包邮</div>
											</li>
											<li>
												<div></div>
												<div>
												   <input id="Submit_button" class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" type="submit" style="border: 0;" value="提交" />		  
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
			</div>
		</div>
		
		<div class="modal" id="upload-tx-overlay">
			<form enctype="multipart/form-data" method="post" id="upload-tx-form">
				<h2 class="modal-title" style="border-bottom: 1px #ccc solid; width:395px;">
					店铺 Logo
				</h2>
				<p class="modal-content">				
					图片文件:<input type="file" name="imagePath" id="tx-file" />
				</p>
				<p>注意：为达到最佳效果，使用一个图像是：350像素宽，350像素高。</p>
				<p class="modal-buttons">
					<button type="submit" id="controlButton1" style="margin-left:131px; border: 0;">提交</button>
					<button class="close" type="button"  style="margin-left: 5px;">
						关闭
					</button>
				</p>
			</form>
		</div>
<!-- 		店铺首页大图 -->
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
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
