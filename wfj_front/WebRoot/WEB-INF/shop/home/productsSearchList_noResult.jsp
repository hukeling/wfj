<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品搜索无结果页面</title>
<%@ include file="../include/head.jsp"%>

<style>
#plist, #plist .list-h {
    overflow: visible;
    position: relative;
    z-index: 2;
}
.m, .sm {
    margin-bottom: 10px;
}
#notfound {
    font-family: verdana,宋体;
    font-size: 14px;
    padding: 40px 0 50px;
    width:400px;
    margin:0 auto;
     position: relative;
}
#notfound h2 {
    font: 500 24px/20px 微软雅黑;
    margin-bottom: 25px;
}
#notfound h3 {
    font-size: 12px;
    line-height: 3;
}
ol, ul {
    list-style: outside none none;
}
.root61 #plist li {
    width: 221px;
}
#plist.plist-n7 li {
    margin-bottom: 10px;
}
#notfound li {
    font-size: 12px;
    line-height: 1.8;
    padding: 0;
    text-align: left;
}
#plist li {
    border-bottom: 1px dotted #ddd;
    font-family: arial,宋体;
    padding: 20px 13px 0;
    width: 230px;
}

#notfound b {
    display: block;
    height: 29px;
    left: -52px;
    position: absolute;
    top: 38px;
    width: 32px;
    z-index: 1;
}
#filter, #notfound b, #re-search .text, #re-search .button, #plist .item-book .summary .close {
    background: url("${fileUrlConfig.fileServiceUploadRoot}shop/front/images/noResult.jpg") no-repeat  0 -336px;
}
</style>
</head>

<body>
	<%@ include file="../include/header.jsp"%>
	<div id="plist" class="m psearch prebuy plist-n7 no-preview">
		<div id="notfound">
			<h2>抱歉,没有找到符合条件的商品！</h2>
			<h3>建议您：</h3>
			<ul>
				<li>1.适当减少筛选条件,可以获得更多结果</li>
				<li>2.调整价格区间</li>
				<li>3.尝试其他关键字</li>
			</ul>
			<b></b>
		</div>
	</div>
	<%@ include file="../include/footer.jsp"%>
</body>
</html>
