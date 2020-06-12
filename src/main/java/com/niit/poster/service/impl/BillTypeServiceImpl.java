package com.niit.poster.service.impl;

import com.niit.poster.service.BillTypeService;
import com.niit.poster.domain.BillType;
import com.niit.poster.repository.BillTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BillType}.
 */
@Service
@Transactional
public class BillTypeServiceImpl implements BillTypeService {

    private final Logger log = LoggerFactory.getLogger(BillTypeServiceImpl.class);

    private final BillTypeRepository billTypeRepository;

    public BillTypeServiceImpl(BillTypeRepository billTypeRepository) {
        this.billTypeRepository = billTypeRepository;
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
}
