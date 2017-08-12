package shop.product.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.product.pojo.DeployShowPhoto;
import shop.product.service.IDeployShowPhotoService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
/**
 * 商品图片展示配置
 * @author Administrator
 *
 */
public class DeployShowPhotoAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private IDeployShowPhotoService deployShowPhotoService;
	private DeployShowPhoto deployShowPhoto;
	private List<DeployShowPhoto> deployShowPhotoList = new ArrayList<DeployShowPhoto>();
	private String deployShowPhotoId;
	private String ids;
	public void setDeployShowPhotoService(
			IDeployShowPhotoService deployShowPhotoService) {
		this.deployShowPhotoService = deployShowPhotoService;
	}
	//跳转到数据字典列表页面
	public String gotoDeployShowPhotoPage(){
		return SUCCESS;
	}
	//查询所有信息列表
	public void listDeployShowPhoto() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String name = request.getParameter("name");
			String typeName = request.getParameter("typeName");
			StringBuffer sb = CreateWhereSQLForSelect.appendLike(null, null, null);
			if(name!=null&&!"".equals(name)){
				sb.append(CreateWhereSQLForSelect.appendLike("name","like",request.getParameter("name")));
			}
			if(typeName!=null&&!"".equals(typeName)){
				sb.append(CreateWhereSQLForSelect.appendLike("typeName","like",request.getParameter("typeName")));
			}
			if(!"".equals(sb.toString()) && sb != null){
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" o.deployShowPhotoId desc"));
		int totalRecordCount = deployShowPhotoService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String [] selectColumns={"deployShowPhotoId","value","name","type","typeName"};
		deployShowPhotoList = deployShowPhotoService.findListByPageHelper(selectColumns,pageHelper, hqlsb.toString());
		//keyBookList = keyBookService.findListByPageHelper(null,pageHelper, " order by o.keyBookId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", deployShowPhotoList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateDeployShowPhoto() throws IOException{
		if(deployShowPhoto!=null){
			deployShowPhoto = (DeployShowPhoto) deployShowPhotoService.saveOrUpdateObject(deployShowPhoto);
			if(deployShowPhoto.getDeployShowPhotoId()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	//获取一条记录
	public void getDeployShowPhotoInfo() throws IOException{
		deployShowPhoto = (DeployShowPhoto) deployShowPhotoService.getObjectByParams(" where o.deployShowPhotoId='"+deployShowPhotoId+"'");
		JSONObject jo = JSONObject.fromObject(deployShowPhoto);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteDeployShowPhoto() throws IOException{
		Boolean isSuccess = deployShowPhotoService.deleteObjectsByIds("deployShowPhotoId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public DeployShowPhoto getDeployShowPhoto() {
		return deployShowPhoto;
	}
	public void setDeployShowPhoto(DeployShowPhoto deployShowPhoto) {
		this.deployShowPhoto = deployShowPhoto;
	}
	public List<DeployShowPhoto> getDeployShowPhotoList() {
		return deployShowPhotoList;
	}
	public void setDeployShowPhotoList(List<DeployShowPhoto> deployShowPhotoList) {
		this.deployShowPhotoList = deployShowPhotoList;
	}
	public String getDeployShowPhotoId() {
		return deployShowPhotoId;
	}
	public void setDeployShowPhotoId(String deployShowPhotoId) {
		this.deployShowPhotoId = deployShowPhotoId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
