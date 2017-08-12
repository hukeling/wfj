package basic.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;
import util.action.BaseAction;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
/**
 * 地区
 * 
 * @author 文建迎
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes","unused", "serial" })
public class AreaAction extends BaseAction {
	private IAreaService areaService;
	private BasicArea basicArea;
    private Integer parentId;
    private String id;
	  /**
	 * 访问地区分类页面
	 * 
	 */
    public String gotoAreaTree(){
    	return SUCCESS;
    }
	/**
	 * 得到树的节点(地区)
	 * 
	 */
	public void getNodes() {
		response.setContentType("text/xml;charset=utf-8");
		List<BasicArea> list = areaService.findObjects(null, "where 1=1 and o.parentId="+parentId+"");
		StringBuffer sbf = new StringBuffer();
		BasicArea basicArea = null;
		sbf.append("<List>");
		for (Iterator ite = list.iterator(); ite.hasNext();) {
			basicArea = (BasicArea) ite.next();
			if (basicArea != null) {
				sbf.append("<area>");
				sbf.append("<name>").append(basicArea.getName()).append("</name>");
				sbf.append("<id>").append(basicArea.getAreaId()).append("</id>");
				sbf.append("<isleaf>").append(basicArea.getIsLeaf()).append("</isleaf>");
				sbf.append("</area>");
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
	/**
	 * 格式化地区表数据
	 * 
	 */
	public  void aa(){
		int i=0;
		 List<BasicArea> allObjList = areaService.findObjects(null, null);
		 for(BasicArea ba:allObjList){
			 if(",".equals(ba.getTreePath())){//1ji
				 ba.setIsLeaf(0);
			 }else{
				 BasicArea obj = (BasicArea) areaService.getObjectByParams("where o.areaId="+ba.getParentId());
				 if(",".equals(obj.getTreePath())){//2ji
					 Integer count = areaService.getCount(" where o.parentId="+ba.getAreaId());
					 if(count>0){//有下ji
						 ba.setIsLeaf(0);
					 }else{
						 ba.setIsLeaf(1);
					 }
				 }else{//3ji
					 ba.setIsLeaf(1);
				 }
			 }
			 areaService.saveOrUpdateObject(ba);
		 }
	 }
	/**
	 * 得到分类对象
	 * 
	 */
	 public void getAreaObject() throws Exception {
			BasicArea basicArea = (BasicArea) areaService.getObjectById(id);
			JSONObject jo = JSONObject.fromObject(basicArea);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	 /**
		 * 删除地区
		 * 
		 */
	 public void deleteArea() throws Exception {
		 	BasicArea basicAreaTemp = (BasicArea) areaService.getObjectByParams(" where o.areaId="+id);//拿到原对象
			boolean isSuccess = areaService.deleteObjectById(id);
			if(isSuccess){
				Integer parentIdTemp = basicAreaTemp.getParentId();
				BasicArea ba = (BasicArea)areaService.getObjectByParams(" where o.parentId="+parentIdTemp);
				if(ba==null){//将父类修改为叶子节点
					BasicArea bap = (BasicArea)areaService.getObjectById(String.valueOf(parentIdTemp));
					bap.setIsLeaf(1);
					areaService.saveOrUpdateObject(bap);
				}
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", isSuccess);
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
 	/**
	 * 保存或修改
 	 * @throws IOException 
	 * 
	 */
	 public void saveOrEditArea() throws IOException{
		 if(basicArea!=null&&basicArea.getAreaId()!=null){
			 BasicArea ba = (BasicArea) areaService.getObjectByParams(" where o.areaId="+basicArea.getAreaId());//拿到原对象
			 basicArea.setTreePath(ba.getTreePath());
			 basicArea.setFullName(ba.getFullName());
			 basicArea.setIsLeaf(ba.getIsLeaf());
			 basicArea.setCreateDate(ba.getCreateDate());
			 basicArea.setModifyDate(ba.getModifyDate());
		 }else{
			 BasicArea ba = (BasicArea) areaService.getObjectByParams(" where  o.areaId="+basicArea.getParentId());
			 String treePath=ba.getTreePath()+basicArea.getParentId()+",";
			 String fullName=ba.getFullName()+basicArea.getName();
			 basicArea.setTreePath(treePath);//set路径
			 basicArea.setIsLeaf(1);
			 basicArea.setFullName(fullName);//setFullName
			 basicArea.setCreateDate(new Date());//set创建时间
			 basicArea.setModifyDate(new Date());
			 ba.setIsLeaf(0);
			 areaService.saveOrUpdateObject(ba);
		 }
		 BasicArea ba = (BasicArea) areaService.saveOrUpdateObject(basicArea);
		 if(ba!=null&&ba.getAreaId()!=null){
			 JSONObject jo=new JSONObject();
			 jo.accumulate("isSuccess",true);
			 response.setContentType("text/html;charset=utf-8");
			 PrintWriter out = response.getWriter();
			 out.println(jo.toString());
			 out.flush();
			 out.close();
		 }
	 }
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public BasicArea getBasicArea() {
		return basicArea;
	}
	public void setBasicArea(BasicArea basicArea) {
		this.basicArea = basicArea;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
