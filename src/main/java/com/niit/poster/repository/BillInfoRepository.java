package com.niit.poster.repository;

import com.niit.poster.domain.BillInfo;

import com.niit.poster.domain.BillType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillInfoRepository extends JpaRepository<BillInfo, Long> {


//    查
    /**
     * 通过 JPA 方式实现     分页查询 根据 海报文字(bill_word) 模糊查询 全部海报信息
     * @param keywords  海报文字
     * @param page 分页参数
     * @return
     */
    Page<BillInfo> findByBillWordLike(String keywords, Pageable page);

    /**
     * 通过 JPA 方法名定义方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords
     * @param billTypeId
     * @param page
     * @return
     */
    Page<BillInfo> findByBillWordLikeAndBillTypeId(String keywords, Long billTypeId, Pageable page);

    /**
     * 不延伸
     * 单个对象查询 Map(必须确定返回一个对象)
     * @param keywords
     * @return
     */
    /*BillInfo findByBillWord(String keywords);*/

    /**
     * 通过 JPA @Query注解 方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字
     * @param billTypeId  海报类型ID
     * @param page  分页参数
     * @return
     */
     //直接使用SQL查询 nativeQuery = true 表示不用翻译，直接执行
   /* @Query(value = "select * from bill_info where bill_word like ?1 and (0 = ?2 or bill_type_id =?2)",nativeQuery = true)*/

   //对象查询                                ?1表示第一个参数               若 or 前等式成立则不执行 or 后等式
    @Query("select a from BillInfo a where a.billWord like ?1 and (0 = ?2 or a.billType.id = ?2)")
    Page<BillInfo> getBills(String keywords,Integer billTypeId,Pageable page);

}
