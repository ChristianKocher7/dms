package org.bfh.dms.mapper;

import org.bfh.dms.domain.Device;
import org.bfh.dms.dto.DeviceDto;

public interface DtoMapper {
    static Device map(DeviceDto deviceDto) {
        return Device.of(deviceDto.getTimestamp(),
                deviceDto.getName(),
                deviceDto.getModel(),
                deviceDto.getDeviceUser(),
                deviceDto.getOs(),
                deviceDto.getBuild(),
                deviceDto.getCpu(),
                deviceDto.getMemory(),
                deviceDto.getHardDisk(),
                deviceDto.getInstalledBiosVersion(),
                deviceDto.getBiosDate(),
                deviceDto.getSerialNumber(),
                deviceDto.getMaintenance(),
                deviceDto.getPreviousUser1(),
                deviceDto.getPreviousUser2(),
                deviceDto.getTeamviewerId());
    }

    static DeviceDto map(Device device) {
        return DeviceDto.of(device.getTimestamp(),
                device.getName(),
                device.getModel(),
                device.getDeviceUser(),
                device.getOs(),
                device.getBuild(),
                device.getCpu(),
                device.getMemory(),
                device.getHardDisk(),
                device.getInstalledBiosVersion(),
                device.getBiosDate(),
                device.getSerialNumber(),
                device.getMaintenance(),
                device.getPreviousUser1(),
                device.getPreviousUser2(),
                device.getTeamviewerId());
    }

}
