package com.a4b.androidhelper.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window3 extends JFrame {

    private JPanel contentPane;
    private JTextField bookNameField;
    private JTextField authorNameField;
    private JComboBox genreComboBox;
    private JCheckBox isTakenCheckBox;
    private JButton cancelButton;
    private JButton saveButton;

    public Window3() {

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelChanges();
            }
        });
    }

    private void cancelChanges() {
        // Reset fields
        bookNameField.setText("");
        genreComboBox.setSelectedIndex(0);
        isTakenCheckBox.setSelected(false);
    }

    public static class Hello{
        public static void main(String[] args) {
            // Launch the book editor form
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    Window3 bookEditorExample = new Window3();
                    bookEditorExample.setVisible(true);

    //                bookEditorExample.setSaveButtonListener(new SaveButtonListener() {
    //                    @Override
    //                    public void onSaveClicked(Book book) {
    //                        System.out.println("Entered Book Details:");
    //                        System.out.println("Book Title: " + book.getName());
    //                        System.out.println("Author: " + book.getAuthor().getName());
    //                        System.out.println("Genre: " + book.getGenre());
    //                        System.out.println("Is Unavailable: " + book.isTaken());
    //                    }
    //                });
                }
            });
        }
    }
}


