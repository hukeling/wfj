package shop.product.service;
import java.util.List;

import shop.product.pojo.Brand;
import util.service.IBaseService;
/**
 * 品牌Service接口
 * @author ss
 *
 */
public interface IBrandService  extends IBaseService <Brand> {
	List<Brand> findPhoneBrandListBySql(String sql);
}
