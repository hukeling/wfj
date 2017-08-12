package phone.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import phone.service.IPhoneCategoryhibService;
import phone.util.JsonIgnore;
import shop.product.pojo.ProductType;
import util.action.BaseAction;
/**
 * 手机端分类查询及商品查询
 * @author hukeling
 *
 */
@SuppressWarnings("all")
public class PhoneCategoryAction extends BaseAction {
	IPhoneCategoryhibService phoneCategoryHibService;
	private String method;// 方法名
	private int productId;// 商品id
	private int productTypeId;// 类别id
	private int level;// 类别等级
	private String orderBy;// 商品排序标准：normal：默认；totalSalesup：销量升序；totalSalesdown:销量降序；priceup:价格升序；pricedown:价格降序
	private String sortName;// 类别名
	private String keyword;//根据商品名进行模糊查询的关键字
	
	/**
	 * 获取手机端分类(所有分类信息)
	 * @throws IOException
	 */
	public void phonecategory() throws IOException {
		List<ProductType> list=new JSONArray();
		list = phoneCategoryHibService.initProdutType();
		JsonIgnore.outputJo(response,list, JsonIgnore.getIgnoreProType());
	}

	/**
	 * 根据父ID(一级id)查询子分类(二级和三级分类)
	 * @throws IOException
	 */
	public String listCategory() throws IOException {
		if (level == 1 && productTypeId != 0) {
			List list = new ArrayList();
			list = phoneCategoryHibService.listCategory(productTypeId);
			ProductType ept = (ProductType) list.get(0);// 获取的第一个二级分类类
			List sanList = ept.getChildProductType();
			ProductType spt = (ProductType) sanList.get(0);// 获取的第一个二级的第一个三级类
			if (list.size() > 0) {
				request.setAttribute("secondCategoryList", list);
				request.setAttribute("ept", ept);
				request.setAttribute("spt", spt);
			}
		}
		return SUCCESS;
	}

	/**
	 * 根据父ID(二级)查询子分类(三级分类)
	 * @throws IOException
	 */
	public void listSanCategory() throws IOException {
			List list = new ArrayList();
			list = phoneCategoryHibService.listSanCategory(productTypeId);
			request.setAttribute("thirdlist", list);
			JsonIgnore.outputJo(response,list, JsonIgnore.getIgnoreProType());
	}
	
	/**
	 * 根据商品分类id(productTypeId)按标准(orderBy)排序的商品(当orderBy为空时无序)
	 * @throws IOException
	 */
	public void selectProInfoByTypeId() throws IOException {
		List list = new ArrayList();
		list = phoneCategoryHibService.selectProInfoByTypeId(productTypeId,
				orderBy);
		request.setAttribute("proInfoList", list);
		JsonIgnore.outputJo(response,list,JsonIgnore.getIgnoreProInfo());
	}
	/**
	 * 根据商品名进行模糊查询
	 * @throws IOException
	 */
	public void mohuSearch() throws IOException{
		keyword=new String(keyword.getBytes("iso8859-1"),"utf-8");
		List list = new ArrayList();
		list = phoneCategoryHibService.mohuSearch(keyword);
		JsonIgnore.outputJo(response,list,JsonIgnore.getIgnoreProInfo());
	}
	
	/**
	 * @param keyword:商品名关键字
	 * @param orderBy:排序方式
	 * @return 根据商品名关键字查询并按照指定标准排序后的所有商品列
	 * @throws IOException 
	 */
	public void mohuSearchOrderBy() throws IOException{
		keyword=new String(keyword.getBytes("iso8859-1"),"utf-8");
		List list = new ArrayList();
		list = phoneCategoryHibService.mohuSearchOrderBy(keyword, orderBy);
		JsonIgnore.outputJo(response,list,JsonIgnore.getIgnoreProInfo());
	}
	/**
	 * 
	 * @return 推荐商品列表
	 * @throws IOException 
	 */
	public void recommandPro() throws IOException{
		List list=new ArrayList();
		list=phoneCategoryHibService.recommandPro();
		JsonIgnore.outputJo(response,list,JsonIgnore.getIgnoreProInfo());
	}
	
	   

	public void setPhoneCategoryHibService(
			IPhoneCategoryhibService phoneCategoryHibService) {
		this.phoneCategoryHibService = phoneCategoryHibService;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	

}
