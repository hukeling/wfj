package rating.buyersRecord.service.imp;
import rating.buyersRecord.dao.IBuyersRecordDao;
import rating.buyersRecord.pojo.BuyersRecord;
import rating.buyersRecord.service.IBuyersRecordService;
import util.service.BaseService;
/**
 * 买家等级升迁记录Service接口实现
 * @author wsy
 *
 */
public class BuyersRecordService extends BaseService<BuyersRecord> implements IBuyersRecordService {
	@SuppressWarnings("unused")
	private IBuyersRecordDao buyersRecordDao;//买家等级升迁记录Dao
	public void setBuyersRecordDao(IBuyersRecordDao buyersRecordDao) {
		this.baseDao = this.buyersRecordDao = buyersRecordDao;
	}
}
