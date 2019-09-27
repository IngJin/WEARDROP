package hugi;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import community.CommentVO;
import community.CommunityVO;
@Service
public class HugiServiceImpl implements HugiService {
	@Autowired private HugiService dao;
	
	@Override
	public boolean insert(HugiVO vo) {
		return dao.insert(vo);
	}

	@Override
	public List<HugiVO> list() {
		return dao.list();
	}

	@Override
	public HugiVO detail(String id) {
		return dao.detail(id);
	}

	@Override
	public boolean update(HugiVO vo) {
		return dao.update(vo);
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}

	@Override
	public void read(String id) {
		dao.read(id);
	}

	@Override
	public List<HugiVO> list(HashMap<String, String> map) {
		return dao.list(map);
	}

	@Override
	public List<HugiVO> and_list3() {
		return dao.and_list3();
	}

	@Override
	public boolean and_delete(HashMap map) {
		return dao.and_delete(map);
	}

	@Override
	public boolean and_insert(HugiVO vo) {
		return dao.and_insert(vo);
	}

	@Override
	public boolean and_update(HugiVO vo) {
		return dao.and_update(vo);
	}

	@Override
	public boolean comment_insert(HashMap<String, Object> map) {
		return dao.comment_insert(map);
	}

	@Override
	public List<CommentVO> comment_list(int pid) {
		return dao.comment_list(pid);
	}

	@Override
	public boolean comment_update(CommentVO vo) {
		return dao.comment_update(vo);
	}

	@Override
	public boolean comment_delete(int id) {
		return dao.comment_delete(id);
	}

}
