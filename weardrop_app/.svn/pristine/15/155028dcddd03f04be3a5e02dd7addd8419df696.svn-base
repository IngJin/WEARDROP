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
import community.CommunityPage;
import community.CommunityServiceImpl;
import community.CommunityVO;
import main.MainVO;

@Controller
public class commuityController {
	@Autowired
	private CommunityServiceImpl service;
	@Autowired
	private CommonService common;
	@Autowired
	CommunityPage page;

	// �����Խ��� ȭ�� ��û
	@RequestMapping("/list.com")
	public String list(Model model, String search, String keyword, @RequestParam(defaultValue = "1") int curPage) {
		page.setCurPage(curPage);
		page.setSearch(search);
		page.setKeyword(keyword);
		// System.out.println("keyword =========="+keyword);
		// System.out.println("curPage =========="+curPage);
		model.addAttribute("page", service.list(page));
		return "community/list";
	}

	// �Խñ��ۼ�ȭ�� ��û
	@RequestMapping("/new.com")
	public String write() {
		return "community/new";
	}

	// �ű԰Խñ� ����ó�� ��û
	@RequestMapping("/insert.com")
	public String insert(CommunityVO vo, MultipartFile file, HttpSession session) {
		// 로그인한 사용자의 userid를 담는다.
		System.out.println(vo.getWriter());
		System.out.println(vo.getUserid());
		vo.setUserid(((MainVO) session.getAttribute("info_login")).getUserid());
		vo.setWriter(((MainVO) session.getAttribute("info_login")).getWriter());
		// ÷������
		if (file.getSize() > 0) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.upload("community", file, session));
		}
		service.insert(vo);
		return "redirect:list.com";
	}

	// �Խñ� ��ȭ�� ��û
	@RequestMapping("/detail.com")
	public String detail(Model model, String id, @RequestParam(defaultValue = "0") int read) {
		if (read == 1) {
			service.read(id); // ��ȸ�� ���� ó��
		}
		model.addAttribute("vo", service.detail(id));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		model.addAttribute("page", page); // curpage ����
		return "community/detail";
	}

	// �Խñ� ����ȭ�� ��û(�����Խ���)
	@RequestMapping("/modify.com")
	public String modify(Model model, String id) {
		model.addAttribute("vo", service.detail(id));
		return "community/modify";
	}

	// �Խñ� �������� ó����û
	@RequestMapping("/update.com")
	public String update(CommunityVO vo, MultipartFile file, HttpSession session, String attach) {
		// ���� ÷�ε� ������ ���µ� �����ϸ鼭 ÷���ϴ� ���
		// ���� DB�� ����Ǿ� �ִ� ÷������������ ��ȸ�� �´�.
		CommunityVO commu = service.detail(vo.getId());

		String uuid = commu.getFilepath(); // ������ �Ѱ��� ������ ��ȯ
		uuid = session.getServletContext().getRealPath("resources") + uuid;

		if (file.getSize() > 0) {
			vo.setFilename(file.getOriginalFilename());
			vo.setFilepath(common.upload("community", file, session));
			// ���� ÷�ε� ������ �־��� �� ������ �ٸ� ���Ϸ� ������ ÷���ϴ� ���
			// ���� ÷�ε� ������ ����
			File f = new File(uuid);
			if (f.exists())
				f.delete();
		} else {
			// ���� ÷�ε� ������ �־��µ� ÷�ε� ������ �����ϴ� ���
			if (attach.equals("y")) {
				File f = new File(uuid);
				if (f.exists())
					f.delete();
			} else {
				// ���� ÷�ε� ������ �ְ� �״�� ����ϴ� ���
				vo.setFilename(commu.getFilename());
				vo.setFilepath(commu.getFilepath());
			}
		}
		service.update(vo);
		return "redirect:detail.com?id=" + vo.getId();
	}

	// �Խñ� ���� ó����û
	@RequestMapping("/delete.com")
	public String delete(int id) {
		service.delete(id);
		return "redirect:list.com";
	}

	// ÷������ �ٿ�ε� ��û
	@ResponseBody
	@RequestMapping("/download.com")
	public File download(String id, HttpSession session, HttpServletResponse response) {
		CommunityVO vo = service.detail(id);
		return common.download(vo.getFilepath(), vo.getFilename(), session, response);
	}

	// ���� ȭ�� ��û
	@RequestMapping("/map.com")
	public String map() {
		return "community/map";
	}

	// 댓글 저장 처리 요청
	@ResponseBody
	@RequestMapping("/community/comment/insert")
	public boolean comment_insert(int pid, String content, HttpSession session) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("content", content);
		map.put("userid", ((MainVO) session.getAttribute("info_login")).getUserid());
		return service.comment_insert(map);
	}

	// 댓글이 보여지는 처리
	@RequestMapping("/community/comment/{pid}")
	public String comment_list(@PathVariable int pid, Model model) {
		model.addAttribute("list", service.comment_list(pid));
		model.addAttribute("crlf", "\r\n");
		model.addAttribute("lf", "\n");
		return "community/comment/list";
	}

	// 댓글 수정처리
	@ResponseBody
	@RequestMapping(value = "/community/comment/update", produces = "application/text; charset=utf-8")
	public String comment_update(@RequestBody CommentVO vo) {
		return service.comment_update(vo) ? "성공" : "실패";
	}

	// 댓글 삭제처리
	@ResponseBody
	@RequestMapping("/community/comment/delete/{id}")
	public void comment_delete(@PathVariable int id) {
		service.comment_delete(id);
	}

}
