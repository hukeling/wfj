<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>订单列表</title>
		<%@ include file="../include/head.jsp"%>
        <link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}css/themes/default/easyui.css"/>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.metadata.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMessage.js"></script>
		<script src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.validate_customerMethod.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/common.js"></script>
	<script>
		$(function() {
	 		$("#ordersSort").val($("#selectParams").val());
			$("#ordersStatus").val($("#selectStatus").val());
			var status = $("#selectStatus").val();
			
			$("#showSatus").html($("#status"+status).text());
			$("input[name='logisticsType']").removeAttr("disabled");
			$('#deliver-goods-overlay').css("display","none");
			//更改成已发货，填写相应信息
/* 			$('#deliver-goods-overlay').overlay({
				mask: {
			        color: '#ebecff',
			        loadSpeed: 200,
			        opacity: 0.4
		        }
			}); */
			
		/* 	$('.edit-deliver-goods').click(function(){
				$("#ordersId").val(this.id);
				$('#deliver-goods-overlay').overlay().load();
			}); */
			$("#deliver-goods-form").validate({meta: "validate", 
	    	      submitHandler:function(form){
	    	    	//var code $("#code").combogrid('getValue');
	    	    	var code = $("#code").combogrid('getValue');
	    	    	var cityCourier = $("#cityCourier").combogrid('getValue');
	    	    	var r1=document.getElementById("logisticsType_1");
	    	    	var r2=document.getElementById("logisticsType_2");
    	    		$("#codeError").val("");
	    	    	if(r1.checked){
	    	    		if(code[0]==null||code[0]==""){
		    	    		$("#codeError").html("<font color='red'>不可为空</font>");
		    	    	}else{
							form.submit();
		    	    	}
	    	    	}else{
	    	    		if(cityCourier[0]==null||cityCourier[0]==""){
		    	    		$("#cityCourierError").html("<font color='red'>不可为空</font>");
		    	    	}else{
							form.submit();
		    	    	}
	    	    	}
	    	      }
			});
		});
	function changeSend(ordersId,type){
		$("#overly_div").attr("style","opacity: 0.4;");
		$("#ordersId").val(ordersId);
		$('#deliver-goods-overlay').css("display","");
		$("#logisticsType_"+type).attr("checked",true);
		$('#type1').css("display","none");
		$('#type2').css("display","none");
		$('#type2_1').css("display","none");
		$("#logisticsType_"+type).click();
		$("input[name='logisticsType']").attr("disabled",true);
	}
		//更改成正在配货
		function changeOrderState(ordersId){
			window.location = "${pageContext.request.contextPath}/front/store/shopOrder/changgeOrdersState.action?orders.ordersId="+ ordersId+"&currentPage=${currentPage}";
		}
		//根据条件排列订单
		/*function ordersSort(value) {
				window.location = "${pageContext.request.contextPath}/front/store/shopOrder/shopOrderList.action?params="+ value;
		}
	
		//根据状态查找订单
		function ordersStatus(value){
		 	window.location="${pageContext.request.contextPath}/front/store/shopOrder/shopOrderList.action?orders.ordersState="+value;
		}*/
		
		//选择快递公司的同时，回填网址信息
		var urlInfo=function(){
			//获取select控件的指
			var svalue=$("#sdeliveryCorpName").val();
			//与数据字典中快递公司list做比较
			<s:iterator value="#application.keybook['expressCompany']" var="kb">
		           if(svalue=="<s:property value='#kb.name'/>"){
		                   $("#deliveryUrl").val("<s:property value='#kb.value'/>");
		           }
			</s:iterator>
		};
		//全选和反选
		function selectAllCheckBox(id) {
			var b = document.getElementsByName("ordersInfo");
			var all = document.getElementById(id);
			if (all != null) {
				if (all.checked) {
					for (var i = 0; i < b.length; i++) {
						b[i].checked = true;
					}
				} else {
					for (i = 0; i < b.length; i++) {
						b[i].checked = false;
					}
				}
			}
		}
		//点击一个checkbox的操作
		function doCheckBox() {
			//获取选中checkbox的长度
			var checkLength = $('input[name=ordersInfo]:checked').length;
			//获取所有checkbox的长度（除全选checkbox外）
			var totalLength = $('input[name=ordersInfo]').length;
			if (checkLength == totalLength) {
				//选中全选checkbox
				$("#selectAll").attr("checked", true);
			} else {
				$("#selectAll").removeAttr("checked");
			}
		}
		function dc(id) {
			//循环组拼选中的id数组
			$(".checkboxClass").each(function() {
				if (this.checked) {
					id += "," + this.value;
				}
			});
			//组拼订单id串
			id = id.substring(1, id.length);
			if(id==""){
				lalert("提醒","请选择要导出的订单");
			}else{
				$.ajax({
					type: "POST",
					dataType: "JSON",
					url : "${pageContext.request.contextPath}/front/store/shopOrder/importExcelDP.action",
					data: {ids:id},
					success:function(data){
						if(data.isSuccess=="true"){
							var url=data.filePathXLS;
							window.location.href="${pageContext.request.contextPath}/downloadFile.action?downloadFileUrl="+url+"&downloadFileName="+data.downloadFileName;
						}else{
							lalert("提醒","导出失败点击确定关闭");
						}
					}
				});
			}
		} 
		
		$(function(){
			//初始化物流公司信息
			$('#code').combogrid({
				idField:'code',
				textField:'deliveryCorpName',
				rownumbers:true,//序号
				width:300,
				url:'${pageContext.request.contextPath}/back/logistics/initLogistics.action',
				columns:[[
					{field:'deliveryCorpName',title:'物流公司名称',width:100},
					{field:'deliveryUrl',title:'物流公司网址',width:150}
				]]
			});
		});
		function close_div(){
			$("#deliver-goods-overlay").css("display","none");
			$("#overly_div").removeAttr("style");
		}
		function choice(value){
			if(value==1){
				$('#type1').css("display","");
				$('#type2').css("display","none");
				$('#type2_1').css("display","none");
			}else{
				$('#type1').css("display","none");
				$('#type2').css("display","");
				$("#province").val(null);
				$("#cities").val(null);
				$("#district").val(null);
				$('#type2_1').css("display","none");
			}
		}
		function chengeArea(id,level){
			if(level==3){
				var province=$("#province").val();
				var city=$("#cities").val();
				var district=$("#district").val();
				var responsibleAreas=province+","+city+","+district;
				//初始化同城快递信息
		   		$('#cityCourier').combogrid({
					idField:'cityCourierId',
					textField:'cityCourierName',
					rownumbers:true,//序号
					width:300,
					url:'${pageContext.request.contextPath}/back/logistics/initCityCourier.action',
					queryParams :{id : responsibleAreas},
					columns:[[
						{field:'cityCourierName',title:'快递员姓名',width:80},
						{field:'responsibleAreas',title:'负责区域',width:150},
						{field:'phone',title:'联系电话',width:100}
					]]
				});
				$('#type2_1').css("display","");
			}else{
				$.ajax({
					url:"${pageContext.request.contextPath}/front/store/shopOrder/findCityList.action",
					type:"post",
					dataType:"json",
					data:{areaId:id},
					success:function(data){
						if(data!=""){
							var areaList=data;
							var htmlOption="<option value=''>--请选择--</option>";
							for(var i=0;i<areaList.length;i++){
								htmlOption+="<option  value='" + areaList[i].areaId+"'>" + areaList[i].name+ "</option>";
							}
							if(level==1){
								$("#cities").html(htmlOption);
							}if(level==2){
								$("#district").html(htmlOption);
							}
						}
					}
					});
			}
		}
		function removeAttrRadioDisplay(){
			$("input[name='logisticsType']").removeAttr("disabled");
		}
</script>
	</head>

	<body>
	<div id="deliver-goods-overlay" style="width: 400px;height: 300px; background-color: #fff;border: 2px solid #82bffe; border-radius: 5px; padding: 15px; position: absolute;top:376px; left:40%; z-index:9; display:none;">
					<form onsubmit="return removeAttrRadioDisplay();" method="post" id="deliver-goods-form" action="${pageContext.request.contextPath}/front/store/shopOrder/changeShipments.action">
						<h2 class="modal-title" style="text-align:center;height:30px">
							<input id="logisticsType_1" type="radio" name="logisticsType" value="1" onclick="choice(this.value)"/>物流公司信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input id="logisticsType_2" type="radio" name="logisticsType" value="2" onclick="choice(this.value)"/>同城配送信息
						</h2>
						<input type="hidden" id="ordersId" name="orders.ordersId" value=""/>
						<input type="hidden" name="currentPage" value="${currentPage }"/>
						<table id="type1">
							<tr>
								<td style="text-align: right">物流单号:</td>
								<td><input style="border: 1px #ccc solid;" type="text" name="shipping.deliverySn" value="" class="{validate:{required:true,onlyCharAndNumber:true}}"/></td>
							</tr>
							
							<tr>
								<td style="text-align: right">物流公司名称:</td>
								<td colspan="3">
								<select id="code" name="shipping.code" class="{validate:{required:true}}"></select><span id="codeError"></span>
								</td>
							</tr>
						</table>
						
						<table id="type2">
							<tr>
								<td style="text-align: right">区域:</td>
								<td>
									<select id="province" onchange="chengeArea(this.value,'1')">
										<option value="">省</option>
										<s:iterator value="provinceList" var="first">
											<option  value="<s:property value="#first.areaId"/>"><s:property value="#first.name"/></option>
										</s:iterator>
									</select>
									<select id="cities" onchange="chengeArea(this.value,'2')">
										<option value="">地级市</option>
									</select>
									<select id="district" onchange="chengeArea(this.value,'3')">
										<option value="">区(县)</option>
									</select>
								</td>
							</tr>
							<tr id="type2_1">
								<td style="text-align: right">快递信息:</td>
								<td colspan="3">
								<select id="cityCourier" name="cityCourier.cityCourierId" class="{validate:{required:true}}"></select><span id="cityCourierError"></span>
								</td>
							</tr>
						</table>
						 <div style="width:250px; height:auto; margin-top:25px; padding-left:80px;">
							<p class="modal-buttons">
								<button style="border: 0; cursor: pointer; border: 1px #77235b solid;" type="submit">提交</button>
								<button style="border: 0; cursor: pointer; border: 1px #77235b solid; border-radius: 3px; color: #fff;
 font-size: 16px; font-weight: bold; height: 30px; line-height: 20px;margin-left: 50px;text-align: center;width: 60px; background-color: #077df8;" onclick="close_div()" type="button">关闭</button>
							</p>
							
						</div>
					</form>
					
				</div>
		<%@ include file="../include/header.jsp"%>
		<div id="overly_div" class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">订单列表</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/store/shopOrder/shopOrderList.action" method="post">
					<div class="shop_orderSearch ClearBoth" style="padding-bottom:5px;">
						<p>
							<input id="selectParams" type="hidden" value="<s:property value='params'/>" />
							<span style="font-weight: 100;">排序 :</span>
							<select id="ordersSort" name="params" onchange="changePage('${pageHelper.prePageIndex}');" style="height:22px; line-height: 22px; border:0; border:1px #ccc solid;">
								<option id="date" value="createTime">订单时间</option>
								<option id="orderId" value="ordersNo">订单号</option>
								<option id="amount" value="finalAmount">支付金额</option>
							</select>
						</p>
	
						<p> <input type="hidden" id="selectStatus" value="${orders.ordersState}"/>
							<span style="font-weight: 100;">订单状态:</span>
							<select id="ordersStatus" name="orders.ordersState" onchange="changePage('1');" style="height:22px; line-height: 22px; border:0; border:1px #ccc solid;">
								<option id="status" value="">全部</option>
								<option id="status1" value="99">生成订单</option>   
								<option id="status1" value="0">未付款</option>   
								<option id="status2" value="1">已付款</option> 
								<option id="status3" value="3">已配货</option>  
								<option id="status4" value="4">已发货</option> 
								<option id="status5" value="5">完成</option>
								<option id="status6" value="6">订单取消</option> 
<!-- 								<option id="status7" value="7">问题订单</option>   -->
								<option id="status9" value="9">已评价</option>  
							</select>
						</p>
					
					<aside class="accountOrder_search ClearBoth publicMarginBot15">
						<div class="FontSize14" style="line-height:24px;">
							订单号:
						</div>
						<div>
							<input name="orders.ordersNo" style="height:22px; line-height: 22px; border:0; border:1px #ccc solid;" type="text" value="${orders.ordersNo}"/>
						</div>
						<div>
							<input type="submit" value="" onclick="changePage('1');"
								class="publicNoBorder ColorWhite1 FontSize14" style="cursor: pointer; height:22px; width:42px; display:block; background:url(${fileUrlConfig.fileServiceUploadRoot}shop/front/images/chaxun.jpg)"/>
						</div>
						<!-- <div class="float-right publicHidden">
									<input type="button" onclick=" dc('');" href="javascript:;"
										class="publicNoBorder BackgroundPurple ColorWhite1 FontSize14"
										style="padding: 2px 5px;margin-left: 1px;" value="导出" />
							</div> -->
					</div>
					<!--${pageContext.request.contextPath}/front/store/shopOrder/shopOrderList.action     ${pageContext.request.contextPath}/shopOrder/noDelivery.action-->
					
					</aside>
					
					<div class="clear"></div>
					<div class="TabParent_lj ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrdersTable_lj">
								<tr class="gradient publicBorder">
									<td width="5%">
									<input id="selectAll" style="margin-top:14px;" class="checkboxClass2" type="checkbox" name="ordersInfo" onclick="selectAllCheckBox(this.id)" value=""/><span class="selectAll2"></span></td>
									<td width="18%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;">
										订单号
									</td>
									<td width="14%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										日期
									</td>
									<td width="14%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										买家
									</td>
									<td width="12%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										金额
									</td>
									<td width="10%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										订单状态
									</td>
									<td width="10%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										付款状态
									</td>
									<td width="12%" style="border-bottom: 1px #d2d2d2 solid; border-top: 1px #d2d2d2 solid;">
										操作
									</td>
								</tr>

								<s:iterator value="productInfoList">
									<tr>
										<td>
										<input class="checkboxClass"  style="margin-top:14px;" name="ordersInfo" onclick="doCheckBox()" id="checkbox_<s:property value="ordersId" />" type="checkbox" value="<s:property value="ordersId" />"/></td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="ordersNo" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:property value="customerName" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											￥<fmt:formatNumber value="${finalAmount}" pattern="#,##0.00#" />
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:if test="ordersState == 0">生成订单</s:if>
											<s:elseif test="ordersState == 1">已付款</s:elseif>
											<s:elseif test="ordersState == 3">已配货</s:elseif>
											<s:elseif test="ordersState == 4">已发货</s:elseif>
											<s:elseif test="ordersState == 5">完成</s:elseif>
											<s:elseif test="ordersState == 6">订单已取消</s:elseif>
											<s:elseif test="ordersState == 9">已评价</s:elseif>
										</td>
										<td style="border-bottom: 1px #d2d2d2 solid;">
											<s:if test="settlementStatus == 0">未付款</s:if>
											<s:elseif test="settlementStatus == 1">已付款</s:elseif>
										</td>
										<td class="line-height2 lastR">
											<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderInfo.action?orders.ordersId=${ordersId}&orders.customerId=${customerId}"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center;">订单明细</a>
											<a href="${pageContext.request.contextPath }/front/store/shopOrder/shopOrderPrint.action?orders.ordersId=${ordersId}&orders.customerId=${customerId}"
												class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius" style="text-align: center;" target="_blank" >打印</a>
											<s:if test="sendType!=3">
												<s:if test="ordersState == 0&&settlementStatus==1">
													<a href="javascript:;" onclick="changeOrderState(<s:property value="ordersId" />);" class="ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius">更改为已配货</a>
												</s:if>
												<s:if test="ordersState == 3&&settlementStatus==1">
													<a href="javascript:changeSend('${ordersId}','${sendType}');" id="${ordersId}"  class="edit-deliver-goods ROButton publicblock BackgroundPurple ColorWhite1 FontSize16 radius">更改为已发货</a>
												</s:if>
											</s:if>
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
				<!--//right-->
			</div>
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
