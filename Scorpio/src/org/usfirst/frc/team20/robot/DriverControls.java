package org.usfirst.frc.team20.robot;

import org.usfirst.frc.team20.robot.Team20Libraries.T20GamePad;

import edu.wpi.first.wpilibj.Timer;
import subsystems.Drivetrain.driveModes;

public class DriverControls extends Scorpio {

	private T20GamePad driverJoy = new T20GamePad(T20GamePad.JS_TYPE_XBOX, 0);

	private double heading = 0;
	private boolean navXOn = true;
	private boolean flashToggler, triToggle = false;
	private Timer flashTimer = new Timer();

	public DriverControls() {
	}

	private long mills = 0;

	public void driverControls() {

		if (driverJoy.getOneShotButtonA()) {
			if (flashToggler) {
				flashA_AH.flashlightOff();
				flashToggler = false;
			} else {
				flashA_AH.flashlightOn();
				flashToggler = true;
			}
		}

		if (driverJoy.getOneShotButtonY()) {
			triToggle = true;
			flashTimer.start();
		}
		if (triToggle) {
			if (flashTimer.get() > 0 && flashTimer.get() < .2) {
				flashA_AH.flashlightOn();
			}
			if (flashTimer.get() > .2 && flashTimer.get() < .4) {
				flashA_AH.flashlightOff();
			}
			if (flashTimer.get() > .4 && flashTimer.get() < .6) {
				flashA_AH.flashlightOn();
			}
			//
			if (flashTimer.get() > .6 && flashTimer.get() < .8) {
				flashA_AH.flashlightOff();
			}
			if (flashTimer.get() > .8 && flashTimer.get() < 1) {
				flashA_AH.flashlightOn();
				flashTimer.stop();
				flashTimer.reset();
				flashToggler = true;
				triToggle = false;
			}
		}
		System.out.println(
				"One side " + drivetrain.getLeftSideEncVal() + " other side " + drivetrain.getRightSideEncVal());

		// System.out.println(" navX" + ahrs.ahrs.getAngle());
		if (Math.abs(driverJoy.getAxisTrigger()) > 0.05 && drivetrain.driveMode != driveModes.CAMERA_TARGET) {
			drivetrain.setRobotCentric();
			heading = drivetrain.getHeading();
			mills = System.currentTimeMillis() + 200;
		} else {
			if (drivetrain.driveMode != driveModes.CAMERA_TARGET && mills < System.currentTimeMillis()) {
				if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {

					heading = drivetrain.getHeading();
				}
				if (ahrs.ahrs.isConnected() && navXOn) {
					// drivetrain.setFieldCentric();
				}
			}
		}

		// SWITCH DRIVE MODE
		if (driverJoy.getOneShotButtonStart()) {
			switch (drivetrain.driveMode) {
			case FIELD_CENTRIC:
				drivetrain.setCameraTargetMode();
				break;
			case ROBOT_CENTRIC:
				heading = drivetrain.getHeading();
				drivetrain.setCameraTargetMode();
				break;
			case CAMERA_TARGET:
				heading = drivetrain.getHeading();
				drivetrain.setRobotCentric();
			}
		}
		// END SWITCH DRIVE MODE

		// RESET SENSORS
		if (driverJoy.getOneShotButtonBack()) {
			ahrs.ahrs.reset();
			heading = ahrs.ahrs.getAngle();
		}

		if (driverJoy.getOneShotButtonA()) {
			drivetrain.resetEncoders();
		}
		// END RESET SENSORS

		if (drivetrain.driveMode == driveModes.ROBOT_CENTRIC) {
			heading = driverJoy.getAxisTrigger();
		}
		drivetrain.drive(driverJoy.getAxisLeftStickY(), heading);

		// TOMAHAWK CONTROL
		if (driverJoy.getButtonB()) {
			tomahawks.actuateTomahawks();
		}
		if (driverJoy.getButtonX()) {
			tomahawks.retractTomahawks();
		}
		// END TOMAHAWK CONTROL
	}

}
