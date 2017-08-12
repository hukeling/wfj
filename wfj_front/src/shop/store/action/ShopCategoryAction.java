package shop.store.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import shop.store.pojo.ShopCategory;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopCategoryService;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
/**
 * ShopCategoryAction - 店铺分类Action
 */
@SuppressWarnings("serial")
public class ShopCategoryAction extends BaseAction{
	private IShopCategoryService shopCategoryService;//店铺分类Service
	private IShopInfoService shopInfoService;//店铺信息service
	private ShopCategory shopCategory;//店铺分类实体类
	private String id;
	private String shopCategoryId;//店铺分类ID
	//跳转到店铺分类页面
	public String gotoShopCategoryPage(){
		return SUCCESS;
	}
	//查询所有数据
	@SuppressWarnings("unchecked")
	public void getNodes() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		List<ShopCategory> list = shopCategoryService.queryByParentId(id);
		StringBuffer sbf = new StringBuffer();
		ShopCategory shopCategory = null;
		sbf.append("<List>");
		for (Iterator ite = list.iterator(); ite.hasNext();) {
			shopCategory = (ShopCategory) ite.next();
			if (shopCategory != null) {
				sbf.append("<ShopCategory>");
				sbf.append("<name>").append(shopCategory.getShopCategoryName()).append(
						"</name>");
				sbf.append("<id>").append(shopCategory.getShopCategoryId()).append(
						"</id>");
				sbf.append("<loadType>").append(shopCategory.getLoadType()).append(
					"</loadType>");
				sbf.append("</ShopCategory>");
			}
		}
		sbf.append("</List>");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(sbf.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//新增或许修改新闻分类 
	public void saveOrEditShopCategory() throws Exception {
		if(shopCategory!=null){
			Integer parentId = shopCategory.getParentId();
			if(parentId != 1){
				shopCategoryService.saveOrUpdateFatherLoadType(parentId.toString());
				shopCategory.setLoadType("1");
			}
			if(parentId==1){
				shopCategory.setLoadType("1");
			}
			if(shopCategory.getShopCategoryId()==null){
				ShopCategory sc = (ShopCategory) shopCategoryService.getObjectByParams(" where o.shopCategoryId='"+parentId+"'");
				shopCategory.setLevel(sc.getLevel()+1);
			}
			shopCategoryService.saveOrUpdateObject(shopCategory);
		}
	}
	//得到分类对象
	 public void getShopCategoryObject() throws Exception {
		 	ShopCategory shopCategory = (ShopCategory) shopCategoryService.getObjectByParams(" where o.shopCategoryId='"+shopCategoryId+"'");
			JSONObject jo = JSONObject.fromObject(shopCategory);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
	}
	 //删除分类
	 @SuppressWarnings("unchecked")
	public void delShopCategory() throws Exception {
		Boolean isSuccess;
	    List list = shopCategoryService.queryByParentId(shopCategoryId);
	    if(list==null || list.size()==0){
	    	//删除的时候要判断此节点的父类还有没有子类，如果没有了就要把父类改成叶子节点，否则不变
	    	shopCategory = (ShopCategory) shopCategoryService.getObjectByParams(" where o.shopCategoryId='"+shopCategoryId+"'");
	    	Integer parentId = shopCategory.getParentId();
	    	isSuccess =  shopCategoryService.deleteObjectByParams(" where o.shopCategoryId='"+shopCategoryId+"'");
	    	Integer count = shopCategoryService.getCount(" where o.parentId="+parentId+"");
	    	if(count==0){
	    		ShopCategory sc = (ShopCategory) shopCategoryService.getObjectByParams(" where o.shopCategoryId='"+parentId+"'");
	    		sc.setLoadType("1");
	    		shopCategoryService.saveOrUpdateObject(sc);
	    	}
	    }else{
	    	isSuccess = false;
	    }
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	 /**
	  * 跳转到店铺信息列表-根据分类id
	  */
	 public String gotoShopInfoPageByShopCategoryId(){
		//查询店铺分类名称
		 ShopCategory shopCategory = (ShopCategory) shopCategoryService.getObjectByParams(" where o.shopCategoryId="+shopCategoryId);
		 request.setAttribute("shopCategoryName", shopCategory.getShopCategoryName());
		 return SUCCESS;
	 }
	 /**
	 * 根据店铺分类查询店铺信息
	 * @throws IOException 
	 */
	 public void findShopInfoByShopCategoryId() throws IOException{
		 //查询该分类下的店铺信息
		 Integer totalRecordCount=shopInfoService.getCount(" where o.shopCategoryId="+shopCategoryId);
		 pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		 List<ShopInfo> shopInfoList = shopInfoService.findListByPageHelper(null, pageHelper, " where o.shopCategoryId="+shopCategoryId);
		 Map<String, Object> jsonMap = new HashMap<String, Object>();
		 jsonMap.put("total", totalRecordCount);
		 jsonMap.put("rows", shopInfoList);
		 JSONObject jo = JSONObject.fromObject(jsonMap);
		 response.setContentType("text/html;charset=utf-8");
		 PrintWriter out = response.getWriter();
		 out.println(jo.toString());
		 out.flush();
		 out.close();
	 }
	public ShopCategory getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(ShopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setShopCategoryService(IShopCategoryService shopCategoryService) {
		this.shopCategoryService = shopCategoryService;
	}
	public String getShopCategoryId() {
		return shopCategoryId;
	}
	public void setShopCategoryId(String shopCategoryId) {
		this.shopCategoryId = shopCategoryId;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
}
