package basic.service.imp;
import util.service.BaseService;
import basic.dao.IUsersDao;
import basic.pojo.Users;
import basic.service.IUsersService;
/**
 * 后台管理员Service接口实现类
 * @author ss
 *
 */
public class UsersService extends BaseService  <Users> implements IUsersService {
	@SuppressWarnings("unused")
	private IUsersDao usersDao;
	public void setUsersDao(IUsersDao usersDao) {
		this.baseDao =this.usersDao= usersDao;
	}
}
