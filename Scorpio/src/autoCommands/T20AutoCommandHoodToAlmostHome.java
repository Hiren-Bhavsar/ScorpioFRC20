package autoCommands;

import org.usfirst.frc.team20.robot.Scorpio;
import org.usfirst.frc.team20.robot.Team20Libraries.T20Command;

public class T20AutoCommandHoodToAlmostHome extends Scorpio implements T20Command {
	private boolean isFinished, isStarted;

	public T20AutoCommandHoodToAlmostHome() {
		this.isFinished = false;
		this.isStarted = false;
	}

	@Override
	public void execute() {
		if (isFinished) {
			return;
		}

		if (!isStarted) {
			System.out.println("<Hood Moving To Safe Position>");
			hood.moveHoodPositon(hood.HOOD_POS_HOME);
			isStarted = true;
		}
		if (Math.abs(hood.getHoodEnc() - hood.HOOD_POS_HOME) < 2000) {
			System.out.println("</Hood Moving To Safe Position>");
			this.isFinished = true;
		}

	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

	@Override
	public T20Command copy() {
		return new T20AutoCommandHoodToAlmostHome();
	}

}
