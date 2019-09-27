package center;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import notice.NoticeVO;

@Repository
public class CenterDAO implements CenterService {
@Autowired
private SqlSession sql;
	
	@Override
	public boolean insert(CenterVO vo) {
		
		return sql.insert("center.mapper.insert", vo) > 0 ? true: false;
			}

	@Override
	public boolean update(CenterVO vo) {
		// TODO Auto-generated method stub
		return sql.update("center.mapper.update", vo)>0 ? true: false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return sql.delete("center.mapper.delete", id)>0 ? true : false;
	}

	@Override
	public CenterPage list(CenterPage vo) {
		vo.setTotalList((Integer)sql.selectOne("center.mapper.total", vo));
		List<CenterVO> list = sql.selectList("center.mapper.list", vo);
		vo.setList(list);
		
		return vo;
	}

	@Override
	public List<CenterVO> content(int pid) {
		
		 return sql.selectList("center.mapper.content", pid);
	}

	@Override
	public List<CenterVO> list(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return sql.selectList("center.mapper.list", map);
	}

	@Override
	public List<exVO> listex(HashMap<String, String> map) {
		return sql.selectList("center.mapper.listex", map);
	}

	@Override
	public  boolean insertex(HashMap map) {
		// TODO Auto-generated method stub
		return sql.insert("center.mapper.insertex", map) > 0 ? true: false;
	}

	@Override
	public CenterVO detail(String id) {
		// TODO Auto-generated method stub
		return sql.selectOne("center.mapper.detail", id);
	}

	@Override
	public boolean deleteex(String id) {
		// TODO Auto-generated method stub
		return sql.delete("center.mapper.deleteex", id) > 0 ? true : false;
	}

	@Override
	public boolean updateex(HashMap map) {
		// TODO Auto-generated method stub
		return sql.update("center.mapper.updateex", map) > 0 ? true: false;
	}

	@Override
	public List<exVO> and_list() {
		return sql.selectList("center.mapper.and_list");
	}
	

}
