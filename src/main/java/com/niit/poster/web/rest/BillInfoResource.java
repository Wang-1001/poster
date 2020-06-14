package com.niit.poster.web.rest;

import com.niit.poster.domain.BillInfo;
import com.niit.poster.service.BillInfoService;
import com.niit.poster.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

    /**
     * 查询海报
     * 通过 Jdbc(SQL语句)进行查询
     * @return
     */
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

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 Jdbc(SQL语句)进行查询
     * @param keywords
     * @param billTypeId
     * @return
     */
//    @GetMapping("/bill-info/all/jdbc")
//    public ResponseEntity getAllBillInfoJdbc(String keywords, Integer billTypeId ){
//        try{
//            List<BillInfo> result = billInfoService.getAllBillInfoJdbc(keywords,billTypeId);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new BadRequestAlertException(e.getMessage(),"getAllBillInfoJdbc",e.getLocalizedMessage());
//        }
//
//    }

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 Jdbc(SQL语句)进行查询
     * 分页
     * @param keywords
     * @param billTypeId
     * @return
     */
    @GetMapping("/bill-info/all/jdbc")
    public ResponseEntity getAllBillInfoJdbc(String keywords, Integer billTypeId,Integer pageIndex, Integer pageSize){
        try{
            Page<BillInfo> result = billInfoService.getAllBillInfoJdbcPaged(keywords,billTypeId,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillInfoJdbc",e.getLocalizedMessage());
        }

    }

    /**
     * 根据关键字(海报文字)查询
     * 通过 JPA
     * 分页
     * @param keywords
     * @param billTypeId
     * @param pageIndex
     * @param pageSize
     * @return
     */
//    @GetMapping("/bill-info/all/jpa")
//    public ResponseEntity getAllBillInfoJpa(String keywords,Long billTypeId,Integer pageIndex, Integer pageSize){
//        try{
//            Page<BillInfo> result = billInfoService.getAllBillInfoJpa(keywords,pageIndex,pageSize);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new BadRequestAlertException(e.getMessage(),"getAllBillInfoJpa",e.getLocalizedMessage());
//        }
//
//    }

    /**
     * 根据 海报类型ID 和 海报文字 模糊查询 海报
     * 通过 JPA
     * 分页
     * @param keywords
     * @param billTypeId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/bill-info/all/jpa")
    public ResponseEntity getAllBillInfoJpa(String keywords, Long billTypeId,Integer pageIndex, Integer pageSize){
        try{
            Page<BillInfo> result = billInfoService.getAllBillInfoJpa(keywords,billTypeId,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillInfoJpa",e.getLocalizedMessage());
        }

    }

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
    @GetMapping("/bill-info/all/jpa-query/{billTypeId}")
    public ResponseEntity getAllBillInfoJpaQuery(
        @RequestParam(required = false,defaultValue = "") String keywords,
        @PathVariable Long billTypeId,
        @RequestParam(required = false,defaultValue = "0") Integer pageIndex,
        @RequestParam(required = false,defaultValue = "5") Integer pageSize){
        try{
            Page<BillInfo> result = billInfoService.getAllBillInfoJpaQuery(keywords,billTypeId,pageIndex,pageSize);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillInfoJpaQuery",e.getLocalizedMessage());
        }

    }

    /**
     * 添加海报
     * @param billInfo
     * @return
     */
    @PostMapping("/bill-infos/add")
    public ResponseEntity addBill(@RequestBody BillInfo billInfo){
        try{
            BillInfo result = billInfoService.addBill(billInfo);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"addBill",e.getLocalizedMessage());
        }

    }


}
