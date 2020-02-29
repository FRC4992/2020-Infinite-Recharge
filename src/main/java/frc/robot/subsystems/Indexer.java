/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.playingwithfusion.TimeOfFlight;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Indexer extends SubsystemBase {
  /**
   * Creates a new Indexer.
   */
  public WPI_TalonSRX indexerMotor;
  public TimeOfFlight tof;
  public Indexer() {
    indexerMotor = new WPI_TalonSRX(Constants.INDEX_MOTOR);
    tof = new TimeOfFlight(Constants.TOF);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
