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
public class AccountController {

  final private AccountRepository repository;

  public AccountController(AccountRepository repository) {
    this.repository = repository;
  }

  @PostMapping("costAccountAmount")
  public String costAccountAmount(@RequestParam Integer userId, @RequestParam Integer amount) {

    Optional<AccountDO> oAccount = repository.findById(userId);
    if (!oAccount.isPresent()) {
      return "error";
    }

    AccountDO account = oAccount.get();
    account.setAmount(account.getAmount() - amount);
    repository.save(account);
    return "success";
  }

}
