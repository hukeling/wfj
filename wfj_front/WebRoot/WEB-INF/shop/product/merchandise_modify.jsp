<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>编辑商品</title>
		<%@ include file="../include/head.jsp"%>
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
		<!-- 		选择适用专业标内容样式 -->
		<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/choseTagsAlert.css"/>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<style type="text/css">
			.specificationSelect {
				height: 100px;
				padding: 5px;
				overflow-y: scroll;
				border: 1px solid #cccccc;
			}
			.specificationSelect li {
				float: left;
				min-width: 150px;
				_width: 200px;
			}
			.hidden {
				display: none;
			}
			#specificationSelect{overflow:hidden;}
		</style>
		<script type="text/javascript">
			$(function() { //表单验证
			    $("#basicForm").validate({
			        meta: "validate",
			        submitHandler: function(form) {
			        	
			        	var reg = /^[1-9]{1}\d*$/;//正整数正则
			        	 if($("#productTypeId").val()==""){
			        		 lalert("提醒","商品分类必选!");
			        		 return false;
			        	 }else if($("#marketPrice").val()==""){
			        		 lalert("提醒","市场价格必填!");
			        		 return false;
			        	 }else if($("#productName").val()==""){
			        		 lalert("提醒","商品名称必填!");
			        		 return false;
			        	 }else if($("#brandId").val()==""){
			        		 lalert("提醒","品牌必选!");
			        		 return false;
			        	 }else if($("#shopProCategoryId").val()==""){
			        		 lalert("提醒","店铺内部分类必选!");
			        		 return false;
			        	 }else if($("#salesPrice").val()==""){
			        		 lalert("提醒","销售价格必填!");
			        		 return false;
			        	 }else if($("#storeNumber").val()==""){
			        		 lalert("提醒","库存必填!");
			        		 return false;
			        	 }else if(!reg.test($("#stockUpDate").val())){
			        		 lalert("提醒","基本信息，预计出货日必须为第一位大于0的正整数!");
			        		 return  false;
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
			        	 $("#KEFunctionDesc").val(editor.html());
			        	//处理运费
						if($('input[name="productInfo.isChargeFreight"]:checked').val()==2){
							 $("#freightPriceHidd").val($("#freightPrice").val());
						}
			        	//控制保存按钮变灰，避免重复提交
						$(".submitImg").attr("disabled","disabled");
						$("#brandId").removeAttr("disabled");
						$(".submitImgLoad").attr("style","display:block;");
			        	form.submit();
			        	return false;
			        }
			    });
			
				var s = '${productInfo.isChargeFreight}';
				if(s==2){
					$("#yunfei").show();
				}
				var m = '${productInfo.measuringUnitName}';
				$("#measuringUnitName1").html("/"+m);
			});
			
			$(function(){
				//定义变量
				var html1="";
	   			var html2="";
	   			var html3="";
	   			var html4="";
   				
   				//变量赋值
   				//1
   				<s:iterator value="#application.categoryMap" var="type1">
   					if(<s:property value="#type1.key.productTypeId"/> == $("#categoryLevel1").val()){
   						var name='<s:property value="#type1.key.sortName"/>';
   						html1="<select disabled=\"disabled\" ><option>"+name+"</option></select>";
   					}
			    </s:iterator>
			    //2
			    <s:iterator value="#application.categoryMap" var="type1">
						if(<s:property value="#type1.key.productTypeId"/> == $("#categoryLevel1").val()){
							<s:iterator value="#type1.value" var="type2">
   							if(<s:property value="#type2.key.productTypeId"/> == $("#categoryLevel2").val()){
		   						var name='<s:property value="#type2.key.sortName"/>';
		   						html2="<select disabled=\"disabled\" ><option>"+name+"</option></select>";
   							}
							</s:iterator>
   					}
			    </s:iterator>
			    //3
			    <s:iterator value="#application.categoryMap" var="type1">
						if(<s:property value="#type1.key.productTypeId"/> == $("#categoryLevel1").val()){
							<s:iterator value="#type1.value" var="type2">
   							if(<s:property value="#type2.key.productTypeId"/> == $("#categoryLevel2").val()){
   								<s:iterator value="#type2.value" var="type3">
		   							if(<s:property value="#type3.key.productTypeId"/> == $("#categoryLevel3").val()){
				   						var name='<s:property value="#type3.key.sortName"/>';
				   						html3="<select disabled=\"disabled\" ><option>"+name+"</option></select>";
		   							}
   								</s:iterator>
   							}
							</s:iterator>
   					}
			    </s:iterator>
			    //4
			    if($("#categoryLevel4").val()!=null&&$("#categoryLevel4").val()!=""){
			    <s:iterator value="#application.categoryMap" var="type1">
						if(<s:property value="#type1.key.productTypeId"/> == $("#categoryLevel1").val()){
							<s:iterator value="#type1.value" var="type2">
   							if(<s:property value="#type2.key.productTypeId"/> == $("#categoryLevel2").val()){
   								<s:iterator value="#type2.value" var="type3">
		   							if(<s:property value="#type3.key.productTypeId"/> == $("#categoryLevel3").val()){
		   								<s:iterator value="#type3.value" var="type4">
				   							if(<s:property value="#type4.productTypeId"/> == $("#categoryLevel4").val()){
						   						var name='<s:property value="#type4.sortName"/>';
						   						html4="<select disabled=\"disabled\" ><option>"+name+"</option></select>";
				   							}
		   								</s:iterator>
		   							}
   								</s:iterator>
   							}
							</s:iterator>
   					}
			    </s:iterator>
			    }
			    //将html文本显示在页面上
   				$("#productType-span-1").html(html1);
   				$("#productType-span-2").html(html2);
   				$("#productType-span-3").html(html3);
			    $("#productType-span-4").html(html4);
   			});
			
			
			//更改邮费方式
			function isChargeFreight(value) {
			    if (value == "1") {
			        $("#freightPrice").closest("tr").css("display", "none");
			    } else {
			        $("#freightPrice").closest("tr").css("display", "");
			    }
			}
			//参数属性初始化
			$(function() {
			    //分类的属性start
								var jaListProductAttr = $("#listProductAttr").val();
							    var jattributeValueList = $("#attributeValueList").val();
							    var jpaiList = $("#paiList").val();
						    	$("#totalProdAttr").html("");
						    	if (jaListProductAttr != "") {
							        var listProductAttr = eval(jaListProductAttr); //分类属性的集合
							        if (listProductAttr.length > 0) {
							            var htmlAttrStr = "<tr><strong>属性</strong></tr>";
							            for (var i = 0; i < listProductAttr.length; i++) {
							    			var attributeValueList = eval(jattributeValueList);
							                var ids="";
						                	var paiList = eval(jpaiList);
					                    	for(var n = 0; n < paiList.length; n++){
					                    		ids+=","+paiList[n].attrValueId;
					                    	}
					                    	if(ids!=""){
					                    		ids+=",";
					                    	}
					                    	var tmp="";//作为临时组品的数据
							           for (var j = 0; j < attributeValueList.length; j++) {
							               		if(listProductAttr[i].productAttrId==attributeValueList[j].productAttrId){
							                    		if(ids.indexOf(","+attributeValueList[j].attrValueId+",")>=0){
							                    			tmp += "<option style='width:80px ' value='" + attributeValueList[j].attrValueId + "' selected='selected'>" + attributeValueList[j].attrValueName + "</option>";
							                    		}else{
							                    			tmp += "<option style='width:80px ' value='" + attributeValueList[j].attrValueId + "'>" + attributeValueList[j].attrValueName + "</option>";
							                    		}
							                    }
							                }
							        		 //如果tmp不为空 则添加对应的属性及属性值数据
					                	if(tmp!=""){
								                htmlAttrStr += "<tr>";
								                htmlAttrStr += "<th style='text-align: right;width: 80px'>" + listProductAttr[i].name + ":</th><input type='hidden' name='listProdAttr[" + i + "].name'  value='" + listProductAttr[i].productAttrId + "'/><td><select id='" + listProductAttr[i].name + "' name='listProdAttr[" + i + "].value'>";
								                htmlAttrStr+=tmp;
								                htmlAttrStr += " </select></td></tr>";
					                		}
							            }
							            $("#totalProdAttr").html(htmlAttrStr);
							        }
							    }
							    var productAttr = $("#productAttr").val(); //所选择的属性
							    if (productAttr != "") {
							        productAttr = eval(productAttr);
							        for (var i = 0; i < productAttr.length; i++) {
							            $("#" + productAttr[i].name).val(productAttr[i].value);
							        }
							    }
			    //分类的属性end 
			    //分类的参数start
			  
			    //分类的参数end
			    var ppINfo = $("#ppINfo").val();
			    if (ppINfo != "") {
// 			        var productPara = data.productPara;//分类的参数集合
			        var htmlParaStr = '';
			        var infoObj = eval(ppINfo);
			        var trHtml = '';
			        var h = 0;
			        var trHtml = '<table align="center"  class="addOrEditTable" width="900px;"><\/tr> ';
			        for (var i = 0; i < infoObj.length; i++) {
			            trHtml += '<table id="parameterTable' + i + '" class="parameterTable"  frame="below" width="100%"> ';
			            trHtml += '<tr  class="parameterTr" >' + '<strong>' + infoObj[i].name + '</strong> <input type="hidden" name="listParamGroup[' + i + '].name" value="' + infoObj[i].name + '" \/><input type="hidden"  name="listParamGroup[' + i + '].paramGroupId" value="' + infoObj[i].paramGroupId + '"\/>  ';
			            trHtml += '<td> <input type="hidden"  name="listParamGroup[' + i + '].order " value="' + infoObj[i].order + '" style="width: 50px;" \/> <\/td> ';
			            trHtml += ' <\/td><\/tr>';
			            trHtml += '<tr > <table id="parameterInfoTable' + i + '" class="parameterInfoTable" frame="below" width="100%">';
			            var a = eval(infoObj[i].paramGroupInfo);
			
			            for (var u = 0; u < a.length; u++) {
			                trHtml += '<tr class="parameterInfoTr" >';
			                trHtml += ' <th style="text-align: right;width: 80px"> ' + a[u].name + ':<\/th><input type="hidden"  name="listParamGroupInfo[' + h + '].name" value="' + a[u].name + '"\/> <input type="hidden" id="pgiId0" name="listParamGroupInfo[' + h + '].pgiId" value="' + a[u].pgiId + '"\/> ';
			                trHtml += ' <td> <input type="hidden" name="listParamGroupInfo[' + h + '].order" value="' + a[u].order + '"   \/><input type="hidden" name="listParamGroupInfo[' + h + '].pgInfoId" value="' + a[u].pgInfoId + '"/> <\/td>';
			                trHtml += ' <td> <input type="text" id="'+a[u].pgInfoId+'" name="listParamGroupInfo[' + h + '].value" value="' + a[u].value + '"  \/> <\/td>';
			                trHtml += '<\/tr>';
			                h++;
			            }
			            trHtml += '<\/table><\/tr>';
			            trHtml += '<\/table>';
			        }
			        trHtml += '<\/table>';
			        $("#totalProdPara").append(trHtml);
			
			    }
			
			    //分类中参数展示end
			    //商品中参数赋值
			    var productPara = $("#productPara").val();
			    if (productPara != "") {
			        //var productPara = data.productPara;//分类的参数集合
			        var infoObj = eval(productPara);
			        var h = 0;
			        for (var i = 0; i < infoObj.length; i++) {
			            var a = eval(infoObj[i].paramGroupInfo);
			            for (var u = 0; u < a.length; u++) {
			               $("#"+a[u].pgInfoId).val(a[u].value);
			            }
			        }
			    }
			});
			
			//图片动态添加和删除
			$(function() {
			    var $inputForm = $("#inputForm");
			    var $addProductImage = $("#addProductImage");
			    var $deleteProductImage = $("a.deleteProductImage");
			    var $productImageTable = $("#productImageTable");
			    var $editImageBtn = $("a.editImageBtn");
			    // 增加商品图片
			    $addProductImage.click(function() {
			    	var Index = $(".imgs").length;
			    	var trHtml = '<tr class="imgs"> ';
			    	trHtml+='<td>';
			    	trHtml+='<input type="hidden" name="listProductImage[' + Index + '].source" id="source' + Index + '" value=""\/>';
			    	trHtml+='<input type="hidden" name="listProductImage[' + Index + '].large" id="large' + Index + '" value="' + Index + '"\/>';
			    	trHtml+='<input type="hidden" name="listProductImage[' + Index + '].medium" id="medium' + Index + '" value="' + Index + '"\/>';
			    	trHtml+='<input type="hidden" name="listProductImage[' + Index + '].thumbnail" id="thumbnail' + Index + '" value="' + Index + '"\/>';
			    	trHtml+='<img style="width: 60px;height: 40px;" id="member-img' + Index + '" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png" class="accountFigure"  \/><div class="tip-tx"><a href="javascript:;" class="editImageBtn" id="img_' + Index + '">添加图片<\/a></div><\/td>';
			    	trHtml+='<td> <input type="text" name="listProductImage[' + Index + '].title" class="text" maxlength="200" \/> <\/td> ';
			    	trHtml+='<td> <input type="text" name="listProductImage[' + Index + '].orders" class="text productImageOrder" maxlength="9" style="width: 50px;" \/> <\/td>';
			    	trHtml+=' <td> <a href="javascript:;" class="deleteProductImage">[删除]<\/a> <\/td> <\/tr>';
			        $productImageTable.append(trHtml);
			    });
			    // 删除商品图片
			    $deleteProductImage.live("click",
			    function() {
			    	var $this = $(this);
			    	var productImageId = this.id;
			    	if(productImageId!=""){
			    		lconfirm("提醒","确认删除",function(o){
			    			var url='${pageContext.request.contextPath}/front/store/frontProduct/deleteProductImg.action';
			    			  $.post(url,{"productImageId":productImageId},function(data){
			    					if(data){
			    						$this.closest("tr").remove();	
			    				    }
			    			},"json");
			    			$("#confirm-overlay").overlay().close();//关闭浮层
			    		});
			    	}else{
			    		$this.closest("tr").remove();	
			    	}
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
				$editImageBtn.live("click",function(){
					var id= this.id;
					var imageNum = id.split("_");
					$("#controlButton").removeAttr("disabled");
					$("#imageNum").val(imageNum[1]);
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
					            		$("#controlButton").removeAttr("disabled");
					            		//member-img
					            		$('#tx-error').html('文件扩展名不是PNG或JPG!');
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
						$('#tx-error').html('文件扩展名不是PNG或JPG!');
						$("#controlButton").removeAttr("disabled");
					}
					return false;
				});
			});
		
			//重新访问店铺商品
			var gotoShopProductListPage=function(){
				var p=$("#pageIndex2").val();
				location.href="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoUndercarriageProductListPage.action";
			}
			//异步刷新参数和属性
			function chengeAttrPara(prodctTypeId) {
				lconfirm("提醒","商品所属分类更改，会改变商品的属性和参数，需从新填写属性和参数！",function(o){
				   $("#confirm-overlay").overlay().close();//关闭浮层
				   $.ajax({
					   type: "POST", 
					   dataType: "JSON",
					   url: "${pageContext.request.contextPath}/front/store/frontProduct/findAttrPara.action",
					   data: {selectProductTypeId:prodctTypeId},
					   success: function(data){
						   if(data!=null){
							   //品牌start
							   var s = "<select id='brandId' disabled='disabled' name='productInfo.brandId'>";
							   if(data.brandList!=null){
								   for(var i=0;i<data.brandList.length;i++){
									   s+= "<option  value='"+data.brandList[i].brandId+"'>";
									   s+=data.brandList[i].brandName; 				  		
									   s+="</option>";	  	
								   }
							   }
							   s+="</select>";
							   $("#brandTd").html(s);
							 //商品计量单位名称start
							   var m = "<select id='measuringUnitName' name='productInfo.measuringUnitName' onchange='assign(this.value);'>";
							   if(data.measuringUnitList!=null){
								   for(var i=0;i<data.measuringUnitList.length;i++){
									   m+= "<option  value='"+data.measuringUnitList[i].name+"'>";
									   m+=data.measuringUnitList[i].name; 				  		
									   m+="</option>";	  	
								   }
							   }
							   m+="</select>";
							   $("#brandTd").html(m);
							   $("#totalProdPara").html("");
								$("#totalProdAttr").html("");
								var jaListProductAttr = data.listProductAttr;
							    if (jaListProductAttr != "") {
							        var listProductAttr = eval(jaListProductAttr); //分类属性的集合
							        if (listProductAttr.length > 0) {
							            var htmlAttrStr = "<tr><strong>商品属性</strong></tr>";
							            for (var i = 0; i < listProductAttr.length; i++) {
							                var infoArray = eval(listProductAttr[i].info);
							                htmlAttrStr += "<tr>";
							                htmlAttrStr += "<th style='text-align: right;width: 200px'>" + listProductAttr[i].name + ":</th><input type='hidden' name='listProdAttr[" + i + "].name'  value='" + listProductAttr[i].name + "'/><td><select id='" + listProductAttr[i].name + "' name='listProdAttr[" + i + "].value'>";
							                for (var j = 0; j < infoArray.length; j++) {
							                    htmlAttrStr += "<option style='width:80px '  value='" + infoArray[j] + "'>" + infoArray[j] + "</option>";
							                }
							                htmlAttrStr += " </select></td></tr>";
							            }
							            $("#totalProdAttr").html(htmlAttrStr);
							        }
							    }
							    //分类的属性end 
							    
				    //分类的参数start
				    var productPara = data.productPara;
						
				    if (productPara != "" && productPara!=null) {
				        //var productPara = data.productPara;//分类的参数集合
				        var htmlParaStr = '';
				        var infoObj = eval(productPara.info);
				        var trHtml = '';
				        var h = 0;
				        var trHtml = '<table align="center"  class="addOrEditTable" width="900px;"><tr><strong><td colspan="2" style="font-size:16px;">商品参数<\/td></strong><\/tr> ';
				        for (var i = 0; i < infoObj.length; i++) {
				            trHtml += '<table id="parameterTable' + i + '" class="parameterTable"  frame="below" width="100%"> ';
				            trHtml += '<tr  class="parameterTr" >' + '<strong>' + infoObj[i].name + '</strong> <input type="hidden" name="listParamGroup[' + i + '].name" value="' + infoObj[i].name + '" \/><input type="hidden"  name="listParamGroup[' + i + '].paramGroupId" value="' + infoObj[i].paramGroupId + '"\/>  ';
				            trHtml += '<td> <input type="hidden"  name="listParamGroup[' + i + '].order " value="' + infoObj[i].order + '" style="width: 50px;" \/> <\/td> ';
				            trHtml += ' <\/td><\/tr>';
				            trHtml += '<tr > <table id="parameterInfoTable' + i + '" class="parameterInfoTable" frame="below" width="100%">';
				            var a = eval(infoObj[i].paramGroupInfo);
				            for (var u = 0; u < a.length; u++) {
				                trHtml += '<tr class="parameterInfoTr" >';
				                trHtml += ' <th style="text-align: right;width: 80px"> ' + a[u].name + ':<\/th><input type="hidden"  name="listParamGroupInfo[' + h + '].name" value="' + a[u].name + '"\/> <input type="hidden" id="pgiId0" name="listParamGroupInfo[' + h + '].pgiId" value="' + a[u].pgiId + '"\/> ';
				                trHtml += ' <td> <input type="hidden" name="listParamGroupInfo[' + h + '].order" value="' + a[u].order + '"   \/> <\/td>';
				                trHtml += ' <td> <input type="text" name="listParamGroupInfo[' + h + '].value" value="' + a[u].value + '"  \/> <\/td>';
				                trHtml += '<\/tr>';
				                h++;
				            }
				            trHtml += '<\/table><\/tr>';
				            trHtml += '<\/table>';
				        }
				        trHtml += '<\/table>';
				        $("#totalProdPara").append(trHtml);
				    }
				    //分类的参数end

						   }
					   }
					});
				}); 
				}

			//新增的规格tr计数器，便于动态生成存放规格的tr
			var count_specificationValues_id_index=0;
			//存放当前分类下的所有规格，用于动态控制不同规格以及对应的规格值的展示
			var specificationArray=new Array();
			//初始化规格
			$(function() {
			    var specificationHtmlStr="";
			    <s:iterator  value="specificationList" var="specification" status="sta">
			    	var index="<s:property value='#sta.index'/>";
				    var specificationId="<s:property value='#specification.specificationId'/>";
				    specificationArray.push(specificationId);
				    var name="<s:property value='#specification.name'/>";
				    var notes="<s:property value='#specification.notes'/>";
					var specification_param=specificationId+"_"+name+"_"+notes;
					specificationHtmlStr+="<div style='float:left;margin:5px 10px;'>";
					specificationHtmlStr+="<span style='display:block;'>";
					specificationHtmlStr+="<input disabled='disabled' onclick='changeSelectedSpecification("+specificationId+")' id='"+specificationId+"' class='check' name='specificationIds' type='checkbox' value='"+specification_param+"'/>";
					specificationHtmlStr+="&nbsp;<label for='"+specificationId+"'>"+name;
					specificationHtmlStr+="[&nbsp;"+notes+"&nbsp;]</label></span></div>";
				</s:iterator>
				
			    $("#specificationSelect").html(specificationHtmlStr);
			    if(specificationHtmlStr!=""){
				    initSelectedSpecificationValue();
			    }
			    //是否锁定复选框
			    initLockCheckBox();
			});
			
			//初始化显示规格对应的规格值
			function initSelectedSpecificationValue(){
				var specificationIdArray =new Array();
				$("#specificationSelect input:checked").each(function(i){
					var specificationValueStringArray=this.value.split("_");
					var specificationId=specificationValueStringArray[0];
					specificationIdArray.push(specificationId);
				});
				var selectedSpecificationValuesHtmlStr="";
				$.ajax({
					   type: "POST", 
					   dataType: "JSON",
					   url: "${pageContext.request.contextPath}/front/store/frontProduct/getGoodsProductSpecificationValue.action",
					   async:false,
					   data: {goods:"<s:property value='productInfo.goods'/>",productId: "<s:property value='productInfo.productId'/>"},
					   success: function(data){
						   for(i=0;i<data[0].length;i++){
							    var productSpecificationValueObj=data[0][i];
							    var selectedSpecificationId=productSpecificationValueObj.specificationId;
								document.getElementById(selectedSpecificationId).checked=true;
							}
					   
					        var specificationProductHtmlStr="";
							$("#specificationProductTable-title").css("display","");
							var isOwnChecked=false;
							$("#specificationSelect input:checked").each(function(i){
								isOwnChecked=true;
								var specificationValueStringArray=this.value.split("_");
								var specificationId=specificationValueStringArray[0];
								var name=specificationValueStringArray[1];
								var notes=specificationValueStringArray[2];
								specificationProductHtmlStr+="<th  style='padding:5px 10px;' id='specification_"+specificationId+"'>";
								specificationProductHtmlStr+="<span  style='display:inline-block; white-space:nowrap;'>"+name+"</span>";
								specificationProductHtmlStr+="<br><span  style='margin:0px;15px 5px 5px; display:inline;'>[&nbsp;"+notes+"&nbsp;]</span></th>";
							});
							specificationProductHtmlStr+="<td></td>";
							$("#specificationProductTable-title").html(specificationProductHtmlStr);
									
					        for(i=0;i<data.length;i++){
					    	  var selectedSpecificationValuesHtmlStr="";
					    	  count_specificationValues_id_index=i;
					    	  
					    	  for(j=0;j<data[i].length;j++){
					    		  var productSpecificationValueObj=data[i][j];
					    		  var goodId=productSpecificationValueObj.goodId;
					    		  var productId=productSpecificationValueObj.productId;
					    		  var specificationValueId=productSpecificationValueObj.specificationValueId;
					    		  var specificationId=productSpecificationValueObj.specificationId;
					    		  
						   		   $.ajax({
									   type: "POST", 
									   dataType: "JSON",
									   url: "${pageContext.request.contextPath}/front/store/frontProduct/getSpecificationValueBySpecificationId.action",
									   async:false,
									   data: {specificationId: specificationId},
									   success: function(data){
										   if(data!=null){
											  var ssv_obj=$("#selectedSpecificationValues_"+count_specificationValues_id_index);
									    	  if(ssv_obj.attr("id")==null){
									    	  	var ssvTrHtml="<tr class='cannotdel' id='selectedSpecificationValues_"+count_specificationValues_id_index+"'></tr>";//动态创建多组tr
									    	  	$("#specificationProductTable").append(ssvTrHtml);
									    	  }
											   specificationValueList=data;
											   selectedSpecificationValuesHtmlStr+="<td class='specificationValue_"+specificationId+"'  style='width:auto; height:30px; padding:10px 20px; text-align:center;'>";
											   selectedSpecificationValuesHtmlStr+="<select class='disable_"+specificationId+" sort_"+specificationId+"' name='"+goodId+"-"+productId+"-specification_"+specificationId+"'>";
											    for(var i=0;i<specificationValueList.length;i++){
											    	var specificationValue=specificationValueList[i];
											    	if(specificationValueId==specificationValue.specificationValueId){
											 		   selectedSpecificationValuesHtmlStr+="<option selected value='"+specificationValue.specificationValueId+"'>"+specificationValue.name+"</option>";
											 		}else{
											 		   selectedSpecificationValuesHtmlStr+="<option value='"+specificationValue.specificationValueId+"'>"+specificationValue.name+"</option>";
											 		}
											    }
											    selectedSpecificationValuesHtmlStr+="</select></td>";
										   }
									   }
									});
					    	  }
					    	  if(i>0){
						    	  selectedSpecificationValuesHtmlStr+="<td class='delete_text' style='padding-left:10px; width:120px; white-space:nowrap;'><a href='javascript:removeSpecification("+productId+","+count_specificationValues_id_index+");' >[&nbsp;解除组关联&nbsp;]</a></td>";
					    	  }else{
					    	  }
					    	  $("#selectedSpecificationValues_"+count_specificationValues_id_index).html(selectedSpecificationValuesHtmlStr);
					      }
						   
					   }
				});
				
			}
			
			//操作复选框显示选中的规格以及对应的规格值
			function changeSelectedSpecification(specificationId){
				var isChecked=$("#"+specificationId).attr("checked");
				if(isChecked!="checked"){
					$(".specificationValue_"+specificationId).hide();
					$(".disable_"+specificationId).attr("disabled",true);
				}else{
					var objsArray=$(".specificationValue_"+specificationId);
					if(objsArray.length==0){
						var opSpecificationId=0;
						for(i=0;i<specificationArray.length;i++){
							if(specificationArray[i]==specificationId){
								if(i==0){
									opSpecificationId=specificationArray[i+1];
								}else{
									opSpecificationId=specificationArray[i-1];
								}
								break;
							}
						}
						var selectedSpecificationValuesHtmlStr=changeSelectedSpecificationValue(specificationId);
						$(".specificationValue_"+opSpecificationId).each(function(i){
							$(this).after(selectedSpecificationValuesHtmlStr);
						});
					}else{
						$(".specificationValue_"+specificationId).show();
						$(".disable_"+specificationId).attr("disabled",false);
					}
					
				}
				
				var check_objs=$("#specificationSelect input:checked");
// 				if(check_objs.length<3){
					var selectedSpecificationValuesHtmlStr="";
					var specificationProductHtmlStr="";
					$("#specificationProductTable-title").css("display","");
					$("#specificationSelect input:checked").each(function(i){
						isOwnChecked=true;
						var specificationValueStringArray=this.value.split("_");
						var specificationId=specificationValueStringArray[0];
						var name=specificationValueStringArray[1];
						var notes=specificationValueStringArray[2];
						specificationProductHtmlStr+="<th style='padding:5px 10px;' id='specification_"+specificationId+"'>";
						specificationProductHtmlStr+="<span  style='margin:5px 15px 5px 5px; display:inline;'>"+name+"</span>";
						specificationProductHtmlStr+="<br><span  style='margin:0px;15px 5px 5px; display:inline;'>[&nbsp;"+notes+"&nbsp;]</span></th>";
					});
					specificationProductHtmlStr+="<td></td>";
					$("#specificationProductTable-title").html(specificationProductHtmlStr);
					if(check_objs.length==0){
						$(".delete_text").hide();
					}else{
						$(".delete_text").show();
					}
// 				}else if(check_objs.length>2){
// 					$("#"+specificationId).attr("checked",false);
// 					lalert("提醒", "最多选择两个规格!");
// 				}
				
			}
			//显示规格对应的规格值
			function changeSelectedSpecificationValue(specificationId){
				var selectedSpecificationValuesHtmlStr="";
				$.ajax({
					   type: "POST", 
					   dataType: "JSON",
					   url: "${pageContext.request.contextPath}/front/store/frontProduct/getSpecificationValueBySpecificationId.action",
					   async:false,
					   data: {specificationId: specificationId},
					   success: function(data){
						   specificationValueList=data;
						   selectedSpecificationValuesHtmlStr+="<td class='specificationValue_"+specificationId+"'>";
						   selectedSpecificationValuesHtmlStr+="&nbsp;&nbsp;<select class='disable_"+specificationId+" sort_"+specificationId+"' name='specification_"+specificationId+"'>";
						    for(var i=0;i<specificationValueList.length;i++){
						    	var specificationValue=specificationValueList[i];
						    	selectedSpecificationValuesHtmlStr+="<option value='"+specificationValue.specificationValueId+"'>"+specificationValue.name+"</option>";
						    }
						    selectedSpecificationValuesHtmlStr+="</select></td>";
					   }
				});
				return 	selectedSpecificationValuesHtmlStr;
			}
			
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
						selectedSpecificationValuesHtmlStr+=changeSelectedSpecificationValue(specificationId); 
					});
					if(isOwnChecked){
						selectedSpecificationValuesHtmlStr+="<td class='delete_text' style='padding-left:10px; width:120px; white-space:nowrap;'><a href='javascript:deleteSpecification("+count_specificationValues_id_index+");' style='padding-left:10px;'>[&nbsp;删除&nbsp;]</a></td>";
					}
					newTrHtmlStr+=selectedSpecificationValuesHtmlStr+"</tr>";
					$("#specificationProductTable").append(newTrHtmlStr);
				}else{
					lalert("提醒", "至少选择一个规格!");
				}
			}
			
			// 删除规格商品
			function deleteSpecification(specificationValues_id_index){
				$("#selectedSpecificationValues_"+specificationValues_id_index).remove();
			}
			
			// 异步解除当前商品所在组的规格以及规格值的关联关系
			function removeSpecification(productId,count_specificationValues_id_index){
				lconfirm("提醒！","确定解除商品组规格关联吗？",function(o){
					$.ajax({
						   type: "POST", 
						   dataType: "JSON",
						   url: "${pageContext.request.contextPath}/front/store/frontProduct/removeProductSpecificationValueGoodsGuanlian.action",
						   data: {productId: productId,optionProductId:'${productId}'},
						   success: function(data){
							   if(data.isSuccess){
								   $("#selectedSpecificationValues_"+count_specificationValues_id_index).remove();
								   lclose();
							   }
						   }
					 });
					return true;
				});
				
			}
			$(function() { //表单验证
			    $("#spForm").validate({
			        meta: "validate",
			        submitHandler: function(form) {
			        	var submitFlag=validateSubmit();
			            //表单提交
		            if(submitFlag){
				        	form.submit();
			            }else{
			            	$("#submitButton_ps").removeAttr("disabled");
			            }
						//控制保存按钮不可用，避免重复提交
						/* $("#submitButton_ps").bind("click", function(){
							//控制保存按钮变灰，避免重复提交
							  $("#submitButton_ps").attr("disabled","disabled");
						}); */
			        }
			    });
			});
		
		   function validateSubmit(){
			 //是否有重复
	            var isRepeats = false;
	            var specifications = new Array();
	            var speciArray = new Array();
	            
	            //判断添加的规格值是否有重复的
	            $("#specificationProductTable").find("tr:gt(0)").each(function(i) {//找到添加规格行
	                var specificationValues = $(this).find("select").serialize();//找到当前行的所有规格值组下拉框，并把当前的规格值组序列化
	               // alert(specificationValues);
	                if(specificationValues!=""){
	                	var inputparam = $(this).find("input").serialize();//找到当前行的所有文本框，并序列化
	                	var count=specificationValues.split("&").length;
	                	var tmp=specificationValues.substring(0,specificationValues.indexOf("specification"));
	                	//替换所有的指定字符串为空“127-277-”
	                	//即将127-277-specification_2=4&127-277-specification_86=503&127-277-specification_87=506转化成
	                	//specification_2=4&specification_86=503&specification_87=506
	                	if(tmp!=""){
	                		var specificationValuesReplaced=specificationValues;
							specificationValuesReplaced=specificationValuesReplaced.replace(eval("/"+tmp+"/g"),'');
	                	}else{
	                		specificationValuesReplaced=specificationValues;
	                	}
	                	
		                if ($.inArray(specificationValuesReplaced, speciArray)>=0) {//判断当前行的规格值是否存在之前的数组中
		                    lalert("提醒", "商品规格重复!");
		                    isRepeats = true;
		                    return false;
		                } else {
		                	speciArray.push(specificationValuesReplaced);//存放到规格的数组中，下次对比使用
		                	specifications.push(specificationValues +"&"+ inputparam);//保存使用的数组
		                }
	                }
	            });
	            
	            if (!isRepeats) {
	                $("#specifications").val(specifications);
	                return true;
	            }else{
	            	 return false;
	            }
	            return true;
		   }
		   function initLockCheckBox(){
			   if($("#specificationProductTable tr").length==1){
					unLockCheckBox();
				}
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
	 							if(areaList[i].aid==$("#deliveryAddressCities")){
	 								htmlOption+="<option  value='" + areaList[i].aid+"' selected='selected'>" + areaList[i].name+ "</option>";
	 							}else{
	 								htmlOption+="<option  value='" + areaList[i].aid+"'>" + areaList[i].name+ "</option>";
	 							}
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
						<!-- 修改商品基本信息 -->
						<form id="basicForm"  action="${pageContext.request.contextPath}/front/store/frontProduct/updateFrontProductInfo.action" method="post">
						<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02">
							<input type="hidden" name="productId" value="<s:property value='productInfo.productId'/>"/>
							<input type="hidden" name="goods" value="<s:property value='productInfo.goods'/>"/>
							<input type="hidden" id="pm" value="<s:property value='productInfo.measuringUnitName'/>"/>
							<input type="hidden" id="categoryLevel1" value="<s:property value='productInfo.categoryLevel1'/>" name="productInfo.categoryLevel1"/>
							<input type="hidden" id="categoryLevel2" value="<s:property value='productInfo.categoryLevel2'/>" name="productInfo.categoryLevel2"/>
							<input type="hidden" id="categoryLevel3" value="<s:property value='productInfo.categoryLevel3'/>" name="productInfo.categoryLevel3"/>
							<input type="hidden" id="categoryLevel4" value="<s:property value='productInfo.categoryLevel4'/>" name="productInfo.categoryLevel4"/>
							<div>
								<table width="961px;">
									<tr><th style="text-align: right">提醒：</th>
										<td class="strong">基本信息，详情，属性，参数为集体修改，图片和规格是单独修改，需要单独提交。</td>
									</tr>
									<tr><th style="text-align: right"></th>
											<td class="strong"><font color="red">*</font>商品挂在最后一级分类上。</td>
										</tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>商品分类：</th>
										<td>
											<input id="productTypeId-hidden" type="hidden" name="productInfo.productTypeId" value="<s:property value='productInfo.productTypeId'/>"/>
								           <span id="productType-span-1">
								           </span>
								           <span id="productType-span-2">
								           </span>
								           <span id="productType-span-3">
								           </span>
							          	   <span id="productType-span-4">
							          	   </span>
										</td>
									</tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>商品名称：</th>
									<td><s:property value="productInfo.productName" /></td></tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>商品品牌：</th>
											<td id="brandTd">
										 	<select id="brandId" disabled="disabled" name="productInfo.brandId" class="{validate:{required:true}}">
												  <s:iterator  value="brandList">
												  	<option  value="<s:property value="brandId" />"<s:if test="productInfo.brandId==brandId">selected="selected"</s:if> >
												  		<s:property value="brandName"/>
												  	</option>
												  </s:iterator>
								           	</select>
										 </td>
									</tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>店铺内部商品分类：</th>
										 <td>
										 	<select id="shopProCategoryId" name="shopProCategoryId" class="{validate:{required:true}}">
									             <option value="">请选择</option>
												  <s:iterator  value="shopProCategoryList">
												  	<option  value="<s:property value="shopProCategoryId" />"<s:if test="shopProCategoryId==selectshopProCategoryId">selected="selected"</s:if> >
												  		<s:property value="shopProCategoryName"/>
												  	</option>
												  </s:iterator>
								           	</select>
								         </td>
									</tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>市场价格：</th><td><input style="border:1px #cecece solid;" type="text" id="marketPrice" name="productInfo.marketPrice" value="<s:property value="productInfo.marketPrice" />" class="{validate:{required:true,number:true,max:99999999,min:0.1}}"/></td></tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>销售价格：</th><td><input style="border:1px #cecece solid;" type="text" id="salesPrice" name="productInfo.salesPrice" value="<s:property value="productInfo.salesPrice" />" class="{validate:{required:true,number:true,max:99999999,min:0.1}}"/></td></tr>
									<%-- <tr><th style="text-align: right"><i class="ColorRed">*</i>结算价格：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.costPrice" value="<s:property value="productInfo.costPrice" />" class="{validate:{required:true,number:true,max:99999999,min:0}}"/></td></tr> --%>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>商品库存：</th><td><input style="border:1px #cecece solid;" type="text" id="storeNumber" name="productInfo.storeNumber" value="<s:property value="productInfo.storeNumber" />" class="{validate:{required:true,number:true}}"/></td></tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>预计出货日：</th>
										<td><input style="border:1px #cecece solid;" type="text" id="stockUpDate" class="{validate:{required:true,number:true,max:9999}}" name="productInfo.stockUpDate" value="<s:property value="productInfo.stockUpDate" />"/>&nbsp;&nbsp;&nbsp;天</td> 
 									</tr>
 									<tr>

									 <th style="text-align: right; width:115px;"><i class="ColorRed">*</i>发货地：</th>
										 <td>
										 	<select id="deliveryAddressProvince" name="productInfo.deliveryAddressProvince" onchange="chengeArea(this.value,'1','')" class="{validate:{required:true}}">
										 		 <option value="">省</option>
												  <s:iterator value="firstAreaList" var="first">
													<option  value="<s:property value="#first.aid"/>" <s:if test="#first.aid==productInfo.deliveryAddressProvince">selected="selected"</s:if> ><s:property value="#first.name"/></option>
												</s:iterator>
								           	</select>
								           	
								           	<select id="deliveryAddressCities" name="productInfo.deliveryAddressCities" class="{validate:{required:true}}">
									             <option value="">地级市</option>
									             <s:iterator value="secondAreaList" var="second">
													<option  value="<s:property value="#second.aid"/>" <s:if test="#second.aid==productInfo.deliveryAddressCities">selected="selected"</s:if> ><s:property value="#second.name"/></option>
												</s:iterator>
								           	</select>
								           	
								           </td>
								           
									</tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>商品编号：</th><td><input style="border:1px #cecece solid;" type="text" id="productCode" name="productInfo.productCode" value="<s:property value="productInfo.productCode" />"  class="{validate:{required:true}}"  /></td></tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>条形码：</th><td><input  style="border:1px #cecece solid;" type="text" id="barCode" name="productInfo.barCode" value="<s:property value="productInfo.barCode" />" class="{validate:{required:true,number:true}}" /></td></tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>计量单位：</th>
											<td id="name">
										 	<select id="measuringUnitName" name="productInfo.measuringUnitName" class="{validate:{required:true}}" onchange='assign(this.value);'>
												  <s:iterator  value="measuringUnitList">
												  	<option  value="<s:property value="name"/>"<s:if test="productInfo.measuringUnitName==name">selected="selected"</s:if> >
												  		<s:property value="name"/>
												  	</option>
												  </s:iterator>
								           	</select>
										 </td>
									</tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>商品重量：</th><td><input style="border:1px #cecece solid;" type="text" id="weight" name="productInfo.weight" value="<s:property value="productInfo.weight" />" class="{validate:{required:true,number:true}}"  />kg<span id="measuringUnitName1"></span></td></tr>
<!-- 									<tr><th style="text-align: right"><i class="ColorRed">*</i>重量单位：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.weightUnit" value="<s:property value="productInfo.weightUnit" />"  class="{validate:{required:true}}"  /></td></tr> -->
									<tr><th style="text-align: right"><i class="ColorRed">*</i>包装规格：</th><td><input style="border:1px #cecece solid;" type="text" id="packingSpecifications" name="productInfo.packingSpecifications" value="<s:property value="productInfo.packingSpecifications" />"  class="{validate:{required:true}}"  /></td></tr>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>制造商型号 ：</th><td><input style="border:1px #cecece solid;" type="text" id="manufacturerModel" name="productInfo.manufacturerModel" value="<s:property value="productInfo.manufacturerModel" />" class="text,{validate:{required:true}}" maxlength="200" />
									<input type="hidden" name="productInfo.isPutSale" value="<s:property value="productInfo.isPutSale"/>" />
									<input id="freightPrice" type="hidden" name="productInfo.freightPrice" value="<s:property value="productInfo.freightPrice" />"/>
									</td></tr>
<!-- 									<tr><th style="text-align: right">商品描述 ：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.describle" value="<s:property value="productInfo.describle" />" class="text" maxlength="100"/></td></tr> -->
<!-- 									<tr><th style="text-align: right">是否上架：</th><td><input type="radio" name="productInfo.isPutSale" value="1" <s:if test="productInfo.isPutSale==1">checked="checked"</s:if>/>&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;<input type="radio" name="productInfo.isPutSale" value="2" <s:if test="productInfo==null||productInfo.isPutSale==2">checked="checked"</s:if>/>&nbsp;&nbsp;&nbsp;是</td></tr> -->
									<%-- <tr><th style="text-align: right">是否包含运费：</th><td><input type="radio" name="productInfo.isChargeFreight" value="1" onclick="isChargeFreight(this.value);" <s:if test="productInfo==null">checked="checked"</s:if> <s:if test="productInfo.isChargeFreight==1">checked="checked"</s:if>/>&nbsp;&nbsp;&nbsp;免运费&nbsp;&nbsp;&nbsp;<input type="radio" name="productInfo.isChargeFreight" value="2" onclick="isChargeFreight(this.value);" <s:if test="productInfo.isChargeFreight==2">checked="checked"</s:if>/>&nbsp;&nbsp;&nbsp;填写运费</td></tr>
									<tr id="yunfei" style="display:none"><th style="text-align: right">商品运费：</th><td><input id="freightPrice" type="text" name="productInfo.freightPrice" value="<s:property value="productInfo.freightPrice" />" class="{validate:{number:true,max:99999999,min:0}}"/></td></tr> --%>
									<tr><th style="text-align: right"><i class="ColorRed">*</i>赠送购买者商城币：</th><td><input disabled="disabled"  style="border:1px #cecece solid;" type="text" name="productInfo.giveAwayCoinNumber" class="{validate:{required:true}}" value="<s:property value="productInfo.giveAwayCoinNumber" />"/></td></tr>
									<tr><th style="text-align: right">商品备注：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.note" value="<s:property value="productInfo.note" />"/></td></tr>
<!-- 									<tr><th style="text-align: right">搜索 TAG：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.productTag" value="<s:property value="productInfo.productTag" />"/></td></tr> -->
									<tr><th style="text-align: right">SEO 标题：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.seoTitle" value="<s:property value="productInfo.seoTitle" />"/></td></tr>
									<tr><th style="text-align: right">SEO 关键字：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.seoKeyWord" value="<s:property value="productInfo.seoKeyWord" />"/></td></tr>
									<tr><th style="text-align: right">SEO 描述：</th><td><input style="border:1px #cecece solid;" type="text" name="productInfo.seoDescription" value="<s:property value="productInfo.seoDescription" />"/></td></tr>
								</table>
								<div style="padding:20px 0;">
									<table class="margin-center">
										<tr>
											<td >
												<input id="pageIndex2" name="pageIndex2" value="${pageIndex2}" type="hidden" />
												<input id="submitButton" class="submitImg BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px; text-align: center;  cursor: pointer; line-height:16px;"  type="submit" value="提交"></input>
												<input onclick="gotoShopProductListPage();" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" type="button" value="返回"  onclick="window.history.back(-1);" style="border:0; text-align: center;  cursor: pointer; line-height:16px;"></input>
												<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
											</td>
										</tr>
		       						</table>
	       						</div>	
							</div>
							</div>
<!-- 							</form> -->
							<!-- 修改商品FCK -->
<!-- 							<form action="${pageContext.request.contextPath}/front/store/frontProduct/updateFrontProductReferral.action" method="post"> -->
								<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02">
								<div>
									<table width="750px;">
									 	<tr>
										      <td  align="left" colspan="3">&nbsp;&nbsp;
										      	<input type="hidden" name="productInfo.functionDesc" id="KEFunctionDesc" value="<s:property value="productInfo.functionDesc"/>" />
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
									<div style="padding:20px 0;">
										<table class="margin-center">
											<tr>
												<td >
													<input class="submitImg BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px;"  type="submit" value="提交"></input>
													<input onclick="javascript:history.back(-1);" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" type="button" value="返回"  onclick="window.history.back(-1); "></input>
													<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
												</td>
											</tr>
			       						</table>
		       						</div> 	
								</div>
								</div>
<!-- 							</form> -->
							
	        				<!-- 修改商品属性 -->
<!-- 	        				<form action="${pageContext.request.contextPath}/front/store/frontProduct/updateFrontProductAttribute.action" method="post"> -->
								<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02">
									<div>
										<input type="hidden" id="listProductAttr" value="<s:property value="jaListProductAttr"/>"/>
										<input type="hidden" id="attributeValueList" value="<s:property value="jaattributeValueList"/>"/>
										<input type="hidden" id="paiList" value="<s:property value="japaiList"/>"/>
										<input type="hidden" id="productAttr" value="<s:property value="joProductPara.productAttributeValue"/>"/>
										<table id="totalProdAttr" width="100%"></table>
										<div style="padding:20px 0;">
											<table class="margin-center">
												<tr>
													<td >
														<input class="submitImg BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px;"  type="submit" value="提交"></input>
														<input onclick="javascript:history.back(-1);" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" type="button" value="返回"  onclick="window.history.back(-1); "></input>
														<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
													</td>
												</tr>
				       						</table>
		       							</div>
									</div>
								</div>
<!-- 							</form> -->
							<!-- 修改商品参数 -->
<!-- 							<form action="${pageContext.request.contextPath}/front/store/frontProduct/updateFrontProductParameter.action" method="post"> -->
								<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02">
								<div>
									<s:if test="productInfo.productId!=null">
										<input type="hidden" id="productPara" value="<s:property value="productInfo.productParametersValue"/>"/>
										<input type="hidden" id="ppINfo" value="<s:property value="productPara.info"/>"/>
									</s:if>
									<s:else>
										<input type="hidden" id="productPara" value="<s:property value="joProductPara.info"/>"/>
									</s:else>
									<table id="totalProdPara" width="100%"></table>
									<div style="padding:20px 0;">
										<table class="margin-center">
											<tr>
												<td >
													<input class="submitImg BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px;"  type="submit" value="提交"></input>
													<input onclick="javascript:history.back(-1);" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" type="button" value="返回"  onclick="window.history.back(-1); "></input>
													<p class="submitImgLoad" style="display:none;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
												</td>
											</tr>
			       						</table>
		       						</div> 
								</div>
								</div>
								
								<%--商品标签--%>
								<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02" id="checkTages">
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
									    <div style="padding:20px 0;">
										<table class="margin-center">
											<tr>
												<td >
													<input class="BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px;"  type="submit" value="提交"></input>
													<input onclick="javascript:history.back(-1);" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" type="button" value="返回"  onclick="window.history.back(-1); "></input>
												</td>
											</tr>
			       						</table>
		       						</div>	
									  </div>
									<script>
									//初始化标签信息
									$(function(){
										var firstShopTradeTag='${firstShopTradeTag}';
										if(firstShopTradeTag!=null){
											$("#selected-shopTradeTag").html("");
											$("a.tanchuk_col").removeAttr("class");
											$("#ShopTradeTag-ul").find("a:first").attr("class","tanchuk_col");
											firstShopTradeTag=jQuery.parseJSON(firstShopTradeTag); 
											$("#shopSituationTag-ul").attr("ttId",firstShopTradeTag.ttId);
											//获取右侧的使用场合信息
											getShopSituationTagInfoList(firstShopTradeTag.ttId);
											$('#checkTages  input[type="checkbox"]:checked').attr("checked",false);
											//标签回显
											var shopProductTradeSituationTag='${shopProductTradeSituationTagJsonArray}';
											shopProductTradeSituationTagJsonArray=jQuery.parseJSON(shopProductTradeSituationTag);
											//定义顶部已选标签
											var topTagesHtml="";
											//使用each进行遍历  
											$.each(shopProductTradeSituationTagJsonArray,function(n,value) {   
												if(value!=null&&value!=""){
													
													var ids=value.ids;
													var ttId=value.ttId;
													$("#selected-shopTradeTag-hidden-"+ttId).val(ids);
													//生成顶部已选标签删除按钮的id
													var id="selected-shopTradeTag-centon-div-"+ttId;
													//获取“适用专业”名称
													var name=$("#selected-shopTradeTag-name-hidden-"+ttId).val();
													//定义顶部已选择适用专业标签html内容
													var topHtml="<div id=\""+id+"\" class=\"hangye_fenlei\">"+name+"<input type=\"button\"  class=\"but_aniu\" onclick=\"deleteTopTage('"+id+"')\"/></div>";
												    $("#selected-shopTradeTag").append(topHtml);
												    var idsArray="["+ids+"]";
												    idsArray=jQuery.parseJSON(idsArray);
												    if(firstShopTradeTag.ttId==ttId){
													    $.each(idsArray,function(n,value) {  
													    	$("#checkbox"+value).attr("checked",true);
											            });  
												    }
												}
											});  
											//给隐藏域中的tages赋值
											$("#topTages-hidden").val(shopProductTradeSituationTag);
										}else{
											$("#selected-shopTradeTag").html("");
											$("a.tanchuk_col").removeAttr("class");
											$("#shopSituationTag-ul").removeAttr("ttId");
											$('input[type="checkbox"]:checked').attr("checked",false);
											$('input[name="selected-shopTradeTag-hidden"]').val("");
											 $("#shopSituationTag-ul").html("");
										}
									});
									
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
											$("#topTages-hidden").val("");
										}
										
										//添加顶部的 已选适用专业标签
										function insertTopTages(){
											//如果使用场合ul的自定义属性为空则不获取选中的checkbox信息
											if($("#shopSituationTag-ul").attr("ttId")!=""){
												//定义一个字符串 用来存储 使用场合ID
												var ids="";
												//获取页面中选中的checkbox
												$('input[type="checkbox"]:checked').each(function(){
													var idss = $(this).attr("id");
													if("checkbox"==idss.substring(0,8)){
														var value=$(this).val();
														ids+=","+value;
													}
												});
												if(ids!=""){
													ids=ids.substring(1,ids.length);
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
							</form>
							
							
							
							<!-- 修改商品图片 -->
							<form action="${pageContext.request.contextPath}/front/store/frontProduct/updateFrontProductImg.action" method="post" enctype="multipart/form-data">
								<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02">
								<input type="hidden" name="productId" value="<s:property value='productInfo.productId'/>"/>
								<input type="hidden" name="goods" value="<s:property value='productInfo.goods'/>"/>
								<input type="hidden" name="logoImg" value="<s:property value='productInfo.logoImg'/>"/>
								<div>
									<table width="750px;"  >
									<tr ><td colspan="1"><strong>商品 Logo</strong></td></tr>
									<tr><td>图片</td></tr>
									<tr>
										<td><input type="file" name="otherUploadImgs" value="<s:property value='productInfo.logoImg'/>" class="productImageFile"/></td><td><s:property value="name"/></td>
									</tr>
									<tr>
										<td><span><img style="width: 80px;height: 80px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='productInfo.logoImg'/>"/></span></td>
									</tr>
									</table>
									<table id="productImageTable" width="750px;"   >
										<tr ><td colspan="1"><strong ><a id="addProductImage" style="color: blue" href="javascript:;" class="BackgroundF2Hui publicinline publicPadding5_10 publicBorder">添加滚动图片</a></strong></td></tr>
										<tr><td>图片预览</td><td>标题</td><td>排序</td><td>操作</td></tr>
										<s:iterator value="productImgList" status="sta">
											<tr class="imgs">
												<td>
													<input type="hidden" name="listProductImage[<s:property value="#sta.index"/>].productId"  value="<s:property value='productId'/>"/>
													<input type="hidden" name="listProductImage[<s:property value="#sta.index"/>].productImageId"  value="<s:property value='productImageId'/>"/>
													<input type="hidden" name="listProductImage[<s:property value="#sta.index"/>].source" id="source<s:property value="#sta.index"/>" value="<s:property value='source'/>"/>
													<input type="hidden" name="listProductImage[<s:property value="#sta.index"/>].large" id="large<s:property value="#sta.index"/>" value="<s:property value='large'/>"/>
													<input type="hidden" name="listProductImage[<s:property value="#sta.index"/>].medium" id="medium<s:property value="#sta.index"/>" value="<s:property value='medium'/>"/>
													<input type="hidden" name="listProductImage[<s:property value="#sta.index"/>].thumbnail" id="thumbnail<s:property value="#sta.index"/>" value="<s:property value='thumbnail'/>"/>
													<img style="width: 60px;height: 40px;" id="member-img<s:property value="#sta.index"/>" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='source'/>" class="accountFigure" />
													<div class="tip-tx"><a href="javascript:;" class="editImageBtn" id="img_<s:property value="#sta.index"/>">修改图片</a></div>
												</td>
												<td><input type="text" name="listProductImage[<s:property value="#sta.index"/>].title" class="text" maxlength="200" value="<s:property value='title'/>"/></td> 
												<td><input type="text" name="listProductImage[<s:property value="#sta.index"/>].orders" class="text productImageOrder" maxlength="9" style="width: 50px;" value="<s:property value='orders'/>"/></td> 
												<td> <a href="javascript:;" class="deleteProductImage" id="<s:property value='productImageId'/>">[删除]</a></td>
											</tr>
										</s:iterator>
									</table>
									<div style="padding:20px 0;">
										<table class="margin-center">
											<tr>
												<td >
													<input class="BackgroundPurple publicinline publicPadding5_10 ColorWhite1 FontSize14" style="margin-right:15px; border:0;"  type="submit" value="提交"></input>
													<input onclick="javascript:history.back(-1);" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14" style="border:0;" type="button" value="返回"  onclick="window.history.back(-1); "></input>
												</td>
											</tr>
			       						</table>
		       						</div>	
	        					</div>
	        					</div>
	        				</form>	
	        				<form action="${pageContext.request.contextPath}/front/store/frontProduct/updateFrontProductSpecification.action" method="post" id="spForm">
								<input type="hidden" name="productId" value="<s:property value='productInfo.productId'/>"/>
								<input id="specifications" type="hidden" name="specifications" value=""/>
								<div class="TabParent_lj publicBorder ClearBoth TabParent_lj02" >
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
							    		<table id="specificationProductTable" style="margin-top:15px; width:100%; height:auto;">
							    		    <!--选定规格值名称 -->
							    			<tr id="specificationProductTable-title"  style="background-color: #E8EBF0; border-bottom-style: solid;padding:5px;"></tr>
							                <!--选定规格值-->
											<%--<tr id="selectedSpecificationValues_0"></tr> --%>
							    		</table>
							    		</div>
				    					<!-- 规格值 end -->
				    					<div style="padding:20px 0;">
											<table class="margin-center">
												<tr>
													<td>
														<input id="submitButton_ps" class="BackgroundPurple publicinline publicPadding5_10 ColorWhite FontSize14" style="margin-right:15px; border:0;"  type="submit" value="提交"></input>
														<input onclick="javascript:history.back(-1);" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite FontSize14" type="button" value="返回"></input>
													</td>
												</tr>
							    			</table>
						  			 	</div>
				    		   		 </div>
				    		        <!-- 商品规格 end -->
							</div>
						</form>	
						<script>
							$(function() {
								$("ul.FavouriteTab").tabs("div.TabParent_lj > div", {
									initialIndex : 0
								});
							});
						</script>
					</div>
					</section>
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
								<input type="file" name="imagePath" id="tx-file"/>
								<p>注意:为达到最佳效果,使用一个图像,是200像素宽,300像素高.</p>
							</p>
							<p class="modal-buttons">
								<button type="submit" id="controlButton">提交</button>
								<button class="close" type="button">
									关闭
								</button>
							</p>
						</form>
					</div>
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
