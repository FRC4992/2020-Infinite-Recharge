/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /**
   * Creates a new Intake.
   */
  public VictorSP intakeMotor;
  public Intake() {
    intakeMotor = new VictorSP(Constants.INTAKE_MOTOR);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void intake(){
    intakeMotor.set(0.723);
  }
  public void stop(){
    intakeMotor.set(0);
  }
}
