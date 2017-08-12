package shop.shops.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.shops.pojo.Shops;
import shop.shops.pojo.ShopsShopinfo;
import shop.shops.service.IShopsService;
import shop.shops.service.IShopsShopinfoService;
import shop.store.pojo.ShopCategory;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopCategoryService;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import util.other.Utils;
/**
 * 商城与店铺关系action的操作
 * @author 郑月龙
 */
@SuppressWarnings("serial")
public class ShopsShopinfoAction extends BaseAction {
	/**商城与店铺关系Service*/
	private IShopsShopinfoService shopsShopinfoService;
	/**商城(线下实际商城)Service*/
	private IShopsService shopsService;
	/**店铺service*/
	private IShopInfoService shopInfoService;
	/**店铺分类service*/
	private IShopCategoryService shopCategoryService;
	/**商城与店铺关系对象*/
	private ShopsShopinfo shopsShopinfo;
	/**商城与店铺关系对象ID*/
	private String shopsShopinfoId;
	/**商城与店铺关系对象ID串*/
	private String ids;
	/**商城(线下实际商城)对象*/
	private Shops shops;
	/**商城对象ID*/
	private String shopsId;
	/**商城对象集合*/
	private List<ShopsShopinfo> shopsShopinfoList = new ArrayList<ShopsShopinfo>();
	private List<Map> shopsShopinfoListMap = new ArrayList<Map>();
	/**店铺名称*/
	private String shopName;
	/**店铺对象*/
	private ShopInfo shopInfo;
	/**店铺对象集合*/
	private List<ShopInfo> shopInfoList = new ArrayList<ShopInfo>();
	/**店铺管理员名称*/
	private String customerName;
	/**店铺分类对象集合*/
	private List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
	/**店铺分类对象*/
	private ShopCategory shopCategory;
	/**店铺分类ID*/
	private String shopCategoryId;
	/**店铺id字符串*/
	private String shopInfoIds;
	
	
	/**
	 * 跳转到商城列表页面
	 */
	public String gotoShopsShopinfoPage() throws Exception {
		shops = (Shops) shopsService.getObjectById(shopsId);
		return SUCCESS;
	}
	
	/**
	 * 异步查询商城与店铺信息
	 */
	public void findShopsShopinfoList() throws Exception{
		//定义查询语句
		StringBuffer hqlsb = new StringBuffer();
		//定义统计记录数量语句
		StringBuffer countHql = new StringBuffer();
		countHql.append("select count(ss.shopsShopinfoId) as shopsShopinfoId from Shops s, ShopsShopinfo ss, ShopInfo si, ShopCategory sc"
				+ " where ss.shopsId=s.shopsId and ss.shopInfoId=si.shopInfoId and ss.shopCategoryId=sc.shopCategoryId and s.shopsId="+shopsId);
		hqlsb.append(" select ss.shopsShopinfoId as shopsShopinfoId, ss.shopsId as shopsId, s.shopsName as shopsName, "
				+ "si.shopName as shopName, si.customerName as customerName, sc.shopCategoryName as shopCategoryName"
				+ " from Shops s, ShopsShopinfo ss, ShopInfo si, ShopCategory sc"
				+ " where ss.shopsId=s.shopsId and ss.shopInfoId=si.shopInfoId and ss.shopCategoryId=sc.shopCategoryId and s.shopsId="+shopsId);
		
		//查询条件：店铺名称shopName不为空
		if(Utils.objectIsNotEmpty(shopName)){
			hqlsb.append(" and si.shopName like'%"+shopName+"%'");
			countHql.append(" and si.shopName like'%"+shopName+"%'");
		}
		//排序字段
		hqlsb.append("order by ss.shopsShopinfoId desc");
		//得到记录数量
		int totalRecordCount = shopsShopinfoService.getMoreTableCount(countHql.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		shopsShopinfoListMap = shopsShopinfoService.findListMapPage(hqlsb.toString(),pageHelper);
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", shopsShopinfoListMap);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 保存或修改对象
	 * @throws Exception 
	 */
	public void saveOrUpdateShopsShopinfo() throws Exception {
		String[] split = shopInfoIds.split(",");
		if(Utils.objectIsNotEmpty(shopsId)){
			shops = (Shops) shopsService.getObjectById(shopsId);
			for(String s:split){
				shopInfo = (ShopInfo) shopInfoService.getObjectById(s);
				if(Utils.objectIsNotEmpty(shopInfo)){
					ShopsShopinfo shopsShopinfo=new ShopsShopinfo();
					shopsShopinfo.setShopsId(shops.getShopsId());
					shopsShopinfo.setCity(shops.getCity());
					shopsShopinfo.setRegionLocation(shops.getRegionLocation());
					shopsShopinfo.setShopCategoryId(shopInfo.getShopCategoryId());
					shopsShopinfo.setShopInfoId(shopInfo.getShopInfoId());
					shopsShopinfoService.saveOrUpdateObject(shopsShopinfo);
				}
			}
		}
	}
	/**
	 * 删除对象
	 */
	public void deleteShopsShopinfo(){
		//如果shopsId不为空则进行删除一条记录
		if(Utils.objectIsNotEmpty(ids)){
			//根据商城记录id删除记录
			shopsShopinfoService.deleteObjectsByIds("shopsShopinfoId", ids);
		}
	}
	/**
	 * 根据id查询一个对象
	 * @throws IOException
	 */
	public void getShopsShopinfoObject() throws IOException{
		//shops = (Shops)shopsService.getObjectByParams(" where o.shopsId="+shopsId);
		shopsShopinfo = (ShopsShopinfo)shopsShopinfoService.getObjectById(shopsShopinfoId);
		JSONObject jo = JSONObject.fromObject(shopsShopinfo);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 取得店铺类型集合
	 */
	public void findShopCategoryList() throws Exception {
		String hql = "where 1=1 and o.shopCategoryId>1";
		if(Utils.objectIsNotEmpty(shopCategoryId)){
			hql += " and o.shopCategoryId="+shopCategoryId;
		}
		shopCategoryList  = shopCategoryService.findObjects(hql);
		JSONArray jo = JSONArray.fromObject(shopCategoryList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 查询店铺集合
	 */
	public void findShopInfoList() throws Exception {
		String sql = "select o.shopInfoId as shopInfoId from ShopsShopinfo o where 1=1 ";
		if(Utils.objectIsNotEmpty(shopsId)){
			sql += " and o.shopsId="+shopsId;
		}
		//得到当前商城下所有的店铺ID集合
		List<Map> listMap = shopsShopinfoService.findListMapByHql(sql);
		//查询店铺的sql语句
		String hql = " select o.shopInfoId as shopInfoId, o.shopName as shopName, o.customerName as customerName, o.companyName as companyName, o.isPass as isPass from ShopInfo o where 1=1 ";
		//分类ID不为空
		if(Utils.objectIsNotEmpty(shopCategoryId)){
			hql += " and o.shopCategoryId="+shopCategoryId;
		}
		//店铺管理者不为空
		if(Utils.objectIsNotEmpty(customerName)){
			hql += " and o.customerName like'%"+customerName+"%'";
		}
		//店铺名称不为空
		if(Utils.objectIsNotEmpty(shopName)){
			hql += " and o.shopName like'%"+shopName+"%'";
		}
		//查到店铺集合
		List<Map> list = shopsShopinfoService.findListMapByHql(hql);
		//用来存放一页店铺信息
		List<Map> shopInfoListMap = new ArrayList<Map>();
		//去除已经被当前店铺已经选择的店铺
		for(Map map :listMap){
			for(int i=0;i<list.size();i++){
				Map map2 = list.get(i);
				if(map2.get("shopInfoId").equals(map.get("shopInfoId"))){
					list.remove(i);//从list中删除一条记录
				}
			}
		}
		//设置分页
		pageHelper.setPageInfo(pageSize, list.size(), currentPage);
		//截取位置开始值
		int fromIndex=0;
		fromIndex = pageHelper.getPageRecordBeginIndex();
		//截取位置结束值
		int toIndex=0;
		toIndex = pageHelper.getPageRecordEndIndex();
		//如果结束值大于总记录值
		if(toIndex>list.size()) toIndex = list.size();
		//取得一页记录
		shopInfoListMap = list.subList(fromIndex,toIndex);
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", list.size());// total键 存放总记录数，必须的
		jsonMap.put("rows", shopInfoListMap);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	public ShopsShopinfo getShopsShopinfo() {
		return shopsShopinfo;
	}

	public void setShopsShopinfo(ShopsShopinfo shopsShopinfo) {
		this.shopsShopinfo = shopsShopinfo;
	}

	public String getShopsShopinfoId() {
		return shopsShopinfoId;
	}

	public void setShopsShopinfoId(String shopsShopinfoId) {
		this.shopsShopinfoId = shopsShopinfoId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<ShopsShopinfo> getShopsShopinfoList() {
		return shopsShopinfoList;
	}

	public void setShopsShopinfoList(List<ShopsShopinfo> shopsShopinfoList) {
		this.shopsShopinfoList = shopsShopinfoList;
	}

	public void setShopsShopinfoService(IShopsShopinfoService shopsShopinfoService) {
		this.shopsShopinfoService = shopsShopinfoService;
	}

	public List<Map> getShopsShopinfoListMap() {
		return shopsShopinfoListMap;
	}

	public void setShopsShopinfoListMap(List<Map> shopsShopinfoListMap) {
		this.shopsShopinfoListMap = shopsShopinfoListMap;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopsId() {
		return shopsId;
	}

	public void setShopsId(String shopsId) {
		this.shopsId = shopsId;
	}

	public void setShopsService(IShopsService shopsService) {
		this.shopsService = shopsService;
	}

	public Shops getShops() {
		return shops;
	}

	public void setShops(Shops shops) {
		this.shops = shops;
	}

	public ShopInfo getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(ShopInfo shopInfo) {
		this.shopInfo = shopInfo;
	}

	public List<ShopInfo> getShopInfoList() {
		return shopInfoList;
	}

	public void setShopInfoList(List<ShopInfo> shopInfoList) {
		this.shopInfoList = shopInfoList;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

	public List<ShopCategory> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

	public ShopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public void setShopCategoryService(IShopCategoryService shopCategoryService) {
		this.shopCategoryService = shopCategoryService;
	}

	public String getShopInfoIds() {
		return shopInfoIds;
	}

	public void setShopInfoIds(String shopInfoIds) {
		this.shopInfoIds = shopInfoIds;
	}
}
