package shop.front.store.action;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.store.pojo.ShopInfo;
import shop.store.pojo.ShopProCategory;
import shop.store.service.IShopInfoService;
import shop.store.service.IShopProCategoryService;
import util.action.BaseAction;
/**
 * ShopInfoAction - 前台店铺内的分类
 * @Author:
 */
@SuppressWarnings("serial")
public class ShopInfoProCategoryAction extends BaseAction {
	/**店铺基本信息Service**/
	private IShopInfoService shopInfoService;
	/**店铺基本信息实体**/
	private ShopInfo shopInfo;
	/**SHOP_店铺内部商品分类service**/
	private IShopProCategoryService shopProCategoryService; 
	/**SHOP_店铺内部商品分类List**/
	private List<ShopProCategory> listShopPc;
	/**店铺内部商品分类对象**/
	private ShopProCategory shopProCategory;
	/**店铺内部商品分类id**/
	private Integer shopProCategoryId;
	/**当前页码**/
	private Integer pageIndex;
	/**店铺分类下的商品list**/
	@SuppressWarnings("unchecked")
	private List<Map> listCategoryProduct;
	/**------------------------------------------------end------------------------------------------------------------------**/
	/**
	 * 跳转到店铺内的商品分类
	 */
	public String toShopCategory(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId ="+customer.getCustomerId());
			if(null!=shopInfo){
				listShopPc = shopProCategoryService.findObjects("where o.parentId>0 and  o.shopInfoId="+shopInfo.getShopInfoId());
			}
		}
		//listShopPc = shopProCategoryService.findObjects("where o.shopInfoId=1");
		return SUCCESS;
	}
	/**查询店铺一个分类**/
	public String addOrEditShopCategory(){
		Customer customer = (Customer) session.getAttribute("customer");
		if(customer!=null){
			shopInfo = (ShopInfo) shopInfoService.getObjectByParams("where o.customerId ="+customer.getCustomerId());
		}
		if(shopProCategory!=null && shopProCategory.getShopProCategoryId()!=null){
			shopProCategory = (ShopProCategory) shopProCategoryService.getObjectByParams(" where o.shopProCategoryId="+shopProCategory.getShopProCategoryId());
		}
		return SUCCESS;
	}
	/**修改或者保存店铺分类**/
	public void saveOrUpdateShopCategory(){
		try {
			if(null!=shopProCategory){
				shopProCategoryService.saveOrUpdateShopCategory(shopProCategory);
			}
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out;
			out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**删除店铺分类**/
	public void deleteShopCategory(){
		try {
			ShopInfo shopInfo = (ShopInfo) session.getAttribute("shopInfo");
			if(shopProCategoryId!=null ){
				shopProCategoryService.deleteObjectByParams(" where o.shopProCategoryId="+shopProCategoryId+" and o.shopInfoId="+shopInfo.getShopInfoId());
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out;
				out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查看当前店铺内部分类下的商品
	 * @return
	 */
	public String findCategoryProduct(){
		if(shopProCategory!=null && shopProCategory.getShopInfoId()!=null){
			listShopPc=shopProCategoryService.findObjects(" where o.shopInfoId="+shopProCategory.getShopInfoId());
			StringBuffer coutHql = new StringBuffer();
			coutHql.append("SELECT count(d.productId) FROM ShopProCategory a ,ShopInfo b,ShopProCateClass c,ProductInfo d where b.shopInfoId = a.shopInfoId and a.shopProCategoryId=c.shopProCategoryId and d.productId=c.productId  and a.shopInfoId="+shopProCategory.getShopInfoId());
			StringBuffer hql = new StringBuffer();
			hql.append("SELECT a.shopProCategoryName as shopProCategoryName, a.shopProCategoryId as shopProCategoryId,b.shopInfoId as shopInfoId,d.productId as productId,d.productName as productName ," +
					" d.salesPrice as salesPrice,d.logoImg as logoImg,d.storeNumber as storeNumber ,d.totalSales as totalSales ,d.isPass as isPass ," +
					" d.isPutSale as isPutSale FROM ShopProCategory a ,ShopInfo b,ShopProCateClass c,ProductInfo d where b.shopInfoId = a.shopInfoId" +
					" and a.shopProCategoryId=c.shopProCategoryId and d.productId=c.productId  and a.shopInfoId="+shopProCategory.getShopInfoId());
			if(shopProCategory.getShopProCategoryId()!=null){
				coutHql.append(" and a.shopProCategoryId="+shopProCategory.getShopProCategoryId());
				hql.append(" and a.shopProCategoryId="+shopProCategory.getShopProCategoryId());
			}
			int totalRecordCount = shopProCategoryService.getMultilistCount(String.valueOf(coutHql));
			if(pageIndex==null){
				pageIndex=1;
			}
			pageHelper.setPageInfo(5, totalRecordCount, currentPage);
			listCategoryProduct = shopProCategoryService.findListMapPage(hql.toString(), pageHelper);
		}
		return SUCCESS;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public ShopInfo getShopInfo() {
		return shopInfo;
	}
	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}
	public void setShopProCategoryService(
			IShopProCategoryService shopProCategoryService) {
		this.shopProCategoryService = shopProCategoryService;
	}
	public List<ShopProCategory> getListShopPc() {
		return listShopPc;
	}
	public void setListShopPc(List<ShopProCategory> listShopPc) {
		this.listShopPc = listShopPc;
	}
	public ShopProCategory getShopProCategory() {
		return shopProCategory;
	}
	public void setShopProCategory(ShopProCategory shopProCategory) {
		this.shopProCategory = shopProCategory;
	}
	public Integer getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(Integer shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getListCategoryProduct() {
		return listCategoryProduct;
	}
	@SuppressWarnings("unchecked")
	public void setListCategoryProduct(List<Map> listCategoryProduct) {
		this.listCategoryProduct = listCategoryProduct;
	}
}
