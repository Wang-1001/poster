package com.niit.poster.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.niit.poster.domain.BillType;
import com.niit.poster.security.SecurityUtils;
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


//    查
//    /**
//     * 通过 JdbcTemplate(SQL语句) 方式实现     查询海报信息
//     * @return
//     */
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

//    /**
//     * 通过 JdbcTemplate(SQL语句) 方式实现     根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
//     * @param keywords
//     * @param billTypeId
//     * @return
//     */
//    @Override
//    public List<BillInfo> getAllBillInfoJdbcOld2(String keywords, Integer billTypeId) {
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
     * 通过 JdbcTemplate(SQL语句) 方式实现     根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param userName  登录用户
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<BillInfo> getAllBillInfoJdbc(String keywords, Integer billTypeId, String userName, Integer pageIndex, Integer pageSize) {
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
        if (userName != null){
            sql += " AND bill_user_name = '" + userName + "'";
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
     * 通过 JdbcTemplate(SQL语句) 方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param userName  登录用户
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @Override
    public Page<BillInfo> getAllBillInfoJdbcPaged(String keywords, Integer billTypeId, String userName, Integer pageIndex, Integer pageSize) {
        if (keywords==null)keywords="";
        if (billTypeId==null)billTypeId=0;
        if (pageIndex==null)pageIndex=0;
        if (pageSize==null)pageSize=5;

        //分页返回
        //1、分页的列表   List<BillInfo>
        List<BillInfo> billInfoList = this.getAllBillInfoJdbc(keywords, billTypeId, userName, pageIndex, pageSize);
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
        if (userName != null){
            totalSql += " AND bill_user_name = '" + userName + "'";
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
     * 通过 JPA 方法名定义方式实现     分页查询 根据 海报文字(bill_word) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @Override
    public Page<BillInfo> getAllBillByBillWordLikeJpa(String keywords,Integer pageIndex, Integer pageSize) {
        /*if (keywords==null)keywords="";
        if (pageIndex==null)pageIndex=0;
        if (pageSize==null)pageSize=5;*/
        keywords="%" + keywords.replace("'","''") + "%";

        Page<BillInfo> result = billInfoRepository.findByBillWordLike(keywords,PageRequest.of(pageIndex,pageSize));
        return result;
    }

    /**
     * 通过 JPA 方法名定义方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @Override
    public Page<BillInfo> getAllBillJpa(String keywords,Long billTypeId, Integer pageIndex, Integer pageSize) {
        /*if (keywords==null)keywords="";
        if (billTypeId==null)billTypeId=0l;
        if (pageIndex==null)pageIndex=0;
        if (pageSize==null)pageSize=5;*/
        keywords="%" + keywords.replace("'","''") + "%";

        Page<BillInfo> result = null;
        if (billTypeId>0){
            result = billInfoRepository.findByBillWordLikeAndBillTypeId(keywords,billTypeId,PageRequest.of(pageIndex,pageSize));
        }else {
            result = billInfoRepository.findByBillWordLike(keywords,PageRequest.of(pageIndex,pageSize));
        }
        return result;
    }

    /**
     * 通过 JPA @Query注解 方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @Override
    public Page<BillInfo> getAllBillJpaQuery(String keywords, Long billTypeId, Integer pageIndex, Integer pageSize) {
       /* if (keywords==null)keywords="";
        if (billTypeId==null)billTypeId=0l;
        if (pageIndex==null)pageIndex=0;
        if (pageSize==null)pageSize=5;*/
        keywords="%" + keywords.replace("'","''") + "%";

        Page<BillInfo> result = billInfoRepository.getBills(keywords,billTypeId.intValue(),PageRequest.of(pageIndex,pageSize));
        return result;
    }

    /**
     * 通过 JPA @Query注解 方式实现     登录用户 分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 查询自己的海报信息
     * @param keywords  查询关键字(海报文字)
     * @param billTypeId  海报类型ID
     * @param pageIndex  页码
     * @param pageSize  页长
     * @return
     */
    @Override
    public Page<BillInfo> getMyBills(String keywords,Long billTypeId, Integer pageIndex, Integer pageSize) {
        keywords="%" + keywords.replace("'","''") + "%";

        Optional<String> loginOptional =  SecurityUtils.getCurrentUserLogin();
        if (loginOptional.isPresent()){
            String login = loginOptional.get();

            Page<BillInfo> result = billInfoRepository.getMyBills(keywords, billTypeId.intValue(), login, PageRequest.of(pageIndex,pageSize));
            return result;
        }else {
            return null;
        }

    }

//    增
    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     新增海报
     * @param billInfo
     * @return
     */
    @Override
    public BillInfo creatBillJdbc(BillInfo billInfo) {
        String sql="INSERT INTO bill_info (bill_user_name, bill_pic, bill_word, bill_author, bill_time, bill_layout_mode, bill_type_id) " +
            "VALUES ('"+billInfo.getBillUserName()
            +"', '"+billInfo.getBillPic()
            +"', '"+billInfo.getBillWord()
            +"', '"+billInfo.getBillAuthor()+"', now(), '"
            +billInfo.getBillLayoutMode()+"',"+(billInfo.getBillType()==null?"null":billInfo.getBillType().getId())+")";
        int result=this.jdbcTemplate.update(sql);
        if(result>0){
            return billInfo;
        }else{
            return null;
        }

    }

    /**
     * 通过 JPA 方式实现     新增海报(调用已有的Repository)
     * @param billInfo
     * @return
     */
    @Override
    public BillInfo addBillJpa(BillInfo billInfo) {
        //调用Repository中已有的添加方法
        billInfo.setBillUserName("admin");
        billInfo.setBillTime(ZonedDateTime.now());
        BillInfo result = billInfoRepository.save(billInfo);
        return result;
    }


//    删
    /**
     * 不完整，未进行信息校验
     * 通过 JdbcTemplate(SQL语句) 方式实现     根据海报ID 删除海报信息（不完整，未进行校验）
     * @param billId
     * @return
     */
    @Override
    public boolean deleteBillJdbc(Long billId) {
        String sql="delete from bill_info where id = " + billId;
        int result=jdbcTemplate.update(sql);
        if(result>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 通过 JPA 方式实现     登录用户 根据海报ID 删除自己的海报信息
     * @param billId
     * @return
     */
    @Override
    public boolean deleteBillJpa(Long billId) throws Exception {
        //校验Id是的存在
        Optional<BillInfo> billInfoOptional = billInfoRepository.findById(billId);
        if (billInfoOptional.isPresent()){
            BillInfo billInfo = billInfoOptional.get();

            //校验海报是否是当前登录用户的
            String billUserName = billInfo.getBillUserName();
            Optional<String> loginOptional =  SecurityUtils.getCurrentUserLogin();
            if (loginOptional.isPresent()){
                String login = loginOptional.get();
                if (login.equals(billUserName)){
                    try{
                        billInfoRepository.deleteById(billId);
                        return true;
                    }catch (Exception e){
                        throw e;
                    }

                }else {
                    throw new Exception("无权限删除");
                }
            }else {
                throw new Exception("无权限删除");
            }
        }else {
            throw new Exception("未找到海报编码为：" + billId + "的数据");
        }
    }



}
