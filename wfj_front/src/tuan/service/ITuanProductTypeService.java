package tuan.service;

import java.util.List;

import tuan.pojo.TuanProductType;
import util.service.IBaseService;
/**
 * 团购商品分类Service接口
 * @author 
 *
 */
public interface ITuanProductTypeService  extends IBaseService <TuanProductType> {
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
       
	 * @param productTypeId   商品分类ID
	 */
	public void updateFatherLoadType(String productTypeId);
	/**
	 * 根据父ID查询子列表
	 * 
	 * @param id 商品分类父ID
	 * 
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("rawtypes")
	public List queryByParentId(String id);
}
