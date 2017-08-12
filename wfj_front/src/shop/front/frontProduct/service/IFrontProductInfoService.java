package shop.front.frontProduct.service;
import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import shop.product.pojo.ProductAttrIndex;
import shop.product.pojo.ProductImg;
import shop.product.pojo.ProductInfo;
import util.service.IBaseService;
/**
 * 商品Service接口
 * @author ss
 *
 */
public interface IFrontProductInfoService  extends IBaseService <ProductInfo> {
	/**
	 * 根据多条的规格和规格值组，生成多个商品
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateListProductInfo(String shopProCategoryId,ProductInfo productInfo,String parameters,List<ProductImg> listProductImage,Map fileUrlConfig,List<File> listProductUploadImgs,List<String>  listProductUploadImgsFileName,JSONArray jsonArray,List<ProductAttrIndex> paiList);
	public void updateFrontProductSpecification(Integer productId,String goods,String parameters);
	/**
	 * 添加商品没有规格
	 * @param shopProCategoryId店铺内部分类
	 * @param productInfo商品信息
	 * @param listProductImage商品图片
	 * @param fileUrlConfig上传图片的配置文件
	 * @param listProductUploadImgs上传图片路径
	 * @param listProductUploadImgsFileName上传图片名称
	 */
	public void saveOrUpdateProductInfo(String shopProCategoryId,ProductInfo productInfo,List<ProductImg> listProductImage,Map fileUrlConfig,List<File> listProductUploadImgs,List<String>  listProductUploadImgsFileName);
	/***
	 * 修改商品的基本信息
	 * @param product
	 * @param shopProCategoryId
	 */
	public Boolean saveOrUpdateBasicProduct(ProductInfo product,Integer shopProCategoryId);
	/**
	 * 修改商品图片
	 * @param productInfo商品信息
	 * @param listProductImage商品图片集合
	 * @param fileUrlConfig上传路径配置
	 * @param listProductUploadImgs上传路径集合
	 * @param listProductUploadImgsFileName上传名称集合
	 */
	public Boolean saveOrUpdateProdImg(ProductInfo productInfo,List<ProductImg> listProductImage);
	/**
	 * 解除该商品所在商品组的规格以及对应的规格值的关联关系
	 * @param productId  商品Id
	 */
	public boolean removeProductSpecificationValueGoodsGuanlian(Integer productId,String optionProductId);
	/**
	 * 修改规格
	 * @param 
	 * @throws Exception 
	 */
	public boolean updateFrontProductSpecification(String specifications,Integer oldProductId) throws Exception ;
	/**
	 *  详情页滚动图片上传
	 * @param imagePath
	 * @param imagePathFileName
	 * @param productImg
	 * @param fileUrlConfig
	 * @return
	 * @throws Exception 
	 */
	public ProductImg uploadProductImage(File imagePath,String imagePathFileName,ProductImg productImg,Map<Object,Object> fileUrlConfig) throws Exception ;
}
