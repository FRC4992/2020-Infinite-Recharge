/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Indexer;

public class TempShoot extends CommandBase {
  /**
   * Creates a new TempShoot.
   */
  public TempShoot() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Indexer.ballCount = 0;
    RobotContainer.indexer.disable();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.shooter.leftShooter.set(-0.725);
    RobotContainer.shooter.rightShooter.set(0.725);
    RobotContainer.indexer.indexerMotor.set(-0.3);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.shooter.leftShooter.set(0);
    RobotContainer.shooter.rightShooter.set(0);
    RobotContainer.indexer.indexerMotor.set(0);
    RobotContainer.indexer.setSetpoint(RobotContainer.indexer.indexerMotor.getSelectedSensorPosition());
    RobotContainer.indexer.enable();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
