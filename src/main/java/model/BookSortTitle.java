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

import java.util.Comparator;

/**
 * This class represents a Comparator
 * which compares books by title.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 *
 */

public class BookSortTitle implements Comparator<Book> {

	/**
	 * Determines which author comparison
	 * shall be taken.
	 * 
	 * 0 for sorting A to Z
	 * 1 for sorting Z to A
	 */
	int count;
	
	public BookSortTitle() {
		count = 0;
	}
	
	@Override
	public int compare(Book b1, Book b2) {
		
		if(count == 0) {
			return b1.getTitle().toLowerCase().compareTo(b2.getTitle().toLowerCase());
		} else {
			return b2.getTitle().toLowerCase().compareTo(b1.getTitle().toLowerCase());
		}
	}
	
	/**
	 * @return
	 * the counter which determines the
	 * way how the author comparison is done.
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * Set the way how the author comparison
	 * shall be done
	 * 
	 * @param count1 determines which author 
	 * comparison shall be taken.
	 * 
	 * 0 for sorting A to Z
	 * 1 for sorting Z to A
	 */
	public void setCount(int count1) {
		count = count1;
	}
	
}
