package frc.robot.Autonomous;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.States;

public class PlayoffAutoCommand {

    public static Command getPathPlannerCommandFarAmp(){
        return new PathPlannerAuto("PlayoffAuto");
      } 
    
      public static Command getPathPlannerCommandAutoLeave(){
        return new PathPlannerAuto("PlayoffAuto2");
      } 
    
      public static Command ScorePlayoffAuto(){
        return new SequentialCommandGroup(
          new WaitCommand(8.5),
          PlayoffAutoCommand.getPathPlannerCommandFarAmp(),
          new WaitCommand(.2),
          //RobotContainer.getInstance().scoreHookDelay().withTimeout(1.5),
          new WaitCommand(.1),
          // new InstantCommand(() -> RobotContainer.getInstance().arm.setArmState(States.ArmPos.STOW), RobotContainer.getInstance().arm),
          // new InstantCommand(() -> RobotContainer.getInstance().hook.setHookState(States.HookPos.STOW), RobotContainer.getInstance().hook),
          new WaitCommand(0),
          PlayoffAutoCommand.getPathPlannerCommandAutoLeave()
        );
      }
  
}
