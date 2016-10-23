package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandDriveTimeRightMoarPower extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speedRight, speedLeft, time, heading;
	private Timer driveTimer = new Timer();

	public T20AutoCommandDriveTimeRightMoarPower(double speedLeft, double speedRight, double time) {
		this.isFinished = false;
		this.isStarted = false;
		this.time = time;
		this.speedLeft = speedLeft;
		this.speedRight = speedRight;

	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<RightMoar: " + " For Time: " + this.time + ">");
			drivetrain.setRobotCentric();
			isStarted = !isStarted;
			driveTimer.start();
		}
		if (driveTimer.get() < this.time) {
			drivetrain.driveRightMoar(this.speedRight, this.speedLeft);
		} else if (driveTimer.get() > this.time) {
			drivetrain.drive(0, 0);
			System.out.println("</RightMoar: " + " For Time: " + this.time + ">");
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandDriveTimeRightMoarPower(this.speedLeft, this.speedRight, this.time);
	}

}
