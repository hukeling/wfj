<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 <script type="text/javascript">

 function changePage(pageIndex){
 	document.getElementById("pageNumber").value=pageIndex;
 	$("#formModule").submit();
 }
 </script>
<div class="pageList strong ClearBoth">
    <table id="dataArea" cellpadding="0" cellspacing="0" width="99.8%">
		<tr>
			<td class="mainpage">
				<span style="cursor:pointer;" onclick="changePage(<s:property value='pager.prePageIndex'/>);"><a href="javascript:;"><font size="2px;">◀  上一页</font></a></span>&nbsp;
				<s:iterator value="%{pager.optionalPageIndexList}" status="index" var="pageNumberIndex" >
					<s:set var="ind" value="pageNumberIndex"></s:set>
		        	<span style="cursor:pointer;" onclick="changePage('<s:property />');"><a href="javascript:;" <s:if test="#ind == pager.pageNumber"> style="color: red;" </s:if>  ><font size="2px;"><s:property value="#ind"/></font></a></span>&nbsp;	
				</s:iterator>
			    <span style="cursor:pointer;" onclick="changePage(<s:property value='pager.nextPageIndex'/>);"><a href="javascript:;"><font size="2px;">下一页 ▶</font></a></span>&nbsp;&nbsp;
			</td>
		</tr>
  	 </table>
</div>