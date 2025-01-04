package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.HookConstants;
import frc.robot.States;
import frc.utils.Util;

public class Hook extends SubsystemBase {
    /* READ ME:
     * Creates 1 SparkMAX objects with their CAN ID and Motor Type
     * We use encoder values from an external Absoulte encoder in the SparkMAX to measure revolutions
     * Use PID Feedback Control Loops to reach hookPosition in single revolutions which we stored in Constants.java
     * All Hook Movements are Absolute
     */
    private static final CANSparkMax motor = Util.createSparkMAX(HookConstants.motorID, MotorType.kBrushless, 20);
    private SparkAbsoluteEncoder hookEncoder = motor.getAbsoluteEncoder(Type.kDutyCycle);
    private RelativeEncoder relHookEncoder = motor.getEncoder();

    /* READ ME:
    * Creates a PID Controller which we use to control the motors movement
    */
    private SparkPIDController pidController;
    public double enable = 0;
    
    
    private static Hook instance;
    public static Hook getInstance() {
        if(instance == null) instance = new Hook();
        return instance;

    }
    
    
    private Hook() {
        resetEncoders();
        motor.setInverted(false);
        motor.setIdleMode(IdleMode.kBrake);
        pidController = motor.getPIDController();
        pidController.setFeedbackDevice(hookEncoder); //Absolute Encoder
        pidController.setP(HookConstants.kP); //Proportianal Gain
        pidController.setI(HookConstants.kI); //Intergral Gain
        pidController.setD(HookConstants.kD); //Derivative Gain
        pidController.setIZone(0);
        pidController.setFF(0);
        pidController.setOutputRange(HookConstants.pidOutputLow, HookConstants.pidOutputHigh); //Max and Min Values for PID Output
        register();
    }


    public void setOpenLoop(double value) {
        SmartDashboard.putNumber("Arm Commanded arm actuation", value);
        motor.set(value);
    }
    
    public void stopArm() {
        setOpenLoop(0);
    }
    
    /**
     * Resets encoders to zero (relative)
     */
    public void resetEncoders() {
        relHookEncoder.setPosition(0.0);
    }
    
    @Override
    public void periodic() {
        //logging
        SmartDashboard.putNumber("hook abs encoder", hookEncoder.getPosition());
        SmartDashboard.putNumber("hook rel encoder", relHookEncoder.getPosition());
        SmartDashboard.putNumber("Hook Voltage", motor.getBusVoltage());
        SmartDashboard.putNumber("Hook Output Current", motor.getOutputCurrent());
        SmartDashboard.putNumber("Score set", enable);
    }
    

    /* READ ME:
    * Select the Setpoint aka reference point for the PID Controller
    */
    public void setHookPosition(double position) {
        pidController.setReference(position, ControlType.kPosition);
        SmartDashboard.putNumber("Hook SetPoint", position);
        enable = position;
    }
    
    public void setHookPositionDegree(double degreePosition) { //define degreePosition earlier
        double convertDeg = 11.375;
        double encoderPosition = degreePosition*convertDeg; //degree to encoder
        double currentPosition = hookEncoder.getPosition(); 
        SmartDashboard.putNumber("degreePosition", degreePosition);
        SmartDashboard.putNumber("encoder value", encoderPosition);
        SmartDashboard.putNumber("current arm position", currentPosition);
        
    }

    /* READ ME:
     * Depending on what we input in (ie: button matching), we can select which setpoint we want to go to.
     */

    public void setHookState(States.HookPos state) {
        switch (state) {
            case STOW:
                setHookPosition(HookConstants.stow);
                break;
            case OPEN:
                setHookPosition(HookConstants.open);
                break;
            default:
                setHookPosition(HookConstants.score);
                break;
        }
    }
}
