package ru.chirkovds.springtest.entity.Grid;

import ru.chirkovds.springtest.entity.Client;
import java.util.List;

public class ClientGrid {

    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<Client> clientData;

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<Client> getClientData(){
        return this.clientData;
}
    public void setClientData(List<Client> clientData) {
        this.clientData = clientData;
    }
    public String toString(){
        return "Total pages: " + totalPages + ", CurrentPage " + currentPage + ", TotalRecords: " + totalPages + ", ClientData Size: " + clientData.size();
    }
}