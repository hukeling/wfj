<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>商品评价列表</title>
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css">
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.tools.min.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollUp.js"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js"></script>
	<script type="text/javascript">
		
		//计算评价的百分比
		$().ready(function(){
			var hao = "<s:property value='#request.haoPingCount'/>";
			var zhong = "<s:property value='#request.zhongPingCount'/>";
			var cha = "<s:property value='#request.chaPingCount'/>";
			var total = hao*1+zhong*1+cha*1;
			$("#totalCount").html(total);
			if(total == 0){
				$("#haoVD").html(100);
				$("#haoV").html(100);
				$("#zhongV").html(0);
				$("#chaV").html(0);
			}else{
				var haoV = (Number(hao/total*100)).toFixed(0);
				var zhongV = (Number(zhong/total*100)).toFixed(0);
				var chaV = 100-haoV-zhongV;
				$("#haoVD").html(haoV);
				$("#haoV").html(haoV);
				$("#zhongV").html(zhongV);
				$("#chaV").html(chaV);
			}
		});
		//评价类型的切换
		function changCurrent(id){
			for(var i=1;i<=4;i++){
				if(id==i){
					$("#ping_"+i).removeClass("pjlino");
					$("#ping_"+i).addClass("pjliyes");
				}else{
					$("#ping_"+i).removeClass("pjliyes");
					$("#ping_"+i).addClass("pjlino");
				}
			}
		}
		//控制父层iframe的高度
		function setHeight(nHeight){
			var thisPageHeight = nHeight+250;
			$(window.parent.document).find("IFRAME").attr("height",thisPageHeight);
		};
		
		//iframe的高度自适应
	   function autoHeight() {
		   var ifm= document.getElementById("iframe_body");
		   var subWeb = document.frames ? document.frames["iframe_body"].document : ifm.contentDocument;
		   if(ifm != null && subWeb != null) {
		   ifm.height = subWeb.body.scrollHeight;
		   }
		   }
	</script>
	<style type="text/css">
	    .smsp2{height:37px;background:url(/common/shop/front/images/bgbgyin.png);border-top:0;border-bottom:0px;}
		.pjlino{float:left;font-size: 14px;padding:0px 20px;line-height: 35px; font-weight: bold; margin-top:2px;}
		.pjlino a{text-decoration:none;color:#000;}
		.pjlino a:hover{color:#d01743 ;}
		.pjliyes{background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/jiantou.png) no-repeat center bottom;float:left;font-size: 14px;padding:0px 20px;line-height: 35px;  border-bottom:2px #FF6600  solid;/*border-left:1px #CCC solid;border-right:1px #CCC solid;border-bottom:none;*/ color:#FF6600 ; margin-top:2px;}
		.pjliyes a{text-decoration:none;color:#FF6600 ; font-weight: bold;}
	</style>
</head>
<body>
	<div style="width:1210px;float: left; border-left:none;  border-right:none;  border-bottom:none;">
	 <div style="width:1208px; margin-top: 10px;margin-bottom:20px; overflow: hidden; border-bottom:1px #ccc solid;">
      	<div style="margin-top: 5px;padding-left: 10px;font-size: 16px;font-weight:bold;">商品评价</div>
      	<div>
      		<div style="width: 150px;height: 70px;float:left;margin-left: 100px;padding-top:2px; line-height:30px;">
      			<span id="haoVD" style="font-size:40px;color:#FF6600 ; padding:15px 0;"></span>&nbsp;<span style="font-size:20px;color:#FF6600;padding:15px 0;">%</span><br/>
      			<span style="color:#9c9a9c;position: relative;top:-5px;left:14px;">好评度</span>
      		</div>
      		<div style="width: 600px;height: 50px;float:left;">
      			<ul>
      				<li>好评<span style="color:#9c9a9c;line-height: 20px;">&nbsp;(<span id="haoV"></span>%)</span></li>
      				<li>中评<span style="color:#9c9a9c;line-height: 20px;">&nbsp;(<span id="zhongV"></span>%)</span></li>
      				<li>差评<span style="color:#9c9a9c;line-height: 20px;">&nbsp;(<span id="chaV"></span>%)</span></li>
      			</ul>
      		</div>
      	</div>
	 </div>
	 <div class="smsp2 smsp" style="width:1208px;margin-top: 10px; border-bottom:2px #ccc solid;">
	 	<ul>
	      	<li id="ping_1" class="pjliyes" style="border-left: none;"><a onclick="changCurrent(1)" href="${pageContext.request.contextPath}/productsdetail/evaluateTypeList.action?evaluateType=all&productId=<s:property value='productId'/>" target="evaluateList">全部评价(<span id="totalCount"></span>)</a></li>
	      	<li id="ping_2" class="pjlino"><a onclick="changCurrent(2)"  href="${pageContext.request.contextPath}/productsdetail/evaluateTypeList.action?evaluateType=good&productId=<s:property value='productId'/>" target="evaluateList">好评&nbsp;(<s:property value='#request.haoPingCount'/>)</a></li>
	      	<li id="ping_3" class="pjlino"><a onclick="changCurrent(3)"  href="${pageContext.request.contextPath}/productsdetail/evaluateTypeList.action?evaluateType=medium&productId=<s:property value='productId'/>" target="evaluateList">中评&nbsp;(<s:property value='#request.zhongPingCount'/>)</a></li>
	      	<li id="ping_4" class="pjlino"><a onclick="changCurrent(4)"  href="${pageContext.request.contextPath}/productsdetail/evaluateTypeList.action?evaluateType=bad&productId=<s:property value='productId'/>" target="evaluateList">差评&nbsp;(<s:property value='#request.chaPingCount'/>)</a></li>
	 	</ul>
	 </div>
	 
	 <!-- 评价内容 -->
	<div id="iframeDiv" style="width:955px;">
		<iframe id="iframe_body" onload="autoHeight()" name="evaluateList" width="955px" src="${pageContext.request.contextPath}/productsdetail/evaluateTypeList.action?evaluateType=all&productId=<s:property value='productId'/>" frameborder="0" scrolling="no"></iframe>
	</div>
	
 </div>
       
</body>
</html>