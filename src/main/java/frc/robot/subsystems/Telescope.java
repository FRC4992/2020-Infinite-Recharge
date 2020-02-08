/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Telescope extends SubsystemBase {
  /**
   * Creates a new Telescope.
   */
  VictorSP motor;
  public DigitalInput limitTop;
  public DigitalInput limitBottom;
  public Telescope() {
    motor = new VictorSP(Constants.TELESCOPE_MOTOR);
    limitTop = new DigitalInput(Constants.LIMIT_SWITCH_TOP);
    limitBottom = new DigitalInput(Constants.LIMIT_SWITCH_BOTTOM);
  }

  public void setSpeed(double speed) {
    motor.setSpeed(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
