package com.vichen.controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenyu
 * @date 2021/2/1
 */
@Entity
@Table(name = "seata_order")
public class OrderDO {

  /**
   * 订单编号
   */
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer orderNo;
  /**
   * 用户账户
   */
  private Integer userId;
  /**
   * 订单金额
   */
  private Integer totalAmount;
  /**
   * 库存ID
   */
  private Integer storageId;
  /**
   * 库存数量
   */
  private Integer storageNumber;

  public Integer getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(Integer orderNo) {
    this.orderNo = orderNo;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Integer getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Integer totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Integer getStorageId() {
    return storageId;
  }

  public void setStorageId(Integer storageId) {
    this.storageId = storageId;
  }

  public Integer getStorageNumber() {
    return storageNumber;
  }

  public void setStorageNumber(Integer storageNumber) {
    this.storageNumber = storageNumber;
  }

  @Override
  public String toString() {
    return "OrderDO{" +
      "orderNo=" + orderNo +
      ", userId=" + userId +
      ", totalAmount=" + totalAmount +
      ", storageId=" + storageId +
      ", storageNumber=" + storageNumber +
      '}';
  }
}
