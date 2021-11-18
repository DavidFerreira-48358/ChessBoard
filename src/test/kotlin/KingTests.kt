import ForTests.BoardForTests
import org.junit.Test
import kotlin.test.assertEquals

class KingTests {
    @Test
    fun `King moves front`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "    K   " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King moves left`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d3")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "   K    " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King moves right`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d3")
            .makeMove("Kd3e3")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "    K   " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King moves back`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3e2")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "        " +
                    "PPPPKPPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King moves front left`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d4")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "   KP   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King moves front right`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3f4")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    PK  " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King moves back left`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3f3")
            .makeMove("Kf3e2")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "        " +
                    "PPPPKPPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King moves back right`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d3")
            .makeMove("Kd3e2")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "        " +
                    "PPPPKPPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    /**
     * Precisa de ser reformulados quando rei n poder mover para check
     */
    @Test
    fun `King eats front`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d3")
            .makeMove("Pd7d5")
            .makeMove("Kd3d4").makeMove("Kd4d5")
        assertEquals(
            "rnbqkbnr" +
                    "ppp pppp" +
                    "        " +
                    "   K    " +
                    "    P   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King eats left`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d3")
            .makeMove("Pc7c5")
            .makeMove("Kd3d4").makeMove("Kd4d5").makeMove("Kd5c5")
        assertEquals(
            "rnbqkbnr" +
                    "pp ppppp" +
                    "        " +
                    "  K     " +
                    "    P   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King eats right`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d3")
            .makeMove("Pe7e5")
            .makeMove("Kd3d4").makeMove("Kd4d5").makeMove("Kd5e5")
        assertEquals(
            "rnbqkbnr" +
                    "pppp ppp" +
                    "        " +
                    "    K   " +
                    "    P   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King eats back`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d3")
            .makeMove("Pc7c5")
            .makeMove("Kd3d4").makeMove("Kd4d5").makeMove("Kd5d6")
            .makeMove("Kd6c6").makeMove("Kc6c5")
        assertEquals(
            "rnbqkbnr" +
                    "pp ppppp" +
                    "        " +
                    "  K     " +
                    "    P   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King eats front left`() {
        val sut = BoardForTests().makeMove("Pe2e4").makeMove("Pd7d5").makeMove("Pd5d4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d4")
        assertEquals(
            "rnbqkbnr" +
                    "ppp pppp" +
                    "        " +
                    "        " +
                    "   KP   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King eats front right`() {
        val sut = BoardForTests().makeMove("Pe2e4").makeMove("Pf7f5").makeMove("Pf5f4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3f4")
        assertEquals(
            "rnbqkbnr" +
                    "ppppp pp" +
                    "        " +
                    "        " +
                    "    PK  " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King eats back left`() {
        val sut = BoardForTests().makeMove("Pe2e4").makeMove("Pd7d5").makeMove("Pd5d4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3f4")
            .makeMove("Kf4e5")
            .makeMove("Ke5d4")
        assertEquals(
            "rnbqkbnr" +
                    "ppp pppp" +
                    "        " +
                    "        " +
                    "   KP   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    @Test
    fun `King eats back right`() {
        val sut = BoardForTests().makeMove("Pe2e4").makeMove("Pf7f5").makeMove("Pf5f4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3d4")
            .makeMove("Kd4e5")
            .makeMove("Ke5f4")
        assertEquals(
            "rnbqkbnr" +
                    "ppppp pp" +
                    "        " +
                    "        " +
                    "    PK  " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
    /**
     * Precisa de ser reformulados quando rei n poder mover para check
     */
    @Test
    fun `King cant eat own team`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Ke1e2")
            .makeMove("Ke2e3")
            .makeMove("Ke3e4")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "    K   " +
                    "PPPP PPP" +
                    "RNBQ BNR", sut.toString()
        )
    }
}