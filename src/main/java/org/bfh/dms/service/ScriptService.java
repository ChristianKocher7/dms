package org.bfh.dms.service;

import lombok.extern.slf4j.Slf4j;
import org.bfh.dms.domain.Device;
import org.bfh.dms.dto.DeviceDto;
import org.bfh.dms.repository.DeviceRepository;
import org.bfh.dms.utils.ScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

//TODO remove system out calls before prod
@Service
@Slf4j
public class ScriptService {

    @Autowired
    private DeviceRepository deviceRepository;

    public void saveNewDevice(DeviceDto deviceDto) {
        Optional<Device> deviceOptional = deviceRepository.findBySerialNumber(deviceDto.getSerialNumber());
        if (deviceOptional.isPresent()) {
            log.info("Device already found with serial number: {}", deviceOptional.get().getSerialNumber());
        } else {
            log.info("Saving new device");
            deviceRepository.save(createNewDevice(deviceDto));
            log.info("New device successfully saved!");
        }
    }

    private Device createNewDevice(DeviceDto deviceDto) {
        String deviceUser = ScriptUtils.convertName(deviceDto.getDeviceUser());
        String memory = ScriptUtils.convertMemory(deviceDto.getMemory());
        String hardDisks = ScriptUtils.convertHardiskSizes(deviceDto.getHardDisk());
        LocalDate biosDate = LocalDate.parse(ScriptUtils.convertBiosDate(deviceDto.getBiosDate().toString()));
        System.out.println(biosDate);
        String os = ScriptUtils.convertOSName(deviceDto.getOs());
        String osBuild = ScriptUtils.convertOsBuild(deviceDto.getBuild());
        System.out.println(ScriptUtils.convertOsBuild(deviceDto.getBuild()));
        return Device.of(deviceDto.getTimestamp(),
                deviceDto.getName(),
                deviceDto.getModel(),
                deviceUser,
                os,
                osBuild,
                deviceDto.getCpu(),
                memory,
                hardDisks,
                deviceDto.getInstalledBiosVersion(),
                biosDate,
                deviceDto.getSerialNumber(),
                deviceDto.getMaintenance(),
                deviceDto.getPreviousUser1(),
                deviceDto.getPreviousUser2(),
                deviceDto.getTeamviewerId());
    }
}
