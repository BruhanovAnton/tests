package task4;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import task2.PointAndRectangleMain;

public class BankStatisticDataMain {

	JTextArea ta;

	public BankStatisticDataMain() {
		JFrame frame = new JFrame("Statistic calculator");

		JPanel panel = new JPanel();

		panel.setLayout(new FlowLayout());

		JLabel label = new JLabel("Выберите файл!");

		JButton button = new JButton();

		button.setText("Выбрать");

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getBankSatatistic(getFilePath());
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

	public void getBankSatatistic(String path) throws FileNotFoundException {

		Scanner scn = new Scanner(new File(path));
		List<Visitor> valuesList = new ArrayList<Visitor>();

		String[] time = new String[2];

		int startTime = 0;
		int endTime = 0;
		while (scn.hasNext()) {

			String period = scn.nextLine();
			time = period.split(" ");

			startTime = Integer.parseInt(time[0]);
			endTime = Integer.parseInt(time[1]);

			valuesList.add(new Visitor(startTime, endTime));

		}

		getMaxAttendance(valuesList);

		scn.close();
	}

	public void getMaxAttendance(List<Visitor> valuesList) {
		int maxPeopleAmount = 0;
		int openStore = 480;
		int closeStore = 1200;
		Map<Integer, Integer> statisticDataList = new TreeMap<Integer, Integer>();
		MaxVisitorTime mvt = new MaxVisitorTime(0, 0);

		while (openStore < closeStore) {
			int start = openStore;
			int end = openStore + 30;
			int people = 0;
			for (Visitor v : valuesList) {
				if (v.getStartVisitTime() > start && v.getEndVisitTime() < end) {
					++people;

				}
			}

			if (people > mvt.getPeopleAmount()) {
				mvt.setPeopleAmount(people);
				mvt.setTimePeriod(openStore);
				statisticDataList.put(people, openStore);
			}
			openStore += 30;

		}

		System.out.println(
				"Максимльмое число посетителей: " + mvt.getPeopleAmount() + " в период: " + mvt.getClockTime());
		StringBuilder sb = new StringBuilder();
		
		sb.append("Максимльмое число посетителей: " + mvt.getPeopleAmount() + " в период: " + mvt.getClockTime()+"\n");
		for (Map.Entry<Integer, Integer> entry : statisticDataList.entrySet()) {
			int key = entry.getKey();
			int value = entry.getValue();

			System.out.println(key + " человека в период " + new MaxVisitorTime(key, value).getClockTime());

			sb.append(key + " человека в период " + new MaxVisitorTime(key, value).getClockTime() + "\n");
		}

		ta.setText(sb.toString());
	}

	static class MaxVisitorTime {
		int peopleAmount;
		int timePeriod;

		public MaxVisitorTime(int peopleAmount, int timePeriod) {
			super();
			this.peopleAmount = peopleAmount;
			this.timePeriod = timePeriod;
		}

		public int getPeopleAmount() {
			return peopleAmount;
		}

		public void setPeopleAmount(int peopleAmount) {
			this.peopleAmount = peopleAmount;
		}

		public int getTimePeriod() {
			return timePeriod;
		}

		public void setTimePeriod(int timePeriod) {
			this.timePeriod = timePeriod;
		}

		public String getClockTime() {

			String time = "";

			int hours = (timePeriod / 60);
			int fromTime = 0;

			if (timePeriod % 60 == 0) {

				time = hours + ":00 - " + hours + ":30";
			} else {
				time = hours + ":30 - " + hours + ":00";
			}

			return time;
		}

	}

	static class Visitor {
		int startVisitTime;
		int endVisitTime;

		public Visitor(int startVisitTime, int endVisitTime) {
			super();
			this.startVisitTime = startVisitTime;
			this.endVisitTime = endVisitTime;
		}

		public int getStartVisitTime() {
			return startVisitTime;
		}

		public void setStartVisitTime(int startVisitTime) {
			this.startVisitTime = startVisitTime;
		}

		public int getEndVisitTime() {
			return endVisitTime;
		}

		public void setEndVisitTime(int endVisitTime) {
			this.endVisitTime = endVisitTime;
		}

	}

	public static void main(String[] args) {
		BankStatisticDataMain bsd = new BankStatisticDataMain();

	}

}
