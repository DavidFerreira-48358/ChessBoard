import ForTests.BoardForTests
import org.junit.Test
import kotlin.test.assertEquals

class PawnTests {
    @Test
    fun `white pawn eats black pawn left`() {
        val sut = BoardForTests().makeMove("Pe2e4").makeMove("Pd7d5").makeMove("Pe4d5")
        assertEquals(
            "rnbqkbnr" +
                    "ppp pppp" +
                    "        " +
                    "   P    " +
                    "        " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `white pawn eats black pawn right`() {
        val sut = BoardForTests().makeMove("Pe2e4").makeMove("Pf7f5").makeMove("Pe4f5")
        assertEquals(
            "rnbqkbnr" +
                    "ppppp pp" +
                    "        " +
                    "     P  " +
                    "        " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `black pawn eats white pawn right`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Pd7d5")
            .makeMove("Pd5e4")
        assertEquals(
            "rnbqkbnr" +
                    "ppp pppp" +
                    "        " +
                    "        " +
                    "    p   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `black pawn eats white pawn left`() {
        val sut = BoardForTests().makeMove("Pe2e4")
            .makeMove("Pf7f5")
            .makeMove("Pf5e4")
        assertEquals(
            "rnbqkbnr" +
                    "ppppp pp" +
                    "        " +
                    "        " +
                    "    p   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests if pawn cant move 4 tiles in 2 moves`() { //ver
        val sut = BoardForTests()
            .makeMove("e2e4")
            .makeMove("e4e6")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "        " +
                    "    P   " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests if pawn can move 3 tiles in 2 moves`() { //ver
        val sut = BoardForTests().makeMove("e2e4").makeMove("e4e5")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "        " +
                    "    P   " +
                    "        " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests white pawn eat forward`() {
        val sut = BoardForTests().makeMove("Pe2e3").makeMove("Pe3e4").makeMove("Pe4e5").makeMove("Pe5e6").makeMove("Pe6e7")
        assertEquals(
            "rnbqkbnr" +
                    "pppppppp" +
                    "    P   " +
                    "        " +
                    "        " +
                    "        " +
                    "PPPP PPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests black pawn eat forward`() {
        val sut = BoardForTests().makeMove("Pe7e6").makeMove("Pe6e5").makeMove("Pe5e4").makeMove("Pe4e3").makeMove("Pe3e2")
        assertEquals(
            "rnbqkbnr" +
                    "pppp ppp" +
                    "        " +
                    "        " +
                    "        " +
                    "    p   " +
                    "PPPPPPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    /**
     * EN PASSANT TESTS
     */
    @Test
    fun `Tests en passant move up left`() {
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Pa4a5")
            .makeMove("Pb7b5")
            .makeMove("Pa5b6")
        assertEquals(
            "rnbqkbnr" +
                    "p pppppp" +
                    " P      " +
                    "        " +
                    "        " +
                    "        " +
                    " PPPPPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests en passant move up right`() {
        val sut = BoardForTests().makeMove("Pb2b4").makeMove("Pb4b5")
            .makeMove("Pa7a5")
            .makeMove("Pb5a6")
        assertEquals(
            "rnbqkbnr" +
                    " ppppppp" +
                    "P       " +
                    "        " +
                    "        " +
                    "        " +
                    "P PPPPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests en passant move down left`() {
        val sut = BoardForTests().makeMove("Pa7a5").makeMove("Pa5a4")
            .makeMove("Pb2b4")
            .makeMove("Pa4b3")
        assertEquals(
            "rnbqkbnr" +
                    " ppppppp" +
                    "        " +
                    "        " +
                    "        " +
                    " p      " +
                    "P PPPPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests en passant move down right`() {
        val sut = BoardForTests().makeMove("Pb7b5").makeMove("Pb5b4")
            .makeMove("Pa2a4")
            .makeMove("Pb4a3")
        assertEquals(
            "rnbqkbnr" +
                    "p pppppp" +
                    "        " +
                    "        " +
                    "        " +
                    "p       " +
                    " PPPPPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tests en passant cant be played after another move`() { // ver este teste
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Pa4a5")
            .makeMove("Pb7b5")
            .makeMove("Pe7e5")
            .makeMove("Pa5b6")
        assertEquals(
            "rnbqkbnr" +
                    "p pp ppp" +
                    "        " +
                    "Pp  p   " +
                    "        " +
                    "        " +
                    " PPPPPPP" +
                    "RNBQKBNR", sut.toString()
        )
    }

}