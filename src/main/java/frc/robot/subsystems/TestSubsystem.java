
package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax;

import java.text.DecimalFormat;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.States;
import frc.robot.Constants.TestSubsystemConstants;
import frc.utils.Util;


public class TestSubsystem extends SubsystemBase {
    private static final CANSparkMax motorR = Util.createSparkMAX(TestSubsystemConstants.rightArmMotorID, MotorType.kBrushless); //ID,MotorType
    private static final CANSparkMax motorL = Util.createSparkMAX(TestSubsystemConstants.leftArmMotorID, MotorType.kBrushless); //ID,MotorType
    private RelativeEncoder relEncoder = motorR.getEncoder();

    /* READ ME:
    * Creates a PID Controller which we use to control the motors movement
    */
    private SparkPIDController pidController;
    
    private static TestSubsystem instance;
    public static TestSubsystem getInstance() {
        if(instance == null) instance = new TestSubsystem();
        return instance;

    }
    
    private TestSubsystem() {
        resetEncoders();
        motorL.follow(motorR, true); // Slave motors to leading motor to sync them together
        motorR.setIdleMode(IdleMode.kBrake); //If duty-cyle = 0, e-brake
        motorL.setIdleMode(IdleMode.kBrake); ////If duty-cyle = 0, e-brake
        motorR.setSmartCurrentLimit(20); //AMPS Soft E-limit
        motorL.setSmartCurrentLimit(20); ////AMPS Soft E-limit
        //motorR.setInverted(false);

        pidController = motorR.getPIDController(); //Sets the control of the right motor to speed that the PID controller commanded
        pidController.setP(TestSubsystemConstants.kP); //Proportional Gain 
        pidController.setI(TestSubsystemConstants.kI);// Intergral Gain
        pidController.setD(TestSubsystemConstants.kD); // Derivative Gain
        pidController.setIZone(0); // DW will be 0 for most of the time
        pidController.setOutputRange(TestSubsystemConstants.pidOutputLow, TestSubsystemConstants.pidOutputHigh); // Max and Min output so the robot does rip itself apart
        register(); //Register Subsystem for Command Scheduluer to call in periodic
    }
    /**
     * Runs motors at a value [-1 to 1]. Log current value on SmartDashboard
     */
    public void setOpenLoop(double value) {
        motorR.set(value);
        motorL.set(value);
        SmartDashboard.putNumber("Open-loop Value", value);
    }
    
    /**
     * Runs motors at a value 0 (stop).
     */
    public void stop() {
        setOpenLoop(0);
    }
    
    /**
     * Resets encoders to zero
     */
    public void resetEncoders() {
        relEncoder.setPosition(0.0);
    }
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Relative Encoder value [Rots]", relEncoder.getPosition());
        SmartDashboard.putNumber("Right Motor Current [Amps]", motorR.getOutputCurrent());
    }
    
    /* READ ME:
     * Select the Setpoint aka reference point for the PID Controller
     */
    public void setPosition(double position) {
        pidController.setReference(position, ControlType.kPosition);
        SmartDashboard.putNumber("Current SetPoint", position);
    }

    /* READ ME:
     * Depending on what we input in (ie: button matching), we can select which setpoint we want to go to.
     */
    public void setState(States.TestPos state) {
        SmartDashboard.putNumber("Position", state.val);
        switch (state) {
            case POS1:
                setPosition(TestSubsystemConstants.pos1);
                break;
            default:
                setPosition(TestSubsystemConstants.pos2);
                break;
        }
    }

}
