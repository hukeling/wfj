package shop.homeModule.service.imp;
import shop.homeModule.dao.IDayRecommendDao;
import shop.homeModule.pojo.DayRecommend;
import shop.homeModule.service.IDayRecommendService;
import util.service.BaseService;
/**
 * DayRecommendService - 前台每日推荐Service接口实现类
 * @author 张攀攀
 * 2014-01-07
 *
 */
public class DayRecommendService extends BaseService<DayRecommend> implements IDayRecommendService{
	private IDayRecommendDao dayRecommendDao;
	/**
	 * 保存每日推荐商品
	 */
	public Boolean saveOrUpdateDayRecommend(DayRecommend dayRecommends,String productData){
		String[] productDatas = productData.split(",");
		Integer isShow = dayRecommends.getIsShow();
		if(productDatas!=null && productDatas.length>0){
			for(int i=0;i<productDatas.length;i++){//批量添加
				DayRecommend dayRecommend = new DayRecommend();
				String[] idAndName = productDatas[i].split("@");//0:Id;1:name
				dayRecommend.setProductId(Integer.parseInt(idAndName[0]));
				dayRecommend.setIsShow(isShow);
				dayRecommend.setSort(0);
				dayRecommendDao.saveOrUpdateObject(dayRecommend);
			}
		}
		return null;
	}
	public void setDayRecommendDao(IDayRecommendDao dayRecommendDao) {
		this.baseDao = this.dayRecommendDao = dayRecommendDao;
	}
}
