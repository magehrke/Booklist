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
 * which compares books by date.
 * 
 * @author Maximilian Alexander Gehrke
 * @version 1.0
 *
 */

public class BookSortDate implements Comparator<Book>{
	
	int count;
	
	public BookSortDate() {
		count = 0;
	}
	
	@Override
	public int compare(Book b1, Book b2) {
		
		String datum1 = b1.getYear() + b1.getMonth();
		String datum2 = b2.getYear() + b2.getMonth();
		
		if(count == 0) {
			return datum1.compareTo(datum2);
		} else {
			return datum2.compareTo(datum1);
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count1) {
		count = count1;
	}
}
