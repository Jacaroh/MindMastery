package game.ui;

import game.MasterMind;
import game.MatchResult;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.ArrayList;

import static java.awt.Color.*;

class MasterMindUI extends JFrame {
  static MasterMind masterMind = new MasterMind();
  static JFrame frame;
  static JPanel guessMenu = new JPanel(new FlowLayout());
  static JTable board;

  static Object[] colorIcons = {
    new ImageIcon("MasterMind/src/game/MAGENTA.png"),
    new ImageIcon("MasterMind/src/game/PINK.png"),
    new ImageIcon("MasterMind/src/game/RED.png"),
    new ImageIcon("MasterMind/src/game/ORANGE.png"),
    new ImageIcon("MasterMind/src/game/YELLOW.png"),
    new ImageIcon("MasterMind/src/game/GREEN.png"),
    new ImageIcon("MasterMind/src/game/CYAN.png"),
    new ImageIcon("MasterMind/src/game/BLUE.png"),
    new ImageIcon("MasterMind/src/game/BLACK.png"),
    new ImageIcon("MasterMind/src/game/GRAY.png"),
  };

  static Object[] blackIcons = {
    new ImageIcon("MasterMind/src/game/NONE.png"),
    new ImageIcon("MasterMind/src/game/BLACK1.png"),
    new ImageIcon("MasterMind/src/game/BLACK2.png"),
    new ImageIcon("MasterMind/src/game/BLACK3.png"),
    new ImageIcon("MasterMind/src/game/BLACK4.png"),
    new ImageIcon("MasterMind/src/game/BLACK5.png"),
    new ImageIcon("MasterMind/src/game/BLACK6.png"),
  };

  static Object[] grayIcons = {
    new ImageIcon("MasterMind/src/game/NONE.png"),
    new ImageIcon("MasterMind/src/game/GRAY1.png"),
    new ImageIcon("MasterMind/src/game/GRAY2.png"),
    new ImageIcon("MasterMind/src/game/GRAY3.png"),
    new ImageIcon("MasterMind/src/game/GRAY4.png"),
    new ImageIcon("MasterMind/src/game/GRAY5.png"),
    new ImageIcon("MasterMind/src/game/GRAY6.png"),
  };

  static Object trophyIcon = new ImageIcon("MasterMind/src/game/TROPHY.png");

  static JComboBox<Object> colorChoice1 = new JComboBox<>(colorIcons);
  static JComboBox<Object> colorChoice2 = new JComboBox<>(colorIcons);
  static JComboBox<Object> colorChoice3 = new JComboBox<>(colorIcons);
  static JComboBox<Object> colorChoice4 = new JComboBox<>(colorIcons);
  static JComboBox<Object> colorChoice5 = new JComboBox<>(colorIcons);
  static JComboBox<Object> colorChoice6 = new JComboBox<>(colorIcons);

  static List<Color> baseColors = List.of(new Color[]{magenta, pink, red, orange, yellow, green, cyan, blue, black, gray});

  public static List<Object> endGame(Object blackIcon, Object grayIcon) {
    List<Object> finalList = new ArrayList<>();

    for (var color : masterMind.selectedColors) {
      finalList.add(colorIcons[baseColors.indexOf(color)]);
    }
    if (MasterMind.currentGameState == MasterMind.GameState.LOST) {
        finalList.add("LOST");
        finalList.add(blackIcon);
        finalList.add(grayIcon);
      }
    else {
      finalList.add("WON!");
      finalList.add(blackIcon);
      finalList.add(trophyIcon);
    }

    return finalList;
  }

  static JButton giveUpButton = new JButton("give up");
  static JButton guessButton = new JButton("guess");
  static JLabel guessInstruction = new JLabel("Pick colors:");

  public static void main(String[] args) {
    frame = new JFrame("Master Mind Game");
    DefaultTableModel boardModel = new DefaultTableModel(21, 10);
    board = new JTable(boardModel) {
      public Class getColumnClass(int column) {
        return (column == 0 || column == 7) ? Object.class : Icon.class;
      }
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    board.setRowHeight(35);

    guessButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        List<Color> returnList = new ArrayList<>();
        returnList.add(baseColors.get(colorChoice1.getSelectedIndex()));
        returnList.add(baseColors.get(colorChoice2.getSelectedIndex()));
        returnList.add(baseColors.get(colorChoice3.getSelectedIndex()));
        returnList.add(baseColors.get(colorChoice4.getSelectedIndex()));
        returnList.add(baseColors.get(colorChoice5.getSelectedIndex()));
        returnList.add(baseColors.get(colorChoice6.getSelectedIndex()));

        if (MasterMind.currentGameState == MasterMind.GameState.PLAYING) {
          MatchResult matches = masterMind.guess(returnList);

          List<Object> tempList = new ArrayList<>();
          tempList.add("Guess " + masterMind.turnNumber + ":");
          for (var color : returnList) {
            tempList.add(colorIcons[baseColors.indexOf(color)]);
          }
          tempList.add("Results:");
          tempList.add(blackIcons[matches.positionalMatches]);
          tempList.add(grayIcons[matches.matches]);

          for (int index = 0; index < 10; index++) {
            boardModel.setValueAt(tempList.get(index), masterMind.turnNumber - 1, index);
          }

          if (MasterMind.currentGameState != MasterMind.GameState.PLAYING) {
            List<Object> finalList = endGame(blackIcons[matches.positionalMatches], grayIcons[matches.matches]);
            for (int index = 0; index < 10; index++) {
              boardModel.setValueAt(finalList.get(index), 20, index + 1);
            }
          }
        }
        boardModel.fireTableDataChanged();
      }
    });

    giveUpButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (MasterMind.currentGameState == MasterMind.GameState.PLAYING) {
          Object endingBlackGuess = blackIcons[0];
          Object endingGrayGuess = grayIcons[0];

          if(masterMind.turnNumber != 0) {
            endingBlackGuess = boardModel.getValueAt(masterMind.turnNumber - 1, 8);
            endingGrayGuess = boardModel.getValueAt(masterMind.turnNumber - 1, 9);
          }

          masterMind.gameOver();
          List<Object> answerList = endGame(endingBlackGuess, endingGrayGuess);

          for (int index = 0; index < 10; index++) {
            boardModel.setValueAt(answerList.get(index), 20, index + 1);
          }

          boardModel.fireTableDataChanged();
        }
      }
    });

    guessMenu.add(guessInstruction);
    guessMenu.add(colorChoice1);
    guessMenu.add(colorChoice2);
    guessMenu.add(colorChoice3);
    guessMenu.add(colorChoice4);
    guessMenu.add(colorChoice5);
    guessMenu.add(colorChoice6);
    guessMenu.add(guessButton);
    guessMenu.add(giveUpButton);

    Container pane = frame.getContentPane();
    pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
    frame.add(guessMenu);
    frame.add(board);
    boardModel.setValueAt("Answer Key:", 20, 0);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 850);
    frame.setVisible(true);
  }
}