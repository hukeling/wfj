package shop.front.home.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import shop.homeModule.service.IDayRecommendLBTService;
import shop.information.pojo.Information;
import shop.information.pojo.InformationCategory;
import shop.information.service.IInformationCategoryService;
import shop.information.service.IInformationCommentService;
import shop.information.service.IInformationService;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopCategory;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopCategoryService;
import shop.store.service.IShopInfoService;
import shop.tags.pojo.ShopSituationTag;
import shop.tags.pojo.ShopTradeSituationTag;
import shop.tags.pojo.ShopTradeTag;
import shop.tags.service.IShopProductTradeSituationTagService;
import shop.tags.service.IShopSituationTagService;
import shop.tags.service.IShopTradeSituationTagService;
import shop.tags.service.IShopTradeTagService;
import util.action.BaseAction;
import util.other.Utils;
/**
 * HomeModuleAction:前台导航条大模块Action
 * @author 张攀攀
 * 2013-01-05
 *
 */
public class HomeModuleAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private IProductInfoService productInfoService;//商品的service层引用
	private IDayRecommendLBTService dayRecommendLBTService;//每日推荐轮播图service层引用
	private IShopInfoService shopInfoService;//店铺service
	private IInformationService informationService;//资讯service
	private IInformationCommentService informationCommentService;//资讯留言service
	private IInformationCategoryService informationCategoryService;//资讯分类service
	private IShopCategoryService shopCategoryService;//店铺分类service
	private List<Map> productInfoList;//每日推荐商品列表
	private List<Map> dayRecommendLBTList;//每日推荐轮播图列表
	private String orderBy;//排序字段
	private String param;
	private List<ShopCategory> shopTypeList=new ArrayList<ShopCategory>();//店铺内部分类list
	private String shopInfoId;//店铺的id
	private String shopCategoryId;//店铺分类id
	private String categoryId;//资讯分类id
	private String articleId;//资讯id
	private String isOk="0";//标识，如果isOk为1 则标示评论成功
	/**适用行业service**/
	private IShopTradeTagService shopTradeTagService;
	/**使用场合service**/
	private IShopSituationTagService shopSituationTagService;
	/**适用行业与使用场合关联表service**/
	private IShopTradeSituationTagService shopTradeSituationTagService;
	/**商品关联适用行业与使用场合service**/
	private IShopProductTradeSituationTagService shopProductTradeSituationTagService;
	/**标签名称-行业专用**/
	private String tageName;
	/**初始化头部样式**/
	private String headerType;
	/**适用行业ID**/
	private String ttId;
	/**使用场合ID**/
	private String stId;
	/**图片或列表标记**/
	private String flagImgOrList;
	/**
	 * 前台导航条“每日推荐”模块信息查询封装
	 * @author 张攀攀
	 * 2013-01-05
	 */
	public String dayRecommend(){
		//头部轮播图显示：broadcastingIamgeUrl-图片;interlinkage-链接
		String hql_lbt = "select drlbt.broadcastingIamgeUrl as broadcastingIamgeUrl,drlbt.interlinkage as interlinkage from DayRecommendLBT drlbt"+
		                 " where drlbt.isShow=1 order by drlbt.sortCode asc";
		dayRecommendLBTList = dayRecommendLBTService.findListMapByHql(hql_lbt);
		//查询商品列表
		String select_count = "select count(dr.id) from DayRecommend dr,ProductInfo pi,ShopInfo c ";
		String select_list = "select pi.productId as productId,pi.productName as productName,pi.marketPrice as marketPrice,pi.salesPrice as salesPrice,pi.logoImg as logoImg,pi.totalSales as totalSales from DayRecommend dr,ProductInfo pi ,ShopInfo c ";
		String condition = " where dr.isShow=1 and dr.productId=pi.productId and pi.isShow=1 and pi.shopInfoId=c.shopInfoId and pi.isPass=1 and pi.isPutSale=2 and c.isPass=3 and c.isClose=0 ";
		String orderByStr = "";
		if(Utils.stringIsNotEmpty(orderBy)){
			if("defaults".equals(orderBy)){//默认
				orderByStr = " order by dr.sort asc";
			}else if("totalSaleDesc".equals(orderBy)){//销量的降序
				orderByStr = " order by pi.totalSales desc";
			}else if("priceAsc".equals(orderBy)){//价格升序
				orderByStr = " order by pi.salesPrice asc";
			}else if("timeDesc".equals(orderBy)){//时间的降序
				orderByStr = " order by pi.putSaleDate desc";
			}else if("priceDesc".equals(orderBy)){//价格降序
				orderByStr = " order by pi.salesPrice desc";
			}
		}else{
			orderBy = "defaults";//默认
			orderByStr = " order by dr.sort asc";
		}
		Integer totalRecordCount = productInfoService.getCountByHQL(select_count+condition);
		pageHelper.setPageInfo(12, totalRecordCount, currentPage);
		productInfoList = productInfoService.findListMapPage(select_list+condition+orderByStr, pageHelper);
		return SUCCESS;
	}
	/**
	 * 一级导航-【行业专用industrySpecific】
	 * @auther wsy
	 */
	@SuppressWarnings("rawtypes")
	public String gotoIndustrySpecificPage(){
		
		//排序
		String orderByStr = "";
		if(Utils.stringIsNotEmpty(orderBy)){
			if("defaults".equals(orderBy)){//默认
				orderByStr = " order by e.putSaleDate asc";
			}else if("totalSaleDesc".equals(orderBy)){//销量的降序
				orderByStr = " order by e.totalSales desc";
			}else if("salesPriceAsc".equals(orderBy)){//价格升序
				orderByStr = " order by e.salesPrice asc";
			}else if("timeDesc".equals(orderBy)){//时间的降序
				orderByStr = " order by e.putSaleDate desc";
			}else if("salesPriceDesc".equals(orderBy)){//价格降序
				orderByStr = " order by e.salesPrice desc";
			}else if("putSaleDateDesc".equals(orderBy)){//最新上架时间 desc
				orderByStr = " order by e.putSaleDate desc";
			}
		}else{
			orderBy = "defaults";//默认
			orderByStr = " order by e.putSaleDate asc";
		}
		
		String path = request.getContextPath();
		//查询出全部正常使用的适用行业标签
		List<Map<String, Object>> shopTradeTagList = shopTradeTagService.findListMapBySql("select e.ttId as ttId,e.tageName as tageName,d.count as count from (select c.ttId as ttId, c.tageName as tageName, count(c.productId) as count from (select a.ttId as ttId,a.productId as productId,b.tageName as tageName,m.isPutSale as isPutSale from shop_product_trade_situation_tag a ,shop_trade_tag b ,shop_productinfo m,shop_shopinfo n where a.productId=m.productId and  m.shopInfoId=n.shopInfoId and m.isPutSale=2 and m.isPass=1 and m.isShow=1 and n.isPass=3 and n.isClose=0 and a.ttId=b.ttId and b.useState=1 group by a.ttId,a.productId) c group by c.ttId) d right join shop_trade_tag e on d.ttId=e.ttId");
		request.setAttribute("shopTradeTagList", shopTradeTagList);
		//如果适用行业不为空 则直接查询  否则适用行业适用第一个数据进行下一步操作
		//查寻第一个标签下关联的商品信息
		if(shopTradeTagList!=null&&shopTradeTagList.size()>0){
			if(ttId==null||StringUtils.isEmpty(ttId)){
				//获取第一条数据
				Map<String, Object> shopTradeTag = shopTradeTagList.get(0);
				//适用行业的标签ID 
				ttId=String.valueOf(shopTradeTag.get("ttId"));
			}
			if(stId==null||StringUtils.isEmpty(stId)){
				//从关联表中获取商品ids
				List<Map<String, Object>> productIdsList = shopProductTradeSituationTagService.findListMapBySql("select group_concat(conv( oct( b.productId ) , 8, 10 )) as productIds from (select a.productId as productId ,a.ttId as ttId from shop_product_trade_situation_tag a,shop_trade_tag c where a.ttId=c.ttId and c.useState=1 and a.ttId="+ttId+" group by a.productId) b group by  b.ttId");
				if(productIdsList!=null&&productIdsList.size()>0){
					String productIds=String.valueOf(productIdsList.get(0).get("productIds"));
					String countSql="select count(e.productId) as count from shop_productinfo e ,shop_shopinfo f where e.shopInfoId=f.shopInfoId and e.productId in ( "+productIds+" ) and e.isPutSale=2 and e.isPass=1 and e.isShow=1 and f.isClose=0 and f.isPass=3 "+orderByStr;
					List<Map<String, Object>> countList = shopProductTradeSituationTagService.findListMapBySql(countSql);
					Integer totalRecordCount =0;
					if(countList!=null&&countList.size()>0){
						totalRecordCount = Integer.parseInt(String.valueOf(countList.get(0).get("count")));
					}
					pageHelper.setPageInfo(15, totalRecordCount, currentPage);
					List<Map<String, Object>> productList = shopProductTradeSituationTagService.findListMapPageBySql("select e.salesPrice as salesPrice,e.marketPrice as marketPrice, e.productId as productId,e.productFullName as productFullName,e.logoImg as logoImg,e.shopInfoId as shopInfoId,f.shopName as shopName,e.goods as goods from shop_productinfo e ,shop_shopinfo f where e.shopInfoId=f.shopInfoId and e.productId in ( "+productIds+" ) and e.isPutSale=2 and e.isPass=1 and e.isShow=1 and f.isClose=0 and f.isPass=3 "+orderByStr, pageHelper);
					if(productList!=null&&productList.size()>0){
						for(Map map:productList){
							String goodsCountHql="select count(a.goods) as goodsCount from ProductInfo a ,ShopInfo f where a.goods="+map.get("goods")+" and a.shopInfoId = f.shopInfoId and a.isPutSale=2 and a.isPass=1 and a.isShow=1 and f.isClose=0 and f.isPass=3";
							int goodsCount = productInfoService.getCountByHQL(goodsCountHql);
							map.put("goodsCount", goodsCount);
						}
					}
					request.setAttribute("productList", productList);
				}
				List<Map<String, Object>> shopSituationTagList=null;
				//查询对应的使用场合
				List<ShopTradeSituationTag> shopTradeSituationTagList = shopTradeSituationTagService.findObjects(" where o.ttId="+ttId);
				if(shopTradeSituationTagList!=null&&shopTradeSituationTagList.size()>0){
					String ststIds="";
					for(ShopTradeSituationTag stst:shopTradeSituationTagList){
						ststIds+=","+stst.getStId();
					}
					if(!"".equals(ststIds)){
						ststIds=ststIds.substring(1, ststIds.length());
						//查询对应的使用场合数据（包括统计）
						shopSituationTagList = shopSituationTagService.findListMapBySql(" select e.stId as stId,e.tageName as tageName,d.count as count from (select c.stId as stId, c.tageName as tageName, count(c.productId) as count from (select a.stId as stId,a.productId as productId,b.tageName as tageName from shop_product_trade_situation_tag a ,shop_situation_tag b,shop_productinfo e ,shop_shopinfo f where a.productId=e.productId and e.shopInfoId = f.shopInfoId and e.isPutSale=2 and e.isPass=1  and e.isShow=1 and f.isClose=0 and f.isPass=3 and a.stId=b.stId and b.useState=1 and a.stId=b.stId and b.useState=1 and a.ttId="+ttId+" group by a.stId,a.productId) c group by c.stId) d right join shop_situation_tag e on d.stId=e.stId  where e.stId in ( "+ststIds+" )");
						request.setAttribute("shopSituationTagList", shopSituationTagList);
						request.setAttribute("flag", "true");
					}else{
						request.setAttribute("flag", "false");
					}
					
				}
				if(ttId!=null||StringUtils.isEmpty(ttId)){
					ShopTradeTag shopTradeTag = (ShopTradeTag) shopTradeTagService.getObjectByParams(" where o.ttId="+ttId);
					if(shopTradeTag!=null){
						tageName="<a href=\""+path+"homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&ttId="+ttId+"&flagImgOrList="+flagImgOrList+"\">"+shopTradeTag.getTageName()+"</a>";
					}
				}else{
					if(shopTradeTagList!=null&&shopTradeTagList.size()>0){
						tageName="<a href=\""+path+"homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&ttId="+ttId+"&flagImgOrList="+flagImgOrList+"\">"+shopTradeTagList.get(0).get("tageName")+"</a>";
					}
				}
			}else{
				//从关联表中获取商品ids
				List<Map<String, Object>> productIdsList = shopProductTradeSituationTagService.findListMapBySql("select group_concat(conv( oct( b.productId ) , 8, 10 )) as productIds from (select a.productId as productId ,a.stId as stId from shop_product_trade_situation_tag a,shop_situation_tag c where a.stId=c.stId and c.useState=1 and a.stId="+stId+" and a.ttId="+ttId+" group by a.productId) b group by  b.stId");
				if(productIdsList!=null&&productIdsList.size()>0){
					String productIds=String.valueOf(productIdsList.get(0).get("productIds"));
					String countSql="select count(e.productId) as count from shop_productinfo e ,shop_shopinfo f where e.shopInfoId=f.shopInfoId and e.productId in ( "+productIds+" ) and e.isPutSale=2 and e.isPass=1 and e.isShow=1 and f.isClose=0 and f.isPass=3"+orderByStr;
					List<Map<String, Object>> countList = shopProductTradeSituationTagService.findListMapBySql(countSql);
					Integer totalRecordCount =0;
					if(countList!=null&&countList.size()>0){
						totalRecordCount = Integer.parseInt(String.valueOf(countList.get(0).get("count")));
					}
					pageHelper.setPageInfo(15, totalRecordCount, currentPage);
					List<Map<String, Object>> productList = shopProductTradeSituationTagService.findListMapPageBySql("select e.salesPrice as salesPrice,e.marketPrice as marketPrice, e.productId as productId,e.productFullName as productFullName,e.logoImg as logoImg,e.shopInfoId as shopInfoId,f.shopName as shopName,e.goods as goods from shop_productinfo e ,shop_shopinfo f where e.shopInfoId=f.shopInfoId and e.productId in ( "+productIds+" ) and e.isPutSale=2 and e.isPass=1  and e.isShow=1 and f.isClose=0 and f.isPass=3"+orderByStr,pageHelper);
					if(productList!=null&&productList.size()>0){
						for(Map map:productList){
							String goodsCountHql="select count(a.goods) as goodsCount from ProductInfo a ,ShopInfo f where a.goods="+map.get("goods")+" and a.shopInfoId = f.shopInfoId and a.isPutSale=2 and a.isPass=1 and a.isShow=1 and f.isClose=0 and f.isPass=3";
							int goodsCount = productInfoService.getCountByHQL(goodsCountHql);
							map.put("goodsCount", goodsCount);
						}
					}
					request.setAttribute("productList", productList);
				}
				//查询对应的使用场合
				List<ShopTradeSituationTag> shopTradeSituationTagList = shopTradeSituationTagService.findObjects(" where o.ttId="+ttId);
				if(shopTradeSituationTagList!=null&&shopTradeSituationTagList.size()>0){
					String ststIds="";
					for(ShopTradeSituationTag stst:shopTradeSituationTagList){
						ststIds+=","+stst.getStId();
					}
					if(!"".equals(ststIds)){
						ststIds=ststIds.substring(1, ststIds.length());
						//查询对应的使用场合数据（包括统计）
						List<Map<String, Object>> shopSituationTagList = shopSituationTagService.findListMapBySql(" select e.stId as stId,e.tageName as tageName,d.count as count from (select c.stId as stId, c.tageName as tageName, count(c.productId) as count from (select a.stId as stId,a.productId as productId,b.tageName as tageName from shop_product_trade_situation_tag a ,shop_situation_tag b,shop_productinfo e ,shop_shopinfo f where a.productId=e.productId and e.shopInfoId = f.shopInfoId and e.isPutSale=2 and e.isPass=1  and e.isShow=1 and f.isClose=0 and f.isPass=3 and a.stId=b.stId and b.useState=1 and a.ttId="+ttId+" group by a.stId,a.productId) c group by c.stId) d right join shop_situation_tag e on d.stId=e.stId  where e.stId in ( "+ststIds+" )");
						request.setAttribute("shopSituationTagList", shopSituationTagList);
						request.setAttribute("flag", "true");
					}else{
						request.setAttribute("flag", "false");
					}
					
				}
				ShopTradeTag shopTradeTag = (ShopTradeTag) shopTradeTagService.getObjectByParams(" where o.ttId="+ttId);
				if(shopTradeTag!=null){
					tageName="<a href=\""+path+"homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&ttId="+ttId+"&flagImgOrList="+flagImgOrList+"\">"+shopTradeTag.getTageName()+"</a>";
				}
				ShopSituationTag shopSituationTag = (ShopSituationTag) shopSituationTagService.getObjectByParams(" where o.stId="+stId);
				if(shopSituationTag!=null){
					tageName+="&nbsp;&gt;&nbsp<a href=\""+path+"homeModule/gotoIndustrySpecificPage.action?headerType=industrySpecific&ttId="+ttId+"&stId="+stId+"&flagImgOrList="+flagImgOrList+"\">"+shopSituationTag.getTageName()+"</a>";
				}
			}
		}
		String result="success"+flagImgOrList;
		return result;
	}
	
	/**
	 * 一级导航-【资讯站】
	 * @auther mqr
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String gotoInforMationPage(){
		//右侧店铺分类
		shopTypeList= shopCategoryService.findSome(0, 11," where o.parentId>0  order by o.sortCode");
		//查询店铺信息
		String shopDirHql="SELECT a.shopInfoId as shopInfoId ,a.shopName as shopName,a.customerName as customerName,a.marketBrandUrl as marketBrandUrl,a.logoUrl as logoUrl,count(z.articleId) as count FROM ShopInfo a, Information z where 1=1";
		if(StringUtils.isNotEmpty(shopCategoryId)){
			shopDirHql+=" and a.shopCategoryId="+shopCategoryId;
		}
		//追加查询条件
		shopDirHql+=" and a.isPass=3 and a.isClose=0";
		//判断店铺是否发表了资讯
		shopDirHql+=" and a.shopInfoId=z.shopInfoId";
		List<Map> findListMapByHql = shopInfoService.findListMapByHql(shopDirHql);
		if(findListMapByHql!=null&&findListMapByHql.size()>0){
			Integer totalRecordCount = findListMapByHql.size();
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			List<Map> mList=shopInfoService.findListMapPage(shopDirHql+" GROUP BY z.shopInfoId order by a.shopInfoId ", pageHelper);
			if(mList!=null&&mList.size()>0){
				for(Map m:mList){
					Object object = m.get("shopInfoId");
					if(object!=null){
						List<Information> informationList = (List<Information>) informationService.findListSpecifiedNumber(null, 0, 5, "where o.shopInfoId="+String.valueOf(object));
						m.put("informationList", informationList);
					}
				}
			}
			request.setAttribute("mapList", mList);
		}
		return SUCCESS;
	}
	/**
	 * 查询更多资讯信息
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String getMore(){
		if(StringUtils.isNotEmpty(shopInfoId)){
			//查询店铺信息
			ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+shopInfoId);
			request.setAttribute("shopInfo", shopInfo);
			//查询店铺资讯分类
			List<InformationCategory> icList = (List<InformationCategory>) informationCategoryService.findListSpecifiedNumber(null, 0, 7, " where o.shopInfoId="+shopInfoId+" and o.isShow=1");
			request.setAttribute("informationCategoryList", icList);
			//查询资讯
			String hql="select a.articleId as articleId,a.title as title,a.imgUrl as imgUrl,a.createTime as createTime,a.publishUser as publishUser,a.clickCount as clickCount,a.brief as brief,b.shopProCategoryName as shopProCategoryName from Information a,InformationCategory b where a.categoryId=b.categoryId and a.shopInfoId="+shopInfoId+" and a.isShow=1 and a.isEssence=0";
			String strCount="select count(a.articleId) from Information a ,InformationCategory b where a.categoryId=b.categoryId and a.shopInfoId="+shopInfoId+" and a.isShow=1 and a.isEssence=0";
			if(StringUtils.isNotEmpty(categoryId)){
				hql+=" and a.categoryId="+categoryId;
				strCount+=" and a.categoryId="+categoryId;
			}
			hql+=" order by a.sortCode";
			Integer totalRecordCount=informationService.getMultilistCount(strCount);
			pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
			List<Map> findListMapPage = informationService.findListMapPage(hql, pageHelper);
			request.setAttribute("informationList", findListMapPage);
			//查询五条置顶资讯
			List<Information> topInformationList = (List<Information>) informationService.findListSpecifiedNumber(null, 0, 5, " where o.isEssence=1 and o.isShow=1 and o.shopInfoId="+shopInfoId+" order by o.sortCode");
			if(topInformationList!=null&&topInformationList.size()>0){
				for(Information i:topInformationList){
					InformationCategory ic = (InformationCategory) informationCategoryService.getObjectByParams(" where o.categoryId="+i.getCategoryId());
					if(ic!=null){
						i.setModifyUser(ic.getShopProCategoryName());
					}
				}
			}
			request.setAttribute("topInformationList", topInformationList);
			request.setAttribute("size", topInformationList.size());
			//查询最新资讯10条
			List<Information> informationList1 = (List<Information>) informationService.findListSpecifiedNumber(null, 0, 10, " where o.isShow=1 and o.shopInfoId="+shopInfoId+" order by o.createTime desc");
			//查询热门资讯10条
			List<Information> informationList2 = (List<Information>) informationService.findListSpecifiedNumber(null, 0, 10, " where o.isShow=1 and o.shopInfoId="+shopInfoId+" order by o.clickCount desc");
			request.setAttribute("informationList1", informationList1);
			request.setAttribute("informationList2", informationList2);
		}
		return SUCCESS;
	}
	/**
	 * 阅读全文操作
	 * @return
	 */
	public String getInfo(){
		//查询店铺
		ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.shopInfoId="+shopInfoId);
		request.setAttribute("shopInfo", shopInfo);
		//查询店铺资讯分类
		List<InformationCategory> icList = (List<InformationCategory>) informationCategoryService.findListSpecifiedNumber(null, 0, 7, " where o.shopInfoId="+shopInfoId+" and o.isShow=1");
		request.setAttribute("informationCategoryList", icList);
		//查询资料
		Information information = (Information) informationService.getObjectByParams(" where o.shopInfoId="+shopInfoId+" and o.articleId="+articleId);
		//查询所属分类
		if(information!=null){
			InformationCategory ic = (InformationCategory) informationCategoryService.getObjectByParams(" where o.categoryId="+information.getCategoryId());
			categoryId=String.valueOf(ic.getCategoryId());
			request.setAttribute("informationCategory", ic);
			//查询回复数量
			Integer count = informationCommentService.getCount(" where o.articleId="+articleId);
			request.setAttribute("count",count);
		}
		request.setAttribute("information", information);
		return SUCCESS;
	}
	/**
	 * 修改点击次数 
	 * 使用重定向 防止刷新页面无限增加点击次数
	 * @return
	 */
	public String changeClickNum(){
		//查询资料
		Information information = (Information) informationService.getObjectByParams(" where o.shopInfoId="+shopInfoId+" and o.articleId="+articleId);
		information.setClickCount(information.getClickCount()+1);
		information=(Information) informationService.saveOrUpdateObject(information);
		return SUCCESS;
	}
	//setter getter
	public List<Map> getProductInfoList() {
		return productInfoList;
	}
	public void setProductInfoList(List<Map> productInfoList) {
		this.productInfoList = productInfoList;
	}
	public List<Map> getDayRecommendLBTList() {
		return dayRecommendLBTList;
	}
	public void setDayRecommendLBTList(List<Map> dayRecommendLBTList) {
		this.dayRecommendLBTList = dayRecommendLBTList;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}
	public void setDayRecommendLBTService(
			IDayRecommendLBTService dayRecommendLBTService) {
		this.dayRecommendLBTService = dayRecommendLBTService;
	}
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setShopCategoryService(IShopCategoryService shopCategoryService) {
		this.shopCategoryService = shopCategoryService;
	}
	public List<ShopCategory> getShopTypeList() {
		return shopTypeList;
	}
	public void setShopTypeList(List<ShopCategory> shopTypeList) {
		this.shopTypeList = shopTypeList;
	}
	public String getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public void setInformationService(IInformationService informationService) {
		this.informationService = informationService;
	}
	public void setInformationCategoryService(
			IInformationCategoryService informationCategoryService) {
		this.informationCategoryService = informationCategoryService;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public void setInformationCommentService(
			IInformationCommentService informationCommentService) {
		this.informationCommentService = informationCommentService;
	}
	public String getIsOk() {
		return isOk;
	}
	public void setIsOk(String isOk) {
		this.isOk = isOk;
	}
	public void setShopTradeTagService(IShopTradeTagService shopTradeTagService) {
		this.shopTradeTagService = shopTradeTagService;
	}
	public void setShopSituationTagService(
			IShopSituationTagService shopSituationTagService) {
		this.shopSituationTagService = shopSituationTagService;
	}
	public void setShopTradeSituationTagService(
			IShopTradeSituationTagService shopTradeSituationTagService) {
		this.shopTradeSituationTagService = shopTradeSituationTagService;
	}
	public void setShopProductTradeSituationTagService(
			IShopProductTradeSituationTagService shopProductTradeSituationTagService) {
		this.shopProductTradeSituationTagService = shopProductTradeSituationTagService;
	}
	public String getTageName() {
		return tageName;
	}
	public void setTageName(String tageName) {
		this.tageName = tageName;
	}
	public String getTtId() {
		return ttId;
	}
	public void setTtId(String ttId) {
		this.ttId = ttId;
	}
	public String getStId() {
		return stId;
	}
	public void setStId(String stId) {
		this.stId = stId;
	}
	public String getFlagImgOrList() {
		return flagImgOrList;
	}
	public void setFlagImgOrList(String flagImgOrList) {
		this.flagImgOrList = flagImgOrList;
	}
}
