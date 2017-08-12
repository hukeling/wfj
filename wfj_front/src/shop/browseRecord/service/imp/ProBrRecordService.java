package shop.browseRecord.service.imp;
import shop.browseRecord.dao.IProBrRecordDao;
import shop.browseRecord.pojo.ProBrRecord;
import shop.browseRecord.service.IProBrRecordService;
import util.service.BaseService;
/**
 * 浏览商品记录service层实现类
 * @author 张攀攀
 *
 */
public class ProBrRecordService extends BaseService<ProBrRecord> implements IProBrRecordService{
	@SuppressWarnings("unused")
	private IProBrRecordDao proBrRecordDao;
	public void setProBrRecordDao(IProBrRecordDao proBrRecordDao) {
		this.baseDao = this.proBrRecordDao = proBrRecordDao;
	}
}
