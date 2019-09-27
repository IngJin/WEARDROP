package notice;

import java.util.HashMap;
import java.util.List;

public interface NoticeService {
	//C:create R:read U:update D:delete
	boolean insert(NoticeVO vo);
	List<NoticeVO> list();
	List<NoticeVO> list(HashMap<String, String> map);
	NoticePage list(NoticePage vo);
	NoticeVO detail(int id);
	boolean update(NoticeVO vo);
	void read(int id);
	boolean delete(int id);
	List<NoticeVO> and_list();

	
}
