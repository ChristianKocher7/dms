package org.bfh.dms.controller;

import org.bfh.dms.dto.DeviceDto;
import org.bfh.dms.service.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/script")
public class ScriptController {

    @Autowired
    private ScriptService scriptService;

    @PostMapping("/device")
    public void newEmployee(@RequestBody DeviceDto deviceDto) {
        scriptService.saveNewDevice(deviceDto);
    }
}
