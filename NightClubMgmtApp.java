import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * This class creates the main Application that runs the menus and processing the user actions.
 */
public class NightClubMgmtApp 
{		
	private JFrame Main_Frame;
	private JPanel Parent_Panel,Main_Menu_Panel,Add_Clubbers_Panel;
	private JButton[] MainMenu_Btn_Array;
	private JButton[] AddClubbers_Btn_Array;
	private JLabel Clubbers_Count;
	private ArrayList<ClubAbstractEntity> clubbers;
	private String Input_Key="";	
	private CardLayout c1=new CardLayout();
	private boolean What_Check; // true = No Need To Check Unique-ID (For The Search Option)
								// false = Need To Check Unique-ID (For The Add Option)
	
	/**
	 * NightClubMgmtApp constructor - initializes all the panels and all the GUI elements, with
	 * auxiliary of private methods.
	 */
	public NightClubMgmtApp() 
	{				
		loadClubbersDBFromFile();
		Main_Frame=new JFrame("B.K Management App");		
		Main_Frame.setSize(500, 550);
		
		Create_Main_Menu_Panel();
		Create_Add_Clubber_Panel();
		Create_Parent_Panel();
		Main_Frame.add(Parent_Panel);
		
		clubbers=new ArrayList<>();
		
		Main_Frame.setResizable(false);
		Main_Frame.setLocationRelativeTo(null);
		Main_Frame.setVisible(true);
		Main_Frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
		Update_Count_Label();
	}
	
	/**
	 * Creates the Parent-panel that holds all the different menu-panels,and
	 *  adds them to the Parent-panel.
	 */
	private void Create_Parent_Panel() 
	{		
		Parent_Panel=new JPanel();
		Parent_Panel.setLayout(c1);
		Parent_Panel.add("1",Main_Menu_Panel );
		Parent_Panel.add("2",Add_Clubbers_Panel );
		c1.show(Parent_Panel, "1");
	}	
	
	/**
	 * Creates the Main-Menu-panel and all its components: Buttons, Labels etc.
	 * This method also positioning and setting different settings of the different GUI elements.
	 */
	private void Create_Main_Menu_Panel() 
	{
		Color TOP_BLUE=new Color(70,130,180);
		Color BACKGROUND_BLUE=new Color(135,206,250);
		Font F1_PLAIN=new Font("Rockwell",Font.PLAIN,18);
		Main_Menu_Panel_Handler handler=new Main_Menu_Panel_Handler();
		
		String[] MainMenu_BtnNames_Array= {"Search","Add","Remove","Exit"}; 
		MainMenu_Btn_Array=new JButton[MainMenu_BtnNames_Array.length];
		
		Main_Menu_Panel=new JPanel();
		Main_Menu_Panel.setLayout(null);
		Main_Menu_Panel.setBackground(BACKGROUND_BLUE);
				
		JLabel Main_Menu_Top_Label=Create_Costume_Label("B.K Management App",JLabel.CENTER,36);						
		Main_Menu_Panel.add(Main_Menu_Top_Label);
					
		int Btn_X_Start=Main_Frame.getWidth()/2-90,Btn_Y_Start=Main_Menu_Top_Label.getHeight()+30,Btn_Width=180,Btn_Height=50;				
		for(int i=0;i<MainMenu_Btn_Array.length;i++) // Creating,Adding,Setting Colors,Fonts And ToolTips And Adding Listeners To the 'Main-Menu-Panel' Buttons
		{
			MainMenu_Btn_Array[i]=new JButton(MainMenu_BtnNames_Array[i]); // Creating
			
			Main_Menu_Panel.add(MainMenu_Btn_Array[i]); // Adding
			
			MainMenu_Btn_Array[i].setBounds(Btn_X_Start, Btn_Y_Start+i*80, Btn_Width, Btn_Height); // Positioning
			
			MainMenu_Btn_Array[i].setFont(F1_PLAIN); // Setting Font
			MainMenu_Btn_Array[i].setBackground(TOP_BLUE); // Setting Colors
			MainMenu_Btn_Array[i].setForeground(Color.white);
			
			MainMenu_Btn_Array[i].addActionListener(handler); // Adding ActionsListener	
			
			if(i==MainMenu_Btn_Array.length-1) // Handling the "Exit" button's ToolTip exclusively
			{
				MainMenu_Btn_Array[i].setToolTipText(MainMenu_BtnNames_Array[i] + " " + "The Program");
				break;
			}
			MainMenu_Btn_Array[i].setToolTipText(MainMenu_BtnNames_Array[i] + " " + "A Clubber"); // Setting ToolTips			
		}

		
		Clubbers_Count=Create_Costume_Label("Registered Clubbers: ",JLabel.CENTER,18);
		Main_Menu_Panel.add(Clubbers_Count);
		Clubbers_Count.setBounds(0, MainMenu_Btn_Array[MainMenu_Btn_Array.length-1].getY()+80, 500, 40);
	}

	/**
	 * Creates the Add-Clubber-panel and all its components: Buttons, Labels etc.
	 * This method also positioning and setting different settings of the different GUI elements.
	 */
	private void Create_Add_Clubber_Panel() 
	{
		Color TOP_BLUE=new Color(70,130,180);
		Color BACKGROUND_BLUE=new Color(135,206,250);
		Font F1_PLAIN=new Font("Rockwell",Font.PLAIN,18);
		Add_Clubbers_Panel_Handler handler=new Add_Clubbers_Panel_Handler();
		
		String[] AddClubbers_BtnNames_Array= {"Person","Soldier","Student","Return"};
		AddClubbers_Btn_Array=new JButton[AddClubbers_BtnNames_Array.length];
		
		Add_Clubbers_Panel=new JPanel();
		Add_Clubbers_Panel.setLayout(null);
		Add_Clubbers_Panel.setBackground(BACKGROUND_BLUE);
			
		JLabel Add_Clubber_Top_Label=Create_Costume_Label("Add Clubber Menu",JLabel.CENTER,36);						
		Add_Clubbers_Panel.add(Add_Clubber_Top_Label);						
		
		int Btn_X_Start=Main_Frame.getWidth()/2-90,Btn_Y_Start=Add_Clubber_Top_Label.getHeight()+30,Btn_Width=180,Btn_Height=50;				
		for(int i=0;i<AddClubbers_Btn_Array.length;i++) // Creating,Adding,Setting Colors,Fonts And ToolTips And Adding Listeners To the 'Add-Clubber-Panel' Buttons
		{
			AddClubbers_Btn_Array[i]=new JButton(AddClubbers_BtnNames_Array[i]); // Creating
			
			Add_Clubbers_Panel.add(AddClubbers_Btn_Array[i]); // Adding
			
			AddClubbers_Btn_Array[i].setBounds(Btn_X_Start, Btn_Y_Start+i*80, Btn_Width, Btn_Height); // Positioning
			
			AddClubbers_Btn_Array[i].setFont(F1_PLAIN); // Setting Font
			AddClubbers_Btn_Array[i].setBackground(TOP_BLUE); // Setting Colors
			AddClubbers_Btn_Array[i].setForeground(Color.white);
			
			AddClubbers_Btn_Array[i].addActionListener(handler); // Adding ActionsListener
			
			if(i==AddClubbers_Btn_Array.length-1) // Handling the "Return" button's ToolTip exclusively
			{
				AddClubbers_Btn_Array[i].setToolTipText("Return To Main Menu");
				break;
			}
			AddClubbers_Btn_Array[i].setToolTipText("Add A Clubber Of Type "+"'"+AddClubbers_BtnNames_Array[i]+"'"); // Setting ToolTips						
		}
	}

	/**
	 * Creates A label that shows in the top of every panel.
	 * @param Label_TXT - the text to be shown in the Label.
	 * @param Alignment - the Alignment to be defined for the Label.
	 * @return the JLabel component created.
	 */
	private JLabel Create_Costume_Label(String Label_TXT,int Alignment,int TextSize)
	{
		Color TOP_BLUE=new Color(70,130,180);
		JLabel Top_Label=new JLabel(Label_TXT,Alignment);
		Top_Label.setOpaque(true);
		Top_Label.setBackground(TOP_BLUE);
		Top_Label.setForeground(Color.white);
		Top_Label.setBounds(0, 0, 500, 120);
		Top_Label.setFont(new Font("Rockwell",Font.BOLD,TextSize));
		return Top_Label;
	}
	
	/**
	 * This method search for a match based on a given key from the user.
	 * Note the use of the method 'match' that activating the specific 'match' method based on
	 * the type to the 'clubber' .
	 */
	private void manipulateDB()
	{
		boolean found = false;	
		for(ClubAbstractEntity clubber : clubbers)
		{
			if(clubber.match(Input_Key))
			{
				found = true;
				clubber.setVisible(true);
				break;
			}
		}
		if(!found)
		{
			JOptionPane.showMessageDialog(null, "Clubber with key: " + Input_Key + " Does not Exists","Information",JOptionPane.INFORMATION_MESSAGE);	        
		}
		else
		{
			found = !found;			
		}
		
	}// End of method manipulateDB
	
	@SuppressWarnings("unchecked")
	/**
	 * Loads the data from the file and creates the corresponding objects and put them into
	 * the 'clubbers' ArrayList.
	 */
	private void loadClubbersDBFromFile()
	{
		try
		{
			FileInputStream File_IS = new FileInputStream("BKCustomers.dat");
			ObjectInputStream Object_IS = new ObjectInputStream(File_IS);
			clubbers = (ArrayList<ClubAbstractEntity>) Object_IS.readObject();
			File_IS.close();
			Object_IS.close();
		}
		catch(FileNotFoundException Fexc)
		{
			JOptionPane.showMessageDialog(null,"No Clubbers In The DB Yet, Press OK To Continue To Main Menu");
		}
		catch(IOException Iexc)
		{
			//
		}
		catch(ClassNotFoundException Cexc)
		{
			//
		}			
	}	
		
//		clubbers.add(new Person("0-2423535|1", "Mark", "Mc'Cormic","+(1)4-9520205"));			
//		clubbers.add(new Soldier("0-2223335|1", "Zohar", "Couper-Berg","+(44)206-8208167", "O/4684109"));
//		clubbers.add(new Student("2-5554445|3", "Avi", "Avrahami-O'Mally","+(972)50-6663210", "SCE/12345"));

	/**
	 * Writes the data in the 'clubbers' ArrayList into the file.
	 */
	private void writeClubbersDBtoFile()
	{		
		if(!(clubbers.size()==0))
		{
			try 
			{
				FileOutputStream fileOut = new FileOutputStream("BKCustomers.dat");
				ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
				objectOut.writeObject(clubbers);
				objectOut.close();
				fileOut.close();
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}		
	}

	/**
	 * A private inner class for handling the Main-Menu options, that is: 'Search', 'Add','Remove' and 'Exit'.
	 * This class implements the interface of ActionListener.
	 */
	private class Main_Menu_Panel_Handler implements ActionListener
	{
    	/**
    	* Overridden method for defining the actions to be performed  for the 'Search', 'Add', 'Remove' and 'Exit' buttons.
    	*/
		@Override
		public void actionPerformed(ActionEvent ev) 
		{						
			Object source=ev.getSource();
			if(source==MainMenu_Btn_Array[0]) // Search
			{
				Input_Key = JOptionPane.showInputDialog(Main_Menu_Panel, "Please Enter The Clubber's Key ", Input_Key);
                if (Input_Key == null)
                {
                	JOptionPane.showMessageDialog(Main_Menu_Panel, "Operation Canceled");
                }
                else if(Input_Key.isEmpty())
                {
                	JOptionPane.showMessageDialog(Main_Menu_Panel, "No Key Entered!", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                	What_Check=true;
                    manipulateDB();
                }
			}
						
			else if(source==MainMenu_Btn_Array[1]) // Add
			{
				c1.show(Parent_Panel, "2");
			}
			
			else if(source==MainMenu_Btn_Array[2]) // Remove
			{
				Remove_Clubber();
			}
			
			else if(source==MainMenu_Btn_Array[3]) // Exit
			{
				writeClubbersDBtoFile();
				System.exit(0);
			}
			Input_Key="";
		}		
	}
	
	/**
	 * A private inner class for handling the Add-Clubber options, that is the options to create a clubber of type:
	 * 'Person', 'Soldier' or 'Student'.
	 * This class implements the interface of ActionListener.
	 */
	private class Add_Clubbers_Panel_Handler implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent ev) 
		{		
			Object source = ev.getSource();
			What_Check=false;
			if(source==AddClubbers_Btn_Array[0]) // Person
			{
				clubbers.add(new Person()); // Create an empty 'Person' Object, Real Details Will Be Updated By The User
				Update_Count_Label();
			}
			else if(source==AddClubbers_Btn_Array[1]) // Soldier
			{
				clubbers.add(new Soldier()); // Create an empty 'Soldier' Object, Real Details Will Be Updated By The User
				Update_Count_Label();
			}
			else if(source==AddClubbers_Btn_Array[2]) // Student
			{
				clubbers.add(new Student()); // Create an empty 'Student' Object, Real Details Will Be Updated By The User
				Update_Count_Label();
			}
			else if(source==AddClubbers_Btn_Array[3]) // 'Return' Button
			{
				c1.show(Parent_Panel, "1");
			}			

		}		
	}	

	/**
	 * Remove a Clubber from the DB.
	 */
	public void Remove_Clubber()
	{		
		String Key = JOptionPane.showInputDialog(Main_Menu_Panel, "Please Enter The Clubber's Key To Be Removed ", Input_Key);
			
        if (Key == null)
        {
        	JOptionPane.showMessageDialog(Main_Menu_Panel, "Operation Canceled");
        }
        else if(Key.isEmpty())
        {
        	JOptionPane.showMessageDialog(Main_Menu_Panel, "No Key Entered!", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
        	ClubAbstractEntity entity = null;
        	boolean found = false;
        	for(ClubAbstractEntity clubber:clubbers)
        	{
        		if(clubber.match(Key))
        		{
        			found = true;
        			entity=clubber;
        			clubber.setVisible(true);
        			break;	
        		}
        	}
        	if(!found)
    		{
    			JOptionPane.showMessageDialog(null, "Clubber with key: " + Input_Key + " Does not Exists","Information",JOptionPane.INFORMATION_MESSAGE);	        
    		}
        	else 
			{
        		if(JOptionPane.showConfirmDialog(null,"This Action Will Delete The Clubber's Information From The Data-Base, Confirm?","Warning", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        		{
        			clubbers.remove(entity);
        			entity.setVisible(false);
        			Update_Count_Label();
        		}
        		else
        		{
        			What_Check=true;
        		}
        		found = !found;	
			}
		}       	
       }

	
	/**
	 * Update the Bottom-Label in the Main-Menu-Panel that shows how many registered clubbers there are in the DB.
	 */
	private void Update_Count_Label()
	{
		Clubbers_Count.setText("Registered Clubbers: "+clubbers.size());
	}
	
	/**
	 * Checks if a given ID already exists in the DB, that is the 'clubbers' ArrayList.
	 * @param ID_TO_CHECK - the ID to check.
	 * @return boolean answer - false if the given ID already exists in the DB, true if the given ID does not exists in the DB.
	 */
	public boolean Check_Unique_ID(String ID_TO_CHECK)
	{
		for(ClubAbstractEntity clubber : clubbers)
		{
			if(clubber.match(ID_TO_CHECK))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Determines whether a Unique-ID-Check is needed or not.
	 * @return the check type - true if a Unique-ID-Check is not needed, false otherwise.
	 */
	public boolean What_Check_Type()
	{
		return What_Check;
	}					
}//End of class NightClubMgmtApp
