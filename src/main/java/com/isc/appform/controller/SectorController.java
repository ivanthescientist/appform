package com.isc.appform.controller;

import com.isc.appform.dto.SectorTreeNodeDTO;
import com.isc.appform.service.SectorTreeBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sectors")
public class SectorController {
    private final Logger logger = LoggerFactory.getLogger(SectorController.class);
    private final SectorTreeBuilderService service;

    @Autowired
    public SectorController(SectorTreeBuilderService service) {
        this.service = service;
    }

    @RequestMapping("")
    public SectorTreeNodeDTO getSectors() {
        logger.debug("Fetching current sector tree");
        return service.getCurrentTree();
    }

}
