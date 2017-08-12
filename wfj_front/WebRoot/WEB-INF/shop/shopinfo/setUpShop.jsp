<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>店铺信息</title>
	<%@ include file="../include/head.jsp" %>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<script type="text/javascript">
	function isRegionLocation(){
		  var country = $("#country").val();
		  $("#regionLocation").html("<option value='-1'>---Please select a region---</option>");
		  
		  
     var url = '${pageContext.request.contextPath}/back/shopinfo/selectCountry.action';
		$.post(url,{"ids":country},function(data){
			if(data){
		         var length = data.length;
				   for(var i=0;i<length;i++){
					   $("#regionLocation").append($("<option value='"+data[i].districtInfoId+"'>"+data[i].districtName+"</option>"));
				   }
			}
		},"json");
	}
	function checks(){
		var s = "";
		$("input[type='checkbox']:checked").each(function(){
			s += $(this).val()+"(,)";
	  	});
	  	$("#nationalIsoId").val(s);
	}
	
	 $(function() {//表单验证
 	    $("#fontForm").validate({meta: "validate", 
	    	      submitHandler:function(form){
 	    				
	               		form.submit();
	              }
 	    });
    });	
    
   function uploadIDCardsImage() {
		$(document).ready(
			function(){
	        	var options = {
	            	url : "${pageContext.request.contextPath}/front/store/frontshopinfo/uploadImageFront.action?is_ajax=2",
                    type : "post",  
                    dataType:"json",
                    success : function(data) {
                    	if(data.IDCardsImage=="false1"){
                       		$("#mybigmessage").html(" <font style='color:red'>请选择上传图片</font>");
                     	}else if(data.photoUrl=="false2"){
                       		$("#mybigmessage").html(" <font style='color:red'>请上传jpg、png、gif格式的图片 ！</font>");
                     	}else{
                      		$("#eIDCardsImage").val(data.photoUrl);
                      		$("#IDCardsImagePreview").html("");
                    	 	$("#IDCardsImagePreview").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' width='150px' height='150px' />");
                     	} 
                    }
                };
             $("#IDCardsImageForm").ajaxSubmit(options);
         });  
	}

  function uploadBusinessLicense() {
		$(document).ready(
			function(){  
	        	var options = {
	            	url : "${pageContext.request.contextPath}/front/store/frontshopinfo/uploadImageFront.action?is_ajax=2",
                  type : "post",  
                  dataType:"json",
                  success : function(data) {
                  	if(data.IDCardsImage=="false1"){
                   		$("#mybigmessage").html(" <font style='color:red'>请选择上传图片</font>");
                   	}else if(data.photoUrl=="false2"){
                   		$("#mybigmessage").html(" <font style='color:red'>请上传jpg、png、gif格式的图片 ！</font>");
                   	}else{
                   		$("#ebusinessLicense").val(data.photoUrl);
                   		$("#businessLicensePreview").html("");
               	 		$("#businessLicensePreview").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' width='150px' height='150px' />");
                   	} 
                  }
              };
           $("#businessLicenseForm").ajaxSubmit(options);
       });  
	}
  function uploadCompanyDocuments() {
		$(document).ready(
			function(){  
	        	var options = {
	            	url : "${pageContext.request.contextPath}/front/store/frontshopinfo/uploadImageFront.action?is_ajax=2",
            type : "post",  
            dataType:"json",
            success : function(data) {
            	if(data.IDCardsImage=="false1"){
             		$("#mybigmessage").html(" <font style='color:red'>请选择上传图片</font>");
             	}else if(data.photoUrl=="false2"){
             		$("#mybigmessage").html(" <font style='color:red'>请上传jpg、png、gif格式的图片 ！</font>");
             	}else{
             		$("#ecompanyDocuments").val(data.photoUrl);
             		$("#companyDocumentsPreview").html("");
         	 		$("#companyDocumentsPreview").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' width='150px' height='150px' />");
             	} 
            }
        };
     $("#companyDocumentsForm").ajaxSubmit(options);
	 });  
	}
  
  function uploadTaxRegistrationDocuments() {
		$(document).ready(
			function(){  
	        	var options = {
	            	url : "${pageContext.request.contextPath}/front/store/frontshopinfo/uploadImageFront.action?is_ajax=2",
            type : "post",  
            dataType:"json",
            success : function(data) {
            	if(data.IDCardsImage=="false1"){
             		$("#mybigmessage").html(" <font style='color:red'>请选择上传图片</font>");
             	}else if(data.photoUrl=="false2"){
             		$("#mybigmessage").html(" <font style='color:red'>请上传jpg、png、gif格式的图片 ！</font>");
             	}else{
             		$("#etaxRegistrationDocuments").val(data.photoUrl);
             		$("#taxRegistrationDocumentsPreview").html("");
         	 		$("#taxRegistrationDocumentsPreview").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' width='150px' height='150px' />");
             	} 
            }
        };
     $("#taxRegistrationDocumentsForm").ajaxSubmit(options);
 });  
	}
  
  function uploadMarketBrandUrl() {
		$(document).ready(
			function(){  
	        	var options = {
	            	url : "${pageContext.request.contextPath}/front/store/frontshopinfo/uploadImageFront.action?is_ajax=2",
            type : "post",  
            dataType:"json",
            success : function(data) {
            	if(data.IDCardsImage=="false1"){
             		$("#mybigmessage").html(" <font style='color:red'>请选择上传图片</font>");
             	}else if(data.photoUrl=="false2"){
             		$("#mybigmessage").html(" <font style='color:red'>请上传jpg、png、gif格式的图片 ！</font>");
             	}else{
             		$("#emarketBrandUrl").val(data.photoUrl);
             		$("#marketBrandUrlPreview").html("");
         	 		$("#marketBrandUrlPreview").html("<img src='"+data.visitFileUploadRoot+data.photoUrl+"' width='150px' height='150px' />");
             	} 
            }
        };
     $("#marketBrandUrlForm").ajaxSubmit(options);
 });  
	}
</script>
<style type="text/css">
		#imgcss{
			margin-top: -50px;
		}
		</style>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth" align="center">
			
			<form id="IDCardsImageForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable" >
			          <tr>
					      <td  width="150"><p align="left"><font style="font-weight: bold;">身份证照片:</font></p> </td>
					      <td  width="440">
					      	<input id="sfile" type="file" size="30" name="imagePath" />
				  	        <input id="buttonId" type="button" value="上传预览" onclick="uploadIDCardsImage();"/>
					      </td>
					  </tr> 
				 </table>
		   	</form>
			<form id="businessLicenseForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable" >
			          <tr>
					      <td  width="150"><p align="left"><font style="font-weight: bold;">营业执照图片:</font></p></td>
					      <td  width="440">
						      <input id="yfile" type="file" size="30" name="imagePath"/>
				  	          <input id="buttonId" type="button" value="上传预览" onclick="uploadBusinessLicense()"/>
					      </td>
					  </tr> 
				 </table>
		   	</form>
			<form id="companyDocumentsForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable">
			          <tr>
					      <td  width="150"><p align="left"><font style="font-weight: bold;">公司证件图片:</font></p> </td>
					      <td  width="440">
				      		<input id="gfile" type="file" size="30" name="imagePath"/>
				  	        <input id="buttonId" type="button" value="上传预览" onclick="uploadCompanyDocuments()"/>
					      </td>
					  </tr> 
				 </table>
		   	</form>
			<form id="taxRegistrationDocumentsForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable" >
			          <tr>
					      <td  width="150"><p align="left"><font style="font-weight: bold;">税务登记证图片:</font></p></td>
					      <td  width="440">
					      	<input id="sfile" type="file" size="30" name="imagePath"/>
					  	    <input id="buttonId" type="button" value="上传预览" onclick="uploadTaxRegistrationDocuments()"/>
					      </td>
					  </tr> 
				 </table>
		   	</form>
			<form id="marketBrandUrlForm" method="post" enctype="multipart/form-data">
			     <table align="center" class="addOrEditTable" >
			          <tr>
					      <td  width="150"><p align="left"><font style="font-weight: bold;">售卖品牌图片:</font></p></td>
					      <td  width="440" >
					     	<input id="smfile" type="file" size="30" name="imagePath"/>
					  	    <input id="buttonId" type="button" value="上传预览" onclick="uploadMarketBrandUrl()"/>
					      </td>
					  </tr> 
				 </table>
		   	</form>
			
			
			<form id="fontForm" action="${pageContext.request.contextPath}/front/store/frontshopinfo/saveOrUpdateShopInfo.action" method="post">
			<input id="eIDCardsImage" type="hidden" name="shopInfo.IDCardsImage" value=""/>
	        <input id="ebusinessLicense" type="hidden" name="shopInfo.businessLicense" value=""/>
	        <input id="ecompanyDocuments" type="hidden" name="shopInfo.companyDocuments" value=""/>
	        <input id="etaxRegistrationDocuments" type="hidden" name="shopInfo.taxRegistrationDocuments" value=""/>
        	<input id="emarketBrandUrl" type="hidden" name="shopInfo.marketBrandUrl" value=""/>
			<input type="hidden" id="nationalIsoId" name="nationalIso" value=""/>
			<table width="690" border="0" align="center">
				<tr>
					<td width="150"><p align="right"><font color="red">*</font><font style="font-weight: bold;">公司名称:</font></p>
					</td>
					<td colspan="4" width="50">
						<input type="text" id="shopName" name="shopInfo.shopName" class="{validate:{required:true}}"/>
						<p><span id="country_errors"></span>请输入您的公司的全称.</p>
					</td>
				</tr>
				<tr>
					<td width="150">
						<p align="right"><font color="red">*</font><font style="font-weight: bold;">注册地址:</font></p>
					</td>
					<td width="50"><p>美国</p>
					</td>
					<td colspan="3" width="150">
						<select id="regionLocation" name="shopInfo.regionLocation" class="{validate:{required:true}}"> 
			              <option value="">---请选择---</option>
			            </select>
			            <span id="regionLocation_errors"></span>
					</td>
				</tr>
				<tr>
					<td width="150">
						<div align="right">
							<font color="red">*</font><font style="font-weight: bold;">公司运营 <br> 地址</font>
						</div>
					</td>
					<td width="50"><p>街:</p>
					</td>
					<td colspan="3" width="150">
						<input type="text" id="address" name="shopInfo.address" class="{validate:{required:true,maxlength:[50]}}"/>
						<p>请输入您的公司地址,例如。街道名称 &amp;号码,建筑数量.</p>
					</td>
				</tr>
				<tr>
					<td width="150">&nbsp;</td>
					<td width="50"><p>城市:</p>
					</td>
					<td colspan="3" width="150">
						<input type="text" name="city" id="shopInfo.city" class="{validate:{required:true,maxlength:[50]}}" />
					</td>
				</tr>
				<tr>
					<td width="150">&nbsp;</td>
					<td width="50"><p>国家/地区:</p>
					</td>
					<td colspan="3" width="150">
						<select id="country" name="shopInfo.country" onchange="isRegionLocation()" class="{validate:{required:true}}"> 
			              <option value="">---请选择---</option>
						  <s:iterator value="districtInfoList">
						  	<option value="<s:property value="districtInfoId"/>"><s:property value="districtName"/></option>
						  </s:iterator>
			            </select>
					</td>
				</tr>
				<tr>
					<td width="150">&nbsp;</td>
					<td width="50"><p>邮政编码:</p>
					</td>
					<td colspan="3" width="150">
						<input type="text" id="postCode" name="shopInfo.postCode" class="{validate:{required:true,maxlength:[50]}}"/>
					</td>
				</tr>
				<tr>
					<td width="150">
						<p align="right"><font color="red">*</font><font style="font-weight: bold;">主要产品:</font></p>
					</td>
					<td>
						<input type="text" id="mainProductOne" name="mainProductOne" size="15" class="{validate:{required:true,maxlength:[50]}}"/>
					</td>
					<td>
						<input type="text" id="mainProductTwo" name="mainProductTwo" size="15" class="{validate:{required:true,maxlength:[50]}}"/>
					</td>
					<td>
						<input type="text" id="mainProductThree" name="mainProductThree" size="15" class="{validate:{required:true,maxlength:[50]}}"/>
					</td>
					<td >
						<input type="text" id="mainProductFour" name="mainProductFour" size="15" class="{validate:{required:true,maxlength:[50]}}"/>
					</td>
				</tr>
				<tr>
					<td >
						<p align="right"><font style="font-weight: bold;">公司注册日期:</font></p>
					</td>
					<td colspan="4" >
						<select id="companyRegistered" name="shopInfo.companyRegistered" class="{validate:{required:true,maxlength:[50]}}">
							<option value="-1">--请选择--</option>
							<s:iterator value="companyRegisteredList" var="list">
								<option value="<s:property value="#list"/>"><s:property value="#list"/></option>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td >
						<p align="right"><font style="font-weight: bold;">合法所有者:</font></p>
					</td>
					<td colspan="4" width="50">
						<input type="text" id="legalOwner" name="shopInfo.legalOwner" class="{validate:{required:true,maxlength:[50]}}"/>
					</td>
				</tr>
				<tr>
					<td rowspan="3" height="200">
						<p align="right"><font style="font-weight: bold;">公司认证:</font></p>
					</td>
					<td height="30" colspan="4" width="150">
						<input type="checkbox" name="companyCertificationOne" id="companyCertificationOne" value="HACCP" onclick="checks()"/>&nbsp;&nbsp;HACCP<br>
						<input type="checkbox" name="companyCertificationTwo" id="companyCertificationTwo" value="ISO 9001:2000" onclick="checks()"/>&nbsp;&nbsp;ISO 9001:2000<br>
						<input type="checkbox" name="companyCertificationThree" id="companyCertificationThree" value="ISO 9001:2008" onclick="checks()"/>&nbsp;&nbsp;ISO 9001:2008<br>
						<input type="checkbox" name="companyCertificationFour" id="companyCertificationFour" value="QS-9000" onclick="checks()"/>&nbsp;&nbsp;QS-9000<br>
						<input type="checkbox" name="companyCertificationFive" id="companyCertificationFive" value="ISO 14001:2004" onclick="checks()"/>&nbsp;&nbsp;ISO 14001:2004<br>
						<input type="checkbox" name="companyCertificationSix" id="companyCertificationSix" value="ISO/TS 16949" onclick="checks()"/>&nbsp;&nbsp;ISO/TS 16949<br>
						<input type="checkbox" name="companyCertificationSeven" id="companyCertificationSeven" value="SA8000" onclick="checks()"/>&nbsp;&nbsp;SA8000<br>
						<input type="checkbox" name="companyCertificationEight" id="companyCertificationEight" value="ISO 17799" onclick="checks()"/>&nbsp;&nbsp;ISO 17799<br>
						<input type="checkbox" name="companyCertificationNine" id="companyCertificationNine" value="OHSAS 18001" onclick="checks()"/>&nbsp;&nbsp;OHSAS 18001<br>
						<input type="checkbox" name="companyCertificationTen" id="companyCertificationTen" value="TL 9000" onclick="checks()"/>&nbsp;&nbsp;TL 9000<br>
						<input type="checkbox" name="companyCertificationEleven" id="companyCertificationEleven" />&nbsp;&nbsp;其他
					</td>
				</tr>
				<tr>
					<td height="32" colspan="4" width="150">
						<input type="text" id="companyCertification" name="shopInfo.companyCertification" size="50" class="{validate:{required:true,maxlength:[50]}}"/>
						<p>使用单独的认证名称 (,)</p>
					</td>
				</tr>
				<!-- 此处加上图片上传 -->
				
				<tr>
				<td align="right" width="150px">售卖品牌图片预览 :</td>
		      <td  align="left" colspan="3">&nbsp;&nbsp;
		          &nbsp;&nbsp;&nbsp;&nbsp;<span id="marketBrandUrlPreview"></span>
		      </td>
				</tr>
				
				
				
				<tr>
				<td align="right" width="150px">身份证图片预览 :</td>
		      <td  align="left" colspan="3">&nbsp;&nbsp;
		          &nbsp;&nbsp;&nbsp;&nbsp;<span id="IDCardsImagePreview"></span>
		      </td>
				</tr>
				
				
				
				
				<tr>
				<td align="right" width="150px">执照图片预览 :</td>
		      <td  align="left" colspan="3">&nbsp;&nbsp;
		          &nbsp;&nbsp;&nbsp;&nbsp;<span id="businessLicensePreview" ></span>
		      </td>
				</tr>
				
				
				
				<tr>
				 <td align="right" width="150px">公司证件图片预览 :</td>
		      <td  align="left" colspan="3">&nbsp;&nbsp;
		          &nbsp;&nbsp;&nbsp;&nbsp;<span id="companyDocumentsPreview"></span>
		      </td>
				</tr>
				
				
				
				<tr>
				<td align="right" width="150px">税务登记证图片预览 :</td>
		      <td  align="left" colspan="3">&nbsp;&nbsp;
		          &nbsp;&nbsp;&nbsp;&nbsp;<span id="taxRegistrationDocumentsPreview" ></span>
		      </td>
				</tr>
				
				
				
				<tr>
					<td height="29" colspan="4" width="150">
						<input type="checkbox" id="acceptAgreement" name="acceptAgreement" class="{validate:{required:true}}"/>
						I have read and accepted the <a href="#"><font color="blue">Membership Agreement</font></a>
						<p><span id="Iaccept"></span></p>
					</td>
				</tr>
				<tr>
					<td width="150">&nbsp;</td>
					<td width="50">
						<input type="submit" value="提交" />
					</td>
					<td colspan="3" width="150">
						<input type="reset" value="返回" />
					</td>
				</tr>
			</table>
			</form>
			</div>
		</div>
</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
