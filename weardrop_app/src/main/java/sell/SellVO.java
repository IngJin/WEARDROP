package sell;

import java.sql.Date;

public class SellVO {
	private int no, replycnt;
	private String id, userid, title, writer;
	private String price;
	private String content;
	private Date writedate;
	private int readcnt;
	private String filename,filepath;
	private int code; 
	
	public SellVO() {}
	
	public SellVO(String userid, String title, String content,  String writer, String price, String filename, String filepath, int code) {
		this.userid = userid;
		this.title = title;
		this.content = content;
		this.writer = writer;	
		this.price = price;
		this.filename = filename;
		this.filepath = filepath;
		this.code = code;
		
	} 
	
	public SellVO(String id, String userid, String title, String content,  String writer, String price, String filename, String filepath) {
		this.id=id;
		this.userid=userid;
		this.title=title;
		this.content=content;
		this.writer=writer;
		this.price = price;
		this.filename=filename;
		this.filepath=filepath;
	}
	
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getReplycnt() {
		return replycnt;
	}
	public void setReplycnt(int replycnt) {
		this.replycnt = replycnt;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getWritedate() {
		return writedate;
	}
	public void setWritedate(Date writedate) {
		this.writedate = writedate;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
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
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
