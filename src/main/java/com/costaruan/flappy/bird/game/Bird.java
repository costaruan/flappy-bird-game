/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.costaruan.flappy.bird.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author costaruan
 */
public class Bird {

    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image image;

    public Bird() {
        x = FlappyBird.WIDTH / 2;
        y = FlappyBird.HEIGHT / 2;
        try {
            image = ImageIO.read(new File("src\\main\\java\\com\\costaruan\\flappy\\bird\\game\\images\\bird.png"));
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public void physics() {
        x += vx;
        y += vy;
        vy += 0.5f;
    }

    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(image, Math.round(x - RAD), Math.round(y - RAD), 2 * RAD, 2 * RAD, null);
    }

    public void jump() {
        vy = -8;
    }

    public void reset() {
        x = 640 / 2;
        y = 640 / 2;
        vx = vy = 0;
    }
}
