package center;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import center.CenterVO;

@Service("service")
public class CenterServiceImpl implements CenterService {
	
	@Autowired private CenterDAO dao;

	@Override
	public boolean insert(CenterVO vo) {
		
		return dao.insert(vo);
	}

	@Override
	public boolean update(CenterVO vo) {
		// TODO Auto-generated method stub
		return dao.update(vo);
	}
	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}

	@Override
	public CenterPage list(CenterPage vo) {
		// TODO Auto-generated method stub
		return dao.list(vo);
	}

	@Override
	public List<CenterVO> content(int pid) {
		
		return dao.content(pid);
	}

	@Override
	public List<CenterVO> list(HashMap<String, String> map) {
		
		return dao.list(map);
	}

	@Override
	public List<exVO> listex(HashMap<String, String> map) {
		return dao.listex(map);
	}

	@Override
	   public boolean insertex(HashMap map) {
	      return dao.insertex(map);
	   }

	@Override
	public CenterVO detail(String id) {
		// TODO Auto-generated method stub
		return dao.detail(id);
	}

	@Override
	public boolean deleteex(String id) {
		// TODO Auto-generated method stub
		return dao.deleteex(id);
	}

	@Override
	public boolean updateex(HashMap map) {
		// TODO Auto-generated method stub
		return dao.updateex(map);
	}

	@Override
	public List<exVO> and_list() {
		return dao.and_list();
	}
	
	

}
