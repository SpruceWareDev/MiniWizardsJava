package dev.spruce.miniwizards.game.graphics;

import dev.spruce.miniwizards.game.input.InputManager;

import javax.swing.*;

public class Window {

    private JFrame window;

    public Window(String title, int width, int height) {
        window = new JFrame();
        init(title, width, height);
    }

    private void init(String title, int width, int height) {
        window.setSize(width, height);
        window.setTitle(title);
        window.addKeyListener(InputManager.getInstance());
        window.addMouseListener(InputManager.getInstance());
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public JFrame getWindow() {
        return window;
    }
}
