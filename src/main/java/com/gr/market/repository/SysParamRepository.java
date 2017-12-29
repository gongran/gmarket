package com.gr.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gr.market.entity.SysParam;

@Repository
public interface SysParamRepository extends JpaRepository<SysParam, Long> {
	Optional<SysParam> findOneByParamName(String paramName);
}
