package shop.front.customer.action;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rating.buyersRecord.service.IBuyersRecordService;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerCollectProductService;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductImgService;
import shop.product.service.IProductInfoService;
import util.action.BaseAction;
public class AccountWishListAction extends BaseAction {
	private IProductInfoService productInfoService;
	@SuppressWarnings("unused")
	private IProductImgService productImgService;
	private ICustomerCollectProductService customerCollectProductService;
	private IShoppingCartService shoppingCartService;
	@SuppressWarnings("unchecked")
	private List<Map> products; //收藏产品列表
	private static final long serialVersionUID = 1L;
	//排序字段
	private String latest;
	private String popular;
	private String price;
	//批量删除
	private String productIds;
	//批量添加到购物车
	private String productIdsJson;
	/******计算商品折扣价 begin******/
	private IBuyersRecordService buyersRecordService;
	private BigDecimal buyPrice;
	private Integer discount;
	private Integer cdiscount;
	private Integer clevel;
	/******计算商品折扣价 end******/
	/**
	 * 收藏列表
	 * @return
	 */
	public String wishList(){
		products = getProductList("1".equals(latest),"1".equals(popular), util.other.Utils.parseInt(price, -1));
		return SUCCESS;
	}
	/**
	 * 收藏表格
	 * @return
	 */
	public String wishGallery(){
		return wishList();
	}
	/**
	 * 批量删除wish product
	 * @throws IOException 
	 */
	public void deleteWishProduct() throws IOException{
		if(productIds!=null){
			boolean rs =false;
			try {
				rs = customerCollectProductService.deleteObjectsByIds("customerCollectProductId", productIds);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().print(String.format("{\"success\":%s}", rs));
		}
	}
	/**
	 * 添加到购物车
	 * @throws IOException
	 */
	public void addToCart() throws IOException{
		if(productIdsJson!=null){
			Customer customer = (Customer)session.getAttribute("customer");
			int userId =((Customer) session.getAttribute("customer")).getCustomerId();
			boolean rs = false;
			try {
				ProductInfo proInfo = new ProductInfo();
				JSONArray  array = JSONArray.fromObject(productIdsJson);
				for (int i = 0; i < array.size(); i++) {
					JSONObject j = array.getJSONObject(i);
					int sid = j.getInt("sid");
					int pid = j.getInt("pid");
					String stockUpDate = j.getString("stockUpDate");
					proInfo = (ProductInfo) productInfoService.getObjectById(String.valueOf(pid));
					ShoppingCart cart = new ShoppingCart();
					//查看是否有重复.如果购物车重复 + 1;
					List<ShoppingCart> carts =shoppingCartService.findObjects(String.format(" where customerId=%s and productId=%s and shopInfoId =%s", userId,pid,sid));
					if(!carts.isEmpty()){
						cart = carts.get(0);
						cart.setQuantity(cart.getQuantity()+1);
					}else{
						cart.setShopInfoId(sid);
						cart.setProductId(pid);
						cart.setStockUpDate(stockUpDate);
						cart.setCreateTime(new Date());
						cart.setCustomerId(userId);
						cart.setQuantity(1);
					}
					shoppingCartService.saveOrUpdateObject(cart);
				}
				rs = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.getWriter().print(String.format("{\"success\":%s}", rs));
		}
	}
	/**
	 * 获取列表
	 * @param latest 是否最新
	 * @param popular 是否受欢迎
	 * @param priceOrder 0 升序, 1 降序
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> getProductList(boolean latest,boolean popular,int priceOrder){
		int userId = ((Customer) session.getAttribute("customer")).getCustomerId();
		StringBuffer hql = new StringBuffer("SELECT p.logoImg as logoImg,p.productId as productId, p.productName as productName, p.productFullName as productFullName, p.stockUpDate as stockUpDate, "
				+ "p.marketPrice as marketPrice, p.salesPrice as salesPrice , p.memberPrice as memberPrice, p.isRecommend as isRecommend, "
				+ "p.putSaleDate as putSaleDate, p.totalSales as totalSales,p.describle as describle,s.shopName as shopName ,"
				+ "s.shopInfoId as shopInfoId ,c.customerCollectProductId as customerCollectProductId "
				+ "FROM ProductInfo p, CustomerCollectProduct c,ShopInfo s "
				+ "WHERE p.productId = c.productId and p.shopInfoId = s.shopInfoId and c.customerId =  ").append(userId);
		StringBuffer hql_count = new StringBuffer("SELECT count(p.productId) "
				+ "FROM ProductInfo p, CustomerCollectProduct c,ShopInfo s "
				+ "WHERE p.productId = c.productId and p.shopInfoId = s.shopInfoId and c.customerId =  ").append(userId);
		if(latest){ //最新的
			hql.append(" order by p.isNew,p.putSaleDate desc ");
		}else if(popular){ //受欢迎的
			hql.append(" order by p.totalSales desc,p.totalHits desc");
		}else if(priceOrder==0){ //价格升序
			hql.append(" order by p.salesPrice ");
		}else if(priceOrder==1){ //价格降序
			hql.append(" order by p.salesPrice desc");
		}else { //默认时间
			hql.append(" order by p.putSaleDate desc");
		}
		//总条数
		int count =productInfoService.getMultilistCount(hql_count.toString());
		pageHelper.setPageInfo(pageSize, count, currentPage);
		//list
		List<Map> list = productInfoService.findListMapPage(hql.toString(), pageHelper);
		return list;
	}
	/**
	 * 计算商品会员价
	 */
	public BigDecimal getCustomerProductPrice(Customer customer,ProductInfo productInfo){
		Integer rebate = 0;
		String sql="";
		sql = "select br.levelDiscountValue as levelDiscountValue, br.buyersLevel as buyersLevel from BuyersRecord br "
				+ "where br.customerId="+customer.getCustomerId()
				+ "order by br.optionDate desc";
		List<Map> listbs= buyersRecordService.findListMapByHql(sql);
		if(productInfo.getMarketPrice()!=null){
			if(productInfo.getMarketPrice().compareTo(new BigDecimal(0))>0){//将0与市场价格比较
				if(listbs!=null&&listbs.size()>0){
					Map bs = listbs.get(0);
					Object object = bs.get("levelDiscountValue");
					Integer buyersLevel = Integer.valueOf(bs.get("buyersLevel").toString());
					if(object!=null){
						//会员等级不等于1
						if(buyersLevel!=1){
							BigDecimal bilv =new BigDecimal(String.valueOf(object));
							bilv = bilv.divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_EVEN);//0.几的比率
							BigDecimal discount = bilv.multiply(productInfo.getSalesPrice());//优惠价
							discount = productInfo.getSalesPrice().subtract(discount);//实际售价
							//将BigDecimal 转换成Integer
							String str = String.valueOf(discount);
							String bilv1 = String.valueOf(bilv);
							//判断str是否包含“.”
							if(str.indexOf(".")>0&&bilv1.indexOf(".")>0){
								str=str.substring(0, str.lastIndexOf("."));
								buyPrice = discount.divide(new BigDecimal(1),2, BigDecimal.ROUND_HALF_EVEN);
							}
							rebate = Integer.valueOf(String.valueOf(object));
							//
							BigDecimal discount2 = productInfo.getMarketPrice().subtract(productInfo.getSalesPrice());//原价与售价的差价
							discount2 = discount2.divide(productInfo.getMarketPrice(),2, BigDecimal.ROUND_HALF_EVEN);//0.几的比率
							discount2 = discount2.multiply(new BigDecimal(100));
							//将BigDecimal 转换成Integer
							String str2 = String.valueOf(discount2);
							//判断str是否包含“.”
							if(str2.indexOf(".")>0){
								str2=str2.substring(0, str2.lastIndexOf("."));
							}
							cdiscount = Integer.parseInt(str2);
						}else{//会员等级等于1
							if(productInfo.getMarketPrice().subtract(productInfo.getSalesPrice()).compareTo(new BigDecimal(0))>0){
									BigDecimal discount = productInfo.getMarketPrice().subtract(productInfo.getSalesPrice());//原价与售价的差价
									discount = discount.divide(productInfo.getMarketPrice(),2, BigDecimal.ROUND_HALF_EVEN);//0.几的比率
									buyPrice= productInfo.getSalesPrice();//售价
									discount = discount.multiply(new BigDecimal(100));
									//将BigDecimal 转换成Integer
									String str = String.valueOf(discount);
									//判断str是否包含“.”
									if(str.indexOf(".")>0){
										str=str.substring(0, str.lastIndexOf("."));
									}
									rebate = Integer.parseInt(str);
									clevel =1;
							}else{
								buyPrice= productInfo.getSalesPrice();//售价
								rebate=0;
							}
						}
					}
				}
			}else{
				buyPrice= new BigDecimal(0);
				rebate=0;
			}
		}
		discount = rebate;
		return buyPrice;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setProductImgService(IProductImgService productImgService) {
		this.productImgService = productImgService;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getProducts() {
		return products;
	}
	@SuppressWarnings("rawtypes")
	public void setProducts(List<Map> products) {
		this.products = products;
	}
	public String getLatest() {
		return latest;
	}
	public void setLatest(String latest) {
		this.latest = latest;
	}
	public String getPopular() {
		return popular;
	}
	public void setPopular(String popular) {
		this.popular = popular;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setCustomerCollectProductService(
			ICustomerCollectProductService customerCollectProductService) {
		this.customerCollectProductService = customerCollectProductService;
	}
	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public String getProductIdsJson() {
		return productIdsJson;
	}
	public void setProductIdsJson(String productIdsJson) {
		this.productIdsJson = productIdsJson;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getCdiscount() {
		return cdiscount;
	}
	public void setCdiscount(Integer cdiscount) {
		this.cdiscount = cdiscount;
	}
	public Integer getClevel() {
		return clevel;
	}
	public void setClevel(Integer clevel) {
		this.clevel = clevel;
	}
	public void setBuyersRecordService(IBuyersRecordService buyersRecordService) {
		this.buyersRecordService = buyersRecordService;
	}
}
