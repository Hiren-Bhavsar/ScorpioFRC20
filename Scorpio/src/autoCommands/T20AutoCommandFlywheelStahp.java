package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandFlywheelStahp extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private double speed;

	public T20AutoCommandFlywheelStahp(double speed) {
		this.isFinished = false;
		this.isStarted = false;
		this.speed = speed;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Flywheel Going To RPM: " + this.speed + ">");
			isStarted = !isStarted;
		}
		flywheel.flywheelToSpeed(0);
		if (isStarted) {
			System.out.println("</Flywheel Going To RPM: " + this.speed + ">");
			flywheel.flywheelToSpeed(0);
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandFlywheelStahp(this.speed);
	}

}
