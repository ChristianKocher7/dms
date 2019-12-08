package org.bfh.dms.infrastructure.rest;

import org.bfh.dms.core.dto.DeviceDto;
import org.bfh.dms.api.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposed at url '/script' used by the script installed on the individual devices which are called
 * at login time.
 */
@RestController
@RequestMapping(value = "/script")
public class ScriptController {

    /**
     * Injected script service from the API layer
     */
    @Autowired
    private ScriptService scriptService;

    /**
     * POST mapping at url '/device' which hands the device DTO from the request body to our script service for processing
     * @param deviceDto DTO to be processed
     */
    @PostMapping("/device")
    public void receiveDeviceLoginInformation(@RequestBody DeviceDto deviceDto) {
        scriptService.processDevice(deviceDto);
    }
}
