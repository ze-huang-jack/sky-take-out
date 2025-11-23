package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询当前登录用户的所有地址信息
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list() {
        log.info("查询当前登录用户的所有地址信息");
        List<AddressBook> list = addressBookService.list();
        return Result.success(list);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default")
    public Result<String> setDefault(@RequestBody AddressBook addressBook) {
        log.info("设置默认地址: {}", addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("/default")
    public Result<AddressBook> defaultAddress() {
        log.info("查询默认地址");
        AddressBook defaultAddress = addressBookService.getDefaultAddress();
        return Result.success(defaultAddress);
    }

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public Result<String> add(@RequestBody AddressBook addressBook) {
        log.info("新增地址: {}", addressBook);
        addressBookService.add(addressBook);
        return Result.success();
    }

    /**
     * 根据id删除地址
     */
    @DeleteMapping
    public Result<String> delete(Long id) {
        log.info("根据id删除地址: {}", id);
        addressBookService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AddressBook> get(@PathVariable Long id) {
        log.info("根据id查询地址: {}", id);
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     */
    @PutMapping
    public Result<String> update(@RequestBody AddressBook addressBook) {
        log.info("根据id修改地址: {}", addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }
}
