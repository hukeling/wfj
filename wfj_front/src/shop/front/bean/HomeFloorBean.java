package shop.front.bean;
import java.util.List;
import java.util.Map;

import shop.product.pojo.Brand;
/**
 * SHOPJSP 商城首页楼层封装的实体bean
 */
public class HomeFloorBean {
	//楼层ID（即所有分类的一级分类ID）
	private Integer floorId;
	//楼层名称（即所有分类的一级分类的名称）
	private String floorName;
	//该楼层下的品牌列表
	private List<Brand> floorBrandList;
	//该楼层下的子分类的商品列表
	private Map<Object,List<?>> floorChildProductMap;
	public Integer getFloorId() {
		return floorId;
	}
	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public List<Brand> getFloorBrandList() {
		return floorBrandList;
	}
	public void setFloorBrandList(List<Brand> floorBrandList) {
		this.floorBrandList = floorBrandList;
	}
	public Map<Object, List<?>> getFloorChildProductMap() {
		return floorChildProductMap;
	}
	public void setFloorChildProductMap(Map<Object, List<?>> floorChildProductMap) {
		this.floorChildProductMap = floorChildProductMap;
	}
}
