package no.hiof.skole.robotcodefirst;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

public class TestTargeting  extends AdvancedRobot {

    Enemy target;
    final double PI = Math.PI;

    @Override
    public void run() {
        target = new Enemy();

       turnRadarRightRadians(Double.POSITIVE_INFINITY);

        do {
            scan();
        } while (true);
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent event) {
        target.setBearing(event.getBearingRadians());
        target.setDistance(event.getDistance());
        target.setName(event.getName());
        target.setSpeed(event.getVelocity());
        target.setHeading(event.getHeadingRadians());

        double radarTurn = getHeadingRadians() + target.getBearing() - getRadarHeadingRadians();
        setTurnRadarRightRadians(radarTurn);

        System.out.println(target);
    }
}
