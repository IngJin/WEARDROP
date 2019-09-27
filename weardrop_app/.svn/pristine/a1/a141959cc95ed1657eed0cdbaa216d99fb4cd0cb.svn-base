package gallery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GalleryDAO implements GalleryService {

	@Autowired
	private SqlSession sql;

	@Override
	public List<GalleryVO> list() {
		return sql.selectList("gallery.mapper.list");
	}

	@Override
	public GalleryVO detail(String id) {
		return sql.selectOne("gallery.mapper.detail", id);
	}

	@Override
	public boolean insert(GalleryVO vo) {
		return sql.insert("gallery.mapper.insert", vo) > 0 ? true : false;
	}

	@Override
	public void read(String id) {
		sql.update("gallery.mapper.read", id);
	}

	@Override
	public List<GalleryVO> test() {
		return sql.selectList("gallery.mapper.list");
	}

	@Override
	public boolean galUpdate() {
		return sql.update("gallery.mapper.galupdate") > 0 ? true : false;
	}

	@Override
	public boolean andInsert(GalleryVO vo) {
		return sql.insert("gallery.mapper.andInsert", vo) > 0 ? true : false;
	}

	@Override
	public boolean delete(String id) {
		return sql.delete("gallery.mapper.delete", id) > 0 ? true : false;
	}

	@Override
	public boolean update(GalleryVO vo) {
		return sql.update("gallery.mapper.update", vo) > 0 ? true : false;// boolean Ÿ���̱� ����
	}

	@Override
	public List<GalleryVO> list(HashMap<String, String> map) {
		return sql.selectList("gallery.mapper.list", map);
	}
	
	@Override
	public List<GalleryVO> andlist() {
		return sql.selectList("gallery.mapper.andlist");
	}

	@Override
	public boolean and_delete(HashMap map) {
		return sql.delete("gallery.mapper.anddelete", map) > 0 ? true : false;
	}

	@Override
	public boolean and_update(GalleryVO vo) {
		return sql.update("gallery.mapper.andupdate", vo) > 0 ? true : false;
	}
}
