<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>快速下单</title>
    <%@ include file="../include/head.jsp"%>
    <style>
	/*全局控制*/
	body{padding:0; margin:0; font-size:12px; font-family:"微软雅黑"; color:#333;}
	table, div{word-break: break-all;}
	#wrap{margin: auto; width: 1123px;height:auto;z-index: 1;color: #333;font-family: "微软雅黑";font-size: 12px;}
	#wrap table td{font-size:12px;}
	#content{margin-bottom: 10px; width: 100%; z-index: 1;}
	.quick-order table{margin-top: 10px; width: 100%;height:auto;border: 1px solid #b2b2b2; border-collapse: collapse;}
	.table thead tr{background-color: #d2d2d2;}
	.quick-order thead td{font-weight: bold; padding: 6px;}
	.table thead td{border: 1px solid #b2b2b2;}
	.quick-order table input{padding: 3px 5px;}
	.prd-select{margin-right: 5px;}
	input{vertical-align: middle;}
	.quick-order tbody td{vertical-align: middle;}
	.table tbody td{border: 1px solid #b2b2b2; border-collapse: collapse; padding: 4px 6px; text-align: center;}
	.prd-list-select{text-align: center;}
	.assist-text{color: #a2a2a2;}
	.quick-order .amount{border: 1px solid #d2d2d2; text-align: center; width: 40px;}
	.cart-alarm{background-color: #ffe0e0; border: 1px solid #f07f7f; color: #f00; float: left; margin-top: 8px; padding: 0 3px; width: 102px;}
	table .operation a{color: #1478da;}
	table .operation{ text-align: center;}
	.quick-order .add-cart{background: #eee; border:1px solid #b2b2b2;border-top:0; padding: 15px;}
	.cart-opeart{float: left; height: 25px;}
	.cart-delete{background: url("images/global.png") no-repeat; display: inline-block; float: left; padding: 2px 15px 2px 20px; width: 80px;}
	.prominent-link, .prominent-text{color: #1478da;text-decoration:none;}
	.btn-s-assist{background:none; color: #1478da; cursor: pointer; height: 24px; overflow: visible; padding: 2px 10px 3px; vertical-align: middle; width: auto !important; border: 0; font-family:'微软雅黑'; font-size: 12px;}
	.amount-total{float: right; text-align: right; width: 337px;}
	.quick-order .amount-total p{border-top: 0 none; clear: both; margin-top: 0; padding: 0 0 10px;}
	.amount-total p span{font-weight: bold;}
	.amount-total p i{color: #1478da; font-size: 22px;font-style: normal; font-weight: normal; line-height: 22px;}
	.btn-xl{/* background-position: 0 -651px;*/ font-family: '微软雅黑','黑体'; font-size: 20px; height: 42px; padding: 0 20px;width:156px;height:38px;}
	.btn-xl:hover{background-position: 0 -733px;}
	.btn1{ border:none !important; color: #fff; cursor: pointer; overflow: visible; vertical-align: middle;background:#d01743;border-radius: 4px;}
	.quick-order .bulk-add-sku{border-top: 1px solid #d2d2d2; float: left; margin-top: 20px; padding-top: 10px; width:553px;}
	.quick-order .bulk-add-sku p{font-weight: bold; margin-bottom: 5px;}
	textarea{border: 1px solid #d2d2d2; padding: 6px 7px 7px;}
	.quick-order .bulk-add-sku input{margin-top: 10px;}
	.btn-l{ font-size: 14px; font-weight: bold; height: 30px; padding: 0 15px 2px;width:60px;height:30px;}
	.clr{clear: both;}
	.del{text-decoration: line-through;  line-height:10px; text-align:center}
	.ColorRed {color: #d01743 !important; }
	.sku{border: 1px solid #d2d2d2;}
	</style>
    <script type="text/javascript">
    //初始化总金额
	$(function(){  
		amountMoneySub();
	});
    //根据sku查询一条记录
    function changeSku(obj){
    	var $obj = $(obj).parent().parent();//找到当前input的上级td,以及td的上级tr
    	var sku = $.trim($(obj).attr("value"));
    	var useNum=$('input[class="sku"][value!=""]').length;
    	var allNum=$('input[class="sku"]').length;
    	var flag=true;
    	if(sku!=""&&sku!=null){
	    		 var skuSize=$("input[value='"+sku+"']").length+1;//获取当前sku的个数
	    		 //校验是否操作已有sku
	     		 var productId = $obj.find("td").eq(0).find("input").val();
	    		 if(productId!=""&&productId!="on"){//不为空说明 当前操作的是已有商品记录
	    			 skuSize--;
	    		 }
	    		 if(skuSize>1){
	    			 flag=false;
	    		 }
	    		 if(!flag){
					  lalert("该订货号已存在！");
					  $(obj).val("");
			          var html=""+
				      "<td class='prd-list-select'><input name='ordersInfo' type='checkbox'/></td>"+
				      "<td><input class='sku' type='text' onblur='changeSku(this)' value=''/></td>"+
				      "<td><div class='itemDiv'></div></td>"+
				      "<td><div></div></td>"+
				      //"<td><div></div></td>"+
				      "<td><input class='amount' maxlength='3' type='text' value=''/></td>"+
				      "<td><div></div></td>"+
				      "<td class='operation'><a onclick='remove_tr($(this));' href='javascript:;'>删除</a></td>";
				      $obj.html(html);
				      amountMoneySub();
	    		 }else{
   		    		$.ajax({
   			  			url:"${pageContext.request.contextPath}/front/quickOrder/getProductInfoBySku.action",
   			  			type:"post",
   			  			dataType:"json",
   			  			async : false,
   			  			data:{sku:sku},
   			  			success:function(data){
   			  				if(data.productInfo==null||data.productInfo==""){
   			  					lalert("不存在该订货号信息！");
   			  					$(obj).val("");
   			  				}else{
   			  					 var html=""+
   			  					 "<td class='prd-list-select'><input id='selectAll' class='check' name='ordersInfo' type='checkbox' value='"+data.productInfo.productId+"' onclick='doCheckBox()'/></td>"+
   			  					 "<td><input class='sku' type='text' onblur='changeSku(this)' value='"+data.productInfo.sku+"'/></td>"+
   			  					 "<td><div height='50px'><div  style=' width:50px; height:50px; float:left;'><a href='${pageContext.request.contextPath}/productInfo/"+data.productInfo.productId+".html' width='50px' height='50px'><img src='"+"<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>"+data.productInfo.logoImg+"' width='50px' height='50px'/></a></div><p style='height:50px; float:lef; width:400px; text-align:left;'><a href='${pageContext.request.contextPath}/productInfo/"+data.productInfo.productId+".html'>"+data.productInfo.productFullName+"</a></br>品牌："+data.brand.brandName+"</p></div></td>"+
   			  					 "<td>￥<b class='del'>"+data.productInfo.marketPrice.toFixed(2)+"</b><br/>￥<span class='ColorRed'>"+data.productInfo.salesPrice.toFixed(2)+"</span><font color='#fe4f00'>/"+data.productInfo.measuringUnitName+"</font></td>"+
   			  					 "<td><input class='amount' type='text' maxlength='3' onblur='changeCount(this)' value='1'/>"+data.productInfo.measuringUnitName+"</td>"+
   			  					 "<td>￥<span>"+data.productInfo.salesPrice.toFixed(2)+"</span></td>"+
   			  					 "<td style='display: none;'><input class='shopInfoId' type='hidden' value='"+data.productInfo.shopInfoId+"'/></td>"+
   			  					 "<td style='display: none;'><input class='stockUpDate' type='hidden' value='"+data.productInfo.stockUpDate+"'/></td>"+
   			  					 "<td class='operation'><a onclick='remove_tr($(this));' href='javascript:'>删除</a></td>";
   			  					 $obj.html(html);
   			  					 amountMoneySub();
   			  				}
   			  			}
   		  			});
   		        }
    	}
    }
    var cc = 0;
    //修改数量,计算总金额
    function changeCount(obj){
    	var total="";//总金额
    	var count = $(obj).attr("value").trim();
    	var reg = /^[1-9]\d*$/;
    	var flag = reg.test(count);
    	var $index = $(obj).parent().parent();//找到当前input的上级td,以及td的上级tr
    	if(!flag){
    		//lalert("请输入大于零的正整数！");
    		var skuvalue = $index.find("td").eq(1).find("input").val();
    		if(skuvalue!=""){
	    		if($(obj).next().html()==null){
		    		$(obj).after("<div class=\"cart-alarm\" style=\"\">数量在1-999之间</div>");
	    		}
	    		cc++;
	    		if(cc>0){
		    		$("#btnGoCart").attr("disabled","disabled");
	    		}
    		}else{
    			if(count!=""){
	    			if($(obj).next().html()==null){
			    		$(obj).after("<div class=\"cart-alarm\" style=\"\">数量在1-999之间</div>");
		    		}
    			}else{
    				$(obj).next().remove();
    			}
    		}
    	}else{
	    	var salesPrice = $index.find("td").eq(3).find("span").text();
	    	total = salesPrice*count;
	    	if(total!=null&&total!=""){
	    		total = $index.find("td").eq(5).find("span").html(total.toFixed(2));
	    	}	
	    	amountMoneySub();
	    	$(obj).next().remove();
	    	cc--;
	    	if(cc==0){
		    	$("#btnGoCart").removeAttr("disabled");
	    	}
    	}
    };
    //全选和反选
    function selectAllCheckBox(id){
	    var b = document.getElementsByName("ordersInfo");
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
        }
	}
   //点击一个checkbox的操作
   function doCheckBox(){
       //获取选中checkbox的长度
       var checkLength=$('input[name=ordersInfo]:checked').length;
       //获取所有checkbox的长度（除全选checkbox外）
       var totalLength=$('input[name=ordersInfo]').length;
       if(checkLength==totalLength){
            //选中全选checkbox
            $("#chkall").checked = true;
       }else{
            $("#chkall").checked = false;
       }
    }
	//删除一条记录
	function remove_tr(obj){
		var $index = obj.parent().parent();//找到当前input的上级td,以及td的上级tr
		var html=""+
			 "<td class='prd-list-select'><input name='ordersInfo' type='checkbox'/></td>"+
			 "<td><input class='sku' type='text' onblur='changeSku(this)' value=''/></td>"+
			 "<td><div class='itemDiv'></div></td>"+
			 "<td><div></div></td>"+
			 //"<td><div></div></td>"+
			 "<td><input class='amount' maxlength='3' type='text' value=''/></td>"+
			"<td><div></div></td>"+
			"<td class='operation'><a onclick='remove_tr($(this));' href='javascript:;'>删除</a></td>";
			$index.html(html);
			amountMoneySub();//统计总金额
	}
	//批量添加sku
	function save(){
		var oldSkus="";
		var skus = $.trim($("#txtNo").val());
		while(skus.indexOf("，")!=-1){//寻找每一个中文逗号，并替换
			var a =skus.replace("，",",");
            skus=a;
        }
		var skuArray=new Array();
		skuArray=skus.split(",");
		var newSkus="";
		if(skuArray!=""&&skuArray.length>0){
			for(var i=0;i<skuArray.length;i++){
				newSkus+="'"+skuArray[i]+"',";
			}
		}
		if(newSkus!=""){
			newSkus=newSkus.substring(0,newSkus.lastIndexOf(","));
		}
		 $(".sku[value!='']").each(function(){
			  var $index = $(this).parent().parent();//找到当前input的上级td,以及td的上级tr
		      var oldSku = $index.find("td").eq(1).find("input[class='sku']").val();
		      oldSkus+="'"+oldSku+"',";
		 });
		 newSkus=oldSkus+newSkus;
		 if(oldSkus!=""){
			 oldSkus=oldSkus.substring(0,oldSkus.lastIndexOf(","));
		}
		if(skus!=null&&skus!=""){
			$.ajax({
				url:"${pageContext.request.contextPath}/front/quickOrder/listProductInfoBySku.action",
				type:"post",
				dataType:"json",	
				async : false,
				data:{skus:newSkus},
				success:function(data){
					if(data!=null&&data!=""){
						var talNum=$('input[class="sku"]').length;
						for(var i=0;i<data.length;i++){
							var useNum=$('input[class="sku"][value!=""]').length;
							var canUseNum=Number(talNum)-Number(useNum);
							$("#qtable tbody tr ").each(function(){
								var index=$(this).index();
								if(Number(index)==Number(i)){
									var html = "";
									html+=""+
				  					 "<td class='prd-list-select'><input name='ordersInfo' class='check' type='checkbox' value='"+data[i].productId+"'/></td>"+
				  					 "<td><input class='sku' type='text' onblur='changeSku(this)' value='"+data[i].sku+"'/></td>"+
				  					 "<td><div height='50px'><div  style=' width:50px; height:50px; float:left;'><a href='${pageContext.request.contextPath}/productInfo/"+data[i].productId+".html'><img src='"+"<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>"+data[i].logoImg+"' width='50px' height='50px' /></a></div><p style='height:50px; float:lef; width:400px; text-align:left;'><a href='${pageContext.request.contextPath}/productInfo/"+data[i].productId+".html'>"+data[i].productFullName+"</a><br/>品牌："+data[i].brandName+"</p></div></td>"+
				  					 "<td><div>￥<b class='del'> "+data[i].marketPrice.toFixed(2)+"/"+data[i].measuringUnitName+"</b><br/>￥<span class='ColorRed'>"+data[i].salesPrice.toFixed(2)+"</span><font color='#fe4f00'>/"+data[i].measuringUnitName+"</font></div></td>"+
				  					 "<td><input class='amount' type='text' maxlength='3' value='1' onblur='changeCount(this)'/>"+data[i].measuringUnitName+"</td>"+
				  					 "<td><div>￥<span>"+data[i].salesPrice.toFixed(2)+"<span></div></td>"+
				  					 "<td class='operation'><a onclick='remove_tr($(this));' href='javascript:'>删除</a></td>";
				  					 if(Number(canUseNum)==Number(0)){
				  						 html += "<tr>"+
					  					 "<td class='prd-list-select'><input name='ordersInfo' class='check' type='checkbox' value='"+data[i].productId+"'/></td>"+
					  					 "<td><input class='sku' type='text' onblur='changeSku(this)' value='"+data[i].sku+"'/></td>"+
					  					 "<td><a href='${pageContext.request.contextPath}/productInfo/"+data[i].productId+".html'><img src='"+"<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/>"+data[i].logoImg+"' width='50px' height='50px' /></a><a href='${pageContext.request.contextPath}/productInfo/"+data[i].productId+".html'>"+data[i].productFullName+"</a><br/>品牌："+data[i].brandName+"</div></td>"+
					  					 "<td><div>￥<b class='del'> "+data[i].marketPrice.toFixed(2)+"/"+data[i].measuringUnitName+"</b><br/>￥<span class='ColorRed'>"+data[i].salesPrice.toFixed(2)+"</span><font color='#fe4f00'>/"+data[i].measuringUnitName+"</font></div></td>"+
					  					 "<td><input class='amount' type='text' maxlength='3' value='1' onblur='changeCount(this.value)'/>"+data[i].measuringUnitName+"</td>"+
					  					 "<td><div class='subtotalDiv'>￥"+data[i].salesPrice.toFixed(2)+"</div></td>"+
					  					 "<td class='operation'><a onclick='remove_tr($(this));' href='javascript:'>删除</a></td>"+
				  					 	 "</tr>";
				  						$("#qtable tbody").append(html);
				  					 }else{
					  					$(this).html(html);
				  					 }
								}
							});
						}
						$("#btnGoCart").removeAttr("disabled");
					}else{
						lalert("不存在该SKU订货号信息！");
						$("#btnGoCart").attr("disabled","disabled");
					}
					
				}
			});
		}
		amountMoneySub();//统计总金额
	}
	//加入购物车
	function addProToShopCart(){
		var productData="";
		var array="[";
		$(".sku[value!='']").each(function(){
	         var $index = $(this).parent().parent();//找到当前input的上级td,以及td的上级tr
	     	 var salesPrice = $index.find("td").eq(3).find("div").text();
	     	 var productId = $index.find("td").eq(0).find("input[class='check']").val();
	     	 var count = $index.find("td").eq(4).find("input[class='amount']").val();
	     	 var shopInfoId = $index.find("td").eq(6).find("input[class='shopInfoId']").val();
	     	 var stockUpDate = $index.find("td").eq(7).find("input[class='stockUpDate']").val();
	     	 array +="{'productId':'"+productId+"','num':'"+count+"','shopInfoId':'"+shopInfoId+"','stockUpDate':'"+stockUpDate+"','buyPrice':'"+salesPrice+"'},";
			 productData+=productId+"@"+count+",";
		});
		if(Number(array.indexOf(","))>Number(0)){
			array=array.substring(0, array.lastIndexOf(","));
		}
		array+="]";	
		if(Number(productData.indexOf(","))>Number(0)){
			productData=productData.substring(0,productData.lastIndexOf(","));
		}
		var type = '${session.customer.type}';//会员类型,如果是2,供应商就不能加入购物车
		var url ="${pageContext.request.contextPath}/loginCustomer/checkLogin.action";
		if(productData!=null&&productData!=""){
			if(type==2){
				lalert("提醒","由于您是供应商,不能加入购物车");
			}else{
				$.post(url,{},function(data){
					if (data.bool == true) {
						location.href="${pageContext.request.contextPath}/shopFront/shoppingCar/addProductsToShopCart.action?productData="+productData;
					}else{//用户未登录
						var cookieValue = $.cookie('customerCar');
						var value = "";
						if(cookieValue==null){
							value = ""+array+"";
						}else{
							var oldCookieValue = eval(cookieValue);//格式化之前cookie中的数据，以便与当前加入的数据做对比
							var newArray = eval(array);
							var commonProductIds="";
							if(oldCookieValue!=null&&oldCookieValue!=""){
								for(var j=0;j<newArray.length;j++){
									var flag=true;//之前cookie中的数据与现在加入的数据是否一样标记。默认true表示当前商品不在cookie中
									var numLast=0;
									for(var i=0;i<oldCookieValue.length;i++){//迭代之前cookie
										var oldNum =oldCookieValue[i].num;//当前cookie拿到的商品数量
										if(newArray[j].productId==oldCookieValue[i].productId){//判断当前操作的商品是否在之前的cookie中
											numLast = Number(oldNum)+Number(newArray[j].num);//如果存在数量累加
											flag=false;//设置标记
											//从新组装之前cookie数据
											value +=",{'productId':'"+oldCookieValue[i].productId+"','num':'"+numLast+"','shopInfoId':'"+oldCookieValue[i].shopInfoId+"','stockUpDate':'"+newArray[j].stockUpDate+"','buyPrice':'"+newArray[j].buyPrice+"','sku':'"+newArray[j].sku+"'}";//以json格式存放，方便维护和取
											if(commonProductIds==""){
												commonProductIds=",";
											}
											commonProductIds+=oldCookieValue[i].productId+",";
										}
									}
									if(flag){
										value +=",{'productId':'"+newArray[j].productId+"','num':'"+newArray[j].num+"','shopInfoId':'"+newArray[j].shopInfoId+"','stockUpDate':'"+newArray[j].stockUpDate+"','buyPrice':'"+newArray[j].buyPrice+"','sku':'"+newArray[j].sku+"'}";//以json格式存放，方便维护和取
									}
								}
								
							}
							for(var i=0;i<oldCookieValue.length;i++){//迭代之前cookie
								var p=","+oldCookieValue[i].productId+",";
								var ind=commonProductIds.indexOf(p)==-1;
								if(ind){
									value +=",{'productId':'"+oldCookieValue[i].productId+"','num':'"+oldCookieValue[i].num+"','shopInfoId':'"+oldCookieValue[i].shopInfoId+"','stockUpDate':'"+oldCookieValue[i].stockUpDate+"','buyPrice':'"+oldCookieValue[i].buyPrice+"','sku':'"+oldCookieValue[i].sku+"'}";//以json格式存放，方便维护和取
								}
							}
							
							//去掉value前面的“,”
							value = value.substring(1,value.length);
							value ="["+value+"]";
						}
						$.cookie('customerCar', value , {expires: 7, path:"/"}); //设置带时间的cookie 7天，加path属性时，只能其他页面使用，且其他页面取值条件为当前页面的上级路径包含path一致
						location.href="${pageContext.request.contextPath}/shopFront/shoppingCar/gotoShoppingCart.action";
					}
				},"JSON");
			}
		}else{
			lalert("提醒","请添加商品！");
		}
	}
    //统计金额和数量
    function amountMoneySub(){
    	var Subtotal=0;//销售总价格
    	$(".sku[value!='']").each(function(){
    		 var $index = $(this).parent().parent();//找到当前input的上级td,以及td的上级tr
 	     	 var salesPrice = $index.find("td").eq(3).find("span").text();
 	     	 var count = $index.find("td").eq(4).find("input[class='amount']").val();
 	     	 Subtotal = Number(salesPrice*count)+Number(Subtotal);
    	});
     	 Subtotal=Subtotal.toFixed(2); 
     	var regNum =/^((\d{1,3}(,\d{3})+?|\d+)(\.\d{2})?|(\.\d{2}))$/;
     	if(regNum.test(Subtotal)){
    		$("#totalamount").html(Subtotal);
     	}else{
     		$("#totalamount").html("0.00");
     	}
    }
    //继续添加商品，每次追加10条
    function accessories(){
    	var html="";
    	for(var i=0;i<10;i++){
		 	html += "<tr>"+
						 "<td class='prd-list-select'><input name='ordersInfo' type='checkbox'/></td>"+
						 "<td><input class='sku' onblur='changeSku(this)' type='text' value=''/></td>"+
						 "<td><div class='itemDiv'></div></td>"+
						 "<td><div class='priceDiv'></div></td>"+
						 //"<td class='assist-text'><div></div></td>"+
						 "<td><input class='amount' type='text' maxlength='3' value='' onblur='changeCount(this.value)'/></td>"+
						 "<td><div class='subtotalDiv'></div></td>"+
						 "<td style='display: none;'><input class='shopInfoId' type='hidden' value=''/></td>"+
	  					 "<td style='display: none;'><input class='stockUpDate' type='hidden' value=''/></td>"+
						 "<td class='operation'><a onclick='remove_tr($(this));' href='javascript:'>删除</a></td>"+
					"</tr>";
    	}
        $("#qtable tbody").append(html);
	}
    //删除选中商品
    function deleteTarget(id){
    	if(id=="all"){//删除选中的商品
    		var carts=$("input[class='check']:checked");
			var html=""+
					 "<td class='prd-list-select'><input class='check' name='ordersInfo' type='checkbox'/></td>"+
					 "<td><input class='sku' type='text' onblur='changeSku(this.value)'/></td>"+
					 "<td><div class='itemDiv'></div></td>"+
					 "<td><div class='priceDiv'></div></td>"+
					 //"<td class='assist-text'><div></div></td>"+
					 "<td><input class='amount' type='text' maxlength='3' onblur='changeCount(this.value)' value=''/></td>"+
					 "<td><div></div></td>"+
					 "<td style='display: none;'><input class='shopInfoId' type='hidden'/></td>"+
					 "<td style='display: none;'><input class='stockUpDate' type='hidden'/></td>"+
					 "<td class='operation'><a onclick='remove_tr($(this));' href='javascript:'>删除</a></td>";
	       		if(carts.length==0){
		    		/* $(".sku[value!='']").each(function(){
		    			for(var i=0;i<carts.length;i++){
		    				 if(carts[i].value!=""){
			    				if(carts[i].checked){//如果选中
			    					var index=$(this).index();
			    					var $index = $(this).parent().parent();//找到当前input的上级td,以及td的上级tr
			    					$index.html(html);
			    				}else{ */
			    					lalert("提醒","请选择要删除的商品！");
			    			/* 	}
			    			}
	    				}
		    		}); */
	       		}else{
	       			/* $(".sku[value!='']").each(function(){
	       	         var $index = $(this).parent().parent();//找到当前input的上级td,以及td的上级tr
	       	      		$index.html(html);
	       			}); */
	       			for(var i=0;i<carts.length;i++){
	    				 if(carts[i].value!=""){
	    					var $index = $(carts[i]).parent().parent();//找到当前input的上级td,以及td的上级tr
	    					$index.html(html);
		    			}
   					}
	       		}
//     		amountMoneySub();
    	}
    }
    
    //初始化的时候清空所有框的值
    $(function(){
    	$('input[class="sku"]').val("");
    	$('input[class="amount"]').val("");
    });
    
    </script>
  </head>
  
  <body>
    <%@ include file="../include/header.jsp"%>
    <div id="wrap">
    <div id="content" class="quick-order">
    	<table id="qtable" class="prdt-list table">
            <thead>
                <tr>
                    <td width="8%" align="center"><input id="chkall" name="ordersInfo" class="prd-select" type="checkbox" onclick="selectAllCheckBox(this.id)"/>全选</td>
                    <td width="9%" align="center">订货号</td>
                    <td width="46%" align="center">产品描述</td>
                    <td width="9%" align="center">含税单价</td>
                    <!-- <td width="8%">优惠</td> -->
                    <td width="12%" align="center">数量</td>
                    <td width="9%" align="center">小计</td>
                    <td width="8%" align="center">操作</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                   <!--  <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="prd-list-select"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
                <tr>
                    <td class="prd-list-select" align="center"><input name="ordersInfo" type="checkbox" onclick="doCheckBox()"/></td>
                    <td align="center"><input class="sku" type="text" value="" onblur="changeSku(this)"/></td>
                    <td align="center"><div class="itemDiv"></div></td>
                    <td align="center"><div class="priceDiv"></div></td>
                    <!-- <td class="assist-text"><div class="discountDiv"></div></td> -->
                    <td align="center">
                        <input class="amount" type="text" maxlength="3" onblur="changeCount(this)"/>
                    </td>
                    <td align="center"><div class="subtotalDiv"></div></td>
                    <td class="operation" align="center">
                   		<a href="javascript:;" onclick="remove_tr(this)">删除</a>
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="add-cart">
        	<div class="cart-opeart">
                <p class="cart-delete">
                	<a class="prominent-link" href="javascript:;" onclick="deleteTarget('all')">删除选中商品</a>
                </p>
                <input id="btnAddRow" class="btn-s-assist cart-go-on" type="button" onclick="accessories()" value="继续添加商品"/>
            </div>
            <div class="amount-total">
          		<p>
                    <span>总价(未含运费)：</span>
                        <span>
                        	￥<i class="totalamount" style="color:#d01743;"><span id="totalamount"></span></i>
                    	</span>
                </p>
                <div>
               		 <input id="btnGoCart" class="btn1 btn-xl" onclick="addProToShopCart();" type="button" value="去结算"/>
                </div>
            </div>
            <div class="bulk-add-sku">
                <p>批量添加订货号（依次填入订货号，并用“逗号”隔开）</p>
                <textarea id="txtNo" rows="4" cols="64" name="textarea"></textarea>
				<div>
               		 <input id="btnAddNo" class="btn1 btn-l" type="button" onclick="save();" value="添加"/>
                </div>
            </div>
            <div class="clr"></div> 
        </div>
    </div>
</div>
    <%@ include file="../include/footer.jsp"%>
  </body>
</html>
