package callmezydd.schedulerreport.service;

import callmezydd.schedulerreport.config.TelegramBot;
import callmezydd.schedulerreport.model.LocoDataMySQL;
import callmezydd.schedulerreport.model.LocoDataMySQLReport;
import callmezydd.schedulerreport.repo.LocoMySQLReportRepository;
import callmezydd.schedulerreport.repo.LocoMySQLRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TelegramReportTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private LocoMySQLReportRepository locoMySQLReportRepository;

    @Mock
    private LocoMySQLRepository locoMySQLRepository;

    @Value("${telegram.bot.chatId}")
    private String chatId;

    private TelegramReport telegramReport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        telegramReport = new TelegramReport(telegramBot, locoMySQLReportRepository);
    }

    @Test
    void testSendReportToTelegram_NoNewData() {
        // Mock data where latestReportDateTime is not after latestRawDateTime
        LocalDateTime latestReportDateTime = LocalDateTime.of(2023, 1, 1, 1, 0);
        LocalDateTime latestRawDateTime = LocalDateTime.of(2023, 1, 1, 0, 0);

        LocoDataMySQLReport mockLocoDataMySQLReport = createMockLocoDataMySQLReport(latestReportDateTime);

        when(locoMySQLReportRepository.findTopByOrderByDateTimeDesc()).thenReturn(mockLocoDataMySQLReport);
        when(locoMySQLRepository.findTopByOrderByDateTimeDesc()).thenReturn(createMockLocoData(latestRawDateTime));

        // Call the method to be tested
        telegramReport.sendReportToTelegram();

        // Verify that the sendMessage method was not called
        verify(telegramBot, never()).sendMessage(eq(chatId), anyString());
    }

    private LocoDataMySQL createMockLocoData(LocalDateTime dateTime) {
        LocoDataMySQL mockLocoData = new LocoDataMySQL();
        // set properties according to your needs
        mockLocoData.setDateTime(dateTime);
        // ...

        return mockLocoData;
    }

    private LocoDataMySQLReport createMockLocoDataMySQLReport(LocalDateTime dateTime) {
        LocoDataMySQLReport mockLocoDataMySQLReport = new LocoDataMySQLReport();
        // set properties according to your needs
        mockLocoDataMySQLReport.setDateTime(dateTime);

        return mockLocoDataMySQLReport;
    }
}
