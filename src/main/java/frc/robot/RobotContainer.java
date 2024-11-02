// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  /* Controllers */

  // controller is the first port on driver station
  private final Joystick joystick = new Joystick(0);
  /* Drive Controls */

  private final JoystickButton elevatorUp = new JoystickButton(joystick, 5);
  private final JoystickButton elevatorDown = new JoystickButton(joystick, 6);
  

  /* Driver Buttons */
  /* Subsystems */
  public final ElevatorSubsystem m_Elevator;




  // temp

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() { 
    m_Elevator = new ElevatorSubsystem();
    // Register pathplanner commands

    
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // /* Driver Buttons */
    elevatorUp.onTrue(new InstantCommand(() -> m_Elevator.runElevatorUp())).onFalse(new InstantCommand(() -> m_Elevator.stopElevator()));
    elevatorDown.onTrue(new InstantCommand(() -> m_Elevator.runElevatorDown())).onFalse(new InstantCommand(() -> m_Elevator.stopElevator()));

  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }
}
  