package org.bfh.dms.api.service;

import lombok.extern.slf4j.Slf4j;
import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.DeviceDto;
import org.bfh.dms.core.repository.DeviceRepository;
import org.bfh.dms.api.utils.ScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScriptService {

    @Autowired
    private DeviceRepository deviceRepository;

    //TODO improve this code
    @Transactional
    public void processDevice(DeviceDto deviceDto) {
        List<Device> deviceList = deviceRepository.findBySerialNumber(deviceDto.getSerialNumber());
        if (!deviceList.isEmpty()) {
            log.info("{} devices found with serial number: {}", deviceList.size(), deviceDto.getSerialNumber());
            Optional<Device> optSameNameDevice = deviceList.stream().filter(device -> device.getDeviceName().equals(deviceDto.getDeviceName())).findFirst();
            if (optSameNameDevice.isPresent()) {
                log.info("Device with deviceName {} is already present", deviceDto.getDeviceName());
                Device sameNameDevice = optSameNameDevice.get();
                if (sameNameDevice.getDeviceUser().equals(ScriptUtils.convertUserName(deviceDto.getDeviceUser()))) {
                    log.info("Device with name {} has been accessed by same user as last time.", sameNameDevice.getDeviceUser());
                    sameNameDevice.setTimestamp(LocalDate.now());
                    deviceRepository.save(sameNameDevice);
                    log.info("Updated login timestamp for device with name {}", sameNameDevice.getDeviceUser());
                } else {
                    log.info("Device with name {} has been accessed by a different user.", deviceDto.getDeviceName());
                    sameNameDevice.setPreviousUser2(sameNameDevice.getPreviousUser1());
                    sameNameDevice.setPreviousUser1(sameNameDevice.getDeviceUser());
                    sameNameDevice.setDeviceUser(ScriptUtils.convertUserName(deviceDto.getDeviceUser()));
                    deviceRepository.save(sameNameDevice);
                    log.info("Device user and previous users of the device has been updated for device with name {}", deviceDto.getDeviceName());
                }
            } else {
                log.info("Restaged device: setting old entry to obsolete and creating new device entry with new deviceName.");
                for (Device device : deviceList) {
                    if (!device.isObsolete()) {
                        device.setObsolete(true);
                        deviceRepository.save(device);
                    }
                }
                deviceRepository.save(createNewDevice(deviceDto));
                log.info("Device with name {} has been added.", deviceDto.getDeviceName());
            }
        } else {
            log.info("A new device with serial number {} has been detected.", deviceDto.getSerialNumber());
            log.info("Saving new device");
            deviceRepository.save(createNewDevice(deviceDto));
            log.info("New device successfully saved!");
        }
    }

    private Device createNewDevice(DeviceDto deviceDto) {
        String deviceUser = ScriptUtils.convertUserName(deviceDto.getDeviceUser());
        String memory = ScriptUtils.convertMemory(deviceDto.getMemory());
        String hardDisks = ScriptUtils.convertHarddiskSizes(deviceDto.getHardDisk());
        LocalDate biosDate = LocalDate.parse(ScriptUtils.convertBiosDate(deviceDto.getBiosDate()));
        log.info(biosDate.toString());
        String os = ScriptUtils.convertOSName(deviceDto.getOs());
        String osBuild = ScriptUtils.convertOsBuild(deviceDto.getBuild());
        log.info(ScriptUtils.convertOsBuild(deviceDto.getBuild()));
        return Device.of(deviceDto.getTimestamp(),
                deviceDto.getDeviceName(),
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
                deviceDto.getTeamviewerId(),
                false);
    }
}
