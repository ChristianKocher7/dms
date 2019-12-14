package org.bfh.dms.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Device Entity class
 * The device entity is the cornerstone of the application. It contains all the information pertaining to a device
 * currently in circulation.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String serialNumber;

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

    private LocalDate biosDate;

    private LocalDate maintenance;

    private String previousUser1;
    private String previousUser2;
    private int teamviewerId;

    private boolean obsolete;


    /**
     * Static factory method for easily creating a device entity
     * Parameters are self explanatory
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
     * @param obsolete this flag indicates if a device has been restaged making the entry obsolete
     * @return returns new completely mapped Device entity
     */
    public static Device of(LocalDate timestamp,
                            String deviceName,
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
                            int teamviewerId,
                            boolean obsolete) {
        Device instance = new Device();
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
