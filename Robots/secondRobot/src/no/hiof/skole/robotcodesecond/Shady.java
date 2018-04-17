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
    boolean firstRunOfUpAndDown = true;
    int opponentsRemaining;

    @Override
    public void run() {
        while (true) {
            //If only 1 person remain, switch to rambot mode.
            if (getOthers() == 1) {
                //Resets gun to normal position.
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
                firstRunOfUpAndDown = true;
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
    //Will start ramming and shooting the robot we collide with.
    @Override
    public void onHitRobot(HitRobotEvent event) {
        pointGunForward();
        turnRight(event.getBearing());
        fire(3);
        ahead(10);
        inPosition = false;


    }
    //Will move up and down on the map after corner condition are met.
    public void upAndDownShooting() {
        if(firstRunOfUpAndDown) {
            if (getWidth() > getBattleFieldWidth() / 2) {
                turnGunRight(-getGunHeading() - 90);
            } else {
                turnGunRight(-getGunHeading() + 90);
            }
            firstRunOfUpAndDown = false;
        }
        double maxDistance = getBattleFieldHeight() - 100;
        ahead(maxDistance);
        back(maxDistance);
        out.println("Up and down finnish");

        out.println(getHeading());
        if(getHeading() == 0.0 || getHeading() == 180.0) {
        }
        else {
            out.println("Should be 0.0 or 180, but is: " + getHeading());
            moveToCorner();
        }
    }
    //Robot will look for the closest wall to move to, then move over to the closest corner. After complete, upAndDownShooting() will be executed.
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
        out.println("moveToCorner Complete!");
    }
    //We have two different attack mode. It depends on how many robots are left.
    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        if(stageOfBattle == 4) {
            stageFourScanned(event);
        }
        if(stageOfBattle == 1) {
            stageOneScanned(event);
        }


    }
    //attack mode for when only 1 enemy remains. Will start ramming and shooting enemy robot.
    public void stageOneScanned(ScannedRobotEvent event) {
        //If enemy robot has not moved(too much), shot again.
        //Very good against stationary robots.
        if((lastSpotted - event.getBearing()) < 10) {
            fire(4);
        }
        //Turn face against enemy robot.
        turnRight(event.getBearing());
        //If more than 150px away, just move.
        if(event.getDistance() > 150) {
            ahead(150);
        }
        //If less than 150, move and do onScannedRobot from start.
        if(event.getDistance() < 150) {
            ahead(75);
            scan();
        }
        //If very close, turn gun towards enemy, fire and move forward.
        if(event.getDistance() < 50) {
            turnGunRight(event.getBearing());
            fire(401/event.getDistance());
            ahead(event.getDistance());
        }
        //Saves the last place enemy bearing was last time onScannedRobot was triggered.
        lastSpotted = event.getBearing();
    }

    //Will try to
    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        setBodyColor(new Color(255,0,0));
        //Prøvde på en unnamanøver, men fant ut at den best taktikken er front end shooting. Hadde nok fungert mye bedre om det hadde vært en advanced robot.

        //If in rambot mode, will try to move left and right for every hit taken.
        /*
        if (stageOfBattle == 1) {
            if (moveCount == 0) {
                turnLeft(40);
                moveCount++;
            } else if (moveCount == 1) {
                turnRight(40);
                moveCount--;

            }
        }
        */
        setBodyColor(new Color(0,0,255));
    }
    @Override
    public void onBulletHit(BulletHitEvent event) {

    }
    //Sets gun position forward
    public void pointGunForward() {
        turnGunRight(-getGunHeading() + getHeading());
        resetGunPosition = false;
    }
    /*
        Attack mode if there exists more than 1 robot on the map.
        If enemy is in sight, gun will be turned slighty towards the direction the enemy robot is heading.
    */
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
