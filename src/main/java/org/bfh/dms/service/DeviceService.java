package org.bfh.dms.service;

import org.bfh.dms.domain.Device;
import org.bfh.dms.dto.DeviceDto;
import org.bfh.dms.mapper.DtoMapper;
import org.bfh.dms.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<DeviceDto> getAllDevices(){
        System.out.println("saving new device to db....");
        saveDevice(DeviceDto.of("X203984", "Lenovo", "John Doe", LocalDate.now()));
        System.out.println("device saved");
        List<Device> devices = deviceRepository.findAll();
        List<DeviceDto> deviceDtos = new ArrayList<>();
        System.out.println("mapping dtos...");
        for(Device device : devices){
            deviceDtos.add(DtoMapper.map(device));
        }
        return deviceDtos;
    }

    public void saveDevice(DeviceDto deviceDto){
        deviceRepository.save(DtoMapper.map(deviceDto));
    }
}
