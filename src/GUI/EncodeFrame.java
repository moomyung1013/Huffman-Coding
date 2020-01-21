package GUI;

import javax.swing.*;

import FILE.Node;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EncodeFrame 
{
	public long endTime;
	String filename;
	JPanel panel1=new JPanel();
	JPanel panelN=new JPanel();
	JPanel panelS=new JPanel();
	JPanel panelW=new JPanel();
	JPanel panelE=new JPanel();
	Container c;
	ArrayList <Node> node=new ArrayList<Node>();
	//Node [] node;
	private JFrame frame;
	private final JTextArea textFieldOriginal = new JTextArea();
	private final JTextArea textFieldEncode = new JTextArea();
	//JScrollPane scrollPane=new JScrollPane(textFieldOriginal);

	
	public EncodeFrame(ArrayList<Node> node, double dataEncodeTime, double dataEncodeRate, String str, String EncodeData)
	{
		this.node=node;
		
		textFieldOriginal.setBounds(12, 80, 465, 321);
		textFieldOriginal.setColumns(10);
		textFieldOriginal.setEditable(false);
		frame=new JFrame();
		c=frame.getContentPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Encoding Success!");
		
		panel1.setBackground(new Color(204,204,255));
		panelE.setBackground(SystemColor.menu);
		panelN.setBackground(SystemColor.menu);
		panelS.setBackground(SystemColor.menu);
		panelW.setBackground(SystemColor.menu);
		
		c.add(panel1,BorderLayout.CENTER);
		panel1.setLayout(null);
		
		textFieldOriginal.setText(str);
		textFieldEncode.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		textFieldEncode.setText(EncodeData);
		
		JLabel lblNewLabel = new JLabel("\uD30C\uC77C\uC744 \uC131\uACF5\uC801\uC73C\uB85C \uC555\uCD95\uD558\uC600\uC2B5\uB2C8\uB2E4!");
		lblNewLabel.setForeground(UIManager.getColor("SplitPaneDivider.draggingColor"));
		lblNewLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 24));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 24, 964, 29);
		panel1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("< \uC6D0\uBCF8 \uD30C\uC77C >");
		lblNewLabel_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_1.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(12, 63, 465, 15);
		panel1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("< \uC555\uCD95 \uB370\uC774\uD130 >");
		lblNewLabel_2.setForeground(Color.DARK_GRAY);
		lblNewLabel_2.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(487, 63, 463, 15);
		panel1.add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane(textFieldOriginal);
		scrollPane.setBounds(12, 80, 465, 321);
		JScrollPane scrollPane2 = new JScrollPane(textFieldEncode);
		scrollPane2.setBounds(485, 80, 465, 321);
		
		textFieldOriginal.setCaretPosition(0);
		textFieldEncode.setCaretPosition(0);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel1.add(scrollPane);
		panel1.add(scrollPane2);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 407, 591, 29);
		panel1.add(panel);
		panel.setLayout(null);
		
		String encodeRate="압축률 : ";
		String encodeTime="압축시간 : ";
		encodeRate+=Double.toString(dataEncodeRate)+" %";
		encodeTime+=Double.toString(dataEncodeTime)+" s";
		JLabel encodeRateLabel = new JLabel(encodeRate);
		encodeRateLabel.setFont(new Font("굴림", Font.BOLD, 14));
		encodeRateLabel.setBounds(12, 0, 333, 29);
		panel.add(encodeRateLabel);
		
		JLabel encodeTimeLabel = new JLabel(encodeTime);
		encodeTimeLabel.setFont(new Font("굴림", Font.BOLD, 14));
		encodeTimeLabel.setBounds(353, 0, 238, 29);
		panel.add(encodeTimeLabel);
		
		JButton startFrameButton = new JButton("시작 메뉴로 돌아가기");
		startFrameButton.setBackground(SystemColor.inactiveCaptionBorder);
		startFrameButton.setFont(new Font("굴림", Font.BOLD, 12));
		startFrameButton.setBounds(760, 407, 192, 29);
		startFrameButton.addActionListener(new MyActionListener());
		panel1.add(startFrameButton);
		
		JButton FreqButton = new JButton("빈도수 찾기");
		FreqButton.setFont(new Font("굴림", Font.BOLD, 12));
		FreqButton.setBackground(SystemColor.inactiveCaptionBorder);
		FreqButton.setBounds(615, 407, 133, 29);
		FreqButton.addActionListener(new MyActionListener());
		panel1.add(FreqButton);
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
			else if(e.getActionCommand()=="빈도수 찾기")
			{
				boolean result=false;
				
				String ch=JOptionPane.showInputDialog("문자를 입력하시오!");
				if(ch!=null)
				{
					for(int i=0;i<node.size();i++)
					{
						if(ch.charAt(0)==node.get(i).ch)
						{
							String success="\'"+ch+"\' 의 빈도수는 "+Integer.toString(node.get(i).fq)+"입니다!";
							JOptionPane.showMessageDialog(null,success,"Success",JOptionPane.INFORMATION_MESSAGE);
							result=true;
							break;
						}
					}
				
					if(result==false)
					{
						JOptionPane.showMessageDialog(null, "해당 정보가 존재하지 않습니다!","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
}
