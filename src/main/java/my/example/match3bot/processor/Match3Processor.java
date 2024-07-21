package my.example.match3bot.processor;

public interface Match3Processor {

    String NO_MOVES_FOUND_ERR_MSG = "No moves found!!!";

    void startProcess();

    default void publicNoMovesFoundErrMsg() {
        System.out.println(NO_MOVES_FOUND_ERR_MSG);
    }
}
