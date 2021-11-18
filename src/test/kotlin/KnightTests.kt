import ForTests.BoardForTests
import org.junit.Test
import kotlin.test.assertEquals

class KnightTests {
    @Test
    fun `Tests horses down rightNleft and up rightNleft`() {
        val sut = BoardForTests().makeMove("Nb1a3")
            .makeMove("Ng1h3")
            .makeMove("Nb8a6")
            .makeMove("Ng8h6")
        assertEquals(
            "r bqkb r" +
                    "pppppppp" +
                    "n      n" +
                    "        " +
                    "        " +
                    "N      N" +
                    "PPPPPPPP" +
                    "R BQKB R", sut.toString()
        )
    }
    @Test
    fun `Tests horses eating down rightNleft and up rightNleft`() {
        val sut = BoardForTests().makeMove("Pa7a5").makeMove("Pa5a4").makeMove("Pa4a3")
            .makeMove("Ph7h5").makeMove("Ph5h4").makeMove("Ph4h3")
            .makeMove("Pa2a4").makeMove("Pa4a5").makeMove("Pa5a6")
            .makeMove("Ph2h4").makeMove("Ph4h5").makeMove("Ph5h6")
            .makeMove("Nb1a3")
            .makeMove("Ng1h3")
            .makeMove("Nb8a6")
            .makeMove("Ng8h6")
        assertEquals(
            "r bqkb r" +
                    " pppppp " +
                    "n      n" +
                    "        " +
                    "        " +
                    "N      N" +
                    " PPPPPP " +
                    "R BQKB R", sut.toString()
        )
    }
    @Test
    fun `Tests horses left upNdown and right upNdown`() {
        val sut = BoardForTests().makeMove("Nb1a3").makeMove("Na3c4")
            .makeMove("Ng1h3").makeMove("Nh3f4")
            .makeMove("Nb8a6").makeMove("Na6c5")
            .makeMove("Ng8h6").makeMove("Nh6f5")
        assertEquals(
            "r bqkb r" +
                    "pppppppp" +
                    "        " +
                    "  n  n  " +
                    "  N  N  " +
                    "        " +
                    "PPPPPPPP" +
                    "R BQKB R", sut.toString()
        )
    }
    @Test
    fun `Tests horses eating left upNdown and right upNdown`() {
        val sut = BoardForTests().makeMove("Pc7c5").makeMove("Pf7f5").makeMove("Pc5c4").makeMove("Pf5f4")
            .makeMove("Nb1a3").makeMove("Na3c4")
            .makeMove("Ng1h3").makeMove("Nh3f4")
            .makeMove("Nc4a3").makeMove("Nf4h3")
            .makeMove("Pc2c4").makeMove("Pf2f4").makeMove("Pc4c5").makeMove("Pf4f5")
            .makeMove("Nb8a6").makeMove("Na6c5")
            .makeMove("Ng8h6").makeMove("Nh6f5")
        assertEquals(
            "r bqkb r" +
                    "pp pp pp" +
                    "        " +
                    "  n  n  " +
                    "        " +
                    "N      N" +
                    "PP PP PP" +
                    "R BQKB R", sut.toString()
        )
    }
}