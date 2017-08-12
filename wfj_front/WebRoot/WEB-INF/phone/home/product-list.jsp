<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head lang="en">
<meta charset="utf-8">
<title></title>
<meta name="viewport"
	content="initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="../css/phone/mui.min.css">
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
	/* 根据产品类别id获取产品列表信息 */
	function getProlist(ptid){
		var customerId="${customerId}";
		$.ajax({
			type:'post',      
			url:'${pageContext.request.contextPath}/phone/conditionProinfo.action',  
			data:({productTypeId:ptid}),
			dataType:'json',
			success:function(data) {
				 $("ul").html("");//清空ul内容
				if(data.Status==true){
					$.each(data.Data,function(i,item){
						if(item.logoImg==""){
							var img="../img/tuijian_38.jpg"
							}else{
								var img="${fileUrlConfig.visitFileUploadRoot}"+item.logoImg;
							}
		                 $("ul").append(
		                "<li class='mui-table-view-cell mui-media mui-col-xs-6'>"+
		                "<a href="+
		                "${pageContext.request.contextPath}/phoneProductInfo/"+
		                item.productId+
		                ".html?customerId="+
		                customerId+
		                "><img class='mui-media-object' src='"+img+"'/>"+
		                "<div class='mui-media-body'>"+item.productName+"</div>"+
		                "<div style='height: 20px; margin-top: 8px;'>￥<span style='color: #cc0000; font-size: 26px;'>"+item.salesPrice+"</span></div>"+
		                "<div class='mui-media-body'>"+item.shopName+"</div>"+
		                "</a></li>"		 
		                 );
					});
				}
		        } 
			});  
	}
	/* 根据二级分类Id获取三级分类  */
	function listSanCategory(ptid){
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}/phone/sancategorylist.action',
			data:({productTypeId:ptid}),
			dataType:'json',
			success:function(data){
				$("#sancategory").html("");//清空ul内容
				if(data.Data!=""){
					$.each(data.Data,function(i,item){
		                 $("#sancategory").append(
		                		"<a class='mui-control-item mui-active'  href='#' onclick='getProlist("+item.productTypeId+");'>"+ 
		 							item.sortName+"</a>	"	
		                 );
					});
				}
			}
		});
	}
	
	function init(ept,spt){
		listSanCategory(ept);
		getProlist(spt);
	}
	 
	</script>
</head>
<body onload="init(${ept.productTypeId},${spt.productTypeId});">
	<div class="mui-content">
		<div id="slider">
			<div id="sliderSegmentedControl"
				class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
				<div class="mui-scroll" style="width: 100%;">
					<!--二级菜单  -->
					<c:forEach var="pttwo" items="${secondCategoryList}">
						<a class="mui-control-item mui-active mui-col-xs-4" href="#"
							onclick="listSanCategory(${pttwo.productTypeId})">
							${pttwo.sortName}</a>
					</c:forEach>
				</div>
			</div>

			<div class="mui-slider-group">
				<div id="item1mobile"
					class="mui-slider-item mui-control-content mui-active">
					<div id="scroll1">
						<div id="sancategory" style="display: block;">
							<!-- 三级菜单 -->
						</div>
						<!--产品列表 -->
						<ul class="mui-table-view mui-grid-view">
						</ul>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>