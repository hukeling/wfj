package basic.service.imp;
/**
 * 权限Service接口实现类
 * @author ss
 *
 */
import java.util.List;

import util.service.BaseService;
import basic.dao.IPurviewDao;
import basic.pojo.Purview;
import basic.service.IPurviewService;
	public class PurviewService extends BaseService  <Purview> implements IPurviewService {
		private IPurviewDao purviewDao;
		public void setPurviewDao(IPurviewDao purviewDao) {
			this.baseDao = this.purviewDao = purviewDao;
		}
		/**
		 * 根据父ID查询列表
		 * @param ID值
		 * @return 返回一个list集合
		 */
		public List<Purview> queryByParentId(String id) {
			return purviewDao.findObjects(" where 1=1 and o.parentId="+id+" order by o.sortCode");
		}
		/**
		 * 删除权限
		 * @param id 权限id
		 * @return boolean类型的 true 或 false
		 */
		public Boolean deletePurview(String id){
			//获取当前权限对象
			Purview purview = purviewDao.getObjectById(id);
			//删除权限对象
			boolean deleteObjectById = purviewDao.deleteObjectById(id);
			if(deleteObjectById){
				//删除成功，判断该obj的父obj是否改为叶子节点
				Purview pObj = (Purview) purviewDao.get(" where o.purviewId="+purview.getParentId());
				Integer count = purviewDao.getCount(" where o.parentId="+pObj.getPurviewId());
		    	if((count-1)==0){//count为数据库中的总条数 在这里减去要删除的对象
		    		pObj.setIsLeaf(1);
		    		purviewDao.saveOrUpdateObject(pObj);
		    	}
		    	return true;
			}else{
				return false;
			}
		}
	}
