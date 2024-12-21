package dev.spruce.miniwizards.game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

public class InputManager implements KeyListener, MouseListener {

    private static InputManager instance;

    private boolean[] keys;
    private CopyOnWriteArrayList<IKeyInput> keySubscribers;

    public void init() {
        keys = new boolean[256];
        keySubscribers = new CopyOnWriteArrayList<>();
    }

    public void subscribe(IKeyInput input) {
        keySubscribers.add(input);
    }

    public void unsubscribe(IKeyInput input) {
        keySubscribers.remove(input);
    }

    public void unsubscribeAll() {
        keySubscribers.clear();
    }

    public boolean isKeyDown(int keyCode) {
        if (keyCode > 0 && keyCode < keys.length - 1)
            return keys[keyCode];
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        for (IKeyInput input : keySubscribers) {
            input.onKeyTyped(e.getKeyCode());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() > 0 && e.getKeyCode() < keys.length - 1) {
            keys[e.getKeyCode()] = true;
            for (IKeyInput input : keySubscribers) {
                input.onKeyPress(e.getKeyCode());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() > 0 && e.getKeyCode() < keys.length - 1) {
            keys[e.getKeyCode()] = false;
            for (IKeyInput input : keySubscribers) {
                input.onKeyRelease(e.getKeyCode());
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static InputManager getInstance() {
        if (instance == null) {
            instance = new InputManager();
        }
        return instance;
    }
}
