package com.niit.poster.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.niit.poster.service.BillTypeService;
import com.niit.poster.domain.BillType;
import com.niit.poster.repository.BillTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BillType}.
 */
@Service
@Transactional
public class BillTypeServiceImpl implements BillTypeService {

    private final Logger log = LoggerFactory.getLogger(BillTypeServiceImpl.class);

    private final BillTypeRepository billTypeRepository;
    private final JdbcTemplate jdbcTemplate;

    public BillTypeServiceImpl(BillTypeRepository billTypeRepository, JdbcTemplate jdbcTemplate) {
        this.billTypeRepository = billTypeRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a billType.
     *
     * @param billType the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BillType save(BillType billType) {
        log.debug("Request to save BillType : {}", billType);
        return billTypeRepository.save(billType);
    }

    /**
     * Get all the billTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BillType> findAll(Pageable pageable) {
        log.debug("Request to get all BillTypes");
        return billTypeRepository.findAll(pageable);
    }


    /**
     * Get one billType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BillType> findOne(Long id) {
        log.debug("Request to get BillType : {}", id);
        return billTypeRepository.findById(id);
    }

    /**
     * Delete the billType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillType : {}", id);
        billTypeRepository.deleteById(id);
    }


//    查
    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     查询 全部海报分类
     * @return
     */
    @Override
    public List<BillType> getAllBillTypeJdbc() {
        String sql = "select * from bill_type order by bill_type_sort";
        List<Map<String,Object>> sqlResult = this.jdbcTemplate.queryForList(sql);
        if (sqlResult != null){
            List<BillType> result = new ArrayList<BillType>();
            //解析 List<Map<String,Object>> sqlResult  --->  List<BillType> result
            for (Map<String,Object> sqlItem : sqlResult){
                BillType billType = new BillType();
                billType.setId(TypeUtils.castToLong(sqlItem.get("id")));
                billType.setBillTypeName(TypeUtils.castToString(sqlItem.get("bill_type_name")));
                billType.setBillTypeSort(TypeUtils.castToInt(sqlItem.get("bill_type_sort")));
                billType.setDataTime(ZonedDateTime.ofLocal(TypeUtils.castToTimestamp(sqlItem.get("data_time")).toLocalDateTime(), ZoneId.systemDefault(),null));
                result.add(billType);
            }
            return result;
        }
        return null;
    }

//    /**
//     * 功能可实现，无太大意义
//     * 通过 JPA 方式实现     分页查询 指定分类排序(bill_type_sort) 的海报分类，若不指定分类排序则 查全部海报分类
//     * @param billTypeSort  分类排序参数，若分类参数为 null 则默认查询全部
//     * @param pageIndex  页码，默认为0
//     * @param pageSize  页长，默认为5
//     * @return
//     */
//    @Override
//    public Page<BillType> getAllBillTypeJpaPaged(Integer billTypeSort, Integer pageIndex, Integer pageSize) {
//        /*if (billTypeSort==null)billTypeSort=0;
//        if (pageIndex==null)pageIndex=0;
//        if (pageSize==null)pageSize=5;*/
//
//        Page<BillType> result = null;
//        if (billTypeSort>0){
//            result = billTypeRepository.findAllByBillTypeSort(billTypeSort,PageRequest.of(pageIndex,pageSize));
//        }else {
//            result = billTypeRepository.findAll(PageRequest.of(pageIndex,pageSize));
//        }
//        return result;
//    }

    /**
     * 通过 JPA 方式实现     查询 分类排序为倒叙 的全部海报分类
     * @return
     */
    @Override
    public List<BillType> getAllBillTypeJpa() {
        List<BillType> result = billTypeRepository.findAllByOrderByBillTypeSortDesc();
        return result;
    }

}
