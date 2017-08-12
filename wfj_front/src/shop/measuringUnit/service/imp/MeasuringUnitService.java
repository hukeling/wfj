package shop.measuringUnit.service.imp;
import shop.measuringUnit.dao.IMeasuringUnitDao;
import shop.measuringUnit.pojo.MeasuringUnit;
import shop.measuringUnit.service.IMeasuringUnitService;
import util.service.BaseService;
/**
 * 计量单位Service接口实现类
 * @author wangya
 *
 */
public class MeasuringUnitService extends BaseService<MeasuringUnit> implements IMeasuringUnitService{
	@SuppressWarnings("unused")
	private IMeasuringUnitDao measuringUnitDao;
	public void setMeasuringUnitDao(IMeasuringUnitDao measuringUnitDao) {
		this.baseDao =this.measuringUnitDao= measuringUnitDao;
	}
}