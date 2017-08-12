<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>团购商品详情</title>
	<%@ include file="../include/head.jsp"%>
    <link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/public_002.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/ms3All-min.css" />
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/homeIndex/css/productInfo_list.css" />
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/product.css" />
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/oproduct.css" /> 
	<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/jquery.jcarousel.css" rel="stylesheet" type="text/css" />
	<link href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/jqzoom.css" rel="stylesheet" type="text/css" />
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.jcarousel.js" type="text/javascript"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/jquery.jqueryzoom.js" type="text/javascript"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.cookie.js"  type="text/javascript" ></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/tab.js" type="text/javascript"></script>
	<script src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/public.js" type="text/javascript"></script>
	<style type="text/css">
		table{border-collapse:collapse;border:none;}
		.paraTitle{wdith:973px;height:25px;text-align:center; background:#F5FAFE; display:block;line-height:25px; font-size:14px; font-weight:bold; border-top:1px #ccc solid;}
		.parameterInfoTr{height:25px;}
		.paraTh{border-right:1px solid #ccc; background:#F5FAFE;padding-right:5px;border-top:1px solid #ccc;border-bottom:1px solid #ccc;}
		.paraTd{padding-left:5px;background:#fff;border-top:1px solid #ccc;border-bottom:1px solid #ccc;}
		#yinProduct_1{background:#fff;padding-top:15px;}
		.discProd{width:220px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;}
		.shpinxiq_left_detail{width:350px;height:auto; float:left;padding-left:10px;}
		.yinDefault {
			border: 1px solid #ccc;
			padding: 2px 10px 2px 9px;margin:0 5px 0px 0;
			cursor:pointer;
		}
		.yinBor {
			border: 1px solid #f00 ;
		}
		.hide {
			display: none;
		}
		
		.tuweizi{width:1002px; height:auto; overflow:hidden;}
		.tuweizi p{width:619px; height:auto; line-height:24px; margin:0 auto;}
		.tuweizi ul li {
			width:292px;
			float: left;
			overflow:hidden;
			height:27px;
			margin-left:10px;
		}
		.tuweizi ul li span.nameYin{ text-align:left;}
		.tuweizi ul li span{float:left; display:block;}
		.curr_base {
			border:2px solid #ccc;
			padding: 1px;
		}
		.cur_on {
			border:2px solid #D01743;
		}
		#pd_lists {width:100%;position: relative;overflow-x: auto;overflow-y: hidden;}
		body{font-family: 微软雅黑;}
		.yinBor_i{background:url(${fileUrlConfig.fileServiceUploadRoot}shop/images/workspace/gouY.png)no-repeat;   position:absolute; width:15px; height:15px; z-index: 9; top:35px; right:0;}
		.yinBor_i2{background:url(${fileUrlConfig.fileServiceUploadRoot}shop/images/workspace/gouY.png)no-repeat;   position:absolute; width:15px; height:15px; z-index: 9; top:10px; right:0;}
		.yjfhr{color:red;background-color: #FF0000;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
		.yjfhr2{color:red;background-color: #3C89FF;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
		.ggText{text-decoration:none;width:auto; white-space: nowrap; display:block; float:left;height:25px;line-height:25px; padding:0 10px; text-align:center; position:relative;cursor: pointer;}
		.ggImage{text-decoration:none;width:50px; white-space: nowrap; display:block; float:left;height:50px;line-height:28px; padding:0; position:relative;cursor: pointer;}
		.hyds_jr{ display: block; width:226px; height:36px; border:3px #D01743 solid; background: #D01743; line-height:36px; text-align: center; color:#fff; font-size:17px; float:left; margin-right:20px; text-decoration: none;}
	</style>
    <link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/productInfo/tm_detail0522.css"/>
    <!--闪购结束时间js-->
    <script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/js/NewDetailsTool.js"></script>
    <script type="text/javascript">
		$(function () {
			$.scrollUp();
		});
	</script>
    <script type="text/javascript">
	    var frontTableTitleId = "spjs";
	    //商品详细信息的table切换
		function changeContent(paramsId){
			$("#"+frontTableTitleId).removeAttr("style");//去除上一个的头部背景色,去除样式
			$("#"+paramsId).css({"background":"#ED145B","color":"#fff"});//添加当前的头部背景色
			frontTableTitleId = paramsId;//记录当前的ID
		}
	    //跳转到订单确认页
	    function qianggou(id){
	    	//异步校验是否为本店铺商品
	    	$.ajax({
	    		url:"${pageContext.request.contextPath}/tuan/checkTuanProduct.action",
	    		dataType:"json",
	    		type:"post",
	    		data:{productId:id},
	    		success:function(data){
	    			if(data.isExsit==false){
						lalert("提醒","由于您是供应商，不能购买！");	    				
	    			}else{
	    				location.href="${pageContext.request.contextPath}/front/customer/shoppingOnline/gotoTuanOrder.action?productId="+id;
	    			}
	    		}
	    	});
	    }
		//iframe的高度自适应
		function autoHeight() { 
			var ifm= document.getElementById("iframe_body"); 
			var subWeb = document.frames ? document.frames["iframe_body"].document : ifm.contentDocument;
			if(ifm != null && subWeb != null) { 
				var height = parseInt(subWeb.body.scrollHeight);
				ifm.height = subWeb.body.scrollHeight; 
				if (ifm.contentDocument && ifm.contentDocument.body.offsetHeight) { // ff,chrome等
					height = parseInt(ifm.contentDocument.body.offsetHeight);
					ifm.height = height;
				} 
			}
		}
		//初始化倒计时
		$(function() {
			var endTime = '<s:date name="tuanProduct.endTime" />';//结束时间对象
			endTime = endTime.replace(/\-/g, "/"); 
			//调用时间倒计时方法
			countDown(endTime,"#demo01 .day","#demo01 .hour","#demo01 .minute","#demo01 .second");
		});
	    //开始时间与结束之间的倒计时
		function countDown(end_time,day_elem,hour_elem,minute_elem,second_elem){
			var endTime = new Date(end_time).getTime();
			//获得服务器当前时间
			var currentTime = new Date().getTime();
			//得到截止日期与当前日期相差时间
			var sys_second = (endTime-currentTime)/1000;
			//console.log("sys_second:"+ sys_second );//打印调试
			var timer = setInterval(function(){
				if (sys_second > 0) {
					sys_second -= 1;
					var day = Math.floor((sys_second / 3600) / 24);
					var hour = Math.floor((sys_second / 3600) % 24);
					var minute = Math.floor((sys_second / 60) % 60);
					var second = Math.floor(sys_second % 60);
					day_elem && $(day_elem).text(day);//计算天
					$(hour_elem).text(hour<10?"0"+hour:hour);//计算小时
					$(minute_elem).text(minute<10?"0"+minute:minute);//计算分
					$(second_elem).text(second<10?"0"+second:second);// 计算秒
				} else { 
					clearInterval(timer);
				}
			}, 1000);
		}
		//初始化加载商品的属性和参数
		$(function() {
				$("#innow").removeAttr("disabled");
				$("#tocart").removeAttr("disabled");
				 var html = '';
				 //分类的属性start
					var productAttributeValue = $("#productAttr").val();
	 				if(productAttributeValue!="" && productAttributeValue.length>0){
						productAttributeValue = eval(productAttributeValue);
						var attrHtml="";
						for(var k=0;k<productAttributeValue.length;k++){
							attrHtml+="<li><span class='nameYin'>"+productAttributeValue[k].name+" ： </span><span>"+productAttributeValue[k].attrValueName+"</span></li>";
						}
						$("#productAttrShow").append(attrHtml);
					}
				    //分类的属性end 
				    //分类的参数start
				    var productPara = $("#productPara").val();
				    if (productPara != "" && productPara !="[]") {
						var infoObj = eval(productPara);
						var h = 0;
						var trHtml = '<table  align="center"  class="addOrEditTable" width="900px;">';
						for ( var i = 0; i < infoObj.length; i++) {
							trHtml += '<table id="parameterTable' + i + '" class="parameterTable"  frame="below" width="100%"> ';
							trHtml += '<tr  class="parameterTr" >' + '<div class="paraTitle">' + infoObj[i].name + '</div> ';
							trHtml += ' <\/td><\/tr>';
							trHtml += '<tr > <table id="parameterInfoTable' + i + '" class="parameterInfoTable" frame="below" width="927px;">';
							var a = eval(infoObj[i].paramGroupInfo);
							for ( var u = 0; u < a.length; u++) {
								trHtml += '<tr class="parameterInfoTr" >';
								trHtml += ' <td class="paraTh" style="text-align: right;width: 150px; background:#fff;  vertical-align:middle;"> ' + a[u].name + '<\/td> ';
								trHtml += ' <td class="paraTd" style="vertical-align:middle;">' + a[u].value + ' <\/td>';
								trHtml += '<\/tr>';
								h++;
							}
							trHtml += '<\/table><\/tr>';
							trHtml += '<\/table>';
						}
						trHtml += '<\/table>';
						$("#totalProdPara").html(trHtml);
							
				    }
				    //分类的参数end
				});
	</script>
    
    <style type="text/css">
			table{border-collapse:collapse;border:none;}
			.notSelected {
				border: 1px solid #CCCCCC;
				padding: 3px 5px;
				background: #ccc;
			}
		    .countdownY .colockbox{background:url("${pageContext.request.contextPath}/common/shop/front/img/colockbg2.png");}
			.countdownY .colockbox span{color:#fff;position:relative;top:2px;}
			.paraTitle{wdith:973px;height:25px;text-align:center; background:#f5f3f4; display:block;line-height:23px; font-weight: bold;}
			.parameterInfoTr{height:25px;}
			.paraTh{border-right:1px solid #ccc; background:#f5f3f4;padding-right:5px;border-top:1px solid #ccc;border-bottom:1px solid #ccc;}
			.paraTd{padding-left:5px;background:#fff;border-top:1px solid #ccc;border-bottom:1px solid #ccc;}
			#yinProduct_1{background:#fff;padding-top:15px;}			
			.discProd{width:220px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;}
			.yinDefault {
				border: 1px solid #ccc;
				padding: 2px 10px 2px 9px;margin:0 5px 5px 0;
			}
			.yinBor {
				border: 1px solid #f00 ; background:url(${pageContext.request.contextPath}/common/shop/images/workspace/gouY.png)no-repeat right bottom;
			}
			.yinBorError {
				border: 1px solid #f00 ;margin:0 5px 5px 0;padding: 2px 10px 2px 9px; background:url(${pageContext.request.contextPath}/common/shop/images/workspace/gouerror.png)no-repeat right bottom;
			}
			.hide {display: none;}
			.tuweizi ul li {
				width: 298px;
				float: left;
				overflow:hidden;
				height:25px;
				margin-left:10px;
			}
			.tuweizi ul li span.nameYin{width:90px; text-align:right;}
			.tuweizi ul li span{float:left; display:block;}
			/*放大镜效果*/
			.shpinxiq_left_detail{width:350px;height:auto; float:left;}
			.curr_base {border:2px solid #ccc;padding:1px;}
			.cur_on {border:2px solid #ed145b;}
			.spanp{padding-left: 0; padding-right:0;} 
			.spanp p{padding-left: 2%; padding-right:2%;width: 96%;}
			 .spanp p img{padding-left: 0px;padding-right:0px;}
			.spanp img{padding-left: 2%; padding-right:2%;}
			.splunb_head ul li a{text-decoration:none; color:#fff; display:block;}
		</style>
		
  </head>
  
  <body style="font-family:微软雅黑;">  
   <!--top-->
   <%@ include file="../include/header.jsp"%>
   <input type="hidden" id="productPara" value="<s:property value="joProductPara.productParametersValue"/>"/>
   <input type="hidden" id="productAttr" value="<s:property value="jaattrList"/>"/>
   <!--top end-->
        <div class="w1000">
            <!--上面部分开始-->
            <div class="tm1209_top">
                    <!--mian bao xie -->
                     <div class="tm_mbx">
                     	<a target="_blank" href="${pageContext.request.contextPath}/">首页</a>&nbsp;&gt;&nbsp;${tuanProductType.sortName}
                     </div> 
                    <!--today tuangou-->
                    <div class="tg_today clearfix">
                        <div class="tg_today_l">
                            <b>今日团购</b>
                            <b style="margin-left: 5px;color:black;"><s:property value='tuanProduct.title'/></b><br/> 
                            <span>
                            	<s:property value='tuanProduct.introduction'/>
                            </span>
                        </div>
                          <%--   <div class="tg_today_r">
                                <img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='brand.brandBigImageUrl'/>" width="200" height="100" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"/>
                            </div> --%>
                    </div>
                    <!--imgbox-->
                    <div class="tm_imgbox clearfix">
                        <!-- <div class="xqgx"></div> -->
                        <div class="imgbox_l">
                            <img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='tuanProduct.tuanImageUrl'/>" style="padding:2px 12px 10px 11px; height:390px; width:730px;" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"/>
                        </div>
                        <div class="imgbox_r">
                            <div class="imgbox_r_jt">
                                <div class="imgbox_r_buy">
                                        <!--正常-->
                                        <input type="button" class="buy_a" name="buy" onclick="qianggou('${tuanProduct.productId}')" value="抢购"/>
                                        <span class="buy_pri"><em>￥</em>${tuanProduct.price}</span>
                                </div>
                            </div>
                            <div class="imgbox_info">
                               <div class="imgbox_sort clearfix">
                                    <span class="sort_nz"></span>
                                    <div class="sort_status">
                                        <p>距闪购结束</p>
                                        <div class='nameYin'>
                                        <div style='float:left;' class='countdownY'></div>
                                        <div style='float:left;' class='countdownY'>
											<div id="demo01" style="margin-left:5px;"><span class='day'>-</span>&nbsp;天&nbsp;<span class='hour'>-</span>&nbsp;小时&nbsp;<span class='minute'>-</span>&nbsp;分&nbsp;<span class='second'>-</span>&nbsp;秒</div>
										</div>
                                    </div>
                                </div>
                                <div class="imgbox_sort" style="margin-top:126px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="100%" class="price_table">
                                        <tbody>
                                        <tr>
                                            <td class="sort_titl">市场价</td>
                                            <td class="sort_titl">折扣</td>
                                            <td class="sort_titl">你节省</td>
                                        </tr>
                                        <tr>
                                            <td class="sort_cn">￥${productInfo.marketPrice}</td>
                                            <td class="sort_cn"><s:property value='tuanProduct.price*10/productInfo.marketPrice'/>折</td>
                                            <td class="sort_cn">￥<s:property value='productInfo.marketPrice-tuanProduct.price'/></td>
                                        </tr>
                                    </tbody>
                                    </table>
                                </div>
                                <div class="imgbox_sort clearfix" style="margin-top: 5px;float: left;">
                                        <span class="sort_corn"></span>
                                        <div class="sort_num">
                                            <p class="peo">${tuanProduct.bought}人</p>
                                            <p>已经购买</p>
                                        </div>
                                        <div class="sort_save" style="margin-top: 0px;margin-left: 50px;">
                                       		<img src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='tuanProduct.qrCode'/>" width="100" height="100" onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" />
                                        </div>
                                </div>
                                <div class="imgbox_sort clearfix bt0 fx_w" style="margin-top: 140px;">
                                    <span class="fx_text">分享到：</span> 
                                    <!-- JiaThis Button BEGIN -->
									<div class="jiathis_style">
										<a class="jiathis_button_qzone"></a>
										<a class="jiathis_button_tsina"></a>
										<a class="jiathis_button_tqq"></a>
										<a class="jiathis_button_weixin"></a>
										<a class="jiathis_button_renren"></a>
										<a class="jiathis_button_xiaoyou"></a>
										<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
										<a class="jiathis_counter_style"></a>
									</div>
									<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1396428696618235" charset="utf-8"></script>
									<!-- JiaThis Button END -->
									<div style="clear: both;"></div>
                               </div>
                            </div>
                        </div>
                    </div>
            </div>
            <!--上面部分结束-->
            <div class="tm1209_center">
                     <!--left star-->
                     <div class="tmc_l" style="margin-top: 10px;">
                          <div class="splunb_head" style="width:929px; border-bottom:0;  height:38px;">
                             <ul id="yinProduct_a_current_" style="width:929px; background: #d01743; height:38px;">
                                <li onclick="changeContent('spjs')"><a id="spjs" style="background:#ed145b;color:#fff;" class="current" href="javascript:;">商品介绍</a></li>
								<li onclick="changeContent('spcs')"><a id="spcs" href="javascript:;">商品参数</a></li>
								<li onclick="changeContent('sppj')"><a id="sppj" target="evaluateFrame" href="${pageContext.request.contextPath}/productsdetail/evaluateProductList.action?productId=<s:property value='productInfo.productId'/>" >商品评价</a></li>
								<li onclick="changeContent('shbz')"><a id="shbz" href="javascript:;">售后保障</a></li>
                             </ul>
                         </div>
                        <div class="smsp" style="width:929px; border-left:0; border-right:0; border-bottom:0;">
						<div class="tuweizi" id="yinProduct_1" style="width:929px;">
							<ul id="productAttrShow" >
								<li>
									<span class="discProd" title="${productInfo.productName}">商品名称：${productInfo.productName}</span>
								</li>
								<li>
									<span class="discProd" >上架时间：<s:date name="productInfo.putSaleDate" format="yyyy-MM-dd HH:mm:ss" /></span>
								</li>
								<li>
									<span class="discProd" >商品重量：${productInfo.weight}&nbsp;${productInfo.weightUnit}</span>
								</li>
								<li>
									<span class="discProd" >商品品牌：
										<s:if test="brand!=null">${brand.brandName}</s:if><s:else>其他</s:else>
									</span>
								</li>
								<li>
									<span class="discProd" >销售价：${productInfo.salesPrice}</span>
								</li>
							</ul>
							<div class="splunb" style="border-top:1px solid #ccc; width:929px;"></div>
							<!-- 商品描述 begin -->
							<div class="splunb" style=" width:929px;">
								<span class="spanp">
									<s:property value="productInfo.functionDesc" escape="false"/>
								</span>
							</div>
							<!-- 商品描述 end -->
						</div>
						<div class="tuweizi hide" id="yinProduct_2" style="background:#f5f3f4;">
							<span id="totalProdPara"></span>
						</div>
						<div class="tuweizi hide" id="yinProduct_3" style="width:929px;">
							<iframe id="iframe_body" onload="autoHeight()" name="evaluateFrame" width="100%" src="#" frameborder="0" scrolling="no"></iframe>
						</div>
						<div class="tuweizi hide" id="yinProduct_4" style="width:929px;">
						<div style="display: block; padding:10px;overflow:hidden;border-top:1px dotted #DEDEDE;line-height:24px;">
							<strong style="font-size: 14px;">售后保障：</strong><br/><br/>
								本店所售商品一律为原装正品并销售全国联保；按国家规定实行1年保修7退换。(7天内出现任何质量问题来回邮费由我方负责，人为除外，8-30天换货来回运费我方与贵方各一半，30天以后均有贵方负责)
									<br/>特此声明！ 签收注意事项 我司所发的商品都是经过质量检查，确保全新，完好无损，有问题之商品是出不了仓库大门。 特别提醒
									为充分保障您的权益，收货时请不要急于在物流单上签字，请先做以下操作：
								<br/>
								第一，检查外包装是否完好，并当着快递面拆箱验货（我们会在快递单上注明必须当面验货，部分快递是要求先签字后验货）
								，发现破损,配件缺失等，请立即拒收。
								<br/>
									第二，签收后产品无破损无配件缺失，请将产品通上电，简单检查产品工作状态，如不能正常运转请立即联系我们。
								<br/>
									第三，需要他人代签的（如门卫，保安，亲友等）请务尽管交
									待好签收注意项。本制度未尽事宜，按淘宝官方制度或国家相关法律法规解决，解释权归本店所有。
								<br/>
									 关于退货
								如果您在收到我们的产品15天之内，发现产品存在非人为的质量问题，我们将免费为您提供更换或者维修服务由此产生的费用同我店负责。
								更换条件：产品在两周之内发生非人为问题，没有明显使用痕迹（可以退厂）
								<br/>外观无损，所有附件均完整的情况下我们
								可以提供产品换货服务。
								退货条件：7天内没有使用过，没有人为损坏，而是由于顾客主观原因我店可以提供退货服务，但是需要由顾客承担往
								返双向邮费。维修条件：产品的代维修服务，费用由又方协商。
								关于费用：非人为质量问题的商品，有顾客描述产品故障，我店在收到退回的商品并检测确实存在顾客所描述的故障，
								运费由我们承担，检测没有顾客所描述的故障我们将原物原路寄回，运费费用由您承担；我点所承担运费仅限与第一次
								相同的运费，多余的部分由买家承担。
								拒绝到付：到付是正常运费的2倍以上，顾客如果到付运费我店将按情况处理，需要我们承担的运费的快件请在发货前 与我们沟通
						</div>
						</div>
						<script type="text/javascript">
							new tab('yinProduct_a_current_', '_', null, 'onclick');
						</script>
						<br />
						<div style="display: block; padding:10px;overflow:hidden;border-top:1px dotted #DEDEDE;line-height:130%">
							<p style="font-size: 14px;padding-bottom: 15px;"><strong>服务承诺：</strong></p>
							<p style="padding-bottom: 5px;">SHOPJSP商城向您保证所售商品均为正品行货，自营商品自带机打发票，与商品一起寄送。凭质保证书及SHOPJSP商城发票，可享受全国联保服务（奢侈品、钟表除外；奢侈品、</p>
							<p style="padding-bottom: 5px;">钟表由联系保修，享受法定三包售后服务），与您亲临商场选购的商品享受相同的质量保证。SHOPJSP商城还为您提供具有竞争力的商品价格和运费政策，请您放心购买！</p>
							<p style="padding-bottom: 5px;">注：因厂家会在没有任何提前通知的情况下更改产品包装、产地或者一些附件，本司不能确保客户收到的货物与商城图片、产地、附件说明完全一致。只能确保为原厂正货！并且保证与</p>
							<p>当时市场上同样主流新品一致。若本商城没有及时更新，请大家谅解！</p> 
						</div>
						</div>
                        </div>
                       <!--left end-->
                       <!--right star-->
                       <!-- 店铺详情 begin -->
                       <div class="tmc_r" style="margin-top: 10px;">
                       <div style="width:248px; height:auto;">
                       <div class="nkexh">店铺详情</div> 
                       <article class="publicPadding2_5 publicMarginT5">
						<h3 class="FontSize13 FontSizeB"><s:property value="shopInfo.shopName"/></h3>
						<p class="publicMarginT5">
							<s:iterator begin="0" end="photoNum-1">
								<i class="publicinline medal-j-y-t medal-j"></i> 
							</s:iterator>
						</p>
						<p>
							<span class="ColorGreen"><s:if test="grade > 0">${grade}</s:if><s:else>0</s:else>%</span>
							&nbsp;信誉
						</p>
						<s:if test="qqList!=null">
							<s:iterator value="qqList" var="map">
								<p class="publicMarginT5" style=" width: 192px;">
									<a style="text-decoration: none;" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=<s:property value='#map.qq'/>&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:12345678:51" />&nbsp;&nbsp;&nbsp;&nbsp;<span style="background-color:#2e9bf2; width: 191px; height:16px;font-size:12px; padding:2px 0; vertical-align:middle; color:#fff; border-radius:3px;">&nbsp;&nbsp;<s:property value="#map.nikeName" />&nbsp;&nbsp;</span></a>
								</p>
							</s:iterator>
						</s:if>
	 					<s:if test="shopInfo.phoneShowStatus==1">
	 						<h3 class="FontSize13 FontSizeB">联系电话：<s:property value="shopInfo.phone"/></h3>
	 					</s:if>
						<p class="publicdashedline" style="padding-bottom:5px;">
							<a target="_blank" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=<s:property value="shopInfo.shopInfoId" />" class="ColorBlue">进入店铺 &gt;&gt;</a>
						</p>
						<!-- 免邮费设置 -->
						<s:if test="shopInfo.minAmount!=null&&shopInfo.minAmount!=''">
							<div class="textP ClearBoth">
								<p style="color:red;font-weight:800">该店满<s:property value="shopInfo.minAmount"/>元免邮费<p/>
							</div>
						</s:if>
						<h4 class="FontSizeB">付款方式</h4>
						<b class=" publicinline Payments_icon " style="margin-top: 10px;">
							<img style="height:30px;width:50px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/cart/Alipay.jpg"/>
							<img style="height:40px;width:90px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/cart/wechat.jpg"/>
						</b>
					</article>
                    </div>
                </div>
                <!-- 店铺详情 end -->
                <!--right end-->
        </div>
    </div>
    
    
	<div style="clear:both;"></div>
	<div style="width:100%;">
      <%@ include file="../include/footer.jsp"%>
	</div>
</div>
</body>
</html>