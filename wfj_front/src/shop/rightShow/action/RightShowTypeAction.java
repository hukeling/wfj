package shop.rightShow.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.rightShow.pojo.RightShowType;
import shop.rightShow.service.IRightShowTypeService;
import util.action.BaseAction;
import basic.pojo.KeyBook;

import com.opensymphony.xwork2.ActionContext;
/**
 * 首页右面模块分类action的操作
 * @author 张攀攀
 */
@SuppressWarnings("serial")
public class RightShowTypeAction extends BaseAction {
	private IRightShowTypeService rightShowTypeService;//右侧信息分类Service
	private List<KeyBook> modTypeList;
	private RightShowType rightShowType;
	private List<RightShowType> rightShowTypeList = new ArrayList<RightShowType>();//右侧信息分类List
	private String rightShowTypeId;
	private String id;
	//删除对象
	public void deleteRightShowType(){
		rightShowTypeService.deleteObjectByParams(" where o.rightShowTypeId="+rightShowTypeId);
	}
	//保存会修改对象
	public void saveOrUpdateRightShowType(){
		rightShowTypeService.saveOrUpdateObject(rightShowType);
	}
	//查询当前模块分类对象
	public void getRightShowTypeObject() throws IOException{
		rightShowType = (RightShowType)rightShowTypeService.getObjectByParams(" where o.rightShowTypeId="+rightShowTypeId);
		JSONObject jo = JSONObject.fromObject(rightShowType);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	//查询右面模块分类树
	@SuppressWarnings("unchecked")
	public void getNodes() {
		response.setContentType("text/xml;charset=utf-8");
		//通过父类Id查询子特别商品分类列表
		String hql = " where o.parentId='"+id+"' order by o.rightShowTypeId";
		rightShowTypeList = rightShowTypeService.findObjects(hql);
		StringBuffer sbf = new StringBuffer();
		sbf.append("<List>");
		for (Iterator ite = rightShowTypeList.iterator(); ite.hasNext();) {
			rightShowType = (RightShowType) ite.next();
			if (rightShowType != null) {
				sbf.append("<RightShowType>");
				sbf.append("<type>").append(rightShowType.getShowType()).append(
			            "</type>");
				sbf.append("<name>").append(rightShowType.getTypeName()).append(
						"</name>");
				sbf.append("<id>").append(rightShowType.getRightShowTypeId()).append(
						"</id>");
				sbf.append("</RightShowType>");
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
	//商品模块右面展示
	@SuppressWarnings("unchecked")
	public String  gotoRightShowTypePage(){
		//查询右面展示模块数据字典
		modTypeList = (List<KeyBook>) ((Map) ActionContext.getContext().getApplication().get("keybook")).get("typeShow");
		return SUCCESS;
	}
	public RightShowType getRightShowType() {
		return rightShowType;
	}
	public void setRightShowType(RightShowType rightShowType) {
		this.rightShowType = rightShowType;
	}
	public List<RightShowType> getRightShowTypeList() {
		return rightShowTypeList;
	}
	public void setRightShowTypeList(List<RightShowType> rightShowTypeList) {
		this.rightShowTypeList = rightShowTypeList;
	}
	public String getRightShowTypeId() {
		return rightShowTypeId;
	}
	public void setRightShowTypeId(String rightShowTypeId) {
		this.rightShowTypeId = rightShowTypeId;
	}
	public void setRightShowTypeService(IRightShowTypeService rightShowTypeService) {
		this.rightShowTypeService = rightShowTypeService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<KeyBook> getModTypeList() {
		return modTypeList;
	}
	public void setModTypeList(List<KeyBook> modTypeList) {
		this.modTypeList = modTypeList;
	}
}
