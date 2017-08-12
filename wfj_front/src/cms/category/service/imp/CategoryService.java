package cms.category.service.imp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.service.BaseService;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
import cms.category.dao.ICategoryDao;
import cms.category.pojo.CmsCategory;
import cms.category.service.ICategoryService;
/**
 *  
 * 文章分类service
 */
public class CategoryService extends BaseService<CmsCategory> implements ICategoryService {
	 private ICategoryDao categoryDao;
     private IArticleService articleService;
	public void setCategoryDao(ICategoryDao categoryDao) {
		this.baseDao=this.categoryDao = categoryDao;
	}
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	/**
	 * 根据id查找子分类对象
	 * 
	 * @param id
	 *            
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List queryByParentId(String id) {
		return categoryDao.findObjects(null,"where 1=1 and o.parentId="+id+"order by o.sortCode");
	}
	/**
	 * 修改父亲的节点状态为1
	 * 
	 * @param categoryId
	 *            
	 * @return 
	 */
	public void updateFatherIsLeaf(String categoryId) {
		CmsCategory category = (CmsCategory)categoryDao.getObjectById(categoryId);
		category.setIsLeaf(0);
		categoryDao.updateObject(category);
	}
	/**
	 * 根据id删除分类
	 * 
	 * @param key, ids
	 *            
	 * @return Boolean
	 */
	public Boolean deleteCategoryByIds(String key, String ids) {
		String [] idss=ids.split(",");
		String queryParams=" where 1=1 and ";
		if(idss.length>1){//删除多个对象
			queryParams+="(";
			for(int i=0;i<idss.length;i++){
				if(i==0){
					queryParams+="o."+key+"='"+idss[i]+"'";
				}else{
					queryParams+=" or o."+key+"='"+idss[i]+"'";
				}
			}
			queryParams+=")";
		}else{
			queryParams+=" o."+key+"='"+idss[0]+"'";//删除一个对象
		}
		CmsCategory category = (CmsCategory)categoryDao.getObjectById(idss[0]);
		if(category.getIsLeaf()==1){
			return baseDao.deleteByParams(queryParams);
		}else{
			return false;
		}
	}
	/**
	 * 删除当前分类 
	 * 
	 * @param ids
	 *            
	 * @return String
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public String deleteCategoryAndArticleByIds(String ids) {
		 //strFlag="1"，有文章 。strFlag="2" 可以删除，既是叶子节点也没有文章
		 String strFlag="";
			 //1判断当前分类下面是否有文章，有文章则不能删除
			 Integer count = articleService.getCount("where 1=1 and o.categoryId="+ids);
			 if(count==0){
			 //2删除
				 //2.1 删除前先取出来给后面的使用 ,当前分类的父分类 
				 Integer categoryId = ((CmsCategory)getObjectById(ids)).getParentId();
				 boolean boole = categoryDao.deleteByParams("where 1=1 and o.categoryId="+ids);
				 strFlag="2";
		        //2.2修改节点,检查父分类下面是否还有子分类 ，如果没有把isLeaf置为：0 ,否则不变  1是叶子，0 不是叶子 
				 List<CmsCategory> list = categoryDao.findObjects(null,"where 1=1 and o.parentId="+categoryId);
				 //通过上面整个方法验证，当没查到数据的时候，getCount得到的长度是1
				 Integer count2 = categoryDao.getCount("where 1=1 and o.parentId="+categoryId);
					if(count2==1){
						CmsCategory category2 = (CmsCategory)categoryDao.getObjectById(categoryId.toString());
						category2.setIsLeaf(1);
						categoryDao.updateObject(category2);
					}
			 }else{
				 strFlag="1";
			 }
		return strFlag;
	}
    //查询某个大分类下面对应的小分类及对应的文章列表
	@SuppressWarnings("unchecked")
	public Map<String, List<CmsArticle>> getCategoryAndArticleList(Integer parentId) {
		Map<String,List<CmsArticle>> categoryAndArticleMap = new HashMap<String, List<CmsArticle>>();
		String[] categorParams ={"categoryId","categoryName","parentId"};
		List<CmsCategory> categoryList =(List<CmsCategory>)categoryDao.findObjects(categorParams,"where 1=1 and o.parentId="+parentId);
		for(CmsCategory cmsCategory:categoryList){
			String categoryName = cmsCategory.getCategoryName();
			Integer categoryId = cmsCategory.getCategoryId();
			String[] cmsParams ={"categoryId","articleId","title","brief","createTime","imgUrl"};
			List<CmsArticle> articleList = articleService.findObjects(cmsParams,"where 1=1 and o.categoryId="+categoryId+" order by o.articleId desc");
			categoryAndArticleMap.put(categoryName, articleList);
		}
		return categoryAndArticleMap;
	}
}
