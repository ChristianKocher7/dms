package org.bfh.dms.api.service;

import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.ScriptDeviceDto;
import org.bfh.dms.core.repository.DeviceRepository;
import org.bfh.dms.infrastructure.mock.MockFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScriptServiceTest {

    @MockBean
    private DeviceRepository deviceRepository;

    private ScriptService scriptService;

    @Before
    public void setUp() {
        this.scriptService = new ScriptService(deviceRepository);
    }

    @Test
    public void shouldSaveNewDevice(){
        //given
        ScriptDeviceDto scriptDeviceDto = MockFactory.createScriptDeviceDto();
        doReturn(new ArrayList<Device>()).when(deviceRepository).findBySerialNumber(anyString());

        //when
        scriptService.processDevice(scriptDeviceDto);

        //then
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    public void shouldCreateNewEntryAndUpdateObsoleteFlagOfOldEntry(){
        //given
        ScriptDeviceDto scriptDeviceDto = MockFactory.createScriptDeviceDto();
        Device device = MockFactory.createDevice();
        device.setDeviceName("another name");
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(device);
        doReturn(deviceList).when(deviceRepository).findBySerialNumber(anyString());

        //when
        scriptService.processDevice(scriptDeviceDto);

        //then
        verify(deviceRepository, times(2)).save(any(Device.class));
    }

    @Test
    public void shouldUpdateTimestamp(){
        //given
        ScriptDeviceDto scriptDeviceDto = MockFactory.createScriptDeviceDto();
        Device device = MockFactory.createDevice();
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(device);
        doReturn(deviceList).when(deviceRepository).findBySerialNumber(anyString());

        //when
        scriptService.processDevice(scriptDeviceDto);

        //then
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    public void shouldUpdateUsers(){
        //given
        ScriptDeviceDto scriptDeviceDto = MockFactory.createScriptDeviceDto();
        Device device = MockFactory.createDevice();
        device.setDeviceUser("Someone Else");
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(device);
        doReturn(deviceList).when(deviceRepository).findBySerialNumber(anyString());

        //when
        scriptService.processDevice(scriptDeviceDto);

        //then
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    public void shouldCreateNewDevice(){
        //given
        ScriptDeviceDto scriptDeviceDto = MockFactory.createScriptDeviceDto();

        //when
        Device returnedDevice = scriptService.createNewDevice(scriptDeviceDto);

        //then
        assertEquals(scriptDeviceDto.getSerialNumber(), returnedDevice.getSerialNumber());
        assertEquals(scriptDeviceDto.getDeviceName(), returnedDevice.getDeviceName());
        assertEquals("1 TB \\ 512 GB", returnedDevice.getHardDisk());
        assertEquals("Win10x64Pro", returnedDevice.getOs());
    }
}
