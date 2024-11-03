package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
    public CANSparkMax leftElevator;
    public CANSparkMax rightElevator;

    public ElevatorSubsystem() {
        leftElevator = new CANSparkMax(Constants.SubsystemConstants.leftElevatorID, MotorType.kBrushless);
        rightElevator = new CANSparkMax(Constants.SubsystemConstants.rightElevatorID, MotorType.kBrushless);
        
        // set elevator to brake mode :)
        leftElevator.setIdleMode(IdleMode.kBrake);
        rightElevator.setIdleMode(IdleMode.kBrake);

        // If the elevator is moving the wrong way just swap the true and false
        leftElevator.setInverted(false);
        leftElevator.setInverted(false);

    }

    public void runElevatorUp() {
        leftElevator.set(-Constants.SubsystemConstants.elevatorSpeed);
        rightElevator.set(-Constants.SubsystemConstants.elevatorSpeed);

    }

    public void runElevatorDown() {
        leftElevator.set(Constants.SubsystemConstants.elevatorSpeed);
        rightElevator.set(Constants.SubsystemConstants.elevatorSpeed);

    }

    public void stopElevator() {
        leftElevator.stopMotor();
        rightElevator.stopMotor();

    }
}
