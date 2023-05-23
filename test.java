package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class test extends JFrame implements ActionListener, KeyListener {
    boolean isDistyped = false;
    String passage = "";
    String typedPass = "";
    String message = "";

    int typed = 0;
    int disTyped = 0;
    int count = 0;
    int WPM;

    double start;
    double end;
    double elapsed;
    double seconds;

    boolean running;
    boolean ended;

    final int SCREEN_WIDTH;
    final int SCREEN_HEIGHT;
    final int DELAY = 100;

    JButton button;
    Timer timer;
    JLabel label;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(test::new);
    }

    public test() {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SCREEN_WIDTH = 720;
        SCREEN_HEIGHT = 400;
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        Font fontBtn = new Font("MV Boli", Font.BOLD, 30);

        button = new JButton("Start");
        button.setFont(fontBtn);
        button.setForeground(Color.BLUE);
        button.setVisible(true);
        button.addActionListener(this);
        button.setFocusable(false);
        
        button = new JButton("Start");
        button.setFont(fontBtn);
        button.setForeground(Color.BLUE);
        button.setVisible(true);
        button.addActionListener(this);
        button.setFocusable(false);

        label = new JLabel();
        label.setText("Click the Start Button");
        label.setFont(fontBtn);
        label.setVisible(true);
        label.setOpaque(true);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.lightGray);

        this.add(button, BorderLayout.SOUTH);
        this.add(label, BorderLayout.NORTH);
        this.getContentPane().setBackground(Color.WHITE);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setResizable(false);
        this.setTitle("Typing Test");
        this.revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setFont(new Font("MV Boli", Font.BOLD, 25));

        if (running) {

            if (passage.length() > 1) {
                g.drawString(passage.substring(0, 50), g.getFont().getSize(), g.getFont().getSize() * 5);
                g.drawString(passage.substring(50, 100), g.getFont().getSize(), g.getFont().getSize() * 7);
                g.drawString(passage.substring(100, 150), g.getFont().getSize(), g.getFont().getSize() * 9);
                g.drawString(passage.substring(150, 200), g.getFont().getSize(), g.getFont().getSize() * 11);
            }

            g.setColor(Color.GREEN);
            if (typedPass.length() > 0) {
                if (typed < 50)
                    g.drawString(typedPass.substring(0, typed), g.getFont().getSize(), g.getFont().getSize() * 5);
                else
                    g.drawString(typedPass.substring(0, 50), g.getFont().getSize(), g.getFont().getSize() * 5);
            }
            if (typedPass.length() > 50) {
                if (typed < 100)
                    g.drawString(typedPass.substring(50, typed), g.getFont().getSize(), g.getFont().getSize() * 7);
                else
                    g.drawString(typedPass.substring(50, 100), g.getFont().getSize(), g.getFont().getSize() * 7);
            }
            if (typedPass.length() > 100) {
                if (typed < 150)
                    g.drawString(typedPass.substring(100, typed), g.getFont().getSize(), g.getFont().getSize() * 9);
                else
                    g.drawString(typedPass.substring(100, 150), g.getFont().getSize(), g.getFont().getSize() * 9);
            }
            if (typedPass.length() > 150) {
                if (typed < 200)
                    g.drawString(typedPass.substring(150, typed), g.getFont().getSize(), g.getFont().getSize() * 11);
        }

        if (ended) {
            g.setColor(Color.RED);
            if (passage.length() > 1) {
                g.drawString(passage.substring(0, 50), g.getFont().getSize(), g.getFont().getSize() * 5);
                g.drawString(passage.substring(50, 100), g.getFont().getSize(), g.getFont().getSize() * 7);
                g.drawString(passage.substring(100, 150), g.getFont().getSize(), g.getFont().getSize() * 9);
                g.drawString(passage.substring(150, 200), g.getFont().getSize(), g.getFont().getSize() * 11);
            }

            g.setColor(Color.GREEN);
            if (typedPass.length() > 0) {
                if (typed < 50)
                    g.drawString(typedPass.substring(0, typed), g.getFont().getSize(), g.getFont().getSize() * 5);
                else
                    g.drawString(typedPass.substring(0, 50), g.getFont().getSize(), g.getFont().getSize() * 5);
            }
            if (typedPass.length() > 50) {
                if (typed < 100)
                    g.drawString(typedPass.substring(50, typed), g.getFont().getSize(), g.getFont().getSize() * 7);
                else
                    g.drawString(typedPass.substring(50, 100), g.getFont().getSize(), g.getFont().getSize() * 7);
            }
            if (typedPass.length() > 100) {
                if (typed < 150)
                    g.drawString(typedPass.substring(100, typed), g.getFont().getSize(), g.getFont().getSize() * 9);
                else
                    g.drawString(typedPass.substring(100, 150), g.getFont().getSize(), g.getFont().getSize() * 9);
            }
            if (typedPass.length() > 150)
                    g.drawString(typedPass.substring(150, typed), g.getFont().getSize(), g.getFont().getSize() * 11);
        }}
    }
            
    

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            if (!running) {
                passage = generatePassage();
                typedPass = "";
                typed = 0;
                isDistyped = false;
                running = true;
                ended = false;
                start = System.nanoTime();
                timer = new Timer(DELAY, this);
                timer.start();
                button.setText("Stop");
                label.setText("");
                repaint();
            } else {
                timer.stop();
                running = false;
                ended = true;
                button.setText("Start");
                label.setText("Click the Start Button");
                elapsed = System.nanoTime() - start;
                seconds = elapsed / 1000000000.0;
                WPM = (int) ((((double) typed / 5) / seconds) * 60);
                message = "Time Elapsed: " + seconds + " seconds | WPM: " + WPM;
                JOptionPane.showMessageDialog(this, message);
                repaint();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (running) {
        	for(int i=0 ; i<passage.length();i++) {
        		if(passage.charAt(i)==e.getKeyCode()) {
        			System.out.println(e.getKeyCode());
        		}
        	}
        	
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && typed > 0 && !isDistyped) {
                typed--;
                typedPass = typedPass.substring(0, typed);
                isDistyped = true;
            } else if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
                typed++;
                isDistyped = false;
            }
            repaint();
        }
    }

    
    public void keyTyped(KeyEvent e) {
    }

    
    public void keyReleased(KeyEvent e) {
    }

    public String generatePassage() {
        String[] words = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog.",
                "Pack", "my", "box", "with", "five", "dozen", "liquor", "jugs.",
                "Jackdaws", "love", "my", "big", "sphinx", "of", "quartz.",
                "Mr.", "Jock", "TV", "quiz", "PhD.", "bags", "few", "lynx.",
                "Crazy", "Freddie", "said", "my", "mom", "my", "crazy", "friend",
                "One", "morning", "I", "shot", "an", "elephant", "in", "my", "pajamas.",
                "How", "he", "got", "in", "my", "pajamas,", "I'll", "never", "know."};

        Random rand = new Random();
        StringBuilder passage = new StringBuilder();

        for (int i = 0; i < 200; i++) {
            int index = rand.nextInt(words.length);
            passage.append(words[index]).append(" ");
        }

        return passage.toString();
    }
}
