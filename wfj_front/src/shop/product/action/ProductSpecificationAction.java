package shop.product.action;
import java.util.ArrayList;
import java.util.List;

import shop.product.pojo.ProductSpecification;
import shop.product.service.IProductSpecificationService;
import util.action.BaseAction;
/**
 * ProductSpecificationAction - 商品和规格Action类
 */
@SuppressWarnings("serial")
public class ProductSpecificationAction extends BaseAction{
	@SuppressWarnings("unused")
	private IProductSpecificationService productSpecificationService;//商品和规格Service
	@SuppressWarnings("unused")
	private List<ProductSpecification> productSpecificationList = new ArrayList<ProductSpecification>();//商品和规格List
	@SuppressWarnings("unused")
	private String productSpecificationId;
	@SuppressWarnings("unused")
	private String ids;
}
