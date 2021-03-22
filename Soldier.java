import javax.swing.*;
import java.awt.*;

/**
 * This class creates a 'Soldier' entity and its GUI components.
 * This class extends {@link Person} 
 */
public class Soldier extends Person
{
    private String personalNum;
    private JLabel soldier_personalNum_astricslabel;
    private JTextField soldier_personalNum_textField;
   
	/**
	 * An empty-Soldier-constructor that initializes GUI elements and 
	 * listeners, with auxiliary of private methods.
	 */
    public Soldier()
    {
    	// A 'Person' Empty-Constructor Is Implicitly Called Here //
    	
        createAndAddElements();
        this.setSize(460, 250);
        this.setTitle("Soldier Clubber's Data");
    }
    
	/**
	 * Soldier Constructor - initializes all the instance variable
	 * @param ID the soldiers's unique ID.
	 * @param FirstName the soldiers's First name.
	 * @param SurName the soldiers's Surname.
	 * @param TelNo the soldiers's telephone number.
	 * @param personalNum the soldiers's personal number.
	 */
    public Soldier(String ID, String FirstName, String SurName,String TelNo, String personalNum)
    {
        super(ID, FirstName, SurName, TelNo);
        this.personalNum=personalNum;     
    }

    /**
     * Creates the GUI elements - the TextFields and Labels.
     * Note the use of the method: {@link Person#AddElementToPersonPanel(Component)}} that simply 
     * adds the given element to the panel.
     */
    private void createAndAddElements() 
    {
        soldier_personalNum_textField=new JTextField(25);
        soldier_personalNum_astricslabel=new JLabel("*");
        this.soldier_personalNum_astricslabel.setForeground(Color.RED);
        this.soldier_personalNum_astricslabel.setVisible(false);
        AddElementToPersonPanel(new JLabel("Personal No.", JLabel.RIGHT));
        AddElementToPersonPanel(soldier_personalNum_textField);
        AddElementToPersonPanel(soldier_personalNum_astricslabel);
    }

	/**
	 * Overridden method that checks if a given key is equals to the clubber's key.
	 * This method checks for a match not only by ID but also by peronal number.
     * @param key is a given key to match.
     * @return boolean answer - found or not.
	 */
    @Override
    public boolean match(String key)
    {  	
        if(super.match(key) || key.equals(personalNum))
            return true;
        return false;
    }

    /**
     * Overridden method that validates the data entered by the user in the TextFields, based on the Regular-Expressions of the class.
     * @return boolean answer - validation of the data based on the regular-expressions successes or not.
     */
    @Override
    protected boolean validateData()
    {
        String s="[ROC]\\/[1-9]\\d{6}";
        if((!super.validateData())||!(soldier_personalNum_textField.getText().matches(s))) 
        {
        	if(soldier_personalNum_textField.getText().matches(s))
        		soldier_personalNum_astricslabel.setVisible(false);
        	soldier_personalNum_astricslabel.setVisible(true);
            return false;
        }
        else
        	soldier_personalNum_astricslabel.setVisible(false);
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
        this.personalNum= soldier_personalNum_textField.getText();
        soldier_personalNum_textField.setText(personalNum);
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
        soldier_personalNum_textField.setText(this.personalNum);
        soldier_personalNum_astricslabel.setVisible(false);
    }

}
