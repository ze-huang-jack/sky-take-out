package com.sky.service;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressBookService {

    void add(AddressBook addressBook);

    List<AddressBook> list();

    void setDefault(AddressBook addressBook);

    void deleteById(Long id);

    AddressBook getById(Long id);

    void update(AddressBook addressBook);

    AddressBook getDefaultAddress();
}
