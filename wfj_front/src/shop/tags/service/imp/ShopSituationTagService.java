package shop.tags.service.imp;
import shop.tags.dao.IShopSituationTagDao;
import shop.tags.pojo.ShopSituationTag;
import shop.tags.service.IShopSituationTagService;
import util.service.BaseService;
/**
 * 使用场合标签Service接口实现类
 * @author mqr
 */
public class ShopSituationTagService extends BaseService<ShopSituationTag> implements IShopSituationTagService {
	@SuppressWarnings("unused")
	private IShopSituationTagDao shopSituationTagDao;
	public void setShopSituationTagDao(IShopSituationTagDao shopSituationTagDao) {
		this.baseDao = this.shopSituationTagDao = shopSituationTagDao;
	}
}
