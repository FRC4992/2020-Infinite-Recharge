/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Pneumatics
    public static final int
    SHIFTER_FORWARD = 2,
    SHIFTER_REVERSE = 0,

    SPINNER_FORWARD = 1,
    SPINNER_REVERSE = 3;

    // Motors
    public static final int
    TOP_LEFT_DRIVE = 4,
    BOTTOM_LEFT_DRIVE = 1,
    TOP_RIGHT_DRIVE = 3,
    BOTTOM_RIGHT_DRIVE = 2,
    INTAKE_MOTOR = 0,
    INDEX_MOTOR = 7,
    LEFT_SHOOT_MOTOR = 9,
    RIGHT_SHOOT_MOTOR = 10,
    TELESCOPE_MOTOR = 15,
    LEFT_WINCH = 5,
    RIGHT_WINCH = 6,
    PANEL_MOTOR = 12,
    SHOOTER_TILT_MOTOR = 8;

    // LEDs
    public static final int
    LED_PWM = 7,
    LED_LENGTH = 60,
    LED_MIDDLE_START = 19,
    LED_MIDDLE_END = 38;

    // Joystick
    public static final int
    STICK = 0,
    PANEL = 1;

    // Sensors
    public static final int
    TOF = 15;
	public static final double DRIVE_TICKS_PER_METER = 15.57;
    public static final double INTAKE_SENSOR_RANGE_MM = 60;
    public static final int INDEXER_TICKS_PER_SEGMENT = 2240;
    public static final int BALL_RANGE = 80;
    public static final int MAX_VELOCITY_ERROR = 20;

    public static final int
    TELESCOPE_ENCODER_A = 1,
    TELESCOPE_ENCODER_B = 2
    ;
    public static final double 
    TELESCOPE_MAX = 500,
    TELESCOPE_MIN = 0;

    public static final double
    TILTER_MIN = 0,
    TILTER_MAX = 868;
    // TILTER_MAX = 200;

    
}
