package com.hanul.test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.google.gson.Gson;

import common.CommonService;
import community.CommunityVO;
import gallery.GalleryServiceImpl;
import gallery.GalleryVO;

@Controller
@SessionAttributes("category")
public class GalleryController {

	@Autowired
	private GalleryServiceImpl service;
	@Autowired
	private CommonService common;

	// ������ ȭ���û
	@RequestMapping("/list.gal")
	public String list(Model model, String search, String keyword) {
		// DB���� ������ ����� ��ȸ�ؿ�
		// ���ȭ�鿡 ����� �� �ֵ��� Model�� ��´�.
		model.addAttribute("category", "gal"); // ī�װ�
		//�˻��� ����� �ش��ϴ� �����۸���� ��ȸ�ϴ� ���
		if( keyword != null ) {
			//key,value ������ �ڷᱸ�� : HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("search", search);
			map.put("keyword", keyword);
			model.addAttribute("list", service.list(map));
			model.addAttribute("search",search);
			model.addAttribute("keyword",keyword);
		}else {
			//�˻����� �����۸���� ��ȸ�ϴ� ���
			model.addAttribute("list", service.list());
		}
		return "gallery/list2";
	}//list()
	
	// �� ȭ���û
	@RequestMapping("/detail.gal")
	public String detail(Model model, String id, @RequestParam(defaultValue = "0") int read, HttpSession session) {
		String resources = session.getServletContext().getRealPath("resources");
		if (read == 1) {
			// ��ȸ�� ���� ó��
			service.read(id);
		}
		model.addAttribute("vo", service.detail(id));
		model.addAttribute("resources", resources);
		return "gallery/detail";
	}//detail()

	// �ű� ������ �ۼ� ȭ���û
	@RequestMapping("/new.gal")
	public String gallery() {
		return "gallery/new";
	}// gallery()

	// �ű� ������ ����ó�� ��û
	@RequestMapping("/insert.gal")
	public String insert(GalleryVO vo, MultipartFile file, HttpSession session) { // ÷������ ÷�� MultipartFile
		// ȭ�鿡�� �Է��� ������ DB�� ������ ��
		// ���ȭ������ ����
		// ÷�������� �ִ� ��� ���������� �����Ͱ�ü�� ��´�.
		if (file.getSize() > 0) {// ÷���� ������ �ִ� ���
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.upload("gallery", file, session));
		}
		service.insert(vo);
		return "redirect:list.gal";
	}// insert()
	
	
	//������ ���� ó�� ��û
	@RequestMapping("/delete.gal")
	public String delete(String id, HttpSession session) {
		//÷�ε� ������ �ִٸ� ������ �������� �ش� ������ ����
	File f = new File(
			session.getServletContext().getRealPath(
			"resources" + service.detail(id).getFilepath()));
			if(f.exists()) f.delete(); //������ �����ϴ� ó��
			//�ش� �������� DB���� ������ ��
			//���ȭ������ ����
			service.delete(id);
			return "redirect:list.gal";
	}//delete()
	
	//������ ����ȭ�� ��û
		@RequestMapping("/modify.gal")
		public String modify(String id, Model model) {
			//DB���� �ش� ������ ������ ��ȸ�ؿ� ��
			//����ȭ�鿡 ����ϵ��� Model�� ��´�.
			model.addAttribute("vo", service.detail(id));
			return "gallery/modify";
		}//modify()
		
		//������ ���� ó�� ��û
		@RequestMapping("/update.gal")
		public String update(GalleryVO vo, MultipartFile file, 
								HttpSession session, String attach) { // ���ϵ� ÷���ؾ��ϹǷ� MultipartFile, �������� ��ġ HttpSession, ���ֵ̹����� attach
			// ȭ�鿡�� ���� �Է��� ������ DB�� ������ �� 
			// ��ȭ������ ����
			
			// 1. ���� ÷�ε� ������ ���µ� �����ϸ鼭 ÷���ϴ� ���
			// ���� DB�� ����Ǿ� �ִ� ÷������������ ��ȸ�� �´�.
			GalleryVO gallery = service.detail(vo.getId());
			String uuid = gallery.getFilepath();
			// /upload/notice/2019/07/03/ads-er232-00faf_abc.txt
			// �������κ��� ��ġ�����͸� �����;���
			uuid = session.getServletContext().getRealPath("resources") +uuid;
			// d:/Study_Spring/...../upload/gallery/2019/07/03/ads-er232-00faf_abc.txt
			
			if(file.getSize()>0) {
				vo.setFilename(file.getOriginalFilename());
				vo.setFilepath(common.upload("gallery", file, session));
				// 2. ���� ÷�ε� ������ �־��� �� ������ �ٸ� ���Ϸ� ������ ÷���ϴ� ���
				// ���� ÷�ε� ������ �����Ѵ�.
				File f = new File(uuid);
				if(f.exists()) f.delete();
			}else {
				// 3. ���� ÷�ε� ������ �־��µ� ÷�ε� ������ �����ϴ� ���
				if(attach.equals("y")) {
					File f = new File(uuid);
					if(f.exists()) f.delete();
				}else {
					// 4. ���� ÷�ε� ������ �ְ� �״�� ����ϴ� ���
					vo.setFilename(gallery.getFilename());
					vo.setFilepath(gallery.getFilepath());
				}
			}
			service.update(vo);
			return "redirect:detail.gal?id=" + vo.getId();
		}//update()	
	
	//�ȵ���̵� ó��
	@ResponseBody @RequestMapping(value="/json.gal", 
			produces="application/json; charset=utf-8")
	public String JSONDate(Model model) {
		List<GalleryVO> data = new ArrayList<GalleryVO>();
		data = service.andlist();
		//System.out.println(data);
		service.galUpdate();
		String json = new Gson().toJson(model.addAttribute("andlist", data));
		return json;
	}
	
	@ResponseBody @RequestMapping(value="/andinsert.gal", 
			produces = "application/json;charset=UTF-8", method = {RequestMethod.GET,RequestMethod.POST})
	public JSONObject andInsert(@RequestParam ("filename") MultipartFile file,
								@RequestParam HashMap<String, String> map,
								HttpSession session) {

		JSONObject result = new JSONObject();
		
		try {
			String userid = (String) URLDecoder.decode(map.get("userid"),"UTF-8") ;
			String title = (String) URLDecoder.decode(map.get("title"),"UTF-8") ;
			String content = (String) URLDecoder.decode(map.get("content"),"UTF-8") ;
			String writer = (String) URLDecoder.decode(map.get("writer"),"UTF-8") ;
			String filename = file.getOriginalFilename();
			String filepath = common.upload("gallery", file, session);
			
			System.out.println("userid==============" + userid);
			System.out.println("title==============" + title);
			System.out.println("content==============" + content);
			System.out.println("writer==============" + writer);
			
			GalleryVO vo = new GalleryVO(userid, title, content, writer, filename, filepath);
			System.out.println("userid : " + userid);
			System.out.println("title : " + title);
			System.out.println("content : "+ content);
			System.out.println("writer : "+ writer);
			System.out.println("filename : "+ file.getOriginalFilename());
			System.out.println(file.getSize());
			vo.setFilename(file.getOriginalFilename());
			
			result.put("message", service.andInsert(vo));
			System.out.println(result);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		List<GalleryVO> data = new ArrayList<GalleryVO>();
		data = service.andlist();
		service.galUpdate();
		
		return result;
	}
	
	// 삭제(세일정보, 자유글)
		@ResponseBody
		@RequestMapping(value = "/anddelete.gal", produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST })
		public JSONObject delete(@RequestParam HashMap<String, Object> map) {
			JSONObject result = new JSONObject();
			result.put("message", service.and_delete(map));
			List<GalleryVO> data = new ArrayList<GalleryVO>();
			service.andlist();
			return result;
		}
		
		// 수정
		@ResponseBody
		@RequestMapping(value = "/andupdate.gal", produces = "application/json; charset=utf-8", method = {RequestMethod.GET, RequestMethod.POST })
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
				filepath = common.upload("gallery", file, session);

			} catch (Exception e) {
				// TODO: handle exception
			}
			//System.out.println("id==============" + id);
			//System.out.println("userid ==============" + userid);
			//System.out.println("title==============" + title);
			//System.out.println("content==============" + content);
			//System.out.println("writer==============" + writer);
			//System.out.println("fileName :" + file.getOriginalFilename());

			GalleryVO vo = new GalleryVO(id, userid, title, content, writer, filename, filepath);

			//System.out.println("id :" + id);
			//System.out.println("userid :" + userid);
			//System.out.println("title :" + title);
			//System.out.println("content : " + content);
			//System.out.println("writer :" + writer);

			//System.out.println(file.getSize());
			vo.setFilename(file.getOriginalFilename());

			result.put("message", service.and_update(vo));
			//System.out.println(result);
			List<GalleryVO> data = new ArrayList<GalleryVO>();
			data = service.andlist();
			for (int i = 0; i < data.size(); i++) {
				String filePath = data.get(i).getFilepath().replace("\\", "/");
				data.get(i).setFilepath(filePath);
				//System.out.println("=====================" + data.get(i).getFilepath());
			}
			return result;
		}
	
//	@RequestMapping("/andinsert.gal")
//	public String andinsert(GalleryVO vo, @RequestParam("p_name") String p_name, MultipartFile file, HttpSession session) { // ÷������ ÷�� MultipartFile
//		// ȭ�鿡�� �Է��� ������ DB�� ������ ��
//		// ���ȭ������ ����
//		// ÷�������� �ִ� ��� ���������� �����Ͱ�ü�� ��´�.
//		System.out.println("p_name=====================" + p_name);
//		
//		if (file.getSize() > 0) {// ÷���� ������ �ִ� ���
//			vo.setFilename(file.getOriginalFilename());
//			vo.setFilepath(common.upload("gallery", file, session));
//		}
//		service.insert(vo);
//		return "redirect:list.gal";
//	}// insert()
//	

	

	}
