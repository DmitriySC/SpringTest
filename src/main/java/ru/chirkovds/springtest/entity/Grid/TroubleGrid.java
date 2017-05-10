package ru.chirkovds.springtest.entity.Grid;
import ru.chirkovds.springtest.entity.DTO.TroubleDTO;
import ru.chirkovds.springtest.entity.Trouble;

import java.util.ArrayList;
import java.util.List;

public class TroubleGrid {
    private int totalPages;
    private int currentPage;
    private long totalRecords;
    private List<TroubleDTO> troubleDTOs;

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

    public List<TroubleDTO> getTroubleData(){
        return this.troubleDTOs;
    }
    public void setTroubleData(List<Trouble> troubleData) {
        troubleDTOs = new ArrayList<TroubleDTO>();
        for(Trouble trouble : troubleData){
            this.troubleDTOs.add(new TroubleDTO(trouble));
        }
    }

    public String toString(){
        return "Total pages: " + totalPages + ", CurrentPage " + currentPage + ", TotalRecords: " + totalPages + ", ClientData Size: " + troubleDTOs.size();
    }
}