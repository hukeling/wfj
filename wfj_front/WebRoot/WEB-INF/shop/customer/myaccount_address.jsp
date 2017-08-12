<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>收货地址</title>
		<%@ include file="../include/head.jsp"%>
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css">
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
      <!-- tab效果js -->
		<script type="text/javascript">
			$(document).ready(function() {
			    //默认隐藏div
			    $("#addressSwitchDiv").hide();
			    //点击显示添加地址
			    $("#addressSwitch").click(function() {
			    	 $("#checkbox").removeAttr("checked");
			        $("#addressSwitchDiv").show();
			        $("#dcustomerAcceptAddressId").val("");
			        $("#daddress").val("");
			        $("#dpostcod").val("");
			        $("#dconsignee").val("");
			        $("#dlastName").val("");
			        $("#dmobilePhone").val("");
			        $("#demail").val("");
			        $("#firstArea").val("");
			        $("#secondArea").val("");
			        $("#threeArea").val("");
			    });
			    //点击cancel隐藏添加地址
			    $("#addressSwitchCancel").click(function() {
			       /*  $("#addressSwitchDiv").hide(); */
			       window.location.href="${pageContext.request.contextPath}/front/customer/Setting/address.action";
			    });
			    //级联
			    $('#country').change(function() {
			        getRegion($(this).val(), '', $('#regionLocation'));
			        var cname = $(this).find('option:selected').html();
			        $('#country-full-name').val(cname);
			    });
			    $('#country option').each(function() {
			        if ($(this).html() == '${customerInfo.country}') {
			            $(this).attr('selected', 'selected');
			            getRegion($(this).val(), '${customerInfo.regionLocation}', $('#regionLocation'));
			            return;
			        }
			    });
			});
			    
			//设为默认地址
			function setDefAddress(value) {
				var url = '${pageContext.request.contextPath}/front/customer/addressAction/setDefAddress.action';
				$.post(url,{"CAAId":value},function(data){
						if(data){
							window.location.reload();
						}
					},"json");
			}
			
			//删除收货地址
		 	function deleteAddress(id){
// 		 		var htm="<img src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif'/>";
// 		      	htm+="&nbsp;&nbsp;正在提交，请稍等...";
// 		      	$("#delete"+id).html(htm);
				lconfirm("提醒","确认删除",function(o){
					var url='${pageContext.request.contextPath}/front/customer/addressAction/deleteAddress.action';
					  $.post(url,{"CAAId":id},function(data){
							if(data){
								window.location.reload();
						    }
					},"json");
				});
		 	}
			
			//编辑地址
			function edit(value) {
				$("#msg_area").html("");
			    var parames = value;
			    if (parames == "-1") {
			        $("[name='checkbox']").attr("checked", 'true');
			        $("#addressSwitchDiv").show();
			    } else {
			        var url = "${pageContext.request.contextPath}/front/customer/addressAction/getAddress.action";
			        $.post(url, {
			            'CAAId': value
			        },
			        function(data) {
			        	var custAddress  = data.customerAcceptAddress;
			            $("#dcustomerAcceptAddressId").val(custAddress.customerAcceptAddressId);
			            $("#dconsignee").val(custAddress.consignee);
			            $("#dconsignee").removeAttr("style");
			            $("#dlastName").removeAttr("style");
			            $("#dpostcod").val(custAddress.postcode);
			            $("#dmobilePhone").val(custAddress.mobilePhone);
			            $("#dphone").val(custAddress.phone);
			            $("#demail").val(custAddress.email);
			            $("#dflagContractor").val(custAddress.flagContractor);
			            $("#dbestSendDate").val(custAddress.bestSendDate);
			            $("#old_address").val(custAddress.address);
			            $("#daddress").val(custAddress.address);
			            $("#firstArea").val(custAddress.regionLocation);
						chengeArea(custAddress.regionLocation,1,custAddress.city,2);
						chengeArea(custAddress.city,2,custAddress.areaCounty);
			            $("#daddress").removeAttr("style");
			            if (custAddress.isSendAddress == "1") {
			                $("#checkbox").attr("checked", true);
			            } else {
			                $("#checkbox").removeAttr("checked");
			            }
			            $("#addressSwitchDiv").show();
			        },
			        "JSON");
			    }
			}
			
			//表单验证提交
			$(function() { //表单验证
			    $("#form1").validate({
			        meta: "validate",
			        submitHandler: function(form) {
			        	//校验区域是否必填
						//声明变量flag， 如果为true说明区域已选 如果为false说明区域未选
						var flag=null;
						var firstArea=$("#firstArea").val();
						var secondArea=$("#secondArea").val();
						var threeArea=$("#threeArea").val();
						if(firstArea==""||secondArea==""||threeArea==""){
							flag=false;
						}else{
							flag=true;
						}
						if(!flag){
							$("#msg_area").html("<font color='red'>请选择区域</font>");
						}else{
							$("#msg_area").html("");
							if(firstArea!=""){
								firstArea=$("#"+firstArea).text();
							}
							if(secondArea!=""){
								secondArea = $("#"+secondArea).text();
							}
							if(threeArea!=""){
								threeArea = $("#"+threeArea).text();
							}
							var newAddress = $("#daddress").val();
								var addressIndex = newAddress.indexOf(",");
								newAddress = newAddress.substring(addressIndex+1,newAddress.lenght);
							$("#saveAddress").val("");
							$("#saveAddress").val(firstArea+secondArea+threeArea+","+newAddress);
							//控制保存按钮变灰，避免重复提交
							$(".submitImg").attr("disabled","disabled");
							$(".submitImgLoad").attr("style","display:block;");
							form.submit();
						}
			        }
			    });
			});
			
		//更改地址level等级,selectId如果是编辑直接选中，level等级
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
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				 <section id="Marketpalce">
					<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">收货地址</h3>
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<a id="addressSwitch" href="#" onclick="edit('-1');"
									class="publicinline publicPadding5_10 BackgroundPurple ColorWhite1 publicMargin20 FontSize16">添加/编辑收货地址</a>
								<div class="radius publicPadding20 width90 margin-center BackgroundF8Hui publicMarginBot15" id="addressSwitchDiv">
									<form id="form1"  action="${pageContext.request.contextPath}/front/customer/Setting/saveOrUpdate.action?flag=2" method="post">
										<input type="hidden" id="dcustomerAcceptAddressId" name="customerAcceptAddress.customerAcceptAddressId" a="b" />
										<input type="hidden" id="old_address"  class="{validate:{required:true}}" value="">
										<input type="hidden" id="saveAddress"  name="customerAcceptAddress.address" >
										<p class="strong">区域:</p>
										<p >
											<select name="customerAcceptAddress.regionLocation" id="firstArea" onchange="chengeArea(this.value,'1','')">
												<option value="">--请选择--</option>
												<s:iterator value="firstAreaList" var="first">
													<option value="<s:property value="#first.aid"/>" id="<s:property value="#first.aid"/>"><s:property value="#first.name"/></option>
												</s:iterator>
											</select>
											<select name="customerAcceptAddress.city" id="secondArea" onchange="chengeArea(this.value,'2','')">
												<option value="" >--请选择--</option>
											</select>
											<select name="customerAcceptAddress.areaCounty" id="threeArea" onchange="chengeArea(this.value,'3','')">
												<option value="">--请选择--</option>
											</select>
											<span id="msg_area"></span>
										</p>
										<p class="strong">详细地址:</p>
										<p>
											<input style="line-height: 25px;" id="daddress" name="" type="text" class="atext {validate:{required:true,maxlength:[150]}}">
										</p>
										<p class="strong">
											邮政编码:
										</p>
										<p>
											<input style="line-height: 25px;" id="dpostcod" name="customerAcceptAddress.postcode"
												type="text"
												class="widthpx200 height25 {validate:{required:true,zipCode:true}}">
										</p>
										<p class="strong">
											姓名:
										</p>
										<p>
											<input style="line-height: 25px;" id="dconsignee" name="customerAcceptAddress.consignee"
												type="text" class="widthpx200 height25 {validate:{required:true,maxlength:[20]}}">
											
										</p>

										<p class="strong">
											手机号码:
										</p>
										<p>
											<input style="line-height: 25px;" id="dmobilePhone"
												name="customerAcceptAddress.mobilePhone" type="text"
												class="widthpx200 height25 {validate:{required:true,mobile:true}}">
										</p>
										<p class="strong">
											邮箱:
										</p>
										<p>
											<input style="line-height: 25px;" id="demail" name="customerAcceptAddress.email"
												type="text"
												class="widthpx200 height25 {validate:{required:true,email:true ,maxlength:[100]}}">
										</p>
										<p>
											<input style="line-height: 25px;" id="checkbox" name="customerAcceptAddress.isSendAddress" type="checkbox"
												value="1">
											设置默认地址
										</p>
										<p>
											<input name="" type="submit" value="保存"
												class="submitImg submitButtonL publicPaddingLR10 publicMarginR10 radius" style="border: 1px solid #258CD9; border:0;">
											<input name="" type="button" value="取消"
												class="publicPaddingLR10 publicMarginR10 submitButtonL BackgroundOrange radius"
												style="background-color: #FF6600 !important;border: 1px solid #FF6600;"
												id="addressSwitchCancel">
											<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
										</p>
									</form>
								</div>
								<table id="table1" width="100%" border="0"
									class="OrderinforTableL width95 margin-center publicMarginBot15">
									<tr class=" publicBorder gradient">
										<td width="10%" style="border-bottom: 1px #d2d2d2 solid;border-top: 1px #d2d2d2 solid;border-left: 1px #d2d2d2 solid;">
											收货人
										</td>
										<td width="28%" style="border-bottom: 1px #d2d2d2 solid;border-top: 1px #d2d2d2 solid;">
											详细地址
										</td>
										<td width="8%" style="border-bottom: 1px #d2d2d2 solid;border-top: 1px #d2d2d2 solid;">
											邮政编码
										</td>
										<td width="11%" style="border-bottom: 1px #d2d2d2 solid;border-top: 1px #d2d2d2 solid;">
											手机号码
										</td>
										<td width="15%" style="border-bottom: 1px #d2d2d2 solid;border-top: 1px #d2d2d2 solid;">
											邮箱
										</td>
										<td width="15%" style="border-bottom: 1px #d2d2d2 solid;border-top: 1px #d2d2d2 solid; border-right: 1px #d2d2d2 solid;">
											操作
										</td>
									</tr>
									<!--默认地址 -->
									<s:if test="customerAcceptAddress!=null||customerAcceptAddress.customerAcceptAddressId!=null">
									<tr>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="customerAcceptAddress.consignee" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="customerAcceptAddress.address" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="customerAcceptAddress.postcode" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="customerAcceptAddress.mobilePhone" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="customerAcceptAddress.email" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<a href="javascript:;" class="ColorBlue"
												onclick="edit(<s:property value='customerAcceptAddress.customerAcceptAddressId'/>);">编辑</a>
											<a
												href="javascript:deleteAddress(<s:property value='customerAcceptAddress.customerAcceptAddressId'/>);"
												class="ColorBlue"><span id="delete<s:property value='customerAcceptAddress.customerAcceptAddressId'/>" >删除</span></a>
											<span style="color: gray;">设为默认</span>
										</td>
									</tr>
									</s:if>
									<s:iterator value="customerAcceptAddressList" var='caa'>
										<tr>
											<td>
												<s:property value="#caa.consignee" />
											</td>
											<td>
												<s:property value="#caa.address" />
											</td>
											<td>
												<s:property value="#caa.postcode" />
											</td>
											<td>
												<s:property value="#caa.mobilePhone" />
											</td>
											<td>
												<s:property value="#caa.email" />
											</td>
											<td>
												<a href="#" class="ColorBlue"
													onclick="edit(<s:property value='#caa.customerAcceptAddressId'/>);">编辑</a>
												<a 	href="javascript:deleteAddress(<s:property value='#caa.customerAcceptAddressId'/>);"
													class="ColorBlue">删除</a>
												<a
													href="javascript:setDefAddress(<s:property value='#caa.customerAcceptAddressId'/>);"
														class="ColorBlue">设为默认</a>
												</td>
											</tr>
									</s:iterator>
								</table>
							</div>
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
