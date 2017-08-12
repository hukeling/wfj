package shop.product.service.imp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import promotion.dao.IStoreApplyPromotionDao;
import shop.browseRecord.dao.IProBrRecordDao;
import shop.customer.dao.IEvaluateGoodsDao;
import shop.front.store.dao.IShopProCateClassDao;
import shop.product.dao.IProductImgDao;
import shop.product.dao.IProductInfoDao;
import shop.product.dao.IProductSpecificationDao;
import shop.product.dao.IProductSpecificationValueDao;
import shop.product.pojo.ProductImg;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import util.other.Utils;
import util.service.BaseService;


/**
 * 商品Service接口实现类
 * 
 * @author ss
 * 
 */
public class ProductInfoService extends BaseService<ProductInfo> implements
		IProductInfoService {
	private IProductInfoDao productInfoDao;// 商品dao
	private IProductImgDao productImgDao;// 商品图片dao
	private IEvaluateGoodsDao evaluateGoodsDao;// 客户评价dao
	private IProBrRecordDao proBrRecordDao;// 浏览记录dao
	private IProductSpecificationValueDao productSpecificationValueDao;// 商品规格值中间表dao
	private IProductSpecificationDao productSpecificationDao;// 商品规格中间表dao
	private IShopProCateClassDao shopProCateClassDao;// 店铺商品内部分类关联表dao
	private IStoreApplyPromotionDao storeApplyPromotionDao;// 商品申请参加促销活动dao

	// 删除商品 同时删除相关表中的数据
	public void deleteProduct(Integer productId, ShopInfo shopInfo) {
		@SuppressWarnings("unused")
		ProductInfo productInfo = productInfoDao.get(" where o.productId="
				+ productId + " and o.shopInfoId=" + shopInfo.getShopInfoId());
		String params = " where o.productId=" + productId;
		// 删除商品对应的图片数据
		List<ProductImg> imgList = productImgDao.findObjects(params);
		if (imgList != null && imgList.size() > 0) {
			productImgDao.deleteByParams(params);
		}
		// 删除商品的客户评价
		// if (evaluateGoodsDao.findObjects(params) != null
		// && evaluateGoodsDao.findObjects(params).size() > 0) {
		// evaluateGoodsDao.deleteByParams(params);
		// }
		// 删除浏览记录中间表
		if (proBrRecordDao.findObjects(params) != null
				&& proBrRecordDao.findObjects(params).size() > 0) {
			proBrRecordDao.deleteByParams(params);
		}
		// 删除商品规格之中间表数据
		if (productSpecificationValueDao.findObjects(params) != null
				&& productSpecificationValueDao.findObjects(params).size() > 0) {
			productSpecificationValueDao.deleteByParams(params);
		}
		// 删除商品规格中间表数据
		// if (productSpecificationDao.findObjects(params) != null
		// && productSpecificationDao.findObjects(params).size() > 0) {
		// productSpecificationDao.deleteByParams(params);
		// }
		// 删除店铺内部商品分类数据
		if (shopProCateClassDao.findObjects(params) != null
				&& shopProCateClassDao.findObjects(params).size() > 0) {
			shopProCateClassDao.deleteByParams(params);
		}
		// 删除商品申请参加促销活动数据
		if (storeApplyPromotionDao.findObjects(params) != null
				&& storeApplyPromotionDao.findObjects(params).size() > 0) {
			storeApplyPromotionDao.deleteByParams(params);
		}
		// 删除商品
		productInfoDao.deleteObjectById(String.valueOf(productId));
	}

	/**
	 * 搜索引擎检索无数据，回传一些随机商品信息
	 * 
	 * @param productInfoList
	 */
	public List<ProductInfo> findRandomProductInfoList() {
		String hql = " WHERE o.isPass=1 AND o.isPutSale=2 AND o.isShow=1 order by o.putSaleDate ";
		return productInfoDao.findSome(0, 8, hql);
	}

	/**
	 * 根据商品查询分类ID、分类名称集合
	 * 
	 * @param productInfoList
	 *            商品集合
	 * @return List<Map>： map.key=productTypeId，map.value=相应的分类ID值；
	 *         map.key=sortName，map.value=相应的分类名称值；
	 *         map.key=productInfoTypeTotal，map.value=相应的分类下商品总数；
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProductToTypeInfo(List<ProductInfo> productInfoList) {
		if (productInfoList != null && productInfoList.size() > 0) {
			List productIdList = new ArrayList();// 定义商品ID集合
			for (ProductInfo productInfo : productInfoList) {// 遍历解析商品集合
			// productIdList.add(productInfo.getCategoryLevel1());
				productIdList.add(productInfo.getProductId());
			}
			String newStr = productIdList.toString().replaceAll("\\[", "(")
					.replaceAll("\\]", ")");
			String hql = "SELECT pt.productTypeId as productTypeId , pt.sortName as sortName , count(*) as productInfoTypeTotal FROM ProductInfo pi , ProductType pt WHERE pi.categoryLevel1=pt.productTypeId and pi.productId in "
					+ newStr
					+ " AND pi.isPass=1 AND pi.isPutSale=2 AND pi.isShow=1 group by pt.productTypeId , pt.sortName ";
			List<Map> findListMapByHql = productInfoDao.findListMapByHql(hql);
			return findListMapByHql;
		}
		return null;
	}

	/**
	 * 根据商品查询品牌ID、品牌名称集合
	 * 
	 * @param productInfoList
	 *            商品集合
	 * @return List<Map>： map.key=brandId，map.value=相应的品牌ID值；
	 *         map.key=brandName，map.value=相应的品牌名称值
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProductToBrandInfo(List<ProductInfo> productInfoList) {
		List productIdList = new ArrayList();// 定义商品ID集合
		for (ProductInfo productInfo : productInfoList) {// 遍历解析商品集合
			productIdList.add(productInfo.getProductId());
		}
		String productIds = Utils.listToString(productIdList, ",");
		String hql = "SELECT b.brandId as brandId , b.brandName as brandName FROM ProductInfo p , Brand b WHERE p.brandId=b.brandId AND p.isPass=1 AND p.isPutSale=2 AND p.isShow=1 AND p.productId in ("
				+ productIds + ") group by b.brandId , b.brandName ";
		List<Map> findListMapByHql = productInfoDao.findListMapByHql(hql);
		return findListMapByHql;
	}

	/**
	 * 更新商品 方便商品下架时受到事务控制，以便于更新compass的索引文件
	 */
	public ProductInfo saveOrUpdateProductInfo(ProductInfo productInfo) {
		return productInfoDao.saveOrUpdateObject(productInfo);
	}

	// setter getter
	public void setProductInfoDao(IProductInfoDao productInfoDao) {
		this.baseDao = this.productInfoDao = productInfoDao;
	}

	public void setProductImgDao(IProductImgDao productImgDao) {
		this.productImgDao = productImgDao;
	}

	public void setEvaluateGoodsDao(IEvaluateGoodsDao evaluateGoodsDao) {
		this.evaluateGoodsDao = evaluateGoodsDao;
	}

	public void setProBrRecordDao(IProBrRecordDao proBrRecordDao) {
		this.proBrRecordDao = proBrRecordDao;
	}

	public void setProductSpecificationValueDao(
			IProductSpecificationValueDao productSpecificationValueDao) {
		this.productSpecificationValueDao = productSpecificationValueDao;
	}

	public void setProductSpecificationDao(
			IProductSpecificationDao productSpecificationDao) {
		this.productSpecificationDao = productSpecificationDao;
	}

	public void setStoreApplyPromotionDao(
			IStoreApplyPromotionDao storeApplyPromotionDao) {
		this.storeApplyPromotionDao = storeApplyPromotionDao;
	}

	public void setShopProCateClassDao(IShopProCateClassDao shopProCateClassDao) {
		this.shopProCateClassDao = shopProCateClassDao;
	}
}