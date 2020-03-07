/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Telescope extends SubsystemBase {
  /**
   * Creates a new Telescope.
   */
  VictorSPX motor;
  public Encoder encoder;
  public Telescope() {
    motor = new VictorSPX(Constants.TELESCOPE_MOTOR);
    encoder = new Encoder(Constants.TELESCOPE_ENCODER_A,Constants.TELESCOPE_ENCODER_B);
  }

  public void setSpeed(double speed) {
    motor.set(ControlMode.PercentOutput,speed);
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
