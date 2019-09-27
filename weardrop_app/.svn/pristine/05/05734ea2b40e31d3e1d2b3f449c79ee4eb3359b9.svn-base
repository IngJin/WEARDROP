package main;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MainDAO implements MainService {
	@Autowired private SqlSession sql;
	
	@Override
	public boolean id_check(String userid) {
		return (Integer)sql.selectOne("main.mapper.id_check", userid) > 0 ? false : true;
	}

	@Override
	public boolean insert(MainVO vo) {
		return sql.insert("main.mapper.insert", vo) > 0 ? true : false;
	}

	@Override
	public boolean update(MainVO vo) {
		return sql.update("main.mapper.update", vo) > 0 ? true : false;
	}

	@Override
	public boolean delete(String userid) {
		return sql.delete("main.mapper.delete", userid) > 0 ? true : false;
	}

	@Override
	public MainVO login(HashMap map) {
		return sql.selectOne("main.mapper.login", map);
	}

	@Override
	public List<TestVO> list() {
		return sql.selectList("android.mapper.list");
	}

	@Override
	public boolean email_check(String email) {
		return (Integer)sql.selectOne("main.mapper.email_check", email) > 0 ? false : true;
	}

	@Override
	public MainVO email_login(HashMap map) {
		return sql.selectOne("main.mapper.email_login", map);
	}

	@Override
	public MainVO userid_find(String email) {
		return sql.selectOne("main.mapper.userid_find", email);
	}

	@Override
	public MainVO userpw_find(MainVO vo) {
		return sql.selectOne("main.mapper.userpw_find", vo);
	}

	@Override
	public MirrorVO iot_userid_find(String userid) {
		return sql.selectOne("main.mapper.iot_userid_find", userid);
	}

	@Override
	public boolean iot_sign(String userid) {
		return sql.insert("main.mapper.iot_sign", userid) > 0 ? false : true;
	}

	@Override
	public boolean iot_time_update(HashMap<String, String> map) {
		return sql.update("main.mapper.iot_time", map) > 0 ? true : false;
	}

	@Override
	public boolean iot_weather_update(HashMap<String, String> map) {
		return sql.update("main.mapper.iot_weather", map) > 0 ? true : false;
	}

	@Override
	public boolean iot_delete(String userid) {
		return sql.delete("main.mapper.iot_delete", userid) > 0 ? true : false;
	}
}
