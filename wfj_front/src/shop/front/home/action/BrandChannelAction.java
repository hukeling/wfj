package shop.front.home.action;
import java.util.ArrayList;
import java.util.List;

import shop.product.pojo.Brand;
import shop.product.service.IBrandService;
import util.action.BaseAction;
/** 
 * BrandChannelAction - 品牌频道 
 * @author 孟琦瑞
 */
public class BrandChannelAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String headerType;
	private IBrandService brandService;//品牌service
	private List<Brand> recommendBrandList= new ArrayList<Brand>();//推荐品牌List
	private List<Brand> othersBrandList= new ArrayList<Brand>();//其他品牌List
	//跳转
	public String gotoBrandChannelPage(){
		//查询推荐品牌
		recommendBrandList=brandService.findObjects( " where o.isRecommend=1 and o.isShow=1 order by o.sortCode");
		//查询其他品牌
		othersBrandList=brandService.findObjects("where o.isRecommend=0 and o.isShow=1 order by o.sortCode");
		return SUCCESS;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public List<Brand> getRecommendBrandList() {
		return recommendBrandList;
	}
	public void setRecommendBrandList(List<Brand> recommendBrandList) {
		this.recommendBrandList = recommendBrandList;
	}
	public List<Brand> getOthersBrandList() {
		return othersBrandList;
	}
	public void setOthersBrandList(List<Brand> othersBrandList) {
		this.othersBrandList = othersBrandList;
	}
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
}
