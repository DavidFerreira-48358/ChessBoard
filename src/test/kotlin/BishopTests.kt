import ForTests.BoardForTests
import org.junit.Test
import kotlin.test.assertEquals


class BishopTests {
    @Test
    fun `Bishop moves front right front left back right back left`() {
        val sut = BoardForTests().makeMove("Pb2b4").makeMove("Pg2g4").makeMove("Pb7b5").makeMove("Pg7g5")
            .makeMove("Bc8a6")
            .makeMove("Bf8h6")
            .makeMove("Bc1a3")
            .makeMove("Bf1h3")
        assertEquals(
            "rn qk nr" +
                    "p pppp p" +
                    "b      b" +
                    " p    p " +
                    " P    P " +
                    "B      B" +
                    "P PPPP P" +
                    "RN QK NR", sut.toString()
        )
    }
    @Test
    fun `Bishop eats front right front left`() {
        val sut = BoardForTests().makeMove("Pb2b3").makeMove("Pg2g3")
            .makeMove("Bc1a3").makeMove("Ba3e7")
            .makeMove("Bf1h3").makeMove("Bh3d7")
        assertEquals(
            "rnbqkbnr" +
                    "pppBBppp" +
                    "        " +
                    "        " +
                    "        " +
                    " P    P " +
                    "P PPPP P" +
                    "RN QK NR", sut.toString()
        )
    }
    @Test
    fun `Bishop eats back right back left`() {
        val sut = BoardForTests().makeMove("Pb7b6").makeMove("Pg7g6")
            .makeMove("Bc8a6").makeMove("Ba6e2")
            .makeMove("Bf8h6").makeMove("Bh6d2")
        assertEquals(
            "rn qk nr" +
                    "p pppp p" +
                    " p    p " +
                    "        " +
                    "        " +
                    "        " +
                    "PPPbbPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Bishop cant moves past teammates`() {
        val sut = BoardForTests()
            .makeMove("Bc8a6")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "        " +
                    "        " +
                    "PPPPPPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Bishop cant moves past enemys`() {
        val sut = BoardForTests().makeMove("Pb7b6").makeMove("Pb7b6").makeMove("Pd2d3").makeMove("Pe2e3")
            .makeMove("Bc8a6").makeMove("Bc8e2")
        assertEquals(
            "rn qkbnr" +
                    "p pppppp" +
                    "bp      " +
                    "        " +
                    "        " +
                    "   PP   " +
                    "PPP  PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
}