package com.gr.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gr.market.entity.CoinPrice;
import com.gr.market.repository.CoinPriceRepository;

@Service
@Transactional
public class CoinPriceService {
	@Autowired
	CoinPriceRepository coinPriceRepository;

	public List<CoinPrice> getAll() {
		return coinPriceRepository.findAll();
	}

}
