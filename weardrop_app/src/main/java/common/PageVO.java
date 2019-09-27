package common;

public class PageVO {
	private int pageList = 10; 		//페이지당 보여질 목록 수
	private int blockPage = 10;		//블럭당 보여질 페이지의 수
	private int totalList;			//총 목록 수
	private int totalPage;			//총 페이지 수
	private int totalBlock;			//총 블록 수
	private int curPage;			//현재페이지번호
	private int curBlock;			//현재블럭번호
	private int beginList, endList;	//각 페이지의 끝/시작 목록번호
	private int beginPage, endPage;	//각 끝/시작 페이지 번호
	private String search, keyword;//검색조건, 검색어
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getPageList() {
		return pageList;
	}
	public void setPageList(int pageList) {
		this.pageList = pageList;
	}
	public int getBlockPage() {
		return blockPage;
	}
	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}
	public int getTotalList() {
		return totalList;
	}
	public void setTotalList(int totalList) {
		this.totalList = totalList;
		//총목록의 수를 알면 나머지는 결정된다.
		//총 페이지 수 : 29페이지 = 288/ 10 = 28 ...8			84 페이지 = 832 / 10 = 83...2
		totalPage = totalList / pageList;
		if(totalList % pageList > 0) ++totalPage;
		//총 블록 수 : 3 블록 = 29 / 10 = 2 ...9				9 블록 = 84 / 10 = 8...4	
		totalBlock = totalPage / blockPage;
		if(totalPage % blockPage > 0) ++totalBlock;
		
		//각 페이지의 끝 목록번호 :  총 목록수 - (페이지번호-1) * 페이지당 보여질 목록수
		endList = totalList - (curPage-1) * pageList;
		//각 페이지의 시작 목록번호 :  끝 목록번호 - (페이지당 보여질 목록수-1)
		beginList = endList - (pageList-1);
		
		//블록번호 : 페이지번호 / 블록당 보여질 페이지수
		curBlock = curPage / blockPage;
		if(curPage % blockPage > 0) ++curBlock;
		// 각 블럭의 끝 페이지번호 : 블록번호 * 블록당 보여질 페이지수
		endPage = curBlock * blockPage;
		// 각 블럭의 시작 페이지번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
		beginPage = endPage - (blockPage-1);
		//끝 페이지번호가 총 페이지수보다 클 수 없으므로 총 페이지수를 끝 페이지번호로 한다.
		if(endPage > totalPage) endPage = totalPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalBlock() {
		return totalBlock;
	}
	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}
	public int getBeginList() {
		return beginList;
	}
	public void setBeginList(int beginList) {
		this.beginList = beginList;
	}
	public int getEndList() {
		return endList;
	}
	public void setEndList(int endList) {
		this.endList = endList;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
}
