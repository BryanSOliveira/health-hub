package br.com.bryan.model.criteria;

public class SearchCriteria {
    private String activeFilter;
    private int currentPage;
    private int pageSize;
    private String searchQuery;

    public SearchCriteria() {
    	this.activeFilter = "active";
        this.currentPage = 1;
        this.pageSize = 25;
    }

    public SearchCriteria(String activeFilter) {
		this.activeFilter = activeFilter;
	}

	public String getActiveFilter() {
        return activeFilter;
    }

    public void setActiveFilter(String activeFilter) {
        this.activeFilter = activeFilter;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int page) {
        this.currentPage = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

	@Override
	public String toString() {
		return "SearchCriteria [activeFilter=" + activeFilter + ", currentPage=" + currentPage + ", pageSize=" + pageSize
				+ ", searchQuery=" + searchQuery + "]";
	}
}
