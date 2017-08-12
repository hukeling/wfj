<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
<title>热卖频道</title>
<%@ include file="../include/head.jsp"%>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/Hot_channel.css" rel="stylesheet" type="text/css">
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" >
	$().ready(function(){
		//Lazy Load 延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn"
		});
	});
</script>
</head>

<body style="position:relative;">
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
<div class="main_content">
    <div class="home">
      <div class="home_img"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/u51_normal.png"/></div>
      <div class="lujing">
         <span><a href="${pageContext.request.contextPath}/">首页</a>&nbsp;&gt;&nbsp;热销</span>

      </div>
    </div> 
    <div class="hot_top">
    	<div class="top_noe">
       	<div class="ling"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/hot_channel_03.jpg" > </div>
           <div class="biaoge">
           	<div class="one">
           	<s:iterator value="map" id="m">
               <div class="tab1" style="border-right:1px solid #cccccc;border-bottom:1px #CCCCCC solid;">
               <a href="${pageContext.request.contextPath}/hotsale/gotoHotSaleList.action?productTypeId=<s:property value="#m.key.productTypeId" />"><s:property value="#m.key.sortName" /></a></div>
            </s:iterator>
            </div>
    	   </div>
    	</div>
    	<s:iterator value="productInfoList">
	      <div class="top_two">
	      	<div class="tu_titile"><span style="float:left; width:32px; height:29px;">
	      		<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/hot_channel_09.jpg" class="tub"></span>
	      		<span class="hot">热销</span><span class="orders"><s:property value="totalSales" />  订单</span>
	        	<div class="clear"></div>
	        </div>
	        <div class="tu">
	        <a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
	        <img title="<s:property value='productName' />" class="lazy" src="${fileUrlConfig.visitFileUploadRoot}<s:property value='logoImg'/>"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg' />" style="display:block;height: 240px;width: 240px"/>
	        </a>
	        	<p class="tu_bot"><s:property value='specification' /></p>
		        <a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
	        		<p class="cash"><span style="color:#000;">&nbsp;<s:property value='productName' /></span></p>
		        </a>
	            <p class="cash"><span class="jiage">&nbsp;￥<s:property value='salesPrice' /></span></p>
	        </div>     
	      </div>
     	</s:iterator>
        <div class="clear"></div>        
    </div>
    <div class="hot_line"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/hot_channel_26.jpg"></div>
  	<div class="fenge">
    	<span class="cl"><s:property value="productType.sortName" /></span>
    </div>
    
    <div class="hot_con">
    	<s:iterator value="mapList" status="sta">
      		<div class="top_two" style="margin-left:0;margin-right: 10px;margin-bottom: 10px;">
         	<div class="tu_titile">
	          <span class="zuobiao">
	          	<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/hot_channel_30.jpg" class="tubiao">
	          </span>
	          <span class="hot">TOP<s:property value="#sta.index+1"/>.</span>
	          	<span class="orders"><s:property value="totalSales" /> 订单</span>
	            <div class="clear"></div>
            </div>
	          <div class="tu">
	          <a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
	          	<img title="<s:property value='productName' />" class="lazy" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='logoImg' />" style="display:block;height: 240px;width: 240px"/>
	          </a>
	              <p class="tu_bot"><s:property value='specification' /></p>
		          <a target="_blank" href="${pageContext.request.contextPath}/productInfo/<s:property value="productId" />.html">
		              <p class="cash"><span style="color:#000;">&nbsp;<s:property value='productName' /></span></p>
		          </a>
	              <p class="cash"><span class="jiage">&nbsp;￥<s:property value='salesPrice' /></span></p>
	          </div> 
      		</div>
    	</s:iterator>
        <div class="clear"></div>
    </div>

<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
