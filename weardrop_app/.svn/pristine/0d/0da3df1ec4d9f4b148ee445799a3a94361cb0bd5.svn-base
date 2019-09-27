package community;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("communitydao")
public class CommunityDAO implements CommunityService {
	@Autowired
	private SqlSession sql;

	@Override
	public boolean insert(CommunityVO vo) {
		return sql.insert("community.mapper.insert", vo) > 0 ? true : false;
	}

	@Override
	public List<CommunityVO> list() {
		return sql.selectList("community.mapper.list");
	}

	@Override
	public CommunityVO detail(String id) {
		return sql.selectOne("community.mapper.detail", id);
	}

	@Override
	public boolean update(CommunityVO vo) {
		return sql.update("community.mapper.update", vo) > 0 ? true : false;

	}

	@Override
	public boolean delete(int id) {
		return sql.delete("community.mapper.delete", id) > 0 ? true : false;
	}

	@Override
	public void read(String id) {
		sql.update("community.mapper.read", id);
	}

	@Override
	public void like(int id) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean comment_insert(HashMap<String, Object> map) {
		return sql.insert("community.mapper.comment_insert", map) >0 ? true : false;
	}

	@Override
	public List<CommentVO> comment_list(int pid) {
		return sql.selectList("community.mapper.comment_list",pid);
	}

	@Override
	public boolean comment_update(CommentVO vo) {
		return sql.update("community.mapper.comment_update", vo) > 0 ? true : false;
	}

	@Override
	public boolean comment_delete(int id) {
		return sql.delete("community.mapper.comment_delete", id) > 0 ? true : false;
	}

	@Override
	public List<CommunityVO> test() {
		return sql.selectList("community.mapper.list");
	}

	@Override // �ȵ���̵�
	public List<CommunityVO> and_list() {
		return sql.selectList("android.mapper.list");
	}

	@Override // �ȵ���̵�
	public List<CommunityVO> and_list2() {
		return sql.selectList("android.mapper.list2");
	}
	
/*	@Override
	public List<CommunityVO> and_list3() {
		return sql.selectList("android.mapper.list3");
	}*/

/*	@Override
	public boolean and_insert(HashMap map) {
		System.out.println("DAO ����");
		return sql.insert("android.mapper.insert", map) > 0 ? true : false;
	}*/

	@Override
	public CommunityPage list(CommunityPage vo) {
		// �� ��� �� ��ȸ - ������������ totalList �ʵ忡 ���
		vo.setTotalList((Integer) sql.selectOne("community.mapper.total", vo));
		// �� ��ϼ��� ���� �� ������, �� ��, ���������������� ����/�� ��, ����/�� ��������ȣ ����
		List<CommunityVO> list = sql.selectList("community.mapper.list", vo);
		vo.setList(list);
		return vo;
	}

	@Override
	public boolean and_delete(HashMap map) {
		return sql.delete("android.mapper.delete", map) > 0 ? true : false;
	}

	@Override
	public boolean and_update(CommunityVO vo) {
		return sql.update("android.mapper.update", vo) > 0 ? true : false;
	}

	@Override
	public boolean and_insert(CommunityVO vo) {
		return sql.insert("android.mapper.insert", vo) > 0 ? true : false;
	}

	@Override
	public List<CommentVO> andcomment_list(int pid) {
		return sql.selectList("android.mapper.comment_list");
	}

	@Override
	public boolean filepathupdate() {
		return sql.update("android.mapper.filepathupdate")>0?true:false;
	}

	


}
