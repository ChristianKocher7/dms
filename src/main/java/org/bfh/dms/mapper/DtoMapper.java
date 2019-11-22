package org.bfh.dms.mapper;

import org.bfh.dms.domain.Device;
import org.bfh.dms.dto.DeviceDto;

public interface DtoMapper {
    static Device map(DeviceDto deviceDto) {
        return Device.of(deviceDto.getTimestamp(),
                deviceDto.getName(),
                deviceDto.getModell(),
                deviceDto.getBenutzer(),
                deviceDto.getOs(),
                deviceDto.getBuild(),
                deviceDto.getCpu(),
                deviceDto.getMemory(),
                deviceDto.getHardDisk(),
                deviceDto.getInstalledBiosVersion(),
                deviceDto.getBiosDate(),
                deviceDto.getSeriennummer(),
                deviceDto.getWartung(),
                deviceDto.getVorherigerBenutzer1(),
                deviceDto.getVorherigerBenutzer2(),
                deviceDto.getTeamviewerId());
    }

    static DeviceDto map(Device device) {
        return DeviceDto.of(device.getTimestamp(),
                device.getName(),
                device.getModell(),
                device.getBenutzer(),
                device.getOs(),
                device.getBuild(),
                device.getCpu(),
                device.getMemory(),
                device.getHardDisk(),
                device.getInstalledBiosVersion(),
                device.getBiosDate(),
                device.getSeriennummer(),
                device.getWartung(),
                device.getVorherigerBenutzer1(),
                device.getVorherigerBenutzer2(),
                device.getTeamviewerId());
    }

}
