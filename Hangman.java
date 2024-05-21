import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;


public class Hangman extends JFrame implements ActionListener {

    // counts the wrong guesses
    private int incorrectGuesses;

    // stores the category and word to guess from WordDataBase
    private String[] wordChallange;

    private final WordDataBase wordDataBase;
    private JLabel hangmanImage;
    private JLabel categoryLabel;
    private JLabel hiddenWordLabel;
    private JLabel resultLabel;
    private JLabel wordLabel;
    private JButton[] letterButtons;
    private JDialog resultDialog;
    public JButton playButton;
    public JButton easyButton;
    public JButton mediumButton;
    public JButton hardButton;
    public JButton quitButton;
    public JPanel menuPanel = new JPanel();
    public JPanel buttonPanel = new JPanel();
    public boolean flag = true;
    public String diff = "Easy";
    public static String language = "English";
    public JButton englishButton;
    public JButton polishButton;
    public JButton categoryOnButton;
    public JButton categoryOffButton;
    private JLabel backgroundLabel = new JLabel();






    public Hangman() {
        super("Hangman Game");
        backgroundLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("resources/background.jpg"))));
        setContentPane(backgroundLabel);
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(null);
        setResizable(true);
//        getContentPane().setBackground(Color.GREEN);

        // initializing values
        wordDataBase = new WordDataBase();
        letterButtons = new JButton[26];
        printButtons();
        printMenu();
        createResultText();
    }

    public void addUiComponents() {

        // this method has to be moved there, to load the new challenge only after the play button is pressed so the language actually plays a role
        wordChallange = wordDataBase.loadChallange();

        UIManager.put("Button.disabledText", new ColorUIResource(Color.WHITE));


        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(475, 0, 1000, 600);
        getContentPane().add(hangmanImage);

        // category
        categoryLabel = new JLabel(wordChallange[0]);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.BACKGROUND_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(480, hangmanImage.getPreferredSize().height + 10, 960,
                categoryLabel.getPreferredSize().height);
        getContentPane().add(categoryLabel);


        // hidden word
        hiddenWordLabel = new JLabel(CustomTools.hideWord(wordChallange[1]));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setOpaque(true);
        hiddenWordLabel.setBounds(480, categoryLabel.getY() + 50,
                categoryLabel.getWidth(), categoryLabel.getHeight());
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hiddenWordLabel.setBackground(CommonConstants.BACKGROUND_COLOR);
        getContentPane().add(hiddenWordLabel);
        hiddenWordLabel.setVisible(true);
        buttonPanel.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // all the logic behind pressing buttons based on simple String command which gets its value from the String on button


        // menu loop, lets you play with language and difficulty parameters, flag is set to false after pressing PLAY so it breaks out of the menu loop
        do {
            if (command.equals("Play")){
                menuPanel.setVisible(false);
                addUiComponents();
                if (!categoryOffButton.isEnabled()){
                    categoryLabel.setVisible(false);
                }
                flag = false;
                playButton.setEnabled(true);
                pack();
                break;
            }
            if (command.equals("Easy")){
                easyButton.setEnabled(false);
                easyButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                mediumButton.setEnabled(true);
                hardButton.setEnabled(true);
                diff = "Easy";
                hardButton.setBorder(UIManager.getBorder("Button.border"));
                mediumButton.setBorder(UIManager.getBorder("Button.border"));
                break;

            } else if (command.equals("Medium")){
                mediumButton.setEnabled(false);
                mediumButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                hardButton.setEnabled(true);
                easyButton.setEnabled(true);
                hardButton.setBorder(UIManager.getBorder("Button.border"));
                easyButton.setBorder(UIManager.getBorder("Button.border"));
                diff = "Medium";
                break;
            } else if (command.equals("Hard")){
                hardButton.setEnabled(false);
                hardButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                diff = "Hard";
                mediumButton.setEnabled(true);
                easyButton.setEnabled(true);
                mediumButton.setBorder(UIManager.getBorder("Button.border"));
                easyButton.setBorder(UIManager.getBorder("Button.border"));
                break;
            } else if (command.equals("Quit")){
                dispose();
                return;
            } else if (command.equals("Polish")){
                polishButton.setEnabled(false);
                polishButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                language = "Polish";
                englishButton.setEnabled(true);
                englishButton.setBorder(UIManager.getBorder("Button.border"));
                break;
            } else if (command.equals("English")){
                englishButton.setEnabled(false);
                englishButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                language = "English";
                polishButton.setBorder(UIManager.getBorder("Button.border"));
                polishButton.setEnabled(true);
                break;
            } else if (command.equals("No category")){
                categoryOffButton.setEnabled(false);
                categoryOffButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                categoryOnButton.setEnabled(true);
                categoryOnButton.setBorder(UIManager.getBorder("Button.border"));
                break;
            } else if (command.equals("With category")){
                categoryOnButton.setEnabled(false);
                categoryOnButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                categoryOffButton.setEnabled(true);
                categoryOffButton.setBorder(UIManager.getBorder("Button.border"));
                break;
            }

        } while (flag);

            // reset and exit buttons
            if (command.equals("Reset") || command.equals("Restart")) {
                resetGame();
                flag = true;
                if (command.equals("Restart")) {
                    resultDialog.setVisible(false);
                }
            } else if (command.equals("Exit")) {
                dispose();
                return;
            } else if (flag == false) {
                // disable button if once pressed
                JButton button = (JButton) e.getSource();
                // excluding PLAY button from being set to disabled after pressing
                if (!command.equals("Play")){
                button.setEnabled(false);
                }

                // check if the word contains the letter
                if (wordChallange[1].contains(command)) {
                    // right guess
                    button.setBackground(Color.GREEN);
                    // breaking hidden word to char array so we can check if picked from the button letter equals any letter in hidden word
                    char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                    for (int i = 0; i < wordChallange[1].length(); i++) {
                        // change each '*' to correct letter
                        if (wordChallange[1].charAt(i) == command.charAt(0)) {
                            hiddenWord[i] = command.charAt(0);
                        }
                    }

                    // update hidden word label
                    hiddenWordLabel.setText(String.valueOf(hiddenWord));
                    hiddenWordLabel.setPreferredSize(new Dimension(categoryLabel.getWidth(), categoryLabel.getHeight()));
                    // checking if hiddenWordLabel contains any * left, if it doesn't it means round is won
                    if (!hiddenWordLabel.getText().contains("*")) {
                        // word is guessed, display the text
                        if (language == "English"){
                            resultLabel.setText("Ayyy, you did it!");
                        } else if (language == "Polish"){
                            resultLabel.setText("Brawo kamracie, dobra robota, zwycięstwo!");
                        }
                        resultDialog.setVisible(true);
                    }


                } else {
                    // calculating mistakes value based on difficulty level
                    if (diff == "Easy"){
                        // wrong guess
                        if (command.equals("Play")) {
                        } else {
                            button.setBackground(Color.RED);
                            ++incorrectGuesses;
                            CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");
                        }
                    } else if (diff == "Medium"){
                        // wrong guess
                        if (command.equals("Play")) {} else {
                            button.setBackground(Color.RED);
                            incorrectGuesses += 2;
                            if (incorrectGuesses >= 2 && incorrectGuesses < 4) {
                                CustomTools.updateImage(hangmanImage, "resources/3.png");
                            } else if (incorrectGuesses >= 4 && incorrectGuesses < 6) {
                                CustomTools.updateImage(hangmanImage, "resources/5.png");
                            } else if (incorrectGuesses > 5) {
                                CustomTools.updateImage(hangmanImage, "resources/7.png");
                            }

                        }
                    } else if (diff == "Hard"){
                        // wrong guess
                        if (command.equals("Play")) {} else {
                            button.setBackground(Color.RED);
                            incorrectGuesses += 3;
                            if (incorrectGuesses >= 3 && incorrectGuesses < 4) {
                                CustomTools.updateImage(hangmanImage, "resources/4.png");
                            } else if (incorrectGuesses > 3){
                                CustomTools.updateImage(hangmanImage, "resources/7.png");
                            }
                        }
                    }
                    // losing condition
                    if (incorrectGuesses >= 6) {
                        if (language == "English") {
                            resultLabel.setText("Rip in peace, YOU DIED!");
                        } else if (language == "Polish"){
                            resultLabel.setText("Bardzo się starałeś, lecz z gry wyleciałeś!");
                        }
                        resultDialog.setVisible(true);
                    }
                }
                // setting hidden word for pop-up
                if (language == "English"){
                    wordLabel.setText("Word: " + wordChallange[1]);
                } else if (language == "Polish"){
                    wordLabel.setText("Słowo: " + wordChallange[1]);
                }
            }
    }



        private void createResultText () {

            // method used to create window pop-ups

            resultDialog = new JDialog();
            resultDialog.setTitle("Result");
            resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
            resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
            resultDialog.setResizable(false);
            resultDialog.setLocationRelativeTo(this);
            resultDialog.setModal(true);
            resultDialog.setLayout(new GridLayout(3, 1));
            resultDialog.addWindowListener(new WindowAdapter() {
                // overriding this method resets the game when the pop-up is closed
                @Override
                public void windowClosing(WindowEvent e) {
                    resetGame();
                }
            });

            resultLabel = new JLabel();
            resultLabel.setForeground(Color.WHITE);
            resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

            wordLabel = new JLabel();
            wordLabel.setForeground(Color.WHITE);
            wordLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JButton restartButton = new JButton("Restart");
            restartButton.setForeground(Color.WHITE);
            restartButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            restartButton.addActionListener(this);

            resultDialog.add(resultLabel);
            resultDialog.add(wordLabel);
            resultDialog.add(restartButton);
        }

        public void resetGame () {

            // resetting necessary stuff and setting some parameters to defaults

             buttonPanel.setVisible(false);
             hangmanImage.setVisible(false);
             categoryLabel.setVisible(false);
             hiddenWordLabel.setVisible(false);
             menuPanel.setVisible(true);
             language = "English";
             diff = "Easy";
             setPanelEnabled(menuPanel, true);
            setMenuButtonsBordersDefault();



            // setting flag to true so menu loops works again
            flag = true;

            // load new word
            wordChallange = wordDataBase.loadChallange();
            incorrectGuesses = 0;

            // load starting image
            CustomTools.updateImage(hangmanImage, CommonConstants.IMAGE_PATH);

            // update category
            categoryLabel.setText(wordChallange[0]);

            // update hidden word
            String hiddenWord = CustomTools.hideWord(wordChallange[1]);
            hiddenWordLabel.setText(hiddenWord);

            // enable all buttons
            for (int i = 0; i < letterButtons.length; i++) {
                letterButtons[i].setEnabled(true);
                letterButtons[i].setBackground(CommonConstants.PRIMARY_COLOR);
            }
        }
    public void printMenu(){


             UIManager.put("Button.disabledText", new ColorUIResource(Color.RED));

            // creating all the buttons and panels for the main menu

            playButton = new JButton("Play");
            easyButton = new JButton("Easy");
            mediumButton = new JButton("Medium");
            hardButton = new JButton("Hard");
            quitButton = new JButton("Quit");
            englishButton = new JButton("English");
            polishButton = new JButton("Polish");
            categoryOffButton = new JButton("No category");
            categoryOnButton = new JButton("With category");


            ToolTipManager.sharedInstance().setInitialDelay(0);
            ToolTipManager.sharedInstance().setDismissDelay(25000);

            playButton.setForeground(Color.WHITE);
            playButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            playButton.addActionListener(this);
            playButton.setToolTipText("<html> Starts new round of the game with new random generated word and category. <p> " +
                    "If difficulty level is not chosen, the round will be" +
                    " played in easy mode by default. <p> If the language isn't chosen, the game runs in English by default. </html>");

            easyButton.setForeground(Color.WHITE);
            easyButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            easyButton.addActionListener(this);
            easyButton.setToolTipText("<html> Standard difficulty of the game. It allows you to make mistake 5 times. The 6th time is the dead one. </html>");

            mediumButton.setForeground(Color.WHITE);
            mediumButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            mediumButton.addActionListener(this);
            mediumButton.setToolTipText("<html> A little bit of challenge version. Make a bad guess 3 times and you are out. </html>");

            hardButton.setForeground(Color.WHITE);
            hardButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            hardButton.addActionListener(this);
            hardButton.setToolTipText("<html> A real one challenge where only one mistake is allowed. </html>");

            quitButton.setForeground(Color.WHITE);
            quitButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            quitButton.addActionListener(this);
            quitButton.setToolTipText("Closes the game.. wish you stayed a little longer.. :(");

            polishButton.setForeground(Color.WHITE);
            polishButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            polishButton.addActionListener(this);
            polishButton.setToolTipText("<html> Sets the language for the words and categories as Polish. No polish letters tho :/ </html>");

            englishButton.setForeground(Color.WHITE);
            englishButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            englishButton.addActionListener(this);
            englishButton.setToolTipText("<html> Sets the language for the words and categories as English. This setting is set as default. </html>");

            categoryOnButton.setForeground(Color.WHITE);
            categoryOnButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            categoryOnButton.addActionListener(this);
            categoryOnButton.setToolTipText("<html> Enables category visibility for the hidden word, makes it easier to guess. </html");

            categoryOffButton.setForeground(Color.WHITE);
            categoryOffButton.setBackground(CommonConstants.BACKGROUND_COLOR);
            categoryOffButton.addActionListener(this);
            categoryOffButton.setToolTipText("<html> Disables category visibility. </html>");



            GridLayout diffLayout = new GridLayout(1, 3);
            JPanel diffPanel = new JPanel();
            diffPanel.setBounds((int) (CommonConstants.FRAME_SIZE.width * 0.5), (int) (CommonConstants.FRAME_SIZE.height * 1),
                    (int) (CommonConstants.FRAME_SIZE.width * 0.5), 30);
            diffPanel.setLayout(diffLayout);
            diffPanel.add(easyButton);
            diffPanel.add(mediumButton);
            diffPanel.add(hardButton);

            GridLayout languageLayout = new GridLayout(1, 2);
            JPanel languagePanel = new JPanel();
            languagePanel.setBounds((int) (CommonConstants.FRAME_SIZE.width * 0.5), (int) (CommonConstants.FRAME_SIZE.height * 1),
                (int) (CommonConstants.FRAME_SIZE.width * 0.5), 30);
            languagePanel.setLayout(languageLayout);
            languagePanel.add(englishButton);
            languagePanel.add(polishButton);

            GridLayout categoryLayout = new GridLayout(1,2);
            JPanel categoryPanel = new JPanel();
            categoryPanel.setBounds((int) (CommonConstants.FRAME_SIZE.width * 0.5), (int) (CommonConstants.FRAME_SIZE.height * 1),
                (int) (CommonConstants.FRAME_SIZE.width * 0.5), 30);
            categoryPanel.setLayout(categoryLayout);
            categoryPanel.add(categoryOnButton);
            categoryPanel.add(categoryOffButton);

            GridLayout gridLayout = new GridLayout(5, 1);
            menuPanel = new JPanel();
            menuPanel.setSize(960, 540);
            menuPanel.setLayout(gridLayout);
            menuPanel.add(playButton);
            menuPanel.add(diffPanel);
            menuPanel.add(languagePanel);
            menuPanel.add(categoryPanel);
            menuPanel.add(quitButton);
            menuPanel.setLocation(480, 270);
            menuPanel.setOpaque(false);
            diffPanel.setOpaque(false);
            add(menuPanel);

     }
     public void printButtons(){
         // letter buttons
         GridLayout gridLayout = new GridLayout(4, 7);
         buttonPanel.setBounds((int) (CommonConstants.FRAME_SIZE.width * 0.25),700,
                 CommonConstants.BUTTON_PANEL_SIZE.width, CommonConstants.BUTTON_PANEL_SIZE.height);
         buttonPanel.setLayout(gridLayout);

         for (char c = 'A'; c <= 'Z'; c++) {
             JButton button = new JButton(Character.toString(c));
             button.setBackground(CommonConstants.PRIMARY_COLOR);
             button.setForeground(Color.WHITE);
             button.addActionListener(this);

             // using ASCII values to calculate the current index
             int currentIndex = c - 'A';
             letterButtons[currentIndex] = button;
             buttonPanel.add(letterButtons[currentIndex]);
         }

         getContentPane().add(buttonPanel);
         buttonPanel.setVisible(false);


         // reset button
         JButton resetButton = new JButton("Reset");
         resetButton.setForeground(Color.WHITE);
         resetButton.setBackground(CommonConstants.SECONDARY_COLOR);
         resetButton.addActionListener(this);
         buttonPanel.add(resetButton);

         // quit button
         JButton quitButton = new JButton("Exit");
         quitButton.setForeground(Color.WHITE);
         quitButton.setBackground(CommonConstants.SECONDARY_COLOR);
         quitButton.addActionListener(this);
         buttonPanel.add(quitButton);
     }

    public void setPanelEnabled(Container cont, Boolean isEnabled) {

        // this method enables all the elements of the container, e.g. menuPanel has button components and they
        // gonna be set to method parameter isEnabled
        // additionally loop checks for internal containers and their components
        // method used when reseting the game to set all the buttons in menu to enabled

        cont.setEnabled(isEnabled);

        Component[] components = cont.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof Container) {
                setPanelEnabled((Container) components[i], isEnabled);
            }
            components[i].setEnabled(isEnabled);
        }
    }
    public void setMenuButtonsBordersDefault(){
        ArrayList<JButton> menuButtons = new ArrayList<JButton>();
        menuButtons.add(playButton);
        menuButtons.add(easyButton);
        menuButtons.add(mediumButton);
        menuButtons.add(hardButton);
        menuButtons.add(englishButton);
        menuButtons.add(polishButton);
        menuButtons.add(categoryOffButton);
        menuButtons.add(categoryOnButton);

        for(JButton button : menuButtons){
            button.setBorder(UIManager.getBorder("Button.border"));
        }
        UIManager.put("Button.enabledText", new ColorUIResource(Color.WHITE));
    }
}
