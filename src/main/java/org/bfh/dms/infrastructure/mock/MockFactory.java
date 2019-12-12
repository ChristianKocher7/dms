package org.bfh.dms.infrastructure.mock;

import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.dto.GuiDeviceDto;
import org.bfh.dms.core.dto.ScriptDeviceDto;

import java.time.LocalDate;

/**
 * Factory class with static methods to create mock test data
 */
public class MockFactory {


    /**
     * creates a mocked device entity
     * @return mocked device entity
     */
    public static Device createDevice() {
        return Device.of(LocalDate.now(),
                "ARGECPC003",
                "HP EliteBook 840 G1",
                "John Doe",
                "Win10x64Pro",
                "1903",
                "Intel(R) Core(TM) i7-4600U CPU @ 2.10GHz",
                "16 GB",
                "1 TB \\ 512 GB",
                "L71 Ver. 01.44",
                LocalDate.parse("2018-07-26"),
                "5CG4511135",
                LocalDate.now(),
                "Dave Ast",
                "Jens Wein",
                857098187,
                false);
    }

    /**
     * creates a mocked device DTO for the script logic
     * @return mocked device DTO
     */
    public static ScriptDeviceDto createScriptDeviceDto() {
        return ScriptDeviceDto.of(LocalDate.now(),
                "ARGECPC003",
                "HP EliteBook 840 G1",
                "john.doe",
                "Microsoft Windows 10 Pro|C:\\Windows|\\Device\\Harddisk1\\Partition4::64-bit",
                "10.0.18362",
                "Intel(R) Core(TM) i7-4600U CPU @ 2.10GHz",
                "17023975424",
                "1000202273280::512105932800",
                "L71 Ver. 01.44",
                "20180726000000.000000+000",
                "5CG4511135",
                LocalDate.now(),
                "Dave Ast",
                "Jens Wein",
                857098187,
                false);
    }


    /**
     * creates a mock device DTO for GUI logic
     * @return GuiDeviceDto
     */
    public static GuiDeviceDto createGuiDeviceDto() {
        return GuiDeviceDto.of(LocalDate.now(),
                "ARGECPC003",
                "HP EliteBook 840 G1",
                "John Doe",
                "Win10x64Pro",
                "1903",
                "Intel(R) Core(TM) i7-4600U CPU @ 2.10GHz",
                "16 GB",
                "1 TB \\ 512 GB",
                "L71 Ver. 01.44",
                "2018-07-26",
                "5CG4511135",
                LocalDate.now(),
                "Dave Ast",
                "Jens Wein",
                857098187,
                false);
    }
}
