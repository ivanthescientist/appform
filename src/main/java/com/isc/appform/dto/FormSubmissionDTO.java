package com.isc.appform.dto;

import java.util.ArrayList;
import java.util.List;

public class FormSubmissionDTO {
    private String name;
    private List<Long> selectedSectorIds = new ArrayList<>();

    public List<Long> getSelectedSectorIds() {
        return selectedSectorIds;
    }

    public void setSelectedSectorIds(List<Long> selectedSectorIds) {
        this.selectedSectorIds = selectedSectorIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FormSubmissionDTO{" +
                "name='" + name + '\'' +
                ", selectedSectorIds=" + selectedSectorIds +
                '}';
    }
}
