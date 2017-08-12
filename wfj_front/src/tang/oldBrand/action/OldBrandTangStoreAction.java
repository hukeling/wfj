package tang.oldBrand.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.store.pojo.ShopCategory;
import tang.oldBrand.pojo.OldBrandTangStore;
import tang.oldBrand.service.IOldBrandTangStoreService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import util.other.Utils;
/**
 * 老字号商铺关联Action
 */
@SuppressWarnings("serial")
public class OldBrandTangStoreAction extends BaseAction {
	private IOldBrandTangStoreService oldBrandTangStoreService;//老字号与商铺关联Service 
	private Integer oldBrandId;//老字号ID
	private Integer shopInfoId;//商铺ID
	/**店铺名称*/
	private String shopName;
	/**店铺分类对象*/
	private ShopCategory shopCategory;
	/**店铺分类ID*/
	private String shopCategoryId;
	/**店铺id字符串*/
//	private String shopInfoIds;
	private OldBrandTangStore oldBrandShopInfo;//老字号商铺关联对象
	
	/**
	 * 跳转到老字号店铺列表页
	 * @return
	 */
	public String gotoOldBrandShopinfoPage(){
		return SUCCESS;
	}
	
	public void deleteOldBrandShopinfo(){
		
	}
	
	/**
	 * 根据老字号Id查询审核通过的店铺集合
	 */
	public void findShopInfoList() throws Exception {
		String sql = "select o.shopInfoId as shopInfoId from ShopsShopinfo o where 1=1 ";
		if(Utils.objectIsNotEmpty(oldBrandId)){
			sql += " and o.oldBrandId="+oldBrandId;
		}
		//得到当前商场下所有的店铺ID集合
		List<Map> listMap = oldBrandTangStoreService.findListMapByHql(sql);
		//查询店铺的sql语句
		String hql = " select o.shopInfoId as shopInfoId, o.shopName as shopName, o.customerName as customerName, o.companyName as companyName, o.isPass as isPass from ShopInfo o where 1=1 ";
		//追加查询条件、审核通过（isPass1、待审核2、不通过3、通过）、未关闭（isClose0：不关闭1：关闭）
		hql+=" and o.isPass=3 and o.isClose=0";
		//分类ID不为空
		if(Utils.objectIsNotEmpty(shopCategoryId)){
			hql += " and o.shopCategoryId="+shopCategoryId;
		}
		//店铺名称不为空
		if(Utils.objectIsNotEmpty(shopName)){
			hql += " and o.shopName like'%"+shopName+"%'";
		}
		//查到店铺集合
		List<Map> list = oldBrandTangStoreService.findListMapByHql(hql);
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
	
	



	public void setOldBrandTangStoreService(
			IOldBrandTangStoreService oldBrandTangStoreService) {
		this.oldBrandTangStoreService = oldBrandTangStoreService;
	}

	public OldBrandTangStore getOldBrandShopInfo() {
		return oldBrandShopInfo;
	}

	public void setOldBrandShopInfo(OldBrandTangStore oldBrandShopInfo) {
		this.oldBrandShopInfo = oldBrandShopInfo;
	}

	public void setOldBrandId(Integer oldBrandId) {
		this.oldBrandId = oldBrandId;
	}

	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	 
	 
}
