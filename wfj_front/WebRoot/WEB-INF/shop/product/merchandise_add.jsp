<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商品添加</title>
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
		<style type="text/css">
		
			.specificationSelect {height: 100px;padding: 5px;overflow-y: scroll;border: 1px solid #cccccc;}
			.specificationSelect li {float: left;min-width: 150px;_width: 200px;}
			.hidden {display: none;}
			#specificationSelect{overflow:hidden;}
			.tdddd{}

		</style>
		<script type="text/javascript">
			$(function() { //表单验证
			    $("#inputForm").validate({
			        meta: "validate",
			        submitHandler: function(form) {
			        	$("#submitButton").attr("disabled",true);
			        	if($("#productType-span-3").find("option:selected").length>0){
				        	if($("#productType-span-3").find("option:selected").attr("loadType")=="1"||$("#productType-span-4").attr("flag")){
					        	var submitFlag=validateSubmit();
					        	if(submitFlag){
					        		$("#KeFunctionDesc").val(editor.html());
						        	//form表单控制提交按钮，防止重复提交
									//使用时，在jsp里面需要加上以下内容：
									//1.from提交按钮submit加上class="submitImg"。
									//2.在submit后面加上<span class="submitImgLoad"></span>用来2显示图标和提示信息
									//$(".submitImg").attr("disabled","disabled");
									//$(".submitImgLoad").attr("style","display:block;");
									var pta=$("#productTypeId-1").val()+","+$("#productTypeId-2").val()+","+$("#productTypeId-3").val()+","+$("#productTypeId-4").val();
									$("#productTypeAddress").val(pta);
									//处理运费
									if($('input[name="productInfo.isChargeFreight"]:checked').val()==2){
										 $("#freightPriceHidd").val($("#freightPrice").val());
									}
									form.submit();
					        	}
				        	}else{
				        		lalert("提醒","商品只能挂在最后一级分类上！");
				        		$("#submitButton").removeAttr("disabled");
				        	}
			        	}else{
			        		lalert("提醒","商品只能挂在最后一级分类上！");
			        		$("#submitButton").removeAttr("disabled");
			        	}
			        }
			    });
			});
			function validateSubmit(){
				var reg = /^[1-9]{1}\d*$/;//正整数正则
	         	var isRepeats = true;
	            var specifications = new Array();
	            var speciArray = new Array();
	            $("#specificationProductTable").find("tr:gt(0)").each(function(i) {
	                var specificationValues = $(this).find("select").serialize();
	                if(specificationValues!=""){
	                	var inputparam = $(this).find("input").serialize();
		                if ($.inArray(specificationValues, speciArray)>=0) {
		                    lalert("提醒", "商品规格重复!");
		                    isRepeats = false;
		                } else {
		                	speciArray.push(specificationValues);
		                	specifications.push(specificationValues +"&"+ inputparam);
		                }
	                }
	            });
	            if (isRepeats==true) {
	                $("#parameters").val(specifications);
	            }
	            var address=$("#deliveryAddressCities").val();
	            var marketPrice=$("#marketPrice").val();
	            var salesPrice=$("#salesPrice").val();
	        	if(address==""){
	        		 lalert("提醒","基本信息，发货地必填!");
	        		 isRepeats = false;
	        	 }else if(parseInt(marketPrice)<parseInt(salesPrice)){
	        		 lalert("提醒","基本信息，售价应小于等于市场价!");
	        		 isRepeats = false;
	        	 }else if($("#marketPrice").val()==""){
	        		 lalert("提醒","基本信息，市场价格必填!");
	        		 isRepeats = false;
	        	 }else if($("#productName").val()==""){
	        		 lalert("提醒","基本信息，商品名称必填!");
	        		 isRepeats = false;
	        	 }else if($("#brandId").val()==""){
	        		 lalert("提醒","基本信息，品牌必选!");
	        		 isRepeats = false;
	        	 }else if($("#shopProCategoryId").val()==""){
	        		 lalert("提醒","基本信息，店铺商品分类必选!");
	        		 isRepeats = false;
	        	 }else if($("#salesPrice").val()==""){
	        		 lalert("提醒","基本信息，销售价格必填!");
	        		 isRepeats = false;
	        	 }else if($("#storeNumber").val()==""){
	        		 lalert("提醒","基本信息，库存必填!");
	        		 isRepeats = false;
	        	 }else if($("#otherUploadImgsId").val()==""){
	        		 lalert("提醒","基本信息，商品Logo必填!");
	        		 isRepeats = false;
	        	 }else if(!reg.test($("#stockUpDate").val())){
	        		 lalert("提醒","基本信息，预计出货日必须为第一位大于0的正整数!");
	        		 isRepeats = false;
	        	 }else if($("#productCode").val()==""){
	        		 lalert("提醒","基本信息，商品编号必填!");
	        		 isRepeats = false;
	        	 }else if($("#barCode").val()==""){
	        		 lalert("提醒","基本信息，条形码必填!");
	        		 isRepeats = false;
	        	 }else if($("#measuringUnitName").val()==""){
	        		 lalert("提醒","基本信息，计量单位必填!");
	        		 isRepeats = false;
	        	 }else if($("#weight").val()==""){
	        		 lalert("提醒","基本信息，商品重量必填!");
	        		 isRepeats = false;
	        	 }else if($("#packingSpecifications").val()==""){
	        		 lalert("提醒","基本信息，包装规格必填!");
	        		 isRepeats = false;
	        	 }else if($("#manufacturerModel").val()==""){
	        		 lalert("提醒","基本信息，制造商型号必填!");
	        		 isRepeats = false;
	        	 }else if($("#giveAwayCoinNumber").val()==""){
	        		 lalert("提醒","基本信息，赠送购买者商城币必填!");
	        		 isRepeats = false;
	        	 }
	            return isRepeats;
		   }
			//更改邮费方式
			function isChargeFreight(value) {
			    if (value == "1") {
			        $("#freightPrice").closest("tr").css("display", "none");
			        $("#freightPriceHidd").val("0");
			    } else {
			        $("#freightPrice").closest("tr").css("display", "");
			        $("#freightPriceHidd").val($("#freightPrice").val());
			    }
			}
		
			//图片的操作
			$(function() {
			    var $addProductImage = $("#addProductImage");
			    var $deleteProductImage = $("a.deleteProductImage");
			    var $productImageTable = $("#productImageTable");
			    var $editImageBtn = $("a.editImageBtn");
			    // 增加商品图片
			    $addProductImage.click(function() {
			    	var Index = $(".imgs").length;
	        		var trHtml = '<tr class="imgs"> '+'<input type="hidden" name="listProductImage[' + Index + '].source" id="source' + Index + '" value=""/>';
			    	trHtml+='<input type="hidden" name="listProductImage[' + Index + '].large" id="large' + Index + '" value="' + Index + '"/>';
			    	trHtml+='<input type="hidden" name="listProductImage[' + Index + '].medium" id="medium' + Index + '" value="' + Index + '"/>';
			    	trHtml+='<input type="hidden" name="listProductImage[' + Index + '].thumbnail" id="thumbnail' + Index + '" value="' + Index + '"/>';
			    	trHtml+='<td><img style="width: 60px;height: 40px;" id="member-img' + Index + '" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png" class="accountFigure"  /><div class="tip-tx"><a href="javascript:;" class="editImageBtn" id="' + Index + '">上传图片</a></div><\/td>';
			    	trHtml+='<td> <input type="text" name="listProductImage[' + Index + '].title" class="text" maxlength="200" \/> <\/td> ';
			    	trHtml+='<td> <input type="text" name="listProductImage[' + Index + '].orders" class="text productImageOrder" maxlength="9" style="width: 50px;" \/> <\/td>';
			    	trHtml+=' <td> <a href="javascript:;" class="deleteProductImage">[删除]<\/a> <\/td> <\/tr>';
		        	$productImageTable.append(trHtml);
			    });
			
			    // 删除商品图片
			    $deleteProductImage.live("click",
			    function() {
			        var $this = $(this);
			        $this.closest("tr").remove();
			    });
			    
			  //tools参数设置
				$('.modal').overlay({
					mask: {
				        color: '#ebecff',
				        loadSpeed: 200,
				        opacity: 0.9//模糊度0.1-0.9
			        }
				});
				//图像上传
				$editImageBtn.live("click",
						function(){
					$("#imageNum").val(this.id);
					$("#controlButton").removeAttr("disabled");
					$("#tx-file").val("");
					$('#upload-tx-overlay').overlay().load();
				});
				
				//上传
				$('#upload-tx-form').submit(function(){
					$("#controlButton").attr("disabled","disabled");
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
					            		$('#tx-error').html('文件扩展名不是PNG或JPG!');
					            		$("#controlButton").removeAttr("disabled");
					            	}else{
					            		var imageNum = $("#imageNum").val();
					            		var productImg = data.photoUrl;
					            		$('#member-img'+imageNum).attr('src',data.visitFileUploadRoot+productImg.source);
					            		$('#member-img'+imageNum).attr('alt',data.visitFileUploadRoot+productImg.source);
					            		$('#upload-tx-overlay').overlay().close();
					            		$('#source'+imageNum).val(productImg.source);
					            		$('#large'+imageNum).val(productImg.large);
					            		$('#medium'+imageNum).val(productImg.medium);
					            		$('#thumbnail'+imageNum).val(productImg.thumbnail);
					            	}
					            }
        				};
						$(this).ajaxSubmit(ropts);
					}else{
						$("#controlButton").removeAttr("disabled");
						$('#tx-error').html('文件扩展名不是PNG或JPG!');
					}
					return false;
				});
			});
		//根据分类ID查询参数、属性、规格
			function getOtherInfoById(v){
				$("#productTypeId").val(v);
				if(v!="0"){
					$("#productType-span-4").attr("flag","true");
					$.ajax({
					   type: "POST", 
					   dataType: "JSON",
					   url: "${pageContext.request.contextPath}/front/store/frontProduct/selectOtherInfoByProductTypeId.action",
					   data: {selectProductTypeId:v},
					   success: function(data){
						   if(data!=null){
								    //品牌start
								   var s="";
								   if(data.brandList!=null){
									   s = "<select id='brandId' name='productInfo.brandId' class='{validate:{required:true}}'>";
									   s+= "<option  value=''>";
									   s+="请选择"; 				  		
									   s+="</option>";
									   for(var i=0;i<data.brandList.length;i++){
										   s+= "<option  value='"+data.brandList[i].brandId+"'>";
										   s+=data.brandList[i].brandName; 				  		
										   s+="</option>";	  	
									   }
									   s+="</select>";
									   //计量单位名称的选择
									   var m="";
									   m = "<select id='measuringUnitName' name='productInfo.measuringUnitName' class='{validate:{required:true}}' onchange='assign(this.value);'>";
									   m+= "<option  value=''>";
									   m+="请选择";
									   m+="</option>";
									   for(var i=0;i<data.measuringUnitList.length;i++){
										   m+= "<option  value='"+data.measuringUnitList[i].name+"'>";
										   m+=data.measuringUnitList[i].name; 				  		
										   m+="</option>";	  	
									   }
									   m+="</select>";
								   }
								  // alert(s);
								   $("#brandTd").html(s);
								   $("#measuringUnit").html(m);
								 //分类的属性begin
								var jaListProductAttr = data.listProductAttr;
								var attributeValueList = data.attributeValueList;
							    	$("#totalProdAttr").html("");
							    if (jaListProductAttr !=""&&jaListProductAttr!=null) {
							        var listProductAttr = eval(jaListProductAttr); //分类属性的集合
							        if (listProductAttr.length > 0) {
							            var htmlAttrStr = "<tr><strong>商品属性</strong></tr>";
							            for (var i = 0; i < listProductAttr.length; i++) {
							                if(attributeValueList!=null&&attributeValueList.length>0){
							                	var tmp="";//作为临时组品的数据
						                	for (var j = 0; j < attributeValueList.length; j++) {
								                	if(listProductAttr[i].productAttrId==attributeValueList[j].productAttrId){
								                		tmp += "<option style='width:80px ' value='" + attributeValueList[j].attrValueId + "'>" + attributeValueList[j].attrValueName + "</option>";
								                	}
						                	    }
							                	//如果tmp不为空 则添加对应的属性及属性值数据
						                	if(tmp!=""){
									                htmlAttrStr += "<tr>";
									                htmlAttrStr += "<th style='text-align: right;width: 80px'>" + listProductAttr[i].name + ":</th><input type='hidden' name='listProdAttr[" + i + "].name'  value='" + listProductAttr[i].productAttrId + "'/><td><select id='" + listProductAttr[i].name + "' name='listProdAttr[" + i + "].value'>"
									                htmlAttrStr+=tmp;
									                htmlAttrStr += " </select></td></tr>";
							                	}
							                }
							            }
							            $("#totalProdAttr").html(htmlAttrStr);
							        }
							    }else{
							    	$("#totalProdAttr").html("");
							    }var jaListProductAttr = data.listProductAttr;
									var attributeValueList = data.attributeValueList;
							    	$("#totalProdAttr").html("");
								    if (jaListProductAttr !=""&&jaListProductAttr!=null) {
								        var listProductAttr = eval(jaListProductAttr); //分类属性的集合
								        if (listProductAttr.length > 0) {
								            var htmlAttrStr = "<tr><strong>商品属性</strong></tr>";
								            for (var i = 0; i < listProductAttr.length; i++) {
								                if(attributeValueList!=null&&attributeValueList.length>0){
								                	var tmp="";//作为临时组品的数据
							                	for (var j = 0; j < attributeValueList.length; j++) {
									                	if(listProductAttr[i].productAttrId==attributeValueList[j].productAttrId){
									                		tmp += "<option style='width:80px ' value='" + attributeValueList[j].attrValueId + "'>" + attributeValueList[j].attrValueName + "</option>";
									                	}
							                	    }
								                	//如果tmp不为空 则添加对应的属性及属性值数据
							                	if(tmp!=""){
										                htmlAttrStr += "<tr>";
										                htmlAttrStr += "<th style='text-align: right;width: 80px'>" + listProductAttr[i].name + ":</th><input type='hidden' name='listProdAttr[" + i + "].name'  value='" + listProductAttr[i].productAttrId + "'/><td><select id='" + listProductAttr[i].name + "' name='listProdAttr[" + i + "].value'>"
										                htmlAttrStr+=tmp;
										                htmlAttrStr += " </select></td></tr>";
								                	}
								                }
								            }
								            $("#totalProdAttr").html(htmlAttrStr);
								        }
								    }else{
								    	$("#totalProdAttr").html("");
								    }
							    //分类的属性end 
							    //分类的参数begin
							    var productPara = data.productPara;
							    if (productPara != "" && productPara!=null) {
							    	 $("#totalProdPara").empty();
							        //var productPara = data.productPara;//分类的参数集合
							        var htmlParaStr = "";
							        var infoObj = eval(productPara.info);
							        var h = 0;
							        var trHtml = "";
							        for (var i = 0; i < infoObj.length; i++) {//参数组循环
							            trHtml += "<table class='paramsTable' id='parameterTable" + i + "'  width='100%'><tr>";
							            trHtml += "<td><strong>" + infoObj[i].name + "</strong><input type='hidden' name='listParamGroup[" + i + "].name' value='" + infoObj[i].name + "'/><input type='hidden'  name='listParamGroup[" + i + "].paramGroupId' value='" + infoObj[i].paramGroupId + "'/></td>";
							            trHtml += "<td><input type='hidden'  name='listParamGroup[" + i + "].order' value='" + infoObj[i].order + "' style='width: 50px;'/></td>";
							            trHtml += "</tr>";
							            trHtml += "<tr><td><table id='parameterInfoTable" + i + "' width='100%'>";
							            var a = eval(infoObj[i].paramGroupInfo);
							            for (var u = 0; u < a.length; u++) {//参数循环
							                trHtml += "<tr>";
							                trHtml += "<th style='text-align: right;width: 80px;'> " + a[u].name + ":</th><input type='hidden'  name='listParamGroupInfo[" + h + "].name' value='" + a[u].name + "'/> <input type='hidden'  name='listParamGroupInfo[" + h + "].pgiId' value='" + a[u].pgiId + "'/> ";
							                trHtml += "<td><input type='hidden' name='listParamGroupInfo[" + h + "].order' value='" + a[u].order + "'/><input type='hidden' name='listParamGroupInfo[" + h + "].pgInfoId' value='" + a[u].pgInfoId + "'/> </td>";
							                trHtml += "<td><input type='text' name='listParamGroupInfo[" + h + "].value' value='" + a[u].value + "'/> </td>";
							                trHtml += "</tr>";
							                h++;
							            }
							            trHtml += "</table></td></tr>";
							            trHtml += "</table>";
							        }
							        $("#totalProdPara").append(trHtml);
							    }else{
							    	$("#totalProdPara").html("");
							    }
							    //分类的参数end
							    
							    //分类的规格begin
							    var specificationList = data.specificationList;
							    if(specificationList!=""&&specificationList!=null){
								    var specificationHtmlStr="";
			    					for(var i=0;i<specificationList.length;i++){
			    						var specification=specificationList[i];
			    						var specification_param=specification.specificationId+"_"+specification.name+"_"+specification.notes;
			    						specificationHtmlStr+="<div style='float:left;margin:5px 10px;'>";
			    						specificationHtmlStr+="<span style='display:block;'>";
			    						specificationHtmlStr+="<input onclick='showSelectedSpecification("+specification.specificationId+")' id='"+specification.specificationId+"' class='check' name='specificationIds' type='checkbox' value='"+specification_param+"'/>";
			    						specificationHtmlStr+="&nbsp;<label for='"+specification.specificationId+"'>"+specification.name;
			    						specificationHtmlStr+="[&nbsp;"+specification.notes+"&nbsp;]</label></span></div>";
			    					}
								    $("#specificationSelect").html(specificationHtmlStr);
							    }else{
							    	$("#specificationSelect").html("");
							    }
							    //分类的规格end
	
						   }
					   }
					});
				}else{
					//清除品牌、规格、参数等信息
					$("#brandTd").html("");
					$("#measuringUnit").html("");
					$("#totalProdAttr").html("");
					$("#totalProdPara").html("");
					$("#specificationSelect").html("");
				    $("#productType-span-4").attr("flag","false");
				}
			}
			//显示选中的规格
			var count_specificationValues_id_index=0;
			function showSelectedSpecification(specificationId){
				var isChecked=$("#"+specificationId).attr("checked");
				if(isChecked!="checked"){
					$(".specificationValue_"+specificationId).hide();
				}else{
					$(".specificationValue_"+specificationId).show();
				}
				
// 				var check_objs=$("#specificationSelect input:checked");
// 				if(check_objs.length<3){
					var selectedSpecificationValuesHtmlStr="";
					var specificationProductHtmlStr="";
					$("#specificationProductTable-title").css("display","");
					var isOwnChecked=false;
					$("#specificationSelect input:checked").each(function(i){
						isOwnChecked=true;
						var specificationValueStringArray=this.value.split("_");
						var specificationId=specificationValueStringArray[0];
						var name=specificationValueStringArray[1];
						var notes=specificationValueStringArray[2];
						specificationProductHtmlStr+="<th style='padding:5px 10px;' id='specification_"+specificationId+"'>";
						specificationProductHtmlStr+="<span  style='margin:6px; display:inline;  white-space:nowrap;'>"+name+"</span>";
						specificationProductHtmlStr+="<br><span  style=''>[&nbsp;"+notes+"&nbsp;]</span></th>";
						selectedSpecificationValuesHtmlStr+=showSelectedSpecificationValue(specificationId); 
					});
					specificationProductHtmlStr+="<td></td>";
					$("#specificationProductTable-title").html(specificationProductHtmlStr);
// 				}else if(check_objs.length>2){
// 					$("#"+specificationId).attr("checked",false);
// 					lalert("提醒", "最多选择两个规格!");
// 				}
				
			}
			//显示规格对应的规格值
			function showSelectedSpecificationValue(specificationId){
				var selectedSpecificationValuesHtmlStr="";
				$.ajax({
					   type: "POST", 
					   dataType: "JSON",
					   url: "${pageContext.request.contextPath}/front/store/frontProduct/getSpecificationValueBySpecificationId.action",
					   async:false,
					   data: {specificationId: specificationId},
					   success: function(data){
						   specificationValueList=data;
						   selectedSpecificationValuesHtmlStr+="<td class='specificationValue_"+specificationId+"'style='width:auto; height:30px; padding:10px 20px; text-align:center;'>";
						   selectedSpecificationValuesHtmlStr+="<select class='disabled_"+specificationId+"' name='specification_"+specificationId+"'>";
						    for(var i=0;i<specificationValueList.length;i++){
						    	var specificationValue=specificationValueList[i];
						    	selectedSpecificationValuesHtmlStr+="<option value='"+specificationValue.specificationValueId+"'>"+specificationValue.name+"</option>";
						    }
						    selectedSpecificationValuesHtmlStr+="</select></td>";
					   }
				});
				return 	selectedSpecificationValuesHtmlStr;
			}
			
			//新增的规格tr计数器，便于动态生成存放规格的tr
			function addNewSpecificationValue(){
				var check_objs=$("#specificationSelect input:checked");
				if(check_objs.length>0){
					count_specificationValues_id_index++;
					var newTrHtmlStr="<tr id='selectedSpecificationValues_"+count_specificationValues_id_index+"'>";
					var selectedSpecificationValuesHtmlStr="";
					var isOwnChecked=false;
					$("#specificationSelect input:checked").each(function(i){
						isOwnChecked=true;
						var specificationValueStringArray=this.value.split("_");
						var specificationId=specificationValueStringArray[0];
						var name=specificationValueStringArray[1];
						var notes=specificationValueStringArray[2];
						selectedSpecificationValuesHtmlStr+=showSelectedSpecificationValue(specificationId); 
					});
					if(isOwnChecked){
						selectedSpecificationValuesHtmlStr+="<td class='delete_text' style='padding-left:10px; width:120px; white-space:nowrap;'><a href='javascript:deleteSpecification("+count_specificationValues_id_index+");' >[&nbsp;删除&nbsp;]</a></td>";
					}
					newTrHtmlStr+=selectedSpecificationValuesHtmlStr+"</tr>"
					$("#specificationProductTable").append(newTrHtmlStr);
					lockCheckBox();//锁住复选框
				}else{
					lalert("提醒", "至少选择一个规格!");
				}
			}
			
			// 删除规格商品
			function deleteSpecification(specificationValues_id_index){
				//清除用下拉框展示规格对应的规格值
				$("#selectedSpecificationValues_"+specificationValues_id_index).remove();
				if($("#specificationProductTable tr").length==2){
					unLockCheckBox();
				}
			}
			
			function lockCheckBox(){
				$("#specificationSelect input").each(function(i){
					$(this).attr("disabled","disabled");
				});
			}
			
			function unLockCheckBox(){
				$("#specificationSelect input").each(function(i){
					$(this).removeAttr("disabled");
				});
			}
			function checkOrder(obj){
				if(!/^\d+$/.test(obj.value)){
					obj.value="";
				}else if(obj.value.length>5){
					obj.value="";
				}
			}
			
			//商品分类数据追加
			function getNextLevelDate(level,value,l1,l2,l3){//leve为当前分类级别 value为当前分类ID l1,l2,l3分别为一级二级三级分类ID
				//清除品牌、规格、参数等信息
				$("#brandTd").html("");
				$("#measuringUnit").html("");
				$("#totalProdAttr").html("");
				$("#totalProdPara").html("");
				$("#specificationSelect").html("");
				$("#productType-span-4").attr("flag","false");
				var html="";
				var nextLevel=level+1;
				if(level=="1"){//点击一级分类时
					//清除去其他
					 $("#productType-span-2").html(html);
					 $("#productType-span-3").html(html);
					 $("#productType-span-4").html(html);
					 <s:iterator value="#application.categoryMap" var="type1">
					 	if(value==<s:property value="#type1.key.productTypeId"/>){
				  	 	   html+=" <select  id=\"productTypeId-2\" name=\"productInfo.categoryLevel2\" onchange=\"getNextLevelDate("+nextLevel+",this.value,"+l1+",this.value,"+l3+");\"><option value=\"0\">请选择</option>";
					  	 <s:iterator value="#type1.value" var="type2">
					  	 	if(value==<s:property value="#type2.key.parentId"/>){
					  	 		var id='<s:property value="#type2.key.productTypeId"/>';
					  	 		var name='<s:property value="#type2.key.sortName"/>';
					  	 	    html+="<option value=\""+id+"\" >"+name+"</option>";
					  	 	}
					  	 </s:iterator>
					  	  html+="</select>";
					 	}
				  	 </s:iterator>
				}else if(level=="2"){//点击二级分类时
					//清除去其他
					 $("#productType-span-3").html(html);
					 $("#productType-span-4").html(html);
				  	 $("#productType-span-4").attr("flag","false");
					<s:iterator value="#application.categoryMap" var="type1">
				 	if(l1 == <s:property value="#type1.key.productTypeId"/>){
				  	 <s:iterator value="#type1.value" var="type2">
				  	 	if(l1==<s:property value="#type2.key.parentId"/> && <s:property value="#type2.key.productTypeId"/> == l2 && <s:property value="#type2.value.size"/> >0){
					  	 	   html+=" <select id=\"productTypeId-3\" name=\"productInfo.categoryLevel3\" onchange=\"getNextLevelDateOrOtherInfo("+nextLevel+",this.value,"+l1+","+value+",this.value);\"><option value=\"0\">请选择</option>";
						  	 <s:iterator value="#type2.value" var="type3">
					  	 		var id='<s:property value="#type3.key.productTypeId"/>';
					  	 		var name='<s:property value="#type3.key.sortName"/>';
					  	 		var loadType='<s:property value="#type3.key.loadType"/>';
					  	 	    html+="<option loadType=\""+loadType+"\" value=\""+id+"\" >"+name+"</option>";
						  	 </s:iterator>
						  	  html+="</select>";
				  	 	}
				  	 </s:iterator>
				 	}
			  	 </s:iterator>
				}else if(level=="3"){//点击三级分类时
					//清除去其他
					 $("#productType-span-4").html(html);
					 $("#productType-span-4").attr("flag","false");
					<s:iterator value="#application.categoryMap" var="type1">
				 	if(l1 == <s:property value="#type1.key.productTypeId"/>){
				  	 <s:iterator value="#type1.value" var="type2">
				  	 	if(l1==<s:property value="#type2.key.parentId"/> && <s:property value="#type2.key.productTypeId"/> == l2){
						  	 <s:iterator value="#type2.value" var="type3">
							  	if(l2==<s:property value="#type3.key.parentId"/> && <s:property value="#type3.key.productTypeId"/> == l3 && <s:property value="#type3.value.size"/> >0){
							  	 	   html+=" <select id=\"productTypeId-4\" name=\"productInfo.categoryLevel4\" onchange=\"getOtherInfoById(this.value);\"><option value=\"0\">请选择</option>";
								  	 <s:iterator value="#type3.value" var="type4">
								  	 		var id='<s:property value="#type4.productTypeId"/>';
								  	 		var name='<s:property value="#type4.sortName"/>';
								  	 	    html+="<option value=\""+id+"\" >"+name+"</option>";
								  	 </s:iterator>
								  	  html+="</select>";
							  	}
						  	 </s:iterator>
				  	 	}
				  	 </s:iterator>
				 	}
			  	 </s:iterator>
					
				}
		  	    html+="</select>";
			  	$("#productType-span-"+nextLevel).html(html);
			}
			
			
			$(function(){
				$("#productTypeId-1").val("");
				var firstShopTradeTag='${firstShopTradeTag}';
				if(firstShopTradeTag!=null&&firstShopTradeTag!=""){
					$("#selected-shopTradeTag").html("");
					$("a.tanchuk_col").removeAttr("class");
					$("#ShopTradeTag-ul").find("a:first").attr("class","tanchuk_col");
					firstShopTradeTag=jQuery.parseJSON(firstShopTradeTag); 
					$("#shopSituationTag-ul").attr("ttId",firstShopTradeTag.ttId);
					//获取右侧的使用场合信息
					getShopSituationTagInfoList(firstShopTradeTag.ttId);
					$('input[type="checkbox"]:checked').attr("checked",false);
					$('input[name="selected-shopTradeTag-hidden"]').val("");
				}else{
					$("#selected-shopTradeTag").html("");
					$("a.tanchuk_col").removeAttr("class");
					$("#shopSituationTag-ul").removeAttr("ttId");
					$('input[type="checkbox"]:checked').attr("checked",false);
					$('input[name="selected-shopTradeTag-hidden"]').val("");
					 $("#shopSituationTag-ul").html("");
				}
			});
			
			function getNextLevelDateOrOtherInfo(level,value,l1,l2,l3){//leve为当前分类级别 value为当前分类ID l1,l2,l3分别为一级二级三级分类ID
				if($("#productType-span-3").find("option:selected").attr("loadType")=="1"){
					getOtherInfoById($("#productTypeId-3").val());
				}else{
					getNextLevelDate(level,value,l1,l2,l3);
				}
			}
			function assign(value){
				$("#measuringUnitName1").html("/"+value);
			}
			//更改地址level等级
			function chengeArea(id,level,selectId){
				if(id==null||id==""){
					$("#deliveryAddressCities").html("<option value=''>--地级市--</option>");
					$("#deliveryAddressCities").val(null);
				}else{
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
			  						$("#deliveryAddressCities").html(htmlOption);
			  						$("#deliveryAddressCities").val(selectId);
			  					}
			  				}
			  			}
			 			});
				}
			}
		</script>
	</head>
	
	<body>
		<%@ include file="../include/header.jsp" %>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<form  action="${pageContext.request.contextPath}/front/store/frontProduct/saveOrUpdateFrontProduct.action" id="inputForm" method="post" enctype="multipart/form-data"  >
					<input id="parameters" type="hidden" name="parameters" value=""/>
					<input id="shopInfoId" type="hidden" name="productInfo.shopInfoId" value="<s:property value='shopInfo.shopInfoId'/>"/>
					<input id="productId" type="hidden" name="productInfo.productId" value="<s:property value='productInfo.productId'/>"/>
					<input id="productTypeAddress" type="hidden" name="productInfo.productTypeAddress" value="<s:property value='productInfo.productTypeAddress'/>"/>
					<input id="productTypeId" type="hidden" name="productInfo.productTypeId" value="<s:property value='productInfo.productTypeId'/>"/>
					<input id="createDate" type="hidden" name="productInfo.createDate" value="<s:property value='productInfo.createDate'/>"/>
					
					<div id="rightBox" class="float-right publicHidden">
						<section id="Marketpalce">
						<div class="MarketpalceTree">
							<ul class="FavouriteTab">
								<li class="first-child">
									<a href="javascript:void(0);" class="FontSize14 radius gradient">基本信息</a>
								</li>
								
								<li>
									<a href="javascript:void(0);" class="FontSize14 radius gradient">详情</a>
								</li>
								<li>
									<a href="javascript:void(0);" class="FontSize14 radius gradient" >属性</a>
								</li>
								<li>
									<a href="javascript:void(0);" class="FontSize14 radius gradient" >参数</a>
								</li>
								<li>
									<a href="javascript:void(0);" class="FontSize14 radius gradient" >标签</a>
								</li>
								<li>
									<a href="javascript:void(0);" class="FontSize14 radius gradient" >图片</a>
								</li>
								<li>
									<a href="javascript:void(0);" class="FontSize14 radius gradient">商品规格</a>
								</li>
							</ul>
							<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02">
								<div>
									<table width="961px;">
										<tr><th style="text-align: right">提醒：</th>
											<td class="strong">基本信息，详情，属性，参数为集体修改，图片和规格是单独修改，需要单独提交。</td>
										</tr>
										<tr><th style="text-align: right"><font color="red">重要提示：</font></th>
											<td ><font color="red">请将商品挂在最后一级分类上！</font></td>
										</tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;商品分类：</th>
											<td>
											   <select  id="productTypeId-1" name="productInfo.categoryLevel1" onchange="getNextLevelDate(1,this.value,this.value,null,null);">
									             <option value="" selected="selected">请选择</option>
												  <s:iterator value="#application.categoryMap" var="type1">
													  	<option value="<s:property value="#type1.key.productTypeId"/>"  >
													  		<s:property value="#type1.key.sortName"/>
													  	</option>
												  </s:iterator>
									           </select>
									           <span id="productType-span-2">
									           </span>
									           <span id="productType-span-3">
									           </span>
								          	   <span id="productType-span-4" flag="false">
								          	   </span>
											</td>
										</tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;商品名称：</th>
										<td><input  type="text" id="productName" name="productInfo.productName" value="<s:property value="productInfo.productName" />" class="{validate:{required:true,maxlength:[50]}}"  style="border:1px #cecece solid;"/></td></tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;商品品牌：</th><td id="brandTd"></td></tr>
										<tr><th style="text-align: right; width:200px;"><i class="ColorRed">*</i>&nbsp;店铺商品分类：</th>
											 <td>
											 	<select id="shopProCategoryId" name="shopProCategoryId">
										             <option value="">请选择</option>
													  <s:iterator  value="shopProCategoryList">
													  	<option  value="<s:property value="shopProCategoryId" />"<s:if test="shopProCategoryId==selectshopProCategoryId">selected="selected"</s:if> >
													  		<s:property value="shopProCategoryName"/>
													  	</option>
													  </s:iterator>
									           	</select>
									           </td>
										</tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;市场价格：</th><td><input id="marketPrice" style="border:1px #cecece solid;" type="text"  name="productInfo.marketPrice" value="<s:property value="productInfo.marketPrice" />" class="{validate:{required:true,number:true,max:99999999,min:0.1}}"/></td></tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;销售价格：</th><td><input id="salesPrice" style="border:1px #cecece solid;" type="text" name="productInfo.salesPrice" value="<s:property value="productInfo.salesPrice" />" class="{validate:{required:true,number:true,max:99999999,min:0.1}}"/></td></tr>
										<%-- <tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;结算价格：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.costPrice" value="<s:property value="productInfo.costPrice" />" class="{validate:{required:true,number:true,max:99999999,min:1}}"/></td></tr> --%>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;商品库存：</th><td><input  style="border:1px #cecece solid;" type="text" id="storeNumber" name="productInfo.storeNumber" value="<s:property value="productInfo.storeNumber" />" class="{validate:{required:true,number:true}}"/></td></tr>
 										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;预计出货日：</th>
											<td><input style="border:1px #cecece solid;" type="text" id="stockUpDate" class="{validate:{required:true,number:true,max:9999}}" name="productInfo.stockUpDate" value="<s:property value="productInfo.stockUpDate" />"/>&nbsp;&nbsp;&nbsp;天</td> 
 										</tr> 
 										<tr>
									 <th style="text-align: right; width:115px;"><i class="ColorRed">*</i>&nbsp;发货地：</th>
										 <td>
										 	<select id="deliveryAddressProvince" name="productInfo.deliveryAddressProvince" onchange="chengeArea(this.value,'1','')" class="{validate:{required:true}}">
									             <option value="">省</option>
												  <s:iterator value="firstAreaList" var="first">
													<option  value="<s:property value="#first.aid"/>" id="<s:property value="#first.aid"/>"><s:property value="#first.name"/></option>
												</s:iterator>
								           	</select>
								           	
								           	<select id="deliveryAddressCities" name="productInfo.deliveryAddressCities" class="{validate:{required:true}}">
									             <option value="">地级市</option>
								           	</select>
								           	
								           </td>
								           
									</tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;商品编号：</th><td><input  style="border:1px #cecece solid;" type="text" id="productCode" name="productInfo.productCode" value="<s:property value="productInfo.productCode" />" class="{validate:{required:true}}"  /></td></tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;条形码：</th><td><input  style="border:1px #cecece solid;" type="text" id="barCode" name="productInfo.barCode" value="" class="{validate:{required:true,number:true}}" /></td></tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;计量单位：</th><td id="measuringUnit"></td></tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;商品重量：</th><td><input  style="border:1px #cecece solid;" id="weight" type="text" name="productInfo.weight" value="<s:property value="productInfo.weight" />" class="{validate:{required:true,number:true}}"  />&nbsp;&nbsp;&nbsp;kg<span id="measuringUnitName1"></span></td></tr>
<!-- 										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;重量单位：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.weightUnit" value="<s:property value="productInfo.weightUnit" />"  class="{validate:{required:true}}"  /></td></tr> -->
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;包装规格：</th><td><input  style="border:1px #cecece solid;" type="text" id="packingSpecifications" name="productInfo.packingSpecifications" value="<s:property value="productInfo.packingSpecifications" />"  class="{validate:{required:true}}"  /></td></tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;制造商型号：</th><td><input  style="border:1px #cecece solid;" type="text" id="manufacturerModel" name="productInfo.manufacturerModel" value="<s:property value="productInfo.manufacturerModel" />" class="text,{validate:{required:true}}" maxlength="200"/>
										<input type="hidden" id="isChargeFreight" name="productInfo.isChargeFreight" value="1" />
										</td></tr>
<!-- 										<tr><th style="text-align: right">商品描述：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.describle" value="<s:property value="productInfo.describle" />" class="text" maxlength="100"/></td></tr> -->
<!-- 										<tr><th style="text-align: right">是否上架：</th><td><input type="radio" name="productInfo.isPutSale" value="1" <s:if test="productInfo.isPutSale==1">checked="checked"</s:if>/>&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;<input type="radio" name="productInfo.isPutSale" value="2" <s:if test="productInfo==null">checked="checked"</s:if> <s:if test="productInfo.isPutSale==2">checked="checked"</s:if>/>&nbsp;&nbsp;&nbsp;是</td></tr> -->
										<!-- <tr>
											<th style="text-align: right">是否包含运费：</th>
											<td>
												<input type="radio" name="productInfo.isChargeFreight" value="1" onclick="isChargeFreight(this.value);" <s:if test="productInfo==null">checked="checked"</s:if> <s:if test="productInfo.isChargeFreight==1">checked="checked"</s:if>/>
												&nbsp;&nbsp;&nbsp;免运费&nbsp;&nbsp;&nbsp;
												<input type="radio" name="productInfo.isChargeFreight" value="2" onclick="isChargeFreight(this.value);" <s:if test="productInfo.isChargeFreight==2">checked="checked"</s:if>/>
												&nbsp;&nbsp;&nbsp;填写运费
											</td>
										</tr> -->
										<tr style="display:none"><th style="text-align: right">商品运费：</th><td><input id="freightPrice" type="text" value="<s:property value="productInfo.freightPrice" />" class="{validate:{required:true,number:true,max:99999999,min:0}}"/></td></tr>
										<tr ><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;商品 Logo：</th>
										<td><input id="otherUploadImgsId" type="file" name="otherUploadImgs" value="" class="productImageFile {validate:{required:true}}"/></td><td><s:property value="name"/></td></tr>
										<tr><th style="text-align: right"><i class="ColorRed">*</i>&nbsp;赠送购买者商城币：</th><td><input  style="border:1px #cecece solid;" type="text" id="giveAwayCoinNumber" name="productInfo.giveAwayCoinNumber" class="{validate:{required:true}}" value="<s:property value="productInfo.giveAwayCoinNumber" />"/></td></tr>
										<tr><th style="text-align: right">商品备注：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.note" value="<s:property value="productInfo.note" />"/></td></tr>
<!-- 										<tr><th style="text-align: right">搜索 TAG：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.productTag" value="<s:property value="productInfo.productTag" />"/></td></tr> -->
										<tr><th style="text-align: right">SEO 标题：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.seoTitle" value="<s:property value="productInfo.seoTitle" />"/></td></tr>
										<tr><th style="text-align: right">SEO 关键字：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.seoKeyWord" value="<s:property value="productInfo.seoKeyWord" />"/></td></tr>
										<tr><th style="text-align: right">SEO 描述：</th><td><input  style="border:1px #cecece solid;" type="text" name="productInfo.seoDescription" value="<s:property value="productInfo.seoDescription" />"/></td></tr>
									</table>
										<input  style="border:1px #cecece solid;" type="hidden" name="productInfo.isPutSale" value="1"/>
										<input  style="border:1px #cecece solid;" type="hidden" id="freightPriceHidd" name="productInfo.freightPrice" value="0"/>
								</div>
								<!--商品详情 -->
								<div>
									<table width="750px;">
									 	<tr>
										      <td  align="left" colspan="3">&nbsp;&nbsp;
										      <input type="hidden" name="productInfo.functionDesc" id="KeFunctionDesc"/>
										       <textarea id="functionDesc" rows="8" style="width:690px;height:500px;visibility:hidden;" cols="48" name="" class="{validate:{maxlength:[700]}}"><s:property value="productInfo.functionDesc"/></textarea>
											   <script type="text/javascript">
										  			var editor;
													KindEditor.ready(function(K){
															editor = K.create('#functionDesc',{
															cssPath:'${fileUrlConfig.fileServiceUploadRoot}kindeditor/plugins/code/prettify.css',
															uploadJson:'${pageContext.request.contextPath}/ke/uploadJson.jsp?subMode=shop',
															fileManageJson:'${pageContext.request.contextPath}/ke/fileManagerJson.jsp?subMode=shop',
															allowFileManage:true,
															resizeType : 2,
											                  items : [
											                              'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'cut', 'copy', 'paste',
											                              'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
											                              'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
											                              'superscript', 'clearhtml', 'selectall', '|', 'fullscreen', '|',
											                              'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
											                              'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
											                              'table', 'hr', 'emoticons', 'pagebreak', 'link', 'unlink'
											                          ],
											                    filterMode : false
														});
														prettyPrint();
													});
											   </script>
										  </td>
									    </tr>
									</table>
								</div>
	        					<%--商品属性--%>						
								<div>
									<table id="totalProdAttr" width="100%"></table>
								</div>
								<%--商品参数	--%>
								<div>
									<table id="totalProdPara" width="100%"></table>
									
								</div>
								<%--商品标签--%>
								<div>
									<input id="topTages-hidden" type="hidden" name="tages" />
									<div class="con_main">
									    <div class="conmain_head" style="margin:0 auto;">
									      <div class="biaoti" style="width:auto;padding:0; height:41px;font-weight: bold;">已选适用专业：</div>
									      <div id="selected-shopTradeTag"></div>
									      <span id="tagesErrorMsg" style="color:red;"></span>
									    </div>
									    
									    <div class="conmain_syhy" style="width:530px;margin:0 auto;padding-top:15px;">
									      <div class="syhy_first">
									        <h2 style="background: none; padding:0 0 0 10px;font-weight: bold;">适用专业</h2>
									        <ul id="ShopTradeTag-ul">
									        	<s:if test="#request.shopTradeTagList.size>0">
									        		<s:iterator value="#request.shopTradeTagList" var="stt">
									        		<s:if test="#stt.tageName.length()>15">
											          <li><a title="<s:property value='#stt.tageName'/>" id="selected-shopTradeTag-a-<s:property value='#stt.ttId'/>" href="javascript:getShopSituationTagInfoList(<s:property value='#stt.ttId'/>);"><s:property value='#stt.tageName.substring(0,15)'/></a></li>
									        		</s:if>
									        		<s:else>
											          <li><a title="<s:property value='#stt.tageName'/>" id="selected-shopTradeTag-a-<s:property value='#stt.ttId'/>" href="javascript:getShopSituationTagInfoList(<s:property value='#stt.ttId'/>);"><s:property value='#stt.tageName'/></a></li>
									        		</s:else>
											          <input id="selected-shopTradeTag-hidden-<s:property value='#stt.ttId'/>" name="selected-shopTradeTag-hidden" type="hidden"/>
											          <input id="selected-shopTradeTag-name-hidden-<s:property value='#stt.ttId'/>" type="hidden" value="<s:property value='#stt.tageName'/>"/>
									        		</s:iterator>
									        	</s:if>
									        </ul>
									      </div>
									      
									      <div class="syhy_first">
									        <h2 style="background: none;padding:0 0 0 10px;font-weight: bold;">使用场合</h2>
									        <ul id="shopSituationTag-ul" ttId=""></ul>
									      </div>
									    </div>
									  </div>
									<script>
										//是否可以保存数据的标记  当已选适用专业标签个数不超过5的时候可以保存
										var isSave=true;
									
										//根据适用专业的ID（ttId）查询出已关联的适用专业标签信息
										function getShopSituationTagInfoList(ttId){
											var topTagCount=$("div.hangye_fenlei").length;//已选适用专业标签个数（对多允许选择五个）
											if(topTagCount<=5){
												isSave=true;
												$("#tagesErrorMsg").html("");
											}
											$.ajax({
												   type: "POST",dataType: "JSON",
												   url: "${pageContext.request.contextPath}/front/store/frontProduct/getShopSituationTagInfoList.action",
												   data: {ttId:ttId},
												   async : false,
												   success: function(data){
													   if(data){
														   var html="";
														   if(data.length>0){
															   for(var i=0;i<data.length;i++){
																   var tageName=data[i].tageName;
																   if(tageName.length>15){
																	   tageName=tageName.substring(0,15);
																   }
																   html+=" <li><input id=\"checkbox"+data[i].stId+"\" onclick=\"insertTopTages()\" name=\"checkbox-"+ttId+"\"  type=\"checkbox\" value=\""+data[i].stId+"\" />"+tageName+"</li>";
																}
														   }
														   $("#shopSituationTag-ul").html(html);
														   //将当前操作的适用专业ID标记到使用场合的ul节点上
														   $("#shopSituationTag-ul").attr("ttId",ttId);
														   //标记当前操作的适用专业
														   $("a.tanchuk_col").removeAttr("class");
														   $("#selected-shopTradeTag-a-"+$("#shopSituationTag-ul").attr("ttId")).attr("class","tanchuk_col");
														   //数据回显操作/////////
														   //获取隐藏域中的ID信息
														   var hiddenIdInfo=$("#selected-shopTradeTag-hidden-"+ttId).val();
														    if(hiddenIdInfo!=""){
														    	//转换成数组形式
														    	var hiddenIdInfoArray=new Array();
														    	hiddenIdInfoArray=hiddenIdInfo.split(",");
														    	for(var j=0;j<hiddenIdInfoArray.length;j++){
														    		var value=hiddenIdInfoArray[j];
														    		$("#checkbox"+value).attr("checked",true);//选中
														    	}
														    }
													   }
													   }
												});
											
										}
										
										//删除顶部标签
										function deleteTopTage(id){
											$("#"+id).remove(); ;//删除已选的适用专业
											var ttId=id.substring(id.lastIndexOf("-")+1,id.length);
											$("#selected-shopTradeTag-hidden-"+ttId).val("");
											$('input[name="checkbox-'+ttId+'"]:checked').attr("checked",false); //删除已选的适用专业
										}
										
										//添加顶部的 已选适用专业标签
										function insertTopTages(){
											//如果使用场合ul的自定义属性为空则不获取选中的checkbox信息
											if($("#shopSituationTag-ul").attr("ttId")!=""){
												//定义一个字符串 用来存储 使用场合ID
												var ids="";
												//定义一个字符串用来存储适用场合text文本内容
												var text="";
												//获取页面中选中的checkbox
												$('input[type="checkbox"]:checked').each(function(){
													var value=$(this).val();
													ids+=","+value;
													text+=","+$('label[for="checkbox'+value+'"]').text();
												});
												if(ids!=""){
													ids=ids.substring(1,ids.length);
													text=text.substring(1,text.length);
												}
												//获取到ids后 将其存储到相应的隐藏域中  适用专业对应的隐藏域ID为selected-shopTradeTag-hidden-x  :x为$("#shopSituationTag-ul").attr("ttId")的值
												$("#selected-shopTradeTag-hidden-"+$("#shopSituationTag-ul").attr("ttId")).val(ids);
												//定义已选适用专业标签div Id
												var id="selected-shopTradeTag-centon-div-"+$("#shopSituationTag-ul").attr("ttId");
												var flag=true;
												$("div.hangye_fenlei").each(function(){
													if($(this).attr("id")==id){
														flag=false;//false说明当前操作的“适用专业”在上方的标记中已经存在了
													}
												});
												if(flag&&ids!=""){
													//获取“适用专业”名称
													var name=$("#selected-shopTradeTag-name-hidden-"+$("#shopSituationTag-ul").attr("ttId")).val();
													//定义顶部已选择适用专业标签html内容
													var topHtml="<div id=\""+id+"\" class=\"hangye_fenlei\">"+name+"<input type=\"button\"  class=\"but_aniu\" onclick=\"deleteTopTage('"+id+"')\"/></div>";
												    $("#selected-shopTradeTag").append(topHtml);
												}else{
													if(ids==""){
														$("#"+id).remove();//删除已选的适用专业
													}
												}
											}
											var topTagCount=$("div.hangye_fenlei").length;//已选适用专业标签个数（对多允许选择五个）
											if(topTagCount<=5){
												isSave=true;
												$("#tagesErrorMsg").html("");
											}else{
												$("#tagesErrorMsg").html("适用专业标签个数不能大于5!");
												isSave=false;
												$("#selected-shopTradeTag-hidden-"+$("#shopSituationTag-ul").attr("ttId")).val("");//清空隐藏域中的值
												$("#selected-shopTradeTag-centon-div-"+$("#shopSituationTag-ul").attr("ttId")).remove();//删除已选的适用专业
											}
											
											//声明一个空的变量 用于存储适用专业与使用场合标签的关系 该数据以json的形式存放 方便后台java解析
											var jsonStr='';
											var html="";
											$("div.hangye_fenlei").each(function(){//循环已选择标签
												//获取适用专业标签id
												var id=$(this).attr("id");
												var ttId=id.substring(id.lastIndexOf("-")+1,id.length);
												jsonStr+=',{"ttId":"'+ttId+'","ids":"'+$("#selected-shopTradeTag-hidden-"+ttId).val()+'"}';
											});
											if(jsonStr!=""){
												jsonStr=jsonStr.substring(1,jsonStr.length);
												jsonStr='['+jsonStr+']';
											}
											$("#topTages-hidden").val(jsonStr);//将处理后的json数据存入隐藏域中 用于后台解析
										}
									</script>
								</div>
								<%--商品图片	--%>
								<div>
									<table id="productImageTable" width="750px;"   >
										<tr ><td colspan="1"><strong ><a id="addProductImage" style="color: blue" class="BackgroundF2Hui publicinline publicPadding5_10 publicBorder " href="javascript:;">添加滚动图片</a></strong></td></tr>
										<tr ><td>图片</td><td>标题</td><td>排序</td><td>操作</td></tr>
									</table>
	        					</div>	
								<!-- 商品规格 begin-->
							<div>
								<!-- 添加规格-->
							    <div style="margin-bottom:7px;">
					    			<span>
					    				<a href="javascript:addNewSpecificationValue()"  style="color:#000;font-weight:900;" href="javascript:void(0)"  class="publicPadding5_10 BackgroundF2Hui PublicBorder FontSize14">添加规格</a>
					    			</span>
					    		</div>
					    		<!-- 当前分类下的规格 begin-->
					    		<div id="specificationSelect"></div>
					    		<!-- 当前分类下的规格 end -->
					    		
					    		<!-- 规格值 begin-->
					    		<div style=" width:940px; height:auto; overflow-x:auto; overflow-y:hidden;  ">
					    		<table id="specificationProductTable" style="margin-top:15px;   width:100%; height:auto; ">
					    		    <!--选定规格值名称 -->
					    			<tr id="specificationProductTable-title"  style="background-color: #E8EBF0; border-bottom-style: solid;padding:5px;"></tr>
					                <!--选定规格值-->
									<tr id="selectedSpecificationValues_0" ></tr> 
					    		</table>
					    		</div>
<%--								<input type="hidden" id="productSpecificationValueList" value="productSpecificationValueList"/>--%>
		    					<!-- 规格值 end -->
		    		   		 </div>
		    		        <!-- 商品规格 end -->
		    		        
				</div>
				
				<div style="padding:20px 0;">
					<table class="margin-center">
						<tr>
							<td >
								<input id="submitButton"  class="submitImg BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px; border:0; text-align: center;  cursor: pointer; line-height:16px;"  type="submit" value="提交"></input>
								<input id="backButton" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" type="button" value="返回" onclick="window.history.back(-1); " style="border:0; text-align: center;  cursor: pointer; line-height:16px;"></input>
							<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
							</td>
						</tr>
					</table>
				</div>	
							
				<script>
					$(function() {
						$("ul.FavouriteTab").tabs("div.TabParent_lj > div", {
							initialIndex : 0
						}, {
							history : true
						});
					});
				</script>
			</div>
			</section>

		</div>
		</form>
				<!--//right-->
				<!-- 异步上传图片 -->
				<div class="modal" id="upload-tx-overlay">
					<form enctype="multipart/form-data" method="post" id="upload-tx-form">
						<h2 class="modal-title">
							上传图片
						</h2>
						<p class="modal-content">
							<input type="hidden" id="imageNum" value=""/>
							<p id="tx-error" class="ColorRed"></p>
							图片文件:
							<input type="file" name="imagePath" id="tx-file">
							<p>注意:为达到最佳效果,使用一个图像,是200像素宽,300像素高.</p>
							<p id="showMsg" style="display: none"> 正在提交中....</p>
						</p>
						<p class="modal-buttons">
							<button type="submit" id="controlButton" >提交</button>
							<button class="close" type="button">
								关闭
							</button>
						</p>
					</form>
				</div>
			</div>
		</div>
</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
