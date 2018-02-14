package com.gr.market;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gr.market.request.CreateOrderRequest;
import com.gr.market.response.Account;
import com.gr.market.response.ApiResponse;
import com.gr.market.response.Kline;
import com.gr.market.response.Symbol;
import com.gr.market.util.JsonUtil;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * API client.
 * 
 * @author liaoxuefeng
 */
public class ApiClient {

	static final int CONN_TIMEOUT = 5;
	static final int READ_TIMEOUT = 5;
	static final int WRITE_TIMEOUT = 5;

	// static final String API_HOST = "be.huobi.com";
	static final String API_HOST = "api.huobi.pro";

	static final String API_URL = "https://" + API_HOST;
	static final MediaType JSON = MediaType.parse("application/json");
	static final OkHttpClient client = createOkHttpClient();

	final String accessKeyId;
	final String accessKeySecret;
	final String assetPassword;

	/**
	 * 创建�?个ApiClient实例
	 * 
	 * @param accessKeyId
	 *            AccessKeyId
	 * @param accessKeySecret
	 *            AccessKeySecret
	 */
	public ApiClient(String accessKeyId, String accessKeySecret) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.assetPassword = null;
	}

	/**
	 * 创建一个ApiClient实例
	 * 
	 * @param accessKeyId
	 *            AccessKeyId
	 * @param accessKeySecret
	 *            AccessKeySecret
	 * @param assetPassword
	 *            AssetPassword
	 */
	public ApiClient(String accessKeyId, String accessKeySecret, String assetPassword) {
		this.accessKeyId = accessKeyId;
		this.accessKeySecret = accessKeySecret;
		this.assetPassword = assetPassword;
	}

	/**
	 * 查询交易
	 * 
	 * @return List of symbols.
	 */
	public List<Symbol> getSymbols() {
		ApiResponse<List<Symbol>> resp = get("/v1/common/symbols", null,
				new TypeReference<ApiResponse<List<Symbol>>>() {
				});
		return resp.checkAndReturn();
	}

	public List<Kline> getKLine(Map<String, String> params) {
		ApiResponse<List<Kline>> r = get("/market/history/kline", params,
				new TypeReference<ApiResponse<List<Kline>>>() {
				});
		return r.checkAndReturn();
	}

	/**
	 * 根据账户id查询账户余额
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> getBalance(Map<String, String> params) {
		String accountId = params.get("account-id");
		String url = "/v1/account/accounts/" + accountId + "/balance";
		ApiResponse<Map<String, Object>> r = get(url, params, new TypeReference<ApiResponse<Map<String, Object>>>() {
		});
		return r.checkAndReturn();
	}
	
	public List<Map<String, Object>> getInfoByUrl(String url,Map<String, String> params) {
		ApiResponse<List<Map<String, Object>>> r = get(url, params, new TypeReference<ApiResponse<List<Map<String, Object>>>>() {
		});
		return r.checkAndReturn(); 
	}
	public List<Map<String, Object>> getInfoTickByUrl(String url,Map<String, String> params) {
		ApiResponse<List<Map<String, Object>>> r = get(url, params, new TypeReference<ApiResponse<List<Map<String, Object>>>>() {
		});
		return r.checkAndReturnTick(); 
	}
	public String getStrInfoByUrl(String url,Map<String, String> params) {
		ApiResponse<String> r = get(url, params, new TypeReference<ApiResponse<String>>() {
		});
		return r.checkAndReturn(); 
	}
	/**
	 * 得到所有支持的币种
	 * @param params
	 * @return
	 */
	public List<String> getCurrencys(){
		String url="/v1/common/currencys";
		ApiResponse<List<String>> r = get(url, null, new TypeReference<ApiResponse<List<String>>>() {
		});
		return r.checkAndReturn();
	}

	/**
	 * 查询所有账户信息
	 */
	public List<Account> getAccounts() {
		ApiResponse<List<Account>> resp = get("/v1/account/accounts", null,
				new TypeReference<ApiResponse<List<Account>>>() {
				});
		return resp.checkAndReturn();
	}

	/**
	 * 创建订单（未执行)
	 * 
	 * @param request
	 *            CreateOrderRequest object.
	 * @return Order id.
	 */
	public Long createOrder(CreateOrderRequest request) {
		ApiResponse<Long> resp = post("/v1/order/orders", request, new TypeReference<ApiResponse<Long>>() {
		});
		return resp.checkAndReturn();
	}

	/**
	 * 执行订单
	 * 
	 * @param orderId
	 *            The id of created order.
	 * @return Order id.
	 */
	public String placeOrder(long orderId) {
		ApiResponse<String> resp = post("/v1/order/orders/" + orderId + "/place", null,
				new TypeReference<ApiResponse<String>>() {
				});
		return resp.checkAndReturn();
	}

	// send a GET request.
	<T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
		if (params == null) {
			params = new HashMap<>();
		}
		return call("GET", uri, null, params, ref);
	}

	// send a POST request.
	<T> T post(String uri, Object object, TypeReference<T> ref) {
		return call("POST", uri, object, new HashMap<String, String>(), ref);
	}

	// call api by endpoint.
	<T> T call(String method, String uri, Object object, Map<String, String> params, TypeReference<T> ref) {
		ApiSignature sign = new ApiSignature();
		sign.createSignature(this.accessKeyId, this.accessKeySecret, method, API_HOST, uri, params);
		try {
			Request.Builder builder = null;
			if ("POST".equals(method)) {
				RequestBody body = RequestBody.create(JSON, JsonUtil.writeValue(object));
				builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).post(body);
			} else {
				builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).get();
			}
			if (this.assetPassword != null) {
				builder.addHeader("AuthData", authData());
			}
			Request request = builder.build();
			Response response = client.newCall(request).execute();
			String s = response.body().string();
			return JsonUtil.readValue(s, ref);
		} catch (IOException e) {
			throw new ApiException(e);
		}
	}

	String authData() {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		md.update(this.assetPassword.getBytes(StandardCharsets.UTF_8));
		md.update("hello, moto".getBytes(StandardCharsets.UTF_8));
		Map<String, String> map = new HashMap<>();
		map.put("assetPwd", DatatypeConverter.printHexBinary(md.digest()).toLowerCase());
		try {
			return ApiSignature.urlEncode(JsonUtil.writeValue(map));
		} catch (IOException e) {
			throw new RuntimeException("Get json failed: " + e.getMessage());
		}
	}

	// Encode as "a=1&b=%20&c=&d=AAA"
	String toQueryString(Map<String, String> params) {
		return String.join("&", params.entrySet().stream().map((entry) -> {
			return entry.getKey() + "=" + ApiSignature.urlEncode(entry.getValue());
		}).collect(Collectors.toList()));
	}

	// create OkHttpClient:
	static OkHttpClient createOkHttpClient() {
		return new Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS).readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS).build();
	}

}

/**
 * API签名，签名规范：
 * 
 * http://docs.aws.amazon.com/zh_cn/general/latest/gr/signature-version-2.html
 * 
 * @author liaoxuefeng
 */
class ApiSignature {
	static boolean isLog=false;

	final Logger log = LoggerFactory.getLogger(getClass());

	static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");
	static final ZoneId ZONE_GMT = ZoneId.of("Z");

	/**
	 * 创建�?个有效的签名。该方法为客户端调用，将在传入的params中添加AccessKeyId、Timestamp、SignatureVersion、SignatureMethod、Signature参数�?
	 * 
	 * @param appKey
	 *            AppKeyId.
	 * @param appSecretKey
	 *            AppKeySecret.
	 * @param method
	 *            请求方法�?"GET"�?"POST"
	 * @param host
	 *            请求域名，例�?"be.huobi.com"
	 * @param uri
	 *            请求路径，注意不�??以及后的参数，例�?"/v1/api/info"
	 * @param params
	 *            原始请求参数，以Key-Value存储，注意Value不要编码
	 */
	public void createSignature(String appKey, String appSecretKey, String method, String host, String uri,
			Map<String, String> params) {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(method.toUpperCase()).append('\n') // GET
				.append(host.toLowerCase()).append('\n') // Host
				.append(uri).append('\n'); // /path
		params.remove("Signature");
		params.put("AccessKeyId", appKey);
		params.put("SignatureVersion", "2");
		params.put("SignatureMethod", "HmacSHA256");
		params.put("Timestamp", gmtNow());
		// build signature:
		SortedMap<String, String> map = new TreeMap<>(params);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append('=').append(urlEncode(value)).append('&');
		}
		// remove last '&':
		sb.deleteCharAt(sb.length() - 1);
		// sign:
		Mac hmacSha256 = null;
		try {
			hmacSha256 = Mac.getInstance("HmacSHA256");
			SecretKeySpec secKey = new SecretKeySpec(appSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			hmacSha256.init(secKey);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("No such algorithm: " + e.getMessage());
		} catch (InvalidKeyException e) {
			throw new RuntimeException("Invalid key: " + e.getMessage());
		}
		String payload = sb.toString();
		byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
		String actualSign = Base64.getEncoder().encodeToString(hash);
		params.put("Signature", actualSign);
		if (isLog&&log.isDebugEnabled()) {
			log.debug("Dump parameters:");
			for (Map.Entry<String, String> entry : params.entrySet()) {
				log.debug("  key: " + entry.getKey() + ", value: " + entry.getValue());
			}
		}
	}

	/**
	 * 使用标准URL Encode编码。注意和JDK默认的不同，空格被编码为%20而不�?+�?
	 * 
	 * @param s
	 *            String字符�?
	 * @return URL编码后的字符�?
	 */
	public static String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("UTF-8 encoding not supported!");
		}
	}

	/**
	 * Return epoch seconds
	 */
	long epochNow() {
		return Instant.now().getEpochSecond();
	}

	String gmtNow() {
		return Instant.ofEpochSecond(epochNow()).atZone(ZONE_GMT).format(DT_FORMAT);
	}
}
