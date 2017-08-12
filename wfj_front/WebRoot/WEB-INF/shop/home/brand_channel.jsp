<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragram" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<meta http-equiv="expires" content="0"/>  
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css" />
<title>${application.homekeybook['homeSeoTitle3'][0].value}</title>
<%@ include file="../include/head.jsp"%>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet"/>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/brand_channel.css" rel="stylesheet" type="text/css"/>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
	<style>
		body, html { 
			 min-height:101%;
			 }
	</style>
	<script type="text/javascript">
		$().ready(function(){
			//Lazy Load 延迟加载
			$("img.lazy").lazyload({
				effect : "fadeIn"
			});
		});
	</script>
</head>
<body>
<%@ include file="../include/header.jsp"%>
<div class="clear"></div> 
<div class="margin-center PublicWidth1004">
<input type="hidden" id="customerId" value="<s:property value="#request.session.customer.customerId"/>"/>
<!--mainClass-->
<div class="main_content" style="width:1230px;">
	<div class="lujing2">
      <div class="url">
          <div  style="float:right; margin-left:30px;">
           </div>
          <div class="clear"></div>
     </div>
    </div>
    <div class="biaotou" style="width:1210px; height:47px; padding-bottom:35px;">
    	<div class="bran1"></div>
    	<div class="bran2"></div>
    	<div class="bran1"></div>
        
    </div>
<!--     		推荐品牌 -->
    <div class="tu_top" style="width:1230px;">
    	<ul style="width:1230px; margin-left:-5px;">
    		<s:iterator value="recommendBrandList">
	        	<li>
		        	<a href="javascript:brandLink('<s:property value='brandName'/>');">
		        		<img height="100" width="250" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"  src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brandBigImageUrl'/>"/>
		        	</a>
		        	<s:if test="brandName.length()>13">
			        	<a title="<s:property value='brandName'/>" href="javascript:brandLink('<s:property value='brandName'/>');"><s:property value='brandName.substring(0,13)'/></a>
		        	</s:if>
		        	<s:else>
			        	<a title="<s:property value='brandName'/>" href="javascript:brandLink('<s:property value='brandName'/>');"><s:property value='brandName'/></a>
		        	</s:else>
	        	</li>
	
    		</s:iterator>
            <div class="clear"></div>
        </ul>
    </div>
    <!-- 不推荐的品牌 暂时不启用 当前页面只展示推荐的品牌 后边可能做分页处理 -->
   <%--  <div class="biaozhi">
   	<div class="toulan">
   	  </div>
<!-- 其他品牌 -->
        <div class="tu_top" style="width:1230px;">
        	<ul style="width:1230px; margin-left:-5px;">
        		<s:iterator value="othersBrandList" status="st">
        			<s:if test="#st.Index!=5">
		                <li>
			                <a href="javascript:brandLink('<s:property value='brandName'/>');">
			                	<img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" class="lazy" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brandBigImageUrl'/>" width="153" height="153" />
			                </a>
			               <s:if test="brandName.length()>13">
			        	  		 <a title="<s:property value='brandName'/>" href="javascript:brandLink('<s:property value='brandName'/>');"><s:property value='brandName.substring(0,13)'/></a>
				        	</s:if>
				        	<s:else>
					        	<a title="<s:property value='brandName'/>" href="javascript:brandLink('<s:property value='brandName'/>');"><s:property value='brandName'/></a>
				        	</s:else>
		                </li>
        			</s:if>
        			<s:else>
		                <li>
			                <a href="javascript:brandLink('<s:property value='brandName'/>');">
			                	<img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" class="lazy" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brandBigImageUrl'/>" width="153" height="153" />
			                </a>
			                <s:if test="brandName.length()>13">
			        	  		 <a title="<s:property value='brandName'/>" href="javascript:brandLink('<s:property value='brandName'/>');"><s:property value='brandName.substring(0,13)'/></a>
				        	</s:if>
				        	<s:else>
					        	<a title="<s:property value='brandName'/>" href="javascript:brandLink('<s:property value='brandName'/>');"><s:property value='brandName'/></a>
				        	</s:else>
		                </li>
        			</s:else>
        		</s:iterator>
                <div class="clear"></div>
            </ul>
        </div>        
    </div> --%>
    
</div>

    <!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
