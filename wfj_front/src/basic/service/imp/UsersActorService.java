package basic.service.imp;
import util.service.BaseService;
import basic.dao.IUsersActorDao;
import basic.pojo.UsersActor;
import basic.service.IUsersActorService;
/**
 * 管理员角色Service接口实现类
 * @author ss
 *
 */
public class UsersActorService extends BaseService <UsersActor> implements IUsersActorService{
	@SuppressWarnings("unused")
	private IUsersActorDao usersActorDao;
	public void setUsersActorDao(IUsersActorDao usersActorDao) {
		this.baseDao = this.usersActorDao = usersActorDao;
	}
}
