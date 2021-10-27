import com.w3spaces.electroboy.rpiI2CLCD.LCD;
ERROR
public class Test {
	public static void main(String[] args) {
		LCD.lcdInit(0x27);
		
		LCD.setCursor(0, 0);
		LCD.printString("Hello, World!");
		LCD.setCursor(1, 0);
		LCD.printString("Line 2!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < 5; i++) {			
			LCD.setBacklight(true);
			LCD.clear();
			LCD.setCursor(0, 0);
			LCD.printString("Backlight is on");		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			LCD.setBacklight(false);
			LCD.clear();
			LCD.setCursor(0, 0);
			LCD.printString("Backlight is off");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		LCD.clear();
		LCD.setBacklight(true);
		
		LCD.setCursor(0, 0);
		LCD.printString("LCD Driver By:");
		LCD.setCursor(1, 2);
		LCD.printString("ElectronicsBoy");
	}
}
