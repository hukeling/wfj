<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
 <!--头部翻页效果-->
<div class="fr previousPage">
	<span <s:if test="currentPage==1">class="prevYs"</s:if><s:else> class="prevYsCur" onclick="changePage(<s:property value='pageHelper.prePageIndex'/>)"</s:else> ><b></b></span>
	<a href="javascript:;" <s:if test="currentPage==pageHelper.lastPageIndex">class="nextYNO"</s:if><s:else> class="nextY" onclick="changePage(<s:property value='pageHelper.nextPageIndex'/>)"</s:else>>下一页 <b></b></a>
</div>
 	
