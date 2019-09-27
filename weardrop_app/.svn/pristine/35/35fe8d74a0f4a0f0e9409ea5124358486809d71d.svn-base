package com.hanul.test;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import notice.NoticeServiceImpl;

@Controller
public class AndNoController {

	@Resource(name="NoticeService")private NoticeServiceImpl service;

	// 방명록 목록화면 요청
	@ResponseBody @RequestMapping(value="/No_list_android", produces="application/json; charset=utf-8")
	public JSONObject No_list() {
		JSONObject result = new JSONObject();
		result.put("message", service.and_list());
		return result;
	}
	
}
