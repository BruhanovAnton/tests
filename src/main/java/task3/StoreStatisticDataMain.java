package task3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StoreStatisticDataMain {

	static MaxCashboxeIndex mci;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		mci = new MaxCashboxeIndex(0, 0);
		double cashboxesIndex = 0;

		Scanner scnCashbox1 = new Scanner(new File("cashbox1.txt"));
		Scanner scnCashbox2 = new Scanner(new File("cashbox1.txt"));
		Scanner scnCashbox3 = new Scanner(new File("cashbox1.txt"));
		Scanner scnCashbox4 = new Scanner(new File("cashbox1.txt"));
		Scanner scnCashbox5 = new Scanner(new File("cashbox1.txt"));

		for (int i = 1; i <= 16; i++) {
			cashboxesIndex = Double.parseDouble(scnCashbox1.next()) + Double.parseDouble(scnCashbox2.next())
					+ Double.parseDouble(scnCashbox3.next()) + Double.parseDouble(scnCashbox4.next())
					+ Double.parseDouble(scnCashbox5.next());

			if (cashboxesIndex > mci.getMax()) {
				mci.setMax(cashboxesIndex);
				mci.setPeriod(i);

			}

		}

		System.out.println("Магазин работает с 10:00 по 18:00");

		System.out.println("Период с наибольшим количеством посетителей: " + mci.getPeriodTime());

	}

	static class MaxCashboxeIndex {
		private double max;
		private int period;

		public MaxCashboxeIndex(double max, int period) {
			super();
			this.max = max;
			this.period = period;
		}

		public double getMax() {
			return max;
		}

		public void setMax(double max) {
			this.max = max;
		}

		public int getPeriod() {
			return period;
		}

		public void setPeriod(int period) {
			this.period = period;
		}

		public String getPeriodTime() {
			String time = "";
			int minutes = period * 30;
			int hours = (minutes / 60) + 10;
			int fromTime = 0;

			if (minutes % 60 == 0) {

				fromTime = 10 + (minutes - 30) / 60;
				time = fromTime + ":30 - " + hours + ":00";
			} else {
				time = hours + ":00 - " + hours + ":30";
			}

			return time;
		}

	}

}
