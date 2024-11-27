package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
    public CANSparkMax leftElevator;
    public CANSparkMax rightElevator;

    public CANcoder elevatorEncoder;

    private final double bottomPosition = 1;
    private final double topPosition = 4.308837890625;
    private final double middlePosition = (topPosition - bottomPosition) / 2;

    public enum ElevatorPosition {
        BOTTOM, MIDDLE, TOP
    }

    public ElevatorSubsystem() {
        leftElevator = new CANSparkMax(Constants.SubsystemConstants.leftElevatorID, MotorType.kBrushless);
        rightElevator = new CANSparkMax(Constants.SubsystemConstants.rightElevatorID, MotorType.kBrushless);

        elevatorEncoder = new CANcoder(Constants.SubsystemConstants.elevatorEncoderID);
        
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

        System.out.println(elevatorEncoder.getPosition().getValue());

        if (elevatorEncoder.getPosition().getValue() >= topPosition) {
            stopElevator();
        }
    }

    public void runElevatorDown() {
        leftElevator.set(Constants.SubsystemConstants.elevatorSpeed);
        rightElevator.set(Constants.SubsystemConstants.elevatorSpeed);

        System.out.println(elevatorEncoder.getPosition().getValue());

        if (elevatorEncoder.getPosition().getValue() <= bottomPosition) {
            stopElevator();
        }
    }

    public void goToPosition(ElevatorPosition position) {
        switch (position) {
            case BOTTOM:
                while (elevatorEncoder.getPosition().getValue() > bottomPosition) {
                    runElevatorDown();
                    if (elevatorEncoder.getPosition().getValue() <= bottomPosition) {
                        stopElevator();
                    }
                }
                break;
            case MIDDLE:
                while (elevatorEncoder.getPosition().getValue() > middlePosition + 0.2) {
                    runElevatorDown();
                } 
                while (elevatorEncoder.getPosition().getValue() < middlePosition - 0.2) {
                    runElevatorUp();
                }
                stopElevator();
                break;
            case TOP:
                while (elevatorEncoder.getPosition().getValue() < topPosition) {
                    runElevatorUp();
                    if (elevatorEncoder.getPosition().getValue() >= topPosition) {
                        stopElevator();
                    }
                }
                break;
        }
    }

    public void stopElevator() {
        leftElevator.stopMotor();
        rightElevator.stopMotor();

    }
}
