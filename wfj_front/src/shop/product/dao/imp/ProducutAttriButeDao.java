package shop.product.dao.imp;
import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import shop.product.dao.IProductAttributeDao;
import shop.product.pojo.ProductAttribute;
import util.dao.BaseDao;
/**
 * ProducutAttriBute - 类描述信息
 */
public class ProducutAttriButeDao extends BaseDao<ProductAttribute> implements IProductAttributeDao {
		/**查询所有的SHOP_商品扩展属性**/
		@SuppressWarnings("unchecked")
		public List findAllProductAttribute(){
			HibernateTemplate hibernateTemplate1 = new HibernateTemplate();
			String sql =" select b.productTypeId,b.sortName,a.productAttrId,a.info,a.name,a.productTypeId,a.sort from   shop_product_attribute  a ,shop_producttype b where a.productTypeId=b.productTypeId";
			SessionFactory sessionFactory = hibernateTemplate1.getSessionFactory();
			Session session1 = sessionFactory.openSession();
			List<Map> list=session1.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
		}
}
