package org.bfh.dms.api.service;

import lombok.extern.slf4j.Slf4j;
import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.GuiDeviceDto;
import org.bfh.dms.core.repository.DeviceRepository;
import org.bfh.dms.core.mapper.DomainModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service called by the Device REST Controller in the infrastructure layer to handle all business logic
 * for Devices
 */
@Slf4j
@Service
public class DeviceService {

    private DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }


    /**
     * Method used to get all present devices from the database and map them to DTOs
     * @return list of DeviceDtos
     */
    public List<GuiDeviceDto> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        List<GuiDeviceDto> guiDeviceDtos = new ArrayList<>();
        log.info("mapping dtos...");
        for (Device device : devices) {
            guiDeviceDtos.add(DomainModelMapper.map(device));
        }
        return guiDeviceDtos;
    }
}
