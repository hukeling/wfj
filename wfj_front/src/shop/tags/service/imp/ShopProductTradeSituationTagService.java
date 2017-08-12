package shop.tags.service.imp;
import shop.tags.dao.IShopProductTradeSituationTagDao;
import shop.tags.pojo.ShopProductTradeSituationTag;
import shop.tags.service.IShopProductTradeSituationTagService;
import util.service.BaseService;
/**
 * 适合行业标签Service接口实现类
 * @author mqr
 */
public class ShopProductTradeSituationTagService extends BaseService<ShopProductTradeSituationTag> implements IShopProductTradeSituationTagService {
	@SuppressWarnings("unused")
	private IShopProductTradeSituationTagDao shopProductTradeSituationTagDao;
	public void setShopProductTradeSituationTagDao(IShopProductTradeSituationTagDao shopProductTradeSituationTagDao) {
		this.baseDao = this.shopProductTradeSituationTagDao = shopProductTradeSituationTagDao;
	}
}
