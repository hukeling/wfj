<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>系统消息</title>
		<%@ include file="../include/head.jsp"%>
		<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
			<STYLE type="text/css">
			.RecentlyOrders_table tr{
				font-size: 13px;
				line-height: 3em;
			}
			.RecentlyOrders_table{
				table-layout:fixed;
			}
			.RecentlyOrders_table td{
				text-overflow: ellipsis;word-break:keep-all;white-space: nowrap;overflow:hidden;
			}
		</STYLE>
	</head>
<SCRIPT type="text/javascript">
$(function(){
	//将未读的消息颜色加深
	$("table tr").each(function(){
		if($(this).find("td").eq(1).text()=='未读'){
			$(this).css('font-weight','bold');
		}
	});
	//全选
	$('#ckb-product-all').click(function(){
		if($(this).attr('checked')=='checked'){
			$('input[name="systemMessage_check"]').attr('checked','checked');
		}else{
			$('input[name="systemMessage_check"]').removeAttr('checked');
		}
	});
	$('#delete-systemMessage').click(function(){
		var ckbs =$('input[name="systemMessage_check"]:checked');
		if(ckbs.size() == 0){
			lalert('删除','请选择消息!');
		}else{
			var ids = '';
			for(var i =0;i<ckbs.size();i++){
				if(i==ckbs.size()-1){
					var ckb = ckbs.eq(i);
					ids += ckb.val();
				}else{
					var ckb = ckbs.eq(i);
					ids += ckb.val()+ ",";
				}
			}
			window.location.href="${pageContext.request.contextPath}/front/customer/messageCenter/deleteDraftsMessageCenter.action?ids="+ids;
		}
	});
});
</SCRIPT>
	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth">
				<%@ include file="../include/left_account.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">系统消息</h3>
				<form id="formModule" action="${pageContext.request.contextPath}/front/customer/messageCenter/listInBoxMessage.action" method="post">

					<div class="FontSize13 margin-center publicBorderB" style="padding:5px 0;">
						&nbsp;&nbsp;&nbsp;<input type="checkbox" name="ckb-product-all" id="ckb-product-all" />&nbsp;&nbsp;<label for="ckb-product-all">选择所有</label>
						&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:;" id="delete-systemMessage" class="BackgroundOrange publicinline publicPadding5_10 ColorWhite1 FontSize14">删除</a>
					</div>

					<div class="TabParent_lj publicBorderT ClearBoth">
						<div class="publicMarginTop15">
							<table width="100%" border="0" class="RecentlyOrders_table publicMarginT10">
								<tr class="gradient publicBorder">
									<td width="4%"></td>
									<td width="9%">
										状态
									</td>
									<td width="9%">
										发件人
									</td>
									<td width="60%">
										主题
									</td>
									<td width="18%">
										时间
									</td>
								</tr>

								<s:iterator value="messageCenterList" id="m">
									<tr>
										<td>
											<input type="checkbox" name="systemMessage_check" value="<s:property value='#m.messageId'/>"/>
										</td>
										<td><s:property value="#m.isRead" /></td>
										<td>
											<s:property value="#m.fromMemberName" />
										</td>
										<td>
											<a class="getObj" href="${pageContext.request.contextPath}/front/customer/messageCenter/getMessageObject.action?id=<s:property value='#m.messageId'/>"><s:property value="#m.messageTitle" /></a>
										</td>
										<td>
											<s:property value="#m.createDate" />
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
