package org.bfh.dms.service;

import org.bfh.dms.domain.Device;
import org.bfh.dms.dto.DeviceDto;
import org.bfh.dms.repository.DeviceRepository;
import org.bfh.dms.utils.ScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScriptService {
    private ScriptUtils scriptUtils = new ScriptUtils();
    @Autowired
    private DeviceRepository deviceRepository;

    public void saveNewDevice(DeviceDto deviceDto) {
        System.out.println("Saving new device");
        deviceRepository.save(processDeviceDto(deviceDto));
        System.out.println("New device successfully saved!");
    }

    private Device processDeviceDto(DeviceDto deviceDto) {
        String benutzer = scriptUtils.convertName(deviceDto.getDeviceUser());
        String memory = scriptUtils.convertMemory(deviceDto.getMemory());
        String hardDisks = scriptUtils.convertHardiskSizes(deviceDto.getHardDisk());
        String os = scriptUtils.convertOSName(deviceDto.getOs());
        String osBuild = scriptUtils.convertOsBuild(deviceDto.getBuild());
        System.out.println(scriptUtils.convertOsBuild(deviceDto.getBuild()));
        return Device.of(deviceDto.getTimestamp(),
                deviceDto.getName(),
                deviceDto.getModel(),
                benutzer,
                os,
                osBuild,
                deviceDto.getCpu(),
                memory,
                hardDisks,
                deviceDto.getInstalledBiosVersion(),
                deviceDto.getBiosDate(),
                deviceDto.getSerialNumber(),
                deviceDto.getMaintenance(),
                deviceDto.getPreviousUser1(),
                deviceDto.getPreviousUser2(),
                deviceDto.getTeamviewerId());
    }
}
