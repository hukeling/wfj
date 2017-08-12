package basic.service.imp;
import util.service.BaseService;
import basic.dao.IActorDao;
import basic.pojo.Actor;
import basic.service.IActorService;
/**
 * 角色Service接口实现类
 * @author ss
 *
 */
public class ActorService extends BaseService  <Actor> implements IActorService{
	@SuppressWarnings("unused")
	private IActorDao actorDao;
	public void setActorDao(IActorDao actorDao) {
		this.baseDao =this.actorDao= actorDao;
	}
}
