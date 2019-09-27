package com.hanul.test;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import main.MainEmailServiceImpl;
import main.MainServiceImpl;
import main.MainVO;

@Controller
@SessionAttributes("category")
public class AndroidController {
	@Autowired
	private MainServiceImpl service;
	@Autowired
	private MainEmailServiceImpl emailService;
	/*
	 * @RequestMapping("/detail.bo") public String detail(Model model, int
	 * id, @RequestParam(defaultValue = "0") int read) { // 선택한 방명록 글의 정보를 DB에서 조회해온
	 * 후 // 상세화면에 출력할 수 있도록 Model 에 담는다. if (read == 1) service.read(id);
	 * model.addAttribute("vo", service.detail(id)); model.addAttribute("crlf",
	 * "\r\n"); model.addAttribute("lf", "\n"); model.addAttribute("page", page);
	 * return "board/detail"; }
	 */

	@ResponseBody
	@RequestMapping(value = "/android_userid_find", produces = "application/json; charset=utf-8")
	public JSONObject userid_find(String email) {
		JSONObject result = new JSONObject();
		// DB에 입력된 아이디가 있는지의 여부 판단
		MainVO check = service.userid_find(email);
		if (check == null) {
			result.put("message", "이메일을 찾을수 없습니다. 회원가입을 해주세요.");
		} else {
			result.put("message", "당신의 아이디는 " + check.getUserid() + "입니다.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/android_userpw_find", produces = "application/json; charset=utf-8")
	public JSONObject userpw_find(String userid, String email) {
		JSONObject result = new JSONObject();
		// DB에 입력된 아이디가 있는지의 여부 판단
		MainVO vo = new MainVO();
		vo.setUserid(userid);
		vo.setEmail(email);
		MainVO check = service.userpw_find(vo);
		if (check == null) {
			result.put("message", "비밀번호를 찾을 수 없습니다. 값을 잘못 입력하셨는지 확인해주세요.");
		} else {
			emailService.emailSend(check);
			result.put("message", "비밀번호를 입력하신 이메일 주소로 전송하였습니다.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/id_check_android", produces = "application/json; charset=utf-8")
	public JSONObject id_check(String userid) {
		JSONObject result = new JSONObject();
		// DB에 입력된 아이디가 있는지의 여부 판단
		String check = String.valueOf(service.id_check(userid));
		if (check == "false") {
			result.put("message", "아이디가 중복됩니다.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/email_check_android", produces = "application/json; charset=utf-8")
	public JSONObject email_check(String email) {
		JSONObject result = new JSONObject();
		// DB에 입력된 이메일이 있는지의 여부 판단
		String check = String.valueOf(service.email_check(email));
		if (check == "false") {
			result.put("message", "이미 사용중인 이메일입니다.");
		} else {
			result.put("message", "이메일 사용이 가능합니다.");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/login_android", produces = "application/json; charset=utf-8")
	public JSONObject login(String userid, String userpw, HttpSession session) {
		// DB에서 입력한 아이디, 비번과 일치하는 회원정보 조회
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		MainVO vo = service.login(map);
		JSONObject result = new JSONObject();
		if (vo != null) {
			session.setAttribute("info_login", vo);
			result.put("message", vo);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/email_login_android", produces = "application/json; charset=utf-8")
	public JSONObject email_login(String email, HttpSession session) {
		// DB에서 입력한 아이디, 비번과 일치하는 회원정보 조회
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", email);
		MainVO vo = service.email_login(map);
		JSONObject result = new JSONObject();
		if (vo != null) {
			session.setAttribute("info_login", vo);
			result.put("message", vo);
		}

		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/sign_android", produces = "application/json; charset=utf-8")
	public JSONObject join(MainVO vo) {
		JSONObject result = new JSONObject();
		if (service.insert(vo)) {
			result.put("message", "회원가입에 성공하셨습니다.");
		}
		return result;
	}

	// 회원정보 수정
	@ResponseBody
	@RequestMapping(value = "/mod_android", produces = "application/json; charset=utf-8")
	public JSONObject modified(MainVO vo) {
		// 화면에서 입력한 정보를 DB에 저장한 후 홈으로 이동
		JSONObject result = new JSONObject();
		if (service.update(vo)) {
			result.put("message", "회원정보 수정에 성공하셨습니다.");
		} else {
			result.put("message", "회원정보 수정에 실패했습니다.");
		}
		return result;
	}

	// 회원정보 삭제 처리 요청
	@ResponseBody
	@RequestMapping(value = "/delete_android", produces = "application/json; charset=utf-8")
	public JSONObject delete(String userid) {
		JSONObject result = new JSONObject();
		if (service.delete(userid)) {
			result.put("message", "회원 탈퇴되셨습니다. 이용해주셔서 감사합니다.");
		} else {
			result.put("message", "회원 탈퇴에 실패했습니다.");
		}
		return result;

	}
}