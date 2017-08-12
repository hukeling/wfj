package shop.product.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import shop.product.pojo.Specification;
import shop.product.pojo.SpecificationValue;
import shop.product.service.ISpecificationService;
import shop.product.service.ISpecificationValueService;
import util.action.BaseAction;
/**
 * SpecificationValueAction - 商品规格值Action类
 */
@SuppressWarnings("serial")
public class SpecificationValueAction extends BaseAction{
	private ISpecificationValueService specificationValueService;//商品规格值Service
	private ISpecificationService specificationService;//商品规格Service
	private List<Specification> specificationList = new ArrayList<Specification>();//商品规格List
	@SuppressWarnings("rawtypes")
	private List<Map> specificationValueList = new ArrayList<Map>();//商品规格值List
	private SpecificationValue specificationValue;//商品规格值对象
	private String specificationValueId;//商品规格值ID
	private String ids;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			// 1上传文件的类型
			String typeStr = imagePathFileName.substring(imagePathFileName.lastIndexOf(".") + 1);
			if ("jpg".equals(typeStr) || "JPG".equals(typeStr) || "png".equals(typeStr) || "PNG".equals(typeStr) || "GIF".equals(typeStr) ||"gif".equals(typeStr) || "".equals(typeStr)) {
				// 需要修改图片的存放地址
				String newName = imagePathFileName.substring(imagePathFileName.lastIndexOf("."));
				newName = UUID.randomUUID() + newName;
				File savefile = new File(new File((String) fileUrlConfig.get("fileUploadRoot") + "/"+ (String) fileUrlConfig.get("shop") + "/image/specification"), newName);
				if (!savefile.getParentFile().exists()) {
					savefile.getParentFile().mkdirs();
				}
				FileUtils.copyFile(imagePath, savefile);
				imagePathFileName = (String) fileUrlConfig.get("shop") + "/image/specification/" + newName;
				jo.accumulate("photoUrl", imagePathFileName);
				jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
			} else {
				jo.accumulate("photoUrl", "false2");
			}
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//跳转商品规格值页面
	public String gotoSpecificationValuePage(){
		specificationList = specificationService.findObjects(null);
		return SUCCESS;
	}
	//查询所有信息列表
	public void listSpecificationValue() throws IOException{
		String specificationValueName=request.getParameter("specificationValueName");
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		StringBuffer countHql = new StringBuffer("select count(a.specificationId) from SpecificationValue a,Specification b where a.specificationId=b.specificationId");
		hqlsb.append("select a.specificationValueId as specificationValueId ,a.name as valueName,a.sort as sort,b.specificationId as specificationId,b.name as name from SpecificationValue a,Specification b where a.specificationId=b.specificationId");
		if("true".equals(selectFlag)){
			if(specificationValueName!=null&&!"".equals(specificationValueName.trim())){
				countHql.append(" and b.name like '%"+specificationValueName.trim()+"%'");
				hqlsb.append("  and b.name like '%"+specificationValueName.trim()+"%'");
			}
		}
		hqlsb.append(" order by a.specificationValueId desc");
		int totalRecordCount = specificationValueService.getMoreTableCount(countHql.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		specificationValueList = specificationValueService.findListMapPage(hqlsb.toString(), pageHelper);
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", specificationValueList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateSpecificationValue() throws IOException{
		if(specificationValue!=null){
			specificationValue = (SpecificationValue) specificationValueService.saveOrUpdateObject(specificationValue);
			if(specificationValue.getSpecificationValueId()!=null){
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
	public void getSpecificationValueInfo() throws IOException{
		specificationValue = (SpecificationValue) specificationValueService.getObjectByParams(" where o.specificationValueId='"+specificationValueId+"'");
		JSONObject jo = JSONObject.fromObject(specificationValue);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteSpecificationValue() throws IOException{
		Boolean isSuccess = specificationValueService.deleteObjectsByIds("specificationValueId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getSpecificationValueList() {
		return specificationValueList;
	}
	@SuppressWarnings("rawtypes")
	public void setSpecificationValueList(List<Map> specificationValueList) {
		this.specificationValueList = specificationValueList;
	}
	public SpecificationValue getSpecificationValue() {
		return specificationValue;
	}
	public void setSpecificationValue(SpecificationValue specificationValue) {
		this.specificationValue = specificationValue;
	}
	public String getSpecificationValueId() {
		return specificationValueId;
	}
	public void setSpecificationValueId(String specificationValueId) {
		this.specificationValueId = specificationValueId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setSpecificationValueService(
			ISpecificationValueService specificationValueService) {
		this.specificationValueService = specificationValueService;
	}
	public List<Specification> getSpecificationList() {
		return specificationList;
	}
	public void setSpecificationList(List<Specification> specificationList) {
		this.specificationList = specificationList;
	}
	public void setSpecificationService(ISpecificationService specificationService) {
		this.specificationService = specificationService;
	}
	public File getImagePath() {
		return imagePath;
	}
	public void setImagePath(File imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePathFileName() {
		return imagePathFileName;
	}
	public void setImagePathFileName(String imagePathFileName) {
		this.imagePathFileName = imagePathFileName;
	}
}
