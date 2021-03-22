import java.awt.*;
import javax.swing.*;

/**
 * This class creates a 'Person' entity and its GUI components.
 * This class extends {@link ClubAbstractEntity} 
 */
public class Person extends ClubAbstractEntity
{
	private String ID,First_Name,Sur_Name,Tel;
	private  String[] LabelsNames_Array= {"ID","First Name","SurName","Telephone"};
	private JLabel[] Labels_Array;
	private JTextField[] Text_Fields_Array;
	private JPanel PersonPanel; // Specific panel for the GUI elements of the 'Person'-part of the entity 
	private JLabel[] Astrics_Array;

	/**
	 * An empty-Person-constructor that initializes GUI elements and 
	 * listeners, with auxiliary of private methods.
	 */
	public Person()
	{
		CreateComponents();
		SetGeneralSettings();
		AddElements();
		setVisible(true);
	}
	
	/**
	 * Person Constructor - initializes all the instance variable
	 * @param ID the person's unique ID.
	 * @param FirstName the person's First name.
	 * @param SurName the person's Surname.
	 * @param TelNo the person's telephone number.
	 */
	public Person(String ID, String FirstName, String SurName,String TelNo)
	{
		this.ID=ID;
		this.First_Name=FirstName;
		this.Sur_Name=SurName;
		this.Tel=TelNo;
	}
	
	/**
	 * Creates the GUI elements - all the TextFields, Labels and the panel to hold them. 
	 */
	private void CreateComponents() 
	{
		PersonPanel=new JPanel();
		Labels_Array=new JLabel[LabelsNames_Array.length];
		Text_Fields_Array=new JTextField[Labels_Array.length];
		Astrics_Array=new JLabel[Text_Fields_Array.length];
		for(int i=0;i<LabelsNames_Array.length;i++)
		{
			Labels_Array[i]=new JLabel(LabelsNames_Array[i],JLabel.RIGHT);
			Text_Fields_Array[i]=new JTextField(25);
			Astrics_Array[i]=new JLabel("*");
			Astrics_Array[i].setForeground(Color.red);
			Astrics_Array[i].setVisible(false);
		}
	}
	
	/**
	 * Sets different settings of different GUI elements, as well as defining the LayoutManager of the panel that
	 * holds the GUI elements.
	 */
	private void SetGeneralSettings() 
	{
		this.setTitle("Person clubber's Data");
        this.setSize(450, 220);
        this.PersonPanel.setLayout(new GridLayout(5, 3, 8, 12));
        this.setLocationRelativeTo(null);
	}
	
	/**
	 * Adds the GUI elements to the panel, as well as adding the panel to the main frame.
	 */
	private void AddElements() 
	{
		for(int i=0;i<LabelsNames_Array.length;i++)
		{
			PersonPanel.add(Labels_Array[i]);
			PersonPanel.add(Text_Fields_Array[i]);
			PersonPanel.add(Astrics_Array[i]);
		}
		this.addToCenter(PersonPanel);
	}

	/**
	 * Adds an element to the panel that holds the GUI elements.
	 * This method is for the use of the sub-classes of 'Person' class.
	 * @param obj the component to be added
	 */
	protected void AddElementToPersonPanel(Component obj)
	{
		this.PersonPanel.add(obj);
	}
	
	/**
	 * Overridden method that checks if a given key is equals to the clubber's key.
     * @param key is a given key to match.
     * @return boolean answer - found or not.
	 */
	@Override
	public boolean match(String key) 
	{		
		 if(key.equals(ID))		
		{
			return true;
		}
		return false;		
	}

	/**
	 * Overridden method that validates the data entered by the user in the TextFields, based on the Regular-Expressions of the class.
	 * This method also deals with the validation of a Unique ID.
	 * Note the use of the method: {@link Manager#What_CHeck_To_Use()} and the method: {@link Manager#Check_ID(String)}
	 * Which both determine whether a Unique-ID check is needed or not.
	 * Note the use of the method: {@link #Check_TextFields()} that checks that the entered input in the 
	 * Textfields are correct based on the Defined Regular-Expressions.
	 * @return boolean answer - validation of the data based on the regular-expressions successes or not,
	 * AND also the entered ID is unique and doesn't already exists in the DB. 
	 */
	@Override
	protected boolean validateData() 
	{
		boolean Type=Manager.What_CHeck_To_Use();		
		boolean TextFields_OK=Check_TextFields();
		if(TextFields_OK==false) // First Check - TextFields Are OK/Not-OK By The Regex
		{
			return false;
		}
		else if(TextFields_OK==true && Type==false) // Second Check - TextFields Are OK And A Unique-ID-Check Is Needed 
		{	
			if(Manager.Check_ID(Text_Fields_Array[0].getText())==false) // Same ID Already Exists
			{
				String Messege="A Clubber With ID: " +"'"+Text_Fields_Array[0].getText()+"' "+"Already Exists!";
				JOptionPane.showMessageDialog(null, Messege, "Error", JOptionPane.INFORMATION_MESSAGE);				
				return false;
			}
			else // Same ID Does Not Exists
			{
				return true;
			}
		}
		else if(TextFields_OK==true && Type==true) // Third Check - TextFields Are OK And A Unique-ID-Check Is Not Needed
		{
			return true;
		}
		return false;
	}

	/**
	 * Overridden method that simply saves the data in the text-fields into the Object's instance variables,
	 * based on whether the validation was successful or not.  
	 */
	@Override
	protected void commit() 
	{
		this.ID=Text_Fields_Array[0].getText();
		this.First_Name=Text_Fields_Array[1].getText();
		this.Sur_Name=Text_Fields_Array[2].getText();
		this.Tel=Text_Fields_Array[3].getText();	
	}

	/**
	 * Overridden method that simply restores the data in the Object's instance variables into the text-fields.
	 */
	@Override
	protected void rollBack() 
	{
		Text_Fields_Array[0].setText(this.ID);
		Text_Fields_Array[1].setText(this.First_Name);
		Text_Fields_Array[2].setText(this.Sur_Name);
		Text_Fields_Array[3].setText(this.Tel);	
		ClearAstrics();
	}
	
	/**
	 * Checks whether the data entered in the TextFields is correct, based on the Regular-Expressions defined in the class.
	 * @return boolean answer - Data entered is correct or not.
	 */
	private boolean Check_TextFields()
	{
		boolean[] Fields_Answers=new boolean[Text_Fields_Array.length];
		String[] Regex_Array= {"\\d{1}\\-\\d{7}\\|[1-9]","[A-Z][a-z]+","([A-Z][a-z]*['-]?[A-Z]?[a-z]*)+","\\+\\([1-9]\\d{0,2}\\)[1-9]\\d{0,2}\\-[1-9]\\d{6}"};
		for(int i=0;i<Text_Fields_Array.length;i++)
		{
			if(!(Text_Fields_Array[i].getText().matches(Regex_Array[i])))
			{
				Astrics_Array[i].setVisible(true);
				Fields_Answers[i]=false;
			}
			else
			{
				Astrics_Array[i].setVisible(false);
				Fields_Answers[i]=true;
			}
		}
		for(int i=0;i<Fields_Answers.length;i++)
		{
			if(!Fields_Answers[i])
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Clears the astrics that represents a mistaken input in the TextFields.
	 */
	protected void ClearAstrics()
	{
		for(JLabel A:Astrics_Array)
		{
			A.setVisible(false);
		}
	}	
}
