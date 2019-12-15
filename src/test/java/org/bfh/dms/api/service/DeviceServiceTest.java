package org.bfh.dms.api.service;

import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.GuiDeviceDto;
import org.bfh.dms.core.repository.DeviceRepository;
import org.bfh.dms.infrastructure.mock.MockFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    private DeviceService deviceService;

    @Before
    public void setUp() {
        this.deviceService = new DeviceService(deviceRepository);
    }

    @Test
    public void shouldGetAllDevices() {
        //given
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(MockFactory.createDevice());
        doReturn(deviceList).when(deviceRepository).findAll();

        //when
        List<GuiDeviceDto> allDevices = deviceService.getAllDevices();

        //then
        verify(deviceRepository, times(1)).findAll();
        assertTrue(allDevices.get(0).getSerialNumber().equals(deviceList.get(0).getSerialNumber()));
    }
}
