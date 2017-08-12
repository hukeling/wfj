<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>
	<s:if test="#request.information.seoTitle!=null&&#request.information.seoTitle!=''">${information.seoTitle}</s:if>
	<s:else>资讯站</s:else>
</title>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/information/thaddsy.css" rel="stylesheet"  type="text/css" />
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/information/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"></script>

<script>
	//获取评论标识
	var isOk='${isOk}';
	$(function () { 
		 if(isOk=="1"){
			 alert("评论成功!");
		 }
		 $("#verification").attr("src","${pageContext.request.contextPath}/backLogin/userFirstLogin.action");
	     //点击图片更换验证码
	     $("#verification").click(function(){
	         $(this).attr("src","${pageContext.request.contextPath}/backLogin/userFirstLogin.action?timestamp="+new Date().getTime());
	     });
	 });
	function tijiao(){
		$("#msg").html("");
		$("#msg2").html("");
		var v=$("#pltextarea").val();
		//校验评论字符数
		if(v.length>=500){
			$("#msg2").html("<font color='red'>留言最长字数500字</font>");
			return false;
		}
		if(v.length==0){
			$("#msg2").html("<font color='red'>留言不可为空</font>");
			return false;
		}
		var flag=true;
		$.ajax({
			   type: "POST",dataType: "JSON",
			   url: "${pageContext.request.contextPath}/front/infoMationComment/verificationCode.action",
			   data: {verificationCode:$("#verificationCode").val()},
			   async : false,
			   success: function(data){
					if(eval("("+data+")").success=="1"){
						$("#msg").html("");
						flag=true;
					}else if(eval("("+data+")").success=="2"){
						$("#msg").html("<font color='red'>验证码无效</font>");
						flag=false;
					}else if(eval("("+data+")").success=="3"){
						$("#msg").html("<font color='red'>验证码不可为空</font>");
						flag=false;
					}
			   }
			});
		//将留言内容存储在cookie中
		var value=$("#pltextarea").val();
		//对评论内容进行编码处理
		value=escape(value);
		$.cookie('thshop_comment', value, { expires: 1,path:"/"}); //设置带时间的cookie，期限1天
		return flag;
	}
</script>
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
  <div class="add_main" style="984px; border:1px #d7d7d7 solid; margin-top:10px;">
    <div class="conten_head">
      <div class="conhed_left">${information.title }</br>
        <span style=" font-weight:normal; font-size:12px;">作者：${information.publishUser } | 分类：</span>
        <a style="font-weight:normal; font-size:12px; color:#900;text-decoration: none;">${informationCategory.shopProCategoryName }</a>
      </div>
      
      <div class="conhed_right">
         <p class="liulan"><span style="color:#900;">${information.clickCount }</span></br>浏览</p>
         <p class="liulan" style="float:right;"><span style="color:#900;">${count }</span></br>回复</p>     
      </div>
      
    </div>
    ${information.content }
  </div>
  
  <form id="form1" onsubmit="return tijiao();" action="${pageContext.request.contextPath}/front/customer/infoMationComment/addComment.action?shopInfoId=${shopInfoId }&articleId=${articleId }" method="post">
  <div class="fbpl">
    <div class="pl_head">发表评论:</div>
    <p class="zhwen">正文(*)留言最长字数500字</p>
    <textarea id="pltextarea" name="content" cols="" rows="7" class="wnyu" name="" value=""></textarea>

   <div class="fabu">
      <p class="yanzhengm">
       <input class="wb_text" type="text" id="verificationCode" value="" name="verificationCode" />
        <label class="yz_lab">验证(*)</label>
		<span title="看不清，换一张" ><img style=" vertical-align: middle; cursor:pointer;"  id="verification"  alt="看不清，换一张"/></span>
		 <span id="msg"></span>
		 <span id="msg2"></span>
      </p>
      <div class="fb_an">
        <input name="" type="submit"  class="fban_button" value=""/>
      </div>
     
      <div class="clear"></div>
      <p class="zhwen" style="width:auto; padding-left:42px; height:20px; line-height:20px; padding-top:0; padding-bottom:15px; font-size:14px;">
      欢迎参与讨论，请在这里发表您的看法，交流您的观点。
      </p>
    </div>
  </div>
 </form>
  <div class="foot">
    <div>SHOPJSP版权所有  京ICP证 100908号</div>
  </div>
</body>
</html>




