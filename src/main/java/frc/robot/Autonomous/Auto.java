package frc.robot.Autonomous;

import frc.robot.subsystems.*;
import frc.robot.*;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.sysid.SysIdRoutineLog.State;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.AutoConstants;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Auto {

public static Command getPathPlannerCommandAmp(boolean blue) {
    if (blue) return new PathPlannerAuto("Simple Auto Part 1");
    return new PathPlannerAuto("Simple Auto Part 1 Red");
  }

  public static Command getPathPlannerCommandExitStartingLine(boolean blue){
    if (blue) return new PathPlannerAuto("Simple Auto Part 2");
    return new PathPlannerAuto("Simple Auto Part 2 Red");
  }

  public static Command RedAmp(){
    return new PathPlannerAuto("Simple Auto Part 1 Red");
  }

  public static Command RedExit(){
    return new PathPlannerAuto("Simple Auto Part 2 Red");
  }
  /**
   * Drives to AMP. Scores 1 NOTE. Leave Starting Line and drives to far side of the *
   * Starting Pos, closest to AMP, hugging the subwoofer
   */

  public static Command ScoreAutoOneNoteAmp(boolean blue){
    return new SequentialCommandGroup(
      Auto.getPathPlannerCommandAmp(blue),
      new WaitCommand(1.),
      RobotContainer.getInstance().scoreHookDelay().withTimeout(2.),
      new WaitCommand(1.),
      new InstantCommand(() -> RobotContainer.getInstance().arm.setArmState(States.ArmPos.STOW), RobotContainer.getInstance().arm),
      new InstantCommand(() -> RobotContainer.getInstance().hook.setHookState(States.HookPos.STOW), RobotContainer.getInstance().hook),
      new WaitCommand(1),
      Auto.getPathPlannerCommandExitStartingLine(blue)
    );
  }
  public static Command driveTime (double xspeed, double ySpeed, double rot, double sec){
    return new RunCommand(
      () -> RobotContainer.getInstance().m_robotDrive.drive(
          xspeed,
          ySpeed,
          rot,
          true,
          true),
      RobotContainer.getInstance().m_robotDrive
      ).withTimeout(sec);
  }

  public static Command driveAutoCommand(boolean blue){

    if (blue) return new PathPlannerAuto("B_DriveAwayStraight3mAuto");
    return new PathPlannerAuto("R_DriveAwayStraight3mAuto");
    // INSERT AUTO NAME INTO THE CHOICE!
  }
  
public static Command RedAmpAuto(){
    return new SequentialCommandGroup(
      Auto.RedAmp(),
      new WaitCommand(1.),
      RobotContainer.getInstance().scoreHookDelay().withTimeout(2.),
      new WaitCommand(1.),
      new InstantCommand(() -> RobotContainer.getInstance().arm.setArmState(States.ArmPos.STOW), RobotContainer.getInstance().arm),
      new InstantCommand(() -> RobotContainer.getInstance().hook.setHookState(States.HookPos.STOW), RobotContainer.getInstance().hook),
      new WaitCommand(1),
      Auto.RedExit()
    );
  }

  }
