package com.niit.poster.repository;

import com.niit.poster.domain.BillInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillInfoRepository extends JpaRepository<BillInfo, Long> {
}
