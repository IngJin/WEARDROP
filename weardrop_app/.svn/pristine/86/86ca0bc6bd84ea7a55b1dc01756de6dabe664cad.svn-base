package common;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
public class CommonServiceImpl implements CommonService {
	@Override
	public String upload(String category, MultipartFile file, HttpSession session) {
		// ���ε��ϰ��� �ϴ� ������ ��� ���ε� �� ������ (��������ġ)�� �����Ѵ�.
		// upload/notice/abc.txt or upload/board/abc.txt �̸� �� �����ȿ� ������ �ʹ� ��������.
		// ����, ���ó�¥�� �������� ������ ������ �� �ֵ��� �ɰ���� �Ѵ�. upload/notice/2019/07/02/abc.txt or upload/board/2019/07/02/abc.txt
		
//		String resources = session.getServletContext().getRealPath("resources");		//�������� ��� ã�� (Study_Spring/.../iot/resources)
//		System.out.println("resources : " + resources);
//		String resources2 = resources.replace("D:\\Study_Web\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\weardrop_app\\resources", "");
		
		String resources = "D:\\TomcatServer\\webapps\\weardrop\\resources";
		// TODO: 월요일의 설화가 해야할 일
		String upload = resources + File.separator + "upload"; 							//Study_Spring/.../iot/resources/upload
		System.out.println("upload : " + upload);

		String folder = makeFolder(category, upload); 		//���� ���� : Study_Spring/.../iot/resources/upload/notice/2019/07/03
		System.out.println("folder : " + folder);

		//���ôٹ��� ������� ���ε�� ���� �ߺ��� ���� ���� ������ ���̵� ���ϸ� �ο�
		//UUID(��������ĺ��� : Universally Unique ID) 
		String uuid = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();		//�����̸��� ���̵� �����ϱ� ���� _ ����
		System.out.println("uuid : " + uuid);

		//������ �����ϴ� ó��: ������ �Ű��ִ� ���·�
		try {
		file.transferTo(new File(folder, uuid));
		System.out.println("file : " + file);

		}catch(Exception e) {
			
		}
		return folder.substring(resources.length()) + File.separator + uuid;	//�����̸� ��ȯ
	}
	private String makeFolder(String category, String upload) {
		//5���� ������ ����� ���� ��ü(upload/notice/2019/07/02/abc.txt)
		StringBuilder sb = new StringBuilder(upload);	//.../upload
		sb.append(File.separator + category);			//.../upload/notice
		
		//���� �ý����� ��¥������ �ľ��ϱ�
		Date now = new Date();
		sb.append(File.separator + new SimpleDateFormat("yyyy").format(now));	//.../upload/notice/2019
		sb.append(File.separator + new SimpleDateFormat("MM").format(now));		//.../upload/notice/2019/07
		sb.append(File.separator + new SimpleDateFormat("dd").format(now)); 	////.../upload/notice/2019/07/03
		String folder = sb.toString();	
		
		//�ش� ������ ������ ������ �����ϴ� ó��
		File f = new File(folder);
		if(!f.exists()) f.mkdirs();
		
		return folder;	//������ �̸��� �˷��ֱ�
	}//makeFolder()
	@Override
	public File download(String filepath, String filename, HttpSession session, HttpServletResponse response) {
		// �ٿ�ε��� ���ϰ�ü ����
		File file = new File(session.getServletContext().getRealPath("resources") + filepath);
		// �ٿ�ε��� ������ ����Ÿ�԰���
		String mime = session.getServletContext().getMimeType(filename);
		if(mime == null) mime = "application/octet-stream";
		
		response.setContentType(mime);
		
		try {
		//÷������ �ٿ�ε��� �� �����̸��� �ѱ��̸����� �� �� �ֵ��� encoding
		//���� �̸��� ������ ���� ��� +�� �ƴ� �������� ó���� �� �ֵ��� ascii�� ġȯ(space�� ascii��: 20)	
		filename = URLEncoder.encode(filename, "utf-8").replaceAll("\\+", "%20");	
			
		//������ �ٿ�ε� ó�� (������ �������� ������ �ִ� ������ ctrl c�ؼ� ����� ���� PC�� ctrl v �ϴ� �Ͱ� ���� ó��)
		response.setHeader("content-disposition", "attachment; filename=" + filename);
		ServletOutputStream out = response.getOutputStream();
		FileCopyUtils.copy(new FileInputStream(file), out);
		out.flush();
		}catch(Exception e) {}
		return file;		//�������� ��ȯ
	}
}
