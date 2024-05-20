import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


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




    public Hangman() {
        super("Hangman Game");
        setSize(CommonConstants.FRAME_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(true);
        getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);

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



        hangmanImage = CustomTools.loadImage(CommonConstants.IMAGE_PATH);
        hangmanImage.setBounds(0, 0, hangmanImage.getPreferredSize().width, hangmanImage.getPreferredSize().height);
        getContentPane().add(hangmanImage);

        // category
        categoryLabel = new JLabel(wordChallange[0]);
        categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setBackground(CommonConstants.BACKGROUND_COLOR);
        categoryLabel.setBorder(BorderFactory.createLineBorder(CommonConstants.SECONDARY_COLOR));
        categoryLabel.setBounds(0, hangmanImage.getPreferredSize().height + 10, CommonConstants.FRAME_SIZE.width,
                categoryLabel.getPreferredSize().height);

        getContentPane().add(categoryLabel);

        if (diff == "Hard"){
            categoryLabel.setVisible(false);
        }

        // hidden word
        hiddenWordLabel = new JLabel(CustomTools.hideWord(wordChallange[1]));
        hiddenWordLabel.setForeground(Color.WHITE);
        hiddenWordLabel.setBounds(0, categoryLabel.getY() + categoryLabel.getPreferredSize().height + 20,
                CommonConstants.FRAME_SIZE.width, hiddenWordLabel.getPreferredSize().height);
        hiddenWordLabel.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().add(hiddenWordLabel);
        buttonPanel.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        do {
            if (command.equals("Play")){
                menuPanel.setVisible(false);
                addUiComponents();
                flag = false;
                playButton.setEnabled(true);
                break;
            }
            if (command.equals("Easy")){
                easyButton.setEnabled(false);
                mediumButton.setEnabled(true);
                hardButton.setEnabled(true);
                diff = "Easy";
                break;

            } else if (command.equals("Medium")){
                mediumButton.setEnabled(false);
                hardButton.setEnabled(true);
                easyButton.setEnabled(true);
                diff = "Medium";
                break;
            } else if (command.equals("Hard")){
                hardButton.setEnabled(false);
                diff = "Hard";
                mediumButton.setEnabled(true);
                easyButton.setEnabled(true);
                break;
            } else if (command.equals("Quit")){
                dispose();
                return;
            } else if (command.equals("Polish")){
                polishButton.setEnabled(false);
                language = "Polish";
                englishButton.setEnabled(true);
                break;
            } else if (command.equals("English")){
                englishButton.setEnabled(false);
                language = "English";
                polishButton.setEnabled(true);
                break;
            }

        } while (flag);

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
                if (!command.equals("Play")){
                button.setEnabled(false);
                }

                // check if the word contains the letter
                if (wordChallange[1].contains(command)) {
                    // right guess
                    button.setBackground(Color.GREEN);
                    char[] hiddenWord = hiddenWordLabel.getText().toCharArray();

                    for (int i = 0; i < wordChallange[1].length(); i++) {
                        // change each '*' to correct letter
                        if (wordChallange[1].charAt(i) == command.charAt(0)) {
                            hiddenWord[i] = command.charAt(0);
                        }
                    }

                    // update hidden word label
                    hiddenWordLabel.setText(String.valueOf(hiddenWord));

                    if (!hiddenWordLabel.getText().contains("*")) {
                        // word is guessed, display the text
                        resultLabel.setText("Pogchamp, you did it poggers x)");
                        resultDialog.setVisible(true);
                    }


                } else {

                    if (diff == "Easy"){
                        // wrong guess
                        if (command.equals("Play")) {
                        } else {
                            button.setBackground(Color.RED);
                            ++incorrectGuesses;
                            CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");
                        }
                    } else if (diff == "Medium"){
                        if (command.equals("Play")) {} else {
                            button.setBackground(Color.RED);
                            incorrectGuesses += 2;
                            if (incorrectGuesses >= 2 && incorrectGuesses < 4) {
                                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 2) + ".png");
                            } else if (incorrectGuesses >= 4 && incorrectGuesses < 6) {
                                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");
                            } else if (incorrectGuesses > 6) {
                                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");
                            }

                        }
                    } else if (diff == "Hard"){
                        if (command.equals("Play")) {} else {
                            button.setBackground(Color.RED);
                            incorrectGuesses += 3;
                            if (incorrectGuesses >= 3 && incorrectGuesses < 4) {
                                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 2) + ".png");
                            } else if (incorrectGuesses > 3){
                                CustomTools.updateImage(hangmanImage, "resources/" + (incorrectGuesses + 1) + ".png");
                            }
                        }
                    }

                    if (incorrectGuesses >= 6) {
                        resultLabel.setText("Rip in peace bro x.x");
                        resultDialog.setVisible(true);
                    }
                }
                wordLabel.setText("Word: " + wordChallange[1]);
            }
    }



        private void createResultText () {
            resultDialog = new JDialog();
            resultDialog.setTitle("Result");
            resultDialog.setSize(CommonConstants.RESULT_DIALOG_SIZE);
            resultDialog.getContentPane().setBackground(CommonConstants.BACKGROUND_COLOR);
            resultDialog.setResizable(false);
            resultDialog.setLocationRelativeTo(this);
            resultDialog.setModal(true);
            resultDialog.setLayout(new GridLayout(3, 1));
            resultDialog.addWindowListener(new WindowAdapter() {
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

             buttonPanel.setVisible(false);
             hangmanImage.setVisible(false);
             categoryLabel.setVisible(false);
             hiddenWordLabel.setVisible(false);
             menuPanel.setVisible(true);
             language = "English";
             diff = "Easy";
             setPanelEnabled(menuPanel, true);

             
            // setting flag to true so menu loops works again
            flag = true;

            // load new word
            wordChallange = wordDataBase.loadChallange();
            incorrectGuesses = 0;

            //load starting image
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

            playButton = new JButton("Play");
            easyButton = new JButton("Easy");
            mediumButton = new JButton("Medium");
            hardButton = new JButton("Hard");
            quitButton = new JButton("Quit");
            englishButton = new JButton("English");
            polishButton = new JButton("Polish");


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
            englishButton.setToolTipText("<html> Sets the language for the words and categories as English. This setting is set as default.");

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

            GridLayout gridLayout = new GridLayout(4, 1);
            menuPanel = new JPanel();
            menuPanel.setBounds((int) (CommonConstants.FRAME_SIZE.width * 0.25), 500,
                    CommonConstants.BUTTON_PANEL_SIZE.width, CommonConstants.BUTTON_PANEL_SIZE.height);
            menuPanel.setLayout(gridLayout);
            menuPanel.add(playButton);
            menuPanel.add(diffPanel);
            menuPanel.add(languagePanel);
            menuPanel.add(quitButton);
            menuPanel.setLocation(700, 350);
            menuPanel.setAlignmentY(SwingConstants.CENTER);
            add(menuPanel);

     }
     public void printButtons(){
         // buttons
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
        cont.setEnabled(isEnabled);

        Component[] components = cont.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof Container) {
                setPanelEnabled((Container) components[i], isEnabled);
            }
            components[i].setEnabled(isEnabled);
        }
    }
}
