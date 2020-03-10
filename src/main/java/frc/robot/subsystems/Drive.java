/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.LEDRunner.AnimationMode;
import frc.robot.commands.ArcadeDrive;

public class Drive extends PIDSubsystem {
  /**
   * Creates a new Drive.
   */
  CANSparkMax frontLeft, frontRight, backLeft, backRight;
  SpeedControllerGroup left, right;
  public DifferentialDrive drive;
  public DoubleSolenoid shifter;
  public AHRS navx;
  CANEncoder leftEncoder, rightEncoder;
  public PIDController autoDist = new PIDController(0.1, 0, 0);
  public PIDController autoRotate = new PIDController(0.01, 0, 0);
  DifferentialDriveOdometry positionTracker;

  public static enum SENSOR_TYPES {
    DISTANCE(0.01, 0, 0, 1), ROTATION(0.001, 0, 0, 1), LIMELIGHT_DISTANCE(0.15, 0, 0, 0),
    LIMELIGHT_ROTATION(0.009, 0, 0, 0), LIMELIGHT_BOTH(0, 0, 0, 0), POWERCELL(0.007, 0, 0, 2),NONE(0,0,0,1);

    private double kP, kI, kD;
    private int pipelineIndex;

    private SENSOR_TYPES(double kP, double kI, double kD, int pipelineIndex) {
      this.kP = kP;
      this.kI = kI;
      this.kD = kD;
      this.pipelineIndex = pipelineIndex;
    }
  }

  SENSOR_TYPES currentSensor = SENSOR_TYPES.ROTATION;
  public double startAngle = 0;

  public Drive() {
    super(new PIDController(0, 0, 0));

    frontLeft = new CANSparkMax(Constants.TOP_LEFT_DRIVE, MotorType.kBrushless);
    frontRight = new CANSparkMax(Constants.TOP_RIGHT_DRIVE, MotorType.kBrushless);
    backLeft = new CANSparkMax(Constants.BOTTOM_LEFT_DRIVE, MotorType.kBrushless);
    backRight = new CANSparkMax(Constants.BOTTOM_RIGHT_DRIVE, MotorType.kBrushless);

    left = new SpeedControllerGroup(frontLeft, backLeft);
    right = new SpeedControllerGroup(frontRight, backRight);
    drive = new DifferentialDrive(left, right);

    shifter = new DoubleSolenoid(Constants.SHIFTER_FORWARD, Constants.SHIFTER_REVERSE);
    navx = new AHRS(SPI.Port.kMXP);

    leftEncoder = frontLeft.getEncoder();
    rightEncoder = frontRight.getEncoder();
    autoDist.setTolerance(0.2, 1);
    autoRotate.setTolerance(2, 2);

    setFullSpeed(true);
    navx.zeroYaw();
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);

    positionTracker = new DifferentialDriveOdometry(Rotation2d.fromDegrees(navx.getAngle()));
  }

  @Override
  public void periodic() {
    super.periodic();
    positionTracker.update(Rotation2d.fromDegrees(-navx.getAngle()), leftEncoder.getPosition()/Constants.DRIVE_TICKS_PER_METER, -rightEncoder.getPosition()/Constants.DRIVE_TICKS_PER_METER);
    // SmartDashboard.putNumber("Gyro", navx.getAngle());
    // SmartDashboard.putData("Drive", getController());
    SmartDashboard.putNumber("Drive Ticks", (leftEncoder.getPosition() - rightEncoder.getPosition()) / 2);
    SmartDashboard.putNumber("Dx",positionTracker.getPoseMeters().getTranslation().getX());
    SmartDashboard.putNumber("Dy",positionTracker.getPoseMeters().getTranslation().getY());
  }

  public void setFullSpeed(boolean full){
    shifter.set(full?DoubleSolenoid.Value.kForward:DoubleSolenoid.Value.kReverse);
  }
  public boolean getFullSpeed(){
    return shifter.get() == DoubleSolenoid.Value.kForward;
  }

  public void setCurrentSensor(SENSOR_TYPES currentSensor) {
    this.currentSensor = currentSensor;
    getController().setPID(currentSensor.kP, currentSensor.kI, currentSensor.kD);
    RobotContainer.setPipleline(currentSensor.pipelineIndex);
    switch (currentSensor) {
    case DISTANCE:
      getController().setTolerance(1, 1);
      break;
    case LIMELIGHT_DISTANCE:
      getController().setTolerance(0.2, 1);
      break;
    case ROTATION:
      getController().setTolerance(5, 2);
      break;
    case POWERCELL:
    case LIMELIGHT_ROTATION:
      getController().setTolerance(2, 2);
      break;
    }

  }

  public void stop() {
    drive.arcadeDrive(0, 0);
  }

  public void setDriveSpeed(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation);
  }

  private int getLEDIndex() {
    double transformed = Constants.LED_MIDDLE_START
        + (RobotContainer.limelightX() + 26) / (2 * 26 / (Constants.LED_MIDDLE_END - Constants.LED_MIDDLE_START));
    return Constants.LED_LENGTH - 1
        - (int) MathUtil.clamp(transformed, Constants.LED_MIDDLE_START, Constants.LED_MIDDLE_END);
    // return 0;
    // TODO: Fix this
  }

  public void displayAlign(int r, int g, int b, AnimationMode searching) {
    if (!RobotContainer.foundTarget()) {
      Robot.ledRunner.setAnimation(searching);
    } else {
      int middleLedIndex = (Constants.LED_MIDDLE_END + Constants.LED_MIDDLE_START) / 2;
      Robot.ledRunner.setAnimation(AnimationMode.OFF);
      int ledIndex = getLEDIndex();
      if (Math.abs(ledIndex - middleLedIndex) < 2) {
        Robot.ledRunner.setAllRGB(r, g, b);
      } else {
        Robot.ledRunner.clear();
        Robot.ledRunner.buffer.setRGB(ledIndex, r, g, b);
        if (ledIndex > middleLedIndex) {
          Robot.ledRunner.setRangeRGB(r, g, b, Constants.LED_MIDDLE_END, Constants.LED_LENGTH);
        } else {
          Robot.ledRunner.setRangeRGB(r, g, b, 0, Constants.LED_MIDDLE_START);
        }
      }
    }
  }
  // TODO: Fix this

  @Override
  public void useOutput(double output, double setpoint) {
    // Use the output here
    // System.out.println("Setpoint: " + setpoint + ", current: " + getMeasurement()
    // + ", output: " + output
    // + ", at setpoint: " + getController().atSetpoint() + ", error: " +
    // getController().getPositionError()
    // + ", setpoint: " + getController().getSetpoint() + ", enabled: " +
    // isEnabled());
    switch (currentSensor) {
    case DISTANCE:
    case LIMELIGHT_DISTANCE:
      left.set(output);
      right.set(-output);
      break;

    case LIMELIGHT_ROTATION:
      drive.feedWatchdog();
    case ROTATION:
      left.set(output);
      right.set(output);

      break;
    case POWERCELL:
      if (RobotContainer.foundTarget()) {
        double driverSpeed = RobotContainer.driveStick.getRawAxis(1);
        drive.arcadeDrive(-Math.signum(driverSpeed) * Math.pow(driverSpeed, 2), output, false);
      } else {
        RobotContainer.drive.drive.arcadeDrive(-RobotContainer.driveStick.getRawAxis(1),
            RobotContainer.driveStick.getRawAxis(0));
      }
      break;
    default:

      break;
    }
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here
    switch (currentSensor) {
    case DISTANCE:
      return (leftEncoder.getPosition() - rightEncoder.getPosition()) / 2;
    case LIMELIGHT_DISTANCE:
      return RobotContainer.limelightDist();
    case ROTATION:
      return navx.getAngle();
    case LIMELIGHT_ROTATION:
    case POWERCELL:
      return RobotContainer.limelightX();
    default:
      return -1;
    }
  }
  public void resetDistance(){
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }
}
