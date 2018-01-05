package com.gr.market.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gr.market.ApiClient;
import com.gr.market.response.Account;
import com.gr.market.service.BlockChainService;
import com.gr.market.service.SysParamService;

@Controller
public class AccountController {
	@Autowired
	BlockChainService blockChainService;
	@Autowired
	SysParamService sysParamService;
	String API_KEY = null;
	String API_SECRET = null;

	@RequestMapping("/adetail")
	@ResponseBody
	public String accountDetail() {
		API_KEY = sysParamService.getSysParamByName("API_KEY").get().getParamValue();
		API_SECRET = sysParamService.getSysParamByName("API_SECRET").get().getParamValue();
		ApiClient client = new ApiClient(API_KEY, API_SECRET);
		List<Account> accounts = client.getAccounts();
		return null;
	}
}
