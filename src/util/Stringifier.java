package util;

public class Stringifier {
	
	public static int stringToInt(String number) {
		int num = 0;
		
		if (number.equals("one"))
			num = 1;
		else if (number.equals("two")) 
			num = 2;
		else if (number.equals("three"))
			num = 3;
		else if (number.equals("four"))
			num = 4;
		else if (number.equals("five"))
			num = 5;
		else if (number.equals("six"))
			num = 6;
		else if (number.equals("seven"))
			num = 7;
		else if (number.equals("eight"))
			num = 8;
		else if (number.equals("nine"))
			num = 9;
		else if (number.equals("zero"))
			num = 0;
		
		return num;
	}
	
	public static String nameOfNumber(int number) {
		String name = "";
		if (number == 0) {
			name = "zero";
		} else if (number == 1) {
			name = "one";
		} else if (number == 2) {
			name = "two";
		} else if (number == 3) {
			name = "three";
		} else if (number == 4) {
			name = "four";
		} else if (number == 5) {
			name = "five";
		} else if (number == 6) {
			name = "six";
		} else if (number == 7) {
			name = "seven";
		} else if (number == 8) {
			name = "eight";
		} else if (number == 9) {
			name = "nine";
		} else if (number == 10) {
			name = "ten";
		} else if (number == 11) {
			name = "eleven";
		} else if (number == 12) {
			name = "twelve";
		} else if (number == 13) {
			name = "thirteen";
		} else if (number == 14) {
			name = "fourteen";
		} else if (number == 15) {
			name = "fifteen";
		} else if (number == 16) {
			name = "sixteen";
		} else if (number == 17) {
			name = "seventeen";
		} else if (number == 18) {
			name = "eighteen";
		} else if (number == 19) {
			name = "nineteen";
		} else if (number < 100) {
			if (number < 30) {
				name = "twenty";
			} else if (number < 40) {
				name = "thirty " + nameOfNumber(number - 30);
			} else if (number < 50) {
				name = "fourty " + nameOfNumber(number - 40);
			} else if (number < 60) {
				name = "fifty " + nameOfNumber(number - 50);
			} else if (number < 70) {
				name = "sixty " + nameOfNumber(number - 60);
			} else if (number < 80) {
				name = "seventy " + nameOfNumber(number - 70);
			} else if (number < 90) {
				name = "eighty " + nameOfNumber(number - 80);
			} else
				name = "ninety " + nameOfNumber(number - 90);
			if (number % 10 != 0)
				name = name + " " + nameOfNumber(number % 10);
		} else if (number < 1000) {
			int hundreds = number / 100;
			name = nameOfNumber(number / 100) + " hundred";
			if (number %100 != 0)
				name = name + " " + nameOfNumber(number % 100);
		} else {
			throw new IllegalArgumentException("Number is too big");
		}
		return name;
	}
	
	public static int numberWithName(String name) {
		name =  name
		.replace("ten", "10")
		.replace("eleven", "11")
		.replace("twelve", "12")
		.replace("thirteen", "13")
		.replace("fourteen", "14")
		.replace("fifteen", "15")
		.replace("sixteen", "16")
		.replace("seventeen", "17")
		.replace("eighteen", "18")
		.replace("nineteen", "19")
		.replace("one", "1")
		.replace("two", "2")
		.replace("three", "3")
		.replace("four", "4")
		.replace("five", "5")
		.replace("six", "6")
		.replace("seven", "7")
		.replace("eight", "8")
		.replace("nine", "9");
		if (name.endsWith("y")) name = name + " 0";
		if (name.endsWith("hundred")) name = name + "0 0";
		name.replaceAll(" ", "");
		return Integer.parseInt(name);
	}

}
