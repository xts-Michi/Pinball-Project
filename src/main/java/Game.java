import javax.swing.*;
import java.awt.*;

public class Game extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private StartPanel startPanel;
    private GamePanel gamePanel;
    private EndPanel endPanel;
    private GameLogic gameLogic;

    public Game() {
        setTitle("Pinball");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        startPanel = new StartPanel(e -> startGame());
        endPanel = new EndPanel(e -> restartGame(), e -> showStartPanel());

        mainPanel.add(startPanel, "Start");
        mainPanel.add(endPanel, "End");

        add(mainPanel);
        showStartPanel();
    }

    private void showStartPanel() {
        cardLayout.show(mainPanel, "Start");
    }

    private void startGame() {
        gameLogic = new GameLogic(3);
        gamePanel = gameLogic.getGamePanel();
        mainPanel.add(gamePanel, "Game");
        gameLogic.setGameOverListener(this::showEndPanel);
        gameLogic.startGame();
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
    }

    private void showEndPanel() {
        endPanel.setScore(gameLogic.getPoints());
        cardLayout.show(mainPanel, "End");
        }



    private void restartGame() {
        gameLogic.endGame();
        startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}