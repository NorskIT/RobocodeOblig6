package no.hiof.skole.robotcodefirst;


import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Shady2 extends Robot {
    boolean top = false;
    boolean left = false;
    boolean inPosition = false;
    boolean goForBroke = false;
    boolean gunCentered = false;
    int opponentsRemaining;

    @Override
    public void run() {
        opponentsRemaining = getOthers();
        moveToCorner();

        while(true) {
            if(!goForBroke)
                upAndDown();
            else
               turnGunRight(360);
        }
    }

    public void upAndDown() {

        double maxDistance = getBattleFieldHeight() - 100;
        ahead(maxDistance);
        back(maxDistance);

    }
    public void moveToCorner() {

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
        if((top & !left) | (!top & left)) {
            turnRight(90);
            turnGunRight(90);
        } else {
            turnLeft(90);
            turnGunLeft(90);
        }

        inPosition = true;
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {

        if(goForBroke) {
            setAdjustGunForRobotTurn(true);
            turnRight(event.getBearing());
            turnGunRight(getHeading() - getGunHeading() + event.getBearing());
            ahead(event.getDistance() + 5);
        }

        customFire(event.getDistance());
    }

    @Override
    public void onRobotDeath(RobotDeathEvent event) {
        opponentsRemaining--;
        if(opponentsRemaining == 1)
            goForBroke = true;
    }

    public void customFire(double distance) {
        if (distance < 100)
            fire(3);
        else if (distance < 150)
            fire(2.5);
        else if (distance < 200)
            fire(2);
        else if (distance < 250)
            fire(1.5);
        else if (distance < 300)
            fire(1);
        else if (distance < 400)
            fire(0.5);
        else
            fire(0.1);
    }

//    @Override
//    public void onHitRobot(HitRobotEvent event) {
//        if(!event.isMyFault()){
//            if (event.getBearing() > -90 && event.getBearing() <= 90) {
//                back(100);
//            } else {
//                ahead(100);
//            }
//        }
//    }
}
