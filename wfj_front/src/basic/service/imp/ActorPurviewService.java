package basic.service.imp;
import util.service.BaseService;
import basic.dao.IActorPurviewDao;
import basic.pojo.ActorPurview;
import basic.service.IActorPurviewService;
/**
 * 角色权限Service接口实现类
 * @author ss
 *
 */
public class ActorPurviewService extends BaseService <ActorPurview> implements IActorPurviewService{
	@SuppressWarnings("unused")
	private IActorPurviewDao actorPurviewDao;
	public void setActorPurviewDao(IActorPurviewDao actorPurviewDao) {
		this.baseDao = this.actorPurviewDao = actorPurviewDao;
	}
}
