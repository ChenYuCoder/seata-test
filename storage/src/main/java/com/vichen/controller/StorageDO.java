package com.vichen.controller;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenyu
 * @date 2021/2/1
 */
@Entity
@Table(name = "seata_storage")
public class StorageDO {

  /**
   * 库存ID
   */
  @Id
  private Integer id;
  /**
   * 库存数量
   */
  private Integer number;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "StorageDO{" +
      "id=" + id +
      ", number=" + number +
      '}';
  }
}
