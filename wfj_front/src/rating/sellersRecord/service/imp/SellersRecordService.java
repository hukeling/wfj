package rating.sellersRecord.service.imp;
import rating.sellersRecord.dao.ISellersRecordDao;
import rating.sellersRecord.pojo.SellersRecord;
import rating.sellersRecord.service.ISellersRecordService;
import util.service.BaseService;
/**
 * 卖家等级升迁记录Service接口实现
 * @author wsy
 *
 */
public class SellersRecordService extends BaseService<SellersRecord> implements ISellersRecordService {
	@SuppressWarnings("unused")
	private ISellersRecordDao sellersRecordDao;//卖家等级升迁记录Dao
	public void setSellersRecordDao(ISellersRecordDao sellersRecordDao) {
		this.baseDao = this.sellersRecordDao = sellersRecordDao;
	}
}
