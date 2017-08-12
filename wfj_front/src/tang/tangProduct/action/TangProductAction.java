package tang.tangProduct.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import tang.tangProduct.pojo.TangProduct;
import tang.tangProduct.service.ITangProductService;
import tang.tangstore.pojo.TangStore;
import tang.tangstore.service.ITangStoreService;
import util.action.BaseAction;
@Controller
public class TangProductAction extends BaseAction {
	private static final long serialVersionUID = -14921656331926235L;
	@Resource
	ITangProductService tangProductService;
	
	ITangStoreService tangStoreService;
	  
	List<TangProduct> tangProductList=new ArrayList<TangProduct>();
	private TangProduct tangProduct; 
	
	private TangStore tangStore;
	
	private String storeId;
	
	private String tangProductId;
	
	private String tangProductIds;
	
	/**
	 * 跳转到商品管理页面
	 */
	public String gotoProductPage(){
		if(storeId!=null&&!"".equals(storeId)){
			tangStore=(TangStore)tangStoreService.getObjectById(storeId);
		}
		return SUCCESS;
	}
	
	public void getProductType(){
		
	}
	
	public void getShopCategory(){
		
	}
	
	/**
	 *获取商品分页列表
	 * @throws IOException 
	 */
	public void listProduct() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		hqlsb.append(" where 1=1");
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String productName = request.getParameter("productName");
			String isShow = request.getParameter("isShow");
//			String storeId =request.getParameter("storeId");
			if(StringUtils.isNotEmpty(productName)){
				productName = productName.trim();
				hqlsb.append(" and o.productName like '%"+productName+"%'");
			}
			if(!"-1".equals(isShow)){
				hqlsb.append(" and o.isShow = "+isShow);
			}
		}
		if(storeId!=null&&!"".equals(storeId)){
			hqlsb.append(" and o.storeId= "+Integer.valueOf(storeId));
		}
		hqlsb.append(" order by o.tangProductId desc");
		int totalRecordCount = tangProductService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		tangProductList = tangProductService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", tangProductList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 根据产品Id获取一条商品数据
	 * @throws IOException 
	 */
	public void getOneProduct() throws IOException{
		tangProduct = (TangProduct) tangProductService.getObjectByParams(" where o.tangProductId='"+tangProductId+"'");
		JSONObject jo = JSONObject.fromObject(tangProduct);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 保存或修改商品
	 * @throws IOException 
	 */
	public void saveOrUpdateProduct() throws IOException{
		if(tangProduct!=null){
			JSONObject jo = new JSONObject(); 
			tangProduct = (TangProduct)tangProductService.saveOrUpdateObject(tangProduct);
				if(tangProduct.getStoreId()!=null){
					jo.accumulate("isSuccess", "true");
				}
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	/**
	 * 批量删除商品
	 * @throws IOException 
	 */
	public void deleteproduct() throws IOException{
		JSONObject jo = new JSONObject();
		Boolean isSuccess =tangProductService.deleteObjectsByIds("tangProductId", tangProductIds);
 		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	 
	 
	public void setTangProductService(ITangProductService tangProductService) {
		this.tangProductService = tangProductService;
	}
	public TangProduct getTangProduct() {
		return tangProduct;
	}
	public void setTangProduct(TangProduct tangProduct) {
		this.tangProduct = tangProduct;
	}
	public String getTangProductIds() {
		return tangProductIds;
	}
	public void setTangProductIds(String tangProductIds) {
		this.tangProductIds = tangProductIds;
	}
	public String getTangProductId() {
		return tangProductId;
	}
	public void setTangProductId(String tangProductId) {
		this.tangProductId = tangProductId;
	}

	public TangStore getTangStore() {
		return tangStore;
	}

	public void setTangStore(TangStore tangStore) {
		this.tangStore = tangStore;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public void setTangStoreService(ITangStoreService tangStoreService) {
		this.tangStoreService = tangStoreService;
	}
	
	
	
}
