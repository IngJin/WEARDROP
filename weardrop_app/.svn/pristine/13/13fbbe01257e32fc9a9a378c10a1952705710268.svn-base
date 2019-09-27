package sell;

import java.util.HashMap;
import java.util.List;

import javax.xml.stream.events.Comment;

import sell.CommentVO;
import sell.SellVO;


public interface SellService {
	boolean insert(SellVO vo);// 삽입
	SellVO detail(String id); // 상세보기
	boolean update(SellVO vo); // 수정
	boolean delete(String id); // 삭제
	void read(String id); // 조회수
	SellPage list(SellPage vo); // 메인화면(페이지)
	
	boolean comment_insert(HashMap<String, Object> map);
	List<CommentVO> comment_list(int pid);
	boolean comment_update(CommentVO vo);
	boolean comment_delete(int id);
	
	List<SellVO> and_list();		//조회(안드)
	List<SellVO> and_list2();		//조회(안드)
	//List<CommunityVO> and_list3();	//json 후기
	boolean and_insert(SellVO vo);	//삽입(안드)
	boolean and_delete(HashMap map);	//삭제(안드)
	boolean and_update(SellVO vo);	//수정(안드)
	List<SellVO> andcomment_list(int pid);	//댓글조회(안드)
}
