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

import java.io.File;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

/**
 * An editor to edit books.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 */

public class Utilities {
	
	/**
	 * Ending which is used for Booklist files.
	 */
	public static final String FILE_ENDING = ".booklist";
	
	public Utilities() {
		//do nothing, just definded for accidential instance creation.
	}

	/**
	 * Sets the frame title based on the name of the file.
	 * 
	 * @param frame
	 *            The frame's title.
	 * @param file
	 *            The document's file (which is edited in the frame.)
	 */
	public static void setFrameTitle(JFrame frame, File file) {

		if (file != null) {
			frame.setTitle(file.getName().substring(0,
					file.getName().lastIndexOf('.')));
			frame.getRootPane().putClientProperty("Window.documentFile", file);
		} else
			frame.setTitle("untitled");
	}
	
	/**
	 * Creates a JButton object that can be placed in toolbars.
	 * 
	 * @param title
	 *            The (very short) title of the button. The title will be
	 *            displayed below the button.
	 * @param iconPath
	 *            The path to the ImageIcon.
	 * @param iconDescription
	 *            A short description of the icon / the action.
	 * @return A new JButton.
	 * @see #createImageIcon(String, String)
	 */
	public static JButton createToolBarButton(String title, String iconPath,
			String iconDescription) {

		JButton button = new JButton(title, createImageIcon(iconPath,
				iconDescription));
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setOpaque(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		return button;
	}
	
	/**
	 * Creates an image icon. If the resource is no longer / not available, an
	 * exception is thrown.
	 * 
	 * @param path
	 *            A path relative to the location of this classes compiled class
	 *            file.
	 * @param description
	 *            A short description of the icon.
	 */
	public static ImageIcon createImageIcon(String path, String description) {

		URL imgURL = Utilities.class.getResource("/"+path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			throw new UnknownError("Missing resource: " + path + "("
					+ description + ")");
		}
	}
	
}
