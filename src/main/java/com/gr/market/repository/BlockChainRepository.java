package com.gr.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gr.market.entity.BlockChain;

@Repository
public interface BlockChainRepository extends JpaRepository<BlockChain, Long> {
	Optional<BlockChain> findOneByBcName(String bcName);

}
