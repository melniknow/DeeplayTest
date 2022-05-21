import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SolutionTest {
    @Test
    public void expected10ForHuman() throws IOException {
        assertEquals(10, Solution.getResult("STWSWTPPTPTTPWPP", "Human"));
    }

    @Test
    public void expected12ForSwamper() throws IOException {
        assertEquals(12, Solution.getResult("STTTSTTTSTTTSSSS", "Swamper"));
    }

    @Test
    public void unknownMapValue() {
        assertThrows(IOException.class, () -> Solution.getResult("STTTSTTThTTTSSSS", "Swamper"));
    }

    @Test
    public void mapError() {
        assertThrows(IOException.class, () -> Solution.getResult("lol", "Swamper"));
    }

    @Test
    public void unknownCharacter() {
        assertThrows(IOException.class, () -> Solution.getResult("STTTSTTTTTTTSSSS", "lol"));
    }
}