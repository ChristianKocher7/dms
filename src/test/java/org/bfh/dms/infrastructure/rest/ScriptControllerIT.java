package org.bfh.dms.infrastructure.rest;

import org.bfh.dms.api.service.ScriptService;
import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.ScriptDeviceDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ScriptController.class)
public class ScriptControllerIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScriptService scriptService;

    @Test
    public void shouldReturnOkAndProcessDevice() throws Exception {
        Device device = MockFactory.createDevice();

        doReturn(device).when(scriptService).processDevice(any(ScriptDeviceDto.class));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic YWRtaW46MTIzNA==");

        mvc.perform(post("/script/device")
                .content(getJsonString())
                .headers(httpHeaders)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(scriptService, times(1)).processDevice(any(ScriptDeviceDto.class));
    }

    private String getJsonString() {
        return "{\n" +
                "\t\"timestamp\": \"2019-12-12\",\n" +
                "\t\"deviceName\": \"DESKTOP-PLL522K\",\n" +
                "\t\"model\": \"VivoBook_ASUSLaptop X580GD_N580GD\",\n" +
                "\t\"deviceUser\": \"christof.jungo\",\n" +
                "\t\"os\": \"Microsoft Windows 10 Pro|C:\\\\Windows|\\\\Device\\\\Harddisk1\\\\Partition4::64-bit\",\n" +
                "\t\"build\": \"10.0.17763\",\n" +
                "\t\"cpu\": \"Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz\",\n" +
                "\t\"memory\": \"17023975424\",\n" +
                "\t\"hardDisk\": \"  1000202273280::512105932800\",\n" +
                "\t\"installedBiosVersion\": \"X580GD.308\",\n" +
                "\t\"biosDate\": \"20180726000000.000000+000\",\n" +
                "\t\"serialNumber\": \"JAN0CV02V278418\",\n" +
                "\t\"maintenance\": \"2019-11-22\",\n" +
                "\t\"previousUser1\": \"Vincent Van Gogh\",\n" +
                "\t\"previousUser2\": \"Achilles\",\n" +
                "\t\"teamviewerId\": 123546,\n" +
                "\t\"obsolete\": false\n" +
                "}";
    }
}
