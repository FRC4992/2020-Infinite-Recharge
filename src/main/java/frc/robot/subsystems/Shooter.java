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

  public WPI_TalonSRX leftShooter, rightShooter;
  WPI_TalonSRX tiltMotor = new WPI_TalonSRX(Constants.SHOOTER_TILT_MOTOR);
  double offsetX, offsetY;

  public double lSpeed = 0, rSpeed = 0;
  public int leftCount = 0, rightCount = 0;
  int pLeftCount = 0, pRightCount = 0;
  long lastTime = 0;
  boolean pLeftGet = false, pRightGet = false;

  public DigitalInput leftProxy, rightProxy;
  Encoder tiltEncoder;
  public Thread encoderThread;

  public Shooter() {
    super(new PIDController(0, 0, 0));
    initFile();
    ReadFromFile();
    leftShooter = new WPI_TalonSRX(Constants.LEFT_SHOOT_MOTOR);
    rightShooter = new WPI_TalonSRX(Constants.RIGHT_SHOOT_MOTOR);

    leftProxy = new DigitalInput(Constants.LEFT_SHOOTER_PROXY);
    rightProxy = new DigitalInput(Constants.RIGHT_SHOOTER_PROXY);
    tiltEncoder = new Encoder(8, 9);
    lastTime = 0;

    encoderThread = new Thread(new Runnable() {

      @Override
      public void run() {
        // TODO Auto-generated method stub
        while (true) {
          if (leftProxy.get() && !pLeftGet) {
            leftCount++;
            pLeftGet = true;
          }
          if(!leftProxy.get()) pLeftGet = false;
          if (rightProxy.get() && !pRightGet){
            rightCount++;
            pRightGet = true;
          }
          if(!rightProxy.get()) pRightGet = false;
          leftShooter.setSelectedSensorPosition(leftCount);
          rightShooter.setSelectedSensorPosition(rightCount);

          // if (System.currentTimeMillis() - lastTime >= 30) {
          //   long deltaTime = System.currentTimeMillis() - lastTime;
          //   SmartDashboard.putNumber("delta",deltaTime);
          //   // lSpeed = 60000.0*(((leftCount - pLeftCount)/2.0) / ((double) deltaTime));
          //   // rSpeed = 60000.0*(((rightCount - pRightCount)/2.0) / ((double) deltaTime));
            
          //   // pRightCount = rightCount;
          //   // pLeftCount = leftCount;



          //   lastTime = System.currentTimeMillis();
          // }
          // try {
          //    encoderThread.sleep(0, 800);
          // } catch (InterruptedException e) {
          //   // TODO Auto-generated catch block
          //   e.printStackTrace();
          // }
        }
      }
    });
    encoderThread.start();
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
    leftShooter.set(output);
    rightShooter.set(-output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    return 0;
  }

  @Override
  public void periodic() {
    // TODO Auto-generated method stub
    super.periodic();
    SmartDashboard.putNumber("Tilt Encoder", tiltEncoder.get());
  }
}
