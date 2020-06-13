package com.niit.poster.web.rest;

import com.niit.poster.domain.BillType;
import com.niit.poster.service.BillTypeService;
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
 * REST controller for managing {@link com.niit.poster.domain.BillType}.
 */
@RestController
@RequestMapping("/api")
public class BillTypeResource {

    private final Logger log = LoggerFactory.getLogger(BillTypeResource.class);

    private static final String ENTITY_NAME = "billType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BillTypeService billTypeService;

    public BillTypeResource(BillTypeService billTypeService) {
        this.billTypeService = billTypeService;
    }

    /**
     * {@code POST  /bill-types} : Create a new billType.
     *
     * @param billType the billType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new billType, or with status {@code 400 (Bad Request)} if the billType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bill-types")
    public ResponseEntity<BillType> createBillType(@RequestBody BillType billType) throws URISyntaxException {
        log.debug("REST request to save BillType : {}", billType);
        if (billType.getId() != null) {
            throw new BadRequestAlertException("A new billType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BillType result = billTypeService.save(billType);
        return ResponseEntity.created(new URI("/api/bill-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bill-types} : Updates an existing billType.
     *
     * @param billType the billType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated billType,
     * or with status {@code 400 (Bad Request)} if the billType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the billType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bill-types")
    public ResponseEntity<BillType> updateBillType(@RequestBody BillType billType) throws URISyntaxException {
        log.debug("REST request to update BillType : {}", billType);
        if (billType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BillType result = billTypeService.save(billType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, billType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bill-types} : get all the billTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of billTypes in body.
     */
    @GetMapping("/bill-types")
    public ResponseEntity<List<BillType>> getAllBillTypes(Pageable pageable) {
        log.debug("REST request to get a page of BillTypes");
        Page<BillType> page = billTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bill-types/:id} : get the "id" billType.
     *
     * @param id the id of the billType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the billType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bill-types/{id}")
    public ResponseEntity<BillType> getBillType(@PathVariable Long id) {
        log.debug("REST request to get BillType : {}", id);
        Optional<BillType> billType = billTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(billType);
    }

    /**
     * {@code DELETE  /bill-types/:id} : delete the "id" billType.
     *
     * @param id the id of the billType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bill-types/{id}")
    public ResponseEntity<Void> deleteBillType(@PathVariable Long id) {
        log.debug("REST request to delete BillType : {}", id);
        billTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * 查询全部海报分类
     * 通过 Jdbc(SQL语句)进行查询
     * @return
     */
    @GetMapping("/bill-type/all/jdbc")
    public ResponseEntity getAllBillTypeJdbc(){
        try {
            List<BillType> result = billTypeService.getAllBillTypeJdbc();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillTypeJdbc",e.getLocalizedMessage());
        }
    }

    /**
     * 根据 分类排序 查询 所有海报分类
     * 通过 JPA
     * 分页
     * @param billTypeSort
     * @param pageIndex
     * @param pageSize
     * @return
     */
//    @GetMapping("/bill-type/all/jpa")
//    public ResponseEntity getAllBillTypeJpa(Integer billTypeSort,Integer pageIndex, Integer pageSize){
//        try {
//            Page<BillType> result = billTypeService.getAllBillTypeJpa(billTypeSort,pageIndex,pageSize);
//            return ResponseEntity.ok(result);
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new BadRequestAlertException(e.getMessage(),"getAllBillTypeJpa",e.getLocalizedMessage());
//        }
//    }

    /**
     * 根据 海报分类排序 倒叙 查询所有
     * 通过 JPA
     * 不分页
     * @return
     */
    @GetMapping("/bill-type/all/jpa")
    public ResponseEntity getAllBillTypeJpa(){
        try {
            List<BillType> result = this.billTypeService.getAllBillTypeJpa();
            return ResponseEntity.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(),"getAllBillTypeJpa",e.getLocalizedMessage());
        }
    }
}