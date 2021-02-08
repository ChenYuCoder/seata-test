package com.vichen.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chenyu
 * @date 2021/2/1
 */
@Repository
public interface StorageRepository extends JpaRepository<StorageDO, Integer> {

}
