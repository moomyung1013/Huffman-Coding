package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DecodeFrame 
{
	JPanel panel1=new JPanel();
	JPanel panelN=new JPanel();
	JPanel panelS=new JPanel();
	JPanel panelW=new JPanel();
	JPanel panelE=new JPanel();
	Container c;
	private JFrame frame;
	private final JTextArea textFieldEncode = new JTextArea();
	private JTextArea textFieldDecode = new JTextArea();
	
	public DecodeFrame(double dataDecodeTime, String originData, String decodeData)
	{
		textFieldEncode.setBounds(12, 80, 465, 321);
		textFieldEncode.setColumns(10);
		textFieldEncode.setText(originData);
		textFieldDecode.setText(decodeData);
		frame=new JFrame();
		c=frame.getContentPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Decoding Success!");
		
		panel1.setBackground(new Color(204,204,255));
		panelE.setBackground(SystemColor.menu);
		panelN.setBackground(SystemColor.menu);
		panelS.setBackground(SystemColor.menu);
		panelW.setBackground(SystemColor.menu);
		
		c.add(panel1,BorderLayout.CENTER);
		panel1.setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane(textFieldEncode);
		scrollPane.setBounds(12, 80, 465, 321);
		JScrollPane scrollPane2 = new JScrollPane(textFieldDecode);
		scrollPane2.setBounds(485, 80, 465, 321);
		
		textFieldDecode.setCaretPosition(0);
		textFieldEncode.setCaretPosition(0);

	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel1.add(scrollPane);
		panel1.add(scrollPane2);
		
		JLabel lblNewLabel = new JLabel("파일을 성공적으로 압축 해제 하였습니다!");
		lblNewLabel.setForeground(UIManager.getColor("SplitPaneDivider.draggingColor"));
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 24, 964, 29);
		panel1.add(lblNewLabel);
		
		JLabel encodeLabel = new JLabel("< 압축 파일 >");
		encodeLabel.setForeground(Color.DARK_GRAY);
		encodeLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12));
		encodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		encodeLabel.setBounds(12, 63, 465, 15);
		panel1.add(encodeLabel);
		
		JLabel decodeLabel = new JLabel("< 압축 해제 파일 >");
		decodeLabel.setForeground(Color.DARK_GRAY);
		decodeLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12));
		decodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		decodeLabel.setBounds(487, 63, 465, 15);
		panel1.add(decodeLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(184, 407, 585, 29);
		panel1.add(panel);
		panel.setLayout(null);
		
		String encodeTime="압축 해제 시간 : ";
		encodeTime+=Double.toString(dataDecodeTime)+" s";
		
		JButton startFrameButton = new JButton("시작 메뉴로 돌아가기");
		startFrameButton.setBounds(363, 0, 187, 29);
		panel.add(startFrameButton);
		startFrameButton.setBackground(SystemColor.inactiveCaptionBorder);
		startFrameButton.setFont(new Font("굴림", Font.BOLD, 12));
		startFrameButton.addActionListener(new MyActionListener());
		
		JLabel encodeTimeLabel = new JLabel(encodeTime);
		encodeTimeLabel.setFont(new Font("굴림", Font.BOLD, 14));
		encodeTimeLabel.setBounds(25, 0, 294, 29);
		panel.add(encodeTimeLabel);
		
		c.add(panelE,BorderLayout.EAST);
		c.add(panelN,BorderLayout.NORTH);
		c.add(panelS,BorderLayout.SOUTH);
		c.add(panelW,BorderLayout.WEST);
		
		frame.setSize(1000,500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	class MyActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(e.getActionCommand()=="시작 메뉴로 돌아가기") 
			{
				frame.setVisible(false);
				new StartFrame();
			}
		}
	}
}
