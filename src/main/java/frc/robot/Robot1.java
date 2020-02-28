/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot1 extends TimedRobot {

  /* DRIVETRAIN */
  /***********************************************************************************************************************************************/
  // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
  public SpeedController frontLeftMotor = new PWMVictorSPX(3);
  public SpeedController backLeftMotor = new PWMVictorSPX(4);
  public SpeedControllerGroup leftDrive = new SpeedControllerGroup(frontLeftMotor, backLeftMotor);

  // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
  public SpeedController frontRightMotor = new PWMVictorSPX(5);
  public SpeedController backRightMotor = new PWMVictorSPX(6);
  public SpeedControllerGroup rightDrive = new SpeedControllerGroup(frontRightMotor, backRightMotor);
  /***********************************************************************************************************************************************/

  /* INTAKE, STORAGE, LOADER, SHOOTER, AND FORTUNE */
  /***********************************************************************************************************************************************/
  // INSTANTIATE INTAKE MOTOR
  public PWMVictorSPX intakeMotor = new PWMVictorSPX(7);

  // INSTANTIATE STORAGE MOTOR
  public PWMVictorSPX storageMotor = new PWMVictorSPX(1);

  // INSTANTIATE SHOOTER MOTORS AND SHOOTER
  public PWMSpeedController leftShooterMotor = new PWMVictorSPX(0);
  public PWMSpeedController rightShooterMotor = new PWMTalonSRX(2);

  // INSTANTIATE FORTUNE MOTOR
  public PWMVictorSPX fortuneMotor = new PWMVictorSPX(8);
  /***********************************************************************************************************************************************/

  /* CONTROLLER */
  /***********************************************************************************************************************************************/
  // INSTANTIATE XBOX CONTROLLER
  public XboxController controller = new XboxController(0);

  // INSTANTIATE JOYSTICK AXES VARIABLES
  public static int leftStickY = 1;
  public static int rightStickY = 5;
  public static int leftTrigger = 2;
  public static int rightTrigger = 3;

  // INSTANTIATE JOYSTICK BUTTONS
  public JoystickButton aButton = new JoystickButton(controller, 1);
  public JoystickButton bButton = new JoystickButton(controller, 2);
  public JoystickButton xButton = new JoystickButton(controller, 3);
  public JoystickButton yButton = new JoystickButton(controller, 4);
  public JoystickButton leftBumper = new JoystickButton(controller, 5);
  public JoystickButton rightBumper = new JoystickButton(controller, 6);
  /***********************************************************************************************************************************************/

  /* PNEUMANTICS, LIMELIGHT, AND COLOR SENSOR */
  /***********************************************************************************************************************************************/
  // INSTANTIATE COMPRESSOR
  public Compressor compressor = new Compressor();

  // INSTANTIATE LOADER SOLENOID
  public DoubleSolenoid loader = new DoubleSolenoid(1, 0);

  // INSTANTIATE FORTUNE SOLENOID
  public DoubleSolenoid fortune = new DoubleSolenoid(3, 2);

  // INSTANTIATE LIMELIGHT TABLE
  public NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  // INSTANTIATE ANALOG SENSORS
  public final I2C.Port i2cPort = I2C.Port.kOnboard;
  public ColorSensorV3 indexer = new ColorSensorV3(i2cPort);

  /***********************************************************************************************************************************************/

  @Override
  // RUNS WHEN ROBOTS STARTS
  public void robotInit() {

    // TURN OFF ALL MOTORS
    leftDrive.set(0);
    rightDrive.set(0);
    intakeMotor.set(0);
    storageMotor.set(0);
    leftShooterMotor.set(0);
    rightShooterMotor.set(0);
    fortuneMotor.set(0);

    // RESET PNEUMATICS
    loader.set(Value.kReverse);
    fortune.set(Value.kReverse);
    compressor.clearAllPCMStickyFaults();

    // TURN LIMELIGHT OFF
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }

  // RUNS ONCE WHEN AUTONOMOUS STARTS
  @Override
  public void autonomousInit() {
  }

  // RUNS DURING AUTONOMOUS
  @Override
  public void autonomousPeriodic() {
  }

  // RUNS ONCE WHEN TELEOP STARTS
  @Override
  public void teleopInit() {
  }

  // RUNS DURING TELEOP
  @Override
  public void teleopPeriodic() {

    /* DRIVETRAIN */
    /***********************************************************************************************************************************************/
    // SETS VARIABLES AS AXES ON CONTROLLER
    double leftY = controller.getRawAxis(leftStickY);
    double rightY = controller.getRawAxis(rightStickY);
    double rightTrig = controller.getRawAxis(rightTrigger);

    // READS TRIGGER VALUES TO ADJUST SPEED OF DRIVETRAIN
    if (rightTrig > .5) {
      leftY = leftY * .95;
      rightY = rightY * .95;
    } else {
      leftY = leftY * .65;
      rightY = rightY * .65;
    }

    // GETS RID OF CONTROLLER DRIFT
    if (leftY < .05) {
      if (leftY > -.05) {
        leftY = 0;
      }
    }
    if (rightY < .05) {
      if (rightY > -.05) {
        rightY = 0;
      }
    }

    // SETS DRIVE SIDES TO AXES
    leftDrive.set(-leftY);
    rightDrive.set(rightY);
    /***********************************************************************************************************************************************/

    /* INTAKE */
    /***********************************************************************************************************************************************/
    // SETS BOOLEAN AS BUMPER
    boolean leftBump = leftBumper.get();
    boolean aBtn = aButton.get();
    boolean bBtn = bButton.get();

    // SET INDEXER AS INDEX
    int indexRED = indexer.getRed();

    // READ LEFTBUMPER TO ACTIVATE INTAKE
    if(leftBump == true) {
      intakeMotor.set(.22);
      storageMotor.set(0);

      // IF BALL TRIGGERS SENSOR, ACTIVATE STORAGE MOTOR
      if (indexRED > 250) { 
        intakeMotor.set(.06);
        storageMotor.set(-.45);
      }

    // IF BUMPER NOT PRESSED, MANUALLY CONTROL INTAKE AND STORAGE MOTORS, AND SLIGHTLY REVERSE STORAGE MOTOR
    } else {
      intakeMotor.set(0);
      storageMotor.set(.1);

      // ABUTTON ACTIVATES STORAGE MOTOR
      if(aBtn == true) {
        storageMotor.set(-.3);
      }

      // BBUTTON ACTIVATES INTAKE MOTOR
      if(bBtn == true) {
        intakeMotor.set(.4);
      } else {
        intakeMotor.set(0);
      }
    }

    // POST COLOR SENSOR RED VALUE
    SmartDashboard.putNumber("RGB Red", indexRED);
/***********************************************************************************************************************************************/

    /* FORTUNE */
/***********************************************************************************************************************************************/
    // SETS BOOLEAN AS YBUTTON
    boolean yBtn = yButton.get();
    int dPad = controller.getPOV();

    // READ YBUTTON TO FIRE FORTUNE PISTON
    if(yBtn == true) {
      fortune.set(Value.kForward);

      // IF RIGHTTRIG ALSO PRESSED, RETRACT FORTUNE PISTON
      if(rightTrig > .5) {
      fortune.set(Value.kReverse);
      }
    } else {
      fortune.set(Value.kOff);
    }
    
    // READ YBUTTON TO ACTIVATE FORTUNE MOTOR
    if (dPad == 270) {
      fortuneMotor.set(-.2);
    } else {
      if(dPad == 90) {
        fortuneMotor.set(.2);
      } else {
        fortuneMotor.set(0);
      }
    }
/***********************************************************************************************************************************************/

  /* SHOOTER */
/***********************************************************************************************************************************************/
    // SETS BOOLEAN AS RIGHT BUMPER
    boolean rightBump = rightBumper.get();

    // READ LEFTBUMPER TO ACTIVATE SHOOTER
    if(rightBump == true) {
      leftShooterMotor.set(0.95);
      rightShooterMotor.set(0.95);
    } else {
      leftShooterMotor.set(0);
      rightShooterMotor.set(0);
    }

    // SETS BOOLEAN AS XBUTTON
    boolean xBtn = xButton.get();
    
    // READS XBUTTON TO FIRE LOADER PISTON
    if(xBtn == true) {
      loader.set(Value.kForward);
    } else { 
      loader.set(Value.kReverse);
    }
/***********************************************************************************************************************************************/

    /* LIMELIGHT */
/***********************************************************************************************************************************************/
    // UPDATE NETWORKTABLES
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");

    // SET VARIABLES
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    double leftTrig = controller.getRawAxis(leftTrigger);

    // ACTIVATE LIMELIGHT WHEN TRIGGER IS PRESSED
    if (leftTrig > .3) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
      while (leftTrig > .8) {
        // PLACE LIMELIGHT CODE HERE
      }
    } else {      
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    /* ROUGH LIMELIGHT CODE */
    /* 
    if (leftTrig > .1) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
      if (leftTrig < .7) {
        if (y < 0) {
          leftDrive.set(.5);
          rightDrive.set(-.5);
          if (y > -.1) {
            rightDrive.set(0);
            leftDrive.set(0);
          }
        }
        if (y > 0) {
          leftDrive.set(-.5);
          rightDrive.set(.5);
          if (y < .1) {
            rightDrive.set(0);
            leftDrive.set(0);
          }
        }
      } else {
        if (x < 0) {
          leftDrive.set(0);
          rightDrive.set(-.2);
          if (x > -.1) {
            rightDrive.set(0);
          }
        }
        if (x > 0) {
          rightDrive.set(0);
          leftDrive.set(.2);
          if (x < .1) {
            leftDrive.set(0);
          }
        }
      }
    } else {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
      leftDrive.set(-leftY);
      rightDrive.set(rightY);
    } */
/***********************************************************************************************************************************************/


  }

  // RUNS ONCE WHEN TEST STARTS
  @Override
  public void testInit() {
  }
  
  // RUNS DURING TEST
  @Override
  public void testPeriodic() {
  }

}
