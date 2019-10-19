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

    private String name;
    private String type;
    private String currentOwner;
    private LocalDate lastLogin;

    public static DeviceDto of(String name, String type, String currentOwner, LocalDate lastLogin) {
        DeviceDto instance = new DeviceDto();
        instance.name = name;
        instance.type = type;
        instance.currentOwner = currentOwner;
        instance.lastLogin = lastLogin;
        return instance;
    }

    public static DeviceDto of(Device device) {
        return of(device.getName(), device.getType(), device.getCurrentOwner(), device.getLastLogin());
    }

}
