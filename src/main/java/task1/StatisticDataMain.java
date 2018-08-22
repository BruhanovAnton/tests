package task1;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class StatisticDataMain extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTextArea ta;

	public StatisticDataMain() {
		JFrame frame = new JFrame("Statistic calculator");

		JPanel panel = new JPanel();

		panel.setLayout(new FlowLayout());

		JLabel label = new JLabel("Выберите файл!");

		JButton button = new JButton();

		button.setText("Выбрать");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getStatisticData(getFilePath());
				} catch (FileNotFoundException e1) {
					System.out.println("Ошибка чтения файла");
				}
			}
		});

		ta = new JTextArea(7, 25);

		panel.add(ta);

		panel.add(label);

		panel.add(button);

		frame.add(panel);

		frame.setSize(300, 300);

		frame.setLocationRelativeTo(null);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);

	}

	public String getFilePath() {

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Choose a directory to save your file: ");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnValue = jfc.showSaveDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			if (jfc.getSelectedFile().isDirectory()) {
				System.out.println("Вы выбрали директорию: " + jfc.getSelectedFile());

			}
			return "" + jfc.getSelectedFile();
		} else {
			System.out.println("Вы ничего не выбрали");

			return null;
		}

	}

	public void getStatisticData(String path) throws FileNotFoundException {
		Scanner scn = new Scanner(new File(path));
		List<Integer> valuesList = new ArrayList<Integer>();

		while (scn.hasNext()) {

			valuesList.add((Integer) scn.nextInt());
		}

		ta.setText("90 percentile " + calculatePercentile(valuesList) + "\n" + "median " + calculateMedian(valuesList)
				+ "\n" + "average " + calculateAverageValue(valuesList) + "\n" + "max " + calculateMaxValue(valuesList)
				+ "\n" + "min " + calculateMinValue(valuesList));

		System.out.println("90 percentile " + calculatePercentile(valuesList));
		System.out.println("median " + calculateMedian(valuesList));
		System.out.println("average " + calculateAverageValue(valuesList));
		System.out.println("max " + calculateMaxValue(valuesList));
		System.out.println("min " + calculateMinValue(valuesList));

		scn.close();
	}

	public Integer calculateMedian(List<Integer> valuesList) {

		Collections.sort(valuesList);

		int middle = valuesList.size() / 2;
		if (valuesList.size() % 2 == 0) {
			int left = valuesList.get(middle) - 1;
			int right = valuesList.get(middle);
			int median = (left + right) / 2;

			return median;
		} else {

			return valuesList.get(middle);
		}

	}

	public double getAmountOfValuesNotLessThen(List<Integer> valuesList, int value) {

		double amount = 0;
		for (Integer i : valuesList) {
			if (i <= value) {
				++amount;
			}
		}

		return amount;
	}

	public String calculatePercentile(List<Integer> valuesList) {
		Collections.sort(valuesList);

		String data = "";
		StringBuilder sb = new StringBuilder();

		double percentile = 0;
		double nearestPercentile = 0;
		for (Integer i : valuesList) {
			percentile = getAmountOfValuesNotLessThen(valuesList, i) / valuesList.size() * 100;

			if (percentile == 90) {
				return i + "";
			} else if (percentile > nearestPercentile && percentile < 90) {
				nearestPercentile = percentile;
			}

		}

		return "В выборке отсутсвует 90 перценталь. Ближайший нижний перценталь: " + nearestPercentile;

	}

	public Integer calculateMaxValue(List<Integer> valuesList) {
		int max = valuesList.get(0);

		for (Integer i : valuesList) {
			if (i > max) {
				max = i;
			}
		}

		return max;
	}

	public Integer calculateMinValue(List<Integer> valuesList) {
		int min = valuesList.get(0);

		for (Integer i : valuesList) {
			if (i < min) {
				min = i;
			}
		}

		return min;
	}

	public Double calculateAverageValue(List<Integer> valuesList) {
		double averageValue = 0;
		double sum = 0;
		for (Integer i : valuesList) {
			sum += i;
		}

		averageValue = sum / valuesList.size();

		return averageValue;
	}

	public static void main(String[] args) {
		StatisticDataMain std = new StatisticDataMain();

	}
}
