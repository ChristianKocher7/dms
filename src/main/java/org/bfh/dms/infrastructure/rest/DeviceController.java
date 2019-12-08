package org.bfh.dms.infrastructure.rest;

import org.bfh.dms.core.dto.DeviceDto;
import org.bfh.dms.api.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


/**
 * REST controller used by the frontend to access devices at the url '/api'
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class DeviceController {


    /**
     * Injected device service from the API layer
     */
    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    /**
     * GET mapping at url '/devices which returns a list of deviceDtos of all present devices in database
     * @return list of deviceDtos present in DB
     */
    @GetMapping(value = "/devices")
    public List<DeviceDto> getAllDevices() {
        System.out.println("getting all devices");
        return deviceService.getAllDevices();
    }


    /**
     * GET mapping at url '/user' to find a specific user in the database
     * @param user specific user
     * @return Principal of the specific user
     */
    @GetMapping(value = "/user")
    public Principal user(Principal user) {
        System.out.println("getting logged in user" + user.getName());
        return user;
    }

}
