/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.costaruan.flappy.bird.game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author costaruan
 */
public class FlappyBird implements ActionListener, KeyListener {

    public static final int FPS = 60, WIDTH = 640, HEIGHT = 480;

    private Bird bird;
    private JFrame frame;
    private JPanel panel;
    private ArrayList<Rectangle> rects;
    private int time, scroll;
    private Timer timer;
    private ImageIcon imageIcon;

    private boolean paused;

    public void go() {
        frame = new JFrame("Flappy Bird");
        bird = new Bird();
        rects = new ArrayList<>();
        panel = new Dashboard(this, bird, rects);

        imageIcon = new ImageIcon("src\\main\\java\\com\\costaruan\\flappy\\bird\\game\\images\\icon.png");

        frame.add(panel);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.setIconImage(imageIcon.getImage());

        paused = true;

        timer = new Timer(1000 / FPS, this);
        timer.start();
    }

    public static void main(String[] args) {
        new FlappyBird().go();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if (!paused) {
            bird.physics();
            if (scroll % 90 == 0) {
                Rectangle r = new Rectangle(WIDTH, 0, Dashboard.PIPE_W, (int) ((Math.random() * HEIGHT) / 5f + (0.2f) * HEIGHT));
                int h2 = (int) ((Math.random() * HEIGHT) / 5f + (0.2f) * HEIGHT);
                Rectangle r2 = new Rectangle(WIDTH, HEIGHT - h2, Dashboard.PIPE_W, h2);
                rects.add(r);
                rects.add(r2);
            }
            ArrayList<Rectangle> toRemove = new ArrayList<>();
            boolean game = true;
            for (Rectangle r : rects) {
                r.x -= 3;
                if (r.x + r.width <= 0) {
                    toRemove.add(r);
                }
                if (r.contains(bird.x, bird.y)) {
                    JOptionPane.showMessageDialog(frame, "You lose!\n" + "Your score was: " + time + ".");
                    game = false;
                }
            }
            rects.removeAll(toRemove);
            time++;
            scroll++;

            if (bird.y > HEIGHT || bird.y + Bird.RAD < 0) {
                game = false;
            }

            if (!game) {
                rects.clear();
                bird.reset();
                time = 0;
                scroll = 0;
                paused = true;
            }
        } else {

        }
    }

    public int getScore() {
        return time;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            bird.jump();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            paused = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public boolean paused() {
        return paused;
    }
}
