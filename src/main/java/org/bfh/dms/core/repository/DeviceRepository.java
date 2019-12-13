package org.bfh.dms.core.repository;

import org.bfh.dms.core.domain.Device;

import java.util.List;

/**
 * Repository interface. When the application context is started this interface is automatically mapped by Spring
 * to it's implemented bean. It is used by services in the API layer to access the database since it is not
 * permitted by Onion Architecture principles to access the infrastructure layer from the API layer.
 */
public interface DeviceRepository {

    /**
     * method used to find a device by it's serial number
     * @param serialNumber
     * @return list of devices with serial number
     */
    List<Device> findBySerialNumber(String serialNumber);

    /**
     * method saves a device to the database
     * @param device
     * @return saved device
     */
    Device save(Device device);

    /**
     * method to return all devices present in DB
     * @return list of all present devices
     */
    List<Device> findAll();

    List<Device> saveAll(List<Device> devices);
}
