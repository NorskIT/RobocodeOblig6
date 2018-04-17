package no.hiof.skole.robotcodefirst;


import robocode.*;

public class Shady extends Robot {
    boolean turnRight = true;
    int opponentsRemaining;
    boolean goForBroke = false;
    int direction = 1;

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
                goForBroke = true;
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

        if(goForBroke) {
            turnRight(event.getBearing());
            turnGunRight(getHeading() - getGunHeading() + event.getBearing());
            ahead(event.getDistance() + 5);
            fire(2);
            scan();
        }

        if (event.getDistance() < 100)
            fire(3);
        else if (event.getDistance() < 150)
            fire(2.5);
        else if (event.getDistance() < 200)
            fire(2);
        else if (event.getDistance() < 250)
            fire(1.5);
        else if (event.getDistance() < 300)
            fire(1);
        else if (event.getDistance() < 400)
            fire(0.5);
        else
            fire(0.1);
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        opponentsRemaining--;
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        direction *= -1;
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        if(!event.isMyFault()){
            if (event.getBearing() > -90 && event.getBearing() <= 90) {
                back(100);
            } else {
                ahead(100);
            }
        }
    }

    @Override
    public void onWin(WinEvent event) {
        while (true) {
            ahead(5);
            back(5);
        }
    }
}
