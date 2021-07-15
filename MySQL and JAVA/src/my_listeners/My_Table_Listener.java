package my_listeners;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class My_Table_Listener implements TableModelListener {

	// DODAJE SE NA TABLE MODEL !!!

	@Override
	public void tableChanged(TableModelEvent e) {


		switch (e.getType()) {
		case -1:
			System.out.println("Red je obrisan!");
			break;
		case 0:
//			System.out.println("Red je dodat!");
			break;
		case 1:
			System.out.println("Red je promenjen!");
			break;
		}
	}

}
