package com.hanul.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;



import main.KakaoAPI;
import main.MainEmailService;
import main.MainEmailServiceImpl;
import main.MainServiceImpl;
import main.MainVO;
import main.MirrorVO;


@Controller
@SessionAttributes("category")
public class MainController {
	@Autowired
	private MainServiceImpl service;
	@Autowired
	private MainEmailServiceImpl emailService;
	@Autowired
	private KakaoAPI kakao;

/*	 NaverLoginBO 
	private NaverLoginBO naverLoginBO;
	private String apiResult = null;

	@Autowired
	private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
		this.naverLoginBO = naverLoginBO;
	}*/


	// iot app 거울 사용 여부 판단
	@ResponseBody
	@RequestMapping(value = "/iot_app_usermirror", produces = "application/json; charset=utf-8")
	public JSONObject iot_app_usring(String userid) {
		JSONObject result = new JSONObject();
		MirrorVO check = service.iot_userid_find(userid);  // 아이디값 조회해서
		if(check == null) { // 디비에 없으면
			service.iot_sign(userid); // 만들고
			check = service.iot_userid_find(userid); // 다시 조회해서
			result.put("message", check); // 앱으로 보냄			
		} else { // 디비에 있으면
			result.put("message", check); // 앱으로 보냄			
		}
		return result;
	}
		
		// iot app 거울 커스터마이징(시간)
		@ResponseBody
		@RequestMapping(value = "/iot_app_usertime", produces = "application/json; charset=utf-8")
		public JSONObject iot_app_time(String userid, String app_time) {	
			JSONObject result = new JSONObject();
			if(app_time.equals("0")) {
				HashMap<String, String> map = new HashMap<String, String>();
				String time = "1";
				map.put("userid", userid);			
				map.put("time", time);
				boolean update_check = service.iot_time_update(map);
				MirrorVO check = service.iot_userid_find(userid);
				result.put("message", "시간 on");
			} else if(app_time.equals("1")) {
				HashMap<String, String> map = new HashMap<String, String>();
				String time = "0";
				map.put("userid", userid);
				map.put("time", time);
				boolean update_check = service.iot_time_update(map);
				MirrorVO check = service.iot_userid_find(userid);
				result.put("message", "시간 off");
			}
			return result;
		}
		
		// iot app 거울 커스터마이징(날씨)
		@ResponseBody
		@RequestMapping(value = "/iot_app_userweather", produces = "application/json; charset=utf-8")
		public JSONObject iot_app_weather(String userid, String app_weather) {	
			JSONObject result = new JSONObject();
			if(app_weather.equals("0")) {
				HashMap<String, String> map = new HashMap<String, String>();
				String weather = "1";
				map.put("userid", userid);			
				map.put("weather", weather);
				boolean update_check = service.iot_weather_update(map);
				MirrorVO check = service.iot_userid_find(userid);
				result.put("message", "날씨 on");
			} else if(app_weather.equals("1")) {
				HashMap<String, String> map = new HashMap<String, String>();
				String weather = "0";
				map.put("userid", userid);
				map.put("weather", weather);
				boolean update_check = service.iot_weather_update(map);
				MirrorVO check = service.iot_userid_find(userid);
				result.put("message", "날씨 off");
			}
			return result;
		}	
		
		// iot 거울 사용 중지
		@ResponseBody
		@RequestMapping(value = "/iot_app_delete", produces = "application/json; charset=utf-8")
		public JSONObject iot_app_delete(String userid) {	
			JSONObject result = new JSONObject();
			service.iot_delete(userid);
			result.put("message", "스마트미러 사용을 중지하셨습니다.");
			return result;
		}
	
	// 메인 페이지 호출
	@RequestMapping(value = { "/", "/index" })
	public String home(Locale locale, Model model) {
		model.addAttribute("category", "");
		return "home";
	}

	// 약관 페이지 호출
	@RequestMapping("/terms.ho")
	public String terms() {
		return "terms";
	}

	// 마이 페이지 호출
	@RequestMapping("/mypage.ho")
	public String mypage() {
		return "main/mypage";
	}

	// 아이디 찾기 페이지 호출
	@RequestMapping("/userid_find.ho")
	public String userid_find() {
		return "userid_find";
	}

	// 비밀번호 찾기 페이지 호출
	@RequestMapping("/userpw_find.ho")
	public String userpw_find() {
		return "userpw_find";
	}

	// 로그인 페이지 호출
	@RequestMapping("/login.ho")
	public String login(Model model, HttpSession session) {
	//	String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		//System.out.println("네이버:" + naverAuthUrl);
		//model.addAttribute("url", naverAuthUrl);
		return "main/login";
	}

	// 로그아웃 처리 요청
	@ResponseBody
	@RequestMapping("/logout_log")
	public void logout(HttpSession session) {
		// 세션에 저장한 회원정보 삭제
		kakao.kakaoLogout((String) session.getAttribute("access_Token"));
		session.removeAttribute("access_Token");
		session.removeAttribute("info_login");
		session.removeAttribute("password_mod");
	}

	// 마이페이지 비번체크
	@ResponseBody
	@RequestMapping(value = "/mypage_check", produces = "text/html; charset=utf-8")
	public String mypage_password(HttpSession session, String userpw) {
		MainVO vo = (MainVO) session.getAttribute("info_login");
		String pw_check = vo.getUserpw().toString();
		StringBuffer sb = new StringBuffer("<script type='text/javascript'>");
		if (userpw.equals(pw_check)) {
			session.setAttribute("password_mod", "Y");
			sb.append("location='mypage.ho'");
		} else {
			sb.append("alert('비밀번호를 잘못 입력하셨습니다.'); history.go(-1);");
		}
		sb.append("</script>");
		return sb.toString();
	}

	// 로그인 처리 요청
	@ResponseBody
	@RequestMapping("/login_log")
	public MainVO login(String userid, String userpw, HttpSession session) {
		// DB에서 입력한 아이디, 비번과 일치하는 회원정보 조회
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("userpw", userpw);
		MainVO vo = service.login(map);
		if (vo != null) {
			session.setAttribute("info_login", vo);
		}
		return vo;
	}

	// 회원가입 처리 요청
	@ResponseBody
	@RequestMapping(value = "/join_log", produces = "text/html; charset=utf-8")
	public String join(MainVO vo) {
		// 화면에서 입력한 정보를 DB에 저장한 후 홈으로 이동
		StringBuffer sb = new StringBuffer("<script type='text/javascript'>");
		if (service.insert(vo)) {
			sb.append("alert('회원가입을 축하합니다.'); location='index'");
		} else {
			sb.append("alert('회원가입 실패 ㅠㅠ'); history.go(-1);");
		}
		sb.append("</script>");
		return sb.toString();
	}

	// 회원정보 수정
	@ResponseBody
	@RequestMapping(value = "/mod_info", produces = "text/html; charset=utf-8")
	public String modified(MainVO vo) {
		// 화면에서 입력한 정보를 DB에 저장한 후 홈으로 이동
		StringBuffer sb = new StringBuffer("<script type='text/javascript'>");
		if (service.update(vo)) {
			sb.append("alert('회원정보를 수정하셨습니다.'); location='index'");
		} else {
			sb.append("alert('회원정보를 수정하지 못했습니다.'); history.go(-1);");
		}
		sb.append("</script>");
		return sb.toString();
	}

	// 회원정보 삭제 처리 요청
	@RequestMapping("/delete.ho")
	public String delete(String userid, HttpSession session) {
		service.delete(userid);
		kakao.kakaoLogout((String) session.getAttribute("access_Token"));
		session.removeAttribute("access_Token");
		session.removeAttribute("info_login");
		return "redirect:index";
	}

	// 아이디 중복확인 요청
	@ResponseBody
	@RequestMapping("/id_check_main")
	public String id_check(String userid) {
		// DB에 입력된 아이디가 있는지의 여부 판단
		return String.valueOf(service.id_check(userid));
	}

	// 이메일 중복확인 요청
	@ResponseBody
	@RequestMapping("/email_check_main")
	public String email_check(String email) {
		// DB에 입력된 아이디가 있는지의 여부 판단
		return String.valueOf(service.email_check(email));
	}

	// 아이디 찾기
	@ResponseBody
	@RequestMapping(value = "/userid_find")
	public MainVO userid_find(String email) {
		// DB에 입력된 이메일이 있는지의 여부 판단
		MainVO check = service.userid_find(email);
		return check;
	}

	// 비밀번호 찾기
	@ResponseBody
	@RequestMapping(value = "/userpw_find")
	public MainVO userpw_find(String userid, String email) {
		// DB에 입력된 이메일이 있는지의 여부 판단
		MainVO vo = new MainVO();
		vo.setUserid(userid);
		vo.setEmail(email);
		MainVO check = service.userpw_find(vo);
		if (check != null) {
			emailService.emailSend(check);
		}
		return check;
	}

	// 카카오톡 로그인
	@RequestMapping(value = "/kakaologin")
	public String kakaologin(@RequestParam("code") String code, HttpSession session) {
		String access_Token = kakao.getAccessToken(code);
		HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);
		session.setAttribute("password_mod", "Y");
		// 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
		if (userInfo.get("email") != null) {
			String email = userInfo.get("email").toString();
			String userid = userInfo.get("userid").toString();
			String writer = userInfo.get("writer").toString();

			// DB에 해당 정보가 존재할 경우
			String check = String.valueOf(service.email_check(email));
			if (check == "false") {
				// 이메일 정보를 매칭하여 일치한 걸 가져온다.
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("email", email);
				MainVO vo = service.email_login(map);
				session.setAttribute("info_login", vo);
				session.setAttribute("access_Token", access_Token);
				// 없으면 해당 정보를 VO에 넣어서 바로 세션 처리.
			} else {
				MainVO vo = new MainVO();
				vo.setEmail(email);
				vo.setUserid(userid);
				vo.setWriter(writer);

				String userpw = randomValue(10);
				vo.setUserpw(userpw);

				service.insert(vo);
				session.setAttribute("info_login", vo);
				session.setAttribute("access_Token", access_Token);
			}
		}
		return "home";
	}

	// 랜덤 비밀번호 생성기(10자리)
	public static String randomValue(int cnt) {
		StringBuffer strPwd = new StringBuffer();
		char str[] = new char[1];
		for (int i = 0; i < cnt; i++) {
			str[0] = (char) ((Math.random() * 94) + 33);
			strPwd.append(str);
		}
		return strPwd.toString();
	}

	/*// 네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException, org.json.simple.parser.ParseException {
		session.setAttribute("password_mod", "Y");
		OAuth2AccessToken oauthToken;
		oauthToken = naverLoginBO.getAccessToken(session, code, state);
		// 1. 로그인 사용자 정보를 읽어온다.
		apiResult = naverLoginBO.getUserProfile(oauthToken); // String형식의 json데이터
		*//**
		 * apiResult json 구조 {"resultcode":"00", "message":"success",
		 * "response":{"id":"33666449","nickname":"shinn****","age":"20-29","gender":"M","email":"shinn0608@naver.com","name":"\uc2e0\ubc94\ud638"}}
		 **//*
		// 2. String형식인 apiResult를 json형태로 바꿈
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		// 3. 데이터 파싱
		// Top레벨 단계 _response 파싱
		JSONObject response_obj = (JSONObject) jsonObj.get("response");
		// response의 nickname값 파싱
		String userid = response_obj.get("id").toString();
		String writer = response_obj.get("nickname").toString();
		String email = response_obj.get("email").toString();

		// 4.파싱 닉네임 세션으로 저장
		// DB에 해당 정보가 존재할 경우
		String check = String.valueOf(service.email_check(email));
		if (check == "false") {
			// 이메일 정보를 매칭하여 일치한 걸 가져온다.
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("email", email);
			MainVO vo = service.email_login(map);
			session.setAttribute("info_login", vo);
			// 없으면 해당 정보를 VO에 넣어서 바로 세션 처리.
		} else {
			MainVO vo = new MainVO();
			vo.setEmail(email);
			vo.setUserid(userid);
			vo.setWriter(writer);

			String userpw = randomValue(10);
			vo.setUserpw(userpw);

			service.insert(vo);
			session.setAttribute("info_login", vo);
		}
		
		return "home";
	}
*/
}
