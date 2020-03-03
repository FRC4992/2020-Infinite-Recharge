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
    SHIFTER_FORWARD = 0,
    SHIFTER_REVERSE = 1;

    // Motors
    public static final int
    TOP_LEFT_DRIVE = 1,
    BOTTOM_LEFT_DRIVE = 2,
    TOP_RIGHT_DRIVE = 3,
    BOTTOM_RIGHT_DRIVE = 4,
    INTAKE_MOTOR = 5,
    INDEX_MOTOR = 6,
    LEFT_SHOOT_MOTOR = 9,
    RIGHT_SHOOT_MOTOR = 10,
    TELESCOPE_MOTOR = 7,
    LEFT_WINCH = 8,
    RIGHT_WINCH = 11,
    PANEL_MOTOR = 12,
    SHOOTER_TILT_MOTOR = 8;

    // LEDs
    public static final int
    LED_PWM = 0,
    LED_LENGTH = 60,
    LED_MIDDLE_START = 19,
    LED_MIDDLE_END = 38;

    // Joystick
    public static final int
    STICK = 0;

    // Sensors
    public static final int
    LEFT_SHOOTER_PROXY = 7,
    RIGHT_SHOOTER_PROXY = 6,
    TOF = 15;

    public static final long NANO_TIME_SCALE = 1*(int)(Math.pow(10, 9));
    public static double INTAKE_SENSOR_RANGE_MM = 55;
    public static int INDEXER_TICKS_PER_SEGMENT = 2240;
    public static int BALL_RANGE = 80;
    public static int MAX_VELOCITY_ERROR = 20;
}
