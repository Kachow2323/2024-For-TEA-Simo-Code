package frc.robot.Autonomous;
import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public final class AutoModeManager{
    public enum DesiredMode {
		DO_NOTHING,
		TEST_PATH_AUTO,
		GO_PATH_AUTO,
        RETURN_PATH_AUTO,
        ONE_NOTE_AUTO,
        PLAYOFF_AUTO,
        THREE_NOTE
	}

    public static AutoModeManager AutoQueue;
    public static AutoModeManager getInstance() {
        if(AutoQueue == null) AutoQueue = new AutoModeManager();
        return AutoQueue;
    }

    private DesiredMode defaultMode = DesiredMode.DO_NOTHING;
    //private Optional<AutoModeBase> mAutoMode = Optional.empty();
    public static SendableChooser<DesiredMode> mModeChooser = new SendableChooser<>();
    public static Command m_autonomousCommand;
    /** This makes a smart dashboard selection for autos.  */
    public AutoModeManager() {
    mModeChooser.addOption("Do Nothing", DesiredMode.DO_NOTHING);
    mModeChooser.addOption("Test Path", DesiredMode.TEST_PATH_AUTO);
    mModeChooser.addOption("Go Path", DesiredMode.GO_PATH_AUTO);
    mModeChooser.addOption("One Notw", DesiredMode.ONE_NOTE_AUTO);
    mModeChooser.addOption("IgnoreReturn", DesiredMode.RETURN_PATH_AUTO);
    mModeChooser.addOption("Playoff", DesiredMode.PLAYOFF_AUTO);
    mModeChooser.addOption("Three Note Auto", DesiredMode.THREE_NOTE);
    mModeChooser.setDefaultOption("Default Auto", DesiredMode.DO_NOTHING);

    }
    /** If no auto is selected then it would do nothing or else it would do the desired mode. */
    public static void updateAutoMode(){
        DesiredMode desiredMode = mModeChooser.getSelected();
        if (desiredMode == null) {
			    desiredMode = DesiredMode.DO_NOTHING;
        }else{
        System.out.println("Auto Chosen");
        }   
        m_autonomousCommand = grabAutoMode(desiredMode);
    }
    /** This returns the selected auto from the selection. */
    public static Command grabAutoMode(DesiredMode data){
        switch(data){
            case DO_NOTHING:
				m_autonomousCommand = DoNothingCommand.NoAuto();
                break;
            case TEST_PATH_AUTO:
                m_autonomousCommand = DriveTimeCommand.TestAuto();
                break;
            case GO_PATH_AUTO:
				m_autonomousCommand = GoAutoCommand.driveAutoCommand();
                break;
			case ONE_NOTE_AUTO:
				m_autonomousCommand = OneNoteCommand.ScoreAutoOneNoteAmp();
                break;
			case PLAYOFF_AUTO:
				m_autonomousCommand = PlayoffAutoCommand.ScorePlayoffAuto();
                break;
            case THREE_NOTE:
                m_autonomousCommand = ThreeNoteAutoCommand.score3NoteCommand();
                break;
            // case RETURN_PATH_AUTO:
			// 	return Optional.of(new TestPathMode());
            default:
			    System.out.println("ERROR: unexpected auto mode!");
				break;
        }
        return m_autonomousCommand;
    }

    
}