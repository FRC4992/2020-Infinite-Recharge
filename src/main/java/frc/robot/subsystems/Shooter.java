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

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class Shooter extends PIDSubsystem {
  /**
   * Creates a new Shooter.
   */
  static File file;
  static BufferedReader br;
  static BufferedWriter bw;
  static FileReader fr;
  static FileWriter fw;

  public CANSparkMax leftShooter, rightShooter;
  WPI_TalonSRX tiltMotor = new WPI_TalonSRX(Constants.SHOOTER_TILT_MOTOR);
  double offsetX, offsetY;


  public DigitalInput leftProxy, rightProxy;
  Encoder tiltEncoder;
  CANEncoder leftShootEncoder, rightShootEncoder;
  PIDController deltaControl;
  double deltaError;

  public Shooter() {
    super(new PIDController(0, 0, 0));
    initFile();
    ReadFromFile();
    leftShooter = new CANSparkMax(Constants.LEFT_SHOOT_MOTOR,MotorType.kBrushless);
    rightShooter = new CANSparkMax(Constants.RIGHT_SHOOT_MOTOR,MotorType.kBrushless);//right side is master
    leftShooter.setIdleMode(IdleMode.kCoast);
    rightShooter.setIdleMode(IdleMode.kCoast);
    leftShootEncoder = leftShooter.getEncoder();
    rightShootEncoder = rightShooter.getEncoder();
    deltaControl = new PIDController(0, 0, 0);
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
    leftShooter.set(output+deltaControl.calculate(deltaError));
    rightShooter.set(-output);
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
    SmartDashboard.putNumber("Tilt Encoder", tiltEncoder.get());
    deltaError = rightShootEncoder.getVelocity()-leftShootEncoder.getVelocity();
    SmartDashboard.putNumber("Left Speed", leftShootEncoder.getVelocity());
    SmartDashboard.putNumber("Right Speed", rightShootEncoder.getVelocity());
    SmartDashboard.putData("Master Controller",getController());
    SmartDashboard.putData("Delta Controller",deltaControl);
  }
}
