package shop.returnsApply.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.returnsApply.pojo.ReturnsApply;
import shop.returnsApply.pojo.ReturnsApplyOPLog;
import shop.returnsApply.service.IReturnsApplyOPLogService;
import shop.returnsApply.service.IReturnsApplyService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
/**
 * 退换申请操作日志Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class ReturnsApplyOPLogAction extends BaseAction{
	private IReturnsApplyOPLogService returnsApplyOPLogService;//退换申请操作日志Service
	private IReturnsApplyService returnsApplyService;//退换申请Service
	private List<ReturnsApplyOPLog> returnsApplyOPLogList = new ArrayList<ReturnsApplyOPLog>();//退换申请操作日志List
	private List<ReturnsApply> returnsApplyList = new ArrayList<ReturnsApply>();//退换申请List
	private String ids;
	private String returnsApplyOPLogId;
	private String params;//查询条件
	private ReturnsApplyOPLog returnsApplyOPLog;
	private String returnsApplyId;//退换货申请ID
	//跳转到退换申请操作日志列表信息
	public String gotoReturnsApplyOPLogPage(){
		//查询退换申请单号
		returnsApplyList = returnsApplyService.findObjects(null);
		return SUCCESS;
	}
	//根据退换单号ID得到退换申请信息的操作日志
	public void listReturnsApplyOPLog() throws Exception {
		/*
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			if(!"-1".equals(request.getParameter("applyId"))){
				StringBuffer sb=CreateWhereSQLForSelect.appendEqual("applyId",request.getParameter("applyId"));
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" applyId desc"));
		*/
		String hql = " where o.applyId="+returnsApplyId;
		int totalRecordCount = returnsApplyOPLogService.getCount(hql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		returnsApplyOPLogList = returnsApplyOPLogService.findListByPageHelper(null,pageHelper, hql);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", returnsApplyOPLogList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除退换申请操作日志信息
	public void deleteReturnsApplyOPLog() throws IOException{
		Boolean isSuccess = returnsApplyOPLogService.deleteObjectsByIds("returnsApplyOPLogId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//通过ID得到一条数据
	public void getReturnsApplyOPLogObject() throws IOException{
		returnsApplyOPLog = (ReturnsApplyOPLog) returnsApplyOPLogService.getObjectByParams(" where o.returnsApplyOPLogId='"+returnsApplyOPLogId+"'");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(returnsApplyOPLog,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改申请操作日志
	public void saveOrUpdateReturnsApplyOPLog() throws Exception{
		String applyState = request.getParameter("applyState");
		if(returnsApplyOPLog!=null){
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatTime = sdf.format(new Date());
			Date date = sdf.parse(formatTime);
			if(returnsApplyOPLog.getReturnsApplyOPLogId()==null || "".equals(returnsApplyOPLog.getReturnsApplyOPLogId())){
				ReturnsApply returnsApply = (ReturnsApply) returnsApplyService.getObjectById(returnsApplyOPLog.getApplyId().toString());
				returnsApplyService.saveOrUpdateObject(returnsApply);//改变申请的状态
				returnsApplyOPLog.setOperatorTime(date);
				returnsApplyOPLogService.saveOrUpdateObject(returnsApplyOPLog);
			}else{
				returnsApplyOPLogService.saveOrUpdateObject(returnsApplyOPLog);
			}
		}
		if(returnsApplyOPLog.getReturnsApplyOPLogId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	public List<ReturnsApplyOPLog> getReturnsApplyOPLogList() {
		return returnsApplyOPLogList;
	}
	public void setReturnsApplyOPLogList(
			List<ReturnsApplyOPLog> returnsApplyOPLogList) {
		this.returnsApplyOPLogList = returnsApplyOPLogList;
	}
	public List<ReturnsApply> getReturnsApplyList() {
		return returnsApplyList;
	}
	public void setReturnsApplyList(List<ReturnsApply> returnsApplyList) {
		this.returnsApplyList = returnsApplyList;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getReturnsApplyOPLogId() {
		return returnsApplyOPLogId;
	}
	public void setReturnsApplyOPLogId(String returnsApplyOPLogId) {
		this.returnsApplyOPLogId = returnsApplyOPLogId;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public ReturnsApplyOPLog getReturnsApplyOPLog() {
		return returnsApplyOPLog;
	}
	public void setReturnsApplyOPLog(ReturnsApplyOPLog returnsApplyOPLog) {
		this.returnsApplyOPLog = returnsApplyOPLog;
	}
	public void setReturnsApplyOPLogService(
			IReturnsApplyOPLogService returnsApplyOPLogService) {
		this.returnsApplyOPLogService = returnsApplyOPLogService;
	}
	public void setReturnsApplyService(IReturnsApplyService returnsApplyService) {
		this.returnsApplyService = returnsApplyService;
	}
	public String getReturnsApplyId() {
		return returnsApplyId;
	}
	public void setReturnsApplyId(String returnsApplyId) {
		this.returnsApplyId = returnsApplyId;
	}
}
