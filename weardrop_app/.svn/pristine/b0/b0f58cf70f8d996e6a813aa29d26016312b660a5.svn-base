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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import common.CommonService;
import main.MainVO;
import sell.CommentVO;
import sell.SellPage;
import sell.SellServiceImpl;
import sell.SellVO;

@Controller @SessionAttributes("category")
public class SellController {
	@Autowired private SellServiceImpl service;
	@Autowired private CommonService common;
	@Autowired private SellPage page;
	
	//신규글저장처리 요청
	@RequestMapping("/insert.se")
	public String insert(SellVO vo, MultipartFile file, HttpSession session) {
		//로그인한 사용자의 userid를 담는다.
		// 댓글용
		vo.setUserid( ((MainVO)session.getAttribute("info_login"))
				.getUserid() );
		// 작성자명용
		vo.setWriter( ((MainVO)session.getAttribute("info_login"))
				.getWriter() );

			if(file.getSize()>0) {
				vo.setFilename(file.getOriginalFilename() );
				vo.setFilepath(common.upload("sell",file,session));
			}		
		
		service.insert(vo);
		return "redirect:list.se";
	}
	
	// 신규 공지글 작성화면 요청
	@RequestMapping("/new.se")
	public String sell() {
		return "sell/new";
	}
	
	// 중고장터 목록 화면 요청
	@RequestMapping("/list.se")
	public String list(@RequestParam(defaultValue="1") int curPage, Model model, String search, String keyword, @RequestParam(defaultValue="1") int code) {
//		model.addAttribute("category", "se");
//		sell_page.setCurPage(code==2 ? curPage : 1 );
//		sell_page.setSearch(search);
//		sell_page.setKeyword(keyword);
////		System.out.println(page);
//		model.addAttribute("sell_page", service.list(sell_page));
//		
//		buy_page.setCurPage(code==1 ? curPage : 1);
//		buy_page.setSearch(search);
//		buy_page.setKeyword(keyword);
//		model.addAttribute("buy_page", service.list(buy_page));
//	
	//model.addAttribute( "list", service.list() );
		
//		page.setCurPage(curPage);
//		page.setSearch(search);
//		page.setKeyword(keyword);
//		model.addAttribute("page", service.list(page));
		return "sell/list";
		
	}
	
	// 중고장터 목록 화면 요청
		@RequestMapping("/list.ajax")
		public String list( @RequestParam(defaultValue="1") int code, @RequestParam(defaultValue="1") int curPage, Model model, String search, String keyword) {
			page.setCurPage(curPage);
			page.setSearch(search);
			page.setKeyword(keyword);
			page.setCode(code);
			model.addAttribute("page", service.list(page));
			return "sell/tabs/list";
			
		}
		
	// 중고장터 상세화면 요청
	@RequestMapping("/detail.se")
	public String detail(Model model, String id, int code, 
			@RequestParam(defaultValue="0") int read) {
		//선택한 방명록 글의 정보를 DB에서 조회해온 후
		//상세화면에 출력할 수 있도록 Model 에 담는다.
		if(read==1) service.read(id);
		model.addAttribute("vo",service.detail(id) );
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		model.addAttribute("page", page);
		return "sell/detail";
	}	
	
	// 첨부파일 다운로드 요청: 파일자체가 응답이 되도록한다.
	@ResponseBody @RequestMapping("/download.se")
	public File download(String id, HttpSession session,
				HttpServletResponse response) {
		// 해당 글에 대해 첨부된 파일정보를 조회한 후
		// 해당 파일을 다운로드 한다
		SellVO vo = service.detail(id);
		return common.download(vo.getFilepath(), vo.getFilename(), session, response);
	}
	
	// 중고장터 글 수정화면 요청
	@RequestMapping("/modify.se")
	public String modify(String id, Model model) {
		//DB에서 해당 장터 정보를 조회해온 후
		//수정화면에 출력하도록 Model에 담는다.
		model.addAttribute("vo", service.detail(id));
		return "sell/modify";
	}
	// 중고장터 글 수정처리 요청
	@RequestMapping("/update.se")
	public String update(SellVO vo, MultipartFile file, HttpSession session, String attach) {
		SellVO sell = service.detail(vo.getId());
		String uuid = sell.getFilepath();
		uuid = session.getServletContext().getRealPath("resources") + uuid;
		
		if(file.getSize() >0 ) {
			//원래 첨부된 파일이 없는데 변경하면서 첨부하는 경우
			vo.setFilename(file.getOriginalFilename() );
			vo.setFilepath(common.upload("sell", file, session));
			File f = new File(uuid);
			if(f.exists() ) f.delete();
		}else {
		//원래 첨부된 파일이 있었는데 첨부된 파일을 삭제하는 경우 
			if(attach.equals("y") ) {
				File f = new File(uuid);
				if(f.exists() ) f.delete();
			}else {
			//원래 첨부된 파일이 있고 그대로 사용하는 경우
				vo.setFilename(sell.getFilename());
				vo.setFilepath(sell.getFilepath());
			}
		}
		service.update(vo);
		return "redirect:detail.se?code="+vo.getCode()+"&id=" + vo.getId();
	}
	// 중고장터 글 삭제처리 요청
	@RequestMapping("/delete.se")
	public String delete(String id, HttpSession session) {
		//첨부된 파일이 있다면 물리적 공간에서 해당 파일을 삭제
		File f = new File (
			session.getServletContext().getRealPath(
			"resources" + service.detail(id).getFilepath())	);
		if( f.exists() ) f.delete();
		
		//해당 공지글을 DB에서 삭제한 후
		//목록화면으로 연결
		service.delete(id);
		return "redirect:list.se";
	}
		// 중고장터 댓글 저장 처리 요청
	@ResponseBody @RequestMapping("/sell/comment/insert")
	public boolean comment_insert(int pid, String content,
					HttpSession session) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("content", content);
		map.put("userid", ((MainVO)session.getAttribute("info_login")).getUserid() );
		return service.comment_insert(map);
	}
	
		// 중고장터 댓글 조회 요청(pid: 부모id를 통하여 뿌림)
	@RequestMapping("/sell/comment/{pid}")
	public String comment_list(@PathVariable int pid, Model model) {
		model.addAttribute("list", service.comment_list(pid));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "sell/comment/list";
	}
	
		// 중고장터 댓글 변경처리 요청
	@ResponseBody @RequestMapping(value="/sell/comment/update",
					produces="application/text; charset=utf-8")
	public String comment_update(@RequestBody CommentVO vo) {
		return service.comment_update(vo) ? "성공" : "실패";
	}
	
	// 중고장터 댓글 삭제처리 요청
	@ResponseBody @RequestMapping("/sell/comment/delete/{id}")
	public void comment_delete(@PathVariable int id) {
		 service.comment_delete(id);
	}
	

}
