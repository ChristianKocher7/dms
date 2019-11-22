package org.bfh.dms.mock;

import org.bfh.dms.domain.Device;
import org.bfh.dms.dto.DeviceDto;
import org.bfh.dms.mapper.DtoMapper;

import java.time.LocalDate;

public class MockFactory {
    public static Device createDevice() {
        return Device.of(LocalDate.now(),
                "ARGECPC003",
                "HP EliteBook 840 G1",
                "John Doe",
                "Win10x64Ent",
                "1803",
                "Intel(R) Core(TM) i7-4600U CPU @ 2.10GHz",
                "16 GB",
                "256 GB",
                "L71 Ver. 01.44",
                LocalDate.now(),
                "5CG4511135",
                LocalDate.now(),
                "Dave Ast",
                "Jens Wein",
                857098187);
    }

    public static DeviceDto createDeviceDto() {
        return DtoMapper.map(createDevice());
    }
}
