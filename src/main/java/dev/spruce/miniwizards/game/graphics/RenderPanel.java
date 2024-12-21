package dev.spruce.miniwizards.game.graphics;

import dev.spruce.miniwizards.game.Game;

import java.awt.*;

public class RenderPanel {

    private Game game;
    private Window window;
    private Canvas canvas;
    private Graphics graphics;
    private boolean running;

    public RenderPanel(Game game, Window window) {
        this.game = game;
        this.window = window;
        initCanvas(window);
        running = true;
    }

    private void initCanvas(Window window) {
        canvas = new Canvas();
        canvas.setSize(window.getWindow().getSize());
        window.getWindow().add(canvas);
        canvas.createBufferStrategy(3);
        canvas.setFocusable(false);
        canvas.setVisible(true);
    }

    public void run() {
        double ns = 1000000000.0 / 60.0;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update(delta);
                updates++;
                delta--;
            }
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println(updates + " ups, " + frames + " fps");
                window.getWindow().setTitle("Mini Wizards | " + updates + " tps, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

    private void update(double delta) {
        game.update(delta);
    }

    private void render() {
        graphics = canvas.getBufferStrategy().getDrawGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, window.getWindow().getSize().width, window.getWindow().getSize().height);
        game.render(graphics);
        canvas.getBufferStrategy().show();
    }
}
