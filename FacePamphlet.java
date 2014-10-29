/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init()
	{   loadDataBase();
	    //initialise the canvas
		canvas=new FacePamphletCanvas();
		add(canvas);
		//west area addition
		ChangeStatus=new JTextField(TEXT_FIELD_SIZE);
		add(ChangeStatus,WEST);
		add(new JButton("Change Status"),WEST);
		//space
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		ChangePicture=new JTextField(TEXT_FIELD_SIZE);
		add(ChangePicture,WEST);
		add(new JButton("Change Picture"),WEST);
		//space
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		AddFriend=new JTextField(TEXT_FIELD_SIZE);
		add(AddFriend,WEST);
		add(new JButton("Add Friend"),WEST);
		//north area addition
		add(new JLabel("Name: "),NORTH);
	    Name=new JTextField(TEXT_FIELD_SIZE);
	    add(Name,NORTH);
	    add(new JButton("Add"),NORTH);
	    add(new JButton("Delete"),NORTH);
	    add(new JButton("Lookup"),NORTH);
		//adding listeners
	    Name.addActionListener(this);
		ChangeStatus.addActionListener(this);
		ChangePicture.addActionListener(this);
		AddFriend.addActionListener(this);
		addActionListeners();
    }
	private void loadDataBase()
	{
		DB=new FacePamphletDatabase();
	}
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) 
    {
		String cmd=e.getActionCommand();
		if(cmd.equalsIgnoreCase("Change Status")||e.getSource()==ChangeStatus)
		{
			if(currentProfile==null)
			{
				canvas.removeAll();
				canvas.showMessage("Please select a profile to update the status");
			}
			else
			{
				currentProfile.setStatus(ChangeStatus.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("Status updated to "+currentProfile.getStatus());
			}
		}
		if(cmd.equalsIgnoreCase("Change Picture")||e.getSource()==ChangePicture)
		{
			if(currentProfile==null)
			{
				canvas.removeAll();
				canvas.showMessage("Please select a profile to change picture ");
			}
			else
			{
				GImage image=null;
				try
				{
					image=new GImage(ChangePicture.getText());
				}
				catch(ErrorException ex)
				{
					canvas.showMessage("Unable to open the file name"+ChangePicture.getText());
				}
				if(image!=null)
				{
					currentProfile.setImage(image);
					canvas.displayProfile(currentProfile);
					canvas.showMessage("Picture updated");
				}
			}
		}
		if(cmd.equalsIgnoreCase("Add Friend")||e.getSource()==AddFriend)
		{
			if(currentProfile==null)
			{
				canvas.removeAll();
				canvas.showMessage("no Profile chosen , go choose one and try again");
			}
			else
			{
				if(DB.containsProfile(AddFriend.getText()))
				{
					if(currentProfile.addFriend(AddFriend.getText()))
					{
						DB.getProfile(AddFriend.getText()).addFriend(currentProfile.getName());
						canvas.displayProfile(currentProfile);
						canvas.showMessage(AddFriend.getText()+" added as a Friend  ");
					}
					else
					{
						canvas.showMessage(currentProfile.getName()+" already has "+AddFriend.getText()+" as a friend");
					}
				}
				else
				{
					canvas.showMessage(AddFriend.getText()+" does not exist to add");
				}
			}
		}
		if(cmd.equalsIgnoreCase("Delete"))
		{
			if(DB.containsProfile(Name.getText()))
			{
				DB.deleteProfile(Name.getText());
				currentProfile=null;
				canvas.removeAll();
				canvas.showMessage("Profile of name "+Name.getText()+" deleted ");
			}
			else
			{
				currentProfile=null;
				canvas.removeAll();
				canvas.showMessage("Profile of name "+Name.getText()+" does not exist ");
			}
		}
		if(cmd.equalsIgnoreCase("Add"))
		{
			if(DB.containsProfile(Name.getText()))
			{
				currentProfile=DB.getProfile(Name.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("A profile wth the name "+currentProfile.getName()+" already exists");
			}
			else
			{
				FacePamphletProfile newProfile=new FacePamphletProfile(Name.getText());
				DB.addProfile(newProfile);
				currentProfile=newProfile;
				canvas.displayProfile(currentProfile);
				canvas.showMessage("New Profile Created !! ");
				
			}
		}
		if(cmd.equalsIgnoreCase("Lookup"))
		{
			if(DB.containsProfile(Name.getText()))
			{
				currentProfile=DB.getProfile(Name.getText());
				canvas.displayProfile(currentProfile);
				canvas.showMessage("A profile with name "+currentProfile.getName()+" is being displayed ");
			}
			else
			{
				currentProfile=null;
				canvas.removeAll();
				canvas.showMessage("Profile of name "+Name.getText()+" does not exist ");
			}
		}
		
	}
    /*instance variables*/
    private JTextField Name;
    private JTextField ChangeStatus;
    private JTextField ChangePicture;
    private JTextField AddFriend;
    private FacePamphletDatabase DB;
    private FacePamphletProfile currentProfile;
    private FacePamphletCanvas canvas;
    
    

}
