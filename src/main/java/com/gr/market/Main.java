package com.gr.market;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gr.market.constant.ChainBlockConstant;
import com.gr.market.response.Account;
import com.gr.market.response.Kline;
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
	public void priceDifference(){
//		String url="/v1/common/symbols";//所有支持的交易对
		Map<String, String> params = new HashMap<>();
		params.put("symbol", "htusdt");
		params.put("period", "1min");
		params.put("size", "1");
		List<Kline> klines = client.getKLine(params);
		for (Kline kl : klines) {
			Long l=new Long(kl.id)*1000;
			System.out.println("时间:" + DateUtil.dateToString(l.toString()) + " 成交金额:" + kl.amount + " 成交笔数:" + kl.count
					+ " 开盘价:" + kl.open + " 收盘价" + kl.close + " 最低价:" + kl.low + " 最高价:" + kl.high + " 成交量:" + kl.vol);
		}
	}
	
	public void juhe(){
		String url="/market/detail/merged";
		Map<String, String> params = new HashMap<>();
		params.put("symbol", "btcusdt");
		List<Map<String,Object>> list=client.getInfoTickByUrl(url, params);
		print(list);
	}

	public void apiSample() {
		Map<String, String> params = new HashMap<>();
		params.put("symbol", "bchusdt");
		params.put("period", "1min");
		params.put("size", "10");
		List<Kline> klines = client.getKLine(params);
		for (Kline kl : klines) {
			Long l=new Long(kl.id)*1000;
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
