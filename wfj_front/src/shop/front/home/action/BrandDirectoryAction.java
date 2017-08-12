package shop.front.home.action;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import shop.product.pojo.Brand;
import shop.product.service.IBrandService;
import util.action.BaseAction;
/** 
 * BrandDirectoryAction - 品牌目录
 * @author 孟琦瑞
 */
public class BrandDirectoryAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private IBrandService brandService;//品牌service
	private String flag="All";//锚点的标记
	private List<Brand> recommendBrandList=new ArrayList<Brand>();//推荐品牌List
	private Map<String,List<Brand>> map=new LinkedHashMap<String,List<Brand>>();//map集合
	public String gotoBrandDirectoryPage(){
		//找出0-9的品牌
		String params=" where subString(o.brandName,1,1) in('0','1','2','3','4','5','6','7','8','9')";
		List<Brand> numList = brandService.findObjects(params);
		if(numList.size()>0){
			map.put("numList", numList);
		}
		//找出A-Z的品牌
		for(int i=0;i<26;i++){
			int ascllNum=65+i;
			String letter=String.valueOf((char)ascllNum);
			List<Brand> list=brandService.findObjects("where o.brandName like '"+letter+"%'");
			if(list!=null && list.size()>0){
				map.put(letter+"List", list);
			}
		}
		//查询推荐品牌
		pageHelper.setPageSize(3);
		recommendBrandList=brandService.findListByPageHelper(null, pageHelper, " where o.isRecommend=1 and o.isShow=1 order by o.sortCode");
		return SUCCESS;
	}
	//根据条件查询
	public String queryByFlag(){
		if("NUM".equals(flag)){
			//找出0-9的品牌
			String params=" where subString(o.brandName,1,1) in('0','1','2','3','4','5','6','7','8','9')";
			List<Brand> numList = brandService.findObjects(params);
			map.put("numList", numList);
		}else{
			List<Brand> list=brandService.findObjects("where o.brandName like '"+flag+"%'");
			map.put(flag+"List", list);
		}
		//查询推荐品牌
		pageHelper.setPageSize(3);
		recommendBrandList=brandService.findListByPageHelper(null, pageHelper, " where o.isRecommend=1 and o.isShow=1 order by o.sortCode");
		return SUCCESS;
	}
	public void setBrandService(IBrandService brandService) {
		this.brandService = brandService;
	}
	public Map<String, List<Brand>> getMap() {
		return map;
	}
	public void setMap(Map<String, List<Brand>> map) {
		this.map = map;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<Brand> getRecommendBrandList() {
		return recommendBrandList;
	}
	public void setRecommendBrandList(List<Brand> recommendBrandList) {
		this.recommendBrandList = recommendBrandList;
	}
}
