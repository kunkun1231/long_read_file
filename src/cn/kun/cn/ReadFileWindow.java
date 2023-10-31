package cn.kun.cn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * 读取文件 窗口
 * 
 * @author yd_k
 *
 */
public class ReadFileWindow extends JFrame {

	private String file = null;
	private String creatFile = null;
	private String find = null;
	private JTextField textFindField;
	private JTextField lineNumber;
	private JTextField startDate;
	private JTextField endDate;
	private JLabel ruslutlabel;
	private boolean isPrint = true;
	private JRadioButton[] button;
	private static boolean sd = true;

	public ReadFileWindow() {
		setTitle("读取文件信息");
		JPanel jPanel = new JPanel();
		jPanel.setLayout(new BorderLayout());

		JPanel center = new JPanel();
		center.setBackground(Color.white);
		center.setLayout(new GridLayout(9, 1));
		// 返回结果
		handFile(center);
		// 添加文件按钮
		addFileButton(center, "请选择文件", true);
		// 添加文件按钮
		addFileButton(center, "请选择文件夹", false);
		// 添加条件
		addFindData(center);
		// 是否打印
		addIsPrintf(center);
		// 打印行数
		addPrintLine(center);
		// 时间查询
		findFindDate(center);
		// 返回结果
		result(center);
		// 确定
		addCommit(center);

		// jPanel.add(top, BorderLayout.NORTH);
		jPanel.add(center, BorderLayout.CENTER);
		add(jPanel);

	}

	public void addCommit(JPanel center) {
		JPanel fileBu = new JPanel(new GridLayout(1, 2));
		JPanel left = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel right = new JPanel(new FlowLayout(FlowLayout.CENTER));
		left.setBackground(Color.white);
		right.setBackground(Color.white);

		final JButton button = new JButton("提交");

		final JButton button1 = new JButton("停止");

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String find = textFindField.getText();
				String line = lineNumber.getText();
				String start = startDate.getText();
				String end = endDate.getText();
				ruslutlabel.setText("正在查询...请稍候");
				String regex = "[0-9]{3}";
				
				ruslutlabel.updateUI();

				if ("".equals(start) && "".equals(end)) {
					if (!"".equals(find) && !"".equals(file)
							&& !"".equals(creatFile)) {

						try {
							if(line.matches(regex)){
								ReadFindFile.findSwingWork(file, find,
										Integer.parseInt(line), isPrint, creatFile,
										ruslutlabel, button,button1);
							}else{
								ruslutlabel.setText("行数请输入数字");
							}
						} catch (Exception e1) {
							e.paramString();
						}
					} else {
						ruslutlabel.setText("文件路径为空 or 关键字为空");
					}
				} else {
					try {

						ReadFindFile.swingWorkDate(file, start, end, creatFile,
								"", ruslutlabel, button,button1);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		button1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ruslutlabel.setText("读取停止");
				sd = true;
				ReadFindFile.SystemStop();

			}
		});
		button1.setVisible(false);
		fileBu.add(button);
		fileBu.add(button1);
		left.add(button);
		right.add(button1);
		fileBu.add(left);
		fileBu.add(right);
		center.add(fileBu);
	}

	public void result(JPanel center) {

		JPanel fileBu = new JPanel(new FlowLayout(FlowLayout.CENTER));
		fileBu.setBackground(Color.white);
		ruslutlabel = new JLabel("");
		fileBu.add(ruslutlabel);
		center.add(ruslutlabel);
	}

	public void handFile(JPanel center) {
		JPanel fileBu = new JPanel(new FlowLayout(FlowLayout.CENTER));
		fileBu.setBackground(Color.white);
		JLabel jLabel = new JLabel("查询文件内容");
		fileBu.add(jLabel);
		center.add(fileBu);

	}

	public void addIsPrintf(JPanel center) {
		JPanel fileBu = new JPanel(new FlowLayout(FlowLayout.LEFT));
		fileBu.setBackground(Color.white);
		JLabel jlabel = new JLabel("是否打印文件:");
		String[] st = { "是", "否" };
		fileBu.add(jlabel);
		setJrAdio(st, fileBu);

		center.add(fileBu);
	}

	private void setJrAdio(String[] st, JPanel fileBu) {
		ButtonGroup bg = new ButtonGroup();
		button = new JRadioButton[st.length];
		for (int i = 0; i < st.length; i++) {
			button[i] = new JRadioButton(st[i]);
			if (i == 0) {
				button[i].setSelected(true);
			}
			fileBu.add(button[i]);
			button[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (button[0].isSelected()) {
						isPrint = true;
					}
					if (button[1].isSelected()) {
						isPrint = false;
					}
					System.out.println(isPrint);
				}
			});
			bg.add(button[i]);
		}
	}

	public void addPrintLine(JPanel center) {
		JPanel fileBu = new JPanel();

		fileBu.setLayout(new BorderLayout());
		fileBu.setBackground(Color.white);
		JLabel label = new JLabel("请输入打印行数:");
		lineNumber = new JTextField("20");

		fileBu.add(label, BorderLayout.WEST);

		fileBu.add(lineNumber, BorderLayout.CENTER);

		center.add(fileBu);
	}

	public void findFindDate(JPanel center) {
		JPanel fileBu = new JPanel();

		fileBu.setLayout(new GridLayout(1, 2));
		JPanel left = new JPanel(new GridLayout(1, 2));

		JLabel label = new JLabel("开始时间:");

		startDate = new JTextField();
		// left.setSize(60, 600);
		left.add(label);
		left.add(startDate);

		JPanel right = new JPanel(new GridLayout(1, 2));

		JLabel labelRight = new JLabel("结束时间:");
		endDate = new JTextField();
		right.add(labelRight);
		right.add(endDate);

		left.setBackground(Color.white);
		right.setBackground(Color.white);
		fileBu.add(left);
		fileBu.add(right);

		center.add(fileBu);
	}

	public void addFindData(JPanel center) {
		JPanel fileBu = new JPanel();

		fileBu.setLayout(new BorderLayout());
		fileBu.setBackground(Color.white);
		JLabel label = new JLabel("请输入关键字:");
		textFindField = new JTextField();

		fileBu.add(label, BorderLayout.WEST);

		fileBu.add(textFindField, BorderLayout.CENTER);

		center.add(fileBu);
	}

	public void addFileButton(JPanel center, String title, final boolean bool) {
		JPanel fileBu = new JPanel();
		fileBu.setBackground(Color.white);
		fileBu.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton choose_Button = new JButton(title);
		final JLabel label = new JLabel("请选择文件...");

		fileBu.add(choose_Button);
		fileBu.add(label);

		choose_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				if (!bool) {
					fileChooser
							.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				}
				int showDialog = fileChooser.showOpenDialog(null);
				if (showDialog == 0) {
					if (bool) {
						file = fileChooser.getSelectedFile().getPath();
						label.setText(file);
					} else {
						creatFile = fileChooser.getSelectedFile().getPath();
						label.setText(creatFile);
					}

				}
				System.out.println(showDialog);
			}
		});

		center.add(fileBu);

	}

	public static void main(String[] args) {
		ReadFileWindow rf = new ReadFileWindow();
		rf.setSize(550, 550);
		rf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rf.setVisible(true);

	}

}
