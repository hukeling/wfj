<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
	<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				 <section id="Marketpalce">
					<div class="MarketpalceTree">
						<div class="TabParent_lj publicBorder ClearBoth">
							<div>
								<div class="radius publicPadding20 width90 margin-center BackgroundF8Hui publicMarginBot15" id="addressSwitchDiv">
									<form id="form1"  action="${pageContext.request.contextPath}/phone/saveOrUpdateCustomerAcceptAddress.action" method="post">
										<input type="hidden" id="dcustomerAcceptAddressId" name="customerAcceptAddress.customerAcceptAddressId" a="b" />
										<input type="hidden" id="customerId" name="customerAcceptAddress.customerId" value="${customer.customerId}">
										<p class="strong">详细地址:</p>
										<p>
											<input style="line-height: 25px;" id="saveAddress" name="customerAcceptAddress.address" type="text" class="atext {validate:{required:true,maxlength:[150]}}">
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
										<p>
											<input name="" type="submit" value="保存"
												 style="border: 1px solid #258CD9; border:1;">
											<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
										</p>
									</form>
								</div>
							</div>
					    </div>
					</div>
				 </section>

				</div>
				<!--//right-->
			</div>
		</div>
</body>
</html>