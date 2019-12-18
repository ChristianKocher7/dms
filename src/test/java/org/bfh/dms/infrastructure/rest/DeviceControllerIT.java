package org.bfh.dms.infrastructure.rest;

import org.bfh.dms.api.service.DeviceService;
import org.bfh.dms.api.service.ImporterService;
import org.bfh.dms.api.service.SearchService;
import org.bfh.dms.core.dto.GuiDeviceDto;
import org.bfh.dms.infrastructure.mock.MockFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
public class DeviceControllerIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeviceService deviceService;

    @MockBean
    private SearchService searchService;

    @MockBean
    private ImporterService importerService;

    @Test
    public void shouldReturnOkAndGetAllDevices() throws Exception {
        GuiDeviceDto guiDeviceDto = MockFactory.createGuiDeviceDto();
        List<GuiDeviceDto> guiDeviceDtoList = new ArrayList<>();
        guiDeviceDtoList.add(guiDeviceDto);

        doReturn(guiDeviceDtoList).when(deviceService).getAllDevices();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWRtaW46MTIzNA==");

        mvc.perform(get("/api/devices")
                .headers(httpHeaders)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(deviceService, times(1)).getAllDevices();
    }

    @Test
    public void shouldReturnOkAndGetSearchResults() throws Exception {
        GuiDeviceDto guiDeviceDto = MockFactory.createGuiDeviceDto();
        List<GuiDeviceDto> guiDeviceDtoList = new ArrayList<>();
        guiDeviceDtoList.add(guiDeviceDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWRtaW46MTIzNA==");

        mvc.perform(get("/api/devices/search/{keyword}", "something")
                .headers(httpHeaders)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(searchService, times(1)).search(anyString());
    }

    @Test
    public void shouldReturnOkAndImportCsv() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWRtaW46MTIzNA==");

        mvc.perform(get("/api/import")
                .headers(httpHeaders)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(importerService, times(1)).importCsv();
    }
}
