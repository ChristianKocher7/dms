package org.bfh.dms.controller;

import org.bfh.dms.dto.DeviceDto;
import org.bfh.dms.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping(value = "/devices")
    public List<DeviceDto> getAllDevices() {
        System.out.println("getting all devices");
        return deviceService.getAllDevices();
    }

    @GetMapping(value = "/user")
    public Principal user(Principal user) {
        System.out.println("getting logged in user" + user.getName());
        return user;
    }

}
