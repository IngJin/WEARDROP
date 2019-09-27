package center;

import java.util.HashMap;
import java.util.List;

import center.CenterVO;


public interface CenterService {
	boolean insert(CenterVO vo);
	boolean update(CenterVO vo);
	boolean delete(int id);
	CenterVO detail(String id);
	CenterPage list(CenterPage vo);
	
	List<CenterVO> list(HashMap<String, String> map);
	
	List<CenterVO> content(int pid);
	
	List<exVO> listex(HashMap<String, String> map);
	
	boolean insertex (HashMap map); //�ȵ� ����
	boolean deleteex(String id);
	boolean updateex(HashMap map);
	
	List<exVO> and_list();


}
