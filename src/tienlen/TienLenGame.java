package tienlen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import game.BotGame;
import game.Client;
import samloc.SamLocGame;


public class TienLenGame extends JFrame{

	 private int numberOfPlayers = 0;
	 private int numberOfBots = 0;
	 private JButton[] playerButtons;
	 private JButton[] AIButtons;
	 
	 public TienLenGame()  {
	        setTitle("Game Setup");
	        setSize(800, 500);
	        setLocation(300,200);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLayout(null); 
	        
	        IntroPanel panel = new IntroPanel(this);
	        this.setContentPane(panel);
	      
	        this.setVisible(true);
	     
	    }
	     
	
	    class IntroPanel extends JPanel {
	    	protected JFrame frame;
	    	Image introImage = new ImageIcon(getClass().getResource("/background/neon.jpg")).getImage();
	  		public IntroPanel(JFrame frame) {
	  			this.frame = frame;
	  			this.setLayout(null);
	  			this.setUp();
	  		}
	  		
	  		public void paintComponent(Graphics g) {
	            g.drawImage(introImage, 0, 0,getWidth() ,getHeight(), this);
			}
	  		
	  		public void setUp() {
	  			
	  			JLabel playerLabel = new JLabel("Select Number of Players:");
		        playerLabel.setBounds(350, 120, 300, 30);
		        playerLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18)); 
		        playerLabel.setForeground(Color.MAGENTA);
		        add(playerLabel);
		        playerButtons = new JButton[5];
		        for (int i = 1; i <= 4; i++) {
		        	playerButtons[i] = new PlayerButton(String.valueOf(i));
		            playerButtons[i].setBounds(350 + (i - 1) * 80, 160, 50, 30);	         
		            add(playerButtons[i]);
		        }
		        
		        JLabel aiLabel = new JLabel("Select Number of Bots:");
		        aiLabel.setBounds(350, 200, 300, 30);
		        aiLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18)); 
		        aiLabel.setForeground(Color.MAGENTA);
		        add(aiLabel);
		        
		        AIButtons = new JButton[5];
		        for (int i = 0; i <= 3; i++) {
		        	AIButtons[i] = new AIButton(String.valueOf(i));
		            AIButtons[i].setBounds(350 + (i ) * 80, 240, 50, 30);
		            add(AIButtons[i]);
		        }
		        
		        JButton startButton = new StartButton("");
		        startButton.setBounds(420, 280, 150, 100);
		        add(startButton);
	  		}
	  		class StartButton extends JButton implements ActionListener{

				public StartButton(String s) {
					super(s);
					ImageIcon i = new ImageIcon(getClass().getResource("/buttons/start.png"));
					
			        Image resizedImg = i.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH); // Resize về 50x50
			        ImageIcon resizedIcon = new ImageIcon(resizedImg); 
			        
					this.setIcon(resizedIcon);
					this.setPreferredSize(new Dimension(40, 40));
					this.setBorderPainted(false);
				    this.setFocusPainted(false);  
					this.setContentAreaFilled(false); 
					
					this.addActionListener(this);
				}
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub           
					if (numberOfPlayers + numberOfBots > 4 || numberOfPlayers + numberOfBots <= 1) {
		                JOptionPane.showMessageDialog(
		                        TienLenGame.this,
		                        "The total number of players and AI must be between 1 and 4.",
		                        "Invalid Selection",
		                        JOptionPane.ERROR_MESSAGE
		                );
		            } else {
		            	frame.setVisible(false);
		            	start();
		            }
				}
			  }
	  		
	  		
	      }
	    
	    class PlayerButton extends JButton implements ActionListener{

			public PlayerButton(String s) {
				super(s);
				this.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18)); 
				this.setBackground(Color.white);
				this.addActionListener(this);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub           
				 String s = e.getActionCommand();
				 numberOfPlayers = Integer.parseInt(s);
				 for(int i=1; i <= 4; i++) {
					 if(i==numberOfPlayers){ 
						 playerButtons[i].setBackground(Color.cyan);
					 } else
					 {
						 playerButtons[i].setBackground(Color.white);
					 }
				 }
				 repaint();
			}
		  }
	    
	    class AIButton extends JButton implements ActionListener{

			public AIButton(String s) {
				super(s);
				this.setBackground(Color.white);
				this.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18)); 
				this.addActionListener(this);
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub           
				String s = e.getActionCommand();
				 numberOfBots = Integer.parseInt(s);
				 for(int i=0; i <= 3; i++) {
					 if(i==numberOfBots){ 
						 AIButtons[i].setBackground(Color.cyan);
					 } else
					 {
						 AIButtons[i].setBackground(Color.white);
					 }
				 }
				 repaint();
			}
			
		  }
	  
	public void start() {
		numberOfPlayers+=numberOfBots;
		TienLenServer server = new TienLenServer(numberOfPlayers);
        new Thread(() -> server.start(1234)).start();  
        
        Client[] clients = new Client[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers - numberOfBots; i++) {
            clients[i] = new TienLenClient(numberOfPlayers);
        }

        for (int i = numberOfPlayers - numberOfBots; i < numberOfPlayers; i++) {
           clients[i] = new BotGame(numberOfPlayers);
        }
	}
	    
	public static void main(String[] args) {
		
		TienLenGame game = new TienLenGame();
	}
  
}


