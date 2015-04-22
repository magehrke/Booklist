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
 * which compares books by author.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 */

public class BookSortAuthor implements Comparator<Book> {

	int count;
	
	public BookSortAuthor() {
		count = 0;
	}
	
	@Override
	public int compare(Book b1, Book b2) {
		
		if(count == 0) {
			String name1 = b1.getLastName().toLowerCase() + b1.getSurName().toLowerCase();
			String name2 = b2.getLastName().toLowerCase() + b2.getSurName().toLowerCase();
			return name1.compareTo(name2);

		} else {
			String name1 = b1.getSurName().toLowerCase() + b1.getLastName().toLowerCase(); 
			String name2 = b2.getSurName().toLowerCase() + b2.getLastName().toLowerCase();
			return name1.compareTo(name2);		
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count1) {
		count = count1;
	}

}
