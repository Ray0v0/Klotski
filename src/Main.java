import control.Game;
import model.Setting;

public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.initialize(new Setting("横刀立马"));
        game.start();
    }
}
