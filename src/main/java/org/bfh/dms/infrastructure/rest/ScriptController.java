package org.bfh.dms.infrastructure.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bfh.dms.api.service.ScriptService;
import org.bfh.dms.core.dto.ScriptDeviceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposed at url '/script' used by the script installed on the individual devices which are called
 * at login time.
 */
@Slf4j
@RestController
@RequestMapping(value = "/script")
public class ScriptController {

    /**
     * Injected script service from the API layer
     */
    @Autowired
    private ScriptService scriptService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * POST mapping at url '/device' which hands the device DTO from the request body to our script service for processing
     * @param scriptDeviceDto DTO to be processed
     */
    @PostMapping("/device")
    public void receiveDeviceLoginInformation(@RequestBody ScriptDeviceDto scriptDeviceDto) throws JsonProcessingException {
        String dtoString = objectMapper.writeValueAsString(scriptDeviceDto);
        log.info("ScriptDeviceDto: {}", dtoString);
        scriptService.processDevice(scriptDeviceDto);
    }
}
