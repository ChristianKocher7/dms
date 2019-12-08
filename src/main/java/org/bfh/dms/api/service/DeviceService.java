package org.bfh.dms.api.service;

import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.DeviceDto;
import org.bfh.dms.core.repository.DeviceRepository;
import org.bfh.dms.core.mapper.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<DeviceDto> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        List<DeviceDto> deviceDtos = new ArrayList<>();
        System.out.println("mapping dtos...");
        for (Device device : devices) {
            deviceDtos.add(DtoMapper.map(device));
        }
        return deviceDtos;
    }
}
