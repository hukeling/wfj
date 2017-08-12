<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请退货</title>
<%@ include file="../include/head.jsp"%>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script> 
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
<script type="text/javascript">
	//提交数量的加减操作
	function applyNumOperate(type){
		var v=$("#count-th").val();//提交数量
		var c='${ordersList.count}';//购买数量
		var ttc='${countTatol}';//已退货数量
		var ccc=c-ttc;//还可以退货数
		if(type==1){
			if(v>1){
				v--;
				$("#count-th").val(v);
			}else{
				lalert("提醒","可提交数量不得小于1！");
				$("#count-th").val(1);
			}
		}else{
			v++;
			if(v>ccc){
				lalert("提醒","可提交数量不能超过"+ccc+"！");
				$("#count-th").val(ccc);
			}else{
				$("#count-th").val(v);
			}
		}
	}

	//提交数量——失去焦点校验
	var flag = true;
	function valueBlur(value){
		flag = true;
		var v = value.trim();
		var c='${ordersList.count}';//购买数量
		var ttc='${countTatol}';//已退货数量
		var ccc=c-ttc;//还可以退货数
		var a=/^[1-9]*[1-9][0-9]*$/;
		if(!a.test(v)){
			lalert("提醒","只能输入大于零的正整数！");
			$("#count-th").val(1);
			amount=1;
// 			flag = false;
		}else{
			if(parseInt(v)>parseInt(ccc)){
				lalert("提醒","可提交数量不能大于商品购买数！");
				$("#count-th").val(1);
			}
// 			flag = false;
		}
	}
	
	$(function(){//表单验证
   		//表单验证
     	$("#returnsApplyForm").validate({meta: "validate", 
     		submitHandler:function(form){
     			if(flag){
     				var b = document.getElementsByName("listProductUploadImgs");
	   	    		if(b!=null&&b!=""){
		   	    	    for(var i=0;i<b.length;i++){
		                   var a = b[i].value;
		                   var array = new Array();
		       			   array = a.split(".");
		       			   var extname = array[1].toLowerCase();//转换大小写
		       			   if(extname!="bmp"&&extname!="jpg"&&extname!="gif"&&extname!="jpeg"&&extname!="png"){
		       				   lalert("提示","只能上传bmp,gif,jpg,jpeg,png格式的图片");
		       				   return;
		       			   }
		                }
	   	    		}
	     			//提交订单锁定提交按钮
	     			$("#tijiaoButton").attr("disabled",true);
	     			form.submit();
     			}
     		}
   		});
	});
</script>
</head>

<body>
<%@ include file="../include/header.jsp"%>

<div class="margin-center PublicWidth1004">
<article class="publicrelative publicMarginBot15 publicPaddingB30 ClearBoth">
<h1 class="FontSize16 publicPadding10 publicBorderB FontSizeB">申请售后服务</h1>
<br/>
<!-- 规则说明 -->
<div style="border: 1px solid #EDD28B;background: none repeat scroll 0 0 #FFFDEE;">
	<div style="margin-top: 8px;margin-left: 10px;">
			<h5 style="font-size: 13px;">售后服务规则</h5>
	</div>
	<div style="display: block;margin-left: 25px;margin-right: 25px;margin-top: 15px;margin-bottom: 15px;">
		1.由您实际收到货物日期起7日内出现质量问题可退货。 <br/>
		2.由您实际收到货物日期起15日以上，为了不耽误您使用，缩短故障商品的维修时间，建议您在商品出现故障时直接联系当地厂商维修网点处理。请访问厂商官方网站或拨打厂商服务热线，咨询维修网点信息，凭借发票可到网点维修。国家三包规定保修期内，无品牌厂商售后服务产品，SHOPJSP提供免费维修服务。<br/>
		3.在商品无任何问题情况下，SHOPJSP承诺：由您实际收到货物日期起7日内，只要未使用不影响二次销售，都可以全额退货。SHOPJSP所售均为全新品，为保护消费者利益，以下情况视为影响二次销售：<br/>
		1）大家电类如冰箱、电视、洗衣机、空调、热水器等非产品本身问题概不接受退换，请谨慎下单； <br/>
		2）密封产品原包装打开； <br/>
		3）产品通电、过水、插入卡槽等已使用； <br/>
		4）钻石、黄金、手表、奢侈品、珠宝首饰及个人配饰类产品不接受退货。 <br/>
		注：<span style="color: #FF6600;">博世电动工具不在国家三包范围之列 出现质量问题可直接联系厂家维修 卡西欧电子琴需凭借厂家检测报告进行鉴定</span>
	</div>
</div>
<br/>
<!-- 商品信息 -->
<section class="publicMarginTop30">
	<table width="100%" border="0" class="Sales_after">
	  <tr class=" publicBorder gradient">
	    <td width="20%" style="border-left: 1px #d2d2d2 solid;border-top: 1px #d2d2d2 solid;border-bottom: 1px #d2d2d2 solid;">订单号</td>
	    <td width="20%" style="border-top: 1px #d2d2d2 solid;border-bottom: 1px #d2d2d2 solid;">商品名称</td>
	    <td width="20%" style="border-top: 1px #d2d2d2 solid;border-bottom: 1px #d2d2d2 solid;">店铺名称</td>
	    <td width="10%" style="border-top: 1px #d2d2d2 solid;border-bottom: 1px #d2d2d2 solid;">商品价格</td>
	    <td width="10%" style="border-top: 1px #d2d2d2 solid;border-bottom: 1px #d2d2d2 solid;border-right: 1px #d2d2d2 solid;">购买数量</td>
	  </tr>
	  <tr class="checkbox_tr">
	    <td><s:property value="orders.ordersNo"/></td>
	    <td class="line-height150 text-left">
	    	<a href=""><img title="<s:property value='ordersList.productFullName'/>" style="height: 50px;width: 50px;" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot}'/><s:property value='ordersList.logoImage'/>"/></a><br>
	    </td>
	    <td><s:property value="ordersList.shopName"/></td>
	    <td>￥<s:property value="ordersList.salesPrice"/></td>
	    <td><s:property value="ordersList.count"/></td>
	  </tr>
	</table>
</section>

<!-- 服务单明细 -->
<aside class="parent_for_p publicMarginTop15">
	<form id="returnsApplyForm" action="${pageContext.request.contextPath}/front/customer/frontCustomerComplaints/saveReturnApply.action" method="post" enctype="multipart/form-data">
	
		<input name="returnsApply.shopInfoId" type="hidden" value="<s:property value="ordersList.shopInfoId"/>"/>
		<input name="returnsApply.productId" type="hidden" value="<s:property value="ordersList.productId"/>"/>
		<input name="returnsApply.ordersId" type="hidden" value="<s:property value="orders.ordersId"/>"/>
	
		<fieldset class="publicPadding10 pBorderY_e1db66">
		<legend class="publicMargin10"><h2 style="padding:5px 0 0 0;">服务单明细：</h2></legend>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed">*</span>服务类型:</p>
			<p><input checked="checked" type="radio" name="returnsApply.disposeMode" value="3"/>&nbsp;&nbsp;退货</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed">*</span>提交数量:</p>
			<p>
				<a style="margin-right: 5px;text-decoration: none;" href="javascript:applyNumOperate(1);" name="applyNum_operate_add">
					<img style="max-height: 25px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/orders/del_16.png"/>
				</a>
				<input onblur="valueBlur(this.value)" id="count-th" name="returnsApply.count" type="text" value="1" size="1" style="text-align: center;"/>
				<a style="margin-left: 5px;text-decoration: none;" href="javascript:applyNumOperate(2);" name="applyNum_operate_add">
					<img style="max-height: 25px;" src="${fileUrlConfig.fileServiceUploadRoot}shop/front/images/orders/add_16.png"/>
				</a>
				&nbsp;&nbsp;
				<s:if test="count>countTatol">
				<font color="red" style="margin-top: 5px;">(已退货数量：${countTatol})</font>
				</s:if>
			</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed">*</span>问题描述:</p>
				<p>
					<textarea name="returnsApply.problemDescription" cols="70" rows="5" wrap="virtual" class="{validate:{required:true,maxlength:[500]}}"></textarea>
				</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed"></span></p>
				<p style="background: none repeat scroll 0 0 #F5F5F5;border: 1px solid #D8D8D8;color: #666666; padding: 2px 6px;margin-left: 10px;">请您如实填写申请原因及商品情况，字数在500字内。</p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed"></span></p>
			<p style="margin-left: 10px;">为了帮助我们更好的解决问题，请您上传图片</p>
			<p style="margin-left: 120px;"><input class="publicPadding5_10 BackgroundOrange FontSize13 FontSizeB ColorWhite1 pBorderY_e1db66 publicMarginR10" id="addProductImage" type="button" value="上传图片"/></p>
		</div>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed"></span></p>
			<p style="margin-left: 10px;" class="ColorQHui" >最多可上传5张图片，每张图片大小不超过5M，支持bmp,gif,jpg,png,jpeg格式文件</p>
		</div>
		<div id="psShowBox" class="ClearBoth" style="display: none;">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed"></span></p>
			<div style="margin-left: 10px;">
				<table id="productImageTable">
					<tr><td>图片</td><td>操作</td></tr>
				</table>
			</div>
		</div>
		<script type="text/javascript">
			$(function() {
			    var productImageIndex = 0;
			    var $addProductImage = $("#addProductImage");
			    var $deleteProductImage = $("a.deleteProductImage");
			    var $productImageTable = $("#productImageTable");
			    // 增加商品图片
			    $addProductImage.click(function() {
				    var l=$("input[type='file']").length;
			    	if(l>4){
			    		lalert("提醒","最多可上传5张图片！");
			    	}else{
				    	$("#psShowBox").css("display","");//展示
				        var trHtml = '<tr> <td> <input type="file" name="listProductUploadImgs" class="productImageFile" \/> <\/td><td> <a href="javascript:;" class="deleteProductImage">[删除]<\/a> <\/td> <\/tr>';
				        $productImageTable.append(trHtml);
				        productImageIndex++;
			    	}
			    });
			
			    // 删除商品图片
			    $deleteProductImage.live("click",function() {
			        var $this = $(this);
			        $this.closest("tr").remove();
			        var l2=$("input[type='file']").length;
			        if(l2==0){
			        	$("#psShowBox").css("display","none");//隐藏
			        }
			    });
			});
		</script>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed">*</span>商品返回方式:</p>
			<p><input checked="true" type="radio"/>&nbsp;&nbsp;快递至商家</p>
		</div>
		<%--<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed">*</span>收货地址:</p>
			<p><input name="returnsApply.shippingAddress" type="text" value="" class="{validate:{required:true,maxlength:[150]}}"></p>
		</div> --%>
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed">*</span>联系人姓名:</p>
			<p><input name="returnsApply.linkman" type="text" value="<s:property value='orders.consignee'/>" class="{validate:{required:true,maxlength:[25]}}"/></p>
		</div> 
		<div class="ClearBoth">
			<p class="widthpx150 text-right FontSize13"><span class="ColorRed">*</span>手机号码:</p>
			<p><input name="returnsApply.mobilePhone" type="text" value="<s:property value='orders.mobilePhone'/>" class="{validate:{required:true,mobile:true}}"/></p>
		</div>
		<div>
			<p class="widthpx150 "></p>
			<p>
				<input id="tijiaoButton" type="submit" value="提交" class="publicPadding5_10 BackgroundOrange FontSize13 FontSizeB ColorWhite1 pBorderY_e1db66 publicMarginR10"/>
			</p>
		</div>
		</fieldset>
	</form>
</aside>
</article>
</div>
<%@ include file="../include/footer.jsp"%>
</body>
</html>
