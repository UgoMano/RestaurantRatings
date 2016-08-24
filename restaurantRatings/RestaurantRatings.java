/*
* Ugochukwu Umeano
* umu23@drexel.edu
* CS338:GUI, Assignment 1
* 
*/

package restaurantRatings;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class RestaurantRatings extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JList list; // list
	private JTable table; // table
	private JTextField[] fields; // list of fields
	private JButton listToTable; // >> button
	private JButton tableToList; // << button
	private final JButton add; // add button
	private final JButton change; // change button
	private final JButton remove; // remove button
	private DefaultListModel listModel; // model of lists
	private DefaultTableModel tableModel; // model of table
	
	// name of columns
	private String[] columnNames = {"Restaurant Name", 
			"City", 
			"State", 
			"Date of Visit", 
			"Rating (1-5)"
			};
	private Object[][] data; // data in the table

	// class constructor
	public RestaurantRatings()
	{
		// Name of Program
		super("Restaurant Ratings");
		
		// Set outer layout 
		setLayout(new BorderLayout());
		
		// Instantiate components
		listModel = new DefaultListModel();
		tableModel = new DefaultTableModel(data, columnNames);
		listToTable = new JButton (">>>");
		tableToList = new JButton ("<<<");
		
		// Deactivate buttons
		listToTable.setEnabled(false);
		tableToList.setEnabled(false);
		
		// Instantiate top pane
		JPanel topPane = new JPanel ();
		
		// Instantiate list
		list = new JList(listModel);
		list.setVisibleRowCount(26);
		list.addListSelectionListener(
			new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent event) 
				{
					showListInField();
					tableToList.setEnabled(false);
					listToTable.setEnabled(true);
					remove.setEnabled(true);
					change.setEnabled(true);
				}
				
			}
		);
		
		// add scroll pane to table
		topPane.add ( new JScrollPane( list), BorderLayout.WEST );
		
		// instantiate pane that holds << and >> buttons
		JPanel topCenterPane = new JPanel ();
		topCenterPane.setLayout(new BoxLayout(topCenterPane, BoxLayout.Y_AXIS));
		
		// Initialize JTable
		table = new JTable(tableModel);
		
		listToTable.setAlignmentY(Component.CENTER_ALIGNMENT);
		topCenterPane.add(listToTable);
		
		// add action listener
		listToTable.addActionListener(
				
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event) 
					{
						String[] restaurantArray = completeList().split("\\s*,\\s*");
						tableModel.insertRow(tableModel.getRowCount(), restaurantArray);
						listModel.remove(list.getSelectedIndex());
						clearFields();
						listToTable.setEnabled(false);
						tableToList.setEnabled(false);
						remove.setEnabled(false);
					}
				}
			);
        
		
		tableToList.setAlignmentY(Component.CENTER_ALIGNMENT);
		topCenterPane.add(tableToList);
		tableToList.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event) 
					{
						listModel.addElement(completeList());
						tableModel.removeRow(table.getSelectedRow());
						clearFields();
						tableToList.setEnabled(false);
						listToTable.setEnabled(false);
						remove.setEnabled(false);
					}
				}
			);

		topPane.add(topCenterPane, BorderLayout.CENTER);
		
		// add listener to table
		table.addMouseListener(
				new MouseListener()
				{
					public void mouseClicked(MouseEvent event) 
					{
						remove.setEnabled(true);
						change.setEnabled(true);
						listToTable.setEnabled(false);
						tableToList.setEnabled(true);
						
						// enter data to fields
						for (int i=0; i<table.getColumnCount(); i++)
							fields[i].setText((String) tableModel.getValueAt(table.getSelectedRow(), i));
					}
					
					public void mouseEntered(MouseEvent event) 
					{
			
					}

					public void mouseExited(MouseEvent event) 
					{
						
					}

					public void mousePressed(MouseEvent event) 
					{
						
					}

					public void mouseReleased(MouseEvent event) 
					{
						
					}	
				}
			);
		
		topPane.add ( new JScrollPane( table), BorderLayout.EAST );

		add(topPane, BorderLayout.NORTH);
		
		// initialize panel for fields
		JPanel centralCenterPane = new JPanel ();
		
		// initialize central panel of container
		JPanel centerPane = new JPanel ();
		
		centerPane.setLayout(new BorderLayout());
	    
		// initialize labelPanel and fieldPanel
		JPanel labelPanel = new JPanel(new GridLayout(columnNames.length, 1));
	    JPanel fieldPanel = new JPanel(new GridLayout(columnNames.length, 1));
	    
	    // add labelPanel and fieldPanel to center pane
	    centerPane.add(labelPanel, BorderLayout.WEST);
	    centerPane.add(fieldPanel, BorderLayout.CENTER);
	    
	    // initialize fields
	    fields = new JTextField[columnNames.length];

	    for (int i = 0; i<columnNames.length; i++) 
	    {
	      fields[i] = new JTextField();
	      
	      // set width of fields
	      fields[i].setColumns(30);
	      
	      // instantiate labels for fields
	      JLabel labels = new JLabel(columnNames[i], JLabel.RIGHT);
	      labels.setLabelFor(fields[i]);
	      labelPanel.add(labels);
	      JPanel flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	      flowPanel.add(fields[i]);
	      fieldPanel.add(flowPanel);
	    }
	  
		// add fields panel to the center panel
		centralCenterPane.add(centerPane, BorderLayout.CENTER);
		centerPane.setBackground(Color.BLUE);
		
		// add center panel to container
		add(centralCenterPane, BorderLayout.CENTER);
		
		// instantiate bottom pane housing add, change and remove buttons
		JPanel bottomPane = new JPanel ();
		
		// instantiating the add, change and remove buttons
		add = new JButton("Add");
		remove = new JButton("Remove");
		change = new JButton("Change");
		
		change.setEnabled(false);
		remove.setEnabled(false);
		
		bottomPane.add (add, BorderLayout.WEST);
	
		add.addActionListener(
				
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event) 
				{
					int count = 0;
					
					for (int i=0; i<columnNames.length; i++)
					{
						if (fields[i].getText().equals(""))
							count++;
					}
					
					// Display error if any of the fields is empty
					if (count > 0)
						JOptionPane.showMessageDialog( null, "Please fill in all the fields", 
								"Error Message", JOptionPane.ERROR_MESSAGE);
					
					else
					{
					
						// add line to list
						listModel.addElement(completeList());
					
						clearFields();
						remove.setEnabled(false);
						listToTable.setEnabled(false);
						tableToList.setEnabled(false);
					}
				}
				
			}
		);
		
		bottomPane.add (change, BorderLayout.CENTER);
		
		change.addActionListener(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent event) 
				{
					int count = 0;
					
					for (int i=0; i<columnNames.length; i++)
					{
						if (fields[i].getText().equals(""))
							count++;
					}
					
					// Display error if any of the fields is empty
					if (count > 0)
						JOptionPane.showMessageDialog( null, "Please fill in all the fields", 
								"Error Message", JOptionPane.ERROR_MESSAGE);
					
					else
					{
						// Change list
						if (!list.isSelectionEmpty())
							listModel.set(list.getSelectedIndex(), completeList());
						
						// Change table
						else if (table.isRowSelected(table.getSelectedRow()))
						{
							String[] restaurantArray = completeList().split("\\s*,\\s*");
							for (int i=0; i<table.getColumnCount(); i++)
								tableModel.setValueAt(restaurantArray[i], table.getSelectedRow(), i);
						}
					
					}
				}	
			}
		);
		
		bottomPane.add (remove, BorderLayout.EAST);
		
		remove.addActionListener(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent event) 
				{
					if (!list.isSelectionEmpty())
					{
						int index = list.getSelectedIndex();
						System.out.println(index);
						listModel.remove(index);
		
						int size = listModel.getSize();
		
						if (size == 0) 
						{ 
							// nothing left to remove
							remove.setEnabled(false);
		
						} 
						else 
						{ 
							// select an index from the DefaultListModel
							if (index == listModel.getSize()) 
							{
								// removed item in last position
								index--;
							}
		
				        list.setSelectedIndex(index);
				        list.ensureIndexIsVisible(index);
						}
					}
					
					else if (table.isRowSelected(table.getSelectedRow()))
					{
						tableModel.removeRow(table.getSelectedRow());
						int index = table.getSelectedRow();
						int size = listModel.getSize();
						
						if (size == 0) 
						{ 
							// Nothing left to remove
							remove.setEnabled(false);
						} 
						else 
						{ 
							//Select an index from the DefaultListModel
							if (index == listModel.getSize()) 
							{
								//removed item in last position
								index--;
							}
						}
					}
					
					clearFields();
					listToTable.setEnabled(false);
					tableToList.setEnabled(false);
				}	
			
			}
		);
		add(bottomPane, BorderLayout.SOUTH);
	}
	
	// output text at given index
	public String getText(int i) 
	{
	    return (fields[i].getText());
	}
	
	// output field data in list format
	public String completeList()
	{
		String text = "";
		
		for (int i=0; i<columnNames.length; i++)
		{
			if (i<columnNames.length-1)
				text += getText(i) + ", ";
			else
				text += getText(i);
		}
		return text;
	}
	
	// method to clear the data in fields
	public void clearFields()
	{
		for (int i=0; i<columnNames.length; i++)
			fields[i].setText("");
	}
	
	// Shows list contents in fields
	public void showListInField() 
	{
		if (list.getSelectedIndex() >= 0)
		{
			String text = (String) listModel.get(list.getSelectedIndex());

			String[] restaurantArray = text.split("\\s*,\\s*");
			for (int i=0; i<columnNames.length; i++)
				fields[i].setText(restaurantArray[i]);
		}
	}
	
	// display table data in fields
	public void showTableInField() 
	{
		if (list.getSelectedIndex() >= 0)
		{
			String text = (String) listModel.get(list.getSelectedIndex());

			String[] restaurantArray = text.split("\\s*,\\s*");
			for (int i=0; i<columnNames.length; i++)
				fields[i].setText(restaurantArray[i]);
		}
				
	}
}
