package org.bfh.dms.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object for the device entity used for GUI REST services. A device entity should never be shared between architecture layers or
 * interfaces outside of the application context. This class is a mappable copy of the entity of the Device entity
 * and is used to transfer Device data between layers and interfaces.
 * This DTO is required in order to
 */
@Getter
@Setter
@NoArgsConstructor
public class GuiDeviceDto {

    private LocalDate timestamp;

    private String deviceName;
    private String model;
    private String deviceUser;
    private String os;
    private String build;
    private String cpu;
    private String memory;
    private String hardDisk;
    private String installedBiosVersion;

    private String biosDate;

    private String serialNumber;

    private LocalDate maintenance;

    private String previousUser1;
    private String previousUser2;
    private int teamviewerId;

    private boolean obsolete;

    /**
     * Static factory method for easily creating a GUI device DTO
     * Parameters are self explanatory
     *
     * @param timestamp
     * @param deviceName
     * @param model
     * @param deviceUser
     * @param os
     * @param build
     * @param cpu
     * @param memory
     * @param hardDisk
     * @param installedBiosVersion
     * @param biosDate
     * @param serialNumber
     * @param maintenance
     * @param previousUser1
     * @param previousUser2
     * @param teamviewerId
     * @param obsolete             this flag indicates if a device has been restaged making the entry obsolete
     * @return returns new completely mapped Device DTO
     */
    public static GuiDeviceDto of(LocalDate timestamp,
                                  String deviceName,
                                  String model,
                                  String deviceUser,
                                  String os,
                                  String build,
                                  String cpu,
                                  String memory,
                                  String hardDisk,
                                  String installedBiosVersion,
                                  String biosDate,
                                  String serialNumber,
                                  LocalDate maintenance,
                                  String previousUser1,
                                  String previousUser2,
                                  int teamviewerId,
                                  boolean obsolete) {
        GuiDeviceDto instance = new GuiDeviceDto();
        instance.timestamp = timestamp;
        instance.deviceName = deviceName;
        instance.model = model;
        instance.deviceUser = deviceUser;
        instance.os = os;
        instance.build = build;
        instance.cpu = cpu;
        instance.memory = memory;
        instance.hardDisk = hardDisk;
        instance.installedBiosVersion = installedBiosVersion;
        instance.biosDate = biosDate;
        instance.serialNumber = serialNumber;
        instance.maintenance = maintenance;
        instance.previousUser1 = previousUser1;
        instance.previousUser2 = previousUser2;
        instance.teamviewerId = teamviewerId;
        instance.obsolete = obsolete;
        return instance;
    }
}
