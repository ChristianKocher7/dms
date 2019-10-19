package org.bfh.dms.controller;

import org.bfh.dms.dto.DeviceDto;
import org.bfh.dms.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private DeviceService deviceService;

    @Autowired
    public AdminController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/devices")
    public List<DeviceDto> getAllDevices(){
        System.out.println ("getting all users");
        return deviceService.getAllDevices();
    }
}
