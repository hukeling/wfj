package shop.common.service;
import java.util.List;

import shop.common.pojo.DistrictInfo;
import util.service.IBaseService;
/**
 * IDistrictInfoService - 地区信息Service接口
 */
@SuppressWarnings("all")
public interface IDistrictInfoService extends IBaseService <DistrictInfo>{
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
	 * @param productTypeId   商品分类ID
	 * 
	 */
	public void saveOrUpdateFatherLoadType(String districtInfoId);
	/**
	 * 根据父ID查询子列表
	 * 
	 * @param id 商品分类父ID
	 * 
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("unchecked")
	public List queryByParentId(String id);
}
