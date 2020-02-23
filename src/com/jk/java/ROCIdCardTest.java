package com.jk.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ROCIdCardTest extends JFrame {

    private String selectGender = "男";
    private int selectGenderId = 1;
    private String selectLocal;

    private JScrollPane layoutScroll;
    private JTextArea resultView;

    private HashSet<String> hashSet;
    private ArrayList<String> arrayList;
    private int h_len = 0;

    public ROCIdCardTest() {
        super("中華民國國民身分證產生器");
        init();
    }

    private void init() {
        ActionListener radioListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractButton button = (AbstractButton) e.getSource();
                selectGender = button.getText();
                selectGenderId = selectGender.equals("男") ? 1 : 2;
            }
        };

        JRadioButton manRadioButton = new JRadioButton("男");
        manRadioButton.setSelected(true);
        JRadioButton maleRadioButton = new JRadioButton("女");
        manRadioButton.addActionListener(radioListener);
        maleRadioButton.addActionListener(radioListener);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(manRadioButton);
        buttonGroup.add(maleRadioButton);

        JComboBox localSpinner = new JComboBox(Card.locations);
        JList localList = new JList(Card.locations);
        selectLocal = localSpinner.getSelectedItem().toString();
        localSpinner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectLocal = localSpinner.getSelectedItem().toString();
            }
        });

        JButton btn = new JButton("產生");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createID(selectGender, selectGenderId, selectLocal);
            }
        });

        resultView = new JTextArea();
        resultView.setFont(new Font("Default", Font.BOLD, 24));
        layoutScroll = new JScrollPane(resultView);

        Container con = this.getContentPane();
        JScrollPane jScrollPane = new JScrollPane(localList);
        jScrollPane.setPreferredSize(new Dimension(100, 200));
        JPanel jPanel = new JPanel();
        jPanel.add(localSpinner);
        con.add(BorderLayout.EAST, jScrollPane);
        con.add(BorderLayout.WEST, jPanel);

        setLayout(new BorderLayout());
        setSize(640, 480);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel radioButtonLayout = new JPanel(new FlowLayout());
        radioButtonLayout.add(manRadioButton);
        radioButtonLayout.add(maleRadioButton);
        JPanel top = new JPanel(new BorderLayout());
        top.add(radioButtonLayout, BorderLayout.WEST);
        top.add(localSpinner, BorderLayout.CENTER);
        top.add(btn, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);
        add(layoutScroll, BorderLayout.CENTER);

        hashSet = new HashSet<>();
        arrayList = new ArrayList<>();
    }

    private void createID(String selectGender, int selectGenderId, String selectLocal) {
        int index = Arrays.asList(Card.locations).indexOf(selectLocal);

        int n1 = Card.locationCode[index] / 10;
        int n2 = Card.locationCode[index] % 10;
        int n4 = (int) (Math.random() * 10);
        int n5 = (int) (Math.random() * 10);
        int n6 = (int) (Math.random() * 10);
        int n7 = (int) (Math.random() * 10);
        int n8 = (int) (Math.random() * 10);
        int n9 = (int) (Math.random() * 10);
        int n10 = (int) (Math.random() * 10);
        int n11 = (int) (Math.random() * 10);
        int temp = n1 + n2 * 9 + selectGenderId * 8 + n4 * 7 + n5 * 6 + n6 * 5 + n7 * 4 + n8 * 3 + n9 * 2 + n10 + n11;

        while (temp % 10 != 0) {
            n4 = (int) (Math.random() * 10);
            n5 = (int) (Math.random() * 10);
            n6 = (int) (Math.random() * 10);
            n7 = (int) (Math.random() * 10);
            n8 = (int) (Math.random() * 10);
            n9 = (int) (Math.random() * 10);
            n10 = (int) (Math.random() * 10);
            n11 = (int) (Math.random() * 10);
            temp = n1 + n2 * 9 + selectGenderId * 8 + n4 * 7 + n5 * 6 + n6 * 5 + n7 * 4 + n8 * 3 + n9 * 2 + n10 + n11;
        }

        String res = Card.locationID[index] + selectGenderId + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11;
        h_len = hashSet.size();
        hashSet.add(selectGender + " " + selectLocal + " " + res);

        if (hashSet.size() > h_len) {
            arrayList.add(selectGender + " " + selectLocal + " " + res);
        }

        StringBuilder s = new StringBuilder();
        for (String str : arrayList) {
            s.append(str).append("\n");
        }
        resultView.setText(s.toString());
    }

    public static void main(String[] args) {
        new ROCIdCardTest();
    }

    static class Card {
        private static String[] locations = {"臺北市", "臺中市", "基隆市", "臺南市", "高雄市", "新北市", "宜蘭縣", "桃園市", "新竹縣", "苗栗縣", "南投縣", "彰化縣", "雲林縣", "嘉義縣", "屏東縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", "連江縣", "嘉義市", "新竹市"};
        private static int[] locationCode = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 22, 23, 24, 27, 28, 29, 30, 32, 33, 34, 35};
        private static String[] locationID = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "T", "U", "V", "X", "W", "Z", "I", "O"};
    }
}
