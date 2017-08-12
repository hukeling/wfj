<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
function showShopSituationTagList(shopInfoId){
	$("#tt2").datagrid({//数据表格
		title:"未关联客服列表信息",
		iconCls:"icon-save", 
		width:"auto",
		height:"auto",
		fitColumns: true,//宽度自适应
		align:"center",
		loadMsg:"正在处理，请等待......",
		//nowrap: false,//true是否将数据显示在一行
		striped: true,//true是否把一行条文化
		url:"${pageContext.request.contextPath}/back/shopCustomerService/listUnShopCustomerService.action?ids="+shopInfoId,
		idField:"customerServiceId",//唯一性标示字段
		frozenColumns:[[//冻结字段
		    {field:"ck",checkbox:true}
		]],
		columns:[[//未冻结字段
	        {field:"trueName",title:"真实姓名",width:120}, 
	        {field:"nikeName",title:"昵称",width:120}, 
	        {field:"qq",title:"QQ",width:120}, 
			{field:"useState",title:"使用状态",width:120,formatter:function(value,rowData,rowIndex){  //function里面的三个参数代表当前字段值，当前行数据对象，行号（行号从0开始）
					if(value=="0"){ 
						return "<font color='red'>废弃</font>";
					}else if(value=="1"){
						return "<font color='blue'>正常使用</font>";
					} 
				}
			}
		]],
		pagination:true,//显示分页栏
		rownumbers:true,//显示行号   
		singleSelect:false//true只可以选中一行
	});
	pageSplitThis(pageSize,queryParams,"tt2");//调用分页函数
}

//保存关联关系
function savaOrUpdateShopTradeSituationTag(){
	var rows = $("#tt2").datagrid("getSelections");//找到所有选中的行
	if(rows.length<=0){
		msgShow("系统提示", "<p class='tipInfo'>请选择关联项！</p>", "warning");
	}else{
		if (rows){
			var ids="";
			for(var i=0;i<rows.length;i++){
				if(i==rows.length-1)ids+=rows[i].customerServiceId;
				else ids+=rows[i].customerServiceId+",";
			}
			$.ajax({
			   type: "POST",dataType: "JSON",
			   url: "${pageContext.request.contextPath}/back/shopCustomerService/savaOrUpdateShopCustomerService.action",
			   data: {ids:ids,shopInfoId:"${ids}"},
			   async : false,
			   success: function(data){
				   $("#tt2").datagrid("clearSelections");
				   $("#tt2").datagrid("reload");
				   $("#tt").datagrid("reload");
			   }
			});
		}	
	}
}

$(function(){
    $('#btn2').linkbutton({plain:true});   
});
function queryTT2(){
	queryParams={selectFlag:true,pageSize:pageSize,currentPage:currentPage,trueName:$.trim($("#qtrueName2").val()),nikeName:$.trim($("#qnikeName2").val()),qq:$.trim($("#qQQ2").val()),useState:$("#quseState2").val()};
  	$("#tt2").datagrid("load",queryParams); 
	pageSplitThis(pageSize,queryParams,"tt2");//调用分页函数
}

</script>
<div id="addOrEditWin">
	<form id="form1"></form>
	<div style="width: 95%;margin: 10px auto;">
		<table style="border:1px solid #99BBE8;text-align: center;" width="100%">
		    	<tr>
		    		<td align="right">真实姓名 ：</td>
		    		<td align="left"><input type="text" id="qtrueName2" />&nbsp;&nbsp;</td>
		    		<td align="right">昵称 ：</td>
		    		<td align="left"><input type="text" id="qnikeName2" />&nbsp;&nbsp;</td>
		    		<!-- <td align="right">QQ ：</td>
		    		<td align="left"><input type="text" id="qQQ2" />&nbsp;&nbsp;</td>
		    		<td align="right">使用状态 ：</td>
		    		<td align="left"><select id="quseState2">
		              <option value="-1">-----请选择-----</option>
		              <option value="0">废弃</option>
		              <option value="1">正常使用</option>
		            </select>&nbsp;&nbsp;</td> -->
		    		<td><a href="javascript:queryTT2();" id="btn2" iconCls="icon-search" >查询</a></td>
		    	</tr>
		    </table><br/>
			<table id="tt2"></table>
		<div data-options="region:'south',border:false" style="text-align:center;padding:5px 0;margin-top: 10px;">
			<input type="button" id="save" class="button_save" onclick="savaOrUpdateShopTradeSituationTag()"  value="" style="cursor:pointer;"/>&nbsp;
			<input type="button" id="close" class="button_close" onclick="closeWin()" value="" style="cursor:pointer;"/>&nbsp;
		</div>
	</div>
</div>
