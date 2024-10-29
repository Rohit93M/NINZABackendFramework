package com.ninza.hrm.genericutility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class JavaUtility {
	
	public int getRandomNumber() {
		return new Random().nextInt(1000);
	}
	
	public String getSystemDatein_ddMMyyyy() {
		return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	}
}
