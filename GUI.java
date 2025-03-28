import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;

public class GUI
{
    private Game game;

    private JFrame window;
    private Images image = new Images();
    private Container con;
    private JPanel titleName, startPanel, mainTextPanel, choiceButtonPanel, playerPanel;
    private JLabel titleText, scrollLabel, backgroundLabel, roomLabel, hpLabel, hpLabelNumber, weaponLabel, weaponLabelInfo;
    private Font titleFont = new Font("Times New Roman", Font.PLAIN, 20);
    private Font startFont = new Font("Times New Roman", Font.PLAIN, 22);
    private Font mainFont = new Font("Times New Roman", Font.PLAIN, 30);
    private JButton startButton, choiceButton;
    private JTextArea mainTextArea, roomDescriptionArea;
    private String pad;
    private Room currentRoom;

    private ImageIcon background;

    public GUI()
    {
        // image = new Images();//Load images before game starts
        game = new Game();

        pad = "                     ";
        window = new JFrame();

        // Create the title panel first and add it to the content pane
        titleName = new JPanel();
        titleName.setBounds(500, 30, 1000, 1000); //large bounds for the scroll to be in

        titleName.setLayout(null); // Allow manual positioning
        titleName.setOpaque(false); // Prevents grey background
        setGUI();
    }

    public void setGUI()
    {
        //Set up the window and the container (these are the displayed image frames)

        window.setSize(1250, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);

        con = window.getContentPane();
        con.setLayout(null);

        // Load and set the background image
        background = image.getImage("Beginning");
        backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, window.getWidth(), window.getHeight());
        con.add(backgroundLabel);
        window.getContentPane().setBackground(Color.BLACK);

        //Load the red scroll for the start message
        ImageIcon scrollMessage = image.getImage("StartMessage");
        scrollLabel = new JLabel(scrollMessage);
        scrollLabel.setLayout(null); // Allow manual positioning
        scrollLabel.setBounds(500, 40, 250, 550);

        //Second Panel will be a textName with the beginning text 

        titleName.add(scrollLabel);
        // note <html> and<b> are special features of JLabel that allow for text to be wrapped to a label
        //so displayed as a paragraph rather than a long line and <b> makes it bold <i> for ittalic <br> break line
        titleText = new JLabel("<html><b><u><br><br>DUNGEON ESCAPE </u> <br>"+"<i><br><br>-Welcome traveller, you have fallen from the burning world"
            + "<i> and have entered depths of the underworld begin your journey of survival... <html>");
        titleText.setForeground(Color.yellow);
        titleText.setFont(titleFont);
        titleText.setBounds(525, 20, 200, 450);
        titleName.add(titleText);
        con.add(titleName);   //add to container which is behind window

        con.setComponentZOrder(titleText, 0); // Ensure the text is above the image but below start message
        con.setComponentZOrder(titleName, 1);

        //startPanel is the Panel with the BEGIN message on it

        startPanel = new JPanel();
        startPanel .setBounds(550, 450, 150, 50);
        startPanel .setBackground(Color.RED);

        startButton = new JButton("BEGIN....");
        startButton.setBackground(Color.RED);
        startButton.setForeground(Color.YELLOW);
        startButton.setFont(startFont);
        startButton.setBorderPainted(true);  // REMOVES the weird border outline (thin) around the button set by default
        startButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) { createGameScreen(); }
            });
        startPanel.add(startButton);

        con.add(startPanel);
        //needs to have order stuff AFTER added otherwise not in container yet
        //con.whatever as container at start(and order applies to each different panel)

        con.setComponentZOrder(startPanel, 0);      // Bring startPanel (and its contents) to the front
        con.setComponentZOrder(titleText, 1);       // Ensure titleText is above the scroll
        con.setComponentZOrder(scrollLabel, 2);      // Ensure scroll is above the background
        con.setComponentZOrder(backgroundLabel, 3); // Background at the bottom

        window.setVisible(true);        
    }

    
    public void createGameScreen() {
        titleName.setVisible(false);
        startPanel.setVisible(false);
        scrollLabel.setVisible(false);
        titleText.setVisible(false);

        mainTextPanel = new JPanel();
        mainTextPanel.setOpaque(false);
        mainTextPanel.setLayout(null);  // Using null layout for absolute positioning
        mainTextPanel.setBounds(0, 0, 800, 600);  // Full size panel

        //Load the red scroll for the start message
        ImageIcon roomMessage = image.getImage("RoomDescription");
        roomLabel = new JLabel(roomMessage);
        roomLabel.setLayout(null);
        roomLabel.setBounds(0, 0, 800, 290);  // Position at top

        String roomDescription = game.roomDescription();
        String paddedRoomDescription = roomDescription;

        // Create a JTextArea for the room description
        roomDescriptionArea = new JTextArea("\n" + paddedRoomDescription);
        roomDescriptionArea.setOpaque(false);
        roomDescriptionArea.setForeground(Color.BLACK);
        roomDescriptionArea.setFont(mainFont);
        roomDescriptionArea.setLineWrap(true);
        roomDescriptionArea.setWrapStyleWord(true);
        roomDescriptionArea.setEditable(false);
        roomDescriptionArea.setBounds(125, 10, 560, 250);

        // Add components to mainTextPanel in the correct order (back to front)
        mainTextPanel.add(roomLabel);      // Add background scroll first
        mainTextPanel.add(roomDescriptionArea);  // Add text on top

        // Use BorderLayout for the container but position mainTextPanel absolutely
        con.setLayout(new BorderLayout());
        con.add(mainTextPanel, BorderLayout.CENTER);

        choiceButtonPanel = new JPanel();
        choiceButtonPanel.setBounds(80, 213, 600, 150);
        choiceButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        choiceButtonPanel.setOpaque(false);
        mainTextPanel.add(choiceButtonPanel);  // Add to mainTextPanel instead of con

        playerPanel = new JPanel();
        playerPanel.setBounds(125, 0, 560, 40);
        //playerPanel.setBackground(Color.blue);
        playerPanel.setOpaque(false);
        playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        con.add(playerPanel);

        buttonGUI();
        // Set the component order to ensure proper layering
        mainTextPanel.setComponentZOrder(roomLabel, 1);        // Background
        mainTextPanel.setComponentZOrder(roomDescriptionArea, 0); // Text on top
        mainTextPanel.setComponentZOrder(choiceButtonPanel, 0);  // Buttons on top
        mainTextPanel.setComponentZOrder(playerPanel, 0);
        con.setComponentZOrder(scrollLabel, 3);       
        con.setComponentZOrder(mainTextPanel, 2);
        con.setComponentZOrder(backgroundLabel, 4);
    }

    public void buttonGUI() {
        choiceButtonPanel.removeAll();
        playerPanel.removeAll();
        
        currentRoom = game.getCurrentRoom();
        game.healthChecker();  //REMEMBER REMEMBER REMEMBER REMOVE heal command
        
        String health = game.getHealth();
        String items = game.getItems();
        
        Map<String, Room> exits = currentRoom.getExits();

        // SETTING the Health and Items to new values after every button press(each room can change the value of them)
        hpLabel = new JLabel("HP:");
        hpLabel.setFont(mainFont);
        hpLabel.setForeground(Color.CYAN);
        playerPanel.add(hpLabel);

        hpLabelNumber = new JLabel(health);
        hpLabelNumber.setFont(mainFont);
        hpLabelNumber.setForeground(Color.MAGENTA);
        playerPanel.add(hpLabelNumber);  

        weaponLabel = new JLabel("  ITEMS:");
        weaponLabel.setFont(mainFont);
        weaponLabel.setForeground(Color.CYAN);
        playerPanel.add(weaponLabel); 

        weaponLabelInfo = new JLabel(items);
        weaponLabelInfo.setFont(mainFont);
        weaponLabelInfo.setForeground(Color.MAGENTA);
        playerPanel.add(weaponLabelInfo);

        System.out.println("Current Room: " + currentRoom.getShortDescription());
        System.out.println("Exits: " + exits);

        for (Map.Entry<String, Room> exit : exits.entrySet()) {
            String direction = exit.getKey();
            JButton choiceButton = new JButton(direction.toUpperCase());
            choiceButton.setPreferredSize(new Dimension(150, 30)); // Fixed size for buttons
            choiceButton.setBackground(Color.BLACK);
            choiceButton.setForeground(Color.WHITE);
            choiceButton.setFont(mainFont);

            System.out.println("Adding button for direction: " + direction);

            choiceButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) { 

                        System.out.println("Button clicked: " + direction);
                        if(currentRoom.getShortDescription().contains("TELEPORT")){
                            game.MagicTeleport();
                            currentRoom = game.getRandomRoom();
                            game.setCurrentRoom(exit.getValue());
                            roomDescriptionArea.setText("\n" + game.getCurrentRoom().getShortDescription());
                            background = game.getCurrentRoom().getImage();
                            backgroundLabel.setIcon(background);
                            buttonGUI();
                        }
                        else{
                            game.setCurrentRoom(exit.getValue());
                            roomDescriptionArea.setText("\n" + game.getCurrentRoom().getShortDescription());
                            background = game.getCurrentRoom().getImage();
                            backgroundLabel.setIcon(background);
                            buttonGUI();
                        }
                    }
                });

            choiceButtonPanel.add(choiceButton);
        }

        choiceButtonPanel.revalidate();
        choiceButtonPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
        con.revalidate();
        con.repaint();
        System.out.println("Panel updated and repainted with new buttons.");
    }

}
