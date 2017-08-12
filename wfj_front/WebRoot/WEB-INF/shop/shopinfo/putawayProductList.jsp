<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上架产品列表</title>
<%@ include file="../include/head.jsp"%>
<script>
	//查询条件初始化中商品分类
	$(function(){
		//初始化商品分类
		$("#qproductTypeId").val('${productTypeId}');
		//初始化排序
		$("#goodsSorting").val('${orderByParams}');
	});
	//商品下架操作
	function changePutSaleInfo(id){//id为商品的ID
		    lconfirm("提醒","商品下架后需要再次审核，是否确定下架？",function(o){
		    	$("#changeisPutSate").val(id);
			    changePage(document.getElementById("currentPage").value);
		    });
	}
	//checkbox全选、取消全选操作
	function selectAllcheckbox(id,name){
	   var b = document.getElementsByName(name);
	      var all = document.getElementById(id);
	         if(all.checked){
	             for(var i=0;i<b.length;i++){
	                 b[i].checked=true;
	             }
	          }else{
	             for(var i=0;i<b.length;i++){
	                 b[i].checked=false;
	             }
	         }
	}
	//点击一个checkbox的操作
	function oneCheckBox(){
		//获取选中checkbox的长度
		var checkLength=$('input[name=checkbox]:checked').length;
		//获取所有checkbox的长度（除全选checkbox外）
		var totalLength=$('input[name=checkbox]').length;
		if(checkLength==totalLength){
			//选中全选checkbox
			$("#checkboxAll").attr("checked",true);
		}else{
			$("#checkboxAll").removeAttr("checked");
		}
	}
 	//批量处理商品下架
	 function batchProcessing(){
 		lconfirm("提醒","商品下架后需要再次审核，是否确定下架？",function(o){
 			//循环祖品商品id串（ids）
 	 		var ids="";
 	 		$('input[name="checkbox"]:checked').each(function(){
 	 			ids+=$(this).val()+",";
 	 		});
 	 		if(ids!=""){
 	 			ids=ids.substring(0,ids.lastIndexOf(","));
 		 		$("#bathChangeisPutSate").val(ids);
 		 		changePage(document.getElementById("currentPage").value);
 	 		}
	    });
	 }

	function changePage(pageIndex){
		if(document.getElementById("currentPage")!=null){
			document.getElementById("currentPage").value=pageIndex;//分页
		}
	 	document.getElementById("orderByParams").value=$("#goodsSorting").val();//排序
	 	document.getElementById("hidProductName").value=$.trim($("#qproductName").val());//商品名称
	 	document.getElementById("hidProductTypeId").value=$("#qproductTypeId").val();//商品分类
	 	$("#formModule").submit();
	 }
	
	//异步修改商品库存数量
    function updateStoreNumber(id){
		var num=$("#num_"+id).val().trim();
		var reg = /^[1-9]\d*$/;
		var flag = reg.test(num);
		if(flag){
		   	$.ajax({
				   type: "POST", dataType: "JSON",
				   url: "${pageContext.request.contextPath}/front/store/frontShopProduct/updateStoreNumber.action",
				   data: {productId:id,storeNumber:num},
				   success: function(data){
					   if(data.isSuccess=="true"){
						   lconfirm_yes("消息提醒", "修改商品库存成功!",function(o){
							   window.location.reload();
							   });
						}else{
							   lalert("修改商品库存失败！");
							   isRepeats = false;
						}
				   }
				});
		}else{
			var a=document.getElementById("warn_"+id);
	    	a.innerHTML="<div class='cart-alarm' style='color:red'>数量在1-999之间</div>";
		}
	}
	
	//申请参团
	function groupApply(id){
		$.ajax({
			type:"POST",
			dataType:"JSON",
			url:"${pageContext.request.contextPath}/front/customer/tuan/checkTuanProduct.action",
			data:{productId:id},
			success:function(data){
				if(data.isExsit==true){
					window.location.href="${pageContext.request.contextPath}/front/customer/tuan/gotoGroupApply.action?productId="+id;
				}else{
					lalert("该商品已申请参团，不可重复操作！");
				}
			}
		});
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
<section>
<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">上架产品列表</h3>
	<aside class=" publicBorder BackgroundF2Hui publicPadding10 ">
	<div class="ClearBoth  parent_for_p">
	   <div style="float:left;">
		<p  style="margin-top:3px;">排序：
		</p>
		<p style="margin-top:3px;">
			 <select id="goodsSorting">
					<option value="Latest" >最新</option>
					<option id="Pricehighestfirst" value="Pricehighestfirst">价格由高到低</option>
					<option id="Pricelowestfirst" value="Pricelowestfirst">价格由低到高</option>
					<option id="Soldhighestfirst" value="Soldhighestfirst">销售量由高到低</option>
					<option id="Soldlowestfirst" value="Soldlowestfirst">销售量由低到高</option>
				</select>
			</p>
			</div>
				<p>商品名称：
				<input id="qproductName" type="text" style="height:20px;" value="${productName}"/>&nbsp;&nbsp;&nbsp;&nbsp;
				商品分类：
				<select  id="qproductTypeId">
	             <option value="-1">-----请选择分类-----</option>
				  <s:iterator value="#application.categoryMap" var="type1">
				  	<option value="<s:property value="#type1.key.productTypeId"/>"  >
				  	<s:property value="#type1.key.sortName"/>
				  	<s:iterator value="#type1.value" var="type2">
					  	<option value="<s:property value="#type2.key.productTypeId"/>"  >
					  		&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type2.key.sortName"/>
					  		<s:iterator value="#type2.value" var="type3">
					  		<option value="<s:property value="#type3.key.productTypeId"/>" >
					  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type3.key.sortName"/>
					  				<s:iterator value="#type3.value" var="type4">
					  					<option value="<s:property value="#type4.productTypeId"/>" >
					  						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type4.sortName"/>
							  			</option>
						  			</s:iterator>
					  		</option>
					  		</s:iterator>	
					  	</option>
				  	</s:iterator>
				  	</option>
				  </s:iterator>
	           </select>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="查询" onclick="changePage('1');"
								class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" style="width: 50px;height: 22px;cursor:pointer;"/>
				</p>
		</div>
	</aside>
	
<div class="MarketpalceTree publicMarginTop15 " style="position: relative;">
<div class="TabParent_lj publicBorder ClearBoth">

<input name="button" style="cursor:pointer; position:absolute;top:330px;background:#e62d59; border:1px #e62d59 solid;margin-right:12px; width:74px; height:22px; line-height:17px;top: 12px;" 
	onclick="batchProcessing();" type="button"
    class="shopProductList_batchProcessing FontSize14 publicPadding5_10 ColorWhite1 BlackgroundOrange"  value="批量下架"/>

<br/><br/>
<div>
<div id="test" class="publicPadding10">
<form  id="formModule" action="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoPutawayProductListInfoPage.action" method="post">
<input id="paramsCheckbox" name="paramsCheckbox"  type="hidden" value=""/>
<!-- 查询条件 -->
<input id="orderByParams" name="orderByParams"  type="hidden" value=""/>
<input id="hidProductName" name="productName"  type="hidden" value=""/>
<input id="hidProductTypeId" name="productTypeId"  type="hidden" value=""/>
<!-- 更改商品显示状态标识 -->
<input id="changeShowSate" name="changeShowSate"  type="hidden" value=""/>
<!-- 商品下架操作标识 -->
<input id="changeisPutSate" name="changeisPutSate"  type="hidden" value=""/>
<!-- 商品批量下架操作标识 -->
<input id="bathChangeisPutSate" name="bathChangeisPutSate"  type="hidden" value=""/>


<table width="100%" border="0" class="OrderinforTableL firstChild_Tab">
  <tr class=" publicBorder gradient">
    <td style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;" width="3%"><input id="checkboxAll" name="checkboxAll" type="checkbox"  onclick="selectAllcheckbox('checkboxAll','checkbox');" value="all"></td>
    <td width="10%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">图片</td>
    <td width="15%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">商品名称</td>
    <td width="16%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">商品分类</td>
    <td width="9%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">价格</td>
    <td width="13%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">销售量</td>
    <td width="13%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">库存</td>
    <td width="11%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">时间</td>
    <td width="9%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-right: 1px #d2d2d2 solid;">操作</td>   
  </tr>
  <!-- 遍历店铺上架的商品List -->
  <s:iterator value="OnshelfList">
		  	<tr class="checkbox_tr">
			    <td style="border-bottom: 1px #d2d2d2 solid;" class="publicNoBorderR publicNoBorderL"><input id="checkboxId_<s:property value='productId'/>" name="checkbox" type="checkbox" onclick="oneCheckBox()" value="<s:property value='productId'/>">
			    </td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><a target="_blank" title="<s:property value="productFullName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId'/>.html"><img style="height:50px; width:50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></a></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;">
			    	<div style="height: 3.5em;word-wrap:break-word;overflow:hidden;">
			    		<a target="_blank" title="<s:property value="productFullName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId'/>.html"><s:property value="productFullName"/></a>
			    	</div>
			    </td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="sortName"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:property value="salesPrice"/></td>
<!-- 			    <td><s:property value='productId'/></td> -->
			    <td style="border-bottom: 1px #d2d2d2 solid;">
			    	<s:if test="totalSales != null">
				    	<s:property value="totalSales"/>&nbsp;件
			    	</s:if>
			    	<s:if test="totalSales == null">
				    	0 &nbsp;件
			    	</s:if>
				</td>
				   <td style="border-bottom: 1px #d2d2d2 solid;">
				    	<s:if test="storeNumber != null">
					    	<input id="num_<s:property value='productId'/>" class="quick-order.amount" type="text" style="padding:4px 5px;width:30px;text-align:center;" maxlength="3" value="<s:property value='storeNumber'/>"/>
				    	</s:if>
				    	<s:if test="storeNumber == null">
					    	<input id="num_<s:property value='productId'/>" class="quick-order.amount" type="text" style="padding:4px 5px;width:30px;text-align:center;" maxlength="3" value="0"/>
				    	</s:if>
					</td>
			    <td style="border-bottom: 1px #d2d2d2 solid;"><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			    <td style="border-bottom: 1px #d2d2d2 solid;" class="publicNoBorderR">
			    	<a href="javascript:void(0);" onclick="changePutSaleInfo(<s:property value="productId"/>);" class="ColorBlue">下架</a>
			    	<a href="javascript:void(0);" onclick="groupApply(<s:property value="productId"/>);" class="ColorBlue">参团</a>
			    	<br/>
			    	<a href="javascript:updateStoreNumber(<s:property value="productId"/>);" class="ColorBlue">提交库存</a>
			    	<span id="warn_<s:property value='productId'/>"></span>
			    </td>
		  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="OnshelfList.size>0||OnshelfList.size>0">
	<div class="pageList strong ClearBoth">
		<input type="hidden" id="currentPage" name="currentPage" value="<s:property value='currentPage'/>"/>
		<!--翻页效果-->
		<div class="flipEffect">
		   <div style="float:right;">
		   		<a <s:if test="currentPage==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pageHelper.prePageIndex'/>)"</s:else>>上一页</a>
		   		<s:iterator value="pageHelper.optionalPageIndexList" var="pageNumber" >
		   			 <a href="javascript:;" <s:if test="#pageNumber==currentPage">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumber'/>)"</s:else> ><s:property value="#pageNumber" /></a>
				</s:iterator>
		   		<a <s:if test="currentPage>=pageHelper.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pageHelper.nextPageIndex'/>)"</s:else>>下一页</a>
		   </div>
		</div>
	</div>
</s:if>
<!--分页 end-->
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
<%@ include file="../include/footer.jsp"%>
</body>
</html>
