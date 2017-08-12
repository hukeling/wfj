package shop.tags.service.imp;
import shop.tags.dao.IShopTradeSituationTagDao;
import shop.tags.pojo.ShopTradeSituationTag;
import shop.tags.service.IShopTradeSituationTagService;
import util.service.BaseService;
/**
 * 适合行业标签和使用场合标签关联表Service接口实现类
 * @author mqr
 */
public class ShopTradeSituationTagService extends BaseService<ShopTradeSituationTag> implements IShopTradeSituationTagService {
	@SuppressWarnings("unused")
	private IShopTradeSituationTagDao shopTradeSituationTagDao;
	public void setShopTradeSituationTagDao(IShopTradeSituationTagDao shopTradeSituationTagDao) {
		this.baseDao = this.shopTradeSituationTagDao = shopTradeSituationTagDao;
	}
}
