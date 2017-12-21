package com.boot.controller;

import com.boot.dao.AccountDao;
import com.boot.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountDao accountDao;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return accountDao.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") int id) {
        return accountDao.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateAccount(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
                                 @RequestParam(value = "money", required = true) double money) {
        Account account = new Account();
        account.setMoney(money);
        account.setName(name);
        account.setId(id);
        Account t = accountDao.saveAndFlush(account);
        return t.toString();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "money") double money) {
        Account account = new Account();
        account.setMoney(money);
        account.setName(name);
        Account t = accountDao.save(account);
        return t.toString();
    }
}
