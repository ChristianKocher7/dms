package org.bfh.dms.mapper;

import org.bfh.dms.domain.Device;
import org.bfh.dms.domain.User;
import org.bfh.dms.dto.DeviceDto;

import java.util.ArrayList;
import java.util.List;

public interface DtoMapper {

    /*static List<Device> mapDeviceDtos(List<DeviceDto> dtos){
        List<Device> devices = new ArrayList<>();
        for(DeviceDto dto : dtos){
            devices.add(Device.of(dto));
        }
        return devices;
    }

    static List<DeviceDto> mapDevices(List<Device> devices){
        List<DeviceDto> deviceDtos = new ArrayList<>();
        for(Device device : devices){
            deviceDtos.add(DeviceDto.of(device));
        }
        return deviceDtos;
    }*/



    static Device map(DeviceDto dto){
        return Device.of(dto);
    }

    static DeviceDto map(Device device){
        return DeviceDto.of(device);
    }

}
