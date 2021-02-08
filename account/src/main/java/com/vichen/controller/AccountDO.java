package com.vichen.controller;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenyu
 * @date 2021/2/1
 */
@Entity
@Table(name = "seata_account")
public class AccountDO {

  /**
   * 用户ID
   */
  @Id
  private Integer id;
  /**
   * 账户
   */
  private String username;
  /**
   * 余额
   */
  private Integer amount;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "AccountDO{" +
      "id=" + id +
      ", username='" + username + '\'' +
      ", amount=" + amount +
      '}';
  }
}
