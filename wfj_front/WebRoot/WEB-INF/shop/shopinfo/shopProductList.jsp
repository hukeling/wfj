<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>店铺产品列表</title>
<%@ include file="../include/head.jsp"%>
<script>
//查询条件初始化中商品分类
	$(function(){
		var productTypeId='${productTypeId}';
		if(productTypeId==null){
			$("#productTypeId").val("-1");
		}else{
			$("#productTypeId").val(productTypeId);
		}
		
	})

var index =${index};
$(function() {
	$("ul.FavouriteTab").tabs("div.TabParent_lj > div",{initialIndex:index},{history: true});
	$("#goodsSorting").val($("#selectParams").val());
});

function changePutSaleInfo(id){//id为商品的ID
	var selectParams=$("#goodsSorting").val();
    
     var url = '${pageContext.request.contextPath}/front/store/frontShopProduct/changePutSaleInfo.action';
		$.post(url,{"id":id},function(data){
			if(data){
		          if(selectParams=="Latest"){
				   window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action?index="+data.index+"&shopInfoId="+${shopInfoId};
			   }else{
				   window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/goodsByParams.action?index="+data.index+"&shopInfoId="+${shopInfoId}+"&params="+selectParams;
			   }
			}
		},"json");
}
//checkbox全选、取消全选操作
 function selectAllcheckbox(id,name,name2,name3){
    var b = document.getElementsByName(name);
       var all = document.getElementById(id);
          if(all.checked){
              for(i=0;i<b.length;i++){
                  b[i].checked=true;
              }
              $("[name='"+name2+"']").removeAttr("checked");
              $("[name='"+name3+"']").removeAttr("checked");
          }else{
              for(i=0;i<b.length;i++){
                  b[i].checked=false;
              }
          } 
	} 
 //批量处理商品上下架
 function batchProcessing(){
	 var selectParams=$("#goodsSorting").val();
	 var m=0;
	 var n=0;
	 var onShelf = document.getElementsByName("onShelf");
	  var offShelf = document.getElementsByName("offShelf");
	    for(var i=0;i<onShelf.length;i++){
			 if(onShelf[i].checked == true){
			    m = m + 1;
			 }
	 	}
	     for(var i=0;i<offShelf.length;i++){
			 if(offShelf[i].checked == true){
			   n = n+ 1;
			 }
	 	}
	 if(m==0&&n==0){
		  lalert("提醒","请选择处理的商品");
		  return;
	 }   
	     
	var formID="";     
	if(m>0){//做上架操作
		$("#paramsCheckbox").val("onShelf");
		formID="formModule";
	}else{//做下架操作
		$("#paramsCheckbox2").val("offShelf");
		formID="formModule2";
	}
     var url = '${pageContext.request.contextPath}/front/store/frontShopProduct/batchProcessing.action';
	$.post(url,$('#'+formID).serialize(),function(data){
		if(data){
            $("[type='checkbox']").removeAttr("checked");//取消选中
		   if(selectParams=="Latest"){
			   window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action?index="+data.index+"&shopInfoId="+${shopInfoId};
		   }else{
			   window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/goodsByParams.action?index="+data.index+"&shopInfoId="+${shopInfoId}+"&params="+selectParams;
		   }
		}
	},"json");
 }
//根据条件排列商品
function goodsSorting(value){
	var n=$("#indexFlag").val();
	if(value=="Latest"){
		window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action?index="+n+"&shopInfoId="+${shopInfoId};
	}else{
		window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/goodsByParams.action?params="+value+"&index="+n+"&shopInfoId="+${shopInfoId};
	}
}
//分页函数
function changePage(pageIndex,flag1,flag2,flag3,flag){//flag 1,2,3 分别代表其他三个分页的当前页  flag代表点击的分页对象
	var selectParams=$("#goodsSorting").val();
	if(flag==1){//上架商品分页操作
		if(selectParams=="Latest"){
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action?index=0&pageIndex2="+flag1+"&pageIndex3="+flag2+"&pageIndex4="+flag3+"&shopInfoId=${shopInfoId}&pageIndex1="+pageIndex;
		}else{
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/goodsByParams.action?index=0&pageIndex2="+flag1+"&pageIndex3="+flag2+"&pageIndex4="+flag3+"&shopInfoId=${shopInfoId}&pageIndex1="+pageIndex+"&params="+selectParams;
		}
	}else if(flag==2){//下架商品分页操作
		if(selectParams=="Latest"){
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action?index=1&pageIndex1="+flag1+"&pageIndex3="+flag2+"&pageIndex4="+flag3+"&shopInfoId=${shopInfoId}&pageIndex2="+pageIndex;
		}else{
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/goodsByParams.action?index=1&pageIndex1="+flag+"&pageIndex3="+flag2+"&pageIndex4="+flag3+"&shopInfoId=${shopInfoId}&pageIndex2="+pageIndex+"&params="+selectParams;
		}
	}else if(flag==3){//审核中商品分页操作
		if(selectParams=="Latest"){
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action?index=2&pageIndex1="+flag1+"&pageIndex2="+flag2+"&pageIndex4="+flag3+"&shopInfoId=${shopInfoId}&pageIndex3="+pageIndex;
		}else{
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/goodsByParams.action?index=2&pageIndex1="+flag1+"&pageIndex2="+flag2+"&pageIndex4="+flag3+"&shopInfoId=${shopInfoId}&pageIndex3="+pageIndex+"&params="+selectParams;
		}
	}else if(flag==4){//违规下架商品分页操作
		if(selectParams=="Latest"){
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action?index=3&pageIndex1="+flag1+"&pageIndex2="+flag2+"&pageIndex3="+flag3+"&shopInfoId=${shopInfoId}&pageIndex4="+pageIndex;
		}else{
		    window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/goodsByParams.action?index=3&pageIndex1="+flag1+"&pageIndex2="+flag2+"&pageIndex3="+flag3+"&shopInfoId=${shopInfoId}&pageIndex4="+pageIndex+"&params="+selectParams;
		}
	}
}
//删除商品
function deleteProduct(productId,flag){//flag为标记 flag=2 时 删除下架商品 flag=4时 删除违规下架商品
	lconfirm("提醒","确定删除？",function(o){
		var selectParams=$("#goodsSorting").val();//排序的参数
		window.location="${pageContext.request.contextPath}/front/store/frontShopProduct/deleteProduct.action?deleteParams="+flag+"&productId="+productId+"&params="+selectParams;
	});
}
//设置index参数
var setIndex=function(n){
	 n=n.substring(0,n.lastIndexOf("l"));
	 $("#indexFlag").val(n);
}

//更改显示状态isShow
var changeIsShowSate=function(v,id){//v:要更改的值,id:商品id
	//获取四个分页对象的当前页
	var p1=${ph1.pageIndex};
	var p2=${ph2.pageIndex};
	var p3=${ph3.pageIndex};
	var p4=${ph4.pageIndex};
	//index当前索引(tabs定位)
	var i= $("#indexFlag").val();
	//获取排序参数
	var	sp=$("#selectParams").val();
	location.href="${pageContext.request.contextPath}/front/store/frontShopProduct/changeIsShowSate.action?pageIndex1="+p1+"&pageIndex2="+p2+"&pageIndex3="+p3+"&pageIndex4="+p4+"&index="+i+"&isShowSate="+v+"&productId="+id+"&params="+sp;
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
<section class="publicrelative">
<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">产品列表</h3>
	<aside class=" publicBorder BackgroundF2Hui publicPadding10 ">
	<div class="ClearBoth  parent_for_p">
	   <div style="float:left;">
		<p  style="margin-top:3px;">排序：
		</p>
		<p style="margin-top:3px;">
			<input id="selectParams" type="hidden"
				value="<s:property value='params'/>" /> <select
					id="goodsSorting" name="goodsSorting"
					onchange="goodsSorting($('#goodsSorting').val());">
					<option value="Latest" >最新</option>
					<option id="Pricehighestfirst" value="Pricehighestfirst">价格由高到低</option>
					<option id="Pricelowestfirst" value="Pricelowestfirst">价格由低到高</option>
					<!-- <option id="Quantityhighestfirst" value="Quantityhighestfirst">数量由高到低</option>
					<option id="Quantitylowestfirst" value="Quantitylowestfirst">数量低到高</option> -->
					<option id="Soldhighestfirst" value="Soldhighestfirst">销售量由高到低</option>
					<option id="Soldlowestfirst" value="Soldlowestfirst">销售量低到高</option>
				</select>
			</p>
			</div>
			<form action="${pageContext.request.contextPath}/front/store/frontShopProduct/gotoShopProductListPage.action" method="post">
				<p>商品名称：
				<input type="text" style="height:20px;" name="productName" value="${productName}"/>&nbsp;&nbsp;&nbsp;&nbsp;
				商品分类：
				<select  id="productTypeId" name="productTypeId">
	             <option value="-1">-----请选择分类-----</option>
				  <s:iterator value="#application.categoryMap" var="type1">
				  	<option value="<s:property value="#type1.key.productTypeId"/>"  >
				  	<s:property value="#type1.key.sortName"/>
				  	<s:iterator value="#type1.value" var="type2">
					  	<option value="<s:property value="#type2.key.productTypeId"/>"  >
					  		&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type2.key.sortName"/>
					  		<s:iterator value="#type2.value" var="type3">
					  		<option value="<s:property value="#type3.productTypeId"/>" >
					  			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#type3.sortName"/>
					  		</option>
					  		</s:iterator>	
					  	</option>
				  	</s:iterator>
				  	</option>
				  </s:iterator>
	           </select>&nbsp;&nbsp;&nbsp;&nbsp;
				是否显示：
				<select id="isShowSate" name="isShowSate">
					<option value="-1">--请选择--</option>
					<option value="1" <s:if test="isShowSate==1">selected="selected"</s:if> >是</option>
					<option value="0" <s:if test="isShowSate==0">selected="selected"</s:if>>否</option>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="indexFlag" name="index" value="${index}" type="hidden" />
				<input name="pageIndex1" value="<s:property value='ph1.prePageIndex'/>" type="hidden" />
				<input name="pageIndex2" value="<s:property value='ph2.prePageIndex'/>" type="hidden" />
				<input name="pageIndex3" value="<s:property value='ph3.prePageIndex'/>" type="hidden" />
				<input name="pageIndex4" value="<s:property value='ph4.prePageIndex'/>" type="hidden" />
				<input type="submit" value="查询" onclick="changePage('${pageHelper.prePageIndex}');"
								class="publicNoBorder publicPadding2_5 BackgroundPurple ColorWhite1 FontSize14" style="width: 50px;height: 25px;">
				</p>
			</form>
		</div>
	</aside>
	<input name="button" style=" position:absolute;top:170px; border:1px #d65b08 solid;margin-right: 5px;" 
		onclick="batchProcessing();" type="button"
		 class="shopProductList_batchProcessing FontSize14 publicPadding5_10 ColorWhite1 BlackgroundOrange"  value="批量上架/下架"/>
<div class="MarketpalceTree publicMarginTop15 ">
<ul class="FavouriteTab">
	<li class="first-child">
		<a href="javascript:void(0);" class="FontSize14 radius gradient"  name="0l" onclick="setIndex(this.name)">上架商品</a>
	</li>
	<li>
		<a href="javascript:void(0);" class="FontSize14 radius gradient" name="1l" onclick="setIndex(this.name)">下架商品</a>
	</li>
	<li>
		<a href="javascript:void(0);" class="FontSize14 radius gradient" name="2l" onclick="setIndex(this.name)">待审核</a>
	</li>
	<li>
		<a href="javascript:void(0);" class="FontSize14 radius gradient" name="3l" onclick="setIndex(this.name)">违规下架</a>
	</li>
</ul>

<div class="TabParent_lj publicBorder ClearBoth">
</br></br>
<div>
<div id="test" class="publicPadding10">
<form  id="formModule">

<input id="paramsCheckbox" name="paramsCheckbox"  type="hidden" value=""/>
<table width="100%" border="0" class="OrderinforTableL firstChild_Tab">
  <tr class=" publicBorder gradient">
    <td width="3%"><input id="onCheckboxAll" name="onCheckboxAll" type="checkbox"  onclick="selectAllcheckbox('onCheckboxAll','onShelf','offShelf','offCheckboxAll');" value="all"></td>
    <td width="10%">图片</td>
    <td width="15%">商品名称</td>
    <td width="16%">商品分类</td>
    <td width="9%">价格</td>
    <td width="13%">销售量</td>
    <td width="11%">时间</td>
    <td width="10%">显示</td>
    <td width="9%">操作</td>   
  </tr>
  <!-- 遍历店铺上架的商品List -->
  <s:iterator value="OnshelfList">
		  	<tr class="checkbox_tr">
			    <td class="publicNoBorderR publicNoBorderL"><input id="checkboxId_<s:property value='productId'/>" name="onShelf" type="checkbox" value="<s:property value='productId'/>">
			    </td>
			    <td><a target="_blank" title="<s:property value="productFullName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId'/>.html"><img style="height:50px; width:50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></a></td>
<!-- 			    <td><a href=""><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/products/recommend01 (1).png" alt="" class="Order_img"></a></td> -->
			    <td>
			    	<div style="height: 3.5em;word-wrap:break-word;overflow:hidden;">
			    		<a target="_blank" title="<s:property value="productFullName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='productId'/>.html"><s:property value="productFullName"/></a>
			    	</div>
			    </td>
			    <td><s:property value="sortName"/></td>
			    <td><s:property value="salesPrice"/></td>
<!-- 			    <td><s:property value='productId'/></td> -->
			    <td>
			    	<s:if test="totalSales != null">
				    	<s:property value="totalSales"/>&nbsp;件
			    	</s:if>
			    	<s:if test="totalSales == null">
				    	0 &nbsp;件
			    	</s:if>
				</td>
			    <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			    <td>
			    	<input value="1" type="radio" <s:if test="isShow==1">checked="checked"</s:if> onclick="changeIsShowSate(this.value,<s:property value='productId'/>)"/>&nbsp;是</br>
			    	<input value="0" type="radio"  <s:if test="isShow==0">checked="checked"</s:if> onclick="changeIsShowSate(this.value,<s:property value='productId'/>)"/>&nbsp;否
			    </td>
			    <td class="publicNoBorderR"><a href="#" onclick="changePutSaleInfo(<s:property value="productId"/>);" class="ColorBlue">下架</a></td>
		  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="OnshelfList.size>0">
	<div class="pageList strong ClearBoth">
	   <div class="flipEffect">
   			<div style="float:right;">
					<a <s:if test="pageIndex1==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph1.prePageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph3.pageIndex'/>,<s:property value='ph4.pageIndex'/>,1)"</s:else>>上一页</a>
					<s:iterator value="%{ph1.optionalPageIndexList}" var="pageNumber" >
			        	<a href="javascript:;" <s:if test="#pageNumber==pageIndex1">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumber'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph3.pageIndex'/>,<s:property value='ph4.pageIndex'/>,1)"</s:else> ><s:property value="#pageNumber" /></a>
					</s:iterator>
				    <a <s:if test="pageIndex1>=ph1.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph1.nextPageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph3.pageIndex'/>,<s:property value='ph4.pageIndex'/>,1)"</s:else>>下一页</a>
			</div>
		</div>
	</div>
</s:if>
<!--分页 end-->
</form>
</div>
</div>
<div>
<div class="publicPadding10">
<form  id="formModule2">
<input id="paramsCheckbox2" name="paramsCheckbox"  type="hidden" value=""/>
<table width="100%" border="0" class="OrderinforTableL">
  <tr class=" publicBorder gradient">
    <td width="3%"><input id="offCheckboxAll" name="offCheckboxAll" type="checkbox" onclick="selectAllcheckbox('offCheckboxAll','offShelf','onShelf','onCheckboxAll');" value="<s:property value='productId'/>"></td>
     <td width="15%">图片</td>
    <td width="15%">商品名称</td>
    <td width="16%">商品分类</td>
    <td width="9%">价格</td>
<!--     <td width="9%">ProductID</td> -->
    <td width="13%">销售量</td>
    <td width="11%">时间</td>
    <td width="9%">操作</td>   
  </tr>
<!-- 遍历店铺下架的商品List -->
 <s:iterator value="OffshelfList">
		  	<tr class="checkbox_tr">
			    <td class="publicNoBorderR"><input name="offShelf" type="checkbox" value="<s:property value="productId"/>">
			    </td>
			    <td><img title="<s:property value="productFullName"/>" style="height:50px; width:50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></td>
			    <td title="<s:property value="productFullName"/>">
			    <div style="height: 3.5em;word-wrap:break-word;overflow:hidden;">
			    	<s:property value="productFullName"/>
			    </div>
			    </td>
			    <td><s:property value="sortName"/></td>
			    <td><s:property value="salesPrice"/></td>
<!-- 			    <td></td> -->
			    <td>
			    	<s:if test="totalSales != null">
				    	<s:property value="totalSales"/>&nbsp;件
			    	</s:if>
			    	<s:if test="totalSales == null">
				    	0&nbsp;件
			    	</s:if>
				</td>
			    <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
<!-- 			    下架商品的操作 -->
			    <td class="publicNoBorderR">
			    	<a href="${pageContext.request.contextPath}/front/store/frontProduct/getProductInfoByProductId.action?productId=<s:property value='productId'/>&pageIndex2=<s:property value='ph2.pageIndex'/>" class="ColorBlue">编辑</a></br>
			    	<a href="#" onclick="changePutSaleInfo(<s:property value="productId"/>);"  class="ColorBlue">上架</a></br>
			    	<s:if test="confirmation != 0"><!--confirmation为1时可以删除商品  -->
				    	<a href="#" onclick="deleteProduct(<s:property value='productId'/>,2);" class="ColorBlue">删除</a>
			    	</s:if>
			    </td>
		  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="OffshelfList.size>0">
	<div class="pageList strong ClearBoth">
	   <div class="flipEffect">
   			<div style="float:right;">
					<a <s:if test="pageIndex2==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph2.prePageIndex'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph3.pageIndex'/>,<s:property value='ph4.pageIndex'/>,2)"</s:else>>上一页</a>
					<s:iterator value="%{ph2.optionalPageIndexList}" var="pageNumber" >
			        	<a href="javascript:;" <s:if test="#pageNumber==pageIndex2">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumber'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph3.pageIndex'/>,<s:property value='ph4.pageIndex'/>,2)"</s:else> ><s:property value="#pageNumber" /></a>
					</s:iterator>
				    <a <s:if test="pageIndex2>=ph2.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph2.nextPageIndex'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph3.pageIndex'/>,<s:property value='ph4.pageIndex'/>,2)"</s:else>>下一页</a>
			</div>
		</div>
	</div>
</s:if>
<!--分页 end-->
</form>
</div>
</div>
<!-- ================ -->
<!-- 审核中的商品 -->
<div>
<div id="test" class="publicPadding10">
<form  id="formModule3">

<table width="100%" border="0" class="OrderinforTableL">
  <tr class=" publicBorder gradient">
<!--     <td width="3%"><input id="onCheckboxAll" name="onCheckboxAll" type="checkbox"  onclick="selectAllcheckbox('onCheckboxAll','onShelf','offShelf','offCheckboxAll');" value="all"></td> -->
    <td width="15%">图片</td>
    <td width="15%">商品名称</td>
    <td width="16%">商品分类</td>
    <td width="9%">价格</td>
<!--     <td width="9%">ProductID</td> -->
    <td width="13%">销售量</td>
    <td width="11%">时间</td>
<!--     <td width="9%">Operation</td>    -->
  </tr>
  <!-- 遍历审核中的商品List -->
  <s:iterator value="reviewProductList">
		  	<tr class="checkbox_tr">
			    <td style=" text-align: center;" title="<s:property value="productFullName"/>"><img style="height:50px; width:50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></td>
			    <td title="<s:property value="productFullName"/>" style="text-align: center;">
			    <div style="height: 3.5em;word-wrap:break-word;overflow:hidden; text-align: center;">
			    	<s:property value="productFullName"/>
			    </div>
			    </td>
			    <td><s:property value="sortName"/></td>
			    <td><s:property value="salesPrice"/></td>
			    <td>
			    	<s:if test="totalSales != null">
				    	<s:property value="totalSales"/>&nbsp;件
			    	</s:if>
			    	<s:if test="totalSales == null">
				    	&nbsp;件
			    	</s:if>
				</td>
			    <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
<!-- 			    <td class="publicNoBorderR"><a href="#" onclick="changePutSaleInfo(<s:property value="productId"/>);" class="ColorBlue">offshelf</a></td> -->
		  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="reviewProductList.size>0">
	<div class="pageList strong ClearBoth">
	   <div class="flipEffect">
   			<div style="float:right;">
					<a <s:if test="pageIndex3==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph3.prePageIndex'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph4.pageIndex'/>,3)"</s:else>>上一页</a>
					<s:iterator value="%{ph3.optionalPageIndexList}" var="pageNumber" >
			        	<a href="javascript:;" <s:if test="#pageNumber==pageIndex3">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumber'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph4.pageIndex'/>,3)"</s:else> ><s:property value="#pageNumber" /></a>
					</s:iterator>
				    <a <s:if test="pageIndex3>=ph3.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph3.nextPageIndex'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph4.pageIndex'/>,3)"</s:else>>下一页</a>
			</div>
		</div>
	</div>
</s:if>
<!--分页 end-->
</form>
</div>
</div>
<!-- ================ -->
<!-- 违规下架的商品 -->
<div>
<div id="test" class="publicPadding10">
<form  id="formModule4">

<table width="100%" border="0" class="OrderinforTableL">
  <tr class=" publicBorder gradient">
<!--     <td width="3%"><input id="onCheckboxAll" name="onCheckboxAll" type="checkbox"  onclick="selectAllcheckbox('onCheckboxAll','onShelf','offShelf','offCheckboxAll');" value="all"></td> -->
    <td width="15%">图片</td>
    <td width="15%">商品名称</td>
    <td width="16%">商品分类</td>
    <td width="9%">价格</td>
<!--     <td width="9%">ProductID</td> -->
    <td width="13%">销售量</td>
    <td width="11%">时间</td>
    <td width="9%">操作</td>   
  </tr>
  <!-- 遍历店铺上架的商品List -->
  <s:iterator value="violationOffProductList">
		  	<tr class="checkbox_tr">
<!-- 			    <td class="publicNoBorderR"><input id="checkboxId_<s:property value='productId'/>" name="onShelf" type="checkbox" value="<s:property value='productId'/>"> </td>-->
			    
			    <td title="<s:property value="productFullName"/>"><img style="height:50px; width:50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg'/>"/></td>
			    <td title="<s:property value="productFullName"/>">
			    <div style="height: 3.5em;word-wrap:break-word;overflow:hidden;">
			    	<s:property value="productFullName"/>
			    </div>
			    </td>
			    <td><s:property value="sortName"/></td>
			    <td><s:property value="salesPrice"/></td>
<!-- 			    <td><s:property value='productId'/></td> -->
			    <td>
			    	<s:if test="totalSales != null">
				    	<s:property value="totalSales"/>&nbsp;件
			    	</s:if>
			    	<s:if test="totalSales == null">
				    	0&nbsp;件
			    	</s:if>
				</td>
			    <td><s:date name="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
			    <td class="publicNoBorderR"><a href="#" onclick="deleteProduct(<s:property value='productId'/>,4);" class="ColorBlue">删除</a></td>
		  	</tr>
  </s:iterator>
</table>
<!--分页 start-->
<s:if test="violationOffProductList.size>0">
	<div class="pageList strong ClearBoth">
	   <div class="flipEffect">
   			<div style="float:right;">
					<a <s:if test="pageIndex4==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph4.prePageIndex'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph3.pageIndex'/>,4)"</s:else>>上一页</a>
					<s:iterator value="%{ph4.optionalPageIndexList}" var="pageNumber" >
			        	<a href="javascript:;" <s:if test="#pageNumber==pageIndex4">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumber'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph3.pageIndex'/>,4)"</s:else> ><s:property value="#pageNumber" /></a>
					</s:iterator>
				    <a <s:if test="pageIndex4>=ph4.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='ph4.nextPageIndex'/>,<s:property value='ph1.pageIndex'/>,<s:property value='ph2.pageIndex'/>,<s:property value='ph3.pageIndex'/>,4)"</s:else>>下一页</a>
			</div>
		</div>
	</div>
</s:if>
<!--分页 end-->
</form>
</div>
</div>
<!-- ================ -->
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
