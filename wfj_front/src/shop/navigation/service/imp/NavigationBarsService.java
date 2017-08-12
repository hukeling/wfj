package shop.navigation.service.imp;
import shop.navigation.dao.INavigationBarsDao;
import shop.navigation.pojo.NavigationBars;
import shop.navigation.service.INavigationBarsService;
import util.service.BaseService;
/**
 * 导航条service层实现类
 * @author 张攀攀
 *
 */
public class NavigationBarsService extends BaseService<NavigationBars> implements INavigationBarsService{
	@SuppressWarnings("unused")
	private INavigationBarsDao navigationBarsDao;
	public void setNavigationBarsDao(INavigationBarsDao navigationBarsDao) {
		this.baseDao = this.navigationBarsDao = navigationBarsDao;
	}
}
