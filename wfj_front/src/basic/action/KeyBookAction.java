package basic.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import basic.pojo.KeyBook;
import basic.service.IKeyBookService;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
import cms.category.pojo.CmsCategory;
import cms.category.service.ICategoryService;

import com.opensymphony.xwork2.ActionContext;
/**
 * 数据字典Action类
 * @author ss
 *
 */
@SuppressWarnings({"rawtypes","unused", "serial" })
public class KeyBookAction extends BaseAction{
	private IKeyBookService keyBookService;
	private KeyBook keyBook;
	private List<KeyBook> keyBookList = new ArrayList<KeyBook>();
	private String keyBookId;
	private String ids;
	/** 底部文章集合 **/
	private IArticleService articleService;//引用文章Service
	private ICategoryService categoryService;//引用文章分类Service
	private Map categoryMap = new LinkedHashMap();//商品分类
	private Map categoryBrandMap = new LinkedHashMap();//商品分类下的品牌
	//跳转到数据字典列表页面
	public String gotoKeyBookPage(){
		return SUCCESS;
	}
	//查询所有信息列表
	public void listKeyBook() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String name = request.getParameter("name");
			String typeName = request.getParameter("typeName");
			StringBuffer sb = CreateWhereSQLForSelect.appendLike(null, null, null);
			if(name!=null&&!"".equals(name.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("name","like",name.trim()));
			}
			if(typeName!=null&&!"".equals(typeName.trim())){
				sb.append(CreateWhereSQLForSelect.appendLike("typeName","like",typeName.trim()));
			}
			if(!"".equals(sb.toString()) && sb != null){
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" keyBookId desc"));
		int totalRecordCount = keyBookService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		String [] selectColumns={"keyBookId","value","name","type","typeName"};
		keyBookList = keyBookService.findListByPageHelper(selectColumns,pageHelper, hqlsb.toString());
		//keyBookList = keyBookService.findListByPageHelper(null,pageHelper, " order by o.keyBookId desc");
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", keyBookList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public void saveOrUpdateKeyBook() throws IOException{
		if(keyBook!=null){
			keyBook = (KeyBook) keyBookService.saveOrUpdateObject(keyBook);
			if(keyBook.getKeyBookId()!=null){
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
	public void getKeyBookInfo() throws IOException{
		keyBook = (KeyBook) keyBookService.getObjectByParams(" where o.keyBookId='"+keyBookId+"'");
		JSONObject jo = JSONObject.fromObject(keyBook);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除记录
	public void deleteKeyBook() throws IOException{
		Boolean isSuccess = keyBookService.deleteObjectsByIds("keyBookId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//更新初始化数据字典
	public void updateInServletContextKeyBook() throws IOException{
		Map<String, Object> servletContext = ActionContext.getContext().getApplication();
		Boolean isSuccess = false;//返回值，是否更新成功，默认是否
		if (servletContext != null) {
			Map<String,List<KeyBook>> map = new HashMap<String,List<KeyBook>>();
			List<String> typeNameList = keyBookService.distinctType("type", "");//查找类型名称
			for(String typeName : typeNameList){
				List<KeyBook> keyBookList = keyBookService.findObjects(" where o.type = '"+typeName+"' order by o.value ");//根据类型名称查出对象集合
				map.put(typeName, keyBookList);
			}
			servletContext.put("keybook", map);
			//全局底部文章加载
			servletContext.put("footrtArticleMap", findFooterArticle());
			isSuccess = true;
		}
	}
	/**
	 * 全局底部文章加载
	 */
	public Map<String,List<CmsArticle>> findFooterArticle(){
		Map<String,List<CmsArticle>> footrtArticleMap = new LinkedHashMap<String,List<CmsArticle>>();
		List<CmsCategory>  cmsCategoryList = categoryService.findObjects(" where o.parentId = 92 ");
		for(CmsCategory cmsCategory : cmsCategoryList){
			List<CmsArticle> cmsArticlesList = articleService.findObjects("where o.isShow = 1 and o.categoryId = "+cmsCategory.getCategoryId());
			String str = cmsCategory.getCategoryName();
			footrtArticleMap.put(str, cmsArticlesList);
		}
		return footrtArticleMap;
	}
	public KeyBook getKeyBook() {
		return keyBook;
	}
	public void setKeyBook(KeyBook keyBook) {
		this.keyBook = keyBook;
	}
	public List<KeyBook> getKeyBookList() {
		return keyBookList;
	}
	public void setKeyBookList(List<KeyBook> keyBookList) {
		this.keyBookList = keyBookList;
	}
	public String getKeyBookId() {
		return keyBookId;
	}
	public void setKeyBookId(String keyBookId) {
		this.keyBookId = keyBookId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setKeyBookService(IKeyBookService keyBookService) {
		this.keyBookService = keyBookService;
	}
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	public void setCategoryService(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}
	public Map getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(Map categoryMap) {
		this.categoryMap = categoryMap;
	}
	public Map getCategoryBrandMap() {
		return categoryBrandMap;
	}
	public void setCategoryBrandMap(Map categoryBrandMap) {
		this.categoryBrandMap = categoryBrandMap;
	}
}
