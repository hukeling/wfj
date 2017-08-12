<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="cmsArticle.title"/>_<s:property value="cmsCategory.categoryName"/>_SHOPJSP</title>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/help_center.css"/>
<%@ include file="../include/head.jsp"%>
</head>
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
  <div class="main_content">
    <div class="home">
      <div class="lujing">
      <a href="${pageContext.request.contextPath}/">首页</a>&nbsp;>&nbsp;<a><s:property value="cmsCategory.categoryName"/></a>
      </div>
    </div> 
    <div class="maincon_left">
      <div class="help_left">
        <div class="biaoti" style="color:#333333"><s:property value="cmsCategory.categoryName"/></div>
        <div class="biaoti_line"></div>
        <div class="topics_ul">
            <ul>
            <s:iterator value="articleList" var="article">
              <li style="line-height:25px;">&#149;
	                <s:if test="title.length()>14">
		              	<a title="<s:property value='title'/>" href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value="articleId"/>"><s:property value="title.substring(0,14)"/></a>
	              	</s:if>
	              	<s:else>
		              	<a title="<s:property value='title'/>" href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value="articleId"/>"><s:property value="title"/></a>
	              	</s:else>
               </li>
            </s:iterator>
            </ul>
        </div>
      </div>
      <div class="mainconl_first" style="width:198px; border-left:1px #CCCCCC solid; border-right:1px #CCCCCC solid; border-bottom:1px #CCCCCC solid;">
        <div class="biaoti" style="color:#333; padding-left:12px; width:191px;">联系我们</div>
        <div class="biaoti_line"></div>
        <div class="question">如果您有其他的问题请邮件联系我们:<p class="mail">jingjimall2014@163.com</p></div>
      </div>
    </div>
    	<div class="neirong" style="margin-top:10px;margin-left: 230px">
         	<h2 class="sd"><s:property value="cmsArticle.title"/></h2>
				<span><s:property value="cmsArticle.content" escape="false"/></span>         
         </div>
  </div>
<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
