package shop.lineofcreditItem.service.imp;
import shop.lineofcreditItem.dao.ILineofcreditItemDao;
import shop.lineofcreditItem.pojo.LineofcreditItem;
import shop.lineofcreditItem.service.ILineofcreditItemService;
import util.service.BaseService;
/**
 * 授信额度Service接口实现类
 * 
 * @author wsy
 * 
 */
public class LineofcreditItemService extends BaseService<LineofcreditItem> implements
		ILineofcreditItemService {
	/** 授信额度dao **/
	@SuppressWarnings("unused")
	private ILineofcreditItemDao lineofcreditItemDao;
	public void setLineofcreditItemDao(ILineofcreditItemDao lineofcreditItemDao) {
		this.baseDao = this.lineofcreditItemDao = lineofcreditItemDao;
	}
}