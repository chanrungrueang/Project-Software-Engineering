package nl.tudelft.jpacman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.ui.*;
import nl.tudelft.jpacman.ui.Action;
import org.w3c.dom.Text;

import javax.swing.*;

/**
 * Creates and launches the JPacMan UI.
 * 
 * @author Jeroen Roosen
 */
@SuppressWarnings("PMD.TooManyMethods")
public class Launcher {
    private static PacManSprites SPRITE_STORE = new PacManSprites();

    public static String DEFAULT_MAP = "/board.txt";

    public static Integer NUMBER_MAP = 1;
    private static String levelMap = DEFAULT_MAP;
    private static PacManUiBuilder builder =null;
    private static PacManUI pacManUI;
    private static Game game;

    private static Dialog d;
    private static boolean again=false;
    /**
     * @return The game object this launcher will start when {@link #launch()}
     *         is called.
     */
    public static Game getGame() {
        return game;
    }

    /**
     * The map file used to populate the level.
     *
     * @return The name of the map file.
     */
    protected static String getLevelMap() {
        return levelMap;
    }
    public void setLevelMap(String DEFAULT_MAP) {
        this.DEFAULT_MAP = DEFAULT_MAP;
    }
    /**
     * Set the name of the file containing this level's map.
     *
     * @param fileName
     *            Map to be used.
     * @return Level corresponding to the given map.
     */
    public Launcher withMapFile(String fileName) {
        levelMap = fileName;
        return this;
    }

    /**
     * Creates a new game using the level from {@link #makeLevel()}.
     *
     * @return a new Game.
     */
    public static Game makeGame() {
        GameFactory gf = getGameFactory();
        Level level = makeLevel();
        game = gf.createSinglePlayerGame(level, loadPointCalculator());
        return game;
    }

    private static PointCalculator loadPointCalculator() {
        return new PointCalculatorLoader().load();
    }

    /**
     * Creates a new level. By default this method will use the map parser to
     * parse the default board stored in the <code>board.txt</code> resource.
     *
     * @return A new level.
     */
    public static Level makeLevel() {
        try {
            return getMapParser().parseMap(getLevelMap());
        } catch (IOException e) {
            throw new PacmanConfigurationException(
                    "Unable to create level, name = " + getLevelMap(), e);
        }
    }

    /**
     * @return A new map parser object using the factories from
     *         {@link #getLevelFactory()} and {@link #getBoardFactory()}.
     */
    protected static MapParser getMapParser() {
        return new MapParser(getLevelFactory(), getBoardFactory());
    }

    /**
     * @return A new board factory using the sprite store from
     *         {@link #getSpriteStore()}.
     */
    protected static BoardFactory getBoardFactory() {
        return new BoardFactory(getSpriteStore());
    }

    /**
     * @return The default {@link PacManSprites}.
     */
    protected static PacManSprites getSpriteStore() {
        return SPRITE_STORE;
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}
     *         and the ghosts from {@link #getGhostFactory()}.
     */
    protected static LevelFactory getLevelFactory() {
        return new LevelFactory(getSpriteStore(), getGhostFactory(), loadPointCalculator());
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}.
     */
    protected static GhostFactory getGhostFactory() {
        return new GhostFactory(getSpriteStore());
    }

    /**
     * @return A new factory using the players from {@link #getPlayerFactory()}.
     */
    protected static GameFactory getGameFactory() {
        return new GameFactory(getPlayerFactory());
    }

    /**
     * @return A new factory using the sprites from {@link #getSpriteStore()}.
     */
    protected static PlayerFactory getPlayerFactory() {
        return new PlayerFactory(getSpriteStore());
    }

    /**
     * Adds key events UP, DOWN, LEFT and RIGHT to a game.
     *
     * @param builder
     *            The {@link PacManUiBuilder} that will provide the UI.
     */
    protected static void addSinglePlayerKeys(final PacManUiBuilder builder) {
        builder.addKey(KeyEvent.VK_UP, moveTowardsDirection(Direction.NORTH))
                .addKey(KeyEvent.VK_DOWN, moveTowardsDirection(Direction.SOUTH))
                .addKey(KeyEvent.VK_LEFT, moveTowardsDirection(Direction.WEST))
                .addKey(KeyEvent.VK_RIGHT, moveTowardsDirection(Direction.EAST));
    }

    private static Action moveTowardsDirection(Direction direction) {
        return () -> {
            assert game != null;
            getGame().move(getSinglePlayer(getGame()), direction);
        };
    }

    private static Player getSinglePlayer(final Game game) {
        List<Player> players = game.getPlayers();
        if (players.isEmpty()) {
            throw new IllegalArgumentException("Game has 0 players.");
        }
        return players.get(0);
    }

    /**
     * Creates and starts a JPac-Man game.
     */
    public static void launch() {
        makeGame();
        builder = new PacManUiBuilder().withDefaultButtons();
        addSinglePlayerKeys(builder);
        pacManUI = builder.build(getGame());
        pacManUI.start();
    }

    public static void reset() {
        assert pacManUI != null;
        pacManUI.dispose();
        SPRITE_STORE = new PacManSprites();
        NUMBER_MAP +=1;
        if(NUMBER_MAP<=5){
            DEFAULT_MAP = "/board"+NUMBER_MAP+".txt";
            System.out.println("Map No "+NUMBER_MAP);
        }

        builder =null;
        levelMap = DEFAULT_MAP;
    }

    /**
     * Disposes of the UI. For more information see
     * {@link javax.swing.JFrame#dispose()}.
     *
     * Precondition: The game was launched first.
     */
    public void dispose() {
        assert pacManUI != null;
        pacManUI.dispose();
    }

    public static void alertWon(){
        Frame f= new Frame();
        d = new Dialog(f , "You Want To Play More?", true);
        d.setLayout( new FlowLayout() );
        Button a = new Button ("OK");
        Button b = new Button ("No");
        a.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                again = true;
                d.setVisible(false);
                d.dispose();
                System.out.println(Launcher.getAgain());

            }
        });
        b.addActionListener ( new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                again = false;
                d.setVisible(false);
                d.dispose();
                System.out.println(Launcher.getAgain());
            }
        });
        d.add(new Label("You Want To Play Next Boards"));
        d.add(a);
        d.add(b);
        d.setSize(200,100);
        d.setVisible(true);
        System.out.println("=====");
    }

    public static void alertLost(){
//        JFrame frame = new JFrame("You Lost !!!");
//        frame.setSize(250, 250);
//        frame.setLocation(300,200);
//        frame.setVisible(true);
//        alertLost alert = new alertLost();
//        alert.show();
        SwingTester test = new SwingTester();
    }

    public static boolean getAgain(){
        return again;
    }

    public static void setAgain(boolean again2){
        again = again2;
    }

    /**
     * Main execution method for the Launcher.
     *
     * @param args
     *            The command line arguments - which are ignored.
     * @throws IOException
     *             When a resource could not be read.
     */
    public static void main(String[] args) throws IOException {

        Launcher start = new Launcher();
        start.launch();
    }
}
