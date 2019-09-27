package hugi;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import community.CommentVO;
import community.CommunityVO;

@Repository
public class HugiDAO implements HugiService {
	@Autowired private SqlSession sql;
	
	@Override
	public boolean insert(HugiVO vo) {
		return sql.insert("hugi.mapper.insert", vo) > 0 ? true: false;
	}

	@Override
	public List<HugiVO> list() {
		return sql.selectList("hugi.mapper.list");
	}

	@Override
	public HugiVO detail(String id) {
		return sql.selectOne("hugi.mapper.detail", id);
	}

	@Override
	public boolean update(HugiVO vo) {
		return sql.update("hugi.mapper.update", vo) > 0 ? true:false;
	}

	@Override
	public boolean delete(int id) {
		return sql.delete("hugi.mapper.delete", id) > 0 ? true:false;
	}

	@Override
	public void read(String id) {
		sql.update("hugi.mapper.read", id);
	}

	@Override
	public List<HugiVO> list(HashMap<String, String> map) {
		return sql.selectList("hugi.mapper.list", map);
	}

	@Override
	public List<HugiVO> and_list3() {
		return sql.selectList("android.mapper.list3");
	}

	@Override
	public boolean and_delete(HashMap map) {
		return sql.delete("android.mapper.hugidelete", map) > 0 ? true : false;
	}

	@Override
	public boolean and_insert(HugiVO vo) {
		return sql.insert("android.mapper.hugiinsert", vo) > 0 ? true : false;
	}

	@Override
	public boolean and_update(HugiVO vo) {
		return sql.update("android.mapper.hugiupdate", vo) > 0 ? true : false;
	}

	@Override
	public boolean comment_insert(HashMap<String, Object> map) {
		return sql.insert("hugi.mapper.comment_insert", map) >0 ? true : false;
	}

	@Override
	public List<CommentVO> comment_list(int pid) {
		return sql.selectList("hugi.mapper.comment_list",pid);
	}

	@Override
	public boolean comment_update(CommentVO vo) {
		return sql.update("hugi.mapper.comment_update", vo) >0 ? true : false;
	}

	@Override
	public boolean comment_delete(int id) {
		return sql.delete("hugi.mapper.comment_delete", id) > 0 ? true : false;
	}

}
