package tuan.service.imp;

import java.util.List;

import tuan.dao.ITuanProductTypeDao;
import tuan.pojo.TuanProductType;
import tuan.service.ITuanProductTypeService;
import util.service.BaseService;
/**
 * 团购商品分类Service接口实现类
 * @author 
 *
 */
public class TuanProductTypeService extends BaseService  <TuanProductType> implements ITuanProductTypeService{
	private ITuanProductTypeDao tuanProductTypeDao;

	public void setTuanProductTypeDao(ITuanProductTypeDao tuanProductTypeDao) {
		this.baseDao = this.tuanProductTypeDao = tuanProductTypeDao;
	}
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
       
	 * @param productTypeId   商品分类ID
	 */
	public void updateFatherLoadType(String productTypeId) {
		TuanProductType tuanProductType = (TuanProductType)tuanProductTypeDao.getObjectById(productTypeId);
		tuanProductType.setLoadType("2");
		tuanProductTypeDao.updateObject(tuanProductType);
	}
	/**
	 * 根据父ID查询子列表
	 * 
	 * @param id 商品分类父ID
	 * 
	 * @return 返回一个list集合
	 */
	public List queryByParentId(String id) {
		return tuanProductTypeDao.findObjects(null," where 1=1 and o.parentId="+id);
	}
}