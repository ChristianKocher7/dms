package org.bfh.dms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bfh.dms.dto.DeviceDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String type;
    private String currentOwner;
    private LocalDate lastLogin;

    public static Device of(String name, String type, String currentOwner, LocalDate lastLogin) {
        Device instance = new Device();
        instance.name = name;
        instance.type = type;
        instance.currentOwner = currentOwner;
        instance.lastLogin = lastLogin;
        return instance;
    }

    public static Device of(DeviceDto deviceDto) {
        return of(deviceDto.getName(), deviceDto.getType(), deviceDto.getCurrentOwner(), deviceDto.getLastLogin());
    }

}
