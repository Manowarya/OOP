package edu.csf.oop.java.seaBattle.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private static final Logger logger = LoggerFactory.getLogger(Log.class);

    private Log() {}

    public static void infoPlace(int x, int y) {
            logger.info("Place ship: " + x + " " + y);
    }
    public static void infoShoot(int x, int y) {
        logger.info("Shoot: " + x + " " + y);
    }

    public static void startGame() {
        logger.info("Start game");
    }
    public static void endGame() {
        logger.info("End game");
    }
}
