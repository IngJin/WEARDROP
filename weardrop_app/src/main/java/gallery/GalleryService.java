package gallery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import community.CommunityVO;

public interface GalleryService {
	List<GalleryVO> list(); //��ȸ
	List<GalleryVO> andlist(); //�ȵ���ȸ
	List<GalleryVO> list(HashMap<String, String> map);
	GalleryVO detail(String id); //��
	boolean insert(GalleryVO vo);//����
	boolean delete(String id); //������ �ߴ�, ���ߴ�.
	boolean update(GalleryVO vo); //������ �ߴ�, ���ߴ�.
	void read(String id);
	List<GalleryVO> test();
	boolean galUpdate();
	boolean andInsert(GalleryVO vo);
	boolean and_delete(HashMap map);	//삭제(안드)
	boolean and_update(GalleryVO vo);	//수정(안드)


	//boolean andInsert(HashMap<String, Object> map);
}
