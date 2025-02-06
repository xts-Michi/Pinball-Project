import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameLogic implements ContactListener {
    private static final float MAX_SPEED = 8;

    private World world;
    private GamePanel gamePanel;
    private Ball ball;
    private Wall[] walls;
    private Flipper[] flippers;
    private ScoringCircle[] scoringCircles;
    private Windmill[] windmills;
    private Tube[] tubes;
    private Timer gameTimer;
    private int lives;
    private int points;
    private GameOverListener gameOverListener;

    public GameLogic(int initialLives) {
        this.lives = initialLives;
        this.points = 0;

        initializeGame();
        setUpKeyListeners();
        setUpTimer();

        world.setContactListener(this);
    }

    public void initializeGame() {
        // create JBox2d world
        world = new World(new Vec2(0, 9f));

        // create walls
        walls = new Wall[]{
                new Wall(world, new Vec2(1.93f, 0.4f), new Vec2(5f, 0.4f)), // top
                new Wall(world, new Vec2(1.93f, 0.4f), new Vec2(1.93f, 0.9f)), // top down left
                new Wall(world, new Vec2(5f, 0.4f), new Vec2(5f, 0.9f)), // top down right
                new Wall(world, new Vec2(1.93f, 0.9f), new Vec2(1.5f, 1.2f)), // diagonal top right
                new Wall(world, new Vec2(5f, 0.9f), new Vec2(5.5f, 1.42f)),
                new Wall(world, new Vec2(1.5f, 1.2f), new Vec2(1.5f, 4.5f)), // straight up left
                new Wall(world, new Vec2(5.5f, 1.42f), new Vec2(5.5f, 4.5f)), // straight up right
                new Wall(world, new Vec2(1.5f, 4.5f), new Vec2(2.75f, 5.75f)), // diagonal bottom left
                new Wall(world, new Vec2(5.5f, 4.5f), new Vec2(4.25f, 5.75f)) // diagonal bottom right
        };

        // create ball and assign velocity
        ball = new Ball(world, new Vec2(3.5f, 3.52f), 0.102f);
        ball.setLinearVelocity(new Vec2(0.4f, 1.2f));

        // create flippers
        flippers = new Flipper[]{
                // left flipper
                new Flipper(
                        world,
                        0.1f,
                        0.90f,
                        60f,
                        45f,
                        8,
                        1f,
                        false,
                        new Vec2(2.5f,6f)
                ),
                // right flipper
                new Flipper(
                        world,
                        0.1f,
                        0.90f,
                        60f,
                        135f,
                        8,
                        1f,
                        true,
                        new Vec2(4.5f,6f)
                ),
                new Flipper(
                        world,
                        0.05f,
                        0.23f,
                        60f,
                        125f,
                        30f,
                        2f,
                        true,
                        new Vec2(1.95f, 4.5f)
                ),
                new Flipper(
                        world,
                        0.05f,
                        0.23f,
                        60f,
                        65f,
                        30f,
                        2f,
                        false,
                        new Vec2(7 - 1.95f, 4.5f)
                ),
                new Flipper(
                        world,
                        0.05f,
                        0.395f,
                        60f,
                        40f,
                        30f,
                        2f,
                        false,
                        new Vec2(3.1f, 3.5f)
                ),
                new Flipper(
                        world,
                        0.05f,
                        0.395f,
                        60f,
                        140f,
                        30f,
                        2f,
                        true,
                        new Vec2(3.9f, 3.5f)
                )
        };

        scoringCircles = new ScoringCircle[] {
                new ScoringCircle(world, new Vec2(3.5f, 1.8f), 0.2f, 50),
                new ScoringCircle(world, new Vec2(3.5f, 4.5f), 0.1f, 50),
                new ScoringCircle(world, new Vec2(2.5f, 2.5f), 0.15f, 100),
                new ScoringCircle(world, new Vec2(4.5f, 2.5f), 0.15f, 100),
                new ScoringCircle(world, new Vec2(1.6f, 3.7f), 0.1f, 20),
                new ScoringCircle(world, new Vec2(5.38f, 3.7f), 0.1f, 20),
        };

        windmills = new Windmill[] {
                new Windmill(world, new Vec2(2.8f, 4.8f), 0.5f, 0.06f, 10f),
                new Windmill(world, new Vec2(4.2f, 4.8f), 0.5f, 0.06f, -10f),
                new Windmill(world, new Vec2(3.5f, 2.5f), 0.5f, 0.1f, 10f),
        };

        tubes = new Tube[] {
        new Tube(
                world, new Vec2(0,0), new Vec2(1.8f, 4.7f), new Vec2(1.4f, 4.45f),
                new Vec2(1.6f, 4f), new Vec2(1.7f, 3.8f), 25, 0.3f, true
        ),
                new Tube(
                        world, new Vec2(0,0), new Vec2(5.2f, 4.7f), new Vec2(5.6f, 4.45f),
                        new Vec2(5.4f, 4f), new Vec2(5.3f, 3.8f), 25, 0.3f, false
                )};

        gamePanel = new GamePanel(world, ball, walls, flippers, scoringCircles, lives, points, windmills, tubes);
    }

    private void setUpKeyListeners() {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    for (Flipper flipper : flippers) {
                        flipper.flip();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    for (Flipper flipper : flippers) {
                        flipper.deflip();
                    }
                }
            }
        });
    }

    private void setUpTimer() {
        gameTimer = new Timer(1000/60, e -> {
            world.step(1f / 60f, 6, 2);
            gamePanel.repaint();
            checkBallPosition();
            limitBallSpeed();
        });
    }

    private void limitBallSpeed() {
        Vec2 velocity = ball.getVelocity();
        float speed = velocity.length();
        if (speed > MAX_SPEED) {
            float scale = MAX_SPEED / speed;
            ball.setLinearVelocity(new Vec2(velocity.x * scale, velocity.y * scale));
        }
    }


    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    private void checkBallPosition() {
        if (ball.getPosition().y > 7) {
            resetBall();
            lives--;
            gamePanel.setLives(lives);
            if (lives == 0) {
                gameTimer.stop();
                gameOverListener.gameOver();
            }
        }
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    private void resetBall() {
        ball.setPosition(new Vec2(3.5f, 3.52f));
        ball.setLinearVelocity(new Vec2(0,0));
        gamePanel.setBall(ball);
    }

    public void startGame() {
        gameTimer.start();
    }

    public void endGame() {
        gameTimer.stop();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        Body b1 = f1.getBody();
        Body b2 = f2.getBody();

        Object o1 = b1.getUserData();
        Object o2 = b2.getUserData();

        if (o1.getClass() == Ball.class && o2.getClass() == ScoringCircle.class) {
            ScoringCircle sc = (ScoringCircle) o2;
            Ball ball = (Ball) o1;
            Vec2 ballVel = ball.getVelocity();
            ball.setLinearVelocity(new Vec2((float) (ballVel.x * 1.5), (float) (ballVel.y * 1.5)));
            points += sc.points;
            gamePanel.addPoints(sc.points);
        }
    }

    @Override
    public void endContact(Contact contact) {
        //
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        //
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        //
    }

    public int getPoints() {
        return points;
    }
}
