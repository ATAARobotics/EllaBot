package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase{
    public SparkMax leftShooter;
    public SparkMax rightShooter;

    private SparkMaxConfig leftShooterConfig = new SparkMaxConfig();
    private SparkMaxConfig rightShooterConfig = new SparkMaxConfig();

    private double leftSpeed = 0;
    private double rightSpeed = 0;

    public ShooterSubsystem() {
        leftShooter = new SparkMax(15, MotorType.kBrushless);
        rightShooter = new SparkMax(16, MotorType.kBrushless); // put ids in constants later

        leftShooterConfig.idleMode(IdleMode.kBrake);
        rightShooterConfig.idleMode(IdleMode.kBrake);

        leftShooterConfig.inverted(true);
        rightShooterConfig.inverted(false);

        leftShooter.configure(leftShooterConfig, null, null);
        rightShooter.configure(rightShooterConfig, null, null);
        
    }

    @Override
    public void periodic() {
        leftShooter.set(leftSpeed);
        rightShooter.set(rightSpeed);

    }

    public void runShooterDefault() {
        leftSpeed = 0.75;
        rightSpeed  = 0.75;
    }

    public void stopShooter() {
        leftSpeed = 0;
        rightSpeed = 0;
        
    }

    public void runShooterSlow() {
        leftSpeed = 0.2;
        rightSpeed = 0.2;

    }

    public void l1() {
        leftSpeed = 0.25;
        rightSpeed = 0.75;
    }
}
