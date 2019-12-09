package org.bfh.dms.core.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ScriptDeviceDtoTest {

    private static final LocalDate TIMESTAMP = LocalDate.now();
    private static final String DEVICE_NAME = "device name";
    private static final String MODEL = "model";
    private static final String DEVICE_USER = "device user";
    private static final String OS = "os";
    private static final String BUILD = "build";
    private static final String CPU = "cpu";
    private static final String MEMORY = "memory";
    private static final String HARD_DISK = "harddisk";
    private static final String INSTALLED_BIOS_VERSION = "bios version";
    private static final String BIOS_DATE = "some date";
    private static final String SERIAL_NUMBER = "213345";
    private static final LocalDate MAINTENANCE = LocalDate.now();
    private static final String PREVIOUS_USER_1 = "bob";
    private static final String PREVIOUS_USER_2 = "john";
    private static final int TEAMVIEWER_ID = 1234;
    private static final boolean OBSOLETE = false;

    @Test
    public void shouldCreateDeviceDto() {
        ScriptDeviceDto scriptDeviceDto = ScriptDeviceDto.of(TIMESTAMP,
                DEVICE_NAME,
                MODEL,
                DEVICE_USER,
                OS,
                BUILD,
                CPU,
                MEMORY,
                HARD_DISK,
                INSTALLED_BIOS_VERSION,
                BIOS_DATE,
                SERIAL_NUMBER,
                MAINTENANCE,
                PREVIOUS_USER_1,
                PREVIOUS_USER_2,
                TEAMVIEWER_ID,
                OBSOLETE);
        assertEquals(scriptDeviceDto.getTimestamp(), TIMESTAMP);
        assertEquals(scriptDeviceDto.getDeviceName(), DEVICE_NAME);
        assertEquals(scriptDeviceDto.getModel(), MODEL);
        assertEquals(scriptDeviceDto.getOs(), OS);
        assertEquals(scriptDeviceDto.getBuild(), BUILD);
        assertEquals(scriptDeviceDto.getCpu(), CPU);
        assertEquals(scriptDeviceDto.getMemory(), MEMORY);
        assertEquals(scriptDeviceDto.getHardDisk(), HARD_DISK);
        assertEquals(scriptDeviceDto.getInstalledBiosVersion(), INSTALLED_BIOS_VERSION);
        assertEquals(scriptDeviceDto.getBiosDate(), BIOS_DATE);
        assertEquals(scriptDeviceDto.getSerialNumber(), SERIAL_NUMBER);
        assertEquals(scriptDeviceDto.getMaintenance(), MAINTENANCE);
        assertEquals(scriptDeviceDto.getPreviousUser1(), PREVIOUS_USER_1);
        assertEquals(scriptDeviceDto.getPreviousUser2(), PREVIOUS_USER_2);
        assertEquals(scriptDeviceDto.getTeamviewerId(), TEAMVIEWER_ID);
        assertEquals(scriptDeviceDto.isObsolete(), OBSOLETE);
    }
}
