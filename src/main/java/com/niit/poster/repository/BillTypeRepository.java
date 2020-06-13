package com.niit.poster.repository;

import com.niit.poster.domain.BillType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillTypeRepository extends JpaRepository<BillType, Long> {

    /**
     * 根据 分类排序 查询 所有海报分类
     * 通过 JPA
     * 分页
     * @param billTypeSort
     * @param page
     * @return
     */
    Page<BillType> findByBillTypeSort(Integer billTypeSort, Pageable page);

}
