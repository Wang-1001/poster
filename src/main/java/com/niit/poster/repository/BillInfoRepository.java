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
}
