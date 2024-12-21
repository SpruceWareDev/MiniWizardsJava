package dev.spruce.miniwizards.game;

import dev.spruce.miniwizards.game.graphics.RenderPanel;
import dev.spruce.miniwizards.game.graphics.Window;
import dev.spruce.miniwizards.game.input.InputManager;
import dev.spruce.miniwizards.game.state.StateManager;
import dev.spruce.miniwizards.game.state.impl.GameState;

import java.awt.*;

public class Game {

    private Window window;
    private RenderPanel renderPanel;

    private static StateManager stateManager;

    public void init() {
        window = new Window("Mini Wizards", 1280, 720);
        renderPanel = new RenderPanel(this, window);
        stateManager = new StateManager(new GameState(true));
        InputManager.getInstance().init();
        renderPanel.run();
    }

    public void update(double delta) {
        stateManager.update(delta);
    }

    public void render(Graphics graphics) {
        stateManager.render(graphics);
    }

    public static StateManager getStateManager() {
        return stateManager;
    }
}
