import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import javax.swing.*;
@SuppressWarnings("serial")

/**
 * This class is an abstract class that holds the basic GUI elements for every 'clubber' entity.
 * This class extends {@link javax.swing.JFrame}, and sets the handling and behavior for {@link OK_Button} and {@link Cancel_Button}.
 * This class contains the private class {@link ButtonsHandler}
 */
public abstract class ClubAbstractEntity extends JFrame  implements Serializable
{
	private JPanel Buttons_Panel // JPanel to contain the "OK" and "Cancel" Buttons
								,Center_Panel; // JPanel to contain the GUI elements for every inherited class
	private JButton OK_Button,Cancel_Button;	
	private ButtonsHandler handler;
	
    /**
     * ClubAbstractEntity Constructor - initializes all the GUI elements and adding them to the main Frame.
    */
	public ClubAbstractEntity()
	{				
		Buttons_Panel=new JPanel();
		Center_Panel=new JPanel();
		OK_Button=new JButton("OK");
		Cancel_Button=new JButton("Cancel");
		handler=new ButtonsHandler();
		
		Buttons_Panel.add(OK_Button);
		Buttons_Panel.add(Cancel_Button);
		this.add(Buttons_Panel,BorderLayout.SOUTH);
		this.add(Center_Panel);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		Cancel_Button.setEnabled(false);
		
		this.OK_Button.addActionListener(handler);
		this.Cancel_Button.addActionListener(handler);
	}
		
	 /**
     * This method is responsible for adding the GUI Components to the Center-Panel which positioned in the center of the Frame. 
     * @param comp the Component to be added.
     */
	protected void addToCenter(Component comp)
	{
		this.Center_Panel.add(comp,BorderLayout.CENTER);
	}

    /**
    * Abstract method that checks if a given key is equals to the clubber's key.
    * @param key is a given key to match.
    * @return boolean answer - found or not.
    */
	public abstract boolean match(String key);
	
	/**
	 * Abstract method that validates the data entered by the user in the TextFields, based on the Regular-Expressions Defined for every sub-class.
	 * @return boolean answer - validation successes or not.
	 */
	protected abstract boolean validateData();
	
	/**
	 * Abstract method that simply saves the data in the text-fields into the Object's instance variables,
	 * based on whether the validation was successful or not.  
	 */
	protected abstract void commit();
	
	/**
	 * Abstract method that simply restores the data in the Object's instance variables into the text-fields.
	 */
	protected abstract void rollBack();

    /**
     * This is a private inner class inside {@link ClubAbstractEntity} for the OK and Cancel buttons event handler
     * That implements the interface of ActionListener and Serializable
     */
	private class ButtonsHandler implements ActionListener,Serializable
	{
		@Override
		public void actionPerformed(ActionEvent ev)
		{
			Object source=ev.getSource();
			if(source==OK_Button)
			{
				if(validateData())
				{
					commit();
					setVisible(false);
					Cancel_Button.setEnabled(true);
				}
			}
			
			else if(source==Cancel_Button)
			{
				rollBack();
				setVisible(false);
			}
		}				
	}	
}


