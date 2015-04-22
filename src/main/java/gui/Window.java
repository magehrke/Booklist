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
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import java.util.Vector;
import java.io.File;

import model.Book;
import model.Library;


public class Window {

	
	/**
	 * Container, which holds all book titles.
	 * It will be displayed on the left half of the 
	 * window and will be horizontal ordered.
	 */
	private JTable bookEntries;
	
	/**
	 * The DefaultTableModel inside the JTable which
	 * holds all the books.
	 */
	private DefaultTableModel model;
	
	/**
	 * Container, which holds horizontal.
	 * This container adds scrolling achsis if needed.
	 */
	private JScrollPane mainWindowScrollPane;
	
	/**
	 * Main window, which holds the GUI.
	 */
	private JFrame mainFrame;

	/**
	 * Add a book.
	 */
	private JButton addBookButton;

	/**
	 * Edit a book.
	 */
	private JButton editBookButton;

	/**
	 * Delete a book.
	 */
	private JButton deleteBookButton;
	
	/**
	 * Orders the books by titles.
	 */
	private JButton orderTitlesButton;
	
	/**
	 * Orders the books by authors.
	 */
	private JButton orderAuthorsButton;
	
	/**
	 * Saves the list, by writing the current 
	 * list to the IOFile.
	 */
	private JButton saveButton;
	
	/**
	 * The library, where all books are stored in.
	 */
	private Library library;
	
	private Vector<Book> books;
		
	private Boolean needSave;
	
	//########### CONSTANTS ###########
	private final double SCREENWIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	private final double SCREENHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	private final double DEFAULTWINDOWWIDTH = (SCREENWIDTH * 0.66);
	private final double DEFAULTWINDOWHEIGHT = (SCREENHEIGHT * 0.66);

	private JMenuItem exportFileMenuItem;

	private JMenuItem openFileMenuItem;

	private JMenuItem saveFileMenuItem;

	private JMenuItem saveAsFileMenuItem;

	private JMenuItem closeFileMenuItem;

	private JMenu fileMenu;

	private JMenuBar menuBar;
	
	private JToolBar toolBar;
	
	private BookEditor bookEditor;

	private JButton orderDateButton;
	
	private JPanel bottomPanel;
	
	private JLabel countOfBooksLabel;
	
	public static final Icon DELETEICON = Utilities.createImageIcon("delete.png", "Delete");
	public static final Icon WARNINGICON = Utilities.createImageIcon("warning.png", "Warning");

	private JMenuItem newFileMenuItem;
	
	private final FileDialog fileDialog;
	
	private int countSortAuthor;
	private int countSortTitle;
	private int countSortDate;
	
	//###########################################################################################################
	
	/**
	 * Constructor of the UI.
	 * An empty window with all its components is created.
	 */
	public Window() {
		
		countSortAuthor = 0;
		countSortTitle = 0;
		countSortDate = 0;
		
		needSave = false;
		
		/*
		 * Create the main window and set the title.
		 */
		mainFrame = new JFrame();
		
		/*
		 * Set frame title to "untitled"
		 */
		mainFrame.setTitle("untitled");
		
		/*
		 * Change the look of the window.
		 * Especially no line between toolbar and the top of the window anymore.
		 */
		mainFrame.getRootPane().putClientProperty("apple.awt.brushMetalLook",
				java.lang.Boolean.TRUE);
		
		/*
		 * What shall happen, if we press the red (x) in the top corner
		 * of the main window.
		 */
		mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		
		mainFrame.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	closeWindow();
			}
		});
		
		/*
		 * Set default window location, dynamically to the screen.
		 */
		mainFrame.setLocation((int) (SCREENWIDTH / 2 - (DEFAULTWINDOWWIDTH / 2)),
				(int) (SCREENHEIGHT / 2 - (DEFAULTWINDOWHEIGHT / 2)));
		
		/*
		 * Set default window size, dynamically to the screen.
		 */
		mainFrame.setSize((int) DEFAULTWINDOWWIDTH, (int) DEFAULTWINDOWHEIGHT);
		
		//############################################
		/*
		 * Create a BookEditor and a fileDialog,
		 * which are used again and again, while the
		 * main Frame is active.
		 */
		
		bookEditor = new BookEditor(this);
		
		fileDialog = new java.awt.FileDialog(mainFrame);
		fileDialog.setFilenameFilter((File directory, String name) -> {
			return name.endsWith(Utilities.FILE_ENDING);
		});
		
		//#############################################
		
		/*
		 * Create JTable and DefaultTableModel
		 * which holds all the books.
		 * The first attribute for the DefaultTableModel
		 * determines the column names.
		 */
		bookEntries = new JTable(new DefaultTableModel(new Object[] {"Titles", "Authors", "Date"}, 0)) {
			
			//Cells shall not be editable with double click
	        private static final long serialVersionUID = 1L;
	        @Override
	        public boolean isCellEditable(int row, int column) {                
	                return false;               
	                
	        };    
		};
		bookEntries.setBorder(BorderFactory.createEmptyBorder());
		
		/*
		 * Set the width of the Date Column.
		 */
		bookEntries.getColumnModel().getColumn(2).setMinWidth(120);
		bookEntries.getColumnModel().getColumn(2).setMaxWidth(120);
		
		/*
		 * If Enter is pressed, open the Bookeditor,
		 * to edit a book.
		 */
		bookEntries.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER ) {
                	int selectedRow = bookEntries.getSelectedRow();
                	openBookEditor();
					bookEntries.setRowSelectionInterval((selectedRow - 1), (selectedRow - 1));
                }
            }
        });

		/*
		 * Extract the DefualtTableModel from bookEntries
		 * which holds all books (rows of books).
		 */
		model = (DefaultTableModel) bookEntries.getModel();

		
		/*
		 * Put everything into a JScrollPane, so there will be
		 * scrolling axis, if needed.
		 */
		mainWindowScrollPane = new JScrollPane(bookEntries);
		mainWindowScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		mainWindowScrollPane.setSize((int) DEFAULTWINDOWWIDTH, (int) DEFAULTWINDOWHEIGHT - 30); 
		mainWindowScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		//#################
		
		/*
		 * Build a strip at the bottom of the window,
		 * where several properties can be shown.
		 */
		countOfBooksLabel = new JLabel("0");
		countOfBooksLabel.setFont(countOfBooksLabel.getFont().deriveFont(10f)); //Schriftgroesse veraendern
		
		JLabel descriptionOfCountOfBooksLabel = new JLabel("Books");
		descriptionOfCountOfBooksLabel.setFont(descriptionOfCountOfBooksLabel.getFont().deriveFont(10f)); //Schriftgroesse veraendern


		
		bottomPanel = new JPanel();
		bottomPanel.add(countOfBooksLabel);
		bottomPanel.add(descriptionOfCountOfBooksLabel);
		
		//#################		
		
		/*
		 * Set everything together,
		 * add everything to the frame.
		 */

		setupMenu();
		setupToolBar();
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
		mainFrame.getContentPane().add(mainWindowScrollPane);
		mainFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		mainFrame.setLocationByPlatform(true);

	}

	//###########################################################################################################
	
	/**
	 * Display all books in the GUI.
	 * 
	 * @param books The ArrayList which holds all
	 * the books.
	 */
	public void update() {
		
		int bookCount = books.size();		
		int modelCurrentRowCount = model.getRowCount();

		/*
		 * Delete all rows (data) in the model / JTable.
		 */
		for(int i = 0; i < modelCurrentRowCount; i++) {
			model.removeRow(0);
		}
		
		/*
		 * Add all data (rows) to the model /JTable.
		 */
		if(countSortAuthor == 0) {
			for(int i = 0; i < bookCount; i++) {
				model.addRow(new Object[] {books.get(i).getTitle(), books.get(i).getAuthor(), books.get(i).getDateWithWords()});
			}
		} else {
			for(int i = 0; i < bookCount; i++) {
				model.addRow(new Object[] {books.get(i).getTitle(), books.get(i).getAuthorLastNameFirst(), books.get(i).getDateWithWords()});
			}
		}
		
		/*
		 * Update the number of the Books in the List.
		 */
		countOfBooksLabel.setText(Integer.toString(bookCount));
	}
	
	//###########################################################################################################
	
	/**
	 * Set up the ToolBar of the frame
	 */
	public void setupToolBar() {
		
					
		/*
		 * Add a button for adding books.
		 * Icon with a plus and an FileListener will be linked.
		 */
		addBookButton = Utilities.createToolBarButton(" Add ", "plus.png", "Add a book to the list");
		addBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Book book = new Book();
				if(bookEditor.edit(book, 1)) {
					library.addBook(book);
					update();
					needSave();
					bookEntries.setRowSelectionInterval((bookEntries.getRowCount() - 1), (bookEntries.getRowCount() - 1));
				}
				
			}
		});
		
		/*
		 * Button for editing Books in a BookEditor window.
		 */
		editBookButton = Utilities.createToolBarButton(" Edit ", "edit.png", "Edit a book");
		
		editBookButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = bookEntries.getSelectedRow();
					openBookEditor();
					bookEntries.setRowSelectionInterval(selectedRow, selectedRow);
				}
		});
		
		
		/*
		 * Add a button for deleting books.
		 * Icon with a minus and an FileListener will be linked.
		 */
		deleteBookButton = Utilities.createToolBarButton(" Delete ", "minus.png", "Delete a book from the list");
		deleteBookButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(bookEntries.getSelectedRow() == -1) {
					JOptionPane.showOptionDialog(null,
							"Please select at least one book",
							"", 
							JOptionPane.DEFAULT_OPTION, 
							JOptionPane.WARNING_MESSAGE,
							WARNINGICON, 
							null, 
							null);
					
				} else {
					
					int chosenButton = JOptionPane.showOptionDialog(null,
							"Are you sure you want to delete these books?",
							"Bookdeletion",
			                JOptionPane.OK_CANCEL_OPTION,
			                JOptionPane.ERROR_MESSAGE, 
			                DELETEICON, 
			                new String[]{"Delete", "Cancel"}, "Delete");
					
					if(chosenButton == JOptionPane.OK_OPTION) {
						int selectedRow = bookEntries.getSelectedRow();
						library.deleteBooks(bookEntries.getSelectedRows());
						update();
						needSave();
						if(selectedRow == bookEntries.getRowCount()) { //last row has been deleted
							selectedRow -= 1;
						}
						
						bookEntries.setRowSelectionInterval(selectedRow, selectedRow);
					}
				}
			}
		});

		
		/*
		 * Add a button for ordering books with titles.
		 */
		orderTitlesButton = Utilities.createToolBarButton(" Sort Titles ", "sort_titles.png", "Sort the books by titles");
		orderTitlesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				library.sortByTitles(countSortTitle);
				countSortTitle = (countSortTitle + 1) % 2;
				update();
				needSave();
			}
		});
		

		/*
		 * Add a button for ordering books with authors.
		 */
		orderAuthorsButton = Utilities.createToolBarButton(" Sort Authors ", "sort_authors.png", "Sort the books by authors");
		orderAuthorsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				library.sortByAuthors(countSortAuthor);
				countSortAuthor = (countSortAuthor + 1) % 2;
				update();
				needSave();
			}
		});
		
		orderDateButton = Utilities.createToolBarButton(" Sort Date ", "date.png", "Sort the books by date");
		orderDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				library.sortByDate(countSortDate);
				countSortDate = (countSortDate + 1) % 2;
				update();
				needSave();
			}
		});
		
		/*
		 * Save the Booklist with all the changes happend
		 * since last saving or opening.
		 */
		saveButton = Utilities.createToolBarButton(" Save ", "disc.png", "Save the Booklist");
		saveButton.addActionListener(ae -> {
			if(needSave) {
				saveWithWarning();
			}
		});
		
		/*
		 * Create the JToolBar and add the buttons.
		 */
		toolBar = new JToolBar();
		toolBar.putClientProperty("JToolBar.isRollover", Boolean.FALSE); //no idea what it does.
		
		toolBar.add(addBookButton);
		toolBar.addSeparator();
		toolBar.add(editBookButton);
		toolBar.addSeparator();
		toolBar.add(deleteBookButton);
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(orderTitlesButton);
		toolBar.addSeparator();
		toolBar.add(orderAuthorsButton);
		toolBar.addSeparator();
		toolBar.add(orderDateButton);
		toolBar.add(Box.createHorizontalGlue()); //the following buttons will be displayed on the right side
		toolBar.add(saveButton);
		
		
		/*
		 * So we cannot pull the JToolBar out, so it
		 * will be displayed as an independent window.
		 */
		toolBar.setFloatable(false);
		
	}
	
	//###########################################################################################################
	
	public void setupMenu() {
		
		// setup the menu and its listeners
		newFileMenuItem = new JMenuItem("New");
		newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		newFileMenuItem.addActionListener(ae -> {
			Window newWindow = new Window();
			Library newLibrary = new Library();
			newWindow.addLibrary(newLibrary);
			newWindow.getMainFrame().setVisible(true);
			needSave();
		});

		openFileMenuItem = new JMenuItem("Open File...");
		openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openFileMenuItem.addActionListener(ae -> {
			fileDialog.setMode(FileDialog.LOAD);
			fileDialog.setVisible(true);
			String filename = fileDialog.getFile();
			if (filename != null) {
				if (!filename.endsWith(Utilities.FILE_ENDING))
					filename += Utilities.FILE_ENDING;
				File file = new File(fileDialog.getDirectory(), filename);
				createBookWindow(file);
			}
		});

		saveFileMenuItem = new JMenuItem("Save");
		saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveFileMenuItem.addActionListener(ae -> {
			if(needSave) {
				saveWithWarning();
			}
		});

		saveAsFileMenuItem = new JMenuItem("Save As...");
		saveAsFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				(java.awt.event.InputEvent.SHIFT_MASK | Toolkit
						.getDefaultToolkit().getMenuShortcutKeyMask())));
		saveAsFileMenuItem.addActionListener(ae -> {
			saveAs();
		});
		
		// setup the menu and its listeners
		exportFileMenuItem = new JMenuItem("Export List as .txt");
		exportFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		exportFileMenuItem.addActionListener(ae -> {
			exportList();
		});

		closeFileMenuItem = new JMenuItem("Close Window");
		closeFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		closeFileMenuItem.addActionListener(ae -> {
			closeWindow();
		});

		fileMenu = new JMenu("File");
		fileMenu.add(newFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(openFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveFileMenuItem);
		fileMenu.add(saveAsFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exportFileMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(closeFileMenuItem);

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
	}
	
	//###########################################################################################################
	
	public void openBookEditor() {
    	if(bookEntries.getSelectedRow() == -1) {
			JOptionPane.showOptionDialog(null,
					"Please select at least one book",
					"", 
					JOptionPane.DEFAULT_OPTION, 
					JOptionPane.WARNING_MESSAGE,
					WARNINGICON, null, null);
			
		} else {
			Book book = books.get(bookEntries.getSelectedRow());
			if(bookEditor.edit(book, 0)) {
				update();
				needSave();
			}
		}
	}
	
	
	public void addLibrary(Library library) {
		this.library = library;
		this.books = library.getBooks();
		Utilities.setFrameTitle(mainFrame, library.getFile()); //set title of the frame to file-name
		update();
	}
	
	//###################################
	
	public void saveWithWarning() {
		int chosenButton = JOptionPane.showOptionDialog(null, "Are you sure you want to save?\nThis cannot be undone!!","Save",
	            JOptionPane.OK_CANCEL_OPTION,
	            JOptionPane.ERROR_MESSAGE, 
	            DELETEICON, 
	            new String[]{"Save", "Cancel"}, "Save");
		if(chosenButton == JOptionPane.OK_OPTION) {
			save();
		}
	}
	public void save() {
		if(needSave) {
			if(library.getFile() != null) {
				doSave();
			} else {
				saveAs();
			}
		}
	}
	
	public void doSave() {
			library.writeToFile();
			needSave = false;
	}
	
	public void saveAs() {
		fileDialog.setMode(FileDialog.SAVE);
		fileDialog.setVisible(true);
		String filename = fileDialog.getFile();
		if (filename != null) {
			if (!filename.endsWith(Utilities.FILE_ENDING))
				filename += Utilities.FILE_ENDING;

			File newFile = new File(fileDialog.getDirectory(), filename);
			if (newFile.exists()) {
				if (JOptionPane
						.showConfirmDialog(
								mainFrame,
								"The file with the name:\n"
										+ filename
										+ "\nalready exists.\nDo you want to overwrite the file?",
								"Warning", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION)
					return;
			}
			library.setFile(newFile);
			doSave();
		}
	}
	
	
	//###################################
	
	
	public void exportList() {
		fileDialog.setMode(FileDialog.SAVE);
		fileDialog.setVisible(true);
		String filename = fileDialog.getFile();
		if (filename != null) {
			if (!filename.endsWith(".txt"))
				filename += ".txt";

			File newFile = new File(fileDialog.getDirectory(), filename);
			if (newFile.exists()) {
				if (JOptionPane
						.showOptionDialog(mainFrame, 
								"The file with the name:\n"
								+ filename
								+ "\nalready exists.\nDo you want to overwrite the file?", 
								"Warning", 
								JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE, WARNINGICON, null, null) == JOptionPane.NO_OPTION)
					return;
			}
			library.export(newFile);
		}
	}
	public static boolean createBookWindow(File file) {
		try {
			Window newWindow = new Window();
			Library newLibrary = new Library(file);
			newWindow.addLibrary(newLibrary);
			newWindow.getMainFrame().setVisible(true);
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(
					null,
					"The document \"" + file.getName()
							+ "\" could not be read." + "\n"
							+ e.getLocalizedMessage(), "",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	public void closeWindow() {
		
    	if(needSave) {
			int chosenButton = JOptionPane.showOptionDialog(null,
					"You have unsaved changes."
					+ "Do you want to save bevor closing the window?",
					"Bookdeletion",
	                JOptionPane.YES_NO_CANCEL_OPTION,
	                JOptionPane.ERROR_MESSAGE, 
	                DELETEICON, 
	                new String[]{"Save", "Don't Save", "Cancel"}, "Save");
			
			if(chosenButton == JOptionPane.OK_OPTION) {
				save();
				mainFrame.setVisible(false);
				mainFrame.dispose();
			} else if(chosenButton == JOptionPane.NO_OPTION) {
				mainFrame.setVisible(false);
				mainFrame.dispose();
			}
    	} else {
    		mainFrame.setVisible(false);
    		mainFrame.dispose();
    	}
	}
	
	/**
	 * Get the main JFrame.
	 * @return The main JFrame.
	 */
	public JFrame getMainFrame() {
		return mainFrame;
	}
	
	public void needSave() {
		needSave = true;
	}
	

}