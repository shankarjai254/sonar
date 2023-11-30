package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.model.LocoDataMongo;
import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.repo.LocoMongoRepository;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocoMigrationTest {

    @Mock
    private LocoMongoRepository locoMongoRepository;

    @Mock
    private LocoMySQLRepository locoMySQLRepository;

    @InjectMocks
    private LocoMigration locoMigration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllLocoMongoData() {
        // Mock data
        List<LocoDataMongo> mockData = new ArrayList<>();
        mockData.add(new LocoDataMongo(/* provide necessary data */));

        // Mock behavior of the repository
        when(locoMongoRepository.findAll()).thenReturn(mockData);

        // Call the service method
        List<LocoDataMongo> result = locoMigration.getAllLocoMongoData();

        // Verify the result
        assertEquals(mockData, result);
    }

    @Test
    void testExistsLocoDataMySQLById() {
        // Mock behavior of the repository
        when(locoMySQLRepository.existsById(anyString())).thenReturn(true);

        // Call the service method
        boolean result = locoMigration.existsLocoDataMySQLById("locoCode");

        // Verify the result
        assertTrue(result);
    }

    @Test
    void testSaveLocoDataMySQL() {
        // Mock data
        LocoDataMySQL locoDataMySQL = new LocoDataMySQL(/* provide necessary data */);

        // Call the service method
        locoMigration.saveLocoDataMySQL(locoDataMySQL);

        // Verify that the repository's save method was called
        verify(locoMySQLRepository, times(1)).save(locoDataMySQL);
    }

}
