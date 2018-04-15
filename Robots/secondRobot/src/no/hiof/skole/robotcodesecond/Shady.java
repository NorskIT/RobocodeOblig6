package no.hiof.skole.robotcodesecond;


import robocode.AdvancedRobot;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Shady extends Robot{
    boolean turnRight = true;

    @Override
    public void run() {
        while(true) {
            if(getX() > 40) {

                moveToCorner();
            }
            else {
                upAndDownShooting();
            }

        }

    }
    public void upAndDownShooting() {
        int mapX = (int) getBattleFieldWidth();
        int mapY = (int) getBattleFieldHeight();
        ahead((mapY-getY())-40);
        back((mapY+getY())+40);
        if((int)getGunHeading() != 90 ) {
            turnGunRight((-getGunHeading())+90);
        }


    }
    public void moveToCorner() {
        turnRight(-(((int) getHeading()) - 180));
        back((((int)-getY())+40));
        turnRight(-(((int) getHeading()) - 270));
        ahead((((int)getX())-30));
        turnRight(-getHeading());
        turnGunRight(90);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        fire(2);
    }
}
