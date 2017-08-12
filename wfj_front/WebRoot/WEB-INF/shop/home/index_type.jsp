<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <title>${application.homekeybook['homeSeoTitle1'][0].value}</title> -->
<title>SHOPJSP</title>
<%@ include file="../include/head.jsp"%>
<!-- 引入列表样式 -->
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/zhjiaju_list.css" />
<!-- 固定层js -->
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/jquery.tmailsilder.v2.js"></script>
</head>
<body>
<!-- 将头部通用部分引入首页 -->
<%@ include file="../include/header.jsp"%>
<script type="text/javascript">
	$(function(){
		var sanjiao = $(".sanjiao");//三角1
		var sanjiao2 = $(".sanjiao2");//三角2
		$('.item').hover(function(){
			var index = $('.item').index(this);//获取当前位置 第几个
			var h3Height = $(".item").find("h3").height()/2;//获取h3标签的高度的一半 如h3高38px;那么我们这里就取一半19px;
			var tmp = h3Height*index*2+h3Height;
			var cha = Number(tmp)+Number(29);
			sanjiao.css({opacity:1,top:cha+"px"});
			sanjiao2.css({opacity:1,top:cha+"px"});
		},function(){
			sanjiao.css({opacity:0,top:"29px"});
			sanjiao2.css({opacity:0,top:"29px"});
		})
	});
</script>
<!-- 页面中间内容 -->
<div class="zhong">

<div class="zhong-1">
	<!-- 左侧悬浮分类数据 -->
	<div class="zuo" style="width:auto; margin-left:57px;">
		<div class="allsort" style="height:auto; float:none; width:auto;">
			<div id="Z_TMAIL_SIDER_V2" class="mc" style="width:140px;">
			   <div style=" height:35px; line-height:35px;   font-size:16px;font-weight: bold; border-bottom:1px #ddd dotted; width:auto; text-align: center;">←全部分类</div>
						<div class="mcCode"> 
							<s:iterator value="#application.categoryMap" var="first" status="stOne">
							 <s:if test="categoryParams==#first.key.productTypeId">
							 <span class="sanjiao"></span>
							 <span class="sanjiao2"></span>
							<s:iterator value="#first.value" var="ptbOne" status="stOne">
								<div class="item <s:if test='#stOne.index==0'>fore</s:if>">
									<span style="cursor: default ;"> 
									
										<h3>
											<a class="hyds_aa">
												<s:if test="#ptbOne.key.sortName.length()>=10">
													<s:property value="#ptbOne.key.sortName.substring(0,9)"/>
												</s:if>
												<s:else>
													<s:property value="#ptbOne.key.sortName"/>
												</s:else>
											</a>
										</h3>
									
									</span>
									<div class="i-mc">
										<div class="close"
											onclick="$(this).parent().parent().removeClass('hover')"></div>
										<div class="subitem">
											<s:iterator value="#ptbOne.value" var="ptbTwo" status="st">
												<dl class="<s:if test='#st.index==0'>fore</s:if>">
													<dt style="cursor: default ;">
														<s:property value="#ptbTwo.key.sortName" />
													</dt>
													<dd>
														<s:iterator value="#ptbTwo.value" var="ptbThree">
															<em><a href="javascript:void(0);"
																onclick="goFirstType(<s:property value='#ptbThree.productTypeId'/>,<s:property value='#ptbThree.level'/>)"><s:property
																		value="#ptbThree.sortName" /></a></em>
														</s:iterator>
													</dd>
												</dl>
											</s:iterator>
										</div>
										<%-- <div id="JD_sort_k" class='fr'>
											<div id="JD_sort_k" class='fr'
												style="height: 400px;overflow: hidden;">
												<s:if test="#application.categoryBrandMap.size > 0">
													<s:iterator value="#application.categoryBrandMap" id="bm"
														status="st">
														<s:if test="#bm.key.productTypeId==#ptbOne.key.productTypeId">
															<s:if test="#bm.value.size>0">
																<s:iterator value="#bm.value" var="brand">
																	<a href="${pageContext.request.contextPath}/productInfoPage/${brand.brandId}-${bm.key.productTypeId}-${categoryParams}.html">
																		<img
																		onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"
																		src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#brand.brandBigImageUrl"/>"
																		width="194" height="70" alt="" style="margin-top:10px;" />
																	</a>
																</s:iterator>
															</s:if>
														</s:if>
													</s:iterator>
												</s:if>
											</div>
										</div> --%>
									</div>
								</div>
								</s:iterator>
								</s:if>
							</s:iterator>
						</div>
						<div class="header_bgyinB"></div>
				</div>
			</div>
			<script type="text/javascript"> 
                $(".allsort").hoverForIE6({current:"allsorthover",delay:200});
                $(".allsort .item").hoverForIE6({delay:150});
			</script>
	</div>
 
  <div class="you">
  <p style=" margin:0px; height:35px; line-height:35px; padding-left:10px;">您已选择：<s:property escape="false" value='#request.pathInfo'/></p>
  <div class="list_cont">
    <s:if test="#request.brandList.size>0">
    	<s:iterator value="#request.brandList" var="o" status="s">
		    <div class="list_fir">
		      <p style="cursor: pointer;" onclick="gotoDetailProductInfo('${o.brandId}','${productTypeId}','${categoryParams}')"><img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${o.brandImageUrl}" />${o.brandName }</p>
		    </div>
    	</s:iterator>
    	<script>
    		//前往商品详情页面
    		var gotoDetailProductInfo=function(brandId,productTypeId,categoryParams){
    			location.href="${pageContext.request.contextPath}/productInfoPage/"+brandId+"-"+productTypeId+"-"+categoryParams+".html";
    		}
    	</script>
    </s:if>
  </div>
  <div style="clear:both;"></div>
  <!--分页 start-->
			<div class="pageList strong ClearBoth">
				<jsp:include page="../../util/splitPage.jsp" />
			</div>
			<!--分页 end-->
  </div>
  <s:if test="#request.brandList.size>0">
  		<form id="formModule" action="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${productTypeId}&categoryParams=${categoryParams}" method="post">
	    	
		</form>
	</s:if>
</div>
</div>
</div>
<!-- 将底部通用部分引入首页 -->
<%@ include file="../include/footer.jsp"%>
</body>
</html>