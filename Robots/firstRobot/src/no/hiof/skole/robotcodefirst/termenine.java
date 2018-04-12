package no.hiof.skole.robotcodefirst;

import robocode.Robot;
import robocode.ScannedRobotEvent;

public class termenine extends Robot {
    boolean lookForEnemy = true;
    boolean turnChooserGun = true;
    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        lookForEnemy = false;
        double enemyLocation = getRadarHeading();
        System.out.println(enemyLocation);

        turnGunLeft(getHeading() - getGunHeading() + event.getBearing());
        fire(3);
        
        if(turnChooserGun) {
            turnChooserGun = false;
        }
        else {
            turnChooserGun = true;
        }
        lookForEnemy = true;
    }

    @Override
    public void run() {
        setAdjustGunForRobotTurn(true);

        while(true) {
<<<<<<< Updated upstream
            turnRadarLeft(360);
=======
            if(lookForEnemy) {
                if(turnChooserGun) {
                    turnGunRight(360);
                }
                else {
                    turnGunLeft(360);
                }
            }

>>>>>>> Stashed changes
        }

    }
}
