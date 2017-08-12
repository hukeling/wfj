<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>企业信息</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css"/>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<style type="text/css">
			.tip-tx{height:25px;width:122px;text-align:center;background-color: #666;}
			.tip-tx a {color:#fff;}
		</style>
		<script type="text/javascript">
	//提交申请
	 $(function() {
		 var shopInfoCheckSatus='${shopInfo.shopInfoCheckSatus}';
		 if(shopInfoCheckSatus=="1"){
			 $("input[name='imagePath']").attr("disabled","disabled");
			 $("#companyName").attr("readonly","readonly");
			 $("#shopName").attr("readonly","readonly");
			 $("#parentIdList").attr("disabled","disabled");
			 $("#postCode").attr("readonly","readonly");
		 }
	 	//表单验证
 	    $("#fontForm").validate({meta: "validate", 
	   	      submitHandler:function(form){
				  var shopInfoCheckSatus='${shopInfo.shopInfoCheckSatus}';
	   	    	  if(shopInfoCheckSatus=="1"){//企业信息审核状态为待审核
	   	    	  	  $("#fontFrom")[0].reset();
	   	    		  lalert("提醒","信息处于待审核状态,无法操作!");
	   	    	  }else{
			   	        $(".submitImg").attr("disabled","disabled"); 
			   	        $(".submitImgLoad").attr("style","display:block;");
	 			        form.submit();
	   	    	  }
	          }
 	    });
    });	
	
	//异步上传图片
	function uploadPhoto(formName,showPlace,savePlace,errorInfo,fileId) {
		var fileValue=$("#"+fileId).val();
		if(fileValue!=null&&fileValue!=""){
			var htm="<img src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif'/>";
      		htm+="&nbsp;&nbsp;正在提交，请稍等...";
      		$("#"+errorInfo+"Img").attr("disabled","disabled");
      		$("#"+errorInfo+"ImgLoad").html(htm);
			$(document).ready(
				function(){  
	        		var options = {
	            		url : "${pageContext.request.contextPath}/front/store/frontshopinfo/uploadImageFront.action?is_ajax=2",
		            	type : "post",  
		            	dataType:"json",
		            	success : function(data) {
		            		$("#"+errorInfo+"Img").removeAttr("disabled");
		              		$("#"+errorInfo+"ImgLoad").html("");
		            		if(data.photoUrl=="false"){
		             			$("#"+errorInfo).html(" <font style='color:red'>上传的文件格式不对，应是 jpg,gif,jpeg,png,bmp</font>");
		             		}else{
		             			$("#"+savePlace).val(data.photoUrl);
		             			$("#"+showPlace).html("");
		         	 			$("#"+showPlace).html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' style='width: 50px;height: 50px' />");
		             		} 
            			}
        			};
	    		$("#"+formName).ajaxSubmit(options);
			});  
	  	}
	}
  		//重置按钮，刷新页面
	  function formReset()
	  {
		  window.location = "${pageContext.request.contextPath}/front/store/frontshopinfo/gotoFrontShopInfoPage.action";
	  }
  
	//更改地址level等级
	function chengeArea(id,level,selectId){
		$.ajax({
  			url:"${pageContext.request.contextPath}/front/customer/addressAction/findAreaList.action",
  			type:"post",
  			dataType:"json",
  			data:{areaId:id},
  			success:function(data){
  				if(data!=""){
  					var areaList=data;
  					var htmlOption="<option value=''>--请选择--</option>";
 						for(var i=0;i<areaList.length;i++){
 							htmlOption+="<option  value='" + areaList[i].aid+"'id='" + areaList[i].aid+"'>" + areaList[i].name+ "</option>";
 						}
  					if(level==1){
  						$("#secondArea").html(htmlOption);
  						$("#secondArea").val(selectId);
						$("#threeArea").html("<option value=''>--请选择--</option>");
						
  					}else if(level==2){
  						$("#threeArea").html("");
  						$("#threeArea").append(htmlOption);
  						$("#threeArea").val(selectId);
  					}
  				}
  			}
 			});
	}
	
	
	//校验企业信息唯一性
	  jQuery.validator.addMethod("checkCompanyName",function(value, element) {
			var companyName='${shopInfo.companyName}';
			var flag=true;//标记用户是否对企业名称进行了修改
			var flag2=true;//标记当前企业名称是否已被使用
			if(companyName!=""&&companyName!=null){
				if(value==companyName){
					flag=false;//false说明用户没有操作当前企业名称
				}
			}
			if(flag){
				$.ajax({
 				   type: "POST",dataType: "JSON",
 				   url:"${pageContext.request.contextPath}front/store/frontshopinfo/checkCompanyName.action",
 				   data: {companyName:value},
 				   async : false,
 				   success: function(data){
 					  if(data){
 							if(data.isSuccess=="false"){
 								flag2=false;
 							}
 						}
 				   }
 				});
			}
			return flag2;
	  },"该企业名称已被使用！" );	
	
	
	//校验店铺名称唯一性
	jQuery.validator.addMethod("checkShopName",function(value, element) {
			var shopName='${shopInfo.shopName}';
			var flag=true;//标记用户是否对店铺名称进行了修改
			var flag2=true;//标记当前店铺名称是否已被使用
			if(shopName!=""&&shopName!=null){
				if(value==shopName){
					flag=false;//false说明用户没有操作当前店铺名称
				}
			}
			if(flag){
				$.ajax({
	 				   type: "POST",dataType: "JSON",
	 				   url:"${pageContext.request.contextPath}front/store/frontshopinfo/checkShopName.action",
	 				   data: {shopName:value},
	 				   async : false,
	 				   success: function(data){
	 					  if(data){
	 							if(data.isSuccess=="false"){
	 								flag2=false;
	 							}
	 						}
	 				   }
	 				});
			}
			return flag2;
	  },"该店铺名称已被使用！" );	
		
		
</script>
		
		
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<s:if test="#session.customer.type==1">
					<%@ include file="../include/left_account.jsp"%>
				</s:if>
				<s:elseif test="#session.customer.type==2">
					<%@ include file="../include/left_shop.jsp"%>
				</s:elseif>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				 <section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">企业信息</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<h3 class="FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">企业信息状态:
									<s:if test="#session.shopInfo.shopInfoCheckSatus==1">
										待审核
									</s:if>
									<s:elseif test="#session.shopInfo.shopInfoCheckSatus==2">
										审核通过
									</s:elseif>
									<s:elseif test="#session.shopInfo.shopInfoCheckSatus==3">
										审核未通过
									</s:elseif>
							</h3>
								<!--销售品牌Logo  -->
		   	<s:if test="#session.customer.type==2">
			<form id="marketBrandUrlForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable shopinfoBox_table" >
			          <tr>
			          	  <td  style="width:122px;height:40px;padding-top:20px;"><p style=" text-align: right;"><font style="font-weight: bold;">销售品牌Logo:</font></p></td>
					      <td style="width:428px; height:40px;padding-top:20px;">
					     	<input style="" id="smfile" type="file" size="30" name="imagePath"/><span id="marketBrandUrl"></span>
					  	    <input id="marketBrandUrlImg" type="button" class="submitButtonL" value="上传" onclick="uploadPhoto('marketBrandUrlForm','marketBrandUrlPreview','emarketBrandUrl','marketBrandUrl','smfile')"/>
					      	<span id="marketBrandUrlImgLoad"></span>
					      </td>
					        <td>
					          <span id="marketBrandUrlPreview" >
					          	<s:if test="shopInfo.marketBrandUrl!=null">
					          		<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot"/><s:property value="shopInfo.marketBrandUrl"/>" style="height: 50px;width: 50px" />
					         	</s:if>
					          </span>
					        </td>
					       
					  </tr> 
				 </table>
		   	</form>
		   	</s:if>
		   	<!--营业执照图片  -->
			<form id="businessLicenseForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable shopinfoBox_table" >
			          <tr>
					      <td  style="width:122px;height:40px;padding-top:20px;"><p style=" text-align: right;"><font style="font-weight: bold;">营业执照:</font></p></td>
					      <td style="width:428px; height:40px;padding-top:20px;">
						      <input id="yfile" type="file" size="30" name="imagePath"/>
						      <span id="businessLicense"></span>
				  	          <input id="businessLicenseImg" type="button" class="submitButtonL" value="上传" onclick="uploadPhoto('businessLicenseForm','businessLicensePreview','ebusinessLicense','businessLicense','yfile')"/>
					      	  <span id="businessLicenseImgLoad"></span>
					      </td>
					       <td>
					         <span id="businessLicensePreview">
					         <s:if test="shopInfo.businessLicense!=null">
					         	<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot+shopInfo.businessLicense"/>" style="height: 50px;width: 50px;"/>
					         </s:if>
					         </span>
					      </td>
					    
					  </tr> 
				 </table>
		   	</form>
		   	
		   	<!--组织机构代码证图片  -->
			<form id="companyDocumentsForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable shopinfoBox_table">
			          <tr>
					      <td style="width:122px;height:40px;padding-top:20px;"><p style=" text-align: right;"><font style="font-weight: bold;">组织机构代码证:</font></p> </td>
					      <td style="width:428px; height:40px;padding-top:20px;">
				      		<input id="gfile" type="file" size="30" name="imagePath"/><span id="companyDocuments"></span>
				  	        <input id="companyDocumentsImg" type="button" class="submitButtonL" value="上传" onclick="uploadPhoto('companyDocumentsForm','companyDocumentsPreview','ecompanyDocuments','companyDocuments','gfile')"/>
					      	<span id="companyDocumentsImgLoad"></span>
					      </td>
					      <td >
					          <span id="companyDocumentsPreview">
					          <s:if test="shopInfo.companyDocuments!=null">
					          	<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot+shopInfo.companyDocuments"/>" style="height: 50px;width: 50px;" />
					          </s:if>
					          </span>
					      </td>
					  </tr> 
				 </table>
		   	</form>
		   	
		   	<!--税务登记证图像 -->
			<form id="taxRegistrationDocumentsForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable shopinfoBox_table" >
			          <tr>
					      <td style="width:122px;height:40px;padding-top:20px;" ><p style=" text-align: right;"><font style="font-weight: bold;">税务登记证:</font></p></td>
					      <td style="width:428px; height:40px;padding-top:20px;">
					      	<input id="sfile" type="file" size="30" name="imagePath"/><span id="taxRegistrationDocuments"></span>
					  	    <input id="taxRegistrationDocumentsImg" type="button" class="submitButtonL" value="上传" onclick="uploadPhoto('taxRegistrationDocumentsForm','taxRegistrationDocumentsPreview','etaxRegistrationDocuments','taxRegistrationDocuments','sfile')"/>
					      	<span id="taxRegistrationDocumentsImgLoad"></span>
					      </td>
					      <td >
					          <span id="taxRegistrationDocumentsPreview" >
					          <s:if test="shopInfo.taxRegistrationDocuments!=null">
					          	<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot+shopInfo.taxRegistrationDocuments"/>" style="height: 50px;width: 50px;" />
					          </s:if>
					          </span>
					      </td>
					  </tr> 
				 </table>
		   	</form>
			
			<form id="fontForm" action="${pageContext.request.contextPath}/front/store/frontshopinfo/saveOrUpdateShopInfo.action" method="post">
			<!-- 从SESSION中取出的 -->
			<input type="hidden" name="shopInfo.customerId" value="<s:property value="#request.session.customer.customerId"/>"/>
			<s:token></s:token>
			<input type="hidden" name="shopInfo.customerName" value="<s:property value="#request.session.customer.customerName"/>"/>
			
			<input  type="hidden" id="shopInfoId" name="shopInfo.shopInfoId" value="<s:property value="shopInfo.shopInfoId"/>"/>
			<input id="eIDCardsImage" type="hidden" name="shopInfo.IDCardsImage" value="<s:property value="shopInfo.marketBrandUrl"/>"/>
	        <input id="ebusinessLicense" type="hidden" name="shopInfo.businessLicense" value="<s:property value="shopInfo.businessLicense"/>"/>
	        <input id="ecompanyDocuments" type="hidden" name="shopInfo.companyDocuments" value="<s:property value="shopInfo.companyDocuments"/>"/>
	        <input id="etaxRegistrationDocuments" type="hidden" name="shopInfo.taxRegistrationDocuments" value="<s:property value="shopInfo.taxRegistrationDocuments"/>"/>
        	<input id="emarketBrandUrl" type="hidden" name="shopInfo.marketBrandUrl" value="<s:property value="shopInfo.marketBrandUrl"/>"/>
			<input type="hidden" id="nationalIsoId" name="nationalIso" value=""/>
			<input type="hidden" id="isPass" name="shopInfo.isPass" value="1"/>
			<table border="0" align="center" class="shopinfoBox_table">
				<tr>
					<td style="font-weight: bold;width:122px;height:37px;padding-top:23px;"><p style=" text-align: right;"><font color="red">*</font>企业名称:</p></td>
					<td colspan="5" style="width:320px; height:40px;padding-top:20px;">
						<input type="text" id="companyName" style="width:290px;" name="shopInfo.companyName" value="<s:property value="shopInfo.companyName"/>" class="{validate:{required:true,maxlength:[150],checkCompanyName:true}} Registration"/>
					</td>
					<td></td>
				</tr>
				<%--<s:if test="#session.customer.type==2">
				<tr>
					<td>
						<p style=" text-align: right;"><font color="red">*</font><font style="font-weight: bold;">店铺名称:</font></p>
					</td>
					<td colspan="4" width="50">
						<input value="<s:property value="shopInfo.shopName"/>" type="text" id="shopName" name="shopInfo.shopName" class="{validate:{required:true,maxlength:[100],checkShopName:true}} Registration" />
					</td>
					  <td></td>
				</tr>
				 <tr>
					<td>
						<p style=" text-align: right;"><font color="red">*</font><font style="font-weight: bold;">平台店铺分类:</font></p>
					</td>
					<td colspan="4" width="50">
						<select  id="parentIdList"  name="shopInfo.shopCategoryId" class="{validate:{required:true}} Registration">
						  <option value="">-----请选择分类-----</option>
						  <s:iterator  value="listShopCate">
						  	<option  <s:if test="shopInfo.shopCategoryId==shopCategoryId">selected="selected"</s:if>  value="<s:property value='shopCategoryId' />">
						  	<s:if test="level==1">&nbsp;&nbsp;<s:property value="shopCategoryName"/></s:if>
						  	<s:if test="level==2">&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="shopCategoryName"/></s:if>
						  	<s:if test="level==3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="shopCategoryName"/></s:if>
						  	</option>
						  </s:iterator>
						</select>
					</td>
				</tr> 
				</s:if>--%>
				<%--  <tr>
					<td ><p style=" text-align: right;"><font color="red">*</font><font style="font-weight: bold;">公司名称:</font></p>
					</td>
					<td colspan="4" width="50">
						<input type="text"  id="companyName" name="shopInfo.companyName" class="{validate:{required:true}} Registration" value="<s:property value="shopInfo.companyName"/>"/>
						<s:if test="shopInfo==null">
							<p style="width:180px; height: 24px; line-height: 24px;"><span id="country_errors"></span>请输入您的公司的全称。</p>
						</s:if>
					</td>
				</tr> --%> 
				<%-- <tr>
					<td >
						<p style=" text-align: right;"><font color="red">*</font><font style="font-weight: bold;">企业所在区域:</font></p>
					</td>								            					
					<td colspan="5" style="line-height:30px;">
						<s:if test="companyAddress==null">
						<select  name="shopInfo.regionLocation" id="firstArea" onchange="chengeArea(this.value,'1','')">
							<option value="">--请选择--</option>
							<s:iterator value="firstAreaList" var="first">
								<option  value="<s:property value="#first.aid"/>" id="<s:property value="#first.aid"/>"><s:property value="#first.name"/></option>
							</s:iterator>
						</select>
						<select name="shopInfo.city" id="secondArea" onchange="chengeArea(this.value,'2','')">
							<option value="" >--请选择--</option>
						</select>
						<select name="shopInfo.areaCounty" id="threeArea" onchange="chengeArea(this.value,'3','')">
							<option value="">--请选择--</option>
						</select>
						</s:if>
 						<s:else>
							<input type="hidden" name="shopInfo.regionLocation" id="regionLocation" value="${shopInfo.regionLocation}"/>
							<input type="hidden" name="shopInfo.city" id="city" value="${shopInfo.city}"/>
							<input type="hidden" name="shopInfo.areaCounty" id="areaCounty" value="${shopInfo.areaCounty}"/>
							<input type="text" class="Registration" id="companyAddress" value="${companyAddress}"/>
							
						</s:else> 
					</td>
				</tr> --%>
				<tr>
					<td >
						<p style=" text-align: right;"><font color="red">*</font><font style="font-weight: bold;">详细地址:</font></p>
					</td>						
					<td colspan="5" >
						<input type="text" id="Address" name="shopInfo.address"  class="{validate:{required:true,maxlength:[50]}} Registration"  value="<s:property value="shopInfo.address"/>"/>
					</td>
				</tr>
				<tr>
					<td style="font-weight: bold;width:122px;height:37px;padding-top:23px;"><p style=" text-align: right;"><font color="red">*</font>邮政编码:</p></td>
					<td colspan="5" style="width:320px; height:40px;padding-top:20px;">
						<input type="text" id="postCode" style="width:290px;" name="shopInfo.postCode" value="<s:property value="shopInfo.postCode"/>" class="{validate:{required:true,maxlength:[6],zipCode:true}} Registration"/>
					</td>
				</tr>
				<%-- 
					<s:if test="#session.customer.type==2">
				<tr>
					<td >
						<p style=" text-align: right;"><font color="red">*</font><font style="font-weight: bold;">主要产品:</font></p>
					</td>			
						<td colspan="5">
							<input type="text"  name="shopInfo.mainProduct"  value="<s:property value="shopInfo.mainProduct"/>"  class="{validate:{required:true,maxlength:[50]}} Registration" />
						</td>
				</tr>
					</s:if>
				<tr>
					<td >
						<p style=" text-align: right;"><font style="font-weight: bold;">公司注册日期:</font></p>
					</td>
					<td colspan="4" >
						<input type="text" name="shopInfo.companyRegistered" onclick="WdatePicker({lang:'cn'})" style="width: 180px;" class="Registration" value="<s:property value="shopInfo.companyRegistered"/>"/>
					</td>
				</tr> --%>
				<%-- <tr>
				    <td colspan="5" style="padding-top:5px; text-align:center;">
				    <div style="width:300px;padding-left:170px;">
                    <input type="checkbox" id="acceptAgreement" name="acceptAgreement"/>我接受,并已阅读<a onclick="openMembership()" href="#"><font color="blue">会员协议.</font></a> 
						<span id="Iaccept"></span>
				    </div>
                    </td >
				</tr> --%>
					<tr>
	                    <td colspan="5" style="padding-top:10px;">
	                    <div style="width:330px;padding-left:133px;">
	                    	<span class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</span>
							<input  style="width:140px;margin-right:20px;" type="submit" value="保存" class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" style="margin-right:20px; text-align: center; cursor: pointer;"/>			
							<input  style="width:140px;margin-right:0;text-align: center; cursor: pointer;background-color: #d01743 !important;border: 1px solid #d01743;" type="button" onclick="formReset();" value="重置"  class="publicPaddingLR10 publicMarginR10 submitButtonL BackgroundOrange radius"/>
						</div>
						</td>
					</tr>
			</table>
			</form>
						
						</div>
					</div>
				 </section>
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
