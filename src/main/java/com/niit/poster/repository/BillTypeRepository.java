package com.niit.poster.repository;

import com.niit.poster.domain.BillType;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BillType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillTypeRepository extends JpaRepository<BillType, Long> {
}
