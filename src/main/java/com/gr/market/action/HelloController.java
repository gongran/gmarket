package com.gr.market.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gr.market.ApiClient;
import com.gr.market.Market02Application;
import com.gr.market.entity.BlockChain;
import com.gr.market.response.Account;
import com.gr.market.response.Kline;
import com.gr.market.service.BlockChainService;
import com.gr.market.util.CommonUtil;
import com.gr.market.util.DateUtil;
import com.gr.market.util.JsonUtil;

@Controller
public class HelloController {
	static final String API_KEY = "";
	static final String API_SECRET = "";
	@Autowired
	BlockChainService blockChainService;

	@RequestMapping("/index") // url
	public String index(Map<String, Object> map) throws IOException {
		ApiClient client = new ApiClient(API_KEY, API_SECRET);
		Map<String, String> params = new HashMap<>();
		params.put("symbol", "bchusdt");
		params.put("period", "1min");
		params.put("size", "10");
		List<Kline> klines = client.getKLine(params);
		map.put("name", "666");
		// 开盘，收盘，最低，最高
		List<String> xAxis = new ArrayList<String>();
		List<List<String>> yAxis = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("######0.00");
		for (Kline kl : klines) {
			String time = DateUtil.dateToString(kl.id);
			xAxis.add(time);
			String open = df.format(Double.valueOf(kl.open));
			String close = df.format(Double.valueOf(kl.close));
			String low = df.format(Double.valueOf(kl.low));
			String high = df.format(Double.valueOf(kl.high));
			List<String> yy = Arrays.asList(open, close, low, high);
			yAxis.add(yy);
		}
		Collections.reverse(xAxis);
		Collections.reverse(yAxis);
		map.put("xAxis", JsonUtil.writeValue(xAxis));
		map.put("yAxis", JsonUtil.writeValue(yAxis));
		return "index";// 真实的视图
	}

	@RequestMapping("/account")
	public String kline(Map<String, Object> map) throws IOException {
		// ApiClient client = new ApiClient(API_KEY, API_SECRET);
		// List<Account> accounts = client.getAccounts();
		// Map<String, String> params = null;
		// List<Map<String, Object>> list = new ArrayList<>();
		// for (Account at : accounts) {
		// String id = String.valueOf(at.id);
		// params = new HashMap<>();
		// params.put("account-id", id);
		// Map<String, Object> amap = client.getBalance(params);
		// @SuppressWarnings("unchecked")
		// List<Map<String, Object>> alist = (List<Map<String, Object>>)
		// amap.get("list");
		// alist = convertMapToHuman(alist, false);
		// amap.put("list", alist);
		// list.add(amap);
		// }
		// map.put("result", list);
		return "account/accounts";
	}

	@RequestMapping("/account02")
	@ResponseBody
	public List<Map<String, Object>> account(Map<String, Object> map, boolean isAll) throws IOException {
		ApiClient client = new ApiClient(API_KEY, API_SECRET);
		List<Account> accounts = client.getAccounts();
		Map<String, String> params = null;
		List<Map<String, Object>> list = new ArrayList<>();
		for (Account at : accounts) {
			String id = String.valueOf(at.id);
			params = new HashMap<>();
			params.put("account-id", id);
			Map<String, Object> amap = client.getBalance(params);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> alist = (List<Map<String, Object>>) amap.get("list");

			alist = convertMapToHuman(alist, isAll);
			amap.put("list", alist);
			list.add(amap);
		}
		// map.put("result", list);
		return list;
	}

	public Map<String, Map<String, Object>> allMarket() {

		ApiClient client = new ApiClient(API_KEY, API_SECRET);
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
		return listr;
	}

	/**
	 * 转换map为前端显示格式并过滤为0的币种
	 * 
	 * @param list
	 */
	private List<Map<String, Object>> convertMapToHuman(List<Map<String, Object>> list, boolean isAll) {
		List<Map<String, Object>> result = new ArrayList<>();
		Set<String> currencys = new HashSet<>();
		for (Map<String, Object> map : list) {
			String currency = String.valueOf(map.get("currency"));
			currencys.add(currency);
		}
		Map<String, Map<String, Object>> am = allMarket();
		boolean con = false;
		for (String key : currencys) {
			Map<String, Object> map = new HashMap<>();
			map.put("currency", key);
			for (Map<String, Object> tmap : list) {
				if (key.equals(tmap.get("currency"))) {
					Map<String, Object> t = am.get(key.toUpperCase());
					String cny = "0";
					if (t != null) {
						cny = String.valueOf(t.get("cny"));
					}
					String bstr = String.valueOf(tmap.get("balance"));
					Double balance = Double.valueOf(bstr);
					if ("trade".equals(tmap.get("type"))) {
						map.put("trade", bstr);
						map.put("cny_trade", CommonUtil.amountFomat(CommonUtil.multiply(bstr, cny),8));
						map.put("v_trade", CommonUtil.amountFomat(bstr, 8));
					} else {
						map.put("frozen", tmap.get("balance"));
						map.put("cny_frozen", CommonUtil.amountFomat(CommonUtil.multiply(bstr, cny),8));
						map.put("v_frozen", CommonUtil.amountFomat(bstr, 8));
					}
					if (!balance.equals(new Double(0))) {
						con = true;
					}
				}
			}
			if (con) {
				result.add(map);
			} else if (isAll) {
				result.add(map);
			}
			con = false;
		}
		return result;
	}
}