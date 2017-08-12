<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>购物车</title>
<%@ include file="../include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css"/>
<script type="text/javascript">
	//初始化
	$(function(){  
		if('${cartMap}'!=""){
	   		var storeNumber = document.getElementsByName("storeNumber");//库存数
	   		var amountItme = document.getElementsByName("amountItme_cart");//购买商品数
	   		for(var i=0;i<amountItme.length;i++){
	   			var count = amountItme[i].value;//当前数量
	   			//判断当前购买数量是否超过999
		   		if(Number(count)>Number(999)){
		   				$("#"+amountItme[i].id).val("999");
	   			}
	  			var store = storeNumber[i].value;//库存
	  			var priceId=amountItme[i].id;
	   			var array = new Array();
				array=priceId.split("_");
				var shopInfoId=array[0];//店铺id
				var id=array[1];
				var productId=array[2];//购物车id
		   		var b = $("#stockUpDate_hidden_"+id).val();//预计出货日
	  			if(Number(store)<=0){
	   				$("#checkOut"+shopInfoId).css("display","none");
	   				$("#noCheckOut"+shopInfoId).css("display","");
	   				$("#showCount"+productId).css("display","none");
	   				$("#noShowCount"+productId).css("display","");
	   			}
	   			if(Number(count)>Number(store)){
	   				$("#s_"+id).attr("class","yjfhr2");
					$("#s_"+id).html(b+"天");
	   			}else{
	   				$("#s_"+id).attr("class","yjfhr");
					$("#s_"+id).html("当日出货");
	   			}
	   		}
	   		amountMoneyCart();//金额和数量
	   		selectAllCheckBox('allcheckbox');//默认全选
		}
	});

    //商品数量的加减，num为1是减 2为加  0为失去焦点时触发
	function  add(productId,num){
		var b = $("#stockUpDate_hidden_"+productId).val();
    	var amount=$("#amount_"+productId+"_cart").val().trim();//当前数量
    	var storeNumber = $("#storeNumber"+productId).val();
    	var a=/^[1-9]?[0-9]?[0-9]?$/;
    	if(!a.test(amount)){
    		lalert("提醒","只能输入小于1000的正整数！");
    		$("#amount_"+productId+"_cart").val(1);
    	}else {
	    	if(num=="0"){
	    		//当前数值是否为0
	    		if(amount==0){
	    			lalert("提醒","购买数量不可为0！");
	        		$("#amount_"+productId+"_cart").val(1);
	    		}else{
	    			if(Number(amount)>Number(storeNumber)){
						$("#s_"+productId).attr("class","yjfhr2");
						$("#s_"+productId).html(b+"天");
					}else{
						$("#s_"+productId).attr("class","yjfhr");
						$("#s_"+productId).html("当日出货");
					}
	    		}
	   		}else{
				var salesPrice= $("#salesPrice1"+productId).val();//单价
				if(num=="-1"){//减少商品数量
					if(amount>1){
						if(amount>0&&amount<1000){
						amount=Number(amount)-Number(1);
						}
						$("#amount_"+productId+"_cart").val(amount);
					}else{
						amount=1;
					}
				}else if(num=="-2"){//增加商品数量
					if(amount>0&&amount<1000){
						amount=Number(amount)+Number(1);
					}
					$("#amount_"+productId+"_cart").val(amount);
				}else if(num=="0"){
				}
				if(amount<=storeNumber){
					$("#s_"+productId).attr("class","yjfhr");
					$("#s_"+productId).html("当日出货");
				}else{
					$("#s_"+productId).attr("class","yjfhr2");
					$("#s_"+productId).html(b+"天");
				}
	   		}
		}
		amountMoneyCart();//金额和数量
	}
    //统计金额和数量
    function amountMoneyCart(){
		var amountItme = document.getElementsByName("amountItme_cart");
		var costPriceTotal =0;//市场价总额
		var Subtotal_cart=0;//销售总价格
		var total="";//小计
		for(var i=0;i<amountItme.length;i++){
			var priceId=amountItme[i].id;
			var array = new Array();
			array=priceId.split("_");
			var productId=array[1];//商品id
			if(document.getElementById("checkbox_"+productId).checked){
				var price=$("#salesPrice1"+productId).val();//销售单价
				var costPrice=$("#costPrice"+productId).val();//市场价
				var discount=$("#discount"+productId).val();//会员折扣
				var OneAmount=amountItme[i].value;//当前商品数量
				if(null!=discount&&discount!=""){
					total =Number(price*OneAmount*discount/10);
					//Subtotal_cart=Number(price*OneAmount*discount/10)+Number(Subtotal_cart);//销售总价格
					//costPriceTotal=Number(costPrice*OneAmount*discount/10)+Number(costPriceTotal);//市场价总额
				}else{
					total =Number(price*OneAmount);
					//Subtotal_cart=Number(price*OneAmount)+Number(Subtotal_cart);//销售总价格
					//costPriceTotal=Number(costPrice*OneAmount)+Number(costPriceTotal);//市场价总额
				}
				Subtotal_cart=Number(total)+Number(Subtotal_cart);//销售总价格
				costPriceTotal=Number(costPrice*OneAmount)+Number(costPriceTotal);//市场价总额
				total = formatCurrency(total);
				$("#total"+productId).html("￥"+total);//当前商品价格小计
				Subtotal_cart=Subtotal_cart.toFixed(2); 
				costPriceTotal=costPriceTotal.toFixed(2);
			}
		}
		$("#SubtotalItems").html("折扣前总价:￥"+formatCurrency(costPriceTotal));//市场价总额
		$("#Subtotal_cart").html("￥"+formatCurrency(Subtotal_cart));//销售价总额
		var Discount = Number(costPriceTotal)-Number(Subtotal_cart);//差额
		Discount=formatCurrency(Discount);//小数点后两位 
		$("#Discount").html("-&nbsp;折扣: ￥"+Discount);
		$("#hiddenDiscount").val(Discount);
		//$("#StatusSubNum").html(SubNum+"/50");
    }
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
     //全选属性
  	   function selectAllCheckBox(id){
  		    var b = document.getElementsByName("cartInfo");
	        var all = document.getElementById(id);
	        if(all!=null){
	           if(all.checked){
	               for(var i=0;i<b.length;i++){
	                   b[i].checked=true;
	               }
	           }else{
	               for(var i=0;i<b.length;i++){
	                   b[i].checked=false;
	               }
	           } 
	           amountMoneyCart();//全选商品后统计金额
	        }
    	} 
     
     //点击一个复选框时
     function doCheckbox(){
	       //获取选中checkbox的长度
	     var checkLength=$('input[name="cartInfo"][flag="son"]:checked').length;
	       //获取所有checkbox的长度（除全选checkbox外）
	     var totalLength=$('input[name="cartInfo"][flag="son"]').length;
		   if(checkLength==totalLength){
		        //选中全选checkbox
		    	 $("#allcheckbox").attr("checked","checked");
	             $("#deleteCheckBox").attr("checked","checked");
		     }else{
		    	 $("#allcheckbox").removeAttr("checked");
	             $("#deleteCheckBox").removeAttr("checked");
		     }
		   amountMoneyCart();//全选商品后统计金额
		}
     
     //onclickCheckOut提交
     function toCheckOut(){
    	 var type = '${session.customer.type}';
    	 if(type==2){
 			lalert("提醒","由于您是供应商,不能结算商品");
 		}else{
	    	 var jsonCart="[";//json格式记录购物车id，数量，此商品的总价
	    	 var productIds="";//记录商品ids
	    	 var discount="";
	    	 $(".checkboxClass").each(function(){
				if(this.checked){//如果选择商品
					var productId = this.value;
					var count= $("#amount_"+productId+"_cart").val();//当前商品数量
					var salesPrice= $("#salesPrice1"+productId).val();//单价
		    			discount=$("#hiddenDiscount").val();//折扣
					var totalPrice=salesPrice*count;//s个商品总价
					var yjchr = $("#stockUpDate_hidden_"+productId).val();//预计出货日
		    		jsonCart+="{'productId':'"+productId+"','count':'"+count+"','totalPrice':'"+totalPrice+"','stockUpDate':'"+yjchr+"'},";
		    		if(""==productIds){
		    			productIds=productId;
		    		}else{
		    			productIds=productIds+","+productId;
		    		}
				}
			 });
	    	 if(jsonCart!="["){
	    		 jsonCart=jsonCart.substring(0,jsonCart.lastIndexOf(","));
	    		 jsonCart+="]";
	    	 }else{
	    		 jsonCart="";
	    	 }
	    	 if(productIds==""){
	    		 $("#submitImgLoad").html("请选择商品！");
	    	 }else{
		    	 $("#submitImg").attr("disabled","disabled");
		    	 $(".submitImgLoad").attr("style","display:block;float: right;margin-right: 20px;");
	    	 	 window.location="${pageContext.request.contextPath}/front/customer/shoppingOnline/toCheckOut.action?jsonCart="+jsonCart+"&productIds="+productIds+"&discount="+discount;
	    	 }
 		}
     }
     /**
      * 将数值四舍五入(保留2位小数)后格式化成金额形式
      *
      * @param num 数值(Number或者String)
      * @return 金额格式的字符串,如'1,234,567.45'
      * @type String
      */
     function formatCurrency(num) {
         num = num.toString().replace(/\$|\,/g,'');
         if(isNaN(num))
         num = "0";
         sign = (num == (num = Math.abs(num)));
         num = Math.floor(num*100+0.50000000001);
         cents = num%100;
         num = Math.floor(num/100).toString();
         if(cents<10)
         cents = "0" + cents;
         for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
         num = num.substring(0,num.length-(4*i+3))+','+
         num.substring(num.length-(4*i+3));
         return (((sign)?'':'-') + num + '.' + cents);
     }
   //验证cookie中是否存在所要的数据
   function chkcookies(NameOfCookie){
	   var c = document.cookie.indexOf(NameOfCookie+"="); 
	   if (c != -1){
	   	return true;
	   }
	   return false;
   }
</script>
<style>
	.yjfhr{color:red;background-color: #FF0000;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
	.yjfhr2{color:red;background-color: #3C89FF;width: 60px;border-radius: 2px;color: #FFFFFF;display: inline-block; line-height: 18px;padding: 4px 7px;text-align: center;}
</style>
</head>
<body>
<%@ include file="../include/header.jsp"%>
<div class="margin-center PublicWidth1004">
<!--mainClass-->
<s:if test="cartMap!=null&&cartMap.size>0">
<div class=" ClearBoth">
<div id="cart">
	<div class="cart_title">
    	<ul>
        	<li class="title4 strong ColorWhite1 " style="line-height:41px;">1.我的购物车</li>
            <li class="strong " style="line-height:41px;">2.填写核对订单信息</li>
            <li class="strong" style="line-height:41px;">3.成功提交订单</li>
        </ul>
    </div>
    <div class="progress_bar strong publicPaddingT15">总数 <img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/cart/bar.jpg"/><span id="StatusSubNum"><s:property value="#session.cartCountMap" />/50</span></div>
    <div class="BackgroundRed height5" style="margin:10px 0 0 0;" ></div>
    <div class="order">
    <table border="0" class="BackgroundF2Hui text-center line-height3" style="width:100%;">
	  <tr align="center">
	    <td width="5%"  align="left" class="publicBorderR strong publicBorderB">
	     <input id="allcheckbox" type="checkbox" name="cartInfo" checked="checked" onclick="selectAllCheckBox(this.id)" value="" style="float:left;margin-top:11px;"/>
	           全选
	     </td>
	     <td width="7%" align="center" valign="middle" class="publicBorderR strong publicBorderB">订货号</td>
	    <td width="18%" align="center" valign="middle" class="publicBorderR strong publicBorderB">商品信息</td>
	    <td width="10%" align="center" valign="middle" class="publicBorderR strong publicBorderB">店铺 </td>
	    <td width="10%" align="center" valign="middle" class="publicBorderR strong publicBorderB">价格 </td>
	    <td width="12%" align="center" valign="middle" class="publicBorderR strong publicBorderB">会员折扣 </td>
	    <td width="7%" align="center" valign="middle" class="publicBorderR strong publicBorderB">预计出货日 </td>
	    <td width="10%" align="center" valign="middle" class="publicBorderR strong publicBorderB">数量 </td>
		<td width="5%" align="center" valign="middle" class="publicBorderR strong publicBorderB">库存 </td>
	    <td width="10%" align="center" valign="middle" class="publicBorderR strong publicBorderB">小计(未含运费) </td>
	    <td width="8%" align="center" valign="middle" class="strong publicBorderB">操作</td>
	  </tr>
	  <c:forEach items="${cartMap}" var="cartMap" >
	  	<c:forEach items="${cartMap.value}" var="cart" varStatus="a">
	  	<input id="freightPrice_${cart.productId}" type="hidden" value="${cart.freightPrice}" />
	  	  <tr>
	  	  
		    <td width="140"  align="left" style="border-bottom: 2px #ccc dotted;">
		    	<c:if test="${cart.flag==0}">
		    		<input class="checkboxClass" name="cartInfo" flag="son" onclick="doCheckbox();" id="checkbox_${cart.productId}" type="checkbox" checked="checked" value="${cart.productId}"/>
		    	</c:if>
		    	<c:if test="${cart.flag!=0}">
		    		<input disabled="disabled"  type="checkbox" value=""/>
		    	</c:if>
		    </td>
		    
	  	  	<td style="border-bottom: 2px #ccc dotted;">
		    	<span>${cart.sku}</span>
		    </td>
		    
		    <td class="items" style="border-bottom: 2px #ccc dotted;">
		    	<a target="_blank" href="${pageContext.request.contextPath}/productInfo/${cart.productId}.html" class="strong"><img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'"  src="${application.fileUrlConfig.visitFileUploadRoot}${cart.smallImgUrl}" style="height:50px; width:50px; float:left; margin-right:5px; dipalay:inline;" class="publicBorder"/></a>
		    	<a target="_blank" href="${pageContext.request.contextPath}/productInfo/${cart.productId}.html" class="strong" style="height:50px; width:100px;">${cart.productFullName}</a>
		        
		    </td>
		    
		    <c:if test="${a.first==true}">
			    <td rowspan="${fn:length(cartMap.value)}" align="center"  class="strong" style="border-bottom: 2px #ccc dotted;">
			    	<a style=" line-height:25px;" target="_blank" href="${pageContext.request.contextPath}/store/frontShopMainPage/gotoShopInfoPage.action?orderBy=normal&shopInfoId=${cart.shopInfoId}" class="strong ColorBlue publicunderline">${cart.shopName}</a>
			    </td>
		    </c:if>
		    
		    <c:if test="${cart.flag==0}">
		    <td id="xxxx" align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
		    	<input type="hidden" id="costPrice${cart.productId}" value="${cart.marketPrice}"/>
		    	<input type="hidden" id="salesPrice1${cart.productId}" value="${cart.salesPrice}"/>
		    	<span class="del">￥&nbsp;<fmt:formatNumber value="${cart.marketPrice}" pattern="#,##0.00#" /></span>
		    	<span class="ColorRed">￥&nbsp;<fmt:formatNumber value="${cart.salesPrice}" pattern="#,##0.00#" /></span>
		    </td>
		    
		    <td id="xxxx" align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
		    <c:if test="${cart.discount!=null}">
		    	<input type="hidden" id="discount${cart.productId}" value="${cart.discount}"/>
		    	<span class="ColorRed"><fmt:formatNumber value="${cart.discount}" pattern="#,##0.0#" />&nbsp;折</span>
		    </c:if>	
		    <c:if test="${cart.discount==null}">
		    	<span class="ColorRed">无折扣</span>
		    </c:if>	
		    </td>
		    
		    <td align="center" valign="middle" style="border-bottom: 2px #ccc dotted;">
		    	<span id="s_${cart.productId}" class="yjfhr"></span>
		    </td>
		    
		    <input type="hidden"  id="stockUpDate_hidden_${cart.productId}"  value="${cart.stockUpDate }"/>
		    <td class="quantity " align="center" width="100" style="border-bottom: 2px #ccc dotted;">
		    	<div id="showCount${cart.productId}" style="display: ">
			    	<input type="hidden" id="storeNumber${cart.productId}" name="storeNumber" value="${cart.storeNumber}"/>
				    <a href="javascript:;" style="text-decoration: none;" class="add" onclick="add('${cart.productId}','-1')">-</a>
				    <input style="text-align:center; vertical-align:middle;" id="amount_${cart.productId}_cart" name="amountItme_cart" type="text" onblur="add('${cart.productId}','0')"  value="${cart.quantity}" maxlength="3"/>
				    <a href="javascript:;" style="text-decoration: none;"  class="add" onclick="add('${cart.productId}','-2')">+</a>
	    		</div>
	    		<div id="noShowCount${cart.productId}" style="display:none">
	    			<span style="color: red">库存为 0</span>
	    		</div>
		    </td>
		    
		    <td style="border-bottom: 2px #ccc dotted;">
		    	<span>${cart.storeNumber}</span>
		    </td>
		    
		    <td align="center" style="border-bottom: 2px #ccc dotted;"><span id="total${cart.productId}"></span></td>
		  	</c:if>
		  	<c:if test="${cart.flag!=0}">
		  		<td colspan="6" style="border-bottom: 2px #ccc dotted;">此商品已下架或不存在</td>
		  	</c:if>
		    <td  align="center" style="border-bottom: 2px #ccc dotted;"><a class="ColorBlue publicunderline" onclick="deleteCart('${cart.productId}');" href="javascript:;" >删除</a></td>
		  </tr>
		  </c:forEach>
	  </c:forEach>
	  <tr>
	        <td  align="right" colspan="11" class="FontSize14 BackgroundE4Hui"> 
	        	<span  class="publicblock line-height2" id="SubtotalItems" style="text-align:right;"></span>
	            <span  class="publicblock line-height2" id="Discount" style="text-align:right;"></span>
	          	<span  class="publicblock line-height2" style="text-align:right;">合计: <span class="strong ColorRed publicPaddingL7" id="Subtotal_cart"></span></span>
	            <input type="hidden" id="hiddenDiscount" value=""/>
            </td>
      </tr>
      <tr>
      	<td align="right" colspan="11" class="FontSize14 BackgroundE4Hui">
      		<div id="checkOut" style="display: ">
        		<a href="javascript:;" id="submitImg" onclick="toCheckOut();"  class="submitImg ColorWhite1 publicinline publicPadding5_10 FontSize15 radius strong BackgroundQRedddd" style="float: right;height:30px;line-height:30px;text-align: center;width: 80px;">去结算</a>
      			<p id="submitImgLoad" class="submitImgLoad  strong" style="display:none;float: right;margin-right: 20px;width:200px;font-size:12px;font-weight:normal;"><img src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/loader.gif"/>&nbsp;&nbsp;正在提交请稍等...</p>
      		</div>
      		<div id="noCheckOut"  style="display:none">
      		<span class='ColorWhite1 BackgroundRed publicinline publicPadding5_10 FontSize16 radius float-right publicMarginTB20 strong'>抱歉,没有库存您无法购买!</span>
      		</div>
      	</td>
      </tr>
  	  <tr style="width: 1210px;">
        <td colspan="11" style="width: 1210px;">
           <div style=" float:left; width:137px;  height:51px;">
           <input id="checkBoxIsOkFlag" type="hidden" value="1"/>
            <input id="deleteCheckBox" name="cartInfo" onclick="selectAllCheckBox(this.id);" type="checkbox" checked="checked" type="checkbox" value=""style="float:left;margin-top:11px;"/>
         	<span class="strong publicPaddingR7">全选</span>&nbsp;<a style="cursor:pointer;" class="ColorBlue publicunderline strong" onclick="deleteCart('all')">删除选中商品</a>
         </div>
         	<div style=" float:right;  width:73px;  height:51px;">
         	  <a style="text-align: right;" href="${pageContext.request.contextPath}/"  class=" ColorBlue publicunderline strong">继续购物&gt;&gt;</a>
         	</div>
         </td>
      </tr>

  </table>
	</div>
   

</div>
</div>
</s:if>

<s:else>
<input id="checkBoxIsOkFlag" type="hidden" value="0"/>
	<s:if test="#session.customer!=null">
		<div style="text-align: center;font-size: 16px;padding-top: 50px;">
			购物车内暂时没有商品，
			<a href="${pageContext.request.contextPath}/" class=" ColorBlue">去首页</a>挑选喜欢的商品
		</div>
	</s:if>
	<s:else>
		<div style="text-align: center;font-size: 16px;padding-top: 50px;">
			购物车内暂时没有商品，<a href="${pageContext.request.contextPath}/loginCustomer/gotoLoginPage.action?directUrl=/shopFront/shoppingCar/gotoShoppingCart.action&type=1" class=" ColorBlue">登录</a>后，将显示您之前加入的商品  <br><br>
			<a href="${pageContext.request.contextPath}/" class=" ColorBlue">去首页</a>挑选喜欢的商品
		</div>
	</s:else>
</s:else>
<!--//mainClass-->
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
