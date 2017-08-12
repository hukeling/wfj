<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
<title>品牌列表</title>
<%@ include file="../include/head.jsp"%>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/brand_directory.css" type="text/css" rel="stylesheet">
</head>
<script type="text/javascript">
$(document).ready(function(){
	$("a").removeClass("allOne");
	var miaodianValue=$("#maodian").val();
	$("#"+miaodianValue).addClass("allOne");
});
function queryByFlag(flag){
	if(flag=='All'){
		window.location="${pageContext.request.contextPath}/brandDirectory/gotoBrandDirectoryPage.action?flag="+flag;
	}else{
		window.location="${pageContext.request.contextPath}/brandDirectory/queryByFlag.action?flag="+flag;
	}
}
//进入用户中心
function gotoAccountCenter(){
	var obj = $("#customerId").val();
		if(obj != ""){
			location.href="${pageContext.request.contextPath}/front/customer/index.action";
		}else{
//			lalert("提示","请登录！");
		} 
}

//品牌墙链接function 
function brandLink(brandName){//参数为品牌名称
	$("#searchContent").val(brandName);
	searchImg();
}
</script>
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
  <div class="main_content">
  <input type="hidden" id="customerId" value="<s:property value="#request.session.customer.customerId"/>"/>
    <!--二级链接-->
    <div class="home">
        <div class="fl homeMork">
            <div class="home_img" style="padding-top:0px; display:inline;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/bj_img/u51_normal.png" style="padding-bottom:2px; "/></div>
            <div class="lujing">
              >品牌
            </div>
        </div>
           <div class="url">
          <div class="clear"></div>
     </div>
        
    </div>
    <!--字母选择-->
    <div class="selectLetters mt15 mb15">
    	<input id="maodian" name="maodian" type="hidden" value="${flag }"/>
       <ul>
           <li class="fl all"><a id="All" class="allOne" href="javascript:queryByFlag('All');">全部</a></li>
           <li class="fl"><a id="NUM" href="javascript:queryByFlag('NUM');">0-9</a></li>
           <li class="fl"><a id="A"  href="javascript:queryByFlag('A');">A</a></li>
           <li class="fl"><a id="B" href="javascript:queryByFlag('B');">B</a></li>
           <li class="fl"><a id="C" href="javascript:queryByFlag('C');">C</a></li>
           <li class="fl"><a id="D" href="javascript:queryByFlag('D');">D</a></li>
           <li class="fl"><a id="E" href="javascript:queryByFlag('E');">E</a></li>
           <li class="fl"><a id="F" href="javascript:queryByFlag('F');">F</a></li>
           <li class="fl"><a id="G" href="javascript:queryByFlag('G');">G</a></li>
           <li class="fl"><a id="H" href="javascript:queryByFlag('H');">H</a></li>
           <li class="fl"><a id="I" href="javascript:queryByFlag('I');">I</a></li>
           <li class="fl"><a id="J" href="javascript:queryByFlag('J');">J</a></li>
           <li class="fl"><a id="K" href="javascript:queryByFlag('K');">K</a></li>
           <li class="fl"><a id="L" href="javascript:queryByFlag('L');">L</a></li>
           <li class="fl"><a id="M" href="javascript:queryByFlag('M');">M</a></li>
           <li class="fl"><a id="N" href="javascript:queryByFlag('N');">N</a></li>
           <li class="fl"><a id="O" href="javascript:queryByFlag('O');">O</a></li>
           <li class="fl"><a id="P" href="javascript:queryByFlag('P');">P</a></li>
           <li class="fl"><a id="Q" href="javascript:queryByFlag('Q');">Q</a></li>
           <li class="fl"><a id="R" href="javascript:queryByFlag('R');">R</a></li>
           <li class="fl"><a id="S" href="javascript:queryByFlag('S');">S</a></li>
           <li class="fl"><a id="T" href="javascript:queryByFlag('T');">T</a></li>
           <li class="fl"><a id="U" href="javascript:queryByFlag('U');">U</a></li>
           <li class="fl"><a id="V" href="javascript:queryByFlag('V');">V</a></li>
           <li class="fl"><a id="W" href="javascript:queryByFlag('W');">W</a></li>
           <li class="fl"><a id="X" href="javascript:queryByFlag('X');">X</a></li>
           <li class="fl"><a id="Y" href="javascript:queryByFlag('Y');">Y</a></li>
           <li class="fl"><a id="Z" href="javascript:queryByFlag('Z');">Z</a></li>
          
       </ul>    
    </div>
    
    <!--广告图片-->
    <div class="adImage mb35">
    	<s:iterator value="recommendBrandList" status="st">
    		<s:if test="#st.Index!=2">
		        <a class="fl db mr19" href="javascript:brandLink('<s:property value='brandName'/>');">
		            <img height="120" width="320"  src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brandBigImageUrl'/>"/>
		        </a>
    		</s:if>
    		<s:else>
		        <a class="fl db" href="javascript:brandLink('<s:property value='brandName'/>');">
		            <img height="120" width="320"   src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brandImageUrl'/>"/>
		        </a>
    		
    		</s:else>
    	</s:iterator>
    </div>
    
    <!--品牌系列-->
    <s:iterator value="map" id="a">
     <s:if test="#a.key.substring(0,3)=='num'">
		    <div class="seriesA oh">
		        <div class="seriesATitle pt10 mb17">
		          <div class="lineBlack"><span class="PictureA" style="background:#cc1b1b; color:#FFF; text-align:center; font-size:40px; line-height:50px; font-weight:1000">0-9</span></div>
		          <div class="PictureA"></div>
		        </div>        
		        <div class="seriesAContent">
		        	<s:iterator value="#a.value">
		            <dl class="fl">
		                <dt><a class="db mr13" href="javascript:brandLink('<s:property value='brandName'/>');"><img height="120" width="320"   src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brandImageUrl'/>"/></a>
		                </dt>
		                <dd  class="mt15"><a href="javascript:brandLink('<s:property value='brandName'/>');">${brandName}</a></dd>
		            </dl>
	            </s:iterator>
		        </div>
		    </div>
     </s:if>
     <s:else>
     	 <div class="seriesA oh">
	        <div class="seriesATitle pt10 mb17">
	          <div class="lineBlack"><span class="PictureA" style="background:#cc1b1b; color:#FFF; text-align:center; font-size:50px; line-height:50px; font-weight:1000"><s:property value="#a.key.substring(0,1)"/></span></div>
	          <div class="PictureA"></div>
	        </div>        
	        <div class="seriesAContent">
	        	<s:iterator value="#a.value">
	            <dl class="fl">
	                <dt><a class="db mr13" href="javascript:brandLink('<s:property value='brandName'/>');"><img height="120" width="320" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brandImageUrl'/>"/></a>
	                </dt>
	                <dd class="mt15"><a href="javascript:brandLink('<s:property value='brandName'/>');">${brandName}</a></dd>
	            </dl>
            </s:iterator>
	        </div>
	    </div>
     </s:else>
    </s:iterator>
    </div>
         
<!--//mainClass-->

</div>

<%@ include file="../include/footer.jsp"%>
</body>
</html>
