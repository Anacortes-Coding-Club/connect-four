import game.ConnectFour;
import bots.*;

public class RunGame {
    public static void main(String[] args) {
        ConnectFour match = new ConnectFour(new TryHardBot(), new WalliBot4(), 250);
        match.runGames(1);
    }
}