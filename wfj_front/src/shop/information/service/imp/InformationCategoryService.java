package shop.information.service.imp;
/**
 * InformationCategoryService 店铺内部资讯分类  service接口实现类
 * @author mqr
 */
import shop.information.dao.IInformationCategoryDao;
import shop.information.pojo.InformationCategory;
import shop.information.service.IInformationCategoryService;
import util.service.BaseService;
public class InformationCategoryService extends BaseService<InformationCategory> implements IInformationCategoryService{
	@SuppressWarnings("unused")
	private IInformationCategoryDao informationCategoryDao;
	public void setInformationCategoryDao(
			IInformationCategoryDao informationCategoryDao) {
		this.baseDao=this.informationCategoryDao = informationCategoryDao;
	}
}
