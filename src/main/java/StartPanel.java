import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPanel extends JPanel {
    private Image backgroundImage;
    private JLabel startButton;
    private float alpha = 1.0f; // Transparency for the blink effect
    private boolean fading = true; // Direction of fading

    public StartPanel(ActionListener startAction) {
        this.backgroundImage = new ImageIcon("images/title_screen_V.3.jpg").getImage();
        this.setLayout(null);

        startButton = new JLabel("Click to start");
        startButton.setFont(new Font("Tahoma", Font.BOLD, 40));
        startButton.setBounds(200, 100, 300, 100);
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.setVerticalAlignment(SwingConstants.CENTER);

        // Adding the startButton to the panel
        this.add(startButton);

        // Make the entire panel clickable
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                startAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });

        // Create a timer to update the transparency for the blink effect
        Timer timer = new Timer(50, e -> blinkText());
        timer.start();
    }

    private void blinkText() {
        // Adjust alpha for fading effect
        if (fading) {
            alpha -= 0.05f;
            if (alpha <= 0.1f) { // Minimum alpha
                alpha = 0.1f;
                fading = false;
            }
        } else {
            alpha += 0.05f;
            if (alpha >= 1.0f) { // Maximum alpha
                alpha = 1.0f;
                fading = true;
            }
        }
        // Update the foreground color with the new alpha value
        startButton.setForeground(new Color(251, 240, 194, (int)(alpha * 255)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
