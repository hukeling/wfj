<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资讯首页</title>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/information/thaddsy.css" rel="stylesheet"  type="text/css" />
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/style.css" rel="stylesheet"  type="text/css" />
<!--焦点图css-->
<style type="text/css">
/* slider */
.slider{width:693px;height:260px;position:relative;overflow:hidden;}
.conbox{position:absolute;/*必要元素*/}
.switcher{position:absolute;bottom:56px;right:10px;float:right;z-index:99;}
.switcher a{background:#fff;border:1px solid #D00000;cursor:pointer;float:left;font-family:arial;height:18px;line-height:18px;width:18px;margin:4px;text-align:center;color:#D00000;}
.switcher a.cur,.switcher a:hover{background:#FF0000;border:1px solid #D00000;height:24px;line-height:24px;width:24px;margin:0 2px;color:#fff;font-weight:800;}
/* slider2 水平 */
#slider2 .conbox div{width:693px;height:260px;overflow:hidden;}
</style>
<!--焦点图js开始-->
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/information/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/information/jquery.Xslider.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/information/jquery.tabs.js"></script>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/information/jquery.lazyload.js"></script>
<!--tab样式-->
<style type="text/css">
#title{width:300px;margin:3% auto 0;}
#title h2{font-size:18px;}
#wrapper{width:980px;margin:0 auto;background:#f8f8f8;border:1px solid #a3a3a3;padding:20px 20px 50px;border-radius:5px;-moz-border-radius:5px;}
#wrapper h3{color:#333;font-size:14px;text-align:center;margin:20px 0;}
/* box */
.box{width:281px;background:#fff;border:1px solid #d7d7d7; border-top:0;}
.tab_menu{overflow:hidden;background:#f4f4f4;}
.tab_menu li{width:135px;float:left;height:30px;line-height:30px;color:#333;text-align:center;cursor:pointer;  border-bottom:1px #d7d7d7 solid;}
.tab_menu li.current{color:#333;background:#fff;  border-bottom:0; }
.tab_menu li a{color:#fff;text-decoration:none;}
.tab_menu li.current a{color:#333;}
.tab_box{padding:10px 10px;}
.tab_box li{font-size:14px; height:24px;line-height:24px;overflow:hidden;  background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/information/jiantou_03.png) no-repeat left center; padding-left:15px;}
.tab_box li span{font-size:12px;font-weight:400;}
.tab_box .hide{display:none;}
</style>
<!--tab js 开始-->
<script type="text/javascript">
var size='${size}';
$(document).ready(function(){
	// 焦点图片垂直滚动
	$("#slider2").Xslider({
		affect:'scrolly',
		ctag: 'div',
		speed:600,
		space: 4000,
		num:size
	});
});
$(function(){
	$("img[original]").lazyload({ placeholder:"${fileUrlConfig.fileServiceUploadRoot}shop/front/images/information/color3.gif" });
	$('.demo1').Tabs();
	$('.demo2').Tabs({
		event:'click'
	});
	$('.demo3').Tabs({
		timeout:300
	});
	$('.demo4').Tabs({
		auto:3000
	});
	$('.demo5').Tabs({
		event:'click',
		callback:lazyloadForPart
	});
	//部分区域图片延迟加载
	function lazyloadForPart(container) {
		container.find('img').each(function () {
			var original = $(this).attr("original");
			if (original) {
				$(this).attr('src', original).removeAttr('original');
			}
		});
	}
});	
//阅读全文
function getInfo(shopInfoId,articleId){
	//5秒后调用
	setTimeout('rel()',2000);
	var url="${pageContext.request.contextPath}/homeModule/changeClickNum.action?shopInfoId="+shopInfoId+"&articleId="+articleId;
	window.open(url,"_blank");
}

function rel(){
	location.reload();
}
</script>
<!--tab js 结束-->
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
      
      <h2>${shopInfo.shopName }</h2>
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
  
 <!--main-->
  <div class="add_main">
    <div class="addmain_left">
       <div class="xunhuan_con">
         <!--调js-->
            <div id="slider2" class="slider">
			<div class="conbox">
				<s:iterator value="#request.topInformationList">
					  <div>
			                <div class="tp_wenzi">
			                 	 <div class="left_img" style=" padding-top:0; padding-bottom:0;">
			                 	       <p style="background-color: #efefef; font-size: 15px; color: #333; height: 30px; line-height: 30px; padding-left:10px; margin-bottom:10px; border-bottom:1px #ccc  solid; font-weight: bold;">置顶</p>
					                    <img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${imgUrl}" />
					                    <p class="biaoti" style="width:441px; margin-right:0px; padding-right:10px;">
					                     ${title }</br><span style=" font-size:12px; color:#999; font-weight:normal;">日期：${createTime} | 作者：</span>
					                     <span style="color:#900000; font-weight:normal; font-size:12px;">${publishUser}</span>
					                     <span  style=" font-size:12px; color:#999; font-weight:normal;"> | 浏览次数：${clickCount}</span>
					                     </p>
					                     <p class="duanluo" style="width:441px; margin-right:0px; padding-right:10px;">
					                     ${brief }
					                     </p> 
					                      <div  style="width:693px; height:30px; line-height:30px;  border-top:1px #CCCCCC solid; font-size:13px; ">
					                       <span style="padding-left:10px; width:500px; display:block; float:left;"><span style="color:#000;">分类：</span>${modifyUser }</span><span style=" float:right; padding-right:10px;"><a  style="color:#900000;" href="javascript:getInfo(${shopInfo.shopInfoId},${articleId});">阅读全文</a></span>
					                       </div>               
				                  </div>
			                </div>
			           		
	           		</div>
           		</s:iterator>
			</div>
            
			<div class="switcher">
				<s:iterator value="#request.topInformationList" status="s">
					<s:if test="#s.index==0">
						<a href="#" class="cur"><s:property value="#s.index+1"/></a>
					</s:if>
					<s:else>
						<a href="#"><s:property value="#s.index+1"/></a>
					</s:else>
				</s:iterator>
			</div>
		</div>
        <!--调js end-->
       </div> 
       
       
       <!--第二次循环-->
       <s:iterator value="#request.informationList" var="obj">
        <div  class="xunhuan_con pddtop">
             <div class="tp_wenzi">
                  <div class="left_img">
                 
                    <img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${imgUrl}" />
                     <p class="biaoti" style="width:437px; margin-right:0px;">
                     ${title }</br><span style=" font-size:12px; color:#999; font-weight:normal;">日期：${createTime} | 作者：</span>
                     <span style="color:#900000; font-weight:normal; font-size:12px;">${publishUser}</span>
                     <span  style=" font-size:12px; color:#999; font-weight:normal;"> | 浏览次数：${clickCount}</span>
                     </p>
                     <p class="duanluo" style="width:437px; margin-right:0px;">
                     ${brief }
                     </p>                  
                  </div>
                </div>
            <div class="yuedu">
             <span style="float:left;"><span style="color:#000;">分类：</span>${shopProCategoryName}</span><span style=" float:right;"><a  style="color:#900000;" href="javascript:getInfo(${shopInfo.shopInfoId},${articleId});">阅读全文</a></span>
           </div>
        </div>
       </s:iterator>
        <!--第二循环结束-->
        
        <!--分页-->
    	  <form id="formModule" action="${pageContext.request.contextPath}/homeModule/getMore.action?shopInfoId=${shopInfo.shopInfoId}&categoryId=${categoryID}" method="post">
	    	<!--分页 start-->
			<s:if test="#request.topInformationList.size>0">
			<div class="pageList strong ClearBoth">
			     <jsp:include page="../../util/splitPage.jsp" />
			</div>
			</s:if>
	    </form>
		<!--分页 end-->
    </div>
 
   <div class="addmain_right">
     <div class="right_img"><img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>${shopInfo.logoUrl}" /></div>
     <!--tab页开始-->
        <div class="tab_head">
           
           
           <div class="box demo2">
			<ul class="tab_menu">
				<li class="current" style="border-right:1px #d7d7d7 solid;">最新文章</li>
				<li style="width:143px;">热门文章</li>
			
			</ul>
			<div class="tab_box">
				<div>
				  <ul>			   
				  	<s:iterator value="#request.informationList1">
					    <li><a href="javascript:getInfo(${shopInfo.shopInfoId},${articleId});">${title}</a></li>
				  	</s:iterator>
			      </ul>
					
				</div>
				<div class="hide">
				   <ul>			   
					   <s:iterator value="#request.informationList2">
						    <li><a href="javascript:getInfo(${shopInfo.shopInfoId},${articleId});">${title}</a></li>
					  	</s:iterator>
			      </ul>
				</div>
			</div>
		</div>
     
        </div>
     <!--tab页结束-->
   </div>
  </div>
 <div class="clear"></div>
 <div class="foot">
    <div>SHOPJSP版权所有  京ICP证 100908号</div>
 </div>
</body>
</html>
