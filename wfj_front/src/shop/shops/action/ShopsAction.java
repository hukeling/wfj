package shop.shops.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.shops.pojo.Shops;
import shop.shops.service.IShopsService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import util.other.Utils;
import basic.service.IAreaService;
/**
 * 商城(线下实际商城)action的操作
 * @author 郑月龙
 */
@SuppressWarnings("serial")
public class ShopsAction extends BaseAction {
	/**商城(线下实际商城)Service*/
	private IShopsService shopsService;
	/**商城(线下实际商城)对象*/
	private Shops shops;
	/**商城对象ID*/
	private String shopsId;
	/**商城对象ID串*/
	private String ids;
	/**商城名称*/
	private String shopsName;
	/**商城编号*/
	private String shopsCode;
	/**商城对象集合*/
	private List<Shops> shopsList = new ArrayList<Shops>();
	/**区域service*/
	private IAreaService areaService;
	private List<Map> firstAreaList;//一级地址
	private Integer areaId;
	
	/**
	 * 跳转到商城列表页面
	 */
	public String gotoShopsPage() throws Exception {
		String firstHql="select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
    	firstAreaList=areaService.findListMapByHql(firstHql);
		return SUCCESS;
	}
	
	/**
	 * 异步查询商城信息
	 */
	public void findShopsList() throws Exception{
		//定义查询语句
		StringBuffer hqlsb = new StringBuffer();
		//定义统计记录数量语句
		StringBuffer countHql = new StringBuffer();
		hqlsb.append(" where 1=1 ");
		countHql.append(" where 1=1 ");
		//查询条件：shopsName不为空
		if(Utils.objectIsNotEmpty(shopsName)){
			hqlsb.append(" and o.shopsName like'%"+shopsName+"%'");
			countHql.append(" and o.shopsName like'%"+shopsName+"%'");
		}
		//查询条件：商城编号不为空
		if(Utils.objectIsNotEmpty(shopsCode)){
			hqlsb.append(" and o.shopsCode like'%"+shopsCode+"%'");
			countHql.append(" and o.shopsCode like'%"+shopsCode+"%'");
		}
		//查询开始时间
		String startTime = request.getParameter("startTime");
		//查询条件：开始时间
		if(Utils.stringIsNotEmpty(startTime)){
			hqlsb.append(" and o.createTime>='"+startTime.trim()+"'");
			countHql.append(" and o.createTime>='"+startTime.trim()+"'");
		}
		//查询结束时间
		String endTime = request.getParameter("endTime");
		//查询条件：结束时间
		if(Utils.stringIsNotEmpty(endTime)){
			hqlsb.append(" and o.createTime<='"+endTime.trim()+"'");
			countHql.append(" and o.createTime<='"+endTime.trim()+"'");
		}
		//排序字段
		hqlsb.append(" order by o.shopsId desc");
		//得到记录数量
		int totalRecordCount = shopsService.getCount(countHql.toString());
		//设置分页信息
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		//得到一页商城对象集合
		shopsList = shopsService.findListByPageHelper(null, pageHelper, hqlsb.toString());
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", shopsList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 保存或修改对象
	 */
	public void saveOrUpdateShops(){
		//如果对象不为空则保存或修改
		if(Utils.objectIsNotEmpty(shops)){
			//当前时间对象
			Date createTime = new Date();
			//如果对象中的shopsId属性不为空则为修改记录
			if(Utils.objectIsNotEmpty(shops.getShopsId())){
				shops.setModifyTime(createTime);//设置最后修改时间
			}else{
				//时间格式化
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				//当前时间被格式化后的字符串
				String createTimeStr = sdf.format(createTime);
				//产生一个[0，1)之间的随机数。
				Double random = Math.random();
				//截取6位随机数
				String num = random.toString().substring(2, 8);
				//当前时间与随机数连接起来得到商城编码
				String shopsCode = createTimeStr+num;
				shops.setShopsCode(shopsCode);//设置商城编码
				shops.setCreateTime(createTime);//设置创建时间
				shops.setModifyTime(createTime);//刚创建对象时最后修改时间设置为创建时间
			}
			//保存对象
			shopsService.saveOrUpdateObject(shops);
		}
	}
	/**
	 * 删除对象
	 */
	public void deleteShops(){
		//如果shopsId不为空则进行删除一条记录
		if(Utils.objectIsNotEmpty(ids)){
			//根据商城记录id删除记录
			shopsService.deleteObjectsByIds("shopsId", ids);
		}
	}
	/**
	 * 根据id查询一个对象
	 * @throws IOException
	 */
	public void getShopsObject() throws IOException{
		//shops = (Shops)shopsService.getObjectByParams(" where o.shopsId="+shopsId);
		shops = (Shops)shopsService.getObjectById(shopsId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(shops,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jo.toString());
		out.flush();
		out.close();
	}
	//根据areaId 查询下级区域
	public void findAreaList() throws IOException{
		String firstHql="select a.name as name,a.areaId as areaId,a.parentId as parent from BasicArea a where a.parentId="+areaId+" order by a.areaId";
    	List<Map> areaList=areaService.findListMapByHql(firstHql);
		JSONArray jo = JSONArray.fromObject(areaList);//格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		pw = response.getWriter();
		pw.write(String.valueOf(jo));
		pw.flush();
		pw.close();
	}
	public Shops getShops() {
		return shops;
	}

	public void setShops(Shops shops) {
		this.shops = shops;
	}

	public String getShopsId() {
		return shopsId;
	}

	public void setShopsId(String shopsId) {
		this.shopsId = shopsId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getShopsName() {
		return shopsName;
	}

	public void setShopsName(String shopsName) {
		this.shopsName = shopsName;
	}

	public String getShopsCode() {
		return shopsCode;
	}

	public void setShopsCode(String shopsCode) {
		this.shopsCode = shopsCode;
	}

	public List<Shops> getShopsList() {
		return shopsList;
	}

	public void setShopsList(List<Shops> shopsList) {
		this.shopsList = shopsList;
	}

	public void setShopsService(IShopsService shopsService) {
		this.shopsService = shopsService;
	}

	public List<Map> getFirstAreaList() {
		return firstAreaList;
	}

	public void setFirstAreaList(List<Map> firstAreaList) {
		this.firstAreaList = firstAreaList;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
}
