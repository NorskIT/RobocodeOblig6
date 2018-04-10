package no.hiof.skole.robotcodefirst.lib;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Robo1 extends Robot {

    @Override
    public void run() {
        while(true) {
            ahead(100);
            turnGunRight(360);
            back(100);
            turnGunRight(360);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        System.out.println(e.getBearing());
        fire(1);
    }
}
