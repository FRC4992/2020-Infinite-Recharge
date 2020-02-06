package frc.robot;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LEDRunner implements Runnable {
    public AddressableLEDBuffer buffer = new AddressableLEDBuffer(Constants.LED_LENGTH);
    int time = 0;

    public static enum AnimationMode {
        INIT, OFF, FLASH, SEARCHING_GREEN, SEARCHING_YELLOW, SEARCHING_RED, DISABLED, FULL_RAINBOW
    }

    AnimationMode currentAnimation = AnimationMode.OFF;

    @Override
    public void run() {
        while (true) {
            try {
                SmartDashboard.putNumber("Animation Time", time);
                switch (currentAnimation) {
                case DISABLED:
                    breathe(0, 0, 255, true);
                    break;
                case INIT:
                    bounce(255, 0, 0, false, 10);
                    break;
                case FLASH:
                    flash();
                    break;
                case SEARCHING_GREEN:
                    bounce(0, 255, 0, true, 10);
                    break;
                case SEARCHING_YELLOW:
                    bounce(255, 175, 0, true, 10);
                    break;
                case SEARCHING_RED:
                    bounce(255, 0, 0, true, 10);
                    break;
                case OFF:
                    // clear();
                    break;
                case FULL_RAINBOW:
                    fullRainbow();
                    break;
                }
            } catch (Exception e) {

            }

        }

    }

    private void breathe(int r, int g, int b, boolean loop) throws InterruptedException {
        time++;
        if (time < 2 * 255) {
            int brightess = Math.abs(time - 255);
            setAllRGB(r * brightess, g * brightess, b * brightess);
            Thread.sleep(1);
        } else {
            if (!loop) {
                currentAnimation = AnimationMode.OFF;
            } else {
                time = 0;
            }
        }
    }

    private void flash() throws InterruptedException {
        setAllRGB(0, 255, 0);
        Thread.sleep(100);
        clear();
        Thread.sleep(100);
    }

    public void setAllRGB(int r, int g, int b) {
        setRangeRGB(r, g, b, 0, buffer.getLength());
    }

    public void setRangeRGB(int r, int g, int b, int start, int end) {
        for (int i = start; i < end; i++) {
            buffer.setRGB(i, r, g, b);
        }
    }

    public void setAllHSV(int h, int s, int v) {
        setRangeHSV(h, s, v, 0, buffer.getLength());
    }

    public void setRangeHSV(int h, int s, int v, int start, int end) {
        for (int i = start; i < end; i++) {
            buffer.setHSV(i, h, s, v);
        }
    }

    public void fullRainbow() throws InterruptedException {
        time++;
        setAllHSV(time % 180, 255, 255);
        Thread.sleep(10);
    }

    private void bounce(int r, int g, int b, boolean loop, int blockSize) throws InterruptedException {
        clear();
        time++;
        if (time + blockSize < buffer.getLength() * 2 + blockSize) {
            for (int i = 0; i < blockSize; i++) {
                buffer.setRGB(i + -Math.abs(time - buffer.getLength() - 1) + buffer.getLength() - 1, r, g, b);
            }
            System.out.println(time);

            Thread.sleep(10);
        } else {
            if (!loop) {
                currentAnimation = AnimationMode.OFF;
            } else {
                time = 0;
                System.out.println("Reset time");
            }

        }
    }

    public void clear() {
        setAllRGB(0, 0, 0);
    }

    public void setAnimation(AnimationMode m) {
        if (m != currentAnimation) {
            time = 0;
        }
        this.currentAnimation = m;
    }

}