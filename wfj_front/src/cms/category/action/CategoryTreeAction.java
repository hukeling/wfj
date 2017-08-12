package cms.category.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import util.action.BaseAction;
import cms.category.pojo.CmsCategory;
import cms.category.service.ICategoryService;
/**
 *  
 * 文章分类action
 */
@SuppressWarnings("serial")
public class CategoryTreeAction extends BaseAction {
	private ICategoryService categoryService;
	private String id;
	private String ids;
	private String categoryId;
	/**
	 * 管理分类
	 * 
	 */
    public String manageCategoryTree(){
    	return SUCCESS;
    }
    /**
	 * 管理分类对应文章
	 * 
	 */
    public String gotoCategoryTree(){
    	return SUCCESS;
    }
    /**
	 * 得到树的节点 
	 * 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getNodes() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		List<CmsCategory> list = categoryService.queryByParentId(id);
		StringBuffer sbf = new StringBuffer();
		CmsCategory category = null;
		sbf.append("<List>");
		for (Iterator ite = list.iterator(); ite.hasNext();) {
			category = (CmsCategory) ite.next();
			if (category != null) {
				sbf.append("<Category>");
				sbf.append("<name>").append(category.getCategoryName()).append(
						"</name>");
				sbf.append("<id>").append(category.getCategoryId()).append(
						"</id>");
				sbf.append("<isleaf>").append(category.getIsLeaf()).append(
						"</isleaf>");
				sbf.append("</Category>");
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
	 * 新增或许修改新闻分类 
	 * 
	 */
	public void saveOrEditCategory() throws Exception {
		String categoryId = request.getParameter("categoryId");
		String categoryName = request.getParameter("categoryName");
		String parentId = request.getParameter("parentId");
		String keywords = request.getParameter("keywords");
		String sortCode = request.getParameter("sortCode");
		String isLeaf = request.getParameter("isLeaf");
		 //1修改父分类的isLeaf=1 为 非叶子节点 ,这个parentId在前台已经有js 对应上父类的 categoryId 
		if(!"0".equals(parentId)){
			categoryService.updateFatherIsLeaf(parentId);
		}
		 //2保存新的分类
		 //0为叶子节点 ,可以添加新闻 
		 CmsCategory category=new CmsCategory();
		 if(categoryId!=null&&!"".equals(categoryId)){
			 category.setCategoryId(Integer.parseInt(categoryId));
		 }
		 category.setCategoryName(categoryName);
		 category.setParentId(Integer.parseInt(parentId));
		 category.setKeywords(keywords);
		 category.setSortCode(Integer.parseInt(sortCode));
		 if(isLeaf!=null&&!"".equals(isLeaf)){
			 category.setIsLeaf(Integer.parseInt(isLeaf));
		 }else{
			 category.setIsLeaf(1);
		 }
		 category=(CmsCategory)categoryService.saveOrUpdateObject(category);
			if(category.getCategoryId()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
	/**
	 * 得到分类对象
	 * 
	 */
	 public void getCategoryObject() throws Exception {
		    CmsCategory category = (CmsCategory) categoryService.getObjectById(categoryId);
			JSONObject jo = JSONObject.fromObject(category);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	 /**
		 * 删除分类
		 * 
		 */
	 public void delCategory() throws Exception {
			String strFlag= categoryService.deleteCategoryAndArticleByIds(ids);
			JSONObject jo = new JSONObject();
			jo.accumulate("strFlag", strFlag);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
