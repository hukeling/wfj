<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">
	#dataArea{border:none;}
	#dataArea tr td{border:none;text-align:right;}
</style>
 <script type="text/javascript">
 function changePage(pageIndex){
 	document.getElementById("currentPage").value=pageIndex;
 	$("#formModule").submit();
 }
  
  function gotoPage(type){
	var pageIndex=$("#goto").val();
	var code = event.keyCode;	
	var regu = /^[-]{0,1}[0-9]{1,}$/;
	if(pageIndex<=<s:property value='pageHelper.pageCount'/>&&pageIndex>0&&regu.test(pageIndex)){
		
		if(type=='onkeydown'){
			if (code==13){
				changePage(pageIndex);
			}else{	
				$("#goto").value=pageIndex;
			}
		}
		if(type=='onclick'){
			changePage(pageIndex);
		}
	}else{
		alert("无效页数");
		$("#goto").val(1);
		return false;
	}
  }
			
 </script>
 
<input type="hidden" id="currentPage" name="currentPage" value="<s:property value='currentPage'/>"/>
<!--翻页效果-->
<div class="flipEffect">
   <div style="float:right;">
   		<a <s:if test="currentPage==1">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pageHelper.prePageIndex'/>)"</s:else>>上一页</a>
   		<s:iterator value="pageHelper.optionalPageIndexList" var="pageNumber" >
   			 <a href="javascript:;" <s:if test="#pageNumber==currentPage">class="currentY"</s:if><s:else>class="numberPages" onclick="changePage(<s:property value='#pageNumber'/>)"</s:else> ><s:property value="#pageNumber" /></a>
		</s:iterator>
   		<a <s:if test="currentPage>=pageHelper.lastPageIndex">class="prevYin"</s:if><s:else> class="prevYinOk" onclick="changePage(<s:property value='pageHelper.nextPageIndex'/>)"</s:else>>下一页</a>
   </div>
</div>
