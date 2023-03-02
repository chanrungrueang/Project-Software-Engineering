import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestByMe {
    private Launcher launcher;

    @BeforeEach
    public void setup() {
        launcher = new Launcher();
        launcher.launch();
    }
    @Disabled
    @DisplayName("Test Pacman walk")
    @Test
    void walk() throws InterruptedException {
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);

        // start cleanly.
        assertThat(game.isInProgress()).isFalse();
        game.start();
        assertThat(game.isInProgress()).isTrue();
        assertThat(player.getScore()).isZero();
        // get points
        game.move(player, Direction.EAST);
        assertThat(player.getScore()).isEqualTo(10);
        game.stop();
        Thread.sleep(2000);
        game.move(player, Direction.EAST);
        Thread.sleep(2000);
        assertThat(game.isInProgress()).isFalse();
    }
    @DisplayName("Test when pacman walk")
    @Test
    public void TC401() throws InterruptedException {
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        // start the game.
        game.start();
        // move the player to north direction.
        System.out.println(player.getSquare());
        game.move(player, Direction.WEST);
        System.out.println(player.getSquare());
    }
    public static void move(Game game, Direction dir, int numSteps) throws InterruptedException {
        Player player = game.getPlayers().get(0);
        for (int i = 0; i < numSteps; i++) {
            game.move(player, dir);
        }
    }
    @AfterEach
    public void tearDown () {
        launcher.dispose();
    }
}
