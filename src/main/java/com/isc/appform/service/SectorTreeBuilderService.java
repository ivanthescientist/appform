package com.isc.appform.service;

import com.isc.appform.domain.Sector;
import com.isc.appform.dto.SectorTreeNodeDTO;
import com.isc.appform.repository.SectorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SectorTreeBuilderService {
    private final Logger logger = LoggerFactory.getLogger(SectorTreeBuilderService.class);
    private final SectorRepository repository;

    @Autowired
    public SectorTreeBuilderService(SectorRepository repository) {
        this.repository = repository;
    }

    public SectorTreeNodeDTO getCurrentTree() {
        long startTime = System.nanoTime();
        logger.debug("Rebuilding sector tree from database");
        SectorTreeNodeDTO treeRoot = new SectorTreeNodeDTO();
        treeRoot.setId(0L);
        treeRoot.setName("root");

        Map<Long, SectorTreeNodeDTO> sectorTreeNodes = repository.findAllWithPrefetch().stream()
                .map(this::toSectorTreeDTO)
                .collect(Collectors.toMap(SectorTreeNodeDTO::getId, Function.identity()));

        logger.debug("Loaded " + sectorTreeNodes.size() + " sector tree nodes from database");

        sectorTreeNodes.values().stream()
                .filter(node -> node.getParentId() == null)
                .forEach(node -> treeRoot.getChildren().add(node));

        logger.debug("Found " + treeRoot.getChildren().size() + " root children");

        for (SectorTreeNodeDTO node : sectorTreeNodes.values()) {
            for (SectorTreeNodeDTO subNode : sectorTreeNodes.values()) {
                if (Objects.equals(subNode.getParentId(), node.getId())) {
                    node.getChildren().add(subNode);
                }
            }
            if (node.getChildren().size() > 0) {
                logger.debug("Added " + node.getChildren().size() + " children to node #" + node.getId());
            }
        }

        logger.debug("Tree rebuild took " + ((System.nanoTime() - startTime) / 1000_000L) + " ms.");

        return treeRoot;
    }

    private SectorTreeNodeDTO toSectorTreeDTO(Sector sector) {
        SectorTreeNodeDTO dto = new SectorTreeNodeDTO();
        dto.setId(sector.getId());
        dto.setName(sector.getName());
        if (sector.getParent() != null) {
            dto.setParentId(sector.getParent().getId());
        }

        return dto;
    }
}
