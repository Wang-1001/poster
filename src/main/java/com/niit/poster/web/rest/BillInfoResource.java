package com.niit.poster.web.rest;

import com.niit.poster.domain.BillInfo;
import com.niit.poster.security.SecurityUtils;
import com.niit.poster.service.BillInfoService;
import com.niit.poster.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.niit.poster.domain.BillInfo}.
 */
@RestController
@RequestMapping("/api")
public class BillInfoResource {

    private final Logger log = LoggerFactory.getLogger(BillInfoResource.class);

    private static final String ENTITY_NAME = "billInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillInfoService billInfoService;

    public BillInfoResource(BillInfoService billInfoService) {
        this.billInfoService = billInfoService;
    }

    /**
     * {@code POST  /bill-infos} : Create a new billInfo.
     *
     * @param billInfo the billInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billInfo, or with status {@code 400 (Bad Request)} if the billInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bill-infos")
    public ResponseEntity<BillInfo> createBillInfo(@RequestBody BillInfo billInfo) throws URISyntaxException {
        log.debug("REST request to save BillInfo : {}", billInfo);
        if (billInfo.getId() != null) {
            throw new BadRequestAlertException("A new billInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillInfo result = billInfoService.save(billInfo);
        return ResponseEntity.created(new URI("/api/bill-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bill-infos} : Updates an existing billInfo.
     *
     * @param billInfo the billInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billInfo,
     * or with status {@code 400 (Bad Request)} if the billInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bill-infos")
    public ResponseEntity<BillInfo> updateBillInfo(@RequestBody BillInfo billInfo) throws URISyntaxException {
        log.debug("REST request to update BillInfo : {}", billInfo);
        if (billInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillInfo result = billInfoService.save(billInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bill-infos} : get all the billInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billInfos in body.
     */
    @GetMapping("/bill-infos")
    public ResponseEntity<List<BillInfo>> getAllBillInfos(Pageable pageable) {
        log.debug("REST request to get a page of BillInfos");
        Page<BillInfo> page = billInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bill-infos/:id} : get the "id" billInfo.
     *
     * @param id the id of the billInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bill-infos/{id}")
    public ResponseEntity<BillInfo> getBillInfo(@PathVariable Long id) {
        log.debug("REST request to get BillInfo : {}", id);
        Optional<BillInfo> billInfo = billInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billInfo);
    }

    /**
     * {@code DELETE  /bill-infos/:id} : delete the "id" billInfo.
     *
     * @param id the id of the billInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bill-infos/{id}")
    public ResponseEntity<Void> deleteBillInfo(@PathVariable Long id) {
        log.debug("REST request to delete BillInfo : {}", id);
        billInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


//    查
//    /**
//     * 通过 JdbcTemplate(SQL语句) 方式实现     查询海报信息
//     * @return
//     */
//    @ApiOperation(value = "使用 Jdbc 查询所有海报信息(没有关键字，直接查询)")
//    @GetMapping("/bill-info/all/jdbc-old")
//    public ResponseEntity getAllBillInfoJdbcOld(){
//        try{
//            List<BillInfo> result = billInfoService.getAllBillInfoJdbcOld();
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new BadRequestAlertException(e.getMessage(),"getAllBillInfoJdbcOld",e.getLocalizedMessage());
//        }
//
//    }

//    /**
//     * 通过 JdbcTemplate(SQL语句) 方式实现     根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
//     * @param keywords
//     * @param billTypeId
//     * @return
//     */
//    @ApiOperation(value = "使用 Jdbc 查询所有海报信息(不分页)")
//    @GetMapping("/bill-info/all/jdbc-old2")
//    public ResponseEntity getAllBillInfoJdbcOld2(String keywords, Integer billTypeId ){
//        try{
//            List<BillInfo> result = billInfoService.getAllBillInfoJdbcOld2(keywords,billTypeId);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new BadRequestAlertException(e.getMessage(),"getAllBillInfoJdbcOld2",e.getLocalizedMessage());
//        }
//
//    }

    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 所有海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @ApiOperation(value = "使用 Jdbc 查询所有海报信息")
    @GetMapping("/bill-info/all/jdbc-page")
    @ApiImplicitParams({
        @ApiImplicitParam(name="keywords",value = "海报文字"),
        @ApiImplicitParam(name="billTypeId",value = "海报类型ID，全部为0"),
        @ApiImplicitParam(name="pageIndex",value = "分页页码，起始为0"),
        @ApiImplicitParam(name="pageSize",value = "分页页长，默认为5")
    })
    public ResponseEntity getAllBillJdbc(String keywords, Integer billTypeId, Integer pageIndex, Integer pageSize){
        try{
            Page<BillInfo> result = billInfoService.getAllBillInfoJdbcPaged(keywords,billTypeId,null,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillJdbc",e.getLocalizedMessage());
        }

    }

    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     登录用户 分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 自己的海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @ApiOperation(value = "使用 Jdbc 根据登录用户查询自己的的海报信息")
    @GetMapping("/bill-info/mine/jdbc-page")
    @ApiImplicitParams({
        @ApiImplicitParam(name="keywords",value = "海报文字"),
        @ApiImplicitParam(name="billTypeId",value = "海报类型ID，全部为0"),
        @ApiImplicitParam(name="pageIndex",value = "分页页码，起始为0"),
        @ApiImplicitParam(name="pageSize",value = "分页页长，默认为5")
    })
    public ResponseEntity getMyBillJdbc(
        String keywords,
        Integer billTypeId,
        Integer pageIndex,
        Integer pageSize){
        try{
            String loginname = SecurityUtils.getCurrentUserLogin().get();
            Page<BillInfo> result = billInfoService.getAllBillInfoJdbcPaged(keywords,billTypeId,loginname,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getMyBillJdbc",e.getLocalizedMessage());
        }

    }

    /**
     * 通过 JPA 方法名定义方式实现     分页查询 根据 海报文字(bill_word) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @ApiOperation(value = "使用 JPA 方法名定义 查询(海报文字)所有海报信息")
    @GetMapping("/bill-info/all/billWord/jpa-page")
    @ApiImplicitParams({
        @ApiImplicitParam(name="keywords",value = "海报文字"),
        @ApiImplicitParam(name="pageIndex",value = "分页页码，起始为0"),
        @ApiImplicitParam(name="pageSize",value = "分页页长，默认为5")
    })
    public ResponseEntity getAllBillByBillWordLikeJpa(
        @RequestParam(required = false,defaultValue = "") String keywords,
        @RequestParam(required = false,defaultValue = "0") Integer pageIndex,
        @RequestParam(required = false,defaultValue = "5") Integer pageSize){
        try{
            Page<BillInfo> result = billInfoService.getAllBillByBillWordLikeJpa(keywords,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillByBillWordLikeJpa",e.getLocalizedMessage());
        }

    }

    /**
     * 通过 JPA 方法名定义方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @ApiOperation(value = "使用 JPA 方法名定义 查询所有海报信息")
    @GetMapping("/bill-info/all/jpa-page")
    @ApiImplicitParams({
        @ApiImplicitParam(name="keywords",value = "海报文字"),
        @ApiImplicitParam(name = "billTypeId",value = "海报类型ID，全部为0"),
        @ApiImplicitParam(name="pageIndex",value = "分页页码，起始为0"),
        @ApiImplicitParam(name="pageSize",value = "分页页长，默认为5")
    })
    public ResponseEntity getAllBillJpa(
        @RequestParam(required = false,defaultValue = "") String keywords,
        Long billTypeId,
        @RequestParam(required = false,defaultValue = "0") Integer pageIndex,
        @RequestParam(required = false,defaultValue = "5") Integer pageSize){
        try{
            Page<BillInfo> result = billInfoService.getAllBillJpa(keywords,billTypeId,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillJpa",e.getLocalizedMessage());
        }

    }

    /**
     * 通过 JPA @Query注解 方式实现     分页查询 根据 海报文字(bill_word) 和 海报类型ID(bill_type_id) 模糊查询 全部海报信息
     * @param keywords  海报文字，默认为""
     * @param billTypeId  海报类型ID，全部为0
     * @param pageIndex  页码，默认为0
     * @param pageSize  页长，默认为5
     * @return
     */
    @ApiOperation(value = "使用 JPA @Query 查询所有海报信息")
    @GetMapping("/bill-info/all/jpa-query-page/{billTypeId}")
    @ApiImplicitParams({
        @ApiImplicitParam(name="keywords",value = "海报文字"),
        @ApiImplicitParam(name = "billTypeId",value = "海报类型ID，全部为0"),
        @ApiImplicitParam(name="pageIndex",value = "分页页码，起始为0"),
        @ApiImplicitParam(name="pageSize",value = "分页页长，默认为5")
    })
    public ResponseEntity getAllBillJpaQuery(
        @RequestParam(required = false,defaultValue = "") String keywords,
        @PathVariable Long billTypeId,
        @RequestParam(required = false,defaultValue = "0") Integer pageIndex,
        @RequestParam(required = false,defaultValue = "5") Integer pageSize){
        try{
            Page<BillInfo> result = billInfoService.getAllBillJpaQuery(keywords,billTypeId,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillJpaQuery",e.getLocalizedMessage());
        }

    }


//    增
    /**
     * 通过 JdbcTemplate(SQL语句) 方式实现     新增海报
     * @param billInfo
     * @return
     */
    @ApiOperation(value = "使用 Jdbc 创建新海报")
    @PostMapping("/bill-infos/creat/jdbc")
    public ResponseEntity creatBillJdbc(@RequestBody BillInfo billInfo){
        try{
            String login = SecurityUtils.getCurrentUserLogin().get();
            billInfo.setBillUserName(login);
            billInfo = billInfoService.creatBillJdbc(billInfo);
            if (billInfo != null){
                return ResponseEntity.ok(billInfo);
            }else {
                return ResponseEntity.badRequest().body("保存失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"creatBillJdbc",e.getLocalizedMessage());
        }
    }

    /**
     * 通过 JPA 方式实现     新增海报(调用已有的Repository)
     * @param billInfo
     * @return
     */
    @ApiOperation(value = "使用 JPA 创建新海报")
    @PostMapping("/bill-infos/add/jpa")
    public ResponseEntity addBillJpa(@RequestBody BillInfo billInfo){
        try{
            BillInfo result = billInfoService.addBillJpa(billInfo);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"addBillJpa",e.getLocalizedMessage());
        }

    }


//    删
    /**
     * 不完整，未进行信息校验
     * 通过 JdbcTemplate(SQL语句) 方式实现     根据海报ID 删除海报信息（不完整，未进行校验）
     * @param billId
     * @return
     */
    @ApiOperation(value = "使用 Jdbc 删除指定海报信息")
    @DeleteMapping("/bill-infos/delete/jdbc/{billId}")
    public ResponseEntity deleteBillJdbc(@PathVariable Long billId){
        try{
            boolean result=billInfoService.deleteBillJdbc(billId);
            if(result){
                return ResponseEntity.ok("删除成功");
            }else{
                return ResponseEntity.noContent().build();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"deleteBillJdbc",e.getLocalizedMessage());
        }
    }


}
