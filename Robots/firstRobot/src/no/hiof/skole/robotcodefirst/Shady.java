package no.hiof.skole.robotcodefirst;


import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.ScannedRobotEvent;

public class Shady extends Robot {
    boolean turnRight = true;
    int opponentsRemaining;
    boolean goFroBroke = false;

    @Override
    public void run() {
        opponentsRemaining = getOthers();

        while(true) {
            if (opponentsRemaining > 1) {
                if (getX() > 40) {

                    moveToCorner();
                } else {
                    upAndDownShooting();
                }
            } else {
                goFroBroke = true;
                turnRadarRight(360);

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

        if(goFroBroke) {
            turnRight(event.getBearing());
            turnGunRight(getHeading() - getGunHeading() + event.getBearing());
            ahead(event.getDistance() + 5);
            fire(2);
            scan();
        }

        fire(2);

    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        opponentsRemaining--;
    }
}
