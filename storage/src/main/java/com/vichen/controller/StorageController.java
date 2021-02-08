package com.vichen.controller;

import io.seata.core.context.RootContext;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyu
 * @date 2021/2/1
 */
@RestController
public class StorageController {

  private final StorageRepository repository;

  public StorageController(StorageRepository repository) {
    this.repository = repository;
  }

  @PostMapping("costStorage")
  public String createOrder(@RequestParam Integer storageId, @RequestParam Integer number) {

    Optional<StorageDO> oStorage = repository.findById(storageId);
    if (!oStorage.isPresent()) {
      return "error";
    }

    StorageDO storage = oStorage.get();
    storage.setNumber(storage.getNumber() - number);
    repository.save(storage);
    return "success";
  }
}
