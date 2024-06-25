import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FlipperGame extends JFrame {

    private World world;
    private FlipperPanel panel;
    private Ball ball;
    private float timeStep = 1.0f / 60.0f;
    private int lives = 3;

    public FlipperGame() {
        setTitle("Pinball");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // create JBox2d world
        world = new World(new Vec2(0, 8.91f));

        // create walls
        Wall[] walls = {
                new Wall(world, new Vec2(0.75f, 0.25f), new Vec2(1.75f, 0.25f)),
                new Wall(world, new Vec2(0.75f, 0.25f), new Vec2(0.25f, 0.75f)),
                new Wall(world, new Vec2(1.75f, 0.25f), new Vec2(2.25f, 0.75f)),
                new Wall(world, new Vec2(0.25f, 0.75f), new Vec2(0.25f, 2.25f)),
                new Wall(world, new Vec2(2.25f, 0.75f), new Vec2(2.25f, 2.25f)),
                new Wall(world, new Vec2(0.25f, 2.25f), new Vec2(0.875f, 2.875f)),
                new Wall(world, new Vec2(2.25f, 2.25f), new Vec2(1.625f, 2.875f))
        };

        // create ball and assign velocity
        ball = new Ball(world, new Vec2(2f, 1.25f), 0.051f);
        ball.setLinearVelocity(new Vec2(0.2f, 0.6f));

        // create flippers
        Flipper[] flippers = {
                // left flipper
                new Flipper(
                        world,
                        0.05f,
                        0.43f,
                        60f,
                        45f,
                        10,
                        1f,
                        false,
                        new Vec2(0.75f,3f)
                ),
                // right flipper
                new Flipper(
                        world,
                        0.05f,
                        0.43f,
                        60f,
                        135f,
                        10,
                        1f,
                        true,
                        new Vec2(1.75f,3f)
                )
        };

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    for (Flipper flipper : flippers) {
                        flipper.flip();
                    }
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    for (Flipper flipper : flippers) {
                        flipper.deflip();
                    }
                }
            }
        });

        // add panel
        panel = new FlipperPanel(world, ball, walls, flippers, lives);
        add(panel);

        // timer
        Timer timer = new Timer(1000 / 60, e -> {
            world.step(timeStep, 6, 2);
            panel.repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        FlipperGame game = new FlipperGame();
        game.setVisible(true);
    }
}
