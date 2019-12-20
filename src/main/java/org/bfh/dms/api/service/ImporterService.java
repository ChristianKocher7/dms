package org.bfh.dms.api.service;

import lombok.extern.slf4j.Slf4j;
import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This service is used to import existing devices lists from CSV files
 */
@Slf4j
@Service
public class ImporterService {

    private DeviceRepository deviceRepository;

    @Autowired
    public ImporterService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * imports a Windows Excel generated CSV file from the path resources\import\ARGECPC_Inventar_Client.CSV
     */
    @Transactional
    public void importCsv() {
        File file = new File("C:\\development\\projects\\bfh\\dms\\src\\main\\resources\\import\\ARGECPC_Inventar_Client.CSV");
        List<Device> devices = new ArrayList<>();

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.split(";");

                if (values.length == 17) {
                    Device device = Device.of(parseDate(values[0]),
                            values[1],
                            values[2],
                            values[3],
                            values[4],
                            values[5],
                            values[6],
                            values[7],
                            values[8],
                            values[9],
                            parseDate(values[10]),
                            values[11],
                            parseDate(values[12]),
                            values[13],
                            values[14],
                            values[15],
                            parseBoolean(values[16]));
                    devices.add(device);
                }

            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        }

        log.info("Importing {} new devices...", devices.size());
        deviceRepository.saveAll(devices);
        log.info("Imported {} new devices!", devices.size());
    }

    /**
     * parses a date string to the required format
     * @param date date to be parsed
     * @return parsed date
     */
    private LocalDate parseDate(String date) {
        if (date.isEmpty()) {
            return null;
        }
        if (date.contains(".")) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } else {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    /**
     * parses a string 'true' or 'false' to a boolean
     * @param booleanString boolean string to be parsed
     * @return
     */
    private boolean parseBoolean(String booleanString) {
        if (booleanString.isEmpty()) {
            return false;
        }

        return Boolean.parseBoolean(booleanString);
    }
}
