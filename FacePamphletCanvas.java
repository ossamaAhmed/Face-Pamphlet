/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() 
	{
		mssg=new GLabel("");
		mssg.setFont(MESSAGE_FONT);
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) 
	{
		remove(mssg);
		mssg.setLabel(msg);
		add(mssg,(getWidth()/2)-(mssg.getWidth()/2),getHeight()-BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) 
	{
	  removeAll();
	  GLabel l=displayName(profile);
	  displayImage(profile,l);
	  displayStatus(profile,l);
	  displayFriends(profile,l);
	}
	private void displayFriends(FacePamphletProfile profile,GLabel label)
	{
		GLabel FRIENDS=new GLabel("FRIENDS:");
		FRIENDS.setFont(PROFILE_FRIEND_LABEL_FONT);
		double x=(getWidth()/2)-(FRIENDS.getWidth()/2);
		double y=TOP_MARGIN+label.getHeight()+IMAGE_MARGIN;
		add(FRIENDS,x,y);
		Iterator<String> fr=profile.getFriends();
		while(fr.hasNext())
		{
			GLabel no=new GLabel(fr.next());
			no.setFont(PROFILE_FRIEND_FONT);
			y+=no.getHeight();
			add(no,x,y);
		}
	}
	private void displayStatus(FacePamphletProfile profile,GLabel label)
	{
		if(profile.getStatus().equalsIgnoreCase(""))
		{
			GLabel no=new GLabel("No STATUS");
			no.setFont(PROFILE_STATUS_FONT);
			add(no,LEFT_MARGIN,TOP_MARGIN+label.getHeight()+IMAGE_MARGIN+IMAGE_HEIGHT+STATUS_MARGIN+no.getHeight());
		}
		else
		{
			GLabel no=new GLabel(profile.getName()+" is :"+profile.getStatus());
			no.setFont(PROFILE_STATUS_FONT);
			add(no,LEFT_MARGIN,TOP_MARGIN+label.getHeight()+IMAGE_MARGIN+IMAGE_HEIGHT+STATUS_MARGIN+no.getHeight());
		}
	}
	private void displayImage(FacePamphletProfile profile,GLabel label)
	{
		if(profile.getImage()==null)
		{
			GRect space=new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
			add(space,LEFT_MARGIN,TOP_MARGIN+label.getHeight()+IMAGE_MARGIN);
			GLabel no=new GLabel("No Picture");
			no.setFont(PROFILE_IMAGE_FONT);
			add(no,LEFT_MARGIN+(IMAGE_WIDTH/2)-(no.getWidth()/2),TOP_MARGIN+label.getHeight()+IMAGE_MARGIN+(IMAGE_HEIGHT/2)+(no.getHeight()/2));
		}
		else
		{
			GImage I=profile.getImage();
			I.scale(IMAGE_WIDTH/I.getWidth(), IMAGE_HEIGHT/I.getHeight());
			add(I,LEFT_MARGIN,TOP_MARGIN+label.getHeight()+IMAGE_MARGIN);
		}
	}
	private GLabel displayName(FacePamphletProfile profile)
	{
		GLabel label=new GLabel(profile.getName());
		label.setFont(PROFILE_NAME_FONT);
		label.setColor(Color.BLUE);
		add(label,LEFT_MARGIN,TOP_MARGIN+label.getHeight());
		return label;
	}
/*instance variables*/
	private GLabel mssg;
	
}
