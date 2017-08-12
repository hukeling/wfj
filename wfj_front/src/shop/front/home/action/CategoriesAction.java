package shop.front.home.action;
import util.action.BaseAction;
/**
 * CategoriesAction - 分类目录Action类
 * @author 孟琦瑞
 */
@SuppressWarnings("serial")
public class CategoriesAction extends BaseAction{
	private String headerType;
	//跳转到分类目录页面
	public String gotoShopCategoriesPage(){
		return SUCCESS;
	}
	/**
	 * setter getter
	 * @param categoriesService
	 */
	public String getHeaderType() {
		return headerType;
	}
	public void setHeaderType(String headerType) {
		this.headerType = headerType;
	}
}
