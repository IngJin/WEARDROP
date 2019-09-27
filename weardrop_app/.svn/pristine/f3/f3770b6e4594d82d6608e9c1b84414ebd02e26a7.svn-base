package main;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MainServiceImpl implements MainService {

	@Autowired private MainDAO dao;
	
	@Override
	public boolean id_check(String userid) {
		return dao.id_check(userid);
	}

	@Override
	public boolean insert(MainVO vo) {
		return dao.insert(vo);
	}

	@Override
	public boolean update(MainVO vo) {
		return dao.update(vo);
	}

	@Override
	public boolean delete(String userid) {
		return dao.delete(userid);
	}

	@Override
	public MainVO login(HashMap map) {
		return dao.login(map);
	}

	@Override
	public List<TestVO> list() {
		return dao.list();
	}

	@Override
	public boolean email_check(String email) {
		return dao.email_check(email);
	}

	@Override
	public MainVO email_login(HashMap map) {
		return dao.email_login(map);
	}

	@Override
	public MainVO userid_find(String email) {
		return dao.userid_find(email);
	}

	@Override
	public MainVO userpw_find(MainVO vo) {
		return dao.userpw_find(vo);
	}

	@Override
	public MirrorVO iot_userid_find(String userid) {
		return dao.iot_userid_find(userid);
	}

	@Override
	public boolean iot_sign(String userid) {
		return dao.iot_sign(userid);
	}

	@Override
	public boolean iot_time_update(HashMap<String, String> map) {
		return dao.iot_time_update(map);
	}

	@Override
	public boolean iot_weather_update(HashMap<String, String> map) {
		return dao.iot_weather_update(map);
	}

	@Override
	public boolean iot_delete(String userid) {
		return dao.iot_delete(userid);
	}
}
