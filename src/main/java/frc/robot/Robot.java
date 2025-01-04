// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.littletonrobotics.urcl.URCL;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.apriltag.AprilTagDetection;
import edu.wpi.first.apriltag.AprilTagDetector;
import edu.wpi.first.apriltag.AprilTagPoseEstimator;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.estimator.PoseEstimator;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.OIConstants;
import frc.robot.Autonomous.AutoModeManager;
import frc.robot.Autonomous.AutoModeManager.DesiredMode;
import frc.robot.Bobaboard.BotControls;
import frc.robot.Bobaboard.ControlHub;
import frc.robot.subsystems.DriveSubsystem;
import frc.utils.CoordinateSpace;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public RobotContainer m_robotContainer;
  private final ControlHub mControlBoard = ControlHub.getInstance();
  private final BotControls mDriveControls = new BotControls();
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_robotContainer = RobotContainer.getInstance();

    //Advantage Scope Reference:
    DataLogManager.start();
    URCL.start();
    DriverStation.startDataLog(DataLogManager.getLog());
    mDriveControls.PutControllerOption();
  } 
  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   */
  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    mDriveControls.o_reportBotControlData();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    AutoModeManager.updateAutoMode();
    AutoModeManager.m_autonomousCommand.schedule();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  //This writes to smart dashbaord wether or not there are controllers and if there is an option selcted use said controller option.
  @Override
  public void teleopInit() {
    mControlBoard.verifyPossibleControllerInit();
    mDriveControls.selectControllerOption();
  }

  /** This function is called periodically during operator control. */
  //So we check for controller presses and do action accordingly.
  
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putString("ALLIANCE", RobotContainer.isRedAlliance().get().toString());
    mControlBoard.verifyControllerIntegrity();
    mControlBoard.update();
    mDriveControls.RunRobot();
  }
        

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
