package cms.category.service;
import java.util.List;
import java.util.Map;

import util.service.IBaseService;
import cms.article.pojo.CmsArticle;
import cms.category.pojo.CmsCategory;
/**
 *  
 * 文章分类service 接口
 */
public interface ICategoryService extends IBaseService<CmsCategory> {
	/**
	 * 根据id查找子分类对象
	 * 
	 * @param id
	 *            
	 * @return List
	 */
	@SuppressWarnings("rawtypes")
	public List queryByParentId(String id);
	/**
	 * 修改父亲的节点状态为1
	 * 
	 * @param categoryId
	 *            
	 * @return 
	 */
	public void updateFatherIsLeaf(String categoryId);
	/**
	 * 根据id删除分类
	 * 
	 * @param key, ids
	 *            
	 * @return Boolean
	 */
	public Boolean deleteCategoryByIds(String string, String ids);
	/**
	 * 删除当前分类 
	 * 
	 * @param ids
	 *            
	 * @return String
	 */
	public String deleteCategoryAndArticleByIds(String ids);
	public Map<String, List<CmsArticle>> getCategoryAndArticleList(Integer parentId);
}
