<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>企业信息</title>
	<%@ include file="../include/head.jsp"%>
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
	
<script type="text/javascript">
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
			<div class="ClearBoth" style="margin-top:10px;">
			<s:if test="#session.customer==null||#session.customer.type==1">
				<s:if test="#session.customer.type==1">
					<%@ include file="../include/left_account.jsp"%>
				</s:if>
				<s:elseif test="#session.customer.type==3">
					<%@ include file="../include/left_account_gr.jsp"%>
				</s:elseif>
			</s:if>
			<s:elseif test="#session.customer.type==2">
				<%@ include file="../include/left_shop.jsp"%>
			</s:elseif>
			<!--right-->
			<div id="rightBox" class="float-right publicHidden">
			<div>
			<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">企业信息</h3>
			<div class="publicBorder publicPadding10">
			<form id="fontForm" action="" method="post">
			<input type="hidden" name="shopInfo.customerId" value="<s:property value="#request.session.customer.customerId"/>"/>
			<input type="hidden" name="shopInfo.customerName" value="<s:property value="#request.session.customer.customerName"/>"/>
			
			<input  type="hidden" name="shopInfo.shopInfoId" value="<s:property value="shopInfo.shopInfoId"/>"/>
			<table width="690px" border="0" class="Registration_Information_table">
					<s:if test="#session.customer.type==2">
				<tr>
					<th style="padding:5px 0;">
						<p style=" text-align: right;"><font style="font-weight: bold;">店铺名称：</font></p>
					</th>
					<td>
						<s:property value="shopInfo.shopName"/>
					</td>
					<td></td>
				</tr>
					</s:if>
				<%-- <tr>
					<td width="150">
						<p align="right"><font style="font-weight: bold;">品牌名称:</font></p>
					</td>
					<td colspan="4" width="50">
						<input type="text"  disabled="disabled" id="marketBrand" name="shopInfo.marketBrand" value="<s:property value="shopInfo.marketBrand"/>"/>
					</td>
				</tr>--%>
				<tr>
					<th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">公司名称：</font></p>
					</th>
					<td colspan="4" width="50">
						<s:property value="shopInfo.companyName"/>
					</td>
				</tr>
				<tr>
				   <th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">公司所在区域：</font></p>
					</th>					
					<td colspan="4" >
						<s:property value="shopInfo.regionLocation"/>
						<s:property value="shopInfo.city"/>
						<s:property value="shopInfo.areaCounty"/>
					</td>
				</tr>
				<tr>
				     <th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">详细地址：</font></p>
					</th> 
					<td colspan="3" >
						<s:property value="shopInfo.address"/>
					</td>
				</tr>
				<tr>
				    <th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">邮政编码：</font></p>
					</th> 
					<td colspan="3" width="150">
						<s:property value="shopInfo.postCode"/>
					</td>
				</tr>
					<s:if test="#session.customer.type==2">
				<tr>
					<th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">主要产品：</font></p>
					</th> 
						<td>
							<s:property value="shopInfo.mainProduct"/>
						</td>
					
				</tr>
					</s:if>
				<tr>
					 <th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">公司注册日期：</font></p>
					</th> 
					<td colspan="4" >
						<s:property value="shopInfo.companyRegistered"/>
					</td>
				</tr>
				<!-- 此处加上图片上传 -->
				<tr><td  ></td><td  ></td></tr><tr><td ></td><td ></td></tr>
				
				<tr>
				   <th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">营业执照图片：</font></p>
					</th> 
			      <td  colspan="3">
			         <span id="businessLicensePreview" style="height: 140px;width: 100px">
			         <s:if test="shopInfo.businessLicense!=null">
			         	<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot+shopInfo.businessLicense"/>" style="height: 140px;width: 100px;" >
			         </s:if>
			         </span>
			      </td>
				</tr>
				<tr>
				   <th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">公司证件照片：</font></p>
					</th> 
			      <td  colspan="3">
			          <span id="companyDocumentsPreview">
			          <s:if test="shopInfo.companyDocuments!=null">
			          	<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot+shopInfo.companyDocuments"/>" style="height: 140px;width: 100px" >
			          </s:if>
			          </span>
			      </td>
				</tr>
				<tr>
				  <th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">税务登记证图像：</font></p>
				  </th> 
			      <td   colspan="3">
			          <span id="taxRegistrationDocumentsPreview" >
			          <s:if test="shopInfo.taxRegistrationDocuments!=null">
			          	<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot+shopInfo.taxRegistrationDocuments"/>" style="height: 140px;width: 100px" >
			          </s:if>
			          </span>
			      </td>
				</tr>
					<s:if test="#session.customer.type==2">
				<tr>
					<th style="padding:5px 0;"><p style="text-align: right;"><font style="font-weight: bold;">销售品牌形象：</font></p>
					</th> 
			        <td  colspan="4" width="50">
			          <span id="marketBrandUrlPreview" >
			          	<s:if test="shopInfo.marketBrandUrl!=null">
			          		<img  src="<s:property value="#application.fileUrlConfig.visitFileUploadRoot"/><s:property value="shopInfo.marketBrandUrl"/>" style="height: 140px;width: 100px" >
			         	</s:if>
			          </span>
			        </td>
				</tr>
					</s:if>
			</table>
			</form>
			</div>
			</div>
			</div>
			</div>
		</div>
</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
