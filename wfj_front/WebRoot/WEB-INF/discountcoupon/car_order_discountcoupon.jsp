<%@ page language="java" contentType="text/html; charset=UTF-8"%>
	<style type="text/css">
		#yOrder a:hover{text-decoration:none;}
	</style>
	
	<script type="text/javascript">
	//初始化或者刷新页面时，在使用了西城商道币抵扣显示抵扣金额和使用的西城商道币数量
	$(function(){
		okcoupon(0);
		$("#p_total_Coupon").css("display","none");
	});
	//使用西城商道优惠券兑换
	function changeCoupon(obj){
		if(obj=="plusCoupon"){//图片为‘+’号时不显示输入框
			$("#minusCoupon").css("display","");
			$("#plusCoupon").css("display","none");
		}else{//图片为'-'时显示下方输入框
			$("#plusCoupon").css("display","");
			$("#minusCoupon").css("display","none");
			$("#errorMsgCoupon").html("");
		}
	}
	
	function okcoupon(ma,discountCouponLowAmount){
		$("#okcoupon").val(ma);
		var finalAmount = $("#finalAmount-des").val();
		var finalAmounts = $("#amount").val();
		$("#mydiscountCouponAmount").val(ma);
		var hb = $("#changeAmount").val();
// 		alert(finalAmounts+"ssss"+discountCouponLowAmount);
		if(finalAmounts<discountCouponLowAmount){
			$("#qlockedState").val(0);
			lalert('消息提醒', '订单总金额没有满足优惠券使用金额，无法使用!');
		}else{
			if(hb!=""&&hb!=null){
				finalAmount = Number(finalAmount)-Number(hb)-Number(ma);//总金额去掉抵扣金额	
			}else{
				finalAmount = Number(finalAmount)-Number(ma);
			}
			if(ma==0){
			$("#p_total_Coupon").css("display","none");//
			}else{
			$("#p_total_Coupon").css("display","");//显示抵扣金额
			}
			
			$("#finalAmount").val(finalAmount.toFixed(2));
			$("#isUseCoupon").val("1");
			$("#total_Coupon").html("￥"+Number(ma).toFixed(2));
			$("#subPrice").html(finalAmount.toFixed(2));
			
			allPrice();//计算价格
		}
	}
</script>