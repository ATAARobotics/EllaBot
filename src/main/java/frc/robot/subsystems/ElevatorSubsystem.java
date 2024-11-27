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

    private final double bottomPosition;
    private final double topPosition;
    private final double middlePosition;

    public enum ElevatorPosition {
        BOTTOM, MIDDLE, TOP
    }

    public ElevatorSubsystem() {
        leftElevator = new CANSparkMax(Constants.ElevatorConstants.leftElevatorID, MotorType.kBrushless);
        rightElevator = new CANSparkMax(Constants.ElevatorConstants.rightElevatorID, MotorType.kBrushless);

        elevatorEncoder = new CANcoder(Constants.ElevatorConstants.elevatorEncoderID);

        bottomPosition = Constants.ElevatorConstants.bottomPosition;
        middlePosition = Constants.ElevatorConstants.middlePosition;
        topPosition = Constants.ElevatorConstants.topPosition;
        
        // set elevator to brake mode :)
        leftElevator.setIdleMode(IdleMode.kBrake);
        rightElevator.setIdleMode(IdleMode.kBrake);

        // If the elevator is moving the wrong way just swap the true and false
        leftElevator.setInverted(false);
        leftElevator.setInverted(false);

    }

    public void runElevatorUp() {
        if (elevatorEncoder.getPosition().getValue() >= topPosition) {
            stopElevator();
            return;
        }
        leftElevator.set(-Constants.ElevatorConstants.elevatorSpeed);
        rightElevator.set(-Constants.ElevatorConstants.elevatorSpeed);

        System.out.println(elevatorEncoder.getPosition().getValue());
    }

    public void runElevatorDown() {
        if (elevatorEncoder.getPosition().getValue() <= bottomPosition) {
            stopElevator();
            return;
        }
        leftElevator.set(Constants.ElevatorConstants.elevatorSpeed);
        rightElevator.set(Constants.ElevatorConstants.elevatorSpeed);

        System.out.println(elevatorEncoder.getPosition().getValue());
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
