package util.pojo;
import java.util.ArrayList;
import java.util.List;
public class PageHelper {
	/** 每页显示的条数 */
	private int pageSize;
	/** 总记录条数 */
	private int pageRecordCount;
	/** 总页数 */
	private int pageCount;
	/** 当前页 */
	private int pageIndex;
	/** 首页 */
	private int firstPageIndex;
	/** 尾页 */
	private int lastPageIndex;
	/** 上一页 */
	private int prePageIndex;
	/** 下一页 */
	private int nextPageIndex;
	/** 开始的索引 */
	private int pageRecordBeginIndex;
	/** 结束的索引 */
	private int pageRecordEndIndex;
	/**页面显示页码的个数**/
	private int showPageSize=10;
	private List<Integer> optionalPageIndexList = new ArrayList<Integer>();
	/**
	 * 设置页面详情。
	 * 
	 * @param pageSize
	 *            每页显示的条数
	 * @param totalRecordCount
	 *            总条数
	 * @param pageIndex
	 *            当前页
	 */
	public void setPageInfo(int pageSize, int totalRecordCount, int pageIndex) {
		this.pageSize = pageSize;
		this.pageRecordCount = totalRecordCount;
		this.pageIndex = pageIndex;
		if (pageRecordCount % pageSize == 0) {
			pageCount = pageRecordCount/pageSize ;
		} else {
			pageCount = pageRecordCount/pageSize + 1;
		}
		//查询的记录总数小于页面规定显示的记录数
		if(pageRecordCount<=pageSize){
			this.prePageIndex=1;
			this.nextPageIndex=1;
		}else{//查询的记录总数等于页面规定显示的记录数倍数，整页显示
			//每页开始的索引=每个显示的的条数*当前页-1(数据的索引是从0开始的)
			if(pageIndex==1){
				this.prePageIndex=pageIndex;
				this.nextPageIndex=pageIndex + 1;
			}else if(pageIndex<pageCount){
				this.prePageIndex=pageIndex-1;
				this.nextPageIndex=pageIndex + 1;
			}else if(pageIndex==pageCount){
				this.prePageIndex=pageIndex-1;
				this.nextPageIndex=pageCount;
			}
			int startPageIndex=pageIndex;
			int endPageIndex=showPageSize;
			if(this.pageCount<=showPageSize){
				startPageIndex=1;
				endPageIndex=pageCount;
			}else if(pageIndex>1&&pageIndex<5){
				startPageIndex=1;
				endPageIndex=showPageSize;
			}else if(pageIndex>=5){
				if((this.pageCount-pageIndex+1)<showPageSize){
					int overPageNumber=this.pageCount-pageIndex;
					if(overPageNumber<=5){
						startPageIndex=this.pageCount-showPageSize+1;
						endPageIndex=this.pageCount;
					}else{
						startPageIndex=pageIndex-4;
						endPageIndex=showPageSize+pageIndex-5;
					}
				}else{
					startPageIndex=pageIndex-4;
					endPageIndex=showPageSize+pageIndex-5;
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
		this.pageRecordBeginIndex=pageSize*(pageIndex-1);
		this.pageRecordEndIndex=this.pageRecordBeginIndex+pageSize;	
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageRecordCount() {
		return pageRecordCount;
	}
	public void setPageRecordCount(int pageRecordCount) {
		this.pageRecordCount = pageRecordCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getFirstPageIndex() {
		return firstPageIndex;
	}
	public void setFirstPageIndex(int firstPageIndex) {
		this.firstPageIndex = firstPageIndex;
	}
	public int getLastPageIndex() {
		return lastPageIndex;
	}
	public void setLastPageIndex(int lastPageIndex) {
		this.lastPageIndex = lastPageIndex;
	}
	public int getPageRecordBeginIndex() {
		return pageRecordBeginIndex;
	}
	public void setPageRecordBeginIndex(int pageRecordBeginIndex) {
		this.pageRecordBeginIndex = pageRecordBeginIndex;
	}
	public int getNextPageIndex() {
		return nextPageIndex;
	}
	public void setNextPageIndex(int nextPageIndex) {
		this.nextPageIndex = nextPageIndex;
	}
	public int getPrePageIndex() {
		return prePageIndex;
	}
	public void setPrePageIndex(int prePageIndex) {
		this.prePageIndex = prePageIndex;
	}
	public List<Integer> getOptionalPageIndexList() {
		return optionalPageIndexList;
	}
	public void setOptionalPageIndexList(List<Integer> optionalPageIndexList) {
		this.optionalPageIndexList = optionalPageIndexList;
	}
	public int getPageRecordEndIndex() {
		return pageRecordEndIndex;
	}
	public void setPageRecordEndIndex(int pageRecordEndIndex) {
		this.pageRecordEndIndex = pageRecordEndIndex;
	}
}