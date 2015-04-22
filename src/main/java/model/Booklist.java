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

package model;

import gui.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map.Entry;
import javax.swing.UIManager;


/**
 * The program Booklist contains a list
 * of the books with functionality
 * to add and remove books or order the list
 * as you like.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 *
 */

public class Booklist {
	
	
	
	/**
	 * The UI which displays the program.
	 */
	private static Window window;
	
	/**
	 * Set the System, the program is exectued on.
	 */
	private static final String OS = System.getProperty("os.name").toLowerCase();
	
	/**
	 * A library is a collection of all books.
	 */
	private static Library library;
	
	/**
	 * The file where the books are read from
	 * and stored in.
	 */
	private static File file;
	
	

	/**
	 * Open the program Booklist to order, add
	 * or change books.
	 * 
	 * @throws FileNotFoundException
	 * 		if there is no File named "Default.booklist"
	 * 		this exception is thrown.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		if (System.getProperty("os.name").startsWith("Mac OS X")) {
			// we have to avoid tight coupling to make the project usable on
			// different platforms
			try {
				Class.forName("gui.MacOSXAdapter");
			} catch (ClassNotFoundException cnfe) {
				System.err
						.println("Setting up the Mac OS X integration failed. Error:");
				cnfe.printStackTrace(System.err);
			}
		} else {
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err
						.println("The native system look and feel is not available ("
								+ e.getLocalizedMessage() + ").");
			}
		}

		// show all user interface related properties
		Iterator<Entry<Object, Object>> properties = javax.swing.UIManager
				.getDefaults().entrySet().iterator();
		while (properties.hasNext()) {
			Entry<Object, Object> property = properties.next();
			System.out.println(property.getKey() + " = " + property.getValue());
		}
		
		//######################################################################
		
		/*
		 * Create the GUI.
		 */
		window = new Window();
		
		/*
		 * Get the directory where the default
		 * file is stored if it exists.
		 */
		String appDir = System.getProperty("user.dir");
		String pathPart;
		if(OS.indexOf("win") >= 0) {
			pathPart = appDir + "\\";
			} else {
			pathPart = appDir + "/";
		}
		
		/*
		 * Try to set the attribute file to the default file.
		 */
		file = new File(pathPart + "Default.booklist");
		
		if(file.exists()) {
			
			/*
			 * Create Library instance.
			 */
			library = new Library(file);
			
			/*
			 * Add library to the window.
			 * It will automatically update the Booklist.
			 */
			window.addLibrary(library);
		}
		
		/*
		 * Set the main frame of the window visible
		 * to show the program.
		 * 
		 * If the default file was found, the books
		 * will be displayed in the main Frame.
		 * If not, the mainFrame will be empty and
		 * the user has the possibility to create
		 * a new .booklist.
		 * 
		 * All other functionality will be exectued
		 * in the window class due to changes in the
		 * GUI.
		 */
		window.getMainFrame().setVisible(true);
		
	}
	
	
}
