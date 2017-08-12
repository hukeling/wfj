package shop.common.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import shop.common.pojo.DistrictInfo;
import shop.common.service.IDistrictInfoService;
import util.action.BaseAction;
/**
 * DistrictInfoAction - 地区信息Action类
 */
@SuppressWarnings("all")
public class DistrictInfoAction extends BaseAction{
	private IDistrictInfoService districtInfoService;//地区信息Service
	private String id;
	private String ids;
	private String districtInfoId;
	private DistrictInfo districtInfo;
	//管理地区信息
    public String gotoDistrictInfoPage(){
    	return SUCCESS;
    }
	@SuppressWarnings("unchecked")
	public void getNodes() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		List<DistrictInfo> list = districtInfoService.queryByParentId(id);
		StringBuffer sbf = new StringBuffer();
		DistrictInfo districtInfo = null;
		sbf.append("<List>");
		for (Iterator ite = list.iterator(); ite.hasNext();) {
			districtInfo = (DistrictInfo) ite.next();
			if (districtInfo != null) {
				sbf.append("<DistrictInfo>");
				sbf.append("<name>").append(districtInfo.getDistrictName()).append(
						"</name>");
				sbf.append("<id>").append(districtInfo.getDistrictInfoId()).append(
						"</id>");
				sbf.append("<loadType>").append(districtInfo.getLoadType()).append(
					"</loadType>");
				sbf.append("</DistrictInfo>");
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
	//保存或者修改地区信息 
	public void saveOrEditDistrictInfo() throws Exception {
		if(districtInfo!=null){
			Integer parentId = districtInfo.getParentId();
			if(parentId != 1){
				districtInfoService.saveOrUpdateFatherLoadType(parentId.toString());
				districtInfo.setLoadType("1");
			}
			if(parentId==1){
				districtInfo.setLoadType("1");
			}
			districtInfoService.saveOrUpdateObject(districtInfo);
		}
	}
	//得到地区信息对象
	 public void getDistrictInfoObject() throws Exception {
		 	DistrictInfo districtInfo = (DistrictInfo) districtInfoService.getObjectByParams(" where o.districtInfoId='"+districtInfoId+"'");
			JSONObject jo = JSONObject.fromObject(districtInfo);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
	}
	 @SuppressWarnings("unchecked")
	public void delDistrictInfo() throws Exception {
		Boolean isSuccess;
	    List list = districtInfoService.queryByParentId(districtInfoId);
	    if(list==null || list.size()==0){
	    	//删除的时候要判断此节点的父类还有没有子类，如果没有了就要把父类改成叶子节点，否则不变
	    	districtInfo = (DistrictInfo) districtInfoService.getObjectByParams(" where o.districtInfoId='"+districtInfoId+"'");
	    	Integer parentId = districtInfo.getParentId();
	    	isSuccess =  districtInfoService.deleteObjectByParams(" where o.districtInfoId='"+districtInfoId+"'");
	    	Integer count = districtInfoService.getCount(" where o.parentId="+parentId+"");
	    	if(count==0){
	    		DistrictInfo pt = (DistrictInfo) districtInfoService.getObjectByParams(" where o.districtInfoId='"+parentId+"'");
	    		pt.setLoadType("1");
	    		districtInfoService.saveOrUpdateObject(pt);
	    	}
	    }else{
	    	isSuccess = false;
	    }
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
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
	public String getDistrictInfoId() {
		return districtInfoId;
	}
	public void setDistrictInfoId(String districtInfoId) {
		this.districtInfoId = districtInfoId;
	}
	public DistrictInfo getDistrictInfo() {
		return districtInfo;
	}
	public void setDistrictInfo(DistrictInfo districtInfo) {
		this.districtInfo = districtInfo;
	}
	public void setDistrictInfoService(IDistrictInfoService districtInfoService) {
		this.districtInfoService = districtInfoService;
	}
}
