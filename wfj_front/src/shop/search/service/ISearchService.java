package shop.search.service;
import java.math.BigDecimal;

import org.apache.lucene.queryParser.ParseException;
import org.compass.core.engine.SearchEngineQueryParseException;

import util.other.Pager;
/**
 * 搜索引擎Service接口类
 * @author mqr
 */
public interface ISearchService {
	/**
	 * 使用搜索引擎搜索商品，得到商品集合
	 * @param productId
	 *            商品ID
	 * @throws SearchEngineQueryParseException 
	 * @throws ParseException 
	 * @return 搜索商品集合
	 */
	public Pager searchProductInfos(Pager pager) throws ParseException, SearchEngineQueryParseException;
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
	 * 
	 * @return 搜索商品集合 pager.list
	 * @throws SearchEngineQueryParseException 
	 * @throws ParseException 
	 */
	public Pager searchProductInfos(Pager pager,Integer productTypeId,BigDecimal minPrice,BigDecimal maxPrice) throws ParseException, SearchEngineQueryParseException;
}
