package com.hanul.test;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import common.CommonService;
import sell.SellVO;
import sell.SellServiceImpl;

@Controller
public class AndsellController {
	@Autowired private SellServiceImpl service;
	@Autowired private CommonService common;
	@Autowired private SellServiceImpl service1;
	
	// json자유글(구매)
	@ResponseBody
	@RequestMapping(value = "/free2.com", produces = "application/json; charset=utf-8")
	public String JSONFree(Model model) {
		List<SellVO> data = new ArrayList<SellVO>();
		data = service.and_list();
		// ÷������ file��� ġȯ
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getFilepath() != null) {
				String filePath = data.get(i).getFilepath().replace("\\", "/");
				data.get(i).setFilepath(filePath);
			} else {
				String filepath = "null";
				data.get(i).setFilepath(filepath);
			}
		}
		String json = new Gson().toJson(model.addAttribute("list", data));
		return json;
	}

	// json 세일정보(판매)
	@ResponseBody
	@RequestMapping(value = "/sale2.com", produces = "application/json; charset=utf-8")
	public String JSONSale(Model model) {
		List<SellVO> data = new ArrayList<SellVO>();
		data = service.and_list2();
		// System.out.println(data);
		// ÷������ file��� ġȯ
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getFilepath() != null) {
				String filePath = data.get(i).getFilepath().replace("\\", "/");
				data.get(i).setFilepath(filePath);
			} else {
				String filepath = "null";
				data.get(i).setFilepath(filepath);
			}
			// System.out.println("=====================" + data.get(i).getFilepath());
		}
		String json = new Gson().toJson(model.addAttribute("list", data));
		return json;
	}


	// 안드 -> 서버 insert(세일정보, 자유글)
	@ResponseBody
	@RequestMapping(value = "/commu2.com", produces = "application/json; charset=utf-8", method = { RequestMethod.GET, RequestMethod.POST })
	public JSONObject commu(@RequestParam("filename") MultipartFile file, @RequestParam HashMap<String, String> map, HttpSession session) {

		JSONObject result = new JSONObject();

		try {
			String userid = (String) URLDecoder.decode(map.get("userid"), "UTF-8");
			String title = (String) URLDecoder.decode(map.get("title"), "UTF-8");
			String content = (String) URLDecoder.decode(map.get("content"), "UTF-8");
			String price = (String) URLDecoder.decode(map.get("price"), "UTF-8");
			String writer = (String) URLDecoder.decode(map.get("writer"), "UTF-8");
			String filename = file.getOriginalFilename();
			String filepath = common.upload("sell", file, session);
			int code = Integer.parseInt(map.get("code"));

			SellVO vo = new SellVO(userid, title, content,  writer, price, filename, filepath, code);

			vo.setFilename(file.getOriginalFilename());

			result.put("message", service.and_insert(vo));
			// System.out.println(result);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<SellVO> data = new ArrayList<SellVO>();
		data = service.and_list();
		
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).getFilepath() != null) {
				String filePath = data.get(i).getFilepath().replace("\\", "/");
				data.get(i).setFilepath(filePath);
			} else {
				String filepath = "null";
				data.get(i).setFilepath(filepath);
			}
			System.out.println("=====================" + data.get(i).getFilepath());
		}
		return result;
	}


	// 삭제(세일정보, 자유글)
	@ResponseBody
	@RequestMapping(value = "/anddelete2.com", produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST })
	public JSONObject delete(@RequestParam HashMap<String, Object> map) {
		JSONObject result = new JSONObject();
		result.put("message", service.and_delete(map));
		List<SellVO> data = new ArrayList<SellVO>();
		service.and_list();
		return result;
	}


	// 수정(자유글, 세일정보)
	@ResponseBody
	@RequestMapping(value = "/andupdate2.com", produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST })
	public JSONObject update(@RequestParam("filename") MultipartFile file, @RequestParam HashMap<String, String> map, HttpSession session) {
		JSONObject result = new JSONObject();
		String id = null;
		String userid = null;
		String title = null;
		String content = null;
		String price = null;
		String writer = null;
		String filename = null;
		String filepath = null;

		try {
			id = (String) URLDecoder.decode(map.get("id"), "UTF-8");
			userid = (String) URLDecoder.decode(map.get("userid"), "UTF-8");
			title = (String) URLDecoder.decode(map.get("title"), "UTF-8");
			content = (String) URLDecoder.decode(map.get("content"), "UTF-8");
			price = (String) URLDecoder.decode(map.get("price"), "UTF-8");
			writer = (String) URLDecoder.decode(map.get("writer"), "UTF-8");
			filename = file.getOriginalFilename();
			filepath = common.upload("sell", file, session);

		} catch (Exception e) {
			// TODO: handle exception
		}


		SellVO vo = new SellVO(id, userid, title, content,  writer, price, filename, filepath);

		vo.setFilename(file.getOriginalFilename());

		result.put("message", service.and_update(vo));
		//System.out.println(result);
		List<SellVO> data = new ArrayList<SellVO>();
		data = service.and_list();
		for (int i = 0; i < data.size(); i++) {
			String filePath = data.get(i).getFilepath().replace("\\", "/");
			data.get(i).setFilepath(filePath);
			//System.out.println("=====================" + data.get(i).getFilepath());
		}
		return result;
	}
	
	

	//json 댓글	
	@ResponseBody	
	@RequestMapping(value = "/reply2.com", produces = "application/json; charset=utf-8")
	public String JSONComment(Model model, int pid) {
		System.out.println("ddd");
		List<SellVO> data = new ArrayList<SellVO>();
		data = service.andcomment_list(pid);
		System.out.println("==============" + data);
		String json = new Gson().toJson(model.addAttribute("list", data));
		return json;
	}
}