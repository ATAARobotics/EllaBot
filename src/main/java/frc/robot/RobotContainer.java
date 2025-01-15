// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveRequest;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
// import frc.robot.generated.TunerConstants;
// import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {
    // private double MaxSpeed = TunerConstants.kSpeedAt12VoltsMps; // kSpeedAt12VoltsMps desired top speed
    @SuppressWarnings("unused")
    private double MaxAngularRate = 1.5 * Math.PI; // 3/4 of a rotation per second max angular velocity
    private double deadzone = 0.38; // Deadzone threshold

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final CommandXboxController joystick = new CommandXboxController(0); // My joystick
    // private final CommandSwerveDrivetrain drivetrain = TunerConstants.DriveTrain; // My drivetrain

    // private final LegacySwerveRequest.FieldCentric drive = new LegacySwerveRequest.FieldCentric()
    //     .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
    //     .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // I want field-centric
                                                                // driving in open loop
    private final LegacySwerveRequest.SwerveDriveBrake brake = new LegacySwerveRequest.SwerveDriveBrake();
    private final LegacySwerveRequest.PointWheelsAt point = new LegacySwerveRequest.PointWheelsAt();

    private final ElevatorSubsystem elevator = new ElevatorSubsystem();
    // private final Telemetry logger = new Telemetry(MaxSpeed);
    private final ShooterSubsystem m_Shooter = new ShooterSubsystem();

    private void configureBindings() {
        // drivetrain.setDefaultCommand( // Drivetrain will execute this command periodically
        //     drivetrain.applyRequest(() -> drive.withVelocityX(applyDeadzone(-joystick.getLeftY()) * MaxSpeed) // Drive forward with
        //                                                                                     // negative Y (forward)
        //         .withVelocityY(applyDeadzone(-joystick.getLeftX()) * MaxSpeed) // Drive left with negative X (left)
        //         .withRotationalRate(applyDeadzone(-joystick.getRightX()) * MaxAngularRate) // Drive counterclockwise with negative X (left)
        //     ));

        // joystick.b().whileTrue(drivetrain.applyRequest(() -> brake));

        // elevator control using pov
        joystick.povUp().whileTrue(Commands.run(() -> elevator.runElevatorUp(), elevator));
        joystick.povDown().whileTrue(Commands.run(() -> elevator.runElevatorDown(), elevator));
        joystick.povUp().or(joystick.povDown()).onFalse(Commands.run(() -> elevator.stopElevator(), elevator));

        // elevator run to position
        // y = top, x = middle, a = bottom
        joystick.y().whileTrue(Commands.run(() -> elevator.goToPosition(ElevatorSubsystem.ElevatorPosition.TOP), elevator));
        joystick.x().whileTrue(Commands.run(() -> elevator.goToPosition(ElevatorSubsystem.ElevatorPosition.MIDDLE), elevator));
        joystick.a().whileTrue(Commands.run(() -> elevator.goToPosition(ElevatorSubsystem.ElevatorPosition.BOTTOM), elevator));

        // reset the field-centric heading on left bumper press
        // joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldRelative()));

        joystick.rightBumper().whileTrue(Commands.run(() -> m_Shooter.runShooterDefault())).onFalse(Commands.run(() -> m_Shooter.stopShooter()));
        joystick.rightTrigger().whileTrue(Commands.run(() -> m_Shooter.l1())).onFalse(Commands.run(() -> m_Shooter.stopShooter()));
        // joystick.rightBumper().onTrue(Commands.run(() -> m_Shooter.runShooterSlow())).onFalse(Commands.run(() -> m_Shooter.stopShooter())); // find a button for this one to bind to: r]uns shooter normally but slower

        // if (Utils.isSimulation()) {
        // drivetrain.seedFieldRelative(new Pose2d(new Translation2d(), Rotation2d.fromDegrees(90)));
        // }
        // drivetrain.registerTelemetry(logger::telemeterize);
    }

    public RobotContainer() {
        configureBindings();
        // Schedule the updateElevator method to run periodically
        elevator.setDefaultCommand(Commands.run(() -> elevator.updateElevator(), elevator));
    }

    private double applyDeadzone(double value) {
        if (Math.abs(value) < deadzone) {
            return 0;
        }
        return value;
    }

    public Command getAutonomousCommand() {
        return Commands.print("No autonomous command configured");
    }
}
