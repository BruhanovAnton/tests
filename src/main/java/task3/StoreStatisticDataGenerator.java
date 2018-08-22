package task3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class StoreStatisticDataGenerator {

	public static void main(String[] args) throws FileNotFoundException {

		StoreStatisticDataGenerator ssg = new StoreStatisticDataGenerator();
		ssg.createCashboxStatistic(new PrintWriter(new File("cashbox1.txt")));
		ssg.createCashboxStatistic(new PrintWriter(new File("cashbox2.txt")));
		ssg.createCashboxStatistic(new PrintWriter(new File("cashbox3.txt")));
		ssg.createCashboxStatistic(new PrintWriter(new File("cashbox4.txt")));
		ssg.createCashboxStatistic(new PrintWriter(new File("cashbox5.txt")));
	}

	public void createCashboxStatistic(PrintWriter printWriter) {
		int queue = 0;
		double amountPpl = 0;
		double avg = 0;
		Random rand = new Random();
		for (int i = 0; i <= 480; i += 5) {
			int n = rand.nextInt(5) + 1;
			int n2 = rand.nextInt(5) + 1;

			queue += n - n2;

			if (queue < 0) {
				queue = 0;

			}

			amountPpl += queue;

			if (i > 0 && i % 30 == 0) {

				avg = amountPpl / 6;
				System.out.println("Среднее значение очереди:" + avg);
				printWriter.println(avg);
				avg = 0;
				amountPpl = 0;

			}

		}
		printWriter.close();
	}
}
