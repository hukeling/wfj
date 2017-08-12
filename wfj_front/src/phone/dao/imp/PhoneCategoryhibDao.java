package phone.dao.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import phone.dao.IPhoneCategoryhibDao;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;

/**
 * 
 * @author hukeling
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes","unused" })
public class PhoneCategoryhibDao  implements IPhoneCategoryhibDao{
	
	HibernateTemplate hibernateTemplate;
	
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 获取hibernatesession
	 * @return
	 */
	public Session getSession() {
		try {
			SessionFactory sf = hibernateTemplate.getSessionFactory();
			return sf.openSession();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查询所有的记录
	 */
	public List  findObjects(String tablename,String whereCondition) {
		String hql="";
		if(tablename!=null){
		 hql = " from "+ tablename + "  o ";
		}
		if (whereCondition != null)
			hql += whereCondition;
		List <ProductType> list=new ArrayList();
		Session session = null;
		try {
			session = this.getSession();
			list = hibernateTemplate.find(hql);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	/**
	 * 
	 * @param brandId:品牌id
	 * @return 根据品牌id查询出的商品信息
	 */
	public List<ProductInfo> proInfoListByBrandId(int brandId){
		String wherehql="where o.isShow=1 and brandId="+brandId;
		List<ProductInfo> list=this.findObjects("ProductInfo", wherehql);
		return list;
	}
	/**
	 * 
	 * @return 推荐商品列表
	 */
	public List<ProductInfo> recommandPro(){
		String wherehql="where o.isShow=1 and isRecommend=1";
		List<ProductInfo> list=this.findObjects("ProductInfo", wherehql);
		return list;
	}
	
	/**
	 * @param keyword:商品名关键字
	 * @return 根据商品名关键字查询所有商品列
	 */
	public List<ProductInfo> mohuSearch(String keyword){
		String wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%' ";
		List<ProductInfo> list=this.findObjects("ProductInfo", wherehql);
		return list;
	}
	/**
	 * @param keyword:商品名关键字
	 * @param orderBy:排序方式
	 * @return 根据商品名关键字查询并按照指定标准排序后的所有商品列
	 */
	public List<ProductInfo> mohuSearchOrderBy(String keyword,String orderBy){
		String wherehql="";
		if(orderBy==null){
			wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%'";
		 }else if(orderBy.equals("normal")){
			 wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%'";
		 }else if(orderBy.equals("priceup")){
			 wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%' order by  salesPrice";
		 }else if(orderBy.equals("pricedown")){
			 wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%' order by salesPrice desc";
		 }else if(orderBy.equals("totalSalesdown")){
			 wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%' order by  totalSales desc";
		 }else if(orderBy.equals("totalSalesup")){
			 wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%' order by  totalSales";
		 }else{
			 wherehql="where o.isShow=1 and productFullName like '%"+keyword+"%'";
		 }
		 List<ProductInfo> list=this.findObjects("ProductInfo", wherehql);
		 return list;
	}
	
	/**
	 * 
	 * @return categoryMap:三级分类信息
	 * @throws IOException
	 */

	 public List initProdutTypeInfo()throws IOException{

		 List<ProductType> productTypeList = new ArrayList<ProductType>();//商品分类List
//					List<ProductType> categoryList = new ArrayList<ProductType>();
					productTypeList = this.findObjects("ProductType"," where o.isShow=1 and o.parentId=1 order by o.sortCode");//一级分类集合
					for(ProductType pt : productTypeList){
						List<ProductType> secondProductTypeList = this.findObjects("ProductType"," where o.parentId='"+pt.getProductTypeId()+"' and o.isShow=1");//二级分类集合
						pt.setChildProductType(secondProductTypeList);//将一级的子类设为secondProductTypeList
						for(ProductType spt : secondProductTypeList){
							List<ProductType> thirdProductTypeList = this.findObjects("ProductType"," where o.parentId='"+spt.getProductTypeId()+"' and o.isShow=1");//三级分类集合
							spt.setChildProductType(thirdProductTypeList);//将二级的子类设为thirdProductTypeList
						}
					}
					return productTypeList;
				}
	 /**
	  * 
	  * @param productTypeId
	  * @return secondProductTypeList:根据一级分类Id获取的二级分类list
	  */
	 public List<ProductType> listCategory(int productTypeId){
		 List<ProductType> secondProductTypeList = new ArrayList<ProductType>();
		 secondProductTypeList=this.findObjects("ProductType"," where o.parentId='"+productTypeId+"' and o.isShow=1");//根据一级分类id查询二级分类
		 for(ProductType protype : secondProductTypeList){
			 List<ProductType> thirdProductTypeList = this.findObjects("ProductType"," where o.parentId='"+protype.getProductTypeId()+"' and o.isShow=1");//根据二级分类查询三级分类集合
			 protype.setChildProductType(thirdProductTypeList);//将二级的子类设为thirdProductTypeList
		 }
		 return secondProductTypeList;
	 }
	 /**
	  * 
	  * @param productTypeId
	  * @return 根据二级id查询三级分类的list
	  */
	 public List<ProductType> listSanCategory(int productTypeId){
		 List<ProductType> thirdProductTypeList = new ArrayList<ProductType>();
		 thirdProductTypeList=this.findObjects("ProductType", "where o.parentId='"+productTypeId+"' and o.isShow=1");//根据二级id查询三级分类
		 return thirdProductTypeList;
	 }
	 
	 /**
	  * 
	  * @param producttypeId:产品类别ID
	  * @return productinfoList:根据产品类别id（producttypeid）查询productinfo得到产品信息
	  */ 
	 public List selectProductInfoByProducttypetid(int producttypeId){
		 List<ProductInfo> productinfoList=new ArrayList<ProductInfo>();
		 productinfoList=this.findObjects("ProductInfo","where o.productTypeId='"+producttypeId+"' and isShow=1");
		 return productinfoList;
	 }
	 /**
	  * 
	  * @param producttypeId:产品类别id
	  * @param orderBy：产品列表排序标准
	  * @return 按标准排序的商品list
	  */
	 public List selectProInfoByTypeId(int producttypeId, String orderBy){
		 String hql="";
		 List<ProductInfo> productinfoList=new ArrayList<ProductInfo>();
		 if(orderBy==null){
			 hql="where o.productTypeId='"+producttypeId+"' and isShow=1";
		 }else if(orderBy.equals("normal")){
			 hql="where o.productTypeId='"+producttypeId+"' and isShow=1";
		 }else if(orderBy.equals("priceup")){
			 hql="where o.productTypeId='"+producttypeId+"' and isShow=1 order by  salesPrice";
		 }else if(orderBy.equals("pricedown")){
			 hql="where o.productTypeId='"+producttypeId+"' and isShow=1 order by  salesPrice desc";
		 }else if(orderBy.equals("totalSalesdown")){
			 hql="where o.productTypeId='"+producttypeId+"' and isShow=1 order by  totalSales desc";
		 }else if(orderBy.equals("totalSalesup")){
			 hql="where o.productTypeId='"+producttypeId+"' and isShow=1 order by  totalSales";
		 }else{
			 hql="where o.productTypeId='"+producttypeId+"' and isShow=1";
		 }
		 productinfoList=this.findObjects("ProductInfo",hql);
		 return productinfoList;
	 }
	 /**
	  * 
	  * @param productid:产品ID
	  * @return productinfoList：根据产品id（productid）查询productinfo得到产品信息
	  */
	 public List selectProductInfoByProductid(int productid){
		 List<ProductInfo> productinfoList=new ArrayList<ProductInfo>();
		 productinfoList=this.findObjects("ProductInfo","where o.productId='"+productid+"' and isShow=1");
		 return productinfoList;
	 }
	 }

