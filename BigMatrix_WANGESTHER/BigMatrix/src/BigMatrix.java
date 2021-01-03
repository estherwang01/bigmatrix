/**
* Name: Esther Wang 
* Mrs. Kankelborg
* Period 1 
* Project 4 BigMatrix
* Revision History:
* 
* 2/11: pulled starter code, started writing 
* 2/12: finished writing something for all the methods except add
* 2/14: handled 0 case in set / multiply 
* 2/14: also dealt with some issues in my rowfirst and colfirst 
* hashmaps (were not equivalent; fixed) 
* 2/15: bug in setting to 0. row not deleting properly. tried various solutions 
* 2/17: continued work from 2/15. Found bug. Started working on fixing timing
* for add matrix
* 2/18: Fixed timing for add matrix. 
* 2/19: Cleaned up code / whitespace / line lengths. 
* Filled in missing documentation 
* 2/20: final changes / documentation. Turned in. 
*
* Class Description: (in your own words): creates a structure for representing 
* huge data sets, such as a 2 billion by 2 billion matrix, 
* using limited memory and supporting operations such as 
* summing a row, column, or the entire matrix. 
*/

import java.util.List;
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

public class BigMatrix 
{
	/*matrixRow = Row first storage: Keys are row indexes 
	Values are type HashMap<Integer, Integer> with key as column index
	and value as the integer value.
	matrixCol is the opposite for column first*/
	private HashMap<Integer, HashMap<Integer, Integer>> matrixRow;
	private HashMap<Integer, HashMap<Integer, Integer>> matrixCol; 
	
	//Default constructor 
	public BigMatrix()
	{
		matrixRow = new HashMap<>(); 
		matrixCol = new HashMap<>(); 
	}
	
	/**
	 * @param row: row index
	 * @param col: column index
	 * @param: value to set to. 0 if wish to remove the element 
	 * @return: void 
	 * Sets the value at (row, col) to {value}. Removes it if {value} = 0. 
	 * Removes the entire row or column if the removal of the value causes
	 * a row or column to become empty. 
	 * */
	public void setValue(int row, int col, int value)
	{
		boolean rowb = matrixRow.containsKey(row); 
		boolean colb = matrixCol.containsKey(col); 
		//Removal case: value = 0 
		if(value==0)
		{
			//If index pair not present, do not need to do anything 
			if(!rowb || !colb)
			{
				return; 
			}
			//removal of the element 
			matrixRow.get(row).remove(col);
			matrixCol.get(col).remove(row); 
			//checking if a whole row / column can be removed 
			if(matrixRow.get(row).isEmpty())
			{
				matrixRow.remove(row); 
			}
			if(matrixCol.get(col).isEmpty())
			{
				matrixCol.remove(col); 
			}
			return; 
		}
		//non zero value set 
		if(!colb)
		{
			HashMap<Integer, Integer> newCol = new HashMap<>(); 
			matrixCol.put(col, newCol); 
		}
		if(!rowb)
		{
			HashMap<Integer, Integer> newRow = new HashMap<>(); 
			matrixRow.put(row, newRow); 
		}
		matrixRow.get(row).put(col, value); 
		matrixCol.get(col).put(row, value); 
		
	}
	
	/**
	 * @param row: row index
	 * @param col: column index 
	 * @return: value at index row and index col. Return 0 if none 
	 * Returns the value at the specified row and column indexes, assuming 
	 * it as 0 by default if there is no value present 
	 * */
	public int getValue(int row, int col)
	{
		boolean rowb = matrixRow.containsKey(row); 
		boolean colb = matrixCol.containsKey(col); 
		//If index pair is not present in the matrix, return 0 
		if(!rowb || !colb)
		{
			return 0; 
		}
		return matrixRow.get(row).getOrDefault(col, 0); 
	}
	
	/**
	 * @param: none 
	 * @return an integer list 
	 * Returns the indexes of all non empty rows in the matrix 
	 * */
	public List<Integer> getNonEmptyRows()
	{
		List<Integer> rows = new ArrayList<>(); 
		Set<Integer> keys =  matrixRow.keySet(); 
		rows.addAll(keys); 
		return rows; 
	}
	
	/**
	 * @param col: column index 
	 * @return: integer list 
	 * Returns a list of the indexes of the non empty rows 
	 * in the specified column 
	 * */
	public List<Integer> getNonEmptyRowsInColumn(int col)
	{
		List<Integer> rows1 = new ArrayList<>(matrixCol.get(col).keySet());
		return rows1; 
	}
	
	/**
	 * @param: none 
	 * @return: integer list 
	 * Returns a list of the indexes of the non empty columns in the matrix 
	 * */
	public List<Integer> getNonEmptyCols()
	{
		List<Integer> cols = new ArrayList<>(); 
		Set<Integer> keys =  matrixCol.keySet(); 
		cols.addAll(keys); 
		return cols; 
	}
	
	/**
	 * @param row: row index 
	 * @return: integer list 
	 * Returns a list of the non empty columns in a specified row in
	 * the matrix 
	 * */
	public List<Integer> getNonEmptyColsInRow(int row)
	{
		List<Integer> cols1 = new ArrayList<>(matrixRow.get(row).keySet());
		return cols1; 
	}
	
	/**
	 * @param row: row index 
	 * @return: integer value (sum) 
	 * Calculates and returns the sum of the elements in a specified row 
	 * Returns 0 if the row is not present in the matrix 
	 * */
	public int getRowSum(int row)
	{
		int sum = 0; 
		//If row does not exist, sum will be 0
		if(!matrixRow.containsKey(row))
		{
			return 0; 
		}
		Collection<Integer> vals = matrixRow.get(row).values(); 
		for(Integer val:vals)
		{
			sum+= val; 
		}
		return sum; 
	}
	
	/**
	 * @param col: column index
	 * @return: integer value (sum) 
	 * Calculates and returns the sum of the values in a specified column
	 * Returns 0 if the column does not exist in the matrix 
	 * */
	public int getColSum(int col)
	{
		int sum = 0; 
		//If column does not exist, sum will be 0 
		if(!matrixCol.containsKey(col))
		{
			return 0; 
		}
		Collection<Integer> vals = matrixCol.get(col).values();
		for(Integer val:vals)
		{
			sum+= val; 
		}
		return sum; 
	}
	
	/**
	 * @param: none 
	 * @return: integer value (sum) 
	 * Calculates and returns the sum of all the elements in the entire matrix 
	 * */
	public int getTotalSum()
	{
		List<Integer> rows = getNonEmptyRows(); 
		int sum = 0; 
		for(Integer row:rows)
		{
			//uses the getRowSum() method from earlier
			sum += getRowSum(row); 
		}
		return sum; 
	}
	
	/**
	 * @param constant: some constant value 
	 * @return: a new BigMatrix 
	 * Takes every value within the this object and multiplies it by the
	 * specified constant, storing the resultant value in the new BigMatrix
	 * which is eventually returned. 
	 * In the case that the constant is 0, an empty matrix should be returned 
	 * */
	public BigMatrix multiplyByConstant(int constant)
	{
		BigMatrix newMatrix = new BigMatrix(); 
		//If constant is 0, matrix becomes empty 
		if(constant == 0)
		{
			return newMatrix; 
		}
		for(Integer row: getNonEmptyRows())
		{
			for(Integer col: getNonEmptyColsInRow(row))
			{
				newMatrix.setValue(row, col, matrixRow.get(row).get(col)*constant);
			}
		}
		return newMatrix; 
	}
	
	/**
	 * @param other: another BigMatrix 
	 * @return: a BigMatrix 
	 * Adds the current matrix to other.
	 * */
	public BigMatrix addMatrix(BigMatrix other)
	{
		BigMatrix newMatrix = other; 
		for(Integer row:getNonEmptyRows())
		{
			for(Integer col:getNonEmptyCols())
			{
				//Set value to current value in other + current
				//value in this
				//If there is no value, getValue() will return 0 
				//which does not cause any issues
				newMatrix.setValue(row, col, getValue(row, col)+
						other.getValue(row,  col)); 
			} 
		}
		return newMatrix; 
	}
}
