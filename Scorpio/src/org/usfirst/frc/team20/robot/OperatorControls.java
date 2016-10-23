package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import subsystems.Drivetrain.driveModes;

public class OperatorControls extends Scorpio {

	protected T20GamePad operatorJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 1);
	public double flyspeedHolder = 0, hoodPositonHolder = 0;
	private boolean hoodHelper = false;
	private boolean isBatterShotting = false;

	public OperatorControls() {

	}

	public void opControls() {
		indexer.indexerBumpSwitchWatchDog();
		lance.lanceMovementWatchDog();
		hood.hoodHomeWatchDog();
		// flywheel.getSpeed();
		// lance.lanceSensors();

		// Lance controls
		if (operatorJoy.getOneShotButtonLB() && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			lance.toggleLance();
			if (!lance.getMagSwitchIsExtened() && hood.getHoodEnc() < hood.HOOD_POS_SAFE && hood.hoodIsActuallyHomed) {
				hoodPositonHolder = hood.HOOD_POS_SAFE;
				flyspeedHolder = flywheel.FLYSPEED_STOP;
			}
		}

		// COLLECTOR CONTROL
		if (operatorJoy.getOneShotButtonY()) {
			lance.intakeLance();
			indexer.intakeIndexer(true);
		}

		if (operatorJoy.getOneShotButtonB()) {
			lance.stopIntake();
			indexer.stopIndexer();
		}

		if (operatorJoy.getOneShotButtonX()) {
			lance.stopIntake();
			indexer.stopIndexer();
		}

		if (operatorJoy.getOneShotButtonA()) {
			lance.backDrive();
			indexer.backdriveIndexer();
		}

		if (operatorJoy.getOneShotButtonLS()) {
			flyspeedHolder = flywheel.FLYSPEED_DEMO;
			hoodPositonHolder = hood.HOOD_POS_DEMO;
		}
		// END COLLECTOR CONTROL

		// FLYWHEEL CONTROL
		if (operatorJoy.getOneShotButtonBack()) {
			isBatterShotting = false;
			lance.stopIntake();
			indexer.stopIndexer();
			flyspeedHolder = flywheel.FLYSPEED_STOP;
			hoodHelper = true;
		}

		if (operatorJoy.getOneShotButtonStart()) {
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		// END FLYWHEEL CONTROL

		// FIRE BALL
		if ((operatorJoy.getAxisTrigger() > .8)) {
			flywheel.fire();
		}

		if (operatorJoy.getAxisTrigger() < -.8 && drivetrain.getHeadingOffSet() < 3 && drivetrain.getHeadingOffSet() > 0
				&& flywheel.getSpeed() > 8200) {
			flywheel.fire();
		}
		// END FIRE BALL

		// Hood CONTROL

		if (operatorJoy.getPOV() == 270) {
			isBatterShotting = false;
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_OUTERWORKS;
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (operatorJoy.getPOV() == 90 || isBatterShotting) {
			isBatterShotting = true;
			if (hood.hoodIsActuallyHomed) {
				if (tomahawks.getBatterTomahawk()) {
					hoodPositonHolder = hood.HOOD_POS_BATTER_TOMAHAWKS;
					flyspeedHolder = flywheel.FLYSPEED_BATTER_TOMAHAWKS;
				} else {
					hoodPositonHolder = hood.HOOD_POS_BATTER;
					flyspeedHolder = flywheel.FLYSPEED_BATTER;
				}
			}
		}
		if (operatorJoy.getPOV() == 0) {
			isBatterShotting = false;
			if (hood.hoodIsActuallyHomed)
				hoodPositonHolder = hood.HOOD_POS_THE_6;
			flyspeedHolder = flywheel.FLYSPEED_OUTERWORKS;
		}
		if (operatorJoy.getPOV() == 180) {
			isBatterShotting = false;
			hoodHelper = true;
			flyspeedHolder = flywheel.FLYSPEED_STOP;
		}

		if (hoodHelper) {
			flyspeedHolder = flywheel.FLYSPEED_STOP;
			hoodHelper = false;
			hoodPositonHolder = -2000;
			if (hood.getHoodEnc() > -2100) {
				hood.hoodIsActuallyHomed = false;
			}
		}

		// END HOOD CONTROL

		// RENABLE THE HOOD
		if (operatorJoy.getOneShotButtonLS()) {
			hood.enableHoodControl();
			hoodPositonHolder = hood.getHoodEnc();
		}

		// SET FLYWHEEL SPEED

		flywheel.flywheelToSpeed(flyspeedHolder);

		// SET HOOD POSITION
		if (drivetrain.driveMode != driveModes.CAMERA_TARGET && hood.hoodIsActuallyHomed
				&& !lance.getMagSwitchIsExtened()) {
			hood.moveHoodPositon(hoodPositonHolder);
		} else {
			hoodPositonHolder = hood.getHoodEnc();
		}

		// System.out.println(" hood pos holder: " + hoodPositonHolder + " hood
		// location: " + hood.getHoodEnc()
		// + " flyspeedholder: " + flyspeedHolder);
	}
}
