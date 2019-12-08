package org.bfh.dms.core.mapper;

import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.DeviceDto;

import java.time.LocalDate;

/**
 * Interface with static methods to easily map DTOs and Entities
 */
public interface DtoMapper {

    /**
     * Maps a deviceDto to a device entity
     * @param deviceDto dto to be mapped
     * @return mapped entity
     */
    static Device map(DeviceDto deviceDto) {
        return Device.of(deviceDto.getTimestamp(),
                deviceDto.getDeviceName(),
                deviceDto.getModel(),
                deviceDto.getDeviceUser(),
                deviceDto.getOs(),
                deviceDto.getBuild(),
                deviceDto.getCpu(),
                deviceDto.getMemory(),
                deviceDto.getHardDisk(),
                deviceDto.getInstalledBiosVersion(),
                LocalDate.parse(deviceDto.getBiosDate()),
                deviceDto.getSerialNumber(),
                deviceDto.getMaintenance(),
                deviceDto.getPreviousUser1(),
                deviceDto.getPreviousUser2(),
                deviceDto.getTeamviewerId(),
                deviceDto.isObsolete());
    }

    /**
     * Maps a device entity to a deviceDto
     * @param device device to be mapped
     * @return mapped deviceDto
     */
    static DeviceDto map(Device device) {
        return DeviceDto.of(device.getTimestamp(),
                device.getDeviceName(),
                device.getModel(),
                device.getDeviceUser(),
                device.getOs(),
                device.getBuild(),
                device.getCpu(),
                device.getMemory(),
                device.getHardDisk(),
                device.getInstalledBiosVersion(),
                device.getBiosDate().toString(),
                device.getSerialNumber(),
                device.getMaintenance(),
                device.getPreviousUser1(),
                device.getPreviousUser2(),
                device.getTeamviewerId(),
                device.isObsolete());
    }

}
