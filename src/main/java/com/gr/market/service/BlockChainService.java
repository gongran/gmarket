package com.gr.market.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gr.market.entity.BlockChain;
import com.gr.market.repository.BlockChainRepository;

@Service
@Transactional
public class BlockChainService {
	@Autowired
	BlockChainRepository blockChainRepository;

	public BlockChain getBolockChainByName(String name) {
		Optional<BlockChain> bc = blockChainRepository.findOneByBcName(name);
		return bc.orElse(new BlockChain());
	}

	public List<BlockChain> getAllBlockChain() {
		return blockChainRepository.findAll();
	}

}
