package hugi;

import java.sql.Date;

public class HugiVO {
	private int readcnt, likecnt, code, no;
	private String id, writer, title, content, filename, filepath, userid;
	private Date writedate;
	
	public HugiVO() {}
	
	public HugiVO(String userid,String title, String content, String writer, String filename, String filepath, int code) {
		super();
		this.userid=userid;
		this.title=title;
		this.content=content;
		this.writer=writer;
		this.filename=filename;
		this.filepath=filepath;
		this.code=code;
	}
	
	public HugiVO(String id, String userid, String title, String content, String writer, String filename, String filepath) {
		super();
		this.id=id;
		this.userid=userid;
		this.title=title;
		this.content=content;
		this.writer=writer;
		this.filename=filename;
		this.filepath=filepath;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	public int getLikecnt() {
		return likecnt;
	}
	public void setLikecnt(int likecnt) {
		this.likecnt = likecnt;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
}
