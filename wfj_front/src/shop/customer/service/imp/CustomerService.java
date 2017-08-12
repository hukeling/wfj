package shop.customer.service.imp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import rating.buyersRecord.dao.IBuyersRecordDao;
import rating.buyersRecord.pojo.BuyersRecord;
import rating.buyersStrategy.service.IBuyersStrategyService;
import shop.customer.dao.ICustomerDao;
import shop.customer.dao.IShopCustomerInfoDao;
import shop.customer.pojo.Customer;
import shop.customer.pojo.ShopCustomerInfo;
import shop.customer.service.ICustomerService;
import shop.lineofcreditItem.dao.ILineofcreditItemDao;
import shop.store.dao.IShopInfoDao;
import shop.store.pojo.ShopInfo;
import util.action.SecurityCode;
import util.action.SecurityCode.SecurityCodeLevel;
import util.other.Utils;
import util.service.BaseService;
/**
 * 客户信息Service接口实现类
 * 
 * @author ss
 * 
 */
@SuppressWarnings("all")
public class CustomerService extends BaseService<Customer> implements
		ICustomerService {
	private ICustomerDao customerDao;
	private IShopCustomerInfoDao shopCustomerDao;
	private IBuyersRecordDao buyersRecordDao;//买家升迁记录
	private IBuyersStrategyService buyersStrategyService;//买家升级策略
	private ILineofcreditItemDao lineofcreditItemDao;//授信明细
	private IShopInfoDao shopInfoDao;//企业信息
	public boolean saveOrUpdateShengJiWoDeShenFen(Customer customer){
		Customer newCustomer = customerDao.getObjectById(String.valueOf(customer.getCustomerId()));
		newCustomer.setType(customer.getType());//会员类型默认为买家  0
		newCustomer = customerDao.saveOrUpdateObject(newCustomer);
		return true;
	}
	/**
	 * 注册用户
	 */
	@SuppressWarnings("static-access")
	public Customer saveCustomer(Customer customer) {
		customer.setLockedState(0);//可用的用户
		Utils ut = new Utils();
		String encodeMd5 = ut.EncodeMd5(customer.getPassword());//密码加密
		String encodeMd52 = ut.EncodeMd5(encodeMd5);
		customer.setPassword(encodeMd52); 
		customer.setRegisterDate(new Date());//注册时间
		customer.setType(customer.getType());//会员类型默认为买家  0
		Customer c = customerDao.saveObject(customer);
		return c;
	}
	/**
	 * 注册用户--手机
	 */
	public Customer saveCustomerByPhone(Customer customer) {
		customer.setLockedState(0);//可用的用户
		customer.setRegisterDate(new Date());//注册时间
		customer.setType(customer.getType());//会员类型默认为买家  0
		//产生4位随机字母
		String str = SecurityCode.getSecurityCode(4,SecurityCodeLevel.onlyChar, true);
		//获取用户的手机号
		String phone=customer.getLoginName();
		/*拼装用户loginName
	       规则：手机号+下斜线+随机字母（4位随机数），比如：94876_oppo）
		 */
		String loginName=phone+"_"+str;
		customer.setLoginName(loginName);
		Customer c = customerDao.saveObject(customer);
		return c;
	}
	/**
	 * 通过sql语句修改单个对象或者批量对象
	 */
	public boolean updateObject(String sql){
		return customerDao.updateObject(sql);
	}
	public void setCustomerDao(ICustomerDao customerDao) {
		this.baseDao = this.customerDao = customerDao;
	}
	public void setShopCustomerDao(IShopCustomerInfoDao shopCustomerDao) {
		this.shopCustomerDao = shopCustomerDao;
	}
	/**
	 * 注册
	 * @param customer 用户对象
	 * @param trueName  真实姓名
	 * @param cardNo  身份证号
	 * @param ip  
	 */
	@SuppressWarnings({ "rawtypes", "static-access", "unused" })
	public Customer saveRegister(Customer customer,String trueName,String cardNo,String ip,String registerType){
		Customer newCustomer=new Customer();
		newCustomer.setRegisterIp(ip);
		newCustomer.setRegisterDate(new Date());
		newCustomer.setLockedState(0);//0：未冻结1：已冻结
		newCustomer.setType(customer.getType());//会员类型 1 企业 2 卖家 3普通用户
		newCustomer.setShareType(1);//默认设置分享类型为1  普通会员
		newCustomer.setIsCanBuy(1);//是否可以采购  默认为  是  1
		if(customer!=null){
			//产生4位随机字母
			String str = SecurityCode.getSecurityCode(4,SecurityCodeLevel.onlyChar, true);
//			if(customer.getLoginName()==null){//pc端注册
			//获取用户的邮箱
			String email=customer.getEmail();
			/*拼装用户loginName
		       规则：邮箱@前字符+下斜线+随机字母（4位随机数），比如：94876_oppo）
			 */
			String loginName=email.split("@")[0]+"_"+str;
			Utils ut = new Utils();
			String encodeMd5 = ut.EncodeMd5(customer.getPassword());//密码加密
			String encodeMd52 = ut.EncodeMd5(encodeMd5);
			newCustomer.setLoginName(loginName);
			newCustomer.setEmail(customer.getEmail());
			newCustomer.setPassword(encodeMd52); 
			newCustomer.setPhone(customer.getPhone());
			newCustomer = customerDao.saveOrUpdateObject(newCustomer);// 注册用户
			if(newCustomer!=null&&newCustomer.getCustomerId()!=null){
				//保存个性信息
				ShopCustomerInfo customerInfo = new ShopCustomerInfo();// 新建会员个性信息
				customerInfo.setCustomerId(newCustomer.getCustomerId());//存入用户ID
				customerInfo.setSex(3);//性别  默认为 保密 3
				shopCustomerDao.saveOrUpdateObject(customerInfo);
			}
			BuyersRecord buyersRecord = new BuyersRecord();
			if(customer.getType()==1||customer.getType()==3){//注册选择买家，买家升迁记录添加最低级别的买家策略
				String hql="select a.buyerRank as buyerRank, a.levelIcon as levelIcon, a.buyersLevel as buyersLevel, a.lineOfCredit as lineOfCredit, a.creditDate as creditDate, a.levelDiscountValue as levelDiscountValue,"
						+" a.minRefValue as minRefValue, a.maxRefValue as maxRefValue from BuyersStrategy a where 1=1 order by a.lineOfCredit asc";
				List<Map> lm = buyersStrategyService.findListMapByHql(hql);
				if(lm!=null&&lm.size()>0){
					Map bs = lm.get(0);
					Object buyerRank = bs.get("buyerRank");
					Object levelIcon = bs.get("levelIcon");
					Object buyersLevel = bs.get("buyersLevel");
					Object lineOfCredit = bs.get("lineOfCredit");
					Object creditDate = bs.get("creditDate");
					Object minRefValue = bs.get("minRefValue");
					Object maxRefValue = bs.get("maxRefValue");
					buyersRecord.setCustomerId(newCustomer.getCustomerId());
					buyersRecord.setBuyerRank(buyerRank.toString());
					buyersRecord.setLevelIcon(levelIcon.toString());
					buyersRecord.setBuyersLevel(Integer.valueOf(buyersLevel.toString()));
					buyersRecord.setLineOfCredit(new BigDecimal(lineOfCredit.toString()));//买家升迁记录授信额度
					buyersRecord.setCreditDate(Integer.valueOf(creditDate.toString()));//买家升迁记录授信期限
					buyersRecord.setMinRefValue(Integer.valueOf(minRefValue.toString()));//买家升迁记录最小值
					buyersRecord.setMaxRefValue(Integer.valueOf(maxRefValue.toString()));//买家升迁记录最大值
					buyersRecord.setLevelDiscountValue(Integer.valueOf(bs.get("levelDiscountValue").toString()));//
					buyersRecord.setRemark("注册选择买家自动初始化为最低级别会员,卖家需手动");//买家升迁记录备注
					buyersRecord.setOptionDate(new Date());//买家升迁记录操作时间
					buyersRecord.setOperator("系统");//买家升迁记录操作人
					buyersRecordDao.saveOrUpdateObject(buyersRecord);
				}
				/**初始化授信明细**/
				/*LineofcreditItem li = new LineofcreditItem();//新建授信明细
				li.setCustomerId(newCustomer.getCustomerId());//插入用户ID
				li.setSerialNumber(SerialNumberUtil.VirtualCoinNumber());//虚拟金币流水号
				li.setType(1);//类型为1代表收入
				li.setOrdersId(0);// 没有订单ID 所以填0
				li.setOrdersNo("");
				li.setTradeTime(new Date());// 时间
				li.setOperatorTime(new Date());
				li.setRemarks("注册所得");
				li.setTransactionNumber(new BigDecimal(0));// 0
				li.setRemainingNumber(buyersRecord.getLineOfCredit());// 余额
				li.setFrozenNumber(new BigDecimal(0));//冻结
				lineofcreditItemDao.saveOrUpdateObject(li);*/
			}
			ShopInfo si=new  ShopInfo();
			si.setCustomerId(newCustomer.getCustomerId());
//			si.setIsPass(1);
			si.setIsClose(0);
			si.setCustomerName(newCustomer.getLoginName());
			shopInfoDao.saveOrUpdateObject(si);
			return newCustomer;
		}
		return null;
	}
	public void setBuyersRecordDao(IBuyersRecordDao buyersRecordDao) {
		this.buyersRecordDao = buyersRecordDao;
	}
	public void setLineofcreditItemDao(ILineofcreditItemDao lineofcreditItemDao) {
		this.lineofcreditItemDao = lineofcreditItemDao;
	}
	public void setBuyersStrategyService(
			IBuyersStrategyService buyersStrategyService) {
		this.buyersStrategyService = buyersStrategyService;
	}
	public void setShopInfoDao(IShopInfoDao shopInfoDao) {
		this.shopInfoDao = shopInfoDao;
	}
}
