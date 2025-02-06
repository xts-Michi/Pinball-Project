import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EndPanel extends JPanel {
    private JLabel scoreLabel;
    private JButton restartButton;
    private JButton menuButton;
    private Image backgroundImage;

    public EndPanel(ActionListener restartAction, ActionListener menuAction) {
        this.backgroundImage = new ImageIcon("images/end_screen_V.1.jpg").getImage();
        setLayout(null); // Use null layout for absolute positioning

        // Create and position the score label
        scoreLabel = new JLabel("0");
        scoreLabel.setForeground(new Color(251, 240, 194)); // Set the text color using RGB
        scoreLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center text within the label
        add(scoreLabel);

        // Create and position the restart button
        restartButton = new JButton("Restart");
        styleRestartButton(restartButton); // Special design for return button
        restartButton.setBounds(250, 490, 200, 40); // Position below the score label
        restartButton.addActionListener(restartAction);
        add(restartButton);

        // Create and position the menu button
        menuButton = new JButton("Back to Menu");
        styleMenuButton(menuButton); // Special design for menu button
        menuButton.setBounds(250, 440, 200, 40); // Position below the restart button
        menuButton.addActionListener(menuAction);
        add(menuButton);

        // Initial position of the score label
        centerScoreLabel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void setScore(int score) {
        scoreLabel.setText(String.valueOf(score)); // Set only the score value
        centerScoreLabel();
    }

    // Center the score label
    private void centerScoreLabel() {
        // Get the metrics from the graphics
        FontMetrics metrics = scoreLabel.getFontMetrics(scoreLabel.getFont());
        // Determine the X coordinate for the text to be centered, with an added offset
        int offset = 10; // Adjust this value to shift the text further to the right
        int x = (getWidth() - metrics.stringWidth(scoreLabel.getText())) / 2 + offset;
        // Set the bounds for the score label with the new X position
        scoreLabel.setBounds(x, 195, metrics.stringWidth(scoreLabel.getText()), 50);
    }

    // Specific button design for the restart button
    private void styleRestartButton(JButton button) {
        button.setContentAreaFilled(false); // Make the button background transparent
        button.setBorderPainted(false); // Remove the button border
        button.setFocusPainted(false); // Remove the focus border
        button.setOpaque(false); // Make sure the button is not opaque
        button.setForeground(new Color(251, 240, 194)); // Set the text color using RGB for restart button
        button.setFont(new Font("Verdana", Font.BOLD, 20)); // Set the font and size for restart button
    }

    // Specific button design for the menu button
    private void styleMenuButton(JButton button) {
        button.setContentAreaFilled(false); // Make the button background transparent
        button.setBorderPainted(false); // Remove the button border
        button.setFocusPainted(false); // Remove the focus border
        button.setOpaque(false); // Make sure the button is not opaque
        button.setForeground(new Color(251, 240, 194)); // Set the text color using RGB for menu button
        button.setFont(new Font("Verdana", Font.BOLD, 20)); // Set the font and size for menu button
    }
}
