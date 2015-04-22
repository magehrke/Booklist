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

import java.awt.Image;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.swing.JOptionPane;



/**
 * This class provides the major part of the Mac OS X integration of the
 * Booklist app.
 * <p>
 * <i> This class does not introduce any coupling on Mac OS X specific classes
 * or technologies. </i>
 * </p>
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 */
public class MacOSXAdapter {

	static {

		// properties to make the application look more similar to a native Mac
		// OS X application
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.mrj.application.growbox.intrudes",
				"false");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name",
				"Flashcards");

		try {
			

			Class<?> applicationClass = Class
					.forName("com.apple.eawt.Application");
			Object application = applicationClass.getMethod("getApplication")
					.invoke(null);
			
			/*
			 * Set the menu as Menu at the bar at the top of the screen
			 * and not at the top of the window.
			 */
			applicationClass.getMethod("setEnabledPreferencesMenu",
					boolean.class).invoke(application, Boolean.FALSE);

			/*
			 * Set the icon of the application.
			 */
			Image appImage = java.awt.Toolkit.getDefaultToolkit().getImage(
					Utilities.class.getResource("/booklist.png"));
			
			applicationClass.getMethod("setDockIconImage", Image.class).invoke(
					application, appImage);
	

			Class<?> applicationAdapterClass = Class
					.forName("com.apple.eawt.ApplicationListener");
			Object applicationAdapter = Proxy.newProxyInstance(
					System.class.getClassLoader(),
					new Class<?>[] { applicationAdapterClass },
					new InvocationHandler() {

						public Object invoke(Object proxy, Method method,
								Object[] args) throws Throwable {
							if (method.getName().equals("handleAbout")) {
								JOptionPane
										.showMessageDialog(
												null,
												"Copyright (c) 2015 Maximilian Alexander Gehrke\nAll rights reserved\nVersion 1.0",
												"Booklist",
												JOptionPane.INFORMATION_MESSAGE,
												Utilities.createImageIcon(
														"booklist.png",
														"The Booklist Icon"));
								args[0].getClass()
										.getMethod("setHandled", boolean.class)
										.invoke(args[0], Boolean.TRUE);
							} else if (method.getName().equals("handleQuit")) {
								// Check to see if the user has unsaved
								// changes.
								// If the user does not have unhandled changes
								// call
								// setHandled(true) otherwise call
								// setHandled(false).
								args[0].getClass()
										.getMethod("setHandled", boolean.class)
										.invoke(args[0], Boolean.TRUE);
							}
							return null;
						}
					});

			applicationClass.getMethod("addApplicationListener",
					Class.forName("com.apple.eawt.ApplicationListener"))
					.invoke(application, applicationAdapter);
			


		} catch (Exception e) {
			System.err.println("Mac OS X integration failed: "
					+ e.getLocalizedMessage() + ". "
					+ e.getMessage() + ".");
		}
	}
}
