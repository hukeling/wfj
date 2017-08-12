package tang.tangstore.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import basic.service.IAreaService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import phone.util.Distance;
import phone.util.JsonIgnore;
import phone.util.PhoneStaticConstants;
import tang.tangProduct.pojo.TangProduct;
import tang.tangProduct.service.ITangProductService;
import tang.tangstore.pojo.BusinessHour;
import tang.tangstore.pojo.LandMark;
import tang.tangstore.pojo.StoreImg;
import tang.tangstore.pojo.StoreService;
import tang.tangstore.pojo.TangService;
import tang.tangstore.pojo.TangStore;
import tang.tangstore.pojo.TangTraffic;
import tang.tangstore.service.IStoreImgService;
import tang.tangstore.service.IStoreServiceService;
import tang.tangstore.service.ITangServiceService;
import tang.tangstore.service.ITangStoreService;
import tang.tangstore.service.ITangTrafficService;
import util.action.BaseAction;

public class TangStoreAction extends BaseAction {

	private static final long serialVersionUID = -6419972432207198226L;
	private ITangStoreService tangStoreService;
	private ITangTrafficService tangTrafficService;
	private ITangProductService tangProductService;
	private IStoreServiceService storeServiceService;
	private ITangServiceService tangServiceService;
	private IStoreImgService storeImgService;
	private Integer shopCategoryId;//店铺类别Id
	private Integer storeId;//店铺Id
	private Double long1;//用户当前所在地经度
	private Double lat1;//用户当前所在地纬度
	private Integer currentPage2;//当前页
	private Integer operation;//操作：0或null是加载第一页；1是上一页；2是下一页；3是获取特定页
	private Integer pageNum;//特定页
	/**
	 * 获取首页、默认加载
	 * http://192.168.1.115:8080/wfj_front/phone/tangStore/listTangStore.action?shopCategoryId=3&operation=0
	 * 获取上一页
	 * http://192.168.1.115:8080/wfj_front/phone/tangStore/listTangStore.action?shopCategoryId=3&operation=1&currentPage2=1
	 * 获取下一页
	 * http://192.168.1.115:8080/wfj_front/phone/tangStore/listTangStore.action?shopCategoryId=3&operation=2&currentPage2=1
	 * 获取指定页
	 * http://192.168.1.115:8080/wfj_front/phone/tangStore/listTangStore.action?shopCategoryId=3&operation=3(&currentPage2=1)&pageNum=2
	 * pc端分页（16条/页）获取店铺信息列表页面
	 */
	
	public void listTangStore() throws IOException{
		List<TangStore> tangStoreList=new ArrayList<TangStore>();
		String sql="where o.isShow=1";
		if(shopCategoryId==null){
			
		}else if(shopCategoryId==PhoneStaticConstants.AllOldBrandStore){//查询所有老字号信息
			sql+="and o.shopCategoryId=56 or o.shopCategoryId=57";
		}else if(shopCategoryId==PhoneStaticConstants.AllWZWFJ){//查询所有玩转王府井
			sql+="and o.shopCategoryId>58 and o.shopCategoryId<63";
		}else if(shopCategoryId==PhoneStaticConstants.WfjJingdian){
			sql+="and o.shopCategoryId=62";
		}
		else if(shopCategoryId!=0){//56:老字号吃喝；57：老字号其他；59：娱乐；60：酒店；62：景点
			sql+="and o.shopCategoryId="+shopCategoryId;
		} 
		int totalRecordCount = tangStoreService.getCount(sql);//查询到
		int pageCount=0;
		if(totalRecordCount%16==0){
			pageCount=totalRecordCount/16;
		}else{
			pageCount=totalRecordCount/16+1;
		}
		if(operation==0){
			currentPage2=1;
		}else if(operation==1&&currentPage2>1){
			currentPage2=currentPage2-1;//获取上一页
		}else if(operation==2){
			currentPage2=currentPage2+1;//获取下一页
		}else if(operation==3&&pageNum>1){//获取特定页
			currentPage2=pageNum;
		}
		if(currentPage2==null||currentPage2<1){
			currentPage2=1;
		}else if(currentPage2>pageCount){
			currentPage2=pageCount;
		}
		pageHelper.setPageInfo(PhoneStaticConstants.tangStorePageSize, totalRecordCount, currentPage2);
		tangStoreList = tangStoreService.findListByPageHelper(null,pageHelper,sql);
		
		JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
		jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
		jsonConfig.setExcludes(JsonIgnore.getIgnoreTangStore());
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("totalPage", pageCount);
		jsonMap.put("Data", tangStoreList);
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	 
	
	
	
	/**
	 * http://192.168.1.115:8080/wfj_front/phone/tangStore/getStore.action?shopCategoryId=3&long1=116.520899&lat1=39.912093
	 * 如有shopCategoryId则分类查询店铺
	 * 如无则获取所有店铺信息
	 * @throws Exception 
	 */
	public void getStore() throws Exception{
		String sql="where o.isShow=1";
		
		
		
		
//		查询所有老字号信息
		if(shopCategoryId==null){
			
		}else if(shopCategoryId==PhoneStaticConstants.AllOldBrandStore){//查询所有老字号信息
			sql+="and o.shopCategoryId=56 or o.shopCategoryId=57";
		}else if(shopCategoryId==PhoneStaticConstants.AllWZWFJ){//查询所有玩转王府井
			sql+="and o.shopCategoryId>58 and o.shopCategoryId<63";
		}
//		else if(shopCategoryId==PhoneStaticConstants.OldEat){
//			sql+="and o.shopCategoryId=56";
//		}else if(shopCategoryId==PhoneStaticConstants.OldOther){
//			sql+="and o.shopCategoryId=57";
//		}else if(shopCategoryId==PhoneStaticConstants.WfjYl){
//			sql+="and o.shopCategoryId=59";
//		}else if(shopCategoryId==PhoneStaticConstants.WfjHotel){
//			sql+="and o.shopCategoryId=60";}
		else if(shopCategoryId==PhoneStaticConstants.WfjJingdian){
			sql+="and o.shopCategoryId=62";
		}
		else if(shopCategoryId!=0){//56:老字号吃喝；57：老字号其他；59：娱乐；60：酒店；62：景点
			sql+=" and o.shopCategoryId="+shopCategoryId;
		} 
		
		//定位城市116.5209,39.912093beijing,108.990209,34.253838xian
				JSONObject json=Distance.getCityByXy(long1,lat1);
				JSONObject regeocode=json.getJSONObject("regeocode");
				JSONObject addressComponent=regeocode.getJSONObject("addressComponent");
				Object city=addressComponent.get("city");
				Object province=addressComponent.get("province");
				sql+=" and o.city like '%"+city+"%' or o.city like '%"+province+"%'";
				
		List<TangStore> storeList=new ArrayList<TangStore>();
		List<TangStore> stList=tangStoreService.findObjects(sql);
		if(stList!=null&&stList.size()>0){
			for(TangStore ts:stList){
				TangStore tangStore=this.getNewTangStore(ts);
				storeList.add(tangStore);
			}
		}
		JsonIgnore.outputJo(response, storeList, JsonIgnore.getIgnoreTangStore());
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * http://192.168.1.115:8080/wfj_front/phone/tangStore/getOneStore.action?storeId=38&long1=116.520899&lat1=39.912093
	 * 根据storeId获取一条店铺信息
	 * @return
	 * @throws IOException 
	 */
	public void getOneStore() throws IOException{
		TangStore tangStore=new TangStore();
		if(storeId!=null&&storeId!=0){
			tangStore=(TangStore)tangStoreService.getObjectById(String.valueOf(storeId));
		}
		if(tangStore!=null){
			tangStore=this.getNewTangStore(tangStore);
			if(tangStore.getShopCategoryId()==PhoneStaticConstants.WfjJingdian){
			JsonIgnore.outputobj(response, tangStore,JsonIgnore.getIgnoreTangStoreJingDian());
			}else if(tangStore.getShopCategoryId()==PhoneStaticConstants.WfjYl){
				JsonIgnore.outputobj(response, tangStore,JsonIgnore.getIgnoreTangStoreWfjYl());	
			}else if(tangStore.getShopCategoryId()==PhoneStaticConstants.WfjHotel){
				JsonIgnore.outputobj(response, tangStore,JsonIgnore.getIgnoreTangStoreWfjHotel());	
			}else if(tangStore.getShopCategoryId()==PhoneStaticConstants.OldEat||tangStore.getShopCategoryId()==PhoneStaticConstants.OldOther){
				JsonIgnore.outputobj(response, tangStore,JsonIgnore.getIgnoreTangStoreOlBrand());	
			}else{
				JsonIgnore.outputobj(response, tangStore,JsonIgnore.getIgnoreTangStore());
			}
		}
		
		
	}

	/**
	 * TangStore数据组装处理
	 * @return
	 */
	public TangStore getNewTangStore(TangStore ts){
		//根据经纬度计算距离：四惠大厦(116.520899,39.912093)北海公园（116.39548,39.932906）距离（11000m）
//		if(ts.getLatitude()==null||ts.getLongitude()==null||ts.getLatitude()==0||ts.getLongitude()==0){//测试用：将无经纬度的店铺的经纬度统一设为四惠大厦的经纬度
//			ts.setLongitude(116.520899);
//			ts.setLatitude(39.912093);
//		}
		if(long1!=null&&lat1!=null&&ts.getLatitude()!=null&&ts.getLongitude()!=null){
				int distance=(int)Distance.getDistance(long1, lat1, ts.getLongitude(), ts.getLatitude());
				ts.setDistance(distance);
		}else{
				ts.setDistance(404);
		}
		ts.setDistanceUnit("米");
		
		//店铺商品组装
		List<TangProduct> proList=new ArrayList<TangProduct>();
		proList=tangProductService.findObjects("where o.isShow=1 and o.storeId="+ts.getStoreId());
		ts.setProList(proList);
		//娱乐信息组装
		if(ts.getShopCategoryId()==PhoneStaticConstants.WfjYl){
			//酒店服务设施组装
			List<TangService> serviceList=new ArrayList<TangService>();
			List<StoreService> sList=storeServiceService.findObjects("where o.storeId="+ts.getStoreId());
			for(StoreService ss:sList){
				TangService tserv=new TangService();
				tserv=(TangService)tangServiceService.getObjectByParams("where o.serviceId="+ss.getServiceId());
				serviceList.add(tserv);
			}
			ts.setServiceList(serviceList);
		}
		//景点信息组装
		if(ts.getShopCategoryId()==PhoneStaticConstants.WfjJingdian){
			//景点交通信息组装
			TangTraffic tangTraffic=(TangTraffic)tangTrafficService.getObjectByParams("where o.storeId='"+storeId+"'");
			if(tangTraffic==null){
				tangTraffic=new TangTraffic();
				tangTraffic.setBusWay("暂无");
				tangTraffic.setSubwayWay("暂无");
				tangTraffic.setTrafficTips("暂无");
			}
			ts.setTangTraffic(tangTraffic);
			//景点更多图片组装
			List<StoreImg> storeImgList=new ArrayList<StoreImg>();
			storeImgList=storeImgService.findObjects("where o.storeId="+ts.getStoreId());
			ts.setStoreImgList(storeImgList);
		 
		}
		
		//酒店信息组装
				if(ts.getShopCategoryId()==60){
					//酒店地标信息组装
					LandMark lm1=new LandMark("故宫","2000",116.520899,39.912093);
					LandMark lm2=new LandMark("长城","2000",116.520899,39.912093);
					LandMark lm3=new LandMark("天安门","2000",116.403963,39.915119);
					List<LandMark> lmList=new ArrayList();
					lmList.add(lm1);
					lmList.add(lm2);
					lmList.add(lm3);
					JSONArray ja=JSONArray.fromObject(lmList);
					ts.setLandMark(ja.toString());
					//酒店服务设施组装
					List<TangService> serviceList=new ArrayList<TangService>();
					List<StoreService> sList=storeServiceService.findObjects("where o.storeId="+ts.getStoreId());
					for(StoreService ss:sList){
						TangService tserv=new TangService();
						tserv=(TangService)tangServiceService.getObjectByParams("where o.serviceId="+ss.getServiceId());
						serviceList.add(tserv);
					}
					ts.setServiceList(serviceList);
				}
		
		
		return ts;
	}



	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getCurrentPage2() {
		return currentPage2;
	}

	public void setCurrentPage2(Integer currentPage2) {
		this.currentPage2 = currentPage2;
	}

	public void setTangServiceService(ITangServiceService tangServiceService) {
		this.tangServiceService = tangServiceService;
	}

	public void setStoreImgService(IStoreImgService storeImgService) {
		this.storeImgService = storeImgService;
	}

	public Integer getShopCategoryId() {
		return shopCategoryId;
	}

	public void setShopCategoryId(Integer shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}

	public void setTangStoreService(ITangStoreService tangStoreService) {
		this.tangStoreService = tangStoreService;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Double getLong1() {
		return long1;
	}

	public void setLong1(Double long1) {
		this.long1 = long1;
	}

	public Double getLat1() {
		return lat1;
	}

	public void setLat1(Double lat1) {
		this.lat1 = lat1;
	}

	public void setTangTrafficService(ITangTrafficService tangTrafficService) {
		this.tangTrafficService = tangTrafficService;
	}

	public void setTangProductService(ITangProductService tangProductService) {
		this.tangProductService = tangProductService;
	}

	public void setStoreServiceService(IStoreServiceService storeServiceService) {
		this.storeServiceService = storeServiceService;
	}

	
}
