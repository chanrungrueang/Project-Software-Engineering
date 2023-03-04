import com.google.common.collect.Lists;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.CollisionMap;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestByMe1 {
    private Level level;

    /**
     * An NPC on this level.
     */
    private final Ghost ghost = mock(Ghost.class);

    /**
     * Starting position 1.
     */
    private final Square square1 = mock(Square.class);

    /**
     * Starting position 2.
     */
    private final Square square2 = mock(Square.class);

    /**
     * The board for this level.
     */
    private final Board board = mock(Board.class);

    /**
     * The collision map.
     */
    private final CollisionMap collisions = mock(CollisionMap.class);

    /**
     * Sets up the level with the default board, a single NPC and a starting
     * square.
     */
    @BeforeEach
    void setUp() {
        final long defaultInterval = 100L;
        level = new Level(board, Lists.newArrayList(ghost), Lists.newArrayList(
            square1, square2), collisions);
        when(ghost.getInterval()).thenReturn(defaultInterval);
    }
    private Launcher launcher;
    @Test
    void Test2()throws InterruptedException {
        launcher = new Launcher();
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        // start the game.
        game.start();

        Thread.sleep(5000);

    }
}
