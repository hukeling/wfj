package shop.shareRigster.service.imp;

import shop.shareRigster.dao.imp.ShareRigsterDao;
import shop.shareRigster.pojo.ShareRigster;
import shop.shareRigster.service.IShareRigsterService;
import util.service.BaseService;
/**
 * 注册与分享service层实现类
 * @author 李慕华
 *
 */
public class ShareRigsterService extends BaseService<ShareRigster> implements IShareRigsterService{
	@SuppressWarnings("unused")
	private ShareRigsterDao shareRigsterDao;

	public void setShareRigsterDao(ShareRigsterDao shareRigsterDao) {
		this.baseDao =this.shareRigsterDao = shareRigsterDao;
	}

}
