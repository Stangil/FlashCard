import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class FlashCardBuilder {
    private JTextArea question;
    private JTextArea answer;
    private ArrayList<FlashCard> cardList;
    private JFrame frame;

    public FlashCardBuilder() {
        frame = new JFrame("Flash Card Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        cardList = new ArrayList<FlashCard>();
        JButton nextButton = new JButton("Next card");
        Font greatFont = new Font("Helvetica Neue", Font.BOLD, 21);

        //question pane
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(greatFont);

        JScrollPane qjScrollPane = new JScrollPane(question);
        qjScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qjScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //answer pane
        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(greatFont);

        JScrollPane ajScrollPane = new JScrollPane(answer);
        ajScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        ajScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Create labels
        JLabel questionLabel = new JLabel("Question");
        JLabel answerLabel = new JLabel("Answer");

        //menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        //add menu items and bar
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);

        menuBar.add(fileMenu);

        //add event listeners for menu bar
        newMenuItem.addActionListener(new NewMenuItemListener());
        saveMenuItem.addActionListener(new SaveMenuItemListener());

        //Creates menu bar into top of frame
        frame.setJMenuBar(menuBar);
        //Adds stuff to main panel
        mainPanel.add(questionLabel);
        mainPanel.add(qjScrollPane);
        mainPanel.add(answerLabel);
        mainPanel.add(ajScrollPane);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        //Add to the frame
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        //frame.add(mainPanel);
        //frame.setLayout(new FlowLayout());
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }//end constructor
//--------------------------------------------------------------------------------------------------
    //Listener methods
    class NextCardListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //create flash card
            FlashCard card = new FlashCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }
    class NewMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Create new file");
        }
    }
    class SaveMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            FlashCard card = new FlashCard(question.getText(),answer.getText());
            cardList.add(card);
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }

    private void saveFile(File selectedFile) {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));

            Iterator<FlashCard> cardIterator = cardList.iterator();
            while (cardIterator.hasNext()){
                FlashCard card = (FlashCard)cardIterator.next();
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer()+"\n");
                //Format to be like this: QUESTION/ANSWER
            }

        }   catch (Exception e){
            System.out.println("Couldn't write to file!!");
        }
    }

    private void clearCard(){
        question.setText("");
        answer.setText("");
        question.requestFocus();//returns cursor to question pane
    }
//MAIN FUNCTION
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlashCardBuilder();
            }
        });
    }

}
