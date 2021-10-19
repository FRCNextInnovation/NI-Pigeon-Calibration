// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.CalibrationMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
    private enum CalibrationState {
        CALIBRATION_WAITING_TO_START("Calibration wating to start..."), CALIBRATING("Calibrating..."),
        CALIBRATION_DONE("Calibration done!");

        public final String value;

        private CalibrationState(String value) {
            this.value = value;
        }
    }

    private static final int PIGEON_ID = 0;

    private static final double CALIBRATION_INIT_TEMPERATURE = 30;
    private static final double CALIBRATION_END_TEMPERATURE = 60;

    private double pigeonTemperature;
    private double pigeonFusedHeading;
    private double calibrationInitHeading;
    private double calibrationEndHeading;
    private boolean isInitHeadingRecord;
    private boolean isEndHeadingRecord;

    private CalibrationState calibrationState;
    private String calibrationLabel;
    private PigeonIMU pigeon;

    private boolean epsilonEqual(double a, double b, double epsilon) {
        return Math.abs(a - b) <= Math.abs(epsilon);
    }

    @Override
    public void robotInit() {
        calibrationState = CalibrationState.CALIBRATION_WAITING_TO_START;
        calibrationLabel = " ";
        pigeon = new PigeonIMU(PIGEON_ID);

        pigeonTemperature = pigeon.getTemp();
        pigeonFusedHeading = pigeon.getFusedHeading();
        calibrationInitHeading = 0.0;
        calibrationEndHeading = 0.0;
        isInitHeadingRecord = false;
        isEndHeadingRecord = false;
    }

    @Override
    public void robotPeriodic() {
        pigeonTemperature = pigeon.getTemp();
        pigeonFusedHeading = pigeon.getFusedHeading();

        SmartDashboard.putString("Calibration State:", calibrationState.value);

        SmartDashboard.putNumber("Pigeon Temperature(Celsius)):", pigeonTemperature);
        SmartDashboard.putNumber("Pigeon Fused Heading/Yaw:", pigeonFusedHeading);
        SmartDashboard.putString("Console", calibrationLabel);
    }

    private void measureHeadingErrorByT() {
        if (epsilonEqual(pigeonTemperature, CALIBRATION_INIT_TEMPERATURE, 1.0)) {
            calibrationInitHeading = pigeonFusedHeading;
            isInitHeadingRecord = true;
        } else if (epsilonEqual(pigeonTemperature, calibrationEndHeading, 1.0)) {
            calibrationEndHeading = pigeonFusedHeading;
            isEndHeadingRecord = true;
        }

        if (isInitHeadingRecord && isEndHeadingRecord) {
            calibrationState = CalibrationState.CALIBRATION_DONE;

            var tError = CALIBRATION_END_TEMPERATURE - CALIBRATION_INIT_TEMPERATURE;
            var headingError = calibrationEndHeading - calibrationInitHeading;
            calibrationLabel = (int) Timer.getFPGATimestamp() + ") Measure finished, the error of t: " + tError
                    + ", heading: " + headingError;

            isInitHeadingRecord = false;
            isEndHeadingRecord = false;
        }
    }

    // !Autonomous mode will let Pigeon enter the temperature mode.
    @Override
    public void autonomousInit() {
        pigeon.enterCalibrationMode(CalibrationMode.Temperature);
        calibrationState = CalibrationState.CALIBRATING;
    }

    // !Test mode will measure the impact of temperature to heading degrees.
    @Override
    public void testInit() {
        isInitHeadingRecord = false;
        isEndHeadingRecord = false;
    }

    @Override
    public void testPeriodic() {
        measureHeadingErrorByT();
    }
}
