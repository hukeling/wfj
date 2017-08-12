<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商城首页</title>
<link type="text/css" rel="stylesheet" href="${contextPath}/common/shop/homeIndex/css/index_header.css"/>
<link type="text/css" rel="stylesheet" href="${contextPath}/common/shop/homeIndex/css/header.css"/>
<link type="text/css" rel="stylesheet" href="${contextPath}/common/shop/homeIndex/css/base.css"/>
<link type="text/css" rel="stylesheet" href="${contextPath}/common/shop/homeIndex/css/style.css"/>
<link type="text/css" rel="stylesheet" href="${contextPath}/common/shop/homeIndex/css/public.css"/>
<link type="text/css" rel="stylesheet" href="${contextPath}/common/shop/front/css/scrollup.css"/>

<script type="text/javascript" src="${contextPath}/common/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${contextPath}/common/shop/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="${contextPath}/common/shop/homeIndex/js/tab.js"></script>
<script type="text/javascript" src="${contextPath}/common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${contextPath}/common/shop/front/js/jquery.scrollUp.js"></script>

<!--延迟加载js-->
<script type="text/javascript">
	$(function(){
		$.scrollUp();
		//首页图片延迟加载
		$("img.lazy").lazyload({         
			effect : "fadeIn"    
		});
	});
</script>

<!--顶部js-->
<script type="text/javascript">
	//点击搜索查询
	searchImg=function(){
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
			if(kw=="请输入关键字"){
				$("#searchContent").val("");
			}
		}else{
			if(kw==""){
				$("#searchContent").val("请输入关键字");
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
	});
	//将搜索框中的内容置空
	function setSearchNull(){
		var keyWord = $.trim($("#searchContent").val());
		if(keyWord == "请输入关键字"){
			$("#searchContent").val("");
		};
	}
	//将搜索框中的内容置空
	function reBack(){
		var keyWord = $.trim($("#searchContent").val());
		if(keyWord == null || keyWord == ""){
			$("#searchContent").val("请输入关键字");
		};
	}
	//搜索商品
	function search(){
		var keyWord = $.trim($("#searchContent").val());
		if(keyWord != null && keyWord != "" && keyWord != "请输入关键字"){
			var newKey = escape(keyWord);
			$("#searchContentValue").val(newKey);
			$("#searchForm").submit();
		}else{
			$("#searchContent").val("请输入关键字");
			//$("#searchContent").focus();
		};
	}
	// 回车处理
	$("#searchContent").keydown(function(){ 
		if(event.keyCode == 13){
			var keyWord = $.trim($("#searchContent").val());
			if(keyWord != null && keyWord != "" && keyWord != "请输入关键字"){
				var newKey = escape(keyWord);
				$("#searchContentValue").val(newKey);
				$("#searchForm").submit();
			}else{
				$("#searchContent").val("请输入关键字");
				//$(this).focus();
			};
		};
	});	
</script>

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
				});
			});
		};
	})(jQuery);
	$(function(){
		var cookie = $.cookie('customerCar');
		//初始化判断cookie中用户名是否存在
		var gsjshop_customerNameValue=$.cookie('gsjshop_customerName');
		if(chkcookies("gsjshop_customerName")){
			if(gsjshop_customerNameValue!=""){
				$("#loginShow").css("display","block");
				$("#noLoginShow").css("display","none");
				$("#cookie_loginName").html("<font color='red'>"+gsjshop_customerNameValue+"</font>");
				$("#cookie_grzx").attr("name",gsjshop_customerNameValue);
			}else{
				$("#loginShow").css("display","none");
				$("#noLoginShow").css("display","block");
			}
		}else{
			$("#loginShow").css("display","none");
			$("#noLoginShow").css("display","block");
		}			
	});
	//检测cookie
	function chkcookies(NameOfCookie){
	  var c = document.cookie.indexOf(NameOfCookie+"="); 
	  if (c != -1){
	    return true;
	  }
	  return false;
	}
	function verdictgwc() {
		location.href = "${contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
	}
</script>

<!--轮换图 js-->
<script type="text/javascript">
$(function() {
	var sWidth = $("#focus").width(); //获取焦点图的宽度（显示面积）
	var len = $("#focus ul li").length; //获取焦点图个数
	var index = 0;
	var picTimer;
	
	//以下代码添加数字按钮和按钮后的半透明条，还有上一页、下一页两个按钮
	var btn = "<div class='btnBg'></div><div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div><div class='preNext pre' style='display:none;'></div><div class='preNext next' style='display:none;'></div>";
	$("#focus").append(btn);
	$("#focus .btnBg").css("opacity",0.5);

	//为小按钮添加鼠标滑入事件，以显示相应的内容
	$("#focus .btn span").css("opacity",0.4).mouseenter(function() {
		index = $("#focus .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");

	//上一页、下一页按钮透明度处理
	$("#focus .preNext").css("opacity",0.2).hover(function() {
		$(this).stop(true,false).animate({"opacity":"0.5"},300);
	},function() {
		$(this).stop(true,false).animate({"opacity":"0.2"},300);
	});

	//上一页按钮
	$("#focus .pre").click(function() {
		index -= 1;
		if(index == -1) {index = len - 1;}
		showPics(index);
	});

	//下一页按钮
	$("#focus .next").click(function() {
		index += 1;
		if(index == len) {index = 0;}
		showPics(index);
	});

	//本例为左右滚动，即所有li元素都是在同一排向左浮动，所以这里需要计算出外围ul元素的宽度
	$("#focus ul").css("width",sWidth * (len));
	
	//鼠标滑上焦点图时停止自动播放，滑出时开始自动播放
	$("#focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},4000); //此4000代表自动播放的间隔，单位：毫秒
	}).trigger("mouseleave");
	
	//显示图片函数，根据接收的index值显示相应的内容
	function showPics(index) { //普通切换
		var nowLeft = -index*sWidth; //根据index值计算ul元素的left值
		$("#focus ul").stop(true,false).animate({"left":nowLeft},300); //通过animate()调整ul元素滚动到计算出的position
		//$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
		$("#focus .btn span").stop(true,false).animate({"opacity":"0.4"},300).eq(index).stop(true,false).animate({"opacity":"1"},300); //为当前的按钮切换到选中的效果
	}

	//鼠标在图片上
	$("#focus").mouseover(function() {
		$("#focus .pre").css("display","block");
		$("#focus .next").css("display","block");
	});
	//鼠标移走
	$("#focus").mouseout(function() {
		$("#focus .pre").css("display","none");
		$("#focus .next").css("display","none");
	});
	
});
	
</script>

<!--热销榜单 js-->
<script type="text/javascript">
	//切换展示
	function openThis(obj){
		var liSize = obj.parentElement.children.length;
		var elementList = obj.parentElement.children;
		for(var i=0;i<liSize;i++){
			if(elementList[i] == obj){
				$(elementList[i]).removeClass("onee");
				$(elementList[i]).addClass("on");
				//切换的同时加载图片
				$(elementList[i]).find('img').each(function() {
					var original = $(this).attr("data-original");
					if (original) {
						$(this).attr('src', original).removeAttr('data-original');
					}
				});
			}else{
				$(elementList[i]).removeClass("on");
				$(elementList[i]).addClass("onee");
			}
		}
	}
	//转向一级分类
	function goFirstType(firstTypeId){
		var winopen = window.open ("${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&orderBy=normal");
		if( winopen==null){//自动提交的链接
		    document.write('<a href="${contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&orderBy=normal" target="_blank" id="autopop" style="display:none"></a>');
		    document.getElementById('autopop').click(); 
		}
	}
</script>

</head>

<body>
  <div class="hesder_topCode">
        <!--top-->
        <div class="hesder_top">
        	<div id="loginShow" style="padding-top:5px;display:none;">
				<span id="cookie_loginName"></span>，欢迎光临本店！
				&nbsp;&nbsp;[<a href="${contextPath}/loginCustomer/exitLogin.action">退出登录</a>]
			</div>
            <div id="noLoginShow" class="welcomeYin fl" style="display:none;" >亲，欢迎来天海!<a href="${contextPath}/loginCustomer/gotoLoginPage.action">请登录</a><a href="${contextPath}/register/gotoRegister.action">免费注册</a></div>
            <div class="welcomeYinRight fr">
                <a style=" margin-top:6px; margin-right:7px;" class="fl" href="javascript:doRedirect('1');">买家中心</a>
                <a style=" margin-top:6px; margin-right:7px;" class="fl" href="javascript:doSellerCheck();">卖家中心</a>
            </div>
        </div>
       </div>   
        <!--top end-->
        <!--topBottom-->
        <div class="topBottom">
            <h1 class="fl logoYin"><a href="${contextPath}"><img style="width: 155px;height:53px;" src="${contextPath}/common/shop/front/images/logo.png"/></a></h1>  
            <div class="fl searchYin">
            <form action="${contextPath}/frontSearchProduct/productInfoSearch.action" method="get" id="searchForm">
            <input type="hidden" name="pager.keyword" id="searchContentValue" />
                <div class="searchYinT">
		            <input style="color:#727171;line-height:16px;" id="searchContent" class="searchText" type="text" value="请输入关键字" onfocus="focusAndBlur('focus')" onblur="focusAndBlur('blur')"  value="<s:property value='keyword'/>" />
                    <input onclick="searchImg()" class="searchSubmit" type="submit" value="搜 索"/>
                </div>
                <!-- <div class="searchYinB">
                    <a href="#">热门搜索</a>
                </div> -->
            </form> 
            </div>
            <div class="fl guaranteeYin">
                <span class="fl">
                    <img class="fl" src="${contextPath}/common/shop/homeIndex/img/picYin.png"/>
                    <a class="fl" href="${contextPath}/helpFront/gotoFooterArticleList.action?articleId=660">品质保障</a>
                </span>
                
                 <span class="fl">
                    <img class="fl" src="${contextPath}/common/shop/homeIndex/img/picYin2.png"/>
                    <a class="fl" href="${contextPath}/helpFront/gotoFooterArticleList.action?articleId=660">售后保障</a>
                </span>
                 <span class="fl">
                    <img class="fl" src="${contextPath}/common/shop/homeIndex/img/picYin3.png"/>
                    <a class="fl" href="${contextPath}/helpFront/gotoFooterArticleList.action?articleId=660">服务保障</a>
                </span>
            </div>  
        </div>
        <!--nav-->
        
            <div class="header_nav">
                <div class="header_navContent">
                        <div class='allsort'>
                            <div class='mt'><strong><a href="${contextPath}/shophomeIndex/allProuductType.do">全部商品分类</a></strong></div>
                            <div class='mc'>
                                <div class="mcCode">
                                <div class="header_bgyinT"></div>
                                <#if (categoryList?size gt 0)> 
                                <#list categoryList as m>
                                <div class="item <#if m_index==0>fore</#if>">
                                    <span>
                                        <img style="left:10px;" src="${contextPath}/common/shop/homeIndex/img/header_navpic${m_index+1}.png" width="20" height="19"/>
                                        <h3>
                                           <a href="javascript:void(0);" onclick="goFirstType(${m.productTypeId})">${m.sortName}</a>
                                        </h3>
                                        <s></s>
                                    </span>
                                    <div class='i-mc'>
                                        <div class='close' onclick="$(this).parent().parent().removeClass('hover')"></div>
                                        <div class='subitem'>
                                        <#list m.childProductType as secondm>
                                            <dl class="<#if secondm_index==0>fore</#if>">
                                                <dt style="cursor:pointer;" onclick="goFirstType(${secondm.productTypeId})">
                                                	${secondm.sortName}
                                                </dt>
                                                <dd>
                                                <#list secondm.childProductType as third>
                                                    <em><a href="javascript:void(0);" onclick="goFirstType(${third.productTypeId})">${third.sortName}</a></em>
                                                </#list>   
                                                </dd>
                                            </dl>
                                        </#list>
                                        </div>
                                        <div id="JD_sort_k" class='fr'>
										<div id="JD_sort_k" class='fr' style="height: 400px;overflow: hidden;">
											<#if m.brandList??>
												<#list m.brandList as brand >
		                                           		 <img src="${fileUrlConfig.visitFileUploadRoot}${brand.brandImageUrl}" width="194" height="70" alt="" style="margin-top:10px;" />
												</#list>
											</#if>
                                        </div>
                                        </div>
                                    </div>
                                </div>
                                </#list>
                                </#if>
                                </div>
                                <div class="header_bgyinB"></div>
                            </div>
                        </div>
                        <script type="text/javascript"> 
                            $(".allsort").hoverForIE6({current:"allsorthover",delay:200});
                            $(".allsort .item").hoverForIE6({delay:150});
                        </script>
            
                        <!--allsort end-->
                       
                    <div class="primaryNav fl">
                        <a href="/" style="background-color: #0F5FB1;">首页</a>
                        <a href="${contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops">店铺</a>
                        <a href="${contextPath}/brandChannel/gotoBrandChannelPage.action?headerType=brands">品牌</a>
                    </div>
                    <div class="header_shoppingCart">
	                    <span></span>
                        <a href="javascript:verdictgwc()" style="text-decoration: none;">购物车<b id="cookie"></b></a>
                    </div>
                </div>
            </div>
        
        <!--nav end-->
    
<div class="wrapper">
    <div class="fl" style="margin-left:200px;">
	<div id="focus">
		<ul>
			<#if (homeLBTList?size gt 0)>
				<#list homeLBTList as h>
					<li><a href="${h.interlinkage}" target="_blank"><img src="${fileUrlConfig.visitFileUploadRoot}${h.broadcastingIamgeUrl}" alt="" /></a></li>
				</#list>
			</#if>
		</ul>
	</div>
    <div class="bannerB">
    <!--123号广告位-->
	    <#if (homeAdvertisementList?size gt 0)>
	    		<a href="${homeAdvertisementList[0].link}" target="_blank"><img src="${fileUrlConfig.visitFileUploadRoot}${homeAdvertisementList[0].imageUrl}" /></a>
	    		<a href="${homeAdvertisementList[1].link}" target="_blank"><img src="${fileUrlConfig.visitFileUploadRoot}${homeAdvertisementList[1].imageUrl}" /></a>
	    		<a href="${homeAdvertisementList[2].link}" target="_blank"><img src="${fileUrlConfig.visitFileUploadRoot}${homeAdvertisementList[2].imageUrl}" /></a>
	    </#if>          
	</div>
    </div>
    <!--焦点图右侧-->
	 <div class="expressPart fl">
	 				<#if (homeAdvertisementList?size gt 2)>
	                	<a href="${homeAdvertisementList[3].link}" target="_blank" ><img src="${fileUrlConfig.visitFileUploadRoot}${homeAdvertisementList[3].imageUrl}" /></a>
                	</#if>
	                <!--天天快报-->
	                <div class="dailyExpress">
	                    <span>天天快报</span>  
	                    <div class="dailyExpressC" style="height:248px;border-bottom: 1px solid #EEEEEE;">	
	                    	<#if (articleList?size gt 0)>
	                    		<#list articleList as al>
	                    			<a href="${contextPath}/helpFront/gotoFooterArticleList.action?articleId=${al.articleId}" target="_blank">${al.title}</a>
	                    		</#list>
	                    	</#if>
	                    </div>
	                </div>
	                <!--促销信息-->
	               <#-- <div class="promotionalYin">
	                    <span>促销信息</span>
	                    <div class="promotionalYinC">
	                    	<#if (promotionList??&&promotionList?size gt 0)>
	                    		<#list promotionList as pl>
	                    			<a href="${contextPath}/front/store/salesPromotionCenter/gotoSalesPromotionCenter.action?promotionId=${pl.promotionId}" target="_blank">${pl.promotionName}</a>
	                    		</#list>
	                    	</#if>
	                    </div>
	                </div> -->
	            </div>           
	

</div><!-- wrapper end -->

<div class="mainY">
            <!--限时速购-->
            <div class="instantaneousCode">
                <!--限时速购倒计时-->
            	<div class="instantaneousPurchase fl">
                    <div class="instantaneousTitle" id="limitColumn_li_current_">
                        <ul>
                        	<#if (#application.homekeybook['countDownLocation']?size gt 0) >
                        		<#list homekeybook["countDownLocation"] as hkb >
	                            	<#if (hkb_index==0)>
	                            		<li class="current">${hkb.typeName}<>
	                            	<#else>
	                            		<li >${hkb.typeName}<>
	                            	</#if>
                            	</#list>
                        	</#if>
                        </ul>
                    </div>
            		<div class="instantaneousContent" id="limitColumn_1">
	                    <#if (homeParticularlyTabList1?size gt 0)>
	                    	<#list homeParticularlyTabList1 as hp1 >
			                        <div class="commodityYin">
			                            <div class="priceMarking"><img src="${contextPath}/common/shop/homeIndex/img/priceMarking.png"/></div>
			                            <div class="countdown">
			                                    <span>${homekeybook["countDownLocation"][0].typeName}</span>
			                            </div>
			                            <!--倒计时 end-->
			                            <a class="commodityPic" target="_blank" href="${hp1.link}"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${hp1.imageUrl}" width="160" height="150"/></a>
			                            <a href="${hp1.link}" target="_blank">${hp1.synopsis}</a><!--描述-->
			                            <div class="priceYin">天海价：<span>￥${hp1.price}</span></div>
			                        </div>
	                    	</#list>
	                    </#if>
                    </div>
            		<div id="limitColumn_2" class="instantaneousContent hide">
	                    <#if (homeParticularlyTabList2?size gt 0)>
	                    	<#list homeParticularlyTabList2 as hp2 >
			                        <div class="commodityYin">
			                            <div class="priceMarking"><img src="${contextPath}/common/shop/homeIndex/img/priceMarking.png"/></div>
			                            <div class="countdown">
			                            	<span >${homekeybook["countDownLocation"][1].typeName}</span>
			                            </div>
			                            <a class="commodityPic" target="_blank" href="${hp2.link}"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${hp2.imageUrl}" width="160" height="150"/></a>
			                            <a href="${hp2.link}" target="_blank">${hp2.synopsis}</a>
			                            <div class="priceYin">天海价：<span>￥${hp2.price}</span></div>
			                        </div>
	                    	</#list>
	                    </#if>
                    </div>
            		<div id="limitColumn_3" class="instantaneousContent hide">
	                    <#if (homeParticularlyTabList3?size gt 0)>
	                    	<#list homeParticularlyTabList3 as hp3 >
			                        <div class="commodityYin">
			                            <div class="priceMarking"><img src="${contextPath}/common/shop/homeIndex/img/priceMarking.png"/></div>
			                            <div class="countdown">
			                            	<span >${homekeybook["countDownLocation"][2].typeName}</span>
			                            </div>
			                            <a class="commodityPic" target="_blank" href="${hp3.link}"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${hp3.imageUrl}" width="160" height="150"/></a>
			                            <a href="${hp3.link}" target="_blank">${hp3.synopsis}</a>
			                            <div class="priceYin">天海价：<span>￥${hp3.price}</span></div>
			                        </div>
	                    	</#list>
	                    </#if>
                    </div>
            		<div id="limitColumn_4" class="instantaneousContent hide">
	                    <#if (homeParticularlyTabList4?size gt 0)>
	                    	<#list homeParticularlyTabList4 as hp4 >
			                        <div class="commodityYin">
			                            <div class="priceMarking"><img src="${contextPath}/common/shop/homeIndex/img/priceMarking.png"/></div>
			                            <div class="countdown">
			                            	<span >${homekeybook["countDownLocation"][3].typeName}</span>
			                            </div>
			                            <a class="commodityPic" target="_blank" href="${hp4.link}"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${hp4.imageUrl}" width="160" height="150"/></a>
			                            <a href="${hp4.link}" target="_blank">${hp4.synopsis}</a>
			                            <div class="priceYin">天海价：<span>￥${hp4.price}</span></div>
			                        </div>
	                    	</#list>
	                    </#if>
                    </div>
            		<div id="limitColumn_5" class="instantaneousContent hide">
	                    <#if (homeParticularlyTabList5?size gt 0)>
	                    	<#list homeParticularlyTabList5 as hp5 >
			                        <div class="commodityYin">
			                            <div class="priceMarking"><img src="${contextPath}/common/shop/homeIndex/img/priceMarking.png"/></div>
			                            <div class="countdown">
			                            	<span >${homekeybook["countDownLocation"][4].typeName}</span>
			                            </div>
			                            <a class="commodityPic" target="_blank" href="${hp5.link}"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${hp5.imageUrl}" width="160" height="150"/></a>
			                            <a href="${hp5.link}" target="_blank">${hp5.synopsis}</a>
			                            <div class="priceYin">天海价：<span>￥${hp5.price}</span></div>
			                        </div>
	                    	</#list>
	                    </#if>
                    </div>
                   <script type="text/javascript">
					  new tab ('limitColumn_li_current_', '_',function(){
								//获取当前标签索引
								var n=this['Index'];
								//切换标签后触发延迟加载函数
								var objContent=document.getElementById("limitColumn_"+n);
								$(objContent).find('img').each(function() {
									var original = $(this).attr("data-original");
									if (original) {
										$(this).attr('src', original).removeAttr('data-original');
									}
								});
							}, 'onmouseover');
				   </script>
                </div>
                <div class="theFirstCode">
                    <!--首发-->
                    <#if (homeAdvertisementList?size gt 3)>
	                    <div class="theFirst">
	                        <div class="theFirstT"><img src="${contextPath}/common/shop/homeIndex/img/theFirstT.png"/></div>
	                        <div style="height:5px; overflow:hidden;">
	                        </div>
	                        <div class="theFirstC">
	                            <a class="fl theFirstPic" href="${homeAdvertisementList[4].link}" target="_blank"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${homeAdvertisementList[4].imageUrl}"/></a>
	                            <div class="theFirstCo fl">
	                                <span>首发<a href="${homeAdvertisementList[4].link}" target="_blank">${homeAdvertisementList[4].title}</a></span>
	                                <a class="theFirstText" href="${homeAdvertisementList[4].link}" target="_blank">${homeAdvertisementList[4].synopsis}</a>
	                            </div>
	                        </div>
	                    </div>
                    </#if>
                    <!--一体机-->
                    <#if (homeAdvertisementList?size gt 4)>
	                     <div class="integratedMachine">
	                        <div class="theFirstT"><img src="${contextPath}/common/shop/homeIndex/img/yitijO.png"/></div>
	                        <div style="height:5px;overflow:hidden;">
	                        </div>
	                        <div class="theFirstC" style="height:163px;">
	                            <img src="${contextPath}/common/shop/homeIndex/img/yitiji.jpg" width="275" height="123"/>
	                           <div class="machine"><a class="" href="${homeAdvertisementList[5].link}" target="_blank">详情</a></div>
	                        </div>
	                    </div>
                    </#if>
                    
                </div>
            </div>
            
        <!--楼层内容-->
        <#if (listMap?size gt 0)> 
        	<#assign fir_index = 1 />
        	<#list listMap as m>
	            <div class="floor">
	                <!--分类-->
	                <div class="brand fl">
	                    <span>${m.yiJCategory.categoryName}</span><!--一级分类名称-->
	                    <div class="allBrand">
	                        <div class="ificationY">
	                        	<!--遍历一级分类下的二级小分类-->
	                        	<#if (m.secCategory?size gt 0)>
	                        		<#list m.secCategory as erc>
			                            <a target="_blank" href="${erc.link}">${erc.title}</a>
	                        		</#list>
	                        	</#if>
	                        </div>
	                        	<!--一级分类下促销-->
	                        <#if (m.promotionProduct)??>	
	                       	 <a target="_blank" class="ificationPic" href="${m.promotionProduct.link}"><img width="209px" height="170px" class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${m.promotionProduct.imageUrl}"></a>
	                        </#if>
	                    </div>
	                </div>
	                <!--商品分类-->
	                <div class="plistY fl">
	                    <div class="instantaneousTitle plistT" id="smc${fir_index}_li_current_">
	                        <ul>
	                        	<!-- 遍历中间tab部分 -->
	                        	<#if (m.listMapTab)??>
		                        	<#if (m.listMapTab?size gt 0)>
		                        		<#assign fir = m.listMapTab[0] /> <!--把listMapTab的第1个元素赋值给fir --> 
			                            <li class="current"><a target="_blank" href="${fir.link}">${fir.tabName}</a></li>
		                        		<#assign others = m.listMapTab?size-1 /> <!--计算剩余的obj个数 --> 
		                        		<#if (others>1)>
			                        		<#list 1..others as x>
					                            <li><a target="_blank" href="${m.listMapTab[x].link}">${m.listMapTab[x].tabName}</a></li>
			                        		</#list>
		                        		</#if>
		                        	</#if>
	                        	</#if>
	                        </ul>
	                    </div>
	                    <#if (m.listMapTab)??>
		                    <#if (m.listMapTab? size gt 0)>
	                            <#assign index = 1 /> <!--定义 一个变量  用于存储当前index --> 
		                    	<#list m.listMapTab as tabMap>
		                    		<#if index==1>
					                    <div class="smc" id="smc${fir_index}_${index}">
				                    <#else>
					                    <div class="smc hide" id="smc${fir_index}_${index}">
				                    </#if>
				                        <!--轮播图-->
				                        <div class="slideCode">
				                            <div class="slide">
				                            	 <!--绿色方块-->
				                                <div class="slideT" id="slideC${fir_index}${index}_a_current_">
					                                <#if (tabMap.lbtList?size gt 0)>
					                                	 <#assign tab_index = 1 />
					                                	 <#list tabMap.lbtList as lbt>
					                                	 	<#if tab_index==1>
								                                <a target="_blank" class="current" href="${lbt.link}"></a>
					                                	 	<#else>
								                                <a target="_blank" href="${lbt.link}"></a>
					                                	 	</#if>
					                                	 	 <#assign tab_index = tab_index+1 />
					                                	 </#list>
					                                </#if>
				                                </div>
				                                 <!--图片-->
				                                <#if (tabMap.lbtList?size gt 0)>
				                                	 <#assign tab_index = 1 />
				                                	 <#list tabMap.lbtList as lbt>
				                                	 	<#if tab_index==1>
							                                <a target="_blank" class="slideC" id="slideC${fir_index}${index}_${tab_index}" href="${lbt.link}"><img src="${fileUrlConfig.visitFileUploadRoot}${lbt.imageUrl}"/ width="473" height="184"></a>
				                                	 	<#else>
							                                <a target="_blank" class="slideC hide" id="slideC${fir_index}${index}_${tab_index}" href="${lbt.link}"><img src="${fileUrlConfig.visitFileUploadRoot}${lbt.imageUrl}"/ width="473" height="184"></a>
				                                	 	</#if>
				                                	 	 <#assign tab_index = tab_index+1 />
				                                	 </#list>
				                                </#if>
				                                <script type="text/javascript">
													new tab ('slideC${fir_index}${index}_a_current_', '_', function(){
														//获取当前标签索引
														var n=this['Index'];
														//切换标签后触发延迟加载函数
														var objContent=document.getElementById("slideC${fir_index}${index}_"+n);
														$(objContent).find('img').each(function() {
															var original = $(this).attr("data-original");
															if (original) {
																$(this).attr('src', original).removeAttr('data-original');
															}
														});
													}, 'onmouseover');
												</script>
				                                
				                            </div>
				                        </div>
				                        <!--商品或促销-->
				                        <ul class="lh style1">
				                        	 <#if (tabMap.tabProductList?size gt 0)>
			                                	 <#assign tab_index = 1 />
			                                	 <#list tabMap.tabProductList as p>
			                                	 	<#if tab_index==1>
							                            <li class="fore1">
							                                <div class="p-img" clstag="homepage|keycount|home2013|20c2" style="opacity: 1;">
							                                	<a title="${p.title}" target="_blank" href="${p.link}">
							                                        <img width="100px" height="100px" class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${p.imageUrl}"/>
							                                    </a>
							                                </div>
							                                <div class="p-name">
							                                	<a title="${p.title}" target="_blank" href="${p.link}">${p.title}</a>
							                                </div>
							                                <div class="p-price">
							                                	<span>${p.synopsis}</span>
							                                </div>
							                            </li>
			                                	 	<#else>
							                            <li class="fore2">
							                                <div class="p-img" clstag="homepage|keycount|home2013|20c2" style="opacity: 1;">
							                                	<a title="${p.title}" target="_blank" href="${p.link}">
							                                        <img width="100px" height="100px" class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${p.imageUrl}"/>
							                                    </a>
							                                </div>
							                                <div class="p-name">
							                                	<a title="${p.title}" target="_blank" href="${p.link}">${p.title}</a>
							                                </div>
							                                <div class="p-price">
							                                	<span>${p.synopsis}</span>
							                                </div>
							                            </li>
			                                	 	</#if>
			                                	 	<#assign tab_index = tab_index+1 />
			                                	 </#list>
			                                  </#if>
				                        </ul>    
				                    </div>
				                    <#assign index = index+1 /> <!--index加1 --> 
			                    </#list>
		                    </#if>
		                </#if>
	                    <script type="text/javascript">
							new tab ('smc${fir_index}_li_current_', '_', function(){
								//获取当前标签索引
								var n=this['Index'];
								//切换标签后触发延迟加载函数
								var objContent=document.getElementById("smc${fir_index}_"+n);
								$(objContent).find('img').each(function() {
									var original = $(this).attr("data-original");
									if (original) {
										$(this).attr('src', original).removeAttr('data-original');
									}
								});
							}, 'onmouseover');
						</script>
	                    <div>
	                </div>
	            </div>
	            
	                 <!--热销榜单-->
	                 <div class="fl hotList">
	                     <div class="hotListT">热销榜单</div>
	                     <div class="hotListC"> 
	                         <ul>
	                         	<#if (m.promotionProductSort??&&m.promotionProductSort?size gt 0)>
	                         		<#list m.promotionProductSort as sortProduct>
	                         			<#if sortProduct_index==0>
											 <li class="item e-childload on" onmouseover="openThis(this)">
												 <em>${sortProduct_index+1}</em>
												 <a href="${sortProduct.link}" target="_blank" class="prod-img" title="${sortProduct.title}"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${sortProduct.imageUrl}" original="img/9275106_60x98.jpg" onerror="imgError(this)" width="60" height="98" border="0" alt="" /></a>
												 <a href="${sortProduct.link}" target="_blank" class="prod-name" title="${sortProduct.title}">${sortProduct.title}</a>
												 <div>
													 <!--<span class="prod-soldnum">售出 <strong>620</strong></span>-->
													 <span class="prod-price">
														${sortProduct.synopsis}
													 </span>
												 </div>
											 </li>
										 <#else>
											 <li class="item e-childload onee" onmouseover="openThis(this)">
												 <em>${sortProduct_index+1}</em>
												 <a href="${sortProduct.link}" target="_blank" class="prod-img" title="${sortProduct.title}"><img class="lazy"  src="${contextPath}/common/images/blank.gif" data-original="${fileUrlConfig.visitFileUploadRoot}${sortProduct.imageUrl}" original="img/9275106_60x98.jpg" onerror="imgError(this)" width="60" height="98" border="0" alt="" /></a>
												 <a href="${sortProduct.link}" target="_blank" class="prod-name" title="${sortProduct.title}">${sortProduct.title}</a>
												 <div>
													 <!--<span class="prod-soldnum">售出 <strong>620</strong></span>-->
													 <span class="prod-price">
														${sortProduct.synopsis}
													 </span>
												 </div>
											 </li>
										 
										 </#if>
									 </#list>
	                             </#if>
	                         </ul>
	                     </div>
	              </div>
	                 <!--热销榜单 end-->
	        </div>
	        <#assign fir_index =fir_index+1 />
        	</#list>
        </#if>
</div> 
<div style="position:absolute;top:1800px;width:100%;">
        <!--footer-->
         <div class="promiseYinC">
		     <div class="promiseYin">
		         <span class="sevenLater">七天退换货</span>
		         <span class="sevenLater2">100%正品</span>
		         <span class="sevenLater3">支持货到付款</span>
		     </div>
		 </div>
		 <div class="dibu">
		     <div class="dibu_content">
		        <img class="pictureYin01" src="${contextPath}/common/shop/homeIndex/img/gouyin.png"/>
		        <img class="pictureYin02" src="${contextPath}/common/shop/homeIndex/img/cheyin.png"/>
		        <img class="pictureYin03" src="${contextPath}/common/shop/homeIndex/img/jiayin.png"/>
		        <img class="pictureYin04" src="${contextPath}/common/shop/homeIndex/img/xiaoyin.png"/>
		        <img class="pictureYin05" src="${contextPath}/common/shop/homeIndex/img/renyin.png"/>
		        <#if (bottomNewsModuleBeans?size gt 0)>
		        	<#list bottomNewsModuleBeans as bnm>
		        		<div class="dibuc_left">
						<!--  <div class="tubiao"><img src="${contextPath}/common/shop/homeIndex/img/xiaolian.gif" /></div>-->
				           <div class="shuoming2">
				             <p class="texie">${bnm.newsTypeName}</p>
				             <#if (bnm.newsContentList?size gt 0)>
				             	<#list bnm.newsContentList as newsContent>
				             		<p  style="text-align: left;margin-left:70px;"><a target="_blank" href="${contextPath}/helpFront/gotoFooterArticleList.action?articleId=${newsContent.articleId}">${newsContent.title}</a></p>
				             	</#list>
				             </#if>
				           </div>
				         </div>
		        	</#list>
		        </#if>
		     </div>
		   </div>
		   <div class="customerYinC">
			   <div class="customerYin">
			       <a href="#" class="hotlineYin">全国服务热线</a>
			       <a href="#" class="mailboxYin">客户服务信箱</a>
			       <a href="#" class="onLineYin">在线客服</a>
			      	       
			   </div>
		   </div>
		   <div class="redLineCode">
			   <div class="redLine">
			       <span style="width:510px;">正品保证</span>
				   <span style="width:530px;">一站式服务</span>
				   <span style="width:100px;">即购即送</span>
			   </div>
		   </div>
		  <div class="foot">
		    <div class="foot_daohang">
		        <p style="padding-top:20px;">关于天海 | 联系我们 | 诚聘英才 | 友情链接 | 法律声明 | 用户体验提升计划 </p>
		        <p>copyright@2002-2013，天海版权所有  京IPC备  10207551号   京B2-20100316出版物经营许可证新出发 京 批字第A-243号</p>
		        <div class="dibu_img">
		        <img src="${contextPath}/common/shop/homeIndex/img/dibu_115.gif" />
		        <img src="${contextPath}/common/shop/homeIndex/img/dibu_119.jpg" />
		        <img src="${contextPath}/common/shop/homeIndex/img/dibu_117.gif" />
		        </div>
		     </div>
		  </div>	    	  
        <!-- footer end-->
        </div>
</body>
</html>