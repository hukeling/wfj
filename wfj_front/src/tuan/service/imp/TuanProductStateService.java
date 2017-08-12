package tuan.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tuan.pojo.TuanProduct;
import tuan.service.ITuanProductService;

/**
 * 团购商品锁定定时器Service
 * @author Administrator
 * 2014-12-02
 */
public class TuanProductStateService {
	/**团购申请Service**/
	private ITuanProductService tuanProductService;
	/**团购商品结束集合**/
	List<TuanProduct> tuanProductList = new ArrayList<TuanProduct>();
	
	
	/**
	 * 团购结束，修改状态
	 */
	@SuppressWarnings("unchecked")
	public void closeState(){
		Date date = new Date();//系统当前时间
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateEnd = dateformat.format(date);
		tuanProductList = tuanProductService.findObjects(null, " where o.state = 1 and UNIX_TIMESTAMP(o.endTime) < UNIX_TIMESTAMP('"+dateEnd+"')");
		for(TuanProduct tuanProduct:tuanProductList){
			tuanProduct.setState(3);//团购已结束
			tuanProductService.saveOrUpdateObject(tuanProduct);
		}
	}


	public List<TuanProduct> getTuanProductList() {
		return tuanProductList;
	}
	public void setTuanProductList(List<TuanProduct> tuanProductList) {
		this.tuanProductList = tuanProductList;
	}
	public void setTuanProductService(ITuanProductService tuanProductService) {
		this.tuanProductService = tuanProductService;
	}
}
