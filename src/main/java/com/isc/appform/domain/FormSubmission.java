package com.isc.appform.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "form_submissions", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class FormSubmission {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "form_submission_sectors",
            joinColumns = @JoinColumn(name = "form_submission_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_FORM_SUBMISSION_SECTORS_SUBMISSION_ID")),
            inverseJoinColumns = @JoinColumn(name = "sector_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "FK_FORM_SUBMISSION_SECTORS_SECTOR_ID")))
    private List<Sector> selectedSectors = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sector> getSelectedSectors() {
        return selectedSectors;
    }

    public void setSelectedSectors(List<Sector> selectedSectors) {
        this.selectedSectors = selectedSectors;
    }
}
