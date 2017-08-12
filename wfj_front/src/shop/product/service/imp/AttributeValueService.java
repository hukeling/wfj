package shop.product.service.imp;

import shop.product.dao.IAttributeValueDao;
import shop.product.pojo.AttributeValue;
import shop.product.service.IAttributeValueService;
import util.service.BaseService;
/**
 * ProductAttributeService - 类描述信息
 */
public class AttributeValueService extends BaseService<AttributeValue> implements
		IAttributeValueService {
	@SuppressWarnings("unused")
	private IAttributeValueDao attributeValueDao;
	public void setAttributeValueDao(IAttributeValueDao attributeValueDao) {
		this.baseDao = this.attributeValueDao = attributeValueDao;
	}
}
