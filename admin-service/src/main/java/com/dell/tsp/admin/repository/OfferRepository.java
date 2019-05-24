package com.dell.tsp.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dell.tsp.admin.entity.OfferEntity;

@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long>{

}

