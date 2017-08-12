<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>商品评价列表</title>
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css">
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
	<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/style.css"/>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.tools.min.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollUp.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js"></script>
	<script type="text/javascript">
		//控制父层iframe的高度
		$().ready(function(){
			var thisPageHeight = document.body.clientHeight;
			$(window.parent.document).find("IFRAME").attr("height",thisPageHeight);
			window.parent.setHeight(thisPageHeight);
		});
	</script>
	<style type="text/css">
	    .smsp2{height:21px;background:url(/common/shop/front/images/bgbgyin.png);border-top:0;border-bottom:0px;border-left:1px solid #ccc;border-right:1px solid #ccc;}
		.pjlino{float:left;font-size: 16px;padding:0px 20px;line-height: 35px;}
		.pjlino a{text-decoration:none;}
		.pjlino a:hover{color:#d01743 ;}
		.pjliyes{float:left;font-size: 16px;padding:0px 20px;line-height: 35px;border-top:2px #d01743  solid;border-left:1px #CCC solid;border-right:1px #CCC solid;border-bottom:none;background:#fff;color:#d01743 ;}
		.pjliyes a{text-decoration:none;color:#d01743 ;}
	</style>
</head>
<body >
	
	<s:if test="#request.cusRevList == null || #request.cusRevList.isEmpty()">
		<div style="text-align: center;height:50px;padding-top:20px;">
			<span style="color:#d01743 ;">暂时还没有评价数据</span>
		</div>
	</s:if><s:else>
		<s:iterator value="#request.cusRevList" var="cr">
			<div style="width:930px;margin-top: 10px;margin-left: 10px;position: relative; margin-bottom:30px;">
		      	<div style="width: 120px;position: absolute;top:0px;left:10px;height:150px;">
		      	    <a style="margin-left:30px;display:block;width:60px;height:60px;padding:3px;cursor:default ;"><img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" style="width:67px;height:67px;border:1px solid #ccc;padding:2px 2px;margin-bottom:5px;"  src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#cr.photoUrl'/>"/></a>
		      	    <a style="display:block;line-height:16px;width:120px;text-align:center;padding-top:10px; cursor:default ;color:#999;text-decoration: none;">
		      	    <s:if test="#cr.isAnonymous==1">
						<s:property value="%{#cr.loginName.substring(0,1)}"/>****<s:property value="%{#cr.loginName.substring(#cr.loginName.length()-1)}"/>
					</s:if>
					<s:else>
						${cr.loginName}
					</s:else>
		      	    </a>
		      	</div>
		      	<div style="margin-left:150px;width:790px;border:1px  #D0E4C2 solid;height:auto; background:#FCFFFA; padding-bottom:20px;">
		      		<div style="height:22px; margin-left:20px;">
			      		<div style="height:22px;padding-top:5px;float:left;">
			      			<s:if test="#cr.level==1">
				      			好评
			      			</s:if><s:elseif test="#cr.level==0">
				      			中评
			      			</s:elseif><s:elseif test="#cr.level==-1">
				      			差评
			      			</s:elseif>
			      		</div>
			      		<div style="float:right;padding-top:9px;margin-right:25px;">
			      			<s:date name="#cr.createTime" format="yyyy-MM-dd HH:mm:ss" />
			      		</div>
		      		</div>
		      		<hr style="width:755px;color:#D0E4C2;"/>
		      		<div style="width: 790px;height: auto;padding-top:5px;padding-bottom:5px;">
		      			<div style="margin:5px 0;overflow:hidden; height:auto; line-height: 24px;
		      			
		      			">
		      			    <span style="color:#9C9A9C; float:left;width:120px;line-height:18px; text-align:right;">
		      			    	 客户评价 ：
		      			     </span>
		      			    <div style="width:660px;float:left;line-height:18px;color:#666;"><s:property value='#cr.content'/></div>
		      			</div>
		      			<s:if test="#cr.backState==2">
			      			<div style="margin:7px 0;overflow:hidden;">
			      			    <span style="color:#9C9A9C; float:left;width:120px;line-height:18px; text-align:right;"> 商 城 客 服 回 复 ：</span>
			      			    <div style="width:660px;float:left;line-height:18px;color:#666;"> <s:property value='#cr.explain'/></div>
			      			</div>
		      			</s:if>
		      			<div style="clear:both;"></div>
		      		</div>
		      	</div>
		      	<div style="position:absolute;top:15px;left:137px;width:14px;height:25px;background:url(/common/shop/front/images/pinlunjt.png);"></div>
			 </div>
		</s:iterator>
		 	<form action="${pageContext.request.contextPath}/productsdetail/evaluateTypeList.action?productId=<s:property value='productId'/>&evaluateType=<s:property value='evaluateType'/>" id="formModule" method="post">
			 	<!--分页 start-->
				<s:if test="#request.cusRevList.size>0">
					<div class="pageList strong ClearBoth" style="margin-top: 0px;">
					     <jsp:include page="../../util/splitPage.jsp" />
					</div>
				</s:if>
				<!--分页 end-->
		 	</form>
	</s:else>
	
</body>
</html>