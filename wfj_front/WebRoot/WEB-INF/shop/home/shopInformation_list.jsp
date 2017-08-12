<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../include/head.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${application.homekeybook['homeSeoTitle5'][0].value}</title>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/youhuijuan_channel.css" type="text/css" rel="stylesheet">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/brand_channel.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/sales_style.css">
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/WookMark.css">
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" >
	$().ready(function(){
		//Lazy Load 延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn"
		});
	});
// 初始化分类css
	$(function(){
		$("div").removeClass("category");
		$("front").removeClass("frontClass");
		var v='${shopCategoryId}';
		if(v==""){
			$("#category_All").addClass("category");
			$("#front_All").addClass("frontClass");
		}else{
			$("#category_"+v).addClass("category");
			$("#front_"+v).addClass("frontClass");
		}
	});
	//根据分类查询店铺
	function searchByCategory(obj){
		window.location.href=$(obj).attr("url");
	}
	//查询更多资讯
	function getMore(shopInfoId){
		var url="${pageContext.request.contextPath}/homeModule/getMore.action?shopInfoId="+shopInfoId;
		window.open(url,"_blank");
	}
	
	//阅读全文
	function getInfo(shopInfoId,articleId){
		var url="${pageContext.request.contextPath}/homeModule/changeClickNum.action?shopInfoId="+shopInfoId+"&articleId="+articleId;
		window.open(url,"_blank");
	}
</script>
<!--Shop directory contents-->
<style type="text/css">
.codeCatalog{width:1210px;height:auto;overflow:hidden; margin-bottom:15px;}
.singleDirectory{padding:10px 0 0 8px;width:394px;height:175px;border-bottom:1px solid #dcdcdc;border-right:1px solid #dcdcdc;margin-right:-1px;float:left;}
.connectEnlarge{ display:block;float:left;width:160px;height:160px;}
.connectEnlarge img{border:1px #f0f0f0 solid;}
.dtails{width:221px;float:left; margin-left:13px;color:#999;}
.dtails a.titledirectory{ display:block;color:#333;font-size:18px;}
.titleBottom {overflow:hidden;height:28px;padding-top:13px;}
.titleBottom span,.titleBottom a{ display:block; float:left;}
.titleBottom a.enterYin{color:#0099FF; text-decoration:underline;}
.titleBottom span.levelStar{ margin:0 7px;}
.titleBottom span.levelStar a{width:13px;height:25px;padding-top:0px;}
.figureShows a{ margin-right:3px;width:47px;height:47px;}
.figureShows a img{width:47px;height:47px;}
.category{background: #990000;}
.frontClass{color:#ffffff;}
.biaoge_head{color:#ffffff;}
/* 资讯样式  begin */
.Tit_06 {
    border-bottom: 2px solid #C9C9C9;
    color: #1F3B7B;
    height: 28px;
}
.Tit_06 .t_name {
    border-bottom: 2px solid #0C3694;
    float: left;
    font: bold 17px/28px "Microsoft YaHei","微软雅黑","宋体";
    height: 28px; line-height:28px;
    margin-bottom: -2px;
    padding-right: 10px;
/*     position: relative; */
}
.Tit_06 a:link, .Tit_06 a:visited {
    color: #1F3B7B;
    text-decoration: none;
}

.Tit_06 a:hover{
    color: #1F3B7B;
    text-decoration:underline;
}


.blk_09 {
    position: relative;
}
.list_14 {
    font-size: 14px;
    line-height: 24px;
    padding: 7px 0 5px;
    height:135px;
}
.list_14 li {
/*    background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/information/news_m_04.png") no-repeat scroll -475px -615px rgba(0, 0, 0, 0); */
    height: 24px;
    overflow: hidden;
/*     padding-left: 13px; */
}
.clearfix:after {
    clear: both;
    content: ".";
    display: block;
    height: 0;
    visibility: hidden;
}

.list_14 .time1 {
    color: #9FADCD;
    float: right;
    font-family: Arial;
    font-size: 12px;
}


/* 资讯样式end */
</style>
</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
  <div class="main_content" style="margin:0 auto;">
    <div class="home">
      <div class="home_img"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/images/u51_normal.png" style="padding-bottom:2px;"></div>
      <div class="lujing">
        <a href="${pageContext.request.contextPath}/">首页</a> &gt;资讯站
      </div>
    </div>
    <div class="make_hold" style="height: auto;">
   
	  <div class="biaoge" style="height:auto; width:1206px; border-top:1px #ccc solid;">
	  
       	<div class="biaoge_head" style="height:auto; width:1206px;  border-bottom:0;">
   			 <div onclick="searchByCategory(this)" url="${pageContext.request.contextPath}/homeModule/gotoInforMationPage.action?headerType=information"  id="category_All" class="biaogeh_left gexian biaoge_head" style="background: #d01743;  width: 186px;"><a style="text-decoration:none; color: #fff;"><font id="front_All">所有店铺</font></a></div>
       		<s:iterator value="shopTypeList">
       			<div style="border-left: 1px #ccc solid; width: 186px;" onclick="searchByCategory(this)" url="${pageContext.request.contextPath}/homeModule/gotoInforMationPage.action?shopCategoryId=<s:property value='shopCategoryId' />&headerType=information" class="biaogeh_left biaoge_head" id="category_<s:property value='shopCategoryId'/>"><a style="text-decoration:none;"><front id="front_<s:property value='shopCategoryId'/>"><s:property value="shopCategoryName" /></front></a></div>
   			</s:iterator>
         </div>
	  </div> 
	</div>
	<br>
<!-- 	店铺目录 -->
<s:if test="#request.mapList.size>0&&#request.mapList[0].shopInfoId!=null">
	<div class="margin-center PublicWidth1004">
		<div class="codeCatalog">
			<div style="width:1210px;">
				<s:iterator value="#request.mapList" status="s">
					<div style="float:left;width:380px;padding: 0 0 0 20px;">
						<div id="blk_fxzsup_01" class="Tit_06">
				        	  <div class="t_name">${shopName }</div>
				        	  <div style="float:right; line-height: 28px;"><a href="javascript:getMore(${shopInfoId });">更多资讯>></a></div>
				        </div>
				        <div id="blk_fxzs_01">
				          <ul data-sudaclick="gn2_list_03" class="list_14">
				          	<s:iterator value="informationList">
							    <li><span style="float:left; vertical-align:middle; padding-right:5px; color:#0c3694;">▪</span><span class="time1" style="width:70px;word-wrap:break-word;overflow:hidden; color:#1d53bf;"> ${createTime }</span><div style="overflow:hidden; text-overflow:ellipsis; white-space:nowrap;width:280px;"><a class="color-red" href="javascript:getInfo(${shopInfoId},${articleId});"><s:property value="title"/></a></div></li>
				          	</s:iterator>
				          </ul>
				        </div>
			        </div>
				</s:iterator>
			</div>
		</div>
	</div>
	    <form id="formModule" action="${pageContext.request.contextPath}/homeModule/gotoInforMationPage.action?headerType=information&shopCategoryId=${shopCategoryId}" method="post">
	     
	    	<!--分页 start-->
			<s:if test="#request.mapList.size>0">
			<div class="pageList strong ClearBoth">
			     <jsp:include page="../../util/splitPage.jsp" />
			</div>
			</s:if>
			<!--分页 end-->
	    </form>
</s:if>
</div>
<!--//mainClass-->
</div>

<%@ include file="../include/footer.jsp"%>
</body>
</html>
