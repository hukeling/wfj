package phone.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public  class JsonIgnore {
	/**
	 * @param response
	 * @param Object
	 * @throws IOException
	 */
	public static void outputobj(HttpServletResponse response,Object obj) throws IOException{
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		if (obj!=null) {
			JSONObject objson=JSONObject.fromObject(obj);
			jo.accumulate("Status", true);
			jo.accumulate("Data", objson);
		}else{
			jo.accumulate("Status", false);
			jo.accumulate("Data", "查询无结果");
		}
		out.write(jo.toString());
		out.flush();
		out.close();
	} 
	
	public static void outputobj(HttpServletResponse response,Object obj,String [] ignoreString) throws IOException{
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		if (obj!=null) {
			JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
			jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
			jsonConfig.setExcludes(ignoreString);
			JSONObject objson=JSONObject.fromObject(obj,jsonConfig);
			jo.accumulate("Status", true);
			jo.accumulate("Data", objson);
		}else{
			jo.accumulate("Status", false);
			jo.accumulate("Data", "查询无结果");
		}
		out.write(jo.toString());
		out.flush();
		out.close();
	} 
	
	/**
	 * 工具方法：向页面输出json格式的数据
	 * @param response
	 * @param list查询结果数据
	 * @throws IOException
	 */
	public static void output(HttpServletResponse response,List list) throws IOException{
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		if (list!=null&&list.size()>0) {
			JSONArray listjson=JSONArray.fromObject(list);
			jo.accumulate("Status", true);
			jo.accumulate("Data", listjson);
		}else{
			jo.accumulate("Status", false);
			jo.accumulate("Data", "查询无结果");
		}
		out.write(jo.toString());
		out.flush();
		out.close();
	} 
	/**
	 * 工具方法：向页面输出过滤后的json格式数据
	 * @param list查询结果数据
	 * @param ignoreString需要忽略（不输出）的字段
	 * @throws IOException
	 */
	public static void outputJo(HttpServletResponse response,List list,String [] ignoreString) throws IOException{
		JSONObject jo = new JSONObject();
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		if (list!=null&&list.size()>0) {
			JSONArray listjson=jsonIgnore(list, ignoreString);
			jo.accumulate("Status", true);
			jo.accumulate("Data", listjson);
		}else{
			jo.accumulate("Status", false);
			jo.accumulate("Data", "查询无结果");
		}
		out.write(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 工具方法：json过滤多余的属性
	 * @param list
	 * @param ignoreString
	 * @return 过滤后的JSONArray
	 */
	public static JSONArray jsonIgnore(List list,String[] ignoreString){
		JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
		jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
		jsonConfig.setExcludes(ignoreString);
		JSONArray listja = JSONArray.fromObject(list, jsonConfig);
		return listja;
	}
	
	
	//设置Experience忽略字段
	static String[] ignoreExperience={"isShow","synopsisTitle","synopsisImg","synopsisContent","lbtImg1","lbtImg2","lbtImg3","tip","expInfoList"};
	//设置Experience列表用忽略字段
	static String[] ignoreExperience2={"isShow","synopsisTitle","synopsisImg"};
	//设置TangImpression忽略字段
	static String [] ignoreTangImpression={
		"jianjie","jianjieImage2","yuanzi","yuanziImage2","tedian","tedianImage2","guihua","guihuaImage2"
	};
	//设置TangStore忽略字段
	static String [] ignoreTangStore={
		"synopsis","longitude","latitude","businessHoursStart","businessHoursEnd","storeServiceList","storeTrafficList","tangTraffic",
		"storeLevel","storeTip","isShow","isRecommend","shopCategoryId","note","businessHourStr","distanceUnit","landMark","proList","serviceList","storeImgList"
	};
	//设置TangStore（景点）忽略字段
	static String [] ignoreTangStoreJingDian={
		"businessHoursStart","businessHoursEnd","serviceList","storeServiceList"
		,"isShow","isRecommend","note","distance","distanceUnit","landMark","businessHourList"
	};
	//设置TangStore（老字号）忽略字段
	static String [] ignoreTangStoreOlBrand={
		"businessHoursStart","businessHoursEnd","businessHourList",  "serviceList","storeImgList","storeLevel"
		,"businessHourStr","tangTraffic","storeTip","storeServiceList","isShow","isRecommend","note","distance","distanceUnit","landMark"
	};
	//设置TangStore（娱乐）忽略字段
	static String [] ignoreTangStoreWfjYl={
		"businessHourList","storeImgList","storeLevel","storeTip","tangTraffic","businessHourStr"
		,"storeServiceList","isShow","isRecommend","note","distanceUnit","landMark","synopsis"
	};
	//设置TangStore（酒店）忽略字段
	static String [] ignoreTangStoreWfjHotel={
		"storeLevel","businessHourStr","businessHoursStart","businessHoursEnd","businessHourStr","storeServiceList",
		"city","businessHourList","tangTraffic","storeImgList","isShow","isRecommend","note","distance","distanceUnit"
	};
	//设置TangLbt
	static String []ignoreTangLbt={"synopsis","sortCode","showLocation",
		"isShow","createTime","publishUser","updateTime","modifyUser"};
	//设置ProductInfo忽略的字段
		static String [] ignoreProInfo={"brandId", "categoryLevel1","categoryLevel2", "categoryLevel3","categoryLevel4", "shopInfoId", "marketPrice",
				"salesPriceBack", "costPrice", "memberPrice","weight", "weightUnit", "measuringUnitName","packingSpecifications", "specification",
				"manufacturerModel", "describle", "createDate","updateDate", "putSaleDate", "isPutSale","productCode", "functionDesc", "isChargeFreight",
				"freightPrice", "productAttributeValue","productParametersValue", "goods", "productRemark","isPass", "note", "productTag", "seoTitle",
				"seoKeyWord", "seoDescription", "totalHits","isRecommend", "isNew", "isHot", "isTop","productAttribute", "isShow", "virtualCoinNumber",
				"giveAwayCoinNumber", "barCode", "qrCode","stockUpDate", "deliveryAddressProvince","deliveryAddressCities", "sku", "brandName",
				"goodsCount",  "tisPass", "tisClose","storeNumber" };
		
		static String [] ignoreProInfo2={"categoryLevel1","categoryLevel2", "categoryLevel3","categoryLevel4", "shopInfoId", "marketPrice",
			"salesPriceBack", "costPrice", "memberPrice","weight", "weightUnit", "measuringUnitName","packingSpecifications", "specification",
			"manufacturerModel", "describle", "createDate","updateDate", "putSaleDate", "isPutSale","productCode", "functionDesc", "isChargeFreight",
			"freightPrice", "productAttributeValue","productParametersValue", "goods", "productRemark","isPass", "note", "productTag", "seoTitle",
			"seoKeyWord", "seoDescription", "totalHits","isRecommend", "isNew", "isHot", "isTop","productAttribute", "isShow", "virtualCoinNumber",
			"giveAwayCoinNumber", "barCode", "qrCode","stockUpDate", "deliveryAddressProvince","deliveryAddressCities", "sku", "brandName",
			"goodsCount",  "tisPass", "tisClose","storeNumber" };
	//设置OrdersList订单详情忽略的字段
		static String [] ignoreOrdersList={"costPrice","marketPrice","memberPrice","buyType","productCode","brandId","createTime","customerId","freightPrice",
			"brandName","stockUpDate","sku","barCode","virtualCoinNumber","virtualCoinProportion","userCoin","changeAmount","virtualCoin","discount"};
		
		static String [] ignoreOrdersList2={"costPrice","marketPrice","memberPrice","buyType","productCode","brandId",
			"brandName","stockUpDate","sku","barCode","virtualCoinNumber","virtualCoinProportion","userCoin","changeAmount","virtualCoin","discount"};
	//设置Orders订单忽略的字段
		static String [] ignoreOrders={"sonaccountId","buyerName","bestSendDate","flagContractor","phone","isUseCoupon","discountCouponId","orderCouponAmount",
			"ip","currency","oosOperator","isLocked","ordersRemark","virtualCoin","userCoin","changeAmount","virtualCoinNumber","useLineOfCredit","taxation"};
	//设置ProductType产品类别忽略的字段
		static String [] ignoreProType={"sortCode","isShow","isRecommend","categoryDescription","loadType",
				"level","industrySpecific","brandList"};
		static String [] ignoreOrdersWithList={"addressForInvoice","bankAccountNumber","bankCode","bestSendDate","buyerName","changeAmount","comments",
			"companyNameForInvoice","currency","customerType","dealId","discount","email","oosOperator","openingBank","orderCouponAmount","settlementStatus",
			"settlementStatusForSellers","sonaccountId","taxation","taxpayerNumber","totalAmount","updateTime","useLineOfCredit","virtualCoin","virtualCoinNumber",
			 "ordersRemark","phone","phoneForInvoice","postcode","flagContractor","ip","isLocked","isUseCoupon","country","city","regionLocation","discountCouponId",
			 "userCoin"};
	//设置ShopInfo商铺详情忽略字段
		static String[] ignoreShopInfo={ "customerId ","customerName", "businessType", 
			 "postCode",  "companyRegistered", "legalOwner", "companyCertification",  "email", "phone",  "businessHoursStart",  "businessHoursEnd", 
			"IDCards", "IDCardsImage ",  "companyDocuments",  "taxRegistrationDocuments",  "businessLicense","marketBrand",  "marketBrandUrl",  "applyTime",  "isPass", 
			"passTime",  "verifier",  "isClose", "logoUrl", "bannerUrl",  "tag",  "synopsis",  "description", "companyName", "templateSet", "qrCode","isRecommend", 
			"companyNameForInvoice", "taxpayerNumber","addressForInvoice","phoneForInvoice", "openingBank","bankAccountNumber", "invoiceCheckStatus", "shopInfoCheckSatus",
			"phoneShowStatus", "invoiceType", "invoiceInfo", "isVip","minAmount", "postage", "productInfoList", "productInfoMap", "commission"
		};
		
		
		
		 

		public static String[] getIgnoreExperience2() {
			return ignoreExperience2;
		}

		public static String[] getIgnoreTangStoreOlBrand() {
			return ignoreTangStoreOlBrand;
		}

		public static String[] getIgnoreTangStoreWfjYl() {
			return ignoreTangStoreWfjYl;
		}

		public static String[] getIgnoreTangStoreWfjHotel() {
			return ignoreTangStoreWfjHotel;
		}

		public static String[] getIgnoreTangStoreJingDian() {
			return ignoreTangStoreJingDian;
		}

		public static String[] getIgnoreTangLbt() {
			return ignoreTangLbt;
		}

		public static String[] getIgnoreTangImpression() {
			return ignoreTangImpression;
		}

		public static String[] getIgnoreTangStore() {
			return ignoreTangStore;
		}
		public static String[] getIgnoreProInfo() {
			return ignoreProInfo;
		}
		public static String[] getIgnoreProType() {
			return ignoreProType;
		}
		public static String[] getIgnoreProInfo2() {
			return ignoreProInfo2;
		}
		public static String[] getIgnoreOrdersList() {
			return ignoreOrdersList;
		}
		public static String[] getIgnoreOrdersWithList() {
			return ignoreOrdersWithList;
		}
		public static String[] getIgnoreShopInfo() {
			return ignoreShopInfo;
		}

		public static String[] getIgnoreExperience() {
			return ignoreExperience;
		}
		
		
		
}
