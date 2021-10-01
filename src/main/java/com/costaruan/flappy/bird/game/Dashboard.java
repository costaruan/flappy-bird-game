/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.costaruan.flappy.bird.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author costaruan
 */
public class Dashboard extends JPanel {

    private Bird bird;
    private ArrayList<Rectangle> rects;
    private FlappyBird flappyBird;
    private Font scoreFont, pauseFont;
    public static final Color backgroundColor = new Color(115, 200, 207);
    public static final int PIPE_W = 50, PIPE_H = 30;
    private Image pipeHead, pipeLength;

    public Dashboard(FlappyBird flappyBird, Bird bird, ArrayList<Rectangle> rects) {
        this.flappyBird = flappyBird;
        this.bird = bird;
        this.rects = rects;
        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 48);

        try {
            pipeHead = ImageIO.read(new File("src\\main\\java\\com\\costaruan\\flappy\\bird\\game\\images\\pipe.png"));
            pipeLength = ImageIO.read(new File("src\\main\\java\\com\\costaruan\\flappy\\bird\\game\\images\\pipe_part.png"));
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, FlappyBird.WIDTH, FlappyBird.HEIGHT);
        bird.update(g);
        g.setColor(Color.RED);
        rects.forEach(r -> {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);

            AffineTransform old = g2d.getTransform();
            g2d.translate(r.x + PIPE_W / 2, r.y + PIPE_H / 2);
            if (r.y < FlappyBird.HEIGHT / 2) {
                g2d.translate(0, r.height);
                g2d.rotate(Math.PI);
            }
            g2d.drawImage(pipeHead, -PIPE_W / 2, -PIPE_H / 2, Dashboard.PIPE_W, Dashboard.PIPE_H, null);
            g2d.drawImage(pipeLength, -PIPE_W / 2, PIPE_H / 2, Dashboard.PIPE_W, r.height, null);
            g2d.setTransform(old);
        });
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + flappyBird.getScore(), 10, 20);

        if (flappyBird.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0, 0, 0));
            g.drawString("PAUSED", FlappyBird.WIDTH / 2 - 100, FlappyBird.HEIGHT / 2 - 75);
            g.drawString("PRESS SPACE TO BEGIN", FlappyBird.WIDTH / 2 - 300, FlappyBird.HEIGHT / 2 + 100);
        }
    }
}
