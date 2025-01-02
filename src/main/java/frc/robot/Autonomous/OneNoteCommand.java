package frc.robot.Autonomous;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.States;

public class OneNoteCommand {

    public static Command getPathPlannerCommandAmp() {
    return new PathPlannerAuto("Simple Auto Part 1");
  }

  public static Command getPathPlannerCommandExitStartingLine(){
    return new PathPlannerAuto("Simple Auto Part 2");
  }

    public static Command ScoreAutoOneNoteAmp(){
    return new SequentialCommandGroup(
      OneNoteCommand.getPathPlannerCommandAmp(),
      new WaitCommand(1.0),
      RobotContainer.getInstance().Score().withTimeout(2.),
      new WaitCommand(1.),
      // new InstantCommand(() -> RobotContainer.getInstance().arm.setArmState(States.ArmPos.STOW), RobotContainer.getInstance().arm),
      // new InstantCommand(() -> RobotContainer.getInstance().hook.setHookState(States.HookPos.STOW), RobotContainer.getInstance().hook),
      new WaitCommand(1.0),
      OneNoteCommand.getPathPlannerCommandExitStartingLine()
    );
  }
  
}
