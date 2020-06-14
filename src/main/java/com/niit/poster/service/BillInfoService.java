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

    /**
     *  查询海报
     *  通过 Jdbc(SQL语句)进行查询
     * @return
     */
//    List<BillInfo> getAllBillInfoJdbcOld();

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 Jdbc(SQL语句)进行查询
     * @param keywords
     * @param billTypeId
     * @return
     */
//    List<BillInfo> getAllBillInfoJdbc(String keywords, Integer billTypeId);

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 Jdbc(SQL语句)进行查询
     * 对以上方法进行改造， 添加分页参数
     * @param keywords
     * @param billTypeId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<BillInfo> getAllBillInfoJdbc(String keywords, Integer billTypeId, String userName, Integer pageIndex, Integer pageSize);

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 Jdbc(SQL语句)进行查询
     * 分页
     * @param keywords
     * @param billTypeId
     * @return
     */
    Page<BillInfo> getAllBillInfoJdbcPaged(String keywords, Integer billTypeId, String userName, Integer pageIndex, Integer pageSize);

    /**
     * 根据关键字(海报文字)查询
     * 通过 JPA
     * 分页
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
//    Page<BillInfo> getAllBillInfoJpa(String keywords,Integer pageIndex, Integer pageSize);

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 JPA
     * 分页
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<BillInfo> getAllBillInfoJpa(String keywords,Long billTypeId,Integer pageIndex, Integer pageSize);

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 JPA + @Query注解
     * 分页
     * @param keywords
     * @param billTypeId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<BillInfo> getAllBillInfoJpaQuery(String keywords,Long billTypeId,Integer pageIndex, Integer pageSize);

    /**
     * 海报新增
     * 通过 JdbcTemplate
     * @param billInfo
     * @return
     */
    BillInfo creatBillJdbc(BillInfo billInfo);

    /**
     * 添加海报
     * @param billInfo
     * @return
     */
    BillInfo addBillJpa(BillInfo billInfo);

    /**
     * 根据 海报ID 删除海报
     * 通过 JdbcTemplate
     * 不完整（未进行ID等校验）
     * @param billId
     * @return
     */
    boolean deleteBillJdbc(Long billId);

}
