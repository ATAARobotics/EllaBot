package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSubsystem extends SubsystemBase {
    public CANSparkMax leftElevator;
    public CANSparkMax rightElevator;

    public CANcoder elevatorEncoder;

    private PIDController elevatorPID;

    private final double bottomPosition;
    private final double topPosition;
    private final double middlePosition;

    public enum ElevatorPosition {
        BOTTOM, MIDDLE, TOP
    }

    private enum ElevatorState {
        IDLE, MOVING_TO_BOTTOM, MOVING_TO_MIDDLE, MOVING_TO_TOP
    }

    private ElevatorState currentState = ElevatorState.IDLE;
    private double targetPosition;

    public ElevatorSubsystem() {
        leftElevator = new CANSparkMax(Constants.ElevatorConstants.LEFT_ELEVATOR_ID, MotorType.kBrushless);
        rightElevator = new CANSparkMax(Constants.ElevatorConstants.RIGHT_ELEVATOR_ID, MotorType.kBrushless);

        elevatorEncoder = new CANcoder(Constants.ElevatorConstants.ELEVATOR_ENCODER_ID);

        elevatorPID = new PIDController(Constants.ElevatorConstants.KP, Constants.ElevatorConstants.KI, Constants.ElevatorConstants.KD);

        bottomPosition = Constants.ElevatorConstants.BOTTOM_POSITION;
        middlePosition = Constants.ElevatorConstants.MIDDLE_POSITION;
        topPosition = Constants.ElevatorConstants.TOP_POSITION;

        leftElevator.setIdleMode(IdleMode.kBrake);
        rightElevator.setIdleMode(IdleMode.kBrake);

        leftElevator.setInverted(true);
        rightElevator.setInverted(false);
    }

    public void runElevatorUp() {
        if (elevatorEncoder.getPosition().getValue() >= topPosition) {
            stopElevator();
            return;
        }
        leftElevator.set(Constants.ElevatorConstants.ELEVATOR_SPEED);
        rightElevator.set(Constants.ElevatorConstants.ELEVATOR_SPEED);

        System.out.println(elevatorEncoder.getPosition().getValue());
    }

    public void runElevatorDown() {
        if (elevatorEncoder.getPosition().getValue() <= bottomPosition) {
            stopElevator();
            return;
        }
        leftElevator.set(-Constants.ElevatorConstants.ELEVATOR_SPEED);
        rightElevator.set(-Constants.ElevatorConstants.ELEVATOR_SPEED);

        System.out.println(elevatorEncoder.getPosition().getValue());
    }


    public void goToPosition(ElevatorPosition position) {
        switch (position) {
            case BOTTOM:
                targetPosition = bottomPosition;
                currentState = ElevatorState.MOVING_TO_BOTTOM;
                break;
            case MIDDLE:
                targetPosition = middlePosition;
                currentState = ElevatorState.MOVING_TO_MIDDLE;
                break;
            case TOP:
                targetPosition = topPosition;
                currentState = ElevatorState.MOVING_TO_TOP;
                break;
        }
    }

    public void updateElevator() {
        switch (currentState) {
            case MOVING_TO_BOTTOM:
                if (elevatorEncoder.getPosition().getValue() > bottomPosition) {
                    runElevatorDownPID(bottomPosition);
                } else {
                    stopElevator();
                    currentState = ElevatorState.IDLE;
                }
                break;
            case MOVING_TO_MIDDLE:
                if (elevatorEncoder.getPosition().getValue() > middlePosition + 0.2) {
                    runElevatorDownPID(middlePosition);
                } else if (elevatorEncoder.getPosition().getValue() < middlePosition - 0.2) {
                    runElevatorUpPID(middlePosition);
                } else {
                    stopElevator();
                    currentState = ElevatorState.IDLE;
                }
                break;
            case MOVING_TO_TOP:
                if (elevatorEncoder.getPosition().getValue() < topPosition) {
                    runElevatorUpPID(topPosition);
                } else {
                    stopElevator();
                    currentState = ElevatorState.IDLE;
                }
                break;
            case IDLE:
                // Do nothing
                break;
        }
    }

    private void runElevatorUpPID(double setpoint) {
        double output = elevatorPID.calculate(elevatorEncoder.getPosition().getValue(), setpoint);
        leftElevator.set(output);
        rightElevator.set(output);

        System.out.println(elevatorEncoder.getPosition().getValue());
    }

    private void runElevatorDownPID(double setpoint) {
        double output = elevatorPID.calculate(elevatorEncoder.getPosition().getValue(), setpoint);
        leftElevator.set(output);
        rightElevator.set(output);

        System.out.println(elevatorEncoder.getPosition().getValue());
    }

    public void stopElevator() {
        leftElevator.set(0);
        rightElevator.set(0);
    }
}