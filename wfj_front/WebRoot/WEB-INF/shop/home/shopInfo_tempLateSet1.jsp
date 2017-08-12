<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<%--<!--产品列表_图片-->--%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>店铺首页</title>
<%@ include file="../include/head.jsp"%>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet"/>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/youhuijuan_channel.css" rel="stylesheet" type="text/css" />
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css" rel="stylesheet" type="text/css" />
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/brand_channel.css" rel="stylesheet" type="text/css"/>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/online.css" rel="stylesheet" type="text/css"/>
<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript">
	$().ready(function(){
		//Lazy Load 延迟加载
		$("img.lazy").lazyload({
			effect : "fadeIn",
			threshold : 500
		});
	});
    $(function(){
    	var whichOrderBy = $("#orderBy").val();
    	if(whichOrderBy!=null && whichOrderBy!=""){
    		$("#"+whichOrderBy).css("color","red");
    	}
 	 });
	    //初始化
	    $(function (){
// 	    	选中品牌
	    	if($("#brandHidden").val()!=null){
	    		var bValue=$("#brandHidden").val();//品牌的id数组
	    		if(bValue!=null){
			    	var brandParams=bValue.split(","); //将品牌字符串以','分割
			    	for(var i=0;i<brandParams.length;i++){//循环选中
			    		var brand=brandParams[i];
			    		$("#brand_"+brand).attr("checked",true);
			    	}
	    		}
	    	}
	    });
	    //delect所有查询条件(不包含排序)
	    function clear_all_query_conditions(){
	    	$("#select_screen").css("display","none");
	    	$("[type='checkbox']").removeAttr("checked");//取消选中
	    	window.location="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId="+${shopInfoId}+"&orderBy=normal";
	    }
	    //左侧复选框操作
	    function checkBoxChange(){
	    	//获得排序参数
	    	var f=$("#flag").val();
	    	//查询品牌参数
	    	var brandValue=[];
			$("input[name='品牌Checkbox']:checked").each(function(){
				brandValue.push($(this).val());    
			}); 
			window.location.href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?brandParams="+brandValue+"&shopInfoId="+${shopInfoId}+"&orderBy="+f;
	    }
	    //单个条件的删除
	     function clearOne(params){//params为删除的参数 (color,brand,price)等
	    	//获得排序参数
	    	var f=$("#flag").val();
    		$("#"+params).css("display","none");
    		$("input[name='"+params+"Checkbox']:checked").removeAttr("checked");
	    	if($("input[type='checkbox']:checked").length == 0){//如果没有选中的checkbox 则清除所有左侧查询条件
	    		$("#select_screen").css("display","none");
	    		window.location="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId="+${shopInfoId}+"&orderBy="+f;
	    	}else{
		    	//查询品牌参数
		    	var brandValue=[];
				$("input[name='品牌Checkbox']:checked").each(function(){    
					brandValue.push($(this).val());    
				}); 
				window.location.href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?brandParams="+brandValue+"&shopInfoId="+${shopInfoId}+"&orderBy="+f;
	    	}
	    		 
	    }
	    
	   //品牌链接function 
		  function brandLink(brandName){//参数为品牌名称
		  	$("#searchContent").val(brandName);
		  	//searchImg();
		  	$("#searchForm").submit();
		  }
		
		//点击搜索查询
			function searchImg(){
				var keyWord = $.trim($("#searchContent").val());
				if(keyWord != null && keyWord != ""){
					var newKey = escape(keyWord);
					$("#searchContentValue").val(newKey);
					$("#searchForm").submit();
				}
			};
			//失去焦点与获得焦点事件
			focusAndBlur=function(params){//params用于区别失去焦点 还是  获得焦点
				var kw=$("#searchContent").val();//搜索框中的关键字
				if(params=="focus"){
					if(kw=="请输入品牌，订货号，商品名称"){
						$("#searchContent").val("");
					}
				}else{
					if(kw==""){
						$("#searchContent").val("请输入品牌，订货号，商品名称");
					}
				}
			};
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
				$("#navbox2").hoverClass("current");
				$("#navbox3").hoverClass("current");
				// 回车处理
				$("#searchContent").keydown(function(){ 
					var evt = event ? event : (window.event ? window.event : null);
					if(evt.keyCode == 13){
						var keyWord = $.trim($("#searchContent").val());
						if(keyWord != null && keyWord != "" && keyWord != "请输入品牌，订货号，商品名称"){
							var newKey = escape(keyWord);
							$("#searchContentValue").val(newKey);
							$("#searchForm").submit();
						}else{
							$("#searchContent").val("请输入品牌，订货号，商品名称");
							//$(this).focus();
						};
					};
				});	
			});
			//将搜索框中的内容置空
			function setSearchNull(){
				var keyWord = $.trim($("#searchContent").val());
				if(keyWord == "请输入品牌，订货号，商品名称"){
					$("#searchContent").val("");
				};
			}
			//将搜索框中的内容置空
			function reBack(){
				var keyWord = $.trim($("#searchContent").val());
				if(keyWord == null || keyWord == ""){
					$("#searchContent").val("请输入品牌，订货号，商品名称");
				};
			}
			//搜索商品
			function search(){
				var keyWord = $.trim($("#searchContent").val());
				if(keyWord != null && keyWord != "" && keyWord != "请输入品牌，订货号，商品名称"){
					var newKey = escape(keyWord);
					$("#searchContentValue").val(newKey);
					$("#searchForm").submit();
				}else{
					$("#searchContent").val("请输入品牌，订货号，商品名称");
					//$("#searchContent").focus();
				};
			}
	    
	     //收藏店铺
		    function favoriteShops(){
		    	var type = "<s:property value='#session.customer.type' />";
		    	if(type==2){
					lalert("提醒","供应商不能收藏店铺！");
					return;
				}
		    	window.location.href="${pageContext.request.contextPath}/front/customer/favoriteShops/favoriteShops.action?shopInfoId="+'${shopInfo.shopInfoId}';
		    }
	    //VIP金卡会员申请
	    function memberApply(){
	    	var loginName="<s:property value='#session.customer.loginName' />";
			if(loginName!=null&&loginName!=""){
				var type = "<s:property value='#session.customer.type' />";
				if(type==2){
					lalert("提醒","供应商不能申请！");
				}else{
			    	$.ajax({
			    		url:"${pageContext.request.contextPath}/front/memberShip/checkMember.action",
			    		dataType:"json",
			    		type:"post",
			    		data:{shopInfoId:'${shopInfo.shopInfoId}'},
			    		success:function(data){
			    			if(data.isExsit==true){
			    				lalert("提醒","您已申请该店铺会员，不可重复申请！");
			    			}else{
			    				$('#edit-payway-overlay').overlay({
									mask: {
										color: '#ebecff',
										loadSpeed: 200,
										opacity: 0.4
									}, 
									closeOnClick: false
								});
								$("#edit-payway-overlay").overlay().load();//加载浮层
								$("#apply").click(function(){
									$("#apply").attr("disabled",true);
									location.href="${pageContext.request.contextPath}/front/memberShip/memberApply.action?shopInfoId="+${shopInfo.shopInfoId};
								});
			    			}
			    		}
			    	});
				}
			}else{
				lalert("提醒","请登录，才可申请！");
			}
	    }
</script>
</head>

<body>
	<%@ include file="../include/header.jsp"%>
	<div  style="height:350px;width:100%;">
   			<img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" style="height:350px; width:100%;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='shopInfo.bannerUrl'/>" style=""/>  			
  </div>
<!-- 	店铺主题====================start==================== -->
<div class="margin-center PublicWidth1004" style="height:220px;" >
<!--mainClass-->
  <div class="main_content">
<!--     店铺大图 -->
   <div >
<!--    		右侧展示店铺名称 -->
     <div style="height:200px;width:1210px;padding:10px 0px 0px 0px;">
        <p class="bra">
<!--         <a href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value='shopInfoId'/>">${shopInfo.shopName }</a> -->
        <b>${shopInfo.shopName }</b>
        </p>
        <!-- VIP金卡会员申请 -->
        <div style="float:right;">
             <div style="width: 110px;">&nbsp;&nbsp;<a style="color:#ec3d00;" href="javascript:memberApply();">VIP金卡会员申请</a>&nbsp;&nbsp;</div>
         </div> 
         <div class="modal" id="edit-payway-overlay" style="width: 300px;height: 100px;">
			<h3 class="modal-title" style="padding-left:75px;">
				确定要申请该店VIP金卡会员？
			</h3>
			<p class="modal-buttons" style="margin-left: 60px;margin-top: 20px;">
				<button type="submit" id="apply" style="border: 0;">确定</button>
				<button class="close" type="button" style="border: 0; margin-left: 5px;">关闭</button>
			</p>
		</div>
         <!--     店铺收藏 -->
         <div  style="float:right;">
             <div style="float:left; width:15px; height:15px;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/brand_channel/pinpaipindao_09.png"></div>
             <div style="width: 94px;">&nbsp;&nbsp;<a href="javascript:favoriteShops();">收藏店铺</a>&nbsp;&nbsp;</div>
         </div>   
  		<div class="xiaobiao">
<!-- 店铺等级 -->
            	<div class="yuan">
	            	<s:if test="photoNum < 5">
		            	<s:iterator begin="0" end="photoNum">
		            		<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/shop_home_page/shop_home_page_10.png" width="16" height="16"/>
		            	</s:iterator>
	            	</s:if>
            	</div>
               	<div class="feed">
               	<s:if test="sum > 0">${sum}</s:if>
               	<s:else>0</s:else>&nbsp;信誉</div>
               	<s:if test="grade > 0">
	                <div class="feed"><span class="jiuwu">${grade}%</span>&nbsp;信誉</div>
	                <span style="font-family:Calisto MT;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#999999;">店主：${shopInfo.customerName}</span>
               	</s:if>
               	<s:else>
               		<div class="feed"><span class="jiuwu">0%</span>&nbsp;信誉</div>
               		<span style="font-family:Calisto MT;font-size:14px;font-weight:normal;font-style:normal;text-decoration:none;color:#999999;">店主：${shopInfo.customerName}</span>
               	</s:else>
          </div>
<!--          店铺简介 -->
	          <div  style="height: 112px;font-size:14px;line-height:28px;width:1080px; margin-top:10px;background:none;word-wrap:break-word;overflow:hidden;">
					${shopInfo.synopsis}
			  </div>
			<!-- 微信二维码begin -->
 			<img style=" width:112px; height:112px; float:right; position: relative; top:-110px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='shopInfo.qrCode'/>" />
  			<!-- 微信二维码end -->
			<!-- 	<div style="float: right; width:112px; height:112px; margin:10px 0 0 10px;"></div> -->
	          <div class="clear"></div>
        </div>
        <div class="clear"></div>
   </div>
  </div>
  <div style="height: 10px;"></div>
      <!--//mainClass-->
</div>
<!-- 	店铺商品展示====================start==================== -->
<div class="margin-center PublicWidth1004">
		<div class="ClearBoth">
			<!--left-->
			<aside id="leftBox" class="publicBorder float-left BackgroundF8Hui">
	  <h3 class="publicPadding10 ColorRed publicrelative publicBorderB_3red FontSize15">分类</h3><!-- 左侧列表 -->
	     <div class="FontSize13 FontSizeB publicblock">  
            <ul class="CategoryUl_L">
					<s:iterator value="productTypeList">
						<li><a
							href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId=<s:property value="shopInfoId"/>&shopProCategoryId=<s:property value="shopProCategoryId"/>&orderBy=<s:property value='orderBy'/>"
							style="text-decoration: none;"> <s:property
									value="shopProCategoryName" /></a><span style="color: #999;"></span></li>
					</s:iterator>
				</ul>
           </div>
           <!-- QQ客服begin -->
		<s:if test="qqList!=null">
			<h3 class="publicPadding10 ColorRed publicrelative publicBorderB_3red FontSize15">QQ客服</h3><!-- 左侧列表 -->
			<div class="FontSize13 FontSizeB publicblock">  
				<ul class="CategoryUl_L" style="padding: 5px 24px;">
					<s:iterator value="qqList" var="map">
						<li><a style="text-decoration: none;" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=<s:property value='#map.qq'/>&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:12345678:51" />&nbsp;&nbsp;&nbsp;&nbsp;<span style="background-color:#2e9bf2; width: 191px; height:16px;font-size:12px; padding:2px 0; vertical-align:middle; color:#fff; border-radius:3px;">&nbsp;&nbsp;<s:property value="#map.nikeName" />&nbsp;&nbsp;</span></a></li>
					</s:iterator>
				</ul>
			</div>
		</s:if>
		 <!-- QQ客服end -->
				<!-- 推荐品牌 -->
 				<h3  class="publicPadding10 ColorRed publicrelative publicBorderB_3red FontSize15 publicMarginTop15">推荐品牌</h3> 
				<div class="FeaturedBrands_img publicMarginBot15 publicPaddingLR10" >
				<s:if  test="brandList.size>0">
				 <ul>
				 <s:iterator value="brandList" var="brand">
				 <li>
					<a href="javascript:${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action?pager.keyword=<s:property value='#brand.brandName'/>;">
					   <img style="height:100px; width:200px;" class="lazy" src="${fileUrlConfig.visitFileUploadRoot}<s:property value='brandBigImageUrl'/>"  data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#brand.brandBigImageUrl"/>" /></a>
				 </li>
				 </s:iterator>
				 </ul>
				</s:if>
				<s:else>
				 <div>
				   <span>
				      <font style="font-size: 12px;">在这个类别下没有品牌图片！</font>
				   </span>
				 </div>
				</s:else>
				</div>
			</aside>
			<!--//left-->
			<input id="flag" type="hidden" value="<s:property value='orderBy'/>"/>
			<!--right-->
			<div id="rightBox" class="float-right publicHidden">
				<!-- 排序 -->
				<aside>
					<div class="FontSize13 gradient publicBorder publicPadding10 publicMarginBot15 produstsList_screen publicrelative ClearBoth">
						<input type="hidden" id="orderBy" value="${orderBy}"/>
						<a id="putSaleDate" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId=<s:property value="shopInfoId"/>&orderBy=putSaleDate">最新上架</a> 
						<a id="totalSales" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId=<s:property value="shopInfoId"/>&orderBy=totalSales">销量</a> 
						<a id="salesPriceAsc" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId=<s:property value="shopInfoId"/>&orderBy=salesPriceAsc" class="price_up">价格</a> 
						<a id="salesPriceDesc" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?shopInfoId=<s:property value="shopInfoId"/>&orderBy=salesPriceDesc" class="price_down">价格</a> 
					</div>
				</aside>
              <!-- 查询的条件显示 -->
              <s:if test="mapParams.size>0">
					<div id="select_screen" class="select_screen" >
					<s:iterator value="mapParams" id="map">
						<span id="${map.key }"> ${map.key }: <s:property value="#map.value"/> <a  onclick="clearOne('${map.key }');" title="Remove Auction" class="remove"></a></span>
					</s:iterator>
						 <a href="javascript:clear_all_query_conditions();" class="Clear_All" >全部清除</a>
					</div> 
				</s:if> 
				<!-- 非顶置商品 -->
				<div class="ProitemBox publicMarginTop15" >
     		<form id="formModule" action="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action" method="post"> 
				 <input name="shopInfoId" type="hidden" value="<s:property value='shopInfoId'/>" />
				 <input name="shopProCategoryId" type="hidden" value="<s:property value='shopProCategoryId'/>" />
				 <input name="orderBy" type="hidden" value="<s:property value='orderBy'/>" />
				<ul class="WishListUl ClearBoth">
					<s:if test="productInfoList.size>0">
					<s:iterator value="productInfoList" var="productInfo" status="s">
							<li style="height:303px;margin:0 7px 0 8px;"><a target="_blank"  style="display:block;width:220px;height:220px;" href="${pageContext.request.contextPath}/productInfo/<s:property value="#productInfo.productId" />.html">
								<img style="width:220px;height:220px;"  title="<s:property value="#productInfo.productFullName"/>" class="lazy"
									src="${fileUrlConfig.visitFileUploadRoot}<s:property value='logoImg'/>" data-original="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#productInfo.logoImg"/>"  style="height:220px;width:220px;"/>
								</a>
								<p style="height: 38px;overflow: hidden; margin-top:7px;">
									<a target="_blank" title="<s:property value="#productInfo.productName"/>" href="${pageContext.request.contextPath}/productInfo/<s:property value='#productInfo.productId'/>.html"><s:property value="#productInfo.productName" /></a>
								</p>
								<%-- <p class="ColorRed">
									&nbsp;&nbsp;<span class="FontSize18">￥<s:property  value="#productInfo.salesPrice" /></span>
								</p> --%>
								<p class="ColorRed">
									<span style="float:right;">共<s:property value="#productInfo.goodsCount" />种规格</span>
									<span class="FontSize18">￥<s:property  value="#productInfo.salesPrice" /><font size="2px">起</font></span>
								</p>
							</li>
					</s:iterator>
					</s:if>
			   </ul>
			
					<!--分页 start-->
					<s:if test="productInfoList.size>0">
					<div class="pageList strong ClearBoth">
					     <jsp:include page="../../util/splitPage.jsp" />
					</div>
					</s:if>
					<!--分页 end-->
				</form>	
					
				</div>
			</div>
				<!--//Proitem-->
			<!--//right-->
		</div>
	</div>
<!-- 	店铺商品展示====================end==================== -->
<%-- <!-- 客服js -->
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/online.js"></script>
<div class='QQbox' id="divQQbox" >
	<div class='Qlist' id='divOnline' onmouseout='hideMsgBox(event);' style='display:none;'>
		<div class='online_top'><img src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/online_01.png' width='170' height='27'/></div>
		<div class='online_empty'><img src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/online_02.png' width='170' height='12'/></div>
		<div class='online_content'>
		<s:if test="qqList!=null">
			<s:iterator value="qqList" var="map">
			<div class='online_list'><a target="blank" href="http://wpa.qq.com/msgrd?v=3&uin=<s:property value="#map.qq" />&site=qq&menu=yes"><img border='0' style='float:left;padding-top:10px;' src="http://wpa.qq.com/pa?p=2:<s:property value="#map.qq" />:17 alt='点击咨询'"/><span style='float:left;cursor: pointer;'><s:property value="#map.nikeName" /></span></a></div>
			</s:iterator>
		</s:if>
		</div>
		<div class='online_bot'><img src='/upload/front/shop/front/images/online_07.png' width='170' height='23'/></div>
	</div>
	<div id='divMenu' onmouseover='OnlineOver();'><img src='/upload/front/shop/front/images/mouseout.png' class='press' alt='在线咨询'/></div>
</div>
<script type="text/javascript">

var tips;
var theTop = 95;/*这是默认高度,越大越往下*/ 
var old = theTop;
initFloatTips();
function initFloatTips() {
	tips = document.getElementById("divQQbox");
	moveTips();
}

function moveTips() {
	var tt=50;
	if (window.innerHeight) {
		pos = window.pageYOffset;
	}else if (document.documentElement && document.documentElement.scrollTop) {
		pos = document.documentElement.scrollTop;
	}else if (document.body) {
		pos = document.body.scrollTop;
	}
	pos=pos-tips.offsetTop+theTop;
	pos=tips.offsetTop+pos/10;
	if (pos < theTop) pos = theTop;
	if (pos != old) {
		tips.style.top = pos+"px";
		tt=10;
	}
	old = pos;
	setTimeout(moveTips,tt);
}

function OnlineOver(){
document.getElementById("divMenu").style.display = "none";
document.getElementById("divOnline").style.display = "block";
document.getElementById("divQQbox").style.width = "170px";
}

function OnlineOut(){
document.getElementById("divMenu").style.display = "block";
document.getElementById("divOnline").style.display = "none";
}
if(typeof(HTMLElement)!="undefined")    //给firefox定义contains()方法，ie下不起作用
{   
      HTMLElement.prototype.contains=function(obj)   
      {   
          while(obj!=null&&typeof(obj.tagName)!="undefind"){ //通过循环对比来判断是不是obj的父元素
	      if(obj==this) return true;   
	      obj=obj.parentNode;
	  }   
return false;
      };
}
function hideMsgBox(theEvent){ //theEvent用来传入事件，Firefox的方式
	if (theEvent){
		var browser=navigator.userAgent; //取得浏览器属性
		if (browser.indexOf("Firefox")>0){ //如果是Firefox
			if (document.getElementById('divOnline').contains(theEvent.relatedTarget)) { //如果是子元素
				return; //结束函式
			} 
		}
		if (browser.indexOf("MSIE")>0){ //如果是IE
			if (document.getElementById('divOnline').contains(event.toElement)) { //如果是子元素
			return; //结束函式
			}
		}
	}
/*要执行的操作*/
document.getElementById("divMenu").style.display = "block";
document.getElementById("divOnline").style.display = "none";
}

</script>
 --%>	<%@ include file="../include/footer.jsp"%>
</body>
</html>
