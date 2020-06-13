package com.niit.poster.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.niit.poster.domain.BillType;
import com.niit.poster.service.BillInfoService;
import com.niit.poster.domain.BillInfo;
import com.niit.poster.repository.BillInfoRepository;
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
 * Service Implementation for managing {@link BillInfo}.
 */
@Service
@Transactional
public class BillInfoServiceImpl implements BillInfoService {

    private final Logger log = LoggerFactory.getLogger(BillInfoServiceImpl.class);

    private final BillInfoRepository billInfoRepository;
    private final JdbcTemplate jdbcTemplate;

    public BillInfoServiceImpl(BillInfoRepository billInfoRepository, JdbcTemplate jdbcTemplate) {
        this.billInfoRepository = billInfoRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Save a billInfo.
     *
     * @param billInfo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BillInfo save(BillInfo billInfo) {
        log.debug("Request to save BillInfo : {}", billInfo);
        return billInfoRepository.save(billInfo);
    }

    /**
     * Get all the billInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BillInfo> findAll(Pageable pageable) {
        log.debug("Request to get all BillInfos");
        return billInfoRepository.findAll(pageable);
    }


    /**
     * Get one billInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BillInfo> findOne(Long id) {
        log.debug("Request to get BillInfo : {}", id);
        return billInfoRepository.findById(id);
    }

    /**
     * Delete the billInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillInfo : {}", id);
        billInfoRepository.deleteById(id);
    }

    /**
     * 查询海报
     * 通过 Jdbc(SQL语句)进行查询
     * @return
     */
//    @Override
//    public List<BillInfo> getAllBillInfoJdbcOld() {
//        String sql = "SELECT " +
//            "  a.*, " +
//            "  b.bill_type_name, " +
//            "  b.bill_type_sort, " +
//            "  b.data_time " +
//            "FROM " +
//            "  bill_info a, " +
//            "  bill_type b " +
//            "WHERE a.bill_type_id = b.id ";
//
//        List<Map<String, Object>> sqlResult = this.jdbcTemplate.queryForList(sql);
//        if (sqlResult != null){
//            List<BillInfo> result = new ArrayList<BillInfo>();
//            //解析 List<Map<String, Object>> sqlResult  --->  List<BillInfo> result
//            for (Map<String, Object> sqlItem : sqlResult) {
//                BillInfo billInfo = new BillInfo();
//                billInfo.setId(TypeUtils.castToLong(sqlItem.get("id")));
//                billInfo.setBillAuthor(TypeUtils.castToString(sqlItem.get("bill_author")));
//                billInfo.setBillLayoutMode(TypeUtils.castToString(sqlItem.get("bill_layout_mode")));
//                billInfo.setBillPic(TypeUtils.castToString(sqlItem.get("bill_pic")));
//                //SQL查询出的字符串2020-06-11 14:40:56.0   --->   ZoneDateTime
//                //目标是获得ZonedDateTime型时间
//                //选择使用ofLocal方法获取该时间  三个参数   1.toLocalDateTime类型时间:Timestamp(时间戳类型)的toLocalDateTime方法可获得   2.指定时区ZoneId.systemDefault()获取系统时区
//                //TypeUtils.castToTimestamp可以把2020-06-11 14:40:56.0   --->   ZoneDateTime
//                billInfo.setBillTime(ZonedDateTime.ofLocal(TypeUtils.castToTimestamp(sqlItem.get("bill_time")).toLocalDateTime(), ZoneId.systemDefault(),null));
//                //海报分类
//                BillType billType = new BillType();
//                billType.setId(TypeUtils.castToLong(sqlItem.get("bill_type_id")));
//                billType.setBillTypeName(TypeUtils.castToString(sqlItem.get("bill_type_name")));
//                billType.setBillTypeSort(TypeUtils.castToInt(sqlItem.get("bill_type_sort")));
//                billType.setDataTime(ZonedDateTime.ofLocal(TypeUtils.castToTimestamp(sqlItem.get("data_time")).toLocalDateTime(), ZoneId.systemDefault(),null));
//                billInfo.setBillType(billType);
//
//                billInfo.setBillUserName(TypeUtils.castToString(sqlItem.get("bill_user_name")));
//                billInfo.setBillWord(TypeUtils.castToString(sqlItem.get("bill_word")));
//
//                result.add(billInfo);
//            }
//            return result;
//        }
//        return null;
//    }

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 Jdbc(SQL语句)进行查询
     * @param keywords
     * @param billTypeId
     * @return
     */
//    @Override
//    public List<BillInfo> getAllBillInfoJdbc(String keywords, Integer billTypeId) {
//        if (keywords==null)keywords="";
//        if (billTypeId==null)billTypeId=0;
//        String sql = "SELECT " +
//            "  a.*, " +
//            "  b.bill_type_name, " +
//            "  b.bill_type_sort, " +
//            "  b.data_time " +
//            "FROM " +
//            "  bill_info a, " +
//            "  bill_type b " +
//            "WHERE a.bill_type_id = b.id " +
//            "  AND bill_word LIKE '%" + keywords.replace("'","''") + "%' " ;
//        if (billTypeId > 0){
//            sql +=  "  AND bill_type_id =" + billTypeId;
//        }
//
//        List<Map<String, Object>> sqlResult = this.jdbcTemplate.queryForList(sql);
//        if (sqlResult != null){
//            List<BillInfo> result = new ArrayList<BillInfo>();
//            for (Map<String, Object> sqlItem : sqlResult) {
//                BillInfo billInfo = new BillInfo();
//                billInfo.setId(TypeUtils.castToLong(sqlItem.get("id")));
//                billInfo.setBillAuthor(TypeUtils.castToString(sqlItem.get("bill_author")));
//                billInfo.setBillLayoutMode(TypeUtils.castToString(sqlItem.get("bill_layout_mode")));
//                billInfo.setBillPic(TypeUtils.castToString(sqlItem.get("bill_pic")));
//
//                billInfo.setBillTime(ZonedDateTime.ofLocal(TypeUtils.castToTimestamp(sqlItem.get("bill_time")).toLocalDateTime(), ZoneId.systemDefault(),null));
//
//                BillType billType = new BillType();
//                billType.setId(TypeUtils.castToLong(sqlItem.get("bill_type_id")));
//                billType.setBillTypeName(TypeUtils.castToString(sqlItem.get("bill_type_name")));
//                billType.setBillTypeSort(TypeUtils.castToInt(sqlItem.get("bill_type_sort")));
//                billType.setDataTime(ZonedDateTime.ofLocal(TypeUtils.castToTimestamp(sqlItem.get("data_time")).toLocalDateTime(), ZoneId.systemDefault(),null));
//                billInfo.setBillType(billType);
//
//                billInfo.setBillUserName(TypeUtils.castToString(sqlItem.get("bill_user_name")));
//                billInfo.setBillWord(TypeUtils.castToString(sqlItem.get("bill_word")));
//
//                result.add(billInfo);
//            }
//            return result;
//        }
//        return null;
//    }

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
    @Override
    public List<BillInfo> getAllBillInfoJdbc(String keywords, Integer billTypeId,Integer pageIndex, Integer pageSize) {
        if (keywords==null)keywords="";
        if (billTypeId==null)billTypeId=0;
        if (pageIndex==null)pageIndex=0;
        if (pageSize==null)pageSize=5;
        String sql = "SELECT " +
            "  a.*, " +
            "  b.bill_type_name, " +
            "  b.bill_type_sort, " +
            "  b.data_time " +
            "FROM " +
            "  bill_info a, " +
            "  bill_type b " +
            "WHERE a.bill_type_id = b.id " +
            "  AND bill_word LIKE '%" + keywords.replace("'","''") + "%' ";
        if (billTypeId > 0){
            sql +=  "  AND bill_type_id =" + billTypeId;
        }
        sql += " LIMIT " + pageIndex*pageSize + "," + pageSize;

        List<Map<String, Object>> sqlResult = this.jdbcTemplate.queryForList(sql);
        if (sqlResult != null){
            List<BillInfo> result = new ArrayList<BillInfo>();
            for (Map<String, Object> sqlItem : sqlResult) {
                BillInfo billInfo = new BillInfo();
                billInfo.setId(TypeUtils.castToLong(sqlItem.get("id")));
                billInfo.setBillAuthor(TypeUtils.castToString(sqlItem.get("bill_author")));
                billInfo.setBillLayoutMode(TypeUtils.castToString(sqlItem.get("bill_layout_mode")));
                billInfo.setBillPic(TypeUtils.castToString(sqlItem.get("bill_pic")));

                billInfo.setBillTime(ZonedDateTime.ofLocal(TypeUtils.castToTimestamp(sqlItem.get("bill_time")).toLocalDateTime(), ZoneId.systemDefault(),null));

                BillType billType = new BillType();
                billType.setId(TypeUtils.castToLong(sqlItem.get("bill_type_id")));
                billType.setBillTypeName(TypeUtils.castToString(sqlItem.get("bill_type_name")));
                billType.setBillTypeSort(TypeUtils.castToInt(sqlItem.get("bill_type_sort")));
                billType.setDataTime(ZonedDateTime.ofLocal(TypeUtils.castToTimestamp(sqlItem.get("data_time")).toLocalDateTime(), ZoneId.systemDefault(),null));
                billInfo.setBillType(billType);

                billInfo.setBillUserName(TypeUtils.castToString(sqlItem.get("bill_user_name")));
                billInfo.setBillWord(TypeUtils.castToString(sqlItem.get("bill_word")));

                result.add(billInfo);
            }
            return result;
        }
        return null;
    }

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 Jdbc(SQL语句)进行查询
     * 分页
     * @param keywords
     * @param billTypeId
     * @return
     */
    @Override
    public Page<BillInfo> getAllBillInfoJdbcPaged(String keywords, Integer billTypeId,Integer pageIndex, Integer pageSize) {
        if (keywords==null)keywords="";
        if (billTypeId==null)billTypeId=0;
        if (pageIndex==null)pageIndex=0;
        if (pageSize==null)pageSize=5;
        //分页返回
        //1、分页的列表   List<BillInfo>
        List<BillInfo> billInfoList = this.getAllBillInfoJdbc(keywords, billTypeId, pageIndex, pageSize);
        //2、数据总数   Long
        String totalSql = "SELECT " +
            "  COUNT(1) totalCount " +
            "FROM " +
            "  bill_info a, " +
            "  bill_type b " +
            "WHERE a.bill_type_id = b.id " +
            "  AND bill_word LIKE '%" + keywords.replace("'","''") + "%' ";
        if (billTypeId > 0){
            totalSql +=  "  AND bill_type_id =" + billTypeId;
        }
        Map<String,Object> totalItem = jdbcTemplate.queryForMap(totalSql);
        Long tatalCount =0l;
        if (totalItem !=null){
            tatalCount = TypeUtils.castToLong(totalItem.get("totalCount"));
        }
        //3、当前是第几页   Pageable
        return new PageImpl(billInfoList, PageRequest.of(pageIndex,pageSize),tatalCount);
    }

    /**
     * 根据关键字(海报文字)查询
     * 通过 JPA
     * 分页
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
//    @Override
//    public Page<BillInfo> getAllBillInfoJpa(String keywords,Integer pageIndex, Integer pageSize) {
//        if (keywords==null)keywords="";
//        if (pageIndex==null)pageIndex=0;
//        if (pageSize==null)pageSize=5;
//
//        keywords="%" + keywords.replace("'","''") + "%";
//        Page<BillInfo> result = billInfoRepository.findByBillWordLike(keywords,PageRequest.of(pageIndex,pageSize));
//        return result;
//    }

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 JPA
     * 分页
     * @param keywords
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public Page<BillInfo> getAllBillInfoJpa(String keywords,Long billTypeId, Integer pageIndex, Integer pageSize) {
        if (keywords==null)keywords="";
        if (billTypeId==null)billTypeId=0l;
        if (pageIndex==null)pageIndex=0;
        if (pageSize==null)pageSize=5;

        keywords="%" + keywords.replace("'","''") + "%";
        Page<BillInfo> result = null;
        if (billTypeId>0){
            result = billInfoRepository.findByBillWordLikeAndBillTypeId(keywords,billTypeId,PageRequest.of(pageIndex,pageSize));
        }else {
            result = billInfoRepository.findByBillWordLike(keywords,PageRequest.of(pageIndex,pageSize));
        }

        return result;
    }


}
