package shop.product.service.imp;
import shop.product.dao.IDeployShowPhotoDao;
import shop.product.pojo.DeployShowPhoto;
import shop.product.service.IDeployShowPhotoService;
import util.service.BaseService;
/**
 *商品图片展示配置
 */
public class DeployShowPhotoService extends BaseService  <DeployShowPhoto> implements IDeployShowPhotoService{
	@SuppressWarnings("unused")
	private IDeployShowPhotoDao deployShowPhotoDao;
	public void setDeployShowPhotoDao(IDeployShowPhotoDao deployShowPhotoDao) {
		this.baseDao = this.deployShowPhotoDao = deployShowPhotoDao;
	}
}