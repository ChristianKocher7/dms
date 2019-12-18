package org.bfh.dms.api.service;

import org.bfh.dms.core.repository.DeviceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImporterServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    private ImporterService importerService;

    @Before
    public void setUp(){
        this.importerService = new ImporterService(deviceRepository);
    }

    @Test
    public void shouldImportAndSaveCsvFile(){
        importerService.importCsv();

        verify(deviceRepository, times(1)).saveAll(anyList());
    }
}
