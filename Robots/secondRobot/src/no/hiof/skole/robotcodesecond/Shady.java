package no.hiof.skole.robotcodesecond;


import robocode.*;
import robocode.Robot;

import java.awt.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Shady extends Robot {
    boolean turnRight = true;
    int stageOfBattle;
    boolean enemyFound = false;
    boolean resetGunPosition = false;
    boolean top = false;
    boolean left = false;
    boolean inPosition = false;
    boolean areWeGettingRammed = false;
    double lastSpotted;
    int moveCount = 0;
    int ramCounter = 0;
    boolean gunCentered = false;
    int opponentsRemaining;

    @Override
    public void run() {
        while (true) {
            if (getOthers() == 1) {
                pointGunForward();
                executeRamBot();
                stageOfBattle = 1;
            }
            else {
                if(!inPosition) {
                    moveToCorner();
                }
                out.println("upAndDownShooting starting...");
                stageOfBattle = 4;
                upAndDownShooting();
            }
        }

    }

    public void executeRamBot() {
        if (enemyFound) {

        } else {
            turnRadarRight(360);
        }
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        pointGunForward();
        turnRight(event.getBearing());
        fire(3);
        ahead(10);
        inPosition = false;


    }

    public void upAndDownShooting() {

        double maxDistance = getBattleFieldHeight() - 100;
        ahead(maxDistance);
        back(maxDistance);
        out.println("Up and down finnish");
    }

    public void moveToCorner() {
        out.println("Starting moveToCorner");

        // hvis vi er på den nedre halvdel styr nedover
        // hvis ikke styr oppover...
        if(getY() < getBattleFieldHeight() /  2)
            turnRight(normalRelativeAngleDegrees(180 - getHeading()));
        else {
            turnRight(normalRelativeAngleDegrees(-getHeading()));
            top = true;
        }

        //kjør mot veggen, men stopp 50px før
        if (top)
            ahead(getBattleFieldHeight() - getY() - 50);
        else
            ahead(getY()-50);

        //vi er på venstre halvdel
        if (getX() < getBattleFieldWidth() / 2) {
            left = true;
            //er vi på toppen må vi svinge venstre, hvis ikke høyre
            if (top)
                turnLeft(90);
            else
                turnRight(90);
        }

        // vi er på høyre halvdel
        else {
            // er vi på toppen må vi svinge til høyre, hvis ikke venstre
            if (top)
                turnRight(90);
            else
                turnLeft(90);
        }

        // kjør mot veggen men stopp 50px før
        if (left)
            ahead(getX() - 50);
        else
            ahead(getBattleFieldWidth() - getX() - 50);

        // avhengig av hvilket hjørne vi er i må vi snu oss høyre eller venstre
        pointGunForward();
        if((top & !left) | (!top & left)) {
            turnRight(90);
            turnGunRight(90);
        } else {
            turnLeft(90);
            turnGunLeft(90);
        }
        areWeGettingRammed = false;

        inPosition = true;
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        if(stageOfBattle == 4) {
            stageFourScanned(event);
        }
        if(stageOfBattle == 1) {
            stageOneScanned(event);
        }


    }
    public void stageOneScanned(ScannedRobotEvent event) {
        if((lastSpotted - event.getBearing()) < 10) {
            fire(4);
        }
        turnRight(event.getBearing());
        if(event.getDistance() > 150) {
            ahead(150);
        }
        if(event.getDistance() > 50) {
            ahead(75);
            scan();
        }
        if(event.getDistance() < 50) {
            turnGunRight(event.getBearing());
            fire(401/event.getDistance());
            ahead(event.getDistance());
        }
        lastSpotted = event.getBearing();
    }

    @Override
    public void onBulletHit(BulletHitEvent event) {
        
        if(stageOfBattle == 1) {
            if(moveCount == 0) {
                turnLeft(40);
                moveCount++;
            }
            else if (moveCount == 1) {
                turnRight(40);
                moveCount--;
            }
        }
    }

    public void pointGunForward() {
        turnGunRight(-getGunHeading() + getHeading());
        resetGunPosition = false;
    }

    public void stageFourScanned(ScannedRobotEvent event) {
        double turnGunDegrees = 4*event.getVelocity();
        boolean hasShot = false;
        if(event.getHeading() < 100 || event.getHeading() > 250) {
            turnGunRight(turnGunDegrees);

            fire(2);
            hasShot = true;
            turnGunRight(turnGunDegrees*-1);
        }
        if(event.getHeading() > 270 || event.getHeading() < 80) {
            turnGunRight(turnGunDegrees*-1);

            fire(2);
            hasShot = true;
            turnGunRight(turnGunDegrees);
        }
        if(!hasShot) {
            fire(2);
        }
    }
}
