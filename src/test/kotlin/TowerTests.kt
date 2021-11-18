import ForTests.BoardForTests
import org.junit.Test
import kotlin.test.assertEquals

class TowerTests {
    @Test
    fun `Towers to front and middle`() {
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Pa7a5").makeMove("Ph2h4").makeMove("Ph7h5")
            .makeMove("Ra1a3").makeMove("Rh1h3").makeMove("Ra8a6").makeMove("Rh8h6")
            .makeMove("Ra3d3").makeMove("Rh3e3").makeMove("Ra6d6").makeMove("Rh6e6")
        assertEquals(
            " nbqkbn " +
                    " pppppp " +
                    "   rr   " +
                    "p      p" +
                    "P      P" +
                    "   RR   " +
                    " PPPPPP " +
                    " NBQKBN ", sut.toString()
        )
    }
    @Test
    fun `Towers cant move past other pieces`() {
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Pa7a5").makeMove("Pd7d5").makeMove("Ph2h4")
            .makeMove("Ra1a6").makeMove("Ra8a3").makeMove("Rh1h3").makeMove("Rh3d3").makeMove("Rd3d6")
        assertEquals(
            "rnbqkbnr" +
                    " pp pppp" +
                    "        " +
                    "p  p    " +
                    "P      P" +
                    "   R    " +
                    " PPPPPP " +
                    "RNBQKBN ", sut.toString()
        )
    }
    @Test
    fun `Tower black eat from front and left side`() {
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Pa7a5").makeMove("Ph2h4")
            .makeMove("Ra8a6").makeMove("Ra6h6").makeMove("Rh6h4").makeMove("Rh4a4")
        assertEquals(
            " nbqkbnr" +
                    " ppppppp" +
                    "        " +
                    "p       " +
                    "r       " +
                    "        " +
                    " PPPPPP " +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tower black eat from back and right side`() {
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Pa7a5").makeMove("Ph2h4")
            .makeMove("Ra8a6").makeMove("Ra6b6").makeMove("Rb6b3")
            .makeMove("Rb3a3").makeMove("Ra3a4").makeMove("Ra4h4")
        assertEquals(
            " nbqkbnr" +
                    " ppppppp" +
                    "        " +
                    "p       " +
                    "       r" +
                    "        " +
                    " PPPPPP " +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tower white eat from front and left side`() {
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Pa7a5").makeMove("Ph2h4")
            .makeMove("Ra8a6").makeMove("Ra6h6").makeMove("Rh6h4").makeMove("Rh4a4")
        assertEquals(
            " nbqkbnr" +
                    " ppppppp" +
                    "        " +
                    "p       " +
                    "r       " +
                    "        " +
                    " PPPPPP " +
                    "RNBQKBNR", sut.toString()
        )
    }
    @Test
    fun `Tower white eat from front and right side`() {
        val sut = BoardForTests().makeMove("Pa7a5").makeMove("Ph7h5").makeMove("Pc7c6").makeMove("Pf7f6")
            .makeMove("Ph2h4").makeMove("Rh1h3").makeMove("Rh3d3").makeMove("Rd3d6")
            .makeMove("Rd6f6").makeMove("Rf6h6").makeMove("Rh6h5").makeMove("Rh5h6")
            .makeMove("Rh6c6").makeMove("Rc6c3").makeMove("Rc3a3")
            .makeMove("Ra3a5")
        assertEquals(
            "rnbqkbnr" +
                    " p pp p " +
                    "        " +
                    "R       " +
                    "       P" +
                    "        " +
                    "PPPPPPP " +
                    "RNBQKBN ", sut.toString()
        )
    }
    @Test
    fun `Tower cant move in diagonal`() {
        val sut = BoardForTests().makeMove("Pa2a4").makeMove("Ph2h4").makeMove("Pa7a5").makeMove("Ph7h5")
            .makeMove("Ra1a3").makeMove("Ra3b4")
            .makeMove("Rh1h3").makeMove("Rh3f4")
            .makeMove("Ra8a6").makeMove("Ra6b5")
            .makeMove("Rh8h6").makeMove("Rh6f5")
        assertEquals(
            " nbqkbn " +
                    " pppppp " +
                    "r      r" +
                    "p      p" +
                    "P      P" +
                    "R      R" +
                    " PPPPPP " +
                    " NBQKBN ", sut.toString()
        )
    }
}