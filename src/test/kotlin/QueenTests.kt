import ForTests.BoardForTests
import org.junit.Test
import kotlin.test.assertEquals

class QueenTests {
    @Test
    fun `Queen cant move past alies diagonalNVertical`() {
        val sut = BoardForTests()
            .makeMove("Qd1b3")
            .makeMove("Qd1f3")
            .makeMove("Qd1d3")
            .makeMove("Qd8b6")
            .makeMove("Qd8f6")
            .makeMove("Qd8d6")
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
    fun `Queen cant move past enemys diagonalNVertical`() {
        val sut = BoardForTests().makeMove("Pd7d5").makeMove("Pd2d4").makeMove("Pc7c5")
            .makeMove("Qd1d3").makeMove("Qd3c4")
            .makeMove("Qc4e6")
            .makeMove("Qc4d6")

        assertEquals(
            "rnbqkbnr" +
                    "pp  pppp" +
                    "        " +
                    "  pp    " +
                    "  QP    " +
                    "        " +
                    "PPP PPPP" +
                    "RNB KBNR", sut.toString()
        )
    }
    @Test
    fun `Queen eat enemys UpRightlNleft`() {
        val sut = BoardForTests().makeMove("Pd7d5").makeMove("Pd2d4").makeMove("Pc7c5")
            .makeMove("Qd1d3").makeMove("Qd3c4")
            .makeMove("Qc4d5")
            .makeMove("Qd5c5")

        assertEquals(
            "rnbqkbnr" +
                    "pp  pppp" +
                    "        " +
                    "  Q     " +
                    "   P    " +
                    "        " +
                    "PPP PPPP" +
                    "RNB KBNR", sut.toString()
        )
    }
    @Test
    fun `Queen eat enemys UpleftlNRight`() {
        val sut = BoardForTests().makeMove("Pd7d5").makeMove("Pd2d4").makeMove("Pe7e5")
            .makeMove("Qd1d3").makeMove("Qd3e4")
            .makeMove("Qe4d5")
            .makeMove("Qd5e5")

        assertEquals(
            "rnbqkbnr" +
                    "ppp  ppp" +
                    "        " +
                    "    Q   " +
                    "   P    " +
                    "        " +
                    "PPP PPPP" +
                    "RNB KBNR", sut.toString()
        )
    }
    @Test
    fun `Queen eat enemys Up`() {
        val sut = BoardForTests().makeMove("Pd2d4")
            .makeMove("Qd1d3").makeMove("Qd3e4")
            .makeMove("Qe4e7")
        assertEquals(
            "rnbqkbnr" +
                    "ppppQppp" +
                    "        " +
                    "        " +
                    "   P    " +
                    "        " +
                    "PPP PPPP" +
                    "RNB KBNR", sut.toString()
        )
    }
    @Test
    fun `Queen eat enemys down`() {
        val sut = BoardForTests().makeMove("Pd7d5")
            .makeMove("Qd8d6").makeMove("Qd6e5")
            .makeMove("Qe5e2")
        assertEquals(
            "rnb kbnr" +
                    "ppp pppp" +
                    "        " +
                    "   p    " +
                    "        " +
                    "        " +
                    "PPPPqPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Queen eat enemys downleftNdownright`() {
        val sut = BoardForTests().makeMove("Pd7d5").makeMove("Pc2c4").makeMove("Pc4c5")
            .makeMove("Pe2e4").makeMove("Pe4e5")
            .makeMove("Qd8d6").makeMove("Qd6e5")
            .makeMove("Qe5d6").makeMove("Qd6c5")
        assertEquals(
            "rnb kbnr" +
                    "ppp pppp" +
                    "        " +
                    "  qp    " +
                    "        " +
                    "        " +
                    "PP P PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
}