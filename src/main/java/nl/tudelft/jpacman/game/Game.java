package nl.tudelft.jpacman.game;

import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Level.LevelObserver;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;


/**
 * A basic implementation of a Pac-Man game.
 *
 * @author Jeroen Roosen
 */
public abstract class Game implements LevelObserver {
    private int stopHour;
    private int stopMinute;

    public int getStopHour() {
        return stopHour;
    }

    public void setStopHour(int stopHour) {
        this.stopHour = stopHour;
    }

    public int getStopMinute() {
        return stopMinute;
    }

    public void setStopMinute(int stopMinute) {
        this.stopMinute = stopMinute;
    }

    public int getStopSecond() {
        return stopSecond;
    }

    public void setStopSecond(int stopSecond) {
        this.stopSecond = stopSecond;
    }

    private int stopSecond;
    private int TotalScore ;

    public int getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(int totalScore) {
        TotalScore = totalScore;
    }

    public int getAllHour() {
        return allHour;
    }

    public void setAllHour(int allHour) {
        this.allHour = allHour;
    }

    public int getAllMinute() {
        return allMinute;
    }

    public void setAllMinute(int allMinute) {
        this.allMinute = allMinute;
    }

    public int getAllSecond() {
        return allSecond;
    }

    public void setAllSecond(int allSecond) {
        this.allSecond = allSecond;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    public Object getProgressLock() {
        return progressLock;
    }

    public PointCalculator getPointCalculator() {
        return pointCalculator;
    }

    public void setPointCalculator(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
    }

    private int allHour = 0;
    private int allMinute = 0;
    private int allSecond = 0;
    /**
     * <code>true</code> if the game is in progress.
     */
    private boolean inProgress;

    /**
     * Object that locks the start and stop methods.
     */
    private final Object progressLock = new Object();

    /**
     * The algorithm used to calculate the points that
     * they player gets whenever some action happens.
     */
    private PointCalculator pointCalculator;

    /**
     * Creates a new game.
     *
     * @param pointCalculator
     *             The way to calculate points upon collisions.
     */
    protected Game(PointCalculator pointCalculator) {
        this.pointCalculator = pointCalculator;
        inProgress = false;
    }

    /**
     * Starts or resumes the game.
     */
    public void start() {
        synchronized (progressLock) {
            if (isInProgress()) {
                return;
            }
            if (getLevel().isAnyPlayerAlive() && getLevel().remainingPellets() > 0) {
                inProgress = true;
                getLevel().addObserver(this);
                getLevel().start();
                int startHour = LocalTime.now().getHour();
                int startMinute = LocalTime.now().getMinute();
                int startSecond = LocalTime.now().getSecond();
                setAllHour(startHour-getAllHour());
                setAllMinute(startMinute-getAllMinute());
                setAllSecond(startSecond-getAllSecond());

                System.out.println("Start time:"+getAllHour()+ " " + getAllMinute() + " " + getAllSecond());


            }
        }


    }

    /**
     * Pauses the game.
     */
    public void stop() {
        synchronized (progressLock) {
            if (!isInProgress()) {
                return;
            }
            inProgress = false;
            int stopHour1 = LocalTime.now().getHour();
            int stopMinute1 = LocalTime.now().getMinute();
            int stopSecond1 = LocalTime.now().getSecond();
            setAllHour(stopHour1-getAllHour());
            setAllSecond(stopSecond1 - getAllSecond());
            setAllMinute(stopMinute1 - getAllMinute());
            //System.out.println(getAllHour()+ " " +getAllMinute()+ " " +getAllSecond());
            getLevel().stop();
//            Launcher.alert();
//            try{
//                if (Launcher.getAgain()){
//                    System.out.println("Again : "+Launcher.getAgain());
//                    Launcher.reset();
//                    Launcher.setAgain(false);
//                    Launcher.launch();
//                }
//                if(Launcher.NUMBER_MAP>5){
//                    System.out.println("Finish !!!");
//                }
//            } catch (Exception e) {
//                System.err.println("Again : "+Launcher.getAgain());
//                throw new RuntimeException(e);
//            }

        }
    }

    /**
     * @return <code>true</code> iff the game is started and in progress.
     */
    public boolean isInProgress() {
        return inProgress;
    }

    /**
     * @return An immutable list of the participants of this game.
     */
    public abstract List<Player> getPlayers();

    /**
     * @return The level currently being played.
     */
    public abstract Level getLevel();

    /**
     * Moves the specified player one square in the given direction.
     *
     * @param player
     *            The player to move.
     * @param direction
     *            The direction to move in.
     */
    public void move(Player player, Direction direction) {
        if (isInProgress()) {
            // execute player move.
            getLevel().move(player, direction);
            pointCalculator.pacmanMoved(player, direction);
        }
    }

    @Override
    public void levelWon() {
        stop();
        Launcher.alertWon();
        try{
            if (Launcher.getAgain()){
                System.out.println("Again : "+Launcher.getAgain());
                Launcher.reset();
                Launcher.setAgain(false);
                Launcher.launch();
            }
            if(Launcher.NUMBER_MAP>5){
                System.out.println("Finish !!!");
            }
        } catch (Exception e) {
            System.err.println("Again : "+Launcher.getAgain());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void levelLost() {
        Player player = getPlayers().get(0);
        int stopHour = LocalTime.now().getHour();
        int stopMinute = LocalTime.now().getMinute();
        int stopSecond = LocalTime.now().getSecond();
        setAllHour(stopHour-getAllHour());
        setAllSecond(stopSecond-getAllSecond());
        setAllMinute(stopMinute-getAllMinute());
        setTotalScore(player.getScore());
        System.out.println(getTotalScore());
        System.out.println("Stop time :" + stopHour+ " " + stopMinute + " " + stopSecond);
        //System.out.println("You lost,your time is :" + getAllHour()+ " " + getAllMinute() + " " + getAllSecond());
        stop();
        Launcher.alertLost();
    }


}
