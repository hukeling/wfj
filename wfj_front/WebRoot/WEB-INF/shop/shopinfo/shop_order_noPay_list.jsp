<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>未结算订单列表</title>
		<%@ include file="../include/head.jsp"%>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
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
	               for(i=0;i<b.length;i++){
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
  	            $("#selectAll").attr("checked",true);
  	       }else{
  	            $("#selectAll").removeAttr("checked");
  	       }
  	    }
		//循环组拼选中的id数组
		function apply(id){
			var checkCount=$(".checkboxClass:checked").length;
			if(Number(checkCount)>Number(0)){
				$(".checkboxClass").each(function(){
					if(this.checked){
						id+=","+this.value;
					}
				});
	// 			组拼订单id串
				if(id==""){
					lalert("提醒","请选择未结算订单!");
				}else{
					id=id.substring(1,id.length);
					$.ajax({
			  			url:"${pageContext.request.contextPath}/front/store/shopOrder/updateOrdersState.action",
			  			type:"post",
			  			dataType:"json",
			  			data:{ids:id},
			  			success:function(data){
			  				if(data){
			  					if(!data.apply){
				  					lalert("提醒","每日只能申请一次，今日已申请!");
			  					}else{
					  				window.location.href=window.location.href;
			  					}
			  				}
			  			}
		  			});
				}
			}else{
				lalert("提醒","请选择未结算订单!");
			}
		}
		</script>
	</head>

	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">未结算订单列表</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/shopOrder/shopNoPayOrderList.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:10px; border-bottom: 1px #ccc dotted;">
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div class="FontSize14" style="line-height:24px;">
							起始日期:
						</div>
						<div>
							<input id="stime" style="width: 150px; height:22px; border:0; border:1px #ccc solid;" name="startTime"  type="text"  
						    onclick="WdatePicker({lang:'cn', maxDate:'%y-%M-{%d-15}'})"  value="${startTime}" onfocus="if(this.value==this.defaultValue){this.value='';}"/>
						</div>
						<div class="FontSize14" style="line-height:24px;">
							结束日期:
						</div>
						<div>
							<input id="etime" style="width: 150px;height:22px; border:0; border:1px #ccc solid;" type="text" name="endTime" 
						    onclick="WdatePicker({minDate:'#F{$dp.$D(\'stime\',{d:0})}',maxDate:'%y-%M-{%d-15}',lang:'cn'})" value="${endTime}" onfocus="if(this.value==this.defaultValue){this.value='';}"/>
						</div>
						<div>
							<input type="submit" value="" onclick="changePage('${pageHelper.prePageIndex}');"
								class="publicNoBorder ColorWhite1 FontSize14" style=" cursor: pointer;  height:22px; width:42px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/chaxun.jpg) no-repeat;"/>
						</div>
						<div>
						<input type="button"onclick="apply('');" class="publicNoBorder ColorWhite1 FontSize14" style="cursor: pointer; cursor: pointer;  height:22px; width:68px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/sqjs.jpg) no-repeat;"/>
						</div>
					</aside>
					</div>
					<div class="clear"></div>
					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table style="width: 100%;border: 0;" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td style="width: 10%" style="border-bottom: 1px #d2d2d2 solid;" >
										<input id="selectAll" style="margin-top: 15px;" type="checkbox" name="ordersInfo" onclick="selectAllCheckBox(this.id)" value=""/><span class="selectAll2"></span>
									</td>
									<td style="width: 18%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;">
										订单号
									</td>
									<td style="width: 14%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										未结算金额
									</td>
									<td style="width: 17%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										收货时间
									</td>
									<td style="width: 20%;" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										结算状态
									</td>
								</tr>
								<s:iterator value="productInfoList">
									<tr>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<input style="margin-top: 15px;" class="checkboxClass" name="ordersInfo" onclick="doCheckBox()" id="checkbox_<s:property value="ordersId" />" type="checkbox" value="<s:property value="ordersId" />"/>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:property value="ordersNo" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											￥<fmt:formatNumber value="${finalAmount}" pattern="#,##0.00#" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											<s:date name="operatorTime" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;  border-left:1px #ccc solid;">
											未结算
										</td>
									</tr>
								</s:iterator>
							</table>
							<!--分页 start-->
							<div class="pageList strong ClearBoth">
								<jsp:include page="../../util/splitPage.jsp" />
							</div>
							<!--分页 end-->
						</div>
					</div>
				</form>
				</div>
				<!--//right-->
			</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
