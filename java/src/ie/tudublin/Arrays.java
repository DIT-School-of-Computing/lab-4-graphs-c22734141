package ie.tudublin;

import processing.core.PApplet;

public class Arrays extends PApplet {
	String[] months = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

	float[] rainfall = { 200, 260, 300, 150, 100, 50, 10, 40, 67, 160, 400, 420 };

	public float map1(float a, float b, float c, float d, float e) {
		float r1 = c - b;
		float r2 = e - d;

		float howFar = a - b;
		return d + (howFar / r1) * r2;
	}

	void randomize() {
		for (int i = 0; i < rainfall.length; i++) {
			rainfall[i] = random(500);
		}
	}

	int windowW = 700;
	int windowH = 700;

	public void settings() {
		size(windowW, windowH);

		String[] m1 = months;
		print(m1[0]);

		for (int i = 0; i < months.length; i++) {
			println("Month: " + months[i] + "\t" + rainfall[i]);
		}
		for (String s : m1) {
			println(s);
		}

		int minIndex = 0;
		for (int i = 0; i < rainfall.length; i++) {
			if (rainfall[i] < rainfall[minIndex]) {
				minIndex = i;
			}
		}

		int maxIndex = 0;
		for (int i = 0; i < rainfall.length; i++) {
			if (rainfall[i] > rainfall[maxIndex]) {
				maxIndex = i;
			}
		}

		println("The month with the minimum rainfall was " + months[minIndex] + " with " + rainfall[minIndex] + "rain");
		println("The month with the max rainfall was " + months[maxIndex] + " with " + rainfall[maxIndex] + "rain");

		float tot = 0;
		for (float f : rainfall) {
			tot += f;
		}

		float avg = tot / (float) rainfall.length;

		// a b-c d-e;
		println(map1(5, 0, 10, 0, 100));
		// 50

		println(map1(25, 20, 30, 200, 300));
		// 250

		println(map1(26, 25, 35, 0, 100));
		// 10

	}

	public void setup() {

		colorMode(HSB);
		background(0);
		randomize();

		getMaxMin();

	}

	float max = rainfall[0];
	float min = rainfall[0];

	void getMaxMin() {

		for (int i = 0; i < rainfall.length; i++) {

			if (rainfall[i] < min) {
				min = rainfall[i];
			}

			if (rainfall[i] > max) {
				max = rainfall[i];
			}

		}

	}

	public void draw() {

		background(0);

		float colorJump = 250 / rainfall.length;
		float hue = 0;

		float centerX = windowW / 2;
		float centerY = windowH / 2;
		float diameter = windowW;

		float prevAngle = 0;

		for (int i = 0; i < rainfall.length; i++) {

			fill(hue, 255, 255);

			float newAngle = prevAngle + map1(rainfall[i], 0, sumArray(rainfall), 0, TWO_PI);

			arc(centerX, centerY, diameter, diameter, prevAngle, newAngle);

			prevAngle = newAngle;
			hue += colorJump;

		}

	}

	float sumArray(float[] myArr) {
		float total = 0;

		for (float i : myArr) {
			total += i;
		}

		return total;
	}

	int getNumDigits(float num) {

		int total = 0;
		while (num / 10 >= 1) {

			total++;
			num /= 10;

		}

		return total;

	}

	void drawChart() {
		float padding1 = 50;
		float padding2 = padding1 + 20;
		float separation = padding2 / 5;
		float colWidth = (width - 2 * padding2 - separation * months.length) / (float) months.length;
		float x = padding2;

		float colorJump = 250 / months.length;
		float hue = 0;

		stroke(255, 0, 255);

		float x1 = x;
		float y1 = 0;

		for (int i = 0; i < months.length; i++) {

			float colHeight = map1(rainfall[i], 0, max, 0, windowH - 2 * padding1);

			float y = padding1 + (windowH - 2 * padding1 - colHeight);

			fill(hue, 255, 255);
			stroke(hue, 255, 255);
			rect(x, y, colWidth, colHeight);

			stroke(255, 0, 255);
			fill(255, 0, 255);
			text(months[i], x + colWidth / 4, windowH - padding1 + 20);

			if (i > 0) {
				line(x1, y1, x + colWidth / 2, y);
			}

			x1 = x + colWidth / 2;
			y1 = y;

			hue += colorJump;

			x += separation + colWidth;

		}

		float y = padding1;

		for (int i = 0; i < (max / 20) - 1; i++) {

			if (i > 0) {
				y = windowH - map1(i, 0, max / 20, padding1, windowH - padding1);

				line(padding1 - 2, y, padding1 + 2, y);

				int newNum = 20 * i;
				text("" + newNum, padding1 - getNumDigits((float) newNum) * 6 - 15, y + 3);
			}

		}

		line(padding1, windowH - padding1, padding1, padding1);
		line(padding1, windowH - padding1, windowW - padding1, windowH - padding1);
	}

}