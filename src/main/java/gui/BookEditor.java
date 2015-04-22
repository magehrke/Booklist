/** License (BSD Style License):
 *  Copyright (c) 2015
 *  Maximilian Alexander Gehrke
 *  All rights reserved.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.KeyStroke;

import javax.swing.border.BevelBorder;

import model.Book;

/**
 * An editor to edit books.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 */
public class BookEditor {

	private final Window owner;
	
	// GUI components

	private final JDialog dialog;

	private final Box titleBox;

	private final JLabel titleLabel;

	private final JTextField titleField;

	private final JTextField surNameField;

	private final Box okCancelBox;

	private final JButton okButton;

	private final JButton cancelButton;

	private final JTextField lastNameField;

	private final Box surAndLastNameBox;

	private final Box lastNameBox;

	private final Box surNameBox;

	private final JLabel surNameLabel;
	
	private final JLabel lastNameLabel;

	private final Box dateBox;

	private final JLabel yearLabel;
	
	private final JLabel monthLabel;

	private final JTextField yearField;

	private final JTextField monthField;

	private final Box authorAndDateBox;

	private final Box yearBox;

	private final Box monthBox;
	
	/**
	 * True if the card needs to be updated, false otherwise. This variable is
	 * set when the dialog is closed.
	 */
	private boolean update = false;
	
	//#########################################################################

	/**
	 * Creates a new editor that can be used to edit books.
	 * 
	 * @param owner
	 *            This editor's parent frame.
	 */
	public BookEditor(Window owner) {

		this.owner = owner;

		// Setup the components:

		titleField = new JTextField();
		titleField.setAlignmentX(0.0f);

		titleLabel = new JLabel("Title:");
		titleLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		titleLabel.setAlignmentX(0.0f);

		titleBox = Box.createVerticalBox();
		titleBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		titleBox.add(titleLabel);
		titleBox.add(Box.createVerticalStrut(5));
		titleBox.add(titleField);

		
		surNameLabel = new JLabel("Surname:");
		surNameLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		surNameLabel.setAlignmentX(0.0f);
		
		lastNameLabel = new JLabel("Lastname:");
		lastNameLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		lastNameLabel.setAlignmentX(0.0f);
		
		surNameField = new JTextField();
		surNameField.setAlignmentX(0.0f); //Text will be aligned left
		
		lastNameField = new JTextField();
		lastNameField.setAlignmentX(0.0f); //Text will be aligned left
		

		
		surNameBox = Box.createVerticalBox();
		surNameBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		surNameBox.add(surNameLabel);
		surNameBox.add(surNameField);
		
		lastNameBox = Box.createVerticalBox();
		lastNameBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		lastNameBox.add(lastNameLabel);
		lastNameBox.add(lastNameField);
				
		surAndLastNameBox = Box.createHorizontalBox();
		surAndLastNameBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		surAndLastNameBox.add(surNameBox);
		surAndLastNameBox.add(Box.createHorizontalGlue());
		surAndLastNameBox.add(lastNameBox);
		
		

		monthLabel = new JLabel("Month:");
		monthLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		monthLabel.setAlignmentX(0.0f);
		
		yearLabel = new JLabel("Year:");
		yearLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		yearLabel.setAlignmentX(0.0f);
		

		monthField = new JTextField();
		monthField.setAlignmentX(0.0f); //Text will be aligned to the left
		
		yearField = new JTextField();
		yearField.setAlignmentX(0.0f); //Text will be aligned to the left
		
		monthBox = Box.createVerticalBox();
		monthBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		monthBox.add(monthLabel);
		monthBox.add(monthField);
		
		yearBox = Box.createVerticalBox();
		yearBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		yearBox.add(yearLabel);
		yearBox.add(yearField);
		
		
		dateBox = Box.createHorizontalBox();
		dateBox.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 10, 10),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		dateBox.add(monthBox);
		dateBox.add(Box.createHorizontalGlue());
		dateBox.add(yearBox);
		
		
		authorAndDateBox = Box.createVerticalBox();
		authorAndDateBox.setBorder(BorderFactory.createEmptyBorder());
		authorAndDateBox.add(surAndLastNameBox);
		authorAndDateBox.add(dateBox);
		
		
		okButton = new JButton("Ok");



		cancelButton = new JButton("Cancel");

		okCancelBox = Box.createHorizontalBox();
		okCancelBox.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		okCancelBox.add(Box.createGlue());
		okCancelBox.add(cancelButton);
		okCancelBox.add(okButton);

		dialog = new JDialog(this.owner.getMainFrame(), "Edit Book", true);
		dialog.getRootPane().putClientProperty("Window.style", "small");
		dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		

		
		int windowHeight = 330;
		dialog.setMinimumSize(new java.awt.Dimension(320, windowHeight));
		dialog.setMaximumSize(new java.awt.Dimension(3000, windowHeight));
		dialog.setSize(640, windowHeight);
		dialog.setLocationRelativeTo(this.owner.getMainFrame());
		dialog.getContentPane().add(titleBox, BorderLayout.NORTH);
		dialog.getContentPane().add(authorAndDateBox);
		dialog.getContentPane().add(okCancelBox, BorderLayout.SOUTH);
		
		// Configure the event handling:

		ActionListener cancelAL = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update = false;
				dialog.setVisible(false);
			}
		};
		
		cancelButton.addActionListener(cancelAL);
		cancelButton.registerKeyboardAction(cancelAL, 
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		
		okButton.addActionListener(ae -> {
			update = true;
			dialog.setVisible(false);
		});
		
		//The OK-Button will now Listen to the enter-key.
		//while we are in this dialog.
		//It will be blue highlighted as well,
		//but doesn't change the selected Area when opening the frame/dialog.
		dialog.getRootPane().setDefaultButton(okButton);
	}

	//#########################################################################
	
	/**
	 * Edits a given book. If the book was edited, then
	 * <code>true</code> is returned.
	 * 
	 * @param book
	 *            The book that may be edited. The book is used to
	 *            initialize this dialog.
	 * @return <code>true</code> if the card was edited; false otherwise.
	 */
	public boolean edit(Book book, int title) {

		// set to false to make sure that the card is not updated, when the
		// dialog is closed
		// using the dialogs "close" button
		update = false;
		
		//Determine which title shall be shown
		if(title == 1) {
			dialog.setTitle("Add Book");
		} else if(title == 0) {
			dialog.setTitle("Edit Book");
		}

		// configure the editor
		titleField.setText(book.getTitle());
		surNameField.setText(book.getSurName());
		lastNameField.setText(book.getLastName());
		monthField.setText(book.getMonth());
		yearField.setText(book.getYear());

		/*
		 * Construct a loop, which will be executed
		 * as long as there are false entries in 
		 * the BookEditor window.
		 */
		boolean allEntriesCorrect = false;
		while(!allEntriesCorrect) {
			
			// show the dialog to enable the user to edit the book
			dialog.setVisible(true);
	
			//########
			//Why are the following components only executed When the window is closed?
			//########
			
			
			// the dialog is closed
			if (update) {
				
				//get the text of all input fields
				String titleFieldText = titleField.getText();
				String surNameFieldText = surNameField.getText();
				String lastNameFieldText = lastNameField.getText();
				String monthFieldText = monthField.getText();
				String yearFieldText = yearField.getText();
				
				//Check if all inputs are correct
				if(titleFieldText.isEmpty()) {
					warningWindowForInput("Please provide a title.", "Missing Title");
				} else if((!monthFieldText.isEmpty()) && (monthFieldText.length() != 2 
						|| (!Character.isDigit(monthFieldText.charAt(0)))
						|| (!Character.isDigit(monthFieldText.charAt(1)))
						|| (Integer.parseInt(monthFieldText) < 1)
						|| (Integer.parseInt(monthFieldText) > 12))) {
					warningWindowForInput("Please provide a correct month or leave it empty."
							+ "\nThe month must be a two digit number btw. 1 and 12.", "Incorrect Month");
				} else if((!yearFieldText.isEmpty()) && (yearFieldText.length() != 4 
						|| (!Character.isDigit(yearFieldText.charAt(0)))
						|| (!Character.isDigit(yearFieldText.charAt(1)))
						|| (!Character.isDigit(yearFieldText.charAt(2)))
						|| (!Character.isDigit(yearFieldText.charAt(3)))
						|| (Integer.parseInt(yearFieldText) < 1900)
						|| (Integer.parseInt(yearFieldText) > 2100))) {
					warningWindowForInput("Please provide a correct year or leave it empty."
							+ "\nThe year must be a four digit number btw. 1900 and 2100.", "Incorrect Year");
				} else {
					//if everything is correct, set the details
					//of the book with the new input
					book.setTitle(titleFieldText);
					book.setSurName(surNameFieldText);
					book.setLastName(lastNameFieldText);
					book.setMonth(monthFieldText);
					book.setYear(yearFieldText);
					
					//end loop and return update (= true)
					allEntriesCorrect = true;
				}
			} else {
				//update = false
				//end the loop and return update (= false)
				allEntriesCorrect = true;
			}
		}
		return update;
	}
	
	//#########################################################################

	/**
	 * Standard warning window for
	 * wrong input.
	 * 
	 * @param message
	 * The message which will be shown.
	 * @param title
	 * the title of the warning window.
	 */
	public void warningWindowForInput(String message, String title) {
		JOptionPane.showOptionDialog(dialog, 
				message, 
				title, 
				JOptionPane.OK_OPTION, 
				JOptionPane.WARNING_MESSAGE, 
				Window.WARNINGICON, 
				new String[] {"OK"}, "OK");
	}

}
