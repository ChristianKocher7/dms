package org.bfh.dms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DeviceDto {

    private LocalDate timestamp;

    private String name;
    private String model;
    private String deviceUser;
    private String os;
    private String build;
    private String cpu;
    private String memory;
    private String hardDisk;
    private String installedBiosVersion;

    private LocalDate biosDate;

    private String serialNumber;

    private LocalDate maintenance;

    private String previousUser1;
    private String previousUser2;
    private int teamviewerId;

    public static DeviceDto of(LocalDate timestamp,
                               String name,
                               String model,
                               String deviceUser,
                               String os,
                               String build,
                               String cpu,
                               String memory,
                               String hardDisk,
                               String installedBiosVersion,
                               LocalDate biosDate,
                               String serialNumber,
                               LocalDate maintenance,
                               String previousUser1,
                               String previousUser2,
                               int teamviewerId) {
        DeviceDto instance = new DeviceDto();
        instance.timestamp = timestamp;
        instance.name = name;
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
        return instance;
    }
}
