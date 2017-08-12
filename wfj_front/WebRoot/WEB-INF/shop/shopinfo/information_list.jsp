<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>资讯信息</title>
	<%@ include file="../include/head.jsp"%>
	<link rel="stylesheet" type="text/css" href="${fileUrlConfig.fileServiceUploadRoot}shop/front/css/cart.css">
	<script type="text/javascript" src="${fileUrlConfig.fileServiceUploadRoot}js/jquery.form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	//添加或者修改
	function AddCartory(articleId){
		var url='${pageContext.request.contextPath}/front/store/infoMation/addOrEditInforMation.action?categoryId=${categoryId}';
		if(articleId!=null){
			url='${pageContext.request.contextPath}/front/store/infoMation/addOrEditInforMation.action?information.articleId='+articleId+'&categoryId=${categoryId}';
		}
		window.open(url, 'newwindow', 'height=500, width=1000, top=100, left=200, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=n o, status=no');
	}
	//子页面操作之后调用此方法刷新页面
	function afterAddOrEdit(){
		window.location.reload();
	}
	//删除此分类
	function deleteCartory(articleId){
		lconfirm("提示","确定要删除吗!",function(o){
			 var url = '${pageContext.request.contextPath}/front/store/infoMation/deleteInforMation.action';
			 $.post(url,{"articleId":articleId},function(data){
					if(data){
				       window.location.reload();
					}
			 },"json");
		});
	}
	//资讯预览
	function preview(articleId){
		var url="${pageContext.request.contextPath}/front/store/infoMation/preview.action?articleId="+articleId;
		window.open(url,"_blank");
	}
	//查看留言
	function previewComment(articleId){
		var url="${pageContext.request.contextPath}/front/store/infoMation/previewComment.action?articleId="+articleId;
		window.open(url,"_blank");
	}
	
	</script>
</head>
	<body>
		<%@ include file="../include/header.jsp"%>
		<div class="margin-center PublicWidth1004">
			<div class="ClearBoth" style="margin-top:10px;">
				<%@ include file="../include/left_shop.jsp"%>
				<!--right-->
				<div id="rightBox" class="float-right publicHidden publicrelative">
				<h3 class="publicBorderB_3red FontSize16 FontSizeB publicPadding10 publicMarginBot15 ">资讯信息</h3>
					<div class="TabParent_lj  ClearBoth">
					<input type="button" onclick="AddCartory(null);" class="BackgroundPurple_h publicinline ColorWhite1 FontSize14 publicBorder_h publicabsolute" style="top:4px; right:4px; width:80px; line-height:20px; cursor: pointer;" value="添加资讯" >
						<div class="publicMarginTop15">
								<table width="100%" border="0" class="RecentlyOrdersTable_lj">
									<tr class="gradient publicBorder">
										<td width="13%"  style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid; border-left: 1px #d2d2d2 solid;">
											标题
										</td>
										<td width="10%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid; ">
											主题图片
										</td>
										<td width="35%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid; ">
											摘要
										</td>
										<td width="5%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
											排序
										</td>
										<td width="9%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
											是否显示
										</td>
										<td width="9%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid;">
											是否推荐
										</td>
										
										<td width="12%" style="border-top: 1px #d2d2d2 solid; border-bottom: 1px #d2d2d2 solid; border-right: 1px #d2d2d2 solid;">
											 操作
										</td>
									</tr>
									<s:iterator value="#request.informationList">
										<tr>
											<td  style="border-bottom: 1px #d2d2d2 solid;" title="<s:property value='title'/>"><div  style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis; width:150px;"><s:property value="title" /></div></td>
											<td  style="border-bottom: 1px #d2d2d2 solid;"><img onerror="this.src='${fileUrlConfig.fileServiceUploadRoot}shop/front/images/mrbj.png'" src="<s:property value='%{#application.fileUrlConfig.visitFileUploadRoot+imgUrl}'/>" style="height: 80px;width: 80px"   border-bottom: 1px #d2d2d2 solid;/></td>
											<td  style="border-bottom: 1px #d2d2d2 solid;" title="<s:property value="brief"/>"><div  style="overflow:hidden; white-space:nowrap; text-overflow:ellipsis; width:450px;"><s:property value="brief"/></div></td>
											<td  style="border-bottom: 1px #d2d2d2 solid;"><s:property value="sortCode"/></td>
											<td  style="border-bottom: 1px #d2d2d2 solid;"><s:if test="isShow==1">显示</s:if><s:if test="isShow==0">不显示</s:if></td>
											<td  style="border-bottom: 1px #d2d2d2 solid;"><s:if test="isEssence==1">推荐</s:if><s:if test="isEssence==0">不推荐</s:if></td>
											<td  style="border-bottom: 1px #d2d2d2 solid;" class="line-height2 lastR">
												<a href="javascript:;" onclick="AddCartory('<s:property value="articleId"/>');">编辑</a>
												<a href="javascript:;" onclick="deleteCartory('<s:property value="categoryId"/>');">删除</a><br> 
												<a href="javascript:;" onclick="preview('<s:property value="articleId"/>'); " >资讯预览</a><br> 
												<a href="javascript:;" onclick="previewComment('<s:property value="articleId"/>'); " >查看留言(${count})</a>
											</td>
										</tr>
									</s:iterator>
<!-- 									<tr >
										<td colspan="5" class="line-height2 lastR">
											
										</td>
									</tr> -->
								</table>							                      
						</div>
					</div>
				</div>
				<!--//right-->
				 <!--分页-->
			    	  <form id="formModule" action="${pageContext.request.contextPath}/front/store/infoMation/gotoInformationPage.action?categoryId=${categoryId}" method="post">
						<s:if test="#request.informationList.size>0">
							<div class="pageList strong ClearBoth">
							     <jsp:include page="../../util/splitPage.jsp" />
							</div>
						</s:if>
				    </form>
				<!--分页 end-->
			</div>
		</div>
		</div>
		<%@ include file="../include/footer.jsp"%>
	</body>
</html>
