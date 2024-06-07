package system;

import java.awt.*;
import javax.swing.*;

import Interpreter.Parser;
import command.Command;
import command.HistoryCommandHandler;
import composite.GraphicObjectManager;
import shapes.CircleObject;
import shapes.ImageObject;
import shapes.RectangleObject;
import view.CircleObjectView;
import view.GraphicObjectPanel;
import view.GraphicObjectViewFactory;
import view.ImageObjectView;
import view.RectangleObjectView;

public class MiniCADSystem {
    public static void main(String[] args) {
        JFrame frame = new JFrame("MiniCAD System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JToolBar toolBar = new JToolBar();
        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");
        JButton helpButton = new JButton("Help");

        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.add(helpButton);

        final GraphicObjectPanel gpanel = new GraphicObjectPanel();
        final GraphicObjectManager manager = new GraphicObjectManager(gpanel);
        final HistoryCommandHandler handler = new HistoryCommandHandler(gpanel);

        undoButton.addActionListener(evt -> handler.undo());
        redoButton.addActionListener(evt -> handler.redo());

        gpanel.setPreferredSize(new Dimension(400, 400));

        GraphicObjectViewFactory.FACTORY.installView(CircleObject.class, new CircleObjectView());
        GraphicObjectViewFactory.FACTORY.installView(ImageObject.class, new ImageObjectView());
        GraphicObjectViewFactory.FACTORY.installView(RectangleObject.class, new RectangleObjectView());

        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(new JScrollPane(gpanel), BorderLayout.CENTER);

        // Creazione del JPanel per centrare la JTextField
        JPanel commandPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spaziatura attorno alla textbox
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField commandField = new JTextField(30); // Imposta una dimensione fissa
        commandPanel.add(commandField, gbc);
        frame.add(commandPanel, BorderLayout.SOUTH);

        commandField.addActionListener(evt -> {
            System.out.println("Command Entered: " + commandField.getText());
            String input = commandField.getText();
            Parser p = new Parser(input, gpanel, manager);
            try {
                if (input.startsWith("ls")) {
                    String result = p.parseListCommand();
                    showResultDialog(frame, "Listing Results", result);
                } else {
                    Command cmd = p.parse();
                    handler.handle(cmd);
                    System.out.println("Command analyzed and executed.");
                }
            } catch (Exception e) {
                System.out.println("Error during command analysis.");
            }
        });

        // Setup Help Dialog
        helpButton.addActionListener(evt -> createHelpDialog(frame));

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createHelpDialog(JFrame owner) {
        JDialog helpDialog = new JDialog(owner, "Comandi di Aiuto", false);
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.setText(
                "<html><body style='font-family: Arial;'>"
                        + "<p><b style='color: blue;'>- create circle (10) (120,120)</b> <span style='color: gray;'>{Crea un cerchio con raggio 10 a (120,120)}</span></p>"
                        + "<p><b style='color: blue;'>- create rectangle (10,20) (120,120)</b> <span style='color: gray;'>{Crea un rettangolo con base 10 ed altezza 20 a (120,120)}</span></p>"
                        + "<p><b style='color: blue;'>- create img (\"src/images/logoalfa.png\")(100, 150)</b> <span style='color: gray;'>{Carica un'immagine dal percorso specificato a (100, 150)}</span></p>"
                        + "<p><b style='color: blue;'>- mv id (x,y)</b> <span style='color: gray;'>{Sposta l'oggetto/gruppo identificato dall'ID nella posizione scelta}</span></p>"
                        + "<p><b style='color: blue;'>- mvoff id (x,y)</b> <span style='color: gray;'>{Sposta l'oggetto/gruppo identificato dall'ID aggiungendo gli offset x,y alla posizione corrente}</span></p>"
                        + "<p><b style='color: blue;'>- del id</b> <span style='color: gray;'>{Elimina l'oggetto/gruppo identificato dall'ID}</span></p>"
                        + "<p><b style='color: blue;'>- scale id1 2.0 </b> <span style='color: gray;'>{ridimensiona l’oggetto/gruppo identificato dall’ID con un fattore di scala pari a 2.0}</span></p>"
                        + "<p><b style='color: blue;'>- grp (id1, id2, ...)</b> <span style='color: gray;'>{Crea un gruppo con gli oggetti specificati}</span></p>"
                        + "<p><b style='color: blue;'>- ungrp id</b> <span style='color: gray;'>{Rimuove il gruppo identificato dall'ID senza rimuovere i suoi componenti dal pannello}</span></p>"
                        + "<p><b style='color: blue;'>- ls id1</b> <span style='color: gray;'>{Visualizza le proprietà dell'oggetto/gruppo identificato da id1}</span></p>"
                        + "<p><b style='color: blue;'>- ls circle</b> <span style='color: gray;'>{Visualizza un elenco di oggetti di tipo cerchio}</span></p>"
                        + "<p><b style='color: blue;'>- ls all</b> <span style='color: gray;'>{Visualizza un elenco di tutti gli oggetti}</span></p>"
                        + "<p><b style='color: blue;'>- ls groups</b> <span style='color: gray;'>{Visualizza un elenco di tutti i gruppi di oggetti}</span></p>"
                        + "<p><b style='color: blue;'>- area id1</b> <span style='color: gray;'>{calcola l’area dell’oggetto identificato da id1 o la somma delle aree degli oggetti parte del gruppo identificato da id1}</span></p>"
                        + "<p><b style='color: blue;'>- area type</b> <span style='color: gray;'>{calcola l’area degli oggetti del tipo type}</span></p>"
                        + "<p><b style='color: blue;'>- perimeter id1</b> <span style='color: gray;'>{calcola il perimetro dell’oggetto identificato da id1 o la somma dei perimetri degli oggetti parte del gruppo identificato da id1}</span></p>"
                        + "<p><b style='color: blue;'>- perimeter type</b> <span style='color: gray;'>{calcola la somma dei perimetri degli oggetti di tipo type}</span></p>"
                        + "</body></html>");

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(400, 250));
        helpDialog.add(scrollPane);
        helpDialog.pack();
        helpDialog.setLocationRelativeTo(owner);
        helpDialog.setVisible(true);
    }

    private static void showResultDialog(JFrame owner, String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 250));

        JDialog resultDialog = new JDialog(owner, title, true);
        resultDialog.getContentPane().add(scrollPane);
        resultDialog.pack();
        resultDialog.setLocationRelativeTo(owner);
        resultDialog.setVisible(true);
    }
}
