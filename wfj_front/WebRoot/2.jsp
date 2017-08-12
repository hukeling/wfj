<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <title>${application.homekeybook['homeSeoTitle1'][0].value}</title> -->
<title>SHOPJSP</title>
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index_header.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/base.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/style.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/public.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/cart.css" />
<link type="text/css" rel="stylesheet"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/scrollup.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
<!-- 商城底部文章样式 -->
<link rel="stylesheet" type="text/css"
	href="${fileUrlConfig.fileServiceUploadRoot}shop/css/pubfootcss.css" />
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.tools.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js"></script>
<!-- 新版轮播图js开始 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tt.index_V1.4.min-t.js"></script>
<!-- 新版轮播图js结束 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.lazyload.min.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/tab.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.scrollUp.js"></script>
<!-- 轮播图下方的滚动图片 -->
<script type="text/javascript"
	src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/js/scroll.1.3.js"></script>
<style type="text/css">
body {
	font-family: 微软雅黑;
}

.z_index_focus {
	z-index: -1;
}

.banner_bg {
	width: 790px;
	height: 251px; /*position: relative;*/
	overflow: hidden;
}

.banner_marquee {
	height: 251px;
	overflow: hidden;
	z-index: 0;
	min-width: 990px;
}

.banner_marquee_list {
	margin: 0px auto;
	width: 790px;
	height: 251px;
	overflow: hidden;
}

.banner_marquee_list li {
	width: 790px;
	height: 251px;
	overflow: hidden;
}

.banner_main {
	margin: 0px auto;
	width: 790px;
	height: 251px;
	overflow: hidden;
}

.banner_marquee_list li a.banner_link {
	width: 790px;
	height: 251px;
	margin-left: 190px;
	display: block;
}

.banner_btn { /*left: 50%;*/
	width: 790px;
	height: 25px;
	text-align: right; /*bottom: 12px; */
	overflow: hidden;
	position: absolute;
	z-index: 5;
	top: 402px;
}

.banner_btn span {
	width: 25px;
	height: 25px;
	text-align: center;
	color: rgb(136, 136, 136);
	line-height: 25px;
	font-family: Verdana, Geneva, sans-serif;
	font-size: 35px;
	display: inline-block;
	cursor: pointer;
	_display: inline;
	_zoom: 1;
}

.banner_btn span.on {
	color: rgb(236, 26, 91);
}

/* 滚动图片 */
.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden
}

.clearfix {
	display: inline-table
}

* html .clearfix {
	height: 1%
}

.clearfix {
	display: block
}

*+html .clearfix {
	min-height: 1%
}

.wc960 {
	width: 790px;
	margin-top: 10px;
	border: 1px solid #CCC;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.icon {
	background:
		url(${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/icon.png)
		no-repeat 0 0;
}

.warp-pic-list li {
	float: left;
	display: inline;
}

.warp-pic-list .img_wrap {
	display: block;
	font-size: 0;
	overflow: hidden;
}

.warp-pic-list .text-area {
	background-color: #f2f2f2;
	line-height: 24px;
}
/*全局板块*/
.row .hd {
	background: url(../images/hd-line_01.jpg) no-repeat 0 50px;
	height: 55px;
}

.row .hd .title {
	font: 26px/40px "微软雅黑", "Microsoft YaHei", "黑体", "SimHei";
}
/*全局页签*/
.tab-T-3 {
	width: 66px;
}

.tab-T-3 li {
	width: 12px;
	height: 12px;
	font-size: 0;
	background-color: #dfdfdf;
	float: left;
	margin-left: 10px;
	cursor: pointer;
	display: inline;
}

.tab-T-3 li.cur {
	background-color: #d81c1b;
}
/*热门车型*/
.rowE .warp-pic-list {
	width: 739px;
	height: 110px;
	overflow: hidden; /* margin-left:10px; */
}

.rowE .count li {
	width: 147px;
	height: 110px;
	display: inline-block;
	border-right: 1px solid #CCC;
}

.rowE .count li:last-child {
	border-right: none;
}

.rowE .count li img {
	width: 147px;
	height: 110px;
	display: inline-block;
}

.rowE .count .img_wrap {
	width: 147px;
	height: 110px;
}

.rowE .count li .text-area {
	padding: 10px 0 10px 15px;
}

.rowE .count li .text-area  p {
	line-height: 24px;
	height: 24px;
}

.rowE .count li:hover .text-area, .rowE .count li.hover .text-area {
	background-color: #d81c1b;
	color: #fff;
}

.rowE .count .p-num {
	font-family: "Tahoma";
	font-weight: bold;
}

.rowE .btn {
	display: block;
	height: 110px;
	position: absolute;
	top: 0px;
	width: 26px;
	z-index: 200;
	cursor: pointer;
}

.rowE .prev {
	background-position: 0 -1px;
	left: 0;
}

.rowE .prev:hover {
	background-position: 0 -112px;
}

.rowE .next {
	background-position: 0 -222px;
	right: -8px;
}

.rowE .next:hover {
	background-position: 0 -333px;
}
/* 购物车css */
#shopimg_cart {
	font-size: 18px;
	color: #fff;
	height: 37px;
	line-height: 30px;
	display: block;
	float: left;
	padding-left: 5px;
	font-family: 微软雅黑;
	font-weight: bold;
}

#shopimg_cart a {
	color: #fff;
	text-decoration: none;
}

#shopimg_cart a:hover {
	color: #fbe303;
	text-decoration: none;
}
</style>

<!--延迟加载js-->

<script type="text/javascript">
	$(function(){
		$.scrollUp();
		//首页图片延迟加载
		$("img.lazy").lazyload({         
			effect : "fadeIn"    
		});
		
		
		$("#count1").dayuwscroll({
			parent_ele:'#wrapBox1',
			list_btn:'#tabT04',
			pre_btn:'#left1',
			next_btn:'#right1',
			path: 'left',
			auto:false,
			time:3000,
			num:5,
			gd_num:5,
			waite_time:1000
		});
	});
	$(function(){
		var cookie = $.cookie('customerCar');
		//初始化判断cookie中用户名是否存在
		var thshop_customerNameValue=$.cookie('thshop_customerName');
		if(chkcookies("thshop_customerName")){
			if(thshop_customerNameValue!=""){
				$("#loginShow").css("display","block");
				$("#noLoginShow").css("display","none");
				$("#cookie_loginName").html("<font color='red'>"+thshop_customerNameValue+"</font>");
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
		location.href = "${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
	}
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
	//购物车，买家中心
	function doRedirect(state) {
		if (state == "1") {//My account
			window.location = "${pageContext.request.contextPath}/front/customer/index.action";
		} else if (state == "2") {//cart
			window.location = "${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
		}
	}
	//用户中心
	function doSellerCheck(type) {
			if (type == "1"||type=="3") {//My account
				window.location = "${pageContext.request.contextPath}/front/customer/index.action";
			} else if (type == "2") {//cart
				window.location = "${pageContext.request.contextPath}/front/store/frontshopinfo/goToShopLoginHome.action";
			}
	}
</script>

<!--左侧分类 js-->
<script type="text/javascript">
	(function($){
		$.fn.hoverForIE6=function(option){
			var s=$.extend({current:"hover",delay:10},option||{});
			$.each(this,function(){
				var timer1=null,timer2=null,flag=false;
				$(this).bind("mouseover",function(){//绑定鼠标移入效果
					if (flag){
						clearTimeout(timer2);
					}else{
						//处理IE7下轮播图遮挡分类js模块问题
						//$("#focus").addClass("z_index_focus");//鼠标移入添加层级-1效果
						//console.log("mouseover :"+$("#focus").attr("class"));
						var _this=$(this);
						timer1=setTimeout(function(){
							_this.addClass(s.current);
							flag=true;
						},s.delay);
						//console.log("mouseover :"+$(".allsort .item .i-mc").css("display"));
					}
				}).bind("mouseout",function(){//绑定鼠标移出效果
					if (flag){
						//处理IE7下轮播图遮挡分类js模块问题
						//$("#focus").removeClass("z_index_focus");//鼠标移出去除层级-1效果
						var _this=$(this);timer2=setTimeout(function(){
							_this.removeClass(s.current);
							flag=false;
						},s.delay);
						//console.log("mouseout :"+$(".allsort .item .i-mc").css("display"));
					}else{
						clearTimeout(timer1);
					}
				});
			});
		};
	})(jQuery);
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
	// 		$("#focus .btn span").removeClass("on").eq(index).addClass("on"); //为当前的按钮切换到选中的效果
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
	function goFirstType(firstTypeId,level){
		var winopen = window.open ("${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&level="+level+"&orderBy=normal");
		/*
		if( winopen==null){//自动提交的链接
		    document.write('<a href="${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&orderBy=normal" target="_blank" id="autopop" style="display:none"></a>');
		    document.getElementById('autopop').click(); 
		}
		*/
	}
	//品牌链接function 
	  function brandLink(brandName){//参数为品牌名称
	  	$("#searchContent").val(brandName);
	  	searchImg();
	  }

	//头部购物车-组品html代码
		function headShoppingCartHtml(){
			//请求购物车数据
			$.post("${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCartAjax.action",function(data){
				var listMap=data;
				var html="";
				if(listMap!=null&&listMap.length>0){
						html+="<div id=\"divMini\" class=\"cart-bar-expand\">";
						for(var i=0;i<listMap.length;i++){
							var productId=listMap[i].productId;
		        			var smallImgUrl=listMap[i].smallImgUrl;
		        			var productName=listMap[i].productName;
		        			var buyPrice=listMap[i].salesPrice.toFixed(2);;
		        			var quantity=listMap[i].quantity;
		        			var webroot='${pageContext.request.contextPath}';
		        			var webroot='${pageContext.request.contextPath}';
		        			html+="<dl> <dd class=\"image\"><a href=\"${pageContext.request.contextPath}/productInfo/"+productId+".html\" target=\"_blank\">";
		        			html+="<img onerror=\"this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'\"  src=\"${application.fileUrlConfig.visitFileUploadRoot}"+smallImgUrl+"\" style=\"height:35px; width:35px;\" />";
				            html+=" </a></dd><dt><a muse_scanned=\"true\" target=\"_blank\" href=\"${pageContext.request.contextPath}/productInfo/"+productId+".html\">"+productName+"</a>";		
				            html+=" </dt><dd class=\"operation\"><a onclick=\"deleteCart('"+productId+"');\" href=\"javascript:;\" class=\"secondary-link\" style=\"color: #1478da;\">删除</a></dd>";
				            html+="<dd>";
				            html+="<input type=\"hidden\" id=\"salesPrice2"+productId+"\" value=\""+buyPrice+"\"/>";
				            html+="<input id=\"amount_"+productId+"\" name=\"amountItme2\" type=\"hidden\" value=\""+quantity+"\" />";
				            html+="<span style=\"font-family:微软雅黑;color:#ff5500;\">￥</span><em>"+buyPrice+"</em>x<i>"+quantity+"</i>";
				            html+=" </dd> </dl>";
						}
						html+="<div class=\"cart-bar-expand-total\">";
						html+=" <p>共<i id=\"minicartcnt2\">"+listMap.length+"</i>件商品</p><p>合计(未含运费):<em id=\"minicarttotalprice\"><span style=\"font-family:微软雅黑;color:#ff5500;\">￥</span><span style=\"font-family:微软雅黑;color:#ff5500;\" id=\"Subtotal\"></span></em></p>";
						html+=" <input value=\"去结算\" id=\"btnGoPay\" href=\"javascript:;\" onclick=\"verdictgwc();\" class=\"btn btn-s\" type=\"button\" />";
						html+="</div></div>";
					}else{
						html+="<div id=\"divMiniNo\" class=\"cart-bar-expand\">";
						html+=" <div class=\"cart-bar-empty\">您还未添加任何商品，欢迎选购！</div>";
						html+=" </div>";
					}
					$("#divShopCart").html(html);
					$("#count").html(listMap.length);
					amountMoney();
			},'json');
		}
		
		
		  //头部购物车	
		  $(document).ready(function(){
				$("#jq_topmenu").hover(function(){
					$(this).addClass("hover").find("div.jq_hidebox").show();
					headShoppingCartHtml();
				},function(){
					$(this).removeClass("hover").find("div.jq_hidebox").hide();
				});
			});
	
	
    //删除
    function deleteCart(id){
    	var ids="";
    	if(id=="all"){//删除选中的商品
    		var carts=document.getElementsByName("cartInfo");
    		for(var i=0;i<carts.length;i++){
    			if(carts[i].value!=""){
    				if(carts[i].checked){//如果选中
	    				if(ids!=""){
	    					ids=ids+","+carts[i].value;
	    				}else{
	    					ids=carts[i].value;
	    				}
    				}
    			}
    		}
    	}else{
    		ids=id;
    	}
    	var url ="${pageContext.request.contextPath}/loginCustomer/checkLogin.action";
		$.post(url,{},function(data){//验证是否登录
			if(data.bool == true){//已登录操作
		    	if(ids!=""){//有选中的商品时
		    		lconfirm("提醒","确定删除!",function(o){
						var url="${pageContext.request.contextPath}/shopFront/shoppingCar/deleteShoppingCart.action";
						$.post(url,{ids:ids},function(data){
							if(data){
							  window.location.reload(); //删除后重新加载列表
						   }
						},"JSON");
		    		});
		    	}else{
		    		lalert("提醒","请选择商品！");
		    	}
			}else{
				//没有登录时，删除cookie中的数据
				if(chkcookies('customerCar')){
					var carCookie = eval($.cookie('customerCar'));
					if(id=="all"){//点击清除选中商品时操作
						lconfirm("提醒","确定删除!",function(o){
							var selectNum=0;//当前选中商品个数
							$(".checkboxClass").each(function(){
								if(this.checked){
									selectNum++;
								}
							});
							if(selectNum==carCookie.length){//如果当前选中商品个数等于cookie中的个数
								$.cookie("customerCar",null,{expires: -1 ,path:"/"});//全部清除
							}else{//如果当前选中商品个数不等于cookie个数
								var value="";
								$(".checkboxClass").each(function(){
									for(var i=0;i<carCookie.length;i++){
										if(!this.checked){//如果当前商品没有选中，说明不删除此商品，cookie中保存
											if(this.value==carCookie[i].productId){
												value +=",{'productId':'"+carCookie[i].productId+"','num':'"+carCookie[i].num+"','shopInfoId':'"+carCookie[i].shopInfoId+"','sku':'"+carCookie[i].sku+"'}";//以json格式存放，方便维护和取
											}
										}
									}
								});
								value = value.substring(1,value.length);
								value ="["+value+"]";
								$.cookie('customerCar', value , {expires: 7, path:"/"}); //设置带时间的cookie 7天，加path属性时，只能其他页面使用，且其他页面取值条件为当前页面的上级路径包含path一致
							}
							window.location.reload(); //删除后重新加载列表
						});
					}else{//单个商品删除操作按钮
						lconfirm("提醒","确定删除!",function(o){
							if(carCookie.length==1){//当前cookie中只有一个商品，直接删除cookie中的商品
								$.cookie("customerCar","",{expires: -1 ,path:"/"});
							}else{
								var value="";
								for(var i=0;i<carCookie.length;i++){
									if(id!=carCookie[i].productId){//判断当前操作商品是否是指定删除的商品
										value +=",{'productId':'"+carCookie[i].productId+"','num':'"+carCookie[i].num+"','shopInfoId':'"+carCookie[i].shopInfoId+"','sku':'"+carCookie[i].sku+"'}";//以json格式存放，方便维护和取
									}						
								}
								value = value.substring(1,value.length);
								value ="["+value+"]";
								$.cookie('customerCar', value , {expires: 7, path:"/"}); //设置带时间的cookie 7天，加path属性时，只能其他页面使用，且其他页面取值条件为当前页面的上级路径包含path一致
							}
							window.location.reload(); //删除后重新加载列表
						});
					}
				}
			}
		},"JSON");
    }
    //统计金额和数量
    function amountMoney(){
		var amountItme = document.getElementsByName("amountItme2");
		var costPriceTotal =0;//市场价总额
		var Subtotal=0;//销售总价格
		for(var i=0;i<amountItme.length;i++){
			var priceId=amountItme[i].id;
			var array = new Array();
			array=priceId.split("_");
			var productId=array[1];//购物车id
					var price=$("#salesPrice2"+productId).val();//单价
					var OneAmount=amountItme[i].value;//当前商品数量
					Subtotal=Number(price*OneAmount)+Number(Subtotal);//销售总价格
					Subtotal=Subtotal.toFixed(2); 
		}
		$("#Subtotal").html(Subtotal);//销售价总额
    }
    
    //快速下单 文字颜色控制js
    $(function(){
    	$("input.useFunction").live("focus",function() {
    		$(this).attr("style","color:#555;");
    	});
    	$(".useFunction").live("blur",function(){
    		if($(this).val()==""){
	    		$(this).attr("style","color: rgb(169, 169, 169);");
    		}
    	});
    });
    
</script>
</head>

<body>
	<div class="hesder_topCode">
		<!--top-->
		<div class="hesder_top">
			<div class="welcomeYin fl" style="padding-left: 0; width: 150px;">
				<div
					style="width: 16px; height: 16px; position: relative; left: 0px; top: 7px; float: left;">
					<img style="width: 16px; height: 16px;"
						src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/brand_channel/pinpaipindao_09.png" />
				</div>
				<div style="width: 100px; float: left; padding-left: 6px;">
					<a
						href="javascript:window.external.addFavorite('http://www.baidu.com','SHOPJSP')"
						title="SHOPJSP">收藏商城</a>
				</div>
			</div>
			<div class="welcomeYin fr">
				<a style="margin-left: 0; padding: 0 7px 0 3px;" class="fr"
					href="${pageContext.request.contextPath}/helpFront/gotoHelpFront.action"
					style=""> <s:if test="#session.customer==null">
						<img
							style="position: relative; padding-left: 6px; padding-right: 3px;"
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg" />
					</s:if> 帮助中心
				</a>
				<s:if test="#session.customer!=null">
					<a style="margin-left: 0; padding: 0 3px;" class="fr"
						href="javascript:doSellerCheck(<s:property value='#session.customer.type'/>);">
						<img
						style="position: relative; top: 3px; padding-left: 6px; padding-right: 3px;"
						src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg" />
						用户中心 <img
						style="position: relative; top: 3px; padding-left: 6px; padding-right: 3px;"
						src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg" />
					</a>
				</s:if>
				<%-- 
				 <a style="margin-left:0; padding:0 3px;" class="fr" href="javascript:doRedirect('1');"><img style=" padding-right:6px;padding-left:6px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg"/>买家中心<img style=" padding-right:6px;padding-left:6px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg"/></a>
				  --%>
				<!-- 当session中存在customer时 ，作为登录处理 -->
				<s:if test="#session.customer!=null">
					<div style="float: right;">
						您好，&nbsp; <span> <font color="red"> <s:if
									test="#session.shopInfo.companyName!=''&&#session.shopInfo.companyName!=null">
									<s:property value="#session.shopInfo.companyName" />
								</s:if> <s:else>
									<s:property value="#session.customer.loginName" />
								</s:else> <s:if test="#session.sonaccount!=null">
									<s:if test="#session.sonaccount.type==1">
										采购人员
									</s:if>
									<s:if test="#session.sonaccount.type==2">
										财务人员
									</s:if>
									: <s:property value="#session.sonaccount.nickname" />
								</s:if>
						</font>
						</span>！ &nbsp; [<a
							href="${pageContext.request.contextPath}/loginCustomer/exitLogin.action">退出</a>]
					</div>
				</s:if>
				<s:else>
					<div id="loginShow" style="display: none; float: right;">
						<span id="cookie_loginName"></span>!&nbsp;欢迎来到SHOPJSP! &nbsp; [<a
							href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">买家登录</a>]
						[<a
							href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=3">买家注册</a>]&nbsp;&nbsp;&nbsp;
						[<a
							href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=2">卖家登录</a>]
						[<a
							href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=2">卖家注册</a>]
					</div>
					<div id="noLoginShow" style="display: block; float: right;">
						您好!&nbsp;欢迎来到SHOPJSP!&nbsp; [<a
							href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">买家登录</a>]
						[<a
							href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=3">买家注册</a>]&nbsp;&nbsp;&nbsp;
						[<a
							href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=2">卖家登录</a>]
						[<a
							href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=2">卖家注册</a>]
					</div>
				</s:else>
			</div>
		</div>
	</div>
	<!--top end-->

	<!--topBottom-->
	<div class="topBottom">
		<div class="fl">
			<!-- 	标准log大小	style="width: 259px;height:49px;" -->
			<a href="${pageContext.request.contextPath}/"><img
				style="width: 150x; height: 70px;"
				src="${fileUrlConfig.fileServiceUploadRoot}front/images/logo.png" /></a>
		</div>
		<div class="fl searchYin" style="margin-left: 192px; margin-right: 0;">
			<form
				action="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action"
				method="get" id="searchForm">
				<input type="hidden" name="pager.keyword" id="searchContentValue" />
				<div class="searchYinT">
					<!-- 		            <input style="color:#727171; margin:0 0 0 30px; height:28px; line-height:28px; float: left; display:inline-block;" id="searchContent" class="searchText" type="text" value="请输入品牌，订货号，商品名称" onfocus="focusAndBlur('focus')" onblur="focusAndBlur('blur')"  value="<s:property value='keyword'/>"/> -->
					<input autocomplete="off"
						style="color: #727171; margin: 0px 0 0 30px; float: left; display: inline-block;"
						id="searchContent" class="searchText" type="text" value=""
						placeholder="请输入品牌，订货号，商品名称" onfocus="focusAndBlur('focus')"
						onblur="focusAndBlur('blur')"
						value="<s:property value='keyword'/>" /> <input
						onclick="searchImg()" class="searchSubmit" type="submit"
						value="搜 索"
						style="float: left; display: inline-block; height: 42px; line-height: 40px;" />
				</div>
				<!-- <div class="searchYinB" >
                    <a href="#">热门搜索</a>
                </div> -->
			</form>
		</div>
		<!-- 热线电话 -->
		<div
			style="float: right; width: 166px; height: 31px; line-height: 31px; padding-top: 13px; color: #333; font-size: 16px; font-weight: bold; text-align: right; padding-right: 6px;">
			<div style="float: left; margin-top: 3px;">
				<img
					src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/phone.png"
					style="height: 31px; width: 43px;" />
			</div>
			<div style="float: left;">
				<p style="line-height: 15px; width: 123px;">010-6291-0181</p>
				<p
					style="border-bottom: 1px solid; margin-top: -8px; width: 116px; margin-left: 4px;"></p>
				<p
					style="line-height: 15px; font-size: 14px; margin-top: -8px; width: 110px; text-align: center; letter-spacing: 3px; padding-left: 10px;">本地实体商城!</p>
			</div>
		</div>
	</div>




	<!--左侧分类 begin-->
	<div class="header_nav" style="padding: 0px;">
		<div class="header_navContent">
			<div class="allsort">
				<div class="mt">
					<strong>全部商品分类 <!-- <a href="javascript:;" style="cursor:default ;text-decoration: none;">全部商品分类</a> -->
					</strong>
				</div>
				<div class="mc">
					<div class="mcCode">
						<div class="header_bgyinT"></div>
						<!-- 首页分类列表 -->
						<s:iterator value="#application.categoryMap" var="ptbOne"
							status="stOne">
							<div class="item <s:if test='#stOne.index==0'>fore</s:if>">
								<span> <img
									src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/header_navpic<s:property value='#stOne.index+1'/>.png"
									width="20" height="20" />
									<h3>
										<a title="<s:property value='#ptbOne.key.sortName'/>"
											href="javascript:void(0);"
											onclick="goFirstType(<s:property value='#ptbOne.key.productTypeId'/>,<s:property value='#ptbOne.key.level'/>)">
											<s:if test="#ptbOne.key.sortName.length()>=10">
												<s:property value="#ptbOne.key.sortName.substring(0,9)" />
											</s:if> <s:else>
												<s:property value="#ptbOne.key.sortName" />
											</s:else>
										</a>
									</h3> <s></s>
								</span>
								<div class="i-mc">
									<div class="close"
										onclick="$(this).parent().parent().removeClass('hover')"></div>
									<div class="subitem">
										<s:iterator value="#ptbOne.value" var="ptbTwo" status="st">
											<dl class="<s:if test='#st.index==0'>fore</s:if>">
												<dt style="cursor: pointer;"
													onclick="goFirstType(<s:property value='#ptbTwo.key.productTypeId'/>,<s:property value='#ptbTwo.key.level'/>)">
													<s:property value="#ptbTwo.key.sortName" />
												</dt>
												<dd>
													<s:iterator value="#ptbTwo.value" var="ptbThree">
														<em><a href="javascript:void(0);"
															onclick="goFirstType(<s:property value='#ptbThree.key.productTypeId'/>,<s:property value='#ptbThree.key.level'/>)"><s:property
																	value="#ptbThree.key.sortName" /></a></em>
													</s:iterator>
												</dd>
											</dl>
										</s:iterator>
									</div>
									<div id="JD_sort_k" class='fr'>
										<div id="JD_sort_k" class='fr'
											style="height: 400px; overflow: hidden;">
											<s:if test="#application.categoryBrandMap.size > 0">
												<s:iterator value="#application.categoryBrandMap" id="bm"
													status="st">
													<s:if test="#bm.key==#ptbOne.key">
														<s:if test="#bm.value.size>0">
															<s:iterator value="#bm.value" var="brand">
																<a
																	href="javascript:brandLink('<s:property value='#brand.brandName'/>');">
																	<img
																	src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#brand.brandBigImageUrl"/>"
																	width="194" height="70" alt=""
																	style="margin-top: 10px;" />
																</a>
															</s:iterator>
														</s:if>
													</s:if>
												</s:iterator>
											</s:if>
										</div>
									</div>
								</div>
							</div>
						</s:iterator>
					</div>
					<div class="header_bgyinB"></div>
				</div>
			</div>
			<script type="text/javascript"> 
                $(".allsort").hoverForIE6({current:"allsorthover",delay:200});
                $(".allsort .item").hoverForIE6({delay:150});
			</script>
			<div class="primaryNav fl">
				<p class="rig_gx" style="">
					<a href="${pageContext.request.contextPath}/"
						style="background-color: #e62d59;">首页</a>
				</p>
				<p class="rig_gx">
					<a
						href="${pageContext.request.contextPath}/store/frontShopDirPage/gotoShopDirPage.action?headerType=shops">线下商场</a>
				</p>
				<p class="rig_gx">
					<a
						href="${pageContext.request.contextPath}/brandChannel/gotoBrandChannelPage.action?headerType=brands">热销品牌</a>
				</p>
				<p class="rig_gx">
					<a
						href="${pageContext.request.contextPath}/homeModule/dayRecommend.action?headerType=dayRecommend">SHOPJSP热销</a>
				</p>

				<p class="rig_gx">
					<a
						href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=1">专业推荐</a>
				</p>
				<a id="home_industrySpecific" class="header_Selected"
					href="${pageContext.request.contextPath}/front/quickOrder/quickOrder.action">极速热购</a>
				<p class="rig_gx">
					<a id="home_coupon"
						href="${pageContext.request.contextPath}/front/discountcoupon/coupon/gotofrontcoupon.action?headerType=coupon">优惠券</a>
				</p>
				<p class="rig_gx">
					<a id="home_group"
						href="${pageContext.request.contextPath}/tuan/gotoTuanHome.action?headerType=group">团购</a>
				</p>
			</div>
			<!-- 改变之后的购物车 -->
			<ul class="cart-bar-online" id="jq_topmenu">
				<s:if test="#session.cartCountMap!=null">
					<span id="count"
						style="width: 25px; font-size: 12px; color: #fbe303; font-weight: bold; text-align: center; display: block; height: 13px; line-height: 13px; float: left; padding-left: 59px;"><s:property
							value="#session.cartCountMap" /></span>
				</s:if>
				<s:else>
					<span id="count"
						style="width: 25px; font-size: 12px; color: #fbe303; font-weight: bold; text-align: center; display: block; height: 13px; line-height: 13px; float: left; padding-left: 59px;">0</span>
				</s:else>
				<span id="shopimg_cart"><a
					href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action">购物车</a></span>
				<li>
					<div id="divShopCart" class="jq_hidebox" style="display: none;">
						<s:if test="#session.cartMap.size>0">
							<div id="divMini" class="cart-bar-expand">
								<s:iterator value="#session.cartMap" var="cartMap">
									<s:iterator value="#cartMap.value" var="cart">
										<dl>
											<dd class="image">
												<a
													href="${pageContext.request.contextPath}/productInfo/${cart.productId}.html"
													target="_blank"> <img
													onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"
													src="${application.fileUrlConfig.visitFileUploadRoot}<s:property value='#cart.smallImgUrl'/>"
													style="height: 35px; width: 35px;" />
												</a>
											</dd>
											<dt>
												<a muse_scanned="true" target="_blank"
													href="${pageContext.request.contextPath}/productInfo/${cart.productId}.html"><s:property
														value="#cart.productName" /></a>
											</dt>
											<dd class="operation">
												<a
													onclick="deleteCart('<s:property value="#cart.productId"/>');"
													href="javascript:;" class="secondary-link"
													style="color: #1478da;">删除</a>
											</dd>
											<dd>
												<input type="hidden"
													id="salesPrice<s:property value="#cart.productId"/>"
													value="<s:property value="#cart.buyPrice"/>" /> <input
													id="amount_<s:property value="#cart.productId"/>"
													name="amountItme" type="hidden"
													value="<s:property value="#cart.quantity"/>" /> ¥<em><s:property
														value="#cart.buyPrice" /></em>x<i><s:property
														value="#cart.quantity" /></i>
											</dd>
										</dl>
									</s:iterator>
								</s:iterator>
								<div class="cart-bar-expand-total">
									<p>
										共<i id="minicartcnt2"><s:property
												value="#session.cartMap.size" /></i>件产品
									</p>
									<p>
										合计:¥<em id="minicarttotalprice"><span id="Subtotal"></span></em>
									</p>
									<input value="去结算" id="btnGoPay" href="javascript:;"
										onclick="verdictgwc();" class="btn btn-s" type="button" />
								</div>
							</div>
						</s:if>
						<s:else>
							<div id="divMiniNo" class="cart-bar-expand">
								<div class="cart-bar-empty">您还未添加任何商品，欢迎选购！</div>
							</div>
						</s:else>
					</div>
				</li>
			</ul>
		</div>
	</div>
	<!--左侧分类 end-->

	<div class="wrapper">
		<div style="width: 190px; height: 390px; float: left;"></div>
		<div class="fl" style="margin-left: 10px; display: inline;">

			<!-- 改版轮播图开始 -->
			<div class="banner_bg" id="slidepicturediv">
				<ul class="banner_marquee_list" id="slidepicture">
					<s:if test="#request.homeLBTList.size>0">
						<s:iterator value="#request.homeLBTList" var="h">
							<li
								style='background: url("<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='#h.broadcastingIamgeUrl'/>") no-repeat 50% 0px;'>
								<div class="banner_main">
									<a title="" class="banner_link" id="1350_62128"
										href="<s:property value='#h.interlinkage'/>" target="_blank"></a>
								</div>
							</li>
						</s:iterator>
					</s:if>
				</ul>
				<div class="banner_btn" id="slidebutton"></div>
			</div>
			<!-- 改版轮播图结束 -->

			<!--infopic begin-->
			<div class="wc960 row rowE">

				<!--<div class="hd clearfix">
				<div class="fl">
					<h2 class="title">热门推荐</h2>
				</div>
				
			</div>-->

				<div class="bd"
					style="margin-top: 0; position: relative; padding: 0 25px;">
					<div id="sca1" class="warp-pic-list">
						<div id="wrapBox1" class="wrapBox" style="width: 741px;">
							<ul id="count1" class="count clearfix">
								<s:iterator value="#request.homeLittleAdvertisementList">
									<li><a style="padding: 0;" target="_blank" href="${link}"
										class="img_wrap"><img
											src="${fileUrlConfig.visitFileUploadRoot}${imageUrl}"
											width="147" height="90" border="0"></a></li>
								</s:iterator>

							</ul>
						</div>
						<a id="right1" href="javascript:;" class="prev icon btn"
							style="cursor: pointer; width: 25px !important; left: 0;"></a> <a
							id="left1" href="javascript:;" class="next icon btn"
							style="cursor: pointer; width: 25px !important; right: 0;"></a>
					</div>
				</div>
			</div>
			<!--infopic end-->

		</div>

		<!--焦点图右侧-->
		<div class="expressPart fl">

			<s:if test="#request.homeAdvertisementList.size>=1">
				<a
					href="<s:property value='#request.homeAdvertisementList[0].link'/>"
					target="_blank"><img
					src="${fileUrlConfig.visitFileUploadRoot}${homeAdvertisementList[0].imageUrl}"
					width="206" height="77" /></a>
			</s:if>
			<!--天天快报-->
			<div class="dailyExpress"
				style="margin: 0; height: auto; margin-top: 5px;">
				<span>SHOPJSP快报<s:if test="#request.articleList.size>0">
						<a
							href="${pageContext.request.contextPath}/shopHome/moreNews.action"
							style="color: #fff; padding-left: 92px; font-size: 12px; font-weight: normal;">更多></a>
					</s:if>
				</span>
				<div class="dailyExpressC"
					style="height: 134px; border-bottom: 1px solid #EEEEEE;">
					<s:if test="#request.articleList.size>0">
						<s:iterator value="#request.articleList" var="art" status="st">
							<s:if test="#st.index<5">
								<s:if test="#art.title.length()>15">
									<a title="<s:property value='#art.title'/>"
										href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value='#art.articleId'/>"
										target="_blank"><s:property
											value='#art.title.substring(0,15)' /></a>
								</s:if>
								<s:else>
									<a title="<s:property value='#art.title'/>"
										href="${pageContext.request.contextPath}/helpFront/gotoHelpFrontList.action?articleId=<s:property value='#art.articleId'/>"
										target="_blank"><s:property value='#art.title' /></a>
								</s:else>
							</s:if>
						</s:iterator>
					</s:if>
				</div>
			</div>

			<s:if test="#request.homeAdvertisementList.size>=2">
				<a
					href="<s:property value='#request.homeAdvertisementList[1].link'/>"
					target="_blank"><img style="margin-top: 8px;"
					src="${fileUrlConfig.visitFileUploadRoot}${homeAdvertisementList[1].imageUrl}"
					width="206" height="110" /></a>
			</s:if>

		</div>
	</div>
	<!-- wrapper end -->

	<div class="mainY" style="margin-bottom: 15px; overflow: hidden;">
		<!--限时速购-->
		<!-- <div class="instantaneousCode"> -->
		<!--限时速购倒计时-->
		<div class="instantaneousPurchase fl">
			<div class="instantaneousTitle" id="limitColumn_li_current_">
				<ul>
					<s:iterator value="#application.homekeybook['countDownLocation']"
						var="hkb" status="u">
						<s:if test="#u.index==0">
							<li class="current"><s:property value="#hkb.typeName" /></li>
						</s:if>
						<s:else>
							<li><s:property value="#hkb.typeName" /></li>
						</s:else>
					</s:iterator>
				</ul>
			</div>
			<div class="instantaneousContent" id="limitColumn_1">
				<s:if test="#request.homeParticularlyTabList1.size>0">
					<s:iterator value="#request.homeParticularlyTabList1" var="hp1"
						status="u">
						<div class="commodityYin">
							<%--                     <div class="priceMarking"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/priceMarking.png"/></div>--%>
							<a class="commodityPic" target="_blank"
								href="<s:property value='#hp1.link' />"><img class="lazy"
								src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
								data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#hp1.imageUrl' />"
								width="160" height="160" /></a> <a
								href="<s:property value='#hp1.link' />" target="_blank"><s:property
									value='#hp1.synopsis' /></a>
							<!--描述-->
							<div class="priceYin">
								SHOPJSP价：<span>￥<s:property value='#hp1.price' /></span>
							</div>
						</div>
					</s:iterator>
				</s:if>
			</div>
			<div id="limitColumn_2" class="instantaneousContent hide">
				<s:if test="#request.homeParticularlyTabList2.size>0">
					<s:iterator value="#request.homeParticularlyTabList2" var="hp2"
						status="u">
						<div class="commodityYin">
							<%--                    <div class="priceMarking"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/priceMarking.png"/></div>--%>
							<a class="commodityPic" target="_blank"
								href="<s:property value='#hp2.link' />"><img class="lazy"
								src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
								data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#hp2.imageUrl' />"
								width="160" height="160" /></a> <a
								href="<s:property value='#hp2.link' />" target="_blank"><s:property
									value='#hp2.synopsis' /></a>
							<div class="priceYin">
								SHOPJSP价：<span>￥<s:property value='#hp2.price' /></span>
							</div>
						</div>
					</s:iterator>
				</s:if>
			</div>
			<div id="limitColumn_3" class="instantaneousContent hide">
				<s:if test="#request.homeParticularlyTabList3.size>0">
					<s:iterator value="#request.homeParticularlyTabList3" var="hp3"
						status="u">
						<div class="commodityYin">
							<%--                    <div class="priceMarking"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/priceMarking.png"/></div>--%>
							<a class="commodityPic" target="_blank"
								href="<s:property value='#hp3.link' />"><img class="lazy"
								src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
								data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#hp3.imageUrl' />"
								width="160" height="160" /></a> <a
								href="<s:property value='#hp3.link' />" target="_blank"><s:property
									value='#hp3.synopsis' /></a>
							<div class="priceYin">
								SHOPJSP价：<span>￥<s:property value='#hp3.price' /></span>
							</div>
						</div>
					</s:iterator>
				</s:if>
			</div>
			<div id="limitColumn_4" class="instantaneousContent hide">
				<s:if test="#request.homeParticularlyTabList4.size>0">
					<s:iterator value="#request.homeParticularlyTabList4" var="hp4"
						status="u">
						<div class="commodityYin">
							<%--                    <div class="priceMarking"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/priceMarking.png"/></div>--%>
							<a class="commodityPic" target="_blank"
								href="<s:property value='#hp4.link' />"><img class="lazy"
								src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
								data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#hp4.imageUrl' />"
								width="160" height="160" /></a> <a
								href="<s:property value='#hp4.link' />" target="_blank"><s:property
									value='#hp4.synopsis' /></a>
							<div class="priceYin">
								SHOPJSP价：<span>￥<s:property value='#hp4.price' /></span>
							</div>
						</div>
					</s:iterator>
				</s:if>
			</div>
			<div id="limitColumn_5" class="instantaneousContent hide">
				<s:if test="#request.homeParticularlyTabList5.size>0">
					<s:iterator value="#request.homeParticularlyTabList5" var="hp5"
						status="u">
						<div class="commodityYin">
							<%--                    <div class="priceMarking"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/priceMarking.png"/></div>--%>
							<a class="commodityPic" target="_blank"
								href="<s:property value='#hp5.link' />"><img class="lazy"
								src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
								data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#hp5.imageUrl' />"
								width="160" height="160" /></a> <a
								href="<s:property value='#hp5.link' />" target="_blank"><s:property
									value='#hp5.synopsis' /></a>
							<div class="priceYin">
								SHOPJSP价：<span>￥<s:property value='#hp5.price' /></span>
							</div>
						</div>
					</s:iterator>
				</s:if>
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
					};
				});
			}, 'onmouseover');
		</script>
		</div>
		<div class="theFirstCode">
			<!--首发-->
			<s:if test="#request.homeAdvertisementList.size>=3">
				<div class="integratedMachine">
					<div class="theFirstT">
						<img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/theFirstT.png" />
					</div>
					<div style="height: 5px; overflow: hidden;"></div>

					<div class="theFirstC" style="height: 150px;">
						<a
							href="<s:property value='#request.homeAdvertisementList[2].link'/>">
							<img
							src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#request.homeAdvertisementList[2].imageUrl'/>"
							width="204" height="149" />
						</a>
					</div>
					<div style="height: 4px;"></div>
				</div>
			</s:if>
			<!--一体机-->
			<s:if test="#request.homeAdvertisementList.size>=4">
				<div class="integratedMachine">
					<div class="theFirstT">
						<img
							src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/yitijO.png" />
					</div>
					<div style="height: 6px; overflow: hidden;"></div>

					<div class="theFirstC" style="height: 150px;">
						<a
							href="<s:property value='#request.homeAdvertisementList[3].link'/>">
							<img
							src="${fileUrlConfig.visitFileUploadRoot}<s:property value='#request.homeAdvertisementList[3].imageUrl'/>"
							width="204" height="149" />
						</a>
					</div>
					<div style="height: 4px;"></div>
				</div>
			</s:if>
		</div>
	</div>

	<!--楼层内容-->
	<s:if test="#request.listMap.size>0">
		<s:set name="fir_index" value="1" />
		<s:iterator value="#request.listMap" var="m">
			<div class="floor" style="margin-bottom: 18px; overflow: hidden;">
				<!--分类-->
				<div class="brand fl">
					<span><s:property value="#m.yiJCategory.categoryName" /></span>
					<!--一级分类名称-->
					<div class="allBrand">
						<div class="ificationY">
							<!--遍历一级分类下的二级小分类-->
							<s:if test="#m.secCategory.size > 0">
								<s:iterator value="#m.secCategory" var="erc">
									<a target="_blank" href="<s:property value='#erc.link' />"><s:property
											value='#erc.title' /></a>
								</s:iterator>
							</s:if>
						</div>
						<!--一级分类下促销-->
						<s:if test="#m.promotionProductList!=null">
							<div class="ificationPic">
								<s:iterator value="#m.promotionProductList" var="ppl">
									<a target="_blank" href="<s:property value='#ppl.link' />"
										style="text-decoration: none;"> <img width="80px"
										height="35px" class="lazy"
										src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
										data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#ppl.imageUrl' />" />
									</a>
								</s:iterator>
							</div>
						</s:if>
					</div>
				</div>
				<!--商品分类-->
				<div class="plistY fl">
					<div class="instantaneousTitle plistT"
						id="smc<s:property value='#fir_index' />_li_current_">
						<ul>
							<!-- 遍历中间tab部分 -->
							<s:if test="#m.listMapTab!=null">
								<s:if test="#m.listMapTab.size>0">
									<s:iterator value="#m.listMapTab" var="mlm" status="u">
										<s:if test="#u.index==0">
											<li class="current"><a target="_blank"
												href="<s:property value='#mlm.link' />"><s:property
														value='#mlm.tabName' /></a></li>
										</s:if>
										<s:else>
											<li><a target="_blank"
												href="<s:property value='#mlm.link' />"><s:property
														value='#mlm.tabName' /></a></li>
										</s:else>
									</s:iterator>
								</s:if>
							</s:if>
						</ul>
					</div>
					<s:if test="#m.listMapTab!=null">
						<s:if test="#m.listMapTab.size>0">
							<s:set name="index" value="1" />
							<s:iterator value="#m.listMapTab" var="tabMap">
								<s:if test="#index==1">
									<div class="smc"
										id="smc<s:property value='#fir_index' />_<s:property value='#index' />">
								</s:if>
								<s:else>
									<div class="smc hide"
										id="smc<s:property value='#fir_index' />_<s:property value='#index' />">
								</s:else>

								<!--商品或促销-->
								<ul class="lh style1" style="border-right: 1px #e8e8e8 solid;">
									<s:if test="#tabMap.tabProductList.size>0">
										<s:iterator value="#tabMap.tabProductList" var="p" status="u">
											<s:if test="#u.index==0">
												<li class="fore1" style="margin-right: 0;">
													<div class="p-img" clstag="homepage|keycount|home2013|20c2"
														style="opacity: 1;">
														<a title="<s:property value='#p.title' />" target="_blank"
															href="<s:property value='#p.link' />"> <img
															width="100px" height="100px" class="lazy"
															src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
															data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#p.imageUrl' />" />
														</a>
													</div>
													<div class="p-name">
														<a title="<s:property value='#p.title' />" target="_blank"
															href="<s:property value='#p.link' />"><s:property
																value='#p.title' /></a>
													</div>
													<div class="p-price">
														<span><s:property value='#p.synopsis' /></span>
													</div>
												</li>
											</s:if>
											<s:else>
												<li class="fore2">
													<div class="p-img" clstag="homepage|keycount|home2013|20c2"
														style="opacity: 1;">
														<a title="<s:property value='#p.title' />" target="_blank"
															href="<s:property value='#p.link' />"> <img
															width="100px" height="100px" class="lazy"
															src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
															data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value='#p.imageUrl' />" />
														</a>
													</div>
													<div class="p-name">
														<a title="<s:property value='#p.title' />" target="_blank"
															href="<s:property value='#p.link' />"><s:property
																value='#p.title' /></a>
													</div>
													<div class="p-price">
														<span><s:property value='#p.synopsis' /></span>
													</div>
												</li>
											</s:else>
										</s:iterator>
									</s:if>
								</ul>
				</div>
				<s:set name="index" value="#index+1" />
		</s:iterator>
	</s:if>
	</s:if>
	<script type="text/javascript">
							new tab ('smc<s:property value="#fir_index" />_li_current_', '_', function(){
								//获取当前标签索引
								var n=this['Index'];
								//切换标签后触发延迟加载函数
								var objContent=document.getElementById("smc<s:property value='#fir_index' />_"+n);
								$(objContent).find('img').each(function() {
									var original = $(this).attr("data-original");
									if (original) {
										$(this).attr('src', original).removeAttr('data-original');
									}
								});
							}, 'onmouseover');
						</script>
	<div></div>
	</div>

	<!--热销榜单-->
	<div class="fl hotList" style="margin-left: 10px;">
		<div class="hotListT">热销排行</div>
		<div class="hotListC">
			<ul>
				<s:if
					test="#m.promotionProductSort.size>0 && #m.promotionProductSort!=null">
					<s:iterator value="#m.promotionProductSort" var="sortProduct"
						status="u">
						<s:if test="#u.index==0">
							<li class="item e-childload on" onmouseover="openThis(this)">
								<em><s:property value="#u.index+1" /></em> <a
								href="<s:property value="#sortProduct.link"/>" target="_blank"
								class="prod-img"
								title="<s:property value="#sortProduct.title"/>"><img
									class="lazy"
									src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
									data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value="#sortProduct.imageUrl"/>"
									original="img/9275106_60x98.jpg" width="60" height="98"
									border="0" alt="" /></a> <a
								href="<s:property value="#sortProduct.link"/>" target="_blank"
								class="prod-name"
								title="<s:property value="#sortProduct.title"/>"><s:property
										value="#sortProduct.title" /></a>
								<div>
									<!--<span class="prod-soldnum">售出 <strong>620</strong></span>-->
									<span class="prod-price"> <s:property
											value="#sortProduct.synopsis" />
									</span>
								</div>
							</li>
						</s:if>
						<s:else>
							<li class="item e-childload onee" onmouseover="openThis(this)">
								<em><s:property value="#u.index+1" /></em> <a
								href="<s:property value="#sortProduct.link"/>" target="_blank"
								class="prod-img"
								title="<s:property value="#sortProduct.title"/>"><img
									class="lazy"
									src="${fileUrlConfig.fileServiceUploadRoot}images/blank.gif"
									data-original="${fileUrlConfig.visitFileUploadRoot}<s:property value="#sortProduct.imageUrl"/>"
									original="img/9275106_60x98.jpg" width="60" height="98"
									border="0" alt="" /></a> <a
								href="<s:property value="#sortProduct.link"/>" target="_blank"
								class="prod-name"
								title="<s:property value="#sortProduct.title"/>"><s:property
										value="#sortProduct.title" /></a>
								<div>
									<!--<span class="prod-soldnum">售出 <strong>620</strong></span>-->
									<span class="prod-price"> <s:property
											value="#sortProduct.synopsis" />
									</span>
								</div>
							</li>
						</s:else>
					</s:iterator>
				</s:if>
			</ul>
		</div>
	</div>
	<!--热销榜单 end-->
	</div>
	<s:set name="fir_index" value="#fir_index+1" />
	</s:iterator>
	</s:if>
	</div>
</body>
</html>