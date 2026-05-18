package tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class LiveTransaccionTableModel extends AbstractTableModel{
	
	private final String[] columns = {
			"ID ORDEN",
			"FECHA",
			"ESTADO",
			"CANTIDAD"
	};
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
