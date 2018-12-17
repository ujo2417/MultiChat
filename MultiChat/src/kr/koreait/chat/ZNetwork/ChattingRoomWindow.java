package kr.koreait.chat.ZNetwork;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import kr.koreait.chat.Member.MemberVO;
import kr.koreait.chat.Room.RoomUpdateWindow;
import kr.koreait.chat.Room.RoomVO;

public class ChattingRoomWindow extends JFrame {


	public RoomVO rvo;
	public MemberVO mvo;
	public JScrollPane 	conversation_scrollPane;
	public JPanel	  	northBar_panel,  conversation_panel, message_panel, 
	                    east_panel, participant_panel, exit_button_panel, east_panel_cover ;
	public JLayeredPane east_layerdPane;
	public JTextArea    conversation_textArea;
	public JLabel     	roomName_label, participant_label;
	public JTextField 	message_textfield;
	public JButton	 	message_send_button,  setting_button, exit_button;
	
	public  String columnNames[] = {"번호", "아이디", "성별", "나이"};
	public  DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		@Override
		public boolean isCellEditable(int row, int column) {				// table 내용 수정 불가
			return false;
		}
	};
	public  JTable participant_table = new JTable(model);
	
	public ChattingRoomWindow(RoomVO rvo, MemberVO mvo ) {
		frontEnd();
		this.rvo = rvo;
		this.mvo = mvo;
	}


	public void frontEnd() {
		setTitle("채팅방");
		setBounds(400, 200, 800, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(null);

		JLayeredPane layeredPane = new JLayeredPane();
		int adjust = 0;   
		if(!isResizable()) { adjust = 10;}
		layeredPane.setBounds(0, 0, this.getSize().width - 16 + adjust, this.getSize().height - 39 + adjust); 
		layeredPane.setLayout(null);
		
		this.add(layeredPane);
		setVisible(true); // 채팅방에 들어갈 수 있는 조건이 되면 true로 설정한다. MCC에서
		
		add_northBar_on(layeredPane);

		add_conversation_scrollPane_on(layeredPane);
		
		add_message_panel_on(layeredPane);
		
		add_east_panel_on(layeredPane);
		
//		add_east_panel_cover_on(layeredPane);

	}
	
	
	public void add_northBar_on(JLayeredPane layeredPane) {
		String fontName = "배달의민족 도현";
		northBar_panel = new JPanel();
		northBar_panel.setLayout(null);
		northBar_panel.setSize(layeredPane.getWidth() * 70/100, layeredPane.getHeight() * 10/100);
		northBar_panel.setLocation(0, 0);
		northBar_panel.setBackground(new Color(92, 209, 229));
		layeredPane.add(northBar_panel);
	
		roomName_label = new JLabel("방 제목 :  / 채널 : " );
		roomName_label.setSize(northBar_panel.getWidth() * 85/100, northBar_panel.getHeight() * 90/100);
		roomName_label.setLocation(northBar_panel.getWidth() * 1/100, (northBar_panel.getHeight()-roomName_label.getHeight())/2);
		roomName_label.setFont(new Font(fontName, Font.BOLD, 20));
		northBar_panel.add(roomName_label);
		
		setting_button = new JButton("설정");
		setting_button.setSize(northBar_panel.getWidth() * 12/90, northBar_panel.getHeight() * 80/130);
		setting_button.setLocation(northBar_panel.getWidth() * 1/100 + roomName_label.getWidth() + northBar_panel.getWidth() * 1/100, (northBar_panel.getHeight()-setting_button.getHeight())/2);
		setting_button.setFont(new Font(fontName, Font.BOLD, 20));
		setting_button.setBackground(new Color(250, 237, 125));
		northBar_panel.add(setting_button);
		
		setting_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
											
				if(mvo.getId().equals(rvo.getId())) {		// MemberVO ID와 RoomVO ID가 일치하면 방장
//				방장인 경우에만 설정 창이 실행된다.
					if(RoomUpdateWindow.isWindowCreated == false) {
						RoomUpdateWindow roomUpdateWindow = new RoomUpdateWindow(rvo);
						RoomUpdateWindow.isWindowCreated = true;
//						roomUpdateWindow.setLocation(this.getLocation().x-roomUpdateWindow.getWidth(), this.getLocation().y);
					}
					else {
						JOptionPane.showMessageDialog(null, "이미 만들어진 윈도우", "이미창", JOptionPane.WARNING_MESSAGE);
					}	
				}else {
//				방장이 아닌 경우 설정 창 실행 불가
					JOptionPane.showMessageDialog(null, "권한이 없습니다.", "수정불가", JOptionPane.WARNING_MESSAGE);
					
				}
				
			}
		});
		

	}
	
	public void add_conversation_scrollPane_on(JLayeredPane layeredPane) {
		
		String fontName = "배달의민족 도현";
		
		conversation_panel = new JPanel();
		conversation_panel.setSize(layeredPane.getWidth() * 70/100, layeredPane.getHeight() * 80/100);
		conversation_panel.setLocation(0, northBar_panel.getHeight());
		conversation_panel.setBackground(new Color(110, 227, 247));
		conversation_panel.setFont(new Font(fontName, Font.PLAIN, 30));
		layeredPane.add(conversation_panel);
		
		conversation_panel.setLayout(null);
		
//		JPanel test_panel = new JPanel();
////		test_panel.setSize(conversation_panel.getWidth()* 50/100, 1000);
//		test_panel.setBorder(new LineBorder(Color.RED));
//		
//		conversation_scrollPane = new JScrollPane(test_panel);
//		conversation_scrollPane.setSize(conversation_panel.getWidth() * 99/100, 100);
//		conversation_scrollPane.setLocation((conversation_panel.getWidth() - conversation_scrollPane.getWidth())/2, 10);
//		conversation_scrollPane.getVerticalScrollBar().setValue(0);
//		
//		conversation_panel.add(conversation_scrollPane);
		
		conversation_textArea = new JTextArea();
		conversation_textArea.setBackground(new Color(235, 235, 235));
		conversation_textArea.setFont(new Font(fontName, Font.PLAIN, 20));
		

		conversation_textArea.setEditable(false);
		
		conversation_scrollPane = new JScrollPane(conversation_textArea);
		conversation_scrollPane.setSize(conversation_panel.getWidth() * 99/100, conversation_panel.getHeight()*95/100);
		conversation_scrollPane.setLocation((conversation_panel.getWidth() - conversation_scrollPane.getWidth())/2, 10);
		conversation_scrollPane.getVerticalScrollBar().setValue(0);
		
		conversation_panel.add(conversation_scrollPane);
		
		
	}

	public void add_message_panel_on(JLayeredPane layeredPane) {
		String fontName = "배달의민족 도현";
		message_panel     = new JPanel();
		message_panel.setLayout(null);
		message_panel.setSize(layeredPane.getWidth() * 70/100, layeredPane.getHeight() * 10/100 + 4);
		message_panel.setLocation(0, northBar_panel.getHeight() + conversation_panel.getHeight());
		message_panel.setBackground(new Color(92, 209, 229));
		layeredPane.add(message_panel);
		
		message_textfield  = new JTextField();
		message_textfield.setSize(message_panel.getWidth() * 80/100, message_panel.getHeight() * 80/100);
		message_textfield.setLocation(message_panel.getWidth() * 1/100, (message_panel.getHeight()-message_textfield.getHeight())/2);
		message_textfield.setFont(new Font(fontName, Font.PLAIN, 20));
		message_panel.add(message_textfield);
		
		message_send_button  = new JButton("전송");
		message_send_button.setSize(message_panel.getWidth() * 17/94, message_panel.getHeight() * 80/100);
		message_send_button.setLocation(message_panel.getWidth() * 1/100 + message_textfield.getWidth() + message_panel.getWidth() * 1/100, (message_panel.getHeight()-message_textfield.getHeight())/2);
		message_send_button.setFont(new Font(fontName, Font.BOLD, 20));
		message_send_button.setBackground(new Color(250, 237, 125));
		message_panel.add(message_send_button);
	}

	public void add_east_panel_on(JLayeredPane layeredPane) {
		east_panel     = new JPanel();
		east_panel.setLayout(null);
		east_panel.setSize(layeredPane.getWidth() * 30/100, layeredPane.getHeight() * 100/100);
		east_panel.setLocation(northBar_panel.getWidth(), 0);
		east_panel.setBackground(new Color(92, 209, 229));
		layeredPane.add(east_panel);
		
		add_exit_button_panel_on(east_panel);
		
		add_party_label_on(east_panel);
		
		add_party_panel_on(east_panel);
	}
	
	public void add_exit_button_panel_on(JPanel panel) {
		
		exit_button_panel  = new JPanel();
		exit_button_panel.setLayout(null);
		exit_button_panel.setSize(panel.getWidth() * 100/100, panel.getHeight() * 10/100);
		exit_button_panel.setLocation((east_panel.getWidth()-exit_button_panel.getWidth())/2, 0);
		exit_button_panel.setBackground(new Color(92, 209, 229));
		panel.add(exit_button_panel);
		

		exit_button = new JButton("채팅방 나가기");
		exit_button.setSize(exit_button_panel.getWidth()*90/110, exit_button_panel.getHeight()*80/130);
		exit_button.setLocation((exit_button_panel.getWidth()-exit_button.getWidth())/2,
				(exit_button_panel.getHeight()-exit_button.getHeight())/2);
		exit_button_panel.add(exit_button);
		
		String fontName = "배달의민족 도현";
		exit_button.setFont(new Font(fontName, Font.BOLD, 20));
		exit_button.setBackground(new Color(250, 237, 125));
		
	}

	public void add_party_label_on(JPanel panel) {
		participant_label     = new JLabel("참가자 목록");
		participant_label.setLayout(null);
		participant_label.setSize(panel.getWidth() * 90/100, panel.getHeight() * 10/100);
		participant_label.setLocation((panel.getWidth()-participant_label.getWidth())/2, exit_button_panel.getLocation().y + exit_button_panel.getHeight());
		participant_label.setBackground(new Color(92, 209, 229));
		panel.add(participant_label);
		
		String fontName = "배달의민족 도현";
		participant_label.setFont(new Font(fontName, Font.BOLD, 30));
		participant_label.setHorizontalAlignment(JLabel.CENTER);
		
		
	}
	
    public void add_party_panel_on(JPanel panel) {
    	
    	participant_panel  = new JPanel();
		participant_panel.setLayout(null);
		participant_panel.setSize(panel.getWidth() * 90/100, panel.getHeight() * 75/92);
		participant_panel.setLocation((east_panel.getWidth()-participant_panel.getWidth())/2
				, participant_label.getLocation().y + participant_label.getHeight() - 7);
		participant_panel.setBackground(new Color(92, 209, 229));
		panel.add(participant_panel);
		
//		table.getColumnModel().getColumn(0).setPreferredWidth(10);
//		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		participant_table.setRowHeight(25);

		middle_alignment();
		
		JScrollPane jsp = new JScrollPane(participant_table);
		jsp.setSize(participant_panel.getWidth()-20, participant_panel.getHeight() * 85/100);
		jsp.setLocation((participant_panel.getWidth()-jsp.getWidth())/2, 10);
		participant_panel.add(jsp);
		
	}
    
	public void add_east_panel_cover_on(JLayeredPane layeredPane) {
		east_layerdPane     = new JLayeredPane();
		east_layerdPane.setLayout(null);
		east_layerdPane.setSize(layeredPane.getWidth() * 20/100, layeredPane.getHeight() * 100/100);
		east_layerdPane.setLocation(northBar_panel.getWidth(), 0);
//		east_panel_cover.setBorder(new LineBorder(Color.YELLOW, 2));
//		east_layerdPane.setBackground(Color.BLACK);
		layeredPane.add(east_layerdPane, 0);
		east_layerdPane.moveToFront(layeredPane);
		east_panel_cover = new JPanel();
		east_panel_cover.setSize(east_layerdPane.getWidth(), east_layerdPane.getHeight());
		east_panel_cover.setLocation(0,0);
		east_panel_cover.setBackground(new Color(0, 255, 255, 100));
		east_layerdPane.add(east_panel_cover);
		
	}

    private void middle_alignment() {
				
		DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
		tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcmSchedule = participant_table.getColumnModel();
		for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
			tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
		}
	}
	
    // 테스트용 메인
	public static void main(String[] args) {
		ChattingRoomWindow window = new ChattingRoomWindow(new RoomVO(), new MemberVO());
	}



}
