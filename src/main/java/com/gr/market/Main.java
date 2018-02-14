package com.gr.market;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gr.market.constant.ChainBlockConstant;
import com.gr.market.response.Account;
import com.gr.market.response.Kline;
import com.gr.market.util.CommonUtil;
import com.gr.market.util.DateUtil;
import com.gr.market.util.JsonUtil;

public class Main {

	static final String API_KEY = ChainBlockConstant.KEY;
	static final String API_SECRET = ChainBlockConstant.SECRET;
	static ApiClient client = new ApiClient(API_KEY, API_SECRET);

	public static void main(String[] args) {
		Main main = new Main();
		try {
			main.juhe();
		} catch (ApiException e) {
			System.err.println("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage());
		}
	}

	/**
	 * 对比差价
	 */
	public void priceDifference() {
		// String url="/v1/common/symbols";//所有支持的交易对
		Map<String, String> params = new HashMap<>();
		params.put("symbol", "htusdt");
		params.put("period", "1min");
		params.put("size", "1");
		List<Kline> klines = client.getKLine(params);
		for (Kline kl : klines) {
			Long l = new Long(kl.id) * 1000;
			System.out.println("时间:" + DateUtil.dateToString(l.toString()) + " 成交金额:" + kl.amount + " 成交笔数:" + kl.count
					+ " 开盘价:" + kl.open + " 收盘价" + kl.close + " 最低价:" + kl.low + " 最高价:" + kl.high + " 成交量:" + kl.vol);
		}
	}

	/**
	 * 火币聚合行情
	 */
	public void juhe() {
		System.out.println("--------------------------------");
		// 币种
		String coins[] = { "cvc", "dta", "elf", "eos", "gnt", "hsr", "ht", "iost", "let", "nas", "omg", "qtum", "ruff",
				"smt", "theta", "ven", "zil" };
		String btc, eth;
		Map<String, String> result = null;

		result = juhe("btc", "usdt");
		btc = result.get("close");
		System.out.println("BTC/USDT:" + btc);

		result = juhe("eth", "usdt");
		eth = result.get("close");
		System.out.println("ETH/USDT:" + eth);
		for (String coin : coins) {
			System.out.println("----------------------");
			System.out.println("币种" + coin + ":");
			result = juhe(coin, "usdt");
			printJuhe(btc, eth, "usdt", result);
			result = juhe(coin, "btc");
			printJuhe(btc, eth, "btc", result);
			result = juhe(coin, "eth");
			printJuhe(btc, eth, "eth", result);
			System.out.println("----------------------");
		}

		System.out.println("--------------------------------");
	}

	public void printJuhe(String btc, String eth, String type, Map<String, String> result) {
		String close=result.get("close");
		if (!"usdt".equals(type)) {
			System.out.println("折合USDT："+CommonUtil.multiply(close, type.equals("eth")?eth:btc));

		}
		System.out.println(type.toUpperCase() + " " + result.get("close"));
		System.out.println("买一 " + result.get("bid") + " 成交量 " + result.get("bidVol"));
		System.out.println("卖一 " + result.get("ask") + " 成交量 " + result.get("askVol"));

	}

	public Map<String, String> juhe(String coin, String type) {
		Map<String, String> result = new HashMap<>();
		String url = "/market/detail/merged";
		Map<String, Object> map = null;
		// 买一价,买一量
		String bid = "";
		// 卖一价,卖一量
		String ask = "";
		// 收盘价
		String close = "";
		String[] bidArray, askArray;
		Map<String, String> params = new HashMap<>();
		params.put("symbol", coin + type);
		List<Map<String, Object>> list = client.getInfoTickByUrl(url, params);
		map = list.get(0);
		close = map.get("close").toString();
		bid = map.get("bid").toString();
		bid = bid.substring(1, bid.length() - 1);
		ask = map.get("ask").toString();
		ask = ask.substring(1, ask.length() - 1);
		bidArray = bid.split(",");
		askArray = ask.split(",");
		result.put("close", close);
		result.put("bid", bidArray[0]);
		result.put("ask", askArray[0]);
		result.put("bidVol", bidArray[1]);
		result.put("askVol", askArray[1]);
		return result;
	}

	public void apiSample() {
		Map<String, String> params = new HashMap<>();
		params.put("symbol", "bchusdt");
		params.put("period", "1min");
		params.put("size", "10");
		List<Kline> klines = client.getKLine(params);
		for (Kline kl : klines) {
			Long l = new Long(kl.id) * 1000;
			System.out.println("时间:" + DateUtil.dateToString(l.toString()) + " 成交金额:" + kl.amount + " 成交笔数:" + kl.count
					+ " 开盘价:" + kl.open + " 收盘价" + kl.close + " 最低价:" + kl.low + " 最高价:" + kl.high + " 成交量:" + kl.vol);
		}
	}

	static void print(Object obj) {
		try {
			System.out.println(JsonUtil.writeValue(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
