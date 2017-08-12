package shop.store.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;

/**
 * 供应商商城币收支明细实体类
 * @author lmh
 * **/
public class SupplierMallCoinDetail extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -8078175652601656431L;
	/**
	 * 供应商商城币收支明细ID
	 * **/
	private Integer supplierMallCoinDetailId;
	/**
	 * 供应商ID
	 * **/
	private Integer supplierId;
	/**
	 * 供应商登陆名
	 * **/
	private String supplierLoginName;
	/**
	 * 订单ID
	 * **/
	private Integer ordersId;
	/**
	 * 订单号
	 * **/
	private String ordersNo;
	/**
	 * 交易时间
	 * **/
	private Date tradingTime;
	/**
	 * 收支类型
	 * **/
	private Integer type;
	/**
	 * 交易商城币
	 * **/
	private BigDecimal tradMallCoin;
	/**
	 * 总收入
	 * **/
	private BigDecimal totalInPut;
	/**
	 * 总支出
	 * **/
	private BigDecimal totalOutPut;
	
	
	public Integer getSupplierMallCoinDetailId() {
		return supplierMallCoinDetailId;
	}
	public void setSupplierMallCoinDetailId(Integer supplierMallCoinDetailId) {
		this.supplierMallCoinDetailId = supplierMallCoinDetailId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierLoginName() {
		return supplierLoginName;
	}
	public void setSupplierLoginName(String supplierLoginName) {
		this.supplierLoginName = supplierLoginName;
	}
	public Integer getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(Integer ordersId) {
		this.ordersId = ordersId;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public Date getTradingTime() {
		return tradingTime;
	}
	public void setTradingTime(Date tradingTime) {
		this.tradingTime = tradingTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public BigDecimal getTradMallCoin() {
		return tradMallCoin;
	}
	public void setTradMallCoin(BigDecimal tradMallCoin) {
		this.tradMallCoin = tradMallCoin;
	}
	public BigDecimal getTotalInPut() {
		return totalInPut;
	}
	public void setTotalInPut(BigDecimal totalInPut) {
		this.totalInPut = totalInPut;
	}
	public BigDecimal getTotalOutPut() {
		return totalOutPut;
	}
	public void setTotalOutPut(BigDecimal totalOutPut) {
		this.totalOutPut = totalOutPut;
	}
}
