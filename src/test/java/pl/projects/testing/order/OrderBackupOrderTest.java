package pl.projects.testing.order;

import org.junit.jupiter.api.Test;
import pl.projects.testing.order.Order;
import pl.projects.testing.order.OrderBackup;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class OrderBackupOrderTest {

    @Test
    void callingBackupWithoutCreatingAFileFirstShouldThrowException() throws IOException {
        OrderBackup orderBackup = new OrderBackup();

        assertThrows(IOException.class, () -> orderBackup.backupOrder(new Order()));
    }
}
