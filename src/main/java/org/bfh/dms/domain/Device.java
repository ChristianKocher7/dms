package org.bfh.dms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Device {

    //Better for testing script as SerialNumber is Unique
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/

    @Id
    private String serialNumber;

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

    private LocalDate maintenance;

    private String previousUser1;
    private String previousUser2;
    private int teamviewerId;

    public static Device of(LocalDate timestamp,
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
        Device instance = new Device();
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
