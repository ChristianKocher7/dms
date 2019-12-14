package org.bfh.dms.infrastructure.rest;

import org.bfh.dms.api.service.DeviceService;
import org.bfh.dms.api.service.ImporterService;
import org.bfh.dms.api.service.SearchService;
import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.GuiDeviceDto;
import org.bfh.dms.core.mapper.DomainModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
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

    private SearchService searchService;

    private ImporterService importerService;

    @Autowired
    public DeviceController(DeviceService deviceService, SearchService searchService, ImporterService importerService) {
        this.deviceService = deviceService;
        this.searchService = searchService;
        this.importerService = importerService;
    }

    /**
     * GET mapping at url '/devices which returns a list of deviceDtos of all present devices in database
     * @return list of deviceDtos present in DB
     */
    @GetMapping(value = "/devices")
    public List<GuiDeviceDto> getAllDevices() {
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

    @GetMapping(value = "/devices/search/{keyword}")
    public List<GuiDeviceDto> search(@PathVariable String keyword){
        List<Device> search = searchService.search(keyword.toLowerCase());
        List<GuiDeviceDto> guiDeviceDtoList = new ArrayList<>();
        for(Device device : search){
            guiDeviceDtoList.add(DomainModelMapper.map(device));
        }
        return guiDeviceDtoList;
    }

    @GetMapping(value = "/import")
    public String importCsv(){
        importerService.importCsv();
        return "imported";
    }

}
