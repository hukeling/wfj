package basic.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import util.action.BaseAction;
import basic.pojo.Purview;
import basic.service.IPurviewService;
/**
 * 权限Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class PurviewAction extends BaseAction {
	private IPurviewService purviewService;//权限Service
	private String id;
	private String ids;
	private String purviewId;
	private Purview purview;
    //管理分类
    public String gotoPurviewPage(){
    	return SUCCESS;
    }
    //得到所有权限,回传时用html，不要用xml，否则&符不能使用
	public void getNodes() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		List<Purview> list = purviewService.queryByParentId(id);
		StringBuffer sbf = new StringBuffer();
		Purview purview = null;
		sbf.append("<List>");
		for (Iterator<Purview> ite = list.iterator(); ite.hasNext();) {
			purview = (Purview) ite.next();
			if (purview != null) {
				sbf.append("<Purview>");
				sbf.append("<name>").append(purview.getPurviewName()).append(
						"</name>");
				sbf.append("<id>").append(purview.getPurviewId()).append(
						"</id>");
				sbf.append("<isLeaf>").append(purview.getIsLeaf()).append(
						"</isLeaf>");
				sbf.append("</Purview>");
			}
		}
		sbf.append("</List>");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(sbf.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//添加或者修改权限
	public void saveOrEditPurview() throws Exception {
		if(purview!=null){
			Integer parentId = purview.getParentId();
			if(purview.getPurviewId()!=null &&!"".equals(purview.getPurviewId())){
				if(parentId!=null){
					Purview purview = (Purview)purviewService.getObjectById(parentId.toString());
					//1为叶子节点，0为非叶子节点 
					//以下的操作就不放到service的事物了,增强可读性与其他系统调整的可移值行。
					//添加当前节点注意修改父亲节点的类型为0,如果没有则不作任何操作
					if(purview!=null){
						purview.setIsLeaf(0);
						purviewService.saveOrUpdateObject(purview);
					}
				}
					purviewService.saveOrUpdateObject(purview);
			}else{
				if(parentId!=null){
					Purview purview = (Purview)purviewService.getObjectById(parentId.toString());
					if(purview!=null){
						purview.setIsLeaf(0);
						purviewService.saveOrUpdateObject(purview);
					}
				}
				purview.setIsLeaf(1);
				purviewService.saveOrUpdateObject(purview);
			}
		}
	}
	 //得到权限对象信息
	 public void getPurviewObject() throws Exception {
		    Purview purview = (Purview) purviewService.getObjectByParams(" where o.purviewId='"+purviewId+"'");
			JSONObject jo = JSONObject.fromObject(purview);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	 //删除权限信息
	public void delPurview() throws Exception {
		boolean isSuccess;
	    if(StringUtils.isNotEmpty(ids)){
	    	isSuccess = purviewService.deletePurview(ids);
	    }else{
	    	isSuccess = false;
	    }
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
//		System.out.println(jo.toString());
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getPurviewId() {
		return purviewId;
	}
	public void setPurviewId(String purviewId) {
		this.purviewId = purviewId;
	}
	public Purview getPurview() {
		return purview;
	}
	public void setPurview(Purview purview) {
		this.purview = purview;
	}
	public void setPurviewService(IPurviewService purviewService) {
		this.purviewService = purviewService;
	}
}
