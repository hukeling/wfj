package phone.dao.imp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.HibernateTemplate;

import phone.dao.IPhonecategoryDao;
import phone.util.DBHelper;
import shop.product.pojo.ProductInfo;
import shop.product.pojo.ProductType;

/**
 * 
 * @author hukeling
 * 
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PhonecategoryDao implements IPhonecategoryDao {
	@SuppressWarnings("unused")
	private HibernateTemplate hibernateTemplate;
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	/**
	 * 
	 * @param productId :shop_productinfo表的productId值
	 * @return proinfoList：根据productId（产品id）查询表shop_productinfo表得到productinfo信息
	 */
	public List<ProductInfo> selectProductInfoByProductid(int productId) {
		List<ProductInfo> proinfoList = new ArrayList<ProductInfo>();
		String sql = "select * from shop_productinfo where productId=?";
		try {
			conn = DBHelper.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, productId);
			rs = pst.executeQuery();
			while (rs.next()) {
				ProductInfo p = new ProductInfo(
						rs.getInt("productId"),
						rs.getInt("brandId"),
						rs.getInt("productTypeId"),
						rs.getInt("category_level1"),
						rs.getInt("category_level2"),
						rs.getInt("category_level3"),
						rs.getInt("category_level4"),
						rs.getInt("shopInfoId"),
						rs.getString("productName"),
						rs.getString("productFullName"),
						rs.getBigDecimal("marketPrice"),
						rs.getBigDecimal("salesPrice"),
						rs.getBigDecimal("salesPriceBack"),
						rs.getBigDecimal("costPrice"),
						rs.getBigDecimal("memberPrice"),
						rs.getDouble("weight"),
						rs.getString("weightUnit"),
						rs.getString("measuringUnitName"),
						rs.getString("packingSpecifications"),
						rs.getString("specification"),
						rs.getString("manufacturerModel"),
						rs.getString("describle"),
						new java.util.Date(rs.getDate("createDate").getTime()),
						new java.util.Date(rs.getDate("updateDate").getTime()),
						new java.util.Date(rs.getDate("putSaleDate").getTime()),
						rs.getInt("isPutSale"), rs.getString("productCode"), rs
								.getString("functionDesc"), rs
								.getInt("storeNumber"), rs
								.getInt("isChargeFreight"), rs
								.getBigDecimal("freightPrice"), rs
								.getString("productAttributeValue"), rs
								.getString("productParametersValue"), rs
								.getInt("goods"), rs.getInt("productRemark"),
						rs.getInt("isPass"), rs.getString("note"), rs
								.getString("productTag"), rs
								.getString("seoTitle"), rs
								.getString("seoKeyWord"), rs
								.getString("seoDescription"), rs
								.getInt("totalHits"), rs.getInt("totalSales"),
						rs.getInt("isRecommend"), rs.getInt("isNew"), rs
								.getInt("isHot"), rs.getInt("isTop"), rs
								.getString("logoImg"), rs
								.getString("productAttribute"), rs
								.getInt("isShow"), rs
								.getBigDecimal("virtualCoinNumber"), rs
								.getBigDecimal("giveAwayCoinNumber"), rs
								.getString("barCode"), rs.getString("qrCode"),
						rs.getInt("stockUpDate"), rs
								.getInt("deliveryAddressProvince"), rs
								.getInt("deliveryAddressCities"), rs
								.getString("sku"));

				proinfoList.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.closeResuleSet(rs);
			DBHelper.closeStatement(pst);
			DBHelper.closeCon(conn);
		}
		return proinfoList;
	}

	/**
	 * 
	 * @param productTypeId :shop_productinfo表的productTypeId值
	 * @return proinfoList：根据productTypeId（类别id）查询表shop_productinfo表得到productinfo信息
	 */
	public List<ProductInfo> selectProductInfoByProducttypetid(int productTypeId) {
		List<ProductInfo> proinfoList = new ArrayList<ProductInfo>();
		String sql = "select * from shop_productinfo where productTypeId=? and isShow=1";
		try {
			conn = DBHelper.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, productTypeId);
			rs = pst.executeQuery();
			while (rs.next()) {
				ProductInfo p = new ProductInfo(
						rs.getInt("productId"),
						rs.getInt("brandId"),
						rs.getInt("productTypeId"),
						rs.getInt("category_level1"),
						rs.getInt("category_level2"),
						rs.getInt("category_level3"),
						rs.getInt("category_level4"),
						rs.getInt("shopInfoId"),
						rs.getString("productName"),
						rs.getString("productFullName"),
						rs.getBigDecimal("marketPrice"),
						rs.getBigDecimal("salesPrice"),
						rs.getBigDecimal("salesPriceBack"),
						rs.getBigDecimal("costPrice"),
						rs.getBigDecimal("memberPrice"),
						rs.getDouble("weight"),
						rs.getString("weightUnit"),
						rs.getString("measuringUnitName"),
						rs.getString("packingSpecifications"),
						rs.getString("specification"),
						rs.getString("manufacturerModel"),
						rs.getString("describle"),
						new java.util.Date(rs.getDate("createDate").getTime()),
						new java.util.Date(rs.getDate("updateDate").getTime()),
						new java.util.Date(rs.getDate("putSaleDate").getTime()),
						rs.getInt("isPutSale"), rs.getString("productCode"), rs
								.getString("functionDesc"), rs
								.getInt("storeNumber"), rs
								.getInt("isChargeFreight"), rs
								.getBigDecimal("freightPrice"), rs
								.getString("productAttributeValue"), rs
								.getString("productParametersValue"), rs
								.getInt("goods"), rs.getInt("productRemark"),
						rs.getInt("isPass"), rs.getString("note"), rs
								.getString("productTag"), rs
								.getString("seoTitle"), rs
								.getString("seoKeyWord"), rs
								.getString("seoDescription"), rs
								.getInt("totalHits"), rs.getInt("totalSales"),
						rs.getInt("isRecommend"), rs.getInt("isNew"), rs
								.getInt("isHot"), rs.getInt("isTop"), rs
								.getString("logoImg"), rs
								.getString("productAttribute"), rs
								.getInt("isShow"), rs
								.getBigDecimal("virtualCoinNumber"), rs
								.getBigDecimal("giveAwayCoinNumber"), rs
								.getString("barCode"), rs.getString("qrCode"),
						rs.getInt("stockUpDate"), rs
								.getInt("deliveryAddressProvince"), rs
								.getInt("deliveryAddressCities"), rs
								.getString("sku"));
				proinfoList.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.closeResuleSet(rs);
			DBHelper.closeStatement(pst);
			DBHelper.closeCon(conn);
		}
		return proinfoList;
	}

	/**
	 * 初始化分类菜单
	 * 
	 * @return categoryMap
	 * @throws IOExceptions
	 */

	public List<ProductType> initProdutTypeInfo() throws IOException {	
		List<ProductType> productTypeList = new ArrayList<ProductType>();// 商品分类List
		productTypeList = this.searchProductTypeById(1);// 获取一级list
		for (ProductType pt : productTypeList) {
			List<ProductType> secondList = this.searchProductTypeById(pt
					.getProductTypeId());// 获取二级list
			pt.setChildProductType(secondList);
			for (ProductType spt : secondList) {
				List<ProductType> thirdList = this.searchProductTypeById(spt
						.getProductTypeId());// 获取三级list
				spt.setChildProductType(thirdList);
			}
		}
		return productTypeList;
	}

	/**
	 * 
	 * @param parentId :shop_producttype表中的parentId
	 * @return categorylist： 根据父ID查询所分类的list
	 */

	public List<ProductType> searchProductTypeById(int parentId) {
		List categorylist = new ArrayList();
		String sql = "select * from shop_producttype where isShow=1 and parentId=?";
		try {
			conn = DBHelper.getConnection();
			pst = conn.prepareStatement(sql);
			pst.setInt(1, parentId);
			rs = pst.executeQuery();
			while (rs.next()) {
				ProductType protype = new ProductType(
						rs.getInt("productTypeId"), rs.getInt("parentId"),
						rs.getString("sortCode"), rs.getString("sortName"),
						rs.getInt("isShow"), rs.getInt("isRecommend"),
						rs.getString("categoryImage"),
						rs.getString("categoryDescription"),
						rs.getString("loadType"), rs.getInt("level"),
						rs.getInt("industrySpecific"));
				categorylist.add(protype);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.closeResuleSet(rs);
			DBHelper.closeStatement(pst);
			DBHelper.closeCon(conn);
		}
		return categorylist;
	}
}
	



