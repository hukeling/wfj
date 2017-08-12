<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>留言列表</title>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/information/thaddsy.css" rel="stylesheet"  type="text/css" />
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/style.css" rel="stylesheet"  type="text/css" />
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/information/jquery-1.4.2.min.js"></script>
<script>
	//删除留言
	function deleteComment(id){
		$.post("${pageContext.request.contextPath}/front/store/infoMation/deleteComment.action",{ids:id},function(data){
			if(data){
				if(data.success==true){
					alert("删除成功！");
					location.reload();
				}else{
					alert("删除失败！");
				}
			}
		},'json');
	}
</script>
<style>
.list_head{width:984px; height:auto; overflow:hidden; border-bottom:1px #d7d7d7 solid; margin:15px 0;}
</style>
</head>
<body>
  <!--add_header-->
  <div class="add_header">
    <div class="add_headcon">
     <h1 class="fl" style=" padding-top:18px;">
          <a href="${pageContext.request.contextPath}/">
          <img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/logo.png" style="width:155px; height:53px;"/>
          </a>
        </h1>
      <h2>${shopInfo.shopName}</h2>
    </div>
  </div>
  
 <!--add_menu-->
   <div class="add_menu">
    <div class="addmen_con">
      <ul>
        <li class="addleft_border"><a href="${pageContext.request.contextPath}/homeModule/getMore.action?shopInfoId=${shopInfo.shopInfoId}">资讯首页</a></li>
        <s:iterator value="#request.informationCategoryList">
	        <li class="addleft_border"><a href="${pageContext.request.contextPath}/homeModule/getMore.action?shopInfoId=${shopInfo.shopInfoId}&categoryId=${categoryId}">${shopProCategoryName }</a></li>
        </s:iterator>
      </ul>
    </div>
  </div>
  
  <div class="conten_lj"><a href="${pageContext.request.contextPath}/homeModule/getMore.action?shopInfoId=${shopInfo.shopInfoId}" style="color:#900000;">资讯首页</a> >> <a href="${pageContext.request.contextPath}/homeModule/getMore.action?shopInfoId=${shopInfo.shopInfoId}&categoryId=${categoryId}" style="color:#900000;">${informationCategory.shopProCategoryName }</a> >> ${information.title }</div>
  
 <!--main-->
  <div class="add_main" style="width:984px; height:auto; border:1px #d7d7d7 solid; margin-top:10px; margin-bottom:15px; border-bottom: 0;">
  
  
  	<s:iterator value="#request.icList" status="s">
	     <div class="list_head">
	         <p><a href="javascript:;"><img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${photoUrl}" /></a></br><a style="color:#1d53bf;cursor:default ;text-decoration: none;">${loginName }</a></p>
	         <p id="pp_right">
	         ${content}
	         </p>
	         <div class="huifu"><s:property value='#s.index+1'/>楼&nbsp;<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
	          <a href="javascript:deleteComment('${discussId }');" style="color:#1d53bf;">删除</a>
	         </div>
	     </div>
  	</s:iterator>	
     
     


  </div>
  <!--分页-->
   	  <form id="formModule" action="${pageContext.request.contextPath}/front/store/infoMation/previewComment.action?articleId=${articleId}" method="post">
		<s:if test="#request.icList.size>0">
			<div class="pageList strong ClearBoth" style="margin-right: 180px;">
			     <jsp:include page="../../util/splitPage.jsp" />
			</div>
		</s:if>
     </form>
  <!--分页 end-->
 
  <div class="foot">
    <div>SHOPJSP版权所有  京ICP证 100908号</div>
  </div>
</body>
</html>




