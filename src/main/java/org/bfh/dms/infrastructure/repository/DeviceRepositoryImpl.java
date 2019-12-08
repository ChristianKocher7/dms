package org.bfh.dms.infrastructure.repository;

import org.bfh.dms.core.domain.Device;
import org.bfh.dms.core.repository.DeviceRepository;
import org.bfh.dms.infrastructure.jpa.JpaDeviceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation of DeviceRepository interface in the core layer. Is used to access the database from the infrastructure
 * layer.
 */
@Repository
public class DeviceRepositoryImpl implements DeviceRepository {

    /**
     * injected JpaDeviceRepository implemented by Spring context used to access the database
     */
    private JpaDeviceRepository jpaDeviceRepository;

    public DeviceRepositoryImpl(JpaDeviceRepository jpaDeviceRepository) {
        this.jpaDeviceRepository = jpaDeviceRepository;
    }

    /**
     * method used to find a device by it's serial number
     * @param serialNumber
     * @return list of devices with serial number
     */
    @Override
    public List<Device> findBySerialNumber(String serialNumber) {
        return this.jpaDeviceRepository.findBySerialNumber(serialNumber);
    }

    /**
     * method saves a device to the database
     * @param device
     * @return saved device
     */
    @Override
    public Device save(Device device) {
        return this.jpaDeviceRepository.save(device);
    }

    /**
     * method to return all devices present in DB
     * @return list of all present devices
     */
    @Override
    public List<Device> findAll() {
        return this.jpaDeviceRepository.findAll();
    }
}
