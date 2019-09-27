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
import community.CommentVO;
import community.CommunityServiceImpl;
import community.CommunityVO;
import hugi.HugiServiceImpl;
import hugi.HugiVO;

@Controller
public class AndcommunityController {
	@Autowired private CommunityServiceImpl service;
	@Autowired private CommonService common;
	@Autowired private HugiServiceImpl service1;
	// json자유글
	@ResponseBody
	@RequestMapping(value = "/free.com", produces = "application/json; charset=utf-8")
	public String JSONFree(Model model) {
		List<CommunityVO> data = new ArrayList<CommunityVO>();
		data = service.and_list();
		//System.out.println("============="+data);
		// ÷������ file��� ġȯ
		for (int i = 0; i < data.size(); i++) {
			if( data.get(i).getFilepath() != null) {
				String filePath = data.get(i).getFilepath().replace("\\", "/");
				String filePath2 = filePath.replace("weardrop_app", "weardrop");
				data.get(i).setFilepath(filePath2);
			} else {
				String filePath = "null";
				data.get(i).setFilepath(filePath);
			}
			// System.out.println("=====================" + data.get(i).getFilepath());
		}
		String json = new Gson().toJson(model.addAttribute("list", data));
		return json;
	}

	// json 세일정보
	@ResponseBody
	@RequestMapping(value = "/sale.com", produces = "application/json; charset=utf-8")
	public String JSONSale(Model model) {
		List<CommunityVO> data = new ArrayList<CommunityVO>();
		data = service.and_list2();
		// System.out.println(data);
		// ÷������ file��� ġȯ
		for (int i = 0; i < data.size(); i++) {
			if( data.get(i).getFilepath() != null) {
				String filePath = data.get(i).getFilepath().replace("\\", "/");
				String filePath2 = filePath.replace("weardrop_app", "weardrop");
				data.get(i).setFilepath(filePath2);
			} else {
				String filePath = "null";
				data.get(i).setFilepath(filePath);
			}
			// System.out.println("=====================" + data.get(i).getFilepath());
		}
		String json = new Gson().toJson(model.addAttribute("list", data));
		return json;
	}

	// 안드 -> 서버 insert(세일정보, 자유글)
	@ResponseBody
	@RequestMapping(value = "/commu.com", produces = "application/json; charset=utf-8", method = { RequestMethod.GET, RequestMethod.POST })
	public JSONObject commu(@RequestParam("filename") MultipartFile file, @RequestParam HashMap<String, String> map, HttpSession session) {

		JSONObject result = new JSONObject();

		try {
			String userid = (String) URLDecoder.decode(map.get("userid"), "UTF-8");
			String title = (String) URLDecoder.decode(map.get("title"), "UTF-8");
			String content = (String) URLDecoder.decode(map.get("content"), "UTF-8");
			String writer = (String) URLDecoder.decode(map.get("writer"), "UTF-8");
			String filename = file.getOriginalFilename();
			String filepath = common.upload("community", file, session);
			int code = Integer.parseInt(map.get("code"));
			// System.out.println("title==============" + title);
			// System.out.println("content==============" + content);
			// System.out.println("writer==============" + writer);

			CommunityVO vo = new CommunityVO(userid, title, content, writer, filename, filepath, code);
			// System.out.println("���� : " + title);
			// System.out.println("���� : " + content);
			// System.out.println("�ۼ��� : " + writer);
			// System.out.println("���ϸ� : " + file.getOriginalFilename());
			// System.out.println(file.getSize());
			vo.setFilename(file.getOriginalFilename());

			result.put("message", service.and_insert(vo));
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<CommunityVO> data = new ArrayList<CommunityVO>();
		data = service.and_list();
		for (int i = 0; i < data.size(); i++) {
			
			if( data.get(i).getFilepath() != null) {
				String filePath = data.get(i).getFilepath().replace("\\", "/");
				data.get(i).setFilepath(filePath);
			} else {
				String filePath = "null";
				data.get(i).setFilepath(filePath);
			}
			 System.out.println("=====================" + data.get(i).getFilepath());
		}
		return result;
	}

	// 삭제(세일정보, 자유글)
	@ResponseBody
	@RequestMapping(value = "/anddelete.com", produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST })
	public JSONObject delete(@RequestParam HashMap<String, Object> map) {
		JSONObject result = new JSONObject();
		result.put("message", service.and_delete(map));
		List<CommunityVO> data = new ArrayList<CommunityVO>();
		service.and_list();
		return result;
	}

	// 삭제(후기)
	@ResponseBody
	@RequestMapping(value = "/anddelete.hu", produces = "application/json; charset=utf-8", method = { RequestMethod.GET, RequestMethod.POST })
	public JSONObject hugidelete(@RequestParam HashMap<String, Object> map) {
		JSONObject result = new JSONObject();
		result.put("message", service1.and_delete(map));
		List<HugiVO> data = new ArrayList<HugiVO>();
		service1.and_list3();
		return result;
	}

	// 수정(자유글, 세일정보)
	@ResponseBody
	@RequestMapping(value = "/andupdate.com", produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST })
	public JSONObject update(@RequestParam("filename") MultipartFile file, @RequestParam HashMap<String, String> map, HttpSession session) {
		JSONObject result = new JSONObject();
		String id = null;
		String userid = null;
		String title = null;
		String content = null;
		String writer = null;
		String filename = null;
		String filepath = null;

		try {
			id = (String) URLDecoder.decode(map.get("id"), "UTF-8");
			userid = (String) URLDecoder.decode(map.get("userid"), "UTF-8");
			title = (String) URLDecoder.decode(map.get("title"), "UTF-8");
			content = (String) URLDecoder.decode(map.get("content"), "UTF-8");
			writer = (String) URLDecoder.decode(map.get("writer"), "UTF-8");
			filename = file.getOriginalFilename();
			filepath = common.upload("community", file, session);

		} catch (Exception e) {
			// TODO: handle exception
		}
		//System.out.println("id==============" + id);
		//System.out.println("userid ==============" + userid);
		//System.out.println("title==============" + title);
		//System.out.println("content==============" + content);
		//System.out.println("writer==============" + writer);
		//System.out.println("fileName :" + file.getOriginalFilename());

		CommunityVO vo = new CommunityVO(id, userid, title, content, writer, filename, filepath);

		//System.out.println("id :" + id);
		//System.out.println("userid :" + userid);
		//System.out.println("title :" + title);
		//System.out.println("content : " + content);
		//System.out.println("writer :" + writer);

		//System.out.println(file.getSize());
		vo.setFilename(file.getOriginalFilename());

		result.put("message", service.and_update(vo));
		//System.out.println(result);
		List<CommunityVO> data = new ArrayList<CommunityVO>();
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
	@RequestMapping(value = "/reply.com", produces = "application/json; charset=utf-8")
	public String JSONComment(Model model, int pid) {
		System.out.println("ddd");
		List<CommentVO> data = new ArrayList<CommentVO>();
		data = service.andcomment_list(pid);
		// System.out.println("==============" + data);
		String json = new Gson().toJson(model.addAttribute("list", data));
		return json;
	}
	
		
	// 댓글 insert
/*	@ResponseBody
	@RequestMapping(value = "/replysave.com", produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST })
	public JSONObject Replyinsert(@RequestParam HashMap<String, String> map, HttpSession session, String userid) {
		JSONObject result = new JSONObject();

		try {
			String content = (String) URLDecoder.decode(map.get("content"), "UTF-8");
			System.out.println("content==============" + content);

			CommentVO vo = new CommentVO(content);

			result.put("message", service.comment_insert(map));
			System.out.println(result);
		} catch (Exception e) {
			// TODO: handle exception
		}

		List<CommentVO> data = new ArrayList<CommentVO>();
		data = service.comment_list(pid);
		return result;
	}
}*/
}