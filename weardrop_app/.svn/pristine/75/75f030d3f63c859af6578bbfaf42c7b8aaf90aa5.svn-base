package com.hanul.test;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import center.CenterServiceImpl;

@Controller
public class AndQnaController {

	@Resource(name="service")private CenterServiceImpl service;
	
	// 방명록 목록화면 요청
		@ResponseBody @RequestMapping(value="/Co_list_android", produces="application/json; charset=utf-8")
		public JSONObject Co_list() {
			JSONObject result = new JSONObject();
			result.put("message", service.and_list());
			return result;
		}
}
