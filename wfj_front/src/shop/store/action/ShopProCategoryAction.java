package shop.store.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import shop.store.pojo.ShopProCategory;
import shop.store.service.IShopProCategoryService;
import util.action.BaseAction;
/**
 * ShopProCategoryAction - 店铺内部商品分类Action
 */
@SuppressWarnings("serial")
public class ShopProCategoryAction extends BaseAction{
	private IShopProCategoryService shopProCategoryService;//店铺分类Service
	private ShopProCategory shopProCategory;//店铺分类实体类
	private String id;
	private String shopProCategoryId;//店铺分类ID
	//跳转到店铺内部商品分类页面
	public String gotoShopProCategoryPage(){
		return SUCCESS;
	}
	//查询所有数据
	@SuppressWarnings("unchecked")
	public void getNodes() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml;charset=utf-8");
		List<ShopProCategory> list = shopProCategoryService.queryByParentId(id);
		StringBuffer sbf = new StringBuffer();
		ShopProCategory shopProCategory = null;
		sbf.append("<List>");
		for (Iterator ite = list.iterator(); ite.hasNext();) {
			shopProCategory = (ShopProCategory) ite.next();
			if (shopProCategory != null) {
				sbf.append("<ShopProCategory>");
				sbf.append("<name>").append(shopProCategory.getShopProCategoryName()).append(
						"</name>");
				sbf.append("<id>").append(shopProCategory.getShopProCategoryId()).append(
						"</id>");
				sbf.append("<loadType>").append(shopProCategory.getLoadType()).append(
					"</loadType>");
				sbf.append("</ShopProCategory>");
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
	public void saveOrEditShopProCategory() throws Exception {
		if(shopProCategory!=null){
			Integer parentId = shopProCategory.getParentId();
			if(parentId != 1){
				shopProCategoryService.saveOrUpdateFatherLoadType(parentId.toString());
				shopProCategory.setLoadType("1");
			}
			if(parentId==1){
				shopProCategory.setLoadType("1");
			}
			if(shopProCategory.getShopProCategoryId()==null){
				ShopProCategory spc = (ShopProCategory) shopProCategoryService.getObjectByParams(" where o.shopProCategoryId='"+parentId+"'");
				shopProCategory.setLevel(spc.getLevel()+1);
			}
			shopProCategoryService.saveOrUpdateObject(shopProCategory);
		}
	}
	//得到分类对象
	 public void getShopProCategoryObject() throws Exception {
		 	ShopProCategory shopProCategory = (ShopProCategory) shopProCategoryService.getObjectByParams(" where o.shopProCategoryId='"+shopProCategoryId+"'");
			JSONObject jo = JSONObject.fromObject(shopProCategory);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
	}
	 //删除分类
	 @SuppressWarnings("unchecked")
	public void delShopProCategory() throws Exception {
		Boolean isSuccess;
	    List list = shopProCategoryService.queryByParentId(shopProCategoryId);
	    if(list==null || list.size()==0){
	    	//删除的时候要判断此节点的父类还有没有子类，如果没有了就要把父类改成叶子节点，否则不变
	    	shopProCategory = (ShopProCategory) shopProCategoryService.getObjectByParams(" where o.shopProCategoryId='"+shopProCategoryId+"'");
	    	Integer parentId = shopProCategory.getParentId();
	    	isSuccess =  shopProCategoryService.deleteObjectByParams(" where o.shopProCategoryId='"+shopProCategoryId+"'");
	    	Integer count = shopProCategoryService.getCount(" where o.parentId="+parentId+"");
	    	if(count==0){
	    		ShopProCategory spc = (ShopProCategory) shopProCategoryService.getObjectByParams(" where o.shopProCategoryId='"+parentId+"'");
	    		spc.setLoadType("1");
	    		shopProCategoryService.saveOrUpdateObject(spc);
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
	public ShopProCategory getShopProCategory() {
		return shopProCategory;
	}
	public void setShopProCategory(ShopProCategory shopProCategory) {
		this.shopProCategory = shopProCategory;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShopProCategoryId() {
		return shopProCategoryId;
	}
	public void setShopProCategoryId(String shopProCategoryId) {
		this.shopProCategoryId = shopProCategoryId;
	}
	public void setShopProCategoryService(
			IShopProCategoryService shopProCategoryService) {
		this.shopProCategoryService = shopProCategoryService;
	}
}
