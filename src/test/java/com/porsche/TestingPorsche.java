package com.porsche;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestingPorsche {
	
	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

//		==========================step 1 Open Browser===============
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().window().fullscreen();
		
//      ==========================step 2 Go to Url===============
		driver.get("https://www.porsche.com/usa/modelstart/");
		
//		==========================step 3 Select model 718===============
		driver.findElement(By.className("b-teaser-preview-wrapper")).click();
		
//		==========================step 4 Remember the price of 718 Cayman===============
		String price1 = driver.findElement(By.className("m-14-model-price")).getText();
		String subPrice = price1.substring(0, price1.length() - 4);

		int firstPrice = somethingPrice(subPrice);
		
//		==========================step 5 Click on Build & Price under 718 Cayman===============
		String url2 = "https://bit.ly/2K1ggjw";
		driver.get(url2);
//		==========================step 6 Verify that Base price displayed on the page is same as the price from step 4===============
		int basePrice = findBasePrice(driver);

		if (firstPrice == basePrice) {
			System.out.println("PASS. First Price equal to Base Price.");
		} else {
			System.out.println("FAIL. First Price is not match Base Price.");
		}
//		==========================step 7 Verify that Price for Equipment is 0===============
		int intEquipmentPrice = findEquipmentPrice(driver);
		if (intEquipmentPrice == 0) {
			System.out.println("PASS. Equipment Price is 0.");
		} else {
			System.out.println("FAIL. Equipment Price is " + intEquipmentPrice);
		}
//		========step 8 Verify that total price is the sum of base price + Delivery, Processing and Handling Fee===============
		checkTotalPrice(driver);
		
//		==========================step 9 Select color “Miami Blue”===============
		driver.findElement(By.cssSelector("span[style='background-color: rgb(0, 120, 138);']")).click();
		
//		==========================step 10 Verify that Price for Equipment is Equal to Miami Blue price===============
		String colorPrice = driver.findElement(By.id("s_exterieur_x_FJ5")).getAttribute("data-price");

		checkEquipmentPrice(driver, colorPrice);

//		======step 11 Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee=======
		checkTotalPrice(driver);

//		===============step 12 Select 20" Carrera Sport Wheels====================
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		driver.findElement(By.xpath("//*[@id=\"submenu_exterieur_x_AA_submenu_x_IRA\"]/a")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_MXRD\"]/span/span")).click();
		
//		==========step 13 Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels========
		String wheelsPrice = driver.findElement(By.xpath("//*[@id=\"s_exterieur_x_IRA\"]/div[2]/div[1]/div/div[2]"))
				.getText();
		
		checkEquipmentPrice(driver, colorPrice, wheelsPrice);

//		========step 14 Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee=====
		checkTotalPrice(driver);

//		===============step 15 Select seats 'Power Sport Seats (14-way) with Memory Package'====================
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		driver.findElement(By.xpath("//*[@id=\"submenu_interieur_x_AI_submenu_x_submenu_parent\"]/span")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"submenu_interieur_x_AI_submenu_x_submenu_seats\"]/a")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"s_interieur_x_PP06\"]")).click();

//		===step 16 Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package====
		String seatPrice = driver.findElement(By.xpath("//*[@id=\"seats_73\"]/div[2]/div[1]/div[3]/div")).getText();

		checkEquipmentPrice(driver, colorPrice, wheelsPrice, seatPrice);
//		======step 17 Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee=====
		checkTotalPrice(driver);

//		===============step 18 Click on Interior Carbon Fiber====================
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		driver.findElement(
				By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_submenu_parent\"]/span")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_IIC\"]/a")).click();
		Thread.sleep(1000);
		
//		===============step 19 Select Interior Trim in Carbon Fiber i.c.w. Standard Interior====================
		driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH_x_c01_PEKH\"]")).click();

//		===============step 20 Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport
//		Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in
//		Carbon Fiber i.c.w. Standard Interior========
		String interiorPrice = driver.findElement(By.xpath("//*[@id=\"vs_table_IIC_x_PEKH\"]/div[1]/div[2]/div"))
				.getText();

		checkEquipmentPrice(driver, colorPrice, wheelsPrice, seatPrice, interiorPrice);

//		======step 21 Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee====
		checkTotalPrice(driver);

//		===============step 22 Click on Performance====================
		driver.findElement(By.xpath("//*[@id=\"s_conf_submenu\"]/div/div")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//*[@id=\"submenu_individualization_x_individual_submenu_x_IMG\"]/a")).click();
		Thread.sleep(1000);
		
//		===============step 23 Select 7-speed Porsche Doppelkupplung (PDK)====================
		driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M250_x_c11_M250\"]")).click();

//		===============step 24 Select Porsche Ceramic Composite Brakes (PCCB)====================
		driver.findElement(By.xpath("//*[@id=\"search_x_inp\"]")).sendKeys("porsche ceramic");
		driver.findElement(By.xpath("//*[@id=\"search_x_M450_x_c94_M450_x_shorttext\"]")).click();

//		============= step 25 Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport
//		Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. 
//		Standard Interior + 7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite Brakes (PCCB)=========
		
		String brakesPrice = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M450\"]/div[1]/div[2]/div"))
				.getText();
		String speedPrice = driver.findElement(By.xpath("//*[@id=\"vs_table_IMG_x_M250\"]/div[1]/div[2]/div"))
				.getText();

		checkEquipmentPrice(driver, colorPrice, wheelsPrice, seatPrice, interiorPrice, brakesPrice, speedPrice);

//		=======step 26 Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee=======
		checkTotalPrice(driver);
		driver.quit();

	}
// 	================ This method checking Equipment Price checkEquipmentPrice(WebDriver driver, String... prices) ======================
	public static void checkEquipmentPrice(WebDriver driver, String... prices) {
		int optionsPrice = 0;
		for (String each : prices) {
			optionsPrice += somethingPrice(each);
		}

		if (findEquipmentPrice(driver) == optionsPrice) {
			System.out.println("PASS. Equipments Price is equal to the options price.");
		} else {
			System.out.println("FAIL. NOT A MATCH EQUIPMENTS PRICE");
			System.out.println("EQUIPMENTS PRICE : " + findEquipmentPrice(driver));
			System.out.println();
		}
	}

//	================ This method checking Total Price checkTotalPrice(WebDriver driver) ======================
	public static void checkTotalPrice(WebDriver driver) {
		if (findTotalPrice(driver) == findBasePrice(driver) + findEquipmentPrice(driver) + findFees(driver)) {
			System.out.println("PASS. Total price is equal to the sum of given values.");
		} else {
			System.out.println("FAIL. NOT A MATCH TOTAL PRICE");
			System.out.println("TOTAL PRICE : " + findTotalPrice(driver));
			System.out.println(
					"ALL VALUE ARE : " + (findBasePrice(driver) + findEquipmentPrice(driver) + findFees(driver)));
		}

	}
//	============ This method String price convert to int price===============
	public static int somethingPrice(String price) {
		String digitPrice = "";
		for (int i = 0; i < price.length(); i++) {
			if (Character.isDigit(price.charAt(i))) {
				digitPrice += price.charAt(i);
			} else {
				continue;
			}
		}
		int intPrice = Integer.parseInt(digitPrice);

		return intPrice;
	}

//	============ This method finding Base Price===============
	public static int findBasePrice(WebDriver driver) {
		String basePrice = driver.findElement(By.xpath("/html/body/div/div/div/section/section/div/div[1]/div[2]"))
				.getText();
		int basePrice1 = somethingPrice(basePrice);

		return basePrice1;
	}

//	============ This method finding Equipment Price===============
	public static int findEquipmentPrice(WebDriver driver) {
		String equipmentPrice = driver.findElement(By.xpath("/html/body/div/div/div/section/section/div/div[2]/div[2]"))
				.getText();

		int equipmentPrice1 = somethingPrice(equipmentPrice);
		return equipmentPrice1;
	}

//	============ This method finding Fees Price===============
	public static int findFees(WebDriver driver) {
		String feesPrice = driver.findElement(By.xpath("/html/body/div/div/div/section/section/div/div[3]/div[2]"))
				.getText();

		int feesPrice1 = somethingPrice(feesPrice);
		return feesPrice1;
	}

//	============ This method finding Total Price===============
	public static int findTotalPrice(WebDriver driver) {
		String totalPrice = driver.findElement(By.xpath("/html/body/div/div/div/section/section/div/div[4]/div[2]"))
				.getText();

		int totalPrice1 = somethingPrice(totalPrice);
		return totalPrice1;
		
	}


}
