package frc.utils;


import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C.Port;
import frc.robot.Constants;

public class Util {

    /**
     * Create a CANSparkMax with current limiting enabled
     * 
     * @param id the ID of the Spark MAX
     * @param motortype the type of motor the Spark MAX is connected to 
     * @param stallLimit the current limit to set at stall
     * 
     * @return a fully configured CANSparkMAX
     */
    public static CANSparkMax createSparkMAX(int id, MotorType motortype, int stallLimit) {
        CANSparkMax sparkMAX = new CANSparkMax(id, motortype);
        sparkMAX.setSmartCurrentLimit(stallLimit);
        // sparkMAX.restoreFactoryDefaults();
        // sparkMAX.enableVoltageCompensation(voltageCompensation);
        return sparkMAX;
    }

    private static int sparkMAXDefaultCurrentLimit = 40;
    //40 amps - v1.1. | 20-30 amps - 550

    /**
     * Create a CANSparkMax with default current limiting enabled
     * 
     * @param id the ID of the Spark MAX
     * @param motortype the type of motor the Spark MAX is connected to
     * 
     * @return a fully configured CANSparkMAX
     */
    public static CANSparkMax createSparkMAX(int id, MotorType motortype) {
        return createSparkMAX(id, motortype, sparkMAXDefaultCurrentLimit);
    }

    /**
     * Returns value if greater than deadband and 0 if not above threshold
     * @param val
     * @param deadband
     * @return Afflicted Deadband Value
     */
    public static double deadBand(double val, double deadband) {
		return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	}
}