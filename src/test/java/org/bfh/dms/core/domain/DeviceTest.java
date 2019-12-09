package org.bfh.dms.core.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DeviceTest {
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
    private static final LocalDate BIOS_DATE = LocalDate.now();
    private static final String SERIAL_NUMBER = "213345";
    private static final LocalDate MAINTENANCE = LocalDate.now();
    private static final String PREVIOUS_USER_1 = "bob";
    private static final String PREVIOUS_USER_2 = "john";
    private static final int TEAMVIEWER_ID = 1234;
    private static final boolean OBSOLETE = false;

    @Test
    public void shouldCreateDeviceDto() {
        Device device = Device.of(TIMESTAMP,
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
        assertEquals(device.getTimestamp(), TIMESTAMP);
        assertEquals(device.getDeviceName(), DEVICE_NAME);
        assertEquals(device.getModel(), MODEL);
        assertEquals(device.getOs(), OS);
        assertEquals(device.getBuild(), BUILD);
        assertEquals(device.getCpu(), CPU);
        assertEquals(device.getMemory(), MEMORY);
        assertEquals(device.getHardDisk(), HARD_DISK);
        assertEquals(device.getInstalledBiosVersion(), INSTALLED_BIOS_VERSION);
        assertEquals(device.getBiosDate(), BIOS_DATE);
        assertEquals(device.getSerialNumber(), SERIAL_NUMBER);
        assertEquals(device.getMaintenance(), MAINTENANCE);
        assertEquals(device.getPreviousUser1(), PREVIOUS_USER_1);
        assertEquals(device.getPreviousUser2(), PREVIOUS_USER_2);
        assertEquals(device.getTeamviewerId(), TEAMVIEWER_ID);
        assertEquals(device.isObsolete(), OBSOLETE);
    }
}
