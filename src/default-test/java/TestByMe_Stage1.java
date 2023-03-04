import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class TestByMe_Stage1 {
    /*@BeforeEach
    public void setup() {
        Launcher launcher = new Launcher();
        launcher.setLevelMap("/board2.txt");
        launcher.launch();
    }
    */
    @DisplayName("When start Pacman Should walk")
    @Test
    void TC00() throws InterruptedException {
        System.out.println("asd");
        //Game game = launcher.getGame();
        Thread.sleep(2000);

    }
    @Disabled
    @DisplayName("When start Pacman Should walk")
    @Test
    void TC01() throws InterruptedException {
        Launcher launcher = new Launcher();
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        game.start();
        Square Location1 = player.getSquare();
        game.move(player, Direction.EAST);
        Square Location2 = player.getSquare();
        assertNotSame(Location1,Location2);
    }
    @Disabled
    @DisplayName("When pacman eat pellet player should get score")
    @Test
    public void TC03() throws InterruptedException {
        Launcher launcher = new Launcher();
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        // start the game.
        game.start();
        move(game,Direction.WEST,1);
        assertEquals(10,player.getScore());
    }
    @Disabled
    @DisplayName("Test when pacman walk and Die")
    @Test
    public void TC04() throws InterruptedException{
        Launcher launcher = new Launcher();
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        // start the game.
        game.start();
        while(player.isAlive()!=false) {
            move(game,getRandomDirection(),1);
        }
        assertEquals(false,game.isInProgress());
        assertEquals(false,player.isAlive());
    }
    @Disabled
    @DisplayName("When pacman walk and collision wall pacman should cannot thru")
    @Test
    public void TC05() throws InterruptedException{
        Launcher launcher = new Launcher();
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        // start the game.
        game.start();
        Square location1 = player.getSquare();
        move(game,Direction.NORTH,1);
        Square location2 = player.getSquare();
        assertEquals(location1,location2);
    }
    @Disabled
    @DisplayName("Test when pacman walk and Die and Start game again")
    @Test
    public void TC06() throws InterruptedException{
        Launcher launcher = new Launcher();
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        // start the game.
        game.start();
        while(player.isAlive()!=false) {
            move(game,getRandomDirection(),1);
        }
        game.start();
        assertEquals(true,game.isInProgress());
        assertEquals(true,player.isAlive());
    }
    @DisplayName("Test 5 stage")
    @Test
    public void TC07() throws InterruptedException, AWTException {
        Launcher launcher = new Launcher();
        Robot robot = new Robot();
        launcher.launch();
        Game game = launcher.getGame();
        Player player = game.getPlayers().get(0);
        // start the game.
        game.start();
        while(game.getLevel().remainingPellets() != 0) {
            move(game,getRandomDirection(),1);
        }
        robot.delay(10);
        robot.mouseMove(200,80);
        robot.delay(1000);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.keyPress(KeyEvent.VK_SPACE);
        robot.delay(1000);

    }

    private Direction getRandomDirection() {
        return Direction.values()[new Random().nextInt(Direction.values().length)];
    }
    public static void move(Game game, Direction dir, int numSteps) throws InterruptedException {
        Player player = game.getPlayers().get(0);
        for (int i = 0; i < numSteps; i++) {
            game.move(player, dir);
        }
    }
}
