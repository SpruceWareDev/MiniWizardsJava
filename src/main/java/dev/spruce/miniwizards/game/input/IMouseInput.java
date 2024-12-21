package dev.spruce.miniwizards.game.input;

public interface IMouseInput {

        void onMousePress(int button);

        void onMouseRelease(int button);

        void onMouseHold(int button);

        void onMouseMove(int x, int y);

        void onMouseDrag(int x, int y);

        void onMouseScroll(int scroll);
}
