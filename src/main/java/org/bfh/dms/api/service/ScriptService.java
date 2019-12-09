package org.bfh.dms.api.service;

import lombok.extern.slf4j.Slf4j;
import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.ScriptDeviceDto;
import org.bfh.dms.core.repository.DeviceRepository;
import org.bfh.dms.api.utils.ScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * This service is called by the Script REST Controller and contains the business logic to handle
 * all REST calls from the script installed on the devices. The main purpose is to decide how to save
 * the devices. If a device's serial number is not present in the DB then a new Device is created.
 * If a serial number is present, the name of the device has not changed and the user who has logged
 * in to the device is the same as last time the timestamp is simply updated. If the user has changed
 * since last time then the timestamp is updated and the device user is updated. If the device name
 * has changed then the previous entries are marked as obsolete and a new Entry is added for the
 * specific serial number.
 */
@Service
@Slf4j
public class ScriptService {

    private DeviceRepository deviceRepository;

    @Autowired
    public ScriptService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }

    /**
     * this method is used to process a device which has been sent to the ScriptController.
     * It decides if it should update an existing entry or create a new one in the database
     * @param scriptDeviceDto device to be updated or added
     * @return the processed device
     */
    //TODO improve this code
    //TODO all actions should update timestamp
    @Transactional
    public Device processDevice(ScriptDeviceDto scriptDeviceDto) {
        List<Device> deviceList = deviceRepository.findBySerialNumber(scriptDeviceDto.getSerialNumber());
        if (!deviceList.isEmpty()) {
            log.info("{} devices found with serial number: {}", deviceList.size(), scriptDeviceDto.getSerialNumber());
            Optional<Device> optSameNameDevice = deviceList.stream().filter(device -> device.getDeviceName().equals(scriptDeviceDto.getDeviceName())).findFirst();
            if (optSameNameDevice.isPresent()) {
                log.info("Device with deviceName {} is already present", scriptDeviceDto.getDeviceName());
                Device sameNameDevice = optSameNameDevice.get();
                if (sameNameDevice.getDeviceUser().equals(ScriptUtils.convertUserName(scriptDeviceDto.getDeviceUser()))) {
                    log.info("Device with name {} has been accessed by same user as last time.", sameNameDevice.getDeviceUser());
                    sameNameDevice.setTimestamp(LocalDate.now());
                    log.info("Updated login timestamp for device with name {}", sameNameDevice.getDeviceUser());
                    return deviceRepository.save(sameNameDevice);
                } else {
                    log.info("Device with name {} has been accessed by a different user.", scriptDeviceDto.getDeviceName());
                    sameNameDevice.setPreviousUser2(sameNameDevice.getPreviousUser1());
                    sameNameDevice.setPreviousUser1(sameNameDevice.getDeviceUser());
                    sameNameDevice.setDeviceUser(ScriptUtils.convertUserName(scriptDeviceDto.getDeviceUser()));
                    log.info("Device user and previous users of the device has been updated for device with name {}", scriptDeviceDto.getDeviceName());
                    return deviceRepository.save(sameNameDevice);
                }
            } else {
                log.info("Restaged device: setting old entry to obsolete and creating new device entry with new deviceName.");
                for (Device device : deviceList) {
                    if (!device.isObsolete()) {
                        device.setObsolete(true);
                        deviceRepository.save(device);
                    }
                }
                log.info("Device with name {} has been added.", scriptDeviceDto.getDeviceName());
                return deviceRepository.save(createNewDevice(scriptDeviceDto));
            }
        } else {
            log.info("A new device with serial number {} has been detected.", scriptDeviceDto.getSerialNumber());
            log.info("Saving new device");
            return deviceRepository.save(createNewDevice(scriptDeviceDto));
        }
    }

    /**
     * creates a new device based on the ScriptDeviceDto sent to the controller. DeviceUtils is used
     * for formatting values so they are saved correctly in the database.
     * @param scriptDeviceDto device to be converted
     * @return the device with formatted values
     */
    public Device createNewDevice(ScriptDeviceDto scriptDeviceDto) {
        String deviceUser = ScriptUtils.convertUserName(scriptDeviceDto.getDeviceUser());
        String memory = ScriptUtils.convertMemory(scriptDeviceDto.getMemory());
        String hardDisks = ScriptUtils.convertHarddiskSizes(scriptDeviceDto.getHardDisk());
        LocalDate biosDate = LocalDate.parse(ScriptUtils.convertBiosDate(scriptDeviceDto.getBiosDate()));
        log.info(biosDate.toString());
        String os = ScriptUtils.convertOSName(scriptDeviceDto.getOs());
        String osBuild = ScriptUtils.convertOsBuild(scriptDeviceDto.getBuild());
        log.info(ScriptUtils.convertOsBuild(scriptDeviceDto.getBuild()));
        return Device.of(scriptDeviceDto.getTimestamp(),
                scriptDeviceDto.getDeviceName(),
                scriptDeviceDto.getModel(),
                deviceUser,
                os,
                osBuild,
                scriptDeviceDto.getCpu(),
                memory,
                hardDisks,
                scriptDeviceDto.getInstalledBiosVersion(),
                biosDate,
                scriptDeviceDto.getSerialNumber(),
                scriptDeviceDto.getMaintenance(),
                scriptDeviceDto.getPreviousUser1(),
                scriptDeviceDto.getPreviousUser2(),
                scriptDeviceDto.getTeamviewerId(),
                false);
    }
}
