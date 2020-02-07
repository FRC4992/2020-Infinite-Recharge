/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drive extends SubsystemBase {
  /**
   * Creates a new Drive.
   */
  private CANSparkMax frontLeft, frontRight, backLeft, backRight;
  private SpeedControllerGroup left, right;
  private DifferentialDrive differentialDrive;

  private CANEncoder leftTicks, rightTicks;

  public Drive() {
    frontLeft = new CANSparkMax(Constants.TOP_LEFT_DRIVE, MotorType.kBrushless);
    frontRight = new CANSparkMax(Constants.TOP_RIGHT_DRIVE, MotorType.kBrushless);
    backLeft = new CANSparkMax(Constants.BOTTOM_LEFT_DRIVE, MotorType.kBrushless);
    backRight = new CANSparkMax(Constants.BOTTOM_RIGHT_DRIVE, MotorType.kBrushless);
    left = new SpeedControllerGroup(frontLeft, backLeft);
    right = new SpeedControllerGroup(frontRight, backRight);
    differentialDrive = new DifferentialDrive(left, right);
    leftTicks = frontLeft.getEncoder();
    rightTicks = frontRight.getEncoder();
  }

  public double getLeftTicks(){
    return leftTicks.getPosition();
  }
  public double getRightTicks(){
    return rightTicks.getPosition();
  }
  public DifferentialDrive getDrive(){
    return differentialDrive;
  }
  public void setDriveSpeed(double x, double y){
    differentialDrive.arcadeDrive(x, y);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
