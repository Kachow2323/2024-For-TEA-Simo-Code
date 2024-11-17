package frc.robot.Bobaboard;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Hook;

public class BotControls {

	// ControlHub mControlBoard = ControlHub.getInstance();

	static RobotContainer rContainer = RobotContainer.getInstance();
    Arm arm = Arm.getInstance();
    Hook hook = Hook.getInstance();
    DriveSubsystem m_robotDrive = new DriveSubsystem();
    static ControlHub m_ControlHub = ControlHub.getInstance();
    final static SendableChooser<Boolean> ControllerMode = new SendableChooser<>();
    static boolean OneControllerQuery = true;


    public final static boolean ChooseControllers(){
        ControllerMode.addOption("One Controller", true);
        ControllerMode.addOption("Two Controller(s)", false);
        SmartDashboard.putData("Controller Selection", ControllerMode);
        if (ControllerMode.getSelected() == null){
            OneControllerQuery = true;
        }else{
        Boolean m_ControllerSelected = ControllerMode.getSelected();
        OneControllerQuery = m_ControllerSelected;
        }
        return OneControllerQuery;
    }

    public static void oneControllerMode() {
        if (OneControllerQuery){
            if (m_ControlHub.driverController.A_Button.wasActivated() == true) {
                rContainer.FallOffChain();
            }else if (m_ControlHub.driverController.B_Button.wasActivated() == true){
                rContainer.ClimbChain();
            }else if (m_ControlHub.driverController.X_Button.wasActivated() == true){
                rContainer.StowArm();
            }else if (m_ControlHub.driverController.Y_Button.wasActivated() == true){
                rContainer.ScoreNote();
            }else if (m_ControlHub.driverController.L_Bumper.wasActivated() == true){
                rContainer.IntakeNotePrep();
            }else if (m_ControlHub.driverController.R_Bumper.wasActivated() == true){
                rContainer.IntakeNoteStow();
            }
        }else{
            if (m_ControlHub.operatorController.L_Bumper.wasActivated() == true) {
                rContainer.FallOffChain();
            }else if (m_ControlHub.operatorController.R_Bumper.wasActivated() == true){
                rContainer.ClimbChain();
            }else if (m_ControlHub.operatorController.X_Button.wasActivated() == true){
                rContainer.StowArm();
            }else if (m_ControlHub.operatorController.Y_Button.wasActivated() == true){
                rContainer.ScoreNote();
            }else if (m_ControlHub.driverController.L_Bumper.wasActivated() == true){
                rContainer.IntakeNotePrep();
            }else if (m_ControlHub.driverController.R_Bumper.wasActivated() == true){
                rContainer.IntakeNoteStow();
            }
        }
    }


}
