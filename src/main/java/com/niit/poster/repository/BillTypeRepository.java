package com.niit.poster.repository;

import com.niit.poster.domain.BillType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the BillType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillTypeRepository extends JpaRepository<BillType, Long> {


//    查
//    /**
//     * 功能可实现，无太大意义
//     * 通过 JPA 方式实现     分页查询 指定分类排序(bill_type_sort) 的海报分类，若不指定分类排序则 查全部海报分类
//     * @param billTypeSort  分类排序参数，若分类参数为 null 则默认查询全部
//     * @param page  分页参数
//     * @return
//     */
//    Page<BillType> findAllByBillTypeSort(Integer billTypeSort, Pageable page);

    /**
     * 通过 JPA 方式实现     查询 分类排序为倒叙 的全部海报分类
     * @return
     */
    List<BillType> findAllByOrderByBillTypeSortDesc();

}
