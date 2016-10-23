package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

import edu.wpi.first.wpilibj.Timer;

public class T20AutoCommandWaitForBall extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;
	private Timer stopTimer = new Timer();

	public T20AutoCommandWaitForBall() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Intakes Are Intaking>");
			isStarted = !isStarted;
		}
		if (indexer.getIndexerBumpSwitch()) {
			this.isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandWaitForBall();
	}

}
