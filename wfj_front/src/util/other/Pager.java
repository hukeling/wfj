package util.other;
import java.util.ArrayList;
import java.util.List;
/**
 * Bean类 - 分页
 * KEY: SHOPJSPD42921D2B935F129755E6FCD4866E661
 */
public class Pager {
	// 排序方式
	public enum OrderType{
		asc, desc
	}
	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制
	private Integer pageNumber = 1;// 当前页码
	private Integer pageSize = 15;// 每页记录数
	private Integer totalCount = 0;// 总记录数
	private Integer pageCount = 0;// 总页数
	private String property;// 查找属性名称
	private String keyword;// 查找关键字
	private String orderBy = "createDate";// 排序字段
	private OrderType orderType = OrderType.desc;// 排序方式
	private List<?> list;//查询的数据List
	private List<?> listAll;//查询的商品全部数据List
	/** 首页 */
	private Integer firstPageIndex;
	/** 尾页 */
	private Integer lastPageIndex;
	/** 上一页 */
	private Integer prePageIndex;
	/** 下一页 */
	private Integer nextPageIndex;
	/** 开始的索引 */
	private Integer pageRecordBeginIndex;
	/** 结束的索引 */
	private Integer pageRecordEndIndex;
	/**页面显示页码的个数**/
	private Integer showPageSize=10;
	/** 页面显示的页码数集合List **/	
	private List<Integer> optionalPageIndexList = new ArrayList<Integer>();
	/**
	 * 设置页面详情。
	 * 
	 * @param pageSize
	 *            每页显示的条数
	 * @param totalCount
	 *            总条数
	 * @param pageIndex
	 *            当前页
	 */
	public void setPageInfo(int pageSize, int totalCount, int pageNumber) {
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.pageNumber = pageNumber;
		if (totalCount % pageSize == 0) {
			pageCount = totalCount/pageSize ;
		} else {
			pageCount = totalCount/pageSize + 1;
		}
		//查询的记录总数小于页面规定显示的记录数
		if(totalCount<=pageSize){
			this.prePageIndex=1;
			this.nextPageIndex=1;
		}else{//查询的记录总数等于页面规定显示的记录数倍数，整页显示
			//每页开始的索引=每个显示的的条数*当前页-1(数据的索引是从0开始的)
			if(pageNumber==1){
				this.prePageIndex=pageNumber;
				this.nextPageIndex=pageNumber + 1;
			}else if(pageNumber<pageCount){
				this.prePageIndex=pageNumber-1;
				this.nextPageIndex=pageNumber + 1;
			}else if(pageNumber==pageCount){
				this.prePageIndex=pageNumber-1;
				this.nextPageIndex=pageCount;
			}
			int startPageIndex=pageNumber;
			int endPageIndex=showPageSize;
			if(this.pageCount<=showPageSize){
				startPageIndex=1;
				endPageIndex=pageCount;
			}else if(pageNumber>1&&pageNumber<5){
				startPageIndex=1;
				endPageIndex=showPageSize;
			}else if(pageNumber>=5){
				if((this.pageCount-pageNumber+1)<showPageSize){
					int overPageNumber=this.pageCount-pageNumber;
					if(overPageNumber<=5){
						startPageIndex=this.pageCount-showPageSize+1;
						endPageIndex=this.pageCount;
					}else{
						startPageIndex=pageNumber-4;
						endPageIndex=showPageSize+pageNumber-5;
					}
				}else{
					startPageIndex=pageNumber-4;
					endPageIndex=showPageSize+pageNumber-5;
				}
			}
			for (int i =startPageIndex; i <=endPageIndex ; i++){
				if(i<=this.pageCount){
						optionalPageIndexList.add(i);
				}
			}
		}
		this.firstPageIndex=1;
		this.lastPageIndex=pageCount;
		this.pageRecordBeginIndex=pageSize*(pageNumber-1);
		this.pageRecordEndIndex=this.pageRecordBeginIndex+pageSize;	
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public Integer getFirstPageIndex() {
		return firstPageIndex;
	}
	public void setFirstPageIndex(Integer firstPageIndex) {
		this.firstPageIndex = firstPageIndex;
	}
	public Integer getLastPageIndex() {
		return lastPageIndex;
	}
	public void setLastPageIndex(Integer lastPageIndex) {
		this.lastPageIndex = lastPageIndex;
	}
	public Integer getPrePageIndex() {
		return prePageIndex;
	}
	public void setPrePageIndex(Integer prePageIndex) {
		this.prePageIndex = prePageIndex;
	}
	public Integer getNextPageIndex() {
		return nextPageIndex;
	}
	public void setNextPageIndex(Integer nextPageIndex) {
		this.nextPageIndex = nextPageIndex;
	}
	public Integer getPageRecordBeginIndex() {
		return pageRecordBeginIndex;
	}
	public void setPageRecordBeginIndex(Integer pageRecordBeginIndex) {
		this.pageRecordBeginIndex = pageRecordBeginIndex;
	}
	public Integer getPageRecordEndIndex() {
		return pageRecordEndIndex;
	}
	public void setPageRecordEndIndex(Integer pageRecordEndIndex) {
		this.pageRecordEndIndex = pageRecordEndIndex;
	}
	public Integer getShowPageSize() {
		return showPageSize;
	}
	public void setShowPageSize(Integer showPageSize) {
		this.showPageSize = showPageSize;
	}
	public List<Integer> getOptionalPageIndexList() {
		return optionalPageIndexList;
	}
	public void setOptionalPageIndexList(List<Integer> optionalPageIndexList) {
		this.optionalPageIndexList = optionalPageIndexList;
	}
	public List<?> getListAll() {
		return listAll;
	}
	public void setListAll(List<?> listAll) {
		this.listAll = listAll;
	}
}