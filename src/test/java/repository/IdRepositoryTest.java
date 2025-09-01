package repository;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IdRepositoryTest {
    @Test
    void testInstantiation() {
        assertDoesNotThrow(() -> new IdRepository());
    }
}

