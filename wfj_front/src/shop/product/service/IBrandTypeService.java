package shop.product.service;
import shop.product.pojo.BrandType;
import util.service.IBaseService;
/**
 * 品牌和分类Service接口
 * @author ss
 *
 */
public interface IBrandTypeService  extends IBaseService <BrandType> {
	/**
	 * 批量保存品牌分类关系
	 * @param brandIds
	 * @param productTypeId
	 * @return
	 */
	public boolean saveMoreBrandType(Integer[] brandIds,Integer productTypeId );
}
