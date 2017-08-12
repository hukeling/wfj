package shop.information.service.imp;
/**
 * InformationCommentService 资讯评论  service接口实现类
 * @author mqr
 */
import shop.information.dao.IInformationCommentDao;
import shop.information.pojo.InformationComment;
import shop.information.service.IInformationCommentService;
import util.service.BaseService;
public class InformationCommentService extends BaseService<InformationComment> implements IInformationCommentService{
	@SuppressWarnings("unused")
	private IInformationCommentDao informationCommentDao;
	public void setInformationCommentDao(
			IInformationCommentDao informationCommentDao) {
		this.baseDao=this.informationCommentDao = informationCommentDao;
	}
}
