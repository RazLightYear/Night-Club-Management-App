import javax.swing.*;
import java.awt.*;

/**
 * This class creates a 'Student' entity and its GUI components.
 * This class extends {@link Person} 
 */
public class Student extends Person
{
    private String studentID;
    private JLabel student_studentID_astricslabel;
    private JTextField student_studentID_textField;
   
	/**
	 * An empty-Student-constructor that initializes GUI elements and 
	 * listeners, with auxiliary of private methods.
	 */
    public Student()
    {
    	// A 'Person' Empty-Constructor Is Implicitly Called Here //
    	createAndAddElements();
        this.setSize(460, 250);
        this.setTitle("Student Clubber's Data");
    }
    
	/**
	 * Student Constructor - initializes all the instance variable
	 * @param ID the student's unique ID.
	 * @param FirstName the student's First name.
	 * @param SurName the student's Surname.
	 * @param TelNo the student's telephone number.
	 * @param studentID the student's Student-ID.
	 */
    public Student(String ID, String FirstName, String SurName,String TelNo, String studentID)
    {
        super(ID, FirstName, SurName, TelNo);
        this.studentID=studentID;
    }

    /**
     * Creates the GUI elements - the TextFields and Labels.
     * Note the use of the method: {@link Person#AddElementToPersonPanel(Component)}} that simply 
     * adds the given element to the panel.
     */
    private void createAndAddElements() 
    {
        student_studentID_textField=new JTextField(25);
        student_studentID_astricslabel=new JLabel("*");
        this.student_studentID_astricslabel.setForeground(Color.RED);
        this.student_studentID_astricslabel.setVisible(false);
        AddElementToPersonPanel(new JLabel("Student ID", JLabel.RIGHT));
        AddElementToPersonPanel(student_studentID_textField);
        AddElementToPersonPanel(student_studentID_astricslabel); 
    }

	/**
	 * Overridden method that checks if a given key is equals to the clubber's key.
	 * This method checks for a match not only by ID but also by Student-ID.
     * @param key is a given key to match.
     * @return boolean answer - found or not.
	 */
    @Override
    public boolean match(String key)
    {
        if(super.match(key))
            return true;
        else if(this.studentID!=null)
        {
        	try
        	{
        		String s=studentID.substring(4);
        		if(s.equals(key))
        		{
        			return true;
        		}
        	}
        	catch(IndexOutOfBoundsException Exc)
        	{
        		System.err.println("Error in Student Class. Could not execute the command : 'substring'");
        		Exc.printStackTrace();
        	}
        }
        return false;

    }

    /**
     * Overridden method that validates the data entered by the user in the TextFields, based on the Regular-Expressions of the class.
     * @return boolean answer - validation of the data based on the regular-expressions successes or not.
     */
    @Override
    protected boolean validateData()
    {
        String s="[A-Z]{3}\\/[1-9]\\d{4}";
        if((!super.validateData())||!(student_studentID_textField.getText().matches(s))) 
        {
        	if(student_studentID_textField.getText().matches(s))
        		student_studentID_astricslabel.setVisible(false);
        	student_studentID_astricslabel.setVisible(true);
            return false;
        }
        else
            student_studentID_astricslabel.setVisible(false);
        return true;
    }

	/**
	 * Overridden method that simply saves the data in the text-fields into the Object's instance variables,
	 * based on whether the validation was successful or not.
	 * Note the use of {@link Person#commit()}} that uses the Polymorphism and the fact that the super-class already 
	 * has its own implementation of the commit function.  
	 */
    @Override
    protected void commit()
    {
        super.commit();
        this.studentID= student_studentID_textField.getText();
    }

	/**
	 * Overridden method that simply restores the data in the Object's instance variables into the text-fields.
	 * Note the use of {@link Person#rollBack()}} that uses the Polymorphism and the fact that the super-class already
	 * has its own implementation of the RollBack function. 
	 */
    @Override
    protected void rollBack()
    {
        super.rollBack();
        student_studentID_textField.setText(this.studentID);
        student_studentID_astricslabel.setVisible(false);
    }
}