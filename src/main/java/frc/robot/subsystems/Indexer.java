/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Indexer extends PIDSubsystem {
  /**
   * Creates a new Indexer.
   */
  public WPI_TalonSRX indexerMotor;
  public TimeOfFlight tof;
  public static int ballCount = 0;
  public Indexer() {
    super(new PIDController(0.001,0,0));
    indexerMotor = new WPI_TalonSRX(Constants.INDEX_MOTOR);
    tof = new TimeOfFlight(Constants.TOF);
    getController().setTolerance(50);
    indexerMotor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    super.periodic();
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Indexer Ticks", this.getMeasurement());
    SmartDashboard.putData(getController());
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    indexerMotor.set(output);
  }

  @Override
  protected double getMeasurement() {
    return indexerMotor.getSelectedSensorPosition();
  }

  public boolean seeBall(){
    return tof.getRange()<Constants.INTAKE_SENSOR_RANGE_MM;
  }

  public void cycleBalls(){
    setSetpoint(this.getMeasurement()+Constants.INDEXER_TICKS_PER_SEGMENT);
  }
}
