package command;

public class NaiveCommandHandler implements CommandHandler {

    @Override
    public void handle(Command cmd) {
        cmd.doIt();
    }

}
