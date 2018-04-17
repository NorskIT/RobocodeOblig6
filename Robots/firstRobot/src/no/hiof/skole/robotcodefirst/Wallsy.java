package no.hiof.skole.robotcodefirst;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;

/**
 * Inspirert og litt lånt av Walls...
 */

public class Wallsy extends Robot {

    double maxDistance;
    int direction = 1;
    int robotsRemaining;

    @Override
    public void run() {

        robotsRemaining = getOthers();
        // moveAmount er maxlengden å bevege seg - det høyeste av enten lengden eller bredden.
        // bevegelse stopper uansett når man møter veggen. Dette erstatter bare et tilfeldig høyt tall.
        maxDistance = Math.max(getBattleFieldWidth(), getBattleFieldHeight());

        // snu så vi oppnår 90 grader vinkel til veggen
        turnLeft(getHeading() % 90);
        ahead(maxDistance);
        turnGunRight(90);
        turnRight(90);

        while (robotsRemaining > 3) {

            ahead((maxDistance / 10) * direction);
            fire(1);
        }
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        direction *= -1;
    }

    @Override
    public void onHitRobot(HitRobotEvent event) {
        direction *= -1;
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        turnLeft(getHeading() % 90);
        ahead(maxDistance);
        turnRight(90);
    }
}

