package com.niit.poster.service;

import com.niit.poster.domain.BillType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BillType}.
 */
public interface BillTypeService {

    /**
     * Save a billType.
     *
     * @param billType the entity to save.
     * @return the persisted entity.
     */
    BillType save(BillType billType);

    /**
     * Get all the billTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BillType> findAll(Pageable pageable);


    /**
     * Get the "id" billType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillType> findOne(Long id);

    /**
     * Delete the "id" billType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


//    查
    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     查询 全部海报分类
     * @return
     */
    List<BillType> getAllBillTypeJdbc();

//    /**
//     * 功能可实现，无太大意义
//     * 通过 JPA 方式实现     分页查询 指定分类排序(bill_type_sort) 的海报分类，若不指定分类排序则 查全部海报分类
//     * @param billTypeSort  分类排序参数，若分类参数为 null 则默认查询全部
//     * @param pageIndex  页码，默认为0
//     * @param pageSize  页长，默认为5
//     * @return
//     */
//    Page<BillType> getAllBillTypeJpaPaged(Integer billTypeSort,Integer pageIndex, Integer pageSize);

    /**
     * 通过 JPA 方式实现     查询 分类排序为倒叙 的全部海报分类
     * @return
     */
    List<BillType> getAllBillTypeJpa();

}
