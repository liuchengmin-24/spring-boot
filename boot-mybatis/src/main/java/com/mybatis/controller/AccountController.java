package com.mybatis.controller;

import com.mybatis.entity.Account;
import com.mybatis.mapper.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountMapper accountMapper;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return accountMapper.selectAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Account getAccountById(@PathVariable("id") int id) {
        Account account= new Account();
        account.setId(id);
        return accountMapper.selectOne(account);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateAccount(@PathVariable("id") int id, @RequestParam(value = "name", required = true) String name,
                                @RequestParam(value = "money", required = true) double money) {
        Account account= new Account();
        account.setName(name);
        account.setMoney(money);
        account.setId(id);
        int account1 = accountMapper.updateByPrimaryKey(account);
        if(account1==1){
            return "success";
        }else{
            return "fail";
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String postAccount(@RequestParam(value = "name") String name,
                              @RequestParam(value = "money") double money) {
        Account account= new Account();
        account.setName(name);
        account.setMoney(money);
        int account1 = accountMapper.insert(account);
        if(account1==1){
            return "success";
        }else{
            return "fail";
        }
    }
}
