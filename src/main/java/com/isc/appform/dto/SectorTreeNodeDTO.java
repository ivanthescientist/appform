package com.isc.appform.dto;

import java.util.ArrayList;
import java.util.List;

public class SectorTreeNodeDTO {
    private Long id;
    private Long parentId;
    private String name;
    private List<SectorTreeNodeDTO> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SectorTreeNodeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<SectorTreeNodeDTO> children) {
        this.children = children;
    }
}
