package com.niit.poster.service;

import com.niit.poster.domain.BillType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BillType}.
 */
public interface BillTypeService {

    /**
     * Save a billType.
     *
     * @param billType the entity to save.
     * @return the persisted entity.
     */
    BillType save(BillType billType);

    /**
     * Get all the billTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BillType> findAll(Pageable pageable);


    /**
     * Get the "id" billType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillType> findOne(Long id);

    /**
     * Delete the "id" billType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * 查询全部海报分类
     * 通过 Jdbc(SQL语句)进行查询
     * @return
     */
    List<BillType> getAllBillTypeJdbc();

}
