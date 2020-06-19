package com.niit.poster.service;

import com.niit.poster.domain.BillInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BillInfo}.
 */
public interface BillInfoService {

    /**
     * Save a billInfo.
     *
     * @param billInfo the entity to save.
     * @return the persisted entity.
     */
    BillInfo save(BillInfo billInfo);

    /**
     * Get all the billInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BillInfo> findAll(Pageable pageable);


    /**
     * Get the "id" billInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillInfo> findOne(Long id);

    /**
     * Delete the "id" billInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


//    查
//    /**
//     * 通过 JdbcTemplate(SQL语句) 方式实现     查询海报信息
//     * @return
//     */
//    List<BillInfo> getAllBillInfoJdbcOld();

//    /**
//     * 通过 JdbcTemplate(SQL语句) 方式实现     根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
//     * @param keywords
//     * @param billTypeId
//     * @return
//     */
//    List<BillInfo> getAllBillInfoJdbcOld2(String keywords, Integer billTypeId);

    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param userName  登录用户
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<BillInfo> getAllBillInfoJdbc(String keywords, Integer billTypeId, String userName, Integer pageIndex, Integer pageSize);

    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param userName  登录用户
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    Page<BillInfo> getAllBillInfoJdbcPaged(String keywords, Integer billTypeId, String userName, Integer pageIndex, Integer pageSize);

    /**
     * 通过 JPA 方法名定义方式实现     分页查询 根据 海报文字(bill_word) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    Page<BillInfo> getAllBillByBillWordLikeJpa(String keywords,Integer pageIndex, Integer pageSize);

    /**
     * 通过 JPA 方法名定义方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    Page<BillInfo> getAllBillJpa(String keywords,Long billTypeId,Integer pageIndex, Integer pageSize);

    /**
     * 通过 JPA @Query注解 方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    Page<BillInfo> getAllBillJpaQuery(String keywords,Long billTypeId,Integer pageIndex, Integer pageSize);

    /**
     * 通过 JPA @Query注解 方式实现     登录用户 分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 查询自己的海报信息
     * @param keywords  查询关键字(海报文字)
     * @param billTypeId 海报类型ID
     * @param pageIndex  页码
     * @param pageSize  页长
     * @return
     */
    Page<BillInfo> getMyBills(String keywords,Long billTypeId, Integer pageIndex, Integer pageSize);

//    增
    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     新增海报
     * @param billInfo
     * @return
     */
    BillInfo creatBillJdbc(BillInfo billInfo);

    /**
     * 通过 JPA 方式实现     新增海报(调用已有的Repository)
     * @param billInfo
     * @return
     */
    BillInfo addBillJpa(BillInfo billInfo);


//    删
    /**
     * 不完整，未进行信息校验
     * 通过 JdbcTemplate(SQL语句) 方式实现     根据海报ID 删除海报信息（不完整，未进行校验）
     * @param billId
     * @return
     */
    boolean deleteBillJdbc(Long billId);

    /**
     * 通过 JPA 方式实现     登录用户 根据海报ID 删除自己的海报信息
     * @param billId
     * @return
     */
    boolean deleteBillJpa(Long billId) throws Exception;

}
