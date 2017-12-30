package com.gr.market.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gr.market.entity.SysParam;
import com.gr.market.repository.SysParamRepository;

@Service
@Transactional
public class SysParamService {
	@Autowired
	SysParamRepository sysParamRepository;

	@Cacheable(value = "demo", key = "#name")
	public Optional<SysParam> getSysParamByName(String name) {
		return sysParamRepository.findOneByParamName(name);
	}

	public List<SysParam> getAll() {
		return sysParamRepository.findAll();
	}

}
