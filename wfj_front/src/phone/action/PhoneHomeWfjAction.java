package phone.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
@Controller
public class PhoneHomeWfjAction extends BaseAction {
	@Resource
	private IShopInfoService shopInfoService;
	private List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
	@Resource
	private IProductInfoService productInfoService;
	private String shopInfoId;

	public String gotoShopPage() {
		ShopInfo shopInfo = (ShopInfo) shopInfoService
				.getObjectById(shopInfoId);
		if (shopInfo != null) {
			productInfoList = productInfoService
					.findObjects(" where shopInfoId=" + shopInfoId);
		}
		request.setAttribute("shopInfo", shopInfo);
		request.setAttribute("productInfoList", productInfoList);
		return SUCCESS;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

	public void setProductInfoList(List<ProductInfo> productInfoList) {
		this.productInfoList = productInfoList;
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	
	
}
