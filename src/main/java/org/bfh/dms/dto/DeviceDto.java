package org.bfh.dms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bfh.dms.domain.Device;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DeviceDto {

    private LocalDate timestamp;

    private String name;
    private String modell;
    private String benutzer;
    private String os;
    private String build;
    private String cpu;
    private String memory;
    private String hardDisk;
    private String installedBiosVersion;
    private LocalDate biosDate;
    private String seriennummer;
    private LocalDate wartung;
    private String vorherigerBenutzer1;
    private String vorherigerBenutzer2;
    private int teamviewerId;

    public static DeviceDto of(LocalDate timestamp, String name, String modell, String benutzer, String os, String build, String cpu, String memory, String hardDisk, String installedBiosVersion, LocalDate biosDate, String seriennummer, LocalDate wartung, String vorherigerBenutzer1, String vorherigerBenutzer2, int teamviewerId) {
        DeviceDto instance = new DeviceDto();
        instance.timestamp = timestamp;
        instance.name = name;
        instance.modell = modell;
        instance.benutzer = benutzer;
        instance.os = os;
        instance.build = build;
        instance.cpu = cpu;
        instance.memory = memory;
        instance.hardDisk = hardDisk;
        instance.installedBiosVersion = installedBiosVersion;
        instance.biosDate = biosDate;
        instance.seriennummer = seriennummer;
        instance.wartung = wartung;
        instance.vorherigerBenutzer1 = vorherigerBenutzer1;
        instance.vorherigerBenutzer2 = vorherigerBenutzer2;
        instance.teamviewerId = teamviewerId;
        return instance;
    }
}
