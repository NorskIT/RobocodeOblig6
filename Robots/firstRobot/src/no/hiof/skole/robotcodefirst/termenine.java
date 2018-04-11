package no.hiof.skole.robotcodefirst;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class termenine extends Robot {
    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        fire(1);
    }

    @Override
    public void run() {
        while(true) {
            turnRadarLeft(360);
        }
    }
}
