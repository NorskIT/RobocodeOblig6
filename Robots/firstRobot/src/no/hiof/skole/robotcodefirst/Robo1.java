package no.hiof.skole.robotcodefirst;


import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Robo1 extends Robot {

    // test av mannøver - forsøke å flykte fra roboter som er for nærme
    @Override
    public void run() {
        while (true) {
            turnRadarRight(360);
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double bearing = e.getBearing();
        // bearing = vinkelforholdet til roboten som er scanna.
        // -180 til 0 er venstre siden, der -180 er rett bak, -90 rett venstre og 0 rett frem
        // 0 til 180 er høyre siden, der 0 er rett frem, 90 er rett høyre og 180 er rett bak.

        //hvis avstand mindre enn 200, styr unna og flytt fram 100
        if (e.getDistance() < 200) {
            if (bearing > 0 && bearing < 90) {
                turnLeft(90-bearing);
                ahead(100);
            }
            if (bearing > 90 && bearing < 180) {
                turnLeft(180-bearing);
                ahead(100);
            }
            if (bearing > -90 && bearing < 0) {
                turnRight(90-bearing*-1);
                ahead(100);
            }
            if (bearing > -180 && bearing < -90) {
                turnRight(180-bearing*-1);
                ahead(100);
            }
        }
    }
}
