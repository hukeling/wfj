package shop.tags.service.imp;
import shop.tags.dao.IShopTradeTagDao;
import shop.tags.pojo.ShopTradeTag;
import shop.tags.service.IShopTradeTagService;
import util.service.BaseService;
/**
 * 适合行业标签Service接口实现类
 * @author mqr
 */
public class ShopTradeTagService extends BaseService<ShopTradeTag> implements IShopTradeTagService {
	@SuppressWarnings("unused")
	private IShopTradeTagDao shopTradeTagDao;
	public void setShopTradeTagDao(IShopTradeTagDao shopTradeTagDao) {
		this.baseDao = this.shopTradeTagDao = shopTradeTagDao;
	}
}
