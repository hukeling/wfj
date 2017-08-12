<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文章列表</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/help_center.css">
<script type="text/javascript" >
	var keyword="${str}";
	$(function(){
		//初始化关键字
		if(keyword!=""){
			$("#str").val(keyword);
		}
	});

	//查询文章
	function getHelpList(x){
		//防止搜索关键字为中文-乱码-转码
		var str = $.trim(x);
		if(str!=null && str!='' ){
			var v=escape(str);
			re=new RegExp("%","g");
			var newStr=v.replace(re,"_");
			location.href="${pageContext.request.contextPath}/helpFront/gotoSomeHelpFrontList.action?str="+newStr+"&categoryId=${cmsCategory.categoryId}";
		}else{
			$("#str").val("");
            //$("#str").focus();
			//如果关键字为空，跳转至帮助中心首页
			location.href="${pageContext.request.contextPath}/helpFront/gotoHelpFront.action";			
		}
	}
	//查询文章（按回车时）
  	function submitSearch(event){
		var e= event ? event : (window.event ? window.event : null);
		if (e.keyCode == 13){
			getHelpList($("#str").val());
		}
	}
</script>
</head>

<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
  <div class="main_content">
    <div class="home">
      
      <div class="lujing">
	      <a href="${pageContext.request.contextPath}/">首页</a>&nbsp;>&nbsp;<a href="${pageContext.request.contextPath}/helpFront/gotoHelpFront.action">帮助中心</a>
	    	  <s:if test="cmsCategory.categoryName!=null">
	    	  	 &nbsp;>&nbsp;<s:property value="cmsCategory.categoryName"/>
    	  	  </s:if>
      </div>

    </div> 
    
    <div class="maincon_left">
      <div class="help_left">
        <div class="biaoti" style="color:#333333;padding-left:10px;"><s:property value="cmsCategory.categoryName"/></div>
        <div class="topics_ul">
            <ul>
            <s:iterator value="articleList">
              <li>&#149; <a href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value="articleId"/>"><s:property value="title"/></a></li>
            </s:iterator>
            </ul>
        </div>
      </div>
      <div class="mainconl_first" style="width:198px; border-left:1px #CCCCCC solid; border-right:1px #CCCCCC solid; border-bottom:1px #CCCCCC solid;">
        <div class="biaoti" style="color:#333; padding-left:12px; width:191px;">联系我们</div>
        <div class="biaoti_line" style="width:198px;"></div>
        <div class="question">如果您有其他的问题请邮件联系我们:<p class="mail">jingjimall2014@163.com</p></div>
        
      </div>
      
    </div>
	<div class="maincon_right" >
      <div class="help_con">
      <div class="helpzz" style="height:15px; line-height:15px;  font-family:Arial;font-size:13px;font-weight:bold;font-style:normal;text-decoration:none;color:#333333; padding-left:10px; padding-bottom:10px;">帮助中心  - 有什么我们可以帮助您的吗?</div> 
     <div class="ClearBoth" style=" padding-left:10px;">
       <div class="serchtex" style="float:left; width:350px; height:25px;  line-height:25px;border:1px #7f9db9 solid;">
			<input onkeydown="submitSearch(event)" id="str" name="str" type="text" class="texxx" style="width:330px;height:25px; line-height:25px; border:0; color:#999; padding-left: 5px;">
       </div>
       <div class="suosouan" style="width:89px; height:26px; float:left; margin-left:16px; display:inline;">
	       <a style="text-decoration: none;" onclick="getHelpList(document.getElementById('str').value);" >
	       <input type="button" value="搜索" class="BackgroundRed ColorWhite1 publicNoBorder FontSize14" style="width:89px;height:26px; "></a>
       </div>
     </div>
         <div class="neirong" style="margin-top:18px;padding-left:10px;">
         	<h3 class="sd" style="margin-bottom:15px;">关键字 &quot;<font style="color: red;"><s:property value="str" /></font>&quot;</h3>
         	<ul>
         		<s:iterator value="cmsArticleList" id="s">
            	<li style="margin-bottom:10px;">
            		<h2 class="mail" style="margin-bottom:3px;font-size: 16px;"><a href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value="articleId"/>" style="color: blue;"><s:property value="title" /></a></h2>
            		<span class="gxsj" style="margin-bottom:10px;">更新日期: <s:property value="updateTime" /></span>
<%--                	<p class="rf"><s:property value="brief" /></p>--%>
                </li>
         		</s:iterator>
            </ul>
         </div>
     </div>
    </div>  
    
  </div>

<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
