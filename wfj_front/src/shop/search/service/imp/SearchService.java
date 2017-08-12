package shop.search.service.imp;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.compass.core.Compass;
import org.compass.core.CompassHits;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQuery.SortDirection;
import org.compass.core.CompassQuery.SortPropertyType;
import org.compass.core.CompassQueryBuilder;
import org.compass.core.CompassQueryBuilder.CompassBooleanQueryBuilder;
import org.compass.core.CompassSession;
import org.compass.core.CompassTemplate;

import shop.product.pojo.ProductInfo;
import shop.search.service.ISearchService;
import util.other.Pager;
import util.other.Pager.OrderType;
import util.other.Utils;
/**
 * 搜索引擎Service接口实现类
 * @author mqr
 */
public class SearchService implements ISearchService {
	// 搜索引擎模板对象
	private CompassTemplate compassTemplate;
	/**
	 * 使用搜索引擎搜索商品，得到商品集合
	 * @param pager
	 *            分页对象
	 * @return 搜索商品集合 pager.list
	 */
	public Pager searchProductInfos(Pager pager) throws org.apache.lucene.queryParser.ParseException , org.compass.core.engine.SearchEngineQueryParseException {
		Compass compass = compassTemplate.getCompass();//创建compass对象
		CompassSession session = compass.openSession();//打开compassSession
		CompassQueryBuilder queryBuilder = session.queryBuilder();//使用session建立查询
		CompassBooleanQueryBuilder compassBooleanQueryBuilder = queryBuilder.bool();//将queryBuilder转换成多条件查询 ,也叫布尔查询
		CompassQuery compassQuery = compassBooleanQueryBuilder//用addMust方法将queryBuilder的条件组合
				.addMust(queryBuilder.spanEq("isPass", "1"))//商品是否审批
				.addMust(queryBuilder.spanEq("isPutSale", "2"))//商品是否上架
				.addMust(queryBuilder.spanEq("isShow", "1"))//商品是否显示
				.addMust(queryBuilder.spanEq("tisPass", "3"))//店铺是否审核通过
				.addMust(queryBuilder.spanEq("tisClose", "0"))//店铺是否关闭
				.addMust(queryBuilder.queryString("productFullName:" + pager.getKeyword() +" OR sku:"+ pager.getKeyword()
				+" OR brandName:"+pager.getKeyword()).toQuery()).toQuery();//匹配商品名称
		if (StringUtils.isEmpty(pager.getOrderBy()) || pager.getOrderType() == null) {//排序条件为空时，默认排序规则：按照更新时间倒序
			compassQuery.addSort("putSaleDate", SortPropertyType.STRING , SortDirection.REVERSE);
		} else {
			if (pager.getOrderType() == OrderType.asc) {//正序排列asc
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "salesPrice")) {//判断价格是否排序asc
					compassQuery.addSort("salesPrice", SortPropertyType.FLOAT);
				}
			} else {//反序排列desc
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "salesPrice")) {//判断价格是否排序desc
					compassQuery.addSort("salesPrice", SortPropertyType.FLOAT, SortDirection.REVERSE);
				}
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "putSaleDate")) {//判断上架时间是否排序desc
					compassQuery.addSort("putSaleDate", SortPropertyType.STRING , SortDirection.REVERSE );
				}
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "totalSales")) {//判断销售量是否排序desc
					compassQuery.addSort("totalSales", SortPropertyType.AUTO , SortDirection.REVERSE );
				}
			}
		}
		CompassHits compassHits = compassQuery.hits();//搜索引擎查询商品数据结果集
		//返回分页部分商品集合
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		//返回全部商品集合
		List<ProductInfo> productAllList = new ArrayList<ProductInfo>();
		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();//第一页商品结果集
		int maxReasults = pager.getPageSize();//最大每页记录数
		int totalCount = compassHits.length();//商品总数
		//设置页面详情分页页码
		pager.setPageInfo(pager.getPageSize(), totalCount, pager.getPageNumber());
		//分页最大值
		int end = Math.min(totalCount, firstResult + maxReasults);
		for (int i = firstResult; i < end; i++) {//循环遍历每页显示多少商品
			Object obj = compassHits.data(i);
			ProductInfo productInfo = (ProductInfo) obj;
			String productName = compassHits.highlighter(i).fragment("productName");
			String productFullName = compassHits.highlighter(i).fragment("productFullName");
			if (StringUtils.isNotEmpty(productName)){
				productInfo.setProductName(productName);
			}
			if (StringUtils.isNotEmpty(productFullName)){
				productInfo.setProductFullName(productFullName);
			}
			productList.add(productInfo);
		}
		for (int i = 0; i < totalCount; i++) {
			ProductInfo productInfo = (ProductInfo) compassHits.data(i);
			String productName = compassHits.highlighter(i).fragment("productName");
			String productFullName = compassHits.highlighter(i).fragment("productFullName");
			if (StringUtils.isNotEmpty(productName)){
				productInfo.setProductName(productName);
			}
			if (StringUtils.isNotEmpty(productFullName)){
				productInfo.setProductFullName(productFullName);
			}
			productAllList.add(productInfo);
		}
		session.close();
		pager.setList(productList);
		pager.setListAll(productAllList);
		pager.setTotalCount(totalCount);
		return pager;
	}
	/**
	 * 使用搜索引擎搜索商品，得到商品集合，条件组拼，选择某一分类
	 * @param pager
	 *            分页对象
	 * @param productTypeId
	 *            分类ID
	 * @param minPrice
	 *            最小价格
	 * @param maxPrice
	 *            最大价格
	 * @return 搜索商品集合 pager.list
	 */
	public Pager searchProductInfos(Pager pager,Integer productTypeId,BigDecimal minPrice,BigDecimal maxPrice) throws org.apache.lucene.queryParser.ParseException , org.compass.core.engine.SearchEngineQueryParseException  {
		Compass compass = compassTemplate.getCompass();
		CompassSession session = compass.openSession();
		CompassQueryBuilder queryBuilder = session.queryBuilder();
		CompassBooleanQueryBuilder compassBooleanQueryBuilder = queryBuilder.bool();//多条件查询
		//需要追加条件
		compassBooleanQueryBuilder = compassBooleanQueryBuilder
				.addMust(queryBuilder.spanEq("isPass", "1"))//商品是否审批
				.addMust(queryBuilder.spanEq("isPutSale", "2"))//商品是否上架
				.addMust(queryBuilder.spanEq("isShow", "1"))//商品是否显示
				.addMust(queryBuilder.spanEq("tisPass", "3"))//店铺是否审核通过
				.addMust(queryBuilder.spanEq("tisClose", "0"))//店铺是否关闭
				.addMust(queryBuilder.queryString("productFullName:" + pager.getKeyword()+" OR sku:"+ pager.getKeyword()).toQuery());
		if(Utils.objectIsNotEmpty(productTypeId)){//分类ID不等于空
			//此处只过滤一级分类
			compassBooleanQueryBuilder.addMust(queryBuilder.spanEq("categoryLevel1", productTypeId));
		}
		CompassQuery compassQuery = compassBooleanQueryBuilder.toQuery();//匹配商品名称
		if (StringUtils.isEmpty(pager.getOrderBy()) || pager.getOrderType() == null) {//排序条件为空时，默认排序规则：按照更新时间倒序
			compassQuery.addSort("putSaleDate", SortPropertyType.STRING , SortDirection.REVERSE);
		} else {
			if (pager.getOrderType() == OrderType.asc) {//正序排列asc
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "salesPrice")) {//判断价格是否排序asc
					compassQuery.addSort("salesPrice", SortPropertyType.FLOAT);
				}
			} else {//反序排列desc
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "salesPrice")) {//判断价格是否排序desc
					compassQuery.addSort("salesPrice", SortPropertyType.FLOAT, SortDirection.REVERSE);
				}
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "putSaleDate")) {//判断上架时间是否排序desc
					compassQuery.addSort("putSaleDate", SortPropertyType.STRING , SortDirection.REVERSE );
				}
				if(StringUtils.equalsIgnoreCase(pager.getOrderBy(), "totalSales")) {//判断销售量是否排序desc
					compassQuery.addSort("totalSales", SortPropertyType.AUTO , SortDirection.REVERSE );
				}
			}
		}
		CompassHits compassHits = compassQuery.hits();//搜索引擎查询商品数据结果集
		//返回分页部分商品集合
		List<ProductInfo> productList = new ArrayList<ProductInfo>();
		//返回全部商品集合
		List<ProductInfo> productAllList = new ArrayList<ProductInfo>();
		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();//第一页商品结果集
		int maxReasults = pager.getPageSize();//最大每页记录数
		int totalCount = compassHits.length();//商品总数
		/*
		 * 价格搜索注释
		 * 取价格在minPric-maxPric中间的商品
		 */
		if(Utils.objectIsNotEmpty(minPrice)&&Utils.objectIsNotEmpty(maxPrice)){//如果价格不为空，进行价格筛选
			for (int i = 0; i < totalCount; i++) {//循环遍历所有商品
				ProductInfo productInfo = (ProductInfo) compassHits.data(i);
				//进行价格的筛选
				BigDecimal salesPrice = productInfo.getSalesPrice();
				BigDecimal min = minPrice;
				BigDecimal max = maxPrice;
				if(salesPrice.compareTo(min)>=0 && salesPrice.compareTo(max)<=0){
					//取出查询结果高亮显示
					String productName = compassHits.highlighter(i).fragment("productName");
					String productFullName = compassHits.highlighter(i).fragment("productFullName");
					if (StringUtils.isNotEmpty(productName)){
						productInfo.setProductName(productName);
					}
					if (StringUtils.isNotEmpty(productFullName)){
						productInfo.setProductFullName(productFullName);
					}
					//添加此商品
					productAllList.add(productInfo);
				}
			}
			totalCount = productAllList.size();//最终的商品总数
			//分页最大值
			int end = Math.min(totalCount, firstResult + maxReasults);
			for (int i = firstResult; i < end; i++) {//循环遍历每页显示多少商品
				ProductInfo productInfo = (ProductInfo) productAllList.get(i);
				productList.add(productInfo);
			}
		}else{
			for (int i = 0; i < totalCount; i++) {//循环遍历所有商品
				ProductInfo productInfo = (ProductInfo) compassHits.data(i);
				//添加此商品
				productAllList.add(productInfo);
			}
			//分页最大值
			int end = Math.min(totalCount, firstResult + maxReasults);
			for (int i = firstResult; i < end; i++) {//循环遍历每页显示多少商品
				//取出查询结果高亮显示
				ProductInfo productInfo = (ProductInfo) compassHits.data(i);
				String productName = compassHits.highlighter(i).fragment("productName");
				String productFullName = compassHits.highlighter(i).fragment("productFullName");
				if (StringUtils.isNotEmpty(productName)){
					productInfo.setProductName(productName);
				}
				if (StringUtils.isNotEmpty(productFullName)){
					productInfo.setProductFullName(productFullName);
				}
				productList.add(productInfo);
			}
		}
		//设置页面详情分页页码
		pager.setPageInfo(pager.getPageSize(), totalCount, pager.getPageNumber());
		session.close();
		pager.setList(productList);//添加分页过后的商品
		pager.setListAll(productAllList);//添加全部的商品
		pager.setTotalCount(totalCount);//添加商品总数
		return pager;
	}
	//setter getter
	public CompassTemplate getCompassTemplate() {
		return compassTemplate;
	}
	public void setCompassTemplate(CompassTemplate compassTemplate) {
		this.compassTemplate = compassTemplate;
	}
	
}