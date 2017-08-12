package tuan.action;

import java.math.BigDecimal;

import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.service.ICustomerAcceptAddressService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import tuan.pojo.TuanProduct;
import tuan.service.ITuanProductService;
import util.action.BaseAction;
/**
 * 团购订单Action
 * @author jxw
 * 2015/7/28
 */
@SuppressWarnings("serial")
public class TuanOrderAction extends BaseAction{
	/**
	 * 团购商品Service
	 */
	private ITuanProductService tuanProductService;
	/**
	 * 商品Service
	 */
	private IProductInfoService productInfoService;
	/**
	 * 店铺Service
	 */
	private IShopInfoService shopInfoService;
	/**
	 * 店铺实体类
	 */
	private ShopInfo shopInfo;
	/**
	 * 团购商品实体类
	 */
	private TuanProduct tuanProduct;
	/**
	 * 商品实体类
	 */
	private ProductInfo productInfo;
	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * 客户收货地址Service
	 */
	private ICustomerAcceptAddressService customerAcceptAddressService;
	/**
	 * 收货地址
	 */
	private CustomerAcceptAddress custAddress;
	//团购单邮费
	private BigDecimal freight;
	
	/**
	 * 跳转到团购订单确认页面
	 */
	public String gotoTuanOrder(){
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		//团购商品实体类
		tuanProduct = (TuanProduct) tuanProductService.getObjectByParams(" where o.productId="+productId);
		//商品实体类
		productInfo = (ProductInfo) productInfoService.getObjectByParams(" where o.productId="+productId);
		//店铺实体类
		shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+productInfo.getShopInfoId());
		BigDecimal  postage = shopInfo.getPostage();//邮费
		BigDecimal  minAmount = shopInfo.getMinAmount();//包邮最小订单金额
		if(tuanProduct.getPrice().compareTo(minAmount)<0){
    		freight = postage;
    	}else{
    		freight = new BigDecimal(0);
    	}
		//收货人地址
    	custAddress= (CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams(" where o.customerId="+customer.getCustomerId()+" and o.isSendAddress=1");
		return SUCCESS;
	}

	public TuanProduct getTuanProduct() {
		return tuanProduct;
	}
	public void setTuanProduct(TuanProduct tuanProduct) {
		this.tuanProduct = tuanProduct;
	}
	public ProductInfo getProductInfo() {
		return productInfo;
	}
	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setTuanProductService(ITuanProductService tuanProductService) {
		this.tuanProductService = tuanProductService;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public CustomerAcceptAddress getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(CustomerAcceptAddress custAddress) {
		this.custAddress = custAddress;
	}
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
}
