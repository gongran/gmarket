package com.gr.market;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import com.gr.market.entity.BlockChain;
import com.gr.market.entity.SysParam;
import com.gr.market.service.BlockChainService;
import com.gr.market.service.SysParamService;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class Market02ApplicationTests {
	@Autowired
	SysParamService sysParamService;
	@Autowired
	BlockChainService bolockChainService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getSysParam() {
		for (int i = 0; i < 3; i++) {
			Optional<SysParam> sp = sysParamService.getSysParamByName("cnyusd");
			if (sp.isPresent()) {
				System.out.println(sp.get().getParamValue());
			}
		}
	}

	@Test
	public void getBlockChain() {
		BlockChain bc = bolockChainService.getBolockChainByName("btc1");
		System.out.println(bc.getBcCnName());

	}

}
