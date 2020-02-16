package ua.training.game.web.pagination;

import java.util.List;

public class Page<E> {
    private int numberOfPages;
    private int pageSize;
    private int currentPage;
    private List<E> entries;
    private int numberOfEntries;

    public Page() {
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<E> getEntries() {
        return entries;
    }

    public void setEntries(List<E> entries) {
        this.entries = entries;
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }

    public void setNumberOfEntries(int numberOfEntries) {
        this.numberOfEntries = numberOfEntries;
    }
}

