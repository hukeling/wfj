package shop.common.service.imp;
import java.util.List;

import shop.common.dao.IDistrictInfoDao;
import shop.common.pojo.DistrictInfo;
import shop.common.service.IDistrictInfoService;
import util.service.BaseService;
/**
 * DistrictInfoService - 地区信息Service接口实现类
 */
@SuppressWarnings("all")
public class DistrictInfoService extends BaseService<DistrictInfo> implements IDistrictInfoService{
	private IDistrictInfoDao districtInfoDao;//地区信息Dao
	public void setDistrictInfoDao(IDistrictInfoDao districtInfoDao) {
		this.baseDao = this.districtInfoDao = districtInfoDao;
	}
	/**
	 * 修改父亲的节点状态为2
	 * 1：叶子：显示删除
       2：非叶子：不显示删除
	 * @param productTypeId   商品分类ID
	 * 
	 */
	public void saveOrUpdateFatherLoadType(String districtInfoId) {
		DistrictInfo districtInfo = (DistrictInfo)districtInfoDao.getObjectById(districtInfoId);
		districtInfo.setLoadType("2");
		districtInfoDao.updateObject(districtInfo);
	}
	/**
	 * 根据父ID查询子列表
	 * 
	 * @param id 商品分类父ID
	 * 
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("unchecked")
	public List queryByParentId(String id) {
		return districtInfoDao.findObjects(" where 1=1 and o.parentId="+id);
	}
}
