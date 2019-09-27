package com.hanul.test;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import community.CommentVO;
import hugi.HugiServiceImpl;
import hugi.HugiVO;
import main.MainVO;

@Controller
public class hugiController {
	@Autowired private HugiServiceImpl service;
	@Autowired private CommonService common;

	// 후기게시판 화면 요청
	@RequestMapping("/list.hu")
	public String hugi(Model model, String search, String keyword) {
		if( keyword != null ) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("search", search);
			map.put("keyword", keyword);
			model.addAttribute("list", service.list(map));
			model.addAttribute("search",search);
			model.addAttribute("keyword",keyword);
		}else {
			model.addAttribute("list", service.list());
		}
		return "community/hugi";
	}

	// 후기 상세화면 요청
	@RequestMapping("/detail.hu")
	public String detail(Model model, String id, @RequestParam(defaultValue="0") int read) {
		
		if(read == 1) {		//조회수
			service.read(id);
		}
		model.addAttribute("vo", service.detail(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "community/hugidetail";
	}

	// 후기 글쓰기 화면 요청
	@RequestMapping("/new.hu")
	public String hugiwrite() {
		return "community/newhugi";
	}
	
	//후기 게시글 저장처리 요청
	@RequestMapping("/insert.hu")
	public String insert(HugiVO vo, MultipartFile file, HttpSession session ) {
		//로그인한 사용자의 userid를 담는다.
	    vo.setUserid(((MainVO)session.getAttribute("info_login")).getUserid());
		vo.setWriter(((MainVO)session.getAttribute("info_login")).getWriter());
		//첨부파일 저장처리
		if(file.getSize()>0) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.upload("hugi", file, session));
		}
		service.insert(vo);
		return"redirect:list.hu";
	}
	
	//삭제 처리요청
	@RequestMapping("/delete.hu")
	public String delete(int id) {
		service.delete(id);
		return"redirect:list.hu";
	}
	
	//수정화면 요청
	@RequestMapping("/modify.hu")
	public String modify(Model model, String id) {
		model.addAttribute("vo", service.detail(id));
		return"community/hugimodify";
	}
	
	//후기 게시글 수정 처리요청
	@RequestMapping("/update.hu")
	public String update(HugiVO vo, MultipartFile file, HttpSession session, String attach ) {
		//원래 첨부된 파일이 없는데 변경하면서 첨부하는 경우
		//원래 DB에 저장되어 있던 첨부파일정보를 조회해 온다.
		HugiVO hugi = service.detail(vo.getId());
		
		String uuid = hugi.getFilepath();
		uuid = session.getServletContext().getRealPath("resources") + uuid;
		
		if(file.getSize()> 0) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.upload("hugi", file, session));
			//원래 첨부된 파일이 있었고 그 파일을 다른 파일로 변경해 첨부하는 경우
			//원래 첨부된 파일은 삭제
			File f = new File(uuid);
			if(f.exists()) f.delete();
		}else {
			//원래 첨부된 파일이 있었는데 첨부된 파일을 삭제하는 경우
			if(attach.equals("y")) {
				File f = new File(uuid);
				if(f.exists()) f.delete();
			}else {
				//원래 첨부된 파일이 있고 그대로 사용하는 경우
				vo.setFilename(hugi.getFilename());
				vo.setFilepath(hugi.getFilepath());
			}
		}
		service.update(vo);
		return"redirect:detail.hu?id=" + vo.getId();
	}
	
	//첨부파일 다운로드 요청
	@ResponseBody @RequestMapping("/download.hu")
	public File download(String id, HttpSession session, HttpServletResponse response) {
		HugiVO vo = service.detail(id);
		return common.download(vo.getFilepath(), vo.getFilename(), session, response);
	}
	
	// 댓글 저장 처리 요청
	@ResponseBody
	@RequestMapping("/hugi/comment/insert")
	public boolean comment_insert(int pid, String content, HttpSession session) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("content", content);
		map.put("userid", ((MainVO) session.getAttribute("info_login")).getUserid());
		return service.comment_insert(map);
	}

	// 댓글이 보여지는 처리
	@RequestMapping("/hugi/comment/{pid}")
	public String comment_list(@PathVariable int pid, Model model) {
		model.addAttribute("list", service.comment_list(pid));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "hugi/comment/list";
	}

	// 댓글 수정처리
	@ResponseBody
	@RequestMapping(value = "/hugi/comment/update", produces = "application/text; charset=utf-8")
	public String comment_update(@RequestBody CommentVO vo) {
		return service.comment_update(vo) ? "성공" : "실패";
	}

	// 댓글 삭제처리
	@ResponseBody
	@RequestMapping("/hugi/comment/delete/{id}")
	public void comment_delete(@PathVariable int id) {
		service.comment_delete(id);
	}
}
