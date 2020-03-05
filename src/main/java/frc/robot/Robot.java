/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.lang.Thread.State;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Indexer;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.LEDRunner.AnimationMode;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private RobotContainer m_robotContainer;

  public AddressableLED led;
  public static LEDRunner ledRunner;
  private Thread ledThread;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    led = new AddressableLED(Constants.LED_PWM);
    ledRunner = new LEDRunner();
    ledThread = new Thread(ledRunner);
    ledThread.start();
    
    led.setLength(ledRunner.buffer.getLength());
    led.setData(ledRunner.buffer);
    led.start();
  
    ledRunner.setAnimation(AnimationMode.INIT);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
    led.setData(ledRunner.buffer);
    SmartDashboard.putNumber("DX", RobotContainer.drive.navx.getDisplacementX());
    SmartDashboard.putNumber("DY", RobotContainer.drive.navx.getDisplacementY());
    SmartDashboard.putNumber("DZ", RobotContainer.drive.navx.getDisplacementZ());
    SmartDashboard.putNumber("RX", RobotContainer.drive.navx.getRawGyroX());
    SmartDashboard.putNumber("RY", RobotContainer.drive.navx.getRawGyroY());
    SmartDashboard.putNumber("RZ", RobotContainer.drive.navx.getRawGyroZ());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
    // RobotContainer.shooter.WriteToFile();
    
  }

  @Override
  public void disabledPeriodic() {
    if(ledRunner.currentAnimation == AnimationMode.OFF){
      ledRunner.setAnimation(AnimationMode.DISABLED);
    }
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    // RobotContainer.shooter.ReadFromFile();
   RobotContainer.indexer.indexerMotor.setSelectedSensorPosition(0);
  //  m_robotContainer.arcadeDriveCommand.schedule(true);
   ledRunner.setAnimation(AnimationMode.FULL_RAINBOW);
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    double rStick = RobotContainer.stick.getRawAxis(5);
    RobotContainer.shooter.tiltMotor.set(-Math.signum(rStick)*Math.pow(rStick,2));//TODO remove this
    CommandScheduler.getInstance().run();
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    Indexer.ballCount = 0;
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
