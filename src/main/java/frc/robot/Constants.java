// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class ElevatorConstants {

    //Elevator Constants
    //CAN ID's
    public static final int LEFT_ELEVATOR_ID = 13;
    public static final int RIGHT_ELEVATOR_ID = 14;
    public static final int ELEVATOR_ENCODER_ID = 17;
    //Speed
    public static final double ELEVATOR_SPEED = 0.3;
    //Position Presets
    public static final double BOTTOM_POSITION = 1;
    public static final double TOP_POSITION = 4.308837890625;
    public static final double MIDDLE_POSITION = (TOP_POSITION - BOTTOM_POSITION) / 2;
    //PID Consts
    public static final double KP = 0.75;
    public static final double KI = 0;
    public static final double KD = 0.05;

  }

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
}
