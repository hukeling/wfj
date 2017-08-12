package phone.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import shop.product.service.IProductInfoService;
import util.action.BaseAction;

@Controller
public class PhoneRecommendAction extends BaseAction {

	@Resource
	private IProductInfoService productInfoService;

	public void getDayRecommend() {
		String hql = "select a.productId as productId,a.shopInfoId as shopInfoId,a.brandId as brandId,"
				+ "a.productName as productName,a.marketPrice as marketPrice,a.salesPrice as salesPrice,"
				+ "a.logoImg from ProductInfo a,DayRecommend b,Brand c "
				+ "where a.productId=b.productId and a.brandId=c.brandId and b.isShow=1";
		List list = productInfoService.findObjectsByHQL(hql);
		for (Object o : list) {
			System.out.println(o);
		}
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	
	
}
