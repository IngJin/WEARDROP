package main;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

@Service
public class MainEmailServiceImpl implements MainEmailService {

	@Override
	public void emailSend(MainVO check) {
		// 1. 기본 이메일
				// simpleSend(email, name);
				
				// 2. 첨부파일 있는 이메일
				// attachSend(email, name, session);
				
				// 3. Html 형식의 이메일
				HtmlSend(check);
			}
			
			private void HtmlSend(MainVO check) {
				HtmlEmail mail = new HtmlEmail();
				
				mail.setHostName("smtp.daum.net");
				mail.setCharset("utf-8");
				
				mail.setAuthentication("cdkgw11", "qmfforghf!@3");
				mail.setSSLOnConnect(true);
				
				try {
					// 보내는이의 정보
					mail.setFrom("cdkgw11@hanmail.net", "WEARDROP 관리자");
					// 받는이의 정보
					mail.addTo(check.getEmail(), check.getWriter());
					
					// 메일 제목, 내용 작성
					mail.setSubject("WEARDROP에서 요청하신 비밀번호입니다.");
					
					StringBuffer content = new StringBuffer("<html>");
					content.append("<body>");							
					content.append("<div>요청하신 비밀번호는 ");
					content.append("<b style='font-size:1.2em;'>"+ check.getUserpw() + "</b> 입니다. 감사합니다.</div>");			
					content.append("</body>");			
					content.append("</html>");
					
					mail.setHtmlMsg(content.toString());		
					// 메일 보내기	
					mail.send();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
}
