import game.ConnectFour;
import bots.*;

public class Run100Games {
    public static void main(String[] args) {
        ConnectFour match = new ConnectFour(new TryHardBot(), new WalliBot3());
        match.runGames(100);
    }
}
