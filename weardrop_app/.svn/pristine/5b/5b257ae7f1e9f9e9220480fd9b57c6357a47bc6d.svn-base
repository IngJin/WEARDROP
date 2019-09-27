package sell;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sell.SellVO;
import sell.SellPage;

@Service
public class SellServiceImpl implements SellService {
	@Autowired SellDAO dao;
//	@Resource(name="dao") private SellService dao;
	
	@Override
	public boolean insert(SellVO vo) {
		return dao.insert(vo);
	}

	// 페이징처리와 함께 메인화면출력
	@Override
	public SellPage list(SellPage vo) {
		return dao.list(vo);
	}
	// 상세화면
	@Override
	public SellVO detail(String id) {
		return dao.detail(id);
	}

	@Override
	public boolean update(SellVO vo) {
		return dao.update(vo);
	}

	@Override
	public boolean delete(String id) {
		return dao.delete(id);
	}
	
	@Override
		public void read(String id) {
			dao.read(id);
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
	
	
	@Override
	public List<SellVO> and_list() {
		return dao.and_list();
	}

	@Override
	public List<SellVO> and_list2() {
		return dao.and_list2();
	}
	
	/*@Override
	public List<CommunityVO> and_list3() {
		return dao.and_list3();
	}*/



	@Override
	public boolean and_delete(HashMap map) {
		return dao.and_delete(map);
	}

	@Override
	public boolean and_update(SellVO vo) {
		return dao.and_update(vo);
	}

	@Override
	public boolean and_insert(SellVO vo) {
		return dao.and_insert(vo);
	}

	@Override
	public List<SellVO> andcomment_list(int pid) {
		System.out.println("서비스" + pid);
		return dao.andcomment_list(pid);
	}
}
