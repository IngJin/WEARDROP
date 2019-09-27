package notice;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("NoticeService")
public class NoticeServiceImpl implements NoticeService {
	// @Autowired private NoticeDAO dao;
	//NoticeDAO d1 = new NoticeDAO();
	//NoticeService d2 = new NoticeDAO();
	
	
	@Resource(name="dao") private NoticeService dao;
	
	@Override
	public boolean insert(NoticeVO vo) {
		
		return dao.insert(vo);
	}

	@Override
	public List<NoticeVO> list() {		
		return dao.list();
	}

	@Override
	public NoticeVO detail(int id) {
		
		return dao.detail(id);
	}

	@Override
	public boolean update(NoticeVO vo) {
		return dao.update(vo);
	}

	@Override
	public boolean delete(int id) {
		
		return dao.delete(id);
	}

	@Override
	public void read(int id) {
		dao.read(id);
		
	}

	@Override
	public List<NoticeVO> list(HashMap<String, String> map) {
		
		return dao.list(map);
	}

	@Override
	public NoticePage list(NoticePage vo) {
		
		return dao.list(vo);
	}

	@Override
	public List<NoticeVO> and_list() {
		return dao.and_list();
	}
	
}
