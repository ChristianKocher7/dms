package org.bfh.dms.core.mapper;

import org.bfh.dms.api.utils.ScriptUtils;
import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.ScriptDeviceDto;
import org.bfh.dms.core.dto.GuiDeviceDto;

import java.time.LocalDate;

/**
 * Interface with static methods to easily map DTOs and Entities
 */
public interface DomainModelMapper {

    /**
     * Maps a scriptDeviceDto to a device entity
     * @param scriptDeviceDto dto to be mapped
     * @return mapped entity
     */
    static Device map(ScriptDeviceDto scriptDeviceDto) {
        return Device.of(scriptDeviceDto.getTimestamp(),
                scriptDeviceDto.getDeviceName(),
                scriptDeviceDto.getModel(),
                scriptDeviceDto.getDeviceUser(),
                scriptDeviceDto.getOs(),
                scriptDeviceDto.getBuild(),
                scriptDeviceDto.getCpu(),
                scriptDeviceDto.getMemory(),
                scriptDeviceDto.getHardDisk(),
                scriptDeviceDto.getInstalledBiosVersion(),
                LocalDate.parse(ScriptUtils.convertBiosDate(scriptDeviceDto.getBiosDate())),
                scriptDeviceDto.getSerialNumber(),
                scriptDeviceDto.getMaintenance(),
                scriptDeviceDto.getPreviousUser1(),
                scriptDeviceDto.getPreviousUser2(),
                scriptDeviceDto.getTeamviewerId(),
                scriptDeviceDto.isObsolete());
    }
    /**
     * Maps a deviceDto to a device entity
     * @param guiDeviceDto dto to be mapped
     * @return mapped entity
     */
    static Device map(GuiDeviceDto guiDeviceDto) {
        return Device.of(guiDeviceDto.getTimestamp(),
                guiDeviceDto.getDeviceName(),
                guiDeviceDto.getModel(),
                guiDeviceDto.getDeviceUser(),
                guiDeviceDto.getOs(),
                guiDeviceDto.getBuild(),
                guiDeviceDto.getCpu(),
                guiDeviceDto.getMemory(),
                guiDeviceDto.getHardDisk(),
                guiDeviceDto.getInstalledBiosVersion(),
                LocalDate.parse(guiDeviceDto.getBiosDate()),
                guiDeviceDto.getSerialNumber(),
                guiDeviceDto.getMaintenance(),
                guiDeviceDto.getPreviousUser1(),
                guiDeviceDto.getPreviousUser2(),
                guiDeviceDto.getTeamviewerId(),
                guiDeviceDto.isObsolete());
    }


    /**
     * Maps a device entity to a deviceDto
     * @param device device to be mapped
     * @return mapped deviceDto
     */
    static GuiDeviceDto map(Device device) {
        return GuiDeviceDto.of(device.getTimestamp(),
                device.getDeviceName(),
                device.getModel(),
                device.getDeviceUser(),
                device.getOs(),
                device.getBuild(),
                device.getCpu(),
                device.getMemory(),
                device.getHardDisk(),
                device.getInstalledBiosVersion(),
                device.getBiosDate() != null ? device.getBiosDate().toString() : "",
                device.getSerialNumber(),
                device.getMaintenance(),
                device.getPreviousUser1(),
                device.getPreviousUser2(),
                device.getTeamviewerId(),
                device.isObsolete());
    }

}
