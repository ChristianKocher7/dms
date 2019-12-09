package org.bfh.dms.infrastructure.jpa;

import org.bfh.dms.core.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JPA repository interface provided by Spring with additional custom method to find devices by their serial number
 */
public interface JpaDeviceRepository extends JpaRepository<Device, Long> {
    /**
     * method to find all devices with a serial number
     * @param serialNumber
     * @return list of devices with serial number
     */
    List<Device> findBySerialNumber(String serialNumber);
}
