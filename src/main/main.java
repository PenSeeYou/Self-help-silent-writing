package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

public class main {
    public static String[] textfile;
    public static Boolean openyn = true;

    public static void main(String[] args) {
        JFrame frame = new JFrame("听写程序");
        frame.setSize(300, 80);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        UI(panel, frame);
        frame.add(panel);
        frame.setVisible(true);
    }

    public static void UI(JPanel panel, JFrame frame) {
        panel.setLayout(null);
        JTextField jTextField = new JTextField("点击右边的按钮，选择xls文件");
        jTextField.setBounds(5, 0, 265, 20);
        jTextField.setEditable(false);
        panel.add(jTextField);
        // Button
        JButton find = new JButton("...");
        find.setBounds(270, 0, 20, 20);
        panel.add(find);

        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jf = new JFileChooser();
                jf.showOpenDialog(frame);//显示打开的文件对话框
                File f = jf.getSelectedFile();//使用文件类获取选择器选择的文件
                String s = f.getAbsolutePath();//返回路径名
                jTextField.setText(s);
                try {
                    InputStream is = new FileInputStream(new File(s).getAbsolutePath());
                    Workbook workbook = Workbook.getWorkbook(is);
                    Sheet sheet = workbook.getSheet(0);
                    int rows = sheet.getRows();
                    textfile = new String[rows];
                    int Ai;
                    int Aj;
                    for (Ai = 0; Ai < 1; Ai++) {
                        for (Aj = 0; Aj < rows; Aj++) {
                            //System.out.println("第" + Aj + "行，第" + Ai + "列为：" + sheet.getCell(Ai, Aj).getContents());
                            textfile[Aj] = sheet.getCell(Ai, Aj).getContents();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // Start Button
        JButton startbutton = new JButton("开始听写");
        startbutton.setBounds(110, 25, 80, 20);
        panel.add(startbutton);

        startbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (startbutton.getText().toString().equals("停止默写")) {
                    openyn = false;
                    startbutton.setText("开始默写");
                } else {
                    if (textfile[0] != null) {
                        thremd();
                        openyn = true;
                        startbutton.setText("停止默写");
                    }
                }
            }
        });
    }

    public static void thremd() {
        new Thread() {
            @Override
            public void run() {
                VoiceCompose voiceCompose = new VoiceCompose();
                voiceCompose.mainc("请准备好纸和笔。听写马上开始！每个词语听两遍！每遍间隔3秒哦！");
                for (int i = 0; i < textfile.length; i++) {
                    if (openyn == true) {
                        String j = i + 1 + "";
                        voiceCompose.mainc("第" + j + "个，" + textfile[i]);
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {

                        }
                        if (openyn = true) {
                            voiceCompose.mainc(textfile[i]);
                            try {
                                Thread.sleep(3000);
                            } catch (Exception e) {

                            }
                        }
                    } else {
                        break;
                    }

                }
            }
        }.start();
    }
}

