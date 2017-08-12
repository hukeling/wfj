package shop.measuringUnit.service.imp;
import shop.measuringUnit.dao.IProductMeasuringUnitDao;
import shop.measuringUnit.pojo.ProductMeasuringUnit;
import shop.measuringUnit.service.IProductMeasuringUnitService;
import util.service.BaseService;
/**
 * 商品计量单位Service接口实现类
 * @author wangya
 *
 */
public class ProductMeasuringUnitService extends BaseService<ProductMeasuringUnit> implements IProductMeasuringUnitService{
	private IProductMeasuringUnitDao productMeasuringUnitDao;
	public void setProductMeasuringUnitDao(IProductMeasuringUnitDao productMeasuringUnitDao) {
		this.baseDao =this.productMeasuringUnitDao= productMeasuringUnitDao;
	}
	/**
	 * 批量保存商品计量单位关系
	 * @param brandIds
	 * @param measuringUnitId
	 * @return
	 */
	public boolean saveMoreProductMeasuringUnit(Integer[] measuringUnitIds,Integer productTypeId ){
		boolean flag = true;
		for(Integer measuringUnitId:measuringUnitIds){
			ProductMeasuringUnit productMeasuringUnit = new ProductMeasuringUnit();
			productMeasuringUnit.setProductMeasuringUnitId(null);
			productMeasuringUnit.setMeasuringUnitId(measuringUnitId);
			productMeasuringUnit.setProductTypeId(productTypeId);
			productMeasuringUnit = productMeasuringUnitDao.saveOrUpdateObject(productMeasuringUnit);
			if(productMeasuringUnit.getProductMeasuringUnitId()==null){
				flag = false;
			}
		}
		return flag;
	}
}