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

/**
 * This class represents a book.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 *
 */

public class Book {
	
	private String title, surName, lastName, month, year;
	
	private final static String[] NAMEOFMONTH = {
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	};
	
	public Book() {
		title = "";
		surName = "";
		lastName = "";
		month = "";
		year = "";
	}

	public Book(String title, String surName, String lastName, String month, String year) {
		this.title = title;
		this.surName = surName;
		this.lastName = lastName;
		this.month = month;
		this.year = year;
	}
	
	/**
	 * Returns a String which contains the author,
	 * written <i>first Name + last Name </i>
	 * 
	 * @return a String which contains the author,
	 * written <i>first Name + last Name </i>
	 */
	public String getAuthor() {
		if(surName.isEmpty() && lastName.isEmpty()) {
			return "";
		} else if(surName.isEmpty()) {
			return lastName;
		} else if(lastName.isEmpty()) {
			return surName;
		} else {
			return surName + " " + lastName;
		}
	}
	
	/**
	 * Returns a String which contains the author,
	 * written <i>last Name + ", " + first Name </i>
	 * 
	 * @return a String which contains the author,
	 * written <i>last Name + ", " + first Name </i>
	 */
	public String getAuthorLastNameFirst() {
		if(surName.isEmpty() && lastName.isEmpty()) {
			return "";
		} else if(surName.isEmpty()) {
			return lastName;
		} else if(lastName.isEmpty()) {
			return surName;
		} else {
			return lastName + ", " + surName;
		}
	}
	
	/**
	 * Returns the date which consists of
	 * <i> month + "." + year <\i>
	 * 
	 * @return the date which consists of
	 * <i> month + "." + year <\i>
	 */
	public String getDate() {
		if(month.isEmpty() && year.isEmpty()) {
			return "";
		} else if(month.isEmpty()) {
			return year;
		} else if(year.isEmpty()){
			return "";
		} else {
			return month + "." + year;
		}
	}
	
	/**
	 * Returns the date which consists of
	 * <i> year + "," + month<\i>.
	 * Month is displayed as a word, not
	 * as a number.
	 * 
	 * @return the date which consists of
	 * <i> year + "," + month<\i>.
	 * Month is displayed as a word, not
	 * as a number.
	 */
	public String getDateWithWords() {
		if(month.isEmpty() && year.isEmpty()) {
			return "";
		} else if(month.isEmpty()) {
			return year;
		} else if(year.isEmpty()){
			int monthInt = Integer.parseInt(month);
			
			if(monthInt > 0 && monthInt < 13) {
				return NAMEOFMONTH[monthInt - 1];
			} else {
				return "";
			}
		} else {
			int monthInt = Integer.parseInt(month);
			
			if(monthInt > 0 && monthInt < 13) {
				return year + ", " + NAMEOFMONTH[monthInt - 1];
			} else {
				return year;
			}
		}
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the surName
	 */
	public String getSurName() {
		return surName;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
}
