package net.goanation;

import net.goanation.objects.Player;

import javax.swing.*;
import java.awt.*;

public class TicTacToeApp {
    private JFrame frame;
    private JPanel jPanel;
    private boolean play = true;
    private boolean played = false;
    private Player player1 = new Player();
    private Player player2 = new Player();
    private Player turn = player1;

    /**
     * So we wouldn't reference everything from a static context we create a constructor which calls the methods
     */
    private TicTacToeApp() {
        setFrame();
        addButtons();
        setNames();
        playGame();
    }

    /**
     * Main method creating an instance of the mainClass
     */
    public static void main(String[] args) {
        new TicTacToeApp();
    }

    /**
     * Method to set the frame's attributes
     */
    private void setFrame() {
        frame = new JFrame("Tic Tac Toe");
        frame.setContentPane(jPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        centerFrame();
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Method to add 9 buttons to the JPanel
     */
    private void addButtons() {
        jPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            jPanel.add(getButton());
        }
    }

    /**
     * Method to Create a new button, set it's attributes, add an actionListener to the button
     * and return the button to be added to the JPanel
     *
     * @return JButton
     */
    private JButton getButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(100, 100));
        button.addActionListener(e -> setField(button));
        button.setIcon(new ImageIcon(""));
        return button;
    }

    /**
     * Method called by the actionListener to set an imageIcon to the button
     * Based on which player's turn it is the imageIcon is a X or an O
     * After setting the imageIcon is set, the actionListener of the button is removed
     */
    private void setField(JButton button) {
        if (turn == player1) {
            button.setIcon(new ImageIcon(getClass().getResource("images/x.png")));
            turn = player2;
        } else {
            button.setIcon(new ImageIcon(getClass().getResource("images/o.png")));
            turn = player1;
        }
        played = true;
        button.removeActionListener(button.getActionListeners()[0]);
    }

    /**
     * Pretty self explanatory: prompts the user for the userNames and set's them to the corresponding Player
     * after having done that it call the welcomeMessage
     */
    private void setNames() {
        player1.setName(JOptionPane.showInputDialog("Please enter the name for player 1"));
        player2.setName(JOptionPane.showInputDialog("Please enter the name for player 2"));
        showWelcomeDialog();
    }

    /**
     * Show's an greeting and provides some gameInfo
     */
    private void showWelcomeDialog() {
        JOptionPane.showMessageDialog(frame, "Welcome " + player1.getName() + " and " + player2.getName() +
                ".\n" + player1.getName() + " is X and " + player2.getName() + " is O.\n" + player1.getName() + " starts.");
        resizeFrame();
    }

    /**
     * Set's the dimension of the JFrame
     */
    private void resizeFrame() {
        frame.setSize(new Dimension(600, 600));
        centerFrame();
    }

    /**
     * Centers the JFrame in the middle of the computerScreen
     */
    private void centerFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    /**
     * PlayGame method containing the game logic.
     * The while loop provides the user to play multiple games without restarting the program.
     * First I check if there is already a winner, if so The winner is announced, the corresponding player's
     * gamesWon is updated and the user is prompt to ask if he wants to play an other game.
     * If he chooses 1 (no) a goodbye message is shown containing the final score afterwords the frame is closed.
     * Else all the imageIcons are removed and the actionListener re-added enabling the players to start over again
     */
    private void playGame() {
        while (play) {
            if (getWinner() != null) {
                if (turn.getName().equals(player1.getName())) {
                    player2.setGamesWon();
                } else {
                    player1.setGamesWon();
                }
                int choice = JOptionPane.showConfirmDialog(null, getWinner() + "\nDo you want to play an other game?",
                        "We have a winner", JOptionPane.YES_NO_OPTION);
                if (choice == 1) {
                    JOptionPane.showMessageDialog(null, "Thank you for playing.\n" +
                            "This is the final score:\n" + player1.getName() + ": " + player1.getGamesWon() +
                            " games won\n" + player2.getName() + ": " + player2.getGamesWon() + " games won.");
                    play = false;
                    break;
                } else {
                    for (Component component : jPanel.getComponents()) {
                        JButton button = ((JButton) component);
                        button.setIcon(new ImageIcon(""));
                        button.addActionListener(e -> setField(button));
                    }
                }
            }
            if (played && getWinner() == null) {
                JOptionPane.showMessageDialog(null, "It's " + turn.getName() + " turn\n please click on a field.");
                played = false;
            }
        }
        frame.dispose();
    }

    /**
     * @param content is x or o depending on player's turn
     * @return boolean
     * Method checking for the possible winning combinations
     * Here is the error I guess
     */
    private boolean checkForWinner(String content) {
        if (checkButtonIcon(0, content) && checkButtonIcon(1, content) && checkButtonIcon(2, content)) {
            return true;
        } else if (checkButtonIcon(0, content) && checkButtonIcon(3, content) && checkButtonIcon(6, content)) {
            return true;
        } else if (checkButtonIcon(0, content) && checkButtonIcon(5, content) && checkButtonIcon(8, content)) {
            return true;
        } else if (checkButtonIcon(1, content) && checkButtonIcon(4, content) && checkButtonIcon(7, content)) {
            return true;
        } else if (checkButtonIcon(2, content) && checkButtonIcon(4, content) && checkButtonIcon(6, content)) {
            return true;
        } else if (checkButtonIcon(2, content) && checkButtonIcon(5, content) && checkButtonIcon(8, content)) {
            return true;
        } else if (checkButtonIcon(3, content) && checkButtonIcon(4, content) && checkButtonIcon(5, content)) {
            return true;
        } else return checkButtonIcon(8, content) && checkButtonIcon(7, content) && checkButtonIcon(6, content);
    }

    /**
     * @param index   index for the component array
     * @param content is x or depending on player's turn
     * @return boolean
     * Method to check if the path to the imageIcon contains an x or an o
     */
    private Boolean checkButtonIcon(int index, String content) {
        Component[] buttons = jPanel.getComponents();
        return ((JButton) buttons[index]).getIcon().toString().contains(content);
    }

    /**
     * @return String containing the name of the winner
     * Method to determine who is the winner
     * The bug could be here too, but more unlikely
     */
    private String getWinner() {
        if (checkForWinner("x")) {
            return player1.getName() + "is the winner.";
        } else if (checkForWinner("o")) {
            return player2.getName() + "is the winner.";
        } else if (checkForFullBoard()) {
            return "We have a tie.";
        }
        return null;
    }

    /**
     * @return boolean
     * Method to check if all imageIcons have bin set with an image.
     * If no winner is found before this method returns true, the game is a tie
     */
    private boolean checkForFullBoard() {
        boolean check = true;
        for (Component component : jPanel.getComponents()) {
            if (((JButton) component).getIcon().toString().equals("")) {
                check = false;
            }
        }
        return check;
    }
}
