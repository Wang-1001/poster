package com.niit.poster.service.impl;

import com.niit.poster.service.BillInfoService;
import com.niit.poster.domain.BillInfo;
import com.niit.poster.repository.BillInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BillInfo}.
 */
@Service
@Transactional
public class BillInfoServiceImpl implements BillInfoService {

    private final Logger log = LoggerFactory.getLogger(BillInfoServiceImpl.class);

    private final BillInfoRepository billInfoRepository;

    public BillInfoServiceImpl(BillInfoRepository billInfoRepository) {
        this.billInfoRepository = billInfoRepository;
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
}
