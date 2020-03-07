/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;
import frc.robot.RobotContainer;

public class Shooter extends PIDSubsystem {
  /**
   * Creates a new Shooter.
   */
  static File file;
  static BufferedReader br;
  static BufferedWriter bw;
  static FileReader fr;
  static FileWriter fw;
  public double tempRPM = 0;
  public CANSparkMax leftShooter, rightShooter;
  public WPI_TalonSRX tiltMotor = new WPI_TalonSRX(Constants.SHOOTER_TILT_MOTOR);
  double offsetX, offsetY;

  public Encoder tiltEncoder;
  public PIDController tiltController;
  CANEncoder leftShootEncoder, rightShootEncoder;
  public PIDController deltaControl;
  double deltaError;

  public Shooter() {
    super(new PIDController(0.0005, 0, 0.00001));
    initFile();
    ReadFromFile();
    leftShooter = new CANSparkMax(Constants.LEFT_SHOOT_MOTOR, MotorType.kBrushless);
    rightShooter = new CANSparkMax(Constants.RIGHT_SHOOT_MOTOR, MotorType.kBrushless);// right side is master
    leftShooter.setIdleMode(IdleMode.kCoast);
    rightShooter.setIdleMode(IdleMode.kCoast);
    leftShootEncoder = leftShooter.getEncoder();
    rightShootEncoder = rightShooter.getEncoder();
    deltaControl = new PIDController(0.00035, 0, 0);
    deltaControl.setTolerance(20);

    tiltEncoder = new Encoder(8, 9);
    tiltController = new PIDController(0.3, 0, 0);
    tiltController.setTolerance(10);

    getController().setTolerance(80);
  }

  public void setOffsets(double x, double y) {
    offsetX = x;
    offsetY = y;
  }

  public void initFile() {
    try {
      file = new File("/home/lvuser/prefs.txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      fw = new FileWriter(file);
      fr = new FileReader(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    bw = new BufferedWriter(fw);
    br = new BufferedReader(fr);
  }

  // Read from file
  public void ReadFromFile() {
    try {
      br = new BufferedReader(fr);
      String val = br.readLine();
      if (val != null) {
        double x = Double.parseDouble(val.split(",")[0]);
        double y = Double.parseDouble(val.split(",")[0]);
        SmartDashboard.putNumber("X", x);
        SmartDashboard.putNumber("Y", y);
        setOffsets(x, y);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Write to file
  public void WriteToFile() {
    try {
      bw.write(offsetX + "," + offsetY);
      bw.close();
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    double dout = deltaControl.calculate(deltaError);
    SmartDashboard.putNumber("DELTA OUT", dout);
    leftShooter.set(-((setpoint / 5500.0) + Math.max(output + dout, 0)));
    rightShooter.set((setpoint / 5500.0) + Math.max(output, 0));
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return rightShootEncoder.getVelocity();
  }

  @Override
  public void periodic() {
    // TODO Auto-generated method stub
    super.periodic();
    // tiltMotor.set(tiltController.calculate(RobotContainer.limelightY()));
    SmartDashboard.putNumber("Tilt Encoder", tiltEncoder.get());
    // SmartDashboard.putNumber("LimelightY",RobotContainer.limelightY());
    deltaError = -leftShootEncoder.getVelocity() - rightShootEncoder.getVelocity();
    SmartDashboard.putNumber("Delta Error", deltaError);
    // SmartDashboard.putNumber("Left Speed", -leftShootEncoder.getVelocity());
    SmartDashboard.putNumber("Right Speed", rightShootEncoder.getVelocity());
    // SmartDashboard.putData("T");
    // SmartDashboard.putData("TilT Controller",tiltController);
    SmartDashboard.putData("Master Controller", getController());
    SmartDashboard.putData("Delta Controller", deltaControl);
    SmartDashboard.putBoolean("Speed Setpoint", getController().atSetpoint());
    SmartDashboard.putBoolean("Delta Setpoint", deltaControl.atSetpoint());
  }

  public void updateTilter() {
    if (RobotContainer.foundTarget()) {
      tiltMotor.set(tiltController.calculate(RobotContainer.limelightY()));
    } else {
      tiltMotor.set(0);
    }
  }

  public void updateTilterTicks() {
    tiltMotor.set(tiltController.calculate(tiltEncoder.get()));
  }
}
