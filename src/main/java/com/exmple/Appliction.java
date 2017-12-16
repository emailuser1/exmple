package com.exmple;

import com.exmple.utils.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper; 

public class Appliction {
	
	public static void main(String[] args) {
		// 登录
		HttpUtil httpUtil = new HttpUtil();
		String url = "https://cd.huilg.cn/user/login?username=robot&password=Q3e4kp0kl";
		try {
			String json = httpUtil.doGet(url);
			ObjectMapper mapper = new ObjectMapper();  
			LoginResult login = mapper.readValue(json, LoginResult.class);
			if (login.getErrno() != 0) {
				System.out.println(login.getTxt());
				return;
			}
			
			System.out.println(login.getToken());
			
			// 充值
			url = "https://cd.huilg.cn/account/recharge?token=" + login.getToken() + "&appleid=test@gmail.com&password=12356&code=X9MX8V5Z9N3FTYVJ";
			json = httpUtil.doGet(url);
			RechargeResult recharge = mapper.readValue(json, RechargeResult.class);
			if (recharge.getErrno() != 0) {
				System.out.println(recharge.getTxt());
				return;
			}
			
			// 查询充值结果
			url = "https://cd.huilg.cn/account/query?token=" + login.getToken() + "&id=" + recharge.getId();
			json = httpUtil.doGet(url);
			QueryResult query = mapper.readValue(json, QueryResult.class);
			if (query.getErrno() != 0) {
				System.out.println(query.getTxt());
				return;
			}
			
			System.out.println(query.getBalance());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
