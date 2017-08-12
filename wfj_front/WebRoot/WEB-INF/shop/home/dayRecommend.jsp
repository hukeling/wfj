<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 每日推荐 -->
<title>${application.homekeybook['homeSeoTitle4'][0].value}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragram" content="no-cache">
<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="0">  
 <%@ include file="../include/head.jsp"%>
<!--头部-->
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/style.css"/>
<!--轮换图 js-->
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/jquery.jslides.js"></script>
<!--轮换图 js end-->
<!--leftNav js-->
<script type="text/javascript">
(function($){
	$.fn.hoverForIE6=function(option){
		var s=$.extend({current:"hover",delay:10},option||{});
		$.each(this,function(){
			var timer1=null,timer2=null,flag=false;
			$(this).bind("mouseover",function(){
				if (flag){
					clearTimeout(timer2);
				}else{
					var _this=$(this);
					timer1=setTimeout(function(){
						_this.addClass(s.current);
						flag=true;
					},s.delay);
				}
			}).bind("mouseout",function(){
				if (flag){
					var _this=$(this);timer2=setTimeout(function(){
						_this.removeClass(s.current);
						flag=false;
					},s.delay);
				}else{
					clearTimeout(timer1);
				}
			})
		})
	}
})(jQuery);
</script>
<!--顶部js-->
<script type="text/javascript">
(function(a){
	a.fn.hoverClass=function(b){
		var a=this;
		a.each(function(c){
			a.eq(c).hover(function(){
				$(this).addClass(b);
			},function(){
				$(this).removeClass(b);
			});
		});
		return a;
	};
})(jQuery);
$(function(){
	$("#navbox").hoverClass("current");	
});
	//商品排序
	function productSort(orderBy){
		$("#orderBy").val(orderBy);
		$("#currentPage").val(1);//排序默认第一页
		$("#formModule").submit();
	}
</script>
</head>

<body>
        <%@ include file="../include/header.jsp"%>
        <div class="bannerRecommend">
            <!-- banner代码 开始 -->
                <div id="full-screen-slider">
                    <ul id="slides">
                    	<s:iterator value="dayRecommendLBTList" var="dayRecommendLBT">
                    		<li>
                           		 <a style=" text-indent:0; width: 100%; height: 396px;" href="<s:property value='#dayRecommendLBT.interlinkage'/>" target="_blank">   
                           		  <img style="width:100%; height:396px; text-align: center;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#dayRecommendLBT.broadcastingIamgeUrl'/>"/>
                       			 </a>                       			 
                       		</li>
                    	</s:iterator>
                    </ul>
                </div>
                <!-- 代码 结束 -->
        </div>
        
        <!--每日推荐-->
        <div class="recommendedDaily">
            <div class="todayRecommen">
                <div class="todayRecommenT">
                    <div class="todayT"></div>
                </div>
                <div class="theDefault">
                    <span class="fl default">
                        <a href="javascript:;" <s:if test="orderBy=='defaults'">style="color:#FF1400;"</s:if> onclick="productSort('defaults')">默认</a>
                        <a href="javascript:;" <s:if test="orderBy=='totalSaleDesc'">style="color:#FF1400;"</s:if> onclick="productSort('totalSaleDesc')">销量</a>
                        <a href="javascript:;" <s:if test="orderBy=='timeDesc'">style="color:#FF1400;"</s:if> onclick="productSort('timeDesc')">时间</a>
                        <a href="javascript:;" <s:if test="orderBy=='priceAsc'">style="color:#FF1400;"</s:if> onclick="productSort('priceAsc')">价格↑</a>
                        <a href="javascript:;" <s:if test="orderBy=='priceDesc'">style="color:#FF1400;"</s:if> onclick="productSort('priceDesc')">价格↓</a>
                    </span>
                    <!-- 头部翻页 -->
                    <jsp:include page="../../util/splitPageGSJ_top.jsp"></jsp:include>
                </div>
                
                <div class="pickMonth">
                	<s:iterator value="productInfoList" var="productInfo">
                		<a target="_blank" title="<s:property value='#productInfo.productName'/>" class="pickMonthOne" href="${pageContext.request.contextPath}/productInfo/<s:property value='#productInfo.productId'/>">
	                        <img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#productInfo.logoImg'/>" style=" padding-left:30px; margin-top:30px; width:210px; height:210px; overflow:hidden; 
	                        "/>
	                        <h3>
<!-- 	                            <span class="benefit">包邮</span> -->
								<s:property value='#productInfo.productName'/>
	                        </h3>
	                        <div class="item-prices">
	                            <span class="price fl">
	                                <i>¥</i><em><s:property value='#productInfo.salesPrice'/></em>
	                            </span>
	                            <div class="dock fr" style="width:105px;height:45px;">
	                                <span class="sold-num fr" >
                               		 <em><s:property value='#productInfo.totalSales'/></em> 人已买
                           		 	</span>
	                                <del class="orig-price fr" style="margin-right:10px;">¥<s:property value='#productInfo.marketPrice'/></del>
	                            </div>
	                        </div>
	                    </a>
                    </s:iterator>
                </div>
				<form id="formModule" action="dayRecommend.action?headerType=dayRecommend" method="post">
					<input type="hidden" id="currentPage" name="currentPage" value="<s:property value='currentPage'/>"/>
					<input type="hidden" id="orderBy" name="orderBy" value="<s:property value='orderBy'/>"/>
				</form>
				<!--底部翻页效果-->
                <jsp:include page="../../util/splitPageGSJ_footer.jsp"></jsp:include>
                
            </div>
        </div>

     <div class="clear"></div>
	<div style="width:100%; height: auto; ">
          <%@ include file="../include/footer.jsp"%>
	</div> 
  
</body>
</html>

