package com.gr.market;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.gr.market.constant.ChainBlockConstant;
import com.gr.market.entity.BlockChain;
import com.gr.market.response.Account;
import com.gr.market.response.Kline;
import com.gr.market.service.BlockChainService;
import com.gr.market.util.DateUtil;
import com.gr.market.util.JsonUtil;

public class ApiTest {
	static final String API_KEY = ChainBlockConstant.KEY;
	static final String API_SECRET = ChainBlockConstant.SECRET;
	ApiClient client = new ApiClient(API_KEY, API_SECRET);

	/**
	 * 查询当前用户的所有账户
	 */
	@Test
	public void testAccounts() {
		List<Account> accounts = client.getAccounts();
		print(accounts);
		for(Account at:accounts){
			print(at);
		}
	}

	/**
	 * 得到所有支持的币种
	 */
	@Test
	public void testGetCurrencys() {
		List<String> list = client.getCurrencys();
		print(list);
	}

	@Test
	public void test() {
		// 查询系统当前时间
		String url = "/v1/common/timestamp";
		Map<String, String> params = new HashMap<>();
		String time = client.getStrInfoByUrl(url, params);
		time=DateUtil.dateToString(time);
		System.out.println(time);
	}

	/**
	 * 得到各种货币的行情
	 */
	@Test
	public void testPrice() {
		ApplicationContext context = SpringApplication.run(Market02Application.class);
		BlockChainService blockChainService = (BlockChainService) context.getBean("blockChainService");
		List<BlockChain> list = blockChainService.getAllBlockChain();
		Map<String, String> params = new HashMap<>();
		params.put("period", "1min");
		params.put("size", "1");
		Map<String, Object> map = new HashMap<>();
		for (BlockChain bc : list) {
			if (bc.getDealMode() == 0) {
				params.put("symbol", bc.getBcName().toLowerCase() + "usdt");
				Kline kl = null;
				try {
					kl = client.getKLine(params).get(0);
					map.put(bc.getBcName(), kl.close);
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println(bc.getBcName());
				}
			} else if (bc.getDealMode() == 1) {
				params.put("symbol", bc.getBcName().toLowerCase() + "eth");
				Kline kl = null;
				try {
					kl = client.getKLine(params).get(0);
					map.put(bc.getBcName(), "eth_" + kl.close);
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println(bc.getBcName());
				}

			} else if (bc.getDealMode() == 2) {
				params.put("symbol", bc.getBcName().toLowerCase() + "btc");
				Kline kl = null;
				try {
					kl = client.getKLine(params).get(0);
					map.put(bc.getBcName(), "btc_" + kl.close);
				} catch (Exception e) {
					// e.printStackTrace();
					System.out.println(bc.getBcName());
				}

			}
		}
		String ethp = String.valueOf(map.get("ETH"));
		String btcp = String.valueOf(map.get("BTC"));
		Map<String, Map<String, Object>> listr = new HashMap<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Map<String, Object> mm = new HashMap<>();
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			String[] kv = value.split("_");
			mm.put("key", key);
			if (kv.length > 1) {
				mm.put(kv[0], kv[1]);
				if ("eth".equals(kv[0])) {
					BigDecimal b1 = new BigDecimal(kv[1]);
					b1 = b1.multiply(new BigDecimal(ethp));
					b1 = b1.multiply(new BigDecimal("6.6"));
					mm.put("cny", b1);
				} else {
					BigDecimal b1 = new BigDecimal(kv[1]);
					b1 = b1.multiply(new BigDecimal(btcp));
					b1 = b1.multiply(new BigDecimal("6.6"));
					mm.put("cny", b1);
				}
			} else {
				mm.put("usdt", value);
				BigDecimal b1 = new BigDecimal(value);
				b1 = b1.multiply(new BigDecimal("6.6"));
				mm.put("cny", b1.toString());
			}
			listr.put(key, mm);

		}
		print(listr);
	}

	public void print(Object obj) {
		try {
			System.out.println(JsonUtil.writeValue(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
