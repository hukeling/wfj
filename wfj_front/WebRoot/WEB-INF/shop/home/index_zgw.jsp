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
<!-- SHOPJSP中间楼层分类样式 -->
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/homeIndex.css" />
<!--全屏轮播图-->
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/modernizr-custom-v2.7.1.min.js" type="text/javascript"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-finger-v0.1.0.min.js" type="text/javascript"></script>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/flickerplate.css"  type="text/css" rel="stylesheet"/>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/flickerplate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/lunbotu.js"></script>
<style type="text/css">
	/* 全屏轮播图 */
	html {
	font-family: 'Open Sans', Helvetica, Arial, sans-serif;
	background-color: #fff;
	font-weight: 300;
	}
	body {
		margin: 0px;
		padding: 0px;
	}
	a, a:visited {
		color: #E54028;
		text-decoration: none;
	}
	a:hover {
		color: #c22d18;
		text-decoration: underline;
		cursor: pointer;
	}
	
.banner_bg {
    height: 400px;
    overflow: hidden;
    width: 100%;
}
.banner_marquee_list {
    height: 400px;
    margin: 0 auto;
    overflow: hidden;
    width: 100%;
}
.banner_main {
    height: 400px;
    margin: 0 auto;
    overflow: hidden;
    width: 1000px;
}
.banner_btn span {
    background: rgba(0, 0, 0, 0) url("${fileUrlConfig.fileServiceUploadRoot}images/lbtp.png") no-repeat scroll 0 0;
    cursor: pointer;
    display: inline-block;
    float: left;
    font-size: 0;
    height: 10px;
    line-height: 0;
    margin: 4px 14px 0;
    vertical-align: bottom;
    width: 10px;
}
.banner_btn span.on {
    background: rgba(0, 0, 0, 0) url("${fileUrlConfig.fileServiceUploadRoot}images/lbdat.png") no-repeat scroll 0 0;
    height: 18px;
    margin-top: 0;
    width: 18px;
}
.banner_btn {
    height: 25px;
    overflow: hidden;
    padding-left: 10px;
    position: absolute;
    right: 16%;
    text-align: right;
    top: 530px;
    width: auto;
    z-index: 5;
}
</style>

<script type="text/javascript">
	//全屏轮播图
	$(document).ready(function(){
		$('.flicker-example').flicker();
	});
</script>
</head>

<body>
    <%@ include file="../include/header.jsp"%>
    <!-- 全屏轮播图开始 -->
 <%--    <div class="flicker-example" data-block-text="false">
		<ul>
		<s:if test="#request.homeLBTList.size>0">
		<s:iterator value="#request.homeLBTList" var="h">
			<li data-background="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#h.broadcastingIamgeUrl'/>"></li>
		</s:iterator>	
	     </s:if>   
		</ul>
		<div style="clear:both;"></div>
    </div> --%>
    <!-- 全屏轮播图结束 -->
    
    <!--幻灯片 开始-->	
	<div class="banner_bg" id="slidepicturediv">
		<ul class="banner_marquee_list" id="slidepicture">
		<s:if test="#request.homeLBTList.size>0">
			<s:iterator value="#request.homeLBTList" var="h">
				<li style='background: url("<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#h.broadcastingIamgeUrl'/>") no-repeat 50% 0px;'>
					<div class="banner_main">
						<a title="" class="banner_link" id="1350_62128"  href="<s:property value='#h.interlinkage'/>"  target="_blank"></a>
					</div>
				</li>
			</s:iterator>
		</s:if>
		</ul> 
		<div class="banner_btn" id="slidebutton"></div>
	</div>
	<!--幻灯片 结束-->
    
    <s:set name="categoryParams" value="2"></s:set><!-- 分类参数用来 标记当前读取的数据属于哪个主题馆 -->
	<!-- 中间分类内容 begin -->
	<div class="zhjj_hold">
    	<div class="zhjj_hfir">
	      	<h3 class="color11">品牌推荐</h3>
	      	<ul class="zhjj_ull">
		      <!-- 品牌推荐 1 -->
		      <s:iterator value="#request.brandList" var="o">
			       <li><a href="${pageContext.request.contextPath}/productInfoPage/${o.brandId}-${categoryParams }.html"><img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${o.brandBigImageUrl}" /></a></li>
		      </s:iterator>
		   </ul>
	       <div style="clear:both;"></div>
       </div>
       <!-- 楼层分类内容 2 -->
       <s:iterator value="#application.categoryMap" var="first"><!-- 循环所有的分类 -->
       <!-- 循环展示categoryParams分类下的数据 -->
       <s:if test="#categoryParams==#first.key.productTypeId">
       		<s:iterator value="#first.value" var="sec" status="st2">
       			<s:if test="#sec.key.sortCode<=2">
			     <div class="zhjj_hfir">
			     <div class="zhjj_ban"><img  onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${sec.key.categoryImage}" /></div>
			      <h3 class="color11 colorrr<s:property value='#st2.index+1'/>"><s:property value='#sec.key.sortName'/></h3><!-- 二级分类名称 -->
			       <div class="zhjj_send">
			       	<!-- 第一个三级分类 -->
			       	<s:iterator value="#sec.value" var="thir" status="st3">
			       			<s:if test="#st3.index==0">
						         <div class="zhjj_sendleft">
						           <div class="send_cont">
						             <h5><s:property value="#thir.key.sortName"/></h5><!-- 三级分类名称 -->
						             	<s:iterator value="#thir.value" var="four" status="s4">
						             		<s:if test="#s4.index<5">
									             <a href="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${four.productTypeId}&categoryParams=${categoryParams}"><s:property value="#four.sortName" /></a><!-- 四级分类名称 -->
						             		</s:if>
						             	</s:iterator>
						             <img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" class="send_footimg" src="${fileUrlConfig.visitFileUploadRoot}${thir.key.categoryImage}" />
						           </div>
						           
						         </div>
			       			</s:if>
				       </s:iterator>
				       <div class="send_conright">
				          <s:iterator value="#sec.value" var="thir" status="st3">
				          		<s:if test="#st3.index!=0&&#st3.index<=12">
							           <div class="send_juti">
							             <img  onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${thir.key.categoryImage}" />
							             <div class="send_cont fr">
							              <h5><s:property value="#thir.key.sortName"/></h5><!-- 三级分类名称 -->
							             <s:iterator value="#thir.value" var="four" status="s4">
							             	<s:if test="#s4.index<4">
								             	<a href="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${four.productTypeId}&categoryParams=${categoryParams}"><s:property value="#four.sortName" /></a><!-- 四级分类名称 -->
								             </s:if>
						             	</s:iterator>
							           </div>
							           </div>
					           </s:if>
				           </s:iterator>
			            </div>
			         <div style="clear:both;"></div>
			       </div>
			    </div>
    			</s:if>
	         </s:iterator>
    	</s:if>
    </s:iterator>
    <!-- 最后一个楼层内容 3 -->
    <div style="width:1200px; height:auto;">
	    <div class="zhjj_hfir widthh">
	    <!-- 最后一个楼层  靠左侧  读取分类倒数二个 -->
	    <s:iterator value="#application.categoryMap" var="first"><!-- 循环所有的分类 -->
	    	<s:if test="#categoryParams==#first.key.productTypeId">
	       		<s:iterator value="#first.value" var="sec" status="st2">
	       			<s:if test="#sec.key.sortCode==3">
	       			 <div class="zhjj_ban"><img  onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${sec.key.categoryImage}" /></div>
				      <h3 class="color11 colorrr4"><s:property value="#sec.key.sortName"/></h3>
				       <div class="zhjj_send" style="width:838px; border-right:1px #e6e6e6 solid;">
				       <s:iterator value="#sec.value" var="third" status="st3">
				       <!-- 第一个 -->
				       <s:if test="#st3.index==0">
				         <div class="zhjj_sendleft">
				           <div class="send_cont">
				             <h5><s:property value="#third.key.sortName"/></h5>
				             <s:iterator value="#third.value" var="four" status="s4">
				             	<s:if test="#s4.index<5">
						             <a href="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${four.productTypeId}&categoryParams=${categoryParams}"><s:property value="#four.sortName"/></a>
				             	</s:if>
				             </s:iterator>
				             <img class="send_footimg" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${third.key.categoryImage}" />
				           </div>
				         </div>
				       </s:if>
				       </s:iterator>
				         <!-- 中间方块内的 -->
				          <div style="width:350px; float:left;">
				           <s:iterator value="#sec.value" var="third" status="st3">
				           		<s:if test="#st3.index>0&&#st3.index<5">
						           <div class="send_juti">
							             <img class="send_footimg"  onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${third.key.categoryImage}" />
							             <div class="send_cont fr">
							               <h5><s:property value="#third.key.sortName"/></h5>
								             <s:iterator value="#third.value" var="four" status="s4">
								             	<s:if test="#s4.index<4">
									             	<a href="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${four.productTypeId}&categoryParams=${categoryParams}"><s:property value="#four.sortName"/></a>
									             </s:if>
								             </s:iterator>
							            </div>
						           </div>
				           		</s:if>
				           </s:iterator>
				           <div class="clear:both;"></div>
				           </div>
				       <!-- 右侧第二个 -->
			           <s:iterator value="#sec.value" var="third" status="st3">
			           		<s:if test="(#sec.value.size-1)==(#st3.index+1)">
						            <div class="zhjj_sendleft zhjj_sendleftwid">
						           <div class="send_cont">
						            <h5><s:property value="#third.key.sortName"/></h5>
							             <s:iterator value="#third.value" var="four" status="s4"	>
							             	<s:if test="#s4.index<4">
								             	<a href="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${four.productTypeId}&categoryParams=${categoryParams}"><s:property value="#four.sortName"/></a>
								             </s:if>
							             </s:iterator>
						             <img class="send_footimg" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${third.key.categoryImage}" />
						           </div>
						         </div>
					         </s:if>
				         </s:iterator>
				        <!-- 右侧第一个 -->
		            <s:iterator value="#sec.value" var="third" status="st3">
		           		<s:if test="#sec.value.size==(#st3.index+1)">
				         <div class="zhjj_sendleft zhjj_sendleftwid borderxian">
				           <div class="send_cont">
				             <h5><s:property value="#third.key.sortName"/></h5>
					             <s:iterator value="#third.value" var="four" status="s4"	>
					             	<s:if test="#s4.index<4">
						             <a href="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${four.productTypeId}&categoryParams=${categoryParams}"><s:property value="#four.sortName"/></a>
						             </s:if>
					             </s:iterator>
				             <img class="send_footimg" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${third.key.categoryImage}" />
				           </div>
				         </div>
			         </s:if>
	   		     </s:iterator>
	   		     </div>
       			</s:if>
       		</s:iterator>
   		</s:if>
    </s:iterator>
     </div>
     </div>
       <!-- 最后一个楼层  靠右侧  读取分类倒数一个 -->
       <s:iterator value="#application.categoryMap" var="first"><!-- 循环所有的分类 -->
	    	<s:if test="#categoryParams==#first.key.productTypeId">
	       		<s:iterator value="#first.value" var="sec" status="st2">
	       			<s:if test="#sec.key.sortCode==4">
					       <div class="zhjjcont_right">
					         <h3 class="color11 colorrr5"><s:property value="#sec.key.sortName"/></h3>
					         <div class="home_zhuoz">
					          <s:iterator value="#sec.value" var="third" status="st3">
					       		   <s:if test="#st3.index<2">
							           <div class="homne_top">
							             <img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"  src="${fileUrlConfig.visitFileUploadRoot}${third.key.categoryImage}" />
							             <div class="homgimg_right">
							                 <h5><s:property value="#third.key.sortName"/></h5>
								             <s:iterator value="#third.value" var="four" status="s4"	>
								                <s:if test="#s4.index<6">
									            	 <a href="${pageContext.request.contextPath}/shopHome/gotoType.action?productTypeId=${four.productTypeId}&categoryParams=${categoryParams}"><s:property value="#four.sortName"/></a>
									             </s:if>
								             </s:iterator>
							             </div>
							           </div>
						          </s:if>
					          </s:iterator>
					         </div>
					       </div>
	       			</s:if>
	       		</s:iterator>
      		</s:if>
  		</s:iterator>
       <div class="zhjj_ban"><img  onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="${fileUrlConfig.visitFileUploadRoot}${sec.key.categoryImage}" /></div>
      </div>
<!-- 中间分类内容 end -->
<!-- 将底部通用部分引入首页 -->
<%@ include file="../include/footer.jsp"%>
</body>
</html>