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

</head>
<body>
	<div class="mui-content">
		<div id="slider">

			<div style="width: 100%;">
				<!--品牌logo  -->
				<img class="mui-media-object"
					src="${fileUrlConfig.visitFileUploadRoot}${brand.brandBigImageUrl }" />
				<%-- <!--品牌名  -->
				${brand.brandName}<br/> --%>
			</div>

			<div class="mui-slider-group">
				<div id="item1mobile"
					class="mui-slider-item mui-control-content mui-active">


					<!--产品列表 -->
					<ul class="mui-table-view mui-grid-view">
						<c:forEach var="pro" items="${brandProinfoList}">
							<a
								href="${pageContext.request.contextPath}/phoneProductInfo/${pro.productId}.html">

								<li class="mui-table-view-cell mui-media mui-col-xs-6"><img
									class="mui-media-object"
									src="${fileUrlConfig.visitFileUploadRoot}${pro.logoImg}" />
									<div class='mui-media-body'>${pro.productName}</div>
									<div style="height: 20px; margin-top: 8px;">
										￥<span style='color: #cc0000; font-size: 26px;'>${pro.salesPrice}</span>
									</div>
									<div class='mui-media-body'>${pro.shopName}</div></li>
							</a>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>

	</div>
	</div>
</body>
</html>