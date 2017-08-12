package shop.front.shoppingOnLine.service.imp;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import rating.buyersRecord.dao.IBuyersRecordDao;
import shop.customer.pojo.Customer;
import shop.front.shoppingOnLine.dao.IShoppingCartDao;
import shop.front.shoppingOnLine.pojo.ShoppingCart;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.product.dao.IProductInfoDao;
import shop.product.pojo.ProductInfo;
import shop.store.dao.IMemberShipDao;
import shop.store.pojo.MemberShip;
import util.other.Utils;
import util.service.BaseService;
/**
 * ShoppingCartService - 类描述信息
 */
public class ShoppingCartService extends BaseService<ShoppingCart> implements IShoppingCartService {
	private IShoppingCartDao shoppingCartDao;
	private IProductInfoDao productInfoDao;
	/**店铺会员信息**/
	private IMemberShipDao memberShipDao;
	/**商品成交价格**/
	private BigDecimal buyPrice;
	/**会员等级升迁记录表service**/
	private IBuyersRecordDao buyersRecordDao;
	/**buyPrice是否为会员价**/
	private String buyPriceCustomer="0";
  	/**
  	 * 添加或者修改购物车数据
  	 * @param productId商品Id
  	 * @param num数量
  	 * @param buyType购买方式
  	 * @param cookies
  	 * @param customer操作用户
  	 * @return
  	 */
  	public boolean saveOrUpdateCookieCar(String cookieValue,Customer customer){
  		boolean flag=true;
  		try{
  			//保存cookie中购物车中商品		
  			JSONArray ja = JSONArray.fromObject(cookieValue);
  			for(Object obj : ja){
  				JSONObject jo = JSONObject.fromObject(obj);
  				flag = saveOrUpdateCar(Integer.parseInt(jo.getString("productId")),jo.getString("num"),jo.getString("shopInfoId"),customer,jo.getString("stockUpDate"),jo.getString("sku"));
  				if(flag==false){
  					break;
  				}
  			}
  		}catch(Exception e){
  			e.printStackTrace();
  			flag = false;
  		}
  		return flag;
  	}
  	/**
  	 * 实际保存商品添加到购物车之后
  	 * @param productId
  	 * @param num
  	 * @param buyType
  	 * @param customer
  	 */
  	public boolean saveOrUpdateCar(Integer productId,String num,String shopInfoId,Customer customer,String stockUpDate,String sku){
  		if(customer!=null){
  			ShoppingCart oldShoppingCart = new ShoppingCart();
  			oldShoppingCart = (ShoppingCart) shoppingCartDao.get("where o.productId="+productId+"  and o.customerId="+customer.getCustomerId());
  			if(null!=oldShoppingCart){//当前登录人的购物车中有此商品时
  				oldShoppingCart.setStockUpDate(stockUpDate);//预计发货日
  				oldShoppingCart.setQuantity(oldShoppingCart.getQuantity()+Integer.parseInt(num));
  				oldShoppingCart.setSku(sku);//订货号
  				shoppingCartDao.saveOrUpdateObject(oldShoppingCart);
  			}else{//没有此数据
  				ShoppingCart shoppingCart = new ShoppingCart();
  				shoppingCart.setCustomerId(customer.getCustomerId());
  				shoppingCart.setCreateTime(new Date());
  				shoppingCart.setProductId(productId);
  				shoppingCart.setQuantity(Integer.parseInt(num));
  				shoppingCart.setShopInfoId(Integer.parseInt(shopInfoId));
  				shoppingCart.setStockUpDate(stockUpDate);//预计发货日
  				shoppingCart.setSku(sku);//订货号
  				shoppingCartDao.saveOrUpdateObject(shoppingCart);
  			}
  			return true;
  		}else{
  			return false;
  		}
  	}
  	/**
  	 * 根据sku批量保存商品添加到购物车
  	 * @param productData
  	 */
  	public boolean saveOrUpdateShopCarBySku(Customer customer,String productData){
  		if(customer!=null){
  			ShoppingCart oldShoppingCart = new ShoppingCart();
  			String[] productDatas = productData.split(",");
  			for(int i=0;i<productDatas.length;i++){//批量添加
				String[] idAndCount = productDatas[i].split("@");//0:sku;1:count
				String sku = idAndCount[0];
				ProductInfo productInfo = productInfoDao.get(" where o.sku='"+sku+"'");//根据sku查询一条记录
				MemberShip memberShip = memberShipDao.get(" where state=2 and o.customerId="+customer.getCustomerId()+" and o.shopInfoId="+productInfo.getShopInfoId());
				if(Utils.objectIsNotEmpty(productInfo)){
					oldShoppingCart = (ShoppingCart) shoppingCartDao.get("where o.productId="+productInfo.getProductId()+" and o.customerId="+customer.getCustomerId());
				}
				if(null!=oldShoppingCart){//当前登录人的购物车中有此商品时	
					oldShoppingCart.setStockUpDate(String.valueOf(productInfo.getStockUpDate()));//预计发货日
					oldShoppingCart.setQuantity(oldShoppingCart.getQuantity()+Integer.parseInt(idAndCount[1]));
					oldShoppingCart.setSku(sku);//订货号	
					shoppingCartDao.saveOrUpdateObject(oldShoppingCart);
				}else{//没有此数据	
					ShoppingCart shoppingCart = new ShoppingCart();
					shoppingCart.setCustomerId(customer.getCustomerId());
					shoppingCart.setCreateTime(new Date());
					shoppingCart.setProductId(productInfo.getProductId());
					shoppingCart.setQuantity(Integer.parseInt(idAndCount[1]));
					shoppingCart.setShopInfoId(productInfo.getShopInfoId());
					shoppingCart.setStockUpDate(String.valueOf(productInfo.getStockUpDate()));//预计发货日
					shoppingCart.setSku(sku);//订货号    
					if(null!=memberShip){
						shoppingCart.setDiscount(memberShip.getDiscount());
					}
					shoppingCartDao.saveOrUpdateObject(shoppingCart);
				}	
			}
  			return true;
  		}else{
  			return false;
  		}
  	}  
  	/**
  	 * 批量保存商品添加到购物车之后
  	 * @param productData
  	 */
  	public boolean saveOrUpdateShopCar(Customer customer,String productData){
  		if(customer!=null){
  			ShoppingCart oldShoppingCart = new ShoppingCart();
  			String[] productDatas = productData.split(",");
  			for(int i=0;i<productDatas.length;i++){//批量添加
  				String[] idAndCount = productDatas[i].split("@");//0:Id;1:count
  				String productId = idAndCount[0];
  				ProductInfo productInfo = productInfoDao.get(" where o.productId="+productId);//根据ID查询一条记录
  				oldShoppingCart = (ShoppingCart) shoppingCartDao.get("where o.productId="+productId+"  and o.customerId="+customer.getCustomerId());
  				if(null!=oldShoppingCart){//当前登录人的购物车中有此商品时
  					oldShoppingCart.setStockUpDate(String.valueOf(productInfo.getStockUpDate()));//预计发货日
  					oldShoppingCart.setQuantity(oldShoppingCart.getQuantity()+Integer.parseInt(idAndCount[1]));
  					oldShoppingCart.setSku(productInfo.getSku());//商品SKU订货号
  					shoppingCartDao.saveOrUpdateObject(oldShoppingCart);
  				}else{//没有此数据
  					ShoppingCart shoppingCart = new ShoppingCart();
  					shoppingCart.setCustomerId(customer.getCustomerId());
  					shoppingCart.setCreateTime(new Date());
  					shoppingCart.setProductId(Integer.parseInt(productId));
  					shoppingCart.setQuantity(Integer.parseInt(idAndCount[1]));
  					shoppingCart.setShopInfoId(productInfo.getShopInfoId());
  					shoppingCart.setStockUpDate(String.valueOf(productInfo.getStockUpDate()));//预计发货日
  					shoppingCart.setSku(productInfo.getSku());//商品SKU订货号
  					shoppingCartDao.saveOrUpdateObject(shoppingCart);
  				}
  			}
  			return true;
  		}else{
  			return false;
  		}
  	}  
  	/**
	 * 计算商品会员价
	 */
	@SuppressWarnings("rawtypes")
	public void getCustomerProductPrice(Customer customer,BigDecimal marketPrice,BigDecimal salesPrice){
		if(customer!=null){
			String sql = "select br.levelDiscountValue as levelDiscountValue, br.buyersLevel as buyersLevel from BuyersRecord br "
					+ "where br.customerId="+customer.getCustomerId()
					+ "order by br.optionDate desc";
			List<Map> listbs= buyersRecordDao.findListMapByHql(sql);
			if(Utils.objectIsNotEmpty(marketPrice)){
				if(marketPrice.compareTo(new BigDecimal(0))>0){//将0与市场价格比较
					if(listbs!=null&&listbs.size()>0){
						Map bs = listbs.get(0);
						Object object = bs.get("levelDiscountValue");
						Integer buyersLevel = Integer.valueOf(bs.get("buyersLevel").toString());
						if(object!=null){
							//会员等级大于等于3
							if(buyersLevel>=3){
								BigDecimal bilv =new BigDecimal(String.valueOf(object));
								bilv = bilv.divide(new BigDecimal(100),2, BigDecimal.ROUND_HALF_EVEN);//0.几的比率
								BigDecimal discount = bilv.multiply(salesPrice);//优惠价
								discount = salesPrice.subtract(discount);//实际售价
								//将BigDecimal 转换成Integer
								String str = String.valueOf(discount);
								String bilv1 = String.valueOf(bilv);
								//判断str是否包含“.”
								if(str.indexOf(".")>0&&bilv1.indexOf(".")>0){
									str=str.substring(0, str.lastIndexOf("."));
									buyPrice = discount.divide(new BigDecimal(1),2, BigDecimal.ROUND_HALF_EVEN);
									buyPriceCustomer="1";
								}
							}else{
									buyPrice= salesPrice;//销售价
							}
						}
					}
				}
			}
		}
	}
  	/**
  	 * 查看购物车信息
  	 * @param customer
  	 * @param cookies
  	 * @return
  	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<Object,Object> gotoShoppingCart(Customer customer,Cookie[] cookies,Map cartCountMap){
		Map<Object,Object> cartMap = new HashMap<Object,Object>();
		if(customer!=null){
    		List<ShoppingCart> listShopInfo= baseDao.findUnSame("shopInfoId"," where o.customerId="+customer.getCustomerId());
    		BigDecimal count=new BigDecimal(0);
        	//购物车信息	
        	for(int i=0;i<listShopInfo.size();i++){
        		String hql="select c.productFullName as productFullName , c.storeNumber as storeNumber, a.quantity as quantity, a.stockUpDate as stockUpDate,a.discount as discount, c.sku as sku, c.marketPrice as marketPrice, a.shopCartId as shopCartId,b.shopInfoId as shopInfoId,c.productId as productId ," +
    			" c.describle as describle, c.productName as productName,c.salesPrice as salesPrice,d.brandId as brandId," +
    			" b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale, c.stockUpDate as stockUpDate,"+
    			" b.shopName as shopName,b.customerName as customerName,c.logoImg as smallImgUrl ,c.freightPrice as freightPrice from ShoppingCart a ,ShopInfo b ,ProductInfo c ,Brand d " +
    			" where a.productId=c.productId and a.shopInfoId=b.shopInfoId and d.brandId=c.brandId and a.shopInfoId="+listShopInfo.get(i) +" and a.customerId="+customer.getCustomerId();
        		List<Map> list= shoppingCartDao.findListMapByHql(hql);
        		if(Utils.collectionIsNotEmpty(list)){
        			count=count.add(new BigDecimal(list.size()));//追加商品数量	
        			for(Map map:list){
        				Integer j=0;
        				if(Integer.parseInt(String.valueOf(map.get("shopIsPass")))!=3){
        					j=1;
        				}
        				if(Integer.parseInt(String.valueOf(map.get("isClose")))!=0){
        					j=1;
        				}
        				if(Integer.parseInt(String.valueOf(map.get("isPass")))!=1){
        					j=1;
        				}
        				if(Integer.parseInt(String.valueOf(map.get("isPutSale")))!=2){
        					j=1;
        				}
        				map.put("flag", j);
        				//计算成交价格
//        				buyPrice=new BigDecimal(0);
//        				buyPriceCustomer="0";
//        				getCustomerProductPrice(customer,new BigDecimal(String.valueOf(map.get("marketPrice"))),new BigDecimal(String.valueOf(map.get("salesPrice"))));
//        				map.put("buyPrice", buyPrice);
//        				map.put("buyPriceCustomer", buyPriceCustomer);
        			}
        			cartMap.put(listShopInfo.get(i), list);
        		}
        	}
        	cartCountMap.put("count", String.valueOf(count));
    	}else{//用户没有登录时直接访问购物车
    		if(Utils.objectIsNotEmpty(cookies)){
    			for(Cookie cookie:cookies){
    				if(cookie.getName().contentEquals("customerCar")&&!"'null'".equals(cookie.getValue())){
    					BigDecimal count=new BigDecimal(0);
    					try {
    						String cookieValues = URLDecoder.decode(cookie.getValue(), "UTF-8");//cookie解码
    						JSONArray ja = JSONArray.fromObject(cookieValues);//json数组格式化
    						Set<String> shopInfoSet = new HashSet<String>();
    						for(Object ob :ja){//获取当前cookie中店铺id,Set集合去重
    							if(!ob.equals("null")){
    								JSONObject jo = JSONObject.fromObject(ob);
    								shopInfoSet.add(jo.getString("shopInfoId"));
    							}
    						}
    						List<Map<String,String>> listShopProd=new ArrayList<Map<String,String>>();//存放cookie中的商品中的店铺和商品信息，以map格式存放
    						for(String shopInfoId:shopInfoSet){
    							Map<String,String> map = new HashMap<String,String>();//存放当前店铺下的商品
    							String productIds="";
    							for(Object ob :ja){
    								if(!ob.equals("null")){
    									JSONObject jo = JSONObject.fromObject(ob);
    									if(shopInfoId.equals(jo.getString("shopInfoId"))){
    										productIds=productIds+","+jo.getString("productId");
    									}
    								}
    							}
    							productIds =productIds.substring(1, productIds.length());
    							map.put("shopInfoId", shopInfoId);
    							map.put("productId", productIds);
    							listShopProd.add(map);
    						}
    						//购物车信息
    						for(Map<String,String> map:listShopProd){
    							String hql="select c.productFullName as productFullName , c.storeNumber as storeNumber, c.marketPrice as marketPrice, b.shopInfoId as shopInfoId,c.productId as productId ," +
    									" c.describle as describle, c.productName as productName,c.salesPrice as salesPrice,d.brandId as brandId," +
    									" b.isPass as shopIsPass ,b.isClose as isClose ,c.isShow as isShow ,c.isPass as isPass,c.isPutSale as isPutSale, c.stockUpDate as stockUpDate,c.sku as sku,"+
    									" b.shopName as shopName,b.customerName as customerName,c.logoImg as smallImgUrl  from ShopInfo b ,ProductInfo c ,Brand d " +
    									" where d.brandId=c.brandId  and  b.shopInfoId="+map.get("shopInfoId") +" and c.productId in ("+map.get("productId")+")";
    							List<Map> list= shoppingCartDao.findListMapByHql(hql);
    							//cartMap中加入数量和购买方式
    							if(list!=null&&list.size()>0){
    								for(Map m:list){
    									for(Object ob :ja){
    										if(!ob.equals("null")){
    											JSONObject jo = JSONObject.fromObject(ob);
    											if(jo.getString("productId").equals(m.get("productId").toString())){
    												m.put("quantity", Integer.parseInt(jo.getString("num")));
    												m.put("stockUpDate", jo.get("stockUpDate"));//预计发货日
    												m.put("sku", jo.get("sku"));//商品SKU订货号
    											}
    											jo.getString("productId");
    										}
    									}
    								}
    							}
    							if(Utils.collectionIsNotEmpty(list)){
    								count=count.add(new BigDecimal(list.size()));//追加商品数量
    								for(Map map1:list){
    									Integer j=0;
    									if(Integer.parseInt(String.valueOf(map1.get("shopIsPass")))!=3){
    										j=1;
    									}
    									if(Integer.parseInt(String.valueOf(map1.get("isClose")))!=0){
    										j=1;
    									}
    									if(Integer.parseInt(String.valueOf(map1.get("isPass")))!=1){
    										j=1;
    									}
    									if(Integer.parseInt(String.valueOf(map1.get("isPutSale")))!=2){
    										j=1;
    									}
    									map1.put("flag", j);
    									map1.put("buyPrice", String.valueOf(map1.get("salesPrice")));
    			        				map1.put("buyPriceCustomer", "0");
    								}
    								cartMap.put(map.get("shopInfoId"), list);
    							}
    						}
    						cartCountMap.put("count", String.valueOf(count));
    					} catch (Exception e) {
    						e.printStackTrace();
    					}
    				}else{
    					cartCountMap.put("count", "0");
    				}
    			}
    		}else{
    			cartCountMap.put("count", "0");
    		}
    	}
    	return cartMap;
    }
	//去重方法
	public List<ShoppingCart> findUnSame(String field,String where){
		return  baseDao.findUnSame(field,where);
	}
	public void setShoppingCartDao(IShoppingCartDao shoppingCartDao) {
		this.baseDao=this.shoppingCartDao = shoppingCartDao;
	}
	public void setProductInfoDao(IProductInfoDao productInfoDao) {
		this.productInfoDao = productInfoDao;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public void setBuyersRecordDao(IBuyersRecordDao buyersRecordDao) {
		this.buyersRecordDao = buyersRecordDao;
	}
	public String getBuyPriceCustomer() {
		return buyPriceCustomer;
	}
	public void setBuyPriceCustomer(String buyPriceCustomer) {
		this.buyPriceCustomer = buyPriceCustomer;
	}
	public void setMemberShipDao(IMemberShipDao memberShipDao) {
		this.memberShipDao = memberShipDao;
	}    
}
