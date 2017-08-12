package shop.store.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;

/**
 * 供应商佣金币收支明细实体类
 * @author lmh
 * **/
public class SupplierBrokerageCoinDetail extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -4674975374762389817L;
	/**
	 * 供应商佣金币收支明细ID
	 * **/
	private Integer supplierBrokerageCoinDetailId;
	/**
	 * 供应商ID
	 * **/
	private Integer supplierId;
	/**
	 * 供应商登录名
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
	 * 赠送佣金币
	 * **/
	private BigDecimal giveBrokerageCoin;
	/**
	 * 总支出
	 * **/
	private BigDecimal totalOutPut;
	
	
	
	public Integer getSupplierBrokerageCoinDetailId() {
		return supplierBrokerageCoinDetailId;
	}
	public void setSupplierBrokerageCoinDetailId(
			Integer supplierBrokerageCoinDetailId) {
		this.supplierBrokerageCoinDetailId = supplierBrokerageCoinDetailId;
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
	public BigDecimal getGiveBrokerageCoin() {
		return giveBrokerageCoin;
	}
	public void setGiveBrokerageCoin(BigDecimal giveBrokerageCoin) {
		this.giveBrokerageCoin = giveBrokerageCoin;
	}
	public BigDecimal getTotalOutPut() {
		return totalOutPut;
	}
	public void setTotalOutPut(BigDecimal totalOutPut) {
		this.totalOutPut = totalOutPut;
	}

}
