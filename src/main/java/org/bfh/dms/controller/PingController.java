package org.bfh.dms.controller;

import org.bfh.dms.dto.PingDto;
import org.bfh.dms.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class PingController {

    private PingService pingService;

    @Autowired
    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping(value = "/ping")
    public PingDto ping() {
        return pingService.getPing();
    }

    @GetMapping(value = "/ping/{value}")
    public PingDto savePingWithValue(@PathVariable String value) {
        return pingService.savePing(value);
    }
}
