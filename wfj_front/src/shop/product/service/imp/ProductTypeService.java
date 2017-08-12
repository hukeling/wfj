package shop.product.service.imp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import shop.product.dao.IProductTypeDao;
import shop.product.dao.ISpecificationDao;
import shop.product.pojo.ProductType;
import shop.product.pojo.Specification;
import shop.product.service.IProductTypeService;
import util.service.BaseService;
/**
 * 商品分类Service接口实现类
 * 
 * @author ss
 * 
 */
@Service
public class ProductTypeService extends BaseService<ProductType> implements IProductTypeService {
	@Resource
	private IProductTypeDao productTypeDao;
	/**规格dao**/
	@Resource
	private ISpecificationDao specificationDao;
	public void setProductTypeDao(IProductTypeDao productTypeDao) {
		this.baseDao = this.productTypeDao = productTypeDao;
	}
	public void setSpecificationDao(ISpecificationDao specificationDao) {
		this.specificationDao = specificationDao;
	}
	/**
	 * 修改父亲的节点状态为2 1：叶子：显示删除 2：非叶子：不显示删除
	 * 
	 * @param productTypeId
	 *            商品分类ID
	 * 
	 */
	public void saveOrUpdateFatherLoadType(String productTypeId) {
		ProductType productType = (ProductType) productTypeDao
				.getObjectById(productTypeId);
		productType.setLoadType("2");
		productTypeDao.updateObject(productType);
	}
	/**
	 * 根据父ID查询子列表
	 * @param id 商品分类父ID
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("rawtypes")
	public List queryByParentId(String id) {
		return productTypeDao.findObjects(" where 1=1 and o.parentId=" + id);
	}
	/**
	 * 根据分类ID查询品牌列表
	 * @param productTypeId 商品分类ID
	 * @return 返回一个list集合
	 */
	@SuppressWarnings("rawtypes")
	public List queryBrandByProductTypeId(String productTypeId){
		String hql = "select a.brandId as brandId, a.brandName as brandName,a.brandBigImageUrl as brandImageUrl "
				+ "from Brand a,BrandType b,ProductType c where a.brandId=b.brandId and b.productTypeId=c.productTypeId and c.productTypeId in ("+productTypeId+") ";
		return productTypeDao.findListMapByHql(hql);
	}
	/**
	 * 获取规格数据
	 * @param productTypeId
	 * 			商品分类ID
	 * @return map
	 * 			key:规格名称
	 * 			value:规格值ids
	 */
	@SuppressWarnings("rawtypes")
	public Map<String,List> getSpecification(Integer productTypeId){
		Map<String,List> map=new LinkedHashMap<String,List>();
		if(productTypeId!=null){
			List<Specification> specificationList = specificationDao.findObjects(" where o.productTypeId="+productTypeId);
			if(specificationList!=null&&specificationList.size()>0){
				for(Specification sf:specificationList){
					String findSpecificationsHql="select b.specificationValueId as specificationValueId ,b.name as name , a.type as type ,b.image as image,b.specificationId as specificationId"
							+ " from Specification a,SpecificationValue b where a.productTypeId="+productTypeId+" and a.specificationId=b.specificationId and b.specificationId="+sf.getSpecificationId();
					List<Map> findListMapByHql = productTypeDao.findListMapByHql(findSpecificationsHql);
					map.put(String.valueOf(sf.getName()), findListMapByHql);
				}
			}
			return map;
		}
		return null;
	}
	//商品列表页面 左侧分类查询
		public List<ProductType> getProductTypeDir(Integer productTypeId){
			List<ProductType> productTypeList = productTypeDao.findObjects(" where o.parentId = "+productTypeId);
			//如果集合为空或没有查询出对象 则说明当前分类为三级分类 ,故查询当前分类的父分类
			if(productTypeList==null||productTypeList.size()<=0){
				//查询当前分类对象
				ProductType pt = productTypeDao.get(" where o.productTypeId="+productTypeId);
				if(pt!=null){
					//查询当前分类的父分类
					productTypeList = productTypeDao.findObjects(" where o.parentId="+pt.getParentId());
				}
			}
			return productTypeList;
		}
}