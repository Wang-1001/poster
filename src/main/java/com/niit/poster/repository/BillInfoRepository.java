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

    /**
     * 根据关键字(海报文字)查询
     * 通过 JPA
     * 分页
     * @param keywords
     * @param page
     * @return
     */
    Page<BillInfo> findByBillWordLike(String keywords, Pageable page);

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 JPA
     * 分页
     * @param keywords
     * @param billTypeId
     * @param page
     * @return
     */
    Page<BillInfo> findByBillWordLikeAndBillTypeId(String keywords, Long billTypeId, Pageable page);


    /**
     * 单个对象查询
     * 必须确定返回一个对象
     * 不延伸
     * @param keywords
     * @return
     */
//    BillInfo findByBillWord(String keywords);

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 JPA + @Query注解
     * 分页
     * @param keywords
     * @param billTypeId
     * @param page
     * @return
     */
    //直接使用sql查询 nativeQuery = true 表示不用翻译，直接执行
//    @Query(value = "select * from bill_info where bill_word like ?1 and (0 = ?2 or bill_type_id =?2)",nativeQuery = true)
    @Query("select a from BillInfo a where a.billWord like ?1 and (0 = ?2 or a.billType.id = ?2)")
    Page<BillInfo> getBills(String keywords,Integer billTypeId,Pageable page);
}
