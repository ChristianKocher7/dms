package org.bfh.dms.core.mapper;


import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.ScriptDeviceDto;
import org.bfh.dms.core.dto.GuiDeviceDto;
import org.bfh.dms.infrastructure.mock.MockFactory;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class DomainModelMapperTest {
    @Test
    public void shouldMapDevice() {
        Device device = MockFactory.createDevice();
        GuiDeviceDto deviceDto = DomainModelMapper.map(device);
        assertEquals(device.getDeviceUser(), deviceDto.getDeviceUser());
        assertEquals(device.getDeviceName(), deviceDto.getDeviceName());
        assertEquals(device.getCpu(), deviceDto.getCpu());
        assertEquals(device.getMemory(), deviceDto.getMemory());
        assertEquals(device.getMaintenance(), deviceDto.getMaintenance());
    }

    @Test
    public void shouldMapDeviceDto() {
        ScriptDeviceDto deviceDto = MockFactory.createDeviceDto();
        Device device = DomainModelMapper.map(deviceDto);
        assertEquals(device.getDeviceUser(), deviceDto.getDeviceUser());
        assertEquals(device.getDeviceName(), deviceDto.getDeviceName());
        assertEquals(device.getCpu(), deviceDto.getCpu());
        assertEquals(device.getMemory(), deviceDto.getMemory());
        assertEquals(device.getMaintenance(), deviceDto.getMaintenance());
    }
}
