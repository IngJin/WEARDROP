package common;

import java.io.File;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

public interface CommonService {
	/*category: �����ġ�� �������� ������ ī�װ�, file: ���� ������ ���� �̸�, session: ���� ������ �������� ��ġ*/
	String upload(String category, MultipartFile file, HttpSession session); 
	
	//�ٿ�ε��� ó��
	File download(String filepath, String filename, HttpSession session, HttpServletResponse response);
}