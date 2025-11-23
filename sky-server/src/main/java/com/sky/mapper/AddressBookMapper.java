package com.sky.mapper;

import com.sky.entity.AddressBook;
import lombok.Data;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 插入地址数据
     *
     * @param addressBook
     */
    void insert(AddressBook addressBook);

    /**
     * 条件查询地址数据
     *
     * @param addressBook
     * @return
     */
    List<AddressBook> list(AddressBook addressBook);

    /**
     * 更新地址数据
     */
    void update(AddressBook addressBook);

    /**
     * 根据id查询地址数据
     */
    @Select("select * from address_book where id = #{id}")
    AddressBook getById(Long id);

    /**
     * 根据id删除地址数据
     */
    @Delete("delete from address_book where id = #{id}")
    void deleteById(Long id);

    /**
     * 根据 userId 修改 isDefault
     */
    void updateIsDefaultByUserId(AddressBook addressBook);
}
