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
import com.gr.market.entity.CoinPrice;
import com.gr.market.entity.SysParam;
import com.gr.market.response.Account;
import com.gr.market.response.Kline;
import com.gr.market.service.BlockChainService;
import com.gr.market.service.CoinPriceService;
import com.gr.market.service.SysParamService;
import com.gr.market.util.CommonUtil;
import com.gr.market.util.DateUtil;
import com.gr.market.util.JsonUtil;

@Controller
public class HelloController {
	@Autowired
	BlockChainService blockChainService;
	@Autowired
	SysParamService sysParamService;
	@Autowired
	CoinPriceService coinPriceService;
	String API_KEY = null;
	String API_SECRET = null;

	@RequestMapping("/index") // url
	public String index(Map<String, Object> map) throws IOException {
		API_KEY = sysParamService.getSysParamByName("API_KEY").get().getParamValue();
		API_SECRET = sysParamService.getSysParamByName("API_SECRET").get().getParamValue();
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
		return "account/accounts";
	}

	@RequestMapping("/account03")
	@ResponseBody
	public List<Map<String, Object>> accountDetail(Map<String, Object> map, boolean isAll) {
		API_KEY = sysParamService.getSysParamByName("API_KEY").get().getParamValue();
		API_SECRET = sysParamService.getSysParamByName("API_SECRET").get().getParamValue();
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
		return list;
	}

	@RequestMapping("/account02")
	@ResponseBody
	public List<Map<String, Object>> account(Map<String, Object> map, boolean isAll) throws IOException {
		API_KEY = sysParamService.getSysParamByName("API_KEY").get().getParamValue();
		API_SECRET = sysParamService.getSysParamByName("API_SECRET").get().getParamValue();
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
		API_KEY = sysParamService.getSysParamByName("API_KEY").get().getParamValue();
		API_SECRET = sysParamService.getSysParamByName("API_SECRET").get().getParamValue();
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
		// Map<String, Map<String, Object>> am = allMarket();
		List<CoinPrice> cplist = coinPriceService.getAll();
		Map<String, String> usdtMap = new HashMap<>();
		Map<String, String> ethMap = new HashMap<>();
		Map<String, String> btcMap = new HashMap<>();
		for (CoinPrice cp : cplist) {
			if (cp.getCpType().equals("USDT")) {
				usdtMap.put(cp.getCpKey(), cp.getCpValue());
			} else if (cp.getCpType().equals("ETH")) {
				ethMap.put(cp.getCpKey(), cp.getCpValue());
			} else if (cp.getCpType().equals("BTC")) {
				btcMap.put(cp.getCpKey(), cp.getCpValue());
			}

		}

		boolean con = false;
		for (String key : currencys) {
			Map<String, Object> map = new HashMap<>();
			map.put("currency", key);
			String usdEth = usdtMap.get("ETH");
			String usdBtc = usdtMap.get("BTC");
			for (Map<String, Object> tmap : list) {
				if (key.equals(tmap.get("currency"))) {
					String cny = "6.4979";
					String bstr = String.valueOf(tmap.get("balance"));
					Double balance = Double.valueOf(bstr);
					String uv = usdtMap.get(key.toUpperCase()) == null ? "0" : usdtMap.get(key.toUpperCase());
					String cuv = uv.equals("0") ? "0" : CommonUtil.multiply(uv, cny);
					String ev = ethMap.get(key.toUpperCase()) == null ? "0" : ethMap.get(key.toUpperCase());
					String cev = ev.equals("0") ? "0" : CommonUtil.multiply(CommonUtil.multiply(ev, usdEth), cny);
					String bv = btcMap.get(key.toUpperCase()) == null ? "0" : btcMap.get(key.toUpperCase());
					String cbv = bv.equals("0") ? "0" : CommonUtil.multiply(CommonUtil.multiply(bv, usdEth), cny);

					String lastcuv = "0";
					if (!cuv.equals("0")) {
						lastcuv = cuv;
					} else if (!cev.equals("0")) {
						lastcuv = cev;
					} else if (!cbv.equals("0")) {
						lastcuv = cbv;
					}

					String cnyValue = CommonUtil.amountFomat(CommonUtil.multiply(bstr, lastcuv), 8);// 人民币总价值
					String usdtValue = CommonUtil.amountFomat(CommonUtil.multiply(bstr, uv), 8);
					String ethValue = CommonUtil.amountFomat(CommonUtil.multiply(bstr, ev), 8);
					String btcValue = CommonUtil.amountFomat(CommonUtil.multiply(bstr, bv), 8);
					String vValue = CommonUtil.amountFomat(bstr, 8);// 个数
					if ("trade".equals(tmap.get("type"))) {
						map.put("trade", bstr);
						map.put("cny_trade", cnyValue);
						map.put("usdt_trade", usdtValue);
						map.put("eth_trade", ethValue);
						map.put("btc_trade", btcValue);
						map.put("v_trade", vValue);
					} else {
						map.put("frozen", tmap.get("balance"));
						map.put("cny_frozen", cnyValue);
						map.put("usdt_frozen", usdtValue);
						map.put("eth_frozen", ethValue);
						map.put("btc_frozen", btcValue);
						map.put("v_frozen", vValue);
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