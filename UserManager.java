package com.chatServer;
import java.util.*;

public class UserManager {
	private static UserManager instance;
	
	//singleton pattern
	public static UserManager getInstance(){
		if(instance == null){
			instance = new UserManager();
		}
		return instance;
	}
	
	/*maps from a user id to a user*/
	private HashMap<Integer, User> usersById = new HashMap<>();
	
	/*maps from an account name to a user*/
	private HashMap<String, User> usersByAccountName = new HashMap<>();
	
	/*maps from the user id to an online user*/
	private HashMap<Integer, User> onlineUsers = new HashMap<>();
	
	//called by User.requestAddUser(), it calls both A.sentAddRequest() and B.receivedAddRequest()
	public void addUser(User fromUser, String toAccountName){
		User toUser = usersByAccountName.get(toAccountName);
		AddRequest req = new AddRequest(fromUser, toUser, new Date());
		fromUser.sentAddRequest(req);
		toUser.receivedAddRequest(req);
	}
	public void approveAddRequest(AddRequest req){
		 req.status = RequestStatus.Accepted;
		 User from = req.getFromUser();
		 User to = req.getToUser();
		 from.addContact(to);
		 to.addContact(from);
	}
	public void rejectAddRequest(AddRequest req){
		req.status = RequestStatus.Rejected;
		User from = req.getFromUser();
		User to = req.getToUser();
		from.removeAddRequest(req);
		to.removeAddRequest(req);
	}
	public void userSignedOn(String accountName){
		User user = usersByAccountName.get(accountName);
		if(user != null){
			user.setStatus(new UserStatus(UserStatusType.Available, ""));
			onlineUsers.put(user.getId(), user);
		}
	}
	public void userSignedOff(String accountName){
		User user = usersByAccountName.get(accountName);
		if(user != null){
			user.setStatus(new UserStatus(UserStatusType.Offline, ""));
			onlineUsers.remove(user.getId());
		}
	}
	 
}
































