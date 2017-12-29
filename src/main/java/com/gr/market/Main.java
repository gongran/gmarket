package com.gr.market;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gr.market.request.CreateOrderRequest;
import com.gr.market.response.Account;
import com.gr.market.response.Kline;
import com.gr.market.util.DateUtil;
import com.gr.market.util.JsonUtil;

public class Main {

	static final String API_KEY = "";
	static final String API_SECRET = "";

//	public static void main(String[] args) {
//		try {
////			apiSample();
//			apiAccount();
//		} catch (ApiException e) {
//			System.err.println("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}

	static void apiAccount() {
		ApiClient client = new ApiClient(API_KEY, API_SECRET);
		List<Account> accounts = client.getAccounts();
		print(accounts);
	}

	static void apiSample() {
		// create ApiClient using your api key and api secret:
		// 创建客户�?
		ApiClient client = new ApiClient(API_KEY, API_SECRET);
		// get symbol list:
		// print(client.getSymbols());
		// get accounts:
		// List<Account> accounts = client.getAccounts();
		// print(accounts);
		Map<String, String> params = new HashMap<>();
		params.put("symbol", "bchusdt");
		params.put("period", "1min");
		params.put("size", "10");
		List<Kline> klines = client.getKLine(params);
		for (Kline kl : klines) {
			System.out.println("时间:" + DateUtil.dateToString(kl.id) + " 成交�?:" + kl.amount + " 成交笔数:" + kl.count
					+ " �?盘价:" + kl.open + " 收盘�?" + kl.close + " �?低价:" + kl.low + " �?高价:" + kl.high + " 成交�?:"
					+ kl.vol);
		}
		// if (!accounts.isEmpty()) {
		// // find account id:
		// Account account = accounts.get(0);
		// long accountId = account.id;
		// // create order:
		// CreateOrderRequest createOrderReq = new CreateOrderRequest();
		// createOrderReq.accountId = String.valueOf(accountId);
		// createOrderReq.amount = "0.02";
		// createOrderReq.price = "1100.99";
		// createOrderReq.symbol = "ethcny";
		// createOrderReq.type = CreateOrderRequest.OrderType.BUY_LIMIT;
		// Long orderId = client.createOrder(createOrderReq);
		// print(orderId);
		// // place order:
		// String r = client.placeOrder(orderId);
		// print(r);
		// }
	}

	static void print(Object obj) {
		try {
			System.out.println(JsonUtil.writeValue(obj));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
