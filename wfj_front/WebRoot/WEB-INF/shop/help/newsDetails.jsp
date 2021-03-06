<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 易采快报的详情页面 -->
<title>易采快报</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/product_search.css" type="text/css" rel="stylesheet"/>
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/index_header.css" />
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/header.css" />
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/base.css" />
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/style.css" />
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/public.css" />
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/cart.css" />
<link type="text/css" rel="stylesheet" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/scrollup.css" />
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/index.css"/>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/modal.css" />
<!-- 商城底部文章样式 -->
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/css/pubfootcss.css" />
<style type="text/css">
.cont_wenz{ width:1000px; float:left; margin-left:22%; display:inline; min-height:700px; height:auto;}
.wz_bt{ width:1000px; height:30px; line-height:30px; font-size:16px; color:#000; text-align:center; font-weight: bold; padding-top:10px;}
.time_zuoz{ font-size:12px; color:#666; text-align:center; width:1000px; border-bottom:1px #ccc dashed; padding-bottom:10px;}
.clear{ clear:both; font-size:0; height:0; line-height:0;}
.duanluo{ padding:15px 0 15px 20px; font-size:12px; color:#333; line-height:22px; width:980px; text-indent:24px;}
.rmss{ font-size:12px; color:#999;}
.rmss:hover{ font-size:12px; color:red;}
#shopimg_cart{font-size:18px; color:#fff; height:37px; line-height:30px; display:block; float:left; padding-left:5px;font-family: 微软雅黑;font-weight: bold;}
	#shopimg_cart a{color:#fff; text-decoration: none;}
	#shopimg_cart a:hover{color:#fbe303; text-decoration: none;}
</style>
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

	//转向一级分类
	function goFirstType(firstTypeId,level){
		var winopen = window.open ("${pageContext.request.contextPath}/frontBrands/getBrandsAndProductInfos.action?productTypeId="+firstTypeId+"&level="+level+"&orderBy=normal");
	}

		//购物车，我的收藏，用户中心
		function doRedirect(state) {
			if(state == "2"){
				window.location = "${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
			}else if (state == "1") {//My account
				window.location = "${pageContext.request.contextPath}/front/customer/index.action";
			}else if(state == "3"){
				var str = escape(window.location.href);
				re=new RegExp("%","g"); 
				var newstart=str.replace(re,"_");
				var url="<s:property value='directUrl'/>";
				if(url!=""){
					window.location ="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?directUrl="+url;
				}else{
					window.location = "${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?directUrl="+newstart;
				}
					
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
</script>
</head>
<body>
<div class="hesder_topCode">
		<!--top-->
		<div class="hesder_top">
			<div class="welcomeYin fl" style="padding-left:0; width:150px;">
				<div
					style=" width:16px; height:16px; position:relative; left:0px; top:7px; float: left;">
					<img style="width:16px;height:16px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/brand_channel/pinpaipindao_09.png"/>
				</div>
				<div style="width:100px; float:left; padding-left:6px;">
					<a
						href="javascript:window.external.addFavorite('http://www.baidu.com','SHOPJSP')"
						title="SHOPJSP">收藏商城</a>
				</div>
			</div>
			<div class="welcomeYin fr">
				<a style="margin-left:0;padding:0 7px 0 3px;" class="fr" href="${pageContext.request.contextPath}/helpFront/gotoHelpFront.action" style="">
					<s:if test="#session.customer==null">	
						<img style="position:relative; padding-left:6px; padding-right:3px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg" />
					</s:if>
					帮助中心
				</a>
				<s:if test="#session.customer!=null">	
					<a style="margin-left:0; padding:0 3px;" class="fr"
						href="javascript:doSellerCheck(<s:property value='#session.customer.type'/>);">
						<img style="position:relative;top:3px; padding-left:6px; padding-right:3px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg" />
						用户中心
						<img style="position:relative;top:3px; padding-left:6px; padding-right:3px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg" />
					</a>
				</s:if>
				<%-- 
				 <a style="margin-left:0; padding:0 3px;" class="fr" href="javascript:doRedirect('1');"><img style=" padding-right:6px;padding-left:6px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg"/>买家中心<img style=" padding-right:6px;padding-left:6px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/majia_geixian.jpg"/></a>
				  --%>
				<!-- 当session中存在customer时 ，作为登录处理 -->
				<s:if test="#session.customer!=null">
					<div style="float: right;">
						您好，&nbsp;
						<span>
							<font color="red">
								<s:if test="#session.shopInfo.companyName!=''&&#session.shopInfo.companyName!=null">
									<s:property value="#session.shopInfo.companyName" />
								</s:if>
								<s:else>
									<s:property value="#session.customer.loginName" />
								</s:else>
								<s:if test="#session.sonaccount!=null">
									<s:if test="#session.sonaccount.type==1">
										采购人员
									</s:if>
									<s:if test="#session.sonaccount.type==2">
										财务人员
									</s:if>
									: <s:property value="#session.sonaccount.nickname" />
								</s:if>
							</font>
						</span>！ 
						&nbsp;
						[<a href="${pageContext.request.contextPath}/loginCustomer/exitLogin.action">退出</a>]
					</div>
				</s:if>
				<s:else>
					<div id="loginShow" style="display:none;float: right;">
						<span id="cookie_loginName"></span>!&nbsp;欢迎来到SHOPJSP! &nbsp;
						[<a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">买家登录</a>]
						[<a href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=3">买家注册</a>]&nbsp;&nbsp;&nbsp;
						[<a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=2">卖家登录</a>]
						[<a href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=2">卖家注册</a>]
					</div>
					<div id="noLoginShow" style="display:block;float: right;">
						您好!&nbsp;欢迎来到SHOPJSP!&nbsp;
						[<a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=1">买家登录</a>]
						[<a href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=3">买家注册</a>]&nbsp;&nbsp;&nbsp;
						[<a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?type=2">卖家登录</a>]
						[<a href="${pageContext.request.contextPath}/register/gotoCustomerRegistrationPage.action?type=2">卖家注册</a>]
					</div>
				</s:else>
			</div>
		</div>
	</div>
	<!--top end-->

	<!--topBottom-->
	<div class="topBottom" >
		<h1 class="fl">
<!-- 	标准log大小	style="width: 259px;height:49px;" -->
			<a href="/"><img
				style="width: 311px;height:49px;"
				src="${fileUrlConfig.fileServiceUploadRoot}front/images/logo.png" /></a>
		</h1>
		<div class="fl searchYin" style=" margin-left:37px; margin-right:0;">
			<form action="${pageContext.request.contextPath}/frontSearchProduct/productInfoSearch.action"
				method="get" id="searchForm">
				<input type="hidden" name="pager.keyword" id="searchContentValue" />
				<div class="searchYinT">
					<!-- 		            <input style="color:#727171; margin:0 0 0 30px; height:28px; line-height:28px; float: left; display:inline-block;" id="searchContent" class="searchText" type="text" value="请输入品牌，订货号，商品名称" onfocus="focusAndBlur('focus')" onblur="focusAndBlur('blur')"  value="<s:property value='keyword'/>"/> -->
					<input autocomplete="off"
						style="color:#727171;margin:0px 0 0 30px; float: left; display: inline-block;"
						id="searchContent" class="searchText" type="text" value="" placeholder="请输入品牌，订货号，商品名称"
						onfocus="focusAndBlur('focus')" onblur="focusAndBlur('blur')"
						value="<s:property value='keyword'/>" /> <input
						onclick="searchImg()" class="searchSubmit" type="submit"
						value="搜 索"
						style="float: left; display: inline-block; height: 31px; line-height: 31px;" />
				</div>
				
				<!-- 热门搜索广告 -->
				<s:action name="getHotSearchListnInfo" namespace="/shopHome" executeResult="true">
					<s:param name="pager.keyword" value="keyword"></s:param> 
				</s:action>
			</form>
			
		</div>
		
		
		<!-- 热线电话 -->
        <div style="float:right; width:163px; height:31px; line-height:31px; padding-top:13px; color:#333; font-size:16px; font-weight:bold; text-align: right; padding-right:6px;">
        	<div style="float: left;">
	          	<img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/phone.png" style="height:31px; width:43px;" />
        	</div>
          	<div style="float: left;">
	          	<p style="line-height:15px;width:120px;">400-611-2651</p>
	          	<p style="border-bottom: 1px solid;margin-top:-8px;width:116px;margin-left:4px;"></p>
		        <p style="line-height:15px;font-size: 14px;margin-top:-8px;width:110px; text-align: center; letter-spacing: 3px; padding-left:10px;">让采购更简单!</p>
          	</div>
        </div>
		
	</div>
	


	<!--左侧分类 begin-->
	<div class="header_nav" style="padding: 0px;">
		<div class="header_navContent">
			<div class="allsort">
				<div class="mt">
					<strong><a
						href="javascript:;" style="cursor:default ;text-decoration: none;">全部商品分类</a></strong>
				</div>
<!--  全部商品分类链接地址 	${pageContext.request.contextPath}/frontCategories/gotoShopCategoriesPage.action?headerType=AllCategory -->
				<div class="mc">
					<div class="mcCode">
						<div class="header_bgyinT"></div>
						<s:iterator value="#application.categoryMap" var="ptbOne"
							status="stOne">
							<div class="item <s:if test='#stOne.index==0'>fore</s:if>">
								<span> 
								<img
									src="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/img/header_navpic<s:property value='#stOne.index+1'/>.png"
									width="20" height="20" />
									<h3>
										<a title="<s:property value='#ptbOne.key.sortName'/>" href="javascript:void(0);"
											onclick="goFirstType('<s:property value='#ptbOne.key.productTypeId'/>','<s:property value='#ptbOne.key.level'/>')">
											<s:if test="#ptbOne.key.sortName.length()>=10">
												<s:property value="#ptbOne.key.sortName.substring(0,9)"/>
											</s:if>
											<s:else>
												<s:property value="#ptbOne.key.sortName"/>
											</s:else>
										</a>
									</h3>
									 <s></s>
								</span>
								<div class="i-mc">
									<div class="close"
										onclick="$(this).parent().parent().removeClass('hover')"></div>
									<div class="subitem">
										<s:iterator value="#ptbOne.value" var="ptbTwo" status="st">
											<dl class="<s:if test='#st.index==0'>fore</s:if>">
												<dt style="cursor:pointer;"
													onclick="goFirstType('<s:property value='#ptbTwo.key.productTypeId'/>','<s:property value='#ptbTwo.key.level'/>')">
													<s:property value="#ptbTwo.key.sortName" />
												</dt>
												<dd>
													<s:iterator value="#ptbTwo.value" var="ptbThree">
														<em><a href="javascript:void(0);"
															onclick="goFirstType('<s:property value='#ptbThree.key.productTypeId'/>','<s:property value='#ptbThree.key.level'/>')"><s:property
																	value="#ptbThree.key.sortName" /></a></em>
													</s:iterator>
												</dd>
											</dl>
										</s:iterator>
									</div>
									<div id="JD_sort_k" class='fr'>
										<div id="JD_sort_k" class='fr'
											style="height: 400px;overflow: hidden;">
											<s:if test="#application.categoryBrandMap.size > 0">
												<s:iterator value="#application.categoryBrandMap" id="bm"
													status="st">
													<s:if test="#bm.key==#ptbOne.key">
														<s:if test="#bm.value.size>0">
															<s:iterator value="#bm.value" var="brand">
																<a href="javascript:brandLink('<s:property value='#brand.brandName'/>');">
																	<img
																	src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value="#brand.brandBigImageUrl"/>"
																	width="120" height="50" alt="" style="margin-top:10px;" />
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
					<a href="/" style="background-color: #0f5fb1;">首页</a>
				</p>
				<p class="rig_gx">
<!-- 					<a href="${pageContext.request.contextPath}/brandChannel/gotoBrandChannelPage.action?headerType=brands">品牌</a> -->
						<a href="${pageContext.request.contextPath}/brandDirectory/queryByFlag.action?flag=A">品牌</a>
				</p>
				<p class="rig_gx">
					<a  href="${pageContext.request.contextPath}/homeModule/dayRecommend.action?headerType=dayRecommend">热卖</a>
				</p>
				<p class="rig_gx"><a href="${pageContext.request.contextPath}/homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&flagImgOrList=1">专业推荐</a></p>
				<p class="rig_gx"><a id="home_industrySpecific" class="header_Selected" href="${pageContext.request.contextPath}/front/quickOrder/quickOrder.action">快速下单</a></p>
			</div>
			<ul class="cart-bar-online" id="jq_topmenu">
         <s:if test="#session.cartCountMap!=null">
			<span id="count" style="width:25px; font-size:12px; color:#fbe303;font-weight:bold; text-align:center; display:block; height:13px; line-height:13px; float:left; padding-left:59px;"><s:property value="#session.cartCountMap" /></span>
		</s:if>
		<s:else>
			<span id="count" style="width:25px;font-size:12px; color:#fbe303;font-weight:bold; text-align:center; display:block; height:13px; line-height:13px; float:left; padding-left:59px;">0</span>
		</s:else>
		<span id="shopimg_cart"><a href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action">购物车</a></span>
		<li>
			<div id="divShopCart" class="jq_hidebox" style="display: none;">
		        <s:if test="#session.cartMap.size>0">
		        <div id="divMini" class="cart-bar-expand">
		        <s:iterator value="#session.cartMap" var="cartMap">
		        	<s:iterator value="#cartMap.value" var="cart">
				        <dl>
				            <dd class="image">
				            	<a href="${pageContext.request.contextPath}/productInfo/${cart.productId}.html" target="_blank">
				            		<img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"  src="${application.fileUrlConfig.visitFileUploadRoot}<s:property value='#cart.smallImgUrl'/>" style="height:35px; width:35px;" />
				                </a>
				            </dd>
				            <dt>
				            	<a muse_scanned="true" target="_blank" href="${pageContext.request.contextPath}/productInfo/${cart.productId}.html"><s:property value="#cart.productName"/></a>
				            </dt>
				            <dd class="operation">
				            	<a onclick="deleteCart('<s:property value="#cart.productId"/>');" href="javascript:;" class="secondary-link" style="color: #1478da;">删除</a>
				            </dd>
				            <dd>
				                <input type="hidden" id="salesPrice<s:property value="#cart.productId"/>" value="<s:property value="#cart.buyPrice"/>"/>
				                <input id="amount_<s:property value="#cart.productId"/>" name="amountItme" type="hidden" value="<s:property value="#cart.quantity"/>" />
				            	¥<em><s:property value="#cart.buyPrice"/></em>x<i><s:property value="#cart.quantity"/></i>
				            </dd>
				       </dl>
		        	</s:iterator>
			    </s:iterator>  
	            <div class="cart-bar-expand-total">
	                <p>共<i id="minicartcnt2"><s:property value="#session.cartMap.size" /></i>件产品</p><p>合计:¥<em id="minicarttotalprice"><span id="Subtotal"></span></em></p>
	                <input value="去结算" id="btnGoPay" href="javascript:;" onclick="verdictgwc();" class="btn btn-s" type="button" />
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
			
		
 <!--right star-->
   
	<div class="cont_wenz">
      <div class="wz_bt">${cmsArticle.title }</div>
      <p class="time_zuoz">时间：${cmsArticle.createTime }</p>
      <p class="duanluo">
        <s:property escape="false" value='cmsArticle.content'/>
      </p>
    </div>
    <!--right end-->		
		
	


<%@ include file="../include/footer.jsp"%>
</body>
</html>
