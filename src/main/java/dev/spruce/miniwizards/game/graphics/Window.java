package dev.spruce.miniwizards.game.graphics;

import dev.spruce.miniwizards.game.input.InputManager;

import javax.swing.*;

public class Window {

    private static Window instance;

    private final JFrame window;

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

    public static void createWindow(String title, int width, int height) {
        instance = new Window(title, width, height);
    }

    public static Window getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Window has not been initialized");
        }
        return instance;
    }
}
