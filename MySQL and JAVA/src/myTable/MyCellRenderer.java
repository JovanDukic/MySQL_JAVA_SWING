package myTable;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MyCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, 0, 0);

		//String status = table.getModel().getValueAt(row, 7).toString();

		// if (status.equals("employed")) {
		// component.setBackground(Color.ORANGE);
		// component.setForeground(Color.RED);
		// } else {
		// component.setBackground(table.getBackground());
		// component.setForeground(table.getForeground());
		// }

		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}

		return component;

	}

	@Override
	public void setHorizontalAlignment(int alignment) {
		super.setHorizontalAlignment(SwingConstants.CENTER);
	}

}
