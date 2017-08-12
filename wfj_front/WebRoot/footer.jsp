<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'header.jsp' starting page</title>
    <link href="${fileUrlConfig.fileServiceUploadRoot}front/css/public.css" type="text/css" rel="stylesheet" />
	<link href="${fileUrlConfig.fileServiceUploadRoot}front/css/style.css" type="text/css" rel="stylesheet" />
	<link href="${fileUrlConfig.fileServiceUploadRoot}front/css/lunbo.css" type="text/css" rel="stylesheet" />
	<link href="${fileUrlConfig.fileServiceUploadRoot}front/css/xiala.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}front/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}front/js/lbt.js"></script>  
 	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}front/js/xiala.js"></script>
  </head>
  
  <body>
		 <div id="footer">
       		<div class="footer-manage">
            	<div class="substance">
                	<h2>新手入门</h2>
                    <span><a href="#">帮助中心</a></span>
                    <span><a href="#">规则中心</a></span>
                    <span><a href="#">新手上路</a></span>
                    <span><a href="#">在线客服</a></span>
                </div>
                <div class="substance business">
                	<h2>交易保障</h2>
                    <span><a href="#">担保交易</a></span>
                    <span><a href="#">诚信认证</a></span>
                    <span><a href="#">支付方式</a></span>
                </div>
                <div class="substance attention">
                	<h2>关注我们</h2>
                    <span><a href="#">新浪微博</a></span>
                    <span><a href="#">腾讯微博</a></span>
                </div>
                 <div class="substance two-dimension-code">
                 	<h2>首页二维码</h2>
                     <span><a href="#"><img src="${fileUrlConfig.fileServiceUploadRoot}front/images/two-dimension-code-pic.png" width="80" height="80" /></a></span>
                </div>
            </div>
            <div class="footer-shdow"></div>
         <div class="termination">
            	<div class="termination-ct">
                	<ul>
                    	<li><a href="#">关于我们</a></li>
                        <li>|</li>
                        <li><a href="#">新闻中心</a></li>
                        <li>|</li>
                        <li><a href="#">支付方式</a></li>
                        <li>|</li>
                        <li><a href="#">支付方式</a></li>
                        <li>|</li>
                        <li class="no-margin"><a href="#">客服中心</a></li>
                    </ul>
                    <span>Copyright © 2012 ******* 版权所有 备案：***********************</span>
                    <p>服务热线：********* 地址：***************************************</p>
                </div>
             <ul class="footer-collaborate">
               	 <li><a href="#"><img src="${fileUrlConfig.fileServiceUploadRoot}front/images/footer-pic1.png" width="128" height="45" /></a></li>
                 <li><a href="#"><img src="${fileUrlConfig.fileServiceUploadRoot}front/images/footer-pic2.png" width="128" height="45" /></a></li>
                 <li><a href="#"><img src="${fileUrlConfig.fileServiceUploadRoot}front/images/footer-pic3.png" width="128" height="45" /></a></li>
                 <li><a href="#"><img src="${fileUrlConfig.fileServiceUploadRoot}front/images/footer-pic4.png" width="128" height="45" /></a></li>
                 <li class="no-margin"><a href="#"><img src="${fileUrlConfig.fileServiceUploadRoot}front/images/footer-pic5.png" width="128" height="45" /></a></li>
                    
             </ul>
           </div>  
       </div>
  </body>
</html>
